package com.miui.internal.variable.v16;

import android.content.Context;
import android.widget.RemoteViews;

public class Android_Widget_RemoteViews_class extends com.miui.internal.variable.Android_Widget_RemoteViews_class {
    @Override // com.miui.internal.variable.IManagedClassProxy
    public void buildProxy() {
        attachMethod("prepareContext", "(Landroid/content/Context;)Landroid/content/Context;");
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void handle() {
        handlePrepareContext(0, null, null);
    }

    /* access modifiers changed from: protected */
    public Context handlePrepareContext(long j, RemoteViews remoteViews, Context context) {
        Context originalPrepareContext = originalPrepareContext(j, remoteViews, context);
        if (context != originalPrepareContext && "com.android.systemui".equals(context.getApplicationInfo().packageName)) {
            originalPrepareContext.setTheme(com.miui.internal.variable.Android_Widget_RemoteViews_class.REMOTE_VIEWS_TEMPLATE_THEME);
        }
        return originalPrepareContext;
    }

    /* access modifiers changed from: protected */
    public Context originalPrepareContext(long j, RemoteViews remoteViews, Context context) {
        throw new IllegalStateException("com.miui.internal.variable.v16.Android_Widget_RemoteViews_class.originalPrepareContext(long, RemoteViews, Context)");
    }
}
