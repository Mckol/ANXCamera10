package miuix.animation.property;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import miuix.animation.R;

public class ViewPropertyExt {
    public static final BackgroundProperty BACKGROUND = new BackgroundProperty();
    public static final ForegroundProperty FOREGROUND = new ForegroundProperty();

    public static class BackgroundProperty extends ViewProperty implements IIntValueProperty<View> {
        private BackgroundProperty() {
            super("background");
        }

        public int getIntValue(View view) {
            Drawable background = view.getBackground();
            if (background instanceof ColorDrawable) {
                return ((ColorDrawable) background).getColor();
            }
            return 0;
        }

        public float getValue(View view) {
            return 0.0f;
        }

        public void setIntValue(View view, int i) {
            view.setBackgroundColor(i);
        }

        public void setValue(View view, float f) {
        }
    }

    public static class ForegroundProperty extends ViewProperty implements IIntValueProperty<View> {
        private ForegroundProperty() {
            super("foreground");
        }

        public int getIntValue(View view) {
            Object tag = view.getTag(R.id.miuix_animation_tag_foreground_color);
            if (tag instanceof Integer) {
                return ((Integer) tag).intValue();
            }
            return 0;
        }

        public float getValue(View view) {
            return 0.0f;
        }

        public void setIntValue(View view, int i) {
            Drawable foreground;
            view.setTag(R.id.miuix_animation_tag_foreground_color, Integer.valueOf(i));
            if (Build.VERSION.SDK_INT >= 23 && (foreground = view.getForeground()) != null) {
                foreground.invalidateSelf();
            }
        }

        public void setValue(View view, float f) {
        }
    }

    private ViewPropertyExt() {
    }
}
