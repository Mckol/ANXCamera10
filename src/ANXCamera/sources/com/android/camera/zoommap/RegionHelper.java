package com.android.camera.zoommap;

import android.graphics.Rect;
import android.util.Size;
import android.view.View;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.IIntValueProperty;

public class RegionHelper {
    public static final int MARGIN = Util.dpToPixel(14.55f);
    private static final String TAG = "RegionHelper";
    public static final String TARGET_X = "TARGET_X";
    public static final String TARGET_Y = "TARGET_Y";
    private AnimConfig mConfigX;
    private AnimConfig mConfigY;
    private boolean mIsCinematicAspectRatio;
    private boolean mIsHovering = false;
    private View mMapView;
    private Rect mPreviewRect;
    private float mSpeedX;
    private float mSpeedY;
    private int mTranslationX;
    private int mTranslationXMax;
    private int mTranslationXMid;
    private int mTranslationXMin;
    private int mTranslationY;
    private int mTranslationYMax;
    private int mTranslationYMid;
    private int mTranslationYMin;
    private Size mWindowSize;

    public RegionHelper(View view, Size size, boolean z) {
        this.mMapView = view;
        this.mWindowSize = size;
        this.mIsCinematicAspectRatio = z;
        this.mPreviewRect = Util.getDisplayRect((ModuleManager.isSquareModule() ? 4 : DataRepository.dataItemRunning().getUiStyle()) != 4 ? 0 : 4);
        this.mTranslationXMin = -((this.mPreviewRect.width() - size.getWidth()) - (MARGIN * 2));
        if (this.mIsCinematicAspectRatio) {
            this.mTranslationXMin += Util.getCinematicAspectRatioMargin() * 2;
        }
        this.mTranslationXMax = 0;
        this.mTranslationXMid = (this.mTranslationXMin + this.mTranslationXMax) / 2;
        this.mTranslationYMin = 0;
        this.mTranslationYMax = ((this.mPreviewRect.height() - MARGIN) - size.getHeight()) - MARGIN;
        this.mTranslationYMid = (this.mTranslationYMin + this.mTranslationYMax) / 2;
        Log.d(TAG, "mTranslationYMin = " + this.mTranslationYMin + ", mTranslationYMax " + this.mTranslationYMax);
    }

    public Rect getPipWindowDefaultLocation() {
        int width = (this.mPreviewRect.width() - MARGIN) - this.mWindowSize.getWidth();
        if (this.mIsCinematicAspectRatio) {
            width -= Util.getCinematicAspectRatioMargin();
        }
        int i = this.mPreviewRect.top + MARGIN;
        return new Rect(width, i, this.mWindowSize.getWidth() + width, this.mWindowSize.getHeight() + i);
    }

