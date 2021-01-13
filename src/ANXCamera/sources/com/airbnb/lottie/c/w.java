package com.airbnb.lottie.c;

import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.model.a.d;
import com.airbnb.lottie.model.a.h;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import java.io.IOException;

/* access modifiers changed from: package-private */
/* compiled from: MaskParser */
public class w {
    private w() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0071  */
    static Mask a(JsonReader jsonReader, C0035m mVar) throws IOException {
        boolean z;
        jsonReader.beginObject();
        Mask.MaskMode maskMode = null;
        boolean z2 = false;
        h hVar = null;
        d dVar = null;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            int hashCode = nextName.hashCode();
            char c2 = 65535;
            if (hashCode != 111) {
                if (hashCode != 3588) {
                    if (hashCode != 104433) {
                        if (hashCode == 3357091 && nextName.equals("mode")) {
                            z = false;
                            if (!z) {
                                String nextString = jsonReader.nextString();
                                int hashCode2 = nextString.hashCode();
                                if (hashCode2 != 97) {
                                    if (hashCode2 != 105) {
                                        if (hashCode2 != 110) {
                                            if (hashCode2 == 115 && nextString.equals("s")) {
                                                c2 = 1;
                                            }
                                        } else if (nextString.equals("n")) {
                                            c2 = 2;
                                        }
                                    } else if (nextString.equals(SupportedConfigFactory.CLOSE_BY_ULTRA_WIDE)) {
                                        c2 = 3;
                                    }
                                } else if (nextString.equals(SupportedConfigFactory.CLOSE_BY_HHT)) {
                                    c2 = 0;
                                }
                                if (c2 == 0) {
                                    maskMode = Mask.MaskMode.MASK_MODE_ADD;
                                } else if (c2 == 1) {
                                    maskMode = Mask.MaskMode.MASK_MODE_SUBTRACT;
                                } else if (c2 == 2) {
                                    maskMode = Mask.MaskMode.MASK_MODE_NONE;
                                } else if (c2 != 3) {
                                    com.airbnb.lottie.d.d.warning("Unknown mask mode " + nextName + ". Defaulting to Add.");
                                    maskMode = Mask.MaskMode.MASK_MODE_ADD;
                                } else {
                                    mVar.F("Animation contains intersect masks. They are not supported but will be treated like add masks.");
                                    maskMode = Mask.MaskMode.MASK_MODE_INTERSECT;
                                }
                            } else if (z) {
                                hVar = C0016d.i(jsonReader, mVar);
                            } else if (z) {
                                dVar = C0016d.f(jsonReader, mVar);
                            } else if (!z) {
                                jsonReader.skipValue();
                            } else {
                                z2 = jsonReader.nextBoolean();
                            }
                        }
                    } else if (nextName.equals("inv")) {
                        z = true;
                        if (!z) {
                        }
                    }
                } else if (nextName.equals("pt")) {
                    z = true;
                    if (!z) {
                    }
                }
            } else if (nextName.equals(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT)) {
                z = true;
                if (!z) {
                }
            }
            z = true;
            if (!z) {
            }
        }
        jsonReader.endObject();
        return new Mask(maskMode, hVar, dVar, z2);
    }
}
