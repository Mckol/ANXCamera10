package com.android.camera.videoplayer.meta;

import android.view.View;

public class CurrentItemMetaData implements MetaData {
    public final View currentItemView;
    public final int positionOfCurrentItem;

    public CurrentItemMetaData(int i, View view) {
        this.positionOfCurrentItem = i;
        this.currentItemView = view;
    }

    public String toString() {
        return "CurrentItemMetaData{positionOfCurrentItem=" + this.positionOfCurrentItem + ", currentItemView=" + this.currentItemView + '}';
    }
}
