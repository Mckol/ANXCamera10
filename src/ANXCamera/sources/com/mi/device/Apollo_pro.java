package com.mi.device;

public final class Apollo_pro extends Apollo {
    @Override // com.mi.device.Common, com.mi.device.Apollo
    public float Kh() {
        return 0.9352798f;
    }

    @Override // com.mi.device.Common, com.mi.device.Apollo
    public int Lh() {
        return 315;
    }

    @Override // com.mi.device.Common, com.mi.device.Apollo
    public String Ok() {
        return "108M";
    }

    @Override // com.mi.device.Common, com.mi.device.Apollo
    public String Ph() {
        return "3:12032x9024";
    }

    @Override // com.mi.device.Common, com.mi.device.Apollo
    public String Xh() {
        return "capture_inner:0.6:1:2:5:10:30;capture_ruler:4:10:10:5:10";
    }

    @Override // com.mi.device.Common, com.mi.device.Apollo
    public int ci() {
        return 0;
    }

    @Override // com.mi.device.Common, com.mi.device.Apollo
    public String kh() {
        return "notelemfnr:1;telesr:1";
    }

    @Override // com.mi.device.Common, com.mi.device.Apollo
    public boolean yk() {
        return false;
    }
}
