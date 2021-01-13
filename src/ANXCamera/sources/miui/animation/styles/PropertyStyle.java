package miui.animation.styles;

import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import java.util.Arrays;
import miui.animation.IAnimTarget;
import miui.animation.base.AnimConfigLink;
import miui.animation.physics.AccelerateOperator;
import miui.animation.physics.EquilibriumChecker;
import miui.animation.physics.FrictionOperator;
import miui.animation.physics.PhysicsOperator;
import miui.animation.physics.SpringOperator;
import miui.animation.property.FloatProperty;
import miui.animation.property.IIntValueProperty;
import miui.animation.utils.CommonUtils;
import miui.animation.utils.EaseManager;
import miui.animation.utils.LogUtils;

public class PropertyStyle {
    private static final int DIFF_RATIO_THRESHOLD = 3;
    private AnimConfigLink mConfig;
    private long mDuration;
    EquilibriumChecker mEquilibrium;
    private int mFrameCount;
    int[] mIntValues = new int[0];
    private TimeInterpolator mInterpolator;
    private boolean mIsRunning;
    private PhysicsOperator mPhyOperator;
    FloatProperty mProperty;
    IAnimTarget mTarget;
    Object mToTag;
    private long mTotalTime;
    boolean mUseIntValue;
    private double[] mVV = {0.0d, 0.0d};
    float[] mValues = new float[0];

    public PropertyStyle(Object obj, FloatProperty floatProperty) {
        this.mToTag = obj;
        this.mProperty = floatProperty;
        this.mUseIntValue = this.mProperty instanceof IIntValueProperty;
    }

    private void applyDelayedAnimConfig(float f, double d2) {
        double[] dArr = this.mVV;
        double d3 = dArr[0];
        double d4 = dArr[1];
        doPhysicsCalculation(dArr, f, d2);
        EaseManager.EaseStyle easeEffectFromValue = this.mConfig.getEaseEffectFromValue(this.mProperty, d3, this.mVV[0]);
        if (easeEffectFromValue != null) {
            setEase(easeEffectFromValue);
        }
        double[] dArr2 = this.mVV;
        dArr2[0] = d3;
        dArr2[1] = d4;
    }

    private PhysicsOperator createPhyOperator(EaseManager.EaseStyle easeStyle) {
        float[] factors = getFactors(easeStyle);
        int i = easeStyle.style;
        if (i == -4) {
            return new FrictionOperator(factors[0]);
        }
        if (i == -3) {
            return new AccelerateOperator(factors[0]);
        }
        if (i != -2) {
            return null;
        }
        return new SpringOperator(factors[0], factors[1]);
    }

