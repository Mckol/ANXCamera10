package com.android.camera.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import com.android.camera.Util;
import com.android.camera.customization.TintColor;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.log.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import miui.view.animation.CubicEaseOutInterpolator;

public class SeekBarCompat extends SeekBar implements SeekBar.OnSeekBarChangeListener, View.OnTouchListener {
    public static final int CENTER_SEEKBAR = 2;
    private static final int INTERVAL = 5;
    public static final int NORMAL_SEEKBAR = 1;
    private static final String TAG = "SeekBarCompat";
    private ValueAnimator mAnimator;
    private float mAvaliableWidth;
    private Paint mBackgroundPaint;
    private float mBigCircleRadius;
    private boolean mCenterTwoWayMode;
    private Paint mCirclePaint;
    private float mCircleRadius;
    private int mClearGap;
    private Paint mClearPaint;
    private float mCurrentCircleRadius;
    private boolean mIsRTL;
    private float mLineWidth;
    private OnSeekBarCompatTouchListener mOnSeekBarCompatTouchListener;
    private Paint mPinPointPaint;
    private int mPinProgress;
    private Paint mPinProgressPaint;
    private RectF mPinProgressRectF;
    private float mPinRadius;
    private OnSeekBarCompatChangeListener mSeekBarCompatChangeListener;
    private Rect mTouchRect;
    private int mUserLastSeekPoint;
    private boolean mUserSeek;

    public interface OnSeekBarCompatChangeListener {
        void onProgressChanged(SeekBar seekBar, int i, boolean z);

        void onStartTrackingTouch(SeekBar seekBar);

        void onStopTrackingTouch(SeekBar seekBar);
    }

    public interface OnSeekBarCompatTouchListener {
        boolean onTouch(View view, MotionEvent motionEvent);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SeekBarMode {
    }

    public SeekBarCompat(Context context) {
        this(context, null, -1, 0);
    }

