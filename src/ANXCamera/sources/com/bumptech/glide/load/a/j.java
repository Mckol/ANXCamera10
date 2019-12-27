package com.bumptech.glide.load.a;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import java.io.FileNotFoundException;
import java.io.IOException;

/* compiled from: FileDescriptorLocalUriFetcher */
public class j extends m<ParcelFileDescriptor> {
    public j(ContentResolver contentResolver, Uri uri) {
        super(contentResolver, uri);
    }

    @NonNull
    public Class<ParcelFileDescriptor> M() {
        return ParcelFileDescriptor.class;
    }

    /* access modifiers changed from: protected */
    public ParcelFileDescriptor a(Uri uri, ContentResolver contentResolver) throws FileNotFoundException {
        AssetFileDescriptor openAssetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r");
        if (openAssetFileDescriptor != null) {
            return openAssetFileDescriptor.getParcelFileDescriptor();
        }
        throw new FileNotFoundException("FileDescriptor is null for: " + uri);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void e(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        parcelFileDescriptor.close();
    }
}
