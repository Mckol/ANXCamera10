package com.xiaomi.stat.d;

import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.util.Base64;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.GregorianCalendar;
import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;

public class b {

    /* renamed from: a  reason: collision with root package name */
    private static final String f581a = "AndroidKeyStoreUtils";

    /* renamed from: b  reason: collision with root package name */
    private static final String f582b = "AndroidKeyStore";

    /* renamed from: c  reason: collision with root package name */
    private static final String f583c = "RSA/ECB/PKCS1Padding";

    /* renamed from: d  reason: collision with root package name */
    private static final String f584d = "RSA_KEY";

    public static synchronized String a(Context context, String str) throws Exception {
        synchronized (b.class) {
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            KeyStore instance2 = KeyStore.getInstance(f582b);
            instance2.load(null);
            a(context, instance2);
            Certificate certificate = instance2.getCertificate(f584d);
            if (certificate == null) {
                return null;
            }
            instance.init(1, certificate.getPublicKey());
            return Base64.encodeToString(instance.doFinal(str.getBytes("UTF-8")), 0);
        }
    }

    private static void a() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, InvalidAlgorithmParameterException, NoSuchProviderException, NoSuchAlgorithmException {
        Class<?> cls = Class.forName("android.security.keystore.KeyGenParameterSpec$Builder");
        if (cls != null) {
            Constructor<?> constructor = cls.getConstructor(String.class, Integer.TYPE);
            Class<?> cls2 = Class.forName("android.security.keystore.KeyProperties");
            Object newInstance = constructor.newInstance(f584d, Integer.valueOf(cls2.getDeclaredField("PURPOSE_ENCRYPT").getInt(null) | cls2.getDeclaredField("PURPOSE_DECRYPT").getInt(null)));
            cls.getMethod("setDigests", String[].class).invoke(newInstance, new String[]{(String) cls2.getDeclaredField("DIGEST_SHA256").get(null), (String) cls2.getDeclaredField("DIGEST_SHA512").get(null)});
            cls.getMethod("setEncryptionPaddings", String[].class).invoke(newInstance, new String[]{(String) cls2.getDeclaredField("ENCRYPTION_PADDING_RSA_PKCS1").get(null)});
            Object invoke = cls.getMethod("build", new Class[0]).invoke(newInstance, new Object[0]);
            Class<?> cls3 = Class.forName("java.security.KeyPairGenerator");
            if (cls3 != null) {
                KeyPairGenerator keyPairGenerator = (KeyPairGenerator) cls3.getMethod("getInstance", String.class, String.class).invoke(null, "RSA", f582b);
                cls3.getMethod("initialize", AlgorithmParameterSpec.class).invoke(keyPairGenerator, invoke);
                keyPairGenerator.generateKeyPair();
            }
        }
    }

    private static void a(Context context) throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
        gregorianCalendar2.add(1, 1);
        KeyPairGeneratorSpec build = new KeyPairGeneratorSpec.Builder(context).setAlias(f584d).setSubject(new X500Principal("CN=RSA_KEY")).setSerialNumber(BigInteger.valueOf(1337)).setStartDate(gregorianCalendar.getTime()).setEndDate(gregorianCalendar2.getTime()).build();
        KeyPairGenerator instance = KeyPairGenerator.getInstance("RSA", f582b);
        instance.initialize(build);
        instance.generateKeyPair();
    }

    private static void a(Context context, KeyStore keyStore) {
        try {
            if (!keyStore.containsAlias(f584d) && Build.VERSION.SDK_INT >= 18) {
                if (Build.VERSION.SDK_INT < 23) {
                    a(context);
                } else {
                    a();
                }
            }
        } catch (Exception e2) {
            k.d(f581a, "createKey e", e2);
        }
    }

    public static synchronized String b(Context context, String str) throws Exception {
        String str2;
        synchronized (b.class) {
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            KeyStore instance2 = KeyStore.getInstance(f582b);
            instance2.load(null);
            a(context, instance2);
            instance.init(2, (PrivateKey) instance2.getKey(f584d, null));
            str2 = new String(instance.doFinal(Base64.decode(str, 0)), "UTF-8");
        }
        return str2;
    }
}
