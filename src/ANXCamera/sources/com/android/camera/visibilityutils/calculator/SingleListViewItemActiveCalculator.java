package com.android.camera.visibilityutils.calculator;

import android.util.Log;
import android.view.View;
import com.android.camera.visibilityutils.items.ListItem;
import com.android.camera.visibilityutils.items.ListItemData;
import com.android.camera.visibilityutils.scroll_utils.ItemsPositionGetter;
import com.android.camera.visibilityutils.scroll_utils.ScrollDirectionDetector;
import java.util.List;

public class SingleListViewItemActiveCalculator extends BaseItemsVisibilityCalculator {
    private static final int INACTIVE_LIST_ITEM_VISIBILITY_PERCENTS = 70;
    private static final boolean SHOW_LOGS = false;
    private static final String TAG = "SingleListViewItemActiveCalculator";
    private final Callback<ListItem> mCallback;
    private final ListItemData mCurrentItem = new ListItemData();
    private final List<? extends ListItem> mListItems;
    private ScrollDirectionDetector.ScrollDirection mScrollDirection = ScrollDirectionDetector.ScrollDirection.UP;

    /* access modifiers changed from: package-private */
    /* renamed from: com.android.camera.visibilityutils.calculator.SingleListViewItemActiveCalculator$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$camera$visibilityutils$scroll_utils$ScrollDirectionDetector$ScrollDirection = new int[ScrollDirectionDetector.ScrollDirection.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            $SwitchMap$com$android$camera$visibilityutils$scroll_utils$ScrollDirectionDetector$ScrollDirection[ScrollDirectionDetector.ScrollDirection.UP.ordinal()] = 1;
            $SwitchMap$com$android$camera$visibilityutils$scroll_utils$ScrollDirectionDetector$ScrollDirection[ScrollDirectionDetector.ScrollDirection.DOWN.ordinal()] = 2;
        }
    }

    public interface Callback<T extends ListItem> {
        void activateNewCurrentItem(T t, View view, int i);

        void deactivateCurrentItem(T t, View view, int i);
    }

    public SingleListViewItemActiveCalculator(Callback<ListItem> callback, List<? extends ListItem> list) {
        this.mCallback = callback;
        this.mListItems = list;
    }

    private void bottomToTopMostVisibleItem(ItemsPositionGetter itemsPositionGetter, int i, ListItemData listItemData) {
        int lastVisiblePosition = itemsPositionGetter.getLastVisiblePosition();
        for (int indexOfChild = itemsPositionGetter.indexOfChild(listItemData.getView()); indexOfChild >= 0; indexOfChild--) {
            View childAt = itemsPositionGetter.getChildAt(indexOfChild);
            int visibilityPercents = ((ListItem) this.mListItems.get(lastVisiblePosition)).getVisibilityPercents(childAt);
            if (visibilityPercents > i) {
                listItemData.fillWithData(lastVisiblePosition, childAt);
                i = visibilityPercents;
            }
            listItemData.setMostVisibleItemChanged(this.mCurrentItem.getView() != listItemData.getView());
            lastVisiblePosition--;
        }
    }

    private void calculateActiveItem(ItemsPositionGetter itemsPositionGetter, ListItemData listItemData) {
        int visibilityPercents = listItemData.getVisibilityPercents(this.mListItems);
        ListItemData listItemData2 = new ListItemData();
        int i = AnonymousClass1.$SwitchMap$com$android$camera$visibilityutils$scroll_utils$ScrollDirectionDetector$ScrollDirection[this.mScrollDirection.ordinal()];
        if (i == 1) {
            findPreviousItem(itemsPositionGetter, listItemData, listItemData2);
        } else if (i == 2) {
            findNextItem(itemsPositionGetter, listItemData, listItemData2);
        }
        if (enoughPercentsForDeactivation(visibilityPercents) && listItemData2.isAvailable()) {
            setCurrentItem(listItemData2);
        }
    }

    private void calculateMostVisibleItem(ItemsPositionGetter itemsPositionGetter, int i, int i2) {
        ListItemData mockCurrentItem = getMockCurrentItem(itemsPositionGetter, i, i2);
        int visibilityPercents = mockCurrentItem.getVisibilityPercents(this.mListItems);
        int i3 = AnonymousClass1.$SwitchMap$com$android$camera$visibilityutils$scroll_utils$ScrollDirectionDetector$ScrollDirection[this.mScrollDirection.ordinal()];
        if (i3 == 1) {
            bottomToTopMostVisibleItem(itemsPositionGetter, visibilityPercents, mockCurrentItem);
        } else if (i3 == 2) {
            topToBottomMostVisibleItem(itemsPositionGetter, visibilityPercents, mockCurrentItem);
        } else {
            throw new RuntimeException("not handled mScrollDirection " + this.mScrollDirection);
        }
        if (mockCurrentItem.isMostVisibleItemChanged()) {
            setCurrentItem(mockCurrentItem);
        }
    }

    private boolean enoughPercentsForDeactivation(int i) {
        return i <= 70;
    }

    private void findNextItem(ItemsPositionGetter itemsPositionGetter, ListItemData listItemData, ListItemData listItemData2) {
        int indexOfChild;
        View childAt;
        int index = listItemData.getIndex() + 1;
        if (index < this.mListItems.size() && (indexOfChild = itemsPositionGetter.indexOfChild(listItemData.getView())) >= 0 && (childAt = itemsPositionGetter.getChildAt(indexOfChild + 1)) != null) {
            ((ListItem) this.mListItems.get(index)).getVisibilityPercents(childAt);
            listItemData2.fillWithData(index, childAt);
        }
    }

    private void findPreviousItem(ItemsPositionGetter itemsPositionGetter, ListItemData listItemData, ListItemData listItemData2) {
        int indexOfChild;
        int index = listItemData.getIndex() - 1;
        if (index >= 0 && (indexOfChild = itemsPositionGetter.indexOfChild(listItemData.getView())) > 0) {
            View childAt = itemsPositionGetter.getChildAt(indexOfChild - 1);
            ((ListItem) this.mListItems.get(index)).getVisibilityPercents(childAt);
            listItemData2.fillWithData(index, childAt);
        }
    }

    private ListItemData getMockCurrentItem(ItemsPositionGetter itemsPositionGetter, int i, int i2) {
        int i3 = AnonymousClass1.$SwitchMap$com$android$camera$visibilityutils$scroll_utils$ScrollDirectionDetector$ScrollDirection[this.mScrollDirection.ordinal()];
        if (i3 == 1) {
            if (i2 >= 0) {
                i = i2;
            }
            return new ListItemData().fillWithData(i, itemsPositionGetter.getChildAt(itemsPositionGetter.getChildCount() - 1));
        } else if (i3 == 2) {
            return new ListItemData().fillWithData(i, itemsPositionGetter.getChildAt(0));
        } else {
            throw new RuntimeException("not handled mScrollDirection " + this.mScrollDirection);
        }
    }

    private void setCurrentItem(ListItemData listItemData) {
        int index = listItemData.getIndex();
        View view = listItemData.getView();
        this.mCurrentItem.fillWithData(index, view);
        this.mCallback.activateNewCurrentItem((ListItem) this.mListItems.get(index), view, index);
    }

    private void topToBottomMostVisibleItem(ItemsPositionGetter itemsPositionGetter, int i, ListItemData listItemData) {
        int firstVisiblePosition = itemsPositionGetter.getFirstVisiblePosition();
        for (int indexOfChild = itemsPositionGetter.indexOfChild(listItemData.getView()); indexOfChild < itemsPositionGetter.getChildCount(); indexOfChild++) {
            View childAt = itemsPositionGetter.getChildAt(indexOfChild);
            int visibilityPercents = ((ListItem) this.mListItems.get(firstVisiblePosition)).getVisibilityPercents(childAt);
            if (visibilityPercents > i) {
                listItemData.fillWithData(firstVisiblePosition, childAt);
                i = visibilityPercents;
            }
            firstVisiblePosition++;
        }
        listItemData.setMostVisibleItemChanged(this.mCurrentItem.getView() != listItemData.getView());
    }

    @Override // com.android.camera.visibilityutils.scroll_utils.ScrollDirectionDetector.OnDetectScrollListener
    public void onScrollDirectionChanged(ScrollDirectionDetector.ScrollDirection scrollDirection) {
        this.mScrollDirection = scrollDirection;
    }

    @Override // com.android.camera.visibilityutils.calculator.ListItemsVisibilityCalculator
    public void onScrollStateIdle(ItemsPositionGetter itemsPositionGetter, int i, int i2) {
        calculateMostVisibleItem(itemsPositionGetter, i, i2);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.visibilityutils.calculator.BaseItemsVisibilityCalculator
    public void onStateFling(ItemsPositionGetter itemsPositionGetter) {
        ListItemData listItemData = this.mCurrentItem;
        this.mCallback.deactivateCurrentItem((ListItem) this.mListItems.get(listItemData.getIndex()), listItemData.getView(), listItemData.getIndex());
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.visibilityutils.calculator.BaseItemsVisibilityCalculator
    public void onStateTouchScroll(ItemsPositionGetter itemsPositionGetter) {
        ListItemData listItemData = this.mCurrentItem;
        if (!listItemData.isAvailable()) {
            Log.w(TAG, "onStateTouchScroll ignore, currentItem not available");
        } else {
            calculateActiveItem(itemsPositionGetter, listItemData);
        }
    }
}
