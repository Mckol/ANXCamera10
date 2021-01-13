package com.airbnb.lottie.model.content;

import androidx.annotation.Nullable;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.a.d;
import com.airbnb.lottie.a.a.t;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.l;
import com.airbnb.lottie.model.layer.c;

/* compiled from: Repeater */
public class g implements b {
    private final b Nc;
    private final l Oc;
    private final boolean hidden;
    private final String name;
    private final b offset;

    public g(String str, b bVar, b bVar2, l lVar, boolean z) {
        this.name = str;
        this.Nc = bVar;
        this.offset = bVar2;
        this.Oc = lVar;
        this.hidden = z;
    }

    @Override // com.airbnb.lottie.model.content.b
    @Nullable
    public d a(Q q, c cVar) {
        return new t(q, cVar, this);
    }

    public b getCopies() {
        return this.Nc;
    }

    public String getName() {
        return this.name;
    }

    public b getOffset() {
        return this.offset;
    }

    public l getTransform() {
        return this.Oc;
    }

    public boolean isHidden() {
        return this.hidden;
    }
}
