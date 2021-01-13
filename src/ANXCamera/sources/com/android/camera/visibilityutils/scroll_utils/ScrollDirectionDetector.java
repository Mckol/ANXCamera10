package com.android.camera.visibilityutils.scroll_utils;

import android.view.View;

public class ScrollDirectionDetector {
    private static final boolean SHOW_LOGS = false;
    private static final String TAG = "ScrollDirectionDetector";
    private int mOldFirstVisibleItem;
    private ScrollDirection mOldScrollDirection = null;
    private int mOldTop;
    private final OnDetectScrollListener mOnDetectScrollListener;

    public interface OnDetectScrollListener {
        void onScrollDirectionChanged(ScrollDirection scrollDirection);
    }

    public enum ScrollDirection {
        UP,
        DOWN
    }

    public ScrollDirectionDetector(OnDetectScrollListener onDetectScrollListener) {
        this.mOnDetectScrollListener = onDetectScrollListener;
    }

    private void onScrollDown() {
        ScrollDirection scrollDirection = this.mOldScrollDirection;
        ScrollDirection scrollDirection2 = ScrollDirection.DOWN;
        if (scrollDirection != scrollDirection2) {
            this.mOldScrollDirection = scrollDirection2;
            this.mOnDetectScrollListener.onScrollDirectionChanged(scrollDirection2);
        }
    }

    private void onScrollUp() {
        ScrollDirection scrollDirection = this.mOldScrollDirection;
        ScrollDirection scrollDirection2 = ScrollDirection.UP;
        if (scrollDirection != scrollDirection2) {
            this.mOldScrollDirection = scrollDirection2;
            this.mOnDetectScrollListener.onScrollDirectionChanged(scrollDirection2);
        }
    }

    public void onDetectedListScroll(ItemsPositionGetter itemsPositionGetter, int i) {
        int i2 = 0;
        View childAt = itemsPositionGetter.getChildAt(0);
        if (childAt != null) {
            i2 = childAt.getTop();
        }
        int i3 = this.mOldFirstVisibleItem;
        if (i == i3) {
            int i4 = this.mOldTop;
            if (i2 > i4) {
                onScrollUp();
            } else if (i2 < i4) {
                onScrollDown();
            }
        } else if (i < i3) {
            onScrollUp();
        } else {
            onScrollDown();
        }
        this.mOldTop = i2;
        this.mOldFirstVisibleItem = i;
    }
}
