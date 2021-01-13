package miuix.animation.physics;

import miuix.animation.IAnimTarget;
import miuix.animation.property.FloatProperty;

public class EquilibriumChecker {
    public static final float MIN_VISIBLE_CHANGE_ALPHA = 0.00390625f;
    public static final float MIN_VISIBLE_CHANGE_PIXELS = 1.0f;
    public static final float MIN_VISIBLE_CHANGE_ROTATION_DEGREES = 0.1f;
    public static final float MIN_VISIBLE_CHANGE_SCALE = 0.002f;
    private static final float THRESHOLD_MULTIPLIER = 0.75f;
    private static final float VELOCITY_THRESHOLD_MULTIPLIER = 16.666666f;
    private double mTargetValue = Double.MAX_VALUE;
    private float mValueThreshold;
    private float mVelocityThreshold;

    public EquilibriumChecker(IAnimTarget iAnimTarget, FloatProperty floatProperty) {
        this.mValueThreshold = iAnimTarget.getMinVisibleChange(floatProperty) * 0.75f;
        this.mVelocityThreshold = this.mValueThreshold * VELOCITY_THRESHOLD_MULTIPLIER;
    }

    private boolean isAt(double d2, double d3) {
        return Math.abs(this.mTargetValue) == 3.4028234663852886E38d || Math.abs(d2 - d3) < ((double) this.mValueThreshold);
    }

    public boolean isAtEquilibrium(double d2, double d3) {
        return isAt(d2, this.mTargetValue) && Math.abs(d3) < ((double) this.mVelocityThreshold);
    }

    public void setTargetValue(double d2) {
        this.mTargetValue = d2;
    }
}
