package com.airbnb.lottie.model.layer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.W;
import com.airbnb.lottie.a.a;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.a.b.q;
import com.airbnb.lottie.d.h;
import com.airbnb.lottie.e.j;

/* compiled from: ImageLayer */
public class f extends c {
    private final Rect Ve = new Rect();
    @Nullable
    private b<ColorFilter, ColorFilter> fc;
    private final Paint paint = new a(3);
    private final Rect src = new Rect();

    f(Q q, Layer layer) {
        super(q, layer);
    }

    @Nullable
    private Bitmap getBitmap() {
        return this.La.k(this.Ke.kc());
    }

    @Override // com.airbnb.lottie.model.layer.c, com.airbnb.lottie.a.a.f
    public void a(RectF rectF, Matrix matrix, boolean z) {
        super.a(rectF, matrix, z);
        Bitmap bitmap = getBitmap();
        if (bitmap != null) {
            rectF.set(0.0f, 0.0f, ((float) bitmap.getWidth()) * h.uc(), ((float) bitmap.getHeight()) * h.uc());
            this.Je.mapRect(rectF);
        }
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
    public void b(@NonNull Canvas canvas, Matrix matrix, int i) {
        Bitmap bitmap = getBitmap();
        if (bitmap != null && !bitmap.isRecycled()) {
            float uc = h.uc();
            this.paint.setAlpha(i);
            b<ColorFilter, ColorFilter> bVar = this.fc;
            if (bVar != null) {
                this.paint.setColorFilter(bVar.getValue());
            }
            canvas.save();
            canvas.concat(matrix);
            this.src.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
            this.Ve.set(0, 0, (int) (((float) bitmap.getWidth()) * uc), (int) (((float) bitmap.getHeight()) * uc));
            canvas.drawBitmap(bitmap, this.src, this.Ve, this.paint);
            canvas.restore();
        }
    }
}
