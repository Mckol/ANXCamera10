package com.android.camera.fragment.clone;

import com.xiaomi.fenshen.FenShenCam;

public interface ModeSelectedListener {
    void onModeReady();

    void onModeSelected(FenShenCam.Mode mode);
}
