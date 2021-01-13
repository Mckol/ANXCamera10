package com.miui.internal.variable;

import android.widget.GridView;
import com.miui.internal.util.ClassProxy;
import miui.util.SoftReferenceSingleton;

public abstract class Android_Widget_GridView_class extends ClassProxy<GridView> implements IManagedClassProxy {

    public static class Factory extends AbsClassFactory {
        private static final SoftReferenceSingleton<Factory> INSTANCE = new SoftReferenceSingleton<Factory>() {
            /* class com.miui.internal.variable.Android_Widget_GridView_class.Factory.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // miui.util.SoftReferenceSingleton
            public Factory createInstance() {
                return new Factory();
            }
        };
        private Android_Widget_GridView_class Android_Widget_GridView_class;

        private Factory() {
            this.Android_Widget_GridView_class = (Android_Widget_GridView_class) create("Android_Widget_GridView_class");
        }

        public static Factory getInstance() {
            return INSTANCE.get();
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_Widget_GridView_class get() {
            return this.Android_Widget_GridView_class;
        }
    }

    public Android_Widget_GridView_class() {
        super(GridView.class);
    }
}
