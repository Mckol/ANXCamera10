package com.miui.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.internal.R;
import com.miui.internal.util.DeviceHelper;
import miui.view.EditActionMode;
import miui.widget.ArrowPopupWindow;

public class ArrowPopupView extends FrameLayout implements View.OnTouchListener {
    private static final int ANIMATION_REPEAT_COUNT = 8;
    public static final int ARROW_BOTTOM_MODE = 0;
    public static final int ARROW_LEFT_MODE = 3;
    public static final int ARROW_NONE_MODE = -1;
    private static final int ARROW_PADDING = 1;
    public static final int ARROW_RIGHT_MODE = 2;
    public static final int ARROW_TOP_MODE = 1;
    private static final String TAG = "ArrowPopupView";
    private static final int TRANSLATION_VALUE = 4;
    private View mAnchor;
    private AnimationSet mAnimationSet;
    private AnimatorSet mAnimator;
    private ImageView mArrow;
    private Drawable mArrowBottom;
    private Drawable mArrowLeft;
    private int mArrowMode;
    private ArrowPopupWindow mArrowPopupWindow;
    private Drawable mArrowRight;
    private int mArrowSpaceLeft;
    private int mArrowSpaceTop;
    private Drawable mArrowTop;
    private Drawable mArrowTopWithTitle;
    private boolean mAutoDismiss;
    private Drawable mBackground;
    private Drawable mBackgroundLeft;
    private Drawable mBackgroundRight;
    private FrameLayout mContentFrame;
    private LinearLayout mContentFrameWrapper;
    private Animation.AnimationListener mHideAnimatorListener;
    private boolean mIsDismissing;
    private int mMinBorder;
    private Button mNegativeButton;
    private WrapperOnClickListener mNegativeClickListener;
    private int mOffsetX;
    private int mOffsetY;
    private Button mPositiveButton;
    private WrapperOnClickListener mPositiveClickListener;
    private Animation.AnimationListener mShowAnimationListener;
    private boolean mShowingAnimation;
    private int mSpaceLeft;
    private int mSpaceTop;
    private Drawable mTitleBackground;
    private LinearLayout mTitleLayout;
    private TextView mTitleText;
    private Rect mTmpRect;
    private RectF mTmpRectF;
    private View.OnTouchListener mTouchInterceptor;
    private int mTranslationValue;

    /* access modifiers changed from: package-private */
    public class WrapperOnClickListener implements View.OnClickListener {
        public View.OnClickListener mOnClickListener;

        WrapperOnClickListener() {
        }

        public void onClick(View view) {
            View.OnClickListener onClickListener = this.mOnClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
            ArrowPopupView.this.mArrowPopupWindow.dismiss(true);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.mOnClickListener = onClickListener;
        }
    }

    public ArrowPopupView(Context context) {
        this(context, null);
    }

