package miui.animation.physics;

public class SpringOperator implements PhysicsOperator {
    private final double damping;
    private final double tension;

    public SpringOperator(float f, float f2) {
        double d2 = (double) f2;
        this.tension = Math.pow(6.283185307179586d / d2, 2.0d);
        this.damping = (((double) f) * 12.566370614359172d) / d2;
    }

    @Override // miui.animation.physics.PhysicsOperator
    public double updateVelocity(double d2, float f, double... dArr) {
        double d3 = (double) f;
        return (d2 * (1.0d - (this.damping * d3))) + ((double) ((float) (this.tension * (dArr[0] - dArr[1]) * d3)));
    }
}
