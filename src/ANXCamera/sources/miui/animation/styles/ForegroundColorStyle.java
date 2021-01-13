package miui.animation.styles;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import miui.animation.IAnimTarget;
import miui.animation.ViewTarget;
import miui.animation.base.AnimConfigLink;
import miui.animation.property.ViewPropertyExt;

public class ForegroundColorStyle extends ColorStyle {
    private int mTintMode;

    ForegroundColorStyle(Object obj) {
        super(obj, ViewPropertyExt.FOREGROUND);
    }

    private View getView() {
        IAnimTarget target = getTarget();
        if (target instanceof ViewTarget) {
            return ((ViewTarget) target).getTargetObject();
        }
        return null;
    }

    private boolean isInvalid(View view) {
        return view == null || Build.VERSION.SDK_INT < 23;
    }

    /* access modifiers changed from: protected */
    @Override // miui.animation.styles.PropertyStyle
    public void doSetConfig(AnimConfigLink animConfigLink) {
        super.doSetConfig(animConfigLink);
        this.mTintMode = animConfigLink.getTintMode(this.mToTag, this.mProperty);
    }

    /* access modifiers changed from: protected */
    @Override // miui.animation.styles.PropertyStyle
    public void onEnd() {
        TintDrawable tintDrawable;
        super.onEnd();
        View view = getView();
        if (!isInvalid(view) && (tintDrawable = TintDrawable.get(view)) != null && Color.alpha(getCurrentIntValue()) == 0) {
            tintDrawable.restoreOriginalDrawable();
        }
    }

    /* access modifiers changed from: protected */
    @Override // miui.animation.styles.PropertyStyle, miui.animation.styles.ColorStyle
    public void onStart() {
        super.onStart();
        View view = getView();
        if (!isInvalid(view)) {
            TintDrawable.setAndGet(view).initTintBuffer(this, this.mTintMode);
        }
    }
}
