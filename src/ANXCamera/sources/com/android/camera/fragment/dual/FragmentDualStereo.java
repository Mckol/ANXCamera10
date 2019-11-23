package com.android.camera.fragment.dual;

import android.view.View;
import com.android.camera.data.data.ComponentData;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.protocol.ModeProtocol;

public class FragmentDualStereo extends BaseFragment implements View.OnClickListener, View.OnLongClickListener, ManuallyListener, ModeProtocol.DualController, ModeProtocol.HandleBackTrace {
    public static final int FRAGMENT_INFO = 4085;

    public int getFragmentInto() {
        return 4085;
    }

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return 0;
    }

    public void hideSlideView() {
    }

    public void hideZoomButton() {
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
    }

    public boolean isSlideVisible() {
        return false;
    }

    public boolean isZoomVisible() {
        return false;
    }

    public boolean onBackEvent(int i) {
        return false;
    }

    public void onClick(View view) {
    }

    public boolean onLongClick(View view) {
        return false;
    }

    public void onManuallyDataChanged(ComponentData componentData, String str, String str2, boolean z, int i) {
    }

    public void setImmersiveModeEnabled(boolean z) {
    }

    public void setRecordingOrPausing(boolean z) {
    }

    public void showZoomButton() {
    }

    public boolean updateSlideAndZoomRatio(int i) {
        return false;
    }

    public void updateZoomRatio(int i) {
    }

    public int visibleHeight() {
        return 0;
    }
}
