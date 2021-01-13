package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import androidx.annotation.CallSuper;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.airbnb.lottie.C0027e;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.a;
import com.airbnb.lottie.a.a.f;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.a.b.d;
import com.airbnb.lottie.a.b.h;
import com.airbnb.lottie.a.b.p;
import com.airbnb.lottie.e.j;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.model.e;
import com.airbnb.lottie.model.layer.Layer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: BaseLayer */
public abstract class c implements f, b.a, e {
    private static final int CLIP_SAVE_FLAG = 2;
    private static final int CLIP_TO_LAYER_SAVE_FLAG = 16;
    private static final int MATRIX_SAVE_FLAG = 1;
    private static final int Pe = 19;
    private final Paint Ae = new a(1);
    private final Paint Be = new a(1, PorterDuff.Mode.DST_IN);
    private final Paint Ce = new a(1, PorterDuff.Mode.DST_OUT);
    private final Paint De = new a(1);
    private final Paint Ee = new a(PorterDuff.Mode.CLEAR);
    private final RectF Fe = new RectF();
    private final RectF Ge = new RectF();
    private final RectF He = new RectF();
    private final String Ie;
    final Matrix Je = new Matrix();
    final Layer Ke;
    final Q La;
    @Nullable
    private c Le;
    @Nullable
    private c Me;
    private List<c> Ne;
    final p Oc;
    private final List<b<?, ?>> Oe = new ArrayList();
    @Nullable
    private h mask;
    private final Matrix matrix = new Matrix();
    private final Path path = new Path();
    private final RectF rect = new RectF();
    private boolean visible = true;

    c(Q q, Layer layer) {
        this.La = q;
        this.Ke = layer;
        this.Ie = layer.getName() + "#draw";
        if (layer.hc() == Layer.MatteType.INVERT) {
            this.De.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        } else {
            this.De.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        }
        this.Oc = layer.getTransform().F();
        this.Oc.a((b.a) this);
        if (layer.wb() != null && !layer.wb().isEmpty()) {
            this.mask = new h(layer.wb());
            for (b<com.airbnb.lottie.model.content.h, Path> bVar : this.mask.vb()) {
                bVar.b(this);
            }
            for (b<Integer, Integer> bVar2 : this.mask.xb()) {
                a(bVar2);
                bVar2.b(this);
            }
        }
        Yo();
    }

    private boolean Wo() {
        if (this.mask.vb().isEmpty()) {
            return false;
        }
        for (int i = 0; i < this.mask.wb().size(); i++) {
            if (this.mask.wb().get(i).Ub() != Mask.MaskMode.MASK_MODE_NONE) {
                return false;
            }
        }
        return true;
    }

    private void Xo() {
        if (this.Ne == null) {
            if (this.Me == null) {
                this.Ne = Collections.emptyList();
                return;
            }
            this.Ne = new ArrayList();
            for (c cVar = this.Me; cVar != null; cVar = cVar.Me) {
                this.Ne.add(cVar);
            }
        }
    }

    private void Yo() {
        boolean z = true;
        if (!this.Ke.fc().isEmpty()) {
            d dVar = new d(this.Ke.fc());
            dVar.ub();
            dVar.b(new a(this, dVar));
            if (((Float) dVar.getValue()).floatValue() != 1.0f) {
                z = false;
            }
            setVisible(z);
            a(dVar);
            return;
        }
        setVisible(true);
    }

    @Nullable
    static c a(Layer layer, Q q, C0035m mVar) {
        switch (b.ye[layer.getLayerType().ordinal()]) {
            case 1:
                return new h(q, layer);
            case 2:
                return new e(q, layer, mVar.H(layer.kc()), mVar);
            case 3:
                return new i(q, layer);
            case 4:
                return new f(q, layer);
            case 5:
                return new g(q, layer);
            case 6:
                return new m(q, layer);
            default:
                com.airbnb.lottie.d.d.warning("Unknown layer type " + layer.getLayerType());
                return null;
        }
    }

