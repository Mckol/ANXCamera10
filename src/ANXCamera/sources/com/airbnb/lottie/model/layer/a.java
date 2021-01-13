package com.airbnb.lottie.model.layer;

import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.a.b.d;

/* access modifiers changed from: package-private */
/* compiled from: BaseLayer */
public class a implements b.a {
    final /* synthetic */ c this$0;
    final /* synthetic */ d xe;

    a(c cVar, d dVar) {
        this.this$0 = cVar;
        this.xe = dVar;
    }

    @Override // com.airbnb.lottie.a.b.b.a
    public void G() {
        this.this$0.setVisible(this.xe.getFloatValue() == 1.0f);
    }
}
