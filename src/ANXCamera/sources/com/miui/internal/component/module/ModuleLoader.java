package com.miui.internal.component.module;

import android.app.Application;
import android.text.TextUtils;
import com.miui.internal.util.PackageHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import miui.core.ManifestParser;
import miui.module.Dependency;
import miui.module.Module;

public class ModuleLoader {
    private Set<String> loadedModules = new LinkedHashSet();
    private Set<String> loadedResources = new LinkedHashSet();
    private Application mApplication;
    private ModuleFolder[] moduleFolders;

    /* access modifiers changed from: private */
    public static class ModuleEntry {
        public String apkPath;
        public String libFolderPath;
        public Module module;
        public String optFolderPath;
        public boolean optional;

        public ModuleEntry(Module module2, String str, String str2, String str3, boolean z) {
            this.module = module2;
            this.apkPath = str;
            this.libFolderPath = str2;
            this.optFolderPath = str3;
            this.optional = z;
        }
    }

    public ModuleLoader(Application application, ModuleFolder... moduleFolderArr) {
        this.mApplication = application;
        this.moduleFolders = moduleFolderArr;
    }

    private void load(ModuleEntry moduleEntry, String str) {
        String str2 = null;
        String str3 = (moduleEntry.module.getContent() & 1) != 0 ? moduleEntry.apkPath : null;
        String str4 = (moduleEntry.module.getContent() & 2) != 0 ? moduleEntry.libFolderPath : null;
        if ((moduleEntry.module.getContent() & 4) != 0) {
            str2 = moduleEntry.apkPath;
        }
        boolean z = false;
        if (!(str3 == null && str4 == null) && (!loadClass(str, str3, str4)) && !moduleEntry.optional) {
            throw new ModuleLoadException("encounter error when load dex for module: " + moduleEntry.module.getName());
        }
        if (str2 != null && !z) {
            loadResource(str2);
            if (!z || moduleEntry.optional) {
                this.loadedResources.add(moduleEntry.apkPath);
            } else {
                throw new ModuleLoadException("encounter error when load res for module: " + moduleEntry.module.getName());
            }
        }
        if (!z) {
            this.loadedModules.add(moduleEntry.module.getName());
        }
    }

    private boolean loadClass(String str, String str2, String str3) {
        return ModuleClassLoader.load(str2, str, str3, this.mApplication.getClassLoader(), this.mApplication);
    }

    private void loadResource(String str) {
        ModuleResourceLoader.load(Arrays.asList(str), new ArrayList(this.loadedResources));
    }

    public static void loadResources(String... strArr) {
        ModuleResourceLoader.load(Arrays.asList(strArr));
    }

    private Module resolveModule(String str) {
        return ManifestParser.createFromArchive(this.mApplication.getPackageManager(), str).parse(null).getModule();
    }

    public void loadModules(Dependency... dependencyArr) {
        boolean z;
        ArrayList<ModuleEntry> arrayList = new ArrayList();
        ArrayList<ModuleEntry> arrayList2 = new ArrayList();
        for (Dependency dependency : dependencyArr) {
            if (!this.loadedModules.contains(dependency.getName())) {
                boolean z2 = (dependency.getType() & 1) != 0;
                ModuleFolder[] moduleFolderArr = this.moduleFolders;
                int length = moduleFolderArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        z = false;
                        break;
                    }
                    ModuleFolder moduleFolder = moduleFolderArr[i];
                    File file = new File(moduleFolder.getApkFolder(), dependency.getName() + ModuleUtils.MODULE_FILE_EXTENSION);
                    if (file.exists()) {
                        String absolutePath = file.getAbsolutePath();
                        arrayList.add(new ModuleEntry(resolveModule(absolutePath), absolutePath, ModuleUtils.getLibraryFolder(absolutePath).getAbsolutePath(), moduleFolder.getOptFolder().getAbsolutePath(), z2));
                        z = true;
                        break;
                    }
                    i++;
                }
                if (!z) {
                    String apkPath = PackageHelper.getApkPath(this.mApplication, dependency.getName(), null);
                    File file2 = TextUtils.isEmpty(apkPath) ? null : new File(apkPath);
                    if (file2 != null && file2.exists()) {
                        String absolutePath2 = file2.getAbsolutePath();
                        arrayList2.add(new ModuleEntry(resolveModule(absolutePath2), absolutePath2, ModuleUtils.getLibraryFolder(absolutePath2).getAbsolutePath(), null, z2));
                    } else if (!z2) {
                        throw new ModuleLoadException("no such module found: " + dependency.getName());
                    }
                } else {
                    continue;
                }
            }
        }
        for (ModuleEntry moduleEntry : arrayList) {
            if (ModuleUtils.isSignatureValid(this.mApplication, moduleEntry.apkPath)) {
                load(moduleEntry, moduleEntry.optFolderPath);
            } else if (!moduleEntry.optional) {
                throw new ModuleLoadException("invalid signature: " + moduleEntry.module.getName());
            }
        }
        for (ModuleEntry moduleEntry2 : arrayList2) {
            load(moduleEntry2, null);
        }
    }
}
