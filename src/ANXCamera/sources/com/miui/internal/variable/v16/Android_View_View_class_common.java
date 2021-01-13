package com.miui.internal.variable.v16;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;
import com.miui.internal.util.TaggingDrawableHelper;
import com.miui.internal.variable.Android_View_View_class;
import com.miui.internal.variable.VariableExceptionHandler;
import com.miui.internal.view.menu.ContextMenuBuilder;
import miui.reflect.Field;
import miui.reflect.Method;

public class Android_View_View_class_common extends Android_View_View_class {
    private static Class ListenerInfoClass;
    private static Method createSnapshot;
    private static final Method getContextMenuInfo = Method.of(View.class, "getContextMenuInfo", "()Landroid/view/ContextMenu$ContextMenuInfo;");
    private static final Field mKeyedTags = Field.of(View.class, "mKeyedTags", "Landroid/util/SparseArray;");
    private static final Field mLeft = Field.of(View.class, "mLeft", Field.INT_SIGNATURE_PRIMITIVE);
    private static final Field mListenerInfo = Field.of(View.class, "mListenerInfo", "Landroid/view/View$ListenerInfo;");
    private static final Field mOldHeightMeasureSpec = Field.of(View.class, "mOldHeightMeasureSpec", Field.INT_SIGNATURE_PRIMITIVE);
    private static final Field mOldWidthMeasureSpec = Field.of(View.class, "mOldWidthMeasureSpec", Field.INT_SIGNATURE_PRIMITIVE);
    private static Field mOnCreateContextMenuListener;
    private static final Field mPrivateFlags = Field.of(View.class, "mPrivateFlags", Field.INT_SIGNATURE_PRIMITIVE);
    private static final Field mRight = Field.of(View.class, "mRight", Field.INT_SIGNATURE_PRIMITIVE);
    private static final Field mScrollX = Field.of(View.class, "mScrollX", Field.INT_SIGNATURE_PRIMITIVE);
    private static final Field mScrollY = Field.of(View.class, "mScrollY", Field.INT_SIGNATURE_PRIMITIVE);
    private static final Method onCreateContextMenu = Method.of(View.class, "onCreateContextMenu", "(Landroid/view/ContextMenu;)V");

    static {
        try {
            createSnapshot = Method.of(View.class, "createSnapshot", "(Landroid/graphics/Bitmap$Config;IZ)Landroid/graphics/Bitmap;");
        } catch (Exception unused) {
            createSnapshot = null;
        }
    }

    private int getOldHeightMeasureSpec(View view) {
        try {
            return mOldHeightMeasureSpec.getInt(view);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.view.View.mOldHeightMeasureSpec", e2);
            return 0;
        }
    }

    private int getOldWidthMeasureSpec(View view) {
        try {
            return mOldWidthMeasureSpec.getInt(view);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.view.View.mOldWidthMeasureSpec", e2);
            return 0;
        }
    }

    private int getPrivateFlags(View view) {
        try {
            return mPrivateFlags.getInt(view);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.view.View.mPrivateFlags", e2);
            return 0;
        }
    }

    private void setPrivateFlags(View view, int i) {
        try {
            mPrivateFlags.set((Object) view, i);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.view.View.mPrivateFlags", e2);
        }
    }

    /* access modifiers changed from: protected */
    public void attachSpecialMethods() {
    }

    @Override // com.miui.internal.variable.IManagedClassProxy
    public void buildProxy() {
        attachMethod("refreshDrawableState", "()V");
        attachMethod("onCreateDrawableState", "(I)[I");
        attachMethod("createContextMenu", "(Landroid/view/ContextMenu;)V");
        attachConstructor("(Landroid/content/Context;Landroid/util/AttributeSet;I)V");
        attachConstructor("(Landroid/content/Context;Landroid/util/AttributeSet;)V");
        attachSpecialMethods();
    }

    /* access modifiers changed from: protected */
    public int[] callOriginalOnCreateDrawableState(long j, View view, int i) {
        return originalOnCreateDrawableState(j, view, i);
    }

    /* access modifiers changed from: protected */
    public void callOriginalRefreshDrawableState(long j, View view) {
        originalRefreshDrawableState(j, view);
    }

