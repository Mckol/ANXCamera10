package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EdgeEffect;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.recyclerview.R;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RemixRecyclerView;
import java.lang.reflect.Field;
import miuix.os.Build;
import miuix.spring.view.SpringHelper;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

public abstract class SpringRecyclerView extends RemixRecyclerView {
    private static final Field NESTED_SCROLL_HELPER = RecyclerView.class.getDeclaredField("mScrollingChildHelper");
    private static final RecyclerView.EdgeEffectFactory NON_EFFECT_FACTORY = new NonEdgeEffectFactory();
    private static final String TAG = "SpringRecyclerView";
    private static final Field VIEW_FLINGER = RecyclerView.class.getDeclaredField("mViewFlinger");
    private boolean mHorizontalOverScrolling;
    private int mManagedScrollState;
    private SpringFlinger mSpringFlinger;
    private SpringHelper mSpringHelper;
    private SpringNestedScrollingHelper mSpringNestedScrollingHelper;
    private boolean mVerticalOverScrolling;

    private static class NonEdgeEffect extends EdgeEffect {
        NonEdgeEffect(Context context) {
            super(context);
        }

        public boolean draw(Canvas canvas) {
            return false;
        }

        public void finish() {
        }

        @Nullable
        public BlendMode getBlendMode() {
            return null;
        }

        public int getColor() {
            return 0;
        }

        public int getMaxHeight() {
            return 0;
        }

        public boolean isFinished() {
            return true;
        }

        public void onAbsorb(int i) {
        }

        public void onPull(float f) {
        }

        public void onPull(float f, float f2) {
        }

        public void onRelease() {
        }

        public void setBlendMode(@Nullable BlendMode blendMode) {
        }

        public void setColor(int i) {
        }

        public void setSize(int i, int i2) {
        }
    }

    private static class NonEdgeEffectFactory extends RecyclerView.EdgeEffectFactory {
        private NonEdgeEffectFactory() {
        }

        /* access modifiers changed from: protected */
        @Override // androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory
        @NonNull
        public EdgeEffect createEdgeEffect(@NonNull RecyclerView recyclerView, int i) {
            return new NonEdgeEffect(recyclerView.getContext());
        }
    }

    /* access modifiers changed from: private */
    public class SpringFlinger extends RemixRecyclerView.ViewFlinger {
        private SpringFlinger() {
            super();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ViewFlinger, androidx.recyclerview.widget.RemixRecyclerView.ViewFlinger
        public void fling(int i, int i2) {
            int horizontalDistance = SpringRecyclerView.this.mSpringHelper.getHorizontalDistance();
            int verticalDistance = SpringRecyclerView.this.mSpringHelper.getVerticalDistance();
            if (!SpringRecyclerView.this.springAvailable() || (horizontalDistance == 0 && verticalDistance == 0)) {
                super.fling(i, i2);
            } else {
                overFling(i, i2, horizontalDistance, verticalDistance);
            }
        }

        /* access modifiers changed from: package-private */
        public void notifyHorizontalEdgeReached(int i) {
            SpringRecyclerView.this.mHorizontalOverScrolling = true;
            SpringRecyclerView.this.setScrollState(2);
            resetFlingPosition();
            ((RemixRecyclerView.ViewFlinger) this).mOverScroller.notifyHorizontalEdgeReached(0, -i, SpringRecyclerView.this.getWidth());
        }

        /* access modifiers changed from: package-private */
        public void notifyVerticalEdgeReached(int i) {
            SpringRecyclerView.this.mVerticalOverScrolling = true;
            SpringRecyclerView.this.setScrollState(2);
            resetFlingPosition();
            ((RemixRecyclerView.ViewFlinger) this).mOverScroller.notifyVerticalEdgeReached(0, -i, SpringRecyclerView.this.getHeight());
        }

        /* access modifiers changed from: package-private */
        public void overFling(int i, int i2, int i3, int i4) {
            int i5;
            int i6;
            int i7;
            int i8;
            boolean z = true;
            SpringRecyclerView.this.mHorizontalOverScrolling = i3 != 0;
            SpringRecyclerView springRecyclerView = SpringRecyclerView.this;
            if (i4 == 0) {
                z = false;
            }
            springRecyclerView.mVerticalOverScrolling = z;
            SpringRecyclerView.this.setScrollState(2);
            resetFlingPosition();
            int i9 = Integer.MIN_VALUE;
            int i10 = Integer.MAX_VALUE;
            if (Integer.signum(i) * i3 > 0) {
                i6 = -i3;
                i5 = i6;
            } else if (i < 0) {
                i5 = -i3;
                i6 = Integer.MIN_VALUE;
            } else {
                i6 = -i3;
                i5 = Integer.MAX_VALUE;
            }
            if (Integer.signum(i2) * i4 > 0) {
                i8 = -i4;
                i7 = i8;
            } else {
                if (i2 < 0) {
                    i10 = -i4;
                } else {
                    i9 = -i4;
                }
                i8 = i9;
                i7 = i10;
            }
            ((RemixRecyclerView.ViewFlinger) this).mOverScroller.fling(0, 0, i, i2, i6, i5, i8, i7, SpringRecyclerView.this.getWidth(), SpringRecyclerView.this.getHeight());
            postOnAnimation();
        }

        /* access modifiers changed from: package-private */
        public void springBack(int i, int i2) {
            if (i != 0) {
                SpringRecyclerView.this.mHorizontalOverScrolling = true;
            }
            if (i2 != 0) {
                SpringRecyclerView.this.mVerticalOverScrolling = true;
            }
            SpringRecyclerView.this.setScrollState(2);
            resetFlingPosition();
            int i3 = -i;
            int i4 = -i2;
            ((RemixRecyclerView.ViewFlinger) this).mOverScroller.springBack(0, 0, i3, i3, i4, i4);
            postOnAnimation();
        }
    }

