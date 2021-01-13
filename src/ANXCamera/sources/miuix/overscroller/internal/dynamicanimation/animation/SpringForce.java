package miuix.overscroller.internal.dynamicanimation.animation;

import miuix.overscroller.internal.dynamicanimation.animation.DynamicAnimation;

public final class SpringForce implements Force {
    public static final float DAMPING_RATIO_HIGH_BOUNCY = 0.2f;
    public static final float DAMPING_RATIO_LOW_BOUNCY = 0.75f;
    public static final float DAMPING_RATIO_MEDIUM_BOUNCY = 0.5f;
    public static final float DAMPING_RATIO_NO_BOUNCY = 1.0f;
    private static final float MOVE_STOP_PHYSICAL_DISTANCE = 0.6f;
    public static final float STIFFNESS_HIGH = 10000.0f;
    public static final float STIFFNESS_LOW = 200.0f;
    public static final float STIFFNESS_MEDIUM = 1500.0f;
    public static final float STIFFNESS_VERY_LOW = 50.0f;
    private static final double UNSET = Double.MAX_VALUE;
    private static final double VELOCITY_THRESHOLD_MULTIPLIER = 62.5d;
    private double mDampedFreq;
    double mDampingRatio = 0.5d;
    private double mFinalPosition = UNSET;
    private double mGammaMinus;
    private double mGammaPlus;
    private boolean mInitialized = false;
    private final DynamicAnimation.MassState mMassState = new DynamicAnimation.MassState();
    double mNaturalFreq = Math.sqrt(1500.0d);
    double mTimeRatio = 1000.0d;
    private double mValueThreshold;
    private double mVelocityThreshold;

    public SpringForce() {
    }

    public SpringForce(float f) {
        this.mFinalPosition = (double) f;
    }

    private void init() {
        if (!this.mInitialized) {
            if (this.mFinalPosition != UNSET) {
                double d2 = this.mDampingRatio;
                if (d2 > 1.0d) {
                    double d3 = this.mNaturalFreq;
                    this.mGammaPlus = ((-d2) * d3) + (d3 * Math.sqrt((d2 * d2) - 1.0d));
                    double d4 = this.mDampingRatio;
                    double d5 = this.mNaturalFreq;
                    this.mGammaMinus = ((-d4) * d5) - (d5 * Math.sqrt((d4 * d4) - 1.0d));
                } else if (d2 >= 0.0d && d2 < 1.0d) {
                    this.mDampedFreq = this.mNaturalFreq * Math.sqrt(1.0d - (d2 * d2));
                }
                this.mInitialized = true;
                return;
            }
            throw new IllegalStateException("Error: Final position of the spring must be set before the animation starts");
        }
    }

    @Override // miuix.overscroller.internal.dynamicanimation.animation.Force
    public float getAcceleration(float f, float f2) {
        float finalPosition = f - getFinalPosition();
        double d2 = this.mNaturalFreq;
        return (float) (((-(d2 * d2)) * ((double) finalPosition)) - (((d2 * 2.0d) * this.mDampingRatio) * ((double) f2)));
    }

    public float getDampingRatio() {
        return (float) this.mDampingRatio;
    }

    public float getFinalPosition() {
        return (float) this.mFinalPosition;
    }

    public float getStiffness() {
        double d2 = this.mNaturalFreq;
        return (float) (d2 * d2);
    }

    @Override // miuix.overscroller.internal.dynamicanimation.animation.Force
    public boolean isAtEquilibrium(float f, float f2) {
        return ((double) Math.abs(f2)) < this.mVelocityThreshold && ((double) Math.abs(f - getFinalPosition())) < this.mValueThreshold;
    }

    public SpringForce setDampingRatio(float f) {
        if (f >= 0.0f) {
            this.mDampingRatio = (double) f;
            this.mInitialized = false;
            return this;
        }
        throw new IllegalArgumentException("Damping ratio must be non-negative");
    }

    public SpringForce setFinalPosition(float f) {
        this.mFinalPosition = (double) f;
        return this;
    }

    public SpringForce setStiffness(float f) {
        if (f > 0.0f) {
            this.mNaturalFreq = Math.sqrt((double) f);
            this.mInitialized = false;
            return this;
        }
        throw new IllegalArgumentException("Spring stiffness constant must be positive.");
    }

    public SpringForce setTimeRatio(double d2) {
        this.mTimeRatio = d2;
        return this;
    }

    /* access modifiers changed from: package-private */
    public void setValueThreshold(double d2) {
        this.mValueThreshold = Math.abs(d2);
        this.mVelocityThreshold = this.mValueThreshold * VELOCITY_THRESHOLD_MULTIPLIER;
    }

    /* access modifiers changed from: package-private */
    public DynamicAnimation.MassState updateValues(double d2, double d3, long j) {
        double d4;
        double d5;
        init();
        double d6 = ((double) j) / this.mTimeRatio;
        double d7 = d2 - this.mFinalPosition;
        double d8 = this.mDampingRatio;
        if (d8 > 1.0d) {
            double d9 = this.mGammaMinus;
            double d10 = this.mGammaPlus;
            double d11 = d7 - (((d9 * d7) - d3) / (d9 - d10));
            double d12 = ((d7 * d9) - d3) / (d9 - d10);
            d5 = (Math.pow(2.718281828459045d, d9 * d6) * d11) + (Math.pow(2.718281828459045d, this.mGammaPlus * d6) * d12);
            double d13 = this.mGammaMinus;
            double pow = d11 * d13 * Math.pow(2.718281828459045d, d13 * d6);
            double d14 = this.mGammaPlus;
            d4 = pow + (d12 * d14 * Math.pow(2.718281828459045d, d14 * d6));
        } else if (d8 == 1.0d) {
            double d15 = this.mNaturalFreq;
            double d16 = d3 + (d15 * d7);
            double d17 = d7 + (d16 * d6);
            d5 = Math.pow(2.718281828459045d, (-d15) * d6) * d17;
            double pow2 = d17 * Math.pow(2.718281828459045d, (-this.mNaturalFreq) * d6);
            double d18 = this.mNaturalFreq;
            d4 = (d16 * Math.pow(2.718281828459045d, (-d18) * d6)) + (pow2 * (-d18));
        } else {
            double d19 = 1.0d / this.mDampedFreq;
            double d20 = this.mNaturalFreq;
            double d21 = d19 * ((d8 * d20 * d7) + d3);
            double pow3 = Math.pow(2.718281828459045d, (-d8) * d20 * d6) * ((Math.cos(this.mDampedFreq * d6) * d7) + (Math.sin(this.mDampedFreq * d6) * d21));
            double d22 = this.mNaturalFreq;
            double d23 = this.mDampingRatio;
            double pow4 = Math.pow(2.718281828459045d, (-d23) * d22 * d6);
            double d24 = this.mDampedFreq;
            double sin = (-d24) * d7 * Math.sin(d24 * d6);
            double d25 = this.mDampedFreq;
            d4 = ((-d22) * pow3 * d23) + (pow4 * (sin + (d21 * d25 * Math.cos(d25 * d6))));
            d5 = pow3;
        }
        double d26 = 0.0d;
        if (Math.abs(d5) < 0.6000000238418579d) {
            d4 = 0.0d;
        } else {
            d26 = d5;
        }
        DynamicAnimation.MassState massState = this.mMassState;
        massState.mValue = (float) (d26 + this.mFinalPosition);
        massState.mVelocity = (float) d4;
        return massState;
    }
}
