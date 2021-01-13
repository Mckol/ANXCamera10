package miui.util.async.tasks;

import android.content.ContentValues;
import android.net.Uri;
import miui.util.AppConstants;
import miui.util.Pools;
import miui.util.async.Task;

public class ContentResolverUpdateTask extends Task<Integer> {
    private String mDescription;
    private final String[] mSelectionArgs;
    private final Uri mUri;
    private final ContentValues mValues;
    private final String mWhere;

    public ContentResolverUpdateTask(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        this.mUri = uri;
        this.mValues = contentValues;
        this.mWhere = str;
        this.mSelectionArgs = strArr;
    }

    @Override // miui.util.async.Task
    public Integer doLoad() throws Exception {
        return Integer.valueOf(AppConstants.getCurrentApplication().getContentResolver().update(this.mUri, this.mValues, this.mWhere, this.mSelectionArgs));
    }

    @Override // miui.util.async.Task
    public String getDescription() {
        if (this.mDescription == null) {
            if (this.mWhere == null) {
                this.mDescription = this.mUri.toString();
            } else {
                StringBuilder acquire = Pools.getStringBuilderPool().acquire();
                acquire.append(this.mUri.toString());
                acquire.append(" WHERE ");
                if (this.mSelectionArgs != null) {
                    acquire.append('[');
                    acquire.append(this.mSelectionArgs[0]);
                    for (int i = 1; i < this.mSelectionArgs.length; i++) {
                        acquire.append(';');
                        acquire.append(this.mSelectionArgs[i]);
                    }
                    acquire.append("]@");
                }
                acquire.append(this.mWhere);
                this.mDescription = acquire.toString();
                Pools.getStringBuilderPool().release(acquire);
            }
        }
        return this.mDescription;
    }
}
