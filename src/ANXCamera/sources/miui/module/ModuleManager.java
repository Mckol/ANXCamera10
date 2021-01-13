package miui.module;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.miui.internal.component.module.BuiltinRepository;
import com.miui.internal.component.module.CombinedRepository;
import com.miui.internal.component.module.ModuleFolder;
import com.miui.internal.component.module.ModuleLoadException;
import com.miui.internal.component.module.ModuleLoader;
import com.miui.internal.component.module.ModuleUtils;
import com.miui.internal.component.module.PrebuiltRepository;
import com.miui.internal.util.PackageConstants;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import miui.os.FileUtils;

public class ModuleManager {
    private static final ModuleManager INSTANCE = new ModuleManager(PackageConstants.sApplication);
    private static final String MODULE_TARGET_FOLDER = "modules";
    private static final String MODULE_TEMP_FOLDER = "modules-temp";
    private static final WeakHashMap<Application, ModuleManager> sModuleManagerMap = new WeakHashMap<>();
    private Application mApplication;
    private ModuleLoadListener mListener;
    private ModuleLoader mModuleLoader;
    private File mModulesFolder = getModulesFolder(this.mApplication);
    private File mTempModulesFolder;

    public interface ModuleLoadListener {
        public static final int FETCH_ERROR = 1;
        public static final int IO_ERROR = 2;
        public static final int LOAD_ERROR = 3;

        void onLoadFail(String str, int i);

        void onLoadFinish();

        void onLoadSuccess(String str, String str2);
    }

    private ModuleManager(Application application) {
        this.mApplication = application;
        this.mModulesFolder.mkdirs();
        this.mTempModulesFolder = getTempModulesFolder(this.mApplication);
        this.mTempModulesFolder.mkdirs();
        Application application2 = this.mApplication;
        File file = this.mModulesFolder;
        this.mModuleLoader = new ModuleLoader(application2, new ModuleFolder(file, file));
    }

    private boolean copyModule(File file, File file2, String str, String str2) {
        String str3 = str2 + ModuleUtils.MODULE_FILE_EXTENSION;
        File file3 = new File(file, str);
        File file4 = new File(file2, str3);
        if (!file3.exists()) {
            return false;
        }
        file4.delete();
        return file3.renameTo(file4);
    }

    public static ModuleManager createInstance(Application application) {
        ModuleManager moduleManager;
        if (application == null || application == PackageConstants.sApplication) {
            return INSTANCE;
        }
        synchronized (sModuleManagerMap) {
            moduleManager = sModuleManagerMap.get(application);
            if (moduleManager == null) {
                moduleManager = new ModuleManager(application);
                sModuleManagerMap.put(application, moduleManager);
            }
        }
        return moduleManager;
    }

