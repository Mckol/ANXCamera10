package miui.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.app.AlertController;
import com.miui.internal.variable.AlertControllerWrapper;
import java.util.ArrayList;
import miui.R;
import miui.util.AttributeResolver;
import miui.util.HapticFeedbackUtil;
import miui.view.MiuiHapticFeedbackConstants;

public class AlertDialog extends Dialog implements DialogInterface {
    public static final int THEME_DARK = 2;
    public static final int THEME_DARK_EDIT = 4;
    public static final int THEME_DARK_EDIT_DEFAULT = 6;
    public static final int THEME_DAYNIGHT = 8;
    public static final int THEME_DAYNIGHT_EDIT = 9;
    public static final int THEME_DAYNIGHT_EDIT_DEFAULT = 10;
    public static final int THEME_LIGHT = 3;
    public static final int THEME_LIGHT_EDIT = 5;
    public static final int THEME_LIGHT_EDIT_DEFAULT = 7;
    private AlertControllerWrapper mAlert;

    public static class Builder {
        private AlertControllerWrapper.AlertParams P;
        private int mTheme;

        public Builder(Context context) {
            this(context, AlertDialog.resolveDialogTheme(context, 0));
        }

        public Builder(Context context, int i) {
            this.P = new AlertControllerWrapper.AlertParams(new ContextThemeWrapper(context, AlertDialog.resolveDialogTheme(context, i)));
            this.P.mEditMode = i >= 4 && i <= 7;
            this.mTheme = i;
        }

        public Builder addActionItem(int i, int i2, int i3) {
            return addActionItem(((AlertController.AlertParams) this.P).mContext.getText(i), i2, i3);
        }

        public Builder addActionItem(CharSequence charSequence, int i, int i2) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            if (alertParams.mActionItems == null) {
                alertParams.mActionItems = new ArrayList<>();
            }
            this.P.mActionItems.add(new AlertControllerWrapper.AlertParams.ActionItem(charSequence, i, i2));
            return this;
        }

        public AlertDialog create() {
            AlertDialog alertDialog = new AlertDialog(((AlertController.AlertParams) this.P).mContext, this.mTheme);
            this.P.apply(alertDialog.mAlert);
            alertDialog.setCancelable(((AlertController.AlertParams) this.P).mCancelable);
            if (((AlertController.AlertParams) this.P).mCancelable) {
                alertDialog.setCanceledOnTouchOutside(true);
            }
            alertDialog.setOnCancelListener(((AlertController.AlertParams) this.P).mOnCancelListener);
            alertDialog.setOnDismissListener(this.P.mOnDismissListener);
            alertDialog.setOnShowListener(this.P.mOnShowListener);
            DialogInterface.OnKeyListener onKeyListener = ((AlertController.AlertParams) this.P).mOnKeyListener;
            if (onKeyListener != null) {
                alertDialog.setOnKeyListener(onKeyListener);
            }
            return alertDialog;
        }

        public Context getContext() {
            return ((AlertController.AlertParams) this.P).mContext;
        }

        public Builder setAdapter(ListAdapter listAdapter, DialogInterface.OnClickListener onClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mAdapter = listAdapter;
            ((AlertController.AlertParams) alertParams).mOnClickListener = onClickListener;
            return this;
        }

        public Builder setCancelable(boolean z) {
            ((AlertController.AlertParams) this.P).mCancelable = z;
            return this;
        }

