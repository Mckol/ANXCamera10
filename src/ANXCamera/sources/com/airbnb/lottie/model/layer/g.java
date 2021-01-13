package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import com.airbnb.lottie.Q;

/* compiled from: NullLayer */
public class g extends c {
    g(Q q, Layer layer) {
        super(q, layer);
    }

    @Override // com.airbnb.lottie.model.layer.c, com.airbnb.lottie.a.a.f
    public void a(RectF rectF, Matrix matrix, boolean z) {
        super.a(rectF, matrix, z);
        rectF.set(0.0f, 0.0f, 0.0f, 0.0f);
    }

    /* access modifiers changed from: package-private */
    @Override // com.airbnb.lottie.model.layer.c
    public void b(Canvas canvas, Matrix matrix, int i) {
    }
}
