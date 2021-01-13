package com.miui.internal.variable.api.v29;

import android.text.Spannable;
import android.text.util.Linkify;
import com.miui.internal.variable.api.AbstractExtension;
import java.util.ArrayList;
import java.util.regex.Pattern;

public interface Android_Text_Util_Linkify {

    public static class Extension extends AbstractExtension<Interface> {
        private static final Extension INSTANCE = new Extension();

        public static Extension get() {
            return INSTANCE;
        }
    }

    public interface Interface {
        boolean addLinks(Linkify linkify, Spannable spannable, int i);

        void gatherLinks(Linkify linkify, ArrayList arrayList, Spannable spannable, Pattern pattern, String[] strArr, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter);
    }
}
