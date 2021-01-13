package com.miui.internal.variable.v16;

import android.text.Spannable;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Patterns;
import com.android.camera.network.resource.RequestContracts;
import java.util.ArrayList;
import java.util.regex.Pattern;
import miui.reflect.Field;
import miui.reflect.Method;

public class Android_Text_Util_Linkify_class extends com.miui.internal.variable.Android_Text_Util_Linkify_class {
    private static final String TAG = "Linkify.v16";
    private static final Method mApplyLink;
    private static boolean mDisabled = false;
    private static final Field mEnd = Field.of("android.text.util.LinkSpec", "end", Field.INT_SIGNATURE_PRIMITIVE);
    private static final Method mGatherLinks;
    private static final Method mGatherMapLinks = Method.of(Linkify.class, "gatherMapLinks", "(Ljava/util/ArrayList;Landroid/text/Spannable;)V");
    private static final Method mPruneOverlaps = Method.of(Linkify.class, "pruneOverlaps", "(Ljava/util/ArrayList;)V");
    private static final Field mStart = Field.of("android.text.util.LinkSpec", "start", Field.INT_SIGNATURE_PRIMITIVE);
    private static final Field mUrl = Field.of("android.text.util.LinkSpec", RequestContracts.Download.JSON_KEY_URL, "Ljava/lang/String;");

    static {
        Method method;
        Method method2 = null;
        try {
            method = Method.of(Linkify.class, "gatherLinks", "(Ljava/util/ArrayList;Landroid/text/Spannable;Ljava/util/regex/Pattern;[Ljava/lang/String;Landroid/text/util/Linkify$MatchFilter;Landroid/text/util/Linkify$TransformFilter;)V");
        } catch (Throwable th) {
            Log.w(TAG, "reflect gatherLinks failed: ", th);
            method = null;
        }
        mGatherLinks = method;
        try {
            method2 = Method.of(Linkify.class, "applyLink", "(Ljava/lang/String;IILandroid/text/Spannable;)V");
        } catch (Throwable th2) {
            Log.w(TAG, "reflect applyLink failed: ", th2);
        }
        mApplyLink = method2;
    }

    static void disable() {
        mDisabled = true;
    }

    static void enable() {
        mDisabled = false;
    }

    /* access modifiers changed from: protected */
    public boolean addEmailLinks(long j, Linkify linkify, ArrayList arrayList, Spannable spannable, String[] strArr, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        int size = arrayList.size();
        gatherLinks(arrayList, spannable, Patterns.EMAIL_ADDRESS, new String[]{"mailto:"}, null, null);
        return arrayList.size() > size;
    }

    /* access modifiers changed from: protected */
    public void applyLink(String str, int i, int i2, Spannable spannable) {
        Method method = mApplyLink;
        if (method != null) {
            method.invoke(null, null, str, Integer.valueOf(i), Integer.valueOf(i2), spannable);
            return;
        }
        throw new RuntimeException("applyLink: reflect failed, method not found");
    }

    @Override // com.miui.internal.variable.Android_Text_Util_Linkify_class, com.miui.internal.variable.IManagedClassProxy
    public void buildProxy() {
        attachMethod("addLinks", "(Landroid/text/Spannable;I)Z");
        super.buildProxy();
    }

    /* access modifiers changed from: protected */
    public boolean callOriginalAddLinks(long j, Linkify linkify, Spannable spannable, int i) {
        return originalAddLinks(j, linkify, spannable, i);
    }

    /* access modifiers changed from: protected */
    public void gatherLinks(ArrayList arrayList, Spannable spannable, Pattern pattern, String[] strArr, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        Method method = mGatherLinks;
        if (method != null) {
            method.invoke(null, null, arrayList, spannable, pattern, strArr, matchFilter, transformFilter);
            return;
        }
        throw new RuntimeException("gatherLinks: reflect failed, method not found");
    }

    /* access modifiers changed from: protected */
    public void gatherTelLinks(ArrayList arrayList, Spannable spannable, Pattern pattern, String[] strArr, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        gatherLinks(arrayList, spannable, Patterns.PHONE, new String[]{"tel:"}, Linkify.sPhoneNumberMatchFilter, Linkify.sPhoneNumberTransformFilter);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.Android_Text_Util_Linkify_class, com.miui.internal.util.ClassProxy
    public void handle() {
        handleAddLinks(0, null, null, 0);
    }

    /* access modifiers changed from: protected */
    public boolean handleAddLinks(long j, Linkify linkify, Spannable spannable, int i) {
        if (i == 0) {
            return false;
        }
        if (mDisabled) {
            return callOriginalAddLinks(j, linkify, spannable, i);
        }
        URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int length = uRLSpanArr.length - 1; length >= 0; length--) {
            spannable.removeSpan(uRLSpanArr[length]);
        }
        ArrayList arrayList = new ArrayList();
        if ((i & miui.text.util.Linkify.TIME_PHRASES) != 0) {
            gatherLinks(arrayList, spannable, miui.util.Patterns.CHINESE_TIME_PATTERN, new String[]{"time:"}, null, null);
            gatherLinks(arrayList, spannable, miui.util.Patterns.ENGLISH_TIME_PATTERN, new String[]{"time:"}, null, null);
        }
        if ((i & 1) != 0) {
            gatherLinks(arrayList, spannable, miui.util.Patterns.WEB_URL, new String[]{"http://", "https://", "rtsp://"}, Linkify.sUrlMatchFilter, null);
        }
        boolean addEmailLinks = (i & 2) != 0 ? addEmailLinks(j, linkify, arrayList, spannable, new String[]{"mailto:"}, null, null) : false;
        if ((i & 4) != 0) {
            gatherTelLinks(arrayList, spannable, Patterns.PHONE, new String[]{"tel:"}, Linkify.sPhoneNumberMatchFilter, Linkify.sPhoneNumberTransformFilter);
        }
        if ((i & 8) != 0) {
            mGatherMapLinks.invoke(null, null, arrayList, spannable);
        }
        mPruneOverlaps.invoke(null, null, arrayList);
        if (arrayList.size() == 0 && !addEmailLinks) {
            return false;
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            Object obj = arrayList.get(i2);
            applyLink((String) mUrl.get(obj), mStart.getInt(obj), mEnd.getInt(obj), spannable);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean originalAddLinks(long j, Linkify linkify, Spannable spannable, int i) {
        throw new IllegalStateException("com.miui.internal.variable.v16.Android_Text_Util_Linkify_class.originalAddLinks(long, Linkify, Spannable, int)");
    }
}
