package com.ss.android.medialib.model;

import androidx.annotation.Keep;

@Keep
public class FaceAttributeInfo {
    FaceAttribute[] info;

    public FaceAttribute[] getInfo() {
        return this.info;
    }

    public void setInfo(FaceAttribute[] faceAttributeArr) {
        this.info = faceAttributeArr;
    }
}
