package com.android.camera.effect.renders;

import com.android.gallery3d.ui.BasicTexture;

/* access modifiers changed from: package-private */
public abstract class WaterMark {
    protected int mOrientation;
    protected int mPictureHeight;
    protected int mPictureWidth;

    public WaterMark(int i, int i2, int i3) {
        this.mPictureWidth = i;
        this.mPictureHeight = i2;
        this.mOrientation = i3;
    }

    public abstract int getCenterX();

    public abstract int getCenterY();

    public abstract int getHeight();

    public int getLeft() {
        return getCenterX() - (getWidth() / 2);
    }

    public abstract int getPaddingX();

    public abstract int getPaddingY();

    public abstract BasicTexture getTexture();

    public int getTop() {
        return getCenterY() - (getHeight() / 2);
    }

    public abstract int getWidth();
}
