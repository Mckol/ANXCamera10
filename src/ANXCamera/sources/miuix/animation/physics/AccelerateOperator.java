package miuix.animation.physics;

public class AccelerateOperator implements PhysicsOperator {
    private final float accelerate;

    public AccelerateOperator(float f) {
        this.accelerate = f * 1000.0f;
    }

    @Override // miuix.animation.physics.PhysicsOperator
    public double updateVelocity(double d2, float f, double... dArr) {
        return d2 + ((double) (this.accelerate * f));
    }
}
