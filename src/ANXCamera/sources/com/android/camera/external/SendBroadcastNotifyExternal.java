package com.android.camera.external;

import android.content.Context;
import android.content.Intent;
import com.android.camera.log.Log;

public class SendBroadcastNotifyExternal implements INotifyExternal {
    private static final String CAMERA_STATUS_ACTION = "com.android.camera.action.camera_status";
    private static final String CAMERA_STATUS_RECEIVER_PERMISSION = "com.android.camera.permission.CAMERA_STATUS";
    private static final String EXTRA_CAMERA_LENS = "lens";
    private static final String EXTRA_CAMERA_MODULE = "module";
    private static final String TAG = "SendBroadcastNotifyExternal";
    private Context mContext;

    public SendBroadcastNotifyExternal(Context context) {
        this.mContext = context;
    }

    @Override // com.android.camera.external.INotifyExternal
    public void notifyModeAndFacing(int i, int i2) {
        if (this.mContext != null) {
            String str = TAG;
            Log.d(str, "notify external(mode:" + i + ",facing:" + i2 + ")");
            Intent intent = new Intent(CAMERA_STATUS_ACTION);
            intent.putExtra(EXTRA_CAMERA_MODULE, String.valueOf(i));
            intent.putExtra("lens", String.valueOf(i2));
            this.mContext.sendBroadcast(intent, "com.android.camera.permission.CAMERA_STATUS");
        }
    }
}
