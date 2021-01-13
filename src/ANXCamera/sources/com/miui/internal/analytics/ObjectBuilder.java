package com.miui.internal.analytics;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

public class ObjectBuilder<T> {
    private static final String TAG = "ObjectBuilder";
    private ArrayList<ObjectBuilder<T>.BuilderData> mBuilderInf = new ArrayList<>();

    /* access modifiers changed from: private */
    public class BuilderData {
        String mTag;
        Class<? extends T> mType;

        BuilderData(Class<? extends T> cls, String str) {
            this.mType = cls;
            this.mTag = str;
        }
    }

    public T buildObject(String str) {
        Iterator<ObjectBuilder<T>.BuilderData> it = this.mBuilderInf.iterator();
        T t = null;
        while (it.hasNext()) {
            ObjectBuilder<T>.BuilderData next = it.next();
            if (next.mTag.equals(str)) {
                try {
                    t = (T) next.mType.newInstance();
                } catch (InstantiationException e2) {
                    Log.e(TAG, "InstantiationException catched when buildObject", e2);
                } catch (IllegalAccessException e3) {
                    Log.e(TAG, "IllegalAccessException catched when buildObject", e3);
                }
            }
        }
        return t;
    }

    public boolean registerClass(Class<? extends T> cls, String str) {
        Iterator<ObjectBuilder<T>.BuilderData> it = this.mBuilderInf.iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().mTag)) {
                return false;
            }
        }
        return this.mBuilderInf.add(new BuilderData(cls, str));
    }
}
