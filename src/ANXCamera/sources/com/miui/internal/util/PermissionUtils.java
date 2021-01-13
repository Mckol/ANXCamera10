package com.miui.internal.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class PermissionUtils {
    private static final String TAG = "PermissionUtils";

    private static boolean checkPermission(Context context, String str) {
        return context.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
    }

    private static String[] getUngrantedPermissions(Context context, String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (!checkPermission(context, str)) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            return new String[0];
        }
        String[] strArr2 = new String[arrayList.size()];
        arrayList.toArray(strArr2);
        return strArr2;
    }

    public static void requestPermissions(Context context, String[] strArr) {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] ungrantedPermissions = getUngrantedPermissions(context, strArr);
            if (ungrantedPermissions.length > 0) {
                startPermissionRequest(context, ungrantedPermissions);
            }
        }
    }

    private static void startPermissionRequest(Context context, String[] strArr) {
        try {
            Intent intent = (Intent) PackageManager.class.getDeclaredMethod("buildRequestPermissionsIntent", String[].class).invoke(context.getPackageManager(), strArr);
            intent.setFlags(268435456);
            context.startActivity(intent);
            Log.i(TAG, "Success to request permissions: " + Arrays.toString(strArr));
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "Fail to request permissions: " + Arrays.toString(strArr), e2);
        } catch (InvocationTargetException e3) {
            Log.e(TAG, "Fail to request permissions: " + Arrays.toString(strArr), e3);
        } catch (IllegalAccessException e4) {
            Log.e(TAG, "Fail to request permissions: " + Arrays.toString(strArr), e4);
        }
    }
}
