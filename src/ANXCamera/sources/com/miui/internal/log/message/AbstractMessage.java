package com.miui.internal.log.message;

import android.util.Log;

public abstract class AbstractMessage implements Message {
    private static final String TAG = "AbstractMessage";
    private boolean mRecycled;

    @Override // com.miui.internal.log.message.Message
    public abstract void format(Appendable appendable);

    @Override // com.miui.internal.log.message.Message
    public abstract Throwable getThrowable();

    @Override // com.miui.internal.log.message.Message
    public boolean isRecycled() {
        return this.mRecycled;
    }

    /* access modifiers changed from: protected */
    public abstract void onRecycle();

    @Override // com.miui.internal.log.message.Message
    public void prepareForReuse() {
        this.mRecycled = false;
    }

    @Override // com.miui.internal.log.message.Message
    public void recycle() {
        if (this.mRecycled) {
            Log.w(TAG, "Recycle message twice");
            return;
        }
        onRecycle();
        this.mRecycled = true;
        MessageFactory.recycle(this);
    }
}
