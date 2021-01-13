package miui.util;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;
import com.miui.internal.variable.Android_View_Window_class;

public class WindowUtils {
    private WindowUtils() {
    }

    public static void changeWindowBackground(Activity activity, float f) {
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.alpha = f;
        activity.getWindow().setAttributes(attributes);
    }

    public static void setTranslucentStatus(Window window, int i) {
        Android_View_Window_class.Factory.getInstance().get().setTranslucentStatus(window, i);
    }
}
