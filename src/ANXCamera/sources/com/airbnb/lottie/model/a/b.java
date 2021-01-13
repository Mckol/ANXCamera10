package com.airbnb.lottie.model.a;

import com.airbnb.lottie.a.b.d;
import com.airbnb.lottie.e.a;
import java.util.List;

/* compiled from: AnimatableFloatValue */
public class b extends n<Float, Float> {
    b() {
        super(Float.valueOf(0.0f));
    }

    public b(List<a<Float>> list) {
        super((List) list);
    }

    @Override // com.airbnb.lottie.model.a.m
    public com.airbnb.lottie.a.b.b<Float, Float> F() {
        return new d(this.Uc);
    }

    /* Return type fixed from 'java.util.List' to match base method */
    @Override // com.airbnb.lottie.model.a.n, com.airbnb.lottie.model.a.m
    public /* bridge */ /* synthetic */ List<a<Float>> getKeyframes() {
        return super.getKeyframes();
    }

    @Override // com.airbnb.lottie.model.a.n, com.airbnb.lottie.model.a.m
    public /* bridge */ /* synthetic */ boolean isStatic() {
        return super.isStatic();
    }

    @Override // com.airbnb.lottie.model.a.n
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }
}
