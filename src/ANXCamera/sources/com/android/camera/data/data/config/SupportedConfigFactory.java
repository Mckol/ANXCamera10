package com.android.camera.data.data.config;

import b.c.a.b;
import b.c.a.c;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera2.CameraCapabilities;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class SupportedConfigFactory {
    public static final int AI_108 = 171;
    public static final int AI_AUDIO = 168;
    public static final int AI_DETECT = 242;
    public static final int AI_SCENE = 201;
    public static final int AI_WATERMARK = 223;
    public static final int AUTO_ZOOM = 253;
    public static final int BACK = 217;
    public static final int BACKLIGHT = 249;
    public static final int BEAUTY = 239;
    public static final int BOKEH = 200;
    public static final int CINEMATIC_ASPECT_RATIO = 251;
    public static final int CLONE_USE_GUIDE = 164;
    public static final String CLOSE_BY_AI = "e";
    public static final String CLOSE_BY_BOKEH = "f";
    public static final String CLOSE_BY_BURST_SHOOT = "d";
    public static final String CLOSE_BY_DOCUMENT_MODE = "p";
    public static final String CLOSE_BY_FILTER = "k";
    public static final String CLOSE_BY_GROUP = "b";
    public static final String CLOSE_BY_HDR = "g";
    public static final String CLOSE_BY_HHT = "a";
    public static final String CLOSE_BY_MACRO_MODE = "m";
    public static final String CLOSE_BY_MANUAL_MODE = "mm";
    public static final String CLOSE_BY_RATIO = "l";
    public static final String CLOSE_BY_RAW = "n";
    public static final String CLOSE_BY_SUPER_RESOLUTION = "c";
    public static final String CLOSE_BY_ULTRA_PIXEL = "j";
    public static final String CLOSE_BY_ULTRA_PIXEL_PORTRAIT = "o";
    public static final String CLOSE_BY_ULTRA_WIDE = "i";
    public static final String CLOSE_BY_VIDEO = "h";
    public static final int COLOR_ENHANCE = 227;
    public static final int DOCUMENT = 221;
    public static final int DUAL_WATER_MARK = 240;
    public static final int EXPOSURE_FEEDBACK = 258;
    public static final int EYE_LIGHT = 254;
    public static final int FAST = 233;
    public static final int FILTER = 196;
    public static final int FLASH = 193;
    public static final int FLASH_BLANK = 177;
    public static final int FOCUS_PEAK = 199;
    public static final int GENDER_AGE = 238;
    public static final int GRADIENTER = 229;
    public static final int GROUP = 235;
    public static final int HAND_GESTURE = 252;
    public static final int HDR = 194;
    public static final int HHT = 230;
    public static final int ID_CARD = 166;
    public static final int INVALID = 176;
    public static final int LIGHTING = 203;
    public static final int LIVE_MUSIC_SELECT = 245;
    public static final int LIVE_SHOT = 206;
    public static final int LIVE_VIDEO_QUALITY = 187;
    public static final int MACRO_MODE = 255;
    public static final int MAGIC_FOCUS = 231;
    public static final int MAGIC_MIRROR = 236;
    public static final int METER = 214;
    public static final int MIMOJI_EDIT = 161;
    public static final int MIMOJI_GIF = 162;
    public static final int MOON = 246;
    public static final int MORE = 197;
    public static final int[] MUTEX_MENU_CONFIGS = {236, 235, 228, 230, 241, 234, 195, 238, 203, 206, 209};
    public static final int[] MUTEX_VIDEO_FEATURES = {233, 212, 218, 220, 253, 255, 216, 165};
    public static final int NEAR_RANGE_MODE = 167;
    public static final int NEW_SLOW_MOTION = 204;
    public static final int NIGHT = 247;
    public static final int PORTRAIT = 195;
    public static final int RATIO = 210;
    public static final int RAW = 237;
    public static final int REFERENCE_LINE = 219;
    public static final int RGB_HISTOGRAM = 261;
    public static final int SCENE = 234;
    public static final int SETTING = 225;
    public static final int SHINE = 212;
    public static final int SILHOUETTE = 248;
    public static final int SLOW_QUALITY = 213;
    public static final int SPEECH_SHUTTER = 262;
    public static final int SUBTITLE = 220;
    public static final int SUPER_EIS = 218;
    public static final int SUPER_EIS_PRO = 165;
    public static final int SUPER_RESOLUTION = 241;
    public static final int TILT = 228;
    public static final int TIMER = 226;
    public static final int TIMER_BURST = 170;
    public static final int ULTRA_PIXEL = 209;
    public static final int ULTRA_PIXEL_PORTRAIT = 215;
    public static final int ULTRA_WIDE = 205;
    public static final int ULTRA_WIDE_BOKEH = 207;
    public static final int VIDEO_8K = 256;
    public static final int VIDEO_BOKEH = 243;
    public static final int VIDEO_BOKEH_WITH_COLOR_RETENTION = 244;
    public static final int VIDEO_LOG = 260;
    public static final int VIDEO_QUALITY = 208;
    public static final int VV = 216;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ClosableElement {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CloseElementTrigger {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConfigItem {
    }

    private static TopConfigItem createConfigItem(int i) {
        return new TopConfigItem(i);
    }

    private static TopConfigItem createConfigItem(int i, int i2) {
        return new TopConfigItem(i, i2);
    }

    public static String getConfigKey(int i) {
        if (i == 227) {
            return "pref_color_enhance";
        }
        if (i == 228) {
            return "pref_camera_tilt_shift_mode";
        }
        if (i == 252) {
            return "pref_hand_gesture";
        }
        if (i == 253) {
            return "pref_camera_auto_zoom";
        }
        switch (i) {
            case 195:
                return "pref_camera_portrait_mode_key";
            case 199:
                return "pref_camera_peak_key";
            case 201:
                return "pref_camera_ai_scene_mode_key";
            case 206:
                return "pref_live_shot_enabled";
            case 209:
                return "pref_ultra_pixel";
            case 221:
                return "pref_document_mode_key";
            case 223:
                return "pref_watermark_key";
            case 230:
                return "pref_camera_hand_night_key";
            case 241:
                return "pref_camera_super_resolution_key";
            case 258:
                return "pref_camera_exposure_feedback";
            case 262:
                return "pref_speech_shutter";
            default:
                switch (i) {
                    case 234:
                        return "pref_camera_scenemode_setting_key";
                    case 235:
                        return "pref_camera_groupshot_mode_key";
                    case 236:
                        return "pref_camera_magic_mirror_key";
                    case 237:
                        return "pref_camera_raw_key";
                    case 238:
                        return "pref_camera_show_gender_age_key";
                    default:
                        throw new RuntimeException("unknown config item: " + Integer.toHexString(i));
                }
        }
    }

    public static final SupportedConfigs getSupportedExtraConfigs(int i, int i2, CameraCapabilities cameraCapabilities, boolean z) {
        SupportedConfigs supportedConfigs = new SupportedConfigs();
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        b dataItemFeature = DataRepository.dataItemFeature();
        if (i == 161) {
            supportedConfigs.add(208);
            supportedConfigs.add(225);
            supportedConfigs.add(219);
        } else if (i != 162) {
            if (i != 166) {
                if (i == 167) {
                    if (dataItemConfig.supportRatio()) {
                        supportedConfigs.add(210);
                    }
                    supportedConfigs.add(226);
                    supportedConfigs.add(225);
                    if (DataRepository.dataItemFeature().lh() && cameraCapabilities.isSupportRaw()) {
                        supportedConfigs.add(237);
                    }
                    if (c.hk()) {
                        supportedConfigs.add(262);
                    }
                    supportedConfigs.add(219);
                    if (c.zn()) {
                        supportedConfigs.add(229);
                    }
                    if (c.Rn()) {
                        supportedConfigs.add(199);
                    }
                    supportedConfigs.add(258);
                    if (dataItemFeature.lm()) {
                        supportedConfigs.add(170);
                    }
                } else if (i == 169) {
                    supportedConfigs.add(208);
                    supportedConfigs.add(225);
                    if (cameraCapabilities.isSupportedVideoMiMovie()) {
                        supportedConfigs.add(251);
                    }
                    supportedConfigs.add(219);
                    if (i2 == 0 && c.zn()) {
                        supportedConfigs.add(229);
                    }
                } else if (i == 180) {
                    supportedConfigs.add(208);
                    supportedConfigs.add(225);
                    if (cameraCapabilities.isSupportedVideoLogFormat()) {
                        supportedConfigs.add(260);
                    }
                    if (cameraCapabilities.isSupportedVideoMiMovie()) {
                        supportedConfigs.add(251);
                    }
                    supportedConfigs.add(219);
                    if (cameraCapabilities.isSupportHistogram()) {
                        supportedConfigs.add(261);
                    }
                    if (c.zn()) {
                        supportedConfigs.add(229);
                    }
                    supportedConfigs.add(199);
                    supportedConfigs.add(258);
                } else if (i == 183) {
                    supportedConfigs.add(187);
                    supportedConfigs.add(225);
                    supportedConfigs.add(219);
                } else if (i == 184) {
                    if (dataItemConfig.supportRatio()) {
                        supportedConfigs.add(210);
                    }
                    supportedConfigs.add(225);
                    if (c.hk() && DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiPhoto()) {
                        supportedConfigs.add(262);
                    }
                    supportedConfigs.add(219);
                    if (i2 == 0 && c.zn()) {
                        supportedConfigs.add(229);
                    }
                } else if (i == 204) {
                    supportedConfigs.add(225);
                    supportedConfigs.add(219);
                } else if (i == 205) {
                    supportedConfigs.add(226);
                    supportedConfigs.add(225);
                    supportedConfigs.add(219);
                    if (i2 == 0 && c.zn()) {
                        supportedConfigs.add(229);
                    }
                    if (dataItemRunning.supportHandGesture()) {
                        supportedConfigs.add(252);
                    }
                    if (c.hk()) {
                        supportedConfigs.add(262);
                    }
                    if (i2 == 0 && c.Xn()) {
                        supportedConfigs.add(228);
                    }
                } else if (i == 209) {
                    supportedConfigs.add(225);
                    supportedConfigs.add(219);
                } else if (i != 210) {
                    switch (i) {
                        case 171:
                            if (dataItemConfig.supportRatio()) {
                                supportedConfigs.add(210);
                            }
                            supportedConfigs.add(226);
                            supportedConfigs.add(225);
                            if (dataItemFeature.isCinematicPhotoSupported()) {
                                supportedConfigs.add(createConfigItem(251));
                            }
                            if (dataItemRunning.supportHandGesture()) {
                                supportedConfigs.add(252);
                            }
                            if (c.hk()) {
                                supportedConfigs.add(262);
                            }
                            supportedConfigs.add(219);
                            if (i2 == 0 && c.zn()) {
                                supportedConfigs.add(229);
                                break;
                            }
                        case 172:
                            if (dataItemConfig.getComponentConfigSlowMotion().getItems().size() > 1) {
                                supportedConfigs.add(204);
                            }
                            if (dataItemConfig.getComponentConfigSlowMotionQuality().getItems().size() > 1) {
                                supportedConfigs.add(213);
                            }
                            supportedConfigs.add(225);
                            supportedConfigs.add(219);
                            if (i2 == 0 && c.zn()) {
                                supportedConfigs.add(229);
                            }
                            if (dataItemFeature.Qj() && i2 == 0) {
                                supportedConfigs.add(createConfigItem(255));
                                break;
                            }
                        case 173:
                            if (dataItemConfig.supportRatio()) {
                                supportedConfigs.add(210);
                            }
                            supportedConfigs.add(225);
                            if (dataItemFeature.isCinematicPhotoSupported()) {
                                supportedConfigs.add(createConfigItem(251));
                            }
                            if (c.hk()) {
                                supportedConfigs.add(262);
                            }
                            supportedConfigs.add(219);
                            if (i2 == 0 && c.zn()) {
                                supportedConfigs.add(229);
                                break;
                            }
                        case 174:
                            supportedConfigs.add(225);
                            supportedConfigs.add(219);
                            break;
                        case 175:
                            supportedConfigs.add(226).add(225);
                            if (c.hk()) {
                                supportedConfigs.add(262);
                            }
                            supportedConfigs.add(219);
                            if (i2 == 0 && c.zn()) {
                                supportedConfigs.add(229);
                                break;
                            }
                        case 176:
                            break;
                        case 177:
                            if (dataItemConfig.supportRatio()) {
                                supportedConfigs.add(210);
                            }
                            supportedConfigs.add(225);
                            if (c.hk()) {
                                supportedConfigs.add(262);
                            }
                            supportedConfigs.add(219);
                            if (i2 == 0 && c.zn()) {
                                supportedConfigs.add(229);
                                break;
                            }
                        default:
                            switch (i) {
                                case 186:
                                    if (dataItemConfig.supportRatio()) {
                                        supportedConfigs.add(210);
                                    }
                                    supportedConfigs.add(225);
                                    supportedConfigs.add(219);
                                    if (i2 == 0 && c.zn()) {
                                        supportedConfigs.add(229);
                                    }
                                    if (z && dataItemRunning.supportMacroMode(i2, i)) {
                                        supportedConfigs.add(255);
                                        break;
                                    }
                                case 187:
                                    supportedConfigs.add(226);
                                    supportedConfigs.add(225);
                                    if (c.hk()) {
                                        supportedConfigs.add(262);
                                    }
                                    supportedConfigs.add(219);
                                    if (i2 == 0 && c.zn()) {
                                        supportedConfigs.add(229);
                                        break;
                                    }
                                case 188:
                                    if (dataItemConfig.supportRatio()) {
                                        supportedConfigs.add(210);
                                    }
                                    supportedConfigs.add(226);
                                    supportedConfigs.add(225);
                                    if (c.hk()) {
                                        supportedConfigs.add(262);
                                    }
                                    supportedConfigs.add(219);
                                    if (i2 == 0 && c.zn()) {
                                        supportedConfigs.add(229);
                                        break;
                                    }
                                default:
                                    if (z) {
                                        if (dataItemConfig.supportRatio()) {
                                            supportedConfigs.add(210);
                                        }
                                        supportedConfigs.add(226);
                                        supportedConfigs.add(225);
                                        if (dataItemFeature.isCinematicPhotoSupported()) {
                                            supportedConfigs.add(createConfigItem(251));
                                        }
                                        if (dataItemRunning.supportHandGesture()) {
                                            supportedConfigs.add(252);
                                        }
                                        if (c.hk()) {
                                            supportedConfigs.add(262);
                                        }
                                        supportedConfigs.add(219);
                                        if (i2 == 0 && c.zn()) {
                                            supportedConfigs.add(229);
                                        }
                                        if (dataItemRunning.supportSuperMacroMode()) {
                                            if (dataItemFeature.Lj()) {
                                                supportedConfigs.add(206);
                                            }
                                        } else if (dataItemRunning.supportMacroMode(i2, i)) {
                                            supportedConfigs.add(255);
                                        }
                                        if (i2 == 0) {
                                            if (c.Xn()) {
                                                supportedConfigs.add(228);
                                            }
                                            if (c.Dn()) {
                                                supportedConfigs.add(234);
                                            }
                                        }
                                        if (dataItemFeature.supportColorEnhance() && cameraCapabilities.isSupportedColorEnhance() && i2 == 0) {
                                            supportedConfigs.add(227);
                                        }
                                        if (dataItemFeature.lm()) {
                                            supportedConfigs.add(170);
                                            break;
                                        }
                                    } else {
                                        if (dataItemConfig.supportRatio()) {
                                            supportedConfigs.add(210);
                                        }
                                        supportedConfigs.add(226);
                                        supportedConfigs.add(225);
                                        break;
                                    }
                                    break;
                            }
                            break;
                    }
                } else {
                    supportedConfigs.add(225);
                    if (c.hk()) {
                        supportedConfigs.add(262);
                    }
                    supportedConfigs.add(219);
                }
            }
            supportedConfigs.add(225);
            supportedConfigs.add(219);
        } else {
            supportedConfigs.add(208);
            supportedConfigs.add(225);
            if (i2 == 0) {
                if (cameraCapabilities.isSupportedVideoMiMovie()) {
                    supportedConfigs.add(251);
                }
                supportedConfigs.add(219);
                if (c.zn()) {
                    supportedConfigs.add(229);
                }
                if (dataItemRunning.supportMacroMode(i2, i)) {
                    supportedConfigs.add(255);
                }
                if (z && dataItemFeature.fj()) {
                    supportedConfigs.add(253);
                }
            } else if (i2 == 1) {
                if (cameraCapabilities.isSupportedVideoMiMovie()) {
                    supportedConfigs.add(251);
                }
                supportedConfigs.add(219);
            }
            if (dataItemFeature.jk() && z) {
                supportedConfigs.add(220);
            }
        }
        return supportedConfigs;
    }

    public static final SupportedConfigs getSupportedTopConfigs(int i, int i2, boolean z) {
        CameraCapabilities capabilitiesByBogusCameraId = Camera2DataContainer.getInstance().getCapabilitiesByBogusCameraId(i2, i);
        if (capabilitiesByBogusCameraId == null) {
            return null;
        }
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        b dataItemFeature = DataRepository.dataItemFeature();
        ArrayList arrayList = new ArrayList();
        if (dataItemConfig.supportFlash()) {
            arrayList.add(createConfigItem(193));
        }
        if (i == 161) {
            if (z && i2 != 0 && dataItemFeature.pm()) {
                arrayList.add(createConfigItem(243));
            }
            if (dataItemRunning.supportTopShineEntry()) {
                arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem(), 17));
            }
            arrayList.add(createConfigItem(197));
        } else if (i != 162) {
            if (i != 166) {
                if (i == 167) {
                    arrayList.add(createConfigItem(214));
                    if (z && dataItemRunning.supportUltraPixel()) {
                        arrayList.add(createConfigItem(209));
                    }
                    if (dataItemRunning.supportTopShineEntry()) {
                        arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                    }
                    arrayList.add(createConfigItem(197));
                } else if (i == 169) {
                    if (dataItemRunning.supportTopShineEntry()) {
                        arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                    }
                    arrayList.add(createConfigItem(197));
                } else if (i != 254) {
                    if (i != 179) {
                        if (i == 180) {
                            if (DataRepository.dataItemFeature().cj()) {
                                arrayList.add(createConfigItem(168));
                            }
                            arrayList.add(createConfigItem(214));
                            if (z && i2 == 0 && ComponentConfigVideoQuality.is8KCamcorderSupported(0)) {
                                arrayList.add(createConfigItem(256));
                            }
                            ComponentManuallyFocus manuallyFocus = dataItemConfig.getManuallyFocus();
                            if (c.Rn() && !manuallyFocus.getComponentValue(i).equals(manuallyFocus.getDefaultValue(i))) {
                                capabilitiesByBogusCameraId.isAFRegionSupported();
                            }
                            if (dataItemRunning.supportTopShineEntry()) {
                                arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                            }
                            arrayList.add(createConfigItem(197));
                        } else if (i != 204) {
                            if (i != 205) {
                                if (!(i == 209 || i == 210)) {
                                    switch (i) {
                                        case 171:
                                            if (dataItemFeature.isSupportUltraWide() && CameraSettings.isSupportedOpticalZoom() && i2 == 0 && z && dataItemFeature.rk()) {
                                                arrayList.add(createConfigItem(207));
                                            }
                                            if (dataItemConfig.supportAi()) {
                                                arrayList.add(createConfigItem(201, 17));
                                            }
                                            if (dataItemRunning.supportTopShineEntry()) {
                                                arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                                            }
                                            arrayList.add(createConfigItem(197));
                                            break;
                                        case 172:
                                            arrayList.add(createConfigItem(197));
                                            break;
                                        case 173:
                                            arrayList.add(createConfigItem(197));
                                            break;
                                        case 174:
                                            if (z && i2 != 0 && dataItemFeature.pm()) {
                                                arrayList.add(createConfigItem(243));
                                            }
                                            arrayList.add(createConfigItem(245, 17));
                                            arrayList.add(createConfigItem(197));
                                            break;
                                        case 175:
                                            if (DataRepository.dataItemFeature().hl()) {
                                                arrayList.add(createConfigItem(171));
                                            }
                                            if (dataItemRunning.supportTopShineEntry()) {
                                                arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                                            }
                                            arrayList.add(createConfigItem(197));
                                            break;
                                        case 176:
                                            break;
                                        case 177:
                                            arrayList.add(createConfigItem(197));
                                            break;
                                        default:
                                            switch (i) {
                                                case 182:
                                                    break;
                                                case 183:
                                                    arrayList.add(createConfigItem(245, 17));
                                                    arrayList.add(createConfigItem(197));
                                                    break;
                                                case 184:
                                                    if (DataRepository.dataItemFeature().Al()) {
                                                        arrayList.add(createConfigItem(162, 17));
                                                    }
                                                    arrayList.add(createConfigItem(197));
                                                    break;
                                                case 185:
                                                    arrayList.add(createConfigItem(164));
                                                    arrayList.add(createConfigItem(217));
                                                    break;
                                                case 186:
                                                    if (DataRepository.dataItemFeature().Gj()) {
                                                        arrayList.add(createConfigItem(166));
                                                    }
                                                    arrayList.add(createConfigItem(197));
                                                    break;
                                                case 187:
                                                    arrayList.clear();
                                                    if (dataItemRunning.supportTopShineEntry()) {
                                                        arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem(), 17));
                                                    }
                                                    arrayList.add(createConfigItem(197));
                                                    break;
                                                case 188:
                                                    break;
                                                default:
                                                    if (z) {
                                                        if (dataItemConfig.supportHdr()) {
                                                            arrayList.add(createConfigItem(194));
                                                        }
                                                        if (dataItemConfig.supportAi()) {
                                                            arrayList.add(createConfigItem(201));
                                                        }
                                                        if (dataItemRunning.supportSuperMacroMode()) {
                                                            arrayList.add(createConfigItem(255));
                                                        } else if (dataItemFeature.Lj()) {
                                                            arrayList.add(createConfigItem(206));
                                                        }
                                                        if (dataItemRunning.supportTopShineEntry()) {
                                                            arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                                                        }
                                                        if (CameraSettings.checkLensAvailability(CameraAppImpl.getAndroidContext()) && i2 == 0) {
                                                            arrayList.add(createConfigItem(242));
                                                        }
                                                        if (dataItemRunning.supportUltraPixelPortrait() && capabilitiesByBogusCameraId.isSupportedSuperPortrait()) {
                                                            arrayList.add(createConfigItem(215));
                                                        }
                                                        if (dataItemConfig.supportBokeh()) {
                                                            arrayList.add(createConfigItem(200));
                                                        }
                                                        arrayList.add(createConfigItem(197));
                                                        break;
                                                    } else {
                                                        if (dataItemRunning.supportTopShineEntry()) {
                                                            arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                                                        }
                                                        arrayList.add(createConfigItem(197));
                                                        break;
                                                    }
                                            }
                                    }
                                }
                                arrayList.clear();
                                arrayList.add(createConfigItem(197));
                            } else {
                                if (dataItemConfig.supportHdr()) {
                                    arrayList.add(createConfigItem(194));
                                }
                                if (dataItemConfig.supportAi()) {
                                    arrayList.add(createConfigItem(201));
                                }
                                if (dataItemRunning.supportTopShineEntry()) {
                                    arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem()));
                                }
                                arrayList.add(createConfigItem(197));
                            }
                        } else if (dataItemRunning.getComponentRunningDualVideo().ismShouldDraw6Patch()) {
                            arrayList.add(createConfigItem(197));
                        } else if (DataRepository.dataItemFeature().tj()) {
                            arrayList.add(createConfigItem(217));
                        } else {
                            arrayList.add(createConfigItem(197));
                        }
                    }
                    arrayList.add(createConfigItem(217));
                } else {
                    if (arrayList.isEmpty()) {
                        arrayList.add(new TopConfigItem(176));
                    } else {
                        arrayList.set(0, new TopConfigItem(176));
                    }
                    return TopViewPositionArray.fillNotUseViewPosition(arrayList);
                }
            }
            arrayList.clear();
            arrayList.add(createConfigItem(197));
        } else if (i2 == 0) {
            if (DataRepository.dataItemFeature().cj()) {
                arrayList.add(createConfigItem(168));
            }
            if (z && dataItemFeature.kk()) {
                if (dataItemFeature.lk()) {
                    arrayList.add(createConfigItem(165));
                } else {
                    arrayList.add(createConfigItem(218));
                }
            }
            if (z && ComponentConfigVideoQuality.is8KCamcorderSupported(0)) {
                arrayList.add(createConfigItem(256));
            }
            if (!z) {
                if (dataItemRunning.supportTopShineEntry()) {
                    arrayList.add(createConfigItem(212, 17));
                }
                arrayList.add(createConfigItem(225));
            } else {
                if (dataItemConfig.supportHdr()) {
                    arrayList.add(createConfigItem(194));
                }
                if (dataItemRunning.supportTopShineEntry()) {
                    arrayList.add(createConfigItem(212, 17));
                }
                arrayList.add(createConfigItem(197));
            }
        } else {
            if (dataItemConfig.supportHdr() && capabilitiesByBogusCameraId.isSupportVideoHdr()) {
                arrayList.add(createConfigItem(194));
            }
            if (dataItemRunning.supportTopShineEntry()) {
                arrayList.add(createConfigItem(dataItemRunning.getComponentRunningShine().getTopConfigItem(), 17));
            }
            if (z && i == 162 && dataItemFeature.pm()) {
                arrayList.add(createConfigItem(243));
            }
            if (!z) {
                arrayList.add(createConfigItem(225));
            } else {
                arrayList.add(createConfigItem(197));
            }
        }
        return TopViewPositionArray.fillNotUseViewPosition(arrayList);
    }

    public static boolean isMutexConfig(int i) {
        for (int i2 : MUTEX_MENU_CONFIGS) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }
}
