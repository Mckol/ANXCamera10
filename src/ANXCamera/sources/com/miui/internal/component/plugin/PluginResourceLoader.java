package com.miui.internal.component.plugin;

import android.content.res.Resources;
import java.util.List;

public class PluginResourceLoader {
    private PluginLoader pluginLoader;

    public PluginResourceLoader(PluginLoader pluginLoader2) {
        this.pluginLoader = pluginLoader2;
    }

    public Resources lookup(Class<?> cls) throws PluginException {
        return lookup(cls.getName());
    }

    public Resources lookup(String str) throws PluginException {
        List<Plugin> plugins = this.pluginLoader.getPlugins(str);
        if (plugins == null || plugins.size() == 0) {
            throw new PluginException("no plugin found for extension " + str);
        } else if (plugins.size() <= 1) {
            return this.pluginLoader.getResources(plugins.get(0));
        } else {
            throw new PluginException("more than one plugin found for extension " + str);
        }
    }
}
