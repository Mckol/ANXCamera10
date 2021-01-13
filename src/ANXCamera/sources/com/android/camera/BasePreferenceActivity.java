package com.android.camera;

import com.android.camera.fragment.settings.BasePreferenceFragment;
import miui.app.Activity;

public abstract class BasePreferenceActivity extends Activity {
    private static final String TAG = "BasePreferenceActivity";
    protected BasePreferenceFragment mPreferenceFragment;

    @Override // miui.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        BasePreferenceFragment basePreferenceFragment = this.mPreferenceFragment;
        if (basePreferenceFragment != null) {
            basePreferenceFragment.onBackPressed();
        }
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        BasePreferenceFragment basePreferenceFragment = this.mPreferenceFragment;
        if (basePreferenceFragment != null) {
            basePreferenceFragment.onRestart();
        }
    }
}
