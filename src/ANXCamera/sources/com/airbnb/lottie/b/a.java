package com.airbnb.lottie.b;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.Nullable;
import com.airbnb.lottie.C0012c;
import com.airbnb.lottie.d.d;
import com.airbnb.lottie.model.h;
import java.util.HashMap;
import java.util.Map;

/* compiled from: FontAssetManager */
public class a {
    private final h<String> Fd = new h<>();
    private final Map<h<String>, Typeface> Gd = new HashMap();
    private final Map<String, Typeface> Hd = new HashMap();
    private String Jd = ".ttf";
    private final AssetManager assetManager;
    @Nullable
    private C0012c delegate;

    public a(Drawable.Callback callback, @Nullable C0012c cVar) {
        this.delegate = cVar;
        if (!(callback instanceof View)) {
            d.warning("LottieDrawable must be inside of a view for images to work.");
            this.assetManager = null;
            return;
        }
        this.assetManager = ((View) callback).getContext().getAssets();
    }

    private Typeface a(Typeface typeface, String str) {
        boolean contains = str.contains("Italic");
        boolean contains2 = str.contains("Bold");
        int i = (!contains || !contains2) ? contains ? 2 : contains2 ? 1 : 0 : 3;
        return typeface.getStyle() == i ? typeface : Typeface.create(typeface, i);
    }

    private Typeface ca(String str) {
        String C;
        Typeface typeface = this.Hd.get(str);
        if (typeface != null) {
            return typeface;
        }
        Typeface typeface2 = null;
        C0012c cVar = this.delegate;
        if (cVar != null) {
            typeface2 = cVar.B(str);
        }
        C0012c cVar2 = this.delegate;
        if (!(cVar2 == null || typeface2 != null || (C = cVar2.C(str)) == null)) {
            typeface2 = Typeface.createFromAsset(this.assetManager, C);
        }
        if (typeface2 == null) {
            typeface2 = Typeface.createFromAsset(this.assetManager, "fonts/" + str + this.Jd);
        }
        this.Hd.put(str, typeface2);
        return typeface2;
    }

    public void K(String str) {
        this.Jd = str;
    }

    public void b(@Nullable C0012c cVar) {
        this.delegate = cVar;
    }

    public Typeface l(String str, String str2) {
        this.Fd.set(str, str2);
        Typeface typeface = this.Gd.get(this.Fd);
        if (typeface != null) {
            return typeface;
        }
        Typeface a2 = a(ca(str), str2);
        this.Gd.put(this.Fd, a2);
        return a2;
    }
}
