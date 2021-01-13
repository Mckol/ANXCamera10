package com.miui.internal.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import miui.telephony.phonenumber.ChineseTelocationConverter;

public class TelocationUpdateReceiver extends BroadcastReceiver {
    private static final String SERVICE_NAME = "com.miui.telocation";
    private static final String TAG = "TelocationUpdateReceiver";

    public static void onReceiveIntent(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle extras = intent.getExtras();
        Log.d(TAG, "onReceiveIntent: action=" + action);
        if (DataUpdateManager.DATA_UPDATE_RECEIVE.equals(action)) {
            long longExtra = intent.getLongExtra(DataUpdateManager.EXTRA_WATER_MARK, 0);
            String string = extras != null ? extras.getString(DataUpdateManager.EXTRA_SERVICE_NAME) : null;
            Log.d(TAG, "current water mark is " + longExtra + ", service: " + string);
            if (TextUtils.equals(SERVICE_NAME, string) && longExtra != ((long) ChineseTelocationConverter.getInstance().getVersion())) {
                Intent intent2 = new Intent(TelocationUpdateService.ACTION_UPDATE_TELOCATION);
                intent2.setClass(context, TelocationUpdateService.class);
                context.startService(intent2);
            }
        } else if ("android.intent.action.DOWNLOAD_COMPLETE".equals(action)) {
            Log.d(TAG, "telocation download completed.");
            Intent intent3 = new Intent(TelocationUpdateService.ACTION_TELOCATION_DOWNLOAD_COMPLETED);
            if (extras != null) {
                intent3.putExtras(extras);
            }
            intent3.setClass(context, TelocationUpdateService.class);
            context.startService(intent3);
        }
    }

    public void onReceive(Context context, Intent intent) {
        onReceiveIntent(context, intent);
    }
}
