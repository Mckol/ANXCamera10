package com.android.camera.data.backup;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import androidx.collection.SimpleArrayMap;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.data.provider.DataProvider;

public class DataActionBackUpImpl implements DataBackUp {
    private SparseArray<SimpleArrayMap> mBackupArrays;

    @Override // com.android.camera.data.backup.DataBackUp
    public void backupRunning(DataItemRunning dataItemRunning, int i) {
        if (this.mBackupArrays == null) {
            this.mBackupArrays = new SparseArray<>();
        }
        if (this.mBackupArrays.get(i) == null) {
            this.mBackupArrays.put(i, dataItemRunning.cloneValues());
        }
    }

    @Override // com.android.camera.data.backup.DataBackUp
    public void clearBackUp() {
        SparseArray<SimpleArrayMap> sparseArray = this.mBackupArrays;
        if (sparseArray != null) {
            sparseArray.clear();
        }
    }

    @Override // com.android.camera.data.backup.DataBackUp
    @Nullable
    public SimpleArrayMap getBackupRunning(int i) {
        SparseArray<SimpleArrayMap> sparseArray = this.mBackupArrays;
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(i);
    }

    @Override // com.android.camera.data.backup.DataBackUp
    public void revertOrCreateRunning(DataItemRunning dataItemRunning, int i) {
        SimpleArrayMap simpleArrayMap;
        SparseArray<SimpleArrayMap> sparseArray = this.mBackupArrays;
        if (sparseArray == null) {
            this.mBackupArrays = new SparseArray<>();
            simpleArrayMap = null;
        } else {
            simpleArrayMap = sparseArray.get(i);
        }
        if (simpleArrayMap == null) {
            simpleArrayMap = new SimpleArrayMap<>();
            this.mBackupArrays.put(i, simpleArrayMap);
        }
        dataItemRunning.restoreArrayMap(simpleArrayMap);
        dataItemRunning.setBackupKey(i);
    }

    @Override // com.android.camera.data.backup.DataBackUp
    public <P extends DataProvider.ProviderEvent> void startBackup(P p, int i) {
    }
}
