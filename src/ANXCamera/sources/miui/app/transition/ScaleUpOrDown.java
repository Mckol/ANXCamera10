package miui.app.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.ImageView;
import miui.R;
import miui.view.ClipRoundedBounds;
import miui.view.RoundedFrameLayout;

/* access modifiers changed from: package-private */
public class ScaleUpOrDown extends Transition {
    private static final float DEFAULT_BACK_TO_SCREEN_CENTER_SCALE = 0.6f;
    private static final float DEFAULT_ZOOMLESS_SCALE = 1.0f;
    private static final String TAG = "ScaleUpOrDown";
    private FragmentTransitionHelper helper;
    private final int mColor;
    private boolean mIsScaleUp;
    private final Rect mPositionRect;
    private final int mRadius;
    private final Bitmap mThumb;

    public ScaleUpOrDown(Activity activity, FragmentOptions fragmentOptions, boolean z) {
        this.mPositionRect = fragmentOptions.getPositionRect();
        this.mColor = fragmentOptions.getColor();
        this.mRadius = fragmentOptions.getRadius();
        this.mThumb = fragmentOptions.getThumb();
        this.mIsScaleUp = z;
        this.helper = new FragmentTransitionHelper(activity);
        setDuration(350);
        setInterpolator(FragmentTransitionHelper.PHYSIC_BASED_INTERPOLATOR);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void animOverlayViewProgress(RoundedFrameLayout roundedFrameLayout, float f, float f2, int i, int i2, float f3, float f4) {
        roundedFrameLayout.setTranslationX(f);
        roundedFrameLayout.setTranslationY(f2);
        roundedFrameLayout.setClipRoundedBounds(new RectF(0.0f, 0.0f, (float) i, (float) i2), new float[]{f3, f3, f3, f3, f4, f4, f4, f4});
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void animViewProgress(View view, float f, float f2, float f3, float f4) {
        view.setScaleX(f);
        view.setScaleY(f2);
        if (view instanceof ClipRoundedBounds) {
            float f5 = f3 / f;
            float f6 = f3 / f2;
            float f7 = f4 / f;
            float f8 = f4 / f2;
            ((ClipRoundedBounds) view).setClipRoundedBounds(new RectF(0.0f, 0.0f, (float) view.getWidth(), (float) view.getHeight()), new float[]{f5, f6, f5, f6, f7, f8, f7, f8});
        }
    }

    private ValueAnimator createAnimator() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setInterpolator(getInterpolator());
        ofFloat.setDuration(getDuration());
        return ofFloat;
    }

    private RoundedFrameLayout createOverlayView(Context context, View view) {
        RoundedFrameLayout roundedFrameLayout = new RoundedFrameLayout(context);
        roundedFrameLayout.setBackgroundColor(this.mColor);
        roundedFrameLayout.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(view.getHeight(), 1073741824));
        roundedFrameLayout.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        Bitmap bitmap = this.mThumb;
        if (bitmap != null && !bitmap.isRecycled()) {
            ImageView imageView = new ImageView(context);
            imageView.setImageBitmap(this.mThumb);
            imageView.measure(View.MeasureSpec.makeMeasureSpec(this.mPositionRect.width(), 1073741824), View.MeasureSpec.makeMeasureSpec(this.mPositionRect.height(), 1073741824));
            imageView.layout(0, 0, this.mPositionRect.width(), this.mPositionRect.height());
            roundedFrameLayout.addView(imageView);
        }
        return roundedFrameLayout;
    }

