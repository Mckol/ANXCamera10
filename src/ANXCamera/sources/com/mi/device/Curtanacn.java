package com.mi.device;

public final class Curtanacn extends Curtana {
    @Override // com.mi.device.Common
    public boolean Gj() {
        return true;
    }

    @Override // com.mi.device.Curtana, com.mi.device.Common
    public boolean Gl() {
        return false;
    }

    @Override // com.mi.device.Curtana, com.mi.device.Common
    public boolean Hl() {
        return false;
    }

    @Override // com.mi.device.Curtana, com.mi.device.Common
    public boolean supportColorEnhance() {
        return false;
    }
}
