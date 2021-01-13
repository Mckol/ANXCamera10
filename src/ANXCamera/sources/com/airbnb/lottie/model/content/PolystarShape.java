package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.a.d;
import com.airbnb.lottie.a.a.r;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.m;
import com.airbnb.lottie.model.layer.c;

public class PolystarShape implements b {
    private final boolean hidden;
    private final b innerRadius;
    private final String name;
    private final b outerRadius;
    private final b points;
    private final m<PointF, PointF> position;
    private final b qe;
    private final b re;
    private final b rotation;
    private final Type type;

    public enum Type {
        STAR(1),
        POLYGON(2);
        
        private final int value;

        private Type(int i) {
            this.value = i;
        }

        public static Type B(int i) {
            Type[] values = values();
            for (Type type : values) {
                if (type.value == i) {
                    return type;
                }
            }
            return null;
        }
    }

    public PolystarShape(String str, Type type2, b bVar, m<PointF, PointF> mVar, b bVar2, b bVar3, b bVar4, b bVar5, b bVar6, boolean z) {
        this.name = str;
        this.type = type2;
        this.points = bVar;
        this.position = mVar;
        this.rotation = bVar2;
        this.innerRadius = bVar3;
        this.outerRadius = bVar4;
        this.qe = bVar5;
        this.re = bVar6;
        this.hidden = z;
    }

    public b Xb() {
        return this.qe;
    }

    public b Yb() {
        return this.outerRadius;
    }

    public b Zb() {
        return this.re;
    }

    @Override // com.airbnb.lottie.model.content.b
    public d a(Q q, c cVar) {
        return new r(q, cVar, this);
    }

    public b getInnerRadius() {
        return this.innerRadius;
    }

    public String getName() {
        return this.name;
    }

    public b getPoints() {
        return this.points;
    }

    public m<PointF, PointF> getPosition() {
        return this.position;
    }

    public b getRotation() {
        return this.rotation;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isHidden() {
        return this.hidden;
    }
}
