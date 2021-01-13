package com.miui.internal.variable.api.v29;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.miui.internal.variable.api.AbstractExtension;

public interface Android_App_AlertDialog {

    public static class Extension extends AbstractExtension<Interface> {
        private static final Extension INSTANCE = new Extension();

        public static Extension get() {
            return INSTANCE;
        }
    }

    public interface Interface {
        void init(AlertDialog alertDialog, Context context, int i, boolean z);

        void init(AlertDialog alertDialog, Context context, boolean z, DialogInterface.OnCancelListener onCancelListener);
    }
}
