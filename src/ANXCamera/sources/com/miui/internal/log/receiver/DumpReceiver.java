package com.miui.internal.log.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.miui.internal.log.util.Config;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import miui.os.FileUtils;

public class DumpReceiver extends BroadcastReceiver {
    public static final String INTENT_ACTION_DUMP_CACHED_LOG = "com.miui.core.intent.ACTION_DUMP_CACHED_LOG";
    private static final String TAG = "DumpReceiver";
    private final String mCacheLogDirPath;
    private final String mSdcardLogDirPath;

    public DumpReceiver() {
        this(Config.getDefaultCacheLogDir(), Config.getDefaultSdcardLogDir());
    }

    public DumpReceiver(String str, String str2) {
        this.mCacheLogDirPath = str;
        this.mSdcardLogDirPath = str2;
    }

    private static void dumpCachedLog(String str, String str2) {
        File file = new File(str);
        if (file.exists()) {
            String[] list = file.list();
            if (list == null || list.length <= 0) {
                Log.i(TAG, "Skip dumpCachedLog, logNames=" + Arrays.toString(list));
                return;
            }
            File file2 = new File(str2);
            if (file2.exists() || file2.mkdirs() || file2.exists()) {
                file2.listFiles(new FileFilter() {
                    /* class com.miui.internal.log.receiver.DumpReceiver.AnonymousClass1 */

                    public boolean accept(File file) {
                        file.delete();
                        return false;
                    }
                });
                for (String str3 : list) {
                    File file3 = new File(str + File.separatorChar + str3);
                    File file4 = new File(str2 + File.separatorChar + str3);
                    FileUtils.copyFile(file3, file4);
                    Log.d(TAG, "Dump log from " + file3.getPath() + " to " + file4.getPath());
                }
                return;
            }
            Log.e(TAG, "Fail to make dump dir: " + file2.getPath());
            return;
        }
        Log.i(TAG, "Skip dumpCachedLog, cacheLogDir is not exist: " + str);
    }

    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "dumpCachedLog start");
        dumpCachedLog(this.mCacheLogDirPath, this.mSdcardLogDirPath);
        Log.d(TAG, "dumpCachedLog end");
    }
}
