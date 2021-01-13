package miui.autoinstall.config.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.miui.internal.R;
import java.io.File;
import java.lang.ref.WeakReference;
import java.miui.autoinstall.config.pm.a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import miui.autoinstall.config.activity.PromptRestoreDialogActivity;
import miui.autoinstall.config.activity.RestoreFailedDialogActivity;
import miui.autoinstall.config.download.AutoInstallDownLoader;
import miui.autoinstall.config.download.AutoInstallNotification;
import miui.autoinstall.config.entity.LocalAppInfo;
import miui.autoinstall.config.entity.RequestAppInfo;
import miui.autoinstall.config.entity.RequestEntity;
import miui.autoinstall.config.entity.ResponseAppInfo;
import miui.autoinstall.config.entity.RestoreRecord;
import miui.autoinstall.config.pm.AutoInstallFileProvider;
import miui.autoinstall.config.pm.PackageManagerCompat;
import miui.autoinstall.config.utils.AutoInstallRequestUtil;
import miui.autoinstall.config.utils.SignUtil;
import org.json.JSONException;

public class AutoInstallService extends Service implements a {
    private static final String ACTION_AUTO_INSTALL = "miui.autoinstall.config.action.AUTOINSTALL";
    private static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    public static final String ACTION_DOWNLOAD_BY_PASS = "miui.autoinstall.config.action.DOWNLOADBYPASS";
    private static final String ACTION_PROVISION_COMPLETED = "android.provision.action.PROVISION_COMPLETE";
    private static final String ACTION_START_INSTALL = "miui.autoinstall.config.ACTION_INSTALL";
    public static final String ACTION_START_PROCEDURE = "miui.autoinstall.config.ACTION_START";
    private static final String EXTRA_APK_PATH = "extra_apk_path";
    public static final String EXTRA_IS_MOBILE_DATA_REMIND = "extra_is_mobile_data_remind";
    public static final String EXTRA_MOBILE_DATA_CONSUME = "extra_mobile_data_consume";
    private static final String KEY_APP_INFO = "appInfo";
    private static final String TAG = "AutoInstallService";
    private int mCurrentRestoring = 1;
    private DownloadCompleteReceiver mDownloadCompleteReceiver;
    private Map<Long, ResponseAppInfo.ResponseAppInfoItem> mDownloadIds = new HashMap();
    private AutoInstallDownLoader mDownloader;
    private boolean mHasError;
    private Queue<ResponseAppInfo.ResponseAppInfoItem> mInstallQueue = new LinkedList();
    private boolean mIsProvision;
    private boolean mIsRestoring;
    private List<LocalAppInfo> mLocalAppInfos;
    private AutoInstallNotification mNotification;
    private PackageManagerCompat mPackageManagerCompat;
    private Map<String, String> mPackagePath = new HashMap();
    private List<String> mPackageRestoring = new ArrayList();

    /* access modifiers changed from: private */
    public static final class DownloadCompleteReceiver extends BroadcastReceiver {
        private WeakReference<AutoInstallService> mOuter;

        DownloadCompleteReceiver(AutoInstallService autoInstallService) {
            this.mOuter = new WeakReference<>(autoInstallService);
        }

        public void onReceive(Context context, Intent intent) {
            AutoInstallService autoInstallService = this.mOuter.get();
            if (autoInstallService != null) {
                long longExtra = intent.getLongExtra("extra_download_id", -1);
                ResponseAppInfo.ResponseAppInfoItem responseAppInfoItem = (ResponseAppInfo.ResponseAppInfoItem) this.mOuter.get().mDownloadIds.get(Long.valueOf(longExtra));
                if (responseAppInfoItem != null) {
                    if (autoInstallService.mDownloader.queryStatus(longExtra) == 8) {
                        String queryFileDir = autoInstallService.mDownloader.queryFileDir(longExtra);
                        Log.d(AutoInstallService.TAG, "onReceive: " + queryFileDir);
                        autoInstallService.submitInstall(queryFileDir, responseAppInfoItem.packageName);
                        return;
                    }
                    autoInstallService.showRestoreError();
                }
            }
        }
    }

