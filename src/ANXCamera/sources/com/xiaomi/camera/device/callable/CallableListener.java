package com.xiaomi.camera.device.callable;

public interface CallableListener<T> {
    void onFailure(Exception exc);

    void onSuccess(T t);
}
