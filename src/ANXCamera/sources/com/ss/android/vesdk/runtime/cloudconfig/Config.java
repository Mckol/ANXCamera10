package com.ss.android.vesdk.runtime.cloudconfig;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

@Keep
public class Config<T> {
    public static final int TYPE_FLOAT = 1;
    public static final int TYPE_INT = 0;
    public final T defaultValue;
    public final String key;
    public final int type;

    public Config(@NonNull String str, @NonNull int i, @NonNull T t) {
        this.key = str;
        this.type = i;
        this.defaultValue = t;
    }
}
