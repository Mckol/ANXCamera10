package com.miui.internal.variable;

import android.preference.EditTextPreference;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.miui.internal.R;
import com.miui.internal.util.ClassProxy;
import com.miui.internal.variable.api.Overridable;
import com.miui.internal.variable.api.v29.Android_Preference_EditTextPreference;
import miui.util.SoftReferenceSingleton;

public class Android_Preference_EditTextPreference_class extends ClassProxy<EditTextPreference> implements IManagedClassProxy, Overridable<Android_Preference_EditTextPreference.Interface> {
    private Android_Preference_EditTextPreference.Interface mImpl = new Android_Preference_EditTextPreference.Interface() {
        /* class com.miui.internal.variable.Android_Preference_EditTextPreference_class.AnonymousClass1 */

        @Override // com.miui.internal.variable.api.v29.Android_Preference_EditTextPreference.Interface
        public void onAddEditTextToDialogView(EditTextPreference editTextPreference, View view, EditText editText) {
            Android_Preference_EditTextPreference_class.this.handleOnAddEditTextToDialogView(0, editTextPreference, view, editText);
        }
    };
    private Android_Preference_EditTextPreference.Interface mOriginal;

    public static class Factory extends AbsClassFactory {
        private static final SoftReferenceSingleton<Factory> INSTANCE = new SoftReferenceSingleton<Factory>() {
            /* class com.miui.internal.variable.Android_Preference_EditTextPreference_class.Factory.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // miui.util.SoftReferenceSingleton
            public Factory createInstance() {
                return new Factory();
            }
        };
        private Android_Preference_EditTextPreference_class Android_Preference_EditTextPreference_class;

        private Factory() {
            this.Android_Preference_EditTextPreference_class = new Android_Preference_EditTextPreference_class();
        }

        public static Factory getInstance() {
            return INSTANCE.get();
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_Preference_EditTextPreference_class get() {
            return this.Android_Preference_EditTextPreference_class;
        }
    }

    public Android_Preference_EditTextPreference_class() {
        super(EditTextPreference.class);
    }

    @Override // com.miui.internal.variable.api.Overridable
    public Android_Preference_EditTextPreference.Interface asInterface() {
        return this.mImpl;
    }

    public void bind(Android_Preference_EditTextPreference.Interface r1) {
        this.mOriginal = r1;
    }

    @Override // com.miui.internal.variable.IManagedClassProxy
    public void buildProxy() {
        attachMethod("onAddEditTextToDialogView", "(Landroid/view/View;Landroid/widget/EditText;)V");
    }

    /* access modifiers changed from: protected */
    public void callOriginalOnAddEditTextToDialogView(long j, EditTextPreference editTextPreference, View view, EditText editText) {
        Android_Preference_EditTextPreference.Interface r0 = this.mOriginal;
        if (r0 != null) {
            r0.onAddEditTextToDialogView(editTextPreference, view, editText);
        } else {
            originalOnAddEditTextToDialogView(j, editTextPreference, view, editText);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void handle() {
        handleOnAddEditTextToDialogView(0, null, null, null);
    }

    /* access modifiers changed from: protected */
    public void handleOnAddEditTextToDialogView(long j, EditTextPreference editTextPreference, View view, EditText editText) {
        callOriginalOnAddEditTextToDialogView(j, editTextPreference, view, editText);
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.edittext_container);
        if (viewGroup != null) {
            viewGroup.addView(editText, -1, -2);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void onClassProxyDisabled() {
        Android_Preference_EditTextPreference.Extension.get().setExtension(this);
    }

    /* access modifiers changed from: protected */
    public void originalOnAddEditTextToDialogView(long j, EditTextPreference editTextPreference, View view, EditText editText) {
        throw new IllegalStateException("com.miui.internal.variable.Android_Preference_EditTextPreference_class.originalOnAddEditTextToDialogView(long, EditTextPreference, View, EditText)");
    }
}
