package com.arcsoft.avatar.util;

import android.content.Context;
import android.database.sqlite.SQLiteFullException;
import android.media.MediaScannerConnection;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

public class MediaUriManager implements MediaScannerConnection.MediaScannerConnectionClient {

    /* renamed from: c  reason: collision with root package name */
    private static final int f126c = 100;

    /* renamed from: a  reason: collision with root package name */
    private Context f127a;

    /* renamed from: b  reason: collision with root package name */
    private MediaScannerConnection f128b;

    /* renamed from: d  reason: collision with root package name */
    private List<Uri> f129d = new ArrayList();

    /* renamed from: e  reason: collision with root package name */
    private String f130e;

    public MediaUriManager(Context context) {
        this.f127a = context;
        this.f128b = new MediaScannerConnection(this.f127a, this);
    }

    public void addPath(String str) {
        this.f130e = str;
        this.f128b.connect();
    }

    public void addUri(Uri uri) {
        if (uri != null) {
            this.f129d.add(uri);
        }
    }

    public void addUris(List<Uri> list) {
        if (list != null && !list.isEmpty()) {
            this.f129d.addAll(list);
        }
    }

    public Uri getCurrentMediaUri() {
        if (this.f129d.isEmpty()) {
            return null;
        }
        return this.f129d.get(0);
    }

    public List<Uri> getUris() {
        return this.f129d;
    }

    public boolean isEmpty() {
        List<Uri> list = this.f129d;
        return list == null || list.isEmpty();
    }

    public void onMediaScannerConnected() {
        try {
            this.f128b.scanFile(this.f130e, null);
        } catch (SQLiteFullException e2) {
            e2.printStackTrace();
        }
    }

    public void onScanCompleted(String str, Uri uri) {
        try {
            if (this.f129d.size() > 100) {
                this.f129d.remove(this.f129d.size() - 1);
            }
            this.f129d.add(0, uri);
        } finally {
            this.f128b.disconnect();
        }
    }

    public void release() {
        MediaScannerConnection mediaScannerConnection = this.f128b;
        if (mediaScannerConnection != null && mediaScannerConnection.isConnected()) {
            this.f128b.disconnect();
        }
    }
}
