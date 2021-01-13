package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.a.e;
import com.airbnb.lottie.model.content.j;
import com.airbnb.lottie.model.d;
import java.util.Collections;
import java.util.List;

/* compiled from: ShapeLayer */
public class h extends c {
    private final e Pc;

    h(Q q, Layer layer) {
        super(q, layer);
        this.Pc = new e(q, this, new j("__container", layer.Eb(), false));
        this.Pc.a(Collections.emptyList(), Collections.emptyList());
    }

    @Override // com.airbnb.lottie.model.layer.c, com.airbnb.lottie.a.a.f
    public void a(RectF rectF, Matrix matrix, boolean z) {
        super.a(rectF, matrix, z);
        this.Pc.a(rectF, this.Je, z);
    }

    /* access modifiers changed from: package-private */
    @Override // com.airbnb.lottie.model.layer.c
    public void b(@NonNull Canvas canvas, Matrix matrix, int i) {
        this.Pc.a(canvas, matrix, i);
    }

    /* access modifiers changed from: protected */
    @Override // com.airbnb.lottie.model.layer.c
    public void b(d dVar, int i, List<d> list, d dVar2) {
        this.Pc.a(dVar, i, list, dVar2);
    }
}
