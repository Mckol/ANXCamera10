package miuix.animation.property;

public interface IIntValueProperty<T> {
    int getIntValue(T t);

    String getName();

    void setIntValue(T t, int i);
}
