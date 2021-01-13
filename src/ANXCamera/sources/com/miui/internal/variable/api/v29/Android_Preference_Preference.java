package com.miui.internal.variable.api.v29;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import com.miui.internal.variable.api.AbstractExtension;

public interface Android_Preference_Preference {

    public static class Extension extends AbstractExtension<Interface> {
        private static final Extension INSTANCE = new Extension();

        public static Extension get() {
            return INSTANCE;
        }
    }

    public interface Interface {
        void init(Preference preference, Context context, AttributeSet attributeSet, int i);

        void onBindView(Preference preference, View view);
    }
}
