package miui.view.springback;

public class SpringOperator {
    private final double damping;
    private final double tension;

    public SpringOperator(float f, float f2) {
        double d2 = (double) f2;
        this.tension = Math.pow(6.283185307179586d / d2, 2.0d);
        this.damping = (((double) f) * 12.566370614359172d) / d2;
    }

    public double updateVelocity(double d2, float f, double d3, double d4) {
        double d5 = (double) f;
        return (d2 * (1.0d - (this.damping * d5))) + ((double) ((float) (this.tension * (d3 - d4) * d5)));
    }
}
