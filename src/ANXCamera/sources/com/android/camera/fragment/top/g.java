package com.android.camera.fragment.top;

import com.android.camera.ui.ToggleSwitch;

/* compiled from: lambda */
public final /* synthetic */ class g implements ToggleSwitch.OnCheckedChangeListener {
    public static final /* synthetic */ g INSTANCE = new g();

    private /* synthetic */ g() {
    }

    @Override // com.android.camera.ui.ToggleSwitch.OnCheckedChangeListener
    public final void onCheckedChanged(ToggleSwitch toggleSwitch, boolean z) {
        FragmentTopAlert.a(toggleSwitch, z);
    }
}
