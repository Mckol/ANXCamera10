package com.android.camera.data.data.runing;

import androidx.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentRunningDocument extends ComponentData {
    public static final String DOCUMENT_BLACK_WHITE = "bin";
    public static final String DOCUMENT_ORIGIN = "raw";
    public static final String DOCUMENT_STRENGTHEN = "color";
    private DataItemRunning mDataItemRunning;
    private boolean mSupported;

    public ComponentRunningDocument(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
        this.mDataItemRunning = dataItemRunning;
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return "raw";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.document_origin, "raw"));
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.document_blackwhite, DOCUMENT_BLACK_WHITE));
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.document_strengthen, DOCUMENT_STRENGTHEN));
        return Collections.unmodifiableList(arrayList);
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_document_mode_value_key";
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean isSwitchOn(int i) {
        if (!this.mSupported) {
            return false;
        }
        return this.mDataItemRunning.isSwitchOn("pref_document_mode_key");
    }

    public void reInit(int i, int i2, boolean z) {
        this.mSupported = false;
        if (!z) {
            return;
        }
        if ((DataRepository.dataItemFeature().qj() || DataRepository.dataItemFeature().rj()) && i == 186 && i2 == 0) {
            this.mSupported = true;
        }
    }

    public void setEnabled(boolean z) {
        if (z) {
            this.mDataItemRunning.switchOn("pref_document_mode_key");
        } else {
            this.mDataItemRunning.switchOff("pref_document_mode_key");
        }
    }
}
