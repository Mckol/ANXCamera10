package miui.animation.property;

public class IntValueProperty extends ValueProperty implements IIntValueProperty {
    public IntValueProperty(String str) {
        super(str);
    }

    @Override // miui.animation.property.IIntValueProperty
    public int getIntValue(Object obj) {
        Integer num;
        if (!(obj instanceof ValueTargetObject) || (num = (Integer) ((ValueTargetObject) obj).getPropertyValue(getName(), Integer.TYPE)) == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // miui.animation.property.IIntValueProperty
    public void setIntValue(Object obj, int i) {
        if (obj instanceof ValueTargetObject) {
            ((ValueTargetObject) obj).setPropertyValue(getName(), Integer.TYPE, Integer.valueOf(i));
        }
    }

    @Override // miui.animation.property.ValueProperty, miui.animation.property.FloatProperty
    public String toString() {
        return "IntValueProperty{name=" + getName() + '}';
    }
}
