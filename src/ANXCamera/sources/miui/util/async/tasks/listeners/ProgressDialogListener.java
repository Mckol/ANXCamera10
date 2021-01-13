package miui.util.async.tasks.listeners;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import miui.app.ProgressDialog;
import miui.util.async.Task;
import miui.util.async.TaskManager;

public class ProgressDialogListener extends BaseTaskListener {
    static final LinkedHashMap<String, ProgressDialogListener> ALL_LISTENERS = new LinkedHashMap<>();
    boolean mCancelable = false;
    int mCurrentProgress = 0;
    WeakReference<Task<?>> mCurrentTask;
    ProgressDialogFragment mFragment;
    FragmentManager mFragmentManager;
    boolean mIndeterminate = false;
    int mMaxProgress = 0;
    CharSequence mMessage = null;
    int mMessageId = 0;
    int mProgressStyle = 0;
    int mTheme = 0;
    CharSequence mTitle = null;
    int mTitleId = 0;

    public static class ProgressDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
        private ProgressDialogListener mListener;

        public void onCancel(DialogInterface dialogInterface) {
            WeakReference<Task<?>> weakReference;
            Task<?> task;
            ProgressDialogListener progressDialogListener = this.mListener;
            if (!(progressDialogListener == null || !progressDialogListener.mCancelable || (weakReference = progressDialogListener.mCurrentTask) == null || (task = weakReference.get()) == null)) {
                task.cancel();
            }
            super.onCancel(dialogInterface);
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            onCancel(dialogInterface);
        }

        public Dialog onCreateDialog(Bundle bundle) {
            if (this.mListener == null) {
                return super.onCreateDialog(bundle);
            }
            ProgressDialog progressDialog = new ProgressDialog(getActivity(), this.mListener.mTheme);
            ProgressDialogListener progressDialogListener = this.mListener;
            int i = progressDialogListener.mTitleId;
            if (i != 0) {
                progressDialog.setTitle(i);
            } else {
                progressDialog.setTitle(progressDialogListener.mTitle);
            }
            ProgressDialogListener progressDialogListener2 = this.mListener;
            int i2 = progressDialogListener2.mMessageId;
            if (i2 != 0) {
                progressDialog.setTitle(i2);
            } else {
                progressDialog.setMessage(progressDialogListener2.mMessage);
            }
            progressDialog.setProgressStyle(this.mListener.mProgressStyle);
            progressDialog.setIndeterminate(this.mListener.mIndeterminate);
            ProgressDialogListener progressDialogListener3 = this.mListener;
            if (progressDialogListener3.mIndeterminate) {
                progressDialog.setMax(progressDialogListener3.mMaxProgress);
                progressDialog.setProgress(this.mListener.mCurrentProgress);
            }
            if (this.mListener.mCancelable) {
                progressDialog.setButton(-2, progressDialog.getContext().getText(17039360), this);
                progressDialog.setCancelable(true);
            } else {
                progressDialog.setButton(-2, (CharSequence) null, (DialogInterface.OnClickListener) null);
                progressDialog.setCancelable(false);
            }
            return progressDialog;
        }

        public void onResume() {
            super.onResume();
            this.mListener = ProgressDialogListener.ALL_LISTENERS.get(getArguments().getString("ProgressDialogListener"));
            ProgressDialogListener progressDialogListener = this.mListener;
            if (progressDialogListener == null) {
                FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
                beginTransaction.remove(this);
                beginTransaction.commit();
                return;
            }
            progressDialogListener.mFragment = this;
            progressDialogListener.mFragmentManager = getFragmentManager();
            WeakReference<Task<?>> weakReference = this.mListener.mCurrentTask;
            Task<?> task = null;
            if (weakReference != null) {
                task = weakReference.get();
            }
            if (task == null || !task.isRunning()) {
                dismiss();
                ProgressDialogListener.ALL_LISTENERS.remove(getArguments().getString("ProgressDialogListener"));
            }
        }

