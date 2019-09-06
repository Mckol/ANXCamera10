package com.bumptech.glide.load.model;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.a.j;
import com.bumptech.glide.load.a.o;
import com.bumptech.glide.load.g;
import com.ss.android.ugc.effectmanager.effect.model.ComposerHelper;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: UriLoader */
public class C<Data> implements t<Uri, Data> {
    private static final Set<String> SCHEMES = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{ComposerHelper.COMPOSER_PATH, "android.resource", ComposerHelper.COMPOSER_CONTENT})));
    private final c<Data> factory;

    /* compiled from: UriLoader */
    public static final class a implements u<Uri, AssetFileDescriptor>, c<AssetFileDescriptor> {
        private final ContentResolver ae;

        public a(ContentResolver contentResolver) {
            this.ae = contentResolver;
        }

        public void D() {
        }

        public com.bumptech.glide.load.a.d<AssetFileDescriptor> a(Uri uri) {
            return new com.bumptech.glide.load.a.a(this.ae, uri);
        }

        public t<Uri, AssetFileDescriptor> a(x xVar) {
            return new C(this);
        }
    }

    /* compiled from: UriLoader */
    public static class b implements u<Uri, ParcelFileDescriptor>, c<ParcelFileDescriptor> {
        private final ContentResolver ae;

        public b(ContentResolver contentResolver) {
            this.ae = contentResolver;
        }

        public void D() {
        }

        public com.bumptech.glide.load.a.d<ParcelFileDescriptor> a(Uri uri) {
            return new j(this.ae, uri);
        }

        @NonNull
        public t<Uri, ParcelFileDescriptor> a(x xVar) {
            return new C(this);
        }
    }

    /* compiled from: UriLoader */
    public interface c<Data> {
        com.bumptech.glide.load.a.d<Data> a(Uri uri);
    }

    /* compiled from: UriLoader */
    public static class d implements u<Uri, InputStream>, c<InputStream> {
        private final ContentResolver ae;

        public d(ContentResolver contentResolver) {
            this.ae = contentResolver;
        }

        public void D() {
        }

        public com.bumptech.glide.load.a.d<InputStream> a(Uri uri) {
            return new o(this.ae, uri);
        }

        @NonNull
        public t<Uri, InputStream> a(x xVar) {
            return new C(this);
        }
    }

    public C(c<Data> cVar) {
        this.factory = cVar;
    }

    public com.bumptech.glide.load.model.t.a<Data> a(@NonNull Uri uri, int i, int i2, @NonNull g gVar) {
        return new com.bumptech.glide.load.model.t.a<>(new com.bumptech.glide.e.d(uri), this.factory.a(uri));
    }

    /* renamed from: i */
    public boolean c(@NonNull Uri uri) {
        return SCHEMES.contains(uri.getScheme());
    }
}
