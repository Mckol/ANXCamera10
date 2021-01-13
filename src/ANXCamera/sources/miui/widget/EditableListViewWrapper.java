package miui.widget;

import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import com.miui.internal.widget.EditableListViewDelegate;
import miui.widget.EditableListView;

public class EditableListViewWrapper {
    private final EditableListViewDelegate mDelegate;

    /* JADX DEBUG: Multi-variable search result rejected for r0v1, resolved type: com.miui.internal.widget.EditableListViewDelegate */
    /* JADX WARN: Multi-variable type inference failed */
    public EditableListViewWrapper(AbsListView absListView) {
        if (absListView != null) {
            this.mDelegate = new EditableListViewDelegate();
            this.mDelegate.initialize(absListView, absListView.getClass());
            this.mDelegate.setUpdateListener(new EditableListViewDelegate.UpdateListener() {
                /* class miui.widget.EditableListViewWrapper.AnonymousClass1 */

                @Override // com.miui.internal.widget.EditableListViewDelegate.UpdateListener
                public void updateCheckStatus(ActionMode actionMode) {
                    EditableListViewWrapper.this.updateCheckStatus(actionMode);
                }

                @Override // com.miui.internal.widget.EditableListViewDelegate.UpdateListener
                public void updateOnScreenCheckedView(View view, int i, long j) {
                    EditableListViewWrapper.this.updateOnScreenCheckedView(view, i, j);
                }
            });
            return;
        }
        throw new IllegalArgumentException("absListView is null");
    }

    public void clearChoices() {
        this.mDelegate.clearChoices();
    }

    /* access modifiers changed from: protected */
    public ListAdapter getAdapter() {
        return this.mDelegate.getAdapter();
    }

    public int getCheckedItemCount() {
        return this.mDelegate.getListView().getCheckedItemCount();
    }

    public long[] getCheckedItemIds() {
        return this.mDelegate.getListView().getCheckedItemIds();
    }

    public int getCheckedItemPosition() {
        return this.mDelegate.getListView().getCheckedItemPosition();
    }

    public SparseBooleanArray getCheckedItemPositions() {
        return this.mDelegate.getCheckedItemPositions();
    }

    public boolean isAllItemsChecked() {
        return this.mDelegate.isAllItemsChecked();
    }

    public boolean isInActionMode() {
        return this.mDelegate.isInActionMode();
    }

    public boolean isItemChecked(int i) {
        return this.mDelegate.isItemChecked(i);
    }

    public boolean isItemIdChecked(long j) {
        return this.mDelegate.isItemIdChecked(j);
    }

    public void setAdapter(ListAdapter listAdapter) {
        this.mDelegate.setAdapter(listAdapter);
    }

    public void setAllItemsChecked(boolean z) {
        this.mDelegate.setAllItemsChecked(z);
    }

    public void setItemCheckFilter(EditableListView.ItemCheckFilter itemCheckFilter) {
        this.mDelegate.setItemCheckFilter(itemCheckFilter);
    }

    public void setItemChecked(int i, boolean z) {
        this.mDelegate.setItemChecked(i, z);
    }

    public void setMultiChoiceModeListener(AbsListView.MultiChoiceModeListener multiChoiceModeListener) {
        this.mDelegate.setMultiChoiceModeListener(multiChoiceModeListener);
    }

    /* access modifiers changed from: protected */
    public void updateCheckStatus(ActionMode actionMode) {
        this.mDelegate.updateCheckStatus(actionMode);
    }

    /* access modifiers changed from: protected */
    public void updateOnScreenCheckedView(View view, int i, long j) {
        this.mDelegate.updateOnScreenCheckedView(view, i, j);
    }
}
