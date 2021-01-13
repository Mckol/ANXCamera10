package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.W;
import com.airbnb.lottie.a.a.e;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.a.b.o;
import com.airbnb.lottie.a.b.q;
import com.airbnb.lottie.d.h;
import com.airbnb.lottie.ea;
import com.airbnb.lottie.model.DocumentData;
import com.airbnb.lottie.model.a.a;
import com.airbnb.lottie.model.a.k;
import com.airbnb.lottie.model.c;
import com.airbnb.lottie.model.content.j;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: TextLayer */
public class m extends c {
    private final Q La;
    private final RectF We = new RectF();
    private final Paint Xe = new j(this, 1);
    private final Paint Ye = new k(this, 1);
    private final Map<c, List<e>> Ze = new HashMap();
    private final LongSparseArray<String> _e = new LongSparseArray<>();
    private final o bf;
    @Nullable
    private b<Integer, Integer> cf;
    @Nullable
    private b<Float, Float> df;
    @Nullable
    private b<Float, Float> ef;
    @Nullable
    private b<Float, Float> ff;
    private final C0035m ha;
    @Nullable
    private b<Integer, Integer> kc;
    private final Matrix matrix = new Matrix();
    private final StringBuilder stringBuilder = new StringBuilder(2);

    m(Q q, Layer layer) {
        super(q, layer);
        com.airbnb.lottie.model.a.b bVar;
        com.airbnb.lottie.model.a.b bVar2;
        a aVar;
        a aVar2;
        this.La = q;
        this.ha = layer.aa();
        this.bf = layer.getText().F();
        this.bf.b(this);
        a(this.bf);
        k oc = layer.oc();
        if (!(oc == null || (aVar2 = oc.color) == null)) {
            this.kc = aVar2.F();
            this.kc.b(this);
            a(this.kc);
        }
        if (!(oc == null || (aVar = oc.stroke) == null)) {
            this.cf = aVar.F();
            this.cf.b(this);
            a(this.cf);
        }
        if (!(oc == null || (bVar2 = oc.strokeWidth) == null)) {
            this.df = bVar2.F();
            this.df.b(this);
            a(this.df);
        }
        if (oc != null && (bVar = oc.Sd) != null) {
            this.ef = bVar.F();
            this.ef.b(this);
            a(this.ef);
        }
    }

    private boolean D(int i) {
        return Character.getType(i) == 16 || Character.getType(i) == 27 || Character.getType(i) == 6 || Character.getType(i) == 28 || Character.getType(i) == 19;
    }

    private float a(String str, com.airbnb.lottie.model.b bVar, float f, float f2) {
        float f3 = 0.0f;
        for (int i = 0; i < str.length(); i++) {
            c cVar = this.ha.getCharacters().get(c.a(str.charAt(i), bVar.getFamily(), bVar.getStyle()));
            if (cVar != null) {
                f3 = (float) (((double) f3) + (cVar.getWidth() * ((double) f) * ((double) h.uc()) * ((double) f2)));
            }
        }
        return f3;
    }

