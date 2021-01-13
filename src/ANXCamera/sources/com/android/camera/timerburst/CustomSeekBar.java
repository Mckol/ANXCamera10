package com.android.camera.timerburst;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.customization.TintColor;
import miui.view.animation.CubicEaseOutInterpolator;

public class CustomSeekBar extends RelativeLayout {
    private final String TAG = "CustomSeekBar";
    private CircleFullImageView circleFullImageView;
    private float mCircleIndexX;
    ValueAnimator mCircleValueLargerAnimator;
    ValueAnimator mCircleValueShrinkAnimator;
    private int[] mConfigArray = {5, 100, 500, 1000};
    ValueAnimator mContainervalueLargerAnimator;
    ValueAnimator mContainervalueShrinkAnimator;
    private Context mContext;
    private int mCurrentValue;
    private float mCurrentX;
    private int mEventType;
    private float mInnerMarginHorizontal;
    private boolean mIsEnlargeState = false;
    private float mMarginBuffer;
    private float[] mRangeMax;
    private float[] mRangeMin;
    private RelativeLayout mRlSeekInnerContainer;
    private RelativeLayout mRlSeekOutter;
    private float mRlSeekOutterHeight;
    AddSeekBarValueListener mSeekBarValueListener;
    private int mTotalwidth;
    private TextView mTvCurrentValue;
    private TextView mTvEndValue;
    private float mTvEndX;
    TranslateAnimation mTvMoveDownAnimator;
    TranslateAnimation mTvMoveUpAnimator;
    private RelativeLayout mTvParentLayout;
    private float mTvStartRightX;
    private TextView mTvStartValue;
    private float mTvWidth;
    private String mUnitString = "";
    private int mValueEnlargeTimes;
    private int mXStartParent;
    private int widthScreen;

    /* access modifiers changed from: package-private */
    public interface AddSeekBarValueListener {
        void currentSeekBarValue(View view, int i, int i2);
    }

    public class JudgeSegmentResult {
        private float bothSidesMarginBuffer;
        private int[] elementArray;
        private int judgeValue;
        private float perSegmentWidth;
        private int segmentCount;

        JudgeSegmentResult(float f, int[] iArr, float f2) {
            this.elementArray = iArr;
            this.segmentCount = iArr.length - 1;
            this.perSegmentWidth = f / ((float) this.segmentCount);
            this.bothSidesMarginBuffer = f2;
        }

        public float getJudgePosition(float f) {
            float f2;
            int i = 0;
            while (true) {
                if (i >= this.segmentCount) {
                    i = 1;
                    f2 = 0.0f;
                    break;
                }
                int[] iArr = this.elementArray;
                if (((float) iArr[i]) <= f) {
                    int i2 = i + 1;
                    if (f < ((float) iArr[i2])) {
                        f2 = (f - ((float) iArr[i])) / ((float) (iArr[i2] - iArr[i]));
                        break;
                    }
                }
                i++;
            }
            float f3 = this.perSegmentWidth;
            return (((float) i) * f3) + (f2 * f3) + (this.bothSidesMarginBuffer / 2.0f);
        }

        public int getJudgeValue(float f) {
            float f2 = f - CustomSeekBar.this.mRangeMin[0];
            if (f2 < 0.0f) {
                f2 = 0.0f;
            }
            float f3 = f2 / this.perSegmentWidth;
            int i = (int) f3;
            float f4 = f3 - ((float) i);
            int i2 = i + 1;
            if (i2 < CustomSeekBar.this.mConfigArray.length) {
                int[] iArr = this.elementArray;
                int i3 = iArr[i];
                this.judgeValue = ((int) (((float) (iArr[i2] - i3)) * f4)) + i3;
            } else {
                this.judgeValue = CustomSeekBar.this.mConfigArray[CustomSeekBar.this.mConfigArray.length - 1];
            }
            return this.judgeValue;
        }
    }

    public CustomSeekBar(Context context) {
        super(context);
        this.mContext = context;
        initViews();
    }

