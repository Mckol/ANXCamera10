package miuix.animation;

import miuix.animation.property.ColorProperty;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.property.IntValueProperty;
import miuix.animation.property.ValueProperty;
import miuix.animation.property.ValueTargetObject;

public class ValueTarget extends IAnimTarget {
    private static final float DEFAULT_MIN_VALUE = 0.002f;
    static ITargetCreator sCreator = new ITargetCreator() {
        /* class miuix.animation.ValueTarget.AnonymousClass1 */

        @Override // miuix.animation.ITargetCreator
        public IAnimTarget createTarget(Object obj) {
            return new ValueTarget(obj);
        }
    };
    private ValueTargetObject mTargetObj;

    public ValueTarget() {
        this(null);
    }

    private ValueTarget(Object obj) {
        this.mTargetObj = new ValueTargetObject(obj == null ? Integer.valueOf(getId()) : obj);
    }

    @Override // miuix.animation.IAnimTarget
    public float getDefaultMinVisible() {
        return 0.002f;
    }

    public int getIntValue(String str) {
        return getIntValue(new IntValueProperty(str));
    }

    @Override // miuix.animation.IAnimTarget
    public int getIntValue(IIntValueProperty iIntValueProperty) {
        Integer num = (Integer) this.mTargetObj.getPropertyValue(iIntValueProperty.getName(), Integer.TYPE);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // miuix.animation.IAnimTarget
    public float getMinVisibleChange(Object obj) {
        if (!(obj instanceof IIntValueProperty) || (obj instanceof ColorProperty)) {
            return super.getMinVisibleChange(obj);
        }
        return 1.0f;
    }

    @Override // miuix.animation.IAnimTarget
    public FloatProperty getProperty(int i) {
        return null;
    }

    @Override // miuix.animation.IAnimTarget
    public Object getTargetObject() {
        return this.mTargetObj;
    }

    @Override // miuix.animation.IAnimTarget
    public int getType(FloatProperty floatProperty) {
        return -1;
    }

    public float getValue(String str) {
        return getValue(new ValueProperty(str));
    }

    @Override // miuix.animation.IAnimTarget
    public float getValue(FloatProperty floatProperty) {
        Float f = (Float) this.mTargetObj.getPropertyValue(floatProperty.getName(), Float.TYPE);
        if (f == null) {
            return 0.0f;
        }
        return f.floatValue();
    }

    public double getVelocity(String str) {
        return getVelocity(str.hashCode());
    }

    @Override // miuix.animation.IAnimTarget
    public boolean isValid() {
        return this.mTargetObj.isValid();
    }

    public void setIntValue(String str, int i) {
        setIntValue(new IntValueProperty(str), i);
    }

    @Override // miuix.animation.IAnimTarget
    public void setIntValue(IIntValueProperty iIntValueProperty, int i) {
        this.mTargetObj.setPropertyValue(iIntValueProperty.getName(), Integer.TYPE, Integer.valueOf(i));
    }

    public void setValue(String str, float f) {
        setValue(new ValueProperty(str), f);
    }

    @Override // miuix.animation.IAnimTarget
    public void setValue(FloatProperty floatProperty, float f) {
        this.mTargetObj.setPropertyValue(floatProperty.getName(), Float.TYPE, Float.valueOf(f));
    }

    public void setVelocity(String str, double d2) {
        setVelocity(str.hashCode(), d2);
    }
}
