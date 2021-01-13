package com.airbnb.lottie.model.content;

import com.airbnb.lottie.d.b;
import com.airbnb.lottie.d.g;

/* compiled from: GradientColor */
public class c {
    private final float[] ce;
    private final int[] de;

    public c(float[] fArr, int[] iArr) {
        this.ce = fArr;
        this.de = iArr;
    }

    public void a(c cVar, c cVar2, float f) {
        if (cVar.de.length == cVar2.de.length) {
            for (int i = 0; i < cVar.de.length; i++) {
                this.ce[i] = g.lerp(cVar.ce[i], cVar2.ce[i], f);
                this.de[i] = b.a(f, cVar.de[i], cVar2.de[i]);
            }
            return;
        }
        throw new IllegalArgumentException("Cannot interpolate between gradients. Lengths vary (" + cVar.de.length + " vs " + cVar2.de.length + ")");
    }

    public int[] getColors() {
        return this.de;
    }

    public float[] getPositions() {
        return this.ce;
    }

    public int getSize() {
        return this.de.length;
    }
}
