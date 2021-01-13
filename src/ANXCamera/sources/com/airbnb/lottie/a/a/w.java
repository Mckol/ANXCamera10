package com.airbnb.lottie.a.a;

import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.c;
import java.util.ArrayList;
import java.util.List;

/* compiled from: TrimPathContent */
public class w implements d, b.a {
    private final b<?, Float> Rc;
    private final b<?, Float> Sc;
    private final b<?, Float> Tc;
    private final boolean hidden;
    private final List<b.a> listeners = new ArrayList();
    private final String name;
    private final ShapeTrimPath.Type type;

    public w(c cVar, ShapeTrimPath shapeTrimPath) {
        this.name = shapeTrimPath.getName();
        this.hidden = shapeTrimPath.isHidden();
        this.type = shapeTrimPath.getType();
        this.Rc = shapeTrimPath.getStart().F();
        this.Sc = shapeTrimPath.getEnd().F();
        this.Tc = shapeTrimPath.getOffset().F();
        cVar.a(this.Rc);
        cVar.a(this.Sc);
        cVar.a(this.Tc);
        this.Rc.b(this);
        this.Sc.b(this);
        this.Tc.b(this);
    }

    @Override // com.airbnb.lottie.a.b.b.a
    public void G() {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).G();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(b.a aVar) {
        this.listeners.add(aVar);
    }

    @Override // com.airbnb.lottie.a.a.d
    public void a(List<d> list, List<d> list2) {
    }

    public b<?, Float> getEnd() {
        return this.Sc;
    }

    @Override // com.airbnb.lottie.a.a.d
    public String getName() {
        return this.name;
    }

    public b<?, Float> getOffset() {
        return this.Tc;
    }

    public b<?, Float> getStart() {
        return this.Rc;
    }

    /* access modifiers changed from: package-private */
    public ShapeTrimPath.Type getType() {
        return this.type;
    }

    public boolean isHidden() {
        return this.hidden;
    }
}
