package com.mi.device;

public final class Cattail extends Angelica {
    @Override // com.mi.device.Common, com.mi.device.Angelica
    public boolean fm() {
        return false;
    }

    @Override // com.mi.device.Common, com.mi.device.Angelica
    public boolean isSupportMacroMode() {
        return false;
    }
}
