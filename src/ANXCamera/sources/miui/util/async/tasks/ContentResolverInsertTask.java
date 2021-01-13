package miui.util.async.tasks;

import android.content.ContentValues;
import android.net.Uri;
import miui.util.AppConstants;
import miui.util.async.Task;

public class ContentResolverInsertTask extends Task<Uri> {
    private String mDescription;
    private final Uri mUri;
    private final ContentValues mValues;

    public ContentResolverInsertTask(Uri uri, ContentValues contentValues) {
        this.mUri = uri;
        this.mValues = contentValues;
    }

    @Override // miui.util.async.Task
    public Uri doLoad() throws Exception {
        return AppConstants.getCurrentApplication().getContentResolver().insert(this.mUri, this.mValues);
    }

    @Override // miui.util.async.Task
    public String getDescription() {
        if (this.mDescription == null) {
            this.mDescription = this.mValues.toString() + '@' + this.mUri;
        }
        return this.mDescription;
    }
}