    private void a(Canvas canvas, Matrix matrix2) {
        C0027e.beginSection("Layer#saveLayer");
        com.airbnb.lottie.d.h.a(canvas, this.rect, this.Be, 19);
        if (Build.VERSION.SDK_INT < 28) {
            canvas.drawColor(0);
        }
        C0027e.D("Layer#saveLayer");
        for (int i = 0; i < this.mask.wb().size(); i++) {
            Mask mask2 = this.mask.wb().get(i);
            b<com.airbnb.lottie.model.content.h, Path> bVar = this.mask.vb().get(i);
            b<Integer, Integer> bVar2 = this.mask.xb().get(i);
            int i2 = b.ze[mask2.Ub().ordinal()];
            if (i2 != 1) {
                if (i2 == 2) {
                    if (i == 0) {
                        this.Ae.setColor(ViewCompat.MEASURED_STATE_MASK);
                        this.Ae.setAlpha(255);
                        canvas.drawRect(this.rect, this.Ae);
                    }
                    if (mask2.Wb()) {
                        e(canvas, matrix2, mask2, bVar, bVar2);
                    } else {
                        f(canvas, matrix2, mask2, bVar, bVar2);
                    }
                } else if (i2 != 3) {
                    if (i2 == 4) {
                        if (mask2.Wb()) {
                            c(canvas, matrix2, mask2, bVar, bVar2);
                        } else {
                            a(canvas, matrix2, mask2, bVar, bVar2);
                        }
                    }
                } else if (mask2.Wb()) {
                    d(canvas, matrix2, mask2, bVar, bVar2);
                } else {
                    b(canvas, matrix2, mask2, bVar, bVar2);
                }
            } else if (Wo()) {
                this.Ae.setAlpha(255);
                canvas.drawRect(this.rect, this.Ae);
            }
        }
        C0027e.beginSection("Layer#restoreLayer");
        canvas.restore();
        C0027e.D("Layer#restoreLayer");
    }

    private void a(Canvas canvas, Matrix matrix2, Mask mask2, b<com.airbnb.lottie.model.content.h, Path> bVar, b<Integer, Integer> bVar2) {
        this.path.set(bVar.getValue());
        this.path.transform(matrix2);
        this.Ae.setAlpha((int) (((float) bVar2.getValue().intValue()) * 2.55f));
        canvas.drawPath(this.path, this.Ae);
    }

