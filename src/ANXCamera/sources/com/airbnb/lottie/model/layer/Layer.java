package com.airbnb.lottie.model.layer;

import androidx.annotation.Nullable;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.e.a;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.j;
import com.airbnb.lottie.model.a.k;
import com.airbnb.lottie.model.a.l;
import com.airbnb.lottie.model.content.Mask;
import java.util.List;
import java.util.Locale;

public class Layer {
    private final String Jb;
    private final l Oc;
    @Nullable
    private final b Qe;
    private final List<com.airbnb.lottie.model.content.b> Vd;
    private final C0035m ha;
    private final long hf;
    private final boolean hidden;
    @Nullable

    /* renamed from: if  reason: not valid java name */
    private final String f1if;
    private final int jf;
    private final int kf;
    private final LayerType layerType;
    private final float lb;
    private final float lf;
    private final int mf;
    private final int nf;
    @Nullable
    private final k of;
    private final long parentId;
    private final List<a<Float>> pf;
    private final MatteType qf;
    private final int solidColor;
    @Nullable
    private final j text;
    private final List<Mask> vd;

    public enum LayerType {
        PRE_COMP,
        SOLID,
        IMAGE,
        NULL,
        SHAPE,
        TEXT,
        UNKNOWN
    }

    public enum MatteType {
        NONE,
        ADD,
        INVERT,
        UNKNOWN
    }

    public Layer(List<com.airbnb.lottie.model.content.b> list, C0035m mVar, String str, long j, LayerType layerType2, long j2, @Nullable String str2, List<Mask> list2, l lVar, int i, int i2, int i3, float f, float f2, int i4, int i5, @Nullable j jVar, @Nullable k kVar, List<a<Float>> list3, MatteType matteType, @Nullable b bVar, boolean z) {
        this.Vd = list;
        this.ha = mVar;
        this.Jb = str;
        this.hf = j;
        this.layerType = layerType2;
        this.parentId = j2;
        this.f1if = str2;
        this.vd = list2;
        this.Oc = lVar;
        this.jf = i;
        this.kf = i2;
        this.solidColor = i3;
        this.lf = f;
        this.lb = f2;
        this.mf = i4;
        this.nf = i5;
        this.text = jVar;
        this.of = kVar;
        this.pf = list3;
        this.qf = matteType;
        this.Qe = bVar;
        this.hidden = z;
    }

    /* access modifiers changed from: package-private */
    public List<com.airbnb.lottie.model.content.b> Eb() {
        return this.Vd;
    }

    /* access modifiers changed from: package-private */
    public C0035m aa() {
        return this.ha;
    }

    /* access modifiers changed from: package-private */
    public List<a<Float>> fc() {
        return this.pf;
    }

    public long getId() {
        return this.hf;
    }

    public LayerType getLayerType() {
        return this.layerType;
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return this.Jb;
    }

    /* access modifiers changed from: package-private */
    public long getParentId() {
        return this.parentId;
    }

    /* access modifiers changed from: package-private */
    public int getSolidColor() {
        return this.solidColor;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public j getText() {
        return this.text;
    }

    /* access modifiers changed from: package-private */
    public l getTransform() {
        return this.Oc;
    }

    /* access modifiers changed from: package-private */
    public MatteType hc() {
        return this.qf;
    }

    /* access modifiers changed from: package-private */
    public int ic() {
        return this.nf;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    /* access modifiers changed from: package-private */
    public int jc() {
        return this.mf;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public String kc() {
        return this.f1if;
    }

    /* access modifiers changed from: package-private */
    public int lc() {
        return this.kf;
    }

    /* access modifiers changed from: package-private */
    public int mc() {
        return this.jf;
    }

    /* access modifiers changed from: package-private */
    public float nc() {
        return this.lb / this.ha.bb();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public k oc() {
        return this.of;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public b pc() {
        return this.Qe;
    }

    /* access modifiers changed from: package-private */
    public float qc() {
        return this.lf;
    }

    public String toString() {
        return toString("");
    }

    public String toString(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(getName());
        sb.append("\n");
        Layer e2 = this.ha.e(getParentId());
        if (e2 != null) {
            sb.append("\t\tParents: ");
            sb.append(e2.getName());
            Layer e3 = this.ha.e(e2.getParentId());
            while (e3 != null) {
                sb.append("->");
                sb.append(e3.getName());
                e3 = this.ha.e(e3.getParentId());
            }
            sb.append(str);
            sb.append("\n");
        }
        if (!wb().isEmpty()) {
            sb.append(str);
            sb.append("\tMasks: ");
            sb.append(wb().size());
            sb.append("\n");
        }
        if (!(mc() == 0 || lc() == 0)) {
            sb.append(str);
            sb.append("\tBackground: ");
            sb.append(String.format(Locale.US, "%dx%d %X\n", Integer.valueOf(mc()), Integer.valueOf(lc()), Integer.valueOf(getSolidColor())));
        }
        if (!this.Vd.isEmpty()) {
            sb.append(str);
            sb.append("\tShapes:\n");
            for (com.airbnb.lottie.model.content.b bVar : this.Vd) {
                sb.append(str);
                sb.append("\t\t");
                sb.append(bVar);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public List<Mask> wb() {
        return this.vd;
    }
}
