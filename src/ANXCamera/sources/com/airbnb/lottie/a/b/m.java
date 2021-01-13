package com.airbnb.lottie.a.b;

import android.graphics.Path;
import com.airbnb.lottie.d.g;
import com.airbnb.lottie.e.a;
import com.airbnb.lottie.model.content.h;
import java.util.List;

/* compiled from: ShapeKeyframeAnimation */
public class m extends b<h, Path> {
    private final h md = new h();
    private final Path nd = new Path();

    public m(List<a<h>> list) {
        super(list);
    }

    @Override // com.airbnb.lottie.a.b.b
    public Path a(a<h> aVar, float f) {
        this.md.a(aVar.startValue, aVar.endValue, f);
        g.a(this.md, this.nd);
        return this.nd;
    }
}
