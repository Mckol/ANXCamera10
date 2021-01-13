package com.android.camera;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

public final class ThermalHelper {
    private static final String ACTION_SET_FPS = "com.miui.powerkeeper.set_fps";
    private static final String PACKAGE_NAME = "com.miui.powerkeeper";
    private static final String TAG = "ThermalHelper";

    private ThermalHelper() {
    }

    public static void notifyThermalRecordStart(int i, int i2) {
        Log.d(TAG, "notifyThermalRecordStart: quality = " + i + ", fps = " + i2);
        Intent intent = new Intent();
        intent.setPackage(PACKAGE_NAME);
        intent.setAction("record_start");
        intent.putExtra("quality", i);
        intent.putExtra("fps", i2);
        CameraAppImpl.getAndroidContext().sendBroadcast(intent);
    }

    public static void notifyThermalRecordStop(int i, int i2) {
        Log.d(TAG, "notifyThermalRecordStop: quality = " + i + ", fps = " + i2);
        Intent intent = new Intent();
        intent.setPackage(PACKAGE_NAME);
        intent.setAction("record_end");
        intent.putExtra("quality", i);
        intent.putExtra("fps", i2);
        CameraAppImpl.getAndroidContext().sendBroadcast(intent);
    }

    public static void updateDisplayFrameRate(boolean z, Application application) {
        Log.d(TAG, "updateDisplayFrameRate: " + z);
        Intent intent = new Intent();
        intent.setPackage(PACKAGE_NAME);
        intent.setAction(ACTION_SET_FPS);
        intent.putExtra("pkg", application.getPackageName());
        intent.putExtra("uid", application.getApplicationInfo().uid);
        intent.putExtra("isRestore", z);
        CameraAppImpl.getAndroidContext().sendBroadcast(intent);
    }
}
