package com.android.camera.ui.drawable.snap;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import androidx.annotation.DrawableRes;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.bottom.BottomAnimationConfig;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.drawable.CameraPaintBase;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import java.util.ArrayList;
import java.util.List;
import miui.view.animation.CubicEaseInInterpolator;
import miui.view.animation.CubicEaseOutInterpolator;

public class CameraSnapAnimateDrawable extends Drawable implements Animatable {
    private static final float SNAP_ROUND_ORIGINAL_WIDTH = 0.65f;
    private static final SpringConfig mCameraDownConfig = SpringConfig.fromOrigamiTensionAndFriction(100.0d, 15.0d);
    public static final SpringConfig mCameraUpConfig = SpringConfig.fromOrigamiTensionAndFriction(120.0d, 30.0d);
    public static final SpringConfig mCameraUpSplashConfig = SpringConfig.fromOrigamiTensionAndFriction(180.0d, 10.0d);
    public static final SpringConfig mRecordScaleConfig = SpringConfig.fromOrigamiTensionAndFriction(180.0d, 30.0d);
    private CameraSnapPaintBottom mBottomPatinItem;
    private CameraSnapPaintCenterVV mCenterVVPaintItem;
    private CameraSnapPaintCircle mCirclePaintItem;
    private Context mContext;
    private Spring mDownSpring;
    private float mLiveSpeed;
    private long mLiveStartTime;
    private long mLiveTotalTime;
    private LongPressIncreaseListener mLongPressIncreaseListener = new LongPressIncreaseListener() {
        /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass16 */

        @Override // com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.LongPressIncreaseListener
        public void OnIncrease(float f) {
            CameraSnapAnimateDrawable.this.mRecordSpring.setEndValue(Math.min(1.1d, ((((double) f) - 0.6d) / 1.0d) + 0.6000000238418579d));
            CameraSnapAnimateDrawable.this.invalidateSelf();
        }

        @Override // com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.LongPressIncreaseListener
        public void OnTheValue(boolean z) {
            if (z) {
                CameraSnapAnimateDrawable.this.mUpSpring.setSpringConfig(CameraSnapAnimateDrawable.mCameraUpSplashConfig);
                CameraSnapAnimateDrawable.this.mUpSpring.setEndValue(1.0d);
                CameraSnapAnimateDrawable.this.mRecordSpring.setEndValue(0.6d);
            }
        }

        @Override // com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.LongPressIncreaseListener
        public void OnValueUp(float f) {
            double d2 = (double) f;
            CameraSnapAnimateDrawable.this.mUpSpring.setEndValue(d2);
            CameraSnapAnimateDrawable.this.mRecordSpring.setEndValue(d2);
        }
    };
    private ValueAnimator mModeChangeAnimator;
    private ValueAnimator mReboundAnimator;
    public Spring mRecordSpring;
    private List<CameraPaintBase> mRecordingReferredPaints;
    private ValueAnimator mRingAnimator;
    private CameraSnapPaintRound mRoundPaintItem;
    private ValueAnimator mScaleDownAnimator;
    private ValueAnimator mScaleUpAnimator;
    private CameraSnapPaintSecond mSecondPaintItem;
    private SpringSystem mSpringSystem;
    private ValueAnimator mStopAnimator;
    private ValueAnimator mTimeAnimator;
    public Spring mUpSpring;

    public interface BeautyRecordingListener {
        void onAngleChanged(float f);
    }

    public interface LongPressIncreaseListener {
        void OnIncrease(float f);

        void OnTheValue(boolean z);

        void OnValueUp(float f);
    }

    public CameraSnapAnimateDrawable(Context context) {
        this.mContext = context;
        this.mCirclePaintItem = new CameraSnapPaintCircle(context);
        this.mRoundPaintItem = new CameraSnapPaintRound(context);
        this.mSecondPaintItem = new CameraSnapPaintSecond(context);
        this.mBottomPatinItem = new CameraSnapPaintBottom(context);
        this.mCenterVVPaintItem = new CameraSnapPaintCenterVV(context);
        this.mRecordingReferredPaints = new ArrayList();
    }

