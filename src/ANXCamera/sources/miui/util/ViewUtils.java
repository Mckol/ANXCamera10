package miui.util;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import com.miui.internal.variable.Android_View_ViewGroup_class;
import com.miui.internal.variable.Android_View_View_class;

public class ViewUtils {
    static final String TAG = "ViewUtils";
    private static final Android_View_View_class ViewClass = Android_View_View_class.Factory.getInstance().get();
    private static final Android_View_ViewGroup_class ViewGroupClass = Android_View_ViewGroup_class.Factory.getInstance().get();

    private ViewUtils() {
    }

    public static boolean containsPoint(View view, int i, int i2) {
        return i > view.getLeft() && i < view.getRight() && i2 > view.getTop() && i2 < view.getBottom();
    }

    public static int getBackgroundHeight(View view) {
        Drawable background = view.getBackground();
        if (background != null) {
            return background.getIntrinsicHeight();
        }
        return -1;
    }

    public static int getBackgroundWidth(View view) {
        Drawable background = view.getBackground();
        if (background != null) {
            return background.getIntrinsicWidth();
        }
        return -1;
    }

    public static void getContentRect(View view, Rect rect) {
        rect.left = view.getScrollX() + view.getPaddingLeft();
        rect.top = view.getScrollY() + view.getPaddingTop();
        rect.right = (view.getWidth() - view.getPaddingRight()) - rect.left;
        rect.bottom = (view.getHeight() - view.getPaddingBottom()) - rect.top;
    }

    public static int getHorizontalState(View view) {
        return ViewClass.getHorizontalState(view);
    }

    public static boolean getTagChildrenSequenceState(ViewGroup viewGroup) {
        return ViewGroupClass.getTagChildrenSequenceState(viewGroup);
    }

    public static int getVerticalState(View view) {
        return ViewClass.getVerticalState(view);
    }

    public static boolean intersectsWith(View view, Rect rect) {
        return rect != null && view.getLeft() < rect.right && view.getTop() < rect.bottom && view.getRight() > rect.left && view.getBottom() > rect.top;
    }

    public static boolean isLayoutRtl(View view) {
        return view.getLayoutDirection() == 1;
    }

    public static boolean isNightMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static void layoutChildView(ViewGroup viewGroup, View view, int i, int i2, int i3, int i4) {
        boolean isLayoutRtl = isLayoutRtl(viewGroup);
        int width = viewGroup.getWidth();
        int i5 = isLayoutRtl ? width - i3 : i;
        if (isLayoutRtl) {
            i3 = width - i;
        }
        view.layout(i5, i2, i3, i4);
    }

    public static void setHorizontalState(View view, int i) {
        ViewClass.setHorizontalState(view, i);
    }

    public static void setTagChildrenSequenceState(ViewGroup viewGroup, boolean z) {
        ViewGroupClass.setTagChildrenSequenceState(viewGroup, z);
    }

    public static void setVerticalState(View view, int i) {
        ViewClass.setVerticalState(view, i);
    }
}
