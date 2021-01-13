package com.android.camera2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.ViewTreeObserver;
import com.android.camera.log.Log;

public final class DetachableClickListener implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
    private static final String TAG = "DetachableClickListener";
    private DialogInterface.OnClickListener delegateOrNull;
    private DialogInterface.OnCancelListener mCancelListener;

    private DetachableClickListener(DialogInterface.OnClickListener onClickListener, DialogInterface.OnCancelListener onCancelListener) {
        this.delegateOrNull = onClickListener;
        this.mCancelListener = onCancelListener;
    }

    public static DetachableClickListener wrap(DialogInterface.OnClickListener onClickListener, DialogInterface.OnCancelListener onCancelListener) {
        return new DetachableClickListener(onClickListener, onCancelListener);
    }

    public void clearOnDetach(Dialog dialog) {
        dialog.getWindow().getDecorView().getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
            /* class com.android.camera2.DetachableClickListener.AnonymousClass1 */

            public void onWindowAttached() {
                Log.v(DetachableClickListener.TAG, "dialog attach to window");
            }

            public void onWindowDetached() {
                DetachableClickListener.this.delegateOrNull = null;
                DetachableClickListener.this.mCancelListener = null;
            }
        });
    }

    public void onCancel(DialogInterface dialogInterface) {
        DialogInterface.OnCancelListener onCancelListener = this.mCancelListener;
        if (onCancelListener != null) {
            onCancelListener.onCancel(dialogInterface);
        }
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        DialogInterface.OnClickListener onClickListener = this.delegateOrNull;
        if (onClickListener != null) {
            onClickListener.onClick(dialogInterface, i);
        }
    }
}
