package com.airbnb.lottie.model.content;

import android.graphics.Path;
import androidx.annotation.Nullable;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.a.h;
import com.airbnb.lottie.model.a.a;
import com.airbnb.lottie.model.a.d;
import com.airbnb.lottie.model.layer.c;

/* compiled from: ShapeFill */
public class i implements b {
    @Nullable
    private final a color;
    private final boolean fillEnabled;
    private final Path.FillType fillType;
    private final boolean hidden;
    private final String name;
    @Nullable
    private final d opacity;

    public i(String str, boolean z, Path.FillType fillType2, @Nullable a aVar, @Nullable d dVar, boolean z2) {
        this.name = str;
        this.fillEnabled = z;
        this.fillType = fillType2;
        this.color = aVar;
        this.opacity = dVar;
        this.hidden = z2;
    }

    @Override // com.airbnb.lottie.model.content.b
    public com.airbnb.lottie.a.a.d a(Q q, c cVar) {
        return new h(q, cVar, this);
    }

    @Nullable
    public a getColor() {
        return this.color;
    }

    public Path.FillType getFillType() {
        return this.fillType;
    }

    public String getName() {
        return this.name;
    }

    @Nullable
    public d getOpacity() {
        return this.opacity;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public String toString() {
        return "ShapeFill{color=, fillEnabled=" + this.fillEnabled + '}';
    }
}
