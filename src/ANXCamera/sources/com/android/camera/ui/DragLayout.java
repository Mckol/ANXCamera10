package com.android.camera.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Range;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.module.ModuleManager;
import java.util.ArrayList;
import java.util.List;
import miuix.view.animation.CubicEaseOutInterpolator;

public class DragLayout extends FrameLayout {
    private static final int STATE_DRAGGING = 1;
    private static final int STATE_EXPEND = 4;
    private static final int STATE_IDLE = 0;
    private static final int STATE_SETTLING = 2;
    private static final int STATE_SHRINK = 3;
    public static final String TAG = "DragLayout";
    private static boolean debug = true;
    private static DragAnimationConfig sAnimationConfig;
    private ValueAnimator mAnimator;
    private Bar mBar;
    private boolean mCatchDrag;
    private boolean mCurDirectionIsUp;
    private final Dependency mDependency;
    private boolean mDragEnable;
    private Interpolator mDragInterpolator;
    private float mOffset;
    private List<OnDragListener> mOnDragListeners;
    private Interpolator mSettlingInterpolator;
    private boolean mSkipDragUpDown;
    private boolean mStartDirectionIsUp;
    private float mStartX;
    private float mStartY;
    private int mState;

    public static class Bar extends View {
        private static final int FLAT_DURATION = 600;
        private static final int FOLD_DURATION = 200;
        private static final int UI_STATE_FLAT = 0;
        private static final int UI_STATE_FOLD_DOWN = -1;
        private static final int UI_STATE_FOLD_UP = 1;
        private boolean isUpAnimation;
        private float mAlpha;
        private ValueAnimator mAnimator;
        private int mBgColor;
        @UiState
        private int mCurUiState = 0;
        private float mDegree;
        private float mExtendDimen;
        private Interpolator mFlatInterpolator;
        private Interpolator mFoldInterpolator;
        private float mHeight;
        private GradientDrawable mLeftDrawable;
        private float mOffset;
        private float mRadius;
        private GradientDrawable mRightDrawable;
        @UiState
        private int mTargetUiState = 0;
        private float mWidth;
        private float mWidthMax;
        private float mWidthMin;

        public @interface UiState {
        }

