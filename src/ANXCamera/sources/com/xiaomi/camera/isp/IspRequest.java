package com.xiaomi.camera.isp;

import android.os.Parcelable;
import java.util.ArrayList;

public class IspRequest {
    public ArrayList<IspBuffer> inputBuffers;
    public int requestNumber;
    public Parcelable settings;

    public IspRequest(int i, Parcelable parcelable, ArrayList<IspBuffer> arrayList) {
        this.requestNumber = i;
        this.settings = parcelable;
        this.inputBuffers = arrayList;
    }
}
