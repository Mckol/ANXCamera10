package com.airbnb.lottie.model.a;

import android.graphics.PointF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.a.d;
import com.airbnb.lottie.a.a.o;
import com.airbnb.lottie.a.b.p;
import com.airbnb.lottie.model.content.b;
import com.airbnb.lottie.model.layer.c;

/* compiled from: AnimatableTransform */
public class l implements o, b {
    @Nullable
    private final e Ad;
    @Nullable
    private final b Bd;
    @Nullable
    private final b Cd;
    @Nullable
    private final b Dd;
    @Nullable
    private final b Ed;
    @Nullable
    private final d opacity;
    @Nullable
    private final m<PointF, PointF> position;
    @Nullable
    private final b rotation;
    @Nullable
    private final g scale;

    public l() {
        this(null, null, null, null, null, null, null, null, null);
    }

    public l(@Nullable e eVar, @Nullable m<PointF, PointF> mVar, @Nullable g gVar, @Nullable b bVar, @Nullable d dVar, @Nullable b bVar2, @Nullable b bVar3, @Nullable b bVar4, @Nullable b bVar5) {
        this.Ad = eVar;
        this.position = mVar;
        this.scale = gVar;
        this.rotation = bVar;
        this.opacity = dVar;
        this.Dd = bVar2;
        this.Ed = bVar3;
        this.Bd = bVar4;
        this.Cd = bVar5;
    }

    public p F() {
        return new p(this);
    }

    @Nullable
    public e Hb() {
        return this.Ad;
    }

    @Nullable
    public b Ib() {
        return this.Bd;
    }

    @Nullable
    public b Jb() {
        return this.Cd;
    }

    @Override // com.airbnb.lottie.model.content.b
    @Nullable
    public d a(Q q, c cVar) {
        return null;
    }

    @Nullable
    public d getOpacity() {
        return this.opacity;
    }

    @Nullable
    public m<PointF, PointF> getPosition() {
        return this.position;
    }

    @Nullable
    public b getRotation() {
        return this.rotation;
    }

    @Nullable
    public g getScale() {
        return this.scale;
    }

    @Nullable
    public b yb() {
        return this.Ed;
    }

    @Nullable
    public b zb() {
        return this.Dd;
    }
}
