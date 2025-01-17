package com.xiaomi.camera.imagecodec;

import java.util.Locale;

public class OutputConfiguration {
    private final int mFormat;
    private final int mHeight;
    private final int mWidth;

    public OutputConfiguration(int i, int i2, int i3) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mFormat = i3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OutputConfiguration)) {
            return false;
        }
        OutputConfiguration outputConfiguration = (OutputConfiguration) obj;
        return this.mWidth == outputConfiguration.mWidth && this.mHeight == outputConfiguration.mHeight && this.mFormat == outputConfiguration.mFormat;
    }

    public int getFormat() {
        return this.mFormat;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "OutputConfiguration(w:%d, h:%d, format:%d)", Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), Integer.valueOf(this.mFormat));
    }
}