        public void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            ProgressDialogListener progressDialogListener = this.mListener;
            if (progressDialogListener != null) {
                progressDialogListener.mFragment = null;
                progressDialogListener.mFragmentManager = null;
                this.mListener = null;
            }
        }

        /* access modifiers changed from: package-private */
        public void setIndeterminate(boolean z) {
            Dialog dialog = getDialog();
            if (dialog instanceof ProgressDialog) {
                ((ProgressDialog) dialog).setIndeterminate(z);
            }
        }

        /* access modifiers changed from: package-private */
        public void setMaxProgress(int i) {
            Dialog dialog = getDialog();
            if (dialog instanceof ProgressDialog) {
                ((ProgressDialog) dialog).setMax(i);
            }
        }

        /* access modifiers changed from: package-private */
        public void setProgress(int i) {
            Dialog dialog = getDialog();
            if (dialog instanceof ProgressDialog) {
                ((ProgressDialog) dialog).setProgress(i);
            }
        }
    }

    public ProgressDialogListener(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    @Override // miui.util.async.Task.Listener, miui.util.async.tasks.listeners.BaseTaskListener
    public void onFinalize(TaskManager taskManager, Task<?> task) {
        ProgressDialogFragment progressDialogFragment = this.mFragment;
        if (progressDialogFragment != null) {
            progressDialogFragment.dismiss();
        }
        LinkedHashMap<String, ProgressDialogListener> linkedHashMap = ALL_LISTENERS;
        linkedHashMap.remove("ProgressDialogListener@" + hashCode());
    }

    @Override // miui.util.async.Task.Listener, miui.util.async.tasks.listeners.BaseTaskListener
    public void onPrepare(TaskManager taskManager, Task<?> task) {
        this.mCurrentTask = new WeakReference<>(task);
        try {
            String str = "ProgressDialogListener@" + hashCode();
            ALL_LISTENERS.put(str, this);
            this.mFragment = new ProgressDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("ProgressDialogListener", str);
            this.mFragment.setArguments(bundle);
            this.mFragment.show(this.mFragmentManager, str);
        } catch (Exception e2) {
            Log.w("ProgressDialogListener", "cannot show dialog", e2);
            this.mFragment = null;
            this.mFragmentManager = null;
            task.removeListener(this);
        }
    }

    @Override // miui.util.async.Task.Listener, miui.util.async.tasks.listeners.BaseTaskListener
    public void onProgress(TaskManager taskManager, Task<?> task, int i, int i2) {
        ProgressDialogFragment progressDialogFragment = this.mFragment;
        if (i >= 0) {
            if (this.mIndeterminate) {
                setIndeterminate(false);
                if (progressDialogFragment != null) {
                    progressDialogFragment.setIndeterminate(this.mIndeterminate);
                }
            }
            if (this.mMaxProgress != i) {
                setMaxProgress(i);
                if (progressDialogFragment != null) {
                    progressDialogFragment.setMaxProgress(this.mMaxProgress);
                }
            }
            if (this.mCurrentProgress != i2) {
                this.mCurrentProgress = i2;
                if (progressDialogFragment != null) {
                    progressDialogFragment.setProgress(this.mCurrentProgress);
                }
            }
        } else if (!this.mIndeterminate) {
            setIndeterminate(true);
            if (progressDialogFragment != null) {
                progressDialogFragment.setIndeterminate(this.mIndeterminate);
            }
        }
    }

    public ProgressDialogListener setCancelable(boolean z) {
        this.mCancelable = z;
        return this;
    }

    public ProgressDialogListener setIndeterminate(boolean z) {
        this.mIndeterminate = z;
        return this;
    }

    public ProgressDialogListener setMaxProgress(int i) {
        this.mMaxProgress = i;
        return this;
    }

    public ProgressDialogListener setMessage(int i) {
        this.mMessageId = i;
        this.mMessage = null;
        return this;
    }

    public ProgressDialogListener setMessage(CharSequence charSequence) {
        this.mMessage = charSequence;
        this.mMessageId = 0;
        return this;
    }

    public ProgressDialogListener setProgressStyle(int i) {
        this.mProgressStyle = i;
        return this;
    }

    public ProgressDialogListener setTheme(int i) {
        this.mTheme = i;
        return this;
    }

    public ProgressDialogListener setTitle(int i) {
        this.mTitleId = i;
        this.mTitle = null;
        return this;
    }

    public ProgressDialogListener setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        this.mTitleId = 0;
        return this;
    }
}
