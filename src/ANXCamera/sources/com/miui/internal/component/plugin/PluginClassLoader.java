package com.miui.internal.component.plugin;

import java.util.List;

public class PluginClassLoader {
    private PluginLoader pluginLoader;

    public PluginClassLoader(PluginLoader pluginLoader2) {
        this.pluginLoader = pluginLoader2;
    }

    public <T> T lookup(Class<T> cls) throws PluginException {
        return (T) lookup(cls.getName());
    }

    public <T> T lookup(String str) throws PluginException {
        List<Plugin> plugins = this.pluginLoader.getPlugins(str);
        if (plugins == null || plugins.size() == 0) {
            throw new PluginException("no plugin found for extension " + str);
        } else if (plugins.size() <= 1) {
            Plugin plugin = plugins.get(0);
            try {
                return (T) this.pluginLoader.getClassLoader(plugin).loadClass(plugin.getExtension(str).getLocation()).newInstance();
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
                throw new PluginException("no impl of extension " + str + " found in plugin " + plugin.getName());
            } catch (InstantiationException e3) {
                e3.printStackTrace();
                throw new PluginException("init impl of extension " + str + " failed in plugin " + plugin.getName());
            } catch (IllegalAccessException e4) {
                e4.printStackTrace();
                throw new PluginException("access impl of extension " + str + " failed in plugin " + plugin.getName());
            }
        } else {
            throw new PluginException("more than one plugin found for extension " + str);
        }
    }
}
