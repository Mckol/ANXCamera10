package com.android.camera.effect.renders;

import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.BasicTexture;
import com.android.gallery3d.ui.StringTexture;

public class NewStyleTextWaterMark extends WaterMark {
    private static final float RATIO = 0.87f;
    private static final String TAG = "NewStyleTextWaterMark";
    public static final int TEXT_COLOR = -1;
    public static final float TEXT_PIXEL_SIZE = 92.0f;
    private int mCenterX;
    private int mCenterY;
    private int mCharMargin;
    private boolean mIsCinematicAspectRatio;
    private int mPaddingX;
    private int mPaddingY;
    private int mWaterHeight;
    private String mWaterText;
    private BasicTexture mWaterTexture;
    private int mWaterWidth;

    public NewStyleTextWaterMark(String str, int i, int i2, int i3, boolean z) {
        super(i, i2, i3);
        this.mIsCinematicAspectRatio = z;
        float watermarkRatio = Util.getWatermarkRatio(i, i2);
        float textSize = getTextSize() * watermarkRatio;
        this.mPaddingX = Math.round(Util.getWaterMarkPaddingX() * watermarkRatio) & -2;
        this.mPaddingY = Math.round(Util.getWaterMarkPaddingY() * watermarkRatio) & -2;
        if (this.mIsCinematicAspectRatio) {
            textSize *= Util.getCinematicAspectWaterMarkRatio();
            this.mPaddingX = (int) (((float) this.mPaddingX) * Util.getCinematicAspectWaterMarkRatio());
            this.mPaddingY = (int) (((float) this.mPaddingY) * Util.getCinematicAspectWaterMarkRatio());
            if ((i3 == 90 || i3 == 270) && i > i2) {
                i2 = i;
                i = i2;
            }
            int watermarkCinematicAspectMargin = Util.getWatermarkCinematicAspectMargin(i, i2);
            if (i < i2) {
                this.mPaddingX += watermarkCinematicAspectMargin;
            } else {
                this.mPaddingY += watermarkCinematicAspectMargin;
            }
        }
        Log.d(TAG, "ImageWaterMark: textSize = " + textSize + ", mPaddingX = " + this.mPaddingX + ", mPaddingY = " + this.mPaddingY);
        this.mWaterText = str;
        this.mWaterTexture = StringTexture.newInstance(this.mWaterText, textSize, -1, 2);
        this.mWaterWidth = this.mWaterTexture.getWidth();
        this.mWaterHeight = this.mWaterTexture.getHeight();
        this.mCharMargin = (int) ((((float) this.mWaterHeight) * (1.0f - getTextMargin())) / 2.0f);
        if (!DataRepository.dataItemFeature().lj()) {
            if (i3 == 90 || i3 == 270) {
                this.mPaddingX = (this.mPaddingX - this.mCharMargin) & -2;
            } else {
                this.mPaddingY = (this.mPaddingY - this.mCharMargin) & -2;
            }
        }
        Log.d(TAG, "ImageWaterMark: after mWaterWidth = " + this.mWaterWidth + ", mWaterHeight = " + this.mWaterHeight + ", mCharMargin = " + this.mCharMargin + ", mPaddingX = " + this.mPaddingX + ", mPaddingY = " + this.mPaddingY);
        calcCenterAxis();
        if (Util.sIsDumpLog) {
            print();
        }
    }

    private void calcCenterAxis() {
        int i = this.mOrientation;
        if (i == 0) {
            this.mCenterX = (this.mPictureWidth - this.mPaddingX) - (this.mWaterWidth / 2);
            this.mCenterY = (this.mPictureHeight - this.mPaddingY) - (this.mWaterHeight / 2);
        } else if (i == 90) {
            this.mCenterX = (this.mPictureWidth - this.mPaddingY) - (this.mWaterHeight / 2);
            this.mCenterY = this.mPaddingX + (this.mWaterWidth / 2);
        } else if (i == 180) {
            this.mCenterX = this.mPaddingX + (this.mWaterWidth / 2);
            this.mCenterY = this.mPaddingY + (this.mWaterHeight / 2);
        } else if (i == 270) {
            this.mCenterX = this.mPaddingY + (this.mWaterHeight / 2);
            this.mCenterY = (this.mPictureHeight - this.mPaddingX) - (this.mWaterWidth / 2);
        }
    }

    private float getTextMargin() {
        if (DataRepository.dataItemFeature().lj()) {
            return 0.6f;
        }
        return RATIO;
    }

    private float getTextSize() {
        return DataRepository.dataItemFeature().lj() ? 76.0f : 92.0f;
    }

    private void print() {
        String str = TAG;
        Log.v(str, "WaterMark pictureWidth=" + this.mPictureWidth + " pictureHeight =" + this.mPictureHeight + " waterText=" + this.mWaterText + " centerX=" + this.mCenterX + " centerY=" + this.mCenterY + " waterWidth=" + this.mWaterWidth + " waterHeight=" + this.mWaterHeight + " paddingX=" + this.mPaddingX + " paddingY=" + this.mPaddingY);
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public int getCenterX() {
        return this.mCenterX;
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public int getCenterY() {
        return this.mCenterY;
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public int getHeight() {
        return this.mWaterHeight;
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public /* bridge */ /* synthetic */ int getLeft() {
        return super.getLeft();
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public int getPaddingX() {
        return this.mPaddingX;
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public int getPaddingY() {
        return this.mPaddingY;
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public BasicTexture getTexture() {
        return this.mWaterTexture;
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public /* bridge */ /* synthetic */ int getTop() {
        return super.getTop();
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public int getWidth() {
        return this.mWaterWidth;
    }
}
