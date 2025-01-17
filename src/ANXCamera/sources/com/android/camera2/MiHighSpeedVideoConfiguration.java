package com.android.camera2;

import android.util.Range;
import android.util.Size;
import java.util.ArrayList;
import java.util.List;

public class MiHighSpeedVideoConfiguration {
    private static final int FIELD_COUNT = 5;
    private static final int HIGH_SPEED_MAX_MINIMAL_FPS = 120;
    private final int mBatchSizeMax;
    private final int mFpsMax;
    private final int mFpsMin;
    private final Range<Integer> mFpsRange;
    private final int mHeight;
    private final Size mSize;
    private final int mWidth;

    private MiHighSpeedVideoConfiguration(int i, int i2, int i3, int i4, int i5) {
        if (i4 >= 120) {
            this.mFpsMax = i4;
            checkArgumentPositive(i, "width must be positive");
            this.mWidth = i;
            checkArgumentPositive(i2, "height must be positive");
            this.mHeight = i2;
            checkArgumentPositive(i3, "fpsMin must be positive");
            this.mFpsMin = i3;
            this.mSize = new Size(this.mWidth, this.mHeight);
            checkArgumentPositive(i5, "batchSizeMax must be positive");
            this.mBatchSizeMax = i5;
            this.mFpsRange = new Range<>(Integer.valueOf(this.mFpsMin), Integer.valueOf(this.mFpsMax));
            return;
        }
        throw new IllegalArgumentException("fpsMax must be at least 120");
    }

    private static int checkArgumentPositive(int i, String str) {
        if (i > 0) {
            return i;
        }
        throw new IllegalArgumentException(str);
    }

    public static List<MiHighSpeedVideoConfiguration> unmarshal(int[] iArr, int i) {
        if (iArr == null || iArr.length == 0) {
            throw new IllegalArgumentException("empty buffer");
        } else if (iArr.length >= i * 5) {
            ArrayList arrayList = new ArrayList(i);
            int i2 = 0;
            int i3 = 0;
            while (i2 < i) {
                int i4 = i3 + 1;
                int i5 = iArr[i3];
                int i6 = i4 + 1;
                int i7 = iArr[i4];
                int i8 = i6 + 1;
                int i9 = iArr[i6];
                int i10 = i8 + 1;
                arrayList.add(new MiHighSpeedVideoConfiguration(i5, i7, i9, iArr[i8], iArr[i10]));
                i2++;
                i3 = i10 + 1;
            }
            return arrayList;
        } else {
            throw new IllegalArgumentException("invalid buffer length " + iArr.length);
        }
    }

    public int getBatchSizeMax() {
        return this.mBatchSizeMax;
    }

    public int getFpsMax() {
        return this.mFpsMax;
    }

    public int getFpsMin() {
        return this.mFpsMin;
    }

    public Range<Integer> getFpsRange() {
        return this.mFpsRange;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public Size getSize() {
        return this.mSize;
    }

    public int getWidth() {
        return this.mWidth;
    }
}
