package com.miui.internal.variable.v16;

import android.widget.ListPopupWindow;
import android.widget.Spinner;
import com.miui.internal.variable.VariableExceptionHandler;
import com.miui.internal.variable.api.Overridable;
import com.miui.internal.variable.api.v29.Android_Widget_Spinner;
import miui.reflect.Field;
import miui.widget.ArrowPopupWindow;

public class Android_Widget_Spinner_class extends com.miui.internal.variable.Android_Widget_Spinner_class implements Overridable<Android_Widget_Spinner.Interface> {
    private static final Field mPopup = Field.of(Spinner.class, "mPopup", "Landroid/widget/Spinner$SpinnerPopup;");
    private static final Field mPopupWindow = Field.of(ListPopupWindow.class, "mPopup", "Landroid/widget/PopupWindow;");
    private Android_Widget_Spinner.Interface mImpl = new Android_Widget_Spinner.Interface() {
        /* class com.miui.internal.variable.v16.Android_Widget_Spinner_class.AnonymousClass1 */

        @Override // com.miui.internal.variable.api.v29.Android_Widget_Spinner.Interface
        public void setPrompt(Spinner spinner, CharSequence charSequence) {
            Android_Widget_Spinner_class.this.handleSetPrompt(0, spinner, charSequence);
        }
    };
    private Android_Widget_Spinner.Interface mOriginal;

    @Override // com.miui.internal.variable.api.Overridable
    public Android_Widget_Spinner.Interface asInterface() {
        return this.mImpl;
    }

    public void bind(Android_Widget_Spinner.Interface r1) {
        this.mOriginal = r1;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.Android_Widget_Spinner_class
    public void callOriginalSetPrompt(long j, Spinner spinner, CharSequence charSequence) {
        Android_Widget_Spinner.Interface r0 = this.mOriginal;
        if (r0 != null) {
            r0.setPrompt(spinner, charSequence);
        } else {
            super.callOriginalSetPrompt(j, spinner, charSequence);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.Android_Widget_Spinner_class
    public ArrowPopupWindow getArrowPopupWindow(Spinner spinner) {
        try {
            Object obj = mPopup.get(spinner);
            if (!(obj instanceof ListPopupWindow)) {
                return null;
            }
            Object obj2 = mPopupWindow.get(obj);
            if (obj2 instanceof ArrowPopupWindow) {
                return (ArrowPopupWindow) obj2;
            }
            return null;
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("mPopup", e2);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void onClassProxyDisabled() {
        Android_Widget_Spinner.Extension.get().setExtension(this);
    }
}
