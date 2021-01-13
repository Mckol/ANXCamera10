package miui.text;

import android.content.Context;
import com.miui.internal.R;
import java.io.IOException;
import miui.util.Pools;

public class ExtraTextUtils {
    public static final long GB = 1000000000;
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final long KB = 1000;
    public static final long MB = 1000000;
    private static final long UNIT = 1000;

    protected ExtraTextUtils() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static String formatFileSize(Context context, long j) {
        return formatFileSize(context, j, false);
    }

    private static String formatFileSize(Context context, long j, boolean z) {
        String str;
        if (context == null) {
            return "";
        }
        float f = (float) j;
        int i = R.string.size_byte;
        if (f > 900.0f) {
            i = R.string.size_kilo_byte;
            f /= 1000.0f;
        }
        if (f > 900.0f) {
            i = R.string.size_mega_byte;
            f /= 1000.0f;
        }
        if (f > 900.0f) {
            i = R.string.size_giga_byte;
            f /= 1000.0f;
        }
        if (f > 900.0f) {
            i = R.string.size_tera_byte;
            f /= 1000.0f;
        }
        if (f > 900.0f) {
            i = R.string.size_peta_byte;
            f /= 1000.0f;
        }
        if (f < 1.0f) {
            str = String.format("%.2f", Float.valueOf(f));
        } else if (f < 10.0f) {
            if (z) {
                str = String.format("%.1f", Float.valueOf(f));
            } else {
                str = String.format("%.2f", Float.valueOf(f));
            }
        } else if (f >= 100.0f) {
            str = String.format("%.0f", Float.valueOf(f));
        } else if (z) {
            str = String.format("%.0f", Float.valueOf(f));
        } else {
            str = String.format("%.2f", Float.valueOf(f));
        }
        int length = str.length();
        if (length > 3) {
            int i2 = length - 3;
            if (str.charAt(i2) == '.' && str.charAt(length - 2) == '0' && str.charAt(length - 1) == '0') {
                str = str.substring(0, i2);
                return context.getResources().getString(R.string.size_suffix, str, context.getString(i));
            }
        }
        if (length > 2) {
            int i3 = length - 2;
            if (str.charAt(i3) == '.' && str.charAt(length - 1) == '0') {
                str = str.substring(0, i3);
            }
        }
        return context.getResources().getString(R.string.size_suffix, str, context.getString(i));
    }

    public static String formatShortFileSize(Context context, long j) {
        return formatFileSize(context, j, true);
    }

    public static byte[] fromHexReadable(String str) {
        int i;
        int i2;
        int i3;
        int i4;
        int length = str.length();
        if (length % 2 == 0) {
            byte[] bArr = new byte[(length >> 1)];
            for (int i5 = 0; i5 < length; i5 += 2) {
                char charAt = str.charAt(i5);
                if (charAt < '0' || charAt > '9') {
                    if (charAt >= 'a' && charAt <= 'f') {
                        i4 = charAt - 'a';
                    } else if (charAt < 'A' || charAt > 'F') {
                        throw new IllegalArgumentException("s is not a readable string: " + str);
                    } else {
                        i4 = charAt - 'A';
                    }
                    i = i4 + 10;
                } else {
                    i = charAt - '0';
                }
                int i6 = i << 4;
                char charAt2 = str.charAt(i5 + 1);
                if (charAt2 < '0' || charAt2 > '9') {
                    if (charAt2 >= 'a' && charAt2 <= 'f') {
                        i2 = charAt2 - 'a';
                    } else if (charAt2 < 'A' || charAt2 > 'F') {
                        throw new IllegalArgumentException("s is not a readable string: " + str);
                    } else {
                        i2 = charAt2 - 'A';
                    }
                    i3 = i2 + 10;
                } else {
                    i3 = charAt2 - '0';
                }
                bArr[i5 >> 1] = (byte) (i6 + i3);
            }
            return bArr;
        }
        throw new IllegalArgumentException("s is not a readable string: " + str);
    }

    public static String toHexReadable(byte[] bArr) {
        StringBuilder acquire = Pools.getStringBuilderPool().acquire();
        toHexReadable(bArr, acquire);
        String sb = acquire.toString();
        Pools.getStringBuilderPool().release(acquire);
        return sb;
    }

    public static void toHexReadable(byte[] bArr, Appendable appendable) {
        if (bArr != null) {
            try {
                for (byte b2 : bArr) {
                    int i = b2;
                    if (b2 < 0) {
                        i = b2 + 256;
                    }
                    appendable.append(HEX_DIGITS[(i == 1 ? 1 : 0) >> 4]).append(HEX_DIGITS[(i == 1 ? 1 : 0) & 15]);
                }
            } catch (IOException e2) {
                throw new RuntimeException("Exception throw during when append", e2);
            }
        }
    }
}
