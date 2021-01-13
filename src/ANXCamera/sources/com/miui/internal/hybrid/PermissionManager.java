package com.miui.internal.hybrid;

import android.net.Uri;
import com.ss.android.ugc.effectmanager.effect.model.ComposerHelper;
import java.util.HashMap;
import java.util.Map;

public class PermissionManager {
    private Config mConfig;
    private Map<String, Boolean> mValidMap = new HashMap();

    public PermissionManager(Config config) {
        this.mConfig = config;
    }

    private boolean initPermission(String str) {
        Uri parse = Uri.parse(str);
        String host = ComposerHelper.COMPOSER_PATH.equals(parse.getScheme()) ? "*" : parse.getHost();
        boolean z = false;
        for (Map.Entry<String, Permission> entry : this.mConfig.getPermissions().entrySet()) {
            Permission value = entry.getValue();
            String uri = value.getUri();
            String host2 = "*".equals(uri) ? "*" : Uri.parse(uri).getHost();
            if (value.isApplySubdomains()) {
                String[] split = host2.split("\\.");
                String[] split2 = host.split("\\.");
                if (split2.length >= split.length) {
                    int i = 1;
                    while (true) {
                        if (i > split.length) {
                            z = true;
                            continue;
                            break;
                        } else if (!split2[split2.length - i].equals(split[split.length - i])) {
                            z = false;
                            continue;
                            break;
                        } else {
                            i++;
                        }
                    }
                } else {
                    continue;
                }
            } else {
                z = host.equals(host2);
                continue;
            }
            if (z) {
                break;
            }
        }
        return z;
    }

    public boolean isValid(String str) {
        if (!this.mValidMap.containsKey(str)) {
            this.mValidMap.put(str, Boolean.valueOf(initPermission(str)));
        }
        return this.mValidMap.get(str).booleanValue();
    }
}