    private void doFinishProcess() {
        if (!(this.mPhyOperator instanceof SpringOperator)) {
            setFinishVV();
            return;
        }
        double targetDoubleValue = getTargetDoubleValue();
        double diff = getDiff(targetDoubleValue, this.mVV[0]);
        double ceil = Math.ceil(Math.abs(diff) / ((double) this.mTarget.getMinVisibleChange(this.mProperty)));
        if (ceil < 3.0d) {
            if (LogUtils.isLogEnabled()) {
                LogUtils.debug("doFinishProcess", this.mProperty, "ratio = " + ceil, "targetValue = " + targetDoubleValue, "value = " + this.mVV[0], "diff = " + diff, "frameCount = " + this.mFrameCount);
            }
            setFinishVV();
            return;
        }
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("doFinishProcess, start spring back", new Object[0]);
        }
        setEase(CommonUtils.sDefEase);
        this.mIsRunning = true;
    }

    private void doPhysicsCalculation(double[] dArr, float f, double d2) {
        double processTargetValue = processTargetValue(d2);
        double processCurrentValue = processCurrentValue(dArr[0]);
        dArr[1] = this.mPhyOperator.updateVelocity(dArr[1], f, processTargetValue, processCurrentValue);
        dArr[0] = toAnimValue(processCurrentValue + (dArr[1] * ((double) f)));
    }

    private double getDiff(double d2, double d3) {
        return processTargetValue(d2) - processCurrentValue(d3);
    }

    private float[] getFactors(EaseManager.EaseStyle easeStyle) {
        if (easeStyle.factors.length == 0) {
            int i = easeStyle.style;
            if (i == -4) {
                return new float[]{0.4761905f};
            } else if (i == -2) {
                return this.mTarget.getVelocity(this.mProperty) > 0.0d ? new float[]{0.65f, 0.35f} : new float[]{1.0f, 0.35f};
            }
        }
        return easeStyle.factors;
    }

    private double getTargetDoubleValue() {
        return this.mUseIntValue ? (double) getTargetIntValue() : (double) getTargetValue();
    }

    private void setAnimValue(double d2) {
        if (this.mUseIntValue) {
            this.mTarget.setIntValue((IIntValueProperty) this.mProperty, (int) d2);
            return;
        }
        IAnimTarget iAnimTarget = this.mTarget;
        FloatProperty floatProperty = this.mProperty;
        iAnimTarget.setValue(floatProperty, iAnimTarget.shouldUseIntValue(floatProperty) ? (float) ((int) d2) : (float) d2);
    }

    private void setEase(EaseManager.EaseStyle easeStyle) {
        if (LogUtils.isLogEnabled()) {
            String obj = toString();
            LogUtils.debug(obj, ".setEase", this.mProperty.getName(), "ease = " + easeStyle);
        }
        if (EaseManager.isPhysicsStyle(easeStyle.style)) {
            this.mPhyOperator = createPhyOperator(easeStyle);
            if (this.mEquilibrium == null) {
                this.mEquilibrium = new EquilibriumChecker(this.mTarget, this.mProperty);
            }
        } else if (easeStyle instanceof EaseManager.InterpolateEaseStyle) {
            EaseManager.InterpolateEaseStyle interpolateEaseStyle = (EaseManager.InterpolateEaseStyle) easeStyle;
            this.mInterpolator = EaseManager.getInterpolator(interpolateEaseStyle);
            this.mDuration = interpolateEaseStyle.duration;
        }
    }

    private void setEquilibriumChecker() {
        EquilibriumChecker equilibriumChecker = this.mEquilibrium;
        if (equilibriumChecker != null) {
            equilibriumChecker.setTargetValue(getTargetDoubleValue());
        }
    }

    private void setFinishVV() {
        this.mTarget.setVelocity(this.mProperty, 0.0d);
        if (this.mPhyOperator instanceof SpringOperator) {
            setAnimValue(getTargetDoubleValue());
        } else {
            setAnimValue(this.mVV[0]);
        }
    }

    private void updateInterpolatorAnim() {
        this.mIsRunning = this.mTotalTime < this.mDuration;
        float regulateProgress = regulateProgress(!this.mIsRunning ? 1.0f : this.mInterpolator.getInterpolation(((float) this.mTotalTime) / ((float) this.mDuration)));
        if (this.mProperty instanceof IIntValueProperty) {
            this.mTarget.setIntValue((IIntValueProperty) this.mProperty, (int) toAnimValue((double) ((Integer) getEvaluator().evaluate(regulateProgress, Integer.valueOf(this.mIntValues[0]), Integer.valueOf(this.mIntValues[1]))).intValue()));
        } else {
            this.mTarget.setValue(this.mProperty, (float) toAnimValue((double) ((Float) getEvaluator().evaluate(regulateProgress, Float.valueOf(this.mValues[0]), Float.valueOf(this.mValues[1]))).floatValue()));
        }
    }

    private void updatePhysicsAnim(long j) {
        this.mFrameCount++;
        updateVV();
        float f = ((float) j) / 1000.0f;
        double targetDoubleValue = getTargetDoubleValue();
        doPhysicsCalculation(this.mVV, f, targetDoubleValue);
        double[] dArr = this.mVV;
        this.mIsRunning = isAnimRunning(dArr[0], dArr[1]);
        if (this.mIsRunning) {
            this.mTarget.setVelocity(this.mProperty, this.mVV[1]);
            setAnimValue(this.mVV[0]);
            applyDelayedAnimConfig(f, targetDoubleValue);
            return;
        }
        doFinishProcess();
    }

    private void updateVV() {
        if (this.mPhyOperator != null) {
            double intValue = this.mUseIntValue ? (double) this.mTarget.getIntValue((IIntValueProperty) this.mProperty) : (double) this.mTarget.getValue(this.mProperty);
            if (!(this.mUseIntValue || this.mTarget.shouldUseIntValue(this.mProperty)) || Math.abs(this.mVV[0] - intValue) > 1.0d) {
                this.mVV[0] = intValue;
            }
            this.mVV[1] = this.mTarget.getVelocity(this.mProperty);
        }
    }

    public void cancel() {
        if (this.mIsRunning) {
            this.mIsRunning = false;
            onEnd();
        }
    }

    public void clear() {
        this.mIsRunning = false;
        Arrays.fill(this.mVV, 0.0d);
        this.mConfig = null;
        this.mValues = null;
        this.mIntValues = null;
        this.mInterpolator = null;
        this.mPhyOperator = null;
        this.mEquilibrium = null;
        this.mTotalTime = 0;
    }

    /* access modifiers changed from: protected */
    public void doSetConfig(AnimConfigLink animConfigLink) {
    }

    public void end() {
        if (this.mProperty instanceof IIntValueProperty) {
            int targetIntValue = getTargetIntValue();
            if (targetIntValue != Integer.MAX_VALUE) {
                this.mTarget.setIntValue((IIntValueProperty) this.mProperty, targetIntValue);
            }
        } else {
            float targetValue = getTargetValue();
            if (targetValue != Float.MAX_VALUE) {
                this.mTarget.setValue(this.mProperty, targetValue);
            }
        }
        cancel();
    }

    public int getCurrentIntValue() {
        FloatProperty floatProperty = this.mProperty;
        if (floatProperty instanceof IIntValueProperty) {
            return this.mTarget.getIntValue((IIntValueProperty) floatProperty);
        }
        return Integer.MAX_VALUE;
    }

    public float getCurrentValue() {
        return this.mTarget.getValue(this.mProperty);
    }

    /* access modifiers changed from: protected */
    public TypeEvaluator getEvaluator() {
        return this.mProperty instanceof IIntValueProperty ? new IntEvaluator() : new FloatEvaluator();
    }

    public long getRunningTime() {
        return this.mTotalTime;
    }

    public IAnimTarget getTarget() {
        return this.mTarget;
    }

    public int getTargetIntValue() {
        int[] iArr = this.mIntValues;
        if (iArr == null || iArr.length == 0) {
            return Integer.MAX_VALUE;
        }
        return iArr.length > 1 ? iArr[1] : iArr[0];
    }

    public float getTargetValue() {
        float[] fArr = this.mValues;
        if (fArr == null || fArr.length == 0) {
            return Float.MAX_VALUE;
        }
        return fArr.length > 1 ? fArr[1] : fArr[0];
    }

    /* access modifiers changed from: protected */
    public boolean isAnimRunning(double d2, double d3) {
        return !this.mEquilibrium.isAtEquilibrium(d2, d3);
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    /* access modifiers changed from: protected */
    public void onEnd() {
    }

    /* access modifiers changed from: protected */
    public void onStart() {
    }

    /* access modifiers changed from: protected */
    public void onUpdate() {
    }

    /* access modifiers changed from: protected */
    public double processCurrentValue(double d2) {
        return d2;
    }

    /* access modifiers changed from: protected */
    public double processTargetValue(double d2) {
        return d2;
    }

    /* access modifiers changed from: protected */
    public float regulateProgress(float f) {
        return f;
    }

    public void resetRunningTime() {
        this.mTotalTime = 0;
    }

    public final void setConfig(AnimConfigLink animConfigLink) {
        this.mConfig = animConfigLink;
        setEase(this.mConfig.getEase(this.mProperty));
        doSetConfig(animConfigLink);
    }

    public void setIntValues(int... iArr) {
        if (iArr.length == 1) {
            this.mIntValues = new int[]{getCurrentIntValue(), iArr[0]};
        } else {
            this.mIntValues = iArr;
        }
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("PropertyStyle.setIntValues, values = " + iArr, new Object[0]);
        }
        setEquilibriumChecker();
    }

    public void setTarget(IAnimTarget iAnimTarget) {
        this.mTarget = iAnimTarget;
    }

    public void setValues(float... fArr) {
        if (fArr.length == 1) {
            this.mValues = new float[]{getCurrentValue(), fArr[0]};
        } else {
            this.mValues = fArr;
        }
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("PropertyStyle.setValues, values = " + fArr, new Object[0]);
        }
        setEquilibriumChecker();
    }

    public void start() {
        if (this.mIsRunning) {
            return;
        }
        if (this.mInterpolator != null || this.mPhyOperator != null) {
            this.mIsRunning = true;
            this.mTotalTime = 0;
            onStart();
            setEquilibriumChecker();
        }
    }

    /* access modifiers changed from: protected */
    public double toAnimValue(double d2) {
        return d2;
    }

    public void update(long j) {
        if (this.mIsRunning) {
            this.mTotalTime += j;
            if (this.mPhyOperator != null) {
                updatePhysicsAnim(j);
            } else if (this.mInterpolator != null) {
                updateInterpolatorAnim();
            }
            onUpdate();
            if (!this.mIsRunning) {
                onEnd();
            }
        }
    }
}
