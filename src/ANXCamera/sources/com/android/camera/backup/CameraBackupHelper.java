package com.android.camera.backup;

import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.log.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import miui.cloud.backup.data.DataPackage;
import miui.cloud.backup.data.KeyStringSettingItem;
import miui.cloud.backup.data.PrefsBackupHelper;
import org.xmlpull.v1.XmlPullParserException;

public class CameraBackupHelper {
    private static final String TAG = "CameraBackupHelper";

    private static String convertAntiBandingMode(String str) {
        if (str == null) {
            return null;
        }
        char c2 = 65535;
        switch (str.hashCode()) {
            case 109935:
                if (str.equals("off")) {
                    c2 = 0;
                    break;
                }
                break;
            case 1628397:
                if (str.equals("50hz")) {
                    c2 = 1;
                    break;
                }
                break;
            case 1658188:
                if (str.equals("60hz")) {
                    c2 = 2;
                    break;
                }
                break;
            case 3005871:
                if (str.equals("auto")) {
                    c2 = 3;
                    break;
                }
                break;
        }
        if (c2 == 0) {
            return String.valueOf(0);
        }
        if (c2 == 1) {
            return String.valueOf(1);
        }
        if (c2 == 2) {
            return String.valueOf(2);
        }
        if (c2 == 3) {
            return String.valueOf(3);
        }
        Log.w(TAG, "unknown antibanding mode " + str);
        return null;
    }

    private static String convertContrast(String str) {
        return null;
    }

    private static String convertExposureMode(String str) {
        if (str == null) {
            return null;
        }
        char c2 = 65535;
        switch (str.hashCode()) {
            case -1364013995:
                if (str.equals("center")) {
                    c2 = 2;
                    break;
                }
                break;
            case -1200037852:
                if (str.equals("spot-metering")) {
                    c2 = 5;
                    break;
                }
                break;
            case -631448035:
                if (str.equals("average")) {
                    c2 = 0;
                    break;
                }
                break;
            case 3537154:
                if (str.equals("spot")) {
                    c2 = 4;
                    break;
                }
                break;
            case 1302812559:
                if (str.equals("center-weighted")) {
                    c2 = 3;
                    break;
                }
                break;
            case 2133765565:
                if (str.equals("frame-average")) {
                    c2 = 1;
                    break;
                }
                break;
        }
        if (c2 == 0 || c2 == 1) {
            return "0";
        }
        if (c2 == 2 || c2 == 3) {
            return "1";
        }
        if (c2 == 4 || c2 == 5) {
            return "2";
        }
        Log.w(TAG, "unknown exposure mode " + str);
        return null;
    }

    private static String convertSaturation(String str) {
        return null;
    }

    private static String convertSharpness(String str) {
        return null;
    }

