package com.android.camera.ui.zoom;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.view.ViewCompat;
import b.c.a.c;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import miui.view.animation.CubicEaseOutInterpolator;
import miui.view.animation.QuadraticEaseOutInterpolator;

public class ZoomRatioToggleView extends ViewGroup implements View.OnClickListener, View.OnLongClickListener {
    private static final int ANIMATOR_TYPE_IMAGE_IMAGE = 6;
    private static final int ANIMATOR_TYPE_IMAGE_NONE = 3;
    private static final int ANIMATOR_TYPE_IMAGE_TEXT = 4;
    private static final int ANIMATOR_TYPE_NONE_IMAGE = 1;
    private static final int ANIMATOR_TYPE_NONE_TEXT = 0;
    private static final int ANIMATOR_TYPE_TEXT_IMAGE = 5;
    private static final int ANIMATOR_TYPE_TEXT_NONE = 2;
    private static final int ANIMATOR_TYPE_TEXT_TEXT = 7;
    private static final int ANIMATOR_TYPE_TEXT_TEXT_IMMERSIVE_OUT = 8;
    private static final int INVALID_INDEX = -1;
    private static final float INVALID_ZOOM_RATIO = -1.0f;
    private static final int PIXELS_PER_SECOND = 1000;
    private static final String TAG = "ZoomRatioToggleView";
    private static final int TOUCH_SCROLL_THRESHOLD = 10;
    private static final int TOUCH_STATE_CLICK = 1;
    private static final int TOUCH_STATE_IDLE = 0;
    private static final int TOUCH_STATE_LONG_CLICK = 3;
    private static final int TOUCH_STATE_SCROLL = 2;
    private static final int TOUCH_STATE_SHOWVIEW = 4;
    private static final boolean UI_DEBUG_ENABLED = Log.isLoggable(TAG, 3);
    private static final int VELOCITY_THRESHOLD = 100;
    private ToggleStateListener mActionListener;
    private int mCurrentModule;
    private int mCurrentSelectedChildIndex;
    private final Runnable mIndexUpdater;
    private boolean mIsImmersive;
    private boolean mIsSuppressed;
    private int mItemHeight;
    private int mItemWidth;
    private Runnable mLongPressRunnable;
    private Paint mPaint;
    private Rect mRect;
    private AnimatorSet mShowZoomChildAnimatorSet;
    private int mTouchStartX;
    private int mTouchStartY;
    private int mTouchState;
    private final Handler mUiHandler;
    private boolean mUseSliderAllowed;
    private VelocityTracker mVelocityTracker;
    private float[] mZoomArray;
    private AnimatorSet mZoomInOutAnimatorSet;
    private float mZoomRatio;
    private AnimatorSet mZoomShowAnimatorSet;

    public interface ToggleStateListener {
        boolean isInteractive();

        void onClick(ZoomRatioView zoomRatioView);

        boolean toShowSlideView(ZoomRatioView zoomRatioView);
    }

    public static class ViewSpec {
        public final boolean immersive;
        public final boolean suppress;
        public final boolean useSlider;
        public final int visibility;

        public ViewSpec(int i, boolean z, boolean z2, boolean z3) {
            this.visibility = i;
            this.suppress = z;
            this.immersive = z2;
            this.useSlider = z3;
        }
    }

    public ZoomRatioToggleView(@NonNull Context context) {
        this(context, null);
    }

