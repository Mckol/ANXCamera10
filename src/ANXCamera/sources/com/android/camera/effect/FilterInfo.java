package com.android.camera.effect;

import androidx.annotation.NonNull;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class FilterInfo implements Comparable<FilterInfo> {
    public static final int AI_SCENE_FILTER_ID_0_NONE = FILTER_ID_NONE;
    public static final int CATEGORY_SHIFT = 16;
    public static final int FILTER_CATEGORY_AI_SCENE = 5;
    public static final int FILTER_CATEGORY_BEAUTY = 2;
    public static final int FILTER_CATEGORY_LIGHTING = 6;
    public static final int FILTER_CATEGORY_MI_LIVE = 8;
    public static final int FILTER_CATEGORY_NORMAL = 1;
    public static final int FILTER_CATEGORY_PRIVATE = 0;
    public static final int FILTER_CATEGORY_STICKER = 3;
    public static final int FILTER_CATEGORY_VIDEO = 7;
    public static final int FILTER_ID_BLUR = getId(0, 257);
    public static final int FILTER_ID_CINEMATIC = getId(0, 268);
    public static final int FILTER_ID_GAUSSIAN = getId(0, 260);
    public static final int FILTER_ID_GRADIENTER = getId(0, 258);
    public static final int FILTER_ID_KALEIDOSCOPE = getId(0, 266);
    public static final int FILTER_ID_NONE = getId(1, 512);
    public static final int FILTER_ID_PEAKINGMF = getId(0, 261);
    public static final int FILTER_ID_RGB2YUV = getId(0, 264);
    public static final int FILTER_ID_STICKER = getId(0, 262);
    public static final int FILTER_ID_TILTSHIFT = getId(0, 259);
    public static final int FILTER_ID_YUV2RGB = getId(0, 263);
    public static final int FILTER_ID_YUV4442RGB = getId(0, 267);
    public static final int FILTER_ID_ZEBRA = getId(0, 265);
    public static final int RENDER_CATEGORY_MAKEUP = 4;
    public static final int RENDER_ID_MAKEUP = getId(4, 769);
    private int mCategory;
    private int mIconResId;
    private int mIndex;
    private boolean mIsNeedRect;
    private int mNameResId;
    private int mOrder;
    private int tagUniqueFilterId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FilterCategory {
    }

    public FilterInfo(int i, int i2) {
        this(i, 0, 0, i2);
    }

    public FilterInfo(int i, int i2, int i3, int i4) {
        this(getCategory(i), getIndex(i), i2, i3, i4);
    }

    public FilterInfo(int i, int i2, int i3, int i4, int i5) {
        this.mCategory = i;
        this.mIndex = i2;
        this.mOrder = i5;
        this.mNameResId = i3;
        this.mIconResId = i4;
    }

    public static int getCategory(int i) {
        return i >> 16;
    }

    public static int getId(int i, int i2) {
        return (i << 16) | i2;
    }

    public static int getIndex(int i) {
        return i & 65535;
    }

    public int compareTo(@NonNull FilterInfo filterInfo) {
        int i = this.mOrder;
        int i2 = filterInfo.mOrder;
        if (i == i2) {
            return 0;
        }
        return i > i2 ? 1 : -1;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof FilterInfo)) {
            return false;
        }
        FilterInfo filterInfo = (FilterInfo) obj;
        return this.mCategory == filterInfo.mCategory && this.mIndex == filterInfo.mIndex;
    }

    public int getIconResId() {
        return this.mIconResId;
    }

    public int getId() {
        return this.mIndex | (this.mCategory << 16);
    }

    public int getNameResId() {
        return this.mNameResId;
    }

    public int getOrder() {
        return this.mOrder;
    }

    public int getTagUniqueFilterId() {
        return this.tagUniqueFilterId;
    }

    public boolean isNeedRect() {
        return this.mIsNeedRect;
    }

    public void setIconResId(int i) {
        this.mIconResId = i;
    }

    public void setNameResId(int i) {
        this.mNameResId = i;
    }

    public void setNeedRect(boolean z) {
        this.mIsNeedRect = z;
    }

    public void setOrder(int i) {
        this.mOrder = i;
    }

    public void setTagUniqueFilterId(int i) {
        this.tagUniqueFilterId = i;
    }
}
