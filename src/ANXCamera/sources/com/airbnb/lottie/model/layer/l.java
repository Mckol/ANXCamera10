package com.airbnb.lottie.model.layer;

import com.airbnb.lottie.model.DocumentData;

/* compiled from: TextLayer */
/* synthetic */ class l {
    static final /* synthetic */ int[] rf = new int[DocumentData.Justification.values().length];

    /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
    static {
        rf[DocumentData.Justification.LEFT_ALIGN.ordinal()] = 1;
        rf[DocumentData.Justification.RIGHT_ALIGN.ordinal()] = 2;
        try {
            rf[DocumentData.Justification.CENTER.ordinal()] = 3;
        } catch (NoSuchFieldError unused) {
        }
    }
}
