package com.airbnb.lottie.model.layer;

import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.model.layer.Layer;

/* access modifiers changed from: package-private */
/* compiled from: BaseLayer */
public /* synthetic */ class b {
    static final /* synthetic */ int[] ye = new int[Layer.LayerType.values().length];
    static final /* synthetic */ int[] ze = new int[Mask.MaskMode.values().length];

    /* JADX WARNING: Can't wrap try/catch for region: R(24:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|(2:13|14)|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|32) */
    /* JADX WARNING: Can't wrap try/catch for region: R(26:0|1|2|3|(2:5|6)|7|(2:9|10)|11|13|14|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|32) */
    /* JADX WARNING: Can't wrap try/catch for region: R(27:0|1|2|3|5|6|7|(2:9|10)|11|13|14|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|32) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0048 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0052 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x005c */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0066 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x0071 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x007c */
    static {
        try {
            ze[Mask.MaskMode.MASK_MODE_NONE.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            ze[Mask.MaskMode.MASK_MODE_SUBTRACT.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            ze[Mask.MaskMode.MASK_MODE_INTERSECT.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            ze[Mask.MaskMode.MASK_MODE_ADD.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        ye[Layer.LayerType.SHAPE.ordinal()] = 1;
        ye[Layer.LayerType.PRE_COMP.ordinal()] = 2;
        ye[Layer.LayerType.SOLID.ordinal()] = 3;
        ye[Layer.LayerType.IMAGE.ordinal()] = 4;
        ye[Layer.LayerType.NULL.ordinal()] = 5;
        ye[Layer.LayerType.TEXT.ordinal()] = 6;
        try {
            ye[Layer.LayerType.UNKNOWN.ordinal()] = 7;
        } catch (NoSuchFieldError unused5) {
        }
    }
}
