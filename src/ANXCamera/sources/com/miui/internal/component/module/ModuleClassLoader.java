package com.miui.internal.component.module;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

public class ModuleClassLoader {
    private static final String DEX_ELEMENTS_FIELD_NAME = "dexElements";
    private static final String DEX_PATH_LIST_CLASS_NAME = "dalvik.system.DexPathList";
    private static final String DEX_PATH_LIST_ELEMENT_CLASS_NAME = "dalvik.system.DexPathList$Element";
    private static final String DEX_PATH_LIST_NATIVE_ELEMENT_CLASS_NAME = "dalvik.system.DexPathList$NativeLibraryElement";
    private static final String LIB_ELEMENTS_FIELD_NAME = "nativeLibraryPathElements";
    private static final String TAG = "ModuleClassLoader";

    private static Object getDexPathListVariable(ClassLoader classLoader) throws NoSuchFieldException {
        if (classLoader instanceof BaseDexClassLoader) {
            Field[] declaredFields = BaseDexClassLoader.class.getDeclaredFields();
            for (Field field : declaredFields) {
                if (DEX_PATH_LIST_CLASS_NAME.equals(field.getType().getName())) {
                    field.setAccessible(true);
                    try {
                        return field.get(classLoader);
                    } catch (IllegalAccessException | IllegalArgumentException unused) {
                        continue;
                    }
                }
            }
        }
        throw new NoSuchFieldException("dexPathList field not found.");
    }

    private static Field getElementsField(Object obj, String str, String str2) throws NoSuchFieldException {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getName().equals(str)) {
                Class<?> type = field.getType();
                if (type.isArray() && str2.equals(type.getComponentType().getName())) {
                    field.setAccessible(true);
                    return field;
                }
            }
        }
        throw new NoSuchFieldException(str + " field not found.");
    }

    private static Field getNativeLibraryDirectoriesField(Object obj) throws NoSuchFieldException {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            Class<?> type = field.getType();
            if (type.isArray() && type.getComponentType() == File.class) {
                field.setAccessible(true);
                return field;
            }
        }
        throw new NoSuchFieldException("nativeLibraryDirectories field not found.");
    }

    private static void invokeAddDexPath(ClassLoader classLoader, String str) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (classLoader instanceof BaseDexClassLoader) {
            Method method = Class.forName(classLoader.getClass().getName()).getMethod("addDexPath", String.class);
            method.setAccessible(true);
            method.invoke(classLoader, str);
            return;
        }
        throw new NoSuchMethodException("addDexPath method not found.");
    }

    private static void invokeAddNativePath(ClassLoader classLoader, String str) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (classLoader instanceof BaseDexClassLoader) {
            Method method = Class.forName(classLoader.getClass().getName()).getMethod("addNativePath", Collection.class);
            method.setAccessible(true);
            method.invoke(classLoader, Collections.singletonList(str));
            return;
        }
        throw new NoSuchMethodException("addNativePath method not found.");
    }

    public static boolean load(String str, String str2, String str3, ClassLoader classLoader) {
        return load(str, str2, str3, classLoader, null);
    }

    static boolean load(String str, String str2, String str3, ClassLoader classLoader, Context context) {
        String str4;
        String str5;
        if (str == null && (str3 == null || context == null)) {
            return false;
        }
        try {
            Object dexPathListVariable = getDexPathListVariable(classLoader);
            if (str != null) {
                str4 = str2;
                str5 = str;
            } else if (Build.VERSION.SDK_INT < 23) {
                loadLibBeforeAndroidM(dexPathListVariable, str3);
                return true;
            } else {
                str5 = context.getApplicationInfo().sourceDir;
                str4 = null;
            }
            if (Build.VERSION.SDK_INT < 28) {
                Object dexPathListVariable2 = getDexPathListVariable(str4 == null ? new PathClassLoader(str5, str3, classLoader.getParent()) : new DexClassLoader(str5, str4, str3, classLoader.getParent()));
                if (str != null) {
                    loadDex(dexPathListVariable, dexPathListVariable2);
                }
                if (str3 != null) {
                    loadLibrary(dexPathListVariable, dexPathListVariable2, str3);
                }
            } else {
                if (str5 != null) {
                    invokeAddDexPath(classLoader, str5);
                }
                if (str3 != null) {
                    invokeAddNativePath(classLoader, str3);
                }
            }
            return true;
        } catch (IllegalArgumentException e2) {
            Log.e(TAG, "Illegal arguments", e2);
            return false;
        } catch (IllegalAccessException e3) {
            Log.e(TAG, "Illegal access", e3);
            return false;
        } catch (ClassNotFoundException e4) {
            Log.e(TAG, "No class found", e4);
            return false;
        } catch (NoSuchFieldException e5) {
            Log.e(TAG, "No field found", e5);
            return false;
        } catch (NoSuchMethodException e6) {
            Log.e(TAG, "no method found", e6);
            return false;
        } catch (InvocationTargetException e7) {
            Log.e(TAG, "Invocation target", e7);
            return false;
        }
    }

    private static void loadDex(Object obj, Object obj2) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        replaceElement(obj, obj2, DEX_ELEMENTS_FIELD_NAME, DEX_PATH_LIST_ELEMENT_CLASS_NAME);
    }

    private static void loadLibBeforeAndroidM(Object obj, String str) throws NoSuchFieldException, IllegalAccessException {
        Field nativeLibraryDirectoriesField = getNativeLibraryDirectoriesField(obj);
        File[] fileArr = (File[]) nativeLibraryDirectoriesField.get(obj);
        File[] fileArr2 = new File[(fileArr.length + 1)];
        fileArr2[0] = new File(str);
        System.arraycopy(fileArr, 0, fileArr2, 1, fileArr.length);
        nativeLibraryDirectoriesField.set(obj, fileArr2);
    }

    private static void loadLibrary(Object obj, Object obj2, String str) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        int i = Build.VERSION.SDK_INT;
        if (i >= 26) {
            replaceElement(obj, obj2, LIB_ELEMENTS_FIELD_NAME, DEX_PATH_LIST_NATIVE_ELEMENT_CLASS_NAME);
        } else if (i >= 23) {
            replaceElement(obj, obj2, LIB_ELEMENTS_FIELD_NAME, DEX_PATH_LIST_ELEMENT_CLASS_NAME);
        } else {
            loadLibBeforeAndroidM(obj, str);
        }
    }

    private static void replaceElement(Object obj, Object obj2, String str, String str2) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        Field elementsField = getElementsField(obj, str, str2);
        Object[] objArr = (Object[]) elementsField.get(obj);
        Object[] objArr2 = (Object[]) Array.newInstance(Class.forName(str2), objArr.length + 1);
        objArr2[0] = ((Object[]) getElementsField(obj2, str, str2).get(obj2))[0];
        System.arraycopy(objArr, 0, objArr2, 1, objArr.length);
        elementsField.set(obj, objArr2);
    }
}
