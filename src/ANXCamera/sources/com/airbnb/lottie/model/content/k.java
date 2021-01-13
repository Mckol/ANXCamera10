package com.airbnb.lottie.model.content;

import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.a.d;
import com.airbnb.lottie.a.a.u;
import com.airbnb.lottie.model.a.h;
import com.airbnb.lottie.model.layer.c;

/* compiled from: ShapePath */
public class k implements b {
    private final boolean hidden;
    private final int index;
    private final String name;
    private final h ue;

    public k(String str, int i, h hVar, boolean z) {
        this.name = str;
        this.index = i;
        this.ue = hVar;
        this.hidden = z;
    }

    @Override // com.airbnb.lottie.model.content.b
    public d a(Q q, c cVar) {
        return new u(q, cVar, this);
    }

    public h bc() {
        return this.ue;
    }

    public String getName() {
        return this.name;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public String toString() {
        return "ShapePath{name=" + this.name + ", index=" + this.index + '}';
    }
}
