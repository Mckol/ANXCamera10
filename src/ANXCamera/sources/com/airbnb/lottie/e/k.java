package com.airbnb.lottie.e;

/* compiled from: ScaleXY */
public class k {
    private float scaleX;
    private float scaleY;

    public k() {
        this(1.0f, 1.0f);
    }

    public k(float f, float f2) {
        this.scaleX = f;
        this.scaleY = f2;
    }

    public boolean equals(float f, float f2) {
        return this.scaleX == f && this.scaleY == f2;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public void set(float f, float f2) {
        this.scaleX = f;
        this.scaleY = f2;
    }

    public String toString() {
        return getScaleX() + "x" + getScaleY();
    }
}
