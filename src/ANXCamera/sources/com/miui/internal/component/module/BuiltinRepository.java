package com.miui.internal.component.module;

import android.content.Context;
import java.io.File;

public class BuiltinRepository extends LocalRepository {
    public BuiltinRepository(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.component.module.LocalRepository
    public File getModuleFile(File file, String str) {
        return new File(file, "libmodule_" + str + ".so");
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.component.module.LocalRepository
    public File getRootFolder(Context context) {
        return new File(context.getApplicationInfo().nativeLibraryDir);
    }
}
