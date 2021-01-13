package com.miui.internal.variable.api.v29;

import android.widget.Spinner;
import com.miui.internal.variable.api.AbstractExtension;

public interface Android_Widget_Spinner {

    public static class Extension extends AbstractExtension<Interface> {
        private static final Extension INSTANCE = new Extension();

        public static Extension get() {
            return INSTANCE;
        }
    }

    public interface Interface {
        void setPrompt(Spinner spinner, CharSequence charSequence);
    }
}
