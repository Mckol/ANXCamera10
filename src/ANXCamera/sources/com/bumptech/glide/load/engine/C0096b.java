package com.bumptech.glide.load.engine;

import android.os.Process;

/* renamed from: com.bumptech.glide.load.engine.b reason: case insensitive filesystem */
/* compiled from: ActiveResources */
class C0096b implements Runnable {
    final /* synthetic */ ActiveResources this$0;

    C0096b(ActiveResources activeResources) {
        this.this$0 = activeResources;
    }

    public void run() {
        Process.setThreadPriority(10);
        this.this$0.bg();
    }
}
