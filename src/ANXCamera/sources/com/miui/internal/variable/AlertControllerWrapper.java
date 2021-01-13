package com.miui.internal.variable;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.android.internal.app.AlertController;
import com.miui.internal.app.AlertControllerImpl;
import java.util.ArrayList;

public class AlertControllerWrapper extends AlertController {
    AlertControllerImpl mAlertControllerImpl;
    public boolean mHapticFeedbackEnabled;

    public static class AlertParams extends AlertController.AlertParams {
        public ArrayList<ActionItem> mActionItems;
        public CharSequence mCheckBoxMessage;
        public boolean mEditMode;
        public boolean mHapticFeedbackEnabled;
        public boolean mIsChecked;
        public DialogInterface.OnClickListener mOnActionItemClickListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnShowListener mOnShowListener;

        public static class ActionItem {
            public int icon;
            public int id;
            public CharSequence label;

            public ActionItem(CharSequence charSequence, int i, int i2) {
                this.label = charSequence;
                this.icon = i;
                this.id = i2;
            }
        }

        public AlertParams(Context context) {
            super(context);
        }

        private ListAdapter createListAdapter(int i, AlertControllerImpl alertControllerImpl) {
            Cursor cursor = ((AlertController.AlertParams) this).mCursor;
            if (cursor == null) {
                ListAdapter listAdapter = ((AlertController.AlertParams) this).mAdapter;
                return listAdapter != null ? listAdapter : new ArrayAdapter(((AlertController.AlertParams) this).mContext, i, 16908308, ((AlertController.AlertParams) this).mItems);
            }
            return new SimpleCursorAdapter(((AlertController.AlertParams) this).mContext, i, cursor, new String[]{((AlertController.AlertParams) this).mLabelColumn}, new int[]{16908308});
        }

