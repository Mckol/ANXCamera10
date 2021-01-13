package com.miui.internal.variable.v16;

import android.graphics.drawable.Drawable;
import com.miui.internal.variable.VariableExceptionHandler;
import miui.reflect.Method;

public class Android_Graphics_Drawable_Drawable_class extends com.miui.internal.variable.Android_Graphics_Drawable_Drawable_class {
    private static final Method getResolvedLayoutDirectionSelf = getGetResolvedLayoutDirectionSelf();

    private static Method getGetResolvedLayoutDirectionSelf() {
        try {
            return Method.of(Drawable.class, "getResolvedLayoutDirectionSelf", "()I");
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // com.miui.internal.variable.Android_Graphics_Drawable_Drawable_class
    public int getLayoutDirection(Drawable drawable) {
        Method method = getResolvedLayoutDirectionSelf;
        if (method != null) {
            try {
                return method.invokeInt(null, drawable, new Object[0]);
            } catch (RuntimeException e2) {
                VariableExceptionHandler.getInstance().onThrow("android.graphics.drawable.Drawable.getResolvedLayoutDirectionSelf", e2);
            }
        }
        return 0;
    }
}
