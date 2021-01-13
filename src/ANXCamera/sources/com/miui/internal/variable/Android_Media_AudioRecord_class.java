package com.miui.internal.variable;

import android.media.AudioRecord;
import miui.util.SoftReferenceSingleton;

public abstract class Android_Media_AudioRecord_class {

    public static class Factory extends AbsClassFactory {
        private static final SoftReferenceSingleton<Factory> INSTANCE = new SoftReferenceSingleton<Factory>() {
            /* class com.miui.internal.variable.Android_Media_AudioRecord_class.Factory.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // miui.util.SoftReferenceSingleton
            public Factory createInstance() {
                return new Factory();
            }
        };
        private Android_Media_AudioRecord_class Android_Media_AudioRecord_class;

        private Factory() {
            this.Android_Media_AudioRecord_class = (Android_Media_AudioRecord_class) create("Android_Media_AudioRecord_class");
        }

        public static Factory getInstance() {
            return INSTANCE.get();
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_Media_AudioRecord_class get() {
            return this.Android_Media_AudioRecord_class;
        }
    }

    public abstract boolean isExtraParamSupported();

    public abstract int setParameters(AudioRecord audioRecord, String str);
}
