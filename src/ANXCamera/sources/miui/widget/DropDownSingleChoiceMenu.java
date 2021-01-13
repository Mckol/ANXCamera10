package miui.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import com.miui.internal.R;
import com.miui.internal.util.FolmeAnimHelper;
import com.miui.internal.widget.DropDownSingleChoiceMenuHelper;
import java.util.Arrays;
import java.util.List;
import miui.widget.DropDownPopupWindow;

public class DropDownSingleChoiceMenu implements DropDownPopupWindow.Controller {
    private View mAnchorView;
    private Context mContext;
    private List<String> mItems;
    private OnMenuListener mListener;
    private DropDownPopupWindow mPopupWindow;
    private int mSelectedItem;

    public interface OnMenuListener {
        void onDismiss();

        void onItemSelected(DropDownSingleChoiceMenu dropDownSingleChoiceMenu, int i);

        void onShow();
    }

    public DropDownSingleChoiceMenu(Context context) {
        this.mContext = context;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void internalDismiss() {
        this.mPopupWindow = null;
    }

    private void setAccessibilityDelegate(View view) {
        view.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            /* class miui.widget.DropDownSingleChoiceMenu.AnonymousClass4 */

            public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                super.onInitializeAccessibilityEvent(view, accessibilityEvent);
                accessibilityEvent.setClassName(Spinner.class.getName());
            }

            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setClassName(Spinner.class.getName());
            }
        });
    }

    public void dismiss() {
        DropDownPopupWindow dropDownPopupWindow = this.mPopupWindow;
        if (dropDownPopupWindow != null) {
            dropDownPopupWindow.dismiss();
        }
    }

    public List<String> getItems() {
        return this.mItems;
    }

    public int getSelectedItem() {
        return this.mSelectedItem;
    }

    @Override // miui.widget.DropDownPopupWindow.Controller
    public void onAniamtionUpdate(View view, float f) {
    }

    @Override // miui.widget.DropDownPopupWindow.Controller
    public void onDismiss() {
        OnMenuListener onMenuListener = this.mListener;
        if (onMenuListener != null) {
            onMenuListener.onDismiss();
        }
    }

    @Override // miui.widget.DropDownPopupWindow.Controller
    public void onShow() {
    }

    public void setAnchorView(View view) {
        this.mAnchorView = view;
        setAccessibilityDelegate(view);
    }

    public void setItems(List<String> list) {
        this.mItems = list;
    }

    public void setItems(String[] strArr) {
        this.mItems = Arrays.asList(strArr);
    }

    public void setOnMenuListener(OnMenuListener onMenuListener) {
        this.mListener = onMenuListener;
    }

    public void setSelectedItem(int i) {
        this.mSelectedItem = i;
    }

    public void show() {
        if (this.mItems != null && this.mAnchorView != null && this.mPopupWindow == null) {
            this.mPopupWindow = new DropDownPopupWindow(this.mContext, null, 0);
            this.mPopupWindow.setContainerController(new DropDownPopupWindow.DefaultContainerController() {
                /* class miui.widget.DropDownSingleChoiceMenu.AnonymousClass1 */

                @Override // miui.widget.DropDownPopupWindow.DefaultContainerController, miui.widget.DropDownPopupWindow.Controller
                public void onDismiss() {
                    DropDownSingleChoiceMenu.this.internalDismiss();
                }

                @Override // miui.widget.DropDownPopupWindow.DefaultContainerController, miui.widget.DropDownPopupWindow.Controller
                public void onShow() {
                    if (DropDownSingleChoiceMenu.this.mListener != null) {
                        DropDownSingleChoiceMenu.this.mListener.onShow();
                    }
                }
            });
            this.mPopupWindow.setDropDownController(this);
            ListView listView = new DropDownPopupWindow.ListController(this.mPopupWindow).getListView();
            listView.setAdapter((ListAdapter) new ArrayAdapter<String>(this.mContext, R.layout.select_dropdown_popup_singlechoice, this.mItems) {
                /* class miui.widget.DropDownSingleChoiceMenu.AnonymousClass2 */

                public View getView(int i, View view, ViewGroup viewGroup) {
                    View view2 = super.getView(i, view, viewGroup);
                    DropDownSingleChoiceMenuHelper.getView(getContext(), getCount(), i, view2);
                    FolmeAnimHelper.addPressAnim(view2);
                    return view2;
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                /* class miui.widget.DropDownSingleChoiceMenu.AnonymousClass3 */

                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    DropDownSingleChoiceMenu.this.mSelectedItem = i;
                    if (DropDownSingleChoiceMenu.this.mListener != null) {
                        DropDownSingleChoiceMenu.this.mListener.onItemSelected(DropDownSingleChoiceMenu.this, i);
                    }
                    DropDownSingleChoiceMenu.this.dismiss();
                }
            });
            listView.setChoiceMode(1);
            listView.setItemChecked(this.mSelectedItem, true);
            this.mPopupWindow.setAnchor(this.mAnchorView);
            this.mPopupWindow.show();
        }
    }
}
