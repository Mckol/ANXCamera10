package com.android.camera.data.data;

import com.android.camera.data.data.DataItemBase;
import java.util.List;

public abstract class ComponentSimple<T, D extends DataItemBase> extends ComponentData {
    private boolean mClosed;

    public ComponentSimple(D d2) {
        super(d2);
    }

    public <T> boolean asBoolean(T t) {
        return t instanceof Boolean;
    }

    public <T> boolean asFloat(T t) {
        return t instanceof Float;
    }

    public <T> boolean asInteger(T t) {
        return t instanceof Integer;
    }

    public <T> boolean asLong(T t) {
        return t instanceof Long;
    }

    public <T> boolean asString(T t) {
        return t instanceof String;
    }

    @Override // com.android.camera.data.data.ComponentData
    @Deprecated
    public String getDefaultValue(int i) {
        return null;
    }

    public abstract T getDefaultValueGeneric(int i);

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return null;
    }

    public final <T> T getValue(int i) {
        T defaultValueGeneric = getDefaultValueGeneric(i);
        if (asString(defaultValueGeneric)) {
            return (T) this.mParentDataItem.getString(getKey(i), defaultValueGeneric);
        }
        if (asBoolean(defaultValueGeneric)) {
            return (T) Boolean.valueOf(this.mParentDataItem.getBoolean(getKey(i), defaultValueGeneric.booleanValue()));
        }
        if (asInteger(defaultValueGeneric)) {
            return (T) Integer.valueOf(this.mParentDataItem.getInt(getKey(i), defaultValueGeneric.intValue()));
        }
        if (asLong(defaultValueGeneric)) {
            return (T) Long.valueOf(this.mParentDataItem.getLong(getKey(i), defaultValueGeneric.longValue()));
        }
        if (asFloat(defaultValueGeneric)) {
            return (T) Float.valueOf(this.mParentDataItem.getFloat(getKey(i), defaultValueGeneric.floatValue()));
        }
        return null;
    }

    public final <T> void putValue(int i, T t) {
        if (!this.mParentDataItem.isTransient()) {
            this.mParentDataItem.editor();
        }
        if (asString(t)) {
            this.mParentDataItem.putString(getKey(i), t);
        } else if (asBoolean(t)) {
            this.mParentDataItem.putBoolean(getKey(i), t.booleanValue());
        } else if (asInteger(t)) {
            this.mParentDataItem.putInt(getKey(i), t.intValue());
        } else if (asLong(t)) {
            this.mParentDataItem.putLong(getKey(i), t.longValue());
        } else if (asFloat(t)) {
            this.mParentDataItem.putFloat(getKey(i), t.floatValue());
        }
        if (!this.mParentDataItem.isTransient()) {
            this.mParentDataItem.apply();
        }
    }
}
