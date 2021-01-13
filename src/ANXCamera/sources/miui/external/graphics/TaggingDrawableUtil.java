package miui.external.graphics;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import miui.R;

public class TaggingDrawableUtil {
    private static final int[] STATES_TAGS = {R.attr.state_single_v, R.attr.state_first_v, R.attr.state_middle_v, R.attr.state_last_v};
    public static final int[] STATE_SET_FIRST = {R.attr.state_first_v};
    public static final int[] STATE_SET_LAST = {R.attr.state_last_v};
    public static final int[] STATE_SET_MIDDLE = {R.attr.state_middle_v};
    public static final int[] STATE_SET_SINGLE = {R.attr.state_single_v};

    public static void updateBackgroundState(View view, int i, int i2) {
        if (view != null && i2 != 0) {
            Drawable background = view.getBackground();
            if ((background instanceof StateListDrawable) && TaggingDrawable.containsTagState((StateListDrawable) background, STATES_TAGS)) {
                TaggingDrawable taggingDrawable = new TaggingDrawable(background);
                view.setBackground(taggingDrawable);
                background = taggingDrawable;
            }
            if (background instanceof TaggingDrawable) {
                ((TaggingDrawable) background).setTaggingState(i2 == 1 ? STATE_SET_SINGLE : i == 0 ? STATE_SET_FIRST : i == i2 - 1 ? STATE_SET_LAST : STATE_SET_MIDDLE);
            }
        }
    }

    public static void updateBackgroundState(View view, int[] iArr) {
        if (view != null) {
            Drawable background = view.getBackground();
            if ((background instanceof StateListDrawable) && TaggingDrawable.containsTagState((StateListDrawable) background, STATES_TAGS)) {
                TaggingDrawable taggingDrawable = new TaggingDrawable(background);
                view.setBackground(taggingDrawable);
                background = taggingDrawable;
            }
            if (background instanceof TaggingDrawable) {
                ((TaggingDrawable) background).setTaggingState(iArr);
            }
        }
    }

    public static void updateItemBackground(View view, int i, int i2) {
        updateBackgroundState(view, i, i2);
        updateItemPadding(view, i, i2);
    }

    public static void updateItemPadding(View view, int i, int i2) {
        int i3;
        int i4;
        if (view != null && i2 != 0) {
            int paddingStart = view.getPaddingStart();
            view.getPaddingTop();
            int paddingEnd = view.getPaddingEnd();
            view.getPaddingBottom();
            Context applicationContext = view.getContext().getApplicationContext();
            if (i2 == 1) {
                i4 = applicationContext.getResources().getDimensionPixelSize(miuix.compat.R.dimen.miuix_compat_drop_down_menu_padding_small);
                i3 = applicationContext.getResources().getDimensionPixelSize(miuix.compat.R.dimen.miuix_compat_drop_down_menu_padding_small);
            } else if (i == 0) {
                i4 = applicationContext.getResources().getDimensionPixelSize(miuix.compat.R.dimen.miuix_compat_drop_down_menu_padding_large);
                i3 = applicationContext.getResources().getDimensionPixelSize(miuix.compat.R.dimen.miuix_compat_drop_down_menu_padding_small);
            } else if (i == i2 - 1) {
                i4 = applicationContext.getResources().getDimensionPixelSize(miuix.compat.R.dimen.miuix_compat_drop_down_menu_padding_small);
                i3 = applicationContext.getResources().getDimensionPixelSize(miuix.compat.R.dimen.miuix_compat_drop_down_menu_padding_large);
            } else {
                i4 = applicationContext.getResources().getDimensionPixelSize(miuix.compat.R.dimen.miuix_compat_drop_down_menu_padding_small);
                i3 = applicationContext.getResources().getDimensionPixelSize(miuix.compat.R.dimen.miuix_compat_drop_down_menu_padding_small);
            }
            view.setPaddingRelative(paddingStart, i4, paddingEnd, i3);
        }
    }
}
