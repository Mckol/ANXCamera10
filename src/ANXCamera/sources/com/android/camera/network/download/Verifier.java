package com.android.camera.network.download;

import com.android.camera.log.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.CRC32;

public abstract class Verifier {
    private static final String TAG = "Verifier";
    private String mAlgorithm;
    private byte[] mHash;

    public static final class Md5 extends Verifier {
        private static final String ALGORITHM = "MD5";

        public Md5(String str) {
            super("MD5", Verifier.decode(str, 32));
        }

        public Md5(byte[] bArr) {
            super("MD5", bArr);
        }
    }

    public static final class Sha1 extends Verifier {
        private static final String ALGORITHM = "SHA-1";

        public Sha1(String str) {
            super("SHA-1", Verifier.decode(str, 40));
        }

        Sha1(byte[] bArr) {
            super("SHA-1", bArr);
        }
    }

    public Verifier(String str, byte[] bArr) {
        this.mAlgorithm = str;
        this.mHash = bArr;
    }

    public static long crc32(File file, int i) {
        CRC32 crc32 = new CRC32();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                byte[] bArr = new byte[i];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    crc32.update(bArr, 0, read);
                }
                long value = crc32.getValue();
                try {
                    fileInputStream.close();
                } catch (IOException e2) {
                    Log.w(TAG, e2);
                }
                return value;
            } catch (IOException e3) {
                Log.w(TAG, e3);
                try {
                    fileInputStream.close();
                } catch (IOException e4) {
                    Log.w(TAG, e4);
                }
                return 0;
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (IOException e5) {
                    Log.w(TAG, e5);
                }
                throw th;
            }
        } catch (FileNotFoundException e6) {
            Log.w(TAG, e6);
            return 0;
        }
    }

    /* access modifiers changed from: private */
    public static byte[] decode(String str, int i) {
        if (str.length() == i) {
            byte[] bArr = new byte[(str.length() / 2)];
            for (int i2 = 0; i2 < str.length(); i2++) {
                int digit = Character.digit(str.charAt(i2), 16);
                if (digit != -1) {
                    int i3 = i2 / 2;
                    bArr[i3] = (byte) ((digit << (i2 % 2 == 0 ? 4 : 0)) | bArr[i3]);
                } else {
                    throw new IllegalArgumentException(str + " is not a hex string");
                }
            }
            return bArr;
        }
        throw new IllegalArgumentException();
    }

    public static byte[] hash(File file, String str, int i) {
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    byte[] bArr = new byte[i];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        instance.update(bArr, 0, read);
                    }
                    byte[] digest = instance.digest();
                    try {
                        fileInputStream.close();
                    } catch (IOException e2) {
                        Log.w(TAG, e2);
                    }
                    return digest;
                } catch (IOException e3) {
                    Log.w(TAG, e3);
                    try {
                        fileInputStream.close();
                    } catch (IOException e4) {
                        Log.w(TAG, e4);
                    }
                    return null;
                } catch (Throwable th) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e5) {
                        Log.w(TAG, e5);
                    }
                    throw th;
                }
            } catch (FileNotFoundException e6) {
                Log.w(TAG, e6);
                return null;
            }
        } catch (NoSuchAlgorithmException e7) {
            Log.w(TAG, e7);
            return null;
        }
    }

    public MessageDigest getInstance() {
        try {
            return MessageDigest.getInstance(this.mAlgorithm);
        } catch (NoSuchAlgorithmException e2) {
            Log.w(TAG, e2);
            return null;
        }
    }

    public final boolean verify(byte[] bArr) {
        return Arrays.equals(bArr, this.mHash);
    }
}
