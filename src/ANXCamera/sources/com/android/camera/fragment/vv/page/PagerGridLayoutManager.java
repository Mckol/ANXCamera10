package com.android.camera.fragment.vv.page;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IntRange;
import androidx.recyclerview.widget.RecyclerView;

public class PagerGridLayoutManager extends RecyclerView.LayoutManager implements RecyclerView.SmoothScroller.ScrollVectorProvider {
    public static final int HORIZONTAL = 1;
    private static final String TAG = "PagerGridLayoutManager";
    public static final int VERTICAL = 0;
    private boolean mAllowContinuousScroll = true;
    private boolean mChangeSelectInScrolling = true;
    private int mColumns;
    private int mHeightUsed = 0;
    private SparseArray<Rect> mItemFrames = new SparseArray<>();
    private int mItemHeight = 0;
    private int mItemWidth = 0;
    private int mLastPageCount = -1;
    private int mLastPageIndex = -1;
    private int mMaxScrollX;
    private int mMaxScrollY;
    private int mOffsetX = 0;
    private int mOffsetY = 0;
    private int mOnePageSize;
    @OrientationType
    private int mOrientation;
    private PageListener mPageListener = null;
    private RecyclerView mRecyclerView;
    private int mRows;
    private int mScrollState = 0;
    private int mWidthUsed = 0;

    public @interface OrientationType {
    }

    public interface PageListener {
        void onPageSelect(int i);

        void onPageSizeChanged(int i);
    }

    public PagerGridLayoutManager(@IntRange(from = 1, to = 100) int i, @IntRange(from = 1, to = 100) int i2, @OrientationType int i3) {
        this.mOrientation = i3;
        this.mRows = i;
        this.mColumns = i2;
        this.mOnePageSize = this.mRows * this.mColumns;
    }

    private void addOrRemove(RecyclerView.Recycler recycler, Rect rect, int i) {
        View viewForPosition = recycler.getViewForPosition(i);
        Rect itemFrameByPosition = getItemFrameByPosition(i);
        if (!Rect.intersects(rect, itemFrameByPosition)) {
            removeAndRecycleView(viewForPosition, recycler);
            return;
        }
        addView(viewForPosition);
        measureChildWithMargins(viewForPosition, this.mWidthUsed, this.mHeightUsed);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewForPosition.getLayoutParams();
        layoutDecorated(viewForPosition, (itemFrameByPosition.left - this.mOffsetX) + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + getPaddingLeft(), (itemFrameByPosition.top - this.mOffsetY) + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + getPaddingTop(), ((itemFrameByPosition.right - this.mOffsetX) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin) + getPaddingLeft(), ((itemFrameByPosition.bottom - this.mOffsetY) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin) + getPaddingTop());
    }

    private Rect getItemFrameByPosition(int i) {
        int i2;
        Rect rect = this.mItemFrames.get(i);
        if (rect == null) {
            rect = new Rect();
            int i3 = i / this.mOnePageSize;
            int i4 = 0;
            if (canScrollHorizontally()) {
                i2 = (getUsableWidth() * i3) + 0;
            } else {
                i4 = (getUsableHeight() * i3) + 0;
                i2 = 0;
            }
            int i5 = i % this.mOnePageSize;
            int i6 = this.mColumns;
            int i7 = i5 / i6;
            int i8 = i5 - (i6 * i7);
            int i9 = this.mItemWidth;
            int i10 = i2 + (i8 * i9);
            int i11 = this.mItemHeight;
            int i12 = i4 + (i7 * i11);
            rect.left = i10;
            rect.top = i12;
            rect.right = i10 + i9;
            rect.bottom = i12 + i11;
            this.mItemFrames.put(i, rect);
        }
        return rect;
    }

    private int getPageIndexByOffset() {
        int i;
        if (canScrollVertically()) {
            int usableHeight = getUsableHeight();
            int i2 = this.mOffsetY;
            if (i2 <= 0 || usableHeight <= 0) {
                return 0;
            }
            i = i2 / usableHeight;
            if (i2 % usableHeight <= usableHeight / 2) {
                return i;
            }
        } else {
            int usableWidth = getUsableWidth();
            int i3 = this.mOffsetX;
            if (i3 <= 0 || usableWidth <= 0) {
                return 0;
            }
            i = i3 / usableWidth;
            if (i3 % usableWidth <= usableWidth / 2) {
                return i;
            }
        }
        return i + 1;
    }