    private boolean extractLibrary(File file, String str) throws IOException {
        File moduleFile = getModuleFile(str);
        File libraryFolder = ModuleUtils.getLibraryFolder(moduleFile.getAbsolutePath());
        if (libraryFolder.exists()) {
            return true;
        }
        libraryFolder.mkdirs();
        ZipFile zipFile = new ZipFile(moduleFile);
        try {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                if (!zipEntry.isDirectory()) {
                    String name = zipEntry.getName();
                    if (!name.contains("..")) {
                        if (!name.startsWith("lib/" + Build.CPU_ABI)) {
                            if (!name.startsWith("lib/" + Build.CPU_ABI2)) {
                            }
                        }
                        File file2 = new File(libraryFolder, FileUtils.getFileName(name));
                        if (!file2.exists()) {
                            FileUtils.copyToFile(zipFile.getInputStream(zipEntry), file2);
                        }
                    }
                }
            }
            return true;
        } finally {
            zipFile.close();
        }
    }

    public static ModuleManager getInstance() {
        return INSTANCE;
    }

    private File getModuleFile(String str) {
        return new File(this.mModulesFolder, str + ModuleUtils.MODULE_FILE_EXTENSION);
    }

    private File getModulesFolder(Context context) {
        return new File(PackageConstants.getSdkBaseFolder(context), MODULE_TARGET_FOLDER);
    }

    private File getTempModulesFolder(Context context) {
        return new File(PackageConstants.getSdkBaseFolder(context), MODULE_TEMP_FOLDER);
    }

    private void notifyFail(String str, int i) {
        Log.w("miuisdk", "process " + str + " failed: " + i);
        ModuleLoadListener moduleLoadListener = this.mListener;
        if (moduleLoadListener != null) {
            moduleLoadListener.onLoadFail(str, i);
        }
    }

    private void notifyFinish() {
        Log.d("miuisdk", "process modules finished");
        ModuleLoadListener moduleLoadListener = this.mListener;
        if (moduleLoadListener != null) {
            moduleLoadListener.onLoadFinish();
        }
    }

    private void notifySuccess(String str, String str2) {
        Log.d("miuisdk", "process " + str + " successful");
        ModuleLoadListener moduleLoadListener = this.mListener;
        if (moduleLoadListener != null) {
            moduleLoadListener.onLoadSuccess(str, str2);
        }
    }

    public void loadModules(Repository repository, String... strArr) {
        if (!(strArr == null || strArr.length == 0)) {
            BuiltinRepository builtinRepository = new BuiltinRepository(this.mApplication);
            PrebuiltRepository prebuiltRepository = new PrebuiltRepository(this.mApplication);
            CombinedRepository combinedRepository = repository == null ? new CombinedRepository(builtinRepository, prebuiltRepository) : new CombinedRepository(builtinRepository, prebuiltRepository, repository);
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (String str : strArr) {
                if (!getModuleFile(str).exists()) {
                    linkedHashSet.add(str);
                }
            }
            Map<String, String> emptyMap = linkedHashSet.isEmpty() ? Collections.emptyMap() : combinedRepository.fetch(this.mTempModulesFolder, combinedRepository.contains(linkedHashSet));
            for (String str2 : strArr) {
                Log.i("miuisdk", "loading module: " + str2);
                if (!emptyMap.containsKey(str2) || copyModule(this.mTempModulesFolder, this.mModulesFolder, emptyMap.get(str2), str2)) {
                    File moduleFile = getModuleFile(str2);
                    if (moduleFile.exists()) {
                        try {
                            if (!extractLibrary(this.mModulesFolder, str2)) {
                                Log.w("miuisdk", "fail to extract library to " + this.mModulesFolder + " with " + str2);
                                notifyFail(str2, 2);
                            }
                        } catch (IOException e2) {
                            Log.e("miuisdk", "got IOException when extract lib: " + e2.getMessage());
                            notifyFail(str2, 2);
                        }
                    }
                    Dependency dependency = new Dependency();
                    dependency.setName(str2);
                    try {
                        this.mModuleLoader.loadModules(dependency);
                        notifySuccess(str2, moduleFile.getAbsolutePath());
                    } catch (ModuleLoadException e3) {
                        Log.e("miuisdk", "got ModuleLoadException when load modules: " + e3.getMessage());
                        if (!linkedHashSet.contains(str2) || emptyMap.containsKey(str2)) {
                            notifyFail(str2, 3);
                        } else {
                            notifyFail(str2, 1);
                        }
                    } catch (Exception e4) {
                        Log.e("miuisdk", "got Exception when load modules: " + e4.getMessage());
                        notifyFail(str2, 3);
                    }
                } else {
                    Log.w("miuisdk", "can't copy module from " + this.mTempModulesFolder + " to " + this.mModulesFolder + " with " + str2);
                    notifyFail(str2, 2);
                }
            }
            notifyFinish();
        }
    }

    public void loadModules(String... strArr) {
        loadModules(null, strArr);
    }

    public void setModuleLoadListener(ModuleLoadListener moduleLoadListener) {
        this.mListener = moduleLoadListener;
    }
}
