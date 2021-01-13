package com.airbnb.lottie.a.b;

import android.graphics.Path;
import com.airbnb.lottie.model.content.Mask;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MaskKeyframeAnimation */
public class h {
    private final List<b<com.airbnb.lottie.model.content.h, Path>> td;
    private final List<b<Integer, Integer>> ud;
    private final List<Mask> vd;

    public h(List<Mask> list) {
        this.vd = list;
        this.td = new ArrayList(list.size());
        this.ud = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            this.td.add(list.get(i).Vb().F());
            this.ud.add(list.get(i).getOpacity().F());
        }
    }

    public List<b<com.airbnb.lottie.model.content.h, Path>> vb() {
        return this.td;
    }

    public List<Mask> wb() {
        return this.vd;
    }

    public List<b<Integer, Integer>> xb() {
        return this.ud;
    }
}