        public Bar(Context context) {
            super(context);
            this.mWidthMax = context.getResources().getDimension(R.dimen.mode_select_popup_bar_width_max);
            this.mWidthMin = context.getResources().getDimension(R.dimen.mode_select_popup_bar_width_min);
            float f = this.mWidthMax;
            this.mExtendDimen = f - this.mWidthMin;
            this.mWidth = f;
            this.mHeight = context.getResources().getDimension(R.dimen.mode_select_popup_bar_height);
            this.mBgColor = context.getResources().getColor(R.color.mode_select_popup_bar_color);
            this.mAlpha = ((float) Color.alpha(this.mBgColor)) / 255.0f;
            this.mBgColor = Color.argb(255, Color.red(this.mBgColor), Color.green(this.mBgColor), Color.blue(this.mBgColor));
            setAlpha(this.mAlpha);
            this.mRadius = this.mHeight / 2.0f;
            this.mLeftDrawable = new GradientDrawable();
            this.mLeftDrawable.setColor(this.mBgColor);
            this.mLeftDrawable.setSize(((int) this.mWidth) / 2, (int) this.mHeight);
            this.mLeftDrawable.setBounds(0, 0, ((int) this.mWidth) / 2, (int) this.mHeight);
            GradientDrawable gradientDrawable = this.mLeftDrawable;
            float f2 = this.mRadius;
            gradientDrawable.setCornerRadii(new float[]{f2, f2, f2, f2, f2, f2, f2, f2});
            this.mRightDrawable = new GradientDrawable();
            this.mRightDrawable.setColor(this.mBgColor);
            this.mRightDrawable.setSize(((int) this.mWidth) / 2, (int) this.mHeight);
            this.mRightDrawable.setBounds(0, 0, ((int) this.mWidth) / 2, (int) this.mHeight);
            GradientDrawable gradientDrawable2 = this.mRightDrawable;
            float f3 = this.mRadius;
            gradientDrawable2.setCornerRadii(new float[]{f3, f3, f3, f3, f3, f3, f3, f3});
            this.mFoldInterpolator = new CubicEaseOutInterpolator();
            this.mFlatInterpolator = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
            this.mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.android.camera.ui.DragLayout.Bar.AnonymousClass1 */

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    Bar.this.mDegree = Math.abs(20.0f * floatValue);
                    Bar bar = Bar.this;
                    bar.mWidth = bar.mWidthMax - Math.abs(Bar.this.mExtendDimen * floatValue);
                    if (Bar.this.isUpAnimation) {
                        Bar.this.mOffset = Math.abs(floatValue) * -4.0f;
                    } else {
                        Bar.this.mOffset = Math.abs(floatValue) * 8.0f;
                    }
                    Bar.this.invalidate();
                }
            });
            this.mAnimator.addListener(new AnimatorListenerAdapter() {
                /* class com.android.camera.ui.DragLayout.Bar.AnonymousClass2 */

                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    Bar bar = Bar.this;
                    bar.mCurUiState = bar.mTargetUiState;
                }

                public void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                }
            });
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        @UiThread
        private boolean start(@UiState int i) {
            float f;
            float f2;
            int i2 = this.mTargetUiState;
            if (i2 == i) {
                Log.w(DragLayout.TAG, String.format("start bar animation with invalid state {%d} , and cur target state {%d}", Integer.valueOf(i), Integer.valueOf(this.mTargetUiState)));
                return false;
            }
            this.isUpAnimation = i2 == 1 || i == 1;
            float f3 = 0.0f;
            float f4 = this.mCurUiState == 0 ? 0.0f : 1.0f;
            this.mTargetUiState = i;
            if (this.mAnimator.isRunning()) {
                f4 = ((Float) this.mAnimator.getAnimatedValue()).floatValue();
                Log.d(DragLayout.TAG, "cancel running animation, cur process " + f4);
                this.mAnimator.cancel();
            }
            if (i == 0) {
                f4 = -f4;
            } else {
                f3 = 1.0f;
            }
            this.mAnimator.setFloatValues(f4, f3);
            ValueAnimator valueAnimator = this.mAnimator;
            if (i == 0) {
                f2 = Math.abs(f3 - f4);
                f = 600.0f;
            } else {
                f2 = Math.abs(f3 - f4);
                f = 200.0f;
            }
            valueAnimator.setDuration((long) ((int) (f2 * f)));
            this.mAnimator.setInterpolator(i == 0 ? this.mFlatInterpolator : this.mFoldInterpolator);
            Log.d(DragLayout.TAG, String.format("start animation with states {%d} {%d} AND duration {%s} , values {%s:%s} , isUp {%s}", Integer.valueOf(this.mCurUiState), Integer.valueOf(this.mTargetUiState), Long.valueOf(this.mAnimator.getDuration()), Float.valueOf(f4), Float.valueOf(f3), Boolean.valueOf(this.isUpAnimation)));
            this.mAnimator.start();
            return true;
        }

        /* access modifiers changed from: protected */
        public void onDraw(Canvas canvas) {
            canvas.save();
            float f = this.mWidthMax;
            canvas.translate(f / 2.0f, (f * 0.3f) + this.mOffset);
            canvas.save();
            canvas.rotate(this.isUpAnimation ? 180.0f - this.mDegree : this.mDegree - 180.0f);
            this.mLeftDrawable.setSize(((int) this.mWidth) / 2, (int) this.mHeight);
            this.mLeftDrawable.setBounds(0, 0, ((int) this.mWidth) / 2, (int) this.mHeight);
            canvas.translate(-this.mRadius, ((float) (-this.mLeftDrawable.getBounds().height())) / 2.0f);
            this.mLeftDrawable.draw(canvas);
            canvas.restore();
            canvas.save();
            canvas.rotate(this.isUpAnimation ? this.mDegree : -this.mDegree);
            this.mRightDrawable.setSize(((int) this.mWidth) / 2, (int) this.mHeight);
            this.mRightDrawable.setBounds(0, 0, ((int) this.mWidth) / 2, (int) this.mHeight);
            canvas.translate(-this.mRadius, ((float) (-this.mLeftDrawable.getBounds().height())) / 2.0f);
            this.mRightDrawable.draw(canvas);
            canvas.restore();
            canvas.restore();
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int i, int i2) {
            float f = this.mWidthMax;
            setMeasuredDimension((int) f, (int) (((double) f) * 0.6d));
        }
    }

    /* access modifiers changed from: private */
    public class Dependency {
        private List<Boolean> mCatchDragList;
        private float mTotalTranY;

        private Dependency() {
            this.mTotalTranY = 0.0f;
            this.mCatchDragList = new ArrayList(3);
        }

        public boolean catchDrag(int i, int i2) {
            this.mCatchDragList.clear();
            boolean z = false;
            for (OnDragListener onDragListener : DragLayout.this.mOnDragListeners) {
                boolean catchDrag = onDragListener.catchDrag(i, i2);
                this.mCatchDragList.add(Boolean.valueOf(catchDrag));
                z |= catchDrag;
            }
            return z;
        }

        public void onDragDone(boolean z) {
            DragLayout dragLayout = DragLayout.this;
            dragLayout.LogV("onDone dragUp : " + z);
            if (!z) {
                DragLayout.this.setTranslationY(0.0f);
            }
            this.mTotalTranY = DragLayout.this.getTranslationY();
            for (OnDragListener onDragListener : DragLayout.this.mOnDragListeners) {
                onDragListener.onDragDone(z);
            }
            if (DragLayout.this.mBar != null) {
                DragLayout.this.mBar.start(0);
            }
        }

        public void onDragProgress(int i) {
            float f = this.mTotalTranY + ((float) i);
            DragLayout.this.setTranslationY(f);
            for (OnDragListener onDragListener : DragLayout.this.mOnDragListeners) {
                onDragListener.onDragProgress((int) f);
            }
        }

        public void onDragStart(boolean z) {
            DragLayout dragLayout = DragLayout.this;
            dragLayout.LogV("onStart dragUp : " + z);
            for (OnDragListener onDragListener : DragLayout.this.mOnDragListeners) {
                onDragListener.onDragStart(z);
            }
        }

        public boolean onInterceptDrag() {
            boolean z = false;
            for (int i = 0; i < DragLayout.this.mOnDragListeners.size(); i++) {
                if (this.mCatchDragList.get(i).booleanValue()) {
                    z |= ((OnDragListener) DragLayout.this.mOnDragListeners.get(i)).onInterceptDrag();
                }
            }
            return z;
        }

        public boolean showPress(int i, int i2) {
            boolean z = false;
            for (int i3 = 0; i3 < DragLayout.this.mOnDragListeners.size(); i3++) {
                z |= ((OnDragListener) DragLayout.this.mOnDragListeners.get(i3)).showDragAnimation(i, i2);
            }
            return z;
        }
    }

    public static class DragAnimationConfig {
        private Range<Float> mBgAlphaRange;
        private Range<Float> mCornerRadiusRange;
        private Range<Float> mDisappearAlphaRange;
        private float mDisappearDistance;
        private Range<Float> mDisappearRange;
        private Range<Float> mDisplayAlphaRange;
        private float mDisplayDistance;
        private Range<Float> mDisplayRange;
        private float mDragThreshold = 12.0f;
        private int mDuration;
        private float mTotalDragDistance;

        public DragAnimationConfig(Context context) {
            this.mDuration = context.getResources().getInteger(R.integer.drag_animation_duration);
            this.mTotalDragDistance = context.getResources().getDimension(R.dimen.more_mode_drag_distance);
            this.mDragThreshold = context.getResources().getDimension(R.dimen.drag_start_threshold);
            Float valueOf = Float.valueOf(0.0f);
            this.mDisappearRange = new Range<>(valueOf, Float.valueOf(context.getResources().getDimension(R.dimen.more_mode_disappear_point)));
            this.mDisappearDistance = this.mDisappearRange.getUpper().floatValue() - this.mDisappearRange.getLower().floatValue();
            this.mDisplayRange = new Range<>(Float.valueOf(context.getResources().getDimension(R.dimen.more_mode_disappear_point)), Float.valueOf(context.getResources().getDimension(R.dimen.more_mode_display_point)));
            this.mDisplayDistance = this.mDisplayRange.getUpper().floatValue() - this.mDisplayRange.getLower().floatValue();
            this.mCornerRadiusRange = new Range<>(valueOf, Float.valueOf(context.getResources().getDimension(R.dimen.more_mode_corner_radius)));
            Float valueOf2 = Float.valueOf(1.0f);
            this.mDisappearAlphaRange = new Range<>(valueOf, valueOf2);
            this.mDisplayAlphaRange = new Range<>(Float.valueOf(0.1f), valueOf2);
            this.mBgAlphaRange = new Range<>(valueOf, valueOf2);
        }

        public void calDragLayoutHeight(Context context, int i) {
            if (context != null) {
                int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.mode_item_height);
                int dimensionPixelOffset = context.getResources().getDimensionPixelOffset(R.dimen.mode_more_hor_margin);
                if (i <= 9) {
                    this.mTotalDragDistance = (float) (((Util.getNavigationBarHeight(context) + (dimensionPixelSize * 3)) + dimensionPixelOffset) - Util.getBottomHeight());
                } else {
                    this.mTotalDragDistance = (float) (((Util.getNavigationBarHeight(context) + (dimensionPixelSize * 4)) + dimensionPixelOffset) - Util.getBottomHeight());
                }
            }
        }

        public Range<Float> getBgAlphaRange() {
            return this.mBgAlphaRange;
        }

        public Range<Float> getCornerRadiusRange() {
            return this.mCornerRadiusRange;
        }

        public Range<Float> getDisappearAlphaRange() {
            return this.mDisappearAlphaRange;
        }

        public float getDisappearDistance() {
            return this.mDisappearDistance;
        }

        public Range<Float> getDisappearRange() {
            return this.mDisappearRange;
        }

        public Range<Float> getDisplayAlphaRange() {
            return this.mDisplayAlphaRange;
        }

        public float getDisplayDistance() {
            return this.mDisplayDistance;
        }

        public Range<Float> getDisplayRange() {
            return this.mDisplayRange;
        }

        public float getDragThreshold() {
            return this.mDragThreshold;
        }

        public int getDuration() {
            return this.mDuration;
        }

        public float getTotalDragDistance() {
            return this.mTotalDragDistance;
        }
    }

    public interface OnDragListener {
        default boolean catchDrag(int i, int i2) {
            return false;
        }

        void onDragDone(boolean z);

        void onDragProgress(int i);

        void onDragStart(boolean z);

        default boolean onInterceptDrag() {
            return false;
        }

        default boolean showDragAnimation(int i, int i2) {
            return false;
        }
    }

    public DragLayout(@NonNull Context context) {
        this(context, null);
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDragEnable = false;
        this.mSkipDragUpDown = false;
        this.mCatchDrag = false;
        this.mState = 0;
        this.mOnDragListeners = new ArrayList();
        if (sAnimationConfig == null) {
            sAnimationConfig = new DragAnimationConfig(context);
        }
        this.mDependency = new Dependency();
        this.mDragInterpolator = new LinearInterpolator();
        this.mSettlingInterpolator = new CubicEaseOutInterpolator();
        this.mAnimator = new ValueAnimator();
        this.mAnimator.setInterpolator(this.mSettlingInterpolator);
        this.mAnimator.addUpdateListener(new b(this));
        this.mAnimator.addListener(new AnimatorListenerAdapter() {
            /* class com.android.camera.ui.DragLayout.AnonymousClass1 */

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (DragLayout.this.mState == 2) {
                    DragLayout dragLayout = DragLayout.this;
                    dragLayout.setState(dragLayout.mCurDirectionIsUp ? 4 : 3);
                    DragLayout.this.mDependency.onDragDone(DragLayout.this.mCurDirectionIsUp);
                }
            }

            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                if (DragLayout.this.mState == 4 || DragLayout.this.mState == 3) {
                    DragLayout dragLayout = DragLayout.this;
                    dragLayout.mStartDirectionIsUp = dragLayout.mState == 3;
                    DragLayout dragLayout2 = DragLayout.this;
                    dragLayout2.mCurDirectionIsUp = dragLayout2.mStartDirectionIsUp;
                    DragLayout.this.mDependency.onDragStart(DragLayout.this.mStartDirectionIsUp);
                    DragLayout.this.setState(2);
                }
            }
        });
    }

    private void LogD(String str) {
        Log.d(TAG, str);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void LogV(String str) {
        if (debug) {
            Log.v(TAG, str);
        }
    }

    private void ensureView() {
        if (this.mBar == null) {
            this.mBar = new Bar(getContext());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.mode_select_popup_bar_width_max), (int) (((float) getResources().getDimensionPixelSize(R.dimen.mode_select_popup_bar_width_max)) * 0.6f));
            layoutParams.gravity = 1;
            layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.mode_select_popup_bar_margin_top);
            addView(this.mBar, 1, layoutParams);
            this.mBar.setVisibility(this.mDragEnable ? 0 : 8);
        }
        if (this.mState == 0) {
            setState(3);
        }
    }

    public static DragAnimationConfig getAnimationConfig() {
        return sAnimationConfig;
    }

    private boolean onDrag(MotionEvent motionEvent) {
        LogV("onDrag skip ?" + this.mSkipDragUpDown + ", mState " + this.mState);
        int i = this.mState;
        boolean z = false;
        if (i == 3 || i == 4) {
            if (this.mState == 3) {
                z = true;
            }
            this.mStartDirectionIsUp = z;
            boolean z2 = this.mStartDirectionIsUp;
            this.mCurDirectionIsUp = z2;
            this.mDependency.onDragStart(z2);
            setState(1);
            this.mOffset = 0.0f;
            this.mStartY = motionEvent.getRawY();
            return true;
        } else if (i != 1) {
            return false;
        } else {
            float rawY = motionEvent.getRawY() - this.mStartY;
            if (this.mStartDirectionIsUp && rawY > 0.0f) {
                return true;
            }
            if ((this.mStartDirectionIsUp || rawY >= 0.0f) && Math.abs(this.mOffset) < getAnimationConfig().getTotalDragDistance()) {
                float interpolation = this.mDragInterpolator.getInterpolation(Math.max(Math.min(Math.abs(rawY), getAnimationConfig().getTotalDragDistance()), 0.0f));
                if (rawY < 0.0f) {
                    interpolation = -interpolation;
                }
                float f = this.mOffset;
                if (interpolation != f) {
                    if (interpolation < f) {
                        z = true;
                    }
                    this.mCurDirectionIsUp = z;
                }
                this.mOffset = interpolation;
                this.mDependency.onDragProgress((int) this.mOffset);
            }
            return true;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setState(int i) {
        if (this.mState != i) {
            LogD("setState " + i);
            this.mState = i;
        }
    }

    public void addOnDragListener(OnDragListener onDragListener) {
        LogV("addOnDragListener " + onDragListener);
        if (!this.mOnDragListeners.contains(onDragListener)) {
            this.mOnDragListeners.add(onDragListener);
        }
    }

    public /* synthetic */ void b(ValueAnimator valueAnimator) {
        if (this.mState == 2) {
            this.mOffset = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.mDependency.onDragProgress((int) this.mOffset);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        Bar bar;
        LogV("dispatchTouchEvent " + motionEvent.getActionMasked());
        if (!this.mDragEnable) {
            return super.dispatchTouchEvent(motionEvent);
        }
        if (this.mState == 2) {
            return true;
        }
        int actionMasked = motionEvent.getActionMasked();
        int i = 4;
        if (actionMasked == 0) {
            LogV("onDown " + motionEvent.getActionMasked());
            this.mSkipDragUpDown = false;
            this.mStartX = motionEvent.getRawX();
            this.mStartY = motionEvent.getRawY();
            this.mCatchDrag = this.mDependency.catchDrag((int) this.mStartX, (int) this.mStartY);
            if (this.mCatchDrag) {
                LogV("dependency wanna catch drag event.");
            }
            if (this.mDependency.showPress((int) this.mStartX, (int) this.mStartY) && (bar = this.mBar) != null) {
                int i2 = this.mState;
                if (i2 == 3) {
                    bar.start(1);
                } else if (i2 == 4) {
                    bar.start(-1);
                }
            }
        } else if (actionMasked == 1) {
            Bar bar2 = this.mBar;
            if (bar2 != null) {
                bar2.start(0);
            }
            if (this.mState == 1) {
                LogV("onUp " + this.mStartDirectionIsUp + " " + this.mCurDirectionIsUp + ", mOffset = " + this.mOffset);
                if (Math.abs(this.mOffset) < getAnimationConfig().getTotalDragDistance()) {
                    if (this.mStartDirectionIsUp == this.mCurDirectionIsUp) {
                        float max = Math.max(0.0f, getAnimationConfig().getTotalDragDistance() - Math.abs(this.mOffset));
                        ValueAnimator valueAnimator = this.mAnimator;
                        float[] fArr = new float[2];
                        fArr[0] = this.mOffset;
                        fArr[1] = this.mCurDirectionIsUp ? -getAnimationConfig().getTotalDragDistance() : getAnimationConfig().getTotalDragDistance();
                        valueAnimator.setFloatValues(fArr);
                        this.mAnimator.setDuration((long) ((((float) getAnimationConfig().getDuration()) * max) / getAnimationConfig().getTotalDragDistance()));
                    } else {
                        this.mAnimator.setFloatValues(this.mOffset, 0.0f);
                        this.mAnimator.setDuration((long) ((((float) getAnimationConfig().getDuration()) * Math.abs(this.mOffset)) / getAnimationConfig().getTotalDragDistance()));
                    }
                    this.mAnimator.start();
                    setState(2);
                } else {
                    this.mDependency.onDragDone(this.mCurDirectionIsUp);
                    if (!this.mCurDirectionIsUp) {
                        i = 3;
                    }
                    setState(i);
                }
                if (!this.mCatchDrag) {
                    return true;
                }
            }
        } else if (actionMasked == 2) {
            if (this.mSkipDragUpDown) {
                return super.dispatchTouchEvent(motionEvent);
            }
            if (!this.mCatchDrag || !this.mDependency.onInterceptDrag()) {
                float rawX = motionEvent.getRawX() - this.mStartX;
                float rawY = motionEvent.getRawY() - this.mStartY;
                LogV(String.format("onMove distanceX = %s distanceY = %s", Float.valueOf(rawX), Float.valueOf(rawY)));
                int i3 = this.mState;
                if ((i3 == 3 || i3 == 4) && Math.abs(rawX) < getAnimationConfig().getDragThreshold() && Math.abs(rawY) < getAnimationConfig().getDragThreshold()) {
                    return true;
                }
                if (this.mState != 3 || (Math.abs(rawX) <= Math.abs(rawY) && rawY <= 0.0f)) {
                    if (this.mState == 4) {
                        if (rawY == 0.0f) {
                            LogD("skip drag down.");
                            return super.dispatchTouchEvent(motionEvent);
                        } else if (rawY < 0.0f) {
                            this.mSkipDragUpDown = true;
                            LogD("skip drag down.");
                            return super.dispatchTouchEvent(motionEvent);
                        }
                    }
                    Bar bar3 = this.mBar;
                    if (bar3 != null) {
                        int i4 = this.mState;
                        if (i4 == 3) {
                            bar3.start(1);
                        } else if (i4 == 4) {
                            bar3.start(-1);
                        }
                    }
                    return onDrag(motionEvent) || super.dispatchTouchEvent(motionEvent);
                }
                this.mSkipDragUpDown = true;
                LogD("skip drag up.");
                Bar bar4 = this.mBar;
                if (bar4 != null) {
                    bar4.start(0);
                }
                return super.dispatchTouchEvent(motionEvent);
            }
            LogV("skip drag caz dependency intercept.");
            this.mSkipDragUpDown = true;
            return super.dispatchTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean isExpanded() {
        return this.mState == 4;
    }

    public boolean isShrink() {
        int i = this.mState;
        return i == 3 || i == 0;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        ensureView();
        super.onMeasure(i, i2);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mDragEnable;
    }

    public void removeOnDragListener(OnDragListener onDragListener) {
        LogV("removeOnDragListener " + onDragListener);
        this.mOnDragListeners.remove(onDragListener);
    }

    public void reset() {
        this.mDependency.onDragStart(false);
        this.mDependency.onDragProgress(0);
        this.mDependency.onDragDone(false);
        setState(0);
    }

    public void setDragEnable(boolean z) {
        int i = 0;
        boolean z2 = CameraSettings.getMoreModeStyle() != 1 ? false : z;
        if (!DataRepository.dataItemGlobal().isNormalIntent()) {
            z2 = false;
        }
        if (!DataRepository.dataItemGlobal().getComponentModuleList().isCommonMode(ModuleManager.getActiveModuleIndex())) {
            z2 = false;
        }
        LogD("setDragEnable : " + z + ", force : " + z2);
        if (this.mDragEnable != z2) {
            this.mDragEnable = z2;
            Bar bar = this.mBar;
            if (bar != null) {
                if (!this.mDragEnable) {
                    i = 8;
                }
                bar.setVisibility(i);
            }
        }
    }

    public boolean shrink(boolean z) {
        if (this.mState != 4) {
            LogD("shrink fail, state error. now state :" + this.mState);
            return false;
        }
        this.mAnimator.setFloatValues(0.0f, getAnimationConfig().getTotalDragDistance());
        this.mAnimator.setDuration(z ? (long) getAnimationConfig().getDuration() : 0);
        this.mAnimator.start();
        return true;
    }
}
