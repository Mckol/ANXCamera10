package com.bumptech.glide.load.engine.a;

import android.content.Context;
import com.bumptech.glide.load.engine.a.f.a;
import java.io.File;

/* compiled from: InternalCacheDiskCacheFactory */
class l implements a {
    final /* synthetic */ String Eg;
    final /* synthetic */ Context val$context;

    l(Context context, String str) {
        this.val$context = context;
        this.Eg = str;
    }

    public File w() {
        File cacheDir = this.val$context.getCacheDir();
        if (cacheDir == null) {
            return null;
        }
        String str = this.Eg;
        return str != null ? new File(cacheDir, str) : cacheDir;
    }
}
