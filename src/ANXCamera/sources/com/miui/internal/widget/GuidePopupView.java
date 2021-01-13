package com.miui.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.internal.R;
import miui.widget.GuidePopupWindow;

public class GuidePopupView extends FrameLayout implements View.OnTouchListener {
    public static final int ARROW_BOTTOM_LEFT_MODE = 7;
    public static final int ARROW_BOTTOM_MODE = 0;
    public static final int ARROW_BOTTOM_RIGHT_MODE = 5;
    public static final int ARROW_LEFT_MODE = 3;
    public static final int ARROW_NONE_MODE = -1;
    public static final int ARROW_RIGHT_MODE = 2;
    public static final int ARROW_TOP_LEFT_MODE = 4;
    public static final int ARROW_TOP_MODE = 1;
    public static final int ARROW_TOP_RIGHT_MODE = 6;
    private View mAnchor;
    private int mAnchorHeight;
    private int mAnchorLocationX;
    private int mAnchorLocationY;
    private int mAnchorWidth;
    private ObjectAnimator mAnimator;
    private int mArrowMode;
    private int mColorBackground;
    private Context mContext;
    private int mDefaultOffset;
    private GuidePopupWindow mGuidePopupWindow;
    private Animator.AnimatorListener mHideAnimatorListener;
    private boolean mIsDismissing;
    private boolean mIsMirrored;
    private float mLineLength;
    private int mMinBorder;
    private LinearLayout mMirroredTextGroup;
    private int mOffsetX;
    private int mOffsetY;
    private final Paint mPaint;
    private Animator.AnimatorListener mShowAnimatorListener;
    private float mStartPointRadius;
    private float mTextCircleRadius;
    private ColorStateList mTextColor;
    private LinearLayout mTextGroup;
    private int mTextSize;
    private View.OnTouchListener mTouchInterceptor;
    private boolean mUseDefaultOffset;

    class WrapperOnClickListener implements View.OnClickListener {
        public View.OnClickListener mOnClickListener;

        WrapperOnClickListener() {
        }

        public void onClick(View view) {
            View.OnClickListener onClickListener = this.mOnClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
            GuidePopupView.this.mGuidePopupWindow.dismiss(true);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.mOnClickListener = onClickListener;
        }
    }

    public GuidePopupView(Context context) {
        this(context, null);
    }

