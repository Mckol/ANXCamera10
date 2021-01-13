package com.miui.internal.widget;

import android.content.res.Resources;
import android.database.DataSetObserver;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.WrapperListAdapter;
import java.util.ArrayList;
import java.util.List;
import miui.R;
import miui.reflect.Method;
import miui.view.ActionModeAnimationListener;
import miui.view.EditActionMode;
import miui.widget.EditableListView;

public class EditableListViewDelegate {
    private static final Method CLEAR_CHOICES = Method.of(AbsListView.class, "clearChoices", "()V");
    private static final Method GET_CHECKED_ITEM_POSITIONS = Method.of(AbsListView.class, "getCheckedItemPositions", "()Landroid/util/SparseBooleanArray;");
    private static final Method IS_ITEM_CHECKED = Method.of(AbsListView.class, "isItemChecked", "(I)Z");
    private static final int KEY_CHECKBOX = Integer.MAX_VALUE;
    private static final Method SET_ADAPTER = Method.of(AbsListView.class, "setAdapter", "(Landroid/widget/ListAdapter;)V");
    private static final Method SET_ITEM_CHECKED = Method.of(AbsListView.class, "setItemChecked", "(IZ)V");
    private static final Method SET_MULTI_CHOICE_MODE_LISTENER = Method.of(AbsListView.class, "setMultiChoiceModeListener", "(Landroid/widget/AbsListView$MultiChoiceModeListener;)V");
    private AbsListView mAbsListView;
    private Class<? extends AbsListView> mAbsListViewClass;
    private ActionMode mActionMode;
    private List<CheckBox> mCheckBoxList = new ArrayList();
    private int mCheckedItemCount;
    private UpdateListener mDefaultUpdateListener = new UpdateListener() {
        /* class com.miui.internal.widget.EditableListViewDelegate.AnonymousClass1 */

        @Override // com.miui.internal.widget.EditableListViewDelegate.UpdateListener
        public void updateCheckStatus(ActionMode actionMode) {
            EditableListViewDelegate.this.updateCheckStatus(actionMode);
        }

        @Override // com.miui.internal.widget.EditableListViewDelegate.UpdateListener
        public void updateOnScreenCheckedView(View view, int i, long j) {
            EditableListViewDelegate.this.updateOnScreenCheckedView(view, i, j);
        }
    };
    private EditableListView.ItemCheckFilter mItemCheckFilter;
    private int mLastBottom;
    private ListAdapterWrapper mListAdapterWrapper;
    private MultiChoiceModeListenerWrapper mMultiChoiceModeListenerWrapper;
    private boolean mPreventDispatchItemCheckedStateChange;
    private AbsListView.RecyclerListener mRecyclerListener = new AbsListView.RecyclerListener() {
        /* class com.miui.internal.widget.EditableListViewDelegate.AnonymousClass2 */

        public void onMovedToScrapHeap(View view) {
            CheckBox findCheckBoxByView = EditableListViewDelegate.this.findCheckBoxByView(view);
            if (findCheckBoxByView != null) {
                findCheckBoxByView.setAlpha(1.0f);
                findCheckBoxByView.setTranslationX(0.0f);
            }
        }
    };
    private boolean mSupportHeaderView;

    /* access modifiers changed from: private */
    public class ListAdapterWrapper extends BaseAdapter implements WrapperListAdapter {
        private ListAdapter mWrapped;

        public ListAdapterWrapper(ListAdapter listAdapter) {
            this.mWrapped = listAdapter;
        }

        public boolean areAllItemsEnabled() {
            return this.mWrapped.areAllItemsEnabled();
        }

        public int getCount() {
            return this.mWrapped.getCount();
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            ListAdapter listAdapter = this.mWrapped;
            return listAdapter instanceof BaseAdapter ? ((BaseAdapter) listAdapter).getDropDownView(i, view, viewGroup) : super.getDropDownView(i, view, viewGroup);
        }

        public Object getItem(int i) {
            return this.mWrapped.getItem(i);
        }

        public long getItemId(int i) {
            return this.mWrapped.getItemId(i);
        }