    public ArrowPopupView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.arrowPopupViewStyle);
    }

    public ArrowPopupView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTmpRect = new Rect();
        this.mTmpRectF = new RectF();
        this.mAutoDismiss = true;
        this.mShowingAnimation = false;
        this.mShowAnimationListener = new Animation.AnimationListener() {
            /* class com.miui.internal.widget.ArrowPopupView.AnonymousClass1 */

            public void onAnimationEnd(Animation animation) {
                ArrowPopupView.this.mAnimationSet = null;
                if (ArrowPopupView.this.mShowingAnimation) {
                    ArrowPopupView.this.animateShowing();
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        };
        this.mHideAnimatorListener = new Animation.AnimationListener() {
            /* class com.miui.internal.widget.ArrowPopupView.AnonymousClass2 */

            public void onAnimationEnd(Animation animation) {
                ArrowPopupView.this.mIsDismissing = false;
                ArrowPopupView.this.mAnimationSet = null;
                ArrowPopupView.this.mArrowPopupWindow.dismiss();
                ArrowPopupView.this.setArrowMode(-1);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        };
        this.mArrowMode = -1;
        this.mAutoDismiss = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ArrowPopupView, i, 0);
        this.mBackground = obtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_contentBackground);
        this.mBackgroundLeft = obtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_backgroundLeft);
        this.mBackgroundRight = obtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_backgroundRight);
        this.mTitleBackground = obtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_titleBackground);
        this.mArrowTop = obtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_topArrow);
        this.mArrowTopWithTitle = obtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_topArrowWithTitle);
        this.mArrowBottom = obtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_bottomArrow);
        this.mArrowRight = obtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_rightArrow);
        this.mArrowLeft = obtainStyledAttributes.getDrawable(R.styleable.ArrowPopupView_leftArrow);
        obtainStyledAttributes.recycle();
        this.mMinBorder = context.getResources().getDimensionPixelOffset(R.dimen.arrow_popup_window_min_border);
    }

    private void adjustArrowMode() {
        int[] iArr = new int[2];
        this.mAnchor.getLocationInWindow(iArr);
        int width = getWidth();
        int height = getHeight();
        int measuredWidth = this.mContentFrameWrapper.getMeasuredWidth();
        int measuredHeight = this.mContentFrameWrapper.getMeasuredHeight();
        int i = 0;
        int[] iArr2 = {iArr[1] - measuredHeight, ((height - iArr[1]) - this.mAnchor.getHeight()) - measuredHeight, iArr[0] - measuredWidth, ((width - iArr[0]) - this.mAnchor.getWidth()) - measuredWidth};
        int i2 = Integer.MIN_VALUE;
        int i3 = 0;
        while (true) {
            if (i >= 4) {
                break;
            } else if (iArr2[i] >= this.mMinBorder) {
                i3 = i;
                break;
            } else {
                if (iArr2[i] > i2) {
                    i2 = iArr2[i];
                    i3 = i;
                }
                i++;
            }
        }
        setArrowMode(i3);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void animateShowing() {
        if (DeviceHelper.FEATURE_WHOLE_ANIM) {
            AnimationSet animationSet = this.mAnimationSet;
            if (animationSet != null) {
                animationSet.cancel();
            }
            AnimatorSet animatorSet = this.mAnimator;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            this.mAnimator = new AnimatorSet();
            this.mAnimator.addListener(new AnimatorListenerAdapter() {
                /* class com.miui.internal.widget.ArrowPopupView.AnonymousClass6 */

                public void onAnimationEnd(Animator animator) {
                    ArrowPopupView.this.mArrowPopupWindow.dismiss();
                }
            });
            float f = getContext().getResources().getDisplayMetrics().density * 4.0f;
            Property property = View.TRANSLATION_Y;
            int i = this.mArrowMode;
            if (i == 0) {
                f = -f;
            } else if (i == 2) {
                f = -f;
                property = View.TRANSLATION_X;
            } else if (i == 3) {
                property = View.TRANSLATION_X;
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mContentFrameWrapper, property, 0.0f, f, 0.0f);
            ofFloat.setInterpolator(new AccelerateDecelerateInterpolator());
            ofFloat.setDuration(1200L);
            if (this.mAutoDismiss) {
                ofFloat.setRepeatCount(8);
            } else {
                ofFloat.setRepeatCount(-1);
            }
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.miui.internal.widget.ArrowPopupView.AnonymousClass7 */

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ArrowPopupView.this.mTranslationValue = ((Float) valueAnimator.getAnimatedValue()).intValue();
                    int abs = Math.abs(ArrowPopupView.this.mTranslationValue);
                    ArrowPopupView arrowPopupView = ArrowPopupView.this;
                    arrowPopupView.invalidate(arrowPopupView.mContentFrameWrapper.getLeft() - abs, ArrowPopupView.this.mContentFrameWrapper.getTop() - abs, ArrowPopupView.this.mContentFrameWrapper.getRight() + abs, ArrowPopupView.this.mContentFrameWrapper.getBottom() + abs);
                }
            });
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mArrow, property, 0.0f, f, 0.0f);
            ofFloat2.setInterpolator(new AccelerateDecelerateInterpolator());
            ofFloat2.setDuration(1200L);
            if (this.mAutoDismiss) {
                ofFloat2.setRepeatCount(8);
            } else {
                ofFloat2.setRepeatCount(-1);
            }
            this.mAnimator.playTogether(ofFloat, ofFloat2);
            this.mAnimator.start();
        }
    }

    private void arrowHorizontalLayout() {
        int i;
        int[] iArr = new int[2];
        this.mAnchor.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        int i3 = iArr[1];
        getLocationOnScreen(iArr);
        int width = this.mAnchor.getWidth();
        int height = this.mAnchor.getHeight();
        int width2 = getWidth();
        int height2 = getHeight();
        int measuredWidth = this.mContentFrameWrapper.getMeasuredWidth() > this.mContentFrameWrapper.getMinimumWidth() ? this.mContentFrameWrapper.getMeasuredWidth() : this.mContentFrameWrapper.getMinimumWidth();
        int measuredHeight = this.mContentFrameWrapper.getMeasuredHeight() > this.mContentFrameWrapper.getMinimumHeight() ? this.mContentFrameWrapper.getMeasuredHeight() : this.mContentFrameWrapper.getMinimumHeight();
        int arrowWidth = getArrowWidth();
        int arrowHeight = getArrowHeight();
        this.mSpaceTop = ((height / 2) + i3) - iArr[1];
        int i4 = height2 - this.mSpaceTop;
        this.mArrowSpaceTop = ((i3 + ((height - arrowHeight) / 2)) - iArr[1]) + ((this.mContentFrameWrapper.getPaddingTop() - this.mContentFrameWrapper.getPaddingBottom()) / 2);
        int i5 = measuredHeight / 2;
        int i6 = measuredHeight - i5;
        this.mSpaceLeft = getLeft() + this.mOffsetX;
        int i7 = this.mArrowMode;
        if (i7 == 2) {
            this.mSpaceLeft += (((i2 - measuredWidth) + this.mContentFrameWrapper.getPaddingRight()) - arrowWidth) - iArr[0];
            i = (((i2 - arrowWidth) - iArr[0]) + this.mOffsetX) - 1;
        } else if (i7 == 3) {
            this.mSpaceLeft += (((i2 + width) - this.mContentFrameWrapper.getPaddingLeft()) + arrowWidth) - iArr[0];
            i = this.mSpaceLeft + (this.mContentFrameWrapper.getPaddingLeft() - arrowWidth) + 1;
        } else {
            i = 0;
        }
        int i8 = this.mSpaceTop;
        if (i8 >= i5 && i4 >= i6) {
            this.mSpaceTop = (i8 - i5) + this.mOffsetY;
        } else if (i4 < i6) {
            this.mSpaceTop = (height2 - measuredHeight) + this.mOffsetY;
        } else if (this.mSpaceTop < i5) {
            this.mSpaceTop = this.mOffsetY;
        }
        this.mArrowSpaceTop += this.mOffsetY;
        int i9 = this.mArrowSpaceTop;
        if (i9 < 0) {
            this.mArrowSpaceTop = 0;
        } else if (i9 + arrowHeight > height2) {
            this.mArrowSpaceTop = i9 - ((i9 + arrowHeight) - height2);
        }
        this.mContentFrameWrapper.layout(Math.max(this.mSpaceLeft, 0), Math.max(this.mSpaceTop, 0), Math.min(this.mSpaceLeft + measuredWidth, width2), Math.min(this.mSpaceTop + measuredHeight, height2));
        ImageView imageView = this.mArrow;
        int i10 = this.mArrowSpaceTop;
        imageView.layout(i, i10, arrowWidth + i, arrowHeight + i10);
    }

    private void arrowLayout() {
        int i = this.mArrowMode;
        if (i == 0 || i == 1) {
            arrowVerticalLayout();
        } else {
            arrowHorizontalLayout();
        }
        View contentView = getContentView();
        if (contentView != null) {
            ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
            if (contentView.getMeasuredHeight() > this.mContentFrameWrapper.getMeasuredHeight() - this.mTitleLayout.getMeasuredHeight()) {
                layoutParams.height = this.mContentFrameWrapper.getMeasuredHeight() - this.mTitleLayout.getMeasuredHeight();
                contentView.setLayoutParams(layoutParams);
            } else if (contentView.getMeasuredWidth() > this.mContentFrameWrapper.getMeasuredWidth()) {
                layoutParams.width = this.mContentFrameWrapper.getMeasuredWidth();
                contentView.setLayoutParams(layoutParams);
            }
            if (layoutParams.height <= 0 || layoutParams.width <= 0) {
                Log.w(TAG, "Invalid LayoutPrams of content view, please check the anchor view");
            }
        }
    }

    private void arrowVerticalLayout() {
        int i;
        int width = this.mAnchor.getWidth();
        int height = this.mAnchor.getHeight();
        int width2 = getWidth();
        int height2 = getHeight();
        int measuredWidth = this.mContentFrameWrapper.getMeasuredWidth() > this.mContentFrameWrapper.getMinimumWidth() ? this.mContentFrameWrapper.getMeasuredWidth() : this.mContentFrameWrapper.getMinimumWidth();
        int measuredHeight = this.mContentFrameWrapper.getMeasuredHeight() > this.mContentFrameWrapper.getMinimumHeight() ? this.mContentFrameWrapper.getMeasuredHeight() : this.mContentFrameWrapper.getMinimumHeight();
        int arrowWidth = getArrowWidth();
        int arrowHeight = getArrowHeight();
        int[] iArr = new int[2];
        this.mAnchor.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        int i3 = iArr[1];
        getLocationOnScreen(iArr);
        this.mSpaceLeft = ((width / 2) + i2) - iArr[0];
        int i4 = width2 - this.mSpaceLeft;
        this.mArrowSpaceLeft = (i2 + ((width - arrowWidth) / 2)) - iArr[0];
        this.mSpaceTop = getTop() + this.mOffsetY;
        int i5 = this.mArrowMode;
        if (i5 == 0) {
            this.mSpaceTop += ((i3 - iArr[1]) - measuredHeight) + (this.mContentFrameWrapper.getPaddingBottom() - arrowHeight);
            i = (((i3 - iArr[1]) - arrowHeight) + this.mOffsetY) - 1;
        } else if (i5 == 1) {
            this.mSpaceTop += (((i3 + height) - iArr[1]) - this.mContentFrameWrapper.getPaddingTop()) + arrowHeight;
            i = this.mSpaceTop + (this.mContentFrameWrapper.getPaddingTop() - arrowHeight) + 1;
        } else {
            i = 0;
        }
        int i6 = measuredWidth / 2;
        int i7 = measuredWidth - i6;
        int i8 = this.mSpaceLeft;
        if (i8 >= i6 && i4 >= i7) {
            this.mSpaceLeft = i8 - i6;
        } else if (i4 < i7) {
            this.mSpaceLeft = width2 - measuredWidth;
        } else if (this.mSpaceLeft < i6) {
            this.mSpaceLeft = 0;
        }
        int i9 = this.mSpaceLeft;
        int i10 = this.mOffsetX;
        this.mSpaceLeft = i9 + i10;
        this.mArrowSpaceLeft += i10;
        int i11 = this.mArrowSpaceLeft;
        if (i11 < 0) {
            this.mArrowSpaceLeft = 0;
        } else if (i11 + arrowWidth > width2) {
            this.mArrowSpaceLeft = i11 - ((i11 + arrowWidth) - width2);
        }
        this.mContentFrameWrapper.layout(Math.max(this.mSpaceLeft, 0), Math.max(this.mSpaceTop, 0), Math.min(this.mSpaceLeft + measuredWidth, width2), Math.min(this.mSpaceTop + measuredHeight, height2));
        ImageView imageView = this.mArrow;
        int i12 = this.mArrowSpaceLeft;
        imageView.layout(i12, i, arrowWidth + i12, arrowHeight + i);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void getAnimationPivot(float[] fArr) {
        float f;
        int i;
        int top = this.mArrow.getTop();
        int bottom = this.mArrow.getBottom();
        int left = this.mArrow.getLeft();
        int right = this.mArrow.getRight();
        int i2 = this.mArrowMode;
        float f2 = 0.0f;
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 == 2) {
                    f2 = (float) right;
                    i = (bottom - top) / 2;
                } else if (i2 != 3) {
                    f = 0.0f;
                } else {
                    f2 = (float) left;
                    i = (bottom - top) / 2;
                }
                top += i;
            } else {
                f2 = (float) (left + ((right - left) / 2));
            }
            f = (float) top;
        } else {
            f2 = (float) (left + ((right - left) / 2));
            f = (float) bottom;
        }
        fArr[0] = f2;
        fArr[1] = f;
    }

    private int getArrowHeight() {
        int measuredHeight = this.mArrow.getMeasuredHeight();
        return measuredHeight == 0 ? this.mArrow.getDrawable().getIntrinsicHeight() : measuredHeight;
    }

    private int getArrowWidth() {
        int measuredWidth = this.mArrow.getMeasuredWidth();
        return measuredWidth == 0 ? this.mArrow.getDrawable().getIntrinsicWidth() : measuredWidth;
    }

    public void addShadow() {
        ArrowPopupWindowHelper.addShadow(this.mArrow, new ViewOutlineProvider() {
            /* class com.miui.internal.widget.ArrowPopupView.AnonymousClass3 */

            public void getOutline(View view, Outline outline) {
                if (view.getWidth() != 0 && view.getHeight() != 0) {
                    int width = view.getWidth();
                    int height = view.getHeight();
                    Rect rect = new Rect(0, 0, width, height);
                    if (width > height) {
                        int i = (width - height) / 2;
                        rect.left += i;
                        rect.right -= i;
                    } else {
                        int i2 = (height - width) / 2;
                        rect.top += i2;
                        rect.bottom -= i2;
                    }
                    Path path = new Path();
                    float dimension = ArrowPopupView.this.getContext().getResources().getDimension(R.dimen.arrow_popup_triangle_corners);
                    int i3 = ArrowPopupView.this.mArrowMode;
                    if (i3 == 1) {
                        int i4 = rect.right;
                        int i5 = rect.left;
                        float f = ((float) (i4 + i5)) / 2.0f;
                        int i6 = rect.top;
                        float f2 = (float) i6;
                        int i7 = rect.bottom;
                        path.moveTo((float) i5, (float) i7);
                        float f3 = ((((float) (i4 - i5)) / 2.0f) / ((float) (i7 - i6))) * dimension;
                        float f4 = dimension + f2;
                        path.lineTo(f - f3, f4);
                        path.quadTo(f, f2, f3 + f, f4);
                        path.lineTo((float) rect.right, (float) rect.bottom);
                    } else if (i3 == 2) {
                        path.moveTo((float) rect.left, (float) rect.top);
                        int i8 = rect.right;
                        float f5 = (float) i8;
                        int i9 = rect.bottom;
                        int i10 = rect.top;
                        float f6 = ((float) (i9 + i10)) / 2.0f;
                        float f7 = f5 - dimension;
                        float f8 = dimension * ((((float) (i9 - i10)) / 2.0f) / ((float) (i8 - rect.left))) * 1.0f;
                        path.lineTo(f7, f6 - f8);
                        path.quadTo(f5, f6, f7, f8 + f6);
                        path.lineTo((float) rect.left, (float) rect.bottom);
                    } else if (i3 == 3) {
                        path.moveTo((float) rect.right, (float) rect.top);
                        int i11 = rect.left;
                        float f9 = (float) i11;
                        int i12 = rect.bottom;
                        int i13 = rect.top;
                        float f10 = ((float) (i12 + i13)) / 2.0f;
                        float f11 = f9 + dimension;
                        float f12 = dimension * ((((float) (i12 - i13)) / 2.0f) / ((float) (rect.right - i11))) * 1.0f;
                        path.lineTo(f11, f10 - f12);
                        path.quadTo(f9, f10, f11, f12 + f10);
                        path.lineTo((float) rect.right, (float) rect.bottom);
                    }
                    if (path.isConvex()) {
                        outline.setConvexPath(path);
                        return;
                    }
                    Log.d(ArrowPopupView.TAG, "outline path is not convex");
                    outline.setOval(rect);
                }
            }
        });
        ArrowPopupWindowHelper.addShadow(this.mContentFrameWrapper, new ViewOutlineProvider() {
            /* class com.miui.internal.widget.ArrowPopupView.AnonymousClass4 */

            public void getOutline(View view, Outline outline) {
                if (view.getWidth() != 0 && view.getHeight() != 0) {
                    Rect rect = new Rect(0, 0, view.getWidth(), view.getHeight());
                    rect.bottom -= view.getPaddingBottom();
                    rect.top += view.getPaddingTop();
                    rect.right -= view.getPaddingRight();
                    rect.left += view.getPaddingLeft();
                    outline.setRoundRect(rect, (float) ArrowPopupView.this.getContext().getResources().getDimensionPixelOffset(R.dimen.arrow_popup_view_round_corners));
                }
            }
        });
    }

    public void animateToDismiss() {
        if (!this.mIsDismissing) {
            AnimatorSet animatorSet = this.mAnimator;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            AnimationSet animationSet = this.mAnimationSet;
            if (animationSet != null) {
                animationSet.cancel();
            }
            this.mAnimationSet = new AnimationSet(true);
            float[] fArr = new float[2];
            getAnimationPivot(fArr);
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.6f, 1.0f, 0.6f, 0, fArr[0], 0, fArr[1]);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            if (DeviceHelper.FEATURE_WHOLE_ANIM) {
                scaleAnimation.setDuration(150);
                alphaAnimation.setDuration(150);
            } else {
                this.mAnimationSet.setDuration(0);
            }
            this.mAnimationSet.addAnimation(scaleAnimation);
            this.mAnimationSet.addAnimation(alphaAnimation);
            this.mAnimationSet.setAnimationListener(this.mHideAnimatorListener);
            this.mAnimationSet.setInterpolator(new AccelerateInterpolator(2.0f));
            startAnimation(this.mAnimationSet);
        }
    }

    public void animateToShow() {
        invalidate();
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            /* class com.miui.internal.widget.ArrowPopupView.AnonymousClass5 */

            public boolean onPreDraw() {
                ArrowPopupView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                if (ArrowPopupView.this.mAnimator != null) {
                    ArrowPopupView.this.mAnimator.cancel();
                }
                if (ArrowPopupView.this.mAnimationSet != null) {
                    ArrowPopupView.this.mAnimationSet.cancel();
                }
                ArrowPopupView.this.mAnimationSet = new AnimationSet(true);
                float[] fArr = new float[2];
                ArrowPopupView.this.getAnimationPivot(fArr);
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.6f, 1.0f, 0.6f, 1.0f, 0, fArr[0], 0, fArr[1]);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                if (DeviceHelper.FEATURE_WHOLE_ANIM) {
                    scaleAnimation.setDuration(280);
                    alphaAnimation.setDuration(280);
                } else {
                    ArrowPopupView.this.mAnimationSet.setDuration(0);
                }
                ArrowPopupView.this.mAnimationSet.addAnimation(scaleAnimation);
                ArrowPopupView.this.mAnimationSet.addAnimation(alphaAnimation);
                ArrowPopupView.this.mAnimationSet.setAnimationListener(ArrowPopupView.this.mShowAnimationListener);
                ArrowPopupView.this.mAnimationSet.setInterpolator(new DecelerateInterpolator(2.0f));
                ArrowPopupView arrowPopupView = ArrowPopupView.this;
                arrowPopupView.startAnimation(arrowPopupView.mAnimationSet);
                return true;
            }
        });
    }

    public void enableShowingAnimation(boolean z) {
        this.mShowingAnimation = z;
    }

    public int getArrowMode() {
        return this.mArrowMode;
    }

    public View getContentView() {
        if (this.mContentFrame.getChildCount() > 0) {
            return this.mContentFrame.getChildAt(0);
        }
        return null;
    }

    public Button getNegativeButton() {
        return this.mNegativeButton;
    }

    public Button getPositiveButton() {
        return this.mPositiveButton;
    }

    @Deprecated
    public float getRollingPercent() {
        return 1.0f;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0103  */
    public void onDraw(Canvas canvas) {
        int i;
        int i2;
        float f;
        int i3;
        int i4;
        int i5;
        if (this.mBackground == null) {
            int width = this.mSpaceLeft + (this.mContentFrameWrapper.getWidth() / 2);
            int height = this.mSpaceTop + (this.mContentFrameWrapper.getHeight() / 2);
            int i6 = this.mArrowMode;
            if (i6 != 0) {
                if (i6 == 1) {
                    int measuredWidth = this.mArrowSpaceLeft + (this.mArrow.getMeasuredWidth() / 2);
                    i = measuredWidth - this.mSpaceLeft;
                    i2 = this.mContentFrameWrapper.getRight() - measuredWidth;
                    f = 0.0f;
                } else if (i6 == 2) {
                    f = 90.0f;
                    int measuredHeight = this.mArrowSpaceTop + (this.mArrow.getMeasuredHeight() / 2);
                    i = measuredHeight - this.mSpaceTop;
                    i2 = this.mContentFrameWrapper.getBottom() - measuredHeight;
                } else if (i6 != 3) {
                    f = 0.0f;
                    i2 = 0;
                    i = 0;
                } else {
                    f = -90.0f;
                    i5 = this.mArrowSpaceTop + (this.mArrow.getMeasuredHeight() / 2);
                    i = this.mContentFrameWrapper.getBottom() - i5;
                    i4 = this.mSpaceTop;
                }
                int save = canvas.save();
                canvas.rotate(f, (float) width, (float) height);
                i3 = this.mArrowMode;
                if (i3 != 0 || i3 == 1) {
                    canvas.translate((float) this.mSpaceLeft, (float) this.mSpaceTop);
                    this.mBackgroundLeft.setBounds(0, 0, i, this.mContentFrameWrapper.getHeight());
                    canvas.translate(0.0f, (float) (this.mArrowMode != 1 ? this.mTranslationValue : -this.mTranslationValue));
                    this.mBackgroundLeft.draw(canvas);
                    canvas.translate((float) i, 0.0f);
                    this.mBackgroundRight.setBounds(0, 0, i2, this.mContentFrameWrapper.getHeight());
                    this.mBackgroundRight.draw(canvas);
                } else if (i3 == 2 || i3 == 3) {
                    canvas.translate((float) (width - (this.mContentFrameWrapper.getHeight() / 2)), (float) (height - (this.mContentFrameWrapper.getWidth() / 2)));
                    this.mBackgroundLeft.setBounds(0, 0, i, this.mContentFrameWrapper.getWidth());
                    canvas.translate(0.0f, (float) (this.mArrowMode == 3 ? this.mTranslationValue : -this.mTranslationValue));
                    this.mBackgroundLeft.draw(canvas);
                    canvas.translate((float) i, 0.0f);
                    this.mBackgroundRight.setBounds(0, 0, i2, this.mContentFrameWrapper.getWidth());
                    this.mBackgroundRight.draw(canvas);
                }
                canvas.restoreToCount(save);
            }
            f = 180.0f;
            i5 = this.mArrowSpaceLeft + (this.mArrow.getMeasuredWidth() / 2);
            i = this.mContentFrameWrapper.getRight() - i5;
            i4 = this.mSpaceLeft;
            i2 = i5 - i4;
            int save2 = canvas.save();
            canvas.rotate(f, (float) width, (float) height);
            i3 = this.mArrowMode;
            if (i3 != 0) {
            }
            canvas.translate((float) this.mSpaceLeft, (float) this.mSpaceTop);
            this.mBackgroundLeft.setBounds(0, 0, i, this.mContentFrameWrapper.getHeight());
            canvas.translate(0.0f, (float) (this.mArrowMode != 1 ? this.mTranslationValue : -this.mTranslationValue));
            this.mBackgroundLeft.draw(canvas);
            canvas.translate((float) i, 0.0f);
            this.mBackgroundRight.setBounds(0, 0, i2, this.mContentFrameWrapper.getHeight());
            this.mBackgroundRight.draw(canvas);
            canvas.restoreToCount(save2);
        }
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mArrow = (ImageView) findViewById(R.id.popup_arrow);
        this.mContentFrame = (FrameLayout) findViewById(16908290);
        this.mContentFrameWrapper = (LinearLayout) findViewById(R.id.content_wrapper);
        this.mContentFrameWrapper.setBackground(this.mBackground);
        this.mContentFrameWrapper.setMinimumHeight(getContext().getResources().getDimensionPixelOffset(R.dimen.arrow_popup_view_min_height));
        if (!(this.mBackgroundLeft == null || this.mBackgroundRight == null)) {
            Rect rect = new Rect();
            this.mBackgroundLeft.getPadding(rect);
            LinearLayout linearLayout = this.mContentFrameWrapper;
            int i = rect.top;
            linearLayout.setPadding(i, i, i, i);
        }
        this.mTitleLayout = (LinearLayout) findViewById(R.id.title_layout);
        this.mTitleLayout.setBackground(this.mTitleBackground);
        this.mTitleText = (TextView) findViewById(16908310);
        this.mPositiveButton = (Button) findViewById(EditActionMode.BUTTON2);
        this.mNegativeButton = (Button) findViewById(EditActionMode.BUTTON1);
        this.mPositiveClickListener = new WrapperOnClickListener();
        this.mNegativeClickListener = new WrapperOnClickListener();
        this.mPositiveButton.setOnClickListener(this.mPositiveClickListener);
        this.mNegativeButton.setOnClickListener(this.mNegativeClickListener);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mAnchor.isAttachedToWindow()) {
            if (this.mArrowMode == -1) {
                adjustArrowMode();
            }
            arrowLayout();
        } else if (this.mArrowPopupWindow.isShowing()) {
            this.mArrowPopupWindow.dismiss();
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        Rect rect = this.mTmpRect;
        this.mContentFrameWrapper.getHitRect(rect);
        if (motionEvent.getAction() != 0 || rect.contains(x, y)) {
            View.OnTouchListener onTouchListener = this.mTouchInterceptor;
            return onTouchListener != null && onTouchListener.onTouch(view, motionEvent);
        }
        this.mArrowPopupWindow.dismiss(true);
        return true;
    }

    public void setAnchor(View view) {
        this.mAnchor = view;
    }

    public void setArrowMode(int i) {
        this.mArrowMode = i;
        if (i == 0) {
            this.mArrow.setImageDrawable(this.mArrowBottom);
        } else if (i == 1) {
            this.mArrow.setImageDrawable(this.mTitleLayout.getVisibility() == 0 ? this.mArrowTopWithTitle : this.mArrowTop);
        } else if (i == 2) {
            this.mArrow.setImageDrawable(this.mArrowRight);
        } else if (i == 3) {
            this.mArrow.setImageDrawable(this.mArrowLeft);
        }
    }

    public void setArrowPopupWindow(ArrowPopupWindow arrowPopupWindow) {
        this.mArrowPopupWindow = arrowPopupWindow;
    }

    public void setAutoDismiss(boolean z) {
        this.mAutoDismiss = z;
    }

    public void setContentView(int i) {
        setContentView(LayoutInflater.from(getContext()).inflate(i, (ViewGroup) null));
    }

    public void setContentView(View view) {
        setContentView(view, new ViewGroup.LayoutParams(-2, -2));
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.mContentFrame.removeAllViews();
        if (view != null) {
            this.mContentFrame.addView(view, layoutParams);
        }
    }

    public void setNegativeButton(CharSequence charSequence, View.OnClickListener onClickListener) {
        this.mNegativeButton.setText(charSequence);
        this.mNegativeButton.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
        this.mNegativeClickListener.setOnClickListener(onClickListener);
    }

    public void setOffset(int i, int i2) {
        this.mOffsetX = i;
        this.mOffsetY = i2;
    }

    public void setPositiveButton(CharSequence charSequence, View.OnClickListener onClickListener) {
        this.mPositiveButton.setText(charSequence);
        this.mPositiveButton.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
        this.mPositiveClickListener.setOnClickListener(onClickListener);
    }

    @Deprecated
    public void setRollingPercent(float f) {
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitleLayout.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
        this.mTitleText.setText(charSequence);
    }

    public void setTouchInterceptor(View.OnTouchListener onTouchListener) {
        this.mTouchInterceptor = onTouchListener;
    }
}