    /* access modifiers changed from: private */
    public class SpringNestedScrollingHelper extends NestedScrollingChildHelper {
        SpringNestedScrollingHelper(@NonNull View view) {
            super(view);
        }

        @Override // androidx.core.view.NestedScrollingChildHelper
        public boolean dispatchNestedPreScroll(int i, int i2, @Nullable int[] iArr, @Nullable int[] iArr2, int i3) {
            return SpringRecyclerView.this.mSpringHelper.handleNestedPreScroll(i, i2, iArr, iArr2, i3);
        }

        @Override // androidx.core.view.NestedScrollingChildHelper
        public void dispatchNestedScroll(int i, int i2, int i3, int i4, @Nullable int[] iArr, int i5, @Nullable int[] iArr2) {
            SpringRecyclerView.this.mSpringHelper.handleNestedScroll(i, i2, i3, i4, iArr, i5, iArr2);
        }

        /* access modifiers changed from: package-private */
        public boolean super_dispatchNestedPreScroll(int i, int i2, @Nullable int[] iArr, @Nullable int[] iArr2, int i3) {
            if (SpringRecyclerView.this.mHorizontalOverScrolling || SpringRecyclerView.this.mVerticalOverScrolling) {
                return false;
            }
            if (i == 0 && i2 == 0) {
                return false;
            }
            return super.dispatchNestedPreScroll(i, i2, iArr, iArr2, i3);
        }

        /* access modifiers changed from: package-private */
        public void super_dispatchNestedScroll(int i, int i2, int i3, int i4, @Nullable int[] iArr, int i5, @Nullable int[] iArr2) {
            if (!SpringRecyclerView.this.mHorizontalOverScrolling && !SpringRecyclerView.this.mVerticalOverScrolling) {
                super.dispatchNestedScroll(i, i2, i3, i4, iArr, i5, iArr2);
            }
        }
    }

    static {
        VIEW_FLINGER.setAccessible(true);
        NESTED_SCROLL_HELPER.setAccessible(true);
    }

