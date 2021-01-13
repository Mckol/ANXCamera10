package miui.animation.controller;

import android.widget.TextView;
import miui.animation.Folme;
import miui.animation.IAnimTarget;
import miui.animation.IVarFontStyle;
import miui.animation.ViewTarget;
import miui.animation.base.AnimConfig;
import miui.animation.font.FontWeightProperty;
import miui.animation.property.ViewProperty;
import miui.animation.utils.CommonUtils;
import miui.animation.utils.EaseManager;

public class FolmeFont extends FolmeBase implements IVarFontStyle {
    private AnimConfig mDefaultTo = new AnimConfig();
    private int mInitValue;
    private boolean mIsInitSet;
    private FontWeightProperty mProperty;

    public enum FontType {
        INIT,
        TARGET
    }

    public FolmeFont() {
        super(new IAnimTarget[0]);
        this.mDefaultTo.ease = EaseManager.getStyle(0, 350.0f, 0.9f, 0.86f);
    }

    @Override // miui.animation.controller.FolmeBase, miui.animation.IStateContainer
    public void clean() {
        super.clean();
        this.mState = null;
        this.mProperty = null;
        this.mInitValue = 0;
    }

    @Override // miui.animation.IVarFontStyle
    public IVarFontStyle fromTo(int i, int i2, AnimConfig... animConfigArr) {
        IFolmeStateStyle iFolmeStateStyle = this.mState;
        if (iFolmeStateStyle != null) {
            iFolmeStateStyle.getState(FontType.INIT).add((ViewProperty) this.mProperty, i, new long[0]);
            this.mState.getState(FontType.TARGET).add((ViewProperty) this.mProperty, i2, new long[0]);
            this.mState.fromTo(FontType.INIT, FontType.TARGET, animConfigArr);
        }
        return this;
    }

    @Override // miui.animation.IVarFontStyle
    public IVarFontStyle setTo(int i) {
        IFolmeStateStyle iFolmeStateStyle = this.mState;
        if (iFolmeStateStyle != null) {
            iFolmeStateStyle.getState(FontType.TARGET).add((ViewProperty) this.mProperty, i, new long[0]);
            this.mState.setTo(FontType.TARGET);
        }
        return this;
    }

    @Override // miui.animation.IVarFontStyle
    public IVarFontStyle to(int i, AnimConfig... animConfigArr) {
        IFolmeStateStyle iFolmeStateStyle = this.mState;
        if (iFolmeStateStyle != null) {
            if (!this.mIsInitSet) {
                this.mIsInitSet = true;
                iFolmeStateStyle.setTo(FontType.INIT);
            }
            AnimConfig[] animConfigArr2 = (AnimConfig[]) CommonUtils.mergeArray(animConfigArr, this.mDefaultTo);
            if (this.mInitValue == i) {
                this.mState.to(FontType.INIT, animConfigArr2);
            } else {
                this.mState.getState(FontType.TARGET).add((ViewProperty) this.mProperty, i, new long[0]);
                this.mState.to(FontType.TARGET, animConfigArr2);
            }
        }
        return this;
    }

    @Override // miui.animation.IVarFontStyle
    public IVarFontStyle useAt(TextView textView, int i, int i2) {
        this.mState = new FolmeState(Folme.getTarget(textView, ViewTarget.sCreator));
        this.mProperty = new FontWeightProperty(textView, i);
        this.mInitValue = i2;
        this.mState.getState(FontType.INIT).add((ViewProperty) this.mProperty, i2, new long[0]);
        this.mIsInitSet = false;
        return this;
    }
}
