package com.miui.internal.variable;

import com.miui.internal.util.ClassProxy;
import java.util.ArrayList;
import java.util.List;

public abstract class Android_App_ResourcesManager_class extends ClassProxy<Object> implements IManagedClassProxy {
    protected static final Class<?> ResourcsManager_Class = getClass("android.app.ResourcesManager");
    protected static List<String> mAppendedAssetPaths;

    public static class Factory extends AbsClassFactory {
        private Android_App_ResourcesManager_class Android_App_ResourcesManager_class;

        private static final class Holder {
            static final Factory INSTANCE = new Factory();

            private Holder() {
            }
        }

        private Factory() {
            this.Android_App_ResourcesManager_class = (Android_App_ResourcesManager_class) create("Android_App_ResourcesManager_class");
        }

        public static Factory getInstance() {
            return Holder.INSTANCE;
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_App_ResourcesManager_class get() {
            return this.Android_App_ResourcesManager_class;
        }
    }

    public Android_App_ResourcesManager_class() {
        super(ResourcsManager_Class);
    }

    public static void appendAssetPath(String str) {
        if (mAppendedAssetPaths == null) {
            mAppendedAssetPaths = new ArrayList();
        }
        for (String str2 : mAppendedAssetPaths) {
            if (str2.equals(str)) {
                return;
            }
        }
        mAppendedAssetPaths.add(str);
    }

    protected static Class<?> getClass(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.app.ResourcesManager", e2);
            return null;
        }
    }
}