    private void a(RectF rectF, Matrix matrix2) {
        this.Fe.set(0.0f, 0.0f, 0.0f, 0.0f);
        if (dc()) {
            int size = this.mask.wb().size();
            for (int i = 0; i < size; i++) {
                Mask mask2 = this.mask.wb().get(i);
                this.path.set(this.mask.vb().get(i).getValue());
                this.path.transform(matrix2);
                int i2 = b.ze[mask2.Ub().ordinal()];
                if (i2 != 1 && i2 != 2) {
                    if ((i2 != 3 && i2 != 4) || !mask2.Wb()) {
                        this.path.computeBounds(this.He, false);
                        if (i == 0) {
                            this.Fe.set(this.He);
                        } else {
                            RectF rectF2 = this.Fe;
                            rectF2.set(Math.min(rectF2.left, this.He.left), Math.min(this.Fe.top, this.He.top), Math.max(this.Fe.right, this.He.right), Math.max(this.Fe.bottom, this.He.bottom));
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            if (!rectF.intersect(this.Fe)) {
                rectF.set(0.0f, 0.0f, 0.0f, 0.0f);
            }
        }
    }

    private void b(Canvas canvas, Matrix matrix2, Mask mask2, b<com.airbnb.lottie.model.content.h, Path> bVar, b<Integer, Integer> bVar2) {
        com.airbnb.lottie.d.h.a(canvas, this.rect, this.Be);
        this.path.set(bVar.getValue());
        this.path.transform(matrix2);
        this.Ae.setAlpha((int) (((float) bVar2.getValue().intValue()) * 2.55f));
        canvas.drawPath(this.path, this.Ae);
        canvas.restore();
    }

    private void b(RectF rectF, Matrix matrix2) {
        if (ec() && this.Ke.hc() != Layer.MatteType.INVERT) {
            this.Ge.set(0.0f, 0.0f, 0.0f, 0.0f);
            this.Le.a(this.Ge, matrix2, true);
            if (!rectF.intersect(this.Ge)) {
                rectF.set(0.0f, 0.0f, 0.0f, 0.0f);
            }
        }
    }

    private void c(Canvas canvas, Matrix matrix2, Mask mask2, b<com.airbnb.lottie.model.content.h, Path> bVar, b<Integer, Integer> bVar2) {
        com.airbnb.lottie.d.h.a(canvas, this.rect, this.Ae);
        canvas.drawRect(this.rect, this.Ae);
        this.path.set(bVar.getValue());
        this.path.transform(matrix2);
        this.Ae.setAlpha((int) (((float) bVar2.getValue().intValue()) * 2.55f));
        canvas.drawPath(this.path, this.Ce);
        canvas.restore();
    }

    private void d(Canvas canvas, Matrix matrix2, Mask mask2, b<com.airbnb.lottie.model.content.h, Path> bVar, b<Integer, Integer> bVar2) {
        com.airbnb.lottie.d.h.a(canvas, this.rect, this.Be);
        canvas.drawRect(this.rect, this.Ae);
        this.Ce.setAlpha((int) (((float) bVar2.getValue().intValue()) * 2.55f));
        this.path.set(bVar.getValue());
        this.path.transform(matrix2);
        canvas.drawPath(this.path, this.Ce);
        canvas.restore();
    }

    private void e(Canvas canvas) {
        C0027e.beginSection("Layer#clearLayer");
        RectF rectF = this.rect;
        canvas.drawRect(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f, this.Ee);
        C0027e.D("Layer#clearLayer");
    }

    private void e(Canvas canvas, Matrix matrix2, Mask mask2, b<com.airbnb.lottie.model.content.h, Path> bVar, b<Integer, Integer> bVar2) {
        com.airbnb.lottie.d.h.a(canvas, this.rect, this.Ce);
        canvas.drawRect(this.rect, this.Ae);
        this.Ce.setAlpha((int) (((float) bVar2.getValue().intValue()) * 2.55f));
        this.path.set(bVar.getValue());
        this.path.transform(matrix2);
        canvas.drawPath(this.path, this.Ce);
        canvas.restore();
    }

    private void f(Canvas canvas, Matrix matrix2, Mask mask2, b<com.airbnb.lottie.model.content.h, Path> bVar, b<Integer, Integer> bVar2) {
        this.path.set(bVar.getValue());
        this.path.transform(matrix2);
        canvas.drawPath(this.path, this.Ce);
    }

    private void invalidateSelf() {
        this.La.invalidateSelf();
    }

    private void m(float f) {
        this.La.aa().ca().c(this.Ke.getName(), f);
    }

    /* access modifiers changed from: private */
    public void setVisible(boolean z) {
        if (z != this.visible) {
            this.visible = z;
            invalidateSelf();
        }
    }

    @Override // com.airbnb.lottie.a.b.b.a
    public void G() {
        invalidateSelf();
    }

    @Override // com.airbnb.lottie.a.a.f
    public void a(Canvas canvas, Matrix matrix2, int i) {
        C0027e.beginSection(this.Ie);
        if (!this.visible || this.Ke.isHidden()) {
            C0027e.D(this.Ie);
            return;
        }
        Xo();
        C0027e.beginSection("Layer#parentMatrix");
        this.matrix.reset();
        this.matrix.set(matrix2);
        for (int size = this.Ne.size() - 1; size >= 0; size--) {
            this.matrix.preConcat(this.Ne.get(size).Oc.getMatrix());
        }
        C0027e.D("Layer#parentMatrix");
        int intValue = (int) ((((((float) i) / 255.0f) * ((float) (this.Oc.getOpacity() == null ? 100 : this.Oc.getOpacity().getValue().intValue()))) / 100.0f) * 255.0f);
        if (ec() || dc()) {
            C0027e.beginSection("Layer#computeBounds");
            a(this.rect, this.matrix, false);
            b(this.rect, matrix2);
            this.matrix.preConcat(this.Oc.getMatrix());
            a(this.rect, this.matrix);
            if (!this.rect.intersect(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight())) {
                this.rect.set(0.0f, 0.0f, 0.0f, 0.0f);
            }
            C0027e.D("Layer#computeBounds");
            if (!this.rect.isEmpty()) {
                C0027e.beginSection("Layer#saveLayer");
                this.Ae.setAlpha(255);
                com.airbnb.lottie.d.h.a(canvas, this.rect, this.Ae);
                C0027e.D("Layer#saveLayer");
                e(canvas);
                C0027e.beginSection("Layer#drawLayer");
                b(canvas, this.matrix, intValue);
                C0027e.D("Layer#drawLayer");
                if (dc()) {
                    a(canvas, this.matrix);
                }
                if (ec()) {
                    C0027e.beginSection("Layer#drawMatte");
                    C0027e.beginSection("Layer#saveLayer");
                    com.airbnb.lottie.d.h.a(canvas, this.rect, this.De, 19);
                    C0027e.D("Layer#saveLayer");
                    e(canvas);
                    this.Le.a(canvas, matrix2, intValue);
                    C0027e.beginSection("Layer#restoreLayer");
                    canvas.restore();
                    C0027e.D("Layer#restoreLayer");
                    C0027e.D("Layer#drawMatte");
                }
                C0027e.beginSection("Layer#restoreLayer");
                canvas.restore();
                C0027e.D("Layer#restoreLayer");
            }
            m(C0027e.D(this.Ie));
            return;
        }
        this.matrix.preConcat(this.Oc.getMatrix());
        C0027e.beginSection("Layer#drawLayer");
        b(canvas, this.matrix, intValue);
        C0027e.D("Layer#drawLayer");
        m(C0027e.D(this.Ie));
    }

    @Override // com.airbnb.lottie.a.a.f
    @CallSuper
    public void a(RectF rectF, Matrix matrix2, boolean z) {
        this.rect.set(0.0f, 0.0f, 0.0f, 0.0f);
        Xo();
        this.Je.set(matrix2);
        if (z) {
            List<c> list = this.Ne;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    this.Je.preConcat(this.Ne.get(size).Oc.getMatrix());
                }
            } else {
                c cVar = this.Me;
                if (cVar != null) {
                    this.Je.preConcat(cVar.Oc.getMatrix());
                }
            }
        }
        this.Je.preConcat(this.Oc.getMatrix());
    }

    public void a(@Nullable b<?, ?> bVar) {
        if (bVar != null) {
            this.Oe.add(bVar);
        }
    }

    @Override // com.airbnb.lottie.model.e
    public void a(com.airbnb.lottie.model.d dVar, int i, List<com.airbnb.lottie.model.d> list, com.airbnb.lottie.model.d dVar2) {
        if (dVar.f(getName(), i)) {
            if (!"__container".equals(getName())) {
                dVar2 = dVar2.M(getName());
                if (dVar.d(getName(), i)) {
                    list.add(dVar2.a(this));
                }
            }
            if (dVar.g(getName(), i)) {
                b(dVar, i + dVar.e(getName(), i), list, dVar2);
            }
        }
    }

    @Override // com.airbnb.lottie.model.e
    @CallSuper
    public <T> void a(T t, @Nullable j<T> jVar) {
        this.Oc.b(t, jVar);
    }

    @Override // com.airbnb.lottie.a.a.d
    public void a(List<com.airbnb.lottie.a.a.d> list, List<com.airbnb.lottie.a.a.d> list2) {
    }

    /* access modifiers changed from: package-private */
    public abstract void b(Canvas canvas, Matrix matrix2, int i);

    public void b(b<?, ?> bVar) {
        this.Oe.remove(bVar);
    }

    /* access modifiers changed from: package-private */
    public void b(com.airbnb.lottie.model.d dVar, int i, List<com.airbnb.lottie.model.d> list, com.airbnb.lottie.model.d dVar2) {
    }

    /* access modifiers changed from: package-private */
    public void b(@Nullable c cVar) {
        this.Le = cVar;
    }

    /* access modifiers changed from: package-private */
    public void c(@Nullable c cVar) {
        this.Me = cVar;
    }

    /* access modifiers changed from: package-private */
    public Layer cc() {
        return this.Ke;
    }

    /* access modifiers changed from: package-private */
    public boolean dc() {
        h hVar = this.mask;
        return hVar != null && !hVar.vb().isEmpty();
    }

    /* access modifiers changed from: package-private */
    public boolean ec() {
        return this.Le != null;
    }

    @Override // com.airbnb.lottie.a.a.d
    public String getName() {
        return this.Ke.getName();
    }

    /* access modifiers changed from: package-private */
    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        this.Oc.setProgress(f);
        if (this.mask != null) {
            for (int i = 0; i < this.mask.vb().size(); i++) {
                this.mask.vb().get(i).setProgress(f);
            }
        }
        if (this.Ke.qc() != 0.0f) {
            f /= this.Ke.qc();
        }
        c cVar = this.Le;
        if (cVar != null) {
            this.Le.setProgress(cVar.Ke.qc() * f);
        }
        for (int i2 = 0; i2 < this.Oe.size(); i2++) {
            this.Oe.get(i2).setProgress(f);
        }
    }
}
