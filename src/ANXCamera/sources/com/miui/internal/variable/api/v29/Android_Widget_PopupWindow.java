package com.miui.internal.variable.api.v29;

import android.view.WindowManager;
import android.widget.PopupWindow;
import com.miui.internal.variable.api.AbstractExtension;

public interface Android_Widget_PopupWindow {

    public static class Extension extends AbstractExtension<Interface> {
        private static final Extension INSTANCE = new Extension();

        public static Extension get() {
            return INSTANCE;
        }
    }

    public interface Interface {
        void invokePopup(PopupWindow popupWindow, WindowManager.LayoutParams layoutParams);
    }
}
