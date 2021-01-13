package com.ss.android.medialib;

import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils {
    private static final String DEFAULT_FOLDER_NAME = "BDMedia";
    protected static String msFolderPath;

    public static boolean checkFileExists(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return new File(str).exists();
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

    /* JADX WARNING: Removed duplicated region for block: B:105:0x00df A[SYNTHETIC, Splitter:B:105:0x00df] */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x00e9 A[SYNTHETIC, Splitter:B:110:0x00e9] */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x00f3 A[SYNTHETIC, Splitter:B:115:0x00f3] */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x00fd A[SYNTHETIC, Splitter:B:120:0x00fd] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0082 A[SYNTHETIC, Splitter:B:57:0x0082] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x008c A[SYNTHETIC, Splitter:B:62:0x008c] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0096 A[SYNTHETIC, Splitter:B:67:0x0096] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00a0 A[SYNTHETIC, Splitter:B:72:0x00a0] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x00b2 A[SYNTHETIC, Splitter:B:82:0x00b2] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x00bc A[SYNTHETIC, Splitter:B:87:0x00bc] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x00c6 A[SYNTHETIC, Splitter:B:92:0x00c6] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x00d0 A[SYNTHETIC, Splitter:B:97:0x00d0] */
    public static boolean fileChannelCopy(String str, String str2) {
        FileChannel fileChannel;
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        Throwable th;
        FileNotFoundException e2;
        FileChannel fileChannel2;
        IOException e3;
        FileChannel fileChannel3;
        if (!isSdcardWritable()) {
            return false;
        }
        FileInputStream fileInputStream2 = null;
        r0 = null;
        r0 = null;
        r0 = null;
        r0 = null;
        r0 = null;
        FileChannel fileChannel4 = null;
        FileInputStream fileInputStream3 = null;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                fileOutputStream = new FileOutputStream(str2);
                try {
                    fileChannel = fileInputStream.getChannel();
                    try {
                        fileChannel4 = fileOutputStream.getChannel();
                        fileChannel.transferTo(0, fileChannel.size(), fileChannel4);
                        try {
                            fileInputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                        if (fileChannel != null) {
                            try {
                                fileChannel.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        try {
                            fileOutputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                        if (fileChannel4 == null) {
                            return true;
                        }
                        try {
                            fileChannel4.close();
                            return true;
                        } catch (IOException e7) {
                            e7.printStackTrace();
                            return true;
                        }
                    } catch (FileNotFoundException e8) {
                        e2 = e8;
                        fileChannel2 = fileChannel4;
                        fileInputStream2 = fileInputStream;
                        e2.printStackTrace();
                        if (fileInputStream2 != null) {
                        }
                        if (fileChannel != null) {
                        }
                        if (fileOutputStream != null) {
                        }
                        if (fileChannel2 != null) {
                        }
                        return false;
                    } catch (IOException e9) {
                        e3 = e9;
                        fileChannel3 = fileChannel4;
                        fileInputStream3 = fileInputStream;
                        try {
                            e3.printStackTrace();
                            if (fileInputStream3 != null) {
                            }
                            if (fileChannel != null) {
                            }
                            if (fileOutputStream != null) {
                            }
                            if (fileChannel3 != null) {
                            }
                            return false;
                        } catch (Throwable th2) {
                            th = th2;
                            fileInputStream = fileInputStream3;
                            fileChannel4 = fileChannel3;
                            if (fileInputStream != null) {
                            }
                            if (fileChannel != null) {
                            }
                            if (fileOutputStream != null) {
                            }
                            if (fileChannel4 != null) {
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        if (fileInputStream != null) {
                        }
                        if (fileChannel != null) {
                        }
                        if (fileOutputStream != null) {
                        }
                        if (fileChannel4 != null) {
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e10) {
                    e2 = e10;
                    fileChannel = null;
                    fileInputStream2 = fileInputStream;
                    fileChannel2 = fileChannel;
                    e2.printStackTrace();
                    if (fileInputStream2 != null) {
                    }
                    if (fileChannel != null) {
                    }
                    if (fileOutputStream != null) {
                    }
                    if (fileChannel2 != null) {
                    }
                    return false;
                } catch (IOException e11) {
                    e3 = e11;
                    fileChannel = null;
                    fileInputStream3 = fileInputStream;
                    fileChannel3 = fileChannel;
                    e3.printStackTrace();
                    if (fileInputStream3 != null) {
                    }
                    if (fileChannel != null) {
                    }
                    if (fileOutputStream != null) {
                    }
                    if (fileChannel3 != null) {
                    }
                    return false;
                } catch (Throwable th4) {
                    th = th4;
                    fileChannel = null;
                    if (fileInputStream != null) {
                    }
                    if (fileChannel != null) {
                    }
                    if (fileOutputStream != null) {
                    }
                    if (fileChannel4 != null) {
                    }
                    throw th;
                }
            } catch (FileNotFoundException e12) {
                e2 = e12;
                fileOutputStream = null;
                fileChannel = null;
                fileInputStream2 = fileInputStream;
                fileChannel2 = fileChannel;
                e2.printStackTrace();
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (IOException e13) {
                        e13.printStackTrace();
                    }
                }
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException e14) {
                        e14.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e15) {
                        e15.printStackTrace();
                    }
                }
                if (fileChannel2 != null) {
                    try {
                        fileChannel2.close();
                    } catch (IOException e16) {
                        e16.printStackTrace();
                    }
                }
                return false;
            } catch (IOException e17) {
                e3 = e17;
                fileOutputStream = null;
                fileChannel = null;
                fileInputStream3 = fileInputStream;
                fileChannel3 = fileChannel;
                e3.printStackTrace();
                if (fileInputStream3 != null) {
                    try {
                        fileInputStream3.close();
                    } catch (IOException e18) {
                        e18.printStackTrace();
                    }
                }
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException e19) {
                        e19.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e20) {
                        e20.printStackTrace();
                    }
                }
                if (fileChannel3 != null) {
                    try {
                        fileChannel3.close();
                    } catch (IOException e21) {
                        e21.printStackTrace();
                    }
                }
                return false;
            } catch (Throwable th5) {
                th = th5;
                fileOutputStream = null;
                fileChannel = null;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e22) {
                        e22.printStackTrace();
                    }
                }
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException e23) {
                        e23.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e24) {
                        e24.printStackTrace();
                    }
                }
                if (fileChannel4 != null) {
                    try {
                        fileChannel4.close();
                    } catch (IOException e25) {
                        e25.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e26) {
            e2 = e26;
            fileOutputStream = null;
            fileChannel = null;
            fileChannel2 = fileChannel;
            e2.printStackTrace();
            if (fileInputStream2 != null) {
            }
            if (fileChannel != null) {
            }
            if (fileOutputStream != null) {
            }
            if (fileChannel2 != null) {
            }
            return false;
        } catch (IOException e27) {
            e3 = e27;
            fileOutputStream = null;
            fileChannel = null;
            fileChannel3 = fileChannel;
            e3.printStackTrace();
            if (fileInputStream3 != null) {
            }
            if (fileChannel != null) {
            }
            if (fileOutputStream != null) {
            }
            if (fileChannel3 != null) {
            }
            return false;
        } catch (Throwable th6) {
            th = th6;
            fileOutputStream = null;
            fileChannel = null;
            fileInputStream = null;
            if (fileInputStream != null) {
            }
            if (fileChannel != null) {
            }
            if (fileOutputStream != null) {
            }
            if (fileChannel4 != null) {
            }
            throw th;
        }
    }

    public static String getPath() {
        if (msFolderPath == null) {
            msFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + DEFAULT_FOLDER_NAME;
            File file = new File(msFolderPath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return msFolderPath;
    }

    public static boolean isSdcardWritable() {
        try {
            return "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception unused) {
            return false;
        }
    }
}
