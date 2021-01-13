package com.android.camera.module;

import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

/* renamed from: com.android.camera.module.i  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class RunnableC0077i implements Runnable {
    public static final /* synthetic */ RunnableC0077i INSTANCE = new RunnableC0077i();

    private /* synthetic */ RunnableC0077i() {
    }

    public final void run() {
        ((ModeProtocol.IDCardModeProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(233)).switchNextPage();
    }
}
