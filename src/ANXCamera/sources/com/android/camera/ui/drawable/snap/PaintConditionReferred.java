package com.android.camera.ui.drawable.snap;

import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;

public class PaintConditionReferred {
    public boolean forceVideoPattern;
    public boolean isFPS960;
    public boolean needFocusBack;
    public boolean needSnapButtonAnimation;
    public float targetFrameRatio;
    public int targetMode;

    public PaintConditionReferred(int i) {
        this.targetMode = i;
        configVariables();
    }

    public PaintConditionReferred(int i, String str, boolean z) {
    }

    public static PaintConditionReferred create(int i) {
        return new PaintConditionReferred(i);
    }

    public static PaintConditionReferred createModeChange(int i, boolean z, boolean z2) {
        return new PaintConditionReferred(i).setNeedSnapButtonAnimation(true).setIsFPS960(z).setNeedSnapButtonAnimation(z2);
    }

    public boolean bottomHalfScreen() {
        float f = this.targetFrameRatio;
        return f == 1.3333333f || f == 1.0f;
    }

    public void configVariables() {
        int i = this.targetMode;
        if (i != 161) {
            if (i != 162) {
                if (!(i == 168 || i == 169 || i == 172 || i == 174 || i == 204)) {
                    if (i == 254) {
                        int uiStyle = DataRepository.dataItemRunning().getUiStyle();
                        if (uiStyle == 1) {
                            this.targetFrameRatio = 1.7777777f;
                            return;
                        } else if (uiStyle != 3) {
                            this.targetFrameRatio = 1.3333333f;
                            return;
                        } else {
                            this.targetFrameRatio = 2.2222223f;
                            return;
                        }
                    } else if (!(i == 179 || i == 180 || i == 209 || i == 210)) {
                        switch (i) {
                            case 183:
                            case 185:
                                break;
                            case 184:
                                if (CameraSettings.isGifOn()) {
                                    this.targetFrameRatio = 1.0f;
                                } else {
                                    this.targetFrameRatio = Util.getRatio(DataRepository.dataItemConfig().getComponentConfigRatio().getPictureSizeRatioString(this.targetMode));
                                }
                                MimojiStatusManager2 mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
                                if (mimojiStatusManager2.getMimojiRecordState() == 1 && !mimojiStatusManager2.isInMimojiCreate()) {
                                    this.forceVideoPattern = true;
                                    return;
                                }
                                return;
                            default:
                                this.targetFrameRatio = Util.getRatio(DataRepository.dataItemConfig().getComponentConfigRatio().getPictureSizeRatioString(this.targetMode));
                                return;
                        }
                    }
                }
            } else if (!DataRepository.dataItemGlobal().isIntentAction()) {
                this.targetFrameRatio = 1.7777777f;
                return;
            } else if (DataRepository.dataItemRunning().getUiStyle() != 0) {
                this.targetFrameRatio = 1.7777777f;
                return;
            } else {
                this.targetFrameRatio = 1.3333333f;
                return;
            }
        }
        this.targetFrameRatio = 1.7777777f;
    }

    public int getBottomMaskTargetHeight(int i) {
        if (i == 165 || this.targetFrameRatio == 1.0f) {
            return Util.getBottomHeight() + Util.getSquareBottomCoverHeight();
        } else if (bottomHalfScreen()) {
            return Util.getBottomHeight();
        } else {
            if (this.targetFrameRatio == 1.7777777f) {
                return Util.sBottomMargin;
            }
            return 0;
        }
    }

    public int getTopMaskTargetHeight(int i) {
        if (i != 165) {
            float f = this.targetFrameRatio;
            if (f != 1.0f) {
                if (f <= 1.7777777f) {
                    return Util.sTopMargin + Util.sTopBarHeight;
                }
                return 0;
            }
        }
        int bottomHeight = Util.sWindowHeight - Util.getBottomHeight();
        return (bottomHeight - Util.sWindowWidth) - Util.getSquareBottomCoverHeight();
    }

    public PaintConditionReferred setIsFPS960(boolean z) {
        this.isFPS960 = z;
        return this;
    }

    public PaintConditionReferred setNeedSnapButtonAnimation(boolean z) {
        this.needSnapButtonAnimation = z;
        return this;
    }

    public PaintConditionReferred setTargetFrameRatio(float f) {
        this.targetFrameRatio = f;
        return this;
    }
}
