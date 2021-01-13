package com.miui.internal.view;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class SeekBarGradientDrawable extends GradientDrawable {
    protected static final float HIGH_STIFFNESS = 986.96f;
    private int mHeight;
    protected SeekBarGradientState mSeekBarGradientState;
    private int mWidth;

    /* access modifiers changed from: protected */
    public static class SeekBarGradientState extends Drawable.ConstantState {
        Drawable.ConstantState mParent;

        protected SeekBarGradientState() {
        }

        public boolean canApplyTheme() {
            return this.mParent.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.mParent.getChangingConfigurations();
        }

        public Drawable newDrawable() {
            if (this.mParent == null) {
                return null;
            }
            return newSeekBarGradientDrawable(null, null, this);
        }

        public Drawable newDrawable(Resources resources) {
            if (this.mParent == null) {
                return null;
            }
            return newSeekBarGradientDrawable(resources, null, this);
        }

        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            if (this.mParent == null) {
                return null;
            }
            return newSeekBarGradientDrawable(resources, theme, this);
        }

        /* access modifiers changed from: protected */
        public Drawable newSeekBarGradientDrawable(Resources resources, Resources.Theme theme, SeekBarGradientState seekBarGradientState) {
            return new SeekBarGradientDrawable(resources, theme, seekBarGradientState);
        }

        public void setConstantState(Drawable.ConstantState constantState) {
            this.mParent = constantState;
        }
    }

    public SeekBarGradientDrawable() {
        this.mWidth = 0;
        this.mHeight = 0;
        this.mSeekBarGradientState = newSeekBarGradientState();
        this.mSeekBarGradientState.setConstantState(super.getConstantState());
    }

    public SeekBarGradientDrawable(Resources resources, Resources.Theme theme, SeekBarGradientState seekBarGradientState) {
        this.mWidth = 0;
        this.mHeight = 0;
        Drawable newDrawable = resources == null ? seekBarGradientState.mParent.newDrawable() : theme == null ? seekBarGradientState.mParent.newDrawable(resources) : seekBarGradientState.mParent.newDrawable(resources, theme);
        seekBarGradientState.mParent = newDrawable.getConstantState();
        this.mSeekBarGradientState = newSeekBarGradientState();
        this.mSeekBarGradientState.setConstantState(seekBarGradientState.mParent);
        this.mWidth = newDrawable.getIntrinsicWidth();
        this.mHeight = newDrawable.getIntrinsicHeight();
        if (newDrawable instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) newDrawable;
            setCornerRadius(gradientDrawable.getCornerRadius());
            setShape(gradientDrawable.getShape());
            setColor(gradientDrawable.getColor());
        }
    }

    public Drawable.ConstantState getConstantState() {
        return this.mSeekBarGradientState;
    }

    public int getIntrinsicHeight() {
        return this.mHeight;
    }

    public int getIntrinsicWidth() {
        return this.mWidth;
    }

    public boolean isStateful() {
        return true;
    }

    /* access modifiers changed from: protected */
    public SeekBarGradientState newSeekBarGradientState() {
        return new SeekBarGradientState();
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        boolean onStateChange = super.onStateChange(iArr);
        boolean z = false;
        for (int i : iArr) {
            if (i == 16842919) {
                z = true;
            }
        }
        if (z) {
            startPressedAnim();
        }
        if (!z) {
            startUnPressedAnim();
        }
        return onStateChange;
    }

    /* access modifiers changed from: protected */
    public void startPressedAnim() {
    }

    /* access modifiers changed from: protected */
    public void startUnPressedAnim() {
    }
}
