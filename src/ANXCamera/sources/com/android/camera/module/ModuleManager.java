package com.android.camera.module;

import androidx.annotation.Nullable;
import com.android.camera.CameraSettings;
import com.android.camera.constant.ModeConstant;
import com.android.camera.features.mimoji2.module.MimojiModule;

public class ModuleManager {
    public static final int MODULE_INDEX_NONE = -1;
    private static int sCurrentModuleIndex = -1;
    private static ModuleManager sInstance = new ModuleManager();

    private static Module createModuleByAlias(String str) {
        try {
            return (Module) Class.forName(str).newInstance();
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (InstantiationException e4) {
            e4.printStackTrace();
        }
        return null;
    }

    public static int getActiveModuleIndex() {
        return sCurrentModuleIndex;
    }

    private static Module getCameraByDevice() {
        return new Camera2Module();
    }

    @Nullable
    public static Module getModuleByIndex(int i) {
        if (i != 169) {
            if (i == 254) {
                return new FakerModule();
            }
            if (i == 204) {
                return new DualVideoModule();
            }
            if (i != 205) {
                if (i == 209 || i == 210) {
                    return new FunModule();
                }
                switch (i) {
                    case 161:
                        return new FunModule();
                    case 162:
                        break;
                    case 163:
                        break;
                    default:
                        switch (i) {
                            case 165:
                            case 167:
                                break;
                            case 166:
                                return new Panorama3Module();
                            default:
                                switch (i) {
                                    case 171:
                                    case 173:
                                    case 175:
                                        break;
                                    case 172:
                                        break;
                                    case 174:
                                    case 177:
                                        return new LiveModule();
                                    case 176:
                                        return new WideSelfieModule();
                                    default:
                                        switch (i) {
                                            case 179:
                                                return new LiveModuleSubVV();
                                            case 180:
                                                break;
                                            case 181:
                                                return createModuleByAlias(ModeConstant.MODE_FUN2_ALIAS);
                                            case 182:
                                            case 186:
                                                break;
                                            case 183:
                                                return new MiLiveModule();
                                            case 184:
                                                return new MimojiModule();
                                            case 185:
                                                return new CloneModule();
                                            case 187:
                                                return new AmbilightModule();
                                            case 188:
                                                return new SuperMoonModule();
                                            default:
                                                return null;
                                        }
                                }
                        }
                }
            }
            return getCameraByDevice();
        }
        return getVideoByDevice();
    }

    private static Module getVideoByDevice() {
        return new VideoModule();
    }

    public static ModuleManager instance() {
        return sInstance;
    }

    public static boolean isCameraModule() {
        return sCurrentModuleIndex == 163;
    }

    public static boolean isCapture() {
        return isCameraModule() || isSquareModule() || isProPhotoModule() || isSuperNightScene() || isUltraPixel();
    }

    public static boolean isDocumentMode() {
        return CameraSettings.isDocumentModeOn(sCurrentModuleIndex);
    }

    public static boolean isFastMotionModule() {
        return sCurrentModuleIndex == 169;
    }

    public static boolean isFunARModule() {
        int i = sCurrentModuleIndex;
        return i == 177 || i == 184;
    }

    public static boolean isFunModule() {
        return sCurrentModuleIndex == 161;
    }

    public static boolean isIDCardMode() {
        return sCurrentModuleIndex == 182;
    }

    public static boolean isInVideoCategory() {
        return isVideoCategory(sCurrentModuleIndex);
    }

    public static boolean isLandscapeMode() {
        int i = sCurrentModuleIndex;
        return i == 179 || i == 182 || i == 185;
    }

    public static boolean isLiveModule() {
        return sCurrentModuleIndex == 174;
    }

    public static boolean isMiLiveModule() {
        return sCurrentModuleIndex == 183;
    }

    public static boolean isPanoramaModule() {
        return sCurrentModuleIndex == 166;
    }

    public static boolean isPortraitModule() {
        return sCurrentModuleIndex == 171;
    }

    public static boolean isProModule() {
        return isProPhotoModule() || isProVideoModule();
    }

    public static boolean isProPhotoModule() {
        return sCurrentModuleIndex == 167;
    }

    public static boolean isProVideoModule() {
        return sCurrentModuleIndex == 180;
    }

    public static boolean isSquareModule() {
        return sCurrentModuleIndex == 165;
    }

    public static boolean isSuperMoonMode() {
        return sCurrentModuleIndex == 188;
    }

    public static boolean isSuperNightScene() {
        return sCurrentModuleIndex == 173;
    }

    public static boolean isSupportCropFrontMode() {
        return isCameraModule() || isSquareModule() || isPortraitModule();
    }

    public static boolean isUltraPixel() {
        return sCurrentModuleIndex == 175;
    }

    public static boolean isVideoCategory(int i) {
        return i == 161 || i == 162 || i == 169 || i == 172 || i == 174 || i == 180 || i == 183 || i == 204;
    }

    public static boolean isVideoModule() {
        return sCurrentModuleIndex == 162;
    }

    public static boolean isVideoNewSlowMotion() {
        return sCurrentModuleIndex == 172;
    }

    public static boolean isWideSelfieModule() {
        return sCurrentModuleIndex == 176;
    }

    public static void setActiveModuleIndex(int i) {
        sCurrentModuleIndex = i;
    }
}
