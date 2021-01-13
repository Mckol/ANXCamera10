package com.android.camera;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import com.android.camera.fragment.settings.MoreModeFragment;

public class MoreModeActivity extends BasePreferenceActivity {
    public static final String TAG = "MoreModeActivity";

    @Override // miui.app.Activity
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    /* access modifiers changed from: protected */
    @Override // miui.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.findFragmentByTag(MoreModeFragment.TAG) == null) {
            MoreModeFragment moreModeFragment = new MoreModeFragment();
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            beginTransaction.add(16908290, moreModeFragment, MoreModeFragment.TAG);
            beginTransaction.commitAllowingStateLoss();
            this.mPreferenceFragment = moreModeFragment;
        }
    }
}
