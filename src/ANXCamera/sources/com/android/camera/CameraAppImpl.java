package com.android.camera;

import android.app.Activity;
import android.content.Context;
import android.os.Process;
import b.c.a.c;
import com.android.camera.aftersales.AftersalesManager;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.network.util.NetworkUtils;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera2.vendortag.CameraCharacteristicsVendorTags;
import com.android.camera2.vendortag.CaptureRequestVendorTags;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.struct.MarshalQueryableRegister;
import com.miui.filtersdk.BeautificationSDK;
import com.xiaomi.camera.imagecodec.ImagePool;
import java.io.File;
import miui.external.Application;

public class CameraAppImpl extends Application {
    private static CameraApplicationDelegate sApplicationDelegate;
    private final String TAG = CameraAppImpl.class.getSimpleName();
    private boolean isMimojiNeedUpdate = true;
    private boolean sLaunched = true;

    public static Context getAndroidContext() {
        return CameraApplicationDelegate.getAndroidContext();
    }

    public void addActivity(Activity activity) {
        sApplicationDelegate.addActivity(activity);
    }

    public void closeAllActivitiesBut(Activity activity) {
        sApplicationDelegate.closeAllActivitiesBut(activity);
    }

    public boolean containsResumedCameraInStack() {
        return sApplicationDelegate.containsResumedCameraInStack();
    }

    public Activity getActivity(int i) {
        return sApplicationDelegate.getActivity(i);
    }

    public int getActivityCount() {
        return sApplicationDelegate.getActivityCount();
    }

    public boolean isApplicationFirstLaunched() {
        boolean z = this.sLaunched;
        if (!z) {
            return z;
        }
        this.sLaunched = !z;
        return !this.sLaunched;
    }

    public boolean isMainIntentActivityLaunched() {
        return sApplicationDelegate.isMainIntentActivityLaunched();
    }

    public boolean isMimojiNeedUpdate() {
        if (!this.isMimojiNeedUpdate) {
            return false;
        }
        this.isMimojiNeedUpdate = false;
        return true;
    }

    public boolean isNeedRestore() {
        return sApplicationDelegate.getSettingsFlag();
    }

    @Override // miui.external.Application
    public CameraApplicationDelegate onCreateApplicationDelegate() {
        if (sApplicationDelegate == null) {
            sApplicationDelegate = new CameraApplicationDelegate(this);
        }
        File databasePath = getDatabasePath("camera.db");
        if (databasePath != null && databasePath.exists()) {
            deleteDatabase("camera.db");
        }
        System.setProperty("rx2.purge-period-seconds", "3600");
        AftersalesManager.checkSelf(this);
        if ("avenger".equals(c.Bg)) {
            getPackageManager().setApplicationEnabledSetting("com.android.camera", 2, 1);
        }
        MarshalQueryableRegister.preload();
        CameraCharacteristicsVendorTags.preload();
        CaptureRequestVendorTags.preload();
        CaptureResultVendorTags.preload();
        if (DataRepository.dataItemFeature().nl()) {
            int Uk = DataRepository.dataItemFeature().Uk();
            int Wk = DataRepository.dataItemFeature().Wk();
            long totalMemory = Process.getTotalMemory() / 1073741824;
            if (totalMemory > 6) {
                Uk = DataRepository.dataItemFeature().Vk();
                Wk = DataRepository.dataItemFeature().Xk();
            }
            String str = this.TAG;
            Log.d(str, "totalMemory:" + totalMemory + "G, maxAcquireCount = " + Uk + ", maxDequeueCount:" + Wk);
            ImagePool.init(Uk, Wk);
            AlgoConnector.getInstance().startService(this);
        }
        CrashHandler.getInstance().init(this);
        NetworkUtils.bind(this);
        BeautificationSDK.init(this);
        return sApplicationDelegate;
    }

    public void removeActivity(Activity activity) {
        sApplicationDelegate.removeActivity(activity);
    }

    public void resetRestoreFlag() {
        sApplicationDelegate.resetRestoreFlag();
    }
}
