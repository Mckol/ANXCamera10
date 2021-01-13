package com.miui.internal.log.util;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;
import java.io.File;
import miui.os.Environment;
import miui.os.ProcessUtils;
import miui.util.AppConstants;

public class Config {
    private static final String DUMP_SUB_DIR = "/dump";
    public static final String LOG_NAME;
    public static final String PACKAGE_NAME;
    private static final String RELATIVE_LOG_DIR = "/debug_log/";
    private static final String SYSTEM_CACHE_DIR = "/cache";
    private static final String TAG = "Config";

    static {
        String packageName = getPackageName();
        String processNameByPid = ProcessUtils.getProcessNameByPid(Process.myPid());
        if (packageName == null) {
            int indexOf = processNameByPid.indexOf(58);
            packageName = indexOf < 0 ? processNameByPid : processNameByPid.substring(0, indexOf);
        }
        PACKAGE_NAME = packageName;
        LOG_NAME = processNameByPid;
    }

    private static String getApplicationCacheLogDir(Context context) {
        File cacheDir = context.getCacheDir();
        if (cacheDir != null) {
            return cacheDir.getPath() + RELATIVE_LOG_DIR;
        }
        Log.e(TAG, "Fail to getCacheDir");
        return null;
    }

    public static String getDefaultCacheLogDir() {
        return getDefaultCacheLogDir(PACKAGE_NAME);
    }

    public static String getDefaultCacheLogDir(String str) {
        Application currentApplication = AppConstants.getCurrentApplication();
        return currentApplication == null ? getSystemCacheLogDir(str) : getApplicationCacheLogDir(currentApplication);
    }

    public static String getDefaultSdcardLogDir() {
        return getDefaultSdcardLogDir(PACKAGE_NAME);
    }

    public static String getDefaultSdcardLogDir(String str) {
        File externalStorageMiuiDirectory = Environment.getExternalStorageMiuiDirectory();
        if (externalStorageMiuiDirectory != null) {
            return externalStorageMiuiDirectory.getPath() + RELATIVE_LOG_DIR + str + DUMP_SUB_DIR;
        }
        Log.e(TAG, "Fail to getExternalStorageMiuiDirectory");
        return null;
    }

    private static String getPackageName() {
        Application currentApplication = AppConstants.getCurrentApplication();
        if (currentApplication == null) {
            return null;
        }
        return currentApplication.getPackageName();
    }

    private static String getSystemCacheLogDir(String str) {
        String str2 = "/cache/debug_log/" + str;
        File file = new File(str2);
        if (file.exists() || file.mkdirs() || file.exists()) {
            return str2;
        }
        Log.e(TAG, "Fail to getSystemCacheLogDir");
        return null;
    }
}
