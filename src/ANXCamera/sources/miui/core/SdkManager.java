package miui.core;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import com.miui.internal.app.MiuiApplication;
import com.miui.internal.component.ComponentManager;
import com.miui.internal.component.module.ModuleLoader;
import com.miui.internal.initialization.OverrideHelper;
import com.miui.internal.util.ClassProxy;
import com.miui.internal.util.DeviceHelper;
import com.miui.internal.util.PackageConstants;
import com.miui.internal.util.PackageHelper;
import java.util.Map;

public abstract class SdkManager {
    private static final String MIUI_CORE_SERVICE = "com.miui.internal.server.CoreService";
    public static final int RETURN_CODE_LOW_SDK_VERSION = 1;
    public static final int RETURN_CODE_SUCCESS = 0;
    private static final String TAG = "miuisdk";

    private SdkManager() {
    }

    private static int configDebugEnable() {
        String debugEnable = DeviceHelper.getDebugEnable();
        if (debugEnable.length() == 0) {
            return 0;
        }
        for (String str : debugEnable.split("\\,")) {
            String[] split = str.split("-");
            if (split.length == 2) {
                String trim = split[0].trim();
                if (trim.equals("all") || ClassProxy.getProcName().trim().equals(trim)) {
                    String str2 = split[1];
                    if (str2.equals("all")) {
                        new String[]{"all"};
                    } else {
                        str2.split("\\|");
                    }
                }
            }
        }
        return 0;
    }

    public static int initialize(Application application, Map<String, Object> map) {
        synchronized (SdkManager.class) {
            configDebugEnable();
            PackageConstants.sInitializingApplication = application;
            if (PackageConstants.sSdkStatus >= 2) {
                Log.d("miuisdk", "sdk already initialized");
                return 0;
            }
            OverrideHelper.doInitialize();
            if (!PackageHelper.isMiuiSystem()) {
                ModuleLoader.loadResources(PackageConstants.RESOURCE_PATH);
            }
            if (PackageConstants.sSdkStatus < 2) {
                PackageConstants.sSdkStatus = 2;
            }
            return 0;
        }
    }

    private static boolean isCompatible(Context context, Manifest manifest) {
        return new CompatibleManager(context, manifest).isCompatible();
    }

    private static void loadComponents(Application application, Manifest manifest) {
        new ComponentManager(application, manifest).load();
    }

    public static int start(Map<String, Object> map) {
        synchronized (SdkManager.class) {
            if (PackageConstants.sSdkStatus >= 3) {
                Log.d("miuisdk", "sdk already started");
            }
            Application application = PackageConstants.sInitializingApplication;
            PackageConstants.sApplication = application;
            Manifest parse = ManifestParser.create(application).parse(map);
            if (!(application instanceof MiuiApplication) && !isCompatible(application, parse)) {
                return 1;
            }
            if (!"android".equals(application.getPackageName())) {
                loadComponents(application, parse);
            }
            if (PackageConstants.sSdkStatus < 3) {
                PackageConstants.sSdkStatus = 3;
            }
            return 0;
        }
    }

    private static void startService(final Context context) {
        new Handler().post(new Runnable() {
            /* class miui.core.SdkManager.AnonymousClass1 */

            public void run() {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.setClassName("com.miui.core", SdkManager.MIUI_CORE_SERVICE);
                context.startService(intent);
            }
        });
    }

    public static boolean supportUpdate(Map<String, Object> map) {
        return false;
    }

    public static boolean update(Map<String, Object> map) {
        return false;
    }
}