        public int getItemViewType(int i) {
            return this.mWrapped.getItemViewType(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2 = this.mWrapped.getView(i, view, viewGroup);
            CheckBox findCheckBoxByView = EditableListViewDelegate.this.findCheckBoxByView(view2);
            if (findCheckBoxByView != null) {
                int choiceMode = EditableListViewDelegate.this.mAbsListView.getChoiceMode();
                boolean isItemChecked = EditableListViewDelegate.this.isItemChecked(i);
                int i2 = 8;
                if (choiceMode == 3) {
                    if (EditableListViewDelegate.this.mActionMode != null) {
                        i2 = 0;
                    }
                    findCheckBoxByView.setVisibility(i2);
                } else if (choiceMode == 1) {
                    findCheckBoxByView.setVisibility(8);
                }
                if (findCheckBoxByView.getVisibility() == 0 && !(findCheckBoxByView.getTranslationX() == 0.0f && findCheckBoxByView.getAlpha() == 1.0f)) {
                    findCheckBoxByView.setTranslationX(0.0f);
                    findCheckBoxByView.setAlpha(1.0f);
                }
                findCheckBoxByView.setChecked(isItemChecked);
            }
            return view2;
        }

        public int getViewTypeCount() {
            return this.mWrapped.getViewTypeCount();
        }

        public ListAdapter getWrappedAdapter() {
            return this.mWrapped;
        }

        public boolean hasStableIds() {
            return this.mWrapped.hasStableIds();
        }

        public boolean isEmpty() {
            return this.mWrapped.isEmpty();
        }

        public boolean isEnabled(int i) {
            return this.mWrapped.isEnabled(i);
        }

        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            this.mWrapped.registerDataSetObserver(dataSetObserver);
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            this.mWrapped.unregisterDataSetObserver(dataSetObserver);
        }
    }

    /* access modifiers changed from: private */
    public class MultiChoiceModeListenerWrapper implements EditableListView.MultiChoiceModeListener {
        private ActionModeAnimationListener mActionModeAnimationListener = new ActionModeAnimationListener() {
            /* class com.miui.internal.widget.EditableListViewDelegate.MultiChoiceModeListenerWrapper.AnonymousClass1 */

            @Override // miui.view.ActionModeAnimationListener
            public void onStart(boolean z) {
            }

            @Override // miui.view.ActionModeAnimationListener
            public void onStop(boolean z) {
                if (!z) {
                    EditableListViewDelegate.this.mActionMode = null;
                }
            }

            @Override // miui.view.ActionModeAnimationListener
            public void onUpdate(boolean z, float f) {
                int height;
                if (!z) {
                    f = 1.0f - f;
                }
                if (z && f == 1.0f && EditableListViewDelegate.this.mLastBottom > (height = EditableListViewDelegate.this.mAbsListView.getHeight())) {
                    EditableListViewDelegate.this.mAbsListView.smoothScrollBy(EditableListViewDelegate.this.mLastBottom - height, 100);
                }
            }
        };
        private AbsListView.MultiChoiceModeListener mWrapped;

        public MultiChoiceModeListenerWrapper() {
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId() == 16908313) {
                actionMode.finish();
            } else if (menuItem.getItemId() == 16908314) {
                EditableListViewDelegate editableListViewDelegate = EditableListViewDelegate.this;
                editableListViewDelegate.setAllItemsChecked(!editableListViewDelegate.isAllItemsChecked());
            }
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override // miui.widget.EditableListView.MultiChoiceModeListener
        public void onAllItemCheckedStateChanged(ActionMode actionMode, boolean z) {
            AbsListView.MultiChoiceModeListener multiChoiceModeListener = this.mWrapped;
            if (multiChoiceModeListener instanceof EditableListView.MultiChoiceModeListener) {
                ((EditableListView.MultiChoiceModeListener) multiChoiceModeListener).onAllItemCheckedStateChanged(EditableListViewDelegate.this.mActionMode, z);
            }
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            EditableListViewDelegate.this.mCheckedItemCount = 0;
            actionMode.setTitle(R.string.select_item);
            if (!this.mWrapped.onCreateActionMode(actionMode, menu)) {
                return false;
            }
            EditableListViewDelegate.this.mActionMode = actionMode;
            ((EditActionMode) EditableListViewDelegate.this.mActionMode).addAnimationListener(this.mActionModeAnimationListener);
            EditableListViewDelegate.this.mDefaultUpdateListener.updateCheckStatus(actionMode);
            EditableListViewDelegate.this.mLastBottom = -1;
            return true;
        }

        public void onDestroyActionMode(ActionMode actionMode) {
            EditableListViewDelegate.this.mCheckedItemCount = 0;
            this.mWrapped.onDestroyActionMode(actionMode);
        }

        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long j, boolean z) {
            if (!EditableListViewDelegate.this.mPreventDispatchItemCheckedStateChange) {
                int headerViewsCount = EditableListViewDelegate.this.mSupportHeaderView ? ((ListView) EditableListViewDelegate.this.mAbsListView).getHeaderViewsCount() : 0;
                int count = EditableListViewDelegate.this.getAdapter().getCount();
                if (i >= headerViewsCount && i < count + headerViewsCount && i <= EditableListViewDelegate.this.mAbsListView.getLastVisiblePosition() && i >= EditableListViewDelegate.this.mAbsListView.getFirstVisiblePosition() && EditableListViewDelegate.this.isItemCheckable(i - headerViewsCount)) {
                    EditableListViewDelegate.access$412(EditableListViewDelegate.this, z ? 1 : -1);
                    View childAt = EditableListViewDelegate.this.mAbsListView.getChildAt(i - EditableListViewDelegate.this.mAbsListView.getFirstVisiblePosition());
                    EditableListViewDelegate.this.mDefaultUpdateListener.updateOnScreenCheckedView(childAt, i, j);
                    EditableListViewDelegate.this.mDefaultUpdateListener.updateCheckStatus(actionMode);
                    if (childAt != null) {
                        childAt.sendAccessibilityEvent(1);
                    }
                    this.mWrapped.onItemCheckedStateChanged(actionMode, i, j, z);
                    if (EditableListViewDelegate.this.mLastBottom == -1) {
                        EditableListViewDelegate.this.mLastBottom = EditableListViewDelegate.this.mAbsListView.getChildAt(i - EditableListViewDelegate.this.mAbsListView.getFirstVisiblePosition()).getBottom();
                    }
                }
            }
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }

