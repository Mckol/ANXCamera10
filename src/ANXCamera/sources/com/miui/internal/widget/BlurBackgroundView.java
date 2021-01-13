package com.miui.internal.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.miui.blur.drawable.BlurDrawable;
import miui.R;

public class BlurBackgroundView extends FrameLayout {
    private BlurDrawable mBlurBackground;
    private Drawable mBlurForeground;

    public BlurBackgroundView(Context context) {
        this(context, null);
    }

    public BlurBackgroundView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void createBlurBackground() {
        this.mBlurBackground = new BlurDrawable();
        if ((getResources().getConfiguration().uiMode & 48) == 32) {
            this.mBlurBackground.clearMixColor();
            this.mBlurBackground.addMixColor(19, Color.argb(165, 92, 92, 92));
            this.mBlurForeground = new ColorDrawable(getResources().getColor(R.color.action_bar_blurview_fg_dark));
        } else {
            this.mBlurBackground.clearMixColor();
            this.mBlurBackground.addMixColor(18, Color.argb(165, 107, 107, 107));
            this.mBlurForeground = new ColorDrawable(getResources().getColor(R.color.action_bar_blurview_fg_light));
        }
        this.mBlurBackground.setBlurRatio(1.0f);
    }

    public boolean isSupportBlur() {
        if (this.mBlurBackground == null) {
            createBlurBackground();
        }
        return this.mBlurBackground.isSupportBlur();
    }

    public void setAlpha(float f) {
        super.setAlpha(f);
        int i = (int) (f * 255.0f);
        Drawable drawable = this.mBlurForeground;
        if (drawable != null) {
            drawable.setAlpha(i);
        }
        BlurDrawable blurDrawable = this.mBlurBackground;
        if (blurDrawable != null) {
            blurDrawable.setAlpha(i);
        }
    }

    public boolean setBlurBackground(boolean z) {
        if (!isSupportBlur()) {
            return false;
        }
        if (z) {
            if (this.mBlurBackground == null) {
                createBlurBackground();
            }
            if (getVisibility() == 0 && getBackground() != null && getForeground() != null) {
                return true;
            }
            setVisibility(0);
            setForeground(this.mBlurForeground);
            setBackground(this.mBlurBackground);
            setAlpha(1.0f);
            return true;
        } else if (getVisibility() != 0) {
            return true;
        } else {
            setForeground(null);
            setBackground(null);
            this.mBlurForeground = null;
            this.mBlurBackground = null;
            setVisibility(8);
            return true;
        }
    }
}
