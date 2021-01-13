package com.miui.internal.hybrid.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import com.miui.internal.hybrid.HybridManager;
import com.miui.internal.hybrid.webkit.WebkitFactoryProvider;

public class WebViewFactory {
    private static final String META_DATA_KEY = "com.miui.sdk.hybrid.webview";
    private static WebViewFactoryProvider sProviderInstance;
    private static final Object sProviderLock = new Object();

    public static WebViewFactoryProvider getProvider(Context context) {
        synchronized (sProviderLock) {
            if (sProviderInstance != null) {
                return sProviderInstance;
            }
            String str = null;
            try {
                Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
                if (bundle != null) {
                    str = bundle.getString(META_DATA_KEY);
                }
            } catch (PackageManager.NameNotFoundException e2) {
                e2.printStackTrace();
            }
            if (str != null) {
                try {
                    sProviderInstance = (WebViewFactoryProvider) Class.forName(str, false, context.getClassLoader()).newInstance();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            if (sProviderInstance == null) {
                sProviderInstance = new WebkitFactoryProvider();
            }
            if (Log.isLoggable(HybridManager.TAG, 3)) {
                Log.d(HybridManager.TAG, "loaded provider:" + sProviderInstance);
            }
            return sProviderInstance;
        }
    }
}
