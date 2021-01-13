package miui.animation.property;

import android.os.Build;
import android.view.View;
import miui.animation.utils.CommonUtils;

public abstract class ViewProperty extends FloatProperty<View> {
    public static final ViewProperty ALPHA = new ViewProperty("alpha") {
        /* class miui.animation.property.ViewProperty.AnonymousClass14 */

        public float getValue(View view) {
            return view.getAlpha();
        }

        public void setValue(View view, float f) {
            view.setAlpha(f);
        }
    };
    public static final ViewProperty AUTO_ALPHA = new ViewProperty("autoAlpha") {
        /* class miui.animation.property.ViewProperty.AnonymousClass15 */

        public float getValue(View view) {
            return view.getAlpha();
        }

        public void setValue(View view, float f) {
            view.setAlpha(f);
            boolean z = Math.abs(f) <= 0.00390625f;
            if (view.getVisibility() != 0 && f > 0.0f && !z) {
                view.setVisibility(0);
            } else if (z) {
                view.setVisibility(8);
            }
        }
    };
    public static final ViewProperty BACKGROUND = new ViewProperty("deprecated_background") {
        /* class miui.animation.property.ViewProperty.AnonymousClass20 */

        public float getValue(View view) {
            return 0.0f;
        }

        public void setValue(View view, float f) {
        }
    };
    public static final ViewProperty FOREGROUND = new ViewProperty("deprecated_foreground") {
        /* class miui.animation.property.ViewProperty.AnonymousClass19 */

        public float getValue(View view) {
            return 0.0f;
        }

        public void setValue(View view, float f) {
        }
    };
    public static final ViewProperty HEIGHT = new ViewProperty("height") {
        /* class miui.animation.property.ViewProperty.AnonymousClass12 */

        public float getValue(View view) {
            int height = view.getHeight();
            Float f = (Float) view.getTag(CommonUtils.KEY_FOLME_SET_HEIGHT);
            if (f != null) {
                return f.floatValue();
            }
            if (height == 0 && ViewProperty.isInInitLayout(view)) {
                height = view.getMeasuredHeight();
            }
            return (float) height;
        }

        public void setValue(View view, float f) {
            view.getLayoutParams().height = (int) f;
            view.setTag(CommonUtils.KEY_FOLME_SET_HEIGHT, Float.valueOf(f));
            view.requestLayout();
        }
    };
    public static final ViewProperty ROTATION = new ViewProperty("rotation") {
        /* class miui.animation.property.ViewProperty.AnonymousClass6 */

        public float getValue(View view) {
            return view.getRotation();
        }

        public void setValue(View view, float f) {
            view.setRotation(f);
        }
    };
    public static final ViewProperty ROTATION_X = new ViewProperty("rotationX") {
        /* class miui.animation.property.ViewProperty.AnonymousClass7 */

        public float getValue(View view) {
            return view.getRotationX();
        }

        public void setValue(View view, float f) {
            view.setRotationX(f);
        }
    };
    public static final ViewProperty ROTATION_Y = new ViewProperty("rotationY") {
        /* class miui.animation.property.ViewProperty.AnonymousClass8 */

        public float getValue(View view) {
            return view.getRotationY();
        }

        public void setValue(View view, float f) {
            view.setRotationY(f);
        }
    };
    public static final ViewProperty SCALE_X = new ViewProperty("scaleX") {
        /* class miui.animation.property.ViewProperty.AnonymousClass4 */

        public float getValue(View view) {
            return view.getScaleX();
        }

        public void setValue(View view, float f) {
            view.setScaleX(f);
        }
    };
    public static final ViewProperty SCALE_Y = new ViewProperty("scaleY") {
        /* class miui.animation.property.ViewProperty.AnonymousClass5 */

        public float getValue(View view) {
            return view.getScaleY();
        }

        public void setValue(View view, float f) {
            view.setScaleY(f);
        }
    };
    public static final ViewProperty SCROLL_X = new ViewProperty("scrollX") {
        /* class miui.animation.property.ViewProperty.AnonymousClass16 */

        public float getValue(View view) {
            return (float) view.getScrollX();
        }

        public void setValue(View view, float f) {
            view.setScrollX((int) f);
        }
    };
    public static final ViewProperty SCROLL_Y = new ViewProperty("scrollY") {
        /* class miui.animation.property.ViewProperty.AnonymousClass18 */

        public float getValue(View view) {
            return (float) view.getScrollY();
        }

        public void setValue(View view, float f) {
            view.setScrollY((int) f);
        }
    };
    public static final ViewProperty TRANSLATION_X = new ViewProperty("translationX") {
        /* class miui.animation.property.ViewProperty.AnonymousClass1 */

        public float getValue(View view) {
            return view.getTranslationX();
        }

        public void setValue(View view, float f) {
            view.setTranslationX(f);
        }
    };
    public static final ViewProperty TRANSLATION_Y = new ViewProperty("translationY") {
        /* class miui.animation.property.ViewProperty.AnonymousClass2 */

        public float getValue(View view) {
            return view.getTranslationY();
        }

        public void setValue(View view, float f) {
            view.setTranslationY(f);
        }
    };
    public static final ViewProperty TRANSLATION_Z = new ViewProperty("translationZ") {
        /* class miui.animation.property.ViewProperty.AnonymousClass3 */

        public float getValue(View view) {
            if (Build.VERSION.SDK_INT >= 21) {
                return view.getTranslationZ();
            }
            return 0.0f;
        }

        public void setValue(View view, float f) {
            if (Build.VERSION.SDK_INT >= 21) {
                view.setTranslationZ(f);
            }
        }
    };
    public static final ViewProperty VISIBILITY = new ViewProperty("visibility") {
        /* class miui.animation.property.ViewProperty.AnonymousClass17 */

        public float getValue(View view) {
            return (float) view.getVisibility();
        }

        public void setValue(View view, float f) {
            view.setVisibility((int) f);
        }
    };
    public static final ViewProperty WIDTH = new ViewProperty("width") {
        /* class miui.animation.property.ViewProperty.AnonymousClass13 */

        public float getValue(View view) {
            int width = view.getWidth();
            Float f = (Float) view.getTag(CommonUtils.KEY_FOLME_SET_WIDTH);
            if (f != null) {
                return f.floatValue();
            }
            if (width == 0 && ViewProperty.isInInitLayout(view)) {
                width = view.getMeasuredWidth();
            }
            return (float) width;
        }

        public void setValue(View view, float f) {
            view.getLayoutParams().width = (int) f;
            view.setTag(CommonUtils.KEY_FOLME_SET_WIDTH, Float.valueOf(f));
            view.requestLayout();
        }
    };
    public static final ViewProperty X = new ViewProperty("x") {
        /* class miui.animation.property.ViewProperty.AnonymousClass9 */

        public float getValue(View view) {
            return view.getX();
        }

        public void setValue(View view, float f) {
            view.setX(f);
        }
    };
    public static final ViewProperty Y = new ViewProperty("y") {
        /* class miui.animation.property.ViewProperty.AnonymousClass10 */

        public float getValue(View view) {
            return view.getY();
        }

        public void setValue(View view, float f) {
            view.setY(f);
        }
    };
    public static final ViewProperty Z = new ViewProperty("z") {
        /* class miui.animation.property.ViewProperty.AnonymousClass11 */

        public float getValue(View view) {
            if (Build.VERSION.SDK_INT >= 21) {
                return view.getZ();
            }
            return 0.0f;
        }

        public void setValue(View view, float f) {
            if (Build.VERSION.SDK_INT >= 21) {
                view.setZ(f);
            }
        }
    };

    public ViewProperty(String str) {
        super(str);
    }

    /* access modifiers changed from: private */
    public static boolean isInInitLayout(View view) {
        return view.getTag(CommonUtils.KEY_FOLME_INIT_LAYOUT) != null;
    }

    @Override // miui.animation.property.FloatProperty
    public String toString() {
        return "ViewProperty{mPropertyName='" + this.mPropertyName + '\'' + '}';
    }
}