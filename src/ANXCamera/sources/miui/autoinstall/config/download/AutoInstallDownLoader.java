package miui.autoinstall.config.download;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.miui.internal.util.ReflectUtils;

public class AutoInstallDownLoader {
    private DownloadManager mDownloaderManager;

    public AutoInstallDownLoader(Context context) {
        initialize(context);
    }

    private DownloadManager.Request createRequest(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            return null;
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
        request.setAllowedOverRoaming(true);
        request.setDestinationInExternalFilesDir(context, "autoinstall_downloads", str2);
        request.setNotificationVisibility(2);
        try {
            String format = String.format("{\"bypass_recommended_size_limit\":%s}", true);
            ReflectUtils.invoke(request, ReflectUtils.getDeclaredMethod(DownloadManager.Request.class, "setExtra2", String.class), format);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return request;
    }

    @TargetApi(9)
    private void initialize(Context context) {
        this.mDownloaderManager = (DownloadManager) context.getSystemService("download");
    }

    public long enqueue(Context context, String str, String str2) {
        DownloadManager.Request createRequest;
        if (TextUtils.isEmpty(str) || (createRequest = createRequest(context, str, str2)) == null) {
            return -1;
        }
        return this.mDownloaderManager.enqueue(createRequest);
    }

    public String queryFileDir(long j) {
        Cursor query = this.mDownloaderManager.query(new DownloadManager.Query().setFilterById(j));
        return (query == null || !query.moveToFirst()) ? "" : Uri.parse(query.getString(query.getColumnIndex("local_uri"))).getPath();
    }

    public int queryStatus(long j) {
        DownloadManager.Query query = new DownloadManager.Query();
        Cursor query2 = this.mDownloaderManager.query(query.setFilterById(j));
        if (query2 == null || !query2.moveToFirst()) {
            return -1;
        }
        return query2.getInt(query2.getColumnIndex("status"));
    }
}
