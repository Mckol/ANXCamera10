package com.android.camera;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.android.camera.fragment.settings.WatermarkFragment;

public class WatermarkActivity extends BasePreferenceActivity {
    public static final String TAG = "WatermarkActivity";

    /* access modifiers changed from: protected */
    @Override // miui.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.findFragmentByTag(WatermarkFragment.TAG) == null) {
            WatermarkFragment watermarkFragment = new WatermarkFragment();
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            beginTransaction.add(16908290, watermarkFragment, WatermarkFragment.TAG);
            beginTransaction.commit();
            this.mPreferenceFragment = watermarkFragment;
        }
    }
}
