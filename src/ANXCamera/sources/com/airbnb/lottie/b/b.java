package com.airbnb.lottie.b;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import androidx.annotation.Nullable;
import com.airbnb.lottie.AbstractC0026d;
import com.airbnb.lottie.S;
import com.airbnb.lottie.d.d;
import com.airbnb.lottie.d.h;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ImageAssetManager */
public class b {
    private static final Object Md = new Object();
    private String Kd;
    private final Map<String, S> Ld;
    private final Context context;
    @Nullable
    private AbstractC0026d delegate;

    public b(Drawable.Callback callback, String str, AbstractC0026d dVar, Map<String, S> map) {
        this.Kd = str;
        if (!TextUtils.isEmpty(str)) {
            String str2 = this.Kd;
            if (str2.charAt(str2.length() - 1) != '/') {
                this.Kd += '/';
            }
        }
        if (!(callback instanceof View)) {
            d.warning("LottieDrawable must be inside of a view for images to work.");
            this.Ld = new HashMap();
            this.context = null;
            return;
        }
        this.context = ((View) callback).getContext();
        this.Ld = map;
        b(dVar);
    }

    private Bitmap putBitmap(String str, @Nullable Bitmap bitmap) {
        synchronized (Md) {
            this.Ld.get(str).setBitmap(bitmap);
        }
        return bitmap;
    }

    public boolean E(Context context2) {
        return (context2 == null && this.context == null) || this.context.equals(context2);
    }

    @Nullable
    public Bitmap L(String str) {
        S s = this.Ld.get(str);
        if (s == null) {
            return null;
        }
        Bitmap bitmap = s.getBitmap();
        if (bitmap != null) {
            return bitmap;
        }
        AbstractC0026d dVar = this.delegate;
        if (dVar != null) {
            Bitmap a2 = dVar.a(s);
            if (a2 != null) {
                putBitmap(str, a2);
            }
            return a2;
        }
        String fileName = s.getFileName();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        options.inDensity = 160;
        if (!fileName.startsWith("data:") || fileName.indexOf("base64,") <= 0) {
            try {
                if (!TextUtils.isEmpty(this.Kd)) {
                    AssetManager assets = this.context.getAssets();
                    Bitmap a3 = h.a(BitmapFactory.decodeStream(assets.open(this.Kd + fileName), null, options), s.getWidth(), s.getHeight());
                    putBitmap(str, a3);
                    return a3;
                }
                throw new IllegalStateException("You must set an images folder before loading an image. Set it with LottieComposition#setImagesFolder or LottieDrawable#setImagesFolder");
            } catch (IOException e2) {
                d.c("Unable to open asset.", e2);
                return null;
            }
        } else {
            try {
                byte[] decode = Base64.decode(fileName.substring(fileName.indexOf(44) + 1), 0);
                Bitmap decodeByteArray = BitmapFactory.decodeByteArray(decode, 0, decode.length, options);
                putBitmap(str, decodeByteArray);
                return decodeByteArray;
            } catch (IllegalArgumentException e3) {
                d.c("data URL did not have correct base64 format.", e3);
                return null;
            }
        }
    }

    @Nullable
    public Bitmap a(String str, @Nullable Bitmap bitmap) {
        if (bitmap == null) {
            S s = this.Ld.get(str);
            Bitmap bitmap2 = s.getBitmap();
            s.setBitmap(null);
            return bitmap2;
        }
        Bitmap bitmap3 = this.Ld.get(str).getBitmap();
        putBitmap(str, bitmap);
        return bitmap3;
    }

    public void b(@Nullable AbstractC0026d dVar) {
        this.delegate = dVar;
    }
}
