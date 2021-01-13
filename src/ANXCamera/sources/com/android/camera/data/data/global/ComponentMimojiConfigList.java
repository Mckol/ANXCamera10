package com.android.camera.data.data.global;

import androidx.annotation.NonNull;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.ArrayList;
import java.util.List;

public class ComponentMimojiConfigList extends ComponentData {
    public ComponentMimojiConfigList(DataItemGlobal dataItemGlobal) {
        super(dataItemGlobal);
    }

    private List<ComponentDataItem> initItems() {
        return new ArrayList();
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        if (this.mItems == null) {
            this.mItems = initItems();
        }
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return null;
    }
}
