package com.ss.android.medialib.model;

import androidx.annotation.Keep;

@Keep
public class FaceDetectInfo {
    FaceDetect[] info;

    public FaceDetect[] getInfo() {
        return this.info;
    }

    public void setInfo(FaceDetect[] faceDetectArr) {
        this.info = faceDetectArr;
    }
}
