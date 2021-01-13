package miuix.animation.listener;

import java.util.Collection;
import miuix.animation.internal.AnimRunner;
import miuix.animation.property.FloatProperty;
import miuix.animation.styles.PropertyStyle;

public class UpdateInfo {
    public PropertyStyle anim;
    public long endTime;
    public boolean isCanceled;
    public boolean isCompleted;
    public boolean isEndByUser;
    private Number mValue;
    public FloatProperty property;
    public long transId;
    public float velocity;

    public static UpdateInfo findBy(Collection<UpdateInfo> collection, FloatProperty floatProperty) {
        for (UpdateInfo updateInfo : collection) {
            if (updateInfo.property.equals(floatProperty)) {
                return updateInfo;
            }
        }
        return null;
    }

    public static UpdateInfo findByName(Collection<UpdateInfo> collection, String str) {
        for (UpdateInfo updateInfo : collection) {
            if (updateInfo.property.getName().equals(str)) {
                return updateInfo;
            }
        }
        return null;
    }

    public float getFloatValue() {
        return this.mValue.floatValue();
    }

    public int getIntValue() {
        return this.mValue.intValue();
    }

    public Class<?> getType() {
        return this.mValue.getClass();
    }

    public <T> T getValue(Class<T> cls) {
        return (cls == Float.class || cls == Float.TYPE) ? (T) Float.valueOf(this.mValue.floatValue()) : (cls == Double.class || cls == Double.TYPE) ? (T) Double.valueOf(this.mValue.doubleValue()) : (T) Integer.valueOf(this.mValue.intValue());
    }

    public void reset() {
        this.isEndByUser = false;
        this.isCompleted = false;
        this.isCanceled = false;
    }

    public void setComplete(boolean z) {
        this.isCompleted = z;
        if (this.isCompleted) {
            this.endTime = AnimRunner.getInst().getRunningTime();
        }
    }

    public <T extends Number> void setValue(T t) {
        this.mValue = t;
    }

    public String toString() {
        return "UpdateInfo{property=" + this.property + ", mValue=" + this.mValue + ", velocity=" + this.velocity + ", isCompleted=" + this.isCompleted + '}';
    }
}
