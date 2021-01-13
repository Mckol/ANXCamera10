package com.airbnb.lottie.c;

import com.airbnb.lottie.model.content.MergePaths;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import java.io.IOException;

/* access modifiers changed from: package-private */
/* compiled from: MergePathsParser */
public class x {
    private static final JsonReader.a NAMES = JsonReader.a.of("nm", SupportedConfigFactory.CLOSE_BY_MANUAL_MODE, "hd");

    private x() {
    }

    static MergePaths b(JsonReader jsonReader) throws IOException {
        String str = null;
        boolean z = false;
        MergePaths.MergePathsMode mergePathsMode = null;
        while (jsonReader.hasNext()) {
            int a2 = jsonReader.a(NAMES);
            if (a2 == 0) {
                str = jsonReader.nextString();
            } else if (a2 == 1) {
                mergePathsMode = MergePaths.MergePathsMode.A(jsonReader.nextInt());
            } else if (a2 != 2) {
                jsonReader.sc();
                jsonReader.skipValue();
            } else {
                z = jsonReader.nextBoolean();
            }
        }
        return new MergePaths(str, mergePathsMode, z);
    }
}