        public Builder setCheckBox(boolean z, CharSequence charSequence) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            alertParams.mIsChecked = z;
            alertParams.mCheckBoxMessage = charSequence;
            return this;
        }

        public Builder setCursor(Cursor cursor, DialogInterface.OnClickListener onClickListener, String str) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mCursor = cursor;
            ((AlertController.AlertParams) alertParams).mLabelColumn = str;
            ((AlertController.AlertParams) alertParams).mOnClickListener = onClickListener;
            return this;
        }

        public Builder setCustomTitle(View view) {
            ((AlertController.AlertParams) this.P).mCustomTitleView = view;
            return this;
        }

        public Builder setHapticFeedbackEnabled(boolean z) {
            this.P.mHapticFeedbackEnabled = z;
            return this;
        }

        public Builder setIcon(int i) {
            ((AlertController.AlertParams) this.P).mIconId = i;
            return this;
        }

        public Builder setIcon(Drawable drawable) {
            ((AlertController.AlertParams) this.P).mIcon = drawable;
            return this;
        }

        public Builder setIconAttribute(int i) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mIconId = AttributeResolver.resolve(((AlertController.AlertParams) alertParams).mContext, i);
            return this;
        }

        public Builder setItems(int i, DialogInterface.OnClickListener onClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mItems = ((AlertController.AlertParams) alertParams).mContext.getResources().getTextArray(i);
            ((AlertController.AlertParams) this.P).mOnClickListener = onClickListener;
            return this;
        }

        public Builder setItems(CharSequence[] charSequenceArr, DialogInterface.OnClickListener onClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mItems = charSequenceArr;
            ((AlertController.AlertParams) alertParams).mOnClickListener = onClickListener;
            return this;
        }

        public Builder setMessage(int i) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mMessage = ((AlertController.AlertParams) alertParams).mContext.getText(i);
            return this;
        }

        public Builder setMessage(CharSequence charSequence) {
            ((AlertController.AlertParams) this.P).mMessage = charSequence;
            return this;
        }

        public Builder setMultiChoiceAdapter(ListAdapter listAdapter, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mAdapter = listAdapter;
            ((AlertController.AlertParams) alertParams).mIsMultiChoice = true;
            ((AlertController.AlertParams) alertParams).mOnCheckboxClickListener = onMultiChoiceClickListener;
            return this;
        }

        public Builder setMultiChoiceItems(int i, boolean[] zArr, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mItems = ((AlertController.AlertParams) alertParams).mContext.getResources().getTextArray(i);
            AlertControllerWrapper.AlertParams alertParams2 = this.P;
            ((AlertController.AlertParams) alertParams2).mOnCheckboxClickListener = onMultiChoiceClickListener;
            ((AlertController.AlertParams) alertParams2).mCheckedItems = zArr;
            ((AlertController.AlertParams) alertParams2).mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(Cursor cursor, String str, String str2, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mCursor = cursor;
            ((AlertController.AlertParams) alertParams).mOnCheckboxClickListener = onMultiChoiceClickListener;
            ((AlertController.AlertParams) alertParams).mIsCheckedColumn = str;
            ((AlertController.AlertParams) alertParams).mLabelColumn = str2;
            ((AlertController.AlertParams) alertParams).mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(CharSequence[] charSequenceArr, boolean[] zArr, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mItems = charSequenceArr;
            ((AlertController.AlertParams) alertParams).mOnCheckboxClickListener = onMultiChoiceClickListener;
            ((AlertController.AlertParams) alertParams).mCheckedItems = zArr;
            ((AlertController.AlertParams) alertParams).mIsMultiChoice = true;
            return this;
        }

        public Builder setNegativeButton(int i, DialogInterface.OnClickListener onClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mNegativeButtonText = ((AlertController.AlertParams) alertParams).mContext.getText(i);
            ((AlertController.AlertParams) this.P).mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNegativeButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mNegativeButtonText = charSequence;
            ((AlertController.AlertParams) alertParams).mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNeutralButton(int i, DialogInterface.OnClickListener onClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mNeutralButtonText = ((AlertController.AlertParams) alertParams).mContext.getText(i);
            ((AlertController.AlertParams) this.P).mNeutralButtonListener = onClickListener;
            return this;
        }

        public Builder setNeutralButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mNeutralButtonText = charSequence;
            ((AlertController.AlertParams) alertParams).mNeutralButtonListener = onClickListener;
            return this;
        }

        public Builder setOnActionItemClickListener(DialogInterface.OnClickListener onClickListener) {
            this.P.mOnActionItemClickListener = onClickListener;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            ((AlertController.AlertParams) this.P).mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.P.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
            ((AlertController.AlertParams) this.P).mOnItemSelectedListener = onItemSelectedListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            ((AlertController.AlertParams) this.P).mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setOnShowListener(DialogInterface.OnShowListener onShowListener) {
            this.P.mOnShowListener = onShowListener;
            return this;
        }

        public Builder setPositiveButton(int i, DialogInterface.OnClickListener onClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mPositiveButtonText = ((AlertController.AlertParams) alertParams).mContext.getText(i);
            ((AlertController.AlertParams) this.P).mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setPositiveButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mPositiveButtonText = charSequence;
            ((AlertController.AlertParams) alertParams).mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setSingleChoiceItems(int i, int i2, DialogInterface.OnClickListener onClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mItems = ((AlertController.AlertParams) alertParams).mContext.getResources().getTextArray(i);
            AlertControllerWrapper.AlertParams alertParams2 = this.P;
            ((AlertController.AlertParams) alertParams2).mOnClickListener = onClickListener;
            ((AlertController.AlertParams) alertParams2).mCheckedItem = i2;
            ((AlertController.AlertParams) alertParams2).mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(Cursor cursor, int i, String str, DialogInterface.OnClickListener onClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mCursor = cursor;
            ((AlertController.AlertParams) alertParams).mOnClickListener = onClickListener;
            ((AlertController.AlertParams) alertParams).mCheckedItem = i;
            ((AlertController.AlertParams) alertParams).mLabelColumn = str;
            ((AlertController.AlertParams) alertParams).mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(ListAdapter listAdapter, int i, DialogInterface.OnClickListener onClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mAdapter = listAdapter;
            ((AlertController.AlertParams) alertParams).mOnClickListener = onClickListener;
            ((AlertController.AlertParams) alertParams).mCheckedItem = i;
            ((AlertController.AlertParams) alertParams).mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(CharSequence[] charSequenceArr, int i, DialogInterface.OnClickListener onClickListener) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mItems = charSequenceArr;
            ((AlertController.AlertParams) alertParams).mOnClickListener = onClickListener;
            ((AlertController.AlertParams) alertParams).mCheckedItem = i;
            ((AlertController.AlertParams) alertParams).mIsSingleChoice = true;
            return this;
        }

        public Builder setTitle(int i) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mTitle = ((AlertController.AlertParams) alertParams).mContext.getText(i);
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            ((AlertController.AlertParams) this.P).mTitle = charSequence;
            return this;
        }

        public Builder setView(int i) {
            AlertControllerWrapper.AlertParams alertParams = this.P;
            ((AlertController.AlertParams) alertParams).mView = ((AlertController.AlertParams) alertParams).mInflater.inflate(i, (ViewGroup) null);
            return this;
        }

        public Builder setView(View view) {
            ((AlertController.AlertParams) this.P).mView = view;
            return this;
        }

        public AlertDialog show() {
            AlertDialog create = create();
            create.show();
            return create;
        }
    }

    protected AlertDialog(Context context) {
        this(context, resolveDialogTheme(context, 0));
    }

    protected AlertDialog(Context context, int i) {
        super(context, resolveDialogTheme(context, i));
        this.mAlert = new AlertControllerWrapper(context, this, getWindow());
    }

    static int resolveDialogTheme(Context context, int i) {
        switch (i) {
            case 2:
                return R.style.Theme_Dark_Dialog_Alert;
            case 3:
                return R.style.Theme_Light_Dialog_Alert;
            case 4:
                return com.miui.internal.R.style.Theme_Dark_Dialog_Edit;
            case 5:
                return com.miui.internal.R.style.Theme_Light_Dialog_Edit;
            case 6:
                return com.miui.internal.R.style.Theme_Dark_Dialog_Edit_Default;
            case 7:
                return com.miui.internal.R.style.Theme_Light_Dialog_Edit_Default;
            case 8:
                return R.style.Theme_DayNight_Dialog_Alert;
            case 9:
                return com.miui.internal.R.style.Theme_DayNight_Dialog_Edit;
            case 10:
                return com.miui.internal.R.style.Theme_DayNight_Dialog_Edit_Default;
            default:
                if (i >= 16777216) {
                    return i;
                }
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(16843529, typedValue, true);
                return typedValue.resourceId;
        }
    }

    public Button getButton(int i) {
        return this.mAlert.getButton(i);
    }

    public boolean[] getCheckedItems() {
        return this.mAlert.getCheckedItems();
    }

    public ListView getListView() {
        return this.mAlert.getListView();
    }

    public TextView getMessageView() {
        return this.mAlert.getMessageView();
    }

    public ActionBar getMiuiActionBar() {
        return this.mAlert.getImpl().getActionBar();
    }

    public boolean isChecked() {
        return this.mAlert.isChecked();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View decorView = getWindow().getDecorView();
        if (decorView != null && this.mAlert.mHapticFeedbackEnabled && HapticFeedbackUtil.isSupportLinearMotorVibrate(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_POPUP_LIGHT)) {
            decorView.performHapticFeedback(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_POPUP_LIGHT);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mAlert.installContent();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.mAlert.onKeyDown(i, keyEvent) || super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return this.mAlert.onKeyUp(i, keyEvent) || super.onKeyUp(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.mAlert.onStart();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        this.mAlert.onStop();
    }

    public void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.mAlert.setButton(i, charSequence, onClickListener, null);
    }

    public void setButton(int i, CharSequence charSequence, Message message) {
        this.mAlert.setButton(i, charSequence, null, message);
    }

    public void setCustomTitle(View view) {
        this.mAlert.setCustomTitle(view);
    }

    public void setHapticFeedbackEnabled(boolean z) {
        this.mAlert.mHapticFeedbackEnabled = z;
    }

    public void setIcon(int i) {
        this.mAlert.setIcon(i);
    }

    public void setIcon(Drawable drawable) {
        this.mAlert.setIcon(drawable);
    }

    public void setIconAttribute(int i) {
        this.mAlert.setIcon(AttributeResolver.resolve(getContext(), i));
    }

    public void setMessage(CharSequence charSequence) {
        this.mAlert.setMessage(charSequence);
    }

    @Override // android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        this.mAlert.setTitle(charSequence);
    }

    public void setView(View view) {
        this.mAlert.setView(view);
    }
}
