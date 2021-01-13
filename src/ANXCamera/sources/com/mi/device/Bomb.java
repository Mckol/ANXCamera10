package com.mi.device;

public final class Bomb extends Atom {
    @Override // com.mi.device.Common
    public boolean Kl() {
        return false;
    }

    @Override // com.mi.device.Common, com.mi.device.Atom
    public boolean Ol() {
        return true;
    }

    @Override // com.mi.device.Common, com.mi.device.Atom
    public boolean Vl() {
        return true;
    }

    @Override // com.mi.device.Common, com.mi.device.Atom
    public String Wh() {
        return "macro:wide:ultra_wide:tele:sat";
    }

    @Override // com.mi.device.Common, com.mi.device.Atom
    public boolean Wl() {
        return true;
    }

    @Override // com.mi.device.Common, com.mi.device.Atom
    public String Xh() {
        return "capture_inner:0.6:1:3:5:10;capture_ruler:4:10:10:5";
    }

    @Override // com.mi.device.Common
    public String bi() {
        return "3";
    }

    @Override // com.mi.device.Common, com.mi.device.Atom
    public boolean fm() {
        return true;
    }

    @Override // com.mi.device.Common, com.mi.device.Atom
    public boolean gm() {
        return true;
    }

    @Override // com.mi.device.Common, com.mi.device.Atom
    public boolean isSupportMacroMode() {
        return true;
    }

    @Override // com.mi.device.Common, com.mi.device.Atom
    public boolean isSupportedOpticalZoom() {
        return true;
    }

    @Override // com.mi.device.Common, com.mi.device.Atom
    public String ml() {
        return "macro:ultra_wide:pro:capture_intent";
    }

    @Override // com.mi.device.Common
    public String qo() {
        return "1.0:2.0";
    }

    @Override // com.mi.device.Common, com.mi.device.Atom
    public String ro() {
        return "0.6:1.0:3.0";
    }

    @Override // com.mi.device.Common, com.mi.device.Atom
    public int so() {
        return 3;
    }

    @Override // com.mi.device.Common
    public boolean tk() {
        return true;
    }
}