    public ZoomRatioToggleView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZoomRatioToggleView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ZoomRatioToggleView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTouchState = 0;
        this.mCurrentModule = 163;
        this.mUseSliderAllowed = false;
        this.mIsImmersive = false;
        this.mIsSuppressed = false;
        this.mUiHandler = new Handler();
        this.mIndexUpdater = new Runnable() {
            /* class com.android.camera.ui.zoom.ZoomRatioToggleView.AnonymousClass1 */

            public void run() {
                ZoomRatioToggleView.debugUi(ZoomRatioToggleView.TAG, "Macro mode not change");
                ZoomRatioToggleView.this.setSelectedChildIndex();
                ZoomRatioToggleView.debugUi(ZoomRatioToggleView.TAG, "run index updater ");
            }
        };
        init();
    }

    static /* synthetic */ void a(AnimatorSet animatorSet) {
        if (animatorSet != null) {
            animatorSet.cancel();
            animatorSet.removeAllListeners();
        }
    }

    private void announceCurrentZoomRatioForAccessibility() {
        String valueOf = String.valueOf(HybridZoomingSystem.toDecimal(this.mZoomRatio));
        setContentDescription(getString(R.string.accessibility_focus_status, valueOf));
        announceForAccessibility(getString(R.string.accessibility_focus_status, valueOf));
    }

    private void clickChildAt(int i, int i2) {
        int containingChildIndex = getContainingChildIndex(i, i2);
        if (containingChildIndex == -1) {
            return;
        }
        if (containingChildIndex != this.mCurrentSelectedChildIndex) {
            showZoomChildView(true, containingChildIndex, 1);
        } else if (!this.mIsSuppressed || this.mCurrentModule == 188) {
            ToggleStateListener toggleStateListener = this.mActionListener;
            if (toggleStateListener != null && toggleStateListener.toShowSlideView(null)) {
                performHapticFeedback(0);
            }
        } else {
            ZoomRatioView zoomRatioView = (ZoomRatioView) getChildAt(containingChildIndex);
            if (zoomRatioView != null) {
                this.mZoomInOutAnimatorSet.setTarget(zoomRatioView);
                this.mZoomInOutAnimatorSet.start();
                zoomRatioView.setZoomRatio(this.mZoomRatio);
                ToggleStateListener toggleStateListener2 = this.mActionListener;
                if (toggleStateListener2 != null) {
                    toggleStateListener2.onClick(zoomRatioView);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void debugUi(String str, String str2) {
        if (UI_DEBUG_ENABLED || c._g || c.bh || c.dh) {
            com.android.camera.log.Log.d(str, str2);
        }
    }

    private void endTouch(float f) {
        if (this.mActionListener != null && Math.abs(f) >= 100.0f && !this.mIsImmersive && !this.mIsSuppressed && this.mUseSliderAllowed && this.mActionListener.toShowSlideView(null)) {
            performHapticFeedback(0);
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        removeCallbacks(this.mLongPressRunnable);
        this.mTouchState = 0;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getContainingChildIndex(int i, int i2) {
        if (this.mRect == null) {
            this.mRect = new Rect();
        }
        if (this.mIsSuppressed) {
            getChildAt(0).getHitRect(this.mRect);
            Rect rect = this.mRect;
            rect.left = (int) (((float) rect.left) - (((float) rect.width()) * 0.5f));
            Rect rect2 = this.mRect;
            rect2.right = (int) (((float) rect2.right) + (((float) rect2.width()) * 0.5f));
            if (this.mRect.contains(i, i2)) {
                return this.mCurrentSelectedChildIndex;
            }
            return -1;
        }
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            getChildAt(i3).getHitRect(this.mRect);
            if (i3 == 0) {
                Rect rect3 = this.mRect;
                rect3.left = (int) (((float) rect3.left) - (((float) rect3.width()) * 0.5f));
            } else if (i3 == getChildCount() - 1) {
                Rect rect4 = this.mRect;
                rect4.right = (int) (((float) rect4.right) + (((float) rect4.width()) * 0.5f));
            }
            if (this.mRect.contains(i, i2)) {
                return i3;
            }
        }
        return -1;
    }

    private int getDimensionPixelSize(@DimenRes int i) {
        return getResources().getDimensionPixelSize(i);
    }

    private String getString(@StringRes int i, Object... objArr) {
        return getResources().getString(i, objArr);
    }

    private void getTypeAnimator(List<Animator> list, ZoomRatioView zoomRatioView, int i) {
        switch (i) {
            case 0:
            case 4:
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.ALPHA, 0.0f, 0.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.ALPHA, 0.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_X, 0.167f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_Y, 0.167f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_X, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_Y, 1.0f, 1.0f));
                return;
            case 1:
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.ALPHA, 0.0f, 0.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.ALPHA, 0.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_X, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_X, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_Y, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_Y, 1.0f, 1.0f));
                return;
            case 2:
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.ALPHA, 1.0f, 0.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_X, 1.0f, 0.167f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_Y, 1.0f, 0.167f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.ALPHA, 0.0f, 0.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_X, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_Y, 1.0f, 1.0f));
                return;
            case 3:
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.ALPHA, 0.0f, 0.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.ALPHA, 1.0f, 0.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_X, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_X, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_Y, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_Y, 1.0f, 1.0f));
                return;
            case 5:
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.ALPHA, 0.0f, 0.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.ALPHA, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_X, 1.67f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_Y, 1.67f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_X, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_Y, 1.0f, 1.0f));
                return;
            case 6:
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.ALPHA, 0.0f, 0.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.ALPHA, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_X, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_X, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_Y, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_Y, 1.0f, 1.0f));
                return;
            case 7:
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.ALPHA, 0.0f, 0.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.ALPHA, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_X, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_X, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_Y, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_Y, 1.0f, 1.0f));
                return;
            case 8:
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.ALPHA, 0.0f, 0.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.ALPHA, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_X, 1.1f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_X, 1.0f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getTextView(), View.SCALE_Y, 1.1f, 1.0f));
                list.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.SCALE_Y, 1.0f, 1.0f));
                return;
            default:
                return;
        }
    }

    private void init() {
        setWillNotDraw(false);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mZoomShowAnimatorSet = new AnimatorSet();
        this.mZoomShowAnimatorSet.setInterpolator(new CubicEaseOutInterpolator());
        this.mZoomShowAnimatorSet.setDuration(400L);
        this.mShowZoomChildAnimatorSet = new AnimatorSet();
        this.mShowZoomChildAnimatorSet.setDuration(200L);
        this.mShowZoomChildAnimatorSet.setInterpolator(new CubicEaseOutInterpolator());
        this.mZoomInOutAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.zoom_button_zoom_in_out);
        this.mZoomInOutAnimatorSet.setTarget(this);
        this.mZoomInOutAnimatorSet.setInterpolator(new QuadraticEaseOutInterpolator());
    }

    private boolean isLayoutRTL() {
        if (getContext() == null) {
            return false;
        }
        return getResources().getConfiguration().getLayoutDirection() == 1;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void longClickChild(int i) {
        View childAt;
        if (!this.mUseSliderAllowed) {
            return;
        }
        if ((!this.mIsImmersive || this.mIsSuppressed) && i != HybridZoomingSystem.getMacroZoomRatioIndex(this.mCurrentModule) && this.mActionListener != null && (childAt = getChildAt(i)) != null && this.mActionListener.toShowSlideView((ZoomRatioView) childAt)) {
            performHapticFeedback(0);
        }
    }

    private void resetAnimators() {
        debugUi(TAG, "resetAnimators");
        Arrays.asList(this.mZoomInOutAnimatorSet, this.mShowZoomChildAnimatorSet, this.mZoomShowAnimatorSet).forEach(a.INSTANCE);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setSelectedChildIndex() {
        int opticalZoomRatioIndex = HybridZoomingSystem.getOpticalZoomRatioIndex(this.mCurrentModule, this.mZoomRatio);
        if (isLayoutRTL()) {
            opticalZoomRatioIndex = (getChildCount() - 1) - opticalZoomRatioIndex;
        }
        showZoomChildView(!(opticalZoomRatioIndex == this.mCurrentSelectedChildIndex || getVisibility() != 0), opticalZoomRatioIndex, 0);
    }

    private void showZoomChildView(boolean z, int i, int i2) {
        ToggleStateListener toggleStateListener;
        removeCallbacks(this.mIndexUpdater);
        final ZoomRatioView zoomRatioView = (ZoomRatioView) getChildAt(i);
        final ZoomRatioView zoomRatioView2 = (ZoomRatioView) getChildAt(this.mCurrentSelectedChildIndex);
        float opticalZoomRatioAt = HybridZoomingSystem.getOpticalZoomRatioAt(this.mCurrentModule, i);
        if (!z) {
            this.mCurrentSelectedChildIndex = i;
            if (zoomRatioView2 != null) {
                zoomRatioView2.setIconify(true);
            }
            if (zoomRatioView != null) {
                zoomRatioView.setIconify(false);
                if (i2 != 1) {
                    opticalZoomRatioAt = this.mZoomRatio;
                }
                zoomRatioView.setZoomRatio(opticalZoomRatioAt);
            }
        } else {
            ArrayList arrayList = new ArrayList();
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                ZoomRatioView zoomRatioView3 = (ZoomRatioView) getChildAt(i3);
                if (i3 == i) {
                    zoomRatioView3.setZoomRatio(i2 == 1 ? opticalZoomRatioAt : this.mZoomRatio);
                    getTypeAnimator(arrayList, zoomRatioView3, 4);
                } else if (i3 == this.mCurrentSelectedChildIndex) {
                    getTypeAnimator(arrayList, zoomRatioView3, 5);
                } else {
                    getTypeAnimator(arrayList, zoomRatioView3, 6);
                }
            }
            this.mCurrentSelectedChildIndex = i;
            this.mShowZoomChildAnimatorSet.setDuration(200L);
            this.mShowZoomChildAnimatorSet.cancel();
            this.mShowZoomChildAnimatorSet.removeAllListeners();
            this.mShowZoomChildAnimatorSet.addListener(new AnimatorListenerAdapter() {
                /* class com.android.camera.ui.zoom.ZoomRatioToggleView.AnonymousClass4 */

                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    ZoomRatioView zoomRatioView = zoomRatioView2;
                    if (zoomRatioView != null) {
                        zoomRatioView.setIconify(true);
                        zoomRatioView2.resetScale();
                    }
                    ZoomRatioView zoomRatioView2 = zoomRatioView;
                    if (zoomRatioView2 != null) {
                        zoomRatioView2.setIconify(false);
                        zoomRatioView.resetScale();
                    }
                }
            });
            this.mShowZoomChildAnimatorSet.playTogether(arrayList);
            this.mShowZoomChildAnimatorSet.start();
        }
        if (i2 == 1 && (toggleStateListener = this.mActionListener) != null) {
            toggleStateListener.onClick(zoomRatioView);
        }
        announceCurrentZoomRatioForAccessibility();
    }

    private void startLongPressCheck() {
        Runnable runnable = this.mLongPressRunnable;
        if (runnable == null) {
            this.mLongPressRunnable = new Runnable() {
                /* class com.android.camera.ui.zoom.ZoomRatioToggleView.AnonymousClass5 */

                public void run() {
                    if (ZoomRatioToggleView.this.mTouchState == 1) {
                        ZoomRatioToggleView zoomRatioToggleView = ZoomRatioToggleView.this;
                        int containingChildIndex = zoomRatioToggleView.getContainingChildIndex(zoomRatioToggleView.mTouchStartX, ZoomRatioToggleView.this.mTouchStartY);
                        if (containingChildIndex != -1) {
                            ZoomRatioToggleView.this.longClickChild(containingChildIndex);
                        }
                        ZoomRatioToggleView.this.mTouchState = 3;
                    }
                }
            };
        } else {
            removeCallbacks(runnable);
        }
        postDelayed(this.mLongPressRunnable, (long) ViewConfiguration.getLongPressTimeout());
    }

    private boolean startScrollIfNeeded(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int i = this.mTouchStartX;
        if (x >= i - 10 && x <= i + 10) {
            int i2 = this.mTouchStartY;
            if (y >= i2 - 10 && y <= i2 + 10) {
                return false;
            }
        }
        removeCallbacks(this.mLongPressRunnable);
        this.mTouchState = 2;
        return true;
    }

    private void startTouch(MotionEvent motionEvent) {
        this.mTouchStartX = (int) motionEvent.getX();
        this.mTouchStartY = (int) motionEvent.getY();
        startLongPressCheck();
        this.mVelocityTracker = VelocityTracker.obtain();
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.addMovement(motionEvent);
        }
        this.mTouchState = 1;
    }

    private void toShowView(float f) {
        ToggleStateListener toggleStateListener;
        if (Math.abs(f) >= 100.0f && this.mUseSliderAllowed && getContainingChildIndex(this.mTouchStartX, this.mTouchStartY) != -1 && (toggleStateListener = this.mActionListener) != null && toggleStateListener.toShowSlideView(null)) {
            performHapticFeedback(0);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            removeCallbacks(this.mLongPressRunnable);
            this.mTouchState = 4;
        }
    }

    public boolean isSuppressed() {
        return this.mIsSuppressed;
    }

    public void onClick(View view) {
        if (getVisibility() == 0) {
            debugUi(TAG, "UI AUTOMATIC TEST: CLICKED");
            clickChildAt(getWidth() / 2, getHeight() / 2);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return true;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            debugUi(TAG, "onInterceptTouchEvent() DOWN: " + this.mIsImmersive);
            if ((this.mIsImmersive || this.mIsSuppressed) && getContainingChildIndex((int) motionEvent.getX(), (int) motionEvent.getY()) != this.mCurrentSelectedChildIndex) {
                return false;
            }
            ToggleStateListener toggleStateListener = this.mActionListener;
            if (toggleStateListener != null && !toggleStateListener.isInteractive()) {
                return false;
            }
            startTouch(motionEvent);
            return false;
        } else if (action != 2) {
            endTouch(0.0f);
            return false;
        } else {
            debugUi(TAG, "onInterceptTouchEvent() MOVE: " + this.mIsImmersive);
            return startScrollIfNeeded(motionEvent);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int childCount = getChildCount();
        if (childCount > 0) {
            int i6 = childCount == 0 ? 0 : childCount / 2;
            boolean isLayoutRTL = isLayoutRTL();
            if (!isLayoutRTL) {
                int i7 = this.mItemWidth;
                i5 = ((getWidth() / 2) - (i7 / 2)) - (i6 * i7);
            } else {
                int i8 = this.mItemWidth;
                i5 = ((getWidth() / 2) - (i8 / 2)) + (i6 * i8);
            }
            int height = (getHeight() / 2) - (this.mItemHeight / 2);
            for (int i9 = 0; i9 < childCount; i9++) {
                getChildAt(i9).layout(i5, height, this.mItemWidth + i5, this.mItemHeight + height);
                i5 = !isLayoutRTL ? i5 + this.mItemWidth : i5 - this.mItemWidth;
            }
        }
    }

    public boolean onLongClick(View view) {
        if (getVisibility() != 0) {
            return false;
        }
        debugUi(TAG, "UI AUTOMATIC TEST: LONGCLICKED");
        longClickChild(this.mCurrentSelectedChildIndex);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        measureChildren(i, i2);
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                i4 += childAt.getMeasuredWidth();
                i5 = Math.max(i5, childAt.getMeasuredHeight());
                i3++;
            }
        }
        this.mItemWidth = i3 == 0 ? 0 : i4 / i3;
        this.mItemHeight = i5;
        setMeasuredDimension(ViewGroup.resolveSizeAndState(Math.max(i4 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i, 0), ViewGroup.resolveSizeAndState(Math.max(i5 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i2, 0));
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        int eventType = accessibilityEvent.getEventType();
        if (eventType == 4 || eventType == 32768) {
            accessibilityEvent.getText().add(getString(R.string.accessibility_focus_status, Float.valueOf(this.mZoomRatio)));
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (getChildCount() == 0 || !isEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 0) {
            float f = 0.0f;
            if (action != 1) {
                if (action == 2) {
                    debugUi(TAG, "onTouchEvent() MOVE: " + this.mIsImmersive);
                    if (this.mTouchState == 1) {
                        startScrollIfNeeded(motionEvent);
                    }
                    if (this.mTouchState == 2) {
                        VelocityTracker velocityTracker = this.mVelocityTracker;
                        if (velocityTracker == null) {
                            return false;
                        }
                        velocityTracker.addMovement(motionEvent);
                        this.mVelocityTracker.computeCurrentVelocity(1000);
                        toShowView(this.mVelocityTracker.getXVelocity());
                    }
                } else if (action != 3) {
                    endTouch(0.0f);
                }
            }
            debugUi(TAG, "onTouchEvent() UP: " + this.mIsImmersive);
            ToggleStateListener toggleStateListener = this.mActionListener;
            if (toggleStateListener != null && toggleStateListener.isInteractive() && this.mTouchState == 1) {
                clickChildAt((int) motionEvent.getX(), (int) motionEvent.getY());
            }
            if (this.mUseSliderAllowed && this.mTouchState == 4) {
                return true;
            }
            if (this.mTouchState == 2) {
                VelocityTracker velocityTracker2 = this.mVelocityTracker;
                if (velocityTracker2 == null) {
                    return false;
                }
                velocityTracker2.addMovement(motionEvent);
                this.mVelocityTracker.computeCurrentVelocity(1000);
                f = this.mVelocityTracker.getXVelocity();
            }
            endTouch(f);
        } else {
            debugUi(TAG, "onTouchEvent() DOWN: " + this.mIsImmersive);
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (this.mIsImmersive || this.mIsSuppressed) {
                if (getContainingChildIndex(x, y) != this.mCurrentSelectedChildIndex) {
                    return false;
                }
            } else if (getContainingChildIndex(x, y) == -1) {
                return false;
            }
            ToggleStateListener toggleStateListener2 = this.mActionListener;
            if (toggleStateListener2 != null && !toggleStateListener2.isInteractive()) {
                return false;
            }
            startTouch(motionEvent);
        }
        return true;
    }

    public void setActionListener(ToggleStateListener toggleStateListener) {
        this.mActionListener = toggleStateListener;
    }

    public void setBackgroundColor(int i) {
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            ((ZoomRatioView) getChildAt(i2)).setBackgroundColor(i);
        }
    }

    public void setCaptureCount(int i) {
        if (this.mIsImmersive || this.mIsSuppressed) {
            ((ZoomRatioView) getChildAt(this.mIsSuppressed ? 0 : getChildCount() / 2)).setCaptureCount(i);
            return;
        }
        ZoomRatioView zoomRatioView = (ZoomRatioView) getChildAt(this.mCurrentSelectedChildIndex);
        if (zoomRatioView != null) {
            zoomRatioView.setCaptureCount(i);
        }
    }

    public boolean setCapturingMode(int i, boolean z) {
        this.mCurrentModule = i;
        float[] supportedOpticalZoomRatios = HybridZoomingSystem.getSupportedOpticalZoomRatios(this.mCurrentModule);
        if (z) {
            supportedOpticalZoomRatios = new float[]{1.0f};
        }
        int length = supportedOpticalZoomRatios.length;
        if (length <= 0) {
            return false;
        }
        float[] fArr = this.mZoomArray;
        if (fArr == null || !Arrays.equals(fArr, supportedOpticalZoomRatios)) {
            this.mZoomArray = supportedOpticalZoomRatios;
            int defaultOpticalZoomRatioIndex = z ? 0 : HybridZoomingSystem.getDefaultOpticalZoomRatioIndex(this.mCurrentModule);
            resetAnimators();
            removeAllViews();
            int i2 = 0;
            while (i2 < length) {
                ZoomRatioView zoomRatioView = (ZoomRatioView) LayoutInflater.from(getContext()).inflate(R.layout.zoom_ratio_item_view, (ViewGroup) null);
                zoomRatioView.getIconView().setVisibility(0);
                zoomRatioView.getTextView().setVisibility(0);
                zoomRatioView.setZoomRatio(supportedOpticalZoomRatios[i2]);
                zoomRatioView.setZoomRatioIndex(i2);
                zoomRatioView.setIconify(i2 != defaultOpticalZoomRatioIndex);
                addView(zoomRatioView, new ViewGroup.LayoutParams(-2, -2));
                i2++;
            }
            this.mCurrentSelectedChildIndex = defaultOpticalZoomRatioIndex;
            this.mZoomRatio = supportedOpticalZoomRatios[defaultOpticalZoomRatioIndex];
            setSuppressed(z, true);
            return true;
        }
        setSuppressed(z, false);
        return false;
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        debugUi(TAG, "setEnabled(): " + z);
    }

    public void setImmersive(boolean z, boolean z2) {
        setImmersive(z, z2, true);
    }

    public void setImmersive(boolean z, boolean z2, final boolean z3) {
        debugUi(TAG, "setImmersive(): " + z);
        if (z != this.mIsImmersive || z2) {
            this.mIsImmersive = z;
            ArrayList arrayList = new ArrayList();
            final int opticalZoomRatioIndex = HybridZoomingSystem.getOpticalZoomRatioIndex(this.mCurrentModule, this.mZoomRatio);
            if (this.mIsImmersive) {
                if (!this.mIsSuppressed) {
                    if (opticalZoomRatioIndex == getChildCount() / 2) {
                        int childCount = getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            ZoomRatioView zoomRatioView = (ZoomRatioView) getChildAt(i);
                            zoomRatioView.setVisibility(0);
                            if (i == opticalZoomRatioIndex) {
                                getTypeAnimator(arrayList, zoomRatioView, 7);
                            } else {
                                getTypeAnimator(arrayList, zoomRatioView, 3);
                            }
                        }
                    } else {
                        int childCount2 = getChildCount();
                        for (int i2 = 0; i2 < childCount2; i2++) {
                            ZoomRatioView zoomRatioView2 = (ZoomRatioView) getChildAt(i2);
                            zoomRatioView2.setVisibility(0);
                            if (i2 == childCount2 / 2) {
                                getTypeAnimator(arrayList, zoomRatioView2, 4);
                            } else if (i2 == opticalZoomRatioIndex) {
                                getTypeAnimator(arrayList, zoomRatioView2, 2);
                            } else {
                                getTypeAnimator(arrayList, zoomRatioView2, 3);
                            }
                        }
                    }
                    this.mShowZoomChildAnimatorSet.setDuration(200L);
                    this.mShowZoomChildAnimatorSet.cancel();
                    this.mShowZoomChildAnimatorSet.removeAllListeners();
                    this.mShowZoomChildAnimatorSet.addListener(new AnimatorListenerAdapter() {
                        /* class com.android.camera.ui.zoom.ZoomRatioToggleView.AnonymousClass2 */

                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            int childCount = ZoomRatioToggleView.this.getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                ZoomRatioView zoomRatioView = (ZoomRatioView) ZoomRatioToggleView.this.getChildAt(i);
                                zoomRatioView.resetScale();
                                if (childCount == 1 || i == childCount / 2) {
                                    zoomRatioView.setVisibility(0);
                                    zoomRatioView.setIconify(false);
                                } else {
                                    zoomRatioView.setVisibility(4);
                                    zoomRatioView.setIconify(true);
                                }
                            }
                        }

                        public void onAnimationStart(Animator animator) {
                            super.onAnimationStart(animator);
                            if (!z3) {
                                int childCount = ZoomRatioToggleView.this.getChildCount();
                                for (int i = 0; i < childCount; i++) {
                                    ZoomRatioView zoomRatioView = (ZoomRatioView) ZoomRatioToggleView.this.getChildAt(i);
                                    if (!(childCount == 1 || i == childCount / 2)) {
                                        zoomRatioView.setVisibility(4);
                                        zoomRatioView.setIconify(true);
                                    }
                                }
                            }
                        }
                    });
                    this.mShowZoomChildAnimatorSet.playTogether(arrayList);
                    this.mShowZoomChildAnimatorSet.start();
                }
            } else if (!this.mIsSuppressed) {
                if (opticalZoomRatioIndex == getChildCount() / 2) {
                    int childCount3 = getChildCount();
                    for (int i3 = 0; i3 < childCount3; i3++) {
                        ZoomRatioView zoomRatioView3 = (ZoomRatioView) getChildAt(i3);
                        zoomRatioView3.setVisibility(0);
                        if (i3 == opticalZoomRatioIndex) {
                            zoomRatioView3.setZoomRatio(this.mZoomRatio);
                            getTypeAnimator(arrayList, zoomRatioView3, 8);
                        } else {
                            getTypeAnimator(arrayList, zoomRatioView3, 1);
                        }
                    }
                    this.mShowZoomChildAnimatorSet.setDuration(400L);
                } else {
                    int childCount4 = getChildCount();
                    for (int i4 = 0; i4 < childCount4; i4++) {
                        ZoomRatioView zoomRatioView4 = (ZoomRatioView) getChildAt(i4);
                        zoomRatioView4.setVisibility(0);
                        if (i4 == childCount4 / 2) {
                            getTypeAnimator(arrayList, zoomRatioView4, 5);
                        } else if (i4 == opticalZoomRatioIndex) {
                            zoomRatioView4.setZoomRatio(this.mZoomRatio);
                            getTypeAnimator(arrayList, zoomRatioView4, 0);
                        } else {
                            getTypeAnimator(arrayList, zoomRatioView4, 1);
                        }
                    }
                    this.mShowZoomChildAnimatorSet.setDuration(200L);
                }
                this.mShowZoomChildAnimatorSet.cancel();
                this.mShowZoomChildAnimatorSet.removeAllListeners();
                this.mShowZoomChildAnimatorSet.addListener(new AnimatorListenerAdapter() {
                    /* class com.android.camera.ui.zoom.ZoomRatioToggleView.AnonymousClass3 */

                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        int childCount = ZoomRatioToggleView.this.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            ZoomRatioView zoomRatioView = (ZoomRatioView) ZoomRatioToggleView.this.getChildAt(i);
                            zoomRatioView.setVisibility(0);
                            zoomRatioView.resetScale();
                            if (i == opticalZoomRatioIndex) {
                                zoomRatioView.setZoomRatio(ZoomRatioToggleView.this.mZoomRatio);
                                zoomRatioView.setIconify(false);
                            } else {
                                zoomRatioView.setIconify(true);
                            }
                        }
                    }
                });
                this.mShowZoomChildAnimatorSet.playTogether(arrayList);
                this.mShowZoomChildAnimatorSet.start();
            }
        } else {
            debugUi(TAG, "setImmersive() ignored: " + z);
        }
    }

    public void setRotation(float f) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != null) {
                childAt.setRotation(f);
            }
        }
    }

    public void setSuppressed(boolean z, boolean z2) {
        debugUi(TAG, "setSuppressed(): " + z);
        if (z != this.mIsSuppressed || z2) {
            this.mIsSuppressed = z;
            this.mIsImmersive = z;
            if (this.mIsSuppressed) {
                getChildAt(0).setVisibility(0);
            } else if (!this.mIsImmersive) {
                int opticalZoomRatioIndex = HybridZoomingSystem.getOpticalZoomRatioIndex(this.mCurrentModule, this.mZoomRatio);
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    ZoomRatioView zoomRatioView = (ZoomRatioView) getChildAt(i);
                    if (i == opticalZoomRatioIndex) {
                        zoomRatioView.setZoomRatio(this.mZoomRatio);
                        zoomRatioView.setIconify(false);
                    } else {
                        zoomRatioView.setIconify(true);
                    }
                    zoomRatioView.setVisibility(0);
                }
            }
        } else {
            debugUi(TAG, "setSuppressed() ignored: " + z);
        }
    }

    public void setUseSliderAllowed(boolean z) {
        this.mUseSliderAllowed = z;
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        debugUi(TAG, "setVisibility(): " + Util.viewVisibilityToString(i));
        if (i != 0) {
            resetAnimators();
        }
    }

    public void setZoomRatio(float f, int i) {
        if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
            int macroZoomRatioIndex = f == 0.0f ? HybridZoomingSystem.getMacroZoomRatioIndex(this.mCurrentModule) : HybridZoomingSystem.getOpticalZoomRatioIndex(this.mCurrentModule, f);
            debugUi(TAG, "setZoomRatio(): zooming action = " + ZoomingAction.toString(i));
            debugUi(TAG, "setZoomRatio():  current index = " + this.mCurrentSelectedChildIndex);
            debugUi(TAG, "setZoomRatio():   current zoom = " + this.mZoomRatio);
            debugUi(TAG, "setZoomRatio():   target index = " + macroZoomRatioIndex);
            debugUi(TAG, "setZoomRatio():    target zoom = " + f);
            this.mZoomRatio = f;
            if (this.mIsSuppressed) {
                ((ZoomRatioView) getChildAt(0)).setZoomRatio(this.mZoomRatio);
                announceCurrentZoomRatioForAccessibility();
                debugUi(TAG, "setZoomRatio(): mIsSuppressed");
            } else if (this.mIsImmersive) {
                ((ZoomRatioView) getChildAt(getChildCount() / 2)).setZoomRatio(this.mZoomRatio);
                announceCurrentZoomRatioForAccessibility();
                debugUi(TAG, "setZoomRatio(): mIsImmersive");
            } else if (i == 0) {
                debugUi(TAG, "setZoomRatio(): ignored as source is toggle button");
            } else {
                boolean z = UI_DEBUG_ENABLED;
                removeCallbacks(this.mIndexUpdater);
                post(this.mIndexUpdater);
            }
        } else {
            throw new IllegalStateException("setZoomRatio() must be called on main ui thread.");
        }
    }

    public void startTranslationAnimationShow() {
        ObjectAnimator objectAnimator;
        ArrayList arrayList = new ArrayList();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final ZoomRatioView zoomRatioView = (ZoomRatioView) getChildAt(i);
            final float translationX = zoomRatioView.getTranslationX();
            if (i != this.mCurrentSelectedChildIndex) {
                ViewCompat.setAlpha(zoomRatioView, 1.0f);
                objectAnimator = ObjectAnimator.ofFloat(zoomRatioView, View.TRANSLATION_X, ((float) ((int) (((float) ((isLayoutRTL() ? this.mCurrentSelectedChildIndex - i : i - this.mCurrentSelectedChildIndex) * this.mItemWidth)) * 0.1f))) + translationX, translationX);
                arrayList.add(objectAnimator);
            } else {
                ViewCompat.setAlpha(zoomRatioView, 0.0f);
                objectAnimator = ObjectAnimator.ofFloat(zoomRatioView, View.TRANSLATION_X, translationX, translationX);
                arrayList.add(objectAnimator);
            }
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                /* class com.android.camera.ui.zoom.ZoomRatioToggleView.AnonymousClass6 */

                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    ViewCompat.setTranslationX(zoomRatioView, translationX);
                }
            });
        }
        this.mZoomShowAnimatorSet.cancel();
        this.mZoomShowAnimatorSet.removeAllListeners();
        this.mZoomShowAnimatorSet.playTogether(arrayList);
        this.mZoomShowAnimatorSet.start();
    }
}
