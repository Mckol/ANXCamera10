package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.a.d;
import com.airbnb.lottie.a.a.s;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.m;
import com.airbnb.lottie.model.layer.c;

/* compiled from: RectangleShape */
public class f implements b {
    private final b cornerRadius;
    private final boolean hidden;
    private final String name;
    private final m<PointF, PointF> position;
    private final com.airbnb.lottie.model.a.f size;

    public f(String str, m<PointF, PointF> mVar, com.airbnb.lottie.model.a.f fVar, b bVar, boolean z) {
        this.name = str;
        this.position = mVar;
        this.size = fVar;
        this.cornerRadius = bVar;
        this.hidden = z;
    }

    @Override // com.airbnb.lottie.model.content.b
    public d a(Q q, c cVar) {
        return new s(q, cVar, this);
    }

    public b getCornerRadius() {
        return this.cornerRadius;
    }

    public String getName() {
        return this.name;
    }

    public m<PointF, PointF> getPosition() {
        return this.position;
    }

    public com.airbnb.lottie.model.a.f getSize() {
        return this.size;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public String toString() {
        return "RectangleShape{position=" + this.position + ", size=" + this.size + '}';
    }
}
