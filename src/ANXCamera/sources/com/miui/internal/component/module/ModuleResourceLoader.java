package com.miui.internal.component.module;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.ResourcesImpl;
import android.os.Build;
import com.miui.internal.util.PackageConstants;
import com.miui.internal.variable.Android_App_ActivityThread_class;
import com.miui.internal.variable.Android_App_LoadedApk_class;
import com.miui.internal.variable.Android_App_ResourcesManager_class;
import com.miui.internal.variable.Android_Content_Res_AssetManager_class;
import com.miui.internal.variable.Android_Content_Res_ResourcesImpl_class;
import com.miui.internal.variable.Android_Content_Res_Resources_class;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class ModuleResourceLoader {
    /* JADX WARNING: Removed duplicated region for block: B:30:? A[ExcHandler: ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException (unused java.lang.Throwable), SYNTHETIC, Splitter:B:17:0x0066] */
    private static void addAssetPath(Resources resources, String str, List<String> list, List<String> list2) {
        Map map;
        AssetManager assets;
        Android_Content_Res_AssetManager_class android_Content_Res_AssetManager_class = Android_Content_Res_AssetManager_class.Factory.getInstance().get();
        AssetManager assets2 = resources.getAssets();
        if (Build.VERSION.SDK_INT < 21) {
            assets2 = android_Content_Res_AssetManager_class.newInstance();
            android_Content_Res_AssetManager_class.addAssetPath(assets2, str);
            if (list != null) {
                for (String str2 : list) {
                    android_Content_Res_AssetManager_class.addAssetPath(assets2, str2);
                }
            }
        }
        for (String str3 : list2) {
            Android_Content_Res_ResourcesImpl_class android_Content_Res_ResourcesImpl_class = Android_Content_Res_ResourcesImpl_class.Factory.getInstance().get();
            Object resourcesManagerInstance = getResourcesManagerInstance();
            Field declaredField = resourcesManagerInstance.getClass().getDeclaredField("mResourceImpls");
            declaredField.setAccessible(true);
            synchronized (resourcesManagerInstance) {
                map = (Map) declaredField.get(resourcesManagerInstance);
                Android_App_ResourcesManager_class.appendAssetPath(str3);
            }
            if (map != null) {
                try {
                    for (Map.Entry entry : map.entrySet()) {
                        WeakReference weakReference = (WeakReference) entry.getValue();
                        if (!(weakReference.get() == null || (assets = android_Content_Res_ResourcesImpl_class.getAssets((ResourcesImpl) weakReference.get())) == assets2)) {
                            android_Content_Res_AssetManager_class.addAssetPath(assets, str3);
                        }
                    }
                } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException unused) {
                }
            }
            android_Content_Res_AssetManager_class.addAssetPath(assets2, str3);
        }
        if (Build.VERSION.SDK_INT < 21) {
            replaceAssetManager(resources, assets2);
        }
    }

    private static Object getResourcesManagerInstance() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method declaredMethod = Class.forName("android.app.ResourcesManager").getDeclaredMethod("getInstance", new Class[0]);
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke(null, new Object[0]);
    }

    public static void load(List<String> list) {
        load(list, null);
    }

    public static void load(List<String> list, List<String> list2) {
        if (PackageConstants.sSdkStatus < 2) {
            load2Initial(list, list2);
        } else {
            load2Current(list, list2, PackageConstants.sApplication);
        }
    }

    public static void load2Current(List<String> list, List<String> list2, Context context) {
        addAssetPath(context.getResources(), context.getApplicationInfo().sourceDir, list2, list);
    }

    public static void load2Initial(List<String> list, List<String> list2) {
        Android_App_ActivityThread_class android_App_ActivityThread_class = Android_App_ActivityThread_class.Factory.getInstance().get();
        Object currentActivityThread = android_App_ActivityThread_class.currentActivityThread();
        Android_App_LoadedApk_class android_App_LoadedApk_class = Android_App_LoadedApk_class.Factory.getInstance().get();
        Object initialLoadedApk = android_App_ActivityThread_class.getInitialLoadedApk(currentActivityThread);
        addAssetPath(android_App_LoadedApk_class.getResources(initialLoadedApk), android_App_LoadedApk_class.getApplicationInfo(initialLoadedApk).sourceDir, list2, list);
    }

    public static void load2System(List<String> list, List<String> list2) {
        addAssetPath(Resources.getSystem(), null, list2, list);
    }

    private static void replaceAssetManager(Resources resources, AssetManager assetManager) {
        Resources resources2 = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
        AssetManager assets = resources.getAssets();
        Android_Content_Res_Resources_class android_Content_Res_Resources_class = Android_Content_Res_Resources_class.Factory.getInstance().get();
        android_Content_Res_Resources_class.setAssetManager(resources, assetManager);
        android_Content_Res_Resources_class.setAssetManager(resources2, null);
        assets.close();
        resources.updateConfiguration(resources.getConfiguration(), resources.getDisplayMetrics());
    }
}
