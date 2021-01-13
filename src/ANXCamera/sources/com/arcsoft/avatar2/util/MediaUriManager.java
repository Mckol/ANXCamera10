package com.arcsoft.avatar2.util;

import android.content.Context;
import android.database.sqlite.SQLiteFullException;
import android.media.MediaScannerConnection;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

public class MediaUriManager implements MediaScannerConnection.MediaScannerConnectionClient {

    /* renamed from: c  reason: collision with root package name */
    private static final int f284c = 100;

    /* renamed from: a  reason: collision with root package name */
    private Context f285a;

    /* renamed from: b  reason: collision with root package name */
    private MediaScannerConnection f286b;

    /* renamed from: d  reason: collision with root package name */
    private List<Uri> f287d = new ArrayList();

    /* renamed from: e  reason: collision with root package name */
    private String f288e;

    public MediaUriManager(Context context) {
        this.f285a = context;
        this.f286b = new MediaScannerConnection(this.f285a, this);
    }

    public void addPath(String str) {
        this.f288e = str;
        this.f286b.connect();
    }

    public void addUri(Uri uri) {
        if (uri != null) {
            this.f287d.add(uri);
        }
    }

    public void addUris(List<Uri> list) {
        if (list != null && !list.isEmpty()) {
            this.f287d.addAll(list);
        }
    }

    public Uri getCurrentMediaUri() {
        if (this.f287d.isEmpty()) {
            return null;
        }
        return this.f287d.get(0);
    }

    public List<Uri> getUris() {
        return this.f287d;
    }

    public boolean isEmpty() {
        List<Uri> list = this.f287d;
        return list == null || list.isEmpty();
    }

    public void onMediaScannerConnected() {
        try {
            this.f286b.scanFile(this.f288e, null);
        } catch (SQLiteFullException e2) {
            e2.printStackTrace();
        }
    }

    public void onScanCompleted(String str, Uri uri) {
        try {
            if (this.f287d.size() > 100) {
                this.f287d.remove(this.f287d.size() - 1);
            }
            this.f287d.add(0, uri);
        } finally {
            this.f286b.disconnect();
        }
    }

    public void release() {
        MediaScannerConnection mediaScannerConnection = this.f286b;
        if (mediaScannerConnection != null && mediaScannerConnection.isConnected()) {
            this.f286b.disconnect();
        }
    }
}