    private int getPageIndexByPos(int i) {
        return i / this.mOnePageSize;
    }

    private int[] getPageLeftTopByPosition(int i) {
        int[] iArr = new int[2];
        int pageIndexByPos = getPageIndexByPos(i);
        if (canScrollHorizontally()) {
            iArr[0] = pageIndexByPos * getUsableWidth();
            iArr[1] = 0;
        } else {
            iArr[0] = 0;
            iArr[1] = pageIndexByPos * getUsableHeight();
        }
        return iArr;
    }

    private int getTotalPageCount() {
        if (getItemCount() <= 0) {
            return 0;
        }
        int itemCount = getItemCount() / this.mOnePageSize;
        return getItemCount() % this.mOnePageSize != 0 ? itemCount + 1 : itemCount;
    }

    private int getUsableHeight() {
        return (getHeight() - getPaddingTop()) - getPaddingBottom();
    }

    private int getUsableWidth() {
        return (getWidth() - getPaddingLeft()) - getPaddingRight();
    }

    @SuppressLint({"CheckResult"})
    private void recycleAndFillItems(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        if (!state.isPreLayout()) {
            Rect rect = new Rect(this.mOffsetX - this.mItemWidth, this.mOffsetY - this.mItemHeight, getUsableWidth() + this.mOffsetX + this.mItemWidth, getUsableHeight() + this.mOffsetY + this.mItemHeight);
            rect.intersect(0, 0, this.mMaxScrollX + getUsableWidth(), this.mMaxScrollY + getUsableHeight());
            int pageIndexByOffset = getPageIndexByOffset();
            int i = this.mOnePageSize;
            int i2 = (pageIndexByOffset * i) - (i * 2);
            if (i2 < 0) {
                i2 = 0;
            }
            int i3 = (this.mOnePageSize * 4) + i2;
            if (i3 > getItemCount()) {
                i3 = getItemCount();
            }
            detachAndScrapAttachedViews(recycler);
            if (z) {
                while (i2 < i3) {
                    addOrRemove(recycler, rect, i2);
                    i2++;
                }
                return;
            }
            for (int i4 = i3 - 1; i4 >= i2; i4--) {
                addOrRemove(recycler, rect, i4);
            }
        }
    }

    private void setPageCount(int i) {
        if (i >= 0) {
            PageListener pageListener = this.mPageListener;
            if (!(pageListener == null || i == this.mLastPageCount)) {
                pageListener.onPageSizeChanged(i);
            }
            this.mLastPageCount = i;
        }
    }

