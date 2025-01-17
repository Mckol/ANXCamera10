package com.android.camera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.camera.log.Log;
import com.android.camera.permission.PermissionManager;

public class CameraButtonIntentReceiver extends BroadcastReceiver {
    private static final String TAG = "CameraButtonIntentReceiver";

    public void onReceive(Context context, Intent intent) {
        if (!PermissionManager.checkCameraLaunchPermissions()) {
            Log.e(TAG, "no camera permission");
            return;
        }
        Intent intent2 = new Intent("android.intent.action.MAIN");
        intent2.setClass(context, Camera.class);
        intent2.addCategory("android.intent.category.LAUNCHER");
        intent2.setFlags(268435456);
        context.startActivity(intent2);
    }
}
