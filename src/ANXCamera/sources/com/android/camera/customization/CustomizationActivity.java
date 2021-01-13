package com.android.camera.customization;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.android.camera.BasePreferenceActivity;
import com.android.camera.fragment.settings.CustomizationFragment;

public class CustomizationActivity extends BasePreferenceActivity {
    private static final String TAG = "Customization";

    /* access modifiers changed from: protected */
    @Override // miui.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.findFragmentByTag("Customization") == null) {
            CustomizationFragment customizationFragment = new CustomizationFragment();
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            beginTransaction.add(16908290, customizationFragment, "Customization");
            beginTransaction.commit();
            this.mPreferenceFragment = customizationFragment;
        }
    }
}
