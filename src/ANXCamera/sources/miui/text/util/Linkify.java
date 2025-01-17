package miui.text.util;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import miui.util.Patterns;

public class Linkify {
    public static final int ALL = (TIME_PHRASES | 15);
    private static final Comparator<LinkSpec> COMPARATOR = new Comparator<LinkSpec>() {
        /* class miui.text.util.Linkify.AnonymousClass1 */

        public int compare(LinkSpec linkSpec, LinkSpec linkSpec2) {
            int i;
            int i2;
            int i3 = linkSpec.start;
            int i4 = linkSpec2.start;
            if (i3 < i4) {
                return -1;
            }
            if (i3 <= i4 && (i = linkSpec.end) >= (i2 = linkSpec2.end)) {
                return i > i2 ? -1 : 0;
            }
            return 1;
        }
    };
    public static final int EMAIL_ADDRESSES = 2;
    private static final String[] EMPTY = new String[0];
    public static final int MAP_ADDRESSES = 8;
    private static final int PATTERNS_FROM_FRAMEWORK = 14;
    public static final int PHONE_NUMBERS = 4;
    public static int TIME_PHRASES = 16;
    public static final int WEB_URLS = 1;

    /* access modifiers changed from: private */
    public static class LinkSpec {
        int end;
        URLSpan span;
        int start;
        String url;

        private LinkSpec() {
        }
    }

    private static class Super extends android.text.util.Linkify {
        private Super() {
        }
    }

    private Linkify() {
    }

    private static void addLinkMovementMethod(TextView textView) {
        if (!(textView.getMovementMethod() instanceof LinkMovementMethod) && textView.getLinksClickable()) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public static boolean addLinks(Spannable spannable, int i) {
        if (i == 0) {
            return false;
        }
        URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int length = uRLSpanArr.length - 1; length >= 0; length--) {
            spannable.removeSpan(uRLSpanArr[length]);
        }
        android.text.util.Linkify.addLinks(spannable, i & 14);
        int i2 = i & -15 & ALL;
        ArrayList arrayList = new ArrayList();
        if ((i2 & 1) != 0) {
            gatherLinks(arrayList, spannable, Patterns.WEB_URL, new String[]{"http://", "https://", "rtsp://"}, android.text.util.Linkify.sUrlMatchFilter, null);
        }
        if ((i2 & TIME_PHRASES) != 0) {
            gatherLinks(arrayList, spannable, Patterns.CHINESE_TIME_PATTERN, new String[]{"time:"}, null, null);
            gatherLinks(arrayList, spannable, Patterns.ENGLISH_TIME_PATTERN, new String[]{"time:"}, null, null);
        }
        pruneOverlaps(arrayList, spannable);
        if (arrayList.isEmpty()) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            LinkSpec linkSpec = (LinkSpec) it.next();
            String str = linkSpec.url;
            if (str != null) {
                applyLink(str, linkSpec.start, linkSpec.end, spannable);
            }
        }
        return true;
    }

    public static boolean addLinks(TextView textView, int i) {
        if (i == 0) {
            return false;
        }
        CharSequence text = textView.getText();
        if (!(text instanceof Spannable)) {
            SpannableString valueOf = SpannableString.valueOf(text);
            if (!addLinks(valueOf, i)) {
                return false;
            }
            addLinkMovementMethod(textView);
            textView.setText(valueOf);
            return true;
        } else if (!addLinks((Spannable) text, i)) {
            return false;
        } else {
            addLinkMovementMethod(textView);
            return true;
        }
    }

    private static void applyLink(String str, int i, int i2, Spannable spannable) {
        spannable.setSpan(new URLSpan(str), i, i2, 33);
    }

    private static void gatherLinks(ArrayList<LinkSpec> arrayList, Spannable spannable, Pattern pattern, String[] strArr, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        Matcher matcher = pattern.matcher(spannable);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (matchFilter == null || matchFilter.acceptMatch(spannable, start, end)) {
                LinkSpec linkSpec = new LinkSpec();
                linkSpec.url = makeUrl(matcher.group(0), strArr, matcher, transformFilter);
                linkSpec.start = start;
                linkSpec.end = end;
                arrayList.add(linkSpec);
            }
        }
    }

    private static String makeUrl(String str, String[] strArr, Matcher matcher, Linkify.TransformFilter transformFilter) {
        boolean z;
        if (transformFilter != null) {
            str = transformFilter.transformUrl(matcher, str);
        }
        int i = 0;
        while (true) {
            z = true;
            if (i >= strArr.length) {
                z = false;
                break;
            } else if (!str.regionMatches(true, 0, strArr[i], 0, strArr[i].length())) {
                i++;
            } else if (!str.regionMatches(false, 0, strArr[i], 0, strArr[i].length())) {
                str = strArr[i] + str.substring(strArr[i].length());
            }
        }
        if (z) {
            return str;
        }
        return strArr[0] + str;
    }

    private static void pruneOverlaps(ArrayList<LinkSpec> arrayList, Spannable spannable) {
        int i;
        int i2 = 0;
        Object[] objArr = (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int i3 = 0; i3 < objArr.length; i3++) {
            LinkSpec linkSpec = new LinkSpec();
            linkSpec.span = objArr[i3];
            linkSpec.start = spannable.getSpanStart(objArr[i3]);
            linkSpec.end = spannable.getSpanEnd(objArr[i3]);
            arrayList.add(linkSpec);
        }
        Collections.sort(arrayList, COMPARATOR);
        int size = arrayList.size();
        while (i2 < size - 1) {
            LinkSpec linkSpec2 = arrayList.get(i2);
            int i4 = i2 + 1;
            LinkSpec linkSpec3 = arrayList.get(i4);
            int i5 = linkSpec2.start;
            int i6 = linkSpec3.start;
            if (i5 <= i6 && (i = linkSpec2.end) > i6) {
                int i7 = linkSpec3.end;
                int i8 = (i7 > i && i - i5 <= i7 - i6) ? i - i5 < i7 - i6 ? i2 : -1 : i4;
                if (i8 != -1) {
                    Object obj = arrayList.get(i8).span;
                    if (obj != null) {
                        spannable.removeSpan(obj);
                    }
                    arrayList.remove(i8);
                    size--;
                }
            }
            i2 = i4;
        }
    }
}
