package com.miui.internal.variable.v16;

import android.app.Application;
import com.miui.internal.variable.VariableExceptionHandler;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class Android_App_ActivityThread_class implements com.miui.internal.variable.Android_App_ActivityThread_class {
    private static final String TAG = "Android_App_ActivityThread_class_v16";
    private Object mCurrentActivityThread = null;
    private volatile Application mCurrentApplication = null;
    private Object mInitialLoadedApk;
    private String mInitialPackageName;

    public Android_App_ActivityThread_class() {
        try {
            Method declaredMethod = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentActivityThread", new Class[0]);
            declaredMethod.setAccessible(true);
            this.mCurrentActivityThread = declaredMethod.invoke(null, new Object[0]);
        } catch (Exception e2) {
            VariableExceptionHandler.getInstance().onThrow("android.app.ActivityThread.currentActivityThread", e2);
        }
    }

    @Override // com.miui.internal.variable.Android_App_ActivityThread_class
    public Object currentActivityThread() {
        return this.mCurrentActivityThread;
    }

    @Override // com.miui.internal.variable.Android_App_ActivityThread_class
    public Application currentApplication() {
        try {
            if (this.mCurrentApplication == null) {
                Method declaredMethod = this.mCurrentActivityThread.getClass().getDeclaredMethod("currentApplication", new Class[0]);
                declaredMethod.setAccessible(true);
                this.mCurrentApplication = (Application) declaredMethod.invoke(null, new Object[0]);
            }
            return this.mCurrentApplication;
        } catch (Exception e2) {
            VariableExceptionHandler.getInstance().onThrow("android.app.ActivityThread.currentApplication", e2);
            return null;
        }
    }

    @Override // com.miui.internal.variable.Android_App_ActivityThread_class
    public Object getInitialLoadedApk(Object obj) {
        getInitialPackageName(obj);
        return this.mInitialLoadedApk;
    }

    @Override // com.miui.internal.variable.Android_App_ActivityThread_class
    public String getInitialPackageName(Object obj) {
        try {
            if (this.mInitialPackageName == null) {
                Field declaredField = obj.getClass().getDeclaredField("mPackages");
                declaredField.setAccessible(true);
                Map map = (Map) declaredField.get(obj);
                this.mInitialPackageName = (String) map.keySet().iterator().next();
                this.mInitialLoadedApk = ((WeakReference) map.values().iterator().next()).get();
            }
            return this.mInitialPackageName;
        } catch (Exception e2) {
            VariableExceptionHandler.getInstance().onThrow("android.app.ActivityThread.mPackages", e2);
            return null;
        }
    }
}
