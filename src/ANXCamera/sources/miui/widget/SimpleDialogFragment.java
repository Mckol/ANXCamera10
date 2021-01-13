package miui.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class SimpleDialogFragment extends DialogFragment {
    public static final String ARG_CANCELABLE = "cancelable";
    public static final String ARG_MESSAGE = "msg_res_id";
    public static final String ARG_TITLE = "title";
    public static final String ARG_TYPE = "type";
    public static final int TYPE_ALERT = 1;
    public static final int TYPE_PROGRESS = 2;
    private boolean mCancelable = true;
    private String mMessage;
    private DialogInterface.OnClickListener mNegativeButtonClickListener;
    private int mNegativeButtonTextRes;
    private DialogInterface.OnDismissListener mOnDismissListener;
    private DialogInterface.OnClickListener mPositiveButtonClickListener;
    private int mPositiveButtonTextRes;
    private String mTitle;
    private int mType;

    public static final class AlertDialogFragmentBuilder {
        private boolean mCancelable = true;
        private boolean mCreated;
        private String mMessage;
        private String mTitle;
        private int mType;

        public AlertDialogFragmentBuilder(int i) {
            this.mType = i;
        }

        public SimpleDialogFragment create() {
            if (!this.mCreated) {
                this.mCreated = true;
                SimpleDialogFragment simpleDialogFragment = new SimpleDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", this.mTitle);
                bundle.putString(SimpleDialogFragment.ARG_MESSAGE, this.mMessage);
                bundle.putBoolean(SimpleDialogFragment.ARG_CANCELABLE, this.mCancelable);
                bundle.putInt("type", this.mType);
                simpleDialogFragment.setArguments(bundle);
                return simpleDialogFragment;
            }
            throw new IllegalStateException("dialog has been created");
        }

        public AlertDialogFragmentBuilder setCancelable(boolean z) {
            this.mCancelable = z;
            return this;
        }

        public AlertDialogFragmentBuilder setMessage(String str) {
            this.mMessage = str;
            return this;
        }

        public AlertDialogFragmentBuilder setTitle(String str) {
            this.mTitle = str;
            return this;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mType = arguments.getInt("type");
            this.mMessage = arguments.getString(ARG_MESSAGE);
            this.mTitle = arguments.getString("title");
            this.mCancelable = arguments.getBoolean(ARG_CANCELABLE, true);
            return;
        }
        throw new IllegalStateException("no argument");
    }

    public Dialog onCreateDialog(Bundle bundle) {
        int i = this.mType;
        if (i == 1) {
            AlertDialog.Builder title = new AlertDialog.Builder(getActivity()).setMessage(this.mMessage).setCancelable(this.mCancelable).setTitle(this.mTitle);
            int i2 = this.mPositiveButtonTextRes;
            if (i2 > 0) {
                title.setPositiveButton(i2, this.mPositiveButtonClickListener);
            }
            int i3 = this.mNegativeButtonTextRes;
            if (i3 > 0) {
                title.setNegativeButton(i3, this.mNegativeButtonClickListener);
            }
            return title.create();
        } else if (i == 2) {
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(this.mMessage);
            progressDialog.setCancelable(this.mCancelable);
            return progressDialog;
        } else {
            throw new IllegalStateException("unknown dialog type:" + this.mType);
        }
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        DialogInterface.OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialogInterface);
        }
    }

    public void setNegativeButton(int i, DialogInterface.OnClickListener onClickListener) {
        this.mNegativeButtonTextRes = i;
        this.mNegativeButtonClickListener = onClickListener;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setPositiveButton(int i, DialogInterface.OnClickListener onClickListener) {
        this.mPositiveButtonTextRes = i;
        this.mPositiveButtonClickListener = onClickListener;
    }
}
