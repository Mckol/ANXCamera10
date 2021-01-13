package com.android.camera.ui;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Range;
import android.util.Rational;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import androidx.annotation.StringRes;
import b.c.a.c;
import com.android.camera.Camera;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.ui.drawable.focus.CameraExposureDrawable;
import com.android.camera.ui.drawable.focus.CameraFocusAnimateDrawable;
import com.android.camera.ui.drawable.focus.CameraFocusSplitAnimateDrawable;
import com.android.camera.ui.drawable.focus.CameraIndicatorState;
import com.android.camera2.CameraCapabilities;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import miui.view.animation.CubicEaseOutInterpolator;

@TargetApi(21)
public class FocusView extends View implements FocusIndicator, V6FunctionUI, Rotatable {
    private static final int ALPHA_TIMEOUT = 1500;
    public static final int CENTER_CAPTURE = 2;
    public static final int CENTER_CIRCLE = 1;
    public static final int CENTER_LOCK = 5;
    public static final int CENTER_MOON = 4;
    public static final int CENTER_NONE = 0;
    public static final int CENTER_SUN = 3;
    public static final int CURSOR_GATHER = 1;
    public static final int CURSOR_NORMAL = 0;
    public static final int CURSOR_SLIDE_BACK = 2;
    private static final int DISAPPEAR_TIME = 1500;
    private static final int DRAG_MODE_ADJUST_EV = 1;
    private static final int DRAG_MODE_MOVE_EXPOSURE = 3;
    private static final int DRAG_MODE_MOVE_FOCUS = 2;
    private static final int DRAG_MODE_NONE = 0;
    private static final int END_DISAPPEAR_TIMEOUT = 800;
    private static final float GAP_NUM = c.zm();
    public static final int MAX_SLIDE_DISTANCE = Util.dpToPixel(110.3f);
    private static final int MODE_AUTO = 0;
    private static final int MODE_FLAG_ANY = 0;
    private static final int MODE_FLAG_EXPOSURE = 2;
    private static final int MODE_FLAG_FOCUS = 1;
    private static final int MODE_MANUAL_COMBO = 1;
    private static final int MODE_MANUAL_METERING_ONLY = 3;
    private static final int MODE_MANUAL_SPLIT = 2;
    private static final int MSG_ALPHA = 8;
    private static final int MSG_ANIMATE_EV_CENTER = 7;
    private static final int MSG_FINISH_DISAPPEAR = 5;
    private static final int MSG_RESET_CENTER = 6;
    private static final int MSG_START_DISAPPEAR = 4;
    public static final int NORMAL_CAPTURE = 0;
    public static final int NORMAL_EV = 1;
    public static final int SET_BY_AUTO_DISAPPEAR = 6;
    public static final int SET_BY_AUTO_FOCUS_MOVING = 3;
    public static final int SET_BY_CANCEL_PREVIOUS_FOCUS = 2;
    public static final int SET_BY_CLEAR_VIEW = 7;
    public static final int SET_BY_DEVICE_KEEP_MOVING = 4;
    public static final int SET_BY_INITIALIZE = 8;
    public static final int SET_BY_RESET_CENTER = 5;
    public static final int SET_BY_RESET_TO_FACE_AREA = 9;
    public static final int SET_BY_TOUCH_DOWN = 1;
    private static final int START_DISAPPEAR_TIMEOUT = 5000;
    private static final int STATE_CHANGE_TIME = 500;
    private static final int STATE_FAIL = 3;
    private static final int STATE_IDLE = 0;
    private static final int STATE_START = 1;
    private static final int STATE_SUCCESS = 2;
    private static final String TAG = "FocusView";
    private static final int TRIANGLE_BASE_DIS = Util.dpToPixel(3.0f);
    public static final int ZERO_MINUS = 3;
    public static final int ZERO_PLUS = 4;
    private final int MIN_DRAG_DISTANCE;
    private Camera mActivity;
    private RollAdapter mAdapter;
    private long mAdjustedDoneTime;
    private boolean mBeingDragged;
    private boolean mBeingEvAdjusted;
    private int mBottomRelative;
    private CameraExposureDrawable mCameraExposureDrawable;
    private CameraFocusAnimateDrawable mCameraFocusAnimateDrawable;
    private CameraFocusSplitAnimateDrawable mCameraFocusSplitAnimateDrawable;
    private Bitmap mCaptureBitmap;
    private Rect mCaptureTapableRect;
    private int mCenterFlag = 0;
    private int mCenterX;
    private int mCenterY;
    private int mCurrentDistanceY;
    private int mCurrentItem;
    private int mCurrentMinusCircleCenter;
    private float mCurrentMinusCircleRadius;
    private int mCurrentMode;
    private int mCurrentRadius;
    private int mCurrentRayBottom;
    private int mCurrentRayHeight;
    private int mCurrentRayWidth;
    private int mCurrentViewState = 0;
    private int mCursorState = 0;
    private float mDeltaX;
    private float mDeltaY;
    private Rect mDisplayRect;
    private int mDragMode;
    private float mEVAnimationRatio;
    private long mEVAnimationStartTime;
    private float mEVCaptureRatio = -1.0f;
    private float mEvValue;
    private ExposureViewListener mExposureViewListener;
    private int mExposureX;
    private int mExposureY;
    private long mFailTime;
    private String mFocusMode = "auto";
    private boolean mFocusMoving;
    private int mFocusX;
    private int mFocusY;
    private GestureDetector mGestureDetector;
    private Handler mHandler = new Handler() {
        /* class com.android.camera.ui.FocusView.AnonymousClass1 */

        public void handleMessage(Message message) {
            String str = FocusView.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("msg=");
            sb.append(message.what);
            sb.append(" ");
            sb.append(FocusView.this.mAdapter != null);
            Log.d(str, sb.toString());
            if (FocusView.this.mAdapter != null) {
                switch (message.what) {
                    case 4:
                    case 5:
                        if (!FocusView.this.mIsDraw || !FocusView.this.mIsDown) {
                            FocusView.this.reset(6);
                            return;
                        }
                        FocusView.this.clearMessages();
                        sendEmptyMessageDelayed(5, 50);
                        return;
                    case 6:
                        FocusView.this.resetCenter();
                        return;
                    case 7:
                        long uptimeMillis = SystemClock.uptimeMillis() - FocusView.this.mEVAnimationStartTime;
                        if (uptimeMillis >= 520) {
                            FocusView.this.mCurrentViewState = 1;
                            FocusView.this.mCursorState = 0;
                            return;
                        }
                        FocusView.this.mEVAnimationRatio = ((float) uptimeMillis) / 500.0f;
                        FocusView.this.calculateAttribute();
                        FocusView.this.invalidate();
                        sendEmptyMessageDelayed(7, 20);
                        return;
                    case 8:
                        FocusView focusView = FocusView.this;
                        focusView.startAlphaAnimation(focusView);
                        FocusView.this.mCameraFocusAnimateDrawable.startEvAdjustAlphaAnim();
                        return;
                    default:
                        return;
                }
            }
        }
    };
    private int mHeight;
    private CameraIndicatorState mIndicatorState;
    private Interpolator mInterpolator;
    private boolean mIsDown;
    private volatile boolean mIsDraw;
    private boolean mIsEvAdjustable;
    private boolean mIsRecording;
    private boolean mIsSplitFocusExposureDown = false;
    private boolean mIsTouchFocus;
    private int mLastItem;
    private Bitmap mLockBodyBitmap;
    private Bitmap mLockExposureBitmap;
    private Bitmap mLockFocusBitmap;
    private Bitmap mLockHeadBitmap;
    private int mMode = 1;
    private Rect mRecordTapableRect;
    private int mRotation;
    private int mScrollDistanceY;
    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        /* class com.android.camera.ui.FocusView.AnonymousClass2 */

