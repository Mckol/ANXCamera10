package com.android.camera.data.backup;

import androidx.annotation.Nullable;
import androidx.collection.SimpleArrayMap;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.data.provider.DataProvider;

public interface DataBackUp {
    void backupRunning(DataItemRunning dataItemRunning, int i);

    void clearBackUp();

    @Nullable
    SimpleArrayMap getBackupRunning(int i);

    void revertOrCreateRunning(DataItemRunning dataItemRunning, int i);

    <P extends DataProvider.ProviderEvent> void startBackup(P p, int i);
}
