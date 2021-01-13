package com.android.camera.watermark;

import android.content.res.Resources;
import android.os.Build;
import androidx.annotation.StringRes;
import b.c.a.c;
import com.android.camera.CameraAppImpl;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningEisPro;
import com.android.camera.log.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class WaterMarkUtil {
    public static final String DIR_WATER_MARK = "watermarks";
    private static final String FILE_NAME_PREFIX = "ic_water_mark_";
    private static final String GEN2_FILE_NAME_PREFIX = "gen2_";
    private static final int ICON_CC = 1;
    private static final int ICON_DUAL = 2;
    private static final int ICON_NONE = 0;
    private static final int ICON_PENTA = 5;
    private static final int ICON_QUAD = 4;
    private static final int ICON_TRIPLE = 3;
    private static final String TAG = "WaterMarkUtil";
    private static final String WATER_MARK_BACK_NAME_COMMON = "back_common";
    private static final String WATER_MARK_BACK_NAME_PREFIX = "back_";
    private static final String WATER_MARK_FRONT_NAME_COMMON = "front_common";
    private static final String WATER_MARK_FRONT_NAME_PREFIX = "front_";
    private static List<String> sFrontWaterMarkList;
    private static List<String> sWaterMarkList;

    @Retention(RetentionPolicy.SOURCE)
    private @interface ICON_TYPE {
    }

    private WaterMarkUtil() {
    }

    public static String getBackIconName() {
        int backIconType = getBackIconType();
        String str = backIconType != 1 ? backIconType != 2 ? backIconType != 3 ? backIconType != 4 ? backIconType != 5 ? "" : "penta_back" : "quad" : "triple" : "dual" : "cc_back";
        return FILE_NAME_PREFIX + str + ".webp";
    }

    public static String getBackIconNameGen2() {
        int backIconType = getBackIconType();
        String str = backIconType != 3 ? backIconType != 4 ? backIconType != 5 ? "dual" : "penta" : "quad" : "triple";
        return GEN2_FILE_NAME_PREFIX + str + ".webp";
    }

    private static int getBackIconType() {
        return Integer.parseInt(getWaterCameraBack().get(0));
    }

    public static String getDescription() {
        return (!DataRepository.dataItemFeature().lj() && getWaterCameraBack().size() > 2) ? getWaterCameraBack().get(2) : "";
    }

    private static String getExtraSuffix() {
        if (c.lh) {
            if (c.Vm()) {
                return "48m";
            }
            if (c.Wm()) {
                return "speed";
            }
        }
        return c.Om() ? "lite" : c.Qm() ? "s" : (!c.bn() || !c.pn()) ? c.jo() ? c.Bm().replace("_", "") : Build.MODEL.replace(" ", "_").toLowerCase(Locale.ENGLISH) : ComponentRunningEisPro.EIS_VALUE_PRO;
    }

    public static String getFrontIconName() {
        int frontIconType = getFrontIconType();
        String str = frontIconType != 1 ? frontIconType != 5 ? "" : "penta_front" : "cc_front";
        return FILE_NAME_PREFIX + str + ".webp";
    }

    private static int getFrontIconType() {
        return Integer.parseInt(getWaterCameraFront().get(0));
    }

    public static String getFrontTitle() {
        return getWaterCameraFront().size() > 1 ? getWaterCameraFront().get(1) : "";
    }

    private static int getStringId(Resources resources, String str) {
        return resources.getIdentifier(str, "string", "com.android.camera");
    }

    public static String getTitle() {
        return getWaterCameraBack().size() > 1 ? getWaterCameraBack().get(1) : "";
    }

    private static List<String> getWaterCameraBack() {
        if (sWaterMarkList == null) {
            Resources resources = CameraAppImpl.getAndroidContext().getResources();
            sWaterMarkList = Arrays.asList(resources.getString(getWaterMarkId(resources, WATER_MARK_BACK_NAME_PREFIX, WATER_MARK_BACK_NAME_COMMON)).toUpperCase(Locale.ENGLISH).split(":"));
            if (!DataRepository.dataItemFeature().lj() && sWaterMarkList.size() < 4) {
                throw new InvalidParameterException("Back camera water mark need four parameters!");
            }
        }
        return sWaterMarkList;
    }

    private static List<String> getWaterCameraFront() {
        if (sFrontWaterMarkList == null) {
            Resources resources = CameraAppImpl.getAndroidContext().getResources();
            sFrontWaterMarkList = Arrays.asList(resources.getString(getWaterMarkId(resources, WATER_MARK_FRONT_NAME_PREFIX, WATER_MARK_FRONT_NAME_COMMON)).toUpperCase(Locale.ENGLISH).split(":"));
            if (sFrontWaterMarkList.size() < 2) {
                throw new InvalidParameterException("Front camera water mark need two parameters!");
            }
        }
        return sFrontWaterMarkList;
    }

    @StringRes
    private static int getWaterMarkId(Resources resources, String str, String str2) {
        String str3;
        String str4 = str + Build.DEVICE;
        if (DataRepository.dataItemFeature().Di()) {
            str3 = str4 + "_india";
        } else if (Util.isGlobalVersion()) {
            str3 = str4 + "_global";
        } else {
            str3 = str4;
        }
        String str5 = str3 + "_" + getExtraSuffix();
        Log.d(TAG, "getWaterMarkId: all = " + str5);
        int stringId = getStringId(resources, str5);
        if (stringId > 0) {
            Log.d(TAG, "getWaterMarkId: find first = " + str5);
            return stringId;
        }
        int stringId2 = getStringId(resources, str3);
        if (stringId2 > 0) {
            Log.d(TAG, "getWaterMarkId: find second = " + str3);
            return stringId2;
        }
        int stringId3 = getStringId(resources, str4);
        if (stringId3 <= 0) {
            return getStringId(resources, str2);
        }
        Log.d(TAG, "getWaterMarkId: find third = " + str4);
        return stringId3;
    }
}
