package com.airbnb.lottie.d;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.FloatRange;
import com.airbnb.lottie.a.a.l;
import com.airbnb.lottie.model.a;
import com.airbnb.lottie.model.content.h;
import com.airbnb.lottie.model.d;
import java.util.List;

/* compiled from: MiscUtils */
public class g {
    private static PointF _f = new PointF();

    public static double a(double d2, double d3, @FloatRange(from = 0.0d, to = 1.0d) double d4) {
        return d2 + (d4 * (d3 - d2));
    }

    public static int a(int i, int i2, @FloatRange(from = 0.0d, to = 1.0d) float f) {
        return (int) (((float) i) + (f * ((float) (i2 - i))));
    }

    public static PointF a(PointF pointF, PointF pointF2) {
        return new PointF(pointF.x + pointF2.x, pointF.y + pointF2.y);
    }

    public static void a(h hVar, Path path) {
        path.reset();
        PointF ac = hVar.ac();
        path.moveTo(ac.x, ac.y);
        _f.set(ac.x, ac.y);
        for (int i = 0; i < hVar._b().size(); i++) {
            a aVar = hVar._b().get(i);
            PointF Ab = aVar.Ab();
            PointF Bb = aVar.Bb();
            PointF Cb = aVar.Cb();
            if (!Ab.equals(_f) || !Bb.equals(Cb)) {
                path.cubicTo(Ab.x, Ab.y, Bb.x, Bb.y, Cb.x, Cb.y);
            } else {
                path.lineTo(Cb.x, Cb.y);
            }
            _f.set(Cb.x, Cb.y);
        }
        if (hVar.isClosed()) {
            path.close();
        }
    }

    public static void a(d dVar, int i, List<d> list, d dVar2, l lVar) {
        if (dVar.d(lVar.getName(), i)) {
            list.add(dVar2.M(lVar.getName()).a(lVar));
        }
    }

    public static boolean a(float f, float f2, float f3) {
        return f >= f2 && f <= f3;
    }

    public static float clamp(float f, float f2, float f3) {
        return Math.max(f2, Math.min(f3, f));
    }

    public static int clamp(int i, int i2, int i3) {
        return Math.max(i2, Math.min(i3, i));
    }

    static int f(float f, float f2) {
        return floorMod((int) f, (int) f2);
    }

    private static int floorDiv(int i, int i2) {
        int i3 = i / i2;
        return (((i ^ i2) >= 0) || i % i2 == 0) ? i3 : i3 - 1;
    }

    private static int floorMod(int i, int i2) {
        return i - (i2 * floorDiv(i, i2));
    }

    public static float lerp(float f, float f2, @FloatRange(from = 0.0d, to = 1.0d) float f3) {
        return f + (f3 * (f2 - f));
    }
}
