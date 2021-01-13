package com.miui.internal.variable.v16;

import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.AttributeSet;
import android.view.View;
import com.miui.internal.variable.VariableExceptionHandler;
import com.miui.internal.variable.api.Overridable;
import com.miui.internal.variable.api.v29.Android_Preference_Preference;
import miui.reflect.Field;

public class Android_Preference_Preference_class extends com.miui.internal.variable.Android_Preference_Preference_class implements Overridable<Android_Preference_Preference.Interface> {
    private static final Field mPreferenceScreen = Field.of(PreferenceManager.class, "mPreferenceScreen", "Landroid/preference/PreferenceScreen;");
    private Android_Preference_Preference.Interface mImpl = new Android_Preference_Preference.Interface() {
        /* class com.miui.internal.variable.v16.Android_Preference_Preference_class.AnonymousClass1 */

        @Override // com.miui.internal.variable.api.v29.Android_Preference_Preference.Interface
        public void init(Preference preference, Context context, AttributeSet attributeSet, int i) {
            Android_Preference_Preference_class.this.handle_init_(0, preference, context, attributeSet, i);
        }

        @Override // com.miui.internal.variable.api.v29.Android_Preference_Preference.Interface
        public void onBindView(Preference preference, View view) {
            Android_Preference_Preference_class.this.handleOnBindView(0, preference, view);
        }
    };
    private Android_Preference_Preference.Interface mOriginal;

    @Override // com.miui.internal.variable.api.Overridable
    public Android_Preference_Preference.Interface asInterface() {
        return this.mImpl;
    }

    public void bind(Android_Preference_Preference.Interface r1) {
        this.mOriginal = r1;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.Android_Preference_Preference_class
    public void callOriginalOnBindView(long j, Preference preference, View view) {
        Android_Preference_Preference.Interface r0 = this.mOriginal;
        if (r0 != null) {
            r0.onBindView(preference, view);
        } else {
            super.callOriginalOnBindView(j, preference, view);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.Android_Preference_Preference_class
    public void callOriginal_init_(long j, Preference preference, Context context, AttributeSet attributeSet, int i) {
        if (this.mOriginal == null) {
            super.callOriginal_init_(j, preference, context, attributeSet, i);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.Android_Preference_Preference_class
    public PreferenceScreen getPreferenceScreen(Preference preference) {
        try {
            return (PreferenceScreen) mPreferenceScreen.get(preference.getPreferenceManager());
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("mPreferenceScreen", e2);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void onClassProxyDisabled() {
        Android_Preference_Preference.Extension.get().setExtension(this);
    }
}
