package miui.util.async.tasks;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import java.util.ArrayList;
import miui.util.AppConstants;
import miui.util.Pools;
import miui.util.async.Task;

public class ContentResolverBatchTask extends Task<ContentProviderResult[]> {
    private final String mAuthority;
    private String mDescription;
    private final ArrayList<ContentProviderOperation> mOperations;

    public ContentResolverBatchTask(String str, ArrayList<ContentProviderOperation> arrayList) {
        this.mAuthority = str;
        this.mOperations = arrayList;
    }

    @Override // miui.util.async.Task
    public ContentProviderResult[] doLoad() throws Exception {
        return AppConstants.getCurrentApplication().getContentResolver().applyBatch(this.mAuthority, this.mOperations);
    }

    @Override // miui.util.async.Task
    public String getDescription() {
        if (this.mDescription == null) {
            StringBuilder acquire = Pools.getStringBuilderPool().acquire();
            acquire.append('[');
            if (this.mOperations.size() == 0) {
                acquire.append("NULL");
            } else {
                acquire.append(this.mOperations.get(0));
                int size = this.mOperations.size();
                for (int i = 1; i < size; i++) {
                    acquire.append("; ");
                    acquire.append(this.mOperations.get(i));
                }
            }
            acquire.append("]@");
            acquire.append(this.mAuthority);
            this.mDescription = acquire.toString();
            Pools.getStringBuilderPool().release(acquire);
        }
        return this.mDescription;
    }
}