        private void createListView(AlertController alertController) {
            ListAdapter listAdapter;
            final AlertControllerImpl impl = ((AlertControllerWrapper) alertController).getImpl();
            final ListView listView = (ListView) ((AlertController.AlertParams) this).mInflater.inflate(impl.getListLayout(), (ViewGroup) null);
            if (listView != null) {
                if (((AlertController.AlertParams) this).mIsMultiChoice) {
                    listAdapter = createMultiChoiceListAdapter(listView, impl.getMultiChoiceItemLayout(), impl);
                } else {
                    listAdapter = createListAdapter(((AlertController.AlertParams) this).mIsSingleChoice ? impl.getSingleChoiceItemLayout() : impl.getListItemLayout(), impl);
                }
                impl.setAdapter(listAdapter);
                impl.setCheckedItem(((AlertController.AlertParams) this).mCheckedItem);
                impl.setCheckedItems(((AlertController.AlertParams) this).mCheckedItems);
                if (((AlertController.AlertParams) this).mOnClickListener != null) {
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        /* class com.miui.internal.variable.AlertControllerWrapper.AlertParams.AnonymousClass3 */

                        @Override // android.widget.AdapterView.OnItemClickListener
                        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
                            ((AlertController.AlertParams) AlertParams.this).mOnClickListener.onClick(impl.getDialogInterface(), i);
                            if (!((AlertController.AlertParams) AlertParams.this).mIsSingleChoice) {
                                impl.getDialogInterface().dismiss();
                            }
                        }
                    });
                } else if (((AlertController.AlertParams) this).mOnCheckboxClickListener != null) {
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        /* class com.miui.internal.variable.AlertControllerWrapper.AlertParams.AnonymousClass4 */

                        @Override // android.widget.AdapterView.OnItemClickListener
                        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
                            boolean[] zArr = ((AlertController.AlertParams) AlertParams.this).mCheckedItems;
                            if (zArr != null) {
                                zArr[i] = listView.isItemChecked(i);
                            }
                            ((AlertController.AlertParams) AlertParams.this).mOnCheckboxClickListener.onClick(impl.getDialogInterface(), i, listView.isItemChecked(i));
                        }
                    });
                }
                AdapterView.OnItemSelectedListener onItemSelectedListener = ((AlertController.AlertParams) this).mOnItemSelectedListener;
                if (onItemSelectedListener != null) {
                    listView.setOnItemSelectedListener(onItemSelectedListener);
                }
                if (((AlertController.AlertParams) this).mIsSingleChoice) {
                    listView.setChoiceMode(1);
                } else if (((AlertController.AlertParams) this).mIsMultiChoice) {
                    listView.setChoiceMode(2);
                }
                impl.setListView(listView);
            }
        }

        private ListAdapter createMultiChoiceListAdapter(final ListView listView, final int i, AlertControllerImpl alertControllerImpl) {
            ListAdapter listAdapter;
            Cursor cursor = ((AlertController.AlertParams) this).mCursor;
            return cursor == null ? (!this.mEditMode || (listAdapter = ((AlertController.AlertParams) this).mAdapter) == null) ? new ArrayAdapter(((AlertController.AlertParams) this).mContext, i, 16908308, ((AlertController.AlertParams) this).mItems) {
                /* class com.miui.internal.variable.AlertControllerWrapper.AlertParams.AnonymousClass1 */

                public View getView(int i, View view, ViewGroup viewGroup) {
                    boolean[] zArr;
                    View view2 = super.getView(i, view, viewGroup);
                    AlertParams alertParams = AlertParams.this;
                    if (!alertParams.mEditMode && (zArr = ((AlertController.AlertParams) alertParams).mCheckedItems) != null && zArr[i]) {
                        listView.setItemChecked(i, true);
                    }
                    return view2;
                }
            } : listAdapter : new CursorAdapter(((AlertController.AlertParams) this).mContext, cursor, false) {
                /* class com.miui.internal.variable.AlertControllerWrapper.AlertParams.AnonymousClass2 */
                private final int mIsCheckedIndex;
                private final int mLabelIndex;

                {
                    Cursor cursor = getCursor();
                    this.mLabelIndex = cursor.getColumnIndexOrThrow(((AlertController.AlertParams) AlertParams.this).mLabelColumn);
                    this.mIsCheckedIndex = cursor.getColumnIndexOrThrow(((AlertController.AlertParams) AlertParams.this).mIsCheckedColumn);
                }

                public void bindView(View view, Context context, Cursor cursor) {
                    ((CheckedTextView) view.findViewById(16908308)).setText(cursor.getString(this.mLabelIndex));
                    if (!AlertParams.this.mEditMode) {
                        ListView listView = listView;
                        int position = cursor.getPosition();
                        int i = cursor.getInt(this.mIsCheckedIndex);
                        boolean z = true;
                        if (i != 1) {
                            z = false;
                        }
                        listView.setItemChecked(position, z);
                    }
                }

                public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                    return ((AlertController.AlertParams) AlertParams.this).mInflater.inflate(i, (ViewGroup) null);
                }
            };
        }

        public void apply(AlertController alertController) {
            View view = ((AlertController.AlertParams) this).mCustomTitleView;
            if (view != null) {
                alertController.setCustomTitle(view);
            } else {
                CharSequence charSequence = ((AlertController.AlertParams) this).mTitle;
                if (charSequence != null) {
                    alertController.setTitle(charSequence);
                }
            }
            Drawable drawable = ((AlertController.AlertParams) this).mIcon;
            if (drawable != null) {
                alertController.setIcon(drawable);
            }
            int i = ((AlertController.AlertParams) this).mIconId;
            if (i != 0) {
                alertController.setIcon(i);
            }
            CharSequence charSequence2 = ((AlertController.AlertParams) this).mMessage;
            if (charSequence2 != null) {
                alertController.setMessage(charSequence2);
            }
            if (this.mCheckBoxMessage != null) {
                ((AlertControllerWrapper) alertController).getImpl().setCheckBox(this.mIsChecked, this.mCheckBoxMessage);
            }
            CharSequence charSequence3 = ((AlertController.AlertParams) this).mPositiveButtonText;
            if (charSequence3 != null) {
                alertController.setButton(-1, charSequence3, ((AlertController.AlertParams) this).mPositiveButtonListener, (Message) null);
            }
            CharSequence charSequence4 = ((AlertController.AlertParams) this).mNegativeButtonText;
            if (charSequence4 != null) {
                alertController.setButton(-2, charSequence4, ((AlertController.AlertParams) this).mNegativeButtonListener, (Message) null);
            }
            CharSequence charSequence5 = ((AlertController.AlertParams) this).mNeutralButtonText;
            if (charSequence5 != null) {
                alertController.setButton(-3, charSequence5, ((AlertController.AlertParams) this).mNeutralButtonListener, (Message) null);
            }
            if (!(((AlertController.AlertParams) this).mItems == null && ((AlertController.AlertParams) this).mCursor == null && ((AlertController.AlertParams) this).mAdapter == null)) {
                createListView(alertController);
            }
            View view2 = ((AlertController.AlertParams) this).mView;
            if (view2 != null) {
                alertController.setView(view2);
            }
            if (this.mActionItems != null) {
                ((AlertControllerWrapper) alertController).getImpl().setActionItems(this.mActionItems, this.mOnActionItemClickListener);
            }
            ((AlertControllerWrapper) alertController).mHapticFeedbackEnabled = this.mHapticFeedbackEnabled;
        }
    }

    public AlertControllerWrapper(Context context, DialogInterface dialogInterface, Window window) {
        super(context, dialogInterface, window);
        this.mAlertControllerImpl = new AlertControllerImpl(context, dialogInterface, window);
    }

    public Button getButton(int i) {
        return this.mAlertControllerImpl.getButton(i);
    }

    public boolean[] getCheckedItems() {
        return this.mAlertControllerImpl.getCheckedItems();
    }

    public AlertControllerImpl getImpl() {
        return this.mAlertControllerImpl;
    }

    public ListView getListView() {
        return this.mAlertControllerImpl.getListView();
    }

    public TextView getMessageView() {
        return this.mAlertControllerImpl.getMessageView();
    }

    public void installContent() {
        this.mAlertControllerImpl.installContent();
    }

    public boolean isChecked() {
        return this.mAlertControllerImpl.isChecked();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.mAlertControllerImpl.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return this.mAlertControllerImpl.onKeyUp(i, keyEvent);
    }

    public void onStart() {
        this.mAlertControllerImpl.onStart();
    }

    public void onStop() {
        this.mAlertControllerImpl.onStop();
    }

    public void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message) {
        this.mAlertControllerImpl.setButton(i, charSequence, onClickListener, message);
    }

    public void setCheckBox(boolean z, CharSequence charSequence) {
        this.mAlertControllerImpl.setCheckBox(z, charSequence);
    }

    public void setCustomTitle(View view) {
        this.mAlertControllerImpl.setCustomTitle(view);
    }

    public void setIcon(int i) {
        this.mAlertControllerImpl.setIcon(i);
    }

    public void setIcon(Drawable drawable) {
        this.mAlertControllerImpl.setIcon(drawable);
    }

    public void setInverseBackgroundForced(boolean z) {
    }

    public void setMessage(CharSequence charSequence) {
        this.mAlertControllerImpl.setMessage(charSequence);
    }

    public void setTitle(CharSequence charSequence) {
        this.mAlertControllerImpl.setTitle(charSequence);
    }

    public void setView(View view) {
        this.mAlertControllerImpl.setView(view);
    }
}
