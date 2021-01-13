package com.miui.internal.component.module;

import android.content.Context;
import com.miui.internal.util.DeviceHelper;
import java.io.File;

public class PrebuiltRepository extends LocalRepository {
    public PrebuiltRepository(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.component.module.LocalRepository
    public File getModuleFile(File file, String str) {
        return new File(file, str + ModuleUtils.MODULE_FILE_EXTENSION);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.component.module.LocalRepository
    public File getRootFolder(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(DeviceHelper.HAS_CUST_PARTITION ? "/cust/prebuilts/" : "/data/miui/prebuilts/");
        sb.append(context.getPackageName());
        return new File(sb.toString());
    }
}
