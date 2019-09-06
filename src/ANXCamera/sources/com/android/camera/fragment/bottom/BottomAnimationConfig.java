package com.android.camera.fragment.bottom;

import com.android.camera.constant.DurationConstant;

public class BottomAnimationConfig {
    public int mCurrentMode;
    public int mDuration;
    public boolean mIsFPS960;
    public boolean mIsInMimojiCreate;
    public boolean mIsPostProcessing;
    public boolean mIsRecordingCircle;
    public boolean mIsRoundingCircle;
    public boolean mIsStart;
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
        BottomAnimationConfig bottomAnimationConfig = new BottomAnimationConfig(z, i, z2, z3, z4);
        return bottomAnimationConfig;
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
                if (i2 == 161 || i2 == 177) {
                    this.mDuration = DurationConstant.DURATION_VIDEO_RECORDING_FUN;
                } else if (i2 == 174) {
                    this.mDuration = DurationConstant.DURATION_LIVE_RECORD;
                } else {
                    this.mDuration = 10000;
                }
            } else {
                this.mDuration = 30000;
            }
        }
        int i3 = this.mCurrentMode;
        boolean z = true;
        this.mShouldRepeat = (i3 == 161 || i3 == 177 || (i3 == 162 && this.mIsVideoBokeh) || this.mCurrentMode == 173 || this.mIsFPS960) ? false : true;
        this.mIsRecordingCircle = this.mCurrentMode == 173;
        this.mIsRoundingCircle = this.mCurrentMode == 177;
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
}