    private List<e> a(c cVar) {
        if (this.Ze.containsKey(cVar)) {
            return this.Ze.get(cVar);
        }
        List<j> Eb = cVar.Eb();
        int size = Eb.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(new e(this.La, this, Eb.get(i)));
        }
        this.Ze.put(cVar, arrayList);
        return arrayList;
    }

    private void a(Path path, Paint paint, Canvas canvas) {
        if (paint.getColor() != 0) {
            if (paint.getStyle() != Paint.Style.STROKE || paint.getStrokeWidth() != 0.0f) {
                canvas.drawPath(path, paint);
            }
        }
    }

    private void a(DocumentData.Justification justification, Canvas canvas, float f) {
        int i = l.rf[justification.ordinal()];
        if (i == 1) {
            return;
        }
        if (i == 2) {
            canvas.translate(-f, 0.0f);
        } else if (i == 3) {
            canvas.translate((-f) / 2.0f, 0.0f);
        }
    }

    private void a(DocumentData documentData, Matrix matrix2, com.airbnb.lottie.model.b bVar, Canvas canvas) {
        b<Float, Float> bVar2 = this.ff;
        float floatValue = (bVar2 == null ? documentData.size : bVar2.getValue().floatValue()) / 100.0f;
        float a2 = h.a(matrix2);
        String str = documentData.text;
        float uc = documentData.lineHeight * h.uc();
        List<String> ea = ea(str);
        int size = ea.size();
        for (int i = 0; i < size; i++) {
            String str2 = ea.get(i);
            float a3 = a(str2, bVar, floatValue, a2);
            canvas.save();
            a(documentData.Rd, canvas, a3);
            canvas.translate(0.0f, (((float) i) * uc) - ((((float) (size - 1)) * uc) / 2.0f));
            a(str2, documentData, matrix2, bVar, canvas, a2, floatValue);
            canvas.restore();
        }
    }

    private void a(DocumentData documentData, com.airbnb.lottie.model.b bVar, Matrix matrix2, Canvas canvas) {
        float a2 = h.a(matrix2);
        Typeface l = this.La.l(bVar.getFamily(), bVar.getStyle());
        if (l != null) {
            String str = documentData.text;
            ea da = this.La.da();
            if (da != null) {
                str = da.I(str);
            }
            this.Xe.setTypeface(l);
            b<Float, Float> bVar2 = this.ff;
            this.Xe.setTextSize((bVar2 == null ? documentData.size : bVar2.getValue().floatValue()) * h.uc());
            this.Ye.setTypeface(this.Xe.getTypeface());
            this.Ye.setTextSize(this.Xe.getTextSize());
            float uc = documentData.lineHeight * h.uc();
            List<String> ea = ea(str);
            int size = ea.size();
            for (int i = 0; i < size; i++) {
                String str2 = ea.get(i);
                a(documentData.Rd, canvas, this.Ye.measureText(str2));
                canvas.translate(0.0f, (((float) i) * uc) - ((((float) (size - 1)) * uc) / 2.0f));
                a(str2, documentData, canvas, a2);
                canvas.setMatrix(matrix2);
            }
        }
    }

    private void a(c cVar, Matrix matrix2, float f, DocumentData documentData, Canvas canvas) {
        List<e> a2 = a(cVar);
        for (int i = 0; i < a2.size(); i++) {
            Path path = a2.get(i).getPath();
            path.computeBounds(this.We, false);
            this.matrix.set(matrix2);
            this.matrix.preTranslate(0.0f, (-documentData.baselineShift) * h.uc());
            this.matrix.preScale(f, f);
            path.transform(this.matrix);
            if (documentData.Td) {
                a(path, this.Xe, canvas);
                a(path, this.Ye, canvas);
            } else {
                a(path, this.Ye, canvas);
                a(path, this.Xe, canvas);
            }
        }
    }

    private void a(String str, Paint paint, Canvas canvas) {
        if (paint.getColor() != 0) {
            if (paint.getStyle() != Paint.Style.STROKE || paint.getStrokeWidth() != 0.0f) {
                canvas.drawText(str, 0, str.length(), 0.0f, 0.0f, paint);
            }
        }
    }

    private void a(String str, DocumentData documentData, Canvas canvas) {
        if (documentData.Td) {
            a(str, this.Xe, canvas);
            a(str, this.Ye, canvas);
            return;
        }
        a(str, this.Ye, canvas);
        a(str, this.Xe, canvas);
    }

    private void a(String str, DocumentData documentData, Canvas canvas, float f) {
        int i = 0;
        while (i < str.length()) {
            String i2 = i(str, i);
            i += i2.length();
            a(i2, documentData, canvas);
            float measureText = this.Xe.measureText(i2, 0, 1);
            float f2 = ((float) documentData.Sd) / 10.0f;
            b<Float, Float> bVar = this.ef;
            if (bVar != null) {
                f2 += bVar.getValue().floatValue();
            }
            canvas.translate(measureText + (f2 * f), 0.0f);
        }
    }

    private void a(String str, DocumentData documentData, Matrix matrix2, com.airbnb.lottie.model.b bVar, Canvas canvas, float f, float f2) {
        for (int i = 0; i < str.length(); i++) {
            c cVar = this.ha.getCharacters().get(c.a(str.charAt(i), bVar.getFamily(), bVar.getStyle()));
            if (cVar != null) {
                a(cVar, matrix2, f2, documentData, canvas);
                float width = ((float) cVar.getWidth()) * f2 * h.uc() * f;
                float f3 = ((float) documentData.Sd) / 10.0f;
                b<Float, Float> bVar2 = this.ef;
                if (bVar2 != null) {
                    f3 += bVar2.getValue().floatValue();
                }
                canvas.translate(width + (f3 * f), 0.0f);
            }
        }
    }

    private List<String> ea(String str) {
        return Arrays.asList(str.replaceAll("\r\n", "\r").replaceAll("\n", "\r").split("\r"));
    }

    private String i(String str, int i) {
        int codePointAt = str.codePointAt(i);
        int charCount = Character.charCount(codePointAt) + i;
        while (charCount < str.length()) {
            int codePointAt2 = str.codePointAt(charCount);
            if (!D(codePointAt2)) {
                break;
            }
            charCount += Character.charCount(codePointAt2);
            codePointAt = (codePointAt * 31) + codePointAt2;
        }
        long j = (long) codePointAt;
        if (this._e.containsKey(j)) {
            return this._e.get(j);
        }
        this.stringBuilder.setLength(0);
        while (i < charCount) {
            int codePointAt3 = str.codePointAt(i);
            this.stringBuilder.appendCodePoint(codePointAt3);
            i += Character.charCount(codePointAt3);
        }
        String sb = this.stringBuilder.toString();
        this._e.put(j, sb);
        return sb;
    }

    @Override // com.airbnb.lottie.model.layer.c, com.airbnb.lottie.a.a.f
    public void a(RectF rectF, Matrix matrix2, boolean z) {
        super.a(rectF, matrix2, z);
        rectF.set(0.0f, 0.0f, (float) this.ha.getBounds().width(), (float) this.ha.getBounds().height());
    }

    @Override // com.airbnb.lottie.model.layer.c, com.airbnb.lottie.model.e
    public <T> void a(T t, @Nullable com.airbnb.lottie.e.j<T> jVar) {
        super.a(t, jVar);
        if (t == W.COLOR) {
            b<Integer, Integer> bVar = this.kc;
            if (bVar != null) {
                bVar.a(jVar);
            } else if (jVar == null) {
                if (bVar != null) {
                    b(bVar);
                }
                this.kc = null;
            } else {
                this.kc = new q(jVar);
                this.kc.b(this);
                a(this.kc);
            }
        } else if (t == W.STROKE_COLOR) {
            b<Integer, Integer> bVar2 = this.cf;
            if (bVar2 != null) {
                bVar2.a(jVar);
            } else if (jVar == null) {
                if (bVar2 != null) {
                    b(bVar2);
                }
                this.cf = null;
            } else {
                this.cf = new q(jVar);
                this.cf.b(this);
                a(this.cf);
            }
        } else if (t == W.STROKE_WIDTH) {
            b<Float, Float> bVar3 = this.df;
            if (bVar3 != null) {
                bVar3.a(jVar);
            } else if (jVar == null) {
                if (bVar3 != null) {
                    b(bVar3);
                }
                this.df = null;
            } else {
                this.df = new q(jVar);
                this.df.b(this);
                a(this.df);
            }
        } else if (t == W.dk) {
            b<Float, Float> bVar4 = this.ef;
            if (bVar4 != null) {
                bVar4.a(jVar);
            } else if (jVar == null) {
                if (bVar4 != null) {
                    b(bVar4);
                }
                this.ef = null;
            } else {
                this.ef = new q(jVar);
                this.ef.b(this);
                a(this.ef);
            }
        } else if (t != W.TEXT_SIZE) {
        } else {
            if (jVar == null) {
                b<Float, Float> bVar5 = this.ff;
                if (bVar5 != null) {
                    b(bVar5);
                }
                this.ff = null;
                return;
            }
            this.ff = new q(jVar);
            this.ff.b(this);
            a(this.ff);
        }
    }

    /* access modifiers changed from: package-private */
    @Override // com.airbnb.lottie.model.layer.c
    public void b(Canvas canvas, Matrix matrix2, int i) {
        canvas.save();
        if (!this.La.ja()) {
            canvas.setMatrix(matrix2);
        }
        DocumentData documentData = (DocumentData) this.bf.getValue();
        com.airbnb.lottie.model.b bVar = this.ha.getFonts().get(documentData.Qd);
        if (bVar == null) {
            canvas.restore();
            return;
        }
        b<Integer, Integer> bVar2 = this.kc;
        if (bVar2 != null) {
            this.Xe.setColor(bVar2.getValue().intValue());
        } else {
            this.Xe.setColor(documentData.color);
        }
        b<Integer, Integer> bVar3 = this.cf;
        if (bVar3 != null) {
            this.Ye.setColor(bVar3.getValue().intValue());
        } else {
            this.Ye.setColor(documentData.strokeColor);
        }
        int intValue = ((this.Oc.getOpacity() == null ? 100 : this.Oc.getOpacity().getValue().intValue()) * 255) / 100;
        this.Xe.setAlpha(intValue);
        this.Ye.setAlpha(intValue);
        b<Float, Float> bVar4 = this.df;
        if (bVar4 != null) {
            this.Ye.setStrokeWidth(bVar4.getValue().floatValue());
        } else {
            this.Ye.setStrokeWidth(documentData.strokeWidth * h.uc() * h.a(matrix2));
        }
        if (this.La.ja()) {
            a(documentData, matrix2, bVar, canvas);
        } else {
            a(documentData, bVar, matrix2, canvas);
        }
        canvas.restore();
    }
}