    public GuidePopupView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.guidePopupViewStyle);
    }

    public GuidePopupView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mUseDefaultOffset = true;
        this.mTextColor = null;
        this.mPaint = new Paint();
        this.mShowAnimatorListener = new AnimatorListenerAdapter() {
            /* class com.miui.internal.widget.GuidePopupView.AnonymousClass1 */
            private boolean mCancel;

            public void onAnimationCancel(Animator animator) {
                this.mCancel = true;
            }

            public void onAnimationEnd(Animator animator) {
                if (!this.mCancel) {
                    GuidePopupView.this.mAnimator = null;
                }
            }

            public void onAnimationStart(Animator animator) {
                this.mCancel = false;
            }
        };
        this.mHideAnimatorListener = new AnimatorListenerAdapter() {
            /* class com.miui.internal.widget.GuidePopupView.AnonymousClass2 */
            private boolean mCancel;

            public void onAnimationCancel(Animator animator) {
                this.mCancel = true;
            }

            public void onAnimationEnd(Animator animator) {
                if (!this.mCancel) {
                    GuidePopupView.this.mIsDismissing = false;
                    GuidePopupView.this.mAnimator = null;
                    GuidePopupView.this.mGuidePopupWindow.dismiss();
                    GuidePopupView.this.setArrowMode(-1);
                }
            }

            public void onAnimationStart(Animator animator) {
                this.mCancel = false;
                GuidePopupView.this.mIsDismissing = true;
            }
        };
        this.mArrowMode = -1;
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.GuidePopupView, i, 0);
        this.mStartPointRadius = obtainStyledAttributes.getDimension(R.styleable.GuidePopupView_startPointRadius, 0.0f);
        this.mLineLength = obtainStyledAttributes.getDimension(R.styleable.GuidePopupView_lineLength, 0.0f);
        this.mTextCircleRadius = obtainStyledAttributes.getDimension(R.styleable.GuidePopupView_textCircleRadius, 0.0f);
        this.mColorBackground = obtainStyledAttributes.getColor(R.styleable.GuidePopupView_android_colorBackground, 0);
        this.mPaint.setColor(obtainStyledAttributes.getColor(R.styleable.GuidePopupView_paintColor, -1));
        this.mTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.GuidePopupView_android_textSize, 15);
        this.mTextColor = obtainStyledAttributes.getColorStateList(R.styleable.GuidePopupView_android_textColor);
        obtainStyledAttributes.recycle();
        this.mMinBorder = (int) (this.mLineLength + (this.mTextCircleRadius * 2.5f));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0058, code lost:
        if (((float) (r1 - r4)) < r3) goto L_0x0081;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0068, code lost:
        if (((float) (r1 - r4)) < r2) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0077, code lost:
        if (((float) (r0 - r6)) < r3) goto L_0x0062;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0087, code lost:
        if (((float) (r0 - r6)) < r2) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0089, code lost:
        r2 = 5;
     */
    private void adjustArrowMode() {
        int width = getWidth();
        int height = getHeight();
        int i = 4;
        int i2 = this.mAnchorLocationY;
        int i3 = 0;
        int i4 = this.mAnchorHeight;
        int i5 = this.mAnchorLocationX;
        int i6 = this.mAnchorWidth;
        int[] iArr = {i2, (height - i2) - i4, i5, (width - i5) - i6};
        int i7 = i5 + (i6 / 2);
        int i8 = i2 + (i4 / 2);
        int i9 = Integer.MIN_VALUE;
        int i10 = 0;
        while (true) {
            if (i3 >= 4) {
                i3 = i10;
                break;
            } else if (iArr[i3] >= this.mMinBorder) {
                break;
            } else {
                if (iArr[i3] > i9) {
                    i9 = iArr[i3];
                    i10 = i3;
                }
                i3++;
            }
        }
        if (i3 != 0) {
            if (i3 != 1) {
                if (i3 == 2) {
                    float f = this.mTextCircleRadius;
                    if (((float) i8) >= f) {
                    }
                } else if (i3 == 3) {
                    float f2 = this.mTextCircleRadius;
                    if (((float) i8) >= f2) {
                    }
                    setArrowMode(i);
                }
                i = i3;
                setArrowMode(i);
            }
            float f3 = this.mTextCircleRadius;
            if (((float) i7) >= f3) {
            }
            setArrowMode(i);
            i = 6;
            setArrowMode(i);
        }
        float f4 = this.mTextCircleRadius;
        if (((float) i7) >= f4) {
        }
        i = 7;
        setArrowMode(i);
    }

    private void arrowLayout() {
        caculateDefaultOffset();
        drawText(this.mArrowMode, this.mTextGroup, this.mOffsetX, this.mOffsetY);
        if (this.mIsMirrored) {
            drawText(getMirroredMode(), this.mMirroredTextGroup, -this.mOffsetX, -this.mOffsetY);
        }
    }

    private void caculateDefaultOffset() {
        if (!this.mUseDefaultOffset) {
            this.mDefaultOffset = 0;
            return;
        }
        int i = this.mAnchorWidth / 2;
        int i2 = this.mAnchorHeight / 2;
        int sqrt = (int) Math.sqrt(Math.pow((double) i, 2.0d) + Math.pow((double) i2, 2.0d));
        int i3 = this.mArrowMode;
        if (i3 == 0 || i3 == 1) {
            this.mDefaultOffset = i2;
        } else if (i3 == 2 || i3 == 3) {
            this.mDefaultOffset = i;
        } else {
            this.mDefaultOffset = sqrt;
        }
    }

    private void drawPopup(Canvas canvas, int i, int i2, int i3) {
        float f;
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        float f2 = (float) (this.mAnchorLocationX + (this.mAnchorWidth / 2) + i2);
        float f3 = (float) (this.mAnchorLocationY + (this.mAnchorHeight / 2) + i3);
        switch (i) {
            case 0:
                f = 180.0f;
                break;
            case 1:
            default:
                f = 0.0f;
                break;
            case 2:
                f = 90.0f;
                break;
            case 3:
                f = -90.0f;
                break;
            case 4:
                f = -45.0f;
                break;
            case 5:
                f = 135.0f;
                break;
            case 6:
                f = 45.0f;
                break;
            case 7:
                f = -135.0f;
                break;
        }
        canvas.save();
        canvas.rotate(f, f2, f3);
        canvas.translate(0.0f, (float) this.mDefaultOffset);
        int save = canvas.save();
        canvas.clipRect(f2 - 2.0f, f3, f2 + 2.0f, f3 + this.mStartPointRadius, Region.Op.DIFFERENCE);
        canvas.drawCircle(f2, f3, this.mStartPointRadius, this.mPaint);
        canvas.restoreToCount(save);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(4.0f);
        canvas.drawLine(f2, f3, f2, f3 + this.mLineLength, this.mPaint);
        float f4 = f3 + this.mLineLength + this.mTextCircleRadius;
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(4.0f);
        canvas.drawCircle(f2, f4, this.mTextCircleRadius, this.mPaint);
        canvas.restore();
    }

    private void drawText(int i, LinearLayout linearLayout, int i2, int i3) {
        int i4;
        int i5;
        float f;
        int i6;
        float f2 = ((float) this.mDefaultOffset) + this.mLineLength + this.mTextCircleRadius;
        int i7 = this.mAnchorLocationX + (this.mAnchorWidth / 2);
        int i8 = this.mAnchorLocationY + (this.mAnchorHeight / 2);
        int i9 = 0;
        switch (i) {
            case 0:
            case 5:
            case 7:
                i9 = i7 - (linearLayout.getMeasuredWidth() / 2);
                f = ((float) i8) - f2;
                i5 = linearLayout.getMeasuredHeight() / 2;
                i4 = (int) (f - ((float) i5));
                break;
            case 1:
            case 4:
            case 6:
                i9 = i7 - (linearLayout.getMeasuredWidth() / 2);
                f = ((float) i8) + f2;
                i5 = linearLayout.getMeasuredHeight() / 2;
                i4 = (int) (f - ((float) i5));
                break;
            case 2:
                i9 = (int) ((((float) i7) - f2) - ((float) (linearLayout.getMeasuredWidth() / 2)));
                i6 = linearLayout.getMeasuredHeight() / 2;
                i4 = i8 - i6;
                break;
            case 3:
                i9 = (int) ((((float) i7) + f2) - ((float) (linearLayout.getMeasuredWidth() / 2)));
                i6 = linearLayout.getMeasuredHeight() / 2;
                i4 = i8 - i6;
                break;
            default:
                i4 = 0;
                break;
        }
        int sin = (int) (((double) f2) * Math.sin(0.7853981633974483d));
        int i10 = (int) (f2 - ((float) sin));
        if (i != 4) {
            if (i == 5) {
                i9 -= sin;
            } else if (i != 6) {
                if (i == 7) {
                    i9 += sin;
                }
                int i11 = i9 + i2;
                int i12 = i4 + i3;
                linearLayout.layout(i11, i12, linearLayout.getMeasuredWidth() + i11, linearLayout.getMeasuredHeight() + i12);
            } else {
                i9 -= sin;
            }
            i4 += i10;
            int i112 = i9 + i2;
            int i122 = i4 + i3;
            linearLayout.layout(i112, i122, linearLayout.getMeasuredWidth() + i112, linearLayout.getMeasuredHeight() + i122);
        }
        i9 += sin;
        i4 -= i10;
        int i1122 = i9 + i2;
        int i1222 = i4 + i3;
        linearLayout.layout(i1122, i1222, linearLayout.getMeasuredWidth() + i1122, linearLayout.getMeasuredHeight() + i1222);
    }

    private int getMirroredMode() {
        int i = this.mArrowMode;
        if (i == -1) {
            return -1;
        }
        return i % 2 == 0 ? i + 1 : i - 1;
    }

    public void addGuideTextView(LinearLayout linearLayout, String str) {
        String[] split;
        if (!(TextUtils.isEmpty(str) || (split = str.split("\n")) == null || split.length == 0)) {
            for (String str2 : split) {
                TextView textView = (TextView) FrameLayout.inflate(this.mContext, R.layout.guide_popup_text_view, null);
                textView.setText(str2);
                textView.setTextSize(0, (float) this.mTextSize);
                ColorStateList colorStateList = this.mTextColor;
                if (colorStateList != null) {
                    textView.setTextColor(colorStateList);
                }
                linearLayout.addView(textView);
            }
        }
    }

    public void animateToDismiss() {
        if (!this.mIsDismissing) {
            ObjectAnimator objectAnimator = this.mAnimator;
            if (objectAnimator != null) {
                objectAnimator.cancel();
            }
            this.mAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 0.0f);
            this.mAnimator.setDuration(200L);
            this.mAnimator.addListener(this.mHideAnimatorListener);
            this.mAnimator.start();
        }
    }

    public void animateToShow() {
        setAlpha(0.0f);
        invalidate();
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            /* class com.miui.internal.widget.GuidePopupView.AnonymousClass3 */

            public boolean onPreDraw() {
                GuidePopupView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                if (GuidePopupView.this.mAnimator != null) {
                    GuidePopupView.this.mAnimator.cancel();
                }
                GuidePopupView guidePopupView = GuidePopupView.this;
                guidePopupView.mAnimator = ObjectAnimator.ofFloat(guidePopupView, View.ALPHA, 1.0f);
                GuidePopupView.this.mAnimator.setDuration(300L);
                GuidePopupView.this.mAnimator.addListener(GuidePopupView.this.mShowAnimatorListener);
                GuidePopupView.this.mAnimator.start();
                return true;
            }
        });
    }

    public void clearOffset() {
        setOffset(0, 0);
        this.mUseDefaultOffset = true;
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate((float) this.mAnchorLocationX, (float) this.mAnchorLocationY);
        this.mAnchor.setDrawingCacheEnabled(true);
        this.mAnchor.buildDrawingCache();
        canvas.drawBitmap(this.mAnchor.getDrawingCache(), 0.0f, 0.0f, (Paint) null);
        this.mAnchor.setDrawingCacheEnabled(false);
        canvas.restore();
        drawPopup(canvas, this.mArrowMode, this.mOffsetX, this.mOffsetY);
        if (this.mIsMirrored) {
            drawPopup(canvas, getMirroredMode(), -this.mOffsetX, -this.mOffsetY);
        }
    }

    public int getArrowMode() {
        return this.mArrowMode;
    }

    public int getColorBackground() {
        return this.mColorBackground;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTextGroup = (LinearLayout) findViewById(R.id.text_group);
        this.mMirroredTextGroup = (LinearLayout) findViewById(R.id.mirrored_text_group);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mAnchorWidth == 0 || this.mAnchorHeight == 0) {
            setAnchor(this.mAnchor);
        }
        this.mTextCircleRadius = (float) Math.max(Math.sqrt(Math.pow((double) this.mTextGroup.getMeasuredWidth(), 2.0d) + Math.pow((double) this.mTextGroup.getMeasuredHeight(), 2.0d)) / 2.0d, (double) this.mTextCircleRadius);
        if (this.mIsMirrored) {
            this.mTextCircleRadius = (float) Math.max(Math.sqrt(Math.pow((double) this.mMirroredTextGroup.getMeasuredWidth(), 2.0d) + Math.pow((double) this.mMirroredTextGroup.getMeasuredHeight(), 2.0d)) / 2.0d, (double) this.mTextCircleRadius);
        }
        if (this.mArrowMode == -1) {
            adjustArrowMode();
        } else {
            arrowLayout();
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int i = this.mAnchorLocationX;
        Rect rect = new Rect(i, this.mAnchorLocationY, this.mAnchor.getWidth() + i, this.mAnchorLocationY + this.mAnchor.getHeight());
        if (motionEvent.getAction() != 0 || !rect.contains(x, y)) {
            this.mGuidePopupWindow.dismiss(true);
            return true;
        }
        this.mAnchor.callOnClick();
        return true;
    }

    public void setAnchor(View view) {
        this.mAnchor = view;
        this.mAnchorWidth = this.mAnchor.getWidth();
        this.mAnchorHeight = this.mAnchor.getHeight();
        int[] iArr = new int[2];
        this.mAnchor.getLocationInWindow(iArr);
        this.mAnchorLocationX = iArr[0];
        this.mAnchorLocationY = iArr[1];
    }

    public void setArrowMode(int i) {
        this.mArrowMode = i;
    }

    public void setArrowMode(int i, boolean z) {
        setArrowMode(i);
        this.mIsMirrored = z;
        if (this.mIsMirrored) {
            this.mMirroredTextGroup.setVisibility(0);
        } else {
            this.mMirroredTextGroup.setVisibility(8);
        }
    }

    public void setGuidePopupWindow(GuidePopupWindow guidePopupWindow) {
        this.mGuidePopupWindow = guidePopupWindow;
    }

    public void setOffset(int i, int i2) {
        this.mOffsetX = i;
        this.mOffsetY = i2;
        this.mUseDefaultOffset = false;
    }

    public void setTouchInterceptor(View.OnTouchListener onTouchListener) {
        this.mTouchInterceptor = onTouchListener;
    }
}
