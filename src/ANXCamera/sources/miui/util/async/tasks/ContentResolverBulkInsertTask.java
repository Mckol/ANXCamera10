package miui.util.async.tasks;

import android.content.ContentValues;
import android.net.Uri;
import miui.util.AppConstants;
import miui.util.Pools;
import miui.util.async.Task;

public class ContentResolverBulkInsertTask extends Task<Integer> {
    private String mDescription;
    private final Uri mUri;
    private final ContentValues[] mValues;

    public ContentResolverBulkInsertTask(Uri uri, ContentValues[] contentValuesArr) {
        this.mUri = uri;
        this.mValues = contentValuesArr;
    }

    @Override // miui.util.async.Task
    public Integer doLoad() throws Exception {
        return Integer.valueOf(AppConstants.getCurrentApplication().getContentResolver().bulkInsert(this.mUri, this.mValues));
    }

    @Override // miui.util.async.Task
    public String getDescription() {
        if (this.mDescription == null) {
            StringBuilder acquire = Pools.getStringBuilderPool().acquire();
            acquire.append('[');
            ContentValues[] contentValuesArr = this.mValues;
            if (contentValuesArr.length == 0) {
                acquire.append("NULL");
            } else {
                acquire.append(contentValuesArr[0]);
                int length = this.mValues.length;
                for (int i = 1; i < length; i++) {
                    acquire.append("; ");
                    acquire.append(this.mValues[i]);
                }
            }
            acquire.append("]@");
            acquire.append(this.mUri);
            this.mDescription = acquire.toString();
            Pools.getStringBuilderPool().release(acquire);
        }
        return this.mDescription;
    }
}
