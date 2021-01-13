package com.miui.internal.view.menu;

import android.view.ContextMenu;
import android.view.View;
import android.view.ViewParent;
import com.miui.internal.util.ReflectUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ContextMenuHelper {
    private static final Method getContextMenuInfo = ReflectUtils.getDeclaredMethod(View.class, "getContextMenuInfo", new Class[0]);
    private static final Field mListenerInfo = ReflectUtils.getDeclaredField(View.class, "mListenerInfo");
    private static final Field mOnCreateContextMenuListener = ReflectUtils.getDeclaredField("android.view.View$ListenerInfo", "mOnCreateContextMenuListener");
    private static final Method onCreateContextMenu = ReflectUtils.getDeclaredMethod(View.class, "onCreateContextMenu", ContextMenu.class);

    private ContextMenuHelper() {
    }

    public static void createContextMenu(View view, ContextMenuBuilder contextMenuBuilder) {
        View.OnCreateContextMenuListener onCreateContextMenuListener;
        ContextMenu.ContextMenuInfo contextMenuInfo = (ContextMenu.ContextMenuInfo) ReflectUtils.invoke(view, getContextMenuInfo, new Object[0]);
        contextMenuBuilder.setCurrentMenuInfo(contextMenuInfo);
        ReflectUtils.invoke(view, onCreateContextMenu, contextMenuBuilder);
        Object obj = ReflectUtils.get(view, mListenerInfo);
        if (!(obj == null || (onCreateContextMenuListener = (View.OnCreateContextMenuListener) ReflectUtils.get(obj, mOnCreateContextMenuListener)) == null)) {
            onCreateContextMenuListener.onCreateContextMenu(contextMenuBuilder, view, contextMenuInfo);
        }
        contextMenuBuilder.setCurrentMenuInfo(null);
        ViewParent parent = view.getParent();
        if (parent instanceof View) {
            createContextMenu((View) parent, contextMenuBuilder);
        }
    }
}
