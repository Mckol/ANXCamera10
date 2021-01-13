package com.miui.internal.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import miui.os.FileUtils;
import miui.util.DirectIndexedFile;

public class DirectIndexedFileExtractor {
    private static final String IDF_DIR_NAME = "idf";
    public static String IDF_FILES_PATH = null;
    private static final String IDF_FILE_EXT = ".idf";
    private static final String TAG = "DirectIndexedFileExtractor";
    private static final int TARGET_DIR_MODE = 505;
    private static final int TARGET_FILE_MODE = 436;
    private static final String TMP_FILE_NAME_POSTFIX = "-tmp";

    private static void ensureIdfPath(Context context) {
        if (IDF_FILES_PATH == null) {
            try {
                Context createPackageContext = context.createPackageContext("com.miui.core", 2);
                if (Build.VERSION.SDK_INT >= 24) {
                    try {
                        createPackageContext = (Context) createPackageContext.getClass().getMethod("createDeviceProtectedStorageContext", new Class[0]).invoke(createPackageContext, new Object[0]);
                    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
                    }
                }
                if (createPackageContext.getFilesDir() != null) {
                    IDF_FILES_PATH = createPackageContext.getFilesDir().getAbsolutePath() + File.separator + IDF_DIR_NAME;
                }
            } catch (PackageManager.NameNotFoundException e2) {
                e2.printStackTrace();
            }
        }
        String str = IDF_FILES_PATH;
        if (str != null) {
            File file = new File(str);
            if (!file.exists() && file.mkdirs()) {
                FileUtils.chmod(IDF_FILES_PATH, TARGET_DIR_MODE);
                return;
            }
            return;
        }
        Log.w(TAG, "Error: Cannot locate IDF_FILES_PATH");
    }

    /* access modifiers changed from: private */
    public static void extractDirectIndexedFile(Context context, String str) {
        String directIndexedFilePath = getDirectIndexedFilePath(context, str);
        if (directIndexedFilePath == null) {
            Log.w(TAG, "directIndexedFilePath is null");
        } else if (!TextUtils.isEmpty(str)) {
            AssetManager assets = context.getResources().getAssets();
            File file = new File(directIndexedFilePath);
            if (!file.exists() || shouldOverwrite(context, str, directIndexedFilePath)) {
                File file2 = new File(directIndexedFilePath + TMP_FILE_NAME_POSTFIX);
                InputStream inputStream = null;
                try {
                    InputStream open = assets.open(str);
                    boolean copyToFile = FileUtils.copyToFile(open, file2);
                    file.delete();
                    if (!copyToFile || !file2.renameTo(file)) {
                        Log.w(TAG, file2.getAbsolutePath() + " copy failed.");
                    } else {
                        FileUtils.chmod(directIndexedFilePath, TARGET_FILE_MODE);
                        Log.v(TAG, file.getAbsolutePath() + " extraction completed.");
                    }
                    if (open != null) {
                        try {
                            open.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e3) {
                    e3.printStackTrace();
                    if (0 != 0) {
                        inputStream.close();
                    }
                } catch (IOException e4) {
                    e4.printStackTrace();
                    if (0 != 0) {
                        inputStream.close();
                    }
                } catch (Throwable th) {
                    if (0 != 0) {
                        try {
                            inputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    throw th;
                }
            } else {
                Log.v(TAG, " don't need overwrite");
            }
        }
    }

    public static String getDirectIndexedFilePath(Context context, String str) {
        ensureIdfPath(context);
        if (IDF_FILES_PATH == null) {
            return null;
        }
        return IDF_FILES_PATH + File.separator + str;
    }

    public static boolean replaceDirectIndexedFile(Context context, String str, String str2) {
        String directIndexedFilePath = getDirectIndexedFilePath(context, str2);
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            Log.w(TAG, "replaceDirectIndexedFile srcPath is null");
            return false;
        } else if (TextUtils.isEmpty(directIndexedFilePath)) {
            Log.w(TAG, "replaceDirectIndexedFile destPath is null");
            return false;
        } else {
            File file = new File(directIndexedFilePath + TMP_FILE_NAME_POSTFIX);
            File file2 = new File(directIndexedFilePath);
            boolean copyFile = FileUtils.copyFile(new File(str), file);
            if (!file2.delete()) {
                Log.w(TAG, String.format("old idf file:%s delete fail", str2));
            }
            if (!copyFile || !file.renameTo(file2)) {
                Log.w(TAG, String.format("%s copy or rename failed , tmp file delete: %s", file.getAbsolutePath(), Boolean.valueOf(file.exists() ? file.delete() : true)));
            } else {
                Log.v(TAG, file2.getAbsolutePath() + " copy and rename success.");
                z = FileUtils.chmod(directIndexedFilePath, TARGET_FILE_MODE);
            }
            Log.v(TAG, "replaceDirectIndexedFile result is " + z);
            return z;
        }
    }

    private static boolean shouldOverwrite(Context context, String str, String str2) {
        boolean z = false;
        try {
            DirectIndexedFile.Reader open = DirectIndexedFile.open(context.getAssets().open(str, 1));
            try {
                DirectIndexedFile.Reader open2 = DirectIndexedFile.open(str2);
                if (open.getDataVersion() > open2.getDataVersion()) {
                    z = true;
                }
                open.close();
                open2.close();
                return z;
            } catch (IOException e2) {
                e2.printStackTrace();
                open.close();
                return true;
            }
        } catch (IOException e3) {
            e3.printStackTrace();
            return false;
        }
    }

    public static void tryExtractDirectIndexedFiles(final Context context) {
        ensureIdfPath(context);
        try {
            final String[] list = context.getAssets().list("");
            new AsyncTask<Void, Void, Void>() {
                /* class com.miui.internal.util.DirectIndexedFileExtractor.AnonymousClass1 */

                /* access modifiers changed from: protected */
                public Void doInBackground(Void... voidArr) {
                    String[] strArr = list;
                    for (String str : strArr) {
                        if (str.endsWith(DirectIndexedFileExtractor.IDF_FILE_EXT)) {
                            DirectIndexedFileExtractor.extractDirectIndexedFile(context, str);
                        }
                    }
                    return null;
                }
            }.execute(new Void[0]);
        } catch (IOException e2) {
            Log.w(TAG, "Error reading asset files, extraction abort");
            e2.printStackTrace();
        }
    }
}
