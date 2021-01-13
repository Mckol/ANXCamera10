package com.miui.internal.variable.api;

public interface Overridable<T> {
    T asInterface();

    void bind(T t);
}
