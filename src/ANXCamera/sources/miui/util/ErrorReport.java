package miui.util;

import android.app.ActivityManager;
import android.app.ApplicationErrorReport;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.miui.internal.util.DeviceHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import miui.net.ConnectivityHelper;
import miui.os.DropBoxManager;
import miui.os.SystemProperties;
import miui.provider.ExtraSettings;
import miui.security.DigestUtils;
import miui.telephony.TelephonyHelper;
import miui.text.ExtraTextUtils;
import org.json.JSONObject;

public class ErrorReport {
    private static final int ANR_MAX_LINE_NUMBER = 300;
    public static final String DROPBOX_TAG = "fc_anr";
    private static final String ERROR_TYPE_ANR = "anr";
    private static final String ERROR_TYPE_FC = "fc";
    public static final int FLAG_SEND_DIRECTLY = 2;
    public static final int FLAG_UNMETERED_NETWORK_ONLY = 1;
    private static final String JSON_ANR_ACTIVITY = "anr_activity";
    private static final String JSON_ANR_CAUSE = "anr_cause";
    private static final String JSON_APP_VESION = "app_version";
    private static final String JSON_BUILD_VERSION = "build_version";
    private static final String JSON_DEVICE = "device";
    private static final String JSON_ERROR_TYPE = "error_type";
    private static final String JSON_EXCEPTION_CLASS = "exception_class";
    private static final String JSON_EXCEPTION_SOURCE_METHOD = "exception_source_method";
    private static final String JSON_IMEI = "imei";
    private static final String JSON_MAC_ADDRESS = "mac_address";
    private static final String JSON_MODEL = "model";
    private static final String JSON_NETWORK = "network";
    private static final String JSON_PACKAGE_NAME = "package_name";
    private static final String JSON_PLATFORM = "platform";
    private static final String JSON_STACK_TRACK = "stack_track";
    private static final String JSON_USER_ALLOWED = "user_allowed";
    private static final String JSON_WIFI_STATE = "wifi_state";
    private static final String TAG = "ErrorReport";

    private static class AnrDataBuilder implements DataBuilder {
        private Context iContext;
        private String iPackageName;
        private ActivityManager.ProcessErrorStateInfo iState;

        public AnrDataBuilder(Context context, String str, ActivityManager.ProcessErrorStateInfo processErrorStateInfo) {
            this.iContext = context;
            this.iPackageName = str;
            this.iState = processErrorStateInfo;
        }

        @Override // miui.util.ErrorReport.DataBuilder
        public JSONObject getData() {
            return ErrorReport.getAnrData(this.iContext, this.iPackageName, this.iState);
        }
    }

    /* access modifiers changed from: private */
    public interface DataBuilder {
        JSONObject getData();
    }

    /* access modifiers changed from: private */
    public static class ExceptionDataBuilder implements DataBuilder {
        private Context iContext;
        private ApplicationErrorReport.CrashInfo iCrashInfo;
        private String iPackageName;

        public ExceptionDataBuilder(Context context, String str, ApplicationErrorReport.CrashInfo crashInfo) {
            this.iContext = context;
            this.iPackageName = str;
            this.iCrashInfo = crashInfo;
        }

        @Override // miui.util.ErrorReport.DataBuilder
        public JSONObject getData() {
            return ErrorReport.getExceptionData(this.iContext, this.iPackageName, this.iCrashInfo);
        }
    }

    protected ErrorReport() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static JSONObject getAnrData(Context context, String str, ActivityManager.ProcessErrorStateInfo processErrorStateInfo) {
        if (processErrorStateInfo == null) {
            return null;
        }
        JSONObject commonData = getCommonData(context, str);
        try {
            commonData.put(JSON_ERROR_TYPE, ERROR_TYPE_ANR);
            commonData.put(JSON_ANR_CAUSE, processErrorStateInfo.shortMsg);
            commonData.put(JSON_ANR_ACTIVITY, processErrorStateInfo.tag == null ? "" : processErrorStateInfo.tag);
            commonData.put(JSON_STACK_TRACK, getAnrStackTrack());
        } catch (Exception e2) {
            Log.w(TAG, "Fail to getAnrData", e2);
        }
        return commonData;
    }

    /*  JADX ERROR: IF instruction can be used only in fallback mode
        jadx.core.utils.exceptions.CodegenException: IF instruction can be used only in fallback mode
        	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:604)
        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:486)
        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:249)
        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:217)
        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:110)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:56)
        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:93)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:59)
        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:99)
        	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:306)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:69)
        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:93)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:59)
        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:99)
        	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:194)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:67)
        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:93)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:59)
        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:99)
        	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:306)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:69)
        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:93)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:59)
        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:99)
        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:143)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:93)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:59)
        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:244)
        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:237)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:342)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:295)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:264)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
        */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x005d, code lost:
        if (r2 != null) goto L_0x004e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0058 A[SYNTHETIC, Splitter:B:27:0x0058] */
    private static java.lang.String getAnrStackTrack() throws java.io.IOException {
        /*
        // Method dump skipped, instructions count: 101
        */
        throw new UnsupportedOperationException("Method not decompiled: miui.util.ErrorReport.getAnrStackTrack():java.lang.String");
    }

