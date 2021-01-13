package com.miui.internal.variable.api;

public abstract class AbstractExtension<I> {
    private Overridable<I> mExtension;
    private I mOriginal;

    public void bindOriginal(I i) {
        this.mOriginal = i;
        Overridable<I> overridable = this.mExtension;
        if (overridable != null) {
            overridable.bind(i);
        }
    }

    public Overridable<I> getExtension() {
        return this.mExtension;
    }

    public void setExtension(Overridable<I> overridable) {
        this.mExtension = overridable;
        I i = this.mOriginal;
        if (i != null) {
            this.mExtension.bind(i);
        }
    }
}
