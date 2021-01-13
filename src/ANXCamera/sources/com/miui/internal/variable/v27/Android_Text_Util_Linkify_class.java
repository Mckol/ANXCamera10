package com.miui.internal.variable.v27;

import android.text.Spannable;
import android.text.util.Linkify;
import java.util.ArrayList;
import java.util.regex.Pattern;
import miui.reflect.Method;

public class Android_Text_Util_Linkify_class extends com.miui.internal.variable.v16.Android_Text_Util_Linkify_class {
    private static final Method mGatherTelLinks = Method.of(Linkify.class, "gatherTelLinks", "(Ljava/util/ArrayList;Landroid/text/Spannable;Landroid/content/Context;)V");

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_Text_Util_Linkify_class
    public boolean addEmailLinks(long j, Linkify linkify, ArrayList arrayList, Spannable spannable, String[] strArr, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        return callOriginalAddLinks(j, linkify, spannable, 2);
    }

    @Override // com.miui.internal.variable.v16.Android_Text_Util_Linkify_class
    public void gatherTelLinks(ArrayList arrayList, Spannable spannable, Pattern pattern, String[] strArr, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        mGatherTelLinks.invoke(null, null, arrayList, spannable, null);
    }
}
