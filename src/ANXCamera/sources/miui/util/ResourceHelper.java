package miui.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

public class ResourceHelper {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final String TAG = "ResourceHelper";

    private ResourceHelper() {
    }

    private static int getConfigResource(Context context, String str, String str2, String... strArr) {
        Bundle metaData = getMetaData(context);
        if (metaData == null) {
            Log.w(TAG, "Fail to load meta data");
        } else {
            int i = metaData.getInt(str);
            if (i > 0) {
                return i;
            }
        }
        if (TextUtils.isEmpty(str2)) {
            return -1;
        }
        int identifier = context.getResources().getIdentifier(str2, "xml", context.getPackageName());
        if (identifier > 0) {
            return identifier;
        }
        for (String str3 : strArr) {
            int identifier2 = context.getResources().getIdentifier(str2, "xml", str3);
            if (identifier2 > 0) {
                return identifier2;
            }
        }
        return -1;
    }

    private static Bundle getMetaData(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo.metaData == null) {
            try {
                applicationInfo.metaData = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            } catch (PackageManager.NameNotFoundException e2) {
                Log.e(TAG, "Fail to load config for application not found", e2);
            }
        }
        return applicationInfo.metaData;
    }

    public static XmlResourceParser loadXml(Context context, String str, String str2) {
        return loadXml(context, str, str2, EMPTY_STRING_ARRAY);
    }

    public static XmlResourceParser loadXml(Context context, String str, String str2, String... strArr) {
        int configResource = getConfigResource(context, str, str2, strArr);
        if (configResource > 0) {
            return context.getResources().getXml(configResource);
        }
        Log.i(TAG, "Fail to load config for resource id is " + configResource);
        return null;
    }
}
