package com.android.camera.visibilityutils.items;

import android.app.LauncherActivity;
import android.view.View;
import java.util.List;

public class ListItemData {
    private static final boolean SHOW_LOGS = false;
    private static final String TAG = LauncherActivity.ListItem.class.getSimpleName();
    private Integer mIndexInAdapter;
    private boolean mIsMostVisibleItemChanged;
    private View mView;

    public ListItemData fillWithData(int i, View view) {
        this.mIndexInAdapter = Integer.valueOf(i);
        this.mView = view;
        return this;
    }

    public int getIndex() {
        Integer num = this.mIndexInAdapter;
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public View getView() {
        return this.mView;
    }

    public int getVisibilityPercents(List<? extends ListItem> list) {
        if (!isAvailable()) {
            return 0;
        }
        return ((ListItem) list.get(getIndex())).getVisibilityPercents(getView());
    }

    public boolean isAvailable() {
        return (this.mIndexInAdapter == null || this.mView == null) ? false : true;
    }

    public boolean isMostVisibleItemChanged() {
        return this.mIsMostVisibleItemChanged;
    }

    public void setMostVisibleItemChanged(boolean z) {
        this.mIsMostVisibleItemChanged = z;
    }

    public String toString() {
        return "ListItemData{mIndexInAdapter=" + this.mIndexInAdapter + ", mView=" + this.mView + ", mIsMostVisibleItemChanged=" + this.mIsMostVisibleItemChanged + '}';
    }
}
