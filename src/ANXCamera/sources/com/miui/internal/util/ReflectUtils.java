package com.miui.internal.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {
    private ReflectUtils() {
    }

    public static Class<?> forName(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static Object get(Object obj, Field field) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static Field getDeclaredField(Class<?> cls, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField;
        } catch (NoSuchFieldException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static Field getDeclaredField(String str, String str2) {
        return getDeclaredField(forName(str), str2);
    }

    public static Method getDeclaredMethod(Class<?> cls, String str, Class<?>... clsArr) {
        try {
            Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (NoSuchMethodException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static Method getDeclaredMethod(String str, String str2, Class<?>... clsArr) {
        return getDeclaredMethod(forName(str), str2, clsArr);
    }

    public static Field getField(Class<?> cls, String str) {
        try {
            Field field = cls.getField(str);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static Field getField(String str, String str2) {
        return getField(forName(str), str2);
    }

    public static Method getMethod(Class<?> cls, String str, Class<?>... clsArr) {
        try {
            Method method = cls.getMethod(str, clsArr);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static Method getMethod(String str, String str2, Class<?>... clsArr) {
        return getMethod(forName(str), str2, clsArr);
    }

    public static Object invoke(Object obj, Method method, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException(e3);
        }
    }

    public static void set(Object obj, Field field, Object obj2) {
        try {
            field.set(obj, obj2);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }
}