    private void cancelScaleDownAnimation() {
        ValueAnimator valueAnimator = this.mScaleDownAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mScaleDownAnimator.cancel();
            this.mScaleDownAnimator = null;
        }
    }

    private void cancelScaleUpAnimation() {
        ValueAnimator valueAnimator = this.mScaleUpAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mScaleUpAnimator.cancel();
            this.mScaleUpAnimator = null;
        }
    }

    private void initReboundSystem() {
        if (this.mSpringSystem == null) {
            this.mSpringSystem = SpringSystem.create();
            this.mUpSpring = this.mSpringSystem.createSpring();
            this.mUpSpring.setSpringConfig(mCameraUpConfig);
            this.mUpSpring.addListener(new SimpleSpringListener() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass14 */

                @Override // com.facebook.rebound.SpringListener, com.facebook.rebound.SimpleSpringListener
                public void onSpringAtRest(Spring spring) {
                    if (spring.getCurrentValue() == 0.0d) {
                        CameraSnapAnimateDrawable.this.mUpSpring.setSpringConfig(CameraSnapAnimateDrawable.mCameraUpConfig);
                    }
                }

                @Override // com.facebook.rebound.SpringListener, com.facebook.rebound.SimpleSpringListener
                public void onSpringUpdate(Spring spring) {
                    float mapValueFromRangeToRange = (float) SpringUtil.mapValueFromRangeToRange((double) ((float) spring.getCurrentValue()), 0.0d, 1.0d, 1.0d, 1.2d);
                    CameraSnapAnimateDrawable.this.mCirclePaintItem.isRecording = true;
                    CameraSnapAnimateDrawable.this.mCirclePaintItem.isInBeautyMode = true;
                    CameraSnapAnimateDrawable.this.mCirclePaintItem.mCurrentWidthPercent = CameraSnapAnimateDrawable.this.mCirclePaintItem.mSrcWidthPercent * mapValueFromRangeToRange;
                    CameraSnapAnimateDrawable.this.invalidateSelf();
                }
            });
            this.mRecordSpring = this.mSpringSystem.createSpring();
            this.mRecordSpring.setSpringConfig(mRecordScaleConfig);
            this.mRecordSpring.setCurrentValue(1.0d);
            this.mRecordSpring.addListener(new SimpleSpringListener() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass15 */

                @Override // com.facebook.rebound.SpringListener, com.facebook.rebound.SimpleSpringListener
                public void onSpringUpdate(Spring spring) {
                    float currentValue = (float) spring.getCurrentValue();
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.mCurrentWidthPercent = CameraSnapAnimateDrawable.this.mRoundPaintItem.mSrcWidthPercent * currentValue;
                    CameraSnapAnimateDrawable.this.invalidateSelf();
                }
            });
        }
    }

    private boolean isInLongExpose(BottomAnimationConfig bottomAnimationConfig) {
        ValueAnimator valueAnimator;
        return bottomAnimationConfig.mIsLongExpose && bottomAnimationConfig.mIsTimerBurstCircle && (valueAnimator = this.mTimeAnimator) != null && valueAnimator.isRunning();
    }

    private void onLongExposeFinish(BottomAnimationConfig bottomAnimationConfig) {
        CameraSnapPaintBottom cameraSnapPaintBottom = this.mBottomPatinItem;
        cameraSnapPaintBottom.setTargetAlpha(cameraSnapPaintBottom.mBaseAlpha);
        if (isFullScreen()) {
            this.mRoundPaintItem.setTargetAlpha(0);
            this.mRoundPaintItem.prepareRecord(true, false);
            CameraSnapPaintCircle cameraSnapPaintCircle = this.mCirclePaintItem;
            cameraSnapPaintCircle.setTargetStrokeWidth(cameraSnapPaintCircle.mBaseStokeWidth);
            CameraSnapPaintCircle cameraSnapPaintCircle2 = this.mCirclePaintItem;
            cameraSnapPaintCircle2.setTargetWidthPercent(cameraSnapPaintCircle2.mBaseWidthPercent);
            return;
        }
        CameraSnapPaintRound cameraSnapPaintRound = this.mRoundPaintItem;
        cameraSnapPaintRound.setTargetAlpha(cameraSnapPaintRound.mBaseAlpha);
        CameraSnapPaintRound cameraSnapPaintRound2 = this.mRoundPaintItem;
        cameraSnapPaintRound2.setTargetWidthPercent(cameraSnapPaintRound2.mBaseWidthPercent);
        this.mCirclePaintItem.setBaseWidthPercent(0.61f);
        CameraSnapPaintCircle cameraSnapPaintCircle3 = this.mCirclePaintItem;
        cameraSnapPaintCircle3.setTargetWidthPercent(cameraSnapPaintCircle3.mBaseWidthPercent);
        this.mCirclePaintItem.setTargetAlpha(0);
        this.mCirclePaintItem.setResult();
    }

    private void onLongExposePrepare(BottomAnimationConfig bottomAnimationConfig) {
        this.mBottomPatinItem.setTargetAlpha(0);
        this.mBottomPatinItem.setResult();
        if (bottomAnimationConfig.mIsTimerBurstCircle) {
            this.mRoundPaintItem.prepareRecord(true, true);
        } else {
            this.mRoundPaintItem.setTargetAlpha(0);
            this.mRoundPaintItem.setResult();
        }
        if (!isFullScreen()) {
            this.mCirclePaintItem.setBaseWidthPercent(0.69f);
            this.mCirclePaintItem.setTargetWidthPercent(0.69f);
            CameraSnapPaintCircle cameraSnapPaintCircle = this.mCirclePaintItem;
            cameraSnapPaintCircle.setTargetStrokeWidth(cameraSnapPaintCircle.mBaseStokeWidth);
        }
        this.mCirclePaintItem.setNeedSplit(true);
        this.mCirclePaintItem.clearSegments();
        this.mCirclePaintItem.setTargetAlpha(CameraPaintBase.ALPHA_HINT);
    }

    private void updateLiveAnimationConfig() {
        ModeProtocol.LiveSpeedChanges liveSpeedChanges = (ModeProtocol.LiveSpeedChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(232);
        if (liveSpeedChanges != null) {
            this.mLiveSpeed = liveSpeedChanges.getRecordSpeed();
            this.mLiveTotalTime = liveSpeedChanges.getTotalRecordingTime();
            this.mLiveStartTime = liveSpeedChanges.getStartRecordingTime();
        }
    }

    public void addSegmentNow() {
        ValueAnimator valueAnimator = this.mTimeAnimator;
        if (valueAnimator != null) {
            this.mCirclePaintItem.addSegmentNow(valueAnimator.getCurrentPlayTime());
            invalidateSelf();
        }
    }

    public void cancelAnimation() {
        ValueAnimator valueAnimator = this.mTimeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mTimeAnimator = null;
        }
        ValueAnimator valueAnimator2 = this.mRingAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
            this.mRingAnimator = null;
        }
    }

    public void cancelRebound() {
        ValueAnimator valueAnimator = this.mReboundAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            CameraSnapPaintCircle cameraSnapPaintCircle = this.mCirclePaintItem;
            cameraSnapPaintCircle.isInBeautyMode = false;
            cameraSnapPaintCircle.resetRecordingState();
            this.mUpSpring.setEndValue(0.0d);
            this.mRecordSpring.setEndValue(1.0d);
        }
    }

    public void clearBitmap() {
        this.mRoundPaintItem.clearBitmap();
        invalidateSelf();
    }

    public void directFinishRecord() {
        cancelAnimation();
        List<CameraPaintBase> list = this.mRecordingReferredPaints;
        if (list != null) {
            this.mRoundPaintItem.isRecording = false;
            for (CameraPaintBase cameraPaintBase : list) {
                cameraPaintBase.setCurrentValues(cameraPaintBase.mCurrentWidthPercent, cameraPaintBase.mCurrentColor, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(1.0f));
                cameraPaintBase.isRecording = false;
                cameraPaintBase.resetRecordingState();
            }
            this.mRecordingReferredPaints.clear();
        }
    }

    public void draw(Canvas canvas) {
        if (canvas != null) {
            canvas.save();
            this.mBottomPatinItem.drawCanvas(canvas);
            canvas.restore();
            canvas.save();
            this.mRoundPaintItem.drawCanvas(canvas);
            canvas.restore();
            canvas.save();
            this.mCirclePaintItem.drawCanvas(canvas);
            canvas.restore();
            canvas.save();
            this.mCenterVVPaintItem.drawCanvas(canvas);
            canvas.restore();
            canvas.save();
            this.mSecondPaintItem.drawCanvas(canvas);
            canvas.restore();
        }
    }

    public void finishRecord(BottomAnimationConfig bottomAnimationConfig) {
        if (this.mRecordingReferredPaints == null || bottomAnimationConfig.mIsInMimojiCreate) {
            invalidateSelf();
        } else if (bottomAnimationConfig.mNeedFinishRecord) {
            cancelAnimation();
            for (CameraPaintBase cameraPaintBase : this.mRecordingReferredPaints) {
                cameraPaintBase.resetRecordingState();
                cameraPaintBase.setTargetAlpha(255);
            }
            this.mRoundPaintItem.setTargetAlpha(255);
            this.mTimeAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mTimeAnimator.setStartDelay(200);
            this.mTimeAnimator.setDuration(260L);
            this.mTimeAnimator.setInterpolator(new CubicEaseInInterpolator() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass12 */

                @Override // miui.view.animation.CubicEaseInInterpolator
                public float getInterpolation(float f) {
                    float interpolation = super.getInterpolation(f);
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                    for (CameraPaintBase cameraPaintBase : CameraSnapAnimateDrawable.this.mRecordingReferredPaints) {
                        cameraPaintBase.updateValue(interpolation);
                    }
                    CameraSnapAnimateDrawable.this.invalidateSelf();
                    return interpolation;
                }
            });
            this.mTimeAnimator.removeAllListeners();
            this.mTimeAnimator.addListener(new Animator.AnimatorListener() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass13 */

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.isRecording = false;
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                }
            });
            this.mTimeAnimator.start();
        } else {
            stopRecord(bottomAnimationConfig);
        }
    }

    public int getOpacity() {
        return -1;
    }

    public boolean hasSegments() {
        return this.mCirclePaintItem.hasSegments();
    }

    public void hideCirclePaintItem() {
        this.mCirclePaintItem.setVisible(8);
    }

    public void hidePaintCenterVVItem() {
        this.mCenterVVPaintItem.setVisible(8);
    }

    public void hideRoundPaintItem() {
        this.mRoundPaintItem.setVisible(8);
    }

    public void intoPattern(PaintConditionReferred paintConditionReferred) {
        this.mCenterVVPaintItem.setTargetAlpha(0);
        this.mCenterVVPaintItem.setVisible(8);
        int i = paintConditionReferred.targetMode;
        if (!(i == 161 || i == 162)) {
            if (i != 169) {
                if (i != 187) {
                    if (i != 204) {
                        if (i == 254) {
                            return;
                        }
                        if (!(i == 179 || i == 180)) {
                            if (i == 209 || i == 210) {
                                this.mCirclePaintItem.setTargetValues(0.69f, -1, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(5.0f));
                                this.mRoundPaintItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -1, 0, 15.0f);
                                this.mSecondPaintItem.setTargetValues(0.813f, -1, 0, 3.0f);
                                this.mSecondPaintItem.clearPatternAndExternal();
                                this.mBottomPatinItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -13750738, 0, 3.0f);
                                this.mCenterVVPaintItem.setTargetAlpha(CameraPaintBase.ALPHA_OPAQUE);
                                this.mCenterVVPaintItem.setVisible(0);
                                this.mCenterVVPaintItem.intoStartShotPattern(this.mContext);
                                return;
                            }
                            switch (i) {
                                case 172:
                                    this.mCirclePaintItem.setTargetValues(0.51f, -50630, 0, (float) Util.dpToPixel(5.0f));
                                    this.mRoundPaintItem.setTargetValues(0.56f, -50630, CameraPaintBase.ALPHA_OPAQUE, 15.0f);
                                    this.mSecondPaintItem.setTargetValues(0.751f, -1, CameraPaintBase.ALPHA_OUTSTANDING, (float) Util.dpToPixel(1.0f));
                                    if (paintConditionReferred.isFPS960) {
                                        this.mSecondPaintItem.intoSlowPattern();
                                    } else {
                                        this.mSecondPaintItem.intoProgressPattern();
                                    }
                                    this.mBottomPatinItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -13750738, 0, (float) Util.dpToPixel(1.0f));
                                    return;
                                case 173:
                                    break;
                                case 174:
                                    break;
                                default:
                                    switch (i) {
                                        case 183:
                                        case 185:
                                            break;
                                        case 184:
                                            if (paintConditionReferred.forceVideoPattern) {
                                                this.mCirclePaintItem.setTargetValues(0.69f, -1, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(5.0f));
                                                this.mRoundPaintItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -50630, CameraPaintBase.ALPHA_OPAQUE, 15.0f);
                                                this.mSecondPaintItem.setTargetValues(0.813f, -1, 0, 3.0f);
                                                this.mSecondPaintItem.clearPatternAndExternal();
                                                this.mBottomPatinItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -13750738, 0, 3.0f);
                                                return;
                                            } else if (paintConditionReferred.bottomHalfScreen()) {
                                                this.mCirclePaintItem.setTargetValues(0.61f, -1, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(5.0f));
                                                this.mRoundPaintItem.setTargetValues(0.6637f, -1, CameraPaintBase.ALPHA_OPAQUE, 15.0f);
                                                this.mSecondPaintItem.setTargetValues(0.813f, -1, 0, 3.0f);
                                                this.mSecondPaintItem.clearPatternAndExternal();
                                                this.mBottomPatinItem.setTargetValues(0.813f, -13750738, CameraPaintBase.ALPHA_OPAQUE, 3.0f);
                                                return;
                                            } else {
                                                this.mCirclePaintItem.setTargetValues(0.69f, -1, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(5.0f));
                                                this.mRoundPaintItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -1, 0, 15.0f);
                                                this.mSecondPaintItem.setTargetValues(0.813f, -1, 0, 3.0f);
                                                this.mSecondPaintItem.clearPatternAndExternal();
                                                this.mBottomPatinItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -13750738, 0, 3.0f);
                                                return;
                                            }
                                        default:
                                            if (paintConditionReferred.bottomHalfScreen()) {
                                                this.mCirclePaintItem.setTargetValues(0.61f, -1, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(5.0f));
                                                this.mRoundPaintItem.setTargetValues(0.6637f, -1, CameraPaintBase.ALPHA_OPAQUE, 15.0f);
                                                this.mSecondPaintItem.setTargetValues(0.813f, -1, 0, 3.0f);
                                                this.mSecondPaintItem.clearPatternAndExternal();
                                                this.mBottomPatinItem.setTargetValues(0.813f, -13750738, CameraPaintBase.ALPHA_OPAQUE, 3.0f);
                                                return;
                                            }
                                            this.mCirclePaintItem.setTargetValues(0.69f, -1, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(5.0f));
                                            this.mRoundPaintItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -1, 0, 15.0f);
                                            this.mSecondPaintItem.setTargetValues(0.813f, -1, 0, 3.0f);
                                            this.mSecondPaintItem.clearPatternAndExternal();
                                            this.mBottomPatinItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -13750738, 0, 3.0f);
                                            return;
                                    }
                            }
                        }
                    } else if (DataRepository.dataItemRunning().getComponentRunningDualVideo().ismShouldDraw6Patch()) {
                        this.mCirclePaintItem.setTargetValues(0.69f, -1, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(5.0f));
                        this.mRoundPaintItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -1, 0, 15.0f);
                        this.mSecondPaintItem.setTargetValues(0.813f, -1, 0, 3.0f);
                        this.mSecondPaintItem.clearPatternAndExternal();
                        this.mBottomPatinItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -13750738, 0, 3.0f);
                        this.mCenterVVPaintItem.setTargetAlpha(CameraPaintBase.ALPHA_OPAQUE);
                        this.mCenterVVPaintItem.setVisible(0);
                        this.mCenterVVPaintItem.intoStartShotPattern(this.mContext);
                        return;
                    } else {
                        this.mCirclePaintItem.setTargetValues(0.69f, -1, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(5.0f));
                        this.mRoundPaintItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -50630, CameraPaintBase.ALPHA_OPAQUE, 15.0f);
                        this.mSecondPaintItem.setTargetValues(0.813f, -1, 0, 3.0f);
                        this.mSecondPaintItem.clearPatternAndExternal();
                        this.mBottomPatinItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -13750738, 0, 3.0f);
                        return;
                    }
                }
                this.mCirclePaintItem.setTargetValues(0.51f, -1, 0, (float) Util.dpToPixel(5.0f));
                this.mRoundPaintItem.setTargetValues(0.56f, -1, CameraPaintBase.ALPHA_OPAQUE, 15.0f);
                this.mSecondPaintItem.setTargetValues(0.751f, -1, CameraPaintBase.ALPHA_OUTSTANDING, (float) Util.dpToPixel(1.0f));
                this.mSecondPaintItem.intoProgressPattern();
                this.mBottomPatinItem.setTargetValues(0.751f, -13750738, 0, (float) Util.dpToPixel(1.0f));
                return;
            }
            this.mCirclePaintItem.setTargetValues(0.51f, -50630, 0, (float) Util.dpToPixel(5.0f));
            this.mRoundPaintItem.setTargetValues(0.56f, -50630, CameraPaintBase.ALPHA_OPAQUE, 15.0f);
            this.mSecondPaintItem.setTargetValues(0.751f, -1, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(1.0f));
            this.mSecondPaintItem.intoFastPattern();
            this.mBottomPatinItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -13750738, 0, (float) Util.dpToPixel(1.0f));
            return;
        }
        this.mCirclePaintItem.setTargetValues(0.69f, -1, CameraPaintBase.ALPHA_OPAQUE, (float) Util.dpToPixel(5.0f));
        this.mRoundPaintItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -50630, CameraPaintBase.ALPHA_OPAQUE, 15.0f);
        this.mSecondPaintItem.setTargetValues(0.813f, -1, 0, 3.0f);
        this.mSecondPaintItem.clearPatternAndExternal();
        this.mBottomPatinItem.setTargetValues(SNAP_ROUND_ORIGINAL_WIDTH, -13750738, 0, 3.0f);
    }

    public void intoPatternFromParameters(PaintConditionReferred paintConditionReferred) {
        intoPattern(paintConditionReferred);
        this.mCirclePaintItem.setResult();
        this.mRoundPaintItem.setResult();
        this.mSecondPaintItem.setResult();
        this.mBottomPatinItem.setResult();
        this.mCenterVVPaintItem.setResult();
    }

    public boolean isBottomVisible() {
        return this.mBottomPatinItem.mBaseAlpha != 0;
    }

    public boolean isFullScreen() {
        return this.mRoundPaintItem.mBaseAlpha == 0;
    }

    public boolean isRunning() {
        ValueAnimator valueAnimator;
        ValueAnimator valueAnimator2 = this.mModeChangeAnimator;
        return (valueAnimator2 != null && valueAnimator2.isRunning()) || ((valueAnimator = this.mTimeAnimator) != null && valueAnimator.isRunning());
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
    }

    public void pauseRecording() {
        ValueAnimator valueAnimator = this.mTimeAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mTimeAnimator.pause();
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0063 A[FALL_THROUGH] */
    public void prepareRecording(BottomAnimationConfig bottomAnimationConfig) {
        this.mRecordingReferredPaints.clear();
        cancelAnimation();
        cancelScaleDownAnimation();
        cancelScaleUpAnimation();
        this.mRoundPaintItem.prepareRecord(false, true);
        CameraSnapPaintRound cameraSnapPaintRound = this.mRoundPaintItem;
        cameraSnapPaintRound.isRecording = true;
        cameraSnapPaintRound.setTargetAlpha(255);
        if (bottomAnimationConfig.mIsTimerBurstCircle && !bottomAnimationConfig.mIsLongExpose) {
            bottomAnimationConfig.mCurrentMode = isFullScreen() ? 166 : 176;
        }
        int i = bottomAnimationConfig.mCurrentMode;
        float f = 0.0f;
        if (i != 166) {
            if (i != 167) {
                if (!(i == 169 || i == 187)) {
                    if (i != 204) {
                        if (i == 176) {
                            this.mBottomPatinItem.setTargetAlpha(0);
                            CameraSnapPaintBottom cameraSnapPaintBottom = this.mBottomPatinItem;
                            cameraSnapPaintBottom.setTargetWidthPercent(cameraSnapPaintBottom.mBaseWidthPercent * 0.85f);
                            this.mRecordingReferredPaints.add(this.mBottomPatinItem);
                            this.mRoundPaintItem.prepareRecord(true, true);
                            this.mCirclePaintItem.setBaseWidthPercent(0.69f);
                            this.mCirclePaintItem.setTargetWidthPercent(0.69f);
                            CameraSnapPaintCircle cameraSnapPaintCircle = this.mCirclePaintItem;
                            cameraSnapPaintCircle.setTargetStrokeWidth(cameraSnapPaintCircle.mBaseStokeWidth);
                            this.mCirclePaintItem.setNeedSplit(false);
                            this.mRecordingReferredPaints.add(this.mCirclePaintItem);
                        } else if (i != 177) {
                            if (i != 179) {
                                if (i != 180) {
                                    switch (i) {
                                        case 161:
                                            this.mCirclePaintItem.setNeedSplit(true);
                                            CameraSnapPaintCircle cameraSnapPaintCircle2 = this.mCirclePaintItem;
                                            cameraSnapPaintCircle2.setTargetStrokeWidth(cameraSnapPaintCircle2.mBaseStokeWidth * 0.8f);
                                            this.mCirclePaintItem.clearSegments();
                                            int i2 = bottomAnimationConfig.mCurrentMode;
                                            if (i2 == 177 || i2 == 184) {
                                                CameraSnapPaintCircle cameraSnapPaintCircle3 = this.mCirclePaintItem;
                                                cameraSnapPaintCircle3.mCurrentWidthPercent *= cameraSnapPaintCircle3.mBaseWidthPercent * 1.2f;
                                            }
                                            this.mCirclePaintItem.setTargetAlpha(CameraPaintBase.ALPHA_HINT);
                                            this.mRecordingReferredPaints.add(this.mCirclePaintItem);
                                            break;
                                        case 162:
                                            break;
                                        case 163:
                                            break;
                                        default:
                                            switch (i) {
                                                case 172:
                                                case 173:
                                                    break;
                                                default:
                                                    switch (i) {
                                                    }
                                                case 174:
                                                    break;
                                            }
                                    }
                                }
                            } else {
                                this.mCirclePaintItem.setNeedSplit(true);
                                this.mCirclePaintItem.setSpaceAngel(0.0f);
                                this.mCirclePaintItem.clearSegments();
                                CameraSnapPaintCircle cameraSnapPaintCircle4 = this.mCirclePaintItem;
                                cameraSnapPaintCircle4.setTargetStrokeWidth(cameraSnapPaintCircle4.mBaseStokeWidth * 0.7f);
                                CameraSnapPaintCircle cameraSnapPaintCircle5 = this.mCirclePaintItem;
                                cameraSnapPaintCircle5.setTargetWidthPercent(cameraSnapPaintCircle5.mBaseWidthPercent * 1.18f);
                                this.mCirclePaintItem.setNeedProcessShade(true, this.mRoundPaintItem.mCurrentColor);
                                this.mRecordingReferredPaints.add(this.mCirclePaintItem);
                                this.mCenterVVPaintItem.setTargetAlpha(0);
                                CameraSnapPaintCenterVV cameraSnapPaintCenterVV = this.mCenterVVPaintItem;
                                cameraSnapPaintCenterVV.isRecording = true;
                                this.mRecordingReferredPaints.add(cameraSnapPaintCenterVV);
                            }
                        }
                    }
                    this.mCirclePaintItem.setNeedSplit(false);
                    this.mRoundPaintItem.setRecordingBgColor(-1);
                    CameraSnapPaintCircle cameraSnapPaintCircle6 = this.mCirclePaintItem;
                    cameraSnapPaintCircle6.setTargetWidthPercent(cameraSnapPaintCircle6.mBaseWidthPercent * 0.93f);
                    this.mRecordingReferredPaints.add(this.mCirclePaintItem);
                }
                this.mSecondPaintItem.removePatternOnly();
                this.mSecondPaintItem.setTargetAlpha(CameraPaintBase.ALPHA_HINT);
                this.mRecordingReferredPaints.add(this.mSecondPaintItem);
            } else if (bottomAnimationConfig.mIsLongExpose) {
                onLongExposePrepare(bottomAnimationConfig);
                this.mRecordingReferredPaints.add(this.mCirclePaintItem);
            }
            boolean z = this.mRoundPaintItem.mBaseAlpha == 0;
            this.mBottomPatinItem.setTargetWidthPercent(1.0569f);
            if (z) {
                this.mBottomPatinItem.setFixCircleOffset(false);
                this.mBottomPatinItem.setTargetAlpha(0);
            } else {
                this.mBottomPatinItem.setFixCircleOffset(true);
                this.mBottomPatinItem.setTargetAlpha(255);
            }
            this.mRoundPaintItem.setShowCenterBitmap(false);
            if (z) {
                CameraSnapPaintRound cameraSnapPaintRound2 = this.mRoundPaintItem;
                cameraSnapPaintRound2.isRecording = false;
                cameraSnapPaintRound2.setTargetAlpha(0);
            } else {
                CameraSnapPaintRound cameraSnapPaintRound3 = this.mRoundPaintItem;
                cameraSnapPaintRound3.isRecording = false;
                cameraSnapPaintRound3.setCurrentWidthPercent(this.mCirclePaintItem.mCurrentWidthPercent);
                this.mRoundPaintItem.setTargetAlpha(255);
                this.mRoundPaintItem.setTargetWidthPercent(this.mCirclePaintItem.mBaseWidthPercent * 0.68f);
            }
            this.mCirclePaintItem.setNeedSplit(false);
            CameraSnapPaintCircle cameraSnapPaintCircle7 = this.mCirclePaintItem;
            cameraSnapPaintCircle7.setTargetStrokeWidth(cameraSnapPaintCircle7.mBaseStokeWidth);
            if (z) {
                this.mCirclePaintItem.setTargetWidthPercent(1.0569f);
                this.mCirclePaintItem.setTargetAlpha(51);
            } else {
                CameraSnapPaintCircle cameraSnapPaintCircle8 = this.mCirclePaintItem;
                cameraSnapPaintCircle8.setTargetWidthPercent(cameraSnapPaintCircle8.mBaseWidthPercent * 0.68f);
            }
            this.mRecordingReferredPaints.add(this.mBottomPatinItem);
            this.mRecordingReferredPaints.add(this.mCirclePaintItem);
        } else {
            this.mBottomPatinItem.setTargetAlpha(0);
            this.mBottomPatinItem.setResult();
            this.mRoundPaintItem.prepareRecord(true, true);
            this.mRecordingReferredPaints.add(this.mCirclePaintItem);
        }
        float[] fArr = new float[2];
        if (bottomAnimationConfig.mIsInMimojiCreate) {
            f = 1.0f;
        }
        fArr[0] = f;
        fArr[1] = 1.0f;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
        ofFloat.setDuration(300L);
        ofFloat.setInterpolator(new CubicEaseOutInterpolator() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass6 */

            @Override // miui.view.animation.CubicEaseOutInterpolator
            public float getInterpolation(float f) {
                float interpolation = super.getInterpolation(f);
                for (CameraPaintBase cameraPaintBase : CameraSnapAnimateDrawable.this.mRecordingReferredPaints) {
                    cameraPaintBase.updateValue(interpolation);
                }
                CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.invalidateSelf();
                return interpolation;
            }
        });
        ofFloat.start();
    }

    public void removeLastSegment() {
        if (!this.mCirclePaintItem.getSegmentRatios().isEmpty()) {
            this.mCirclePaintItem.removeLastSegmentAndGetLastTime();
            invalidateSelf();
        }
    }

    public void resetRecordingState() {
        this.mCirclePaintItem.resetRecordingState();
        this.mRoundPaintItem.resetRecordingState();
        this.mSecondPaintItem.resetRecordingState();
        this.mBottomPatinItem.resetRecordingState();
        this.mCenterVVPaintItem.resetRecordingState();
    }

    public void resumeRecording() {
        updateLiveAnimationConfig();
        ValueAnimator valueAnimator = this.mTimeAnimator;
        if (valueAnimator != null && valueAnimator.isPaused()) {
            this.mTimeAnimator.resume();
        }
    }

    public void setAlpha(int i) {
    }

    public void setBounds(Rect rect) {
        super.setBounds(rect);
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setDurationText(String str) {
        this.mCenterVVPaintItem.setDurationText(str);
        invalidateSelf();
    }

    public void setStopButtonEnable(boolean z, boolean z2) {
        if (z) {
            this.mRoundPaintItem.setCurrentAlpha(255);
            if (z2) {
                this.mRoundPaintItem.setTargetAlpha(255);
                return;
            }
            return;
        }
        this.mRoundPaintItem.setCurrentAlpha(77);
        this.mRoundPaintItem.setTargetAlpha(77);
    }

    public void setWidthHeight(float f, float f2) {
        float f3 = f / 2.0f;
        float min = Math.min(f, f2) / 2.0f;
        this.mCirclePaintItem.setMiddle(f3, f3, min);
        this.mRoundPaintItem.setMiddle(f3, f3, min);
        this.mSecondPaintItem.setMiddle(f3, f3, min);
        this.mBottomPatinItem.setMiddle(f3, f3, min);
        this.mCenterVVPaintItem.setMiddle(f3, f3, min);
    }

    public void showBitmap(Context context, @DrawableRes int i) {
        this.mRoundPaintItem.showTargetBitmap(context, i);
        invalidateSelf();
    }

    public void showCirclePaintItem() {
        this.mCirclePaintItem.setVisible(0);
    }

    public void showPaintCenterVVItem() {
        CameraSnapPaintCenterVV cameraSnapPaintCenterVV = this.mCenterVVPaintItem;
        CameraSnapPaintRound cameraSnapPaintRound = this.mRoundPaintItem;
        cameraSnapPaintCenterVV.setTargetValues(cameraSnapPaintRound.mCurrentWidthPercent, cameraSnapPaintRound.mCurrentColor, cameraSnapPaintRound.mCurrentAlpha, cameraSnapPaintRound.mCurrentStrokeWidth);
        this.mCenterVVPaintItem.setResult();
        this.mCenterVVPaintItem.prepareRecord();
        this.mCenterVVPaintItem.setVisible(0);
    }

    public void showRoundPaintItem() {
        this.mRoundPaintItem.setVisible(0);
    }

    public void start() {
    }

    public void startModeChangeAnimation() {
        this.mModeChangeAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mModeChangeAnimator.setDuration(300L);
        this.mModeChangeAnimator.setInterpolator(new DecelerateInterpolator() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass1 */

            public float getInterpolation(float f) {
                float interpolation = super.getInterpolation(f);
                CameraSnapAnimateDrawable.this.mCirclePaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.mSecondPaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.mBottomPatinItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.mCenterVVPaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.invalidateSelf();
                return interpolation;
            }
        });
        this.mModeChangeAnimator.setupEndValues();
        this.mModeChangeAnimator.start();
    }

    public void startRebound() {
        initReboundSystem();
        this.mReboundAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mReboundAnimator.setStartDelay(300);
        this.mReboundAnimator.setDuration(8500L);
        this.mReboundAnimator.setInterpolator(new LinearInterpolator() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass17 */

            public float getInterpolation(float f) {
                float interpolation = super.getInterpolation(f);
                float f2 = 8500.0f * interpolation;
                if (f2 > 500.0f) {
                    CameraSnapAnimateDrawable.this.mCirclePaintItem.timeAngle = ((f2 - 500.0f) / 8000.0f) * 360.0f;
                }
                if (f2 <= 1000.0f) {
                    CameraSnapAnimateDrawable.this.mLongPressIncreaseListener.OnValueUp((((f2 / 31.25f) * 2.0f) - 10.0f) / 90.0f);
                    CameraSnapAnimateDrawable.this.mLongPressIncreaseListener.OnTheValue(false);
                } else if (f2 - 1000.0f < 30.0f) {
                    CameraSnapAnimateDrawable.this.mLongPressIncreaseListener.OnTheValue(true);
                } else {
                    CameraSnapAnimateDrawable.this.mLongPressIncreaseListener.OnIncrease((((f2 / 31.25f) * 2.0f) - 10.0f) / 90.0f);
                }
                return interpolation;
            }
        });
        this.mReboundAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass18 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                ((ModeProtocol.BeautyRecording) ModeCoordinatorImpl.getInstance().getAttachProtocol(173)).handleBeautyRecordingStop();
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
                ((ModeProtocol.BeautyRecording) ModeCoordinatorImpl.getInstance().getAttachProtocol(173)).handleBeautyRecordingStart();
            }
        });
        this.mReboundAnimator.start();
    }

    public void startRecord(final BottomAnimationConfig bottomAnimationConfig) {
        if (this.mRecordingReferredPaints != null) {
            cancelAnimation();
            this.mTimeAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            int i = bottomAnimationConfig.mCurrentMode;
            if (i == 174 || i == 183) {
                updateLiveAnimationConfig();
            }
            this.mTimeAnimator.setDuration((long) bottomAnimationConfig.mDuration);
            this.mTimeAnimator.setInterpolator(new LinearInterpolator() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass7 */

                public float getInterpolation(float f) {
                    float f2 = f * 360.0f;
                    int i = bottomAnimationConfig.mCurrentMode;
                    if (i == 174 || i == 183) {
                        f2 = (((float) (((long) ((((double) (System.currentTimeMillis() - CameraSnapAnimateDrawable.this.mLiveStartTime)) * 1.0d) / ((double) CameraSnapAnimateDrawable.this.mLiveSpeed))) + CameraSnapAnimateDrawable.this.mLiveTotalTime)) * 360.0f) / ((float) bottomAnimationConfig.mDuration);
                        if (f2 > 360.0f) {
                            f2 = 360.0f;
                        }
                    }
                    for (CameraPaintBase cameraPaintBase : CameraSnapAnimateDrawable.this.mRecordingReferredPaints) {
                        cameraPaintBase.timeAngle = f2;
                    }
                    float interpolation = super.getInterpolation(f);
                    CameraSnapAnimateDrawable.this.invalidateSelf();
                    return interpolation;
                }
            });
            this.mTimeAnimator.removeAllListeners();
            this.mTimeAnimator.addListener(new Animator.AnimatorListener() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass8 */

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    ModeProtocol.AmbilightProtocol ambilightProtocol;
                    if (bottomAnimationConfig.mCurrentMode == 187 && (ambilightProtocol = (ModeProtocol.AmbilightProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(420)) != null) {
                        ambilightProtocol.onShutterAnimationEnd();
                    }
                    if (bottomAnimationConfig.mIsLongExpose) {
                        CameraSnapAnimateDrawable.this.mCirclePaintItem.setTargetAlpha(CameraSnapAnimateDrawable.this.mCirclePaintItem.mBaseAlpha);
                        CameraSnapAnimateDrawable.this.mCirclePaintItem.setResult();
                        CameraSnapAnimateDrawable.this.mCirclePaintItem.setNeedSplit(false);
                        CameraSnapAnimateDrawable.this.mCirclePaintItem.clearSegments();
                    }
                }

                public void onAnimationRepeat(Animator animator) {
                    int i = bottomAnimationConfig.mCurrentMode;
                    if (!(i == 174 || i == 183)) {
                        for (CameraPaintBase cameraPaintBase : CameraSnapAnimateDrawable.this.mRecordingReferredPaints) {
                            cameraPaintBase.reverseClock();
                        }
                    }
                }

                public void onAnimationStart(Animator animator) {
                    for (CameraPaintBase cameraPaintBase : CameraSnapAnimateDrawable.this.mRecordingReferredPaints) {
                        cameraPaintBase.isRecording = true;
                    }
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.isRecordingCircle = bottomAnimationConfig.mIsRecordingCircle;
                    CameraSnapPaintRound cameraSnapPaintRound = CameraSnapAnimateDrawable.this.mRoundPaintItem;
                    BottomAnimationConfig bottomAnimationConfig = bottomAnimationConfig;
                    cameraSnapPaintRound.isRoundingCircle = bottomAnimationConfig.mIsRoundingCircle;
                    if (bottomAnimationConfig.mIsLongExpose) {
                        CameraSnapAnimateDrawable.this.mCirclePaintItem.setTargetAlpha(CameraPaintBase.ALPHA_HINT);
                        CameraSnapAnimateDrawable.this.mCirclePaintItem.setNeedSplit(true);
                        CameraSnapAnimateDrawable.this.mCirclePaintItem.clearSegments();
                    }
                }
            });
            if (bottomAnimationConfig.mShouldRepeat) {
                this.mTimeAnimator.setRepeatMode(1);
                this.mTimeAnimator.setRepeatCount(-1);
            }
            this.mTimeAnimator.start();
        }
    }

    public void startRecordAnimation(BottomAnimationConfig bottomAnimationConfig) {
        if (bottomAnimationConfig.mIsStart) {
            startRecord(bottomAnimationConfig);
        } else {
            finishRecord(bottomAnimationConfig);
        }
    }

    public void startRingAnimation() {
        cancelAnimation();
        this.mCirclePaintItem.setRingVisible(0);
        this.mRoundPaintItem.setVisible(8);
        this.mRingAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mRingAnimator.setDuration(500L);
        this.mRingAnimator.setRepeatCount(-1);
        this.mRingAnimator.setRepeatMode(1);
        this.mRingAnimator.setInterpolator(new BounceInterpolator() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass5 */

            public float getInterpolation(float f) {
                float interpolation = super.getInterpolation(f);
                CameraSnapAnimateDrawable.this.mCirclePaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.invalidateSelf();
                return interpolation;
            }
        });
        this.mRingAnimator.start();
    }

    public void startScaleDownAnimation(int i) {
        cancelScaleUpAnimation();
        if (isFullScreen()) {
            CameraSnapPaintCircle cameraSnapPaintCircle = this.mCirclePaintItem;
            cameraSnapPaintCircle.setTargetWidthPercent(cameraSnapPaintCircle.mBaseWidthPercent * 0.88f);
            CameraSnapPaintRound cameraSnapPaintRound = this.mRoundPaintItem;
            cameraSnapPaintRound.setTargetWidthPercent(cameraSnapPaintRound.mBaseWidthPercent * 0.88f);
            CameraSnapPaintCircle cameraSnapPaintCircle2 = this.mCirclePaintItem;
            cameraSnapPaintCircle2.setTargetStrokeWidth(cameraSnapPaintCircle2.mBaseStokeWidth * 1.1f);
        } else {
            CameraSnapPaintCircle cameraSnapPaintCircle3 = this.mCirclePaintItem;
            cameraSnapPaintCircle3.setTargetWidthPercent(cameraSnapPaintCircle3.mBaseWidthPercent * 0.88f);
            CameraSnapPaintRound cameraSnapPaintRound2 = this.mRoundPaintItem;
            cameraSnapPaintRound2.setTargetWidthPercent(cameraSnapPaintRound2.mBaseWidthPercent * 0.88f);
            CameraSnapPaintBottom cameraSnapPaintBottom = this.mBottomPatinItem;
            if (cameraSnapPaintBottom.mCurrentAlpha != 0) {
                cameraSnapPaintBottom.setTargetWidthPercent(cameraSnapPaintBottom.mBaseWidthPercent * 1.02f);
            }
        }
        if (this.mCenterVVPaintItem.showBitmapPattern()) {
            this.mCenterVVPaintItem.setBitmapPatternTargetScale(0.9f);
        }
        this.mRoundPaintItem.setBitmapPatternTargetScale(0.9f);
        this.mScaleDownAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mScaleDownAnimator.setDuration(200L);
        this.mScaleDownAnimator.setInterpolator(new CubicEaseOutInterpolator() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass2 */

            @Override // miui.view.animation.CubicEaseOutInterpolator
            public float getInterpolation(float f) {
                float interpolation = super.getInterpolation(f);
                CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.mCirclePaintItem.updateValue(interpolation);
                if (CameraSnapAnimateDrawable.this.mBottomPatinItem.mCurrentAlpha != 0) {
                    CameraSnapAnimateDrawable.this.mBottomPatinItem.updateValue(interpolation);
                }
                if (CameraSnapAnimateDrawable.this.mCenterVVPaintItem.showBitmapPattern()) {
                    CameraSnapAnimateDrawable.this.mCenterVVPaintItem.updateValue(interpolation);
                }
                CameraSnapAnimateDrawable.this.invalidateSelf();
                return interpolation;
            }
        });
        this.mScaleDownAnimator.setupEndValues();
        this.mScaleDownAnimator.start();
    }

    public void startScaleUpAnimation(long j, Animator.AnimatorListener animatorListener) {
        cancelScaleDownAnimation();
        CameraSnapPaintCircle cameraSnapPaintCircle = this.mCirclePaintItem;
        cameraSnapPaintCircle.setTargetWidthPercent(cameraSnapPaintCircle.mBaseWidthPercent);
        CameraSnapPaintCircle cameraSnapPaintCircle2 = this.mCirclePaintItem;
        cameraSnapPaintCircle2.setTargetStrokeWidth(cameraSnapPaintCircle2.mBaseStokeWidth);
        CameraSnapPaintRound cameraSnapPaintRound = this.mRoundPaintItem;
        cameraSnapPaintRound.setTargetWidthPercent(cameraSnapPaintRound.mBaseWidthPercent);
        CameraSnapPaintBottom cameraSnapPaintBottom = this.mBottomPatinItem;
        if (cameraSnapPaintBottom.mCurrentAlpha != 0) {
            cameraSnapPaintBottom.setTargetWidthPercent(cameraSnapPaintBottom.mBaseWidthPercent);
        }
        if (this.mCenterVVPaintItem.showBitmapPattern()) {
            this.mCenterVVPaintItem.setBitmapPatternTargetScale(1.0f);
        }
        this.mRoundPaintItem.setBitmapPatternTargetScale(1.0f);
        this.mScaleUpAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mScaleUpAnimator.setStartDelay(j);
        this.mScaleUpAnimator.setDuration(400L);
        this.mScaleUpAnimator.setInterpolator(new CubicEaseOutInterpolator() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass3 */

            @Override // miui.view.animation.CubicEaseOutInterpolator
            public float getInterpolation(float f) {
                float interpolation = super.getInterpolation(f);
                CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.mCirclePaintItem.updateValue(interpolation);
                if (CameraSnapAnimateDrawable.this.mBottomPatinItem.mCurrentAlpha != 0) {
                    CameraSnapAnimateDrawable.this.mBottomPatinItem.updateValue(interpolation);
                }
                if (CameraSnapAnimateDrawable.this.mCenterVVPaintItem.showBitmapPattern()) {
                    CameraSnapAnimateDrawable.this.mCenterVVPaintItem.updateValue(interpolation);
                }
                CameraSnapAnimateDrawable.this.invalidateSelf();
                return interpolation;
            }
        });
        this.mScaleUpAnimator.addListener(new AnimatorListenerAdapter() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass4 */

            public void onAnimationCancel(Animator animator) {
                CameraSnapAnimateDrawable.this.mScaleUpAnimator = null;
            }

            public void onAnimationEnd(Animator animator) {
                CameraSnapAnimateDrawable.this.mScaleUpAnimator = null;
            }

            public void onAnimationStart(Animator animator) {
            }
        });
        if (animatorListener != null) {
            this.mScaleUpAnimator.addListener(animatorListener);
        }
        this.mScaleUpAnimator.setupEndValues();
        this.mScaleUpAnimator.start();
    }

    public void stop() {
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01a3 A[LOOP:6: B:56:0x019d->B:58:0x01a3, LOOP_END] */
    public void stopRecord(final BottomAnimationConfig bottomAnimationConfig) {
        if (this.mRecordingReferredPaints != null) {
            boolean z = false;
            if (isInLongExpose(bottomAnimationConfig)) {
                this.mRoundPaintItem.setTargetAlpha(0);
                this.mRoundPaintItem.setResult();
                this.mTimeAnimator.addListener(new AnimatorListenerAdapter() {
                    /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass9 */

                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        CameraSnapAnimateDrawable.this.stopRecord(bottomAnimationConfig);
                    }
                });
                return;
            }
            cancelAnimation();
            cancelScaleDownAnimation();
            cancelScaleUpAnimation();
            this.mRoundPaintItem.prepareRecord(false, false);
            if (bottomAnimationConfig.mIsTimerBurstCircle && !bottomAnimationConfig.mIsLongExpose) {
                bottomAnimationConfig.mCurrentMode = isFullScreen() ? 166 : 176;
            }
            switch (bottomAnimationConfig.mCurrentMode) {
                case 161:
                case 174:
                case 183:
                case 184:
                case 185:
                    for (CameraPaintBase cameraPaintBase : this.mRecordingReferredPaints) {
                        cameraPaintBase.setTargetStrokeWidth(cameraPaintBase.mBaseStokeWidth);
                        cameraPaintBase.setTargetWidthPercent(cameraPaintBase.mBaseWidthPercent).setTargetAlpha(cameraPaintBase.mBaseAlpha);
                    }
                    break;
                case 162:
                case 172:
                case 173:
                case 180:
                case 204:
                    if (bottomAnimationConfig.mIsPostProcessing) {
                        this.mRoundPaintItem.setTargetAlpha(0);
                    }
                    CameraSnapPaintRound cameraSnapPaintRound = this.mRoundPaintItem;
                    cameraSnapPaintRound.setTargetWidthPercent(cameraSnapPaintRound.mBaseWidthPercent);
                    for (CameraPaintBase cameraPaintBase2 : this.mRecordingReferredPaints) {
                        cameraPaintBase2.setTargetWidthPercent(cameraPaintBase2.mBaseWidthPercent);
                        cameraPaintBase2.setTargetAlpha(255);
                    }
                    break;
                case 163:
                case 165:
                case 177:
                    for (CameraPaintBase cameraPaintBase3 : this.mRecordingReferredPaints) {
                        cameraPaintBase3.setTargetStrokeWidth(cameraPaintBase3.mBaseStokeWidth);
                        cameraPaintBase3.setTargetWidthPercent(cameraPaintBase3.mBaseWidthPercent).setTargetAlpha(cameraPaintBase3.mBaseAlpha);
                    }
                    if (this.mRoundPaintItem.mBaseAlpha == 0) {
                        z = true;
                    }
                    if (z) {
                        CameraSnapPaintRound cameraSnapPaintRound2 = this.mRoundPaintItem;
                        cameraSnapPaintRound2.setTargetWidthPercent(cameraSnapPaintRound2.mCurrentWidthPercent);
                        CameraSnapPaintRound cameraSnapPaintRound3 = this.mRoundPaintItem;
                        cameraSnapPaintRound3.setTargetAlpha(cameraSnapPaintRound3.mBaseAlpha);
                        break;
                    } else {
                        CameraSnapPaintRound cameraSnapPaintRound4 = this.mRoundPaintItem;
                        cameraSnapPaintRound4.setTargetWidthPercent(cameraSnapPaintRound4.mBaseWidthPercent);
                        break;
                    }
                case 166:
                    this.mRoundPaintItem.setTargetAlpha(0);
                    this.mRoundPaintItem.prepareRecord(true, false);
                    for (CameraPaintBase cameraPaintBase4 : this.mRecordingReferredPaints) {
                        cameraPaintBase4.setTargetWidthPercent(cameraPaintBase4.mBaseWidthPercent).setTargetAlpha(cameraPaintBase4.mBaseAlpha);
                    }
                    break;
                case 167:
                    if (bottomAnimationConfig.mIsLongExpose) {
                        onLongExposeFinish(bottomAnimationConfig);
                        this.mRecordingReferredPaints.add(this.mBottomPatinItem);
                        break;
                    }
                    for (CameraPaintBase cameraPaintBase5 : this.mRecordingReferredPaints) {
                        cameraPaintBase5.setTargetValues(cameraPaintBase5.mCurrentWidthPercent, cameraPaintBase5.mCurrentColor, CameraPaintBase.ALPHA_OUTSTANDING, cameraPaintBase5.mCurrentStrokeWidth);
                    }
                    break;
                case 176:
                    this.mRoundPaintItem.prepareRecord(true, false);
                    CameraSnapPaintRound cameraSnapPaintRound5 = this.mRoundPaintItem;
                    cameraSnapPaintRound5.setTargetWidthPercent(cameraSnapPaintRound5.mBaseWidthPercent);
                    for (CameraPaintBase cameraPaintBase6 : this.mRecordingReferredPaints) {
                        cameraPaintBase6.setTargetWidthPercent(cameraPaintBase6.mBaseWidthPercent).setTargetAlpha(cameraPaintBase6.mBaseAlpha);
                    }
                    this.mBottomPatinItem.setCurrentAlpha(255);
                    this.mBottomPatinItem.setTargetAlpha(255);
                    CameraSnapPaintBottom cameraSnapPaintBottom = this.mBottomPatinItem;
                    cameraSnapPaintBottom.setTargetWidthPercent(cameraSnapPaintBottom.mBaseWidthPercent);
                    this.mCirclePaintItem.setBaseWidthPercent(0.61f);
                    CameraSnapPaintCircle cameraSnapPaintCircle = this.mCirclePaintItem;
                    cameraSnapPaintCircle.setTargetWidthPercent(cameraSnapPaintCircle.mBaseWidthPercent);
                    this.mCirclePaintItem.setTargetAlpha(0);
                    this.mCirclePaintItem.setResult();
                    break;
                case 179:
                    this.mCenterVVPaintItem.setDurationText(null);
                    for (CameraPaintBase cameraPaintBase7 : this.mRecordingReferredPaints) {
                        cameraPaintBase7.setTargetStrokeWidth(cameraPaintBase7.mBaseStokeWidth);
                        cameraPaintBase7.setTargetWidthPercent(cameraPaintBase7.mBaseWidthPercent).setTargetAlpha(cameraPaintBase7.mBaseAlpha);
                    }
                    break;
                default:
                    while (r6.hasNext()) {
                    }
                    break;
            }
            this.mTimeAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mTimeAnimator.setDuration(300L);
            this.mTimeAnimator.setInterpolator(new CubicEaseOutInterpolator() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass10 */

                @Override // miui.view.animation.CubicEaseOutInterpolator
                public float getInterpolation(float f) {
                    float interpolation = super.getInterpolation(f);
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.updateValue(interpolation);
                    for (CameraPaintBase cameraPaintBase : CameraSnapAnimateDrawable.this.mRecordingReferredPaints) {
                        cameraPaintBase.updateValue(interpolation);
                    }
                    CameraSnapAnimateDrawable.this.invalidateSelf();
                    return interpolation;
                }
            });
            this.mTimeAnimator.removeAllListeners();
            this.mTimeAnimator.addListener(new Animator.AnimatorListener() {
                /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass11 */

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    for (CameraPaintBase cameraPaintBase : CameraSnapAnimateDrawable.this.mRecordingReferredPaints) {
                        cameraPaintBase.needZero = false;
                    }
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.setShowCenterBitmap(true);
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.setRecordingBgColor(0);
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.resetRecordingState();
                    CameraSnapAnimateDrawable.this.mRoundPaintItem.setCurrentWidthPercent(CameraSnapAnimateDrawable.this.mRoundPaintItem.mBaseWidthPercent);
                    CameraSnapAnimateDrawable.this.mCirclePaintItem.setTargetAlpha(CameraSnapAnimateDrawable.this.mCirclePaintItem.mBaseAlpha);
                    CameraSnapAnimateDrawable.this.mCirclePaintItem.setResult();
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                    for (CameraPaintBase cameraPaintBase : CameraSnapAnimateDrawable.this.mRecordingReferredPaints) {
                        cameraPaintBase.resetRecordingState();
                        cameraPaintBase.needZero = false;
                    }
                }
            });
            this.mTimeAnimator.start();
        }
    }

    public void stopRingAnimation() {
        ValueAnimator valueAnimator = this.mRingAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mCirclePaintItem.setRingVisible(8);
            this.mRoundPaintItem.setVisible(0);
            invalidateSelf();
        }
    }

    public void test() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(1000L);
        ofFloat.setInterpolator(new CubicEaseOutInterpolator() {
            /* class com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable.AnonymousClass19 */

            @Override // miui.view.animation.CubicEaseOutInterpolator
            public float getInterpolation(float f) {
                float interpolation = super.getInterpolation(f);
                CameraSnapAnimateDrawable.this.mSecondPaintItem.updateValue(interpolation);
                CameraSnapAnimateDrawable.this.invalidateSelf();
                return interpolation;
            }
        });
        ofFloat.setupEndValues();
        ofFloat.start();
    }
}
