package com.miui.internal.util;

import android.content.res.AssetManager;
import android.content.res.Resources;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ResourcesUtils {
    private static Method ASSET_MANAGER_ADD_ASSET_PATH = AssetManager.class.getMethod("addAssetPath", String.class);
    private static Constructor<AssetManager> ASSET_MANAGER_CONSTRUCTOR = AssetManager.class.getConstructor(new Class[0]);

    static {
        try {
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        }
    }

    private ResourcesUtils() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x003e  */
    public static Resources createResources(Resources resources, String... strArr) {
        AssetManager assetManager;
        InstantiationException e2;
        IllegalAccessException e3;
        InvocationTargetException e4;
        try {
            assetManager = ASSET_MANAGER_CONSTRUCTOR.newInstance(new Object[0]);
            try {
                for (String str : strArr) {
                    ASSET_MANAGER_ADD_ASSET_PATH.invoke(assetManager, str);
                }
            } catch (InstantiationException e5) {
                e2 = e5;
                e2.printStackTrace();
                if (resources == null) {
                }
            } catch (IllegalAccessException e6) {
                e3 = e6;
                e3.printStackTrace();
                if (resources == null) {
                }
            } catch (InvocationTargetException e7) {
                e4 = e7;
                e4.printStackTrace();
                if (resources == null) {
                }
            }
        } catch (InstantiationException e8) {
            e2 = e8;
            assetManager = null;
            e2.printStackTrace();
            if (resources == null) {
            }
        } catch (IllegalAccessException e9) {
            e3 = e9;
            assetManager = null;
            e3.printStackTrace();
            if (resources == null) {
            }
        } catch (InvocationTargetException e10) {
            e4 = e10;
            assetManager = null;
            e4.printStackTrace();
            if (resources == null) {
            }
        }
        return resources == null ? new Resources(assetManager, null, null) : new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
    }

    public static Resources createResources(String... strArr) {
        return createResources(null, strArr);
    }
}
