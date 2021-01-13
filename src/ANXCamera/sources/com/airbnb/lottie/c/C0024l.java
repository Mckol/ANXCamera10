package com.airbnb.lottie.c;

import android.graphics.Color;
import androidx.annotation.IntRange;
import com.airbnb.lottie.d.g;
import com.airbnb.lottie.model.content.c;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.airbnb.lottie.c.l  reason: case insensitive filesystem */
/* compiled from: GradientColorParser */
public class C0024l implements L<c> {
    private int zf;

    public C0024l(int i) {
        this.zf = i;
    }

    @IntRange(from = 0, to = 255)
    private int a(double d2, double[] dArr, double[] dArr2) {
        double d3;
        int i = 1;
        while (true) {
            if (i >= dArr.length) {
                d3 = dArr2[dArr2.length - 1];
                break;
            }
            int i2 = i - 1;
            double d4 = dArr[i2];
            double d5 = dArr[i];
            if (dArr[i] >= d2) {
                d3 = g.a(dArr2[i2], dArr2[i], (d2 - d4) / (d5 - d4));
                break;
            }
            i++;
        }
        return (int) (d3 * 255.0d);
    }

    private void a(c cVar, List<Float> list) {
        int i = this.zf * 4;
        if (list.size() > i) {
            int size = (list.size() - i) / 2;
            double[] dArr = new double[size];
            double[] dArr2 = new double[size];
            int i2 = 0;
            while (i < list.size()) {
                if (i % 2 == 0) {
                    dArr[i2] = (double) list.get(i).floatValue();
                } else {
                    dArr2[i2] = (double) list.get(i).floatValue();
                    i2++;
                }
                i++;
            }
            for (int i3 = 0; i3 < cVar.getSize(); i3++) {
                int i4 = cVar.getColors()[i3];
                cVar.getColors()[i3] = Color.argb(a((double) cVar.getPositions()[i3], dArr, dArr2), Color.red(i4), Color.green(i4), Color.blue(i4));
            }
        }
    }

    @Override // com.airbnb.lottie.c.L
    public c a(JsonReader jsonReader, float f) throws IOException {
        ArrayList arrayList = new ArrayList();
        boolean z = jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY;
        if (z) {
            jsonReader.beginArray();
        }
        while (jsonReader.hasNext()) {
            arrayList.add(Float.valueOf((float) jsonReader.nextDouble()));
        }
        if (z) {
            jsonReader.endArray();
        }
        if (this.zf == -1) {
            this.zf = arrayList.size() / 4;
        }
        int i = this.zf;
        float[] fArr = new float[i];
        int[] iArr = new int[i];
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < this.zf * 4; i4++) {
            int i5 = i4 / 4;
            double floatValue = (double) arrayList.get(i4).floatValue();
            int i6 = i4 % 4;
            if (i6 == 0) {
                fArr[i5] = (float) floatValue;
            } else if (i6 == 1) {
                i2 = (int) (floatValue * 255.0d);
            } else if (i6 == 2) {
                i3 = (int) (floatValue * 255.0d);
            } else if (i6 == 3) {
                iArr[i5] = Color.argb(255, i2, i3, (int) (floatValue * 255.0d));
            }
        }
        c cVar = new c(fArr, iArr);
        a(cVar, arrayList);
        return cVar;
    }
}
