package com.airbnb.lottie.model.content;

import androidx.annotation.Nullable;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.a.j;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.c;
import com.airbnb.lottie.model.a.d;
import com.airbnb.lottie.model.a.f;
import com.airbnb.lottie.model.content.ShapeStroke;
import java.util.List;

/* compiled from: GradientStroke */
public class e implements b {
    private final GradientType ee;
    private final f endPoint;
    private final f fe;
    private final c gd;
    private final boolean hidden;
    private final ShapeStroke.LineCapType ie;
    private final ShapeStroke.LineJoinType je;
    private final float ke;
    private final List<b> le;
    @Nullable
    private final b me;
    private final String name;
    private final d opacity;
    private final b width;

    public e(String str, GradientType gradientType, c cVar, d dVar, f fVar, f fVar2, b bVar, ShapeStroke.LineCapType lineCapType, ShapeStroke.LineJoinType lineJoinType, float f, List<b> list, @Nullable b bVar2, boolean z) {
        this.name = str;
        this.ee = gradientType;
        this.gd = cVar;
        this.opacity = dVar;
        this.fe = fVar;
        this.endPoint = fVar2;
        this.width = bVar;
        this.ie = lineCapType;
        this.je = lineJoinType;
        this.ke = f;
        this.le = list;
        this.me = bVar2;
        this.hidden = z;
    }

    public f Kb() {
        return this.endPoint;
    }

    public c Lb() {
        return this.gd;
    }

    public f Ob() {
        return this.fe;
    }

    public ShapeStroke.LineCapType Pb() {
        return this.ie;
    }

    @Nullable
    public b Qb() {
        return this.me;
    }

    public ShapeStroke.LineJoinType Rb() {
        return this.je;
    }

    public List<b> Sb() {
        return this.le;
    }

    public float Tb() {
        return this.ke;
    }

    @Override // com.airbnb.lottie.model.content.b
    public com.airbnb.lottie.a.a.d a(Q q, com.airbnb.lottie.model.layer.c cVar) {
        return new j(q, cVar, this);
    }

    public GradientType getGradientType() {
        return this.ee;
    }

    public String getName() {
        return this.name;
    }

    public d getOpacity() {
        return this.opacity;
    }

    public b getWidth() {
        return this.width;
    }

    public boolean isHidden() {
        return this.hidden;
    }
}
