package miui.os;

import com.android.camera.storage.Storage;
import com.miui.internal.os.Native;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public class FileUtils {
    private static final Pattern SAFE_FILENAME_PATTERN = Pattern.compile("[\\w%+,./=_-]+");
    public static final int S_IRGRP = 32;
    public static final int S_IROTH = 4;
    public static final int S_IRUSR = 256;
    public static final int S_IRWXG = 56;
    public static final int S_IRWXO = 7;
    public static final int S_IRWXU = 448;
    public static final int S_IWGRP = 16;
    public static final int S_IWOTH = 2;
    public static final int S_IWUSR = 128;
    public static final int S_IXGRP = 8;
    public static final int S_IXOTH = 1;
    public static final int S_IXUSR = 64;

    protected FileUtils() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static boolean addNoMedia(String str) {
        File file = new File(str);
        if (!file.isDirectory()) {
            return false;
        }
        try {
            return new File(file, Storage.AVOID_SCAN_FILE_NAME).createNewFile();
        } catch (IOException unused) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0029 A[SYNTHETIC, Splitter:B:18:0x0029] */
    public static long checksumCrc32(File file) throws IOException {
        Throwable th;
        CRC32 crc32 = new CRC32();
        CheckedInputStream checkedInputStream = null;
        try {
            CheckedInputStream checkedInputStream2 = new CheckedInputStream(new FileInputStream(file), crc32);
            try {
                while (checkedInputStream2.read(new byte[128]) >= 0) {
                }
                long value = crc32.getValue();
                try {
                    checkedInputStream2.close();
                } catch (IOException unused) {
                }
                return value;
            } catch (Throwable th2) {
                th = th2;
                checkedInputStream = checkedInputStream2;
                if (checkedInputStream != null) {
                    try {
                        checkedInputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (checkedInputStream != null) {
            }
            throw th;
        }
    }

    public static boolean chmod(String str, int i) {
        if (str == null || str.length() == 0) {
            return false;
        }
        if (i < 0) {
            return true;
        }
        return Native.chmod(str, i, 1);
    }

    public static boolean chown(String str, int i, int i2) {
        if (str == null || str.length() == 0) {
            return false;
        }
        if (i < 0 && i2 < 0) {
            return true;
        }
        if (i < 0) {
            i = -1;
        } else if (i2 < 0) {
            i2 = -1;
        }
        return Native.chown(str, i, i2, 1);
    }

    public static boolean copyFile(File file, File file2) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                return copyToFile(fileInputStream, file2);
            } finally {
                fileInputStream.close();
            }
        } catch (IOException unused) {
            return false;
        }
    }

    /* JADX INFO: finally extract failed */
    public static boolean copyToFile(InputStream inputStream, File file) {
        try {
            if (file.exists() && !file.delete()) {
                return false;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                byte[] bArr = new byte[4096];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read < 0) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.flush();
                try {
                    fileOutputStream.getFD().sync();
                } catch (IOException unused) {
                }
                fileOutputStream.close();
                return true;
            } catch (Throwable th) {
                fileOutputStream.flush();
                try {
                    fileOutputStream.getFD().sync();
                } catch (IOException unused2) {
                }
                fileOutputStream.close();
                throw th;
            }
        } catch (IOException unused3) {
            return false;
        }
    }

    public static String getExtension(String str) {
        int lastIndexOf;
        return (str == null || str.length() == 0 || (lastIndexOf = str.lastIndexOf(46)) <= -1) ? "" : str.substring(lastIndexOf + 1);
    }

    public static String getFileName(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(File.separatorChar);
        return lastIndexOf > -1 ? str.substring(lastIndexOf + 1) : str;
    }

    public static long getFolderSize(File file) {
        long j = 0;
        if (!file.exists()) {
            return 0;
        }
        if (!file.isDirectory()) {
            return file.length();
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File file2 : listFiles) {
                j += getFolderSize(file2);
            }
        }
        return j;
    }

    public static String getName(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(File.separatorChar);
        if (lastIndexOf < 0) {
            lastIndexOf = -1;
        }
        int lastIndexOf2 = str.lastIndexOf(".");
        return lastIndexOf2 < 0 ? str.substring(lastIndexOf + 1) : str.substring(lastIndexOf + 1, lastIndexOf2);
    }

    public static boolean isFilenameSafe(File file) {
        return SAFE_FILENAME_PATTERN.matcher(file.getPath()).matches();
    }

    public static boolean mkdirs(File file, int i, int i2, int i3) {
        return file.exists() ? file.isDirectory() : mkdirs(file.getParentFile(), i, i2, i3) && file.mkdir() && chmod(file.getPath(), i) && chown(file.getPath(), i2, i3);
    }

    public static String normalizeDirectoryName(String str) {
        if (str.charAt(str.length() - 1) == File.separatorChar) {
            return str;
        }
        return str + File.separator;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0030 A[SYNTHETIC, Splitter:B:17:0x0030] */
    private static byte[] readFileAsBytes(String str) throws IOException {
        Throwable th;
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(str, "r");
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int) randomAccessFile.length());
                byte[] bArr = new byte[8192];
                while (true) {
                    int read = randomAccessFile.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    randomAccessFile.close();
                } catch (IOException unused) {
                }
                return byteArray;
            } catch (Throwable th2) {
                th = th2;
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            randomAccessFile = null;
            if (randomAccessFile != null) {
            }
            throw th;
        }
    }

    public static String readFileAsString(String str) throws IOException {
        return new String(readFileAsBytes(str), Charset.forName("UTF-8"));
    }

    public static String readTextFile(File file, int i, String str) throws IOException {
        int read;
        int read2;
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            long length = file.length();
            if (i > 0 || (length > 0 && i == 0)) {
                if (length > 0 && (i == 0 || length < ((long) i))) {
                    i = (int) length;
                }
                byte[] bArr = new byte[(i + 1)];
                int read3 = fileInputStream.read(bArr);
                if (read3 <= 0) {
                    fileInputStream.close();
                    return "";
                } else if (read3 <= i) {
                    String str2 = new String(bArr, 0, read3);
                    fileInputStream.close();
                    return str2;
                } else if (str == null) {
                    String str3 = new String(bArr, 0, i);
                    fileInputStream.close();
                    return str3;
                } else {
                    String str4 = new String(bArr, 0, i) + str;
                    fileInputStream.close();
                    return str4;
                }
            } else if (i < 0) {
                byte[] bArr2 = null;
                byte[] bArr3 = null;
                boolean z = false;
                while (true) {
                    if (bArr2 != null) {
                        z = true;
                    }
                    if (bArr2 == null) {
                        bArr2 = new byte[(-i)];
                    }
                    read2 = fileInputStream.read(bArr2);
                    if (read2 != bArr2.length) {
                        break;
                    }
                    bArr3 = bArr2;
                    bArr2 = bArr3;
                }
                if (bArr3 == null && read2 <= 0) {
                    return "";
                }
                if (bArr3 == null) {
                    String str5 = new String(bArr2, 0, read2);
                    fileInputStream.close();
                    return str5;
                }
                if (read2 > 0) {
                    System.arraycopy(bArr3, read2, bArr3, 0, bArr3.length - read2);
                    System.arraycopy(bArr2, 0, bArr3, bArr3.length - read2, read2);
                    z = true;
                }
                if (str == null || !z) {
                    String str6 = new String(bArr3);
                    fileInputStream.close();
                    return str6;
                }
                String str7 = str + new String(bArr3);
                fileInputStream.close();
                return str7;
            } else {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr4 = new byte[1024];
                do {
                    read = fileInputStream.read(bArr4);
                    if (read > 0) {
                        byteArrayOutputStream.write(bArr4, 0, read);
                    }
                } while (read == bArr4.length);
                String byteArrayOutputStream2 = byteArrayOutputStream.toString();
                fileInputStream.close();
                return byteArrayOutputStream2;
            }
        } finally {
            fileInputStream.close();
        }
    }

    public static boolean rm(String str) {
        return Native.rm(str, 1);
    }

    public static void stringToFile(String str, String str2) throws IOException {
        FileWriter fileWriter = new FileWriter(str);
        try {
            fileWriter.write(str2);
        } finally {
            fileWriter.close();
        }
    }

    public static boolean sync(FileOutputStream fileOutputStream) {
        if (fileOutputStream == null) {
            return true;
        }
        try {
            fileOutputStream.getFD().sync();
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    public static int umask(int i) {
        return Native.umask(i);
    }
}
