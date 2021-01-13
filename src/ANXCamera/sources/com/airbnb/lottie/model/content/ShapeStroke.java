package com.airbnb.lottie.model.content;

import android.graphics.Paint;
import androidx.annotation.Nullable;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.a.v;
import com.airbnb.lottie.model.a.a;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.d;
import com.airbnb.lottie.model.layer.c;
import java.util.List;

public class ShapeStroke implements b {
    private final a color;
    private final boolean hidden;
    private final LineCapType ie;
    private final LineJoinType je;
    private final float ke;
    private final List<b> le;
    private final String name;
    @Nullable
    private final b offset;
    private final d opacity;
    private final b width;

    public enum LineCapType {
        BUTT,
        ROUND,
        UNKNOWN;

        public Paint.Cap Bo() {
            int i = l.ve[ordinal()];
            return i != 1 ? i != 2 ? Paint.Cap.SQUARE : Paint.Cap.ROUND : Paint.Cap.BUTT;
        }
    }

    public enum LineJoinType {
        MITER,
        ROUND,
        BEVEL;

        public Paint.Join Co() {
            int i = l.we[ordinal()];
            if (i == 1) {
                return Paint.Join.BEVEL;
            }
            if (i == 2) {
                return Paint.Join.MITER;
            }
            if (i != 3) {
                return null;
            }
            return Paint.Join.ROUND;
        }
    }

    public ShapeStroke(String str, @Nullable b bVar, List<b> list, a aVar, d dVar, b bVar2, LineCapType lineCapType, LineJoinType lineJoinType, float f, boolean z) {
        this.name = str;
        this.offset = bVar;
        this.le = list;
        this.color = aVar;
        this.opacity = dVar;
        this.width = bVar2;
        this.ie = lineCapType;
        this.je = lineJoinType;
        this.ke = f;
        this.hidden = z;
    }

    public LineCapType Pb() {
        return this.ie;
    }

    public b Qb() {
        return this.offset;
    }

    public LineJoinType Rb() {
        return this.je;
    }

    public List<b> Sb() {
        return this.le;
    }

    public float Tb() {
        return this.ke;
    }

    @Override // com.airbnb.lottie.model.content.b
    public com.airbnb.lottie.a.a.d a(Q q, c cVar) {
        return new v(q, cVar, this);
    }

    public a getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    public d getOpacity() {
        return this.opacity;
    }

    public b getWidth() {
        return this.width;
    }

    public boolean isHidden() {
        return this.hidden;
    }
}
