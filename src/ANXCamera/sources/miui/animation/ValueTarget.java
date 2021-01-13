package miui.animation;

import android.os.Handler;
import android.os.Looper;
import miui.animation.property.ColorProperty;
import miui.animation.property.FloatProperty;
import miui.animation.property.IIntValueProperty;
import miui.animation.property.IntValueProperty;
import miui.animation.property.ValueProperty;
import miui.animation.property.ValueTargetObject;

public class ValueTarget extends IAnimTarget {
    private static final float DEFAULT_MIN_VALUE = 0.002f;
    static ITargetCreator sCreator = new ITargetCreator() {
        /* class miui.animation.ValueTarget.AnonymousClass1 */

        @Override // miui.animation.ITargetCreator
        public IAnimTarget createTarget(Object obj) {
            return new ValueTarget(obj);
        }
    };
    private Handler mHandler;
    private ValueTargetObject mTargetObj;

    public ValueTarget() {
        this(null);
    }

    private ValueTarget(Object obj) {
        this.mTargetObj = new ValueTargetObject(obj == null ? Integer.valueOf(getId()) : obj);
        Looper myLooper = Looper.myLooper();
        if (myLooper != Looper.getMainLooper()) {
            this.mHandler = new Handler(myLooper);
        }
    }

    @Override // miui.animation.IAnimTarget
    public float getDefaultMinVisible() {
        return 0.002f;
    }

    public int getIntValue(String str) {
        return getIntValue(new IntValueProperty(str));
    }

    @Override // miui.animation.IAnimTarget
    public int getIntValue(IIntValueProperty iIntValueProperty) {
        Integer num = (Integer) this.mTargetObj.getPropertyValue(iIntValueProperty.getName(), Integer.TYPE);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // miui.animation.IAnimTarget
    public float getMinVisibleChange(Object obj) {
        if (!(obj instanceof IIntValueProperty) || (obj instanceof ColorProperty)) {
            return super.getMinVisibleChange(obj);
        }
        return 1.0f;
    }

    @Override // miui.animation.IAnimTarget
    public FloatProperty getProperty(int i) {
        return null;
    }

    @Override // miui.animation.IAnimTarget
    public Object getTargetObject() {
        return this.mTargetObj;
    }

    @Override // miui.animation.IAnimTarget
    public int getType(FloatProperty floatProperty) {
        return -1;
    }

    public float getValue(String str) {
        return getValue(new ValueProperty(str));
    }

    @Override // miui.animation.IAnimTarget
    public float getValue(FloatProperty floatProperty) {
        Float f = (Float) this.mTargetObj.getPropertyValue(floatProperty.getName(), Float.TYPE);
        if (f == null) {
            return 0.0f;
        }
        return f.floatValue();
    }

    public double getVelocity(String str) {
        return getVelocity(new ValueProperty(str));
    }

    @Override // miui.animation.IAnimTarget
    public boolean isValid() {
        return this.mTargetObj.isValid();
    }

    @Override // miui.animation.IAnimTarget
    public void post(Runnable runnable) {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }

    public void setIntValue(String str, int i) {
        setIntValue(new IntValueProperty(str), i);
    }

    @Override // miui.animation.IAnimTarget
    public void setIntValue(IIntValueProperty iIntValueProperty, int i) {
        this.mTargetObj.setPropertyValue(iIntValueProperty.getName(), Integer.TYPE, Integer.valueOf(i));
    }

    public void setValue(String str, float f) {
        setValue(new ValueProperty(str), f);
    }

    @Override // miui.animation.IAnimTarget
    public void setValue(FloatProperty floatProperty, float f) {
        this.mTargetObj.setPropertyValue(floatProperty.getName(), Float.TYPE, Float.valueOf(f));
    }
}
