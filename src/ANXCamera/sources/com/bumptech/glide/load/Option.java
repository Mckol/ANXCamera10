package com.bumptech.glide.load;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.util.Preconditions;
import java.security.MessageDigest;

public final class Option<T> {
    private static final CacheKeyUpdater<Object> EMPTY_UPDATER = new CacheKeyUpdater<Object>() {
        /* class com.bumptech.glide.load.Option.AnonymousClass1 */

        @Override // com.bumptech.glide.load.Option.CacheKeyUpdater
        public void update(@NonNull byte[] bArr, @NonNull Object obj, @NonNull MessageDigest messageDigest) {
        }
    };
    private final CacheKeyUpdater<T> cacheKeyUpdater;
    private final T defaultValue;
    private final String key;
    private volatile byte[] keyBytes;

    public interface CacheKeyUpdater<T> {
        void update(@NonNull byte[] bArr, @NonNull T t, @NonNull MessageDigest messageDigest);
    }

    private Option(@NonNull String str, @Nullable T t, @NonNull CacheKeyUpdater<T> cacheKeyUpdater2) {
        Preconditions.checkNotEmpty(str);
        this.key = str;
        this.defaultValue = t;
        Preconditions.checkNotNull(cacheKeyUpdater2);
        this.cacheKeyUpdater = cacheKeyUpdater2;
    }

    @NonNull
    public static <T> Option<T> disk(@NonNull String str, @NonNull CacheKeyUpdater<T> cacheKeyUpdater2) {
        return new Option<>(str, null, cacheKeyUpdater2);
    }

    @NonNull
    public static <T> Option<T> disk(@NonNull String str, @Nullable T t, @NonNull CacheKeyUpdater<T> cacheKeyUpdater2) {
        return new Option<>(str, t, cacheKeyUpdater2);
    }

    @NonNull
    private static <T> CacheKeyUpdater<T> emptyUpdater() {
        return (CacheKeyUpdater<T>) EMPTY_UPDATER;
    }

    @NonNull
    private byte[] getKeyBytes() {
        if (this.keyBytes == null) {
            this.keyBytes = this.key.getBytes(Key.CHARSET);
        }
        return this.keyBytes;
    }

    @NonNull
    public static <T> Option<T> memory(@NonNull String str) {
        return new Option<>(str, null, emptyUpdater());
    }

    @NonNull
    public static <T> Option<T> memory(@NonNull String str, @NonNull T t) {
        return new Option<>(str, t, emptyUpdater());
    }

    public boolean equals(Object obj) {
        if (obj instanceof Option) {
            return this.key.equals(((Option) obj).key);
        }
        return false;
    }

    @Nullable
    public T getDefaultValue() {
        return this.defaultValue;
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    public String toString() {
        return "Option{key='" + this.key + '\'' + '}';
    }

    public void update(@NonNull T t, @NonNull MessageDigest messageDigest) {
        this.cacheKeyUpdater.update(getKeyBytes(), t, messageDigest);
    }
}
