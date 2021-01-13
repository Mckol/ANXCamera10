package com.miui.internal.component.module;

public class ModuleLoadException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public ModuleLoadException() {
    }

    public ModuleLoadException(String str) {
        super(str);
    }

    public ModuleLoadException(String str, Throwable th) {
        super(str, th);
    }

    public ModuleLoadException(Throwable th) {
        super(th);
    }
}
