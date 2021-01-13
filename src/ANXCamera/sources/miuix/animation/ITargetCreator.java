package miuix.animation;

public interface ITargetCreator<T> {
    IAnimTarget createTarget(T t);
}
