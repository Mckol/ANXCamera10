package miui.util;

import android.app.Application;
import android.content.Context;
import com.miui.internal.util.PackageConstants;
import com.miui.internal.variable.Android_App_ActivityThread_class;
import miui.core.ManifestParser;

public class AppConstants {
    private static final String TAG = "AppConstants";

    public static Application getCurrentApplication() {
        if (PackageConstants.sApplication == null) {
            PackageConstants.sApplication = Android_App_ActivityThread_class.Factory.getInstance().get().currentApplication();
        }
        return PackageConstants.sApplication;
    }

    public static int getSdkLevel(Context context, String str) {
        return "com.miui.core".equals(str) ? PackageConstants.CURRENT_SDK_LEVEL : ManifestParser.createFromPackage(context.getPackageManager(), str).parse(null).getModule().getLevel();
    }
}
