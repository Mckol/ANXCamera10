package com.android.camera.data.provider;

import androidx.collection.SimpleArrayMap;
import b.c.a.b;
import com.android.camera.data.data.DataItemBase;

public interface DataProvider {

    public interface ProviderEditor {
        void apply();

        ProviderEditor clear();

        boolean commit();

        ProviderEditor putBoolean(String str, boolean z);

        ProviderEditor putFloat(String str, float f);

        ProviderEditor putInt(String str, int i);

        ProviderEditor putLong(String str, long j);

        ProviderEditor putString(String str, String str2);

        ProviderEditor remove(String str);
    }

    public interface ProviderEvent {
        SimpleArrayMap cloneValues();

        DataItemBase.ConcurrentEditor concurrentEditor();

        boolean contains(String str);

        ProviderEditor editor();

        boolean getBoolean(String str, boolean z);

        float getFloat(String str, float f);

        int getInt(String str, int i);

        long getLong(String str, long j);

        String getString(String str, String str2);

        boolean isTransient();

        String provideKey();
    }

    <P extends ProviderEvent> P dataConfig();

    <P extends ProviderEvent> P dataConfig(int i);

    <P extends ProviderEvent> P dataConfig(int i, int i2);

    b dataFeature();

    <P extends ProviderEvent> P dataGlobal();

    <P extends ProviderEvent> P dataLive();

    <P extends ProviderEvent> P dataNormalConfig();

    <P extends ProviderEvent> P dataRunning();
}
