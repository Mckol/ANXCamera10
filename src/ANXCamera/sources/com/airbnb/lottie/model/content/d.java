package com.airbnb.lottie.model.content;

import android.graphics.Path;
import androidx.annotation.Nullable;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.a.i;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.c;
import com.airbnb.lottie.model.a.f;

/* compiled from: GradientFill */
public class d implements b {
    private final GradientType ee;
    private final f endPoint;
    private final f fe;
    private final Path.FillType fillType;
    private final c gd;
    @Nullable
    private final b ge;
    @Nullable
    private final b he;
    private final boolean hidden;
    private final String name;
    private final com.airbnb.lottie.model.a.d opacity;

    public d(String str, GradientType gradientType, Path.FillType fillType2, c cVar, com.airbnb.lottie.model.a.d dVar, f fVar, f fVar2, b bVar, b bVar2, boolean z) {
        this.ee = gradientType;
        this.fillType = fillType2;
        this.gd = cVar;
        this.opacity = dVar;
        this.fe = fVar;
        this.endPoint = fVar2;
        this.name = str;
        this.ge = bVar;
        this.he = bVar2;
        this.hidden = z;
    }

    public f Kb() {
        return this.endPoint;
    }

    public c Lb() {
        return this.gd;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public b Mb() {
        return this.he;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public b Nb() {
        return this.ge;
    }

    public f Ob() {
        return this.fe;
    }

    @Override // com.airbnb.lottie.model.content.b
    public com.airbnb.lottie.a.a.d a(Q q, com.airbnb.lottie.model.layer.c cVar) {
        return new i(q, cVar, this);
    }

    public Path.FillType getFillType() {
        return this.fillType;
    }

    public GradientType getGradientType() {
        return this.ee;
    }

    public String getName() {
        return this.name;
    }

    public com.airbnb.lottie.model.a.d getOpacity() {
        return this.opacity;
    }

    public boolean isHidden() {
        return this.hidden;
    }
}
