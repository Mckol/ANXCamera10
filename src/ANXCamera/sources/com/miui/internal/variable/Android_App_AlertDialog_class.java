package com.miui.internal.variable;

import android.app.AlertDialog;
import com.miui.internal.util.ClassProxy;
import com.miui.internal.variable.Android_App_AlertDialog_Builder_class;
import miui.util.SoftReferenceSingleton;

public abstract class Android_App_AlertDialog_class extends ClassProxy<AlertDialog> implements IManagedClassProxy {

    public static class Factory extends AbsClassFactory {
        private static final SoftReferenceSingleton<Factory> INSTANCE = new SoftReferenceSingleton<Factory>() {
            /* class com.miui.internal.variable.Android_App_AlertDialog_class.Factory.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // miui.util.SoftReferenceSingleton
            public Factory createInstance() {
                return new Factory();
            }
        };
        private Android_App_AlertDialog_class Android_App_AlertDialog_class;

        private Factory() {
            this.Android_App_AlertDialog_class = (Android_App_AlertDialog_class) create("Android_App_AlertDialog_class");
            Android_App_AlertDialog_Builder_class.Factory.getInstance().getClass();
        }

        public static Factory getInstance() {
            return INSTANCE.get();
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_App_AlertDialog_class get() {
            return this.Android_App_AlertDialog_class;
        }
    }

    public Android_App_AlertDialog_class() {
        super(AlertDialog.class);
    }
}
