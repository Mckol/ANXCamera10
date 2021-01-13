package com.airbnb.lottie.model.content;

import com.airbnb.lottie.model.content.ShapeStroke;

/* access modifiers changed from: package-private */
/* compiled from: ShapeStroke */
public /* synthetic */ class l {
    static final /* synthetic */ int[] ve = new int[ShapeStroke.LineCapType.values().length];
    static final /* synthetic */ int[] we = new int[ShapeStroke.LineJoinType.values().length];

    /* JADX WARNING: Can't wrap try/catch for region: R(14:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|13|14|15|16|17|18|20) */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x003d */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0047 */
    static {
        try {
            we[ShapeStroke.LineJoinType.BEVEL.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            we[ShapeStroke.LineJoinType.MITER.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            we[ShapeStroke.LineJoinType.ROUND.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        ve[ShapeStroke.LineCapType.BUTT.ordinal()] = 1;
        ve[ShapeStroke.LineCapType.ROUND.ordinal()] = 2;
        ve[ShapeStroke.LineCapType.UNKNOWN.ordinal()] = 3;
    }
}
