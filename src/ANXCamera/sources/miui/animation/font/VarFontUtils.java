package miui.animation.font;

import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import com.android.camera.Util;
import com.miui.internal.widget.SearchActionModeViewHelper;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import miui.animation.utils.CommonUtils;
import miui.util.AppConstants;

public class VarFontUtils {
    private static final int DEFAULT_WGHT = 50;
    private static final String FORMAT_WGHT = "'wght' ";
    private static final boolean IS_USING_VAR_FONT = (!isUsingThemeFont());
    private static final String KEY_FONT_WEIGHT = "key_miui_font_weight_scale";
    public static final int MIN_WGHT;
    private static final Typeface MIPRO_FONT = Typeface.create(MIUI_FAMILY, 0);
    private static final String MITYPE_FAMILY = "mitype";
    private static final Typeface MITYPE_FONT = Typeface.create(MITYPE_FAMILY, 0);
    private static final String MITYPE_MONO_FAMILY = "mitype-mono";
    private static final Typeface MITYPE_MONO_FONT = Typeface.create(MITYPE_MONO_FAMILY, 0);
    private static final int[] MITYPE_WGHT;
    private static final String MIUI_FAMILY = "mipro";
    private static final int[] MIUI_WGHT;
    private static final int[][][] RULES;
    private static final String THEME_FONT_DIR = "/data/system/theme/fonts/";
    private static Method sMethodCreateVarFont = getMethodCreateVarFont();

    static {
        if (IS_USING_VAR_FONT) {
            MIUI_WGHT = new int[]{150, 200, 250, 305, 340, SearchActionModeViewHelper.ANIMATION_DURATION, 480, 540, 630, Util.KEYCODE_SLIDE_ON};
            MITYPE_WGHT = new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
            MIN_WGHT = 10;
            RULES = new int[3][][];
            int[][][] iArr = RULES;
            iArr[0] = new int[][]{new int[]{0, 5}, new int[]{0, 5}, new int[]{1, 6}, new int[]{2, 6}, new int[]{2, 7}, new int[]{3, 8}, new int[]{4, 8}, new int[]{5, 9}, new int[]{6, 9}, new int[]{7, 9}};
            iArr[1] = new int[][]{new int[]{0, 2}, new int[]{0, 3}, new int[]{1, 4}, new int[]{1, 5}, new int[]{2, 6}, new int[]{2, 7}, new int[]{3, 8}, new int[]{4, 9}, new int[]{5, 9}, new int[]{6, 9}};
            iArr[2] = new int[][]{new int[]{0, 5}, new int[]{1, 5}, new int[]{2, 5}, new int[]{3, 6}, new int[]{3, 6}, new int[]{4, 7}, new int[]{5, 8}, new int[]{6, 8}, new int[]{7, 8}, new int[]{8, 9}};
            return;
        }
        MIN_WGHT = 0;
        int[] iArr2 = new int[0];
        MITYPE_WGHT = iArr2;
        MIUI_WGHT = iArr2;
        RULES = new int[0][][];
    }

    private VarFontUtils() {
    }

    private static Typeface getBaseFont(int i) {
        return i != 1 ? i != 2 ? MIPRO_FONT : MITYPE_MONO_FONT : MITYPE_FONT;
    }

    private static Method getMethodCreateVarFont() {
        try {
            return Class.forName("android.graphics.Typeface").getMethod("createFromTypefaceWithVariation", Typeface.class, List.class);
        } catch (Exception e2) {
            Log.w(CommonUtils.TAG, "getMethodCreateVarFont failed", e2);
            return null;
        }
    }

    public static int getScaleWght(int i, float f, int i2) {
        return getScaleWght(i, f, i2, getSysFontScale());
    }

    static int getScaleWght(int i, float f, int i2, int i3) {
        float f2;
        if (!IS_USING_VAR_FONT) {
            return i;
        }
        int[] wghtRange = getWghtRange(i, f);
        int wghtByType = getWghtByType(wghtRange[0], i2);
        int wghtByType2 = getWghtByType(i, i2);
        int wghtByType3 = getWghtByType(wghtRange[1], i2);
        if (i3 < 50) {
            float f3 = ((float) i3) / 50.0f;
            f2 = ((1.0f - f3) * ((float) wghtByType)) + (f3 * ((float) wghtByType2));
        } else if (i3 <= 50) {
            return wghtByType2;
        } else {
            float f4 = ((float) (i3 - 50)) / 50.0f;
            f2 = ((1.0f - f4) * ((float) wghtByType2)) + (f4 * ((float) wghtByType3));
        }
        return (int) f2;
    }

    static int getSysFontScale() {
        return Settings.System.getInt(AppConstants.getCurrentApplication().getContentResolver(), KEY_FONT_WEIGHT, 50);
    }

    public static Typeface getVarTypeface(int i, int i2) {
        Method method;
        if (!IS_USING_VAR_FONT || (method = sMethodCreateVarFont) == null) {
            return null;
        }
        try {
            return (Typeface) method.invoke(Typeface.class, getBaseFont(i2), Arrays.asList(new FontVariationAxis("wght", (float) i)));
        } catch (Exception e2) {
            Log.w(CommonUtils.TAG, "getVarTypeface failed", e2);
            return null;
        }
    }

    private static int[] getWghtArray(int i) {
        return (i == 1 || i == 2) ? MITYPE_WGHT : MIUI_WGHT;
    }

    private static int getWghtByType(int i, int i2) {
        return getWghtArray(i2)[i];
    }

    private static int[] getWghtRange(int i, float f) {
        return RULES[f > 20.0f ? 1 : (f <= 0.0f || f >= 12.0f) ? (char) 0 : 2][i];
    }

    private static boolean isUsingThemeFont() {
        String[] list;
        File file = new File(THEME_FONT_DIR);
        try {
            return file.exists() && (list = file.list()) != null && list.length > 0;
        } catch (Exception e2) {
            Log.w(CommonUtils.TAG, "isUsingThemeFont, failed to check theme font directory", e2);
        }
        return false;
    }

    static void setVariationFont(TextView textView, int i) {
        if (IS_USING_VAR_FONT) {
            textView.setFontVariationSettings(FORMAT_WGHT + i);
        }
    }
}
