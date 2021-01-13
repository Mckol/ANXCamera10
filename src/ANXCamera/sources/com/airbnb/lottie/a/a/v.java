package com.airbnb.lottie.a.a;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import androidx.annotation.Nullable;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.W;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.a.b.q;
import com.airbnb.lottie.e.j;
import com.airbnb.lottie.model.content.ShapeStroke;
import com.airbnb.lottie.model.layer.c;

/* compiled from: StrokeContent */
public class v extends b {
    @Nullable
    private b<ColorFilter, ColorFilter> fc;
    private final boolean hidden;
    private final b<Integer, Integer> kc;
    private final c layer;
    private final String name;

    public v(Q q, c cVar, ShapeStroke shapeStroke) {
        super(q, cVar, shapeStroke.Pb().Bo(), shapeStroke.Rb().Co(), shapeStroke.Tb(), shapeStroke.getOpacity(), shapeStroke.getWidth(), shapeStroke.Sb(), shapeStroke.Qb());
        this.layer = cVar;
        this.name = shapeStroke.getName();
        this.hidden = shapeStroke.isHidden();
        this.kc = shapeStroke.getColor().F();
        this.kc.b(this);
        cVar.a(this.kc);
    }

    @Override // com.airbnb.lottie.a.a.b, com.airbnb.lottie.a.a.f
    public void a(Canvas canvas, Matrix matrix, int i) {
        if (!this.hidden) {
            this.paint.setColor(((com.airbnb.lottie.a.b.c) this.kc).getIntValue());
            b<ColorFilter, ColorFilter> bVar = this.fc;
            if (bVar != null) {
                this.paint.setColorFilter(bVar.getValue());
            }
            super.a(canvas, matrix, i);
        }
    }

    @Override // com.airbnb.lottie.model.e, com.airbnb.lottie.a.a.b
    public <T> void a(T t, @Nullable j<T> jVar) {
        super.a(t, jVar);
        if (t == W.STROKE_COLOR) {
            this.kc.a(jVar);
        } else if (t != W.qk) {
        } else {
            if (jVar == null) {
                this.fc = null;
                return;
            }
            this.fc = new q(jVar);
            this.fc.b(this);
            this.layer.a(this.kc);
        }
    }

    @Override // com.airbnb.lottie.a.a.d
    public String getName() {
        return this.name;
    }
}
