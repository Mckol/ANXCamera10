package com.ss.android.ugc.effectmanager.common.utils;

import android.os.Environment;
import android.text.TextUtils;
import com.ss.android.ugc.effectmanager.common.exception.UnzipException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {
    public static boolean checkFileExists(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return new File(str).exists();
    }

    public static String combineFilePath(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        sb.append(str);
        if (TextUtils.isEmpty(str2)) {
            str2 = "";
        }
        sb.append(str2);
        return sb.toString();
    }

    public static File createFile(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(str);
        if (!file.exists()) {
            if (!z) {
                file.mkdirs();
            } else {
                try {
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    file.createNewFile();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return file;
    }

    public static boolean ensureDirExists(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.exists();
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x005d A[SYNTHETIC, Splitter:B:31:0x005d] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x006e A[SYNTHETIC, Splitter:B:40:0x006e] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0078 A[SYNTHETIC, Splitter:B:45:0x0078] */
    public static String getFileContent(String str) {
        Throwable th;
        FileReader fileReader;
        BufferedReader bufferedReader;
        Exception e2;
        File file = new File(str);
        String str2 = "";
        if (!checkFileExists(file.getPath())) {
            return str2;
        }
        try {
            fileReader = new FileReader(file);
            try {
                bufferedReader = new BufferedReader(fileReader);
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            str2 = str2 + readLine;
                        } else {
                            try {
                                break;
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                    } catch (Exception e4) {
                        e2 = e4;
                        try {
                            e2.printStackTrace();
                            if (fileReader != null) {
                            }
                            if (bufferedReader != null) {
                            }
                            return str2;
                        } catch (Throwable th2) {
                            th = th2;
                            if (fileReader != null) {
                            }
                            if (bufferedReader != null) {
                            }
                            throw th;
                        }
                    }
                }
                fileReader.close();
                try {
                    bufferedReader.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            } catch (Exception e6) {
                bufferedReader = null;
                e2 = e6;
                e2.printStackTrace();
                if (fileReader != null) {
                }
                if (bufferedReader != null) {
                }
                return str2;
            } catch (Throwable th3) {
                bufferedReader = null;
                th = th3;
                if (fileReader != null) {
                }
                if (bufferedReader != null) {
                }
                throw th;
            }
        } catch (Exception e7) {
            fileReader = null;
            e2 = e7;
            bufferedReader = null;
            e2.printStackTrace();
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e8) {
                    e8.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            return str2;
        } catch (Throwable th4) {
            fileReader = null;
            th = th4;
            bufferedReader = null;
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e9) {
                    e9.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e10) {
                    e10.printStackTrace();
                }
            }
            throw th;
        }
        return str2;
    }

    public static InputStream getFileStream(String str) {
        File file = new File(str);
        if (!checkFileExists(file.getPath())) {
            return null;
        }
        try {
            return new FileInputStream(file);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static boolean isSdcardAvailable() {
        String externalStorageState = Environment.getExternalStorageState();
        return "mounted".equals(externalStorageState) || "mounted_ro".equals(externalStorageState);
    }

    public static boolean isSdcardWritable() {
        try {
            return "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception unused) {
            return false;
        }
    }

    public static void removeDir(File file) {
        File[] listFiles;
        if (!(file == null || !file.exists() || !file.isDirectory() || (listFiles = file.listFiles()) == null || listFiles.length == 0)) {
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    removeDir(file2);
                } else {
                    file2.delete();
                }
            }
            file.delete();
        }
    }

    public static void removeDir(String str) {
        removeDir(new File(str));
    }

    public static boolean removeFile(String str) {
        if (!isSdcardWritable() || TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        return file.exists() && file.canWrite() && file.delete();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.util.zip.ZipInputStream] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.util.zip.ZipInputStream] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00b8 A[SYNTHETIC, Splitter:B:45:0x00b8] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00c0 A[Catch:{ IOException -> 0x00bc }] */
    /* JADX WARNING: Unknown variable types count: 2 */
    public static void unZip(String str, String str2) throws UnzipException {
        Throwable th;
        ?? r1;
        BufferedOutputStream bufferedOutputStream;
        BufferedOutputStream bufferedOutputStream2;
        IOException e2;
        BufferedOutputStream bufferedOutputStream3 = null;
        try {
            ?? zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(str)));
            while (true) {
                try {
                    ZipEntry nextEntry = zipInputStream.getNextEntry();
                    if (nextEntry != null) {
                        String name = nextEntry.getName();
                        if (nextEntry.isDirectory()) {
                            new File(str2 + File.separator + name).mkdirs();
                        } else {
                            File file = new File(str2 + File.separator + name);
                            if (file.exists()) {
                                file.delete();
                            } else {
                                file.getParentFile().mkdirs();
                            }
                            file.createNewFile();
                            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                            try {
                                byte[] bArr = new byte[2048];
                                while (true) {
                                    int read = zipInputStream.read(bArr);
                                    if (read == -1) {
                                        break;
                                    }
                                    bufferedOutputStream.write(bArr, 0, read);
                                }
                                bufferedOutputStream.flush();
                                bufferedOutputStream.close();
                                bufferedOutputStream3 = bufferedOutputStream;
                            } catch (IOException e3) {
                                e2 = e3;
                                bufferedOutputStream3 = zipInputStream;
                                try {
                                    throw new UnzipException(e2.getMessage());
                                } catch (Throwable th2) {
                                    th = th2;
                                    bufferedOutputStream2 = bufferedOutputStream3;
                                    bufferedOutputStream3 = bufferedOutputStream;
                                    r1 = bufferedOutputStream2;
                                    if (r1 != 0) {
                                        try {
                                            r1.close();
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                    if (bufferedOutputStream3 != null) {
                                        bufferedOutputStream3.close();
                                    }
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                bufferedOutputStream2 = zipInputStream;
                                bufferedOutputStream3 = bufferedOutputStream;
                                r1 = bufferedOutputStream2;
                                if (r1 != 0) {
                                }
                                if (bufferedOutputStream3 != null) {
                                }
                                throw th;
                            }
                        }
                    } else {
                        try {
                            break;
                        } catch (IOException e5) {
                            e5.printStackTrace();
                            return;
                        }
                    }
                } catch (IOException e6) {
                    e2 = e6;
                    bufferedOutputStream = bufferedOutputStream3;
                    bufferedOutputStream3 = zipInputStream;
                    throw new UnzipException(e2.getMessage());
                } catch (Throwable th4) {
                    th = th4;
                    r1 = zipInputStream;
                    if (r1 != 0) {
                    }
                    if (bufferedOutputStream3 != null) {
                    }
                    throw th;
                }
            }
            zipInputStream.close();
            if (bufferedOutputStream3 != null) {
                bufferedOutputStream3.close();
            }
        } catch (IOException e7) {
            e2 = e7;
            bufferedOutputStream = null;
            throw new UnzipException(e2.getMessage());
        } catch (Throwable th5) {
            th = th5;
            r1 = 0;
            if (r1 != 0) {
            }
            if (bufferedOutputStream3 != null) {
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x003a A[SYNTHETIC, Splitter:B:24:0x003a] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0044 A[SYNTHETIC, Splitter:B:30:0x0044] */
    public static void writeToExternal(String str, String str2) {
        Throwable th;
        Exception e2;
        synchronized (FileUtils.class) {
            File file = new File(str2);
            if (!file.exists()) {
                createFile(file.getPath(), true);
            }
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    fileOutputStream2.write(str.getBytes());
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e3) {
                        e = e3;
                    }
                } catch (Exception e4) {
                    e2 = e4;
                    fileOutputStream = fileOutputStream2;
                    try {
                        e2.printStackTrace();
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e5) {
                                e = e5;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e6) {
                                e6.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                    }
                    throw th;
                }
            } catch (Exception e7) {
                e2 = e7;
                e2.printStackTrace();
                if (fileOutputStream != null) {
                }
            }
        }
        e.printStackTrace();
    }
}
