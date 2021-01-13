package com.airbnb.lottie.model.content;

import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.a.d;
import com.airbnb.lottie.a.a.e;
import com.airbnb.lottie.model.layer.c;
import java.util.Arrays;
import java.util.List;

/* compiled from: ShapeGroup */
public class j implements b {
    private final boolean hidden;
    private final List<b> items;
    private final String name;

    public j(String str, List<b> list, boolean z) {
        this.name = str;
        this.items = list;
        this.hidden = z;
    }

    @Override // com.airbnb.lottie.model.content.b
    public d a(Q q, c cVar) {
        return new e(q, cVar, this);
    }

    public List<b> getItems() {
        return this.items;
    }

    public String getName() {
        return this.name;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public String toString() {
        return "ShapeGroup{name='" + this.name + "' Shapes: " + Arrays.toString(this.items.toArray()) + '}';
    }
}
