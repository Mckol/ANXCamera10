package miui.util.async.tasks;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import java.lang.ref.WeakReference;
import miui.util.AppConstants;
import miui.util.Pools;
import miui.util.async.Task;
import miui.util.async.TaskManager;

public abstract class ContentResolverQueryTask<T> extends Task<T> {
    private boolean mAutoRequery;
    private String mDescription;
    private boolean mHasMissingContentChange;
    private boolean mPauseAutoRequery;
    private final String[] mProjection;
    private final String mSelection;
    private final String[] mSelectionArgs;
    private final String mSortOrder;
    private WeakReference<TaskManager> mTaskManager;
    private final Uri mUri;
    private UriObserver mUriObserver;

    public class Cookie {
        public Cookie() {
        }

        public void pause() {
            ContentResolverQueryTask.this.pause();
        }

        public void release() {
            ContentResolverQueryTask.this.disableAutoRequery();
        }

        public void resume() {
            ContentResolverQueryTask.this.resume();
        }
    }

    /* access modifiers changed from: private */
    public static class UriObserver extends ContentObserver {
        private final WeakReference<ContentResolverQueryTask> mTask;

        public UriObserver(ContentResolverQueryTask contentResolverQueryTask) {
            super(new Handler(AppConstants.getCurrentApplication().getMainLooper()));
            this.mTask = new WeakReference<>(contentResolverQueryTask);
        }

        public void onChange(boolean z) {
            ContentResolverQueryTask contentResolverQueryTask = this.mTask.get();
            if (contentResolverQueryTask != null) {
                contentResolverQueryTask.requery();
            }
        }
    }

    public ContentResolverQueryTask(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        this.mUri = uri;
        this.mProjection = strArr;
        this.mSelection = str;
        this.mSelectionArgs = strArr2;
        this.mSortOrder = str2;
    }

    public void disableAutoRequery() {
        this.mAutoRequery = false;
        if (this.mUriObserver != null) {
            AppConstants.getCurrentApplication().getContentResolver().unregisterContentObserver(this.mUriObserver);
            this.mUriObserver = null;
        }
    }

    public ContentResolverQueryTask<T>.Cookie enableAutoRequery() {
        if (!this.mAutoRequery) {
            this.mAutoRequery = true;
            this.mUriObserver = new UriObserver(this);
            AppConstants.getCurrentApplication().getContentResolver().registerContentObserver(this.mUri, true, this.mUriObserver);
        }
        return new Cookie();
    }

    @Override // miui.util.async.Task
    public String getDescription() {
        if (this.mDescription == null) {
            StringBuilder acquire = Pools.getStringBuilderPool().acquire();
            if (this.mProjection != null) {
                acquire.append('[');
                acquire.append(this.mProjection[0]);
                for (int i = 1; i < this.mProjection.length; i++) {
                    acquire.append(';');
                    acquire.append(this.mProjection[i]);
                }
                acquire.append("]@");
            }
            acquire.append(this.mUri);
            if (this.mSelection != null) {
                acquire.append(" WHERE ");
                if (this.mSelectionArgs != null) {
                    acquire.append('[');
                    acquire.append(this.mSelectionArgs[0]);
                    for (int i2 = 1; i2 < this.mSelectionArgs.length; i2++) {
                        acquire.append(';');
                        acquire.append(this.mSelectionArgs[i2]);
                    }
                    acquire.append("]@");
                }
                acquire.append(this.mSelection);
            }
            if (this.mSortOrder != null) {
                acquire.append(" ORDER BY ");
                acquire.append(this.mSortOrder);
            }
            this.mDescription = acquire.toString();
            Pools.getStringBuilderPool().release(acquire);
        }
        return this.mDescription;
    }

    @Override // miui.util.async.Task
    public void onPrepare(TaskManager taskManager) {
        this.mTaskManager = new WeakReference<>(taskManager);
    }

    /* access modifiers changed from: package-private */
    public void pause() {
        this.mPauseAutoRequery = true;
    }

    /* access modifiers changed from: protected */
    public Cursor query() throws Exception {
        return AppConstants.getCurrentApplication().getContentResolver().query(this.mUri, this.mProjection, this.mSelection, this.mSelectionArgs, this.mSortOrder);
    }

    /* access modifiers changed from: package-private */
    public void requery() {
        if (this.mPauseAutoRequery) {
            this.mHasMissingContentChange = true;
            return;
        }
        WeakReference<TaskManager> weakReference = this.mTaskManager;
        if (weakReference != null) {
            TaskManager taskManager = weakReference.get();
            if (taskManager != null) {
                restart();
                taskManager.add(this);
                return;
            }
            disableAutoRequery();
        }
    }

    /* access modifiers changed from: package-private */
    public void resume() {
        this.mPauseAutoRequery = false;
        if (this.mHasMissingContentChange) {
            this.mHasMissingContentChange = false;
            requery();
        }
    }
}
