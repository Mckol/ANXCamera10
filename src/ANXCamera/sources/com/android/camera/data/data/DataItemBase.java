package com.android.camera.data.data;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.util.SimpleArrayMap;
import com.android.camera.CameraAppImpl;
import com.android.camera.data.cloud.DataCloud.CloudItem;
import com.android.camera.data.provider.DataProvider.ProviderEditor;
import com.android.camera.data.provider.DataProvider.ProviderEvent;

public abstract class DataItemBase implements ProviderEvent, ProviderEditor {
    private CloudItem mDataCloudItem;
    private Editor mEditor;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private SharedPreferences mPreferences;
    /* access modifiers changed from: private */
    public SimpleArrayMap<String, Object> mValues = new SimpleArrayMap<>();

    public final class ConcurrentEditor implements ProviderEditor {
        private boolean mIsClear;
        private SimpleArrayMap<String, Object> mModified = new SimpleArrayMap<>();
        private Editor mPreferenceEditor;

        public ConcurrentEditor(Editor editor) {
            this.mPreferenceEditor = editor;
        }

        public void apply() {
            synchronized (DataItemBase.this.mLock) {
                if (this.mPreferenceEditor != null) {
                    this.mPreferenceEditor.apply();
                }
                if (this.mIsClear) {
                    DataItemBase.this.mValues.clear();
                    this.mIsClear = false;
                }
                DataItemBase.this.mValues.putAll(this.mModified);
                this.mModified.clear();
                this.mModified = null;
                this.mPreferenceEditor = null;
            }
        }

        public ProviderEditor clear() {
            this.mIsClear = true;
            Editor editor = this.mPreferenceEditor;
            if (editor != null) {
                editor.clear();
            }
            return this;
        }

        public boolean commit() {
            return false;
        }

        public ProviderEditor putBoolean(String str, boolean z) {
            this.mModified.put(str, Boolean.valueOf(z));
            Editor editor = this.mPreferenceEditor;
            if (editor != null) {
                editor.putBoolean(str, z);
            }
            return this;
        }

        public ProviderEditor putFloat(String str, float f2) {
            this.mModified.put(str, Float.valueOf(f2));
            Editor editor = this.mPreferenceEditor;
            if (editor != null) {
                editor.putFloat(str, f2);
            }
            return this;
        }

        public ProviderEditor putInt(String str, int i) {
            this.mModified.put(str, Integer.valueOf(i));
            Editor editor = this.mPreferenceEditor;
            if (editor != null) {
                editor.putInt(str, i);
            }
            return this;
        }

        public ProviderEditor putLong(String str, long j) {
            this.mModified.put(str, Long.valueOf(j));
            Editor editor = this.mPreferenceEditor;
            if (editor != null) {
                editor.putLong(str, j);
            }
            return this;
        }

        public ProviderEditor putString(String str, String str2) {
            this.mModified.put(str, str2);
            Editor editor = this.mPreferenceEditor;
            if (editor != null) {
                editor.putString(str, str2);
            }
            return this;
        }

        public ProviderEditor remove(String str) {
            this.mModified.put(str, null);
            Editor editor = this.mPreferenceEditor;
            if (editor != null) {
                editor.remove(str);
            }
            return this;
        }
    }

    private SharedPreferences getSharedPreferences() {
        if (this.mPreferences == null) {
            initPreferences();
        }
        return this.mPreferences;
    }

    private void initPreferences() {
        if (!isTransient()) {
            this.mPreferences = CameraAppImpl.getAndroidContext().getSharedPreferences(provideKey(), 0);
        }
    }

    public void apply() {
        if (!isTransient()) {
            synchronized (this.mLock) {
                if (this.mEditor != null) {
                    this.mEditor.apply();
                } else {
                    throw new RuntimeException("Editor null！");
                }
            }
            return;
        }
        throw new RuntimeException("Transient data！");
    }

    public boolean arrayMapContainsKey(String str) {
        boolean containsKey;
        synchronized (this.mLock) {
            containsKey = this.mValues.containsKey(str);
        }
        return containsKey;
    }

    public void arrayMapRemove(String str) {
        synchronized (this.mLock) {
            this.mValues.remove(str);
        }
    }

    public ProviderEditor clear() {
        synchronized (this.mLock) {
            this.mValues.clear();
            if (this.mEditor != null) {
                this.mEditor.clear();
            }
        }
        return this;
    }

    public void clearArrayMap() {
        synchronized (this.mLock) {
            this.mValues.clear();
        }
    }

    public SimpleArrayMap cloneValues() {
        SimpleArrayMap simpleArrayMap;
        synchronized (this.mLock) {
            simpleArrayMap = new SimpleArrayMap();
            simpleArrayMap.putAll(this.mValues);
        }
        return simpleArrayMap;
    }

    public boolean commit() {
        synchronized (this.mLock) {
            if (this.mEditor == null) {
                return false;
            }
            boolean commit = this.mEditor.commit();
            return commit;
        }
    }

    public ConcurrentEditor concurrentEditor() {
        if (isMutable()) {
            return new ConcurrentEditor(isTransient() ? null : getSharedPreferences().edit());
        }
        throw new RuntimeException("not allowed to modify");
    }

    public boolean contains(String str) {
        return getSharedPreferences().contains(str);
    }

