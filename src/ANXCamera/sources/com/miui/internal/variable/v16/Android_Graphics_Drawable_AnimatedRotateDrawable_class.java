package com.miui.internal.variable.v16;

import android.graphics.drawable.Drawable;
import com.android.camera.statistic.MistatsConstants;
import com.miui.internal.variable.Android_Graphics_Drawable_Drawable_class;
import com.miui.internal.variable.VariableExceptionHandler;
import miui.reflect.Method;

public class Android_Graphics_Drawable_AnimatedRotateDrawable_class extends com.miui.internal.variable.Android_Graphics_Drawable_AnimatedRotateDrawable_class {
    private static final Class<?> CLASS;
    private static final Method setFramesCount = Method.of(CLASS, "setFramesCount", "(I)V");
    private static final Method setFramesDuration = Method.of(CLASS, "setFramesDuration", "(I)V");
    private static final Method start = Method.of(CLASS, "start", "()V");
    private static final Method stop = Method.of(CLASS, MistatsConstants.BaseEvent.STOP, "()V");

    static {
        Class<?> cls;
        try {
            cls = Class.forName(com.miui.internal.variable.Android_Graphics_Drawable_AnimatedRotateDrawable_class.NAME);
        } catch (ClassNotFoundException e2) {
            VariableExceptionHandler.getInstance().onThrow(com.miui.internal.variable.Android_Graphics_Drawable_AnimatedRotateDrawable_class.NAME, e2);
            cls = null;
        }
        CLASS = cls;
    }

    @Override // com.miui.internal.variable.Android_Graphics_Drawable_Drawable_class
    public int getLayoutDirection(Drawable drawable) {
        return Android_Graphics_Drawable_Drawable_class.Factory.getInstance().get().getLayoutDirection(drawable);
    }

    @Override // com.miui.internal.variable.Android_Graphics_Drawable_AnimatedRotateDrawable_class
    public void setFramesCount(Drawable drawable, int i) {
        try {
            setFramesCount.invoke(null, drawable, Integer.valueOf(i));
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.graphics.drawable.AnimatedRotateDrawable.setFramesCount", e2);
        }
    }

    @Override // com.miui.internal.variable.Android_Graphics_Drawable_AnimatedRotateDrawable_class
    public void setFramesDuration(Drawable drawable, int i) {
        try {
            setFramesDuration.invoke(null, drawable, Integer.valueOf(i));
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.graphics.drawable.AnimatedRotateDrawable.setFramesDuration", e2);
        }
    }

    @Override // com.miui.internal.variable.Android_Graphics_Drawable_AnimatedRotateDrawable_class
    public void start(Drawable drawable) {
        try {
            start.invoke(null, drawable, new Object[0]);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.graphics.drawable.AnimatedRotateDrawable.start", e2);
        }
    }

    @Override // com.miui.internal.variable.Android_Graphics_Drawable_AnimatedRotateDrawable_class
    public void stop(Drawable drawable) {
        try {
            stop.invoke(null, drawable, new Object[0]);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.graphics.drawable.AnimatedRotateDrawable.stop", e2);
        }
    }
}
