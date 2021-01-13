package miuix.animation.styles;

import android.animation.TypeEvaluator;
import miuix.animation.property.FloatProperty;
import miuix.animation.utils.CommonUtils;

public class ColorStyle extends PropertyStyle {
    private boolean mIsAtTarget;
    private float mProgress;

    ColorStyle(Object obj, FloatProperty floatProperty) {
        super(obj, floatProperty);
    }

    /* access modifiers changed from: protected */
    @Override // miuix.animation.styles.PropertyStyle
    public TypeEvaluator getEvaluator() {
        return CommonUtils.sArgbEvaluator;
    }

    /* access modifiers changed from: protected */
    @Override // miuix.animation.styles.PropertyStyle
    public boolean isAnimRunning(double d2, double d3) {
        if (d2 != getTargetDoubleValue()) {
            this.mIsAtTarget = false;
        } else if (this.mIsAtTarget) {
            return false;
        } else {
            this.mIsAtTarget = true;
        }
        this.mEquilibrium.setTargetValue(1.0d);
        return super.isAnimRunning((double) this.mProgress, d3);
    }

    /* access modifiers changed from: protected */
    @Override // miuix.animation.styles.PropertyStyle
    public void onStart() {
        this.mProgress = 0.0f;
        this.mIsAtTarget = false;
        this.mTarget.setVelocity(this.mProperty, 0.0d);
    }

    /* access modifiers changed from: protected */
    @Override // miuix.animation.styles.PropertyStyle
    public double processCurrentValue(double d2) {
        return (double) this.mProgress;
    }

    /* access modifiers changed from: protected */
    @Override // miuix.animation.styles.PropertyStyle
    public double processTargetValue(double d2) {
        return 1.0d;
    }

    /* access modifiers changed from: protected */
    @Override // miuix.animation.styles.PropertyStyle
    public float regulateProgress(float f) {
        if (f > 1.0f) {
            return 1.0f;
        }
        if (f < 0.0f) {
            return 0.0f;
        }
        return f;
    }

    /* access modifiers changed from: protected */
    @Override // miuix.animation.styles.PropertyStyle
    public double toAnimValue(double d2) {
        this.mProgress = regulateProgress((float) d2);
        return (double) ((Integer) getEvaluator().evaluate(this.mProgress, Integer.valueOf(this.mIntValues[0]), Integer.valueOf(this.mIntValues[1]))).intValue();
    }
}