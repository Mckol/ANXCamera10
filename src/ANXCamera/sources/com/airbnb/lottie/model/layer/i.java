package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.W;
import com.airbnb.lottie.a.a;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.a.b.q;
import com.airbnb.lottie.e.j;

/* compiled from: SolidLayer */
public class i extends c {
    private final Layer Ke;
    @Nullable
    private b<ColorFilter, ColorFilter> fc;
    private final Paint paint = new a();
    private final Path path = new Path();
    private final float[] points = new float[8];
    private final RectF rect = new RectF();

    i(Q q, Layer layer) {
        super(q, layer);
        this.Ke = layer;
        this.paint.setAlpha(0);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(layer.getSolidColor());
    }

    @Override // com.airbnb.lottie.model.layer.c, com.airbnb.lottie.a.a.f
    public void a(RectF rectF, Matrix matrix, boolean z) {
        super.a(rectF, matrix, z);
        this.rect.set(0.0f, 0.0f, (float) this.Ke.mc(), (float) this.Ke.lc());
        this.Je.mapRect(this.rect);
        rectF.set(this.rect);
    }

    @Override // com.airbnb.lottie.model.layer.c, com.airbnb.lottie.model.e
    public <T> void a(T t, @Nullable j<T> jVar) {
        super.a(t, jVar);
        if (t != W.qk) {
            return;
        }
        if (jVar == null) {
            this.fc = null;
        } else {
            this.fc = new q(jVar);
        }
    }

    @Override // com.airbnb.lottie.model.layer.c
    public void b(Canvas canvas, Matrix matrix, int i) {
        int alpha = Color.alpha(this.Ke.getSolidColor());
        if (alpha != 0) {
            int intValue = (int) ((((float) i) / 255.0f) * (((((float) alpha) / 255.0f) * ((float) (this.Oc.getOpacity() == null ? 100 : this.Oc.getOpacity().getValue().intValue()))) / 100.0f) * 255.0f);
            this.paint.setAlpha(intValue);
            b<ColorFilter, ColorFilter> bVar = this.fc;
            if (bVar != null) {
                this.paint.setColorFilter(bVar.getValue());
            }
            if (intValue > 0) {
                float[] fArr = this.points;
                fArr[0] = 0.0f;
                fArr[1] = 0.0f;
                fArr[2] = (float) this.Ke.mc();
                float[] fArr2 = this.points;
                fArr2[3] = 0.0f;
                fArr2[4] = (float) this.Ke.mc();
                this.points[5] = (float) this.Ke.lc();
                float[] fArr3 = this.points;
                fArr3[6] = 0.0f;
                fArr3[7] = (float) this.Ke.lc();
                matrix.mapPoints(this.points);
                this.path.reset();
                Path path2 = this.path;
                float[] fArr4 = this.points;
                path2.moveTo(fArr4[0], fArr4[1]);
                Path path3 = this.path;
                float[] fArr5 = this.points;
                path3.lineTo(fArr5[2], fArr5[3]);
                Path path4 = this.path;
                float[] fArr6 = this.points;
                path4.lineTo(fArr6[4], fArr6[5]);
                Path path5 = this.path;
                float[] fArr7 = this.points;
                path5.lineTo(fArr7[6], fArr7[7]);
                Path path6 = this.path;
                float[] fArr8 = this.points;
                path6.lineTo(fArr8[0], fArr8[1]);
                this.path.close();
                canvas.drawPath(this.path, this.paint);
            }
        }
    }
}
