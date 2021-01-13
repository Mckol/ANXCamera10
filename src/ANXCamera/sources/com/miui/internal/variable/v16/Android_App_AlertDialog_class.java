package com.miui.internal.variable.v16;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.miui.internal.variable.AlertControllerWrapper;
import com.miui.internal.variable.VariableExceptionHandler;
import com.miui.internal.variable.api.Overridable;
import com.miui.internal.variable.api.v29.Android_App_AlertDialog;
import miui.reflect.Field;

public class Android_App_AlertDialog_class extends com.miui.internal.variable.Android_App_AlertDialog_class implements Overridable<Android_App_AlertDialog.Interface> {
    private static final Field mAlertField = Field.of(AlertDialog.class, "mAlert", "Lcom/android/internal/app/AlertController;");
    private Android_App_AlertDialog.Interface mImpl = new Android_App_AlertDialog.Interface() {
        /* class com.miui.internal.variable.v16.Android_App_AlertDialog_class.AnonymousClass1 */

        @Override // com.miui.internal.variable.api.v29.Android_App_AlertDialog.Interface
        public void init(AlertDialog alertDialog, Context context, int i, boolean z) {
            Android_App_AlertDialog_class.this.handle_init_(0, alertDialog, context, i, z);
        }

        @Override // com.miui.internal.variable.api.v29.Android_App_AlertDialog.Interface
        public void init(AlertDialog alertDialog, Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
            Android_App_AlertDialog_class.this.handle_init_(0, alertDialog, context, z, onCancelListener);
        }
    };
    private Android_App_AlertDialog.Interface mOriginal;

    private void updateAlertField(AlertDialog alertDialog) {
        try {
            mAlertField.set(alertDialog, new AlertControllerWrapper(alertDialog.getContext(), alertDialog, alertDialog.getWindow()));
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.app.AlertDialog.mAlert", e2);
        }
    }

    @Override // com.miui.internal.variable.api.Overridable
    public Android_App_AlertDialog.Interface asInterface() {
        return this.mImpl;
    }

    public void bind(Android_App_AlertDialog.Interface r1) {
        this.mOriginal = r1;
    }

    @Override // com.miui.internal.variable.IManagedClassProxy
    public void buildProxy() {
        attachConstructor("(Landroid/content/Context;IZ)V");
        attachConstructor("(Landroid/content/Context;ZLandroid/content/DialogInterface$OnCancelListener;)V");
    }

    /* access modifiers changed from: protected */
    public void callOriginal_init_(long j, AlertDialog alertDialog, Context context, int i, boolean z) {
        if (this.mOriginal == null) {
            original_init_(j, alertDialog, context, i, z);
        }
    }

    /* access modifiers changed from: protected */
    public void callOriginal_init_(long j, AlertDialog alertDialog, Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        if (this.mOriginal == null) {
            original_init_(j, alertDialog, context, z, onCancelListener);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void handle() {
        handle_init_(0L, (AlertDialog) null, (Context) null, 0, false);
        handle_init_(0L, (AlertDialog) null, (Context) null, false, (DialogInterface.OnCancelListener) null);
    }

    /* access modifiers changed from: protected */
    public void handle_init_(long j, AlertDialog alertDialog, Context context, int i, boolean z) {
        callOriginal_init_(j, alertDialog, context, i, z);
        updateAlertField(alertDialog);
    }

    /* access modifiers changed from: protected */
    public void handle_init_(long j, AlertDialog alertDialog, Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        callOriginal_init_(j, alertDialog, context, z, onCancelListener);
        updateAlertField(alertDialog);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void onClassProxyDisabled() {
        Android_App_AlertDialog.Extension.get().setExtension(this);
    }

    /* access modifiers changed from: protected */
    public void original_init_(long j, AlertDialog alertDialog, Context context, int i, boolean z) {
        throw new IllegalStateException("com.miui.internal.variable.v16.Android_App_AlertDialog_class.original_init_(long, AlertDialog, Context, int, boolean)");
    }

    /* access modifiers changed from: protected */
    public void original_init_(long j, AlertDialog alertDialog, Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        throw new IllegalStateException("com.miui.internal.variable.v16.Android_App_AlertDialog_class.original_init_(long, AlertDialog, Context, boolean, OnCancelListener)");
    }
}
