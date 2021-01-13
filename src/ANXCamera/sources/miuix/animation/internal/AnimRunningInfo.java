package miuix.animation.internal;

import miuix.animation.IAnimTarget;
import miuix.animation.base.AnimConfigLink;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.styles.PropertyStyle;
import miuix.animation.styles.StyleFactory;
import miuix.animation.utils.EaseManager;
import miuix.animation.utils.LogUtils;

public class AnimRunningInfo {
    static final int STATUS_BEGIN = 0;
    private static final int STATUS_PENDING = -1;
    static final int STATUS_RUNNING = 1;
    static final int STATUS_RUN_TO_MINIMUM = 2;
    static final int STATUS_STOP = 3;
    public PropertyStyle anim;
    public AnimConfigLink config;
    public EaseManager.EaseStyle ease;
    public long flags;
    private Number fromValue;
    long initTime;
    private UpdateInfo mUpdate = new UpdateInfo();
    AnimRunningInfo pending;
    public FloatProperty property;
    private long startTime = -1;
    public int status = 0;
    public IAnimTarget target;
    private long toFlags;
    public Object toTag;
    Number toValue;
    long transId;

    private void setFromValue() {
        Number number = this.fromValue;
        if (number != null) {
            FloatProperty floatProperty = this.property;
            if (floatProperty instanceof IIntValueProperty) {
                this.target.setIntValue((IIntValueProperty) floatProperty, number.intValue());
            } else {
                this.target.setValue(floatProperty, number.floatValue());
            }
        }
    }

    private void setStartTime(long j) {
        this.startTime = j;
        PropertyStyle propertyStyle = this.anim;
        if (propertyStyle != null) {
            propertyStyle.resetRunningTime();
        }
    }

    private boolean setValue(IAnimTarget iAnimTarget, PropertyStyle propertyStyle) {
        return this.property instanceof IIntValueProperty ? AnimValueUtils.setIntValues(iAnimTarget, propertyStyle, this, this.toValue, this.toFlags) : AnimValueUtils.setFloatValues(iAnimTarget, propertyStyle, this, this.toValue, this.toFlags);
    }

    /* access modifiers changed from: package-private */
    public void begin(IAnimTarget iAnimTarget, long j) {
        this.status = 1;
        this.mUpdate.reset();
        if (this.anim == null) {
            this.anim = StyleFactory.createAnimStyle(iAnimTarget, this.toTag, this.property, this.config);
        }
        setStartTime(j);
        setFromValue();
        float fromSpeed = this.config.getFromSpeed(this.toTag, this.property);
        if (fromSpeed != Float.MAX_VALUE) {
            if (LogUtils.isLogEnabled()) {
                LogUtils.debug("AnimRunningInfo, begin " + this.property.getName(), "fromSpeed = " + fromSpeed);
            }
            iAnimTarget.setVelocity(this.property, (double) fromSpeed);
        }
        if (!setValue(iAnimTarget, this.anim)) {
            stop(true);
        } else if (!this.anim.isRunning()) {
            if (LogUtils.isLogEnabled()) {
                LogUtils.debug("AnimRunningInfo, begin " + this.property.getName(), "toTag = " + this.toTag, "target object = " + iAnimTarget.getTargetObject(), "begin velocity = " + iAnimTarget.getVelocity(this.property));
            }
            this.anim.start();
        }
    }

    /* access modifiers changed from: package-private */
    public long getRunningTime() {
        PropertyStyle propertyStyle = this.anim;
        if (propertyStyle == null) {
            return 0;
        }
        return propertyStyle.getRunningTime();
    }

    /* access modifiers changed from: package-private */
    public boolean isAnimEnd(long j) {
        PropertyStyle propertyStyle;
        return this.startTime < j && ((propertyStyle = this.anim) == null || !propertyStyle.isRunning());
    }

    /* access modifiers changed from: package-private */
    public boolean isFinished() {
        return this.status == 3;
    }

    /* access modifiers changed from: package-private */
    public boolean isPhysicsWithVelocity() {
        return EaseManager.isPhysicsStyle(this.ease.style) && this.target.getVelocity(this.property) != 0.0d;
    }

    /* access modifiers changed from: package-private */
    public boolean isRunning() {
        int i;
        return this.anim != null && ((i = this.status) == 1 || i == 2);
    }

    /* access modifiers changed from: package-private */
    public void run(long j) {
        if (isRunning()) {
            this.anim.update(j);
        }
    }

