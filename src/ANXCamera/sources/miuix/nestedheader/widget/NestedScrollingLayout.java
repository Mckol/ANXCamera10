package miuix.nestedheader.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import java.util.ArrayList;
import java.util.List;
import miuix.nestedheader.R;

public class NestedScrollingLayout extends FrameLayout implements NestedScrollingParent3, NestedScrollingChild3 {
    private static final String TAG = "NestedScrollingLayout";
    private boolean isFirstsetScrollingRange;
    private boolean isHeaderOpen;
    private long mHeaderOpenTime;
    private boolean mHeaderViewVisible;
    private boolean mNestedFlingInConsumedProgress;
    private long mNestedFlingStartInConsumedTime;
    private boolean mNestedScrollAcceptedFling;
    private boolean mNestedScrollInConsumedProgress;
    private final NestedScrollingChildHelper mNestedScrollingChildHelper;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private final int[] mNestedScrollingV2ConsumedCompat;
    private List<OnNestedChangedListener> mOnNestedChangedListeners;
    private final int[] mParentOffsetInWindow;
    private final int[] mParentScrollConsumed;
    protected View mScrollableView;
    private int mScrollableViewId;
    private int mScrollingFrom;
    private int mScrollingProgress;
    private int mScrollingTo;
    private boolean mTriggerViewVisible;

    public interface OnNestedChangedListener {
        void onStartNestedScroll(int i);

        void onStopNestedScroll(int i);

        void onStopNestedScrollAccepted(int i);
    }

    public NestedScrollingLayout(Context context) {
        this(context, null);
    }