    @Override // com.miui.internal.variable.Android_View_View_class
    public Bitmap createSnapshot(View view, Bitmap.Config config, int i, boolean z) {
        Method method = createSnapshot;
        if (method == null) {
            return null;
        }
        try {
            return (Bitmap) method.invokeObject(View.class, view, config, Integer.valueOf(i), Boolean.valueOf(z));
        } catch (Exception e2) {
            VariableExceptionHandler.getInstance().onThrow("invoke setLayoutInScreenEnabled failed", e2);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void handle() {
        handleRefreshDrawableState(0, null);
        handleOnCreateDrawableState(0, null, 0);
        handleCreateContextMenu(0, null, null);
        handle_init_(0, null, null, null, 0);
        handle_init_(0, null, null, null);
    }

    /* access modifiers changed from: protected */
    public void handleCreateContextMenu(long j, View view, ContextMenu contextMenu) {
        View.OnCreateContextMenuListener onCreateContextMenuListener;
        if (contextMenu instanceof ContextMenuBuilder) {
            if (ListenerInfoClass == null) {
                try {
                    ListenerInfoClass = Class.forName("android.view.View$ListenerInfo");
                    mOnCreateContextMenuListener = Field.of(ListenerInfoClass, "mOnCreateContextMenuListener", "Landroid/view/View$OnCreateContextMenuListener;");
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            ContextMenu.ContextMenuInfo contextMenuInfo = (ContextMenu.ContextMenuInfo) getContextMenuInfo.invokeObject(view.getClass(), view, new Object[0]);
            ContextMenuBuilder contextMenuBuilder = (ContextMenuBuilder) contextMenu;
            contextMenuBuilder.setCurrentMenuInfo(contextMenuInfo);
            onCreateContextMenu.invoke(view.getClass(), view, contextMenu);
            Object obj = mListenerInfo.get(view);
            if (!(obj == null || (onCreateContextMenuListener = (View.OnCreateContextMenuListener) mOnCreateContextMenuListener.get(obj)) == null)) {
                onCreateContextMenuListener.onCreateContextMenu(contextMenu, view, contextMenuInfo);
            }
            contextMenuBuilder.setCurrentMenuInfo(null);
            if (view.getParent() != null) {
                view.getParent().createContextMenu(contextMenu);
                return;
            }
            return;
        }
        originalCreateContextMenu(j, view, contextMenu);
    }

    /* access modifiers changed from: protected */
    public int[] handleOnCreateDrawableState(long j, View view, int i) {
        return onCreateDrawableState(view, callOriginalOnCreateDrawableState(j, view, 0), i);
    }

    /* access modifiers changed from: protected */
    public void handleRefreshDrawableState(long j, View view) {
        callOriginalRefreshDrawableState(j, view);
        TaggingDrawableHelper.onDrawableStateChange(view);
    }

    /* access modifiers changed from: protected */
    public void handle_init_(long j, View view, Context context, AttributeSet attributeSet) {
        original_init_(j, view, context, attributeSet);
        TaggingDrawableHelper.initViewSequenceStates(view, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void handle_init_(long j, View view, Context context, AttributeSet attributeSet, int i) {
        original_init_(j, view, context, attributeSet, i);
        TaggingDrawableHelper.initViewSequenceStates(view, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void originalCreateContextMenu(long j, View view, ContextMenu contextMenu) {
        throw new IllegalStateException("com.miui.internal.variable.v16.Android_View_View_class.originalCreateContextMenu(long, View, ContextMenu)");
    }

    /* access modifiers changed from: protected */
    public int[] originalOnCreateDrawableState(long j, View view, int i) {
        throw new IllegalStateException("com.miui.internal.variable.v16.Android_View_View_class.originalOnCreateDrawableState(long, View, int)");
    }

    /* access modifiers changed from: protected */
    public void originalRefreshDrawableState(long j, View view) {
        throw new IllegalStateException("com.miui.internal.variable.v16.Android_View_View_class.originalRefreshDrawableState(long, View)");
    }

    /* access modifiers changed from: protected */
    public void original_init_(long j, View view, Context context, AttributeSet attributeSet) {
        throw new IllegalStateException("com.miui.internal.variable.v16.Android_View_View_class.original_init_(long, View, Context, AttributeSet)");
    }

    /* access modifiers changed from: protected */
    public void original_init_(long j, View view, Context context, AttributeSet attributeSet, int i) {
        throw new IllegalStateException("com.miui.internal.variable.v16.Android_View_View_class.original_init_(long, View, Context, AttributeSet, int)");
    }

    @Override // com.miui.internal.variable.Android_View_View_class
    public void relayout(View view) {
        if (view.getHeight() != 0 && view.getWidth() != 0) {
            int privateFlags = getPrivateFlags(view);
            view.forceLayout();
            view.measure(getOldWidthMeasureSpec(view), getOldHeightMeasureSpec(view));
            view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            setPrivateFlags(view, privateFlags);
        }
    }

    @Override // com.miui.internal.variable.Android_View_View_class
    public void setImportantForAccessibilityNoHideDescendants(View view) {
    }

    @Override // com.miui.internal.variable.Android_View_View_class
    public void setLeftDirectly(View view, int i) {
        try {
            mLeft.set((Object) view, i);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.view.View.mScrollY", e2);
        }
    }

    @Override // com.miui.internal.variable.Android_View_View_class
    public void setRightDirectly(View view, int i) {
        try {
            mRight.set((Object) view, i);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.view.View.mScrollY", e2);
        }
    }

    @Override // com.miui.internal.variable.Android_View_View_class
    public void setScrollXDirectly(View view, int i) {
        try {
            mScrollX.set((Object) view, i);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.view.View.mScrollX", e2);
        }
    }

    @Override // com.miui.internal.variable.Android_View_View_class
    public void setScrollYDirectly(View view, int i) {
        try {
            mScrollY.set((Object) view, i);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.view.View.mScrollY", e2);
        }
    }
}
