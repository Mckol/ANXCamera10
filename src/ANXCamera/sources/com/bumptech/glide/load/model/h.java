package com.bumptech.glide.load.model;

import android.util.Base64;
import com.bumptech.glide.load.model.g.a;
import com.bumptech.glide.load.model.g.c;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: DataUrlLoader */
class h implements a<InputStream> {
    final /* synthetic */ c this$0;

    h(c cVar) {
        this.this$0 = cVar;
    }

    public Class<InputStream> M() {
        return InputStream.class;
    }

    /* renamed from: d */
    public void e(InputStream inputStream) throws IOException {
        inputStream.close();
    }

    public InputStream decode(String str) {
        if (str.startsWith("data:image")) {
            int indexOf = str.indexOf(44);
            if (indexOf == -1) {
                throw new IllegalArgumentException("Missing comma in data URL.");
            } else if (str.substring(0, indexOf).endsWith(";base64")) {
                return new ByteArrayInputStream(Base64.decode(str.substring(indexOf + 1), 0));
            } else {
                throw new IllegalArgumentException("Not a base64 image data URL.");
            }
        } else {
            throw new IllegalArgumentException("Not a valid image data URL.");
        }
    }
}
