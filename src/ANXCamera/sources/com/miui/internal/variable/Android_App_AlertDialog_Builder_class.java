package com.miui.internal.variable;

import android.app.AlertDialog;
import com.miui.internal.util.ClassProxy;
import miui.util.SoftReferenceSingleton;

public abstract class Android_App_AlertDialog_Builder_class extends ClassProxy<AlertDialog.Builder> implements IManagedClassProxy {

    public static class Factory extends AbsClassFactory {
        private static final SoftReferenceSingleton<Factory> INSTANCE = new SoftReferenceSingleton<Factory>() {
            /* class com.miui.internal.variable.Android_App_AlertDialog_Builder_class.Factory.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // miui.util.SoftReferenceSingleton
            public Factory createInstance() {
                return new Factory();
            }
        };
        private Android_App_AlertDialog_Builder_class Android_App_AlertDialog_Builder_class;

        private Factory() {
            this.Android_App_AlertDialog_Builder_class = (Android_App_AlertDialog_Builder_class) create("Android_App_AlertDialog_Builder_class");
        }

        public static Factory getInstance() {
            return INSTANCE.get();
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_App_AlertDialog_Builder_class get() {
            return this.Android_App_AlertDialog_Builder_class;
        }
    }

    public Android_App_AlertDialog_Builder_class() {
        super(AlertDialog.Builder.class);
    }
}
