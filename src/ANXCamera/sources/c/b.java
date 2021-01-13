package c;

import com.mi.device.Common;
import java.util.Locale;

/* compiled from: Feature */
public class b {
    private b() {
    }

    public static Common aa(String str) {
        try {
            return (Common) Class.forName("com.mi.device." + capitalize(str)).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException unused) {
            return new Common();
        }
    }

    private static String capitalize(String str) {
        char[] charArray = str.toLowerCase(Locale.ENGLISH).toCharArray();
        if (charArray[0] >= 'a' && charArray[0] <= 'z') {
            charArray[0] = (char) (charArray[0] - ' ');
        }
        return new String(charArray);
    }
}
