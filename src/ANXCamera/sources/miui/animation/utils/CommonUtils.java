package miui.animation.utils;

import android.animation.ArgbEvaluator;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.Map;
import miui.animation.IAnimTarget;
import miui.animation.utils.EaseManager;

public class CommonUtils {
    private static final Class<?>[] BUILT_IN = {String.class, Integer.TYPE, Integer.class, Long.TYPE, Long.class, Short.TYPE, Short.class, Float.TYPE, Float.class, Double.TYPE, Double.class};
    public static final int KEY_BASE = 269156352;
    public static final int KEY_FOLME_FORGROUND_COLOR = 269156355;
    public static final int KEY_FOLME_INIT_LAYOUT = 269156356;
    public static final int KEY_FOLME_IN_DRAGGING = 269156357;
    public static final int KEY_FOLME_LISTVIEW_POS = 269156353;
    public static final int KEY_FOLME_LISTVIEW_TOUCH_LISTENER = 269156354;
    public static final int KEY_FOLME_SET_HEIGHT = 269156359;
    public static final int KEY_FOLME_SET_WIDTH = 269156358;
    public static final String TAG = "miuisdk_anim";
    public static final int UNIT_SECOND = 1000;
    public static final ArgbEvaluator sArgbEvaluator = new ArgbEvaluator();
    public static final EaseManager.EaseStyle sDefEase = EaseManager.getStyle(-2, 0.85f, 0.3f);
    private static float sTouchSlop;

    private static class OnPreDrawTask implements ViewTreeObserver.OnPreDrawListener {
        Runnable mTask;
        WeakReference<View> mView;

        OnPreDrawTask(Runnable runnable) {
            this.mTask = runnable;
        }

        public boolean onPreDraw() {
            View view = this.mView.get();
            if (view != null) {
                Runnable runnable = this.mTask;
                if (runnable != null) {
                    runnable.run();
                }
                view.getViewTreeObserver().removeOnPreDrawListener(this);
            }
            this.mTask = null;
            return true;
        }

        public void start(View view) {
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            this.mView = new WeakReference<>(view);
            viewTreeObserver.addOnPreDrawListener(this);
        }
    }

    private CommonUtils() {
    }

    public static void getRect(IAnimTarget iAnimTarget, RectF rectF) {
        rectF.left = iAnimTarget.getValue(0);
        rectF.top = iAnimTarget.getValue(1);
        rectF.right = rectF.left + iAnimTarget.getValue(6);
        rectF.bottom = rectF.top + iAnimTarget.getValue(5);
    }

    public static float getSize(IAnimTarget iAnimTarget, int i) {
        if (i == 0) {
            i = 6;
        } else if (i == 1) {
            i = 5;
        } else if (!(i == 6 || i == 5)) {
            i = -1;
        }
        if (i == -1) {
            return 0.0f;
        }
        return iAnimTarget.getValue(i);
    }

    public static float getTouchSlop(View view) {
        if (sTouchSlop == 0.0f) {
            sTouchSlop = (float) ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        }
        return sTouchSlop;
    }

    public static boolean hasFlags(long j, long j2) {
        return (j & j2) != 0;
    }

    public static <T> boolean inArray(T[] tArr, T t) {
        if (!(t == null || tArr == null || tArr.length <= 0)) {
            for (T t2 : tArr) {
                if (t2.equals(t)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <T> boolean isArrayEmpty(T[] tArr) {
        return tArr == null || tArr.length == 0;
    }

    public static boolean isBuiltInClass(Class<?> cls) {
        return inArray(BUILT_IN, cls);
    }

    public static <K, V> StringBuilder mapToString(Map<K, V> map, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        if (map != null && map.size() > 0) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                sb.append('\n');
                sb.append(str);
                sb.append((Object) entry.getKey());
                sb.append('=');
                sb.append((Object) entry.getValue());
            }
            sb.append('\n');
        }
        sb.append('}');
        return sb;
    }

    public static String mapsToString(Map[] mapArr) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < mapArr.length; i++) {
            sb.append('\n');
            sb.append(i);
            sb.append('.');
            sb.append((CharSequence) mapToString(mapArr[i], "    "));
        }
        sb.append('\n');
        sb.append(']');
        return sb.toString();
    }

    @SafeVarargs
    public static <T> T[] mergeArray(T[] tArr, T... tArr2) {
        if (tArr == null) {
            return tArr2;
        }
        if (tArr2 == null) {
            return tArr;
        }
        Object newInstance = Array.newInstance(tArr.getClass().getComponentType(), tArr.length + tArr2.length);
        System.arraycopy(tArr, 0, newInstance, 0, tArr.length);
        System.arraycopy(tArr2, 0, newInstance, tArr.length, tArr2.length);
        return (T[]) ((Object[]) newInstance);
    }

    public static void runOnPreDraw(View view, Runnable runnable) {
        if (view != null) {
            new OnPreDrawTask(runnable).start(view);
        }
    }

    public static float toFloatValue(Object obj) {
        if (obj instanceof Integer) {
            return ((Integer) obj).floatValue();
        }
        if (obj instanceof Float) {
            return ((Float) obj).floatValue();
        }
        throw new IllegalArgumentException("toFloat failed, value is " + obj);
    }

    public static int[] toIntArray(float[] fArr) {
        int[] iArr = new int[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            iArr[i] = (int) fArr[i];
        }
        return iArr;
    }

    public static int toIntValue(Object obj) {
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        if (obj instanceof Float) {
            return ((Float) obj).intValue();
        }
        throw new IllegalArgumentException("toFloat failed, value is " + obj);
    }
}
