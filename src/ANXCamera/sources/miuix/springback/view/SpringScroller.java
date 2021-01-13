package miuix.springback.view;

import android.view.animation.AnimationUtils;

public class SpringScroller {
    private static final float MAX_DELTA_TIME = 0.016f;
    private static final float VALUE_THRESHOLD = 1.0f;
    private double mCurrX;
    private double mCurrY;
    private long mCurrentTime;
    private double mEndX;
    private double mEndY;
    private boolean mFinished = true;
    private int mFirstStep;
    private boolean mLastStep;
    private int mOrientation;
    private double mOriginStartX;
    private double mOriginStartY;
    private double mOriginVelocity;
    private SpringOperator mSpringOperator;
    private long mStartTime;
    private double mStartX;
    private double mStartY;
    private double mVelocity;

    public boolean computeScrollOffset() {
        if (this.mSpringOperator == null || this.mFinished) {
            return false;
        }
        int i = this.mFirstStep;
        if (i != 0) {
            if (this.mOrientation == 1) {
                this.mCurrX = (double) i;
                this.mStartX = (double) i;
            } else {
                this.mCurrY = (double) i;
                this.mStartY = (double) i;
            }
            this.mFirstStep = 0;
            return true;
        } else if (this.mLastStep) {
            this.mFinished = true;
            return true;
        } else {
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
                if (isAtEquilibrium(this.mCurrY, this.mOriginStartY, this.mEndY)) {
                    this.mLastStep = true;
                    this.mCurrY = this.mEndY;
                } else {
                    this.mStartY = this.mCurrY;
                }
            } else {
                double updateVelocity2 = this.mSpringOperator.updateVelocity(this.mVelocity, min, this.mEndX, this.mStartX);
                this.mCurrX = this.mStartX + (((double) min) * updateVelocity2);
                this.mVelocity = updateVelocity2;
                if (isAtEquilibrium(this.mCurrX, this.mOriginStartX, this.mEndX)) {
                    this.mLastStep = true;
                    this.mCurrX = this.mEndX;
                } else {
                    this.mStartX = this.mCurrX;
                }
            }
            return true;
        }
    }

    public final void forceStop() {
        this.mFinished = true;
        this.mFirstStep = 0;
    }

    public final int getCurrX() {
        return (int) this.mCurrX;
    }

    public final int getCurrY() {
        return (int) this.mCurrY;
    }

    public boolean isAtEquilibrium(double d2, double d3, double d4) {
        if (d3 < d4 && d2 > d4) {
            return true;
        }
        int i = (d3 > d4 ? 1 : (d3 == d4 ? 0 : -1));
        if (i <= 0 || d2 >= d4) {
            return (i == 0 && Math.signum(this.mOriginVelocity) != Math.signum(d2)) || Math.abs(d2 - d4) < 1.0d;
        }
        return true;
    }

    public final boolean isFinished() {
        return this.mFinished;
    }

    public void scrollByFling(float f, float f2, float f3, float f4, float f5, int i, boolean z) {
        this.mFinished = false;
        this.mLastStep = false;
        double d2 = (double) f;
        this.mStartX = d2;
        this.mOriginStartX = d2;
        this.mEndX = (double) f2;
        double d3 = (double) f3;
        this.mStartY = d3;
        this.mOriginStartY = d3;
        this.mCurrY = (double) ((int) this.mStartY);
        this.mEndY = (double) f4;
        double d4 = (double) f5;
        this.mOriginVelocity = d4;
        this.mVelocity = d4;
        if (Math.abs(this.mVelocity) <= 5000.0d || z) {
            this.mSpringOperator = new SpringOperator(1.0f, 0.4f);
        } else {
            this.mSpringOperator = new SpringOperator(1.0f, 0.55f);
        }
        this.mOrientation = i;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
    }

    public void setFirstStep(int i) {
        this.mFirstStep = i;
    }
}
