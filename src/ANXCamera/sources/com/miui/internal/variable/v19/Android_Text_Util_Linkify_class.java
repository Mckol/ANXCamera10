package com.miui.internal.variable.v19;

import android.text.Spannable;
import android.text.util.Linkify;
import java.util.ArrayList;
import java.util.regex.Pattern;
import miui.reflect.Method;
import miui.reflect.NoSuchMethodException;

public class Android_Text_Util_Linkify_class extends com.miui.internal.variable.v16.Android_Text_Util_Linkify_class {
    private static Method sGatherTelLinks;

    static {
        try {
            sGatherTelLinks = Method.of(Linkify.class, "gatherTelLinks", "(Ljava/util/ArrayList;Landroid/text/Spannable;)V");
        } catch (NoSuchMethodException unused) {
            sGatherTelLinks = null;
        }
    }

    @Override // com.miui.internal.variable.v16.Android_Text_Util_Linkify_class
    public void gatherTelLinks(ArrayList arrayList, Spannable spannable, Pattern pattern, String[] strArr, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        sGatherTelLinks.invoke(null, null, arrayList, spannable);
    }
}