    private long calculateMobileData(String str, String str2, String str3) {
        if (ensureFileNeedDownload(str, str3) == null) {
            return AutoInstallRequestUtil.calculateFileSize(str2);
        }
        return 0;
    }

    private void deleteInstalledApk(String str) {
        if (!TextUtils.isEmpty(str)) {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    private void doDownload(ResponseAppInfo.ResponseAppInfoItem responseAppInfoItem) {
        File ensureFileNeedDownload = ensureFileNeedDownload(responseAppInfoItem.apkName, responseAppInfoItem.apkMd5);
        if (ensureFileNeedDownload != null) {
            submitInstall(ensureFileNeedDownload.getPath(), responseAppInfoItem.apkName);
            return;
        }
        long enqueue = this.mDownloader.enqueue(this, responseAppInfoItem.cdnPath, responseAppInfoItem.apkName);
        Log.d(TAG, "doDownload: enqueue：" + responseAppInfoItem.apkName);
        if (enqueue != -1) {
            this.mDownloadIds.put(Long.valueOf(enqueue), responseAppInfoItem);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void downloadApp(ResponseAppInfo responseAppInfo) {
        if (responseAppInfo != null) {
            this.mInstallQueue.clear();
            this.mPackageRestoring.clear();
            List<ResponseAppInfo.ResponseAppInfoItem> list = responseAppInfo.data;
            registerDownloadCompleteReceiver();
            boolean isMobileData = isMobileData();
            long j = 0;
            for (ResponseAppInfo.ResponseAppInfoItem responseAppInfoItem : list) {
                if (responseAppInfoItem != null) {
                    Log.d(TAG, "downloadApp: enqueue:" + responseAppInfoItem.apkName);
                    if (!isPackageInstalled(responseAppInfoItem.packageName)) {
                        if (isMobileData) {
                            j += calculateMobileData(responseAppInfoItem.apkName, responseAppInfoItem.cdnPath, responseAppInfoItem.apkMd5);
                        }
                        this.mPackageRestoring.add(responseAppInfoItem.packageName);
                        this.mInstallQueue.add(responseAppInfoItem);
                    } else {
                        this.mPackageManagerCompat.updateInstallRecord(this, responseAppInfoItem.packageName, true);
                    }
                }
            }
            downloadIfRemind(j);
        }
    }

    private void downloadByPass() {
        if (!this.mInstallQueue.isEmpty()) {
            showRestoring(this.mPackageRestoring.size());
            ResponseAppInfo.ResponseAppInfoItem remove = this.mInstallQueue.remove();
            if (remove != null) {
                doDownload(remove);
                return;
            }
            return;
        }
        showRestoreError();
    }

    private void downloadIfRemind(long j) {
        if (j > 0) {
            showMobileDataRemind(j);
        } else {
            downloadByPass();
        }
    }

    private File ensureFileNeedDownload(String str, String str2) {
        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (externalFilesDir == null || !externalFilesDir.exists()) {
            return null;
        }
        File file = new File(externalFilesDir.getPath(), str);
        if (!file.exists()) {
            return null;
        }
        if (TextUtils.equals(SignUtil.getFileMD5(file), str2)) {
            return file;
        }
        file.delete();
        return null;
    }

    private void getNext() {
        if (!this.mInstallQueue.isEmpty()) {
            this.mCurrentRestoring++;
            this.mNotification.updateProgress(this.mCurrentRestoring, this.mPackageRestoring.size());
            ResponseAppInfo.ResponseAppInfoItem remove = this.mInstallQueue.remove();
            if (remove != null) {
                doDownload(remove);
            }
        } else if (hasError()) {
            showRestoreError();
        } else {
            this.mNotification.showSuccessNotification();
            this.mCurrentRestoring = 1;
            stopSelf();
            setRestoring(false);
        }
    }

    private TreeMap<String, Object> getParamsMap(String str) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put(KEY_APP_INFO, str);
        return treeMap;
    }

    private boolean hasError() {
        return this.mHasError;
    }

    private void install(Uri uri, String str) {
        this.mPackageManagerCompat.installPackage(uri, str);
    }

    private boolean isMobileData() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == 0;
    }

    private boolean isOverProvision() {
        return Settings.Secure.getInt(getContentResolver(), "device_provisioned", 0) != 0;
    }

    private boolean isPackageAllExisted(List<LocalAppInfo> list) {
        for (LocalAppInfo localAppInfo : list) {
            if (!(localAppInfo == null || this.mPackageManagerCompat.packageExists(getPackageManager(), localAppInfo.packageName))) {
                Log.d(TAG, "isPackageAllExisted: package:" + localAppInfo.packageName + " not installed");
                return false;
            }
        }
        return true;
    }

    private boolean isPackageAllInstalled(List<LocalAppInfo> list) {
        if (isProvisionAction()) {
            Log.d(TAG, "isPackageAllInstalled: isProvision");
            return isPackageAllExisted(list);
        }
        Log.d(TAG, "isPackageAllInstalled: reboot");
        boolean isRestoreNotNow = this.mPackageManagerCompat.isRestoreNotNow();
        boolean isPackageAllExisted = isPackageAllExisted(list);
        Log.d(TAG, "isPackageAllInstalled: isRestoreNotNow:" + isRestoreNotNow + ",allInstalled:" + isPackageAllExisted);
        if (isRestoreNotNow && !isPackageAllExisted) {
            return false;
        }
        boolean hasFailedRecord = this.mPackageManagerCompat.hasFailedRecord(this, list);
        Log.d(TAG, "isPackageAllInstalled: hasFailedRecord:" + hasFailedRecord);
        return !hasFailedRecord;
    }

    private boolean isPackageAllRecorded(List<LocalAppInfo> list) {
        try {
            return this.mPackageManagerCompat.isRecordAllInstalled(this, list);
        } catch (JSONException e2) {
            Log.e(TAG, "isPackageAllExistedInRecord: ", e2);
            return false;
        }
    }

    private boolean isPackageInstalled(String str) {
        if (isProvisionAction()) {
            return this.mPackageManagerCompat.packageExists(getPackageManager(), str);
        }
        try {
            boolean isPackageInstalledRecord = this.mPackageManagerCompat.isPackageInstalledRecord(this, str, null);
            boolean packageExists = this.mPackageManagerCompat.packageExists(getPackageManager(), str);
            Log.d(TAG, str + ":isPackageRecord:" + isPackageInstalledRecord + ",isPackageInstalled:" + packageExists);
            return isPackageInstalledRecord || packageExists;
        } catch (JSONException e2) {
            Log.e(TAG, "isPackageInstalled: ", e2);
            return false;
        }
    }

    private boolean isRestoring() {
        return this.mIsRestoring;
    }

    private void onMiuiHome() {
        if (isRestoring()) {
            showRestoringToast();
        } else {
            onSystemReboot();
        }
    }

    private void onSystemReboot() {
        if (isOverProvision()) {
            start();
        }
    }

    private boolean packageCompleted(List<RestoreRecord> list, String str) {
        for (RestoreRecord restoreRecord : list) {
            if (TextUtils.equals(restoreRecord.packageName, str)) {
                return true;
            }
        }
        return false;
    }

    private void recordNotInstalledPkg() {
        if (this.mLocalAppInfos != null) {
            PackageManager packageManager = getPackageManager();
            for (LocalAppInfo localAppInfo : this.mLocalAppInfos) {
                if (localAppInfo != null) {
                    PackageManagerCompat packageManagerCompat = this.mPackageManagerCompat;
                    String str = localAppInfo.packageName;
                    packageManagerCompat.updateInstallRecord(this, str, packageManagerCompat.packageExists(packageManager, str));
                }
            }
        }
    }

    private void registerDownloadCompleteReceiver() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE");
        this.mDownloadCompleteReceiver = new DownloadCompleteReceiver(this);
        registerReceiver(this.mDownloadCompleteReceiver, intentFilter);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private ResponseAppInfo requestAppInfo(List<LocalAppInfo> list) {
        ArrayList arrayList = new ArrayList();
        List<RestoreRecord> completedRestore = this.mPackageManagerCompat.getCompletedRestore(this);
        boolean z = completedRestore != null && !completedRestore.isEmpty();
        for (LocalAppInfo localAppInfo : list) {
            if (localAppInfo != null && (!z || !packageCompleted(completedRestore, localAppInfo.packageName))) {
                RequestAppInfo requestAppInfo = new RequestAppInfo();
                requestAppInfo.pn = localAppInfo.packageName;
                requestAppInfo.pvc = String.valueOf(localAppInfo.versionCode);
                arrayList.add(requestAppInfo);
            }
        }
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.nonceStr = SignUtil.getNonceStr();
        requestEntity.appInfo = arrayList;
        requestEntity.sign = SignUtil.getSign(getParamsMap(RequestEntity.list2Json(arrayList)), requestEntity.nonceStr);
        return AutoInstallRequestUtil.requestAppInfo(requestEntity);
    }

    private void requestDownload() {
        setRestoring(true);
        setHasError(false);
        showRestoringToast();
        recordNotInstalledPkg();
        if (this.mDownloader == null) {
            this.mDownloader = new AutoInstallDownLoader(getApplicationContext());
        }
        new Thread(new Runnable() {
            /* class miui.autoinstall.config.service.AutoInstallService.AnonymousClass1 */

            public void run() {
                AutoInstallService autoInstallService = AutoInstallService.this;
                ResponseAppInfo requestAppInfo = autoInstallService.requestAppInfo(autoInstallService.mLocalAppInfos);
                if (requestAppInfo != null) {
                    AutoInstallService.this.downloadApp(requestAppInfo);
                } else {
                    AutoInstallService.this.showRestoreError();
                }
            }
        }).start();
    }

    private void setHasError(boolean z) {
        this.mHasError = z;
    }

    private void setRestoring(boolean z) {
        this.mIsRestoring = z;
    }

    private void showMobileDataRemind(long j) {
        Intent intent = new Intent(this, PromptRestoreDialogActivity.class);
        intent.putExtra(EXTRA_MOBILE_DATA_CONSUME, j);
        intent.putExtra(EXTRA_IS_MOBILE_DATA_REMIND, true);
        intent.addFlags(268435456);
        startActivity(intent);
    }

    private void showPromptRestore() {
        Intent intent = new Intent(this, PromptRestoreDialogActivity.class);
        intent.addFlags(268435456);
        startActivity(intent);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showRestoreError() {
        Intent intent = new Intent(this, RestoreFailedDialogActivity.class);
        intent.setFlags(268435456);
        startActivity(intent);
        stopSelf();
        setRestoring(false);
        setHasError(false);
        this.mNotification.clearAll();
    }

    private void showRestoring(int i) {
        this.mNotification.showStartNotification(i);
    }

    private void showRestoringToast() {
        Toast.makeText(this, getResources().getString(R.string.system_app_is_restore), 0).show();
    }

    private void start() {
        List<LocalAppInfo> list = this.mLocalAppInfos;
        if (list != null && !list.isEmpty()) {
            if (isPackageAllInstalled(this.mLocalAppInfos)) {
                stopSelf();
            } else {
                showPromptRestore();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void submitInstall(String str, String str2) {
        if (this.mPackageManagerCompat.packageExists(getPackageManager(), str2)) {
            this.mPackageManagerCompat.updateInstallRecord(this, str2, true);
            deleteInstalledApk(str);
            getNext();
            return;
        }
        this.mPackagePath.put(str2, str);
        Intent intent = new Intent(this, AutoInstallService.class);
        intent.putExtra(EXTRA_APK_PATH, str);
        intent.setAction(ACTION_START_INSTALL);
        File file = new File(str);
        if (!file.exists()) {
            Log.d(TAG, "submitInstall: file not exist:" + file.getPath());
            setHasError(true);
            getNext();
            return;
        }
        Uri uriForFile = AutoInstallFileProvider.getUriForFile(this, "miui.autoinstall.config.fileprovider", file);
        grantUriPermission("com.xiaomi.market", uriForFile, 3);
        intent.setData(uriForFile);
        startService(intent);
    }

    public boolean isProvisionAction() {
        return this.mIsProvision;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.mPackageManagerCompat = new PackageManagerCompat(this);
        this.mPackageManagerCompat.bindServices(this);
        this.mPackageManagerCompat.registerInstallListener(this);
        this.mLocalAppInfos = this.mPackageManagerCompat.loadSystemAppInfoLocal();
        this.mNotification = new AutoInstallNotification(this);
    }

    public void onDestroy() {
        DownloadCompleteReceiver downloadCompleteReceiver = this.mDownloadCompleteReceiver;
        if (downloadCompleteReceiver != null) {
            unregisterReceiver(downloadCompleteReceiver);
        }
        this.mPackageManagerCompat.unbindService(this);
        super.onDestroy();
    }

    @Override // java.miui.autoinstall.config.pm.a
    public void onRefuseInstall(String str, int i) {
        Log.d(TAG, "packageInstallFailed: " + str);
        if (!TextUtils.isEmpty(str)) {
            this.mPackageManagerCompat.updateInstallRecord(this, str, false);
        }
        setHasError(true);
        getNext();
    }

    @Override // java.miui.autoinstall.config.pm.a
    public void onServiceDead() {
        Log.d(TAG, "onServiceDead");
        this.mCurrentRestoring = 1;
        showRestoreError();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null) {
            String action = intent.getAction() != null ? intent.getAction() : "";
            char c2 = 65535;
            switch (action.hashCode()) {
                case -1589339876:
                    if (action.equals(ACTION_DOWNLOAD_BY_PASS)) {
                        c2 = 5;
                        break;
                    }
                    break;
                case -782377083:
                    if (action.equals(ACTION_START_PROCEDURE)) {
                        c2 = 0;
                        break;
                    }
                    break;
                case -760896832:
                    if (action.equals(ACTION_AUTO_INSTALL)) {
                        c2 = 2;
                        break;
                    }
                    break;
                case -685309954:
                    if (action.equals(ACTION_START_INSTALL)) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -510741405:
                    if (action.equals(ACTION_PROVISION_COMPLETED)) {
                        c2 = 4;
                        break;
                    }
                    break;
                case 798292259:
                    if (action.equals(ACTION_BOOT_COMPLETED)) {
                        c2 = 3;
                        break;
                    }
                    break;
            }
            if (c2 == 0) {
                requestDownload();
            } else if (c2 == 1) {
                install(intent.getData(), intent.getStringExtra(EXTRA_APK_PATH));
            } else if (c2 == 2) {
                Log.d(TAG, "onStartCommand: auto install from outside");
                onMiuiHome();
            } else if (c2 == 3) {
                Log.d(TAG, "onStartCommand: system boot completed receiver: isProvisionAction:" + this.mIsProvision);
                onSystemReboot();
            } else if (c2 == 4) {
                this.mIsProvision = true;
                Log.d(TAG, "onStartCommand: provisioned completed receiver:isProvisionAction:" + this.mIsProvision);
                start();
            } else if (c2 == 5) {
                downloadByPass();
            }
        }
        return super.onStartCommand(intent, i, i2);
    }

    @Override // java.miui.autoinstall.config.pm.a
    public void packageInstalled(String str, int i) {
        Log.d(TAG, "packageInstalled: " + str + ",returnCode:" + i);
        boolean z = i == 1 || i == -25;
        if (!z) {
            setHasError(true);
        } else {
            deleteInstalledApk(this.mPackagePath.remove(str));
        }
        this.mPackageManagerCompat.updateInstallRecord(this, str, z);
        getNext();
    }
}
