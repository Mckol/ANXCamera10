package com.miui.internal.variable.v16;

import android.app.AlertDialog;
import android.content.Context;
import com.miui.internal.variable.AlertControllerWrapper;
import com.miui.internal.variable.VariableExceptionHandler;
import com.miui.internal.variable.api.Overridable;
import com.miui.internal.variable.api.v29.Android_App_AlertDialog_Builder;
import miui.reflect.Field;

public class Android_App_AlertDialog_Builder_class extends com.miui.internal.variable.Android_App_AlertDialog_Builder_class implements Overridable<Android_App_AlertDialog_Builder.Interface> {
    private static final Field P = Field.of(AlertDialog.Builder.class, "P", "Lcom/android/internal/app/AlertController$AlertParams;");
    private Android_App_AlertDialog_Builder.Interface mImpl = new Android_App_AlertDialog_Builder.Interface() {
        /* class com.miui.internal.variable.v16.Android_App_AlertDialog_Builder_class.AnonymousClass1 */

        @Override // com.miui.internal.variable.api.v29.Android_App_AlertDialog_Builder.Interface
        public void init(AlertDialog.Builder builder, Context context, int i) {
            Android_App_AlertDialog_Builder_class.this.handle_init_(0, builder, context, i);
        }
    };
    private Android_App_AlertDialog_Builder.Interface mOriginal;

    @Override // com.miui.internal.variable.api.Overridable
    public Android_App_AlertDialog_Builder.Interface asInterface() {
        return this.mImpl;
    }

    public void bind(Android_App_AlertDialog_Builder.Interface r1) {
        this.mOriginal = r1;
    }

    @Override // com.miui.internal.variable.IManagedClassProxy
    public void buildProxy() {
        attachConstructor("(Landroid/content/Context;I)V");
    }

    /* access modifiers changed from: protected */
    public void callOriginal_init_(long j, AlertDialog.Builder builder, Context context, int i) {
        if (this.mOriginal == null) {
            original_init_(j, builder, context, i);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void handle() {
        handle_init_(0, null, null, 0);
    }

    /* access modifiers changed from: protected */
    public void handle_init_(long j, AlertDialog.Builder builder, Context context, int i) {
        callOriginal_init_(j, builder, context, i);
        try {
            P.set(builder, new AlertControllerWrapper.AlertParams(builder.getContext()));
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("P", e2);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void onClassProxyDisabled() {
        Android_App_AlertDialog_Builder.Extension.get().setExtension(this);
    }

    /* access modifiers changed from: protected */
    public void original_init_(long j, AlertDialog.Builder builder, Context context, int i) {
        throw new IllegalStateException("com.miui.internal.variable.v16.Android_App_AlertDialog_Builder_class.original_init_(long, AlertDialog.Builder, Context, int)");
    }
}
