package com.ss.android.medialib.model;

import androidx.annotation.Keep;

@Keep
public class EnigmaResult {
    Enigma[] result;
    public float zoomFactor;

    public Enigma[] getResult() {
        return this.result;
    }

    public void setResult(Enigma[] enigmaArr) {
        this.result = enigmaArr;
    }
}
