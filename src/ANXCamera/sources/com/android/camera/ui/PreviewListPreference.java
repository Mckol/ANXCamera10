package com.android.camera.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import miuix.preference.DropDownPreference;

public class PreviewListPreference extends DropDownPreference {
    private String mStoredDefaultValue;

    public PreviewListPreference(Context context) {
        super(context);
    }

    public PreviewListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PreviewListPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public String getStoredDefaultValue() {
        return this.mStoredDefaultValue;
    }

    /* access modifiers changed from: protected */
    @Override // miuix.preference.DropDownPreference, androidx.preference.Preference
    public Object onGetDefaultValue(TypedArray typedArray, int i) {
        this.mStoredDefaultValue = typedArray.getString(i);
        return this.mStoredDefaultValue;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.preference.Preference
    public boolean shouldPersist() {
        return getPreferenceManager() != null && isPersistent() && hasKey();
    }
}
