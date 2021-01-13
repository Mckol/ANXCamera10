package com.android.camera.external;

import android.content.Context;

public class NotifyExternalManager implements INotifyExternal {
    private static volatile NotifyExternalManager sInstance;
    private final SendBroadcastNotifyExternal mSendBroadcastNotifyExternal;

    private NotifyExternalManager(Context context) {
        this.mSendBroadcastNotifyExternal = new SendBroadcastNotifyExternal(context);
    }

    public static NotifyExternalManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (NotifyExternalManager.class) {
                if (sInstance == null) {
                    sInstance = new NotifyExternalManager(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    @Override // com.android.camera.external.INotifyExternal
    public void notifyModeAndFacing(int i, int i2) {
        SendBroadcastNotifyExternal sendBroadcastNotifyExternal = this.mSendBroadcastNotifyExternal;
        if (sendBroadcastNotifyExternal != null) {
            sendBroadcastNotifyExternal.notifyModeAndFacing(i, i2);
        }
    }
}