    private boolean isScaleDownToCenter(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    private Animator onAppear(ViewGroup viewGroup, final View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        ValueAnimator createAnimator = createAnimator();
        Context context = viewGroup.getContext();
        View findViewById = view.getRootView().findViewById(R.id.fragment_bottom);
        final ViewGroupOverlay viewGroupOverlay = (ViewGroupOverlay) findViewById.getOverlay();
        final RoundedFrameLayout createOverlayView = createOverlayView(context, findViewById);
        final float width = ((float) this.mPositionRect.width()) / ((float) view.getWidth());
        final float height = ((float) this.mPositionRect.height()) / ((float) view.getHeight());
        Rect rect = this.mPositionRect;
        view.setPivotX(((float) rect.left) / (1.0f - width));
        view.setPivotY(((float) rect.top) / (1.0f - height));
        final int topRoundedCorner = this.helper.getTopRoundedCorner();
        final int bottomRoundedCorner = this.helper.getBottomRoundedCorner();
        createAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class miui.app.transition.ScaleUpOrDown.AnonymousClass1 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                float f = ((float) ScaleUpOrDown.this.mRadius) + (((float) (topRoundedCorner - ScaleUpOrDown.this.mRadius)) * animatedFraction);
                float f2 = ((float) ScaleUpOrDown.this.mRadius) + (((float) (bottomRoundedCorner - ScaleUpOrDown.this.mRadius)) * animatedFraction);
                float f3 = width;
                float f4 = f3 + ((1.0f - f3) * animatedFraction);
                float f5 = height;
                float f6 = f5 + ((1.0f - f5) * animatedFraction);
                ScaleUpOrDown.this.animViewProgress(view, f4, f6, f, f2);
                float[] fArr = new float[9];
                view.getMatrix().getValues(fArr);
                ScaleUpOrDown.this.animOverlayViewProgress(createOverlayView, fArr[2], fArr[5], (int) (((float) view.getWidth()) * f4), (int) (((float) view.getHeight()) * f6), f, f2);
            }
        });
        createAnimator.addListener(new AnimatorListenerAdapter() {
            /* class miui.app.transition.ScaleUpOrDown.AnonymousClass2 */

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                viewGroupOverlay.clear();
            }

            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                viewGroupOverlay.add(createOverlayView);
            }
        });
        return createAnimator;
    }

    private Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        final View view2 = transitionValues.view;
        ValueAnimator createAnimator = createAnimator();
        Context context = viewGroup.getContext();
        final boolean isScaleDownToCenter = isScaleDownToCenter(context);
        final ViewGroupOverlay overlay = viewGroup.getOverlay();
        overlay.clear();
        final RoundedFrameLayout createOverlayView = createOverlayView(context, viewGroup);
        final float width = ((float) this.mPositionRect.width()) / ((float) view2.getWidth());
        final float height = ((float) this.mPositionRect.height()) / ((float) view2.getHeight());
        Rect rect = this.mPositionRect;
        float f = ((float) rect.left) / (1.0f - width);
        float f2 = ((float) rect.top) / (1.0f - height);
        if (isScaleDownToCenter) {
            view2.setPivotX((float) (view2.getWidth() / 2));
            view2.setPivotY((float) (view2.getHeight() / 2));
        } else {
            view2.setPivotX(f);
            view2.setPivotY(f2);
            overlay.add(createOverlayView);
        }
        overlay.add(view2);
        final int topRoundedCorner = this.helper.getTopRoundedCorner();
        final int bottomRoundedCorner = this.helper.getBottomRoundedCorner();
        if (view2.getScaleX() < 1.0f || view2.getScaleY() < 1.0f) {
            view2.setLeft(0);
            view2.setTop(0);
            view2.setRight(viewGroup.getRight());
            view2.setBottom(viewGroup.getBottom());
        }
        createAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class miui.app.transition.ScaleUpOrDown.AnonymousClass3 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                if (isScaleDownToCenter) {
                    float f = (animatedFraction * -0.39999998f) + 1.0f;
                    ScaleUpOrDown.this.animViewProgress(view2, f, f, (float) topRoundedCorner, (float) bottomRoundedCorner);
                    return;
                }
                float f2 = ((float) topRoundedCorner) + (((float) (ScaleUpOrDown.this.mRadius - topRoundedCorner)) * animatedFraction);
                float f3 = ((float) bottomRoundedCorner) + (((float) (ScaleUpOrDown.this.mRadius - bottomRoundedCorner)) * animatedFraction);
                float f4 = ((width - 1.0f) * animatedFraction) + 1.0f;
                float f5 = ((height - 1.0f) * animatedFraction) + 1.0f;
                ScaleUpOrDown.this.animViewProgress(view2, f4, f5, f2, f3);
                float[] fArr = new float[9];
                view2.getMatrix().getValues(fArr);
                ScaleUpOrDown.this.animOverlayViewProgress(createOverlayView, fArr[2], fArr[5], (int) (((float) view2.getWidth()) * f4), (int) (((float) view2.getHeight()) * f5), f2, f3);
            }
        });
        createAnimator.addListener(new AnimatorListenerAdapter() {
            /* class miui.app.transition.ScaleUpOrDown.AnonymousClass4 */

            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                overlay.remove(createOverlayView);
            }

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                overlay.remove(createOverlayView);
            }
        });
        return createAnimator;
    }

    public void captureEndValues(TransitionValues transitionValues) {
        Log.d(TAG, "not used method:captureEndValues");
    }

    public void captureStartValues(TransitionValues transitionValues) {
        Log.d(TAG, "not used method:captureStartValues");
    }

    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return this.mIsScaleUp ? onAppear(viewGroup, transitionValues2.view, transitionValues, transitionValues2) : onDisappear(viewGroup, transitionValues.view, transitionValues, transitionValues2);
    }

    public boolean isTransitionRequired(TransitionValues transitionValues, TransitionValues transitionValues2) {
        return true;
    }
}
