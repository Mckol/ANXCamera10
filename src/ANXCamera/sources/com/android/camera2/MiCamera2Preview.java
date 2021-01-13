package com.android.camera2;

public abstract class MiCamera2Preview {
    protected int mBogusCameraId;
    protected int mCurrentModule;

    public MiCamera2Preview(int i, int i2) {
        this.mCurrentModule = i;
        this.mBogusCameraId = i2;
    }

    public boolean needForCapture() {
        int i = this.mCurrentModule;
        return i == 163 || i == 165 || i == 167 || i == 173 || i == 175 || i == 186 || i == 182 || i == 187 || i == 205 || i == 188;
    }

    public boolean needForFrontCamera() {
        return this.mBogusCameraId == 1;
    }

    public boolean needForManually() {
        int i = this.mCurrentModule;
        return i == 167 || i == 187;
    }

    public boolean needForNormalCapture() {
        int i = this.mCurrentModule;
        return i == 163 || i == 165;
    }

    public boolean needForPortrait() {
        return this.mCurrentModule == 171;
    }

    public boolean needForProVideo() {
        return this.mCurrentModule == 180;
    }

    public boolean needForVideo() {
        int i = this.mCurrentModule;
        return i == 162 || i == 169 || i == 161 || i == 174 || i == 183 || i == 172 || i == 180;
    }
}