    public SpringRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public SpringRecyclerView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.recyclerViewStyle);
    }

    public SpringRecyclerView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mManagedScrollState = 0;
        this.mSpringHelper = new SpringHelper() {
            /* class androidx.recyclerview.widget.SpringRecyclerView.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // miuix.spring.view.SpringHelper
            public boolean canScrollHorizontally() {
                RecyclerView.LayoutManager layoutManager = SpringRecyclerView.this.mLayout;
                return layoutManager != null && layoutManager.canScrollHorizontally();
            }

            /* access modifiers changed from: protected */
            @Override // miuix.spring.view.SpringHelper
            public boolean canScrollVertically() {
                RecyclerView.LayoutManager layoutManager = SpringRecyclerView.this.mLayout;
                return layoutManager != null && layoutManager.canScrollVertically();
            }

            /* access modifiers changed from: protected */
            @Override // miuix.spring.view.SpringHelper
            public boolean dispatchNestedPreScroll(int i, int i2, @Nullable int[] iArr, @Nullable int[] iArr2, int i3) {
                if (SpringRecyclerView.this.mHorizontalOverScrolling && getHorizontalDistance() == 0) {
                    SpringRecyclerView.this.mHorizontalOverScrolling = false;
                }
                if (SpringRecyclerView.this.mVerticalOverScrolling && getVerticalDistance() == 0) {
                    SpringRecyclerView.this.mVerticalOverScrolling = false;
                }
                return SpringRecyclerView.this.mSpringNestedScrollingHelper.super_dispatchNestedPreScroll(i, i2, iArr, iArr2, i3);
            }

            /* access modifiers changed from: protected */
            @Override // miuix.spring.view.SpringHelper
            public void dispatchNestedScroll(int i, int i2, int i3, int i4, @Nullable int[] iArr, int i5, @Nullable int[] iArr2) {
                SpringRecyclerView.this.mSpringNestedScrollingHelper.super_dispatchNestedScroll(i, i2, i3, i4, iArr, i5, iArr2);
                if (springAvailable() && SpringRecyclerView.this.mManagedScrollState == 2) {
                    if (!SpringRecyclerView.this.mHorizontalOverScrolling && canScrollHorizontally() && i3 != 0) {
                        SpringRecyclerView.this.mSpringFlinger.notifyHorizontalEdgeReached(i3);
                    }
                    if (!SpringRecyclerView.this.mVerticalOverScrolling && canScrollVertically() && i4 != 0) {
                        SpringRecyclerView.this.mSpringFlinger.notifyVerticalEdgeReached(i4);
                    }
                }
            }

            /* access modifiers changed from: protected */
            @Override // miuix.spring.view.SpringHelper
            public int getHeight() {
                return SpringRecyclerView.this.getHeight();
            }

            /* access modifiers changed from: protected */
            @Override // miuix.spring.view.SpringHelper
            public int getWidth() {
                return SpringRecyclerView.this.getWidth();
            }

            /* access modifiers changed from: protected */
            @Override // miuix.spring.view.SpringHelper
            public boolean springAvailable() {
                return SpringRecyclerView.this.springAvailable();
            }

            /* access modifiers changed from: protected */
            @Override // miuix.spring.view.SpringHelper
            @Keep
            public void vibrate() {
                HapticCompat.performHapticFeedback(SpringRecyclerView.this, HapticFeedbackConstants.MIUI_SCROLL_EDGE);
            }
        };
        this.mSpringFlinger = new SpringFlinger();
        this.mSpringNestedScrollingHelper = new SpringNestedScrollingHelper(this);
        replaceViewFlinger(this.mSpringFlinger);
        replaceNestedScrollingHelper(this.mSpringNestedScrollingHelper);
        super.setEdgeEffectFactory(NON_EFFECT_FACTORY);
        if (Build.IS_INTERNATIONAL_BUILD) {
            setSpringEnabled(false);
        }
    }

    private void replaceNestedScrollingHelper(NestedScrollingChildHelper nestedScrollingChildHelper) {
        try {
            NESTED_SCROLL_HELPER.set(this, nestedScrollingChildHelper);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }

    private void replaceViewFlinger(RemixRecyclerView.ViewFlinger viewFlinger) {
        try {
            VIEW_FLINGER.set(this, viewFlinger);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean springAvailable() {
        return getOverScrollMode() != 2 && getSpringEnabled();
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void draw(Canvas canvas) {
        int horizontalDistance = this.mSpringHelper.getHorizontalDistance();
        int verticalDistance = this.mSpringHelper.getVerticalDistance();
        if (horizontalDistance == 0 && verticalDistance == 0) {
            super.draw(canvas);
            return;
        }
        int save = canvas.save();
        canvas.translate((float) (-horizontalDistance), (float) (-verticalDistance));
        super.draw(canvas);
        canvas.restoreToCount(save);
    }

    @Override // androidx.recyclerview.widget.RemixRecyclerView
    public /* bridge */ /* synthetic */ boolean getSpringEnabled() {
        return super.getSpringEnabled();
    }

    @Override // androidx.recyclerview.widget.RecyclerView, androidx.recyclerview.widget.RemixRecyclerView
    public /* bridge */ /* synthetic */ boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void onScrollStateChanged(int i) {
        super.onScrollStateChanged(i);
        this.mManagedScrollState = i;
        if (!springAvailable() || i == 2) {
            return;
        }
        if (this.mHorizontalOverScrolling || this.mVerticalOverScrolling) {
            this.mSpringFlinger.stop();
            this.mHorizontalOverScrolling = false;
            this.mVerticalOverScrolling = false;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView, androidx.recyclerview.widget.RemixRecyclerView
    public /* bridge */ /* synthetic */ boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    @Override // androidx.recyclerview.widget.RemixRecyclerView
    public /* bridge */ /* synthetic */ void setOverScrollMode(int i) {
        super.setOverScrollMode(i);
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.recyclerview.widget.RecyclerView
    public void setScrollState(int i) {
        if (this.mManagedScrollState == 1 && i == 0) {
            int horizontalDistance = this.mSpringHelper.getHorizontalDistance();
            int verticalDistance = this.mSpringHelper.getVerticalDistance();
            if (!(horizontalDistance == 0 && verticalDistance == 0)) {
                this.mSpringFlinger.springBack(horizontalDistance, verticalDistance);
                return;
            }
        }
        super.setScrollState(i);
    }

    @Override // androidx.recyclerview.widget.RemixRecyclerView
    public /* bridge */ /* synthetic */ void setSpringEnabled(boolean z) {
        super.setSpringEnabled(z);
    }
}