    private void setPageIndex(int i, boolean z) {
        PageListener pageListener;
        if (i != this.mLastPageIndex) {
            if (isAllowContinuousScroll()) {
                this.mLastPageIndex = i;
            } else if (!z) {
                this.mLastPageIndex = i;
            }
            if ((!z || this.mChangeSelectInScrolling) && i >= 0 && (pageListener = this.mPageListener) != null) {
                pageListener.onPageSelect(i);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return this.mOrientation == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.mOrientation == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
    public PointF computeScrollVectorForPosition(int i) {
        PointF pointF = new PointF();
        int[] snapOffset = getSnapOffset(i);
        pointF.x = (float) snapOffset[0];
        pointF.y = (float) snapOffset[1];
        return pointF;
    }

    /* access modifiers changed from: package-private */
    public int findNextPageFirstPos() {
        int i = this.mLastPageIndex + 1;
        if (i >= getTotalPageCount()) {
            i = getTotalPageCount() - 1;
        }
        return i * this.mOnePageSize;
    }

    /* access modifiers changed from: package-private */
    public int findPrePageFirstPos() {
        int i = this.mLastPageIndex - 1;
        if (i < 0) {
            i = 0;
        }
        return i * this.mOnePageSize;
    }

    public View findSnapView() {
        if (getFocusedChild() != null) {
            return getFocusedChild();
        }
        if (getChildCount() <= 0) {
            return null;
        }
        int pageIndexByOffset = getPageIndexByOffset() * this.mOnePageSize;
        for (int i = 0; i < getChildCount(); i++) {
            if (getPosition(getChildAt(i)) == pageIndexByOffset) {
                return getChildAt(i);
            }
        }
        return getChildAt(0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    public int getOffsetX() {
        return this.mOffsetX;
    }

    public int getOffsetY() {
        return this.mOffsetY;
    }

    /* access modifiers changed from: package-private */
    public int[] getSnapOffset(int i) {
        int[] pageLeftTopByPosition = getPageLeftTopByPosition(i);
        return new int[]{pageLeftTopByPosition[0] - this.mOffsetX, pageLeftTopByPosition[1] - this.mOffsetY};
    }

    public boolean isAllowContinuousScroll() {
        return this.mAllowContinuousScroll;
    }

    public void nextPage() {
        scrollToPage(getPageIndexByOffset() + 1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onAttachedToWindow(RecyclerView recyclerView) {
        super.onAttachedToWindow(recyclerView);
        this.mRecyclerView = recyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!state.isPreLayout() && state.didStructureChange()) {
            int i = 0;
            if (getItemCount() == 0) {
                removeAndRecycleAllViews(recycler);
                setPageCount(0);
                setPageIndex(0, false);
                return;
            }
            setPageCount(getTotalPageCount());
            setPageIndex(getPageIndexByOffset(), false);
            int itemCount = getItemCount() / this.mOnePageSize;
            if (getItemCount() % this.mOnePageSize != 0) {
                itemCount++;
            }
            if (canScrollHorizontally()) {
                this.mMaxScrollX = (itemCount - 1) * getUsableWidth();
                this.mMaxScrollY = 0;
                int i2 = this.mOffsetX;
                int i3 = this.mMaxScrollX;
                if (i2 > i3) {
                    this.mOffsetX = i3;
                }
            } else {
                this.mMaxScrollX = 0;
                this.mMaxScrollY = (itemCount - 1) * getUsableHeight();
                int i4 = this.mOffsetY;
                int i5 = this.mMaxScrollY;
                if (i4 > i5) {
                    this.mOffsetY = i5;
                }
            }
            if (this.mItemWidth <= 0) {
                this.mItemWidth = getUsableWidth() / this.mColumns;
            }
            if (this.mItemHeight <= 0) {
                this.mItemHeight = getUsableHeight() / this.mRows;
            }
            this.mWidthUsed = getUsableWidth() - this.mItemWidth;
            this.mHeightUsed = getUsableHeight() - this.mItemHeight;
            for (int i6 = 0; i6 < this.mOnePageSize * 2; i6++) {
                getItemFrameByPosition(i6);
            }
            if (this.mOffsetX == 0 && this.mOffsetY == 0) {
                while (i < this.mOnePageSize && i < getItemCount()) {
                    View viewForPosition = recycler.getViewForPosition(i);
                    addView(viewForPosition);
                    measureChildWithMargins(viewForPosition, this.mWidthUsed, this.mHeightUsed);
                    i++;
                }
            }
            recycleAndFillItems(recycler, state, true);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        if (!state.isPreLayout()) {
            setPageCount(getTotalPageCount());
            setPageIndex(getPageIndexByOffset(), false);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int i, int i2) {
        super.onMeasure(recycler, state, i, i2);
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i2);
        if (mode != 1073741824 && size > 0) {
            mode = 1073741824;
        }
        if (mode2 != 1073741824 && size2 > 0) {
            mode2 = 1073741824;
        }
        setMeasuredDimension(View.MeasureSpec.makeMeasureSpec(size, mode), View.MeasureSpec.makeMeasureSpec(size2, mode2));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onScrollStateChanged(int i) {
        this.mScrollState = i;
        super.onScrollStateChanged(i);
        if (i == 0) {
            setPageIndex(getPageIndexByOffset(), false);
        }
    }

    public void prePage() {
        scrollToPage(getPageIndexByOffset() - 1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i2 = this.mOffsetX;
        int i3 = i2 + i;
        int i4 = this.mMaxScrollX;
        if (i3 > i4) {
            i = i4 - i2;
        } else if (i3 < 0) {
            i = 0 - i2;
        }
        this.mOffsetX += i;
        setPageIndex(getPageIndexByOffset(), true);
        offsetChildrenHorizontal(-i);
        if (i > 0) {
            recycleAndFillItems(recycler, state, true);
        } else {
            recycleAndFillItems(recycler, state, false);
        }
        return i;
    }

    public void scrollToPage(int i) {
        int i2;
        int i3;
        if (i < 0 || i >= this.mLastPageCount) {
            Log.e(TAG, "pageIndex = " + i + " is out of bounds, mast in [0, " + this.mLastPageCount + ")");
        } else if (this.mRecyclerView == null) {
            Log.e(TAG, "RecyclerView Not Found!");
        } else {
            if (canScrollVertically()) {
                i2 = (getUsableHeight() * i) - this.mOffsetY;
                i3 = 0;
            } else {
                i3 = (getUsableWidth() * i) - this.mOffsetX;
                i2 = 0;
            }
            this.mRecyclerView.scrollBy(i3, i2);
            setPageIndex(i, false);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void scrollToPosition(int i) {
        scrollToPage(getPageIndexByPos(i));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i2 = this.mOffsetY;
        int i3 = i2 + i;
        int i4 = this.mMaxScrollY;
        if (i3 > i4) {
            i = i4 - i2;
        } else if (i3 < 0) {
            i = 0 - i2;
        }
        this.mOffsetY += i;
        setPageIndex(getPageIndexByOffset(), true);
        offsetChildrenVertical(-i);
        if (i > 0) {
            recycleAndFillItems(recycler, state, true);
        } else {
            recycleAndFillItems(recycler, state, false);
        }
        return i;
    }

    public void setAllowContinuousScroll(boolean z) {
        this.mAllowContinuousScroll = z;
    }

    public void setChangeSelectInScrolling(boolean z) {
        this.mChangeSelectInScrolling = z;
    }

    @OrientationType
    public int setOrientationType(@OrientationType int i) {
        if (this.mOrientation == i || this.mScrollState != 0) {
            return this.mOrientation;
        }
        this.mOrientation = i;
        this.mItemFrames.clear();
        int i2 = this.mOffsetX;
        this.mOffsetX = (this.mOffsetY / getUsableHeight()) * getUsableWidth();
        this.mOffsetY = (i2 / getUsableWidth()) * getUsableHeight();
        int i3 = this.mMaxScrollX;
        this.mMaxScrollX = (this.mMaxScrollY / getUsableHeight()) * getUsableWidth();
        this.mMaxScrollY = (i3 / getUsableWidth()) * getUsableHeight();
        return this.mOrientation;
    }

    public void setPageListener(PageListener pageListener) {
        this.mPageListener = pageListener;
    }

    public void smoothNextPage() {
        smoothScrollToPage(getPageIndexByOffset() + 1);
    }

    public void smoothPrePage() {
        smoothScrollToPage(getPageIndexByOffset() - 1);
    }

    public void smoothScrollToPage(int i) {
        if (i < 0 || i >= this.mLastPageCount) {
            String str = TAG;
            Log.e(str, "pageIndex is outOfIndex, must in [0, " + this.mLastPageCount + ").");
        } else if (this.mRecyclerView == null) {
            Log.e(TAG, "RecyclerView Not Found!");
        } else {
            int pageIndexByOffset = getPageIndexByOffset();
            if (Math.abs(i - pageIndexByOffset) > 3) {
                if (i > pageIndexByOffset) {
                    scrollToPage(i - 3);
                } else if (i < pageIndexByOffset) {
                    scrollToPage(i + 3);
                }
            }
            PagerGridSmoothScroller pagerGridSmoothScroller = new PagerGridSmoothScroller(this.mRecyclerView);
            pagerGridSmoothScroller.setTargetPosition(i * this.mOnePageSize);
            startSmoothScroll(pagerGridSmoothScroller);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
        smoothScrollToPage(getPageIndexByPos(i));
    }
}
