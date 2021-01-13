package com.miui.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CompoundButton;
import com.miui.internal.R;
import miui.animation.physics.DynamicAnimation;
import miui.animation.physics.SpringAnimation;
import miui.animation.property.FloatProperty;
import miui.util.AttributeResolver;
import miui.util.HapticFeedbackUtil;
import miui.util.ViewUtils;
import miui.view.MiuiHapticFeedbackConstants;

public class SlidingButtonHelper {
    private static final int FULL_ALPHA = 255;
    private boolean mAnimChecked;
    private Drawable mFrame;
    private int mHeight;
    private DynamicAnimation.OnAnimationUpdateListener mInvalidateUpdateListener = new DynamicAnimation.OnAnimationUpdateListener() {
        /* class com.miui.internal.widget.SlidingButtonHelper.AnonymousClass7 */

        @Override // miui.animation.physics.DynamicAnimation.OnAnimationUpdateListener
        public void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
            SlidingButtonHelper.this.mView.invalidate();
        }
    };
    private boolean mIsSliderEdgeReached = false;
    private int mLastX;
    private SpringAnimation mMarkedAlphaHideAnim;
    private SpringAnimation mMarkedAlphaShowAnim;
    private Drawable mMaskCheckedSlideBar;
    private float mMaskCheckedSlideBarAlpha = 1.0f;
    private FloatProperty<SlidingButtonHelper> mMaskCheckedSlideBarAlphaProperty = new FloatProperty<SlidingButtonHelper>("MaskCheckedSlideBarAlpha") {
        /* class com.miui.internal.widget.SlidingButtonHelper.AnonymousClass2 */

        public float getValue(SlidingButtonHelper slidingButtonHelper) {
            return SlidingButtonHelper.this.getMaskCheckedSlideBarAlpha();
        }

        public void setValue(SlidingButtonHelper slidingButtonHelper, float f) {
            SlidingButtonHelper.this.setMaskCheckedSlideBarAlpha(f);
        }
    };
    private float mMaskCheckedSlideBarAlphaTemp = -1.0f;
    private Drawable mMaskUnCheckedPressedSlideBar;
    private float mMaskUnCheckedPressedSlideBarAlpha = 0.0f;
    private FloatProperty<SlidingButtonHelper> mMaskUnCheckedPressedSlideBarAlphaProperty = new FloatProperty<SlidingButtonHelper>("MaskUnCheckedSlideBarAlpha") {
        /* class com.miui.internal.widget.SlidingButtonHelper.AnonymousClass1 */

        public float getValue(SlidingButtonHelper slidingButtonHelper) {
            return SlidingButtonHelper.this.getMaskUnCheckedPressedSlideBarAlpha();
        }

        public void setValue(SlidingButtonHelper slidingButtonHelper, float f) {
            SlidingButtonHelper.this.setMaskUnCheckedPressedSlideBarAlpha(f);
        }
    };
    private Drawable mMaskUnCheckedSlideBar;
    private CompoundButton.OnCheckedChangeListener mOnPerformCheckedChangeListener;
    private int mOriginalTouchPointX;
    private boolean mParamCached = false;
    private StateListDrawable mSlideBar;
    private int mSliderHeight;
    private SpringAnimation mSliderMoveAnim;
    private boolean mSliderMoved;
    private Drawable mSliderOff;
    private int mSliderOffset;
    private FloatProperty<SlidingButtonHelper> mSliderOffsetProperty = new FloatProperty<SlidingButtonHelper>("SliderOffset") {
        /* class com.miui.internal.widget.SlidingButtonHelper.AnonymousClass6 */

        public float getValue(SlidingButtonHelper slidingButtonHelper) {
            return SlidingButtonHelper.this.getSliderOffset();
        }

        public void setValue(SlidingButtonHelper slidingButtonHelper, float f) {
            SlidingButtonHelper.this.setSliderOffset((int) f);
        }
    };
    private int mSliderOffsetTemp = -1;
    private Drawable mSliderOn;
    private int mSliderOnAlpha;
    private int mSliderOnAlphaTemp = -1;
    private int mSliderPositionEnd;
    private int mSliderPositionStart;
    private SpringAnimation mSliderPressedAnim;
    private float mSliderScale = 1.0f;
    private FloatProperty<SlidingButtonHelper> mSliderScaleFloatProperty = new FloatProperty<SlidingButtonHelper>("SliderScale") {
        /* class com.miui.internal.widget.SlidingButtonHelper.AnonymousClass5 */

        public float getValue(SlidingButtonHelper slidingButtonHelper) {
            return SlidingButtonHelper.this.getSliderScale();
        }

        public void setValue(SlidingButtonHelper slidingButtonHelper, float f) {
            SlidingButtonHelper.this.setSliderScale(f);
        }
    };
    private Drawable mSliderShadow;
    private float mSliderShadowAlpha = 0.0f;
    private FloatProperty<SlidingButtonHelper> mSliderShadowAlphaProperty = new FloatProperty<SlidingButtonHelper>("SliderShadowAlpha") {
        /* class com.miui.internal.widget.SlidingButtonHelper.AnonymousClass4 */

        public float getValue(SlidingButtonHelper slidingButtonHelper) {
            return SlidingButtonHelper.this.getSliderShadowAlpha();
        }

        public void setValue(SlidingButtonHelper slidingButtonHelper, float f) {
            SlidingButtonHelper.this.setSliderShadowAlpha(f);
        }
    };
    private SpringAnimation mSliderShadowHideAnim;
    private SpringAnimation mSliderShadowShowAnim;
    private Drawable mSliderStroke;
    private SpringAnimation mSliderUnPressedAnim;
    private int mSliderWidth;
    private SpringAnimation mStokeAlphaHideAnim;
    private SpringAnimation mStokeAlphaShowAnim;
    private float mStrokeAlpha = 0.1f;
    private FloatProperty<SlidingButtonHelper> mStrokeAlphaProperty = new FloatProperty<SlidingButtonHelper>("StrokeAlpha") {
        /* class com.miui.internal.widget.SlidingButtonHelper.AnonymousClass3 */

        public float getValue(SlidingButtonHelper slidingButtonHelper) {
            return SlidingButtonHelper.this.getStrokeAlpha();
        }

        public void setValue(SlidingButtonHelper slidingButtonHelper, float f) {
            SlidingButtonHelper.this.setStrokeAlpha(f);
        }
    };
    private int mTapThreshold;
    private Rect mTmpRect = new Rect();
    private boolean mTracking;
    private SpringAnimation mUnMarkedPressedAlphaHideAnim;
    private SpringAnimation mUnMarkedPressedAlphaShowAnim;
    private CompoundButton mView;
    private int mWidth;

    public SlidingButtonHelper(CompoundButton compoundButton) {
        this.mView = compoundButton;
        this.mAnimChecked = this.mView.isChecked();
        if (!compoundButton.isChecked()) {
            this.mMaskCheckedSlideBarAlpha = 0.0f;
        }
        initDrawable();
        initAnim();
    }

    private void animateToState(boolean z) {
        if (z != this.mView.isChecked()) {
            this.mView.setChecked(z);
            startCheckedChangeAnimInternal(z);
            notifyCheckedChangeListener();
        }
        animateToState(z, z ? this.mSliderPositionEnd : this.mSliderPositionStart, new Runnable() {
            /* class com.miui.internal.widget.SlidingButtonHelper.AnonymousClass9 */

            public void run() {
                SlidingButtonHelper slidingButtonHelper = SlidingButtonHelper.this;
                slidingButtonHelper.mAnimChecked = slidingButtonHelper.mSliderOffset >= SlidingButtonHelper.this.mSliderPositionEnd;
            }
        });
    }

    private void animateToggle() {
        animateToState(!this.mView.isChecked());
        if (HapticFeedbackUtil.isSupportLinearMotorVibrate(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_SWITCH)) {
            this.mView.performHapticFeedback(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_SWITCH);
        }
    }

    private Bitmap convertToAlphaMask(Drawable drawable) {
        Rect bounds = drawable.getBounds();
        Bitmap createBitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(createBitmap);
        drawable.draw(canvas);
        canvas.setBitmap(null);
        return createBitmap;
    }

    private Drawable createMaskDrawable(Bitmap bitmap, Bitmap bitmap2, Paint paint) {
        Bitmap createBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        canvas.setBitmap(null);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mView.getContext().getResources(), createBitmap);
        bitmapDrawable.setBounds(0, 0, this.mWidth, this.mHeight);
        return bitmapDrawable;
    }

    private StateListDrawable createMaskedSlideBar(Context context, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3) {
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint2.setColorFilter(new PorterDuffColorFilter(AttributeResolver.resolveColor(context, R.attr.slidingBarColor), PorterDuff.Mode.SRC_IN));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Drawable createMaskDrawable = createMaskDrawable(bitmap, bitmap2, paint2);
        Drawable createMaskDrawable2 = createMaskDrawable(bitmap, bitmap3, paint);
        Paint paint3 = new Paint();
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint3.setColorFilter(new PorterDuffColorFilter(this.mView.getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN));
        initMaskedSlideBar(createMaskDrawable, createMaskDrawable2, createMaskDrawable(bitmap, bitmap2, paint3));
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.setBounds(0, 0, this.mWidth, this.mHeight);
        stateListDrawable.setCallback(this.mView);
        return stateListDrawable;
    }

    private Bitmap createSlideBarBitmap(Drawable drawable) {
        drawable.setBounds(0, 0, this.mWidth, this.mHeight);
        Bitmap createBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.draw(canvas);
        canvas.setBitmap(null);
        return createBitmap;
    }

    private void initAnim() {
        this.mSliderPressedAnim = new SpringAnimation(this, this.mSliderScaleFloatProperty, 1.61f);
        this.mSliderPressedAnim.getSpring().setStiffness(986.96f);
        this.mSliderPressedAnim.getSpring().setDampingRatio(0.6f);
        this.mSliderPressedAnim.setMinimumVisibleChange(0.002f);
        this.mSliderPressedAnim.addUpdateListener(this.mInvalidateUpdateListener);
        this.mSliderUnPressedAnim = new SpringAnimation(this, this.mSliderScaleFloatProperty, 1.0f);
        this.mSliderUnPressedAnim.getSpring().setStiffness(986.96f);
        this.mSliderUnPressedAnim.getSpring().setDampingRatio(0.6f);
        this.mSliderUnPressedAnim.setMinimumVisibleChange(0.002f);
        this.mSliderUnPressedAnim.addUpdateListener(this.mInvalidateUpdateListener);
        this.mSliderShadowShowAnim = new SpringAnimation(this, this.mSliderShadowAlphaProperty, 1.0f);
        this.mSliderShadowShowAnim.getSpring().setStiffness(986.96f);
        this.mSliderShadowShowAnim.getSpring().setDampingRatio(0.99f);
        this.mSliderShadowShowAnim.setMinimumVisibleChange(0.00390625f);
        this.mSliderShadowShowAnim.addUpdateListener(this.mInvalidateUpdateListener);
        this.mSliderShadowHideAnim = new SpringAnimation(this, this.mSliderShadowAlphaProperty, 0.0f);
        this.mSliderShadowHideAnim.getSpring().setStiffness(986.96f);
        this.mSliderShadowHideAnim.getSpring().setDampingRatio(0.99f);
        this.mSliderShadowHideAnim.setMinimumVisibleChange(0.00390625f);
        this.mSliderShadowHideAnim.addUpdateListener(this.mInvalidateUpdateListener);
        this.mStokeAlphaShowAnim = new SpringAnimation(this, this.mStrokeAlphaProperty, 0.15f);
        this.mStokeAlphaShowAnim.getSpring().setStiffness(986.96f);
        this.mStokeAlphaShowAnim.getSpring().setDampingRatio(0.99f);
        this.mStokeAlphaShowAnim.setMinimumVisibleChange(0.00390625f);
        this.mStokeAlphaShowAnim.addUpdateListener(this.mInvalidateUpdateListener);
        this.mStokeAlphaHideAnim = new SpringAnimation(this, this.mStrokeAlphaProperty, 0.1f);
        this.mStokeAlphaHideAnim.getSpring().setStiffness(986.96f);
        this.mStokeAlphaHideAnim.getSpring().setDampingRatio(0.99f);
        this.mStokeAlphaHideAnim.setMinimumVisibleChange(0.00390625f);
        this.mStokeAlphaHideAnim.addUpdateListener(this.mInvalidateUpdateListener);
        this.mMarkedAlphaShowAnim = new SpringAnimation(this, this.mMaskCheckedSlideBarAlphaProperty, 1.0f);
        this.mMarkedAlphaShowAnim.getSpring().setStiffness(438.64f);
        this.mMarkedAlphaShowAnim.getSpring().setDampingRatio(0.99f);
        this.mMarkedAlphaShowAnim.setMinimumVisibleChange(0.00390625f);
        this.mMarkedAlphaShowAnim.addUpdateListener(this.mInvalidateUpdateListener);
        this.mMarkedAlphaHideAnim = new SpringAnimation(this, this.mMaskCheckedSlideBarAlphaProperty, 0.0f);
        this.mMarkedAlphaHideAnim.getSpring().setStiffness(986.96f);
        this.mMarkedAlphaHideAnim.getSpring().setDampingRatio(0.99f);
        this.mMarkedAlphaHideAnim.setMinimumVisibleChange(0.00390625f);
        this.mMarkedAlphaHideAnim.addUpdateListener(this.mInvalidateUpdateListener);
        this.mUnMarkedPressedAlphaShowAnim = new SpringAnimation(this, this.mMaskUnCheckedPressedSlideBarAlphaProperty, 0.05f);
        this.mUnMarkedPressedAlphaShowAnim.getSpring().setStiffness(986.96f);
        this.mUnMarkedPressedAlphaShowAnim.getSpring().setDampingRatio(0.99f);
        this.mUnMarkedPressedAlphaShowAnim.setMinimumVisibleChange(0.00390625f);
        this.mUnMarkedPressedAlphaShowAnim.addUpdateListener(this.mInvalidateUpdateListener);
        this.mUnMarkedPressedAlphaHideAnim = new SpringAnimation(this, this.mMaskUnCheckedPressedSlideBarAlphaProperty, 0.0f);
        this.mUnMarkedPressedAlphaHideAnim.getSpring().setStiffness(986.96f);
        this.mUnMarkedPressedAlphaHideAnim.getSpring().setDampingRatio(0.99f);
        this.mUnMarkedPressedAlphaHideAnim.setMinimumVisibleChange(0.00390625f);
        this.mUnMarkedPressedAlphaHideAnim.addUpdateListener(this.mInvalidateUpdateListener);
    }

    private void initDrawable() {
        this.mSliderShadow = this.mView.getResources().getDrawable(R.drawable.sliding_btn_slider_shadow);
        this.mSliderStroke = this.mView.getResources().getDrawable(R.drawable.sliding_btn_slider_stroke_light);
    }

    private void moveSlider(int i) {
        if (ViewUtils.isLayoutRtl(this.mView)) {
            i = -i;
        }
        this.mSliderOffset += i;
        int i2 = this.mSliderOffset;
        int i3 = this.mSliderPositionStart;
        if (i2 < i3) {
            this.mSliderOffset = i3;
        } else {
            int i4 = this.mSliderPositionEnd;
            if (i2 > i4) {
                this.mSliderOffset = i4;
            }
        }
        int i5 = this.mSliderOffset;
        boolean z = i5 == this.mSliderPositionStart || i5 == this.mSliderPositionEnd;
        if (z && !this.mIsSliderEdgeReached && HapticFeedbackUtil.isSupportLinearMotorVibrate(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_SWITCH)) {
            this.mView.performHapticFeedback(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_SWITCH);
        }
        this.mIsSliderEdgeReached = z;
        setSliderOffset(this.mSliderOffset);
    }

    private void onTouchEventDown(int i, int i2, Rect rect) {
        if (rect.contains(i, i2)) {
            boolean z = true;
            this.mTracking = true;
            this.mView.setPressed(true);
            onPressedInner();
            int i3 = this.mSliderOffset;
            if (i3 > this.mSliderPositionStart && i3 < this.mSliderPositionEnd) {
                z = false;
            }
            this.mIsSliderEdgeReached = z;
        } else {
            this.mTracking = false;
        }
        this.mLastX = i;
        this.mOriginalTouchPointX = i;
        this.mSliderMoved = false;
    }

    private void onTouchEventMove(int i) {
        if (this.mTracking) {
            moveSlider(i - this.mLastX);
            this.mLastX = i;
            if (Math.abs(i - this.mOriginalTouchPointX) >= this.mTapThreshold) {
                this.mSliderMoved = true;
                this.mView.getParent().requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    private void onTouchEventUp(int i, int i2, Rect rect) {
        if (!this.mTracking) {
            animateToggle();
        } else if (!this.mSliderMoved) {
            animateToggle();
        } else {
            this.mAnimChecked = this.mSliderOffset >= this.mSliderPositionEnd / 2;
            animateToState(this.mAnimChecked);
            if (rect.contains(i, i2) && HapticFeedbackUtil.isSupportLinearMotorVibrate(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_SWITCH)) {
                this.mView.performHapticFeedback(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_SWITCH);
            }
        }
    }

    private void popSavedParams() {
        if (this.mParamCached) {
            this.mSliderOffset = this.mSliderOffsetTemp;
            this.mSliderOnAlpha = this.mSliderOnAlphaTemp;
            this.mMaskCheckedSlideBarAlpha = this.mMaskCheckedSlideBarAlphaTemp;
            this.mParamCached = false;
            this.mSliderOffsetTemp = -1;
            this.mSliderOnAlphaTemp = -1;
            this.mMaskCheckedSlideBarAlphaTemp = -1.0f;
        }
    }

    private void saveCurrentParams() {
        this.mSliderOffsetTemp = this.mSliderOffset;
        this.mSliderOnAlphaTemp = this.mSliderOnAlpha;
        this.mMaskCheckedSlideBarAlphaTemp = this.mMaskCheckedSlideBarAlpha;
        this.mParamCached = true;
    }

    private void startCheckedChangeAnimInternal(boolean z) {
        SpringAnimation springAnimation = this.mSliderMoveAnim;
        if (springAnimation == null || !springAnimation.isRunning()) {
            this.mSliderOffset = this.mAnimChecked ? this.mSliderPositionEnd : this.mSliderPositionStart;
            this.mSliderOnAlpha = this.mAnimChecked ? 255 : 0;
        }
        popSavedParams();
        setCheckedInner(z);
    }

    private void updateCheckedAnim(boolean z) {
        if (this.mMarkedAlphaHideAnim.isRunning()) {
            this.mMarkedAlphaHideAnim.cancel();
        }
        if (!this.mMarkedAlphaShowAnim.isRunning() && !z) {
            this.mMaskCheckedSlideBarAlpha = 1.0f;
        }
    }

    private void updateUnCheckedAnim(boolean z) {
        if (this.mMarkedAlphaShowAnim.isRunning()) {
            this.mMarkedAlphaShowAnim.cancel();
        }
        if (!this.mMarkedAlphaHideAnim.isRunning() && z) {
            this.mMaskCheckedSlideBarAlpha = 0.0f;
        }
    }

    public void addState(StateListDrawable stateListDrawable, int[] iArr, Drawable drawable, int[] iArr2, Drawable drawable2, int i) {
    }

    public void animateToState(boolean z, int i, final Runnable runnable) {
        SpringAnimation springAnimation = this.mSliderMoveAnim;
        if (springAnimation != null && springAnimation.isRunning()) {
            this.mSliderMoveAnim.cancel();
        }
        if (z == this.mView.isChecked()) {
            this.mSliderMoveAnim = new SpringAnimation(this, this.mSliderOffsetProperty, (float) i);
            this.mSliderMoveAnim.getSpring().setStiffness(986.96f);
            this.mSliderMoveAnim.getSpring().setDampingRatio(0.7f);
            this.mSliderMoveAnim.addUpdateListener(this.mInvalidateUpdateListener);
            this.mSliderMoveAnim.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                /* class com.miui.internal.widget.SlidingButtonHelper.AnonymousClass8 */

                @Override // miui.animation.physics.DynamicAnimation.OnAnimationEndListener
                public void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                    runnable.run();
                }
            });
            this.mSliderMoveAnim.start();
            if (z) {
                if (!this.mMarkedAlphaShowAnim.isRunning()) {
                    this.mMarkedAlphaShowAnim.start();
                }
                if (this.mMarkedAlphaHideAnim.isRunning()) {
                    this.mMarkedAlphaHideAnim.cancel();
                    return;
                }
                return;
            }
            if (!this.mMarkedAlphaHideAnim.isRunning()) {
                this.mMarkedAlphaHideAnim.start();
            }
            if (this.mMarkedAlphaShowAnim.isRunning()) {
                this.mMarkedAlphaShowAnim.cancel();
            }
        }
    }

    public float getMaskCheckedSlideBarAlpha() {
        return this.mMaskCheckedSlideBarAlpha;
    }

    public float getMaskUnCheckedPressedSlideBarAlpha() {
        return this.mMaskUnCheckedPressedSlideBarAlpha;
    }

    public int getMeasuredHeight() {
        return this.mHeight;
    }

    public int getMeasuredWidth() {
        return this.mWidth;
    }

    public float getSliderOffset() {
        return (float) this.mSliderOffset;
    }

    public int getSliderOnAlpha() {
        return this.mSliderOnAlpha;
    }

    public float getSliderScale() {
        return this.mSliderScale;
    }

    public float getSliderShadowAlpha() {
        return this.mSliderShadowAlpha;
    }

    public float getStrokeAlpha() {
        return this.mStrokeAlpha;
    }

    public void initMaskedSlideBar(Drawable drawable, Drawable drawable2, Drawable drawable3) {
        this.mMaskCheckedSlideBar = drawable;
        this.mMaskUnCheckedSlideBar = drawable2;
        this.mMaskUnCheckedPressedSlideBar = drawable3;
    }

    public void initResource(Context context, TypedArray typedArray) {
        this.mView.setDrawingCacheEnabled(false);
        this.mTapThreshold = ViewConfiguration.get(context).getScaledTouchSlop() / 2;
        this.mFrame = typedArray.getDrawable(R.styleable.SlidingButton_frame);
        this.mSliderOn = typedArray.getDrawable(R.styleable.SlidingButton_sliderOn);
        this.mSliderOff = typedArray.getDrawable(R.styleable.SlidingButton_sliderOff);
        this.mView.setBackground(typedArray.getDrawable(R.styleable.SlidingButton_android_background));
        this.mWidth = this.mFrame.getIntrinsicWidth();
        this.mHeight = this.mFrame.getIntrinsicHeight();
        this.mSliderWidth = Math.min(this.mWidth, this.mSliderOn.getIntrinsicWidth());
        this.mSliderHeight = Math.min(this.mHeight, this.mSliderOn.getIntrinsicHeight());
        this.mSliderPositionStart = 0;
        this.mSliderPositionEnd = this.mWidth - this.mSliderWidth;
        this.mSliderOffset = this.mSliderPositionStart;
        TypedValue typedValue = new TypedValue();
        typedArray.getValue(R.styleable.SlidingButton_barOff, typedValue);
        TypedValue typedValue2 = new TypedValue();
        typedArray.getValue(R.styleable.SlidingButton_barOn, typedValue2);
        Drawable drawable = typedArray.getDrawable(R.styleable.SlidingButton_barOff);
        Drawable drawable2 = typedArray.getDrawable(R.styleable.SlidingButton_barOn);
        Bitmap createSlideBarBitmap = createSlideBarBitmap(drawable);
        Bitmap createSlideBarBitmap2 = (typedValue.type == typedValue2.type && typedValue.data == typedValue2.data && typedValue.resourceId == typedValue2.resourceId) ? createSlideBarBitmap : createSlideBarBitmap(drawable2);
        this.mFrame.setBounds(0, 0, this.mWidth, this.mHeight);
        if (!(createSlideBarBitmap2 == null || createSlideBarBitmap == null)) {
            Drawable drawable3 = typedArray.getDrawable(R.styleable.SlidingButton_mask);
            drawable3.setBounds(0, 0, this.mWidth, this.mHeight);
            Bitmap convertToAlphaMask = convertToAlphaMask(drawable3);
            this.mSlideBar = createMaskedSlideBar(context, convertToAlphaMask, createSlideBarBitmap2, createSlideBarBitmap);
            convertToAlphaMask.recycle();
        }
        if (createSlideBarBitmap2 != null && !createSlideBarBitmap2.isRecycled()) {
            createSlideBarBitmap2.recycle();
        }
        if (createSlideBarBitmap != null && !createSlideBarBitmap.isRecycled()) {
            createSlideBarBitmap.recycle();
        }
        setSliderDrawState();
        if (this.mView.isChecked()) {
            setSliderOffset(this.mSliderPositionEnd);
        }
    }

    public void jumpDrawablesToCurrentState() {
        StateListDrawable stateListDrawable = this.mSlideBar;
        if (stateListDrawable != null) {
            stateListDrawable.jumpToCurrentState();
        }
    }

    public void notifyCheckedChangeListener() {
        if (this.mOnPerformCheckedChangeListener != null) {
            this.mOnPerformCheckedChangeListener.onCheckedChanged(this.mView, this.mView.isChecked());
        }
    }

    public void onDraw(Canvas canvas) {
        int i = this.mView.isEnabled() ? 255 : 127;
        float f = ((float) i) / 255.0f;
        onDrawSlideBar(canvas, f, this.mSlideBar);
        this.mFrame.setAlpha(i);
        this.mFrame.draw(canvas);
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this.mView);
        int i2 = isLayoutRtl ? (this.mWidth - this.mSliderOffset) - this.mSliderWidth : this.mSliderOffset;
        int i3 = isLayoutRtl ? this.mWidth - this.mSliderOffset : this.mSliderWidth + this.mSliderOffset;
        int i4 = this.mHeight;
        int i5 = this.mSliderHeight;
        int i6 = (i4 - i5) / 2;
        int i7 = i6 + i5;
        int i8 = (i3 + i2) / 2;
        int i9 = (i7 + i6) / 2;
        onDrawSliderShadow(canvas, i8, i9);
        scaleCanvasStart(canvas, i8, i9);
        if (this.mAnimChecked) {
            this.mSliderOn.setAlpha(i);
            this.mSliderOn.setBounds(i2, i6, i3, i7);
            this.mSliderOn.draw(canvas);
        } else {
            this.mSliderOff.setAlpha(i);
            this.mSliderOff.setBounds(i2, i6, i3, i7);
            this.mSliderOff.draw(canvas);
        }
        onDrawSliderStroke(canvas, f, i2, i6, i3, i7);
        scaleCanvasEnd(canvas);
    }

    public void onDrawSlideBar(Canvas canvas, float f, Drawable drawable) {
        this.mMaskUnCheckedSlideBar.setAlpha((int) (f * 255.0f));
        this.mMaskUnCheckedSlideBar.draw(canvas);
        this.mMaskUnCheckedPressedSlideBar.setAlpha((int) (this.mMaskUnCheckedPressedSlideBarAlpha * 255.0f * f));
        this.mMaskUnCheckedPressedSlideBar.draw(canvas);
        this.mMaskCheckedSlideBar.setAlpha((int) (this.mMaskCheckedSlideBarAlpha * 255.0f * f));
        this.mMaskCheckedSlideBar.draw(canvas);
    }

    public void onDrawSliderShadow(Canvas canvas, int i, int i2) {
        int i3;
        int i4;
        Drawable drawable = this.mSliderShadow;
        if (drawable instanceof BitmapDrawable) {
            i4 = ((BitmapDrawable) drawable).getBitmap().getWidth();
            i3 = ((BitmapDrawable) this.mSliderShadow).getBitmap().getHeight();
        } else {
            i4 = drawable.getIntrinsicWidth();
            i3 = this.mSliderShadow.getIntrinsicHeight();
        }
        int i5 = i4 / 2;
        int i6 = i3 / 2;
        this.mSliderShadow.setBounds(i - i5, i2 - i6, i + i5, i2 + i6);
        this.mSliderShadow.setAlpha((int) (this.mSliderShadowAlpha * 255.0f));
        this.mSliderShadow.draw(canvas);
    }

    public void onDrawSliderStroke(Canvas canvas, float f, int i, int i2, int i3, int i4) {
        this.mSliderStroke.setAlpha((int) (this.mStrokeAlpha * 255.0f * f));
        this.mSliderStroke.setBounds(i, i2, i3, i4);
        this.mSliderStroke.draw(canvas);
    }

    public void onPressedInner() {
        if (this.mSliderUnPressedAnim.isRunning()) {
            this.mSliderUnPressedAnim.cancel();
        }
        if (!this.mSliderPressedAnim.isRunning()) {
            this.mSliderPressedAnim.start();
        }
        if (!this.mSliderShadowShowAnim.isRunning()) {
            this.mSliderShadowShowAnim.start();
        }
        if (!this.mView.isChecked()) {
            if (this.mUnMarkedPressedAlphaHideAnim.isRunning()) {
                this.mUnMarkedPressedAlphaHideAnim.cancel();
            }
            if (!this.mUnMarkedPressedAlphaShowAnim.isRunning()) {
                this.mUnMarkedPressedAlphaShowAnim.start();
            }
            if (!this.mStokeAlphaShowAnim.isRunning()) {
                this.mStokeAlphaShowAnim.start();
            }
        }
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        Rect rect = this.mTmpRect;
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this.mView);
        rect.set(isLayoutRtl ? (this.mWidth - this.mSliderOffset) - this.mSliderWidth : this.mSliderOffset, 0, isLayoutRtl ? this.mWidth - this.mSliderOffset : this.mSliderOffset + this.mSliderWidth, this.mHeight);
        if (action == 0) {
            onTouchEventDown(x, y, rect);
        } else if (action == 1) {
            onUnPressedInner();
            onTouchEventUp(x, y, rect);
            this.mTracking = false;
            this.mSliderMoved = false;
            this.mView.setPressed(false);
        } else if (action == 2) {
            onTouchEventMove(x);
        } else if (action == 3) {
            onUnPressedInner();
            this.mTracking = false;
            this.mSliderMoved = false;
            this.mView.setPressed(false);
        }
    }

    public void onUnPressedInner() {
        if (this.mSliderPressedAnim.isRunning()) {
            this.mSliderPressedAnim.cancel();
        }
        if (!this.mSliderUnPressedAnim.isRunning()) {
            this.mSliderUnPressedAnim.start();
        }
        if (this.mSliderShadowShowAnim.isRunning()) {
            this.mSliderShadowShowAnim.cancel();
        }
        if (!this.mSliderShadowHideAnim.isRunning()) {
            this.mSliderShadowHideAnim.start();
        }
        if (this.mStokeAlphaShowAnim.isRunning()) {
            this.mStokeAlphaShowAnim.cancel();
        }
        if (!this.mView.isChecked()) {
            if (this.mUnMarkedPressedAlphaShowAnim.isRunning()) {
                this.mUnMarkedPressedAlphaShowAnim.cancel();
            }
            if (!this.mUnMarkedPressedAlphaHideAnim.isRunning()) {
                this.mUnMarkedPressedAlphaHideAnim.start();
            }
            if (!this.mStokeAlphaHideAnim.isRunning()) {
                this.mStokeAlphaHideAnim.start();
            }
        }
    }

    public void scaleCanvasEnd(Canvas canvas) {
        canvas.restore();
    }

    public void scaleCanvasStart(Canvas canvas, int i, int i2) {
        canvas.save();
        float f = this.mSliderScale;
        canvas.scale(f, f, (float) i, (float) i2);
    }

    public void setChecked(boolean z) {
        saveCurrentParams();
        this.mSliderOffset = z ? this.mSliderPositionEnd : this.mSliderPositionStart;
        this.mSliderOnAlpha = z ? 255 : 0;
        this.mMaskCheckedSlideBarAlpha = z ? 1.0f : 0.0f;
        SpringAnimation springAnimation = this.mSliderMoveAnim;
        if (springAnimation != null && springAnimation.isRunning()) {
            this.mSliderMoveAnim.cancel();
        }
        if (this.mMarkedAlphaHideAnim.isRunning()) {
            this.mMarkedAlphaHideAnim.cancel();
        }
        if (this.mMarkedAlphaShowAnim.isRunning()) {
            this.mMarkedAlphaShowAnim.cancel();
        }
        this.mView.invalidate();
    }

    public void setCheckedInner(boolean z) {
        if (this.mAnimChecked) {
            updateCheckedAnim(z);
        } else {
            updateUnCheckedAnim(z);
        }
    }

    public void setMaskCheckedSlideBarAlpha(float f) {
        this.mMaskCheckedSlideBarAlpha = f;
    }

    public void setMaskUnCheckedPressedSlideBarAlpha(float f) {
        this.mMaskUnCheckedPressedSlideBarAlpha = f;
    }

    public void setOnPerformCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnPerformCheckedChangeListener = onCheckedChangeListener;
    }

    public void setParentClipChildren() {
        ViewParent parent = this.mView.getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            ((ViewGroup) parent).setClipChildren(false);
        }
    }

    public void setSliderDrawState() {
        Drawable drawable = this.mSliderOn;
        if (drawable != null) {
            drawable.setState(this.mView.getDrawableState());
            this.mSlideBar.setState(this.mView.getDrawableState());
        }
    }

    public void setSliderOffset(int i) {
        this.mSliderOffset = i;
        this.mView.invalidate();
    }

    public void setSliderOnAlpha(int i) {
        this.mSliderOnAlpha = i;
        this.mView.invalidate();
    }

    public void setSliderScale(float f) {
        this.mSliderScale = f;
    }

    public void setSliderShadowAlpha(float f) {
        this.mSliderShadowAlpha = f;
    }

    public void setStrokeAlpha(float f) {
        this.mStrokeAlpha = f;
    }

    public boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mSlideBar;
    }
}
