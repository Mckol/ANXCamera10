package miui.autoinstall.config.pm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import b.b.a.a;
import java.miui.autoinstall.config.pm.MarketInstallObserver;
import java.util.ArrayList;
import java.util.List;
import miui.autoinstall.config.entity.LocalAppInfo;
import miui.autoinstall.config.entity.RestoreRecord;
import miui.autoinstall.config.utils.FileUtil;
import miui.autoinstall.config.utils.SharedPreferencesUtil;
import miui.autoinstall.config.utils.SignUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PackageManagerCompat {
    private static final String FILE_INSTALL_RECORD = "sp_file_auto-install-record";
    public static final int INSTALL_FAILED_VERSION_DOWNGRADE = -25;
    public static final int INSTALL_SUCCESSFUL = 1;
    private static final String KEY_INSTALL_RECORD = "auto-install-record";
    private static final String KEY_LOCAL_RECORD_INSTALL_RESULT = "installResult";
    private static final String KEY_LOCAL_RECORD_PACKAGE_NAME = "packageName";
    private static final String KEY_RESTORE_NOT_NOW = "restore_not_now";
    private static final String KEY_VERSION_CODE = "versionCode";
    private static final String PATH_LOCAL_APP_INFO = "/system/etc/auto-install.json";
    private static final String SERVICE_ACTION = "com.xiaomi.market.action.INSTALL";
    private static final String SERVICE_PACKAGE_NAME = "com.xiaomi.market";
    private static final String TAG = "PackageManagerCompat";
    private final ServiceConnection mConnection = new ServiceConnection() {
        /* class miui.autoinstall.config.pm.PackageManagerCompat.AnonymousClass1 */

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PackageManagerCompat.this.mService = a.b.asInterface(iBinder);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            PackageManagerCompat.this.mService = null;
        }
    };
    private Context mContext;
    private List<LocalAppInfo> mLocalAppConfig;
    private a mService;
    private boolean mServiceBound;
    private SharedPreferencesUtil mSpUtil;
    private MarketInstallObserver marketInstallObserver;

    public PackageManagerCompat(Context context) {
        this.mContext = context;
        this.mSpUtil = new SharedPreferencesUtil(context, FILE_INSTALL_RECORD);
    }

    private void addObject2Array(JSONArray jSONArray, String str, boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(KEY_LOCAL_RECORD_PACKAGE_NAME, str);
            jSONObject.put(KEY_LOCAL_RECORD_INSTALL_RESULT, z);
            jSONArray.put(jSONObject);
        } catch (JSONException e2) {
            Log.e(TAG, "addObject2Array: ", e2);
        }
    }

    private PackageInfo getPackageArchiveInfo(String str, int i) {
        return this.mContext.getPackageManager().getPackageArchiveInfo(str, i);
    }

    private boolean isFailedRecord(String str, JSONArray jSONArray) {
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (TextUtils.equals(jSONObject.getString(KEY_LOCAL_RECORD_PACKAGE_NAME), str) && !jSONObject.getBoolean(KEY_LOCAL_RECORD_INSTALL_RESULT)) {
                    return true;
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

    private JSONArray loadInstallRecord(Context context) {
        String string = this.mSpUtil.getString(KEY_INSTALL_RECORD);
        Log.d(TAG, "loadInstallRecord: " + string);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return new JSONArray(string);
        } catch (JSONException e2) {
            Log.e(TAG, "loadInstallRecord: ", e2);
            return null;
        }
    }

    private void saveInstallRecord(String str) {
        Log.d(TAG, "saveInstallRecord: " + str);
        this.mSpUtil.saveString(KEY_INSTALL_RECORD, str);
    }

    public void bindServices(Context context) {
        Intent intent = new Intent(SERVICE_ACTION);
        intent.setPackage(SERVICE_PACKAGE_NAME);
        this.mServiceBound = context.bindService(intent, this.mConnection, 1);
    }

    public List<RestoreRecord> getCompletedRestore(Context context) {
        ArrayList arrayList = new ArrayList();
        JSONArray loadInstallRecord = loadInstallRecord(context);
        if (loadInstallRecord == null) {
            return new ArrayList();
        }
        int length = loadInstallRecord.length();
        for (int i = 0; i < length; i++) {
            try {
                JSONObject jSONObject = loadInstallRecord.getJSONObject(i);
                if (jSONObject != null && jSONObject.getBoolean(KEY_LOCAL_RECORD_INSTALL_RESULT)) {
                    RestoreRecord restoreRecord = new RestoreRecord();
                    restoreRecord.packageName = jSONObject.getString(KEY_LOCAL_RECORD_PACKAGE_NAME);
                    restoreRecord.installSuccess = true;
                    arrayList.add(restoreRecord);
                }
            } catch (JSONException e2) {
                Log.e(TAG, "isRestoreCompleted: ", e2);
            }
        }
        return arrayList;
    }

    public boolean hasFailedRecord(Context context, List<LocalAppInfo> list) {
        JSONArray loadInstallRecord = loadInstallRecord(context);
        if (loadInstallRecord == null) {
            Log.d(TAG, "hasFailedRecord: has not install record");
            return false;
        }
        for (LocalAppInfo localAppInfo : list) {
            if (localAppInfo != null && isFailedRecord(localAppInfo.packageName, loadInstallRecord)) {
                return true;
            }
        }
        return false;
    }

    public void installPackage(Uri uri, String str) {
        Log.d(TAG, "installPackage: " + uri);
        if (this.mService != null) {
            try {
                String loadPkgSignature = SignUtil.loadPkgSignature(getPackageArchiveInfo(str, 64));
                Bundle bundle = new Bundle();
                bundle.putString("extra_caller_package_name", "com.miui.core");
                bundle.putString("extra_apk_signature", loadPkgSignature);
                this.mService.a(uri, this.marketInstallObserver, bundle);
            } catch (RemoteException e2) {
                Log.e(TAG, "installPackage: ", e2);
            }
        }
    }

    public boolean isPackageInstalledRecord(Context context, String str, JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) {
            jSONArray = loadInstallRecord(context);
        }
        if (jSONArray == null) {
            return false;
        }
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            if (TextUtils.equals(jSONObject.getString(KEY_LOCAL_RECORD_PACKAGE_NAME), str) && jSONObject.getBoolean(KEY_LOCAL_RECORD_INSTALL_RESULT)) {
                return true;
            }
        }
        return false;
    }

    public boolean isRecordAllInstalled(Context context, List<LocalAppInfo> list) throws JSONException {
        JSONArray loadInstallRecord = loadInstallRecord(context);
        if (loadInstallRecord == null) {
            Log.d(TAG, "isRecordAllInstalled: has not install record");
            return true;
        }
        PackageManager packageManager = context.getPackageManager();
        for (LocalAppInfo localAppInfo : list) {
            if (!(localAppInfo == null || isPackageInstalledRecord(context, localAppInfo.packageName, loadInstallRecord) || packageExists(packageManager, localAppInfo.packageName))) {
                return false;
            }
        }
        return true;
    }

    public boolean isRestoreNotNow() {
        return this.mSpUtil.getBoolean(KEY_RESTORE_NOT_NOW, false);
    }

    public List<LocalAppInfo> loadSystemAppInfoLocal() {
        if (this.mLocalAppConfig == null) {
            String fileContent = FileUtil.getFileContent(PATH_LOCAL_APP_INFO);
            if (!TextUtils.isEmpty(fileContent)) {
                try {
                    JSONArray jSONArray = new JSONArray(fileContent);
                    int length = jSONArray.length();
                    this.mLocalAppConfig = new ArrayList();
                    for (int i = 0; i < length; i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        LocalAppInfo localAppInfo = new LocalAppInfo();
                        localAppInfo.packageName = jSONObject.getString(KEY_LOCAL_RECORD_PACKAGE_NAME);
                        localAppInfo.versionCode = jSONObject.getInt(KEY_VERSION_CODE);
                        this.mLocalAppConfig.add(localAppInfo);
                    }
                    return this.mLocalAppConfig;
                } catch (JSONException e2) {
                    Log.e(TAG, "jsonToAppInfoList: ", e2);
                }
            }
        }
        return this.mLocalAppConfig;
    }

    public boolean packageExists(PackageManager packageManager, String str) {
        try {
            return packageManager.getApplicationInfo(str, 128) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public void recordRestoreNotNow(boolean z) {
        this.mSpUtil.saveBoolean(KEY_RESTORE_NOT_NOW, z);
    }

    public void registerInstallListener(java.miui.autoinstall.config.pm.a aVar) {
        this.marketInstallObserver = new MarketInstallObserver(aVar);
    }

    public void unbindService(Context context) {
        if (this.mServiceBound) {
            context.unbindService(this.mConnection);
            this.mServiceBound = false;
        }
    }

    public void updateInstallRecord(Context context, String str, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            JSONArray loadInstallRecord = loadInstallRecord(context);
            if (loadInstallRecord != null) {
                int length = loadInstallRecord.length();
                boolean z2 = false;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    try {
                        JSONObject jSONObject = loadInstallRecord.getJSONObject(i);
                        if (TextUtils.equals(jSONObject.getString(KEY_LOCAL_RECORD_PACKAGE_NAME), str)) {
                            jSONObject.put(KEY_LOCAL_RECORD_INSTALL_RESULT, z);
                            z2 = true;
                            break;
                        }
                        i++;
                    } catch (JSONException e2) {
                        Log.e(TAG, "updateInstallRecord: ", e2);
                    }
                }
                if (!z2) {
                    addObject2Array(loadInstallRecord, str, z);
                }
                saveInstallRecord(loadInstallRecord.toString());
                return;
            }
            JSONArray jSONArray = new JSONArray();
            addObject2Array(jSONArray, str, z);
            saveInstallRecord(jSONArray.toString());
        }
    }
}