        public void setWrapped(AbsListView.MultiChoiceModeListener multiChoiceModeListener) {
            this.mWrapped = multiChoiceModeListener;
        }
    }

    public interface UpdateListener {
        void updateCheckStatus(ActionMode actionMode);

        void updateOnScreenCheckedView(View view, int i, long j);
    }

    static /* synthetic */ int access$412(EditableListViewDelegate editableListViewDelegate, int i) {
        int i2 = editableListViewDelegate.mCheckedItemCount + i;
        editableListViewDelegate.mCheckedItemCount = i2;
        return i2;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private CheckBox findCheckBoxByView(View view) {
        CheckBox checkBox = (CheckBox) view.getTag(Integer.MAX_VALUE);
        if (checkBox == null && (checkBox = (CheckBox) view.findViewById(16908289)) != null) {
            view.setTag(Integer.MAX_VALUE, checkBox);
        }
        return checkBox;
    }

    private List<CheckBox> getCheckBoxesInListView() {
        int childCount = this.mAbsListView.getChildCount();
        List<CheckBox> list = this.mCheckBoxList;
        list.clear();
        for (int i = 0; i < childCount; i++) {
            CheckBox findCheckBoxByView = findCheckBoxByView(this.mAbsListView.getChildAt(i));
            if (findCheckBoxByView != null) {
                list.add(findCheckBoxByView);
            }
        }
        return list;
    }

    private int getCheckableItemCount() {
        EditableListView.ItemCheckFilter itemCheckFilter = this.mItemCheckFilter;
        if (itemCheckFilter != null) {
            return itemCheckFilter.getCheckableItemCount();
        }
        if (getAdapter() != null) {
            return getAdapter().getCount();
        }
        return 0;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isItemCheckable(int i) {
        EditableListView.ItemCheckFilter itemCheckFilter = this.mItemCheckFilter;
        return itemCheckFilter == null || itemCheckFilter.isItemCheckable(i);
    }

    private void updateOnScreenCheckedViews() {
        int firstVisiblePosition = this.mAbsListView.getFirstVisiblePosition();
        int childCount = this.mAbsListView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            int i2 = firstVisiblePosition + i;
            this.mDefaultUpdateListener.updateOnScreenCheckedView(this.mAbsListView.getChildAt(i), i2, getAdapter().getItemId(i2));
        }
    }

    public void clearChoices() {
        CLEAR_CHOICES.invoke(this.mAbsListViewClass, this.mAbsListView, new Object[0]);
        this.mCheckedItemCount = 0;
        this.mDefaultUpdateListener.updateCheckStatus(this.mActionMode);
        updateOnScreenCheckedViews();
        this.mCheckedItemCount = 0;
    }

    public ListAdapter getAdapter() {
        ListAdapterWrapper listAdapterWrapper = this.mListAdapterWrapper;
        if (listAdapterWrapper != null) {
            return listAdapterWrapper.getWrappedAdapter();
        }
        return null;
    }

    public SparseBooleanArray getCheckedItemPositions() {
        return ((SparseBooleanArray) GET_CHECKED_ITEM_POSITIONS.invokeObject(this.mAbsListViewClass, this.mAbsListView, new Object[0])).clone();
    }

    public AbsListView getListView() {
        return this.mAbsListView;
    }

    public void initialize(AbsListView absListView, Class<? extends AbsListView> cls) {
        if (absListView != null) {
            this.mAbsListView = absListView;
            this.mAbsListViewClass = cls;
            this.mAbsListView.setChoiceMode(3);
            this.mAbsListView.setRecyclerListener(this.mRecyclerListener);
            AbsListView absListView2 = this.mAbsListView;
            this.mSupportHeaderView = absListView2 instanceof ListView;
            if (absListView2.getAdapter() != null) {
                SET_ADAPTER.invoke(this.mAbsListViewClass, this.mAbsListView, null);
                setAdapter((ListAdapter) this.mAbsListView.getAdapter());
                return;
            }
            return;
        }
        throw new IllegalArgumentException("absListView is null");
    }

    public boolean isAllItemsChecked() {
        return this.mCheckedItemCount != 0 && getCheckableItemCount() == this.mCheckedItemCount;
    }

    public boolean isInActionMode() {
        return this.mActionMode != null;
    }

    public boolean isItemChecked(int i) {
        return IS_ITEM_CHECKED.invokeBoolean(this.mAbsListViewClass, this.mAbsListView, Integer.valueOf(i + (this.mSupportHeaderView ? ((ListView) this.mAbsListView).getHeaderViewsCount() : 0)));
    }

    public boolean isItemIdChecked(long j) {
        for (long j2 : this.mAbsListView.getCheckedItemIds()) {
            if (j == j2) {
                return true;
            }
        }
        return false;
    }

    public void setAdapter(ListAdapter listAdapter) {
        if (listAdapter == getAdapter()) {
            return;
        }
        if (listAdapter == null) {
            this.mListAdapterWrapper = null;
            SET_ADAPTER.invoke(this.mAbsListViewClass, this.mAbsListView, null);
            return;
        }
        this.mListAdapterWrapper = new ListAdapterWrapper(listAdapter);
        SET_ADAPTER.invoke(this.mAbsListViewClass, this.mAbsListView, this.mListAdapterWrapper);
    }

    public void setAllItemsChecked(boolean z) {
        this.mPreventDispatchItemCheckedStateChange = true;
        int count = getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            if (isItemCheckable(i)) {
                setItemChecked(i, z);
            }
        }
        this.mCheckedItemCount = z ? getCheckableItemCount() : 0;
        this.mPreventDispatchItemCheckedStateChange = false;
        this.mDefaultUpdateListener.updateCheckStatus(this.mActionMode);
        updateOnScreenCheckedViews();
        MultiChoiceModeListenerWrapper multiChoiceModeListenerWrapper = this.mMultiChoiceModeListenerWrapper;
        if (multiChoiceModeListenerWrapper != null) {
            multiChoiceModeListenerWrapper.onAllItemCheckedStateChanged(this.mActionMode, z);
        }
    }

    public void setItemCheckFilter(EditableListView.ItemCheckFilter itemCheckFilter) {
        this.mItemCheckFilter = itemCheckFilter;
    }

    public void setItemChecked(int i, boolean z) {
        SET_ITEM_CHECKED.invoke(this.mAbsListViewClass, this.mAbsListView, Integer.valueOf(i + (this.mSupportHeaderView ? ((ListView) this.mAbsListView).getHeaderViewsCount() : 0)), Boolean.valueOf(z));
    }

    public void setMultiChoiceModeListener(AbsListView.MultiChoiceModeListener multiChoiceModeListener) {
        if (this.mMultiChoiceModeListenerWrapper == null) {
            this.mMultiChoiceModeListenerWrapper = new MultiChoiceModeListenerWrapper();
        }
        this.mMultiChoiceModeListenerWrapper.setWrapped(multiChoiceModeListener);
        SET_MULTI_CHOICE_MODE_LISTENER.invoke(this.mAbsListViewClass, this.mAbsListView, this.mMultiChoiceModeListenerWrapper);
    }

    public void setUpdateListener(UpdateListener updateListener) {
        if (updateListener != null) {
            this.mDefaultUpdateListener = updateListener;
        }
    }

    public void updateCheckStatus(ActionMode actionMode) {
        if (actionMode != null) {
            int i = this.mCheckedItemCount;
            Resources resources = this.mAbsListView.getResources();
            boolean z = true;
            if (i == 0) {
                actionMode.setTitle(resources.getString(R.string.select_item));
            } else {
                actionMode.setTitle(String.format(resources.getQuantityString(R.plurals.items_selected, i), Integer.valueOf(i)));
            }
            ((EditActionMode) actionMode).setButton(EditActionMode.BUTTON2, isAllItemsChecked() ? R.string.deselect_all : R.string.select_all);
            Menu menu = actionMode.getMenu();
            if (i == 0) {
                z = false;
            }
            menu.setGroupEnabled(0, z);
        }
    }

    public void updateOnScreenCheckedView(View view, int i, long j) {
        if (view != null) {
            boolean z = ((SparseBooleanArray) GET_CHECKED_ITEM_POSITIONS.invokeObject(this.mAbsListViewClass, this.mAbsListView, new Object[0])).get(i);
            CheckBox findCheckBoxByView = findCheckBoxByView(view);
            if (findCheckBoxByView != null) {
                findCheckBoxByView.setChecked(z);
            } else if (view instanceof Checkable) {
                ((Checkable) view).setChecked(z);
            }
        }
    }
}
