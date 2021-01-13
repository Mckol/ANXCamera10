package com.arcsoft.camera.utils;

import android.content.Context;
import android.database.sqlite.SQLiteFullException;
import android.media.MediaScannerConnection;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MediaUriManager */
public class c implements MediaScannerConnection.MediaScannerConnectionClient {

    /* renamed from: c  reason: collision with root package name */
    private static final int f319c = 100;

    /* renamed from: a  reason: collision with root package name */
    private Context f320a;

    /* renamed from: b  reason: collision with root package name */
    private MediaScannerConnection f321b;

    /* renamed from: d  reason: collision with root package name */
    private List<Uri> f322d = new ArrayList();

    /* renamed from: e  reason: collision with root package name */
    private String f323e;

    public c(Context context) {
        this.f320a = context;
        this.f321b = new MediaScannerConnection(this.f320a, this);
    }

    public void addPath(String str) {
        this.f323e = str;
        this.f321b.connect();
    }

    public void addUri(Uri uri) {
        if (uri != null) {
            this.f322d.add(uri);
        }
    }

    public void addUris(List<Uri> list) {
        if (list != null && !list.isEmpty()) {
            this.f322d.addAll(list);
        }
    }

    public Uri getCurrentMediaUri() {
        if (this.f322d.isEmpty()) {
            return null;
        }
        return this.f322d.get(0);
    }

    public List<Uri> getUris() {
        return this.f322d;
    }

    public boolean isEmpty() {
        List<Uri> list = this.f322d;
        return list == null || list.isEmpty();
    }

    public void onMediaScannerConnected() {
        try {
            this.f321b.scanFile(this.f323e, null);
        } catch (SQLiteFullException unused) {
        }
    }

    public void onScanCompleted(String str, Uri uri) {
        try {
            if (this.f322d.size() > 100) {
                this.f322d.remove(this.f322d.size() - 1);
            }
            this.f322d.add(0, uri);
        } finally {
            this.f321b.disconnect();
        }
    }

    public void release() {
        MediaScannerConnection mediaScannerConnection = this.f321b;
        if (mediaScannerConnection != null && mediaScannerConnection.isConnected()) {
            this.f321b.disconnect();
        }
    }
}