    public CustomSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initViews();
    }

    public CustomSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        initViews();
    }

    private void doAnim() {
        if (this.mIsEnlargeState) {
            ValueAnimator valueAnimator = this.mContainervalueShrinkAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.mContainervalueShrinkAnimator.cancel();
            }
            ValueAnimator valueAnimator2 = this.mCircleValueShrinkAnimator;
            if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                this.mCircleValueShrinkAnimator.cancel();
            }
            TranslateAnimation translateAnimation = this.mTvMoveUpAnimator;
            if (translateAnimation != null && !translateAnimation.hasEnded()) {
                this.mTvMoveUpAnimator.cancel();
            }
            startEnlagerAnim();
            return;
        }
        ValueAnimator valueAnimator3 = this.mContainervalueLargerAnimator;
        if (valueAnimator3 != null && valueAnimator3.isRunning()) {
            this.mContainervalueLargerAnimator.cancel();
        }
        ValueAnimator valueAnimator4 = this.mCircleValueLargerAnimator;
        if (valueAnimator4 != null && valueAnimator4.isRunning()) {
            this.mCircleValueLargerAnimator.cancel();
        }
        TranslateAnimation translateAnimation2 = this.mTvMoveDownAnimator;
        if (translateAnimation2 != null && !translateAnimation2.hasEnded()) {
            this.mTvMoveDownAnimator.cancel();
        }
        reverseEnlagerAnim();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float getCircleDiameter(int i) {
        if (i == 1) {
            return this.mContext.getResources().getDimension(R.dimen.seek_bar_timer_circle_low);
        }
        if (i == 2) {
            return this.mContext.getResources().getDimension(R.dimen.seek_bar_timer_inner_low_height);
        }
        if (i == 3) {
            return this.mContext.getResources().getDimension(R.dimen.seek_bar_timer_inner_high_height);
        }
        if (i != 4) {
            return 0.0f;
        }
        return this.mContext.getResources().getDimension(R.dimen.seek_bar_timer_outter_height);
    }

    private String getUnitString() {
        return TextUtils.isEmpty(this.mUnitString) ? "" : this.mUnitString;
    }

    private void initViews() {
        View inflate = RelativeLayout.inflate(getContext(), R.layout.layout_custom_seek_bar, this);
        this.mTvParentLayout = (RelativeLayout) inflate.findViewById(R.id.rl_seek_scale);
        this.mTvCurrentValue = (TextView) inflate.findViewById(R.id.tv_current);
        this.mTvCurrentValue.setTextColor(TintColor.tintColor());
        this.mTvStartValue = (TextView) inflate.findViewById(R.id.tv_start);
        this.mTvEndValue = (TextView) inflate.findViewById(R.id.tv_end);
        this.circleFullImageView = (CircleFullImageView) inflate.findViewById(R.id.iv_circle);
        this.circleFullImageView.updateView(1, this.mContext.getColor(R.color.color_white));
        this.mRlSeekOutter = (RelativeLayout) inflate.findViewById(R.id.rl_seek_container);
        this.mRlSeekOutterHeight = getResources().getDimension(R.dimen.seek_bar_timer_outter_height);
        this.mRlSeekInnerContainer = (RelativeLayout) inflate.findViewById(R.id.rl_seek_inner_container);
    }

    private int judgeCorridinate(float f, float f2) {
        int[] iArr = new int[2];
        this.mRlSeekOutter.getLocationOnScreen(iArr);
        int i = iArr[1];
        this.mRangeMin = new float[]{(float) ((int) (((float) iArr[0]) + (getCircleDiameter(4) / 2.0f))), (float) i};
        float circleDiameter = getCircleDiameter(4);
        this.mRangeMax = new float[]{((float) (this.mRlSeekOutter.getWidth() + iArr[0])) - (getCircleDiameter(4) / 2.0f), (float) (i + this.mRlSeekOutter.getHeight() + 50)};
        if (this.mRangeMin[0] - (getCircleDiameter(4) / 2.0f) > f || f > this.mRangeMax[0] + (getCircleDiameter(4) / 2.0f)) {
            return -1;
        }
        if ((this.mRangeMin[1] > f2 || f2 > this.mRangeMax[1]) && !this.mIsEnlargeState) {
            return -1;
        }
        int judgeValue = new JudgeSegmentResult(this.mRangeMax[0] - this.mRangeMin[0], this.mConfigArray, circleDiameter).getJudgeValue(f);
        AddSeekBarValueListener addSeekBarValueListener = this.mSeekBarValueListener;
        if (addSeekBarValueListener != null) {
            addSeekBarValueListener.currentSeekBarValue(this, this.mValueEnlargeTimes, judgeValue);
        }
        int[] iArr2 = this.mConfigArray;
        if (judgeValue < iArr2[0]) {
            judgeValue = iArr2[0];
        }
        int[] iArr3 = this.mConfigArray;
        int i2 = iArr3[iArr3.length - 1];
        return judgeValue > i2 ? i2 : judgeValue;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float judgeCorridinatePosition(float f, float f2) {
        float judgePosition = new JudgeSegmentResult(f, this.mConfigArray, f2).getJudgePosition((float) this.mCurrentValue);
        updateInnerContainer((int) judgePosition);
        return judgePosition;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void reverseEnlager() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mRlSeekInnerContainer.getLayoutParams();
        this.mInnerMarginHorizontal = (this.mRlSeekOutterHeight - getCircleDiameter(2)) / 2.0f;
        layoutParams.height = (int) getCircleDiameter(2);
        if (this.mCurrentValue == this.mConfigArray[0]) {
            layoutParams.width = (int) getCircleDiameter(2);
        }
        float f = this.mInnerMarginHorizontal;
        layoutParams.setMargins((int) f, 0, (int) f, 0);
        this.mRlSeekInnerContainer.setLayoutParams(layoutParams);
        this.mRlSeekInnerContainer.setBackground(getResources().getDrawable(R.drawable.shape_inner_radius_low));
        ((GradientDrawable) this.mRlSeekInnerContainer.getBackground()).setColor(TintColor.tintColor());
        this.circleFullImageView.updateView(1, getResources().getColor(R.color.color_white));
    }

    private void reverseEnlagerAnim() {
        this.mTvMoveDownAnimator = new TranslateAnimation(0.0f, 0.0f, -10.0f, 0.0f);
        this.mTvMoveDownAnimator.setDuration(200);
        this.mTvMoveDownAnimator.setInterpolator(new CubicEaseOutInterpolator());
        this.mTvParentLayout.startAnimation(this.mTvMoveDownAnimator);
        this.mContainervalueShrinkAnimator = ValueAnimator.ofFloat((float) ((int) getCircleDiameter(3)), (float) ((int) getCircleDiameter(2)));
        this.mContainervalueShrinkAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.timerburst.CustomSeekBar.AnonymousClass4 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) CustomSeekBar.this.mRlSeekInnerContainer.getLayoutParams();
                CustomSeekBar customSeekBar = CustomSeekBar.this;
                customSeekBar.mInnerMarginHorizontal = (customSeekBar.mRlSeekOutterHeight - floatValue) / 2.0f;
                layoutParams.setMargins((int) CustomSeekBar.this.mInnerMarginHorizontal, 0, (int) CustomSeekBar.this.mInnerMarginHorizontal, 0);
                layoutParams.height = Math.round(floatValue);
                if (CustomSeekBar.this.mCurrentValue == CustomSeekBar.this.mConfigArray[0]) {
                    layoutParams.width = Math.round(CustomSeekBar.this.getCircleDiameter(2));
                }
                GradientDrawable gradientDrawable = (GradientDrawable) CustomSeekBar.this.mRlSeekInnerContainer.getBackground();
                gradientDrawable.setColor(TintColor.tintColor());
                float f = floatValue / 2.0f;
                gradientDrawable.setCornerRadii(new float[]{f, f, f, f, f, f, f, f});
                CustomSeekBar.this.mRlSeekInnerContainer.setLayoutParams(layoutParams);
            }
        });
        this.mContainervalueShrinkAnimator.setDuration(400L);
        this.mContainervalueShrinkAnimator.setInterpolator(new CubicEaseOutInterpolator());
        this.mCircleValueShrinkAnimator = ValueAnimator.ofFloat((float) (((int) getCircleDiameter(2)) / 2), (float) (((int) getCircleDiameter(1)) / 2));
        this.mCircleValueShrinkAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.timerburst.CustomSeekBar.AnonymousClass5 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                CustomSeekBar.this.circleFullImageView.updateRadiusView(((Float) valueAnimator.getAnimatedValue()).floatValue(), CustomSeekBar.this.getResources().getColor(R.color.color_white));
            }
        });
        this.mCircleValueShrinkAnimator.setInterpolator(new CubicEaseOutInterpolator());
        this.mCircleValueShrinkAnimator.setDuration(400L);
        this.mContainervalueShrinkAnimator.start();
        this.mCircleValueShrinkAnimator.start();
    }

    @TargetApi(11)
    private void showOrHideAnim(View view, boolean z) {
        if (z) {
            view.setVisibility(4);
        } else {
            view.setVisibility(0);
        }
    }

    private void startEnlagerAnim() {
        Log.i("CustomSeekBar", "startEnlagerAnim");
        this.mTvMoveUpAnimator = new TranslateAnimation(0.0f, 0.0f, 0.0f, -10.0f);
        this.mTvMoveUpAnimator.setDuration(200);
        this.mTvMoveUpAnimator.setFillAfter(true);
        this.mTvMoveUpAnimator.setInterpolator(new CubicEaseOutInterpolator());
        this.mTvParentLayout.startAnimation(this.mTvMoveUpAnimator);
        this.mContainervalueLargerAnimator = ValueAnimator.ofInt((int) getCircleDiameter(2), (int) getCircleDiameter(3));
        this.mContainervalueLargerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.timerburst.CustomSeekBar.AnonymousClass2 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) CustomSeekBar.this.mRlSeekInnerContainer.getLayoutParams();
                CustomSeekBar customSeekBar = CustomSeekBar.this;
                customSeekBar.mInnerMarginHorizontal = (customSeekBar.mRlSeekOutterHeight - ((float) intValue)) / 2.0f;
                layoutParams.setMargins((int) CustomSeekBar.this.mInnerMarginHorizontal, 0, (int) CustomSeekBar.this.mInnerMarginHorizontal, 0);
                layoutParams.height = intValue;
                layoutParams.width = Util.clamp(layoutParams.width, intValue, layoutParams.width);
                GradientDrawable gradientDrawable = (GradientDrawable) CustomSeekBar.this.mRlSeekInnerContainer.getBackground();
                gradientDrawable.setColor(TintColor.tintColor());
                float f = (float) (intValue / 2);
                gradientDrawable.setCornerRadii(new float[]{f, f, f, f, f, f, f, f});
                CustomSeekBar.this.mRlSeekInnerContainer.setLayoutParams(layoutParams);
            }
        });
        this.mContainervalueLargerAnimator.setDuration(200L);
        this.mContainervalueLargerAnimator.setInterpolator(new CubicEaseOutInterpolator());
        this.mContainervalueLargerAnimator.start();
        this.mCircleValueLargerAnimator = ValueAnimator.ofInt(((int) getCircleDiameter(1)) / 2, ((int) getCircleDiameter(2)) / 2);
        this.mCircleValueLargerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.timerburst.CustomSeekBar.AnonymousClass3 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                CustomSeekBar.this.circleFullImageView.updateRadiusView((float) ((Integer) valueAnimator.getAnimatedValue()).intValue(), CustomSeekBar.this.getResources().getColor(R.color.color_white));
            }
        });
        this.mCircleValueLargerAnimator.setDuration(200L);
        this.mCircleValueLargerAnimator.setInterpolator(new CubicEaseOutInterpolator());
        this.mCircleValueLargerAnimator.start();
    }

    private void updateInnerContainer(int i) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mRlSeekInnerContainer.getLayoutParams();
        int circleDiameter = (int) getCircleDiameter(this.mIsEnlargeState ? 3 : 2);
        boolean z = false;
        if (this.mCurrentValue == this.mConfigArray[0]) {
            z = true;
        }
        if (!z) {
            circleDiameter += i;
        }
        layoutParams.width = circleDiameter;
        this.mRlSeekInnerContainer.setLayoutParams(layoutParams);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateTvCurrent(boolean z, float f) {
        int i = 0;
        if (this.mCurrentValue == this.mConfigArray[0]) {
            f = 0.0f;
        }
        int[] iArr = new int[2];
        this.mTvEndValue.getLocationOnScreen(iArr);
        this.mTvEndX = (float) iArr[0];
        int[] iArr2 = new int[2];
        this.mTvStartValue.getLocationOnScreen(iArr2);
        int[] iArr3 = new int[2];
        this.mRlSeekInnerContainer.getLocationOnScreen(iArr3);
        this.mCircleIndexX = ((float) iArr3[0]) + f + (getCircleDiameter(2) / 2.0f);
        if (this.mCurrentValue < 3) {
            if (this.mTvStartValue.getVisibility() == 0) {
                showOrHideAnim(this.mTvStartValue, true);
            }
            if (this.mTvEndValue.getVisibility() == 4) {
                showOrHideAnim(this.mTvEndValue, false);
            }
        } else if (this.mTvEndX - this.mCircleIndexX <= this.mTvWidth / 2.0f) {
            if (this.mTvEndValue.getVisibility() == 0) {
                showOrHideAnim(this.mTvEndValue, true);
            }
            if (this.mTvStartValue.getVisibility() == 4) {
                showOrHideAnim(this.mTvStartValue, false);
            }
        } else {
            if (this.mTvEndValue.getVisibility() == 4) {
                showOrHideAnim(this.mTvEndValue, false);
            }
            if (this.mTvStartValue.getVisibility() == 4) {
                showOrHideAnim(this.mTvStartValue, false);
            }
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mTvCurrentValue.getLayoutParams();
        int i2 = (int) (this.mCircleIndexX - (this.mTvWidth / 2.0f));
        int circleDiameter = (int) (this.mIsEnlargeState ? getCircleDiameter(3) : getCircleDiameter(2));
        int i3 = i2 - iArr2[0];
        if (f != 0.0f) {
            i = circleDiameter / 4;
        }
        layoutParams.leftMargin = i3 + i;
        this.mTvCurrentValue.setLayoutParams(layoutParams);
    }

    @SuppressLint({"ResourceType"})
    public void initSeekBarConfig(int[] iArr, int i, int i2, String str, AddSeekBarValueListener addSeekBarValueListener) {
        this.mConfigArray = iArr;
        this.mCurrentValue = i;
        this.mValueEnlargeTimes = i2;
        this.mUnitString = str;
        Log.i("CustomSeekBar", "initSeekBarConfig  mInialValue: " + this.mCurrentValue + "\n valueEnlargeTimes:" + i2 + "\n");
        for (int i3 = 0; i3 < iArr.length; i3++) {
            Log.i("CustomSeekBar", "index:  " + i3 + "   config:" + iArr[i3]);
        }
        this.mSeekBarValueListener = addSeekBarValueListener;
        this.mTvStartValue.setText(String.valueOf(this.mConfigArray[0] * this.mValueEnlargeTimes) + getUnitString());
        StringBuilder sb = new StringBuilder();
        int[] iArr2 = this.mConfigArray;
        sb.append(String.valueOf(iArr2[iArr2.length + -1] * this.mValueEnlargeTimes));
        sb.append(getUnitString());
        this.mTvEndValue.setText(sb.toString());
        TextView textView = this.mTvCurrentValue;
        textView.setText(String.valueOf(this.mCurrentValue * this.mValueEnlargeTimes) + getUnitString());
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /* class com.android.camera.timerburst.CustomSeekBar.AnonymousClass1 */

            public void onGlobalLayout() {
                CustomSeekBar.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                CustomSeekBar.this.mIsEnlargeState = false;
                CustomSeekBar customSeekBar = CustomSeekBar.this;
                customSeekBar.mMarginBuffer = customSeekBar.getCircleDiameter(4);
                CustomSeekBar.this.widthScreen = ((WindowManager) CustomSeekBar.this.mContext.getSystemService("window")).getDefaultDisplay().getWidth();
                CustomSeekBar customSeekBar2 = CustomSeekBar.this;
                customSeekBar2.mTotalwidth = (int) (((float) customSeekBar2.mRlSeekOutter.getWidth()) - CustomSeekBar.this.mMarginBuffer);
                CustomSeekBar customSeekBar3 = CustomSeekBar.this;
                customSeekBar3.mTvStartRightX = (float) (customSeekBar3.mRlSeekInnerContainer.getPaddingLeft() + CustomSeekBar.this.mTvStartValue.getWidth());
                CustomSeekBar customSeekBar4 = CustomSeekBar.this;
                customSeekBar4.mTvEndX = (float) ((customSeekBar4.widthScreen - CustomSeekBar.this.mRlSeekInnerContainer.getPaddingRight()) - CustomSeekBar.this.mTvEndValue.getWidth());
                CustomSeekBar customSeekBar5 = CustomSeekBar.this;
                customSeekBar5.mTvWidth = customSeekBar5.getResources().getDimension(R.dimen.seek_bar_timer_circle_current_tv);
                CustomSeekBar customSeekBar6 = CustomSeekBar.this;
                float judgeCorridinatePosition = customSeekBar6.judgeCorridinatePosition((float) customSeekBar6.mTotalwidth, CustomSeekBar.this.mMarginBuffer);
                CustomSeekBar.this.reverseEnlager();
                int[] iArr = new int[2];
                CustomSeekBar.this.mRlSeekOutter.getLocationOnScreen(iArr);
                CustomSeekBar.this.mXStartParent = iArr[0];
                CustomSeekBar.this.updateTvCurrent(true, judgeCorridinatePosition);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0088  */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        int judgeCorridinate;
        float f;
        float[] fArr;
        int i;
        this.mRlSeekOutterHeight = (float) this.mRlSeekOutter.getHeight();
        this.mCurrentX = motionEvent.getRawX();
        float rawY = motionEvent.getRawY();
        this.mEventType = motionEvent.getAction();
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action == 1) {
                Log.i("CustomSeekBar", "MotionEvent.ACTION_UP");
            } else if (action == 2) {
                Log.i("CustomSeekBar", "MotionEvent.ACTION_MOVE");
            }
            z = false;
            judgeCorridinate = judgeCorridinate(this.mCurrentX, rawY);
            Log.i("CustomSeekBar", " isEnlargeStateTemp : " + z + "\n  mIsEnlargeState:" + this.mIsEnlargeState + "\n  typeCor: " + judgeCorridinate);
            if (z != this.mIsEnlargeState) {
                this.mIsEnlargeState = z;
                doAnim();
            }
            if (this.mCurrentValue != judgeCorridinate && judgeCorridinate > 0) {
                this.mCurrentValue = judgeCorridinate;
                f = this.mCurrentX;
                fArr = this.mRangeMin;
                if (f > fArr[0]) {
                    updateInnerContainer(0);
                    i = 0;
                } else {
                    i = (int) (f - fArr[0]);
                    updateInnerContainer(i);
                }
                updateTvCurrent(false, (float) i);
                this.mTvCurrentValue.setText(String.valueOf(this.mCurrentValue * this.mValueEnlargeTimes) + getUnitString());
            }
            return true;
        }
        Log.i("CustomSeekBar", "MotionEvent.ACTION_DOWN");
        z = true;
        judgeCorridinate = judgeCorridinate(this.mCurrentX, rawY);
        Log.i("CustomSeekBar", " isEnlargeStateTemp : " + z + "\n  mIsEnlargeState:" + this.mIsEnlargeState + "\n  typeCor: " + judgeCorridinate);
        if (z != this.mIsEnlargeState) {
        }
        this.mCurrentValue = judgeCorridinate;
        f = this.mCurrentX;
        fArr = this.mRangeMin;
        if (f > fArr[0]) {
        }
        updateTvCurrent(false, (float) i);
        this.mTvCurrentValue.setText(String.valueOf(this.mCurrentValue * this.mValueEnlargeTimes) + getUnitString());
        return true;
    }
}
