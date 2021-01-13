package com.android.camera;

import android.graphics.Rect;
import b.c.a.c;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class HybridZoomingSystem {
    private static final String DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION = (c.isSupportedOpticalZoom() ? "1.0:2.0" : "1.0");
    public static final float FLOAT_MICRO_SCENE_ZOOM_MAX = 1.0f;
    public static final float FLOAT_MOON_MODE_ZOOM_MAX = 20.0f;
    public static final float FLOAT_MOON_MODE_ZOOM_MIN = 1.0f;
    public static final float FLOAT_STEP_FOR_ZOOM_RATIO_CHANGE = 0.1f;
    public static final float FLOAT_SUPER_MOON_ZOOM_MAX = 60.0f;
    public static final float FLOAT_SUPER_MOON_ZOOM_MIN = 5.0f;
    public static final float FLOAT_ULTRA_WIDE_ZOOM_MAX = 6.0f;
    public static final float FLOAT_ZOOM_RATIO_10X = 10.0f;
    public static final float FLOAT_ZOOM_RATIO_2X = 2.0f;
    public static final float FLOAT_ZOOM_RATIO_30X = 30.0f;
    public static final float FLOAT_ZOOM_RATIO_3X = 3.0f;
    public static final float FLOAT_ZOOM_RATIO_5X = 5.0f;
    public static final float FLOAT_ZOOM_RATIO_MACRO = 0.0f;
    public static final float FLOAT_ZOOM_RATIO_NONE = 1.0f;
    public static float FLOAT_ZOOM_RATIO_ULTR = 0.0f;
    public static final float FLOAT_ZOOM_RATIO_ULTRA_TELE_THRESHHOLD = 3.7f;
    public static final float FLOAT_ZOOM_RATIO_WIDE = 1.0f;
    public static final boolean IS_2_OR_MORE_SAT = (OPTICAL_ZOOM_RATIO_COMBINATION >= 2);
    public static final boolean IS_2_SAT = (OPTICAL_ZOOM_RATIO_COMBINATION == 2);
    public static final boolean IS_3_OR_MORE_SAT = (OPTICAL_ZOOM_RATIO_COMBINATION >= 3);
    public static final boolean IS_3_SAT = (OPTICAL_ZOOM_RATIO_COMBINATION == 3);
    public static final boolean IS_4_OR_MORE_SAT;
    public static final boolean IS_4_SAT = (OPTICAL_ZOOM_RATIO_COMBINATION == 4);
    private static final String MACRO_OPTICAL_ZOOM_RATIO_COMBINATION = DataRepository.dataItemFeature().X(DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION);
    private static final int OPTICAL_ZOOM_RATIO_COMBINATION = DataRepository.dataItemFeature().y(c.isSupportedOpticalZoom() ? 2 : 1);
    public static final String STRING_SUPER_MOON_ZOOM_RATIO_NONE = "5.0";
    public static final String STRING_ZOOM_RATIO_NONE = "1.0";
    private static final String TAG = "HybridZoomingSystem";
    public static final float TOLERANCE_FOR_ZOOM_RATIO_CHANGED = 0.01f;
    public static final String ZOOM_INDEXS_DEFAULT = "0.6:1:2:5:10";
    public static final String ZOOM_INDEXS_SUPER_MOON = "5:20:30:40:50:60";
    public static final String ZOOM_RULER_DEFAULT = "4:10:10:5";
    public static final String ZOOM_RULER_SUPER_MOON = "3:10:10:10:10";
    public static float sDefaultMacroOpticalZoomRatio;
    private static int sDefaultOpticalZoomRatioIndex;
    private static float[] sMacroSupportedOpticalZoomRatios;
    private static int sMacroZoomRatioIndex = -1;
    private static float[] sNightSupportedOpticalZoomRatios;
    private static int sNightSupportedZoomRatioIndex;
    private static float[] sPixelSupportedOpticalZoomRatios;
    private static int sPixelSupportedZoomRatioIndex = 0;
    private static float[] sSuperMoonSupportedOpticalZoomRatios = {5.0f, 20.0f};
    private static int sSuperMoonSupportedZoomRatioIndex = 0;
    private static float[] sSupportedOpticalZoomRatios;
    private static final Map<Integer, String> sZoomRatioHistory = new ConcurrentHashMap();

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th != null) {
            try {
                autoCloseable.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        } else {
            autoCloseable.close();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:0x01e5, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01e6, code lost:
        $closeResource(r0, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x01e9, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0188, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0189, code lost:
        $closeResource(r0, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x018c, code lost:
        throw r1;
     */
    static {
        int i = -1;
        sPixelSupportedOpticalZoomRatios = new float[]{1.0f, 2.0f};
        boolean z = false;
        sNightSupportedOpticalZoomRatios = new float[]{0.6f, 1.0f, 2.0f};
        sNightSupportedZoomRatioIndex = 1;
        sDefaultOpticalZoomRatioIndex = -1;
        sDefaultMacroOpticalZoomRatio = 1.0f;
        FLOAT_ZOOM_RATIO_ULTR = 0.6f;
        ArrayList<String> arrayList = new ArrayList();
        Scanner scanner = new Scanner(DataRepository.dataItemFeature().Y(DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION));
        scanner.useDelimiter("\\s*[:,]\\s*");
        int i2 = -1;
        while (scanner.hasNext()) {
            String next = scanner.next();
            if (next != null && next.length() > 0) {
                arrayList.add(next);
                i2++;
                if (next.equals("1.0") && sDefaultOpticalZoomRatioIndex == -1) {
                    sDefaultOpticalZoomRatioIndex = i2;
                }
            }
        }
        $closeResource(null, scanner);
        if (sDefaultOpticalZoomRatioIndex < 0 || arrayList.size() < 1) {
            throw new IllegalStateException("The supported optical zoom ratios are probably not configured correctly");
        }
        sSupportedOpticalZoomRatios = new float[Math.min(arrayList.size(), 3)];
        int i3 = 0;
        while (true) {
            float[] fArr = sSupportedOpticalZoomRatios;
            if (i3 >= fArr.length) {
                break;
            }
            fArr[i3] = Float.parseFloat((String) arrayList.get(i3));
            float[] fArr2 = sSupportedOpticalZoomRatios;
            if (fArr2[i3] < 1.0f) {
                FLOAT_ZOOM_RATIO_ULTR = fArr2[i3];
            }
            i3++;
        }
        Log.d(TAG, Arrays.toString(sSupportedOpticalZoomRatios) + "[" + sDefaultOpticalZoomRatioIndex + "]");
        if (c.dh) {
            sPixelSupportedOpticalZoomRatios = new float[]{1.0f, 5.0f};
        }
        if (DataRepository.dataItemFeature().isSupportMacroMode()) {
            arrayList.clear();
            Scanner scanner2 = new Scanner(MACRO_OPTICAL_ZOOM_RATIO_COMBINATION);
            scanner2.useDelimiter("\\s*[:,]\\s*");
            while (scanner2.hasNext()) {
                String next2 = scanner2.next();
                if (next2 != null && next2.length() > 0) {
                    arrayList.add(next2);
                    i++;
                    if (i == 0) {
                        sDefaultMacroOpticalZoomRatio = Float.valueOf(next2).floatValue();
                    }
                }
            }
            $closeResource(null, scanner2);
            if (arrayList.size() >= 1) {
                sMacroSupportedOpticalZoomRatios = new float[arrayList.size()];
                int i4 = 0;
                for (String str : arrayList) {
                    sMacroSupportedOpticalZoomRatios[i4] = Float.parseFloat(str);
                    i4++;
                }
                Log.d(TAG, Arrays.toString(sMacroSupportedOpticalZoomRatios));
            } else {
                throw new IllegalStateException("The supported optical zoom ratios are probably not configured correctly");
            }
        }
        if (DataRepository.dataItemFeature().Fk()) {
            if (c.hh) {
                sNightSupportedOpticalZoomRatios = sSupportedOpticalZoomRatios;
            }
            sNightSupportedOpticalZoomRatios[0] = FLOAT_ZOOM_RATIO_ULTR;
            sNightSupportedZoomRatioIndex = 1;
        }
        if (OPTICAL_ZOOM_RATIO_COMBINATION >= 4) {
            z = true;
        }
        IS_4_OR_MORE_SAT = z;
    }

    private HybridZoomingSystem() {
    }

    public static float add(float f, float f2) {
        return ((float) (((int) (f * 10.0f)) + ((int) (f2 * 10.0f)))) / 10.0f;
    }

    public static float clamp(float f, float f2, float f3) {
        return f > f3 ? f3 : f < f2 ? f2 : f;
    }

    public static void clearZoomRatioHistory() {
        Log.d(TAG, "clearZoomRatioHistory()");
        sZoomRatioHistory.clear();
    }

    public static int getDefaultOpticalZoomRatioIndex(int i) {
        return i != 173 ? i != 175 ? i != 188 ? sDefaultOpticalZoomRatioIndex : sSuperMoonSupportedZoomRatioIndex : sPixelSupportedZoomRatioIndex : sNightSupportedZoomRatioIndex;
    }

    public static int getMacroZoomRatioIndex(int i) {
        if (i == 161 || i == 183 || i == 188) {
            return -1;
        }
        switch (i) {
            case 173:
            case 174:
            case 175:
                return -1;
            default:
                return sMacroZoomRatioIndex;
        }
    }

    public static float getMaximumMacroOpticalZoomRatio() {
        float[] fArr = sMacroSupportedOpticalZoomRatios;
        return fArr[fArr.length - 1];
    }

    public static float getMaximumOpticalZoomRatio(int i) {
        float[] fArr = i != 173 ? i != 175 ? i != 188 ? sSupportedOpticalZoomRatios : sSuperMoonSupportedOpticalZoomRatios : sPixelSupportedOpticalZoomRatios : sNightSupportedOpticalZoomRatios;
        return fArr[fArr.length - 1];
    }

    public static float getMinimumMacroOpticalZoomRatio() {
        return sMacroSupportedOpticalZoomRatios[0];
    }

    public static float getMinimumOpticalZoomRatio(int i) {
        return i != 173 ? i != 175 ? i != 188 ? sMacroZoomRatioIndex != -1 ? sSupportedOpticalZoomRatios[1] : sSupportedOpticalZoomRatios[0] : sSuperMoonSupportedOpticalZoomRatios[0] : sPixelSupportedOpticalZoomRatios[0] : sNightSupportedOpticalZoomRatios[0];
    }

    public static float getOpticalZoomRatioAt(int i, int i2) {
        float[] fArr = i != 173 ? i != 175 ? i != 188 ? sSupportedOpticalZoomRatios : sSuperMoonSupportedOpticalZoomRatios : sPixelSupportedOpticalZoomRatios : sNightSupportedOpticalZoomRatios;
        int length = fArr.length;
        if (i2 < 0) {
            Log.e(TAG, "The given index must be in range [0, " + length + ")   curIndex error : " + i2);
            i2 = 0;
        } else if (i2 >= length) {
            Log.e(TAG, "The given index must be in range [0, " + length + ")   curIndex error : " + i2);
            i2 = length + -1;
        }
        return fArr[i2];
    }

    public static int getOpticalZoomRatioIndex(int i, float f) {
        float[] fArr = i != 173 ? i != 175 ? i != 188 ? sSupportedOpticalZoomRatios : sSuperMoonSupportedOpticalZoomRatios : sPixelSupportedOpticalZoomRatios : sNightSupportedOpticalZoomRatios;
        for (int length = fArr.length - 1; length >= 0; length--) {
            if (f >= fArr[length]) {
                return length;
            }
        }
        throw new IllegalArgumentException("Illegal zoom ratio: " + f);
    }

    public static float[] getSupportedOpticalZoomRatios(int i) {
        return i != 173 ? i != 175 ? i != 188 ? sSupportedOpticalZoomRatios : sSuperMoonSupportedOpticalZoomRatios : sPixelSupportedOpticalZoomRatios : sNightSupportedOpticalZoomRatios;
    }

    public static float getTeleMinZoomRatio() {
        CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getAuxCameraId());
        if (capabilities == null) {
            return 2.0f;
        }
        return capabilities.getMinZoomRatio(2.0f);
    }

    public static float getTeleZoomRatio(int i) {
        float[] supportedOpticalZoomRatios = getSupportedOpticalZoomRatios(i);
        int defaultOpticalZoomRatioIndex = getDefaultOpticalZoomRatioIndex(i) + 1;
        if (defaultOpticalZoomRatioIndex < supportedOpticalZoomRatios.length) {
            return supportedOpticalZoomRatios[defaultOpticalZoomRatioIndex];
        }
        return 2.0f;
    }

    public static float getUltraTeleMinZoomRatio() {
        CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getUltraTeleCameraId());
        if (capabilities == null) {
            return 5.0f;
        }
        return capabilities.getMinZoomRatio(5.0f);
    }

    public static String getZoomRatioHistory(int i, String str) {
        if (i == 165 || i == 186) {
            i = 163;
        }
        String str2 = sZoomRatioHistory.get(Integer.valueOf(i));
        return i == 188 ? str2 != null ? str2 : STRING_SUPER_MOON_ZOOM_RATIO_NONE : str2 != null ? str2 : str;
    }

    public static float getZoomRatioNone(boolean z, int i) {
        if (!DataRepository.dataItemFeature().jj() || !z) {
            return 1.0f;
        }
        if (i == 0 || i == 180) {
            return DataRepository.dataItemFeature().wh();
        }
        return 1.0f;
    }

    public static boolean isOpticalZoomRatio(int i, float f) {
        return Arrays.binarySearch(getSupportedOpticalZoomRatios(i), f) >= 0;
    }

    public static boolean isZoomRatioNone(float f, boolean z) {
        return (!DataRepository.dataItemFeature().jj() || !z) ? f == 1.0f : f == 1.0f || f == DataRepository.dataItemFeature().wh();
    }

    static void preload() {
        Log.d(TAG, "preload");
    }

    public static void setZoomRatioHistory(int i, String str) {
        if (i == 165 || i == 186) {
            i = 163;
        }
        sZoomRatioHistory.put(Integer.valueOf(i), str);
    }

    public static float sub(float f, float f2) {
        return ((float) (((int) (f * 10.0f)) - ((int) (f2 * 10.0f)))) / 10.0f;
    }

    public static Rect toCropRegion(float f, Rect rect) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Zoom ratio must be greater than 0.0f");
        } else if (rect != null) {
            int width = rect.width() / 2;
            int height = rect.height() / 2;
            float f2 = 2.0f * f;
            int width2 = (int) (((float) rect.width()) / f2);
            int height2 = (int) (((float) rect.height()) / f2);
            Rect rect2 = new Rect();
            rect2.set(width - width2, height - height2, width + width2, height + height2);
            Log.d(TAG, "toCropRegion(): zoom ratio = " + f + ", crop region = " + rect2);
            return rect2;
        } else {
            throw new IllegalArgumentException("activeArraySize must be non null");
        }
    }

    public static float toDecimal(float f) {
        return ((float) ((int) (f * 10.0f))) / 10.0f;
    }

    public static float toFloat(String str, float f) {
        try {
            return Float.parseFloat(str);
        } catch (Exception unused) {
            Log.e(TAG, "Invalid zoom: " + str);
            return f;
        }
    }

    public static int[] toMTKCropRegion(float f, Rect rect) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Zoom ratio must be greater than 0.0f");
        } else if (rect != null) {
            float f2 = 2.0f * f;
            int width = (int) (((float) rect.width()) / f2);
            int height = (int) (((float) rect.height()) / f2);
            int[] iArr = {(rect.width() / 2) - width, (rect.height() / 2) - height, width * 2, height * 2};
            Log.d(TAG, "toMTKCropRegion(): zoom ratio = " + f + ", crop region = " + ("int[]{" + iArr[0] + ", " + iArr[1] + ", " + iArr[2] + ", " + iArr[3] + "}"));
            return iArr;
        } else {
            throw new IllegalArgumentException("activeArraySize must be non null");
        }
    }

    public static String toString(float f) {
        if (FLOAT_ZOOM_RATIO_ULTR == f || 1.0f == f || getTeleMinZoomRatio() == f || getUltraTeleMinZoomRatio() == f) {
            return String.format(Locale.US, "%.1fx", Float.valueOf(f));
        } else if (0.0f == f) {
            return "macro";
        } else {
            float decimal = toDecimal(f);
            return String.format(Locale.US, "%.1fx", Float.valueOf(decimal));
        }
    }

    public static float toZoomRatio(Rect rect, Rect rect2) {
        if (rect == null || rect2 == null) {
            throw new IllegalArgumentException("activeArraySize & cropRegion must be non null");
        }
        float width = ((float) rect.width()) / ((float) rect2.width());
        Log.c(TAG, "toZoomRatio(): activeArraySize = " + rect + ", crop region = " + rect2);
        return width;
    }
}
