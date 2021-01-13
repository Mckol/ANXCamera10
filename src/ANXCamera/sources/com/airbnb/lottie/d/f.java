package com.airbnb.lottie.d;

/* compiled from: MeanCalculator */
public class f {
    private int n;
    private float sum;

    public void add(float f) {
        this.sum += f;
        this.n++;
        int i = this.n;
        if (i == Integer.MAX_VALUE) {
            this.sum /= 2.0f;
            this.n = i / 2;
        }
    }

    public float getMean() {
        int i = this.n;
        if (i == 0) {
            return 0.0f;
        }
        return this.sum / ((float) i);
    }
}
