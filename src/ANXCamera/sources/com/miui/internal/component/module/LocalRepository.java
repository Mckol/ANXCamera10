package com.miui.internal.component.module;

import android.content.Context;
import com.xiaomi.stat.d;
import java.io.File;
import java.lang.reflect.Field;
import miui.module.Repository;

public abstract class LocalRepository extends Repository {
    private Context mContext;

    public LocalRepository(Context context) {
        this.mContext = context;
    }

    private boolean createSymlink(File file, File file2) {
        try {
            Field declaredField = Class.forName("libcore.io.Libcore").getDeclaredField(d.l);
            declaredField.setAccessible(true);
            Object obj = declaredField.get(null);
            obj.getClass().getMethod("symlink", String.class, String.class).invoke(obj, file.getAbsolutePath(), file2.getAbsolutePath());
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @Override // miui.module.Repository
    public boolean contains(String str) {
        return getModuleFile(getRootFolder(this.mContext), str).exists();
    }

    @Override // miui.module.Repository
    public String fetch(File file, String str) {
        File moduleFile = getModuleFile(getRootFolder(this.mContext), str);
        if (!moduleFile.exists()) {
            return null;
        }
        File file2 = new File(file, str + ModuleUtils.MODULE_FILE_EXTENSION);
        file2.delete();
        if (createSymlink(moduleFile, file2)) {
            return file2.getName();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract File getModuleFile(File file, String str);

    /* access modifiers changed from: protected */
    public abstract File getRootFolder(Context context);
}
