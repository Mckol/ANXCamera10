package com.miui.internal.view;

import android.content.res.Resources;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;

public class CheckWidgetAnimatedStateListDrawable extends AnimatedStateListDrawable {
    protected CheckWidgetConstantState mCheckWidgetConstantState;

    /* access modifiers changed from: protected */
    public static class CheckWidgetConstantState extends Drawable.ConstantState {
        int backGroundColor;
        int backgroundDisableAlpha;
        int backgroundNormalAlpha;
        int blackColor;
        int grayColor;
        Drawable.ConstantState mParent;
        int strokeColor;
        int strokeDisableAlpha;
        int strokeNormalAlpha;
        boolean touchAnimEnable;

        protected CheckWidgetConstantState() {
        }

        public boolean canApplyTheme() {
            return this.mParent.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.mParent.getChangingConfigurations();
        }

        /* access modifiers changed from: protected */
        public Drawable newAnimatedStateListDrawable(Resources resources, Resources.Theme theme, CheckWidgetConstantState checkWidgetConstantState) {
            return new CheckWidgetAnimatedStateListDrawable(resources, theme, checkWidgetConstantState);
        }

        public Drawable newDrawable() {
            if (this.mParent == null) {
                return null;
            }
            return newAnimatedStateListDrawable(null, null, this);
        }

        public Drawable newDrawable(Resources resources) {
            if (this.mParent == null) {
                return null;
            }
            return newAnimatedStateListDrawable(resources, null, this);
        }

        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            if (this.mParent == null) {
                return null;
            }
            return newAnimatedStateListDrawable(resources, theme, this);
        }
    }

    public CheckWidgetAnimatedStateListDrawable() {
        this.mCheckWidgetConstantState = newCheckWidgetConstantState();
    }

    protected CheckWidgetAnimatedStateListDrawable(Resources resources, Resources.Theme theme, CheckWidgetConstantState checkWidgetConstantState) {
        checkWidgetConstantState.mParent = (resources == null ? checkWidgetConstantState.mParent.newDrawable() : theme == null ? checkWidgetConstantState.mParent.newDrawable(resources) : checkWidgetConstantState.mParent.newDrawable(resources, theme)).getConstantState();
        setConstantState((DrawableContainer.DrawableContainerState) checkWidgetConstantState.mParent);
        onStateChange(getState());
        jumpToCurrentState();
        CheckWidgetConstantState checkWidgetConstantState2 = this.mCheckWidgetConstantState;
        checkWidgetConstantState2.grayColor = checkWidgetConstantState.grayColor;
        checkWidgetConstantState2.blackColor = checkWidgetConstantState.blackColor;
        checkWidgetConstantState2.backGroundColor = checkWidgetConstantState.backGroundColor;
        checkWidgetConstantState2.touchAnimEnable = checkWidgetConstantState.touchAnimEnable;
    }

    public boolean canApplyTheme() {
        return true;
    }

    public Drawable.ConstantState getConstantState() {
        return this.mCheckWidgetConstantState;
    }

    /* access modifiers changed from: protected */
    public CheckWidgetConstantState newCheckWidgetConstantState() {
        return new CheckWidgetConstantState();
    }

    /* access modifiers changed from: protected */
    public void setConstantState(DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (this.mCheckWidgetConstantState == null) {
            this.mCheckWidgetConstantState = newCheckWidgetConstantState();
        }
        this.mCheckWidgetConstantState.mParent = drawableContainerState;
    }
}
