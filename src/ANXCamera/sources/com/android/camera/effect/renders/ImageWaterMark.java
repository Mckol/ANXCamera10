package com.android.camera.effect.renders;

import android.graphics.Bitmap;
import android.util.Log;
import com.android.camera.Util;
import com.android.gallery3d.ui.BasicTexture;
import com.android.gallery3d.ui.BitmapTexture;
import java.util.Arrays;

public class ImageWaterMark extends WaterMark {
    private static final String TAG = "ImageWaterMark";
    private int mCenterX;
    private int mCenterY;
    private int mHeight;
    private BitmapTexture mImageTexture;
    private boolean mIsCinematicAspectRatio;
    private int mPaddingX;
    private int mPaddingY;
    private int mWidth;

    public ImageWaterMark(Bitmap bitmap, int i, int i2, int i3, float f, float f2, float f3, boolean z) {
        super(i, i2, i3);
        this.mIsCinematicAspectRatio = z;
        int[] calcDualCameraWatermarkLocationByCinema = this.mIsCinematicAspectRatio ? Util.calcDualCameraWatermarkLocationByCinema(i, i2, bitmap.getWidth(), bitmap.getHeight(), i3) : Util.calcDualCameraWatermarkLocation(i, i2, bitmap.getWidth(), bitmap.getHeight());
        Log.d(TAG, "ImageWaterMark: " + Arrays.toString(calcDualCameraWatermarkLocationByCinema));
        this.mWidth = calcDualCameraWatermarkLocationByCinema[0];
        this.mHeight = calcDualCameraWatermarkLocationByCinema[1];
        this.mPaddingX = calcDualCameraWatermarkLocationByCinema[2];
        this.mPaddingY = calcDualCameraWatermarkLocationByCinema[3];
        this.mImageTexture = new BitmapTexture(bitmap);
        this.mImageTexture.setOpaque(false);
        calcCenterAxis();
    }

    private void calcCenterAxis() {
        int i = this.mOrientation;
        if (i == 0) {
            this.mCenterX = this.mPaddingX + (getWidth() / 2);
            this.mCenterY = (this.mPictureHeight - this.mPaddingY) - (getHeight() / 2);
        } else if (i == 90) {
            this.mCenterX = (this.mPictureWidth - this.mPaddingY) - (getHeight() / 2);
            this.mCenterY = (this.mPictureHeight - this.mPaddingX) - (getWidth() / 2);
        } else if (i == 180) {
            this.mCenterX = (this.mPictureWidth - this.mPaddingX) - (getWidth() / 2);
            this.mCenterY = this.mPaddingY + (getHeight() / 2);
        } else if (i == 270) {
            this.mCenterX = this.mPaddingY + (getHeight() / 2);
            this.mCenterY = this.mPaddingX + (getWidth() / 2);
        }
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
        return this.mHeight;
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
        return this.mImageTexture;
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public /* bridge */ /* synthetic */ int getTop() {
        return super.getTop();
    }

    @Override // com.android.camera.effect.renders.WaterMark
    public int getWidth() {
        return this.mWidth;
    }
}
