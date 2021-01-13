package com.miui.internal.log.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.util.Log;
import java.util.List;

public class DynamicDumpReceiver extends DumpReceiver {
    private static final Object LOCKER = new Object();
    private static final String TAG = "DynamicDumpReceiver";
    private static volatile boolean sReceiverRegistered;
    private static boolean sStaticReceiverChecked;

    public DynamicDumpReceiver() {
    }

    public DynamicDumpReceiver(String str, String str2) {
        super(str, str2);
    }

    private boolean staticReceiverExists(Context context) {
        Intent intent = new Intent(DumpReceiver.INTENT_ACTION_DUMP_CACHED_LOG);
        intent.setPackage(context.getPackageName());
        List<ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
        return queryBroadcastReceivers != null && !queryBroadcastReceivers.isEmpty();
    }

    @Override // com.miui.internal.log.receiver.DumpReceiver
    public void onReceive(Context context, Intent intent) {
        if (!sStaticReceiverChecked) {
            if (staticReceiverExists(context)) {
                Log.i(TAG, "Dynamic receiver removed");
                context.unregisterReceiver(this);
                return;
            }
            sStaticReceiverChecked = true;
        }
        super.onReceive(context, intent);
    }

    public boolean register(Context context) {
        if (sReceiverRegistered) {
            return false;
        }
        synchronized (LOCKER) {
            if (sReceiverRegistered) {
                return false;
            }
            context.registerReceiver(this, new IntentFilter(DumpReceiver.INTENT_ACTION_DUMP_CACHED_LOG), "miui.permission.DUMP_CACHED_LOG", null);
            sReceiverRegistered = true;
            Log.i(TAG, "Dynamic receiver registered");
            return true;
        }
    }
}