    /* access modifiers changed from: package-private */
    public void setConfig(AnimConfigLink animConfigLink) {
        this.config = animConfigLink;
        this.ease = animConfigLink.getEase(this.property);
        this.flags = animConfigLink.getFlags(this.toTag, this.property);
    }

    /* access modifiers changed from: package-private */
    public void setFromInfo(TransitionInfo transitionInfo) {
        this.fromValue = transitionInfo.fromPropValues.get(this.property);
    }

    /* access modifiers changed from: package-private */
    public void setPending(AnimRunningInfo animRunningInfo) {
        this.status = 2;
        this.pending = animRunningInfo;
        animRunningInfo.status = -1;
    }

    /* access modifiers changed from: package-private */
    public void setToInfo(TransitionInfo transitionInfo) {
        this.toValue = transitionInfo.toPropValues.get(this.property);
        this.toTag = transitionInfo.toTag;
        Long l = transitionInfo.toFlags.get(this.property);
        if (l != null) {
            this.toFlags = l.longValue();
        }
    }

    /* access modifiers changed from: package-private */
    public void stop() {
        stop(false);
    }

    /* access modifiers changed from: package-private */
    public void stop(boolean z) {
        if (isRunning()) {
            if (LogUtils.isLogEnabled()) {
                String str = "AnimRunningInfo, stop " + this.property.getName();
                Object[] objArr = new Object[3];
                objArr[0] = "toTag = " + this.toTag;
                objArr[1] = "property = " + this.property.getName();
                StringBuilder sb = new StringBuilder();
                sb.append("anim.getCurrentValue = ");
                sb.append(this.property instanceof IIntValueProperty ? (float) this.anim.getCurrentIntValue() : this.anim.getCurrentValue());
                objArr[2] = sb.toString();
                LogUtils.debug(str, objArr);
            }
            this.status = 3;
            if (z) {
                this.anim.end();
            } else {
                this.mUpdate.isCanceled = true;
                this.anim.cancel();
            }
            AnimRunningInfo animRunningInfo = this.pending;
            if (animRunningInfo != null) {
                animRunningInfo.status = 0;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void update(TransitionInfo transitionInfo, long j) {
        boolean isPhysicsStyle = EaseManager.isPhysicsStyle(this.ease.style);
        this.ease = transitionInfo.config.getEase(this.property);
        boolean isPhysicsStyle2 = EaseManager.isPhysicsStyle(this.ease.style);
        setToInfo(transitionInfo);
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("update anim for " + this.property.getName(), "to = " + this.toTag, "value " + this.toValue, "newEase = " + this.ease);
        }
        if (this.anim == null || isPhysicsStyle != isPhysicsStyle2 || !isPhysicsStyle2) {
            if (LogUtils.isLogEnabled()) {
                LogUtils.debug("update anim, clear old and begin new", new Object[0]);
            }
            PropertyStyle propertyStyle = this.anim;
            if (propertyStyle != null) {
                propertyStyle.clear();
                this.fromValue = null;
                this.anim.setConfig(transitionInfo.config);
            } else {
                this.anim = StyleFactory.createAnimStyle(this.target, this.toTag, this.property, transitionInfo.config);
            }
            begin(this.target, j);
            return;
        }
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("update anim values", new Object[0]);
        }
        this.anim.setConfig(transitionInfo.config);
        setValue(this.target, this.anim);
    }

    /* access modifiers changed from: package-private */
    public UpdateInfo updateToDate() {
        UpdateInfo updateInfo = this.mUpdate;
        updateInfo.transId = this.transId;
        FloatProperty floatProperty = this.property;
        if (floatProperty instanceof IIntValueProperty) {
            updateInfo.setValue(Integer.valueOf(this.target.getIntValue((IIntValueProperty) floatProperty)));
        } else {
            updateInfo.setValue(Float.valueOf(this.target.getValue(floatProperty)));
        }
        this.mUpdate.property = this.property;
        if (EaseManager.isPhysicsStyle(this.ease.style)) {
            this.mUpdate.velocity = (float) this.target.getVelocity(this.property);
        } else {
            this.mUpdate.velocity = 0.0f;
        }
        UpdateInfo updateInfo2 = this.mUpdate;
        updateInfo2.anim = this.anim;
        updateInfo2.setComplete(isFinished());
        return this.mUpdate;
    }
}