    public NestedScrollingLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NestedScrollingLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNestedScrollingV2ConsumedCompat = new int[2];
        this.mParentOffsetInWindow = new int[2];
        this.mParentScrollConsumed = new int[2];
        this.isFirstsetScrollingRange = true;
        this.mHeaderOpenTime = 0;
        this.mNestedFlingStartInConsumedTime = 0;
        this.isHeaderOpen = false;
        this.mHeaderViewVisible = false;
        this.mTriggerViewVisible = false;
        this.mOnNestedChangedListeners = new ArrayList();
        this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        this.mNestedScrollingChildHelper = miuix.core.view.NestedScrollingChildHelper.obtain(this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.NestedScrollingLayout);
        this.mScrollableViewId = obtainStyledAttributes.getResourceId(R.styleable.NestedScrollingLayout_scrollableView, 16908298);
        obtainStyledAttributes.recycle();
        setNestedScrollingEnabled(true);
    }

    private void dispatchScrollingProgressUpdated() {
        onScrollingProgressUpdated(this.mScrollingProgress);
    }

    private void sendStartNestedScroll(int i) {
        for (OnNestedChangedListener onNestedChangedListener : this.mOnNestedChangedListeners) {
            onNestedChangedListener.onStartNestedScroll(i);
        }
    }

    private void sendStopNestedScroll(int i) {
        for (OnNestedChangedListener onNestedChangedListener : this.mOnNestedChangedListeners) {
            onNestedChangedListener.onStopNestedScroll(i);
        }
    }

    private void sendStopNestedScrollAccepted(int i) {
        for (OnNestedChangedListener onNestedChangedListener : this.mOnNestedChangedListeners) {
            onNestedChangedListener.onStopNestedScrollAccepted(i);
        }
    }

    public void addOnScrollListener(OnNestedChangedListener onNestedChangedListener) {
        this.mOnNestedChangedListeners.add(onNestedChangedListener);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean dispatchNestedPreScroll(int i, int i2, @Nullable int[] iArr, @Nullable int[] iArr2, int i3) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreScroll(i, i2, iArr, iArr2, i3);
    }

    @Override // androidx.core.view.NestedScrollingChild3
    public void dispatchNestedScroll(int i, int i2, int i3, int i4, @Nullable int[] iArr, int i5, @NonNull int[] iArr2) {
        this.mNestedScrollingChildHelper.dispatchNestedScroll(i, i2, i3, i4, iArr, i5, iArr2);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, @Nullable int[] iArr, int i5) {
        return this.mNestedScrollingChildHelper.dispatchNestedScroll(i, i2, i3, i4, iArr, i5);
    }

    public int getScrollingProgress() {
        return this.mScrollingProgress;
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean hasNestedScrollingParent(int i) {
        return this.mNestedScrollingChildHelper.hasNestedScrollingParent(i);
    }

    @Override // androidx.core.view.NestedScrollingChild
    public boolean isNestedScrollingEnabled() {
        return this.mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    /* access modifiers changed from: protected */
    @RequiresApi(api = 21)
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mScrollableView = findViewById(this.mScrollableViewId);
        View view = this.mScrollableView;
        if (view != null) {
            view.setNestedScrollingEnabled(true);
            return;
        }
        throw new IllegalArgumentException("The scrollableView attribute is required and must refer to a valid child.");
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        dispatchScrollingProgressUpdated();
    }

    @Override // androidx.core.view.NestedScrollingParent
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        onNestedPreScroll(view, i, i2, iArr, 0);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedPreScroll(@NonNull View view, int i, int i2, @NonNull int[] iArr, int i3) {
        if (i3 != 0) {
            if (!this.mNestedFlingInConsumedProgress) {
                this.mNestedFlingStartInConsumedTime = SystemClock.elapsedRealtime();
            }
            this.mNestedFlingInConsumedProgress = true;
        } else {
            this.mNestedScrollInConsumedProgress = true;
        }
        int[] iArr2 = this.mParentScrollConsumed;
        if (i2 > 0) {
            int max = Math.max(this.mScrollingFrom, Math.min(this.mScrollingTo, this.mScrollingProgress - i2));
            int i4 = this.mScrollingProgress - max;
            this.mScrollingProgress = max;
            dispatchScrollingProgressUpdated();
            iArr[0] = iArr[0] + 0;
            iArr[1] = iArr[1] + i4;
        }
        if (dispatchNestedPreScroll(i - iArr[0], i2 - iArr[1], iArr2, null, i3)) {
            iArr[0] = iArr[0] + iArr2[0];
            iArr[1] = iArr[1] + iArr2[1];
        }
    }

    @Override // androidx.core.view.NestedScrollingParent
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        onNestedScroll(view, i, i2, i3, i4, 0);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScroll(@NonNull View view, int i, int i2, int i3, int i4, int i5) {
        onNestedScroll(view, i, i2, i3, i4, 0, this.mNestedScrollingV2ConsumedCompat);
    }

    @Override // androidx.core.view.NestedScrollingParent3
    public void onNestedScroll(@NonNull View view, int i, int i2, int i3, int i4, int i5, @NonNull int[] iArr) {
        dispatchNestedScroll(i, i2, i3, i4, this.mParentOffsetInWindow, i5, iArr);
        int i6 = i4 - iArr[1];
        if (i4 < 0 && i6 != 0) {
            int i7 = this.mScrollingProgress - i6;
            boolean z = i5 == 0;
            boolean z2 = i7 > this.mScrollingFrom;
            int max = Math.max(this.mScrollingFrom, Math.min(z || !this.mTriggerViewVisible || (this.mTriggerViewVisible && !this.mHeaderViewVisible && i5 == 1 && !z2) || (this.mTriggerViewVisible && i5 == 1 && this.mHeaderViewVisible && ((!this.isHeaderOpen && i7 < 0) || (this.isHeaderOpen && (this.mHeaderOpenTime > this.mNestedFlingStartInConsumedTime ? 1 : (this.mHeaderOpenTime == this.mNestedFlingStartInConsumedTime ? 0 : -1)) <= 0))) ? this.mScrollingTo : this.mTriggerViewVisible && !this.mHeaderViewVisible && i5 == 1 && z2 && this.mScrollingProgress == this.mScrollingFrom ? this.mScrollingFrom : 0, i7));
            this.mScrollingProgress = max;
            dispatchScrollingProgressUpdated();
            iArr[0] = iArr[0] + 0;
            iArr[1] = iArr[1] + (this.mScrollingProgress - max);
        }
    }

    @Override // androidx.core.view.NestedScrollingParent
    public void onNestedScrollAccepted(View view, View view2, int i) {
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(view, view2, i);
        startNestedScroll(i & 2);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScrollAccepted(@NonNull View view, @NonNull View view2, int i, int i2) {
        onNestedScrollAccepted(view, view2, i);
        if (i2 != 0) {
            this.mNestedScrollAcceptedFling = true;
        } else {
            this.mNestedScrollAcceptedFling = false;
        }
    }

    /* access modifiers changed from: protected */
    public void onScrollingProgressUpdated(int i) {
    }

    @Override // androidx.core.view.NestedScrollingParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        boolean z = (i & 2) != 0;
        if (!this.mNestedScrollingChildHelper.startNestedScroll(i)) {
            return isEnabled() && z;
        }
        return true;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public boolean onStartNestedScroll(@NonNull View view, @NonNull View view2, int i, int i2) {
        sendStartNestedScroll(i2);
        return this.mNestedScrollingChildHelper.startNestedScroll(i, i2) || onStartNestedScroll(view, view, i);
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onStopNestedScroll(@NonNull View view, int i) {
        this.mNestedScrollingParentHelper.onStopNestedScroll(view, i);
        sendStopNestedScroll(i);
        stopNestedScroll(i);
        if (this.mNestedScrollInConsumedProgress) {
            this.mNestedScrollInConsumedProgress = false;
            if (!this.mNestedFlingInConsumedProgress && !this.mNestedScrollAcceptedFling) {
                sendStopNestedScrollAccepted(i);
            }
        } else if (this.mNestedFlingInConsumedProgress) {
            this.mNestedFlingInConsumedProgress = false;
            sendStopNestedScrollAccepted(i);
        }
    }

    public void removeOnScrollListener(OnNestedChangedListener onNestedChangedListener) {
        this.mOnNestedChangedListeners.remove(onNestedChangedListener);
    }

    @Override // androidx.core.view.NestedScrollingChild
    public void setNestedScrollingEnabled(boolean z) {
        this.mNestedScrollingChildHelper.setNestedScrollingEnabled(z);
    }

    public void setScrollingRange(int i, int i2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        if (i > i2) {
            Log.w(TAG, "wrong scrolling range: [%d, %d], making from=to");
            i = i2;
        }
        this.mScrollingFrom = i;
        this.mScrollingTo = i2;
        this.mHeaderViewVisible = z;
        this.mTriggerViewVisible = z2;
        int i3 = this.mScrollingProgress;
        int i4 = this.mScrollingFrom;
        if (i3 < i4) {
            this.mScrollingProgress = i4;
        }
        int i5 = this.mScrollingProgress;
        int i6 = this.mScrollingTo;
        if (i5 > i6) {
            this.mScrollingProgress = i6;
        }
        if (((z3 && this.isFirstsetScrollingRange) || z4 || z5) && this.mHeaderViewVisible) {
            this.mScrollingProgress = 0;
            this.isFirstsetScrollingRange = false;
        } else if ((z3 && this.isFirstsetScrollingRange) || z4) {
            this.mScrollingProgress = this.mScrollingFrom;
            this.isFirstsetScrollingRange = false;
        }
        dispatchScrollingProgressUpdated();
    }

    @Override // androidx.core.view.NestedScrollingChild
    public boolean startNestedScroll(int i) {
        return this.mNestedScrollingChildHelper.startNestedScroll(i);
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public boolean startNestedScroll(int i, int i2) {
        return this.mNestedScrollingChildHelper.startNestedScroll(i, i2);
    }

    @Override // androidx.core.view.NestedScrollingChild
    public void stopNestedScroll() {
        this.mNestedScrollingChildHelper.stopNestedScroll();
    }

    @Override // androidx.core.view.NestedScrollingChild2
    public void stopNestedScroll(int i) {
        this.mNestedScrollingChildHelper.stopNestedScroll(i);
    }

    public void updateHeaderOpen(boolean z) {
        if (!this.isHeaderOpen && z) {
            this.mHeaderOpenTime = SystemClock.elapsedRealtime();
        }
        this.isHeaderOpen = z;
    }

    public void updateScrollingProgress(int i) {
        this.mScrollingProgress = i;
    }
}
