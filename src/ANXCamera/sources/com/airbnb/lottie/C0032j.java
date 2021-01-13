package com.airbnb.lottie;

/* renamed from: com.airbnb.lottie.j  reason: case insensitive filesystem */
/* compiled from: LottieAnimationView */
/* synthetic */ class C0032j {
    static final /* synthetic */ int[] bb = new int[RenderMode.values().length];

    /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
    static {
        bb[RenderMode.HARDWARE.ordinal()] = 1;
        bb[RenderMode.SOFTWARE.ordinal()] = 2;
        try {
            bb[RenderMode.AUTOMATIC.ordinal()] = 3;
        } catch (NoSuchFieldError unused) {
        }
    }
}
