package com.airbnb.lottie.model.content;

import com.airbnb.lottie.model.a.d;
import com.airbnb.lottie.model.a.h;

public class Mask {
    private final MaskMode ne;
    private final h oe;
    private final d opacity;
    private final boolean pe;

    public enum MaskMode {
        MASK_MODE_ADD,
        MASK_MODE_SUBTRACT,
        MASK_MODE_INTERSECT,
        MASK_MODE_NONE
    }

    public Mask(MaskMode maskMode, h hVar, d dVar, boolean z) {
        this.ne = maskMode;
        this.oe = hVar;
        this.opacity = dVar;
        this.pe = z;
    }

    public MaskMode Ub() {
        return this.ne;
    }

    public h Vb() {
        return this.oe;
    }

    public boolean Wb() {
        return this.pe;
    }

    public d getOpacity() {
        return this.opacity;
    }
}
