package com.miui.internal.variable.v17;

import android.graphics.drawable.Drawable;
import com.miui.internal.variable.VariableExceptionHandler;
import miui.reflect.Method;

public class Android_Graphics_Drawable_Drawable_class extends com.miui.internal.variable.Android_Graphics_Drawable_Drawable_class {
    private static final Method getLayoutDirection = Method.of(Drawable.class, "getLayoutDirection", "()I");

    @Override // com.miui.internal.variable.Android_Graphics_Drawable_Drawable_class
    public int getLayoutDirection(Drawable drawable) {
        try {
            return getLayoutDirection.invokeInt(null, drawable, new Object[0]);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.graphics.drawable.Drawable.getLayoutDirection", e2);
            return 0;
        }
    }
}
