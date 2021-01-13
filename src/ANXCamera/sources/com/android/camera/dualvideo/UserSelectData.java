package com.android.camera.dualvideo;

public class UserSelectData implements Comparable<UserSelectData> {
    public int m6PatchType;
    public int mCameraID;
    private int mIndex;
    public int mPreviewType;

    public UserSelectData(int i, int i2, int i3, int i4) {
        this.mIndex = i;
        this.mCameraID = i2;
        this.m6PatchType = i3;
        this.mPreviewType = i4;
    }

    public int compareTo(UserSelectData userSelectData) {
        return Integer.compare(this.m6PatchType, userSelectData.m6PatchType);
    }

    public int getIndex() {
        return this.mIndex;
    }

    public String toString() {
        return "ChooseData{mSelectedIndex=" + this.mIndex + ", mCameraID=" + this.mCameraID + ", mSelected6PatchType=" + this.m6PatchType + ", mSelectedPreviewType=" + this.mPreviewType + '}';
    }
}