    public SeekBarCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1, 0);
    }

    public SeekBarCompat(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeekBarCompat(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTouchRect = new Rect();
        init(context);
    }

    private boolean contains(int i, int i2, int i3) {
        return i2 != i3 && i > Math.min(i2, i3) && i < Math.max(i2, i3);
    }

    private void init(Context context) {
        this.mIsRTL = Util.isLayoutRTL(context);
        setThumb(null);
        setOnSeekBarChangeListener(this);
        setOnTouchListener(this);
        setLayerType(2, null);
        this.mLineWidth = (float) Util.dpToPixel(1.818f);
        this.mBackgroundPaint = new Paint();
        this.mBackgroundPaint.setAntiAlias(true);
        this.mBackgroundPaint.setStyle(Paint.Style.FILL);
        this.mBackgroundPaint.setColor(-1);
        this.mBackgroundPaint.setShadowLayer(1.0f, 0.0f, 0.0f, Integer.MIN_VALUE);
        this.mClearPaint = new Paint();
        this.mClearPaint.setAntiAlias(true);
        this.mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.mCirclePaint = new Paint();
        this.mCirclePaint.setStyle(Paint.Style.STROKE);
        this.mCirclePaint.setColor(-1);
        this.mCirclePaint.setAntiAlias(true);
        this.mCirclePaint.setStrokeWidth(this.mLineWidth);
        this.mCircleRadius = (float) Util.dpToPixel(11.27f);
        this.mBigCircleRadius = (float) Util.dpToPixel(16.36f);
        this.mCurrentCircleRadius = this.mCircleRadius;
        this.mCirclePaint.setShadowLayer(1.0f, 0.0f, 0.0f, Integer.MIN_VALUE);
        this.mPinProgressPaint = new Paint();
        this.mPinProgressPaint.setStyle(Paint.Style.FILL);
        this.mPinProgressPaint.setColor(TintColor.tintColor());
        this.mPinProgressPaint.setAntiAlias(true);
        this.mPinPointPaint = new Paint();
        this.mPinPointPaint.setStyle(Paint.Style.FILL);
        this.mPinPointPaint.setColor(-1);
        this.mPinPointPaint.setAntiAlias(true);
        this.mPinPointPaint.setShadowLayer(1.0f, 0.0f, 0.0f, Integer.MIN_VALUE);
        this.mPinProgressRectF = new RectF();
        this.mPinRadius = this.mLineWidth + 1.0f;
        this.mClearGap = Util.dpToPixel(2.181f);
    }

    private void startAnimator(boolean z) {
        float f = z ? this.mBigCircleRadius : this.mCircleRadius;
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        this.mAnimator = ValueAnimator.ofFloat(this.mCurrentCircleRadius, f);
        this.mAnimator.setDuration(200L).setInterpolator(new CubicEaseOutInterpolator());
        this.mAnimator.start();
        this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.SeekBarCompat.AnonymousClass1 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                SeekBarCompat.this.mCurrentCircleRadius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                SeekBarCompat.this.invalidate();
            }
        });
    }

    public int getNextProgress(float f) {
        int i;
        float f2 = this.mAvaliableWidth;
        if (f2 == 0.0f) {
            f2 = (float) getWidth();
        }
        float f3 = f - this.mBigCircleRadius;
        if (f3 < 0.0f) {
            i = this.mIsRTL ? getMax() : getMin();
        } else if (f3 > f2) {
            i = this.mIsRTL ? getMin() : getMax();
        } else {
            i = Math.round((f3 / f2) * ((float) getMax()));
            if (this.mIsRTL) {
                i = getMax() - i;
            }
        }
        if (!this.mCenterTwoWayMode) {
            return Util.clamp(i, 0, getMax());
        }
        int i2 = this.mPinProgress;
        return Util.clamp(i - i2, 0 - i2, getMax() - this.mPinProgress);
    }

    public int getPinProgress() {
        return this.mPinProgress;
    }

    public Rect getTouchRect() {
        return this.mTouchRect;
    }

    public boolean isCenterTwoWayMode() {
        return this.mCenterTwoWayMode;
    }

    /* access modifiers changed from: protected */
    public synchronized void onDraw(Canvas canvas) {
        int height = getHeight() / 2;
        if (this.mAvaliableWidth == 0.0f) {
            this.mAvaliableWidth = ((float) getWidth()) - (this.mBigCircleRadius * 2.0f);
        }
        int progress = getProgress();
        float max = (this.mAvaliableWidth * (((float) (this.mIsRTL ? getMax() - this.mPinProgress : this.mPinProgress)) / ((float) getMax()))) + this.mBigCircleRadius;
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), null, 31);
        float f = (float) height;
        canvas.drawRoundRect(this.mBigCircleRadius - this.mCircleRadius, f - (this.mLineWidth / 2.0f), this.mCircleRadius + (((float) getWidth()) - this.mBigCircleRadius), f + (this.mLineWidth / 2.0f), 1.0f, 1.0f, this.mBackgroundPaint);
        if (this.mIsRTL) {
            this.mPinProgressRectF.left = (((float) getWidth()) - this.mBigCircleRadius) + this.mCircleRadius;
            if (this.mCenterTwoWayMode) {
                if (progress <= this.mPinProgress) {
                    this.mPinProgressRectF.left = this.mPinRadius + max;
                } else if (progress > this.mPinProgress) {
                    this.mPinProgressRectF.left = max - this.mPinRadius;
                }
                this.mPinPointPaint.setColor(-1);
                String str = TAG;
                Log.d(str, "current progress:" + progress);
            } else if (progress <= this.mPinProgress) {
                this.mPinPointPaint.setColor(-1);
            } else {
                this.mPinPointPaint.setColor(TintColor.tintColor());
            }
            this.mPinProgressRectF.right = this.mBigCircleRadius + ((((float) (getMax() - progress)) * this.mAvaliableWidth) / ((float) getMax()));
            this.mPinProgressRectF.top = f - (this.mLineWidth / 2.0f);
            this.mPinProgressRectF.bottom = (this.mLineWidth / 2.0f) + f;
            canvas.drawRoundRect(this.mPinProgressRectF, 0.0f, 0.0f, this.mPinProgressPaint);
            if (this.mPinProgress != 0) {
                canvas.drawCircle(max, f, this.mPinRadius, this.mPinPointPaint);
            }
            if (this.mTouchRect == null) {
                this.mTouchRect = new Rect();
            }
            this.mTouchRect.set((int) (((double) this.mBigCircleRadius) + Math.floor((double) ((((((float) (getMax() - progress)) * this.mAvaliableWidth) / ((float) getMax())) - this.mCurrentCircleRadius) - ((float) this.mClearGap)))), 0, (int) (((double) this.mBigCircleRadius) + Math.ceil((double) (((((float) (getMax() - progress)) * this.mAvaliableWidth) / ((float) getMax())) + this.mCurrentCircleRadius + ((float) this.mClearGap)))), getHeight());
            canvas.drawRect((float) ((int) (((double) this.mBigCircleRadius) + Math.floor((double) ((((((float) (getMax() - progress)) * this.mAvaliableWidth) / ((float) getMax())) - this.mCurrentCircleRadius) - ((float) this.mClearGap))))), f - this.mCurrentCircleRadius, (float) ((int) (((double) this.mBigCircleRadius) + Math.ceil((double) (((((float) (getMax() - progress)) * this.mAvaliableWidth) / ((float) getMax())) + this.mCurrentCircleRadius + ((float) this.mClearGap))))), f + this.mCurrentCircleRadius, this.mClearPaint);
            canvas.drawCircle(this.mBigCircleRadius + ((((float) (getMax() - progress)) * this.mAvaliableWidth) / ((float) getMax())), f, this.mCurrentCircleRadius - (this.mCirclePaint.getStrokeWidth() / 2.0f), this.mCirclePaint);
        } else {
            this.mPinProgressRectF.left = this.mBigCircleRadius - this.mCircleRadius;
            if (this.mCenterTwoWayMode) {
                if (progress <= this.mPinProgress) {
                    this.mPinProgressRectF.left = max - this.mPinRadius;
                } else if (progress > this.mPinProgress) {
                    this.mPinProgressRectF.left = this.mPinRadius + max;
                }
                this.mPinPointPaint.setColor(-1);
                String str2 = TAG;
                Log.d(str2, "current progress:" + progress);
            } else if (progress <= this.mPinProgress) {
                this.mPinPointPaint.setColor(-1);
            } else {
                this.mPinPointPaint.setColor(TintColor.tintColor());
            }
            float f2 = (float) progress;
            this.mPinProgressRectF.right = this.mBigCircleRadius + ((this.mAvaliableWidth * f2) / ((float) getMax()));
            this.mPinProgressRectF.top = f - (this.mLineWidth / 2.0f);
            this.mPinProgressRectF.bottom = (this.mLineWidth / 2.0f) + f;
            canvas.drawRoundRect(this.mPinProgressRectF, 0.0f, 0.0f, this.mPinProgressPaint);
            if (this.mPinProgress != 0) {
                canvas.drawCircle(max, f, this.mPinRadius, this.mPinPointPaint);
            }
            if (this.mTouchRect == null) {
                this.mTouchRect = new Rect();
            }
            this.mTouchRect.set((int) (((double) this.mBigCircleRadius) + Math.floor((double) ((((this.mAvaliableWidth * f2) / ((float) getMax())) - this.mCurrentCircleRadius) - ((float) this.mClearGap)))), 0, (int) (((double) this.mBigCircleRadius) + Math.ceil((double) (((this.mAvaliableWidth * f2) / ((float) getMax())) + this.mCurrentCircleRadius + ((float) this.mClearGap)))), getHeight());
            canvas.drawRect((float) ((int) (((double) this.mBigCircleRadius) + Math.floor((double) ((((this.mAvaliableWidth * f2) / ((float) getMax())) - this.mCurrentCircleRadius) - ((float) this.mClearGap))))), f - this.mCurrentCircleRadius, (float) ((int) (((double) this.mBigCircleRadius) + Math.ceil((double) (((this.mAvaliableWidth * f2) / ((float) getMax())) + this.mCurrentCircleRadius + ((float) this.mClearGap))))), f + this.mCurrentCircleRadius, this.mClearPaint);
            canvas.drawCircle(this.mBigCircleRadius + ((f2 * this.mAvaliableWidth) / ((float) getMax())), f, this.mCurrentCircleRadius - (this.mCirclePaint.getStrokeWidth() / 2.0f), this.mCirclePaint);
        }
        canvas.restoreToCount(saveLayer);
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        int i2 = this.mPinProgress;
        if (i2 != 0) {
            if (z && i == i2) {
                ViberatorContext.getInstance(getContext().getApplicationContext()).performModeSwitch();
                this.mUserLastSeekPoint = i;
            } else if (this.mUserSeek) {
                if (contains(this.mPinProgress, i, this.mUserLastSeekPoint)) {
                    ViberatorContext.getInstance(getContext().getApplicationContext()).performModeSwitch();
                }
                this.mUserLastSeekPoint = i;
            }
        }
        if (this.mSeekBarCompatChangeListener != null) {
            if (this.mCenterTwoWayMode) {
                i -= this.mPinProgress;
            }
            this.mSeekBarCompatChangeListener.onProgressChanged(seekBar, i, z);
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        OnSeekBarCompatChangeListener onSeekBarCompatChangeListener = this.mSeekBarCompatChangeListener;
        if (onSeekBarCompatChangeListener != null) {
            onSeekBarCompatChangeListener.onStartTrackingTouch(seekBar);
        }
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        OnSeekBarCompatChangeListener onSeekBarCompatChangeListener = this.mSeekBarCompatChangeListener;
        if (onSeekBarCompatChangeListener != null) {
            onSeekBarCompatChangeListener.onStopTrackingTouch(seekBar);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000e, code lost:
        if (r0 != 3) goto L_0x0035;
     */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (!this.mUserSeek && this.mUserLastSeekPoint != getProgress()) {
                        this.mUserLastSeekPoint = getProgress();
                        this.mUserSeek = true;
                    }
                }
            }
            this.mUserSeek = false;
            startAnimator(false);
        } else {
            startAnimator(true);
            this.mUserLastSeekPoint = getProgress();
        }
        OnSeekBarCompatTouchListener onSeekBarCompatTouchListener = this.mOnSeekBarCompatTouchListener;
        if (onSeekBarCompatTouchListener != null) {
            return onSeekBarCompatTouchListener.onTouch(view, motionEvent);
        }
        return false;
    }

    public void setCenterTwoWayMode(boolean z) {
        this.mCenterTwoWayMode = z;
        if (this.mCenterTwoWayMode) {
            invalidate();
        }
    }

    public void setOnSeekBarChangeListener(OnSeekBarCompatChangeListener onSeekBarCompatChangeListener) {
        this.mSeekBarCompatChangeListener = onSeekBarCompatChangeListener;
    }

    public void setOnSeekBarCompatTouchListener(OnSeekBarCompatTouchListener onSeekBarCompatTouchListener) {
        this.mOnSeekBarCompatTouchListener = onSeekBarCompatTouchListener;
    }

    public synchronized void setProgress(int i) {
        setProgress(i, false);
    }

    public synchronized void setProgress(int i, boolean z) {
        if (this.mCenterTwoWayMode) {
            i += this.mPinProgress;
        }
        if (!z) {
            super.setProgress(i);
        } else {
            try {
                Method declaredMethod = ProgressBar.class.getDeclaredMethod("setProgressInternal", Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(this, Integer.valueOf(i), true, false);
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
            }
        }
    }

    public void setSeekBarPinProgress(int i) {
        int max = getMax();
        int min = getMin();
        if (i == max || i == min) {
            this.mPinProgress = 0;
        } else {
            this.mPinProgress = i;
        }
    }

    public void setVisibility(int i) {
        Paint paint;
        super.setVisibility(i);
        if (i == 0 && (paint = this.mPinProgressPaint) != null) {
            paint.setColor(TintColor.tintColor());
        }
    }
}
