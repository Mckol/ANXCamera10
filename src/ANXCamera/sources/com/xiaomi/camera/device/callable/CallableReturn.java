package com.xiaomi.camera.device.callable;

public class CallableReturn<T> {
    public final Exception exception;
    public final T value;

    public CallableReturn(Exception exc) {
        this.exception = exc;
        this.value = null;
    }

    public CallableReturn(T t) {
        this.value = t;
        this.exception = null;
    }

    public Exception getError() {
        return this.exception;
    }

    public T getValue() {
        return this.value;
    }
}
