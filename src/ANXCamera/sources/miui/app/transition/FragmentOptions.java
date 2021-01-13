package miui.app.transition;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.transition.Transition;
import miui.app.Fragment;

public class FragmentOptions {
    private static final String KEY_LAUNCH_BOUNDS = "miui:fragment.launchBounds";
    private static final String KEY_LAUNCH_COLOR = "miui:fragment.launchColor";
    private static final String KEY_LAUNCH_RADIUS = "miui:fragment.launchRadius";
    private static final String KEY_LAUNCH_THUMB = "miui:fragment.launchThumb";
    private int mColor;
    private Rect mPositionRect;
    private int mRadius;
    private Bitmap mThumb;
    private RetainedFragment mWorkFragment;

    /* renamed from: miui.app.transition.FragmentOptions$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$miui$app$transition$FragmentOptions$Type = new int[Type.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            $SwitchMap$miui$app$transition$FragmentOptions$Type[Type.SCALE_UP.ordinal()] = 1;
            $SwitchMap$miui$app$transition$FragmentOptions$Type[Type.SCALE_DOWN.ordinal()] = 2;
            $SwitchMap$miui$app$transition$FragmentOptions$Type[Type.PHYSIC_FADE.ordinal()] = 3;
            try {
                $SwitchMap$miui$app$transition$FragmentOptions$Type[Type.EXIT_FADE.ordinal()] = 4;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public static class RetainedFragment extends Fragment {
        private Bitmap mBitmap;

        /* access modifiers changed from: package-private */
        public Bitmap getBitmap() {
            return this.mBitmap;
        }

        @Override // miui.app.Fragment
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            setRetainInstance(true);
        }

        @Override // miui.app.Fragment
        public void onDestroy() {
            super.onDestroy();
            Bitmap bitmap = this.mBitmap;
            if (bitmap != null && !bitmap.isRecycled()) {
                this.mBitmap.recycle();
                this.mBitmap = null;
            }
        }

        /* access modifiers changed from: package-private */
        public void setThumb(Bitmap bitmap) {
            this.mBitmap = bitmap;
        }
    }

    public enum Type {
        SCALE_UP,
        SCALE_DOWN,
        EXIT_FADE,
        PHYSIC_FADE
    }

    private FragmentOptions() {
    }

    private FragmentOptions(Activity activity, Rect rect, Bitmap bitmap, int i, int i2) {
        this.mPositionRect = rect;
        this.mColor = i;
        this.mRadius = i2;
        if (this.mWorkFragment == null) {
            FragmentManager fragmentManager = activity.getFragmentManager();
            this.mWorkFragment = (RetainedFragment) fragmentManager.findFragmentByTag(KEY_LAUNCH_THUMB);
            if (this.mWorkFragment == null) {
                this.mWorkFragment = new RetainedFragment();
                fragmentManager.beginTransaction().add(this.mWorkFragment, KEY_LAUNCH_THUMB).commit();
            }
        }
        this.mWorkFragment.setThumb(bitmap);
    }

    private FragmentOptions(Activity activity, Bundle bundle) {
        this.mPositionRect = (Rect) bundle.getParcelable(KEY_LAUNCH_BOUNDS);
        this.mColor = bundle.getInt(KEY_LAUNCH_COLOR);
        this.mRadius = bundle.getInt(KEY_LAUNCH_RADIUS);
        if (this.mWorkFragment == null) {
            this.mWorkFragment = (RetainedFragment) activity.getFragmentManager().findFragmentByTag(KEY_LAUNCH_THUMB);
        }
        RetainedFragment retainedFragment = this.mWorkFragment;
        if (retainedFragment != null) {
            this.mThumb = retainedFragment.getBitmap();
        }
    }

    private static FragmentOptions fromBundle(Activity activity, Bundle bundle) {
        if (bundle != null) {
            return new FragmentOptions(activity, bundle);
        }
        return null;
    }

    public static Transition newFragmentTransition(Activity activity, Bundle bundle, Type type) {
        int i = AnonymousClass1.$SwitchMap$miui$app$transition$FragmentOptions$Type[type.ordinal()];
        if (i == 1) {
            return newScaleTransition(activity, bundle, true);
        }
        if (i == 2) {
            return newScaleTransition(activity, bundle, false);
        }
        throw new IllegalStateException("Cannot create transition: type " + type);
    }

    public static Transition newFragmentTransition(Type type) {
        int i = AnonymousClass1.$SwitchMap$miui$app$transition$FragmentOptions$Type[type.ordinal()];
        if (i == 3) {
            return new PhysicalFade();
        }
        if (i == 4) {
            return new ExitFade();
        }
        throw new IllegalStateException("Cannot create transition: type " + type);
    }

    private static Transition newScaleTransition(Activity activity, Bundle bundle, boolean z) {
        FragmentOptions fromBundle = fromBundle(activity, bundle);
        if (fromBundle != null) {
            return new ScaleUpOrDown(activity, fromBundle, z);
        }
        return null;
    }

    public static Bundle newTransitionBundle(Activity activity, Rect rect, Bitmap bitmap, int i, int i2) {
        return new FragmentOptions(activity, rect, bitmap, i, i2).toBundle();
    }

    private Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_LAUNCH_BOUNDS, this.mPositionRect);
        bundle.putInt(KEY_LAUNCH_COLOR, this.mColor);
        bundle.putInt(KEY_LAUNCH_RADIUS, this.mRadius);
        return bundle;
    }

    /* access modifiers changed from: package-private */
    public int getColor() {
        return this.mColor;
    }

    /* access modifiers changed from: package-private */
    public Rect getPositionRect() {
        return this.mPositionRect;
    }

    /* access modifiers changed from: package-private */
    public int getRadius() {
        return this.mRadius;
    }

    /* access modifiers changed from: package-private */
    public Bitmap getThumb() {
        return this.mThumb;
    }
}