    public void initAnimConfig() {
        this.mConfigX = new AnimConfig().setEase(-2, 0.7f, 0.5f).addListeners(new TransitionListener() {
            /* class com.android.camera.zoommap.RegionHelper.AnonymousClass1 */

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
                RegionHelper.this.mSpeedX = f;
            }
        });
        this.mConfigY = new AnimConfig().setEase(-2, 0.7f, 0.5f).addListeners(new TransitionListener() {
            /* class com.android.camera.zoommap.RegionHelper.AnonymousClass2 */

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
                RegionHelper.this.mSpeedY = f;
            }
        });
        this.mSpeedX = 0.0f;
        this.mSpeedY = 0.0f;
        Folme.useValue(TARGET_X).setTo(Integer.valueOf(this.mTranslationX), this.mConfigX);
        Folme.useValue(TARGET_Y).setTo(Integer.valueOf(this.mTranslationY), this.mConfigY);
    }

    public void initTranslation() {
        Log.d(TAG, "initTranslation");
        this.mTranslationX = 0;
        this.mTranslationY = 0;
        this.mMapView.setTranslationX(0.0f);
        this.mMapView.setTranslationY(0.0f);
    }

    public boolean isHovering() {
        return this.mIsHovering;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0077  */
    public boolean moveToEdge() {
        int i;
        boolean z;
        int cinematicAspectRatioMargin = this.mIsCinematicAspectRatio ? Util.getCinematicAspectRatioMargin() : 0;
        if (this.mTranslationX < ((this.mTranslationXMin - cinematicAspectRatioMargin) - MARGIN) - (this.mWindowSize.getWidth() / 2)) {
            i = ((this.mTranslationXMin - cinematicAspectRatioMargin) - (MARGIN * 2)) - this.mWindowSize.getWidth();
        } else if (this.mTranslationX > MARGIN + cinematicAspectRatioMargin + (this.mWindowSize.getWidth() / 2)) {
            i = this.mTranslationXMax + this.mWindowSize.getWidth() + (MARGIN * 2) + cinematicAspectRatioMargin;
        } else {
            float f = this.mSpeedX;
            i = f > 2000.0f ? this.mTranslationXMax : f < -2000.0f ? this.mTranslationXMin : this.mTranslationX < this.mTranslationXMid ? this.mTranslationXMin : this.mTranslationXMax;
            z = false;
            float f2 = this.mSpeedY;
            int i2 = f2 <= 2000.0f ? this.mTranslationYMax : f2 < -2000.0f ? this.mTranslationYMin : this.mTranslationY < this.mTranslationYMid ? this.mTranslationYMin : this.mTranslationYMax;
            Log.d(TAG, "moveToEdge mSpeedX: " + this.mSpeedX + ", mSpeedY: " + this.mSpeedY + ", destX: " + i + ", destY: " + i2);
            if (i != 0 && i2 == 0) {
                CameraStatUtils.trackZoomMapMoveWindow(MistatsConstants.ZoomMapAttr.MOVE_DIRECTION_RIGHT_TOP);
            } else if (i != 0 && i2 > 0) {
                CameraStatUtils.trackZoomMapMoveWindow(MistatsConstants.ZoomMapAttr.MOVE_DIRECTION_RIGHT_BOTTOM);
            } else if (i >= 0 && i2 == 0) {
                CameraStatUtils.trackZoomMapMoveWindow(MistatsConstants.ZoomMapAttr.MOVE_DIRECTION_LEFT_TOP);
            } else if (i < 0 && i2 > 0) {
                CameraStatUtils.trackZoomMapMoveWindow(MistatsConstants.ZoomMapAttr.MOVE_DIRECTION_LEFT_BOTTOM);
            }
            final Boolean valueOf = Boolean.valueOf(z);
            Folme.useValue(TARGET_X).to(Integer.valueOf(i), new AnimConfig().setEase(-2, 0.7f, 0.5f).addListeners(new TransitionListener() {
                /* class com.android.camera.zoommap.RegionHelper.AnonymousClass3 */

                @Override // miuix.animation.listener.TransitionListener
                public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
                    RegionHelper.this.mTranslationX = i;
                    RegionHelper.this.mMapView.setTranslationX((float) RegionHelper.this.mTranslationX);
                    if (valueOf.booleanValue() && z) {
                        Log.d(RegionHelper.TAG, "hidden zoom map view");
                        RegionHelper.this.mMapView.setVisibility(4);
                    }
                }
            }));
            Folme.useValue(TARGET_Y).to(Integer.valueOf(i2), new AnimConfig().setEase(-2, 0.7f, 0.5f).addListeners(new TransitionListener() {
                /* class com.android.camera.zoommap.RegionHelper.AnonymousClass4 */

                @Override // miuix.animation.listener.TransitionListener
                public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
                    RegionHelper.this.mTranslationY = i;
                    RegionHelper.this.mMapView.setTranslationY((float) RegionHelper.this.mTranslationY);
                }
            }));
            return z;
        }
        z = true;
        float f22 = this.mSpeedY;
        if (f22 <= 2000.0f) {
        }
        Log.d(TAG, "moveToEdge mSpeedX: " + this.mSpeedX + ", mSpeedY: " + this.mSpeedY + ", destX: " + i + ", destY: " + i2);
        if (i != 0) {
        }
        if (i != 0) {
        }
        if (i >= 0) {
        }
        CameraStatUtils.trackZoomMapMoveWindow(MistatsConstants.ZoomMapAttr.MOVE_DIRECTION_LEFT_BOTTOM);
        final Boolean valueOf2 = Boolean.valueOf(z);
        Folme.useValue(TARGET_X).to(Integer.valueOf(i), new AnimConfig().setEase(-2, 0.7f, 0.5f).addListeners(new TransitionListener() {
            /* class com.android.camera.zoommap.RegionHelper.AnonymousClass3 */

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
                RegionHelper.this.mTranslationX = i;
                RegionHelper.this.mMapView.setTranslationX((float) RegionHelper.this.mTranslationX);
                if (valueOf2.booleanValue() && z) {
                    Log.d(RegionHelper.TAG, "hidden zoom map view");
                    RegionHelper.this.mMapView.setVisibility(4);
                }
            }
        }));
        Folme.useValue(TARGET_Y).to(Integer.valueOf(i2), new AnimConfig().setEase(-2, 0.7f, 0.5f).addListeners(new TransitionListener() {
            /* class com.android.camera.zoommap.RegionHelper.AnonymousClass4 */

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
                RegionHelper.this.mTranslationY = i;
                RegionHelper.this.mMapView.setTranslationY((float) RegionHelper.this.mTranslationY);
            }
        }));
        return z;
    }

    public void setHovering(boolean z) {
        this.mIsHovering = z;
    }

    public void updateTranslation(int i, int i2) {
        Log.d(TAG, "updateTranslation " + i + " " + i2);
        this.mTranslationX = i;
        int i3 = this.mTranslationYMin;
        int i4 = MARGIN;
        this.mTranslationY = Util.getValidValue(i2, i3 - i4, this.mTranslationYMax + i4);
        this.mMapView.setTranslationX((float) this.mTranslationX);
        this.mMapView.setTranslationY((float) this.mTranslationY);
        Folme.useValue(TARGET_X).setTo(Integer.valueOf(this.mTranslationX), this.mConfigX);
        Folme.useValue(TARGET_Y).setTo(Integer.valueOf(this.mTranslationY), this.mConfigY);
    }
}
