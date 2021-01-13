package miui.view;

import android.view.animation.AnimationUtils;
import miui.view.springback.SpringOperator;

public class SpringScroller {
    private static final int HORIZONTAL = 1;
    private static final float MAX_DELTA_TIME = 0.016f;
    private static final float VALUE_THRESHOLD = 1.0f;
    private static final int VERTICAL = 2;
    private double mCurrX;
    private double mCurrY;
    private long mCurrentTime;
    private double mEndX;
    private double mEndY;
    private boolean mFinished = true;
    private boolean mLastStep;
    private int mOrientation;
    private double mOriginStartY;
    private SpringOperator mSpringOperator;
    private long mStartTime;
    private double mStartX;
    private double mStartY;
    private double mVelocity;

    public final void abortAnimation() {
        this.mFinished = true;
    }

    public boolean computeScrollOffset() {
        if (this.mSpringOperator == null || this.mFinished) {
            return false;
        }
        if (this.mLastStep) {
            this.mFinished = true;
            this.mCurrY = this.mEndY;
            this.mCurrX = this.mEndX;
            return true;
        }
        this.mCurrentTime = AnimationUtils.currentAnimationTimeMillis();
        float min = Math.min(((float) (this.mCurrentTime - this.mStartTime)) / 1000.0f, (float) MAX_DELTA_TIME);
        if (min == 0.0f) {
            min = 0.016f;
        }
        this.mStartTime = this.mCurrentTime;
        if (this.mOrientation == 2) {
            double updateVelocity = this.mSpringOperator.updateVelocity(this.mVelocity, min, this.mEndY, this.mStartY);
            this.mCurrY = this.mStartY + (((double) min) * updateVelocity);
            this.mVelocity = updateVelocity;
            if (isAtEquilibrium(this.mCurrY, this.mEndY)) {
                this.mLastStep = true;
            } else {
                this.mStartY = this.mCurrY;
            }
        } else {
            double updateVelocity2 = this.mSpringOperator.updateVelocity(this.mVelocity, min, this.mEndX, this.mStartX);
            this.mCurrX = this.mStartX + (((double) min) * updateVelocity2);
            this.mVelocity = updateVelocity2;
            if (isAtEquilibrium(this.mCurrX, this.mEndX)) {
                this.mLastStep = true;
            } else {
                this.mStartX = this.mCurrX;
            }
        }
        return true;
    }

    public final int getCurrX() {
        return (int) this.mCurrX;
    }

    public final int getCurrY() {
        return (int) this.mCurrY;
    }

    public final int getFinalX() {
        return (int) this.mEndX;
    }

    public final int getStartX() {
        return (int) this.mStartX;
    }

    public boolean isAtEquilibrium(double d2, double d3) {
        return Math.abs(d2 - d3) < 1.0d;
    }

    public final boolean isFinished() {
        return this.mFinished;
    }

    public void setFinalX(int i) {
        this.mEndX = (double) i;
        this.mFinished = false;
    }

    public void startScroll(float f, float f2, float f3, float f4, float f5) {
        this.mFinished = false;
        this.mLastStep = false;
        this.mStartX = (double) f;
        this.mEndX = (double) f2;
        double d2 = (double) f3;
        this.mStartY = d2;
        this.mOriginStartY = d2;
        this.mCurrY = (double) ((int) this.mStartY);
        this.mEndY = (double) f4;
        this.mVelocity = (double) f5;
        if (Math.abs(this.mVelocity) <= 5000.0d) {
            this.mSpringOperator = new SpringOperator(0.9f, 0.35f);
        } else {
            this.mSpringOperator = new SpringOperator(0.9f, 0.35f);
        }
        this.mOrientation = Math.abs(f4 - f3) > Math.abs(f2 - f) ? 2 : 1;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
    }
}
