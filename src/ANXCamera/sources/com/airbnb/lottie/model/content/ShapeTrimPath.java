package com.airbnb.lottie.model.content;

import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.a.d;
import com.airbnb.lottie.a.a.w;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.layer.c;

public class ShapeTrimPath implements b {
    private final b end;
    private final boolean hidden;
    private final String name;
    private final b offset;
    private final b start;
    private final Type type;

    public enum Type {
        SIMULTANEOUSLY,
        INDIVIDUALLY;

        public static Type A(int i) {
            if (i == 1) {
                return SIMULTANEOUSLY;
            }
            if (i == 2) {
                return INDIVIDUALLY;
            }
            throw new IllegalArgumentException("Unknown trim path type " + i);
        }
    }

    public ShapeTrimPath(String str, Type type2, b bVar, b bVar2, b bVar3, boolean z) {
        this.name = str;
        this.type = type2;
        this.start = bVar;
        this.end = bVar2;
        this.offset = bVar3;
        this.hidden = z;
    }

    @Override // com.airbnb.lottie.model.content.b
    public d a(Q q, c cVar) {
        return new w(cVar, this);
    }

    public b getEnd() {
        return this.end;
    }

    public String getName() {
        return this.name;
    }

    public b getOffset() {
        return this.offset;
    }

    public b getStart() {
        return this.start;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public String toString() {
        return "Trim Path: {start: " + this.start + ", end: " + this.end + ", offset: " + this.offset + "}";
    }
}
