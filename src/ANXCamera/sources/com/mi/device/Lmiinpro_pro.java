package com.mi.device;

public final class Lmiinpro_pro extends Lmi {
    @Override // com.mi.device.Common
    public boolean Gl() {
        return true;
    }

    @Override // com.mi.device.Common
    public boolean Hl() {
        return true;
    }

    @Override // com.mi.device.Common, com.mi.device.Lmi
    public float Kh() {
        return 0.95f;
    }

    @Override // com.mi.device.Common, com.mi.device.Lmi
    public int Lh() {
        return 300;
    }

    @Override // com.mi.device.Common, com.mi.device.Lmi
    public String Wh() {
        return "MACRO:WIDE:ULTRA_WIDE:TELE:ULTRA_TELE:SAT";
    }

    @Override // com.mi.device.Common, com.mi.device.Lmi
    public String Xh() {
        return "capture_inner:0.6:1:3:5:10;capture_ruler:4:10:10:5";
    }

    @Override // com.mi.device.Common
    public boolean Yk() {
        return false;
    }

    @Override // com.mi.device.Common
    public String bi() {
        return "4";
    }

    @Override // com.mi.device.Common, com.mi.device.Lmi
    public int ci() {
        return 2;
    }

    @Override // com.mi.device.Common, com.mi.device.Lmi
    public boolean fm() {
        return false;
    }

    @Override // com.mi.device.Common, com.mi.device.Lmi
    public boolean gm() {
        return false;
    }

    @Override // com.mi.device.Common, com.mi.device.Lmi
    public boolean isSupportMacroMode() {
        return false;
    }

    @Override // com.mi.device.Common, com.mi.device.Lmi
    public boolean isSupportedOpticalZoom() {
        return true;
    }

    @Override // com.mi.device.Common
    public boolean qm() {
        return true;
    }

    @Override // com.mi.device.Common, com.mi.device.Lmi
    public String ro() {
        return "0.6:1.0:3.0";
    }

    @Override // com.mi.device.Common
    public boolean shouldCheckSatFallbackState() {
        return true;
    }

    @Override // com.mi.device.Common, com.mi.device.Lmi
    public int so() {
        return 3;
    }

    @Override // com.mi.device.Common
    public boolean tk() {
        return true;
    }
}
