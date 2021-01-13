package com.miui.internal.component.module;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import java.io.File;
import java.util.HashSet;
import miui.os.FileUtils;

public class ModuleUtils {
    public static final String MODULE_FILE_EXTENSION = ".apk";
    public static final String MODULE_LIB_EXTENSION = ".lib";

    private ModuleUtils() {
    }

    private static int compareSignatures(Signature[] signatureArr, Signature[] signatureArr2) {
        if (!(signatureArr == null || signatureArr2 == null)) {
            HashSet hashSet = new HashSet();
            for (Signature signature : signatureArr) {
                hashSet.add(signature);
            }
            HashSet hashSet2 = new HashSet();
            for (Signature signature2 : signatureArr2) {
                hashSet2.add(signature2);
            }
            if (hashSet.equals(hashSet2)) {
                return 0;
            }
        }
        return -3;
    }

    public static File getLibraryFolder(String str) {
        String name = FileUtils.getName(str);
        String parent = new File(str).getParent();
        return new File(parent, name + MODULE_LIB_EXTENSION);
    }

    public static boolean isSignatureValid(Context context, String str) {
        PackageInfo packageInfo;
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(str, 64);
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 64);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            packageInfo = null;
        }
        return (packageArchiveInfo == null || packageInfo == null || compareSignatures(packageArchiveInfo.signatures, packageInfo.signatures) != 0) ? false : true;
    }
}
