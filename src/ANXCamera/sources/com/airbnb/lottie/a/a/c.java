package com.airbnb.lottie.a.a;

import android.graphics.Path;
import com.airbnb.lottie.d.h;
import java.util.ArrayList;
import java.util.List;

/* compiled from: CompoundTrimPathContent */
public class c {
    private List<w> contents = new ArrayList();

    public void a(Path path) {
        for (int size = this.contents.size() - 1; size >= 0; size--) {
            h.a(path, this.contents.get(size));
        }
    }

    /* access modifiers changed from: package-private */
    public void a(w wVar) {
        this.contents.add(wVar);
    }
}
