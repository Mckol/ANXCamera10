package b.c.a;

import android.content.Context;
import dalvik.system.DexFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

/* compiled from: MiDeviceCompat */
public class h implements e {
    private static final h sInstance = new h();
    private e Ki = ja("com.mi.device.Device" + c.Bg.toUpperCase(Locale.ENGLISH));

    public h() {
        if (this.Ki == null) {
            this.Ki = new d();
        }
    }

    public static h getDevice() {
        return sInstance;
    }

    private static e ja(String str) {
        try {
            return (e) Class.forName(str).newInstance();
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (InstantiationException e4) {
            e4.printStackTrace();
        }
        return null;
    }

    private String ka(String str) {
        char[] charArray = str.toCharArray();
        if (charArray[0] < 'a' || charArray[0] > 'z') {
            return str;
        }
        charArray[0] = (char) (charArray[0] - ' ');
        return String.valueOf(charArray);
    }

    private static List<String> m(Context context, String str) {
        ArrayList arrayList = new ArrayList();
        try {
            Enumeration<String> entries = new DexFile(context.getPackageCodePath()).entries();
            while (entries.hasMoreElements()) {
                String nextElement = entries.nextElement();
                if (nextElement.contains(str)) {
                    arrayList.add(nextElement);
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }

    @Override // b.c.a.e
    public boolean D() {
        return this.Ki.D();
    }

    @Override // b.c.a.e
    public boolean H() {
        return this.Ki.H();
    }

    @Override // b.c.a.e
    public boolean I() {
        return this.Ki.I();
    }

    @Override // b.c.a.e
    public int J() {
        return this.Ki.J();
    }
}
