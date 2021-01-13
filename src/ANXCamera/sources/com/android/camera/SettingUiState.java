package com.android.camera;

import com.android.camera.log.Log;

public class SettingUiState {
    public boolean isMutexEnable = false;
    public boolean isRomove = false;

    public String toString(String str) {
        String str2 = "funcName:" + str + "  isRomove:" + this.isRomove + "  isMutexEnable:" + this.isMutexEnable;
        Log.i("SettingUiState", str2);
        return str2;
    }
}