        public boolean onDown(MotionEvent motionEvent) {
            boolean z;
            if (!FocusView.this.mIsDraw) {
                return false;
            }
            switch (FocusView.this.mCurrentMode) {
                case 163:
                case 165:
                case 167:
                case 171:
                case 173:
                case 175:
                case 177:
                case 184:
                case 186:
                case 188:
                case 205:
                    if (FocusView.this.mExposureViewListener != null && FocusView.this.mExposureViewListener.isSupportFocusShoot() && !FocusView.this.mExposureViewListener.isShowAeAfLockIndicator()) {
                        z = true;
                        break;
                    }
                default:
                    z = false;
                    break;
            }
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            if (mainContentProtocol != null && mainContentProtocol.isZoomViewMoving()) {
                return false;
            }
            if (FocusView.this.mCurrentViewState != 0 || !z || !FocusView.this.isInCircle(motionEvent.getX() - ((float) FocusView.this.mDisplayRect.left), motionEvent.getY() - ((float) FocusView.this.mDisplayRect.top), ((float) CameraFocusAnimateDrawable.BIG_RADIUS) * 0.5f)) {
                FocusView.this.mIsDown = true;
                FocusView.this.removeMessages();
                FocusView.this.setTouchDown();
            } else if (FocusView.this.mAdapter != null) {
                ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).removeTiltShiftMask();
                ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
                if (cameraAction != null && !cameraAction.isDoingAction()) {
                    cameraAction.onShutterButtonClick(90);
                }
            }
            return true;
        }

        /* JADX WARNING: Removed duplicated region for block: B:30:0x0098  */
        /* JADX WARNING: Removed duplicated region for block: B:33:0x00c3  */
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            int i;
            int itemByCoordinate;
            float f3;
            if (!FocusView.this.mIsDown || motionEvent2 == null || !FocusView.this.mIsEvAdjustable) {
                return false;
            }
            int gestureOrientation = V6GestureRecognizer.getInstance(FocusView.this.mActivity).getGestureOrientation();
            if ((gestureOrientation != 200 || (FocusView.this.mRotation / 90) % 2 != 0) && (gestureOrientation != 100 || (FocusView.this.mRotation / 90) % 2 == 0)) {
                return false;
            }
            int i2 = FocusView.this.mScrollDistanceY;
            int i3 = FocusView.this.mRotation;
            if (i3 == 0) {
                f3 = ((float) i2) - f2;
            } else if (i3 == 90) {
                f3 = ((float) i2) + f;
            } else if (i3 != 180) {
                if (i3 == 270) {
                    f3 = ((float) i2) - f;
                }
                int i4 = FocusView.MAX_SLIDE_DISTANCE;
                i = (int) (((float) i2) / ((((float) Util.sWindowHeight) / 2.0f) / (((float) i4) / 4.0f)));
                FocusView.this.mCurrentDistanceY = Util.clamp(i, ((-i4) / 2) - FocusView.TRIANGLE_BASE_DIS, FocusView.MAX_SLIDE_DISTANCE / 2);
                if (i == FocusView.this.mCurrentDistanceY) {
                    FocusView.this.mScrollDistanceY = i2;
                }
                FocusView.this.mBeingEvAdjusted = true;
                FocusView.this.resetAlpha();
                FocusView.this.mHandler.sendEmptyMessageDelayed(8, 1500);
                itemByCoordinate = FocusView.this.getItemByCoordinate();
                if (itemByCoordinate != FocusView.this.mCurrentItem) {
                    if (FocusView.this.mCurrentViewState != 3 && itemByCoordinate < FocusView.this.mCurrentItem && FocusView.this.mCurrentItem >= FocusView.this.mAdapter.getCenterIndex() && itemByCoordinate < FocusView.this.mAdapter.getCenterIndex()) {
                        FocusView.this.startAnimation();
                        FocusView focusView = FocusView.this;
                        focusView.mLastItem = focusView.mCurrentItem;
                        FocusView.this.mCurrentViewState = 3;
                    } else if (FocusView.this.mCurrentViewState != 4 && itemByCoordinate > FocusView.this.mCurrentItem && FocusView.this.mCurrentItem < FocusView.this.mAdapter.getCenterIndex() && itemByCoordinate >= FocusView.this.mAdapter.getCenterIndex()) {
                        FocusView.this.startAnimation();
                        FocusView focusView2 = FocusView.this;
                        focusView2.mLastItem = focusView2.mCurrentItem;
                        FocusView.this.mCurrentViewState = 4;
                    }
                    FocusView.this.setCurrentItem(itemByCoordinate, false);
                }
                FocusView.this.mCameraFocusAnimateDrawable.setEvChanged((float) FocusView.this.mCurrentDistanceY, FocusView.this.mEvValue);
                if (FocusView.this.mCurrentViewState == 0 || FocusView.this.mCurrentViewState == 1) {
                    FocusView.this.mCurrentViewState = 1;
                    FocusView.this.calculateAttribute();
                    FocusView.this.invalidate();
                }
                return true;
            } else {
                f3 = ((float) i2) + f2;
            }
            i2 = (int) f3;
            int i42 = FocusView.MAX_SLIDE_DISTANCE;
            i = (int) (((float) i2) / ((((float) Util.sWindowHeight) / 2.0f) / (((float) i42) / 4.0f)));
            FocusView.this.mCurrentDistanceY = Util.clamp(i, ((-i42) / 2) - FocusView.TRIANGLE_BASE_DIS, FocusView.MAX_SLIDE_DISTANCE / 2);
            if (i == FocusView.this.mCurrentDistanceY) {
            }
            FocusView.this.mBeingEvAdjusted = true;
            FocusView.this.resetAlpha();
            FocusView.this.mHandler.sendEmptyMessageDelayed(8, 1500);
            itemByCoordinate = FocusView.this.getItemByCoordinate();
            if (itemByCoordinate != FocusView.this.mCurrentItem) {
            }
            FocusView.this.mCameraFocusAnimateDrawable.setEvChanged((float) FocusView.this.mCurrentDistanceY, FocusView.this.mEvValue);
            FocusView.this.mCurrentViewState = 1;
            FocusView.this.calculateAttribute();
            FocusView.this.invalidate();
            return true;
        }
    };
    private long mStartTime;
    private int mState;
    private long mSuccessTime;
    private int mWidth;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CenterFlag {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CursorState {
    }

    public interface ExposureViewListener {
        boolean isMeteringAreaOnly();

        boolean isNeedHapticFeedback();

        boolean isShowAeAfLockIndicator();

        boolean isShowCaptureButton();

        boolean isSupportAELockOnly();

        boolean isSupportFocusShoot();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SetPositionType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewState {
    }

    public FocusView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mActivity = (Camera) context;
        this.mInterpolator = new CubicEaseOutInterpolator();
        this.mGestureDetector = new GestureDetector(context, this.mSimpleOnGestureListener);
        this.mGestureDetector.setIsLongpressEnabled(false);
        this.mWidth = Util.sWindowWidth;
        this.mHeight = Util.sWindowHeight;
        setCenter(this.mWidth / 2, this.mHeight / 2);
        this.mCaptureBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_capture);
        this.mIndicatorState = new CameraIndicatorState();
        this.mCameraFocusAnimateDrawable = new CameraFocusAnimateDrawable(getContext());
        this.mCameraFocusAnimateDrawable.setIndicatorData(this.mIndicatorState, this.mCaptureBitmap);
        this.mLockHeadBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_aeaf_lock_head);
        this.mLockBodyBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_aeaf_lock_body);
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.ic_exposure_sun);
        this.mLockFocusBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_focus_locked);
        this.mLockExposureBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_exposure_locked);
        this.mCameraFocusAnimateDrawable.setLockIndicatorData(this.mIndicatorState, this.mLockHeadBitmap, this.mLockBodyBitmap);
        this.mCameraFocusAnimateDrawable.setCallback(this);
        this.mCameraExposureDrawable = new CameraExposureDrawable(context);
        this.mCameraExposureDrawable.setIndicatorData(this.mIndicatorState, this.mCaptureBitmap);
        this.mCameraFocusSplitAnimateDrawable = new CameraFocusSplitAnimateDrawable(context);
        this.mCameraFocusSplitAnimateDrawable.setAeAfLockedBitmap(this.mCaptureBitmap, this.mLockFocusBitmap, decodeResource);
        int scaledTouchSlop = ViewConfiguration.get(this.mActivity).getScaledTouchSlop();
        this.MIN_DRAG_DISTANCE = scaledTouchSlop * scaledTouchSlop;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void calculateAttribute() {
        ExposureViewListener exposureViewListener;
        float itemRatio = getItemRatio(this.mCurrentItem);
        float itemRatio2 = getItemRatio(this.mLastItem);
        int i = this.mCurrentMode;
        if (i != 167 && i != 180) {
            int i2 = this.mCurrentViewState;
            int i3 = 1;
            if (i2 == 0) {
                if (this.mIsTouchFocus && (exposureViewListener = this.mExposureViewListener) != null && exposureViewListener.isShowCaptureButton() && this.mExposureViewListener.isSupportFocusShoot()) {
                    i3 = 2;
                }
                this.mCenterFlag = i3;
            } else if (i2 != 1) {
                if (i2 == 3) {
                    float f = this.mEVAnimationRatio;
                    if (f <= 0.5f) {
                        float f2 = f * 2.0f;
                        this.mCurrentRayWidth = Util.dpToPixel(1.5f);
                        float f3 = 1.0f - f2;
                        float f4 = (itemRatio2 * f3) - f2;
                        this.mCurrentRayHeight = Util.dpToPixel((f4 * 2.0f) + 5.0f);
                        this.mCurrentRayBottom = Util.dpToPixel((f4 * 3.0f) + 7.5f);
                        this.mCurrentRadius = Util.dpToPixel((f2 * 3.0f) + 5.0f + (itemRatio2 * 2.0f * f3));
                        this.mCenterFlag = 3;
                    } else {
                        float f5 = (f - 0.5f) * 2.0f;
                        this.mCurrentRadius = Util.dpToPixel(8.0f - (((1.0f - itemRatio) * f5) * 2.0f));
                        int i4 = this.mCurrentRadius;
                        float f6 = 1.0f - f5;
                        this.mCurrentMinusCircleCenter = (int) (((float) i4) * ((0.914f * f6) + 0.5f));
                        this.mCurrentMinusCircleRadius = ((float) i4) * ((f6 * 0.2f) + 0.8f);
                        this.mCenterFlag = 4;
                    }
                } else if (i2 != 4) {
                    this.mCenterFlag = 1;
                } else {
                    float f7 = this.mEVAnimationRatio;
                    if (f7 < 0.5f) {
                        float f8 = f7 * 2.0f;
                        this.mCurrentRadius = Util.dpToPixel(((itemRatio2 + ((1.0f - itemRatio2) * f8)) * 2.0f) + 6.0f);
                        int i5 = this.mCurrentRadius;
                        this.mCurrentMinusCircleCenter = (int) (((float) i5) * ((0.914f * f8) + 0.5f));
                        this.mCurrentMinusCircleRadius = ((float) i5) * ((f8 * 0.2f) + 0.8f);
                        this.mCenterFlag = 4;
                    } else {
                        float f9 = (f7 - 0.5f) * 2.0f;
                        this.mCurrentRayWidth = Util.dpToPixel(1.5f);
                        float f10 = 1.0f - f9;
                        float f11 = (itemRatio * f9) - f10;
                        this.mCurrentRayHeight = Util.dpToPixel((f11 * 2.0f) + 5.0f);
                        this.mCurrentRayBottom = Util.dpToPixel((f11 * 3.0f) + 7.5f);
                        this.mCurrentRadius = Util.dpToPixel((f10 * 3.0f) + 5.0f + (itemRatio * 2.0f * f9));
                        this.mCenterFlag = 3;
                    }
                }
            } else if (this.mCurrentItem < this.mAdapter.getCenterIndex()) {
                this.mCurrentRadius = Util.dpToPixel((itemRatio * 2.0f) + 6.0f);
                int i6 = this.mCurrentRadius;
                this.mCurrentMinusCircleCenter = (int) (((float) i6) * 0.5f);
                this.mCurrentMinusCircleRadius = ((float) i6) * 0.8f;
                this.mCenterFlag = 4;
            } else {
                this.mCurrentRayWidth = Util.dpToPixel(1.5f);
                this.mCurrentRayHeight = Util.dpToPixel(5.0f);
                this.mCurrentRayBottom = Util.dpToPixel(7.5f);
                this.mCurrentRadius = Util.dpToPixel(5.0f);
                this.mCenterFlag = 3;
            }
            this.mCameraFocusAnimateDrawable.cancelResetCenter();
            CameraIndicatorState cameraIndicatorState = this.mIndicatorState;
            cameraIndicatorState.mCenterFlag = this.mCenterFlag;
            cameraIndicatorState.mCurrentRadius = this.mCurrentRadius;
            cameraIndicatorState.mCurrentAngle = (float) getCurrentAngle();
            CameraIndicatorState cameraIndicatorState2 = this.mIndicatorState;
            cameraIndicatorState2.mCurrentRayWidth = this.mCurrentRayWidth;
            cameraIndicatorState2.mCurrentRayHeight = this.mCurrentRayHeight;
            cameraIndicatorState2.mCurrentRayBottom = this.mCurrentRayBottom;
            cameraIndicatorState2.mCurrentMinusCircleCenter = this.mCurrentMinusCircleCenter;
            cameraIndicatorState2.mCurrentMinusCircleRadius = this.mCurrentMinusCircleRadius;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void clearMessages() {
        this.mHandler.removeMessages(8);
        this.mHandler.removeMessages(4);
        this.mHandler.removeMessages(5);
        this.mHandler.removeMessages(6);
        this.mHandler.removeMessages(7);
    }

    private int getCurrentAngle() {
        int i;
        int i2;
        if (this.mCursorState != 2 || (i2 = this.mCurrentViewState) == 3 || i2 == 4) {
            int i3 = this.mCurrentViewState;
            if (i3 == 1) {
                int clamp = Util.clamp(this.mBottomRelative - this.mCurrentDistanceY, 0, MAX_SLIDE_DISTANCE);
                int i4 = MAX_SLIDE_DISTANCE;
                i = Util.clamp(clamp >= i4 / 2 ? ((clamp - (i4 / 2)) * 360) / (i4 / 2) : 0, 0, 360);
            } else if (i3 == 3) {
                return Util.clamp((int) (this.mEVAnimationRatio * 2.0f * 135.0f), 0, 135);
            } else {
                if (i3 == 4) {
                    return Util.clamp((int) ((1.0f - ((this.mEVAnimationRatio - 0.5f) * 2.0f)) * 135.0f), 0, 135);
                }
                return 0;
            }
        } else {
            i = Util.clamp(this.mCurrentItem >= this.mAdapter.getCenterIndex() ? ((this.mCurrentItem - this.mAdapter.getCenterIndex()) * 360) / this.mAdapter.getCenterIndex() : 0, 0, 360);
        }
        return 360 - i;
    }

    private float getInterpolation(float f) {
        float interpolation = this.mInterpolator.getInterpolation(f);
        if (((double) interpolation) > 1.0d) {
            return 1.0f;
        }
        return interpolation;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getItemByCoordinate() {
        return Util.clamp((this.mAdapter.getMaxItem() * (this.mBottomRelative - this.mCurrentDistanceY)) / MAX_SLIDE_DISTANCE, 0, this.mAdapter.getMaxItem());
    }

    private float getItemRatio(int i) {
        float maxItem = ((float) i) / ((float) this.mAdapter.getMaxItem());
        if (maxItem >= 0.5f) {
            maxItem -= 0.5f;
        }
        return maxItem * 2.0f;
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x00cb A[RETURN] */
    private void handleSplitFocusExposureEvent(MotionEvent motionEvent) {
        float f;
        int i;
        Rect rect = this.mIsRecording ? this.mRecordTapableRect : this.mCaptureTapableRect;
        float x = motionEvent.getX() - ((float) this.mDisplayRect.left);
        float y = motionEvent.getY() - ((float) this.mDisplayRect.top);
        if (motionEvent.getActionMasked() == 0) {
            resetAlpha();
            this.mIsSplitFocusExposureDown = false;
            int i2 = this.mMode;
            if (i2 == 1) {
                if (isInCircle(x, y, (float) CameraFocusSplitAnimateDrawable.BIG_RADIUS)) {
                    this.mDragMode = 3;
                }
            } else if (i2 == 2) {
                if (isInCircle(x, y, (float) this.mFocusX, (float) this.mFocusY, (float) CameraFocusSplitAnimateDrawable.BIG_RADIUS)) {
                    this.mDragMode = 2;
                    this.mFocusMoving = false;
                    this.mIsSplitFocusExposureDown = true;
                } else if (isInCircle(x, y, (float) this.mExposureX, (float) this.mExposureY, (float) CameraFocusSplitAnimateDrawable.BIG_RADIUS)) {
                    this.mDragMode = 3;
                    this.mIsSplitFocusExposureDown = true;
                } else {
                    this.mDragMode = 0;
                }
            }
            int i3 = this.mDragMode;
            if (i3 == 2) {
                this.mDeltaX = x - ((float) this.mFocusX);
                this.mDeltaY = y - ((float) this.mFocusY);
            } else if (i3 == 3) {
                this.mDeltaX = x - ((float) this.mExposureX);
                this.mDeltaY = y - ((float) this.mExposureY);
            }
        } else if (motionEvent.getActionMasked() == 2) {
            float f2 = x - this.mDeltaX;
            float f3 = y - this.mDeltaY;
            if (!this.mBeingDragged) {
                float f4 = 0.0f;
                int i4 = this.mDragMode;
                if (i4 == 2) {
                    int i5 = this.mFocusX;
                    f = (((float) i5) - f2) * (((float) i5) - f2);
                    i = this.mFocusY;
                } else {
                    if (i4 == 3) {
                        int i6 = this.mExposureX;
                        f = (((float) i6) - f2) * (((float) i6) - f2);
                        i = this.mExposureY;
                    }
                    if (f4 < ((float) this.MIN_DRAG_DISTANCE)) {
                        return;
                    }
                }
                f4 = f + ((((float) i) - f3) * (((float) i) - f3));
                if (f4 < ((float) this.MIN_DRAG_DISTANCE)) {
                }
            }
            resetAlpha();
            this.mHandler.sendEmptyMessageDelayed(8, 1500);
            if (this.mMode == 1) {
                this.mMode = 2;
                MistatsWrapper.moduleUIClickEvent(this.mCurrentMode == 167 ? "M_manual_" : "M_proVideo_", MistatsConstants.Manual.METERING_FOCUS_SPLIT, "on");
            }
            if (this.mIsRecording) {
                getResources().getDimensionPixelSize(R.dimen.bottom_picker_width);
                getResources().getDimensionPixelSize(R.dimen.bottom_snap_width);
            }
            Rect rect2 = this.mDisplayRect;
            int i7 = rect2.right;
            int i8 = CameraFocusAnimateDrawable.BIG_RADIUS;
            float min = Math.min((float) (i7 - i8), Math.max((float) (rect2.left + i8), f2));
            int i9 = rect.bottom;
            int i10 = CameraFocusAnimateDrawable.BIG_RADIUS;
            float min2 = Math.min((float) ((i9 - i10) - this.mDisplayRect.top), Math.max((float) i10, f3));
            if (this.mDragMode == 2 && !this.mCameraFocusSplitAnimateDrawable.isFocusLocked()) {
                this.mBeingDragged = true;
                int i11 = (int) min;
                this.mCenterX = i11;
                this.mFocusX = i11;
                int i12 = (int) min2;
                this.mCenterY = i12;
                this.mFocusY = i12;
                if (!this.mFocusMoving) {
                    this.mFocusMoving = true;
                }
                this.mCameraFocusSplitAnimateDrawable.setState(2);
                this.mCameraFocusSplitAnimateDrawable.setFocusCenter(this.mFocusX, this.mFocusY);
            } else if (this.mDragMode == 3 && !this.mCameraFocusSplitAnimateDrawable.isExposureLocked()) {
                this.mBeingDragged = true;
                this.mExposureX = (int) min;
                this.mExposureY = (int) min2;
                this.mCameraFocusSplitAnimateDrawable.setState(2);
                this.mCameraFocusSplitAnimateDrawable.setExposureCenter(this.mExposureX, this.mExposureY);
                updateExposureArea();
            }
            invalidate();
        } else if (motionEvent.getActionMasked() == 1) {
            if (this.mDragMode == 2 && !this.mCameraFocusSplitAnimateDrawable.isFocusLocked()) {
                updateFocusArea();
            }
            this.mDragMode = 0;
            this.mBeingDragged = false;
            this.mIsSplitFocusExposureDown = false;
        }
    }

    private void hideTipMessage(@StringRes int i) {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directlyHideTips(i);
        }
    }

    private void initRect() {
        this.mDisplayRect = Util.getPreviewRect(getContext());
        boolean z = false;
        this.mCaptureTapableRect = Util.getDisplayRect(0);
        this.mRecordTapableRect = Util.getDisplayRect(1);
        this.mWidth = this.mDisplayRect.width();
        this.mHeight = this.mDisplayRect.height();
        this.mCenterX = this.mWidth / 2;
        this.mCenterY = this.mHeight / 2;
        setPivotX((float) this.mCenterX);
        setPivotY((float) this.mCenterY);
        CameraFocusAnimateDrawable cameraFocusAnimateDrawable = this.mCameraFocusAnimateDrawable;
        if (1 == getLayoutDirection()) {
            z = true;
        }
        cameraFocusAnimateDrawable.setRtlAndDisplayRect(z, this.mDisplayRect);
        this.mCameraFocusAnimateDrawable.setCenter(this.mCenterX, this.mCenterY);
        this.mCameraFocusSplitAnimateDrawable.setCenter(this.mCenterX, this.mCenterY);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isInCircle(float f, float f2, float f3) {
        float f4 = f - ((float) this.mCenterX);
        float f5 = f2 - ((float) this.mCenterY);
        return Math.sqrt((double) ((f4 * f4) + (f5 * f5))) <= ((double) f3);
    }

    private boolean isInCircle(float f, float f2, float f3, float f4, float f5) {
        float f6 = f - f3;
        float f7 = f2 - f4;
        return Math.sqrt((double) ((f6 * f6) + (f7 * f7))) <= ((double) f5);
    }

    private boolean isStableStart() {
        ExposureViewListener exposureViewListener;
        if (this.mIsTouchFocus && (exposureViewListener = this.mExposureViewListener) != null) {
            return exposureViewListener.isMeteringAreaOnly();
        }
        return false;
    }

    private void processParameterIfNeeded(float f) {
        if (this.mIsTouchFocus && this.mEVCaptureRatio != -1.0f && this.mCenterFlag == 0) {
            this.mCenterFlag = 2;
        }
    }

    private void reload() {
        RollAdapter rollAdapter = this.mAdapter;
        if (rollAdapter != null) {
            this.mCurrentItem = rollAdapter.getItemIndexByValue(Integer.valueOf(CameraSettings.readExposure()));
            updateEV();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void removeMessages() {
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void reset(int i) {
        if (this.mIsDraw || i == 8 || i == 2) {
            clearMessages();
            this.mState = 0;
            setPosition(i, this.mWidth / 2, this.mHeight / 2);
            this.mCurrentViewState = 0;
            this.mCenterFlag = 0;
            hideTipMessage(R.string.hint_ae_af_lock);
            hideTipMessage(R.string.hint_ae_lock);
            this.mIsDown = false;
            stopEvAdjust();
            setDraw(false);
            invalidate();
            this.mMode = 1;
            this.mCameraFocusSplitAnimateDrawable.reset();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void resetAlpha() {
        if (this.mHandler.hasMessages(8)) {
            this.mHandler.removeMessages(8);
        }
        setAlpha(1.0f);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void resetCenter() {
        this.mCurrentViewState = 0;
        this.mAdjustedDoneTime = System.currentTimeMillis();
        ExposureViewListener exposureViewListener = this.mExposureViewListener;
        if (exposureViewListener != null) {
            if (exposureViewListener.isShowAeAfLockIndicator() && CameraSettings.isAEAFLockSupport()) {
                this.mCenterFlag = 5;
                this.mIndicatorState.mCenterFlag = this.mCenterFlag;
                invalidate();
            } else if (this.mExposureViewListener.isShowCaptureButton() && this.mExposureViewListener.isSupportFocusShoot()) {
                this.mCenterFlag = 2;
                this.mIndicatorState.mCenterFlag = this.mCenterFlag;
                invalidate();
            }
        }
    }

    private void resetEvValue() {
        this.mBeingEvAdjusted = false;
        this.mEvValue = 0.0f;
        this.mCurrentItem = 0;
        this.mScrollDistanceY = 0;
        this.mCurrentDistanceY = 0;
        this.mCameraFocusAnimateDrawable.reset();
    }

    private void setCenter(int i, int i2) {
        this.mExposureX = i;
        this.mFocusX = i;
        this.mCenterX = i;
        this.mExposureY = i2;
        this.mFocusY = i2;
        this.mCenterY = i2;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setCurrentItem(int i, boolean z) {
        if (i != this.mCurrentItem) {
            this.mCurrentItem = i;
            if (this.mAdapter != null) {
                ModeProtocol.EvChangedProtocol evChangedProtocol = (ModeProtocol.EvChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(169);
                if (evChangedProtocol == null) {
                    Log.d(TAG, "needEvPresenter");
                } else {
                    evChangedProtocol.onEvChanged(this.mAdapter.getItemValue(i), 1);
                }
            }
            updateEV();
        }
    }

    private void setDraw(boolean z) {
        if (z && this.mIsTouchFocus && this.mIsDraw != z) {
            reload();
        }
        setAlpha(1.0f);
        this.mIsDraw = z;
        if (!this.mIsDraw) {
            CameraFocusAnimateDrawable cameraFocusAnimateDrawable = this.mCameraFocusAnimateDrawable;
            if (cameraFocusAnimateDrawable != null) {
                cameraFocusAnimateDrawable.cancelFocusingAnimation();
            }
            CameraFocusSplitAnimateDrawable cameraFocusSplitAnimateDrawable = this.mCameraFocusSplitAnimateDrawable;
            if (cameraFocusSplitAnimateDrawable != null) {
                cameraFocusSplitAnimateDrawable.cancelFocusingAnimation();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setTouchDown() {
        this.mBottomRelative = (int) (((float) MAX_SLIDE_DISTANCE) * 0.5f);
    }

    private void showTipMessage(int i, @StringRes int i2) {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            if (bottomPopupTips.isPortraitHintVisible()) {
                bottomPopupTips.setPortraitHintVisible(8);
            }
            bottomPopupTips.showTips(i, i2, 2);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startAlphaAnimation(View view) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(1.0f, 0.4f);
        valueAnimator.setDuration(200L);
        valueAnimator.setInterpolator(new CubicEaseOutInterpolator());
        valueAnimator.addUpdateListener(new d(view));
        valueAnimator.start();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startAnimation() {
        this.mEVAnimationStartTime = SystemClock.uptimeMillis();
        this.mHandler.removeMessages(7);
        this.mHandler.sendEmptyMessage(7);
    }

    private void stopEvAdjust() {
        if (this.mBeingEvAdjusted) {
            this.mBeingEvAdjusted = false;
            ModeProtocol.EvChangedProtocol evChangedProtocol = (ModeProtocol.EvChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(169);
            if (evChangedProtocol != null) {
                evChangedProtocol.onEvChanged(0, 2);
            }
        }
    }

    private void updateEV() {
        Rational exposureCompensationRational = Camera2DataContainer.getInstance().getCurrentCameraCapabilities().getExposureCompensationRational();
        RollAdapter rollAdapter = this.mAdapter;
        if (rollAdapter != null) {
            this.mEvValue = ((float) rollAdapter.getItemValue(this.mCurrentItem)) * exposureCompensationRational.floatValue();
        } else {
            this.mEvValue = 0.0f;
        }
    }

    private void updateExposureArea() {
        ((ModeProtocol.EvChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(169)).onMeteringAreaChanged(this.mExposureX, this.mExposureY);
    }

    private void updateFocusArea() {
        Log.d(TAG, "updateFocusArea");
        if (!this.mFocusMode.equals("manual")) {
            ((ModeProtocol.EvChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(169)).onFocusAreaChanged(this.mFocusX, this.mFocusY);
        }
    }

    public boolean canLongPressReset() {
        int i;
        return this.mMode == 2 && ((i = this.mDragMode) == 3 || i == 2);
    }

    public boolean canRestFocus() {
        return !this.mCameraFocusSplitAnimateDrawable.isFocusOrExposureLocked();
    }

    public void checkTouchRegionContainSplitFocusExposure(MotionEvent motionEvent) {
        int i = this.mCurrentMode;
        if (i == 180 || i == 167) {
            Rect tapableRectWithEdgeSlop = Util.getTapableRectWithEdgeSlop(!this.mIsRecording, this.mDisplayRect, this.mCurrentMode, this.mActivity);
            if (this.mIsRecording) {
                tapableRectWithEdgeSlop.bottom = this.mDisplayRect.bottom - this.mActivity.getResources().getDimensionPixelSize(R.dimen.bottom_snap_width);
            }
            if (!tapableRectWithEdgeSlop.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                this.mIsSplitFocusExposureDown = false;
                return;
            }
            float x = motionEvent.getX() - ((float) this.mDisplayRect.left);
            float y = motionEvent.getY() - ((float) this.mDisplayRect.top);
            if (motionEvent.getActionMasked() == 0) {
                int i2 = this.mMode;
                if (i2 == 1) {
                    if (isInCircle(x, y, (float) CameraFocusSplitAnimateDrawable.BIG_RADIUS)) {
                        this.mIsSplitFocusExposureDown = true;
                    }
                } else if (i2 != 2) {
                } else {
                    if (isInCircle(x, y, (float) this.mFocusX, (float) this.mFocusY, (float) CameraFocusSplitAnimateDrawable.BIG_RADIUS)) {
                        this.mIsSplitFocusExposureDown = true;
                    } else if (isInCircle(x, y, (float) this.mExposureX, (float) this.mExposureY, (float) CameraFocusSplitAnimateDrawable.BIG_RADIUS)) {
                        this.mIsSplitFocusExposureDown = true;
                    }
                }
            } else if (motionEvent.getActionMasked() == 1) {
                this.mIsSplitFocusExposureDown = false;
            }
        } else {
            this.mIsSplitFocusExposureDown = false;
        }
    }

    @Override // com.android.camera.ui.FocusIndicator
    public void clear() {
        reset(7);
    }

    public void clear(int i) {
        String str = TAG;
        Log.d(str, "clear: " + i);
        CameraFocusAnimateDrawable cameraFocusAnimateDrawable = this.mCameraFocusAnimateDrawable;
        if (cameraFocusAnimateDrawable != null) {
            cameraFocusAnimateDrawable.cancelFocusingAnimation();
        }
        reset(i);
    }

    @Override // com.android.camera.ui.V6FunctionUI
    public void enableControls(boolean z) {
    }

    public void initialize(ExposureViewListener exposureViewListener) {
        this.mExposureViewListener = exposureViewListener;
        reset(8);
    }

    public void invalidateDrawable(Drawable drawable) {
        invalidate();
    }

    public boolean isEvAdjusted() {
        return this.mBeingEvAdjusted;
    }

    public boolean isEvAdjustedTime() {
        return isShown() && this.mIsTouchFocus && (this.mBeingEvAdjusted || !Util.isTimeout(System.currentTimeMillis(), this.mAdjustedDoneTime, 1500));
    }

    public boolean isFocusViewMoving() {
        int i;
        return this.mMode == 2 && ((i = this.mDragMode) == 3 || i == 2);
    }

    public boolean isSplitFocusExposureDown() {
        return this.mIsSplitFocusExposureDown;
    }

    public boolean isVisible() {
        return this.mIsDraw;
    }

    @Override // com.android.camera.ui.V6FunctionUI
    public void onCameraOpen() {
    }

    @Override // com.android.camera.ui.V6FunctionUI
    public void onCreate() {
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        CameraFocusAnimateDrawable cameraFocusAnimateDrawable = this.mCameraFocusAnimateDrawable;
        if (cameraFocusAnimateDrawable != null) {
            cameraFocusAnimateDrawable.cancelFocusingAnimation();
            this.mCameraFocusAnimateDrawable.setCallback(null);
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mIsDraw) {
            int i = this.mCurrentMode;
            if (i == 180 || i == 167) {
                this.mCameraFocusSplitAnimateDrawable.draw(canvas);
                return;
            }
            CameraFocusAnimateDrawable cameraFocusAnimateDrawable = this.mCameraFocusAnimateDrawable;
            if (cameraFocusAnimateDrawable != null && this.mMode != 3) {
                cameraFocusAnimateDrawable.draw(canvas);
            }
        }
    }

    @Override // com.android.camera.ui.V6FunctionUI
    public void onPause() {
        setDraw(false);
    }

    @Override // com.android.camera.ui.V6FunctionUI
    public void onResume() {
        setDraw(false);
    }

    public boolean onViewTouchEvent(MotionEvent motionEvent) {
        if (this.mAdapter == null || !this.mIsTouchFocus) {
            return false;
        }
        if (this.mState != 2 && !isStableStart()) {
            return false;
        }
        this.mGestureDetector.onTouchEvent(motionEvent);
        boolean z = this.mIsDown;
        if (motionEvent.getActionMasked() == 5 && this.mIsDown) {
            this.mIsDown = false;
        }
        int i = this.mCurrentMode;
        if (i == 167 || i == 180) {
            handleSplitFocusExposureEvent(motionEvent);
        }
        if (1 == motionEvent.getAction() || 3 == motionEvent.getAction()) {
            if (this.mBeingEvAdjusted) {
                CameraStatUtils.trackEvAdjusted(this.mEvValue);
                stopEvAdjust();
                this.mAdjustedDoneTime = System.currentTimeMillis();
                this.mHandler.removeMessages(6);
                this.mHandler.sendEmptyMessageDelayed(6, 1000);
            }
            if (this.mIsDraw) {
                this.mIsDown = false;
            }
        }
        return z || this.mIsDown;
    }

    public void processingFinish() {
        this.mIsRecording = false;
    }

    public void processingStart() {
        this.mIsRecording = true;
    }

    public void reInit() {
        Log.d(TAG, "onCameraOpen>>");
        initRect();
        CameraCapabilities currentCameraCapabilities = Camera2DataContainer.getInstance().getCurrentCameraCapabilities();
        if (currentCameraCapabilities != null) {
            Range<Integer> exposureCompensationRange = currentCameraCapabilities.getExposureCompensationRange();
            int intValue = exposureCompensationRange.getLower().intValue();
            int intValue2 = exposureCompensationRange.getUpper().intValue();
            if (c.Wg) {
                intValue = -3;
                intValue2 = 4;
            }
            if (intValue2 != 0 && intValue2 != intValue) {
                float f = GAP_NUM;
                this.mAdapter = new FloatSlideAdapter(intValue, intValue2, f == 0.0f ? 1.0f : ((float) (intValue2 - intValue)) / f);
                String str = TAG;
                Log.w(str, "onCameraOpen: adapter=" + this.mAdapter);
                if (this.mAdapter != null) {
                    resetEvValue();
                    this.mCurrentMode = DataRepository.dataItemGlobal().getCurrentMode();
                    setRotation(getRotation());
                    if (this.mCurrentMode != 167) {
                        this.mCameraFocusAnimateDrawable.setEvTextVisible(8);
                    } else {
                        this.mCameraFocusAnimateDrawable.setEvTextVisible(0);
                    }
                    setEvAdjustable(CameraSettings.isEvAdjustable());
                    int itemIndexByValue = this.mAdapter.getItemIndexByValue(Integer.valueOf(CameraSettings.readExposure()));
                    if (itemIndexByValue < 0) {
                        this.mCurrentItem = this.mAdapter.getMaxItem() / 2;
                    } else {
                        this.mCurrentItem = itemIndexByValue;
                    }
                    updateEV();
                }
            }
        }
    }

    public void releaseListener() {
        this.mExposureViewListener = null;
    }

    public void setEvAdjustable(boolean z) {
        if (this.mAdapter != null) {
            this.mIsEvAdjustable = z;
            resetEvValue();
            calculateAttribute();
            this.mCameraFocusAnimateDrawable.setEvAdjustVisible(z ? 0 : 8);
            if (!z) {
                this.mCameraFocusAnimateDrawable.setEvTextVisible(8);
            }
            invalidate();
        }
    }

    public void setFocusType(boolean z) {
        this.mIsTouchFocus = z;
    }

    @Override // com.android.camera.ui.Rotatable
    public void setOrientation(int i, boolean z) {
        if (this.mRotation != i) {
            this.mRotation = i;
            this.mCameraFocusAnimateDrawable.setOrientation(i);
            this.mCameraFocusSplitAnimateDrawable.setOrientation(i);
            if (this.mIsDraw) {
                invalidate();
            }
        }
    }

    public void setPosition(int i, int i2, int i3) {
        int i4;
        ModeProtocol.EvChangedProtocol evChangedProtocol;
        this.mCenterX = i2;
        this.mCenterY = i3;
        setPivotX((float) i2);
        setPivotY((float) i3);
        setCenter(i2, i3);
        this.mCameraFocusAnimateDrawable.setCenter(i2, i3);
        this.mCameraFocusSplitAnimateDrawable.setCenter(i2, i3);
        removeMessages();
        if (!(this.mEvValue == 0.0f || (i4 = this.mCurrentMode) == 167 || i4 == 180 || ((i != 1 && i != 2 && i != 5) || (evChangedProtocol = (ModeProtocol.EvChangedProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(169)) == null))) {
            evChangedProtocol.resetEvValue();
        }
        if (this.mCurrentDistanceY != 0 && i != 7) {
            resetEvValue();
        }
    }

    public void setRotation(float f) {
        int i = this.mCurrentMode;
        if (i == 180 || i == 167) {
            super.setRotation(0.0f);
        } else {
            super.setRotation(f);
        }
    }

    @Override // com.android.camera.ui.FocusIndicator
    public void showFail() {
        this.mCameraFocusAnimateDrawable.startFocusFailAnimation();
        Log.d(TAG, "showFail");
        if (this.mState == 1) {
            clearMessages();
            setDraw(true);
            this.mState = 3;
            this.mFailTime = SystemClock.uptimeMillis();
            this.mHandler.sendEmptyMessageDelayed(5, 800);
            invalidate();
        }
    }

    @Override // com.android.camera.ui.FocusIndicator
    public void showStart() {
        Log.d(TAG, "showStart");
        clearMessages();
        this.mState = 1;
        this.mCursorState = 1;
        this.mStartTime = SystemClock.uptimeMillis();
        setDraw(true);
        this.mCameraFocusAnimateDrawable.startTouchDownAnimation();
        if (this.mMode == 1) {
            this.mCameraFocusSplitAnimateDrawable.startTouchDownAnimation(0);
        }
        if (isStableStart()) {
            this.mEVCaptureRatio = 1.0f;
            showSuccess();
        } else {
            this.mEVCaptureRatio = -1.0f;
            this.mCenterFlag = 0;
            processParameterIfNeeded(0.0f);
            this.mHandler.sendEmptyMessageDelayed(4, 5000);
        }
        invalidate();
    }

    @Override // com.android.camera.ui.FocusIndicator
    public void showSuccess() {
        Log.d(TAG, "showSuccess");
        if (this.mState == 1) {
            clearMessages();
            setDraw(true);
            this.mState = 2;
            this.mSuccessTime = SystemClock.uptimeMillis();
            if (!this.mIsTouchFocus) {
                this.mHandler.sendEmptyMessageDelayed(5, 800);
            } else {
                this.mHandler.sendEmptyMessageDelayed(8, 1500);
            }
        }
        ExposureViewListener exposureViewListener = this.mExposureViewListener;
        if (exposureViewListener == null) {
            Log.d(TAG, "needExposurePresenter");
            return;
        }
        if (exposureViewListener.isShowAeAfLockIndicator() && CameraSettings.isAEAFLockSupport()) {
            int i = R.string.hint_ae_af_lock;
            if (this.mExposureViewListener.isSupportAELockOnly()) {
                i = R.string.hint_ae_lock;
            }
            if (c.Pl() && DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_EN_FIRST_CHOICE_LOCK_AE_AF_TOAST, false) && getResources().getConfiguration().getLocales().get(0).getLanguage().equalsIgnoreCase("en")) {
                DataRepository.dataItemGlobal().editor().putBoolean(CameraSettings.KEY_EN_FIRST_CHOICE_LOCK_AE_AF_TOAST, false).apply();
                i = R.string.hint_ae_af_lock_extra;
            }
            if (!this.mIsRecording || !CameraSettings.isSubtitleEnabled(this.mCurrentMode)) {
                showTipMessage(8, i);
            }
            this.mCenterFlag = 5;
            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.Manual.AE_AF_AWB_LOCKED, "CENTER_LOCK", null);
        } else if (!this.mExposureViewListener.isShowCaptureButton() || !this.mExposureViewListener.isSupportFocusShoot()) {
            this.mCenterFlag = 1;
        } else {
            this.mCenterFlag = 2;
        }
        int i2 = this.mCurrentMode;
        if (i2 != 180 && i2 != 167) {
            this.mCameraFocusAnimateDrawable.startFocusSuccessAnimation(this.mCenterFlag, this.mIsTouchFocus);
        } else if (this.mMode == 1) {
            this.mCameraFocusSplitAnimateDrawable.startFocusSuccessAnimation(this.mCenterFlag, this.mIsTouchFocus, 0);
        } else {
            int i3 = this.mCenterFlag;
            if (i3 == 5) {
                this.mMode = 1;
                this.mCameraFocusSplitAnimateDrawable.startFocusSuccessAnimation(i3, this.mIsTouchFocus, 0);
            }
        }
    }

    public void updateFocusMode(String str) {
        this.mFocusMode = str;
        this.mCameraFocusSplitAnimateDrawable.setFocusMode(str);
    }
}
