package com.android.camera.data;

import android.content.Context;
import b.c.a.b;
import com.android.camera.data.backup.DataBackUp;
import com.android.camera.data.backup.DataBackUpMgr;
import com.android.camera.data.cloud.DataCloud;
import com.android.camera.data.cloud.DataCloudItemFeature;
import com.android.camera.data.cloud.DataCloudMgr;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.extra.DataItemLive;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.data.observeable.DataItemObservable;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.data.provider.DataProviderMgr;
import com.android.camera.data.restore.DataRestore;
import com.android.camera.data.restore.DataRestoreMgr;

public class DataRepository implements DataProtocol {
    private static DataRepository sInstance;
    private DataCloudItemFeature mCloudItemFeature = new DataCloudItemFeature();
    private DataBackUpMgr mDataBackUp = new DataBackUpMgr();
    private DataCloud.CloudManager mDataCloudMgr = new DataCloudMgr();
    private DataItemObservable mDataItemObservable;
    private DataProviderMgr mDataProvider = new DataProviderMgr(this.mDataBackUp.backUp());
    private DataRestoreMgr mDataRestore = new DataRestoreMgr();

    public static DataCloud.CloudManager dataCloudMgr() {
        return getInstance().mDataCloudMgr;
    }

    public static DataCloudItemFeature dataItemCloud() {
        return getInstance().mCloudItemFeature;
    }

    public static DataItemConfig dataItemConfig() {
        return (DataItemConfig) provider().dataConfig();
    }

    public static b dataItemFeature() {
        return provider().dataFeature();
    }

    public static DataItemGlobal dataItemGlobal() {
        return (DataItemGlobal) provider().dataGlobal();
    }

    public static DataItemLive dataItemLive() {
        return (DataItemLive) provider().dataLive();
    }

    public static DataItemObservable dataItemObservable() {
        if (getInstance().mDataItemObservable == null) {
            getInstance().mDataItemObservable = new DataItemObservable();
        }
        return getInstance().mDataItemObservable;
    }

    public static DataItemRunning dataItemRunning() {
        return (DataItemRunning) provider().dataRunning();
    }

    public static DataItemConfig dataNormalItemConfig() {
        return (DataItemConfig) provider().dataNormalConfig();
    }

    public static DataRepository getInstance() {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository();
                }
            }
        }
        return sInstance;
    }

    public static DataProvider provider() {
        return getInstance().mDataProvider.provider();
    }

    @Override // com.android.camera.data.DataProtocol
    public DataBackUp backUp() {
        return this.mDataBackUp.backUp();
    }

    @Override // com.android.camera.data.DataProtocol
    public void backUpAll(int i) {
    }

    @Override // com.android.camera.data.DataProtocol
    public DataRestore restore() {
        return this.mDataRestore.restore();
    }

    @Override // com.android.camera.data.DataProtocol
    public void restoreAll(Context context, int i) {
    }
}