    private static JSONObject getCommonData(Context context, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(JSON_NETWORK, getNetworkName(context));
            jSONObject.put(JSON_DEVICE, getDeviceString());
            jSONObject.put("model", getModel());
            jSONObject.put(JSON_MAC_ADDRESS, getMacAddressMd5());
            jSONObject.put(JSON_IMEI, getIMEIMd5());
            jSONObject.put(JSON_PLATFORM, Build.VERSION.RELEASE);
            jSONObject.put(JSON_BUILD_VERSION, Build.VERSION.INCREMENTAL);
            jSONObject.put("package_name", str);
            jSONObject.put("app_version", getPackageVersion(context, str));
            int i = 1;
            jSONObject.put(JSON_WIFI_STATE, ConnectivityHelper.getInstance().isWifiConnected() ? 1 : 0);
            if (!isUserAllowed(context)) {
                i = 0;
            }
            jSONObject.put(JSON_USER_ALLOWED, i);
        } catch (Exception e2) {
            Log.w(TAG, "Fail to getCommonData", e2);
        }
        return jSONObject;
    }

    private static String getDeviceString() {
        String str = SystemProperties.get("ro.product.mod_device", null);
        return TextUtils.isEmpty(str) ? Build.DEVICE : str;
    }

    public static JSONObject getExceptionData(Context context, String str, ApplicationErrorReport.CrashInfo crashInfo) {
        if (crashInfo == null) {
            return null;
        }
        JSONObject commonData = getCommonData(context, str);
        try {
            commonData.put(JSON_ERROR_TYPE, "fc");
            commonData.put(JSON_EXCEPTION_CLASS, crashInfo.exceptionClassName);
            commonData.put(JSON_EXCEPTION_SOURCE_METHOD, crashInfo.throwClassName + "." + crashInfo.throwMethodName);
            commonData.put(JSON_STACK_TRACK, crashInfo.stackTrace);
        } catch (Exception e2) {
            Log.w(TAG, "Fail to getExceptionData", e2);
        }
        return commonData;
    }

    private static String getIMEIMd5() {
        String deviceId = TelephonyHelper.getInstance().getDeviceId();
        return !TextUtils.isEmpty(deviceId) ? ExtraTextUtils.toHexReadable(DigestUtils.get(deviceId, DigestUtils.ALGORITHM_MD5)) : "";
    }

    private static String getMacAddressMd5() {
        String macAddress = ConnectivityHelper.getInstance().getMacAddress();
        return !TextUtils.isEmpty(macAddress) ? ExtraTextUtils.toHexReadable(DigestUtils.get(macAddress, DigestUtils.ALGORITHM_MD5)) : "";
    }

    private static String getModel() {
        return Build.MODEL;
    }

    private static String getNetworkName(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getNetworkOperatorName();
    }

    private static String getPackageVersion(Context context, String str) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 0);
            int i = packageInfo.applicationInfo.flags;
            if ((i & 1) != 0 || (i & 128) != 0) {
                return Build.VERSION.INCREMENTAL;
            }
            return packageInfo.versionName + " (" + packageInfo.versionCode + ")";
        } catch (PackageManager.NameNotFoundException e2) {
            Log.w(TAG, "Fail to find package: " + str, e2);
            return "";
        }
    }

    public static boolean isUserAllowed(Context context) {
        return ExtraSettings.Secure.getInt(context.getContentResolver(), ExtraSettings.Secure.UPLOAD_LOG, DeviceHelper.IS_DEVELOPMENT_VERSION ? 1 : 0) != 0;
    }

    public static void sendAnrReport(Context context, ActivityManager.ProcessErrorStateInfo processErrorStateInfo, int i) {
        if (processErrorStateInfo == null) {
            throw new IllegalArgumentException("state can't be null");
        } else if (isUserAllowed(context)) {
            try {
                sendReportRequestAsync(context, new AnrDataBuilder(context, context.getPackageName(), processErrorStateInfo), i);
            } catch (Exception e2) {
                Log.w(TAG, "Fail to sendAnrReport", e2);
            }
        }
    }

    private static void sendDropboxRequest(Context context, JSONObject jSONObject) {
        if ("1".equals(SystemProperties.get("sys.boot_completed"))) {
            DropBoxManager.getInstance().addText(DROPBOX_TAG, jSONObject.toString());
        } else {
            Log.w(TAG, "Can not request dropbox before boot completed!");
        }
    }

    public static void sendExceptionReport(Context context, String str, ApplicationErrorReport.CrashInfo crashInfo, int i) {
        if (crashInfo == null) {
            throw new IllegalArgumentException("crashInfo can't be null");
        } else if (isUserAllowed(context)) {
            try {
                sendReportRequestAsync(context, new ExceptionDataBuilder(context, str, crashInfo), i);
            } catch (Exception e2) {
                Log.w(TAG, "Fail to sendExceptionReport", e2);
            }
        }
    }

    public static void sendExceptionReport(Context context, Throwable th) {
        if (th != null) {
            sendExceptionReport(context, context.getPackageName(), new ApplicationErrorReport.CrashInfo(th), 1);
            return;
        }
        throw new IllegalArgumentException("throwable can't be null");
    }

    public static boolean sendReportRequest(Context context, JSONObject jSONObject, int i) {
        if (jSONObject == null) {
            throw new IllegalArgumentException("data can't be null");
        } else if ((i & 2) != 0) {
            return false;
        } else {
            sendDropboxRequest(context, jSONObject);
            return false;
        }
    }

    private static void sendReportRequestAsync(final Context context, final DataBuilder dataBuilder, final int i) {
        new AsyncTask<Void, Void, Void>() {
            /* class miui.util.ErrorReport.AnonymousClass1 */

            /* access modifiers changed from: protected */
            public Void doInBackground(Void... voidArr) {
                ErrorReport.sendReportRequest(context, dataBuilder.getData(), i);
                return null;
            }
        }.execute(new Void[0]);
    }
}
