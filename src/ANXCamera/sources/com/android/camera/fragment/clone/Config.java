package com.android.camera.fragment.clone;

import com.xiaomi.fenshen.FenShenCam;

public class Config {
    private static FenShenCam.Mode sMode;
    private static boolean sOpenUseGuideFromFirstUse;

    private Config() {
    }

    public static FenShenCam.Mode getCloneMode() {
        return sMode;
    }

    public static boolean isOpenUseGuideFromFirstUse() {
        return sOpenUseGuideFromFirstUse;
    }

    public static void setCloneMode(FenShenCam.Mode mode) {
        sMode = mode;
    }

    public static void setOpenUseGuideFromFirstUse(boolean z) {
        sOpenUseGuideFromFirstUse = z;
    }
}
