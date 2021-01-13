package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.a.d;
import com.airbnb.lottie.a.a.g;
import com.airbnb.lottie.model.a.f;
import com.airbnb.lottie.model.a.m;
import com.airbnb.lottie.model.layer.c;

/* compiled from: CircleShape */
public class a implements b {
    private final boolean be;
    private final boolean hidden;
    private final String name;
    private final m<PointF, PointF> position;
    private final f size;

    public a(String str, m<PointF, PointF> mVar, f fVar, boolean z, boolean z2) {
        this.name = str;
        this.position = mVar;
        this.size = fVar;
        this.be = z;
        this.hidden = z2;
    }

    @Override // com.airbnb.lottie.model.content.b
    public d a(Q q, c cVar) {
        return new g(q, cVar, this);
    }

    public String getName() {
        return this.name;
    }

    public m<PointF, PointF> getPosition() {
        return this.position;
    }

    public f getSize() {
        return this.size;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public boolean isReversed() {
        return this.be;
    }
}
