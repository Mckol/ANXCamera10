package com.android.camera.dualvideo.render;

import android.graphics.Rect;
import com.android.camera.Util;
import com.android.camera.log.Log;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.IIntValueProperty;

public class RegionHelper {
    private static final int MARGIN = Util.dpToPixel(14.5f);
    private static final int MARGIN_BOTTOM = Util.dpToPixel(14.5f);
    private static final float MINI_SCALE_X = 0.3333f;
    private static final float MINI_SCALE_Y = 0.3333f;
    private static final int SIX_PATCH_HEIGHT = Util.dpToPixel(171.636f);
    private static final int SIX_PATCH_SEPARATOR = Util.dpToPixel(4.364f);
    private static final int SIX_PATCH_WIDTH = Util.dpToPixel(194.182f);
    private static final String TAG = "RegionHelper";
    private AnimConfig mConfigX;
    private AnimConfig mConfigY;
    private int mHeight;
    public boolean mIsHovering = false;
    private UpdatedListener mListener;
    private int mMarginLeft;
    private int mMarginTop;
    private float mSpeedX;
    private float mSpeedY;
    public float mStartX = 0.0f;
    public float mStartY = 0.0f;
    private Rect mValidPreviewRect;
    private int mWidth;
    public int mX;
    public int mY;

    public interface UpdatedListener {
        void onUpdated();
    }

    public RegionHelper(int i, int i2, int i3, int i4) {
        int i5 = MARGIN;
        this.mMarginLeft = (int) ((((float) Util.getDisplayRect().right) * 0.6667f) - ((float) i5));
        this.mMarginTop = i5;
        this.mX = i;
        this.mY = i2;
        this.mWidth = i3;
        this.mHeight = i4;
        int i6 = this.mHeight;
        int i7 = this.mWidth;
        this.mValidPreviewRect = new Rect(i5, i5, (i6 - ((int) (((float) i6) * 0.3333f))) - i5, (i7 - ((int) (((float) i7) * 0.3333f))) - MARGIN_BOTTOM);
    }

    private void checkLocation() {
        int i = this.mMarginLeft;
        int i2 = this.mValidPreviewRect.left;
        if (i < i2) {
            this.mMarginLeft = i2;
        }
        int i3 = this.mMarginLeft;
        int i4 = this.mValidPreviewRect.right;
        if (i3 > i4) {
            this.mMarginLeft = i4;
        }
        int i5 = this.mMarginTop;
        int i6 = this.mValidPreviewRect.top;
        if (i5 < i6) {
            this.mMarginTop = i6;
        }
        int i7 = this.mMarginTop;
        int i8 = this.mValidPreviewRect.bottom;
        if (i7 > i8) {
            this.mMarginTop = i8;
        }
    }

    public void clearListener() {
        this.mListener = null;
    }

    public synchronized int[] getMargin() {
        return new int[]{this.mMarginLeft, this.mMarginTop};
    }

    public synchronized Rect getRenderAreaFor(int i) {
        int i2 = this.mHeight;
        switch (i) {
            case 10:
                return new Rect(this.mX + this.mMarginLeft, this.mY + this.mMarginTop, ((int) (((float) this.mHeight) * 0.3333f)) + this.mX + this.mMarginLeft, ((int) (((float) this.mWidth) * 0.3333f)) + this.mY + this.mMarginTop);
            case 11:
                return new Rect(this.mX, this.mY, this.mX + this.mHeight, this.mY + (this.mWidth / 2));
            case 12:
                return new Rect(this.mX, this.mY + (this.mWidth / 2), this.mX + this.mHeight, this.mY + this.mWidth);
            case 13:
                return new Rect(this.mX, this.mY, this.mHeight + this.mX, this.mWidth + this.mY);
            default:
                switch (i) {
                    case 20:
                        return new Rect(this.mX, this.mY, SIX_PATCH_WIDTH + this.mX, SIX_PATCH_HEIGHT + this.mY);
                    case 21:
                        return new Rect(this.mX + SIX_PATCH_WIDTH + SIX_PATCH_SEPARATOR, this.mY, i2 + this.mX, SIX_PATCH_HEIGHT + this.mY);
                    case 22:
                        return new Rect(this.mX, this.mY + SIX_PATCH_HEIGHT + SIX_PATCH_SEPARATOR, SIX_PATCH_WIDTH + this.mX, (SIX_PATCH_HEIGHT * 2) + SIX_PATCH_SEPARATOR + this.mY);
                    case 23:
                        return new Rect(this.mX + SIX_PATCH_WIDTH + SIX_PATCH_SEPARATOR, this.mY + SIX_PATCH_HEIGHT + SIX_PATCH_SEPARATOR, i2 + this.mX, (SIX_PATCH_HEIGHT * 2) + SIX_PATCH_SEPARATOR + this.mY);
                    case 24:
                        return new Rect(this.mX, this.mY + (SIX_PATCH_HEIGHT * 2) + (SIX_PATCH_SEPARATOR * 2), SIX_PATCH_WIDTH + this.mX, (SIX_PATCH_HEIGHT * 3) + (SIX_PATCH_SEPARATOR * 2) + this.mY);
                    case 25:
                        return new Rect(this.mX + SIX_PATCH_WIDTH + SIX_PATCH_SEPARATOR, this.mY + (SIX_PATCH_HEIGHT * 2) + (SIX_PATCH_SEPARATOR * 2), i2 + this.mX, (SIX_PATCH_HEIGHT * 3) + (SIX_PATCH_SEPARATOR * 2) + this.mY);
                    default:
                        return null;
                }
        }
    }

