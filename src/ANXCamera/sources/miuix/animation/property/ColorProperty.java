package miuix.animation.property;

import java.util.Objects;

public class ColorProperty<T> extends FloatProperty<T> implements IIntValueProperty<T> {
    private int mColorValue;

    public ColorProperty(String str) {
        super(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ColorProperty.class != obj.getClass()) {
            return false;
        }
        return this.mPropertyName.equals(((ColorProperty) obj).mPropertyName);
    }

    @Override // miuix.animation.property.IIntValueProperty
    public int getIntValue(T t) {
        if (t instanceof ValueTargetObject) {
            this.mColorValue = ((Integer) t.getPropertyValue(getName(), Integer.TYPE)).intValue();
        }
        return this.mColorValue;
    }

    @Override // miuix.animation.property.FloatProperty
    public float getValue(T t) {
        return 0.0f;
    }

    public int hashCode() {
        return Objects.hash(this.mPropertyName);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v1, resolved type: T */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.animation.property.IIntValueProperty
    public void setIntValue(T t, int i) {
        this.mColorValue = i;
        if (t instanceof ValueTargetObject) {
            t.setPropertyValue(getName(), Integer.TYPE, Integer.valueOf(i));
        }
    }

    @Override // miuix.animation.property.FloatProperty
    public void setValue(T t, float f) {
    }
}
