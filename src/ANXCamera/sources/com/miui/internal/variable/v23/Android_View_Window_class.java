package com.miui.internal.variable.v23;

import android.view.View;
import android.view.Window;

public class Android_View_Window_class extends com.miui.internal.variable.v19.Android_View_Window_class {
    private static final int FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS = Integer.MIN_VALUE;
    private static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 8192;

    @Override // com.miui.internal.variable.v16.Android_View_Window_class, com.miui.internal.variable.v19.Android_View_Window_class, com.miui.internal.variable.Android_View_Window_class
    public boolean setTranslucentStatus(Window window, int i) {
        if (i == 0) {
            window.clearFlags(Integer.MIN_VALUE);
        } else {
            window.addFlags(Integer.MIN_VALUE);
            View decorView = window.getDecorView();
            if (i == 1) {
                decorView.setSystemUiVisibility(8192);
            } else {
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & -8193);
            }
        }
        return super.setTranslucentStatus(window, i);
    }
}
