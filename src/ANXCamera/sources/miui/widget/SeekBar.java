package miui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import com.miui.internal.R;
import com.miui.internal.widget.ProgressBarDelegate;
import java.lang.ref.WeakReference;

public class SeekBar extends android.widget.SeekBar {
    private ProgressBarDelegate mDelegate;
    private int mForegroundPrimaryColor;
    private int mForegroundPrimaryDisableColor;

    private static class ColorUpdateRunner implements Runnable {
        private WeakReference<SeekBar> mSeekBarRef;

        public ColorUpdateRunner(SeekBar seekBar) {
            this.mSeekBarRef = new WeakReference<>(seekBar);
        }

        public void run() {
            WeakReference<SeekBar> weakReference = this.mSeekBarRef;
            SeekBar seekBar = weakReference == null ? null : weakReference.get();
            if (seekBar != null) {
                seekBar.updatePrimaryColor();
            }
        }
    }

    public SeekBar(Context context) {
        this(context, null);
    }

    public SeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842875);
    }

    public SeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        getDelegate().postConstruct(attributeSet, i);
        int color = context.getColor(R.color.progressPrimaryColor);
        int color2 = context.getColor(R.color.progressDisableColor);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, new int[]{miui.R.attr.seekBarProgressForegroundNormalColor, miui.R.attr.seekBarProgressForegroundDisableColor}, 0, 0);
        int color3 = obtainStyledAttributes.getColor(0, color);
        int color4 = obtainStyledAttributes.getColor(1, color2);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, miui.R.styleable.ProgressBar, i, 0);
        this.mForegroundPrimaryColor = obtainStyledAttributes2.getColor(R.styleable.ProgressBar_foregroundPrimaryColor, color3);
        this.mForegroundPrimaryDisableColor = obtainStyledAttributes2.getColor(R.styleable.ProgressBar_foregroundPrimaryDisableColor, color4);
        obtainStyledAttributes2.recycle();
        post(new ColorUpdateRunner(this));
    }

    private synchronized ProgressBarDelegate getDelegate() {
        if (this.mDelegate == null) {
            this.mDelegate = ProgressBarDelegate.create(this, android.widget.SeekBar.class);
        }
        return this.mDelegate;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updatePrimaryColor() {
        if (isEnabled()) {
            updateProgressDrawable(this.mForegroundPrimaryColor);
            updateThumbDrawable(this.mForegroundPrimaryColor);
            return;
        }
        updateProgressDrawable(this.mForegroundPrimaryDisableColor);
        updateThumbDrawable(this.mForegroundPrimaryDisableColor);
    }

    private void updateProgressDrawable(int i) {
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable instanceof StateListDrawable) {
            Drawable[] children = ((DrawableContainer.DrawableContainerState) ((StateListDrawable) progressDrawable).getConstantState()).getChildren();
            for (Drawable drawable : children) {
                if (drawable instanceof LayerDrawable) {
                    ((LayerDrawable) drawable).findDrawableByLayerId(16908301).setColorFilter(i, PorterDuff.Mode.SRC);
                }
            }
        }
    }

    private void updateThumbDrawable(int i) {
        Drawable findDrawableByLayerId;
        Drawable thumb = getThumb();
        if (thumb instanceof StateListDrawable) {
            Drawable[] children = ((DrawableContainer.DrawableContainerState) ((StateListDrawable) thumb).getConstantState()).getChildren();
            for (Drawable drawable : children) {
                if ((drawable instanceof LayerDrawable) && (findDrawableByLayerId = ((LayerDrawable) drawable).findDrawableByLayerId(16908288)) != null) {
                    findDrawableByLayerId.setColorFilter(i, PorterDuff.Mode.SRC);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        getDelegate().drawableStateChanged();
    }

    public Drawable getProgressMaskDrawable() {
        return getDelegate().getProgressMaskDrawable();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        getDelegate().onSizeChanged(i, i2, i3, i4);
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        updatePrimaryColor();
    }

    public void setForegroundPrimaryColor(int i, int i2) {
        this.mForegroundPrimaryColor = i;
        this.mForegroundPrimaryDisableColor = i2;
        updatePrimaryColor();
    }

    public synchronized void setIndeterminate(boolean z) {
        getDelegate().setIndeterminate(z);
    }

    public void setIndeterminateDrawable(Drawable drawable) {
        getDelegate().setIndeterminateDrawable(drawable);
    }

    public void setProgressDrawable(Drawable drawable) {
        getDelegate().setProgressDrawable(drawable);
    }

    public synchronized void setProgressMaskDrawable(Drawable drawable) {
        getDelegate().setProgressMaskDrawable(drawable);
    }
}
