package com.android.camera.data.provider;

import android.util.SparseArray;
import b.c.a.b;
import com.android.camera.data.backup.DataBackUp;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.extra.DataItemLive;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.DataItemRunning;

public class DataProviderMgr {
    private DataProvider mDataProvider;

    private final class DataProviderImpl implements DataProvider {
        private DataItemGlobal mDataGlobal;
        private SparseArray<DataItemConfig> mDataItemConfigs;
        private b mDataItemFeature = new b();
        private DataItemLive mDataItemLive;
        private DataItemRunning mDataRunning = new DataItemRunning();

        public DataProviderImpl(DataBackUp dataBackUp) {
            this.mDataGlobal = new DataItemGlobal(this.mDataItemFeature, this.mDataRunning, dataBackUp);
            this.mDataItemConfigs = new SparseArray<>(4);
        }

        @Override // com.android.camera.data.provider.DataProvider
        public DataItemConfig dataConfig() {
            return dataConfig(dataGlobal().getCurrentCameraId(), dataGlobal().getIntentType());
        }

        @Override // com.android.camera.data.provider.DataProvider
        public DataItemConfig dataConfig(int i) {
            return dataConfig(i, dataGlobal().getIntentType());
        }

        @Override // com.android.camera.data.provider.DataProvider
        public DataItemConfig dataConfig(int i, int i2) {
            int provideLocalId = DataItemConfig.provideLocalId(i, i2);
            DataItemConfig dataItemConfig = this.mDataItemConfigs.get(provideLocalId);
            if (dataItemConfig != null) {
                return dataItemConfig;
            }
            DataItemConfig dataItemConfig2 = new DataItemConfig(i, i2);
            this.mDataItemConfigs.put(provideLocalId, dataItemConfig2);
            return dataItemConfig2;
        }

        @Override // com.android.camera.data.provider.DataProvider
        public b dataFeature() {
            return this.mDataItemFeature;
        }

        @Override // com.android.camera.data.provider.DataProvider
        public DataItemGlobal dataGlobal() {
            return this.mDataGlobal;
        }

        @Override // com.android.camera.data.provider.DataProvider
        public DataItemLive dataLive() {
            if (this.mDataItemLive == null) {
                this.mDataItemLive = new DataItemLive();
            }
            return this.mDataItemLive;
        }

        @Override // com.android.camera.data.provider.DataProvider
        public DataItemConfig dataNormalConfig() {
            return dataConfig(dataGlobal().getCurrentCameraId(), 0);
        }

        @Override // com.android.camera.data.provider.DataProvider
        public DataItemRunning dataRunning() {
            return this.mDataRunning;
        }
    }

    public DataProviderMgr(DataBackUp dataBackUp) {
        this.mDataProvider = new DataProviderImpl(dataBackUp);
    }

    public DataProvider provider() {
        return this.mDataProvider;
    }
}
