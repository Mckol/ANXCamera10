package com.miui.internal.variable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import miui.util.SoftReferenceSingleton;

public abstract class Android_Graphics_Drawable_StateListDrawable_class extends Android_Graphics_Drawable_Drawable_class {

    public static class Factory extends AbsClassFactory {
        private static final SoftReferenceSingleton<Factory> INSTANCE = new SoftReferenceSingleton<Factory>() {
            /* class com.miui.internal.variable.Android_Graphics_Drawable_StateListDrawable_class.Factory.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // miui.util.SoftReferenceSingleton
            public Factory createInstance() {
                return new Factory();
            }
        };
        private Android_Graphics_Drawable_StateListDrawable_class Android_Graphics_Drawable_StateListDrawable_class;

        private Factory() {
            this.Android_Graphics_Drawable_StateListDrawable_class = (Android_Graphics_Drawable_StateListDrawable_class) create("Android_Graphics_Drawable_StateListDrawable_class");
        }

        public static Factory getInstance() {
            return INSTANCE.get();
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_Graphics_Drawable_StateListDrawable_class get() {
            return this.Android_Graphics_Drawable_StateListDrawable_class;
        }
    }

    public abstract int getStateCount(StateListDrawable stateListDrawable);

    public abstract Drawable getStateDrawable(StateListDrawable stateListDrawable, int i);

    public abstract int[] getStateSet(StateListDrawable stateListDrawable, int i);
}
