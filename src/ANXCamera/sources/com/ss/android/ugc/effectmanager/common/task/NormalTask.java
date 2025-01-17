package com.ss.android.ugc.effectmanager.common.task;

import android.os.Handler;
import android.os.Message;
import com.ss.android.ugc.effectmanager.common.EffectConstants;

public abstract class NormalTask implements BaseTask {
    protected Handler mHandler;
    private boolean mIsCanceled;
    private String mType;
    protected String taskId;

    public NormalTask(Handler handler, String str) {
        this.mHandler = handler;
        this.taskId = str;
        this.mType = EffectConstants.NORMAL;
    }

    public NormalTask(Handler handler, String str, String str2) {
        this.mHandler = handler;
        this.taskId = str;
        this.mType = str2;
    }

    @Override // com.ss.android.ugc.effectmanager.common.task.BaseTask
    public void cancel() {
        this.mIsCanceled = true;
    }

    public String getType() {
        return this.mType;
    }

    public boolean isCanceled() {
        return this.mIsCanceled;
    }

    /* access modifiers changed from: protected */
    public void sendMessage(int i, BaseTaskResult baseTaskResult) {
        baseTaskResult.setTaskID(this.taskId);
        Message obtainMessage = this.mHandler.obtainMessage(i);
        obtainMessage.obj = baseTaskResult;
        obtainMessage.sendToTarget();
    }
}
