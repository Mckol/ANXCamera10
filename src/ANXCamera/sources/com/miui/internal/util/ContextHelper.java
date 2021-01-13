package com.miui.internal.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.Field;

public class ContextHelper {
    private static final String TAG = "ContextHelper";

    private ContextHelper() {
    }

    public static Activity getActivityContextFromView(View view) {
        Context context = ((ViewGroup) view.getRootView()).getChildAt(0).getContext();
        if (context.getClass().getName().contains("com.android.internal.policy.DecorContext")) {
            try {
                Field declaredField = context.getClass().getDeclaredField("mPhoneWindow");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(context);
                Object invoke = obj.getClass().getMethod("getContext", new Class[0]).invoke(obj, new Object[0]);
                if (invoke != null && (invoke instanceof Context)) {
                    context = (Context) invoke;
                }
            } catch (Exception e2) {
                Log.e(TAG, e2.getMessage());
            }
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        Log.i(TAG, "fail to get activity");
        return null;
    }
}
