package miui.animation;

public interface ITargetCreator<T> {
    IAnimTarget createTarget(T t);
}
