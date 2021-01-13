package com.ss.android.ttve.model;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

@Keep
public class VEMusicWaveBean {
    private ArrayList<Float> waveBean;

    @Nullable
    public ArrayList<Float> getWaveBean() {
        return this.waveBean;
    }

    public void setWaveBean(@NonNull ArrayList<Float> arrayList) {
        this.waveBean = arrayList;
    }
}
