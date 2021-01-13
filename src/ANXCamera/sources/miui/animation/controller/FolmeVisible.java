package miui.animation.controller;

import miui.animation.IAnimTarget;
import miui.animation.IVisibleStyle;
import miui.animation.base.AnimConfig;
import miui.animation.property.FloatProperty;
import miui.animation.utils.CommonUtils;
import miui.animation.utils.EaseManager;

public class FolmeVisible extends FolmeBase implements IVisibleStyle {
    private AnimConfig mDefConfig = new AnimConfig();
    private boolean mHasMove;
    private boolean mHasScale;

    public FolmeVisible(IAnimTarget... iAnimTargetArr) {
        super(iAnimTargetArr);
        useAutoAlpha(true);
    }

    private AnimConfig[] getConfig(IVisibleStyle.VisibleType visibleType, AnimConfig... animConfigArr) {
        if (!this.mHasScale && !this.mHasMove) {
            this.mDefConfig.ease = visibleType == IVisibleStyle.VisibleType.SHOW ? EaseManager.getStyle(16, 300.0f) : EaseManager.getStyle(-2, 1.0f, 0.15f);
        } else if (this.mHasScale && !this.mHasMove) {
            this.mDefConfig.ease = visibleType == IVisibleStyle.VisibleType.SHOW ? EaseManager.getStyle(-2, 0.6f, 0.35f) : EaseManager.getStyle(-2, 0.75f, 0.2f);
        } else if (!this.mHasScale) {
            this.mDefConfig.ease = visibleType == IVisibleStyle.VisibleType.SHOW ? EaseManager.getStyle(-2, 0.75f, 0.35f) : EaseManager.getStyle(-2, 0.75f, 0.25f);
        } else {
            this.mDefConfig.ease = visibleType == IVisibleStyle.VisibleType.SHOW ? EaseManager.getStyle(-2, 0.65f, 0.35f) : EaseManager.getStyle(-2, 0.75f, 0.25f);
        }
        return (AnimConfig[]) CommonUtils.mergeArray(animConfigArr, this.mDefConfig);
    }

    private IVisibleStyle.VisibleType getType(IVisibleStyle.VisibleType... visibleTypeArr) {
        return visibleTypeArr.length > 0 ? visibleTypeArr[0] : IVisibleStyle.VisibleType.HIDE;
    }

    @Override // miui.animation.controller.FolmeBase, miui.animation.IStateContainer
    public void clean() {
        super.clean();
        this.mHasScale = false;
        this.mHasMove = false;
    }

    @Override // miui.animation.IVisibleStyle
    public void hide(AnimConfig... animConfigArr) {
        alignState(IVisibleStyle.VisibleType.SHOW, IVisibleStyle.VisibleType.HIDE);
        IFolmeStateStyle iFolmeStateStyle = this.mState;
        IVisibleStyle.VisibleType visibleType = IVisibleStyle.VisibleType.HIDE;
        iFolmeStateStyle.to(visibleType, getConfig(visibleType, animConfigArr));
    }

    @Override // miui.animation.IVisibleStyle
    public IVisibleStyle setAlpha(float f, IVisibleStyle.VisibleType... visibleTypeArr) {
        this.mState.getState(getType(visibleTypeArr)).add(getProperty(14), f, new long[0]);
        return this;
    }

    @Override // miui.animation.IVisibleStyle
    public IVisibleStyle setBound(int i, int i2, int i3, int i4) {
        this.mState.getState(IVisibleStyle.VisibleType.SHOW).add(getProperty(0), i, new long[0]).add(getProperty(1), i2, new long[0]).add(getProperty(6), i3, new long[0]).add(getProperty(5), i4, new long[0]);
        return this;
    }

    @Override // miui.animation.IVisibleStyle
    public IVisibleStyle setHide() {
        alignState(IVisibleStyle.VisibleType.SHOW, IVisibleStyle.VisibleType.HIDE);
        this.mState.setTo(IVisibleStyle.VisibleType.HIDE);
        return this;
    }

    @Override // miui.animation.IVisibleStyle
    public IVisibleStyle setMove(int i, int i2) {
        return setMove(i, i2, IVisibleStyle.VisibleType.HIDE);
    }

    @Override // miui.animation.IVisibleStyle
    public IVisibleStyle setMove(int i, int i2, IVisibleStyle.VisibleType... visibleTypeArr) {
        this.mHasMove = Math.abs(i) > 0 || Math.abs(i2) > 0;
        if (this.mHasMove) {
            this.mState.getState(getType(visibleTypeArr)).add(getProperty(0), i, 1).add(getProperty(1), i2, 1);
        }
        return this;
    }

    @Override // miui.animation.IVisibleStyle
    public IVisibleStyle setScale(float f, IVisibleStyle.VisibleType... visibleTypeArr) {
        this.mHasScale = true;
        this.mState.getState(getType(visibleTypeArr)).add(getProperty(3), f, new long[0]).add(getProperty(2), f, new long[0]);
        return this;
    }

    @Override // miui.animation.IVisibleStyle
    public IVisibleStyle setShow() {
        alignState(IVisibleStyle.VisibleType.HIDE, IVisibleStyle.VisibleType.SHOW);
        this.mState.setTo(IVisibleStyle.VisibleType.SHOW);
        return this;
    }

    @Override // miui.animation.IVisibleStyle
    public IVisibleStyle setShowDelay(long j) {
        this.mState.getState(IVisibleStyle.VisibleType.SHOW).getGlobalConfig().delay = j;
        return this;
    }

    @Override // miui.animation.IVisibleStyle
    public void show(AnimConfig... animConfigArr) {
        alignState(IVisibleStyle.VisibleType.HIDE, IVisibleStyle.VisibleType.SHOW);
        IFolmeStateStyle iFolmeStateStyle = this.mState;
        IVisibleStyle.VisibleType visibleType = IVisibleStyle.VisibleType.SHOW;
        iFolmeStateStyle.to(visibleType, getConfig(visibleType, animConfigArr));
    }

    @Override // miui.animation.IVisibleStyle
    public IVisibleStyle useAutoAlpha(boolean z) {
        FloatProperty property = getProperty(14);
        FloatProperty property2 = getProperty(4);
        if (z) {
            this.mState.getState(IVisibleStyle.VisibleType.SHOW).remove(property2).add(property, 1.0f, new long[0]);
            this.mState.getState(IVisibleStyle.VisibleType.HIDE).remove(property2).add(property, 0.0f, new long[0]);
        } else {
            this.mState.getState(IVisibleStyle.VisibleType.SHOW).remove(property).add(property2, 1.0f, new long[0]);
            this.mState.getState(IVisibleStyle.VisibleType.HIDE).remove(property).add(property2, 0.0f, new long[0]);
        }
        return this;
    }
}
