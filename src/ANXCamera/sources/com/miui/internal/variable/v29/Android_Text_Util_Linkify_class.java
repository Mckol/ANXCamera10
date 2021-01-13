package com.miui.internal.variable.v29;

import android.text.Spannable;
import android.text.util.Linkify;
import android.util.Log;
import com.miui.internal.variable.api.Overridable;
import com.miui.internal.variable.api.v29.Android_Text_Util_Linkify;
import java.util.ArrayList;
import java.util.regex.Pattern;
import miui.reflect.Method;

public class Android_Text_Util_Linkify_class extends com.miui.internal.variable.v27.Android_Text_Util_Linkify_class implements Overridable<Android_Text_Util_Linkify.Interface> {
    private static final String TAG = "Linkify.v29";
    protected static final Method mApplyLink;
    private Android_Text_Util_Linkify.Interface mImpl = new Android_Text_Util_Linkify.Interface() {
        /* class com.miui.internal.variable.v29.Android_Text_Util_Linkify_class.AnonymousClass1 */

        @Override // com.miui.internal.variable.api.v29.Android_Text_Util_Linkify.Interface
        public boolean addLinks(Linkify linkify, Spannable spannable, int i) {
            return Android_Text_Util_Linkify_class.this.handleAddLinks(0, linkify, spannable, i);
        }

        @Override // com.miui.internal.variable.api.v29.Android_Text_Util_Linkify.Interface
        public void gatherLinks(Linkify linkify, ArrayList arrayList, Spannable spannable, Pattern pattern, String[] strArr, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
            Android_Text_Util_Linkify_class.this.handleGatherLinks(0, linkify, arrayList, spannable, pattern, strArr, matchFilter, transformFilter);
        }
    };
    private Android_Text_Util_Linkify.Interface mOriginal;

    static {
        Method method;
        try {
            method = Method.of(Linkify.class, "applyLink", "(Ljava/lang/String;IILandroid/text/Spannable;Ljava/util/function/Function;)V");
        } catch (Throwable th) {
            Log.w(TAG, "reflect applyLink failed: ", th);
            method = null;
        }
        mApplyLink = method;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_Text_Util_Linkify_class
    public void applyLink(String str, int i, int i2, Spannable spannable) {
        Method method = mApplyLink;
        if (method != null) {
            method.invoke(null, null, str, Integer.valueOf(i), Integer.valueOf(i2), spannable, null);
            return;
        }
        throw new RuntimeException("applyLink: reflect failed, method not found");
    }

    @Override // com.miui.internal.variable.api.Overridable
    public Android_Text_Util_Linkify.Interface asInterface() {
        return this.mImpl;
    }

    public void bind(Android_Text_Util_Linkify.Interface r1) {
        this.mOriginal = r1;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_Text_Util_Linkify_class
    public boolean callOriginalAddLinks(long j, Linkify linkify, Spannable spannable, int i) {
        Android_Text_Util_Linkify.Interface r0 = this.mOriginal;
        return r0 != null ? r0.addLinks(linkify, spannable, i) : super.callOriginalAddLinks(j, linkify, spannable, i);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.Android_Text_Util_Linkify_class
    public void callOriginalGatherLinks(long j, Linkify linkify, ArrayList arrayList, Spannable spannable, Pattern pattern, String[] strArr, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        Android_Text_Util_Linkify.Interface r1 = this.mOriginal;
        if (r1 != null) {
            r1.gatherLinks(linkify, arrayList, spannable, pattern, strArr, matchFilter, transformFilter);
        } else {
            super.callOriginalGatherLinks(j, linkify, arrayList, spannable, pattern, strArr, matchFilter, transformFilter);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void onClassProxyDisabled() {
        Android_Text_Util_Linkify.Extension.get().setExtension(this);
    }
}
