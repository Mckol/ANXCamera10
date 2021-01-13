package miui.animation.property;

import android.util.ArrayMap;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import miui.animation.utils.CommonUtils;

public class ValueTargetObject {
    private static final String GET = "get";
    private static final String SET = "set";
    private Map<String, FieldInfo> mFieldMap = new ArrayMap();
    private Map<String, MethodInfo> mGetterMap = new ArrayMap();
    private Map<String, String> mGetterNameMap = new ArrayMap();
    private Map<String, Object> mMap = new ArrayMap();
    private WeakReference<Object> mRef;
    private Map<String, MethodInfo> mSetterMap = new ArrayMap();
    private Map<String, String> mSetterNameMap = new ArrayMap();
    private Object mTempObj;

    /* access modifiers changed from: private */
    public static class FieldInfo {
        Field field;

        private FieldInfo() {
        }
    }

    /* access modifiers changed from: private */
    public static class MethodInfo {
        Method method;

        private MethodInfo() {
        }
    }

    public ValueTargetObject(Object obj) {
        if (CommonUtils.isBuiltInClass(obj.getClass())) {
            this.mTempObj = obj;
        } else {
            this.mRef = new WeakReference<>(obj);
        }
    }

    private Field getField(Object obj, String str, Class<?> cls) {
        FieldInfo fieldInfo = this.mFieldMap.get(str);
        if (fieldInfo == null) {
            fieldInfo = new FieldInfo();
            fieldInfo.field = getFieldByType(obj, str, cls);
            this.mFieldMap.put(str, fieldInfo);
        }
        return fieldInfo.field;
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x000f */
    private Field getFieldByType(Object obj, String str, Class<?> cls) {
        Field field;
        try {
            field = obj.getClass().getDeclaredField(str);
            field.setAccessible(true);
        } catch (NoSuchFieldException unused) {
            field = null;
        }
        try {
            field = obj.getClass().getField(str);
        } catch (NoSuchFieldException unused2) {
        }
        if (field == null || field.getType() == cls) {
            return field;
        }
        return null;
    }

    private Method getMethod(Object obj, String str, Map<String, MethodInfo> map, Class<?>... clsArr) {
        MethodInfo methodInfo = map.get(str);
        if (methodInfo == null) {
            methodInfo = new MethodInfo();
            methodInfo.method = getMethod(obj, str, clsArr);
            map.put(str, methodInfo);
        }
        return methodInfo.method;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        return r1;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x000e */
    private Method getMethod(Object obj, String str, Class<?>... clsArr) {
        Method method = null;
        method = obj.getClass().getDeclaredMethod(str, clsArr);
        method.setAccessible(true);
        try {
            return obj.getClass().getMethod(str, clsArr);
        } catch (NoSuchMethodException unused) {
            return method;
        }
    }

    private String getMethodName(String str, String str2, Map<String, String> map) {
        String str3 = map.get(str);
        if (str3 != null) {
            return str3;
        }
        String str4 = str2 + Character.toUpperCase(str.charAt(0)) + str.substring(1);
        map.put(str, str4);
        return str4;
    }

    private Object getRefObject() {
        WeakReference<Object> weakReference = this.mRef;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    private <T> T getValueByField(Object obj, Field field) {
        try {
            return (T) field.get(obj);
        } catch (Exception unused) {
            return null;
        }
    }

    private <T> T invokeGetter(String str, Class<T> cls, Object obj) {
        Method method = getMethod(obj, getMethodName(str, GET, this.mGetterNameMap), this.mGetterMap, new Class[0]);
        if (method == null) {
            return null;
        }
        return (T) retToClz(invokeMethod(obj, method, new Object[0]), cls);
    }

    private <T> T invokeMethod(Object obj, Method method, Object... objArr) {
        if (method == null) {
            return null;
        }
        try {
            return (T) method.invoke(obj, objArr);
        } catch (Exception e2) {
            Log.d(CommonUtils.TAG, "ValueProperty.invokeMethod failed, " + method.getName(), e2);
            return null;
        }
    }

    private <T> T retToClz(Object obj, Class<T> cls) {
        if (!(obj instanceof Number)) {
            return null;
        }
        Number number = (Number) obj;
        if (cls == Float.class || cls == Float.TYPE) {
            return (T) Float.valueOf(number.floatValue());
        }
        if (cls == Integer.class || cls == Integer.TYPE) {
            return (T) Integer.valueOf(number.intValue());
        }
        throw new IllegalArgumentException("getPropertyValue, clz must be float or int instead of " + cls);
    }

    private <T> void setValueByField(Object obj, Field field, T t) {
        try {
            field.set(obj, t);
        } catch (Exception unused) {
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != ValueTargetObject.class) {
            Object obj2 = this.mTempObj;
            if (obj2 != null) {
                return Objects.equals(obj2, obj);
            }
            Object refObject = getRefObject();
            if (refObject != null) {
                return Objects.equals(refObject, obj);
            }
            return false;
        }
        ValueTargetObject valueTargetObject = (ValueTargetObject) obj;
        Object obj3 = this.mTempObj;
        return obj3 != null ? Objects.equals(obj3, valueTargetObject.mTempObj) : Objects.equals(getRefObject(), valueTargetObject.getRefObject());
    }

    public <T> T getPropertyValue(String str, Class<T> cls) {
        Object refObject = getRefObject();
        if (this.mTempObj == null && refObject != null) {
            T t = (T) invokeGetter(str, cls, refObject);
            if (t != null) {
                return t;
            }
            Field field = getField(refObject, str, cls);
            if (field != null) {
                return (T) getValueByField(refObject, field);
            }
        }
        return (T) this.mMap.get(str);
    }

    public int hashCode() {
        Object obj = this.mTempObj;
        if (obj != null) {
            return obj.hashCode();
        }
        Object refObject = getRefObject();
        if (refObject != null) {
            return refObject.hashCode();
        }
        return 0;
    }

    public boolean isValid() {
        return (this.mTempObj == null && getRefObject() == null) ? false : true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: miui.animation.property.ValueTargetObject */
    /* JADX WARN: Multi-variable type inference failed */
    public <T> void setPropertyValue(String str, Class<T> cls, T t) {
        Object refObject = getRefObject();
        if (this.mTempObj == null && refObject != null) {
            Method method = getMethod(refObject, getMethodName(str, SET, this.mSetterNameMap), this.mSetterMap, cls);
            if (method != null) {
                invokeMethod(refObject, method, t);
                return;
            }
            Field field = getField(refObject, str, cls);
            if (field != null) {
                setValueByField(refObject, field, t);
                return;
            }
        }
        this.mMap.put(str, t);
    }
}
