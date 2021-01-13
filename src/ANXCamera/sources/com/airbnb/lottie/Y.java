package com.airbnb.lottie;

/* access modifiers changed from: package-private */
/* compiled from: LottieTask */
public class Y implements Runnable {
    final /* synthetic */ Z this$0;

    Y(Z z) {
        this.this$0 = z;
    }

    public void run() {
        if (this.this$0.result != null) {
            X x = this.this$0.result;
            if (x.getValue() != null) {
                this.this$0.d((Z) x.getValue());
            } else {
                this.this$0.j(x.getException());
            }
        }
    }
}
