package miui.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CipherUtils {
    public static final String ALOGIRHTM_RSA = "RSA/ECB/PKCS1Padding";
    public static final String KEY_ALOGIRHTM_RSA = "RSA";

    /* access modifiers changed from: private */
    public static class CipherStream extends InputStream {
        final Cipher mCipher;
        byte[] mInputBuffer;
        final InputStream mInputDataStream;
        boolean mIsFinal;
        byte[] mOutputBuffer;
        int mOutputBufferOffset;

        CipherStream(Cipher cipher, InputStream inputStream) throws IOException {
            this.mCipher = cipher;
            this.mInputDataStream = inputStream;
            this.mInputBuffer = new byte[cipher.getBlockSize()];
            update();
        }

        private void update() throws IOException {
            int read = this.mInputDataStream.read(this.mInputBuffer);
            if (read > 0) {
                this.mOutputBuffer = this.mCipher.update(this.mInputBuffer, 0, read);
                this.mOutputBufferOffset = 0;
                return;
            }
            this.mIsFinal = true;
            try {
                this.mOutputBuffer = this.mCipher.doFinal();
                this.mOutputBufferOffset = 0;
            } catch (IllegalBlockSizeException e2) {
                throw new RuntimeException("IllegalBlockSizeException", e2);
            } catch (BadPaddingException e3) {
                throw new RuntimeException("BadPaddingException", e3);
            }
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            int i;
            byte[] bArr = this.mOutputBuffer;
            if (bArr == null || bArr.length <= (i = this.mOutputBufferOffset)) {
                return 0;
            }
            return (bArr.length - i) + this.mInputDataStream.available();
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            byte[] bArr = this.mOutputBuffer;
            if (bArr == null) {
                return -1;
            }
            int i = this.mOutputBufferOffset;
            if (i < bArr.length) {
                this.mOutputBufferOffset = i + 1;
                return bArr[i];
            }
            this.mOutputBuffer = null;
            if (this.mIsFinal) {
                return -1;
            }
            update();
            return read();
        }
    }

    protected CipherUtils() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static InputStream decrypt(InputStream inputStream, Key key, AlgorithmParameterSpec algorithmParameterSpec, String str) throws IOException {
        try {
            Cipher instance = Cipher.getInstance(str);
            instance.init(2, key, algorithmParameterSpec);
            return new CipherStream(instance, inputStream);
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("NoSuchAlgorithmException catched for algorithm " + str, e2);
        } catch (NoSuchPaddingException e3) {
            throw new RuntimeException("NoSuchAlgorithmException catched for algorithm " + str, e3);
        } catch (InvalidKeyException e4) {
            throw new RuntimeException("InvalidKeyException catched for key " + key, e4);
        } catch (InvalidAlgorithmParameterException e5) {
            throw new RuntimeException("InvalidAlgorithmParameterException catched for algorithm parameter " + algorithmParameterSpec, e5);
        }
    }

    public static byte[] decrypt(byte[] bArr, Key key, AlgorithmParameterSpec algorithmParameterSpec, String str) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        try {
            InputStream decrypt = decrypt(new ByteArrayInputStream(bArr), key, algorithmParameterSpec, str);
            byte[] bArr2 = new byte[(bArr.length * 2)];
            byte[] bArr3 = new byte[1024];
            int i = 0;
            while (true) {
                int read = decrypt.read(bArr3);
                if (read == 0) {
                    return Arrays.copyOfRange(bArr2, 0, i);
                }
                int i2 = read + i;
                if (i2 > bArr2.length) {
                    byte[] bArr4 = new byte[((bArr2.length + read) * 2)];
                    System.arraycopy(bArr2, 0, bArr4, 0, i);
                    bArr2 = bArr4;
                }
                System.arraycopy(bArr3, 0, bArr2, i, read);
                i = i2;
            }
        } catch (IOException e2) {
            throw new RuntimeException("IOException catched when using ByteArrayInputStream", e2);
        }
    }

    public static InputStream encrypt(InputStream inputStream, Key key, AlgorithmParameterSpec algorithmParameterSpec, String str) throws IOException {
        try {
            Cipher instance = Cipher.getInstance(str);
            instance.init(1, key, algorithmParameterSpec);
            return new CipherStream(instance, inputStream);
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("NoSuchAlgorithmException catched for algorithm " + str, e2);
        } catch (NoSuchPaddingException e3) {
            throw new RuntimeException("NoSuchAlgorithmException catched for algorithm " + str, e3);
        } catch (InvalidKeyException e4) {
            throw new RuntimeException("InvalidKeyException catched for key " + key, e4);
        } catch (InvalidAlgorithmParameterException e5) {
            throw new RuntimeException("InvalidAlgorithmParameterException catched for algorithm parameter " + algorithmParameterSpec, e5);
        }
    }

    public static byte[] encrypt(byte[] bArr, Key key, AlgorithmParameterSpec algorithmParameterSpec, String str) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        try {
            InputStream encrypt = encrypt(new ByteArrayInputStream(bArr), key, algorithmParameterSpec, str);
            byte[] bArr2 = new byte[(bArr.length * 2)];
            byte[] bArr3 = new byte[1024];
            int i = 0;
            while (true) {
                int read = encrypt.read(bArr3);
                if (read == 0) {
                    return Arrays.copyOfRange(bArr2, 0, i);
                }
                int i2 = read + i;
                if (i2 > bArr2.length) {
                    byte[] bArr4 = new byte[((bArr2.length + read) * 2)];
                    System.arraycopy(bArr2, 0, bArr4, 0, i);
                    bArr2 = bArr4;
                }
                System.arraycopy(bArr3, 0, bArr2, i, read);
                i = i2;
            }
        } catch (IOException e2) {
            throw new RuntimeException("IOException catched when using ByteArrayInputStream", e2);
        }
    }

    public static PrivateKey getPrivateKey(KeySpec keySpec, String str) {
        try {
            return KeyFactory.getInstance(str).generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("NoSuchAlgorithmException catched for algorithm " + str, e2);
        } catch (InvalidKeySpecException e3) {
            throw new RuntimeException("InvalidKeySpecException catched for keySpec " + keySpec.getClass().getName() + ":" + keySpec, e3);
        }
    }

    public static PublicKey getPublicKey(KeySpec keySpec, String str) {
        try {
            return KeyFactory.getInstance(str).generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("NoSuchAlgorithmException catched for algorithm " + str, e2);
        } catch (InvalidKeySpecException e3) {
            throw new RuntimeException("InvalidKeySpecException catched for keySpec " + keySpec.getClass().getName() + ":" + keySpec, e3);
        }
    }

    public static PrivateKey getRsaPrivateKey(String str, String str2, int i) {
        return getPrivateKey(new RSAPrivateKeySpec(new BigInteger(str, i), new BigInteger(str2, i)), "RSA");
    }

    public static PublicKey getRsaPublicKey(String str, String str2, int i) {
        return getPublicKey(new RSAPublicKeySpec(new BigInteger(str, i), new BigInteger(str2, i)), "RSA");
    }
}
