package miui.os;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import java.util.HashMap;
import miui.app.ProgressDialog;

public abstract class AsyncTaskWithProgress<Params, Result> extends AsyncTask<Params, Integer, Result> {
    private static final HashMap<String, AsyncTaskWithProgress<?, ?>> sAllTasks = new HashMap<>();
    private boolean mCancelable = false;
    private int mCurrentProgress = 0;
    private volatile ProgressDialogFragment mFragment = null;
    private final FragmentManager mFragmentManager;
    private boolean mIndeterminate = false;
    private final AsyncTaskWithProgress<Params, Result>.Listeners mListeners = new Listeners();
    private int mMaxProgress = 0;
    private CharSequence mMessage = null;
    private int mMessageId = 0;
    private int mProgressStyle = 0;
    private int mTheme = 0;
    private CharSequence mTitle = null;
    private int mTitleId = 0;

    /* access modifiers changed from: private */
    public class Listeners implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
        private Listeners() {
        }

        public void onCancel(DialogInterface dialogInterface) {
            onClick(dialogInterface, -2);
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            Dialog dialog;
            if (AsyncTaskWithProgress.this.mFragment != null && (dialog = AsyncTaskWithProgress.this.mFragment.getDialog()) != null && dialogInterface == dialog && i == -2) {
                AsyncTaskWithProgress.this.cancel(true);
            }
        }
    }

    public static class ProgressDialogFragment extends DialogFragment {
        private AsyncTaskWithProgress<?, ?> mTask;

        static ProgressDialogFragment newInstance(String str) {
            ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("task", str);
            progressDialogFragment.setArguments(bundle);
            return progressDialogFragment;
        }

        public void onCancel(DialogInterface dialogInterface) {
            AsyncTaskWithProgress<?, ?> asyncTaskWithProgress = this.mTask;
            if (asyncTaskWithProgress != null && ((AsyncTaskWithProgress) asyncTaskWithProgress).mCancelable) {
                ((AsyncTaskWithProgress) this.mTask).mListeners.onCancel(dialogInterface);
            }
            super.onCancel(dialogInterface);
        }

        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            this.mTask = (AsyncTaskWithProgress) AsyncTaskWithProgress.sAllTasks.get(getArguments().getString("task"));
            if (this.mTask == null) {
                FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
                beginTransaction.remove(this);
                beginTransaction.commit();
            }
        }

        public Dialog onCreateDialog(Bundle bundle) {
            if (this.mTask == null) {
                return super.onCreateDialog(bundle);
            }
            ProgressDialog progressDialog = new ProgressDialog(getActivity(), ((AsyncTaskWithProgress) this.mTask).mTheme);
            if (((AsyncTaskWithProgress) this.mTask).mTitleId != 0) {
                progressDialog.setTitle(((AsyncTaskWithProgress) this.mTask).mTitleId);
            } else {
                progressDialog.setTitle(((AsyncTaskWithProgress) this.mTask).mTitle);
            }
            if (((AsyncTaskWithProgress) this.mTask).mMessageId != 0) {
                progressDialog.setMessage(getActivity().getText(((AsyncTaskWithProgress) this.mTask).mMessageId));
            } else {
                progressDialog.setMessage(((AsyncTaskWithProgress) this.mTask).mMessage);
            }
            progressDialog.setProgressStyle(((AsyncTaskWithProgress) this.mTask).mProgressStyle);
            progressDialog.setIndeterminate(((AsyncTaskWithProgress) this.mTask).mIndeterminate);
            if (!((AsyncTaskWithProgress) this.mTask).mIndeterminate) {
                progressDialog.setMax(((AsyncTaskWithProgress) this.mTask).mMaxProgress);
                progressDialog.setProgress(((AsyncTaskWithProgress) this.mTask).mCurrentProgress);
            }
            if (((AsyncTaskWithProgress) this.mTask).mCancelable) {
                progressDialog.setButton(-2, progressDialog.getContext().getText(17039360), ((AsyncTaskWithProgress) this.mTask).mListeners);
                progressDialog.setCancelable(true);
            } else {
                progressDialog.setButton(-2, (CharSequence) null, (DialogInterface.OnClickListener) null);
                progressDialog.setCancelable(false);
            }
            return progressDialog;
        }

        public void onStart() {
            super.onStart();
            AsyncTaskWithProgress<?, ?> asyncTaskWithProgress = this.mTask;
            if (asyncTaskWithProgress != null) {
                ((AsyncTaskWithProgress) asyncTaskWithProgress).mFragment = this;
            }
        }

        public void onStop() {
            AsyncTaskWithProgress<?, ?> asyncTaskWithProgress = this.mTask;
            if (asyncTaskWithProgress != null) {
                ((AsyncTaskWithProgress) asyncTaskWithProgress).mFragment = null;
            }
            super.onStop();
        }

        /* access modifiers changed from: package-private */
        public void setProgress(int i) {
            Dialog dialog = getDialog();
            if (dialog instanceof ProgressDialog) {
                ((ProgressDialog) dialog).setProgress(i);
            }
        }
    }

    public AsyncTaskWithProgress(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    private void dismissDialog() {
        FragmentManager fragmentManager = this.mFragmentManager;
        ProgressDialogFragment progressDialogFragment = (ProgressDialogFragment) fragmentManager.findFragmentByTag("AsyncTaskWithProgress@" + hashCode());
        if (progressDialogFragment != null) {
            progressDialogFragment.dismissAllowingStateLoss();
        }
    }

    public Activity getActivity() {
        if (this.mFragment != null) {
            return this.mFragment.getActivity();
        }
        return null;
    }

    public void onCancelled() {
        HashMap<String, AsyncTaskWithProgress<?, ?>> hashMap = sAllTasks;
        hashMap.remove("AsyncTaskWithProgress@" + hashCode());
        dismissDialog();
    }

    /* access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Result result) {
        HashMap<String, AsyncTaskWithProgress<?, ?>> hashMap = sAllTasks;
        hashMap.remove("AsyncTaskWithProgress@" + hashCode());
        dismissDialog();
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        String str = "AsyncTaskWithProgress@" + hashCode();
        sAllTasks.put(str, this);
        if (this.mFragmentManager != null) {
            this.mFragment = ProgressDialogFragment.newInstance(str);
            this.mFragment.setCancelable(this.mCancelable);
            this.mFragment.show(this.mFragmentManager, str);
        }
    }

    /* access modifiers changed from: protected */
    public void onProgressUpdate(Integer... numArr) {
        super.onProgressUpdate((Object[]) numArr);
        this.mCurrentProgress = numArr[0].intValue();
        if (this.mFragment != null) {
            this.mFragment.setProgress(this.mCurrentProgress);
        }
    }

    public AsyncTaskWithProgress<Params, Result> setCancelable(boolean z) {
        this.mCancelable = z;
        return this;
    }

    public AsyncTaskWithProgress<Params, Result> setIndeterminate(boolean z) {
        this.mIndeterminate = z;
        return this;
    }

    public AsyncTaskWithProgress<Params, Result> setMaxProgress(int i) {
        this.mMaxProgress = i;
        return this;
    }

    public AsyncTaskWithProgress<Params, Result> setMessage(int i) {
        this.mMessageId = i;
        this.mMessage = null;
        return this;
    }

    public AsyncTaskWithProgress<Params, Result> setMessage(CharSequence charSequence) {
        this.mMessageId = 0;
        this.mMessage = charSequence;
        return this;
    }

    public AsyncTaskWithProgress<Params, Result> setProgressStyle(int i) {
        this.mProgressStyle = i;
        return this;
    }

    public AsyncTaskWithProgress<Params, Result> setTheme(int i) {
        this.mTheme = i;
        return this;
    }

    public AsyncTaskWithProgress<Params, Result> setTitle(int i) {
        this.mTitleId = i;
        this.mTitle = null;
        return this;
    }

    public AsyncTaskWithProgress<Params, Result> setTitle(CharSequence charSequence) {
        this.mTitleId = 0;
        this.mTitle = charSequence;
        return this;
    }
}
