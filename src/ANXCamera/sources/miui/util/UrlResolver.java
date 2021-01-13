package miui.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import com.android.server.pm.PackageManagerService;
import com.miui.internal.util.UrlResolverHelper;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class UrlResolver {
    private static Method mResolveIntent;

    private UrlResolver() {
    }

    public static ResolveInfo checkMiuiIntent(Context context, PackageManager packageManager, Intent intent) {
        return checkMiuiIntent(context, true, packageManager, null, intent, null, 0, null, 0);
    }

    public static ResolveInfo checkMiuiIntent(Context context, Object obj, Intent intent, String str, int i, List<ResolveInfo> list, int i2) {
        return checkMiuiIntent(context, false, context.getPackageManager(), obj, intent, str, i, list, i2);
    }

    private static ResolveInfo checkMiuiIntent(Context context, boolean z, PackageManager packageManager, Object obj, Intent intent, String str, int i, List<ResolveInfo> list, int i2) {
        Uri data;
        String host;
        String fallbackParameter;
        boolean z2;
        List<ResolveInfo> queryIntentActivities = z ? packageManager.queryIntentActivities(intent, 0) : list;
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < queryIntentActivities.size(); i3++) {
            ResolveInfo resolveInfo = queryIntentActivities.get(i3);
            if (!resolveInfo.activityInfo.packageName.equals("com.android.browser") && !resolveInfo.activityInfo.packageName.equals("com.mi.globalbrowser")) {
                if (UrlResolverHelper.isWhiteListPackage(resolveInfo.activityInfo.packageName)) {
                    arrayList.add(resolveInfo);
                } else {
                    try {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        PackageInfo packageInfo = packageManager.getPackageInfo(resolveInfo.activityInfo.packageName, 0);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                        if (!(applicationInfo == null || (1 & applicationInfo.flags) == 0 || packageInfo.packageName.equals("com.android.chrome"))) {
                            arrayList.add(resolveInfo);
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
            }
        }
        if (arrayList.size() == 1) {
            return (ResolveInfo) arrayList.get(0);
        }
        if (arrayList.size() > 1 || (data = intent.getData()) == null || (host = data.getHost()) == null || !UrlResolverHelper.isMiHost(host) || (fallbackParameter = UrlResolverHelper.getFallbackParameter(data)) == null) {
            return null;
        }
        Uri parse = Uri.parse(fallbackParameter);
        if (UrlResolverHelper.isBrowserFallbackScheme(parse.getScheme())) {
            parse = UrlResolverHelper.getBrowserFallbackUri(fallbackParameter);
            z2 = true;
        } else {
            z2 = false;
        }
        intent.setData(parse);
        if (z) {
            if (!z2) {
                return checkMiuiIntent(context, packageManager, intent);
            }
            context.startActivity(intent);
            return new ResolveInfo();
        } else if (Build.VERSION.SDK_INT <= 20) {
            return ((PackageManagerService) obj).resolveIntent(intent, str, i, i2);
        } else {
            try {
                if (mResolveIntent == null) {
                    mResolveIntent = context.getClassLoader().loadClass("com.android.server.pm.PackageManagerService").getDeclaredMethod("resolveIntent", Intent.class, String.class, Integer.TYPE, Integer.TYPE);
                }
                return (ResolveInfo) mResolveIntent.invoke(obj, intent, str, Integer.valueOf(i), Integer.valueOf(i2));
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }
}
