package miui.core;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import com.miui.internal.util.PackageConstants;
import com.miui.internal.util.ResourcesUtils;
import miui.module.Dependency;

public class CompatibleManager {
    private Context mContext;
    private Manifest mManifest;
    private String mPackageName;

    public CompatibleManager(Context context, String str, String str2, Bundle bundle) {
        this.mContext = context;
        this.mManifest = parseManifest(str, str2, bundle);
        this.mPackageName = str2;
    }

    public CompatibleManager(Context context, Manifest manifest) {
        this.mContext = context;
        this.mManifest = manifest;
        this.mPackageName = manifest.getModule().getName();
    }

    private Manifest getManifest(PackageManager packageManager, String str) {
        return ManifestParser.createFromPackage(packageManager, str).parse(null);
    }

    private boolean isCompatible(Dependency.Level level, int i) {
        boolean z = level.getMinLevel() <= i && i <= level.getMaxLevel();
        if (!z) {
            Log.w("miuisdk", "current is " + i + " but needs [" + level.getMinLevel() + "-" + level.getMaxLevel() + "]");
        }
        return z;
    }

    private Manifest parseManifest(String str, String str2, Bundle bundle) {
        return ManifestParser.createFromResources(ResourcesUtils.createResources(str), str2, bundle).parse(null);
    }

    public int getLevel() {
        return this.mManifest.getModule().getLevel();
    }

    public boolean isCompatible() {
        Manifest manifest;
        if (!isCompatible(this.mManifest.getSdkDependency().getLevel(), PackageConstants.CURRENT_SDK_LEVEL)) {
            Log.w("miuisdk", this.mPackageName + " is not compatible with sdk");
            return false;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager == null) {
            Log.w("miuisdk", "package manager is not ready yet");
            return true;
        }
        for (String str : this.mManifest.getDependencies().keySet()) {
            Dependency dependency = this.mManifest.getDependency(str);
            if ((dependency.getType() & 2) != 0 && ((manifest = getManifest(packageManager, str)) == null || !isCompatible(dependency.getLevel(), manifest.getModule().getLevel()))) {
                Log.w("miuisdk", this.mPackageName + " is not compatible with module " + str);
                return false;
            }
        }
        return true;
    }

    public boolean isCurrent() {
        return this.mManifest.getSdkDependency().getLevel().getTargetLevel() == PackageConstants.CURRENT_SDK_LEVEL;
    }
}