    public synchronized int[] getSize() {
        return new int[]{this.mWidth, this.mHeight};
    }

    public void moveToEdge() {
        String str = TAG;
        Log.d(str, "moveToEdge xspeed: " + this.mSpeedX + "yspeed: " + this.mSpeedY);
        float f = this.mSpeedX;
        int i = f > 2000.0f ? this.mValidPreviewRect.right : f > -2000.0f ? this.mMarginLeft < this.mValidPreviewRect.centerX() ? this.mValidPreviewRect.left : this.mValidPreviewRect.right : this.mValidPreviewRect.left;
        float f2 = this.mSpeedY;
        int i2 = f2 > 2000.0f ? this.mValidPreviewRect.bottom : f2 > -2000.0f ? this.mMarginTop < this.mValidPreviewRect.centerY() ? this.mValidPreviewRect.top : this.mValidPreviewRect.bottom : this.mValidPreviewRect.top;
        Folme.useValue("xspeed").to(Integer.valueOf(i), new AnimConfig().setEase(-2, 0.7f, 0.5f).addListeners(new TransitionListener() {
            /* class com.android.camera.dualvideo.render.RegionHelper.AnonymousClass3 */

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
                RegionHelper.this.mMarginLeft = i;
                RegionHelper.this.mListener.onUpdated();
            }
        }));
        Folme.useValue("yspeed").to(Integer.valueOf(i2), new AnimConfig().setEase(-2, 0.7f, 0.5f).addListeners(new TransitionListener() {
            /* class com.android.camera.dualvideo.render.RegionHelper.AnonymousClass4 */

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
                RegionHelper.this.mMarginTop = i;
                RegionHelper.this.mListener.onUpdated();
            }
        }));
    }

    public void setListener(UpdatedListener updatedListener) {
        this.mConfigX = new AnimConfig().addListeners(new TransitionListener() {
            /* class com.android.camera.dualvideo.render.RegionHelper.AnonymousClass1 */

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
                RegionHelper.this.mSpeedX = f;
            }
        });
        this.mConfigY = new AnimConfig().addListeners(new TransitionListener() {
            /* class com.android.camera.dualvideo.render.RegionHelper.AnonymousClass2 */

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
                RegionHelper.this.mSpeedY = f;
            }
        });
        this.mSpeedX = 0.0f;
        this.mStartY = 0.0f;
        Folme.useValue("xspeed").setTo(Integer.valueOf(this.mMarginLeft), this.mConfigX);
        Folme.useValue("yspeed").setTo(Integer.valueOf(this.mMarginTop), this.mConfigY);
        this.mListener = updatedListener;
    }

    public synchronized void setSize(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
    }

    public void setStartPosition(float f, float f2) {
        this.mStartX = f;
        this.mStartY = f2;
    }

    public synchronized void updateMarginOffset(int i, int i2) {
        this.mMarginLeft += i;
        this.mMarginTop += i2;
        checkLocation();
        this.mListener.onUpdated();
        Folme.useValue("xspeed").setTo(Integer.valueOf(this.mMarginLeft), this.mConfigX);
        Folme.useValue("yspeed").setTo(Integer.valueOf(this.mMarginTop), this.mConfigY);
    }
}