    private static String convertVideoQuality(String str) {
        if (str == null) {
            return null;
        }
        char c2 = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 56) {
            if (hashCode != 57) {
                switch (hashCode) {
                    case 52:
                        if (str.equals("4")) {
                            c2 = 0;
                            break;
                        }
                        break;
                    case 53:
                        if (str.equals("5")) {
                            c2 = 2;
                            break;
                        }
                        break;
                    case 54:
                        if (str.equals("6")) {
                            c2 = 4;
                            break;
                        }
                        break;
                    default:
                        switch (hashCode) {
                            case 1567:
                                if (str.equals("10")) {
                                    c2 = 3;
                                    break;
                                }
                                break;
                            case 1568:
                                if (str.equals("11")) {
                                    c2 = 5;
                                    break;
                                }
                                break;
                            case 1569:
                                if (str.equals("12")) {
                                    c2 = 7;
                                    break;
                                }
                                break;
                        }
                }
            } else if (str.equals("9")) {
                c2 = 1;
            }
        } else if (str.equals("8")) {
            c2 = 6;
        }
        switch (c2) {
            case 0:
            case 1:
                return "4";
            case 2:
            case 3:
                return "5";
            case 4:
            case 5:
                return "6";
            case 6:
            case 7:
                return "8";
            default:
                String str2 = TAG;
                Log.w(str2, "unknown video quality " + str);
                return null;
        }
    }

    private static String filterValue(String str, int i) {
        if (Util.isSupported(str, CameraAppImpl.getAndroidContext().getResources().getStringArray(i))) {
            return str;
        }
        return null;
    }

    private static List<String> getSettingsKeys() {
        String attributeValue;
        ArrayList arrayList = new ArrayList();
        XmlResourceParser xml = CameraAppImpl.getAndroidContext().getResources().getXml(R.xml.camera_other_preferences_new);
        try {
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if ((eventType == 2 || eventType == 3) && xml.getDepth() >= 3 && (attributeValue = xml.getAttributeValue("http://schemas.android.com/apk/res/android", WatermarkConstant.ITEM_KEY)) != null) {
                    arrayList.add(attributeValue);
                }
            }
        } catch (IOException | XmlPullParserException e2) {
            Log.e(TAG, e2.getMessage(), e2);
        }
        xml.close();
        return arrayList;
    }

    public static void restoreSettings(SharedPreferences sharedPreferences, DataPackage dataPackage, PrefsBackupHelper.PrefEntry[] prefEntryArr, boolean z) {
        List<String> settingsKeys = getSettingsKeys();
        SharedPreferences.Editor edit = sharedPreferences.edit();
        for (PrefsBackupHelper.PrefEntry prefEntry : prefEntryArr) {
            if (settingsKeys.contains(prefEntry.getLocalKey()) && ((!z || !CameraSettings.isCameraSpecific(prefEntry.getLocalKey())) && (z || CameraSettings.isCameraSpecific(prefEntry.getLocalKey())))) {
                try {
                    try {
                        KeyStringSettingItem keyStringSettingItem = dataPackage.get(prefEntry.getCloudKey());
                        if (keyStringSettingItem != null) {
                            String localKey = prefEntry.getLocalKey();
                            char c2 = 65535;
                            switch (localKey.hashCode()) {
                                case -302378757:
                                    if (localKey.equals(CameraSettings.KEY_QC_SATURATION)) {
                                        c2 = 3;
                                        break;
                                    }
                                    break;
                                case -33912691:
                                    if (localKey.equals(CameraSettings.KEY_ANTIBANDING)) {
                                        c2 = 0;
                                        break;
                                    }
                                    break;
                                case 17536442:
                                    if (localKey.equals(CameraSettings.KEY_FRONT_MIRROR)) {
                                        c2 = 5;
                                        break;
                                    }
                                    break;
                                case 549001748:
                                    if (localKey.equals(CameraSettings.KEY_AUTOEXPOSURE)) {
                                        c2 = 1;
                                        break;
                                    }
                                    break;
                                case 936502456:
                                    if (localKey.equals(CameraSettings.KEY_QC_SHARPNESS)) {
                                        c2 = 4;
                                        break;
                                    }
                                    break;
                                case 1907727979:
                                    if (localKey.equals(CameraSettings.KEY_QC_CONTRAST)) {
                                        c2 = 2;
                                        break;
                                    }
                                    break;
                            }
                            String filterValue = c2 != 0 ? c2 != 1 ? c2 != 2 ? c2 != 3 ? c2 != 4 ? c2 != 5 ? (String) keyStringSettingItem.getValue() : filterValue((String) keyStringSettingItem.getValue(), R.array.pref_front_mirror_entryvalues) : convertSharpness((String) keyStringSettingItem.getValue()) : convertSaturation((String) keyStringSettingItem.getValue()) : convertContrast((String) keyStringSettingItem.getValue()) : convertExposureMode((String) keyStringSettingItem.getValue()) : convertAntiBandingMode((String) keyStringSettingItem.getValue());
                            if (filterValue != null) {
                                if (prefEntry.getValueClass() == Integer.class) {
                                    edit.putInt(prefEntry.getLocalKey(), Integer.parseInt(filterValue));
                                } else if (prefEntry.getValueClass() == Long.class) {
                                    edit.putLong(prefEntry.getLocalKey(), Long.parseLong(filterValue));
                                } else if (prefEntry.getValueClass() == Boolean.class) {
                                    edit.putBoolean(prefEntry.getLocalKey(), Boolean.parseBoolean(filterValue));
                                } else if (prefEntry.getValueClass() == String.class) {
                                    edit.putString(prefEntry.getLocalKey(), filterValue);
                                }
                            }
                        }
                    } catch (ClassCastException unused) {
                        Log.e(TAG, "entry " + prefEntry.getCloudKey() + " is not KeyStringSettingItem");
                    }
                } catch (ClassCastException unused2) {
                    Log.e(TAG, "entry " + prefEntry.getCloudKey() + " is not KeyStringSettingItem");
                }
            }
        }
        edit.commit();
    }
}
