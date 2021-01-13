package com.android.camera.data.cloud;

import android.content.SharedPreferences;
import androidx.collection.SimpleArrayMap;
import com.android.camera.CameraAppImpl;
import com.android.camera.data.cloud.DataCloud;

public abstract class DataCloudItemBase implements DataCloud.CloudItem {
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;
    private boolean mReady;
    private SimpleArrayMap<String, Object> mValues = new SimpleArrayMap<>();

    private SharedPreferences getSharedPreferences() {
        if (this.mPreferences == null) {
            initPreferences();
        }
        return this.mPreferences;
    }

    private void initPreferences() {
        this.mPreferences = CameraAppImpl.getAndroidContext().getSharedPreferences(provideKey(), 0);
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public SharedPreferences.Editor editor() {
        this.mEditor = getSharedPreferences().edit();
        return this.mEditor;
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public boolean getCloudBoolean(String str, boolean z) {
        Boolean bool;
        return (this.mReady && (bool = (Boolean) this.mValues.get(str)) != null) ? bool.booleanValue() : z;
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public float getCloudFloat(String str, float f) {
        Float f2;
        return (this.mReady && (f2 = (Float) this.mValues.get(str)) != null) ? f2.floatValue() : f;
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public int getCloudInt(String str, int i) {
        Integer num;
        return (this.mReady && (num = (Integer) this.mValues.get(str)) != null) ? num.intValue() : i;
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public long getCloudLong(String str, long j) {
        Long l;
        return (this.mReady && (l = (Long) this.mValues.get(str)) != null) ? l.longValue() : j;
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public String getCloudString(String str, String str2) {
        if (!this.mReady) {
            return str2;
        }
        String str3 = (String) this.mValues.get(str);
        return str3 == null ? str2 : str3;
    }

    /* access modifiers changed from: protected */
    public SimpleArrayMap<String, Object> getValues() {
        return this.mValues;
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public void setReady(boolean z) {
        this.mReady = z;
    }
}
