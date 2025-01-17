package com.android.camera.fragment.bottom;

import com.android.camera.CameraSettings;
import com.android.camera.constant.DurationConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.timerburst.TimerBurstController;
import miui.text.ExtraTextUtils;

public class BottomAnimationConfig {
    public int mCurrentMode;
    public int mDuration;
    public boolean mIsFPS960;
    public boolean mIsInMimojiCreate;
    public boolean mIsLongExpose = isLongExpose();
    public boolean mIsPostProcessing;
    public boolean mIsRecordingCircle;
    public boolean mIsRoundingCircle;
    public boolean mIsStart;
    public boolean mIsTimerBurstCircle;
    public boolean mIsVideoBokeh;
    public boolean mNeedFinishRecord;
    public boolean mShouldRepeat;

    private BottomAnimationConfig(boolean z, int i, boolean z2, boolean z3, boolean z4) {
        this.mIsPostProcessing = z;
        this.mCurrentMode = i;
        this.mIsStart = z2;
        this.mIsFPS960 = z3;
        this.mIsVideoBokeh = z4;
    }

    public static BottomAnimationConfig generate(boolean z, int i, boolean z2, boolean z3, boolean z4) {
        return new BottomAnimationConfig(z, i, z2, z3, z4);
    }

    private boolean isLongExpose() {
        if (this.mCurrentMode != 167) {
            return false;
        }
        return DataRepository.dataItemConfig().getmComponentManuallyET().isLongExpose(this.mCurrentMode);
    }

    public BottomAnimationConfig configVariables() {
        if (this.mIsFPS960) {
            this.mDuration = 2000;
        } else {
            int i = this.mCurrentMode;
            if (i == 173) {
                this.mDuration = 2000;
            } else if (i != 162 || !this.mIsVideoBokeh) {
                int i2 = this.mCurrentMode;
                if (i2 == 161) {
                    this.mDuration = 15000;
                    ModeProtocol.LiveSpeedChanges liveSpeedChanges = (ModeProtocol.LiveSpeedChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(201);
                    if (liveSpeedChanges != null) {
                        this.mDuration = (int) (((float) this.mDuration) / liveSpeedChanges.getRecordSpeed());
                    }
                } else if (i2 == 184) {
                    if (CameraSettings.isGifOn()) {
                        this.mDuration = 5000;
                    } else {
                        this.mDuration = 15000;
                    }
                } else if (i2 == 174) {
                    this.mDuration = DurationConstant.DURATION_LIVE_RECORD;
                } else if (i2 == 183) {
                    this.mDuration = 15000;
                } else if (i2 == 163 || i2 == 177) {
                    this.mDuration = 15000;
                } else if (i2 == 187) {
                    this.mDuration = 2000;
                } else if (i2 != 167 || !this.mIsLongExpose) {
                    this.mDuration = 10000;
                } else {
                    this.mDuration = (int) (Long.parseLong(DataRepository.dataItemConfig().getmComponentManuallyET().getComponentValue(this.mCurrentMode)) / ExtraTextUtils.MB);
                }
            } else {
                this.mDuration = DurationConstant.DURATION_VIDEO_BOKEH_RECORDING;
            }
        }
        int i3 = this.mCurrentMode;
        boolean z = true;
        this.mShouldRepeat = (i3 == 163 || i3 == 161 || i3 == 177 || i3 == 184 || (i3 == 162 && this.mIsVideoBokeh) || this.mCurrentMode == 173 || this.mIsFPS960) ? false : true;
        this.mIsRecordingCircle = this.mCurrentMode == 173;
        this.mIsTimerBurstCircle = CameraSettings.isTimerBurstEnable() && TimerBurstController.isSupportTimerBurst(this.mCurrentMode) && DataRepository.dataItemLive().getTimerBurstController().isInTimerBurstShotting();
        if (this.mIsTimerBurstCircle || this.mIsLongExpose) {
            this.mShouldRepeat = false;
        }
        if ((this.mCurrentMode != 173 && !this.mIsFPS960) || this.mIsPostProcessing) {
            z = false;
        }
        this.mNeedFinishRecord = z;
        return this;
    }

    public void setSpecifiedDuration(int i) {
        this.mDuration = i;
        this.mShouldRepeat = false;
    }

    public void setSpecifiedDuration(int i, boolean z, boolean z2) {
        this.mDuration = i;
        this.mShouldRepeat = z;
        this.mIsRecordingCircle = z2;
    }
}
