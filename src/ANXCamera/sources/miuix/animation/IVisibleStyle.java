package miuix.animation;

import miuix.animation.base.AnimConfig;

public interface IVisibleStyle extends IStateContainer {

    public enum VisibleType {
        SHOW,
        HIDE
    }

    void hide(AnimConfig... animConfigArr);

    IVisibleStyle setAlpha(float f, VisibleType... visibleTypeArr);

    IVisibleStyle setBound(int i, int i2, int i3, int i4);

    IVisibleStyle setHide();

    IVisibleStyle setMove(int i, int i2);

    IVisibleStyle setMove(int i, int i2, VisibleType... visibleTypeArr);

    IVisibleStyle setScale(float f, VisibleType... visibleTypeArr);

    IVisibleStyle setShow();

    IVisibleStyle setShowDelay(long j);

    void show(AnimConfig... animConfigArr);

    IVisibleStyle useAutoAlpha(boolean z);
}