    public ProviderEditor editor() {
        synchronized (this.mLock) {
            if (!isMutable()) {
                throw new RuntimeException("not allowed to modify");
            } else if (!isTransient() && this.mEditor == null) {
                this.mEditor = getSharedPreferences().edit();
            }
        }
        return this;
    }

    public boolean getBoolean(String str, boolean z) {
        CloudItem cloudItem = this.mDataCloudItem;
        if (cloudItem != null) {
            z = cloudItem.getCloudBooleanDefault(str, z);
        }
        synchronized (this.mLock) {
            Boolean bool = (Boolean) this.mValues.get(str);
            if (bool == null && !isTransient()) {
                bool = Boolean.valueOf(getSharedPreferences().getBoolean(str, z));
            }
            if (bool != null) {
                z = bool.booleanValue();
            }
        }
        return z;
    }

    public double getDoubleFromValues(String str, double d2) {
        synchronized (this.mLock) {
            Double d3 = (Double) this.mValues.get(str);
            if (d3 != null) {
                d2 = d3.doubleValue();
            }
        }
        return d2;
    }

    public float getFloat(String str, float f2) {
        CloudItem cloudItem = this.mDataCloudItem;
        if (cloudItem != null) {
            f2 = cloudItem.getCloudFloatDefault(str, f2);
        }
        synchronized (this.mLock) {
            Float f3 = (Float) this.mValues.get(str);
            if (f3 == null && !isTransient()) {
                f3 = Float.valueOf(getSharedPreferences().getFloat(str, f2));
            }
            if (f3 != null) {
                f2 = f3.floatValue();
            }
        }
        return f2;
    }

    public int getInt(String str, int i) {
        CloudItem cloudItem = this.mDataCloudItem;
        if (cloudItem != null) {
            i = cloudItem.getCloudIntDefault(str, i);
        }
        synchronized (this.mLock) {
            Integer num = (Integer) this.mValues.get(str);
            if (num == null && !isTransient()) {
                num = Integer.valueOf(getSharedPreferences().getInt(str, i));
            }
            if (num != null) {
                i = num.intValue();
            }
        }
        return i;
    }

    public long getLong(String str, long j) {
        CloudItem cloudItem = this.mDataCloudItem;
        if (cloudItem != null) {
            j = cloudItem.getCloudLongDefault(str, j);
        }
        synchronized (this.mLock) {
            Long l = (Long) this.mValues.get(str);
            if (l == null && !isTransient()) {
                l = Long.valueOf(getSharedPreferences().getLong(str, j));
            }
            if (l != null) {
                j = l.longValue();
            }
        }
        return j;
    }

    public String getString(String str, String str2) {
        String string;
        CloudItem cloudItem = this.mDataCloudItem;
        if (cloudItem != null) {
            str2 = cloudItem.getCloudStringDefault(str, str2);
        }
        synchronized (this.mLock) {
            String str3 = (String) this.mValues.get(str);
            string = (str3 != null || isTransient()) ? str3 : getSharedPreferences().getString(str, str2);
            if (string == null) {
                string = str2;
            }
        }
        return string;
    }

    /* access modifiers changed from: protected */
    public SimpleArrayMap<String, Object> getValues() {
        SimpleArrayMap<String, Object> simpleArrayMap;
        synchronized (this.mLock) {
            simpleArrayMap = this.mValues;
        }
        return simpleArrayMap;
    }

    public void injectCloud(CloudItem cloudItem) {
        this.mDataCloudItem = cloudItem;
    }

    /* access modifiers changed from: protected */
    public boolean isMutable() {
        return true;
    }

    public ProviderEditor putBoolean(String str, boolean z) {
        synchronized (this.mLock) {
            this.mValues.put(str, Boolean.valueOf(z));
            if (this.mEditor != null) {
                this.mEditor.putBoolean(str, z);
            }
        }
        return this;
    }

    public ProviderEditor putFloat(String str, float f2) {
        synchronized (this.mLock) {
            this.mValues.put(str, Float.valueOf(f2));
            if (this.mEditor != null) {
                this.mEditor.putFloat(str, f2);
            }
        }
        return this;
    }

    public ProviderEditor putInt(String str, int i) {
        synchronized (this.mLock) {
            this.mValues.put(str, Integer.valueOf(i));
            if (this.mEditor != null) {
                this.mEditor.putInt(str, i);
            }
        }
        return this;
    }

    public ProviderEditor putLong(String str, long j) {
        synchronized (this.mLock) {
            this.mValues.put(str, Long.valueOf(j));
            if (this.mEditor != null) {
                this.mEditor.putLong(str, j);
            }
        }
        return this;
    }

    public ProviderEditor putString(String str, String str2) {
        synchronized (this.mLock) {
            this.mValues.put(str, str2);
            if (this.mEditor != null) {
                this.mEditor.putString(str, str2);
            }
        }
        return this;
    }

    public ProviderEditor remove(String str) {
        synchronized (this.mLock) {
            this.mValues.remove(str);
            if (this.mEditor != null) {
                this.mEditor.remove(str);
            }
        }
        return this;
    }

    public void restoreArrayMap(SimpleArrayMap<String, Object> simpleArrayMap) {
        synchronized (this.mLock) {
            this.mValues.clear();
            this.mValues.putAll(simpleArrayMap);
        }
    }
}
