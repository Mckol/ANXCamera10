package com.miui.internal.hybrid.webkit;

import android.content.Context;
import android.webkit.CookieSyncManager;
import com.miui.internal.hybrid.provider.AbsCookieSyncManager;

public class CookieSyncManagerDelegate extends AbsCookieSyncManager {
    @Override // com.miui.internal.hybrid.provider.AbsCookieSyncManager
    public void createInstance(Context context) {
        CookieSyncManager.createInstance(context);
    }

    @Override // com.miui.internal.hybrid.provider.AbsCookieSyncManager
    public void getInstance() {
        CookieSyncManager.getInstance();
    }

    @Override // com.miui.internal.hybrid.provider.AbsCookieSyncManager
    public void sync() {
        CookieSyncManager.getInstance().sync();
    }
}
