package com.miui.internal.component.plugin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import miui.os.FileUtils;

public class PluginLoader {
    private static final String OPT_FOLDER = "plugins-opt";
    private static final String PLUGIN_FILE_EXTENSION = ".apk";
    private static final String PLUGIN_MANIFEST_BUILT_IN_PATH = "assets/PluginManifest.xml";
    private static final String PLUGIN_MANIFEST_EXTENSION = ".xml";
    private static final String PLUGIN_MANIFEST_FILE_NAME = "PluginManifest.xml";
    private static Map<String, ClassLoader> classLoaderMap = new HashMap();
    private static Map<String, List<Plugin>> extensionMap = new HashMap();
    private static Map<String, Plugin> pluginMap = new HashMap();
    private static Map<String, Resources> resourcesMap = new HashMap();
    private File optFolder = new File(this.pluginFolder.getParentFile(), OPT_FOLDER);
    private File pluginFolder;

    public PluginLoader(File file) {
        this.pluginFolder = file;
        this.pluginFolder.mkdirs();
        this.optFolder.mkdirs();
        loadPlugins();
        buildExtensionMap();
    }

    private void addAssetPath(AssetManager assetManager, String str) {
        try {
            AssetManager.class.getMethod("addAssetPath", String.class).invoke(assetManager, str);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            throw new PluginException("invoke asset manager encounter an error: " + e2.getMessage());
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
            throw new PluginException("invoke asset manager encounter an error: " + e3.getMessage());
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            throw new PluginException("invoke asset manager encounter an error: " + e4.getMessage());
        } catch (NoSuchMethodException e5) {
            e5.printStackTrace();
            throw new PluginException("invoke asset manager encounter an error: " + e5.getMessage());
        }
    }

    private void buildExtensionMap() {
        for (String str : pluginMap.keySet()) {
            Plugin plugin = pluginMap.get(str);
            for (String str2 : plugin.getExtensions().keySet()) {
                List<Plugin> list = extensionMap.get(str2);
                if (list == null) {
                    list = new ArrayList<>();
                    extensionMap.put(str2, list);
                }
                list.add(plugin);
            }
        }
    }

    private Plugin loadPlugin(File file, File file2) throws IOException, PluginParseException {
        return loadPlugin(new FileInputStream(file), file2);
    }

    private Plugin loadPlugin(InputStream inputStream, File file) throws IOException, PluginParseException {
        Plugin parsePlugin = new PluginManifestParser().parsePlugin(inputStream);
        parsePlugin.setFile(file);
        return parsePlugin;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0093 A[SYNTHETIC, Splitter:B:38:0x0093] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x009c A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x009c A[SYNTHETIC] */
    private void loadPlugins() {
        Throwable th;
        IOException e2;
        PluginParseException e3;
        loadSelf();
        File[] listFiles = this.pluginFolder.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.getName().endsWith(".apk")) {
                    File file2 = new File(this.pluginFolder, FileUtils.getName(file.getPath()) + PLUGIN_MANIFEST_EXTENSION);
                    ZipFile zipFile = null;
                    try {
                        if (!file2.exists()) {
                            ZipFile zipFile2 = new ZipFile(file);
                            try {
                                FileUtils.copyToFile(zipFile2.getInputStream(new ZipEntry(PLUGIN_MANIFEST_BUILT_IN_PATH)), file2);
                                zipFile = zipFile2;
                            } catch (IOException e4) {
                                e2 = e4;
                                zipFile = zipFile2;
                                e2.printStackTrace();
                                if (zipFile == null) {
                                }
                            } catch (PluginParseException e5) {
                                e3 = e5;
                                zipFile = zipFile2;
                                try {
                                    e3.printStackTrace();
                                    if (zipFile == null) {
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (zipFile != null) {
                                        try {
                                            zipFile.close();
                                        } catch (IOException e6) {
                                            e6.printStackTrace();
                                        }
                                    }
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                zipFile = zipFile2;
                                if (zipFile != null) {
                                }
                                throw th;
                            }
                        }
                        Plugin loadPlugin = loadPlugin(file2, file);
                        pluginMap.put(loadPlugin.getName(), loadPlugin);
                        if (zipFile != null) {
                            try {
                                zipFile.close();
                            } catch (IOException e7) {
                                e7.printStackTrace();
                            }
                        }
                    } catch (IOException e8) {
                        e2 = e8;
                        e2.printStackTrace();
                        if (zipFile == null) {
                            zipFile.close();
                        }
                    } catch (PluginParseException e9) {
                        e3 = e9;
                        e3.printStackTrace();
                        if (zipFile == null) {
                            zipFile.close();
                        }
                    }
                }
            }
        }
    }

    private void loadSelf() {
        Context applicationContext = PluginContext.getInstance().getApplicationContext();
        Plugin plugin = null;
        try {
            String packageResourcePath = applicationContext.getPackageResourcePath();
            if (packageResourcePath != null) {
                plugin = loadPlugin(applicationContext.getAssets().open(PLUGIN_MANIFEST_FILE_NAME), new File(packageResourcePath));
            }
        } catch (IOException unused) {
        } catch (PluginParseException e2) {
            e2.printStackTrace();
        }
        if (plugin == null) {
            plugin = new Plugin();
            plugin.setName(applicationContext.getPackageName());
            plugin.setGroup(applicationContext.getPackageName());
            plugin.setResources(AccessPermission.PRIVATE);
        }
        pluginMap.put(plugin.getName(), plugin);
        classLoaderMap.put(plugin.getName(), PluginLoader.class.getClassLoader());
        resourcesMap.put(plugin.getName(), applicationContext.getResources());
    }

    private ClassLoader newClassLoader(Plugin plugin) {
        return new DexClassLoader(plugin.getFile().getAbsolutePath(), this.optFolder.getAbsolutePath(), null, PluginLoader.class.getClassLoader());
    }

    private Resources newResources(Plugin plugin) {
        try {
            AssetManager assetManager = (AssetManager) AssetManager.class.newInstance();
            addAssetPath(assetManager, plugin.getFile().getPath());
            for (Dependency dependency : plugin.getDependencies().values()) {
                if (dependency.isResourcesRequired()) {
                    addAssetPath(assetManager, pluginMap.get(dependency.getName()).getFile().getPath());
                }
            }
            Resources resources = PluginContext.getInstance().getApplicationContext().getResources();
            return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
        } catch (InstantiationException e2) {
            e2.printStackTrace();
            throw new PluginException("invoke asset manager encounter an error: " + e2.getMessage());
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            throw new PluginException("invoke asset manager encounter an error: " + e3.getMessage());
        }
    }

    public ClassLoader getClassLoader(Plugin plugin) {
        if (!classLoaderMap.containsKey(plugin.getName())) {
            synchronized (classLoaderMap) {
                if (!classLoaderMap.containsKey(plugin.getName())) {
                    classLoaderMap.put(plugin.getName(), newClassLoader(plugin));
                }
            }
        }
        return classLoaderMap.get(plugin.getName());
    }

    public List<Plugin> getPlugins(String str) {
        return extensionMap.get(str);
    }

    public Resources getResources(Plugin plugin) {
        if (!resourcesMap.containsKey(plugin.getName())) {
            synchronized (resourcesMap) {
                if (!resourcesMap.containsKey(plugin.getName())) {
                    resourcesMap.put(plugin.getName(), newResources(plugin));
                }
            }
        }
        return resourcesMap.get(plugin.getName());
    }

    public boolean hasExtension(String str) {
        return extensionMap.containsKey(str);
    }
}
