package com.miui.internal.view.menu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.miui.internal.R;
import com.miui.internal.util.DeviceHelper;
import com.miui.internal.variable.Android_View_View_class;
import com.miui.internal.view.menu.ActionMenuView;
import com.miui.internal.widget.ActionBarOverlayLayout;
import com.miui.internal.widget.BlurBackgroundView;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import miui.util.ActionBarUtils;
import miui.util.ViewUtils;
import miui.view.animation.CubicEaseOutInterpolator;

public class PhoneActionMenuView extends ActionMenuView {
    private static final int[] ATTRS = {16842964, R.attr.expandBackground, R.attr.splitActionBarOverlayHeight};
    private boolean isShowBlurBackground;
    private int mAverageActionButtonWidth;
    private Rect mBackgroundPadding;
    private View mBackgroundView;
    private BlurBackgroundView mBlurBackgroundView;
    private Drawable mCollapseBackground;
    private Context mContext;
    private Drawable mExpandBackground;
    private ExpandedMenuBlurView mExpandedMenuBlurView;
    private int mMaxActionButtonWidth;
    private int mMenuItemHeight;
    private int mMenuItemWidth;
    private Drawable mOverflowBackgroundBackup;
    private OverflowMenuState mOverflowMenuState;
    private View mOverflowMenuView;
    private OverflowMenuViewAnimator mOverflowMenuViewAnimator;
    private int mSplitActionBarOverlayHeight;

    /* access modifiers changed from: private */
    public enum OverflowMenuState {
        Collapsed,
        Expanding,
        Expanded,
        Collapsing
    }

    /* access modifiers changed from: private */
    public class OverflowMenuViewAnimator implements Animator.AnimatorListener, View.OnClickListener {
        private AnimatorSet mCollapseAnimator;
        private AnimatorSet mExpandAnimator;
        private ActionBarOverlayLayout overlayLayout;

        private OverflowMenuViewAnimator() {
        }

        private void setContentViewImportantForAccessibility(boolean z) {
            if (z) {
                this.overlayLayout.getContentView().setImportantForAccessibility(0);
            } else {
                Android_View_View_class.Factory.getInstance().get().setImportantForAccessibilityNoHideDescendants(this.overlayLayout.getContentView());
            }
        }

        public void cancel() {
            AnimatorSet animatorSet = this.mCollapseAnimator;
            if (animatorSet != null && animatorSet.isRunning()) {
                this.mCollapseAnimator.cancel();
            }
            AnimatorSet animatorSet2 = this.mExpandAnimator;
            if (animatorSet2 != null && animatorSet2.isRunning()) {
                this.mExpandAnimator.cancel();
            }
        }

        public void ensureAnimators() {
            if (this.mExpandAnimator == null) {
                this.overlayLayout = (ActionBarOverlayLayout) ActionBarUtils.getActionBarOverlayLayout(PhoneActionMenuView.this);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ObjectAnimator.ofFloat(PhoneActionMenuView.this, "Value", 1.0f, 0.0f), this.overlayLayout.getContentMaskAnimator(this).show());
                animatorSet.setDuration((long) PhoneActionMenuView.this.getResources().getInteger(17694720));
                animatorSet.setInterpolator(new CubicEaseOutInterpolator());
                animatorSet.addListener(this);
                this.mExpandAnimator = animatorSet;
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(ObjectAnimator.ofFloat(PhoneActionMenuView.this, "Value", 0.0f, 1.0f), this.overlayLayout.getContentMaskAnimator(null).hide());
                animatorSet2.setDuration((long) PhoneActionMenuView.this.getResources().getInteger(17694720));
                animatorSet2.setInterpolator(new CubicEaseOutInterpolator());
                animatorSet2.addListener(this);
                this.mCollapseAnimator = animatorSet2;
                if (!DeviceHelper.FEATURE_WHOLE_ANIM) {
                    this.mExpandAnimator.setDuration(0L);
                    this.mCollapseAnimator.setDuration(0L);
                }
            }
        }

        public void hide() {
            ensureAnimators();
            this.mCollapseAnimator.cancel();
            this.mExpandAnimator.cancel();
            this.mCollapseAnimator.start();
        }

        public void onAnimationCancel(Animator animator) {
            if (PhoneActionMenuView.this.mOverflowMenuState == OverflowMenuState.Expanding || PhoneActionMenuView.this.mOverflowMenuState == OverflowMenuState.Expanded) {
                PhoneActionMenuView.this.setValue(0.0f);
                setContentViewImportantForAccessibility(false);
            } else if (PhoneActionMenuView.this.mOverflowMenuState == OverflowMenuState.Collapsing || PhoneActionMenuView.this.mOverflowMenuState == OverflowMenuState.Collapsed) {
                PhoneActionMenuView.this.setValue(1.0f);
                setContentViewImportantForAccessibility(true);
            }
            PhoneActionMenuView.this.postInvalidateOnAnimation();
        }

        public void onAnimationEnd(Animator animator) {
            if (PhoneActionMenuView.this.mOverflowMenuView != null) {
                if (PhoneActionMenuView.this.mOverflowMenuView.getTranslationY() == 0.0f) {
                    PhoneActionMenuView.this.mOverflowMenuState = OverflowMenuState.Expanded;
                    setContentViewImportantForAccessibility(false);
                } else if (PhoneActionMenuView.this.mOverflowMenuView.getTranslationY() == ((float) PhoneActionMenuView.this.getMeasuredHeight())) {
                    PhoneActionMenuView.this.mOverflowMenuState = OverflowMenuState.Collapsed;
                    setContentViewImportantForAccessibility(true);
                    if (!PhoneActionMenuView.this.isShowBlurBackground) {
                        PhoneActionMenuView.this.mBackgroundView.setBackground(PhoneActionMenuView.this.mCollapseBackground);
                    }
                }
            }
            PhoneActionMenuView.this.postInvalidateOnAnimation();
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
        }

        public void onClick(View view) {
            if (PhoneActionMenuView.this.mOverflowMenuState == OverflowMenuState.Expanded) {
                PhoneActionMenuView.this.getPresenter().hideOverflowMenu(true);
            }
        }

        public void reverse() {
            if (Build.VERSION.SDK_INT >= 26) {
                try {
                    Method declaredMethod = Class.forName("android.animation.AnimatorSet").getDeclaredMethod("reverse", new Class[0]);
                    if (this.mExpandAnimator.isRunning()) {
                        declaredMethod.invoke(this.mExpandAnimator, new Object[0]);
                    }
                    if (this.mCollapseAnimator.isRunning()) {
                        declaredMethod.invoke(this.mCollapseAnimator, new Object[0]);
                    }
                } catch (Exception e2) {
                    Log.e("PhoneActionMenuView", "reverse: ", e2);
                }
            } else {
                ArrayList<Animator> arrayList = null;
                if (this.mExpandAnimator.isRunning()) {
                    arrayList = this.mExpandAnimator.getChildAnimations();
                }
                if (this.mCollapseAnimator.isRunning()) {
                    arrayList = this.mCollapseAnimator.getChildAnimations();
                }
                if (arrayList != null) {
                    Iterator<Animator> it = arrayList.iterator();
                    while (it.hasNext()) {
                        ((ValueAnimator) it.next()).reverse();
                    }
                }
            }
        }

        public void show() {
            ensureAnimators();
            this.mCollapseAnimator.cancel();
            this.mExpandAnimator.cancel();
            this.mExpandAnimator.start();
        }
    }

    public PhoneActionMenuView(Context context) {
        this(context, null);
    }

    public PhoneActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOverflowMenuState = OverflowMenuState.Collapsed;
        this.mMaxActionButtonWidth = 0;
        this.mAverageActionButtonWidth = 0;
        super.setBackground(null);
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS);
        this.mCollapseBackground = obtainStyledAttributes.getDrawable(0);
        this.mExpandBackground = obtainStyledAttributes.getDrawable(1);
        this.mSplitActionBarOverlayHeight = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        obtainStyledAttributes.recycle();
        extractBackground();
        this.mBackgroundView = new View(context);
        addView(this.mBackgroundView);
        addBlurView(context);
        setChildrenDrawingOrderEnabled(true);
        this.mMaxActionButtonWidth = context.getResources().getDimensionPixelSize(R.dimen.action_button_max_width);
    }

    private void addBlurView(Context context) {
        this.mBlurBackgroundView = new BlurBackgroundView(context);
        this.mBlurBackgroundView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        addView(this.mBlurBackgroundView, 0);
        if (this.isShowBlurBackground) {
            setBlurBackground(true);
        }
    }

    private void clearBackground() {
        this.mBackgroundView.setBackground(null);
        setBackground(null);
    }

    private void extractBackground() {
        if (this.mBackgroundPadding == null) {
            this.mBackgroundPadding = new Rect();
        }
        Drawable drawable = this.mOverflowMenuView == null ? this.mCollapseBackground : this.mExpandBackground;
        if (drawable == null) {
            this.mBackgroundPadding.setEmpty();
        } else {
            drawable.getPadding(this.mBackgroundPadding);
        }
    }

    private OverflowMenuViewAnimator getOverflowMenuViewAnimator() {
        if (this.mOverflowMenuViewAnimator == null) {
            this.mOverflowMenuViewAnimator = new OverflowMenuViewAnimator();
        }
        return this.mOverflowMenuViewAnimator;
    }

    private int getStatusBarHeight() {
        Resources resources = getResources();
        int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    private void resetBackground() {
        this.mBackgroundView.setBackground(this.mOverflowMenuState == OverflowMenuState.Collapsed ? this.mCollapseBackground : this.mExpandBackground);
        extractBackground();
    }

    @Override // com.miui.internal.view.menu.ActionMenuView, com.miui.internal.view.menu.MenuView
    public boolean filterLeftoverView(int i) {
        ActionMenuView.LayoutParams layoutParams;
        View childAt = getChildAt(i);
        return (childAt != this.mOverflowMenuView && childAt != this.mBackgroundView && childAt != this.mBlurBackgroundView && ((layoutParams = (ActionMenuView.LayoutParams) childAt.getLayoutParams()) == null || !layoutParams.isOverflowButton)) && super.filterLeftoverView(i);
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i, int i2) {
        int indexOfChild = indexOfChild(this.mOverflowMenuView);
        int indexOfChild2 = indexOfChild(this.mBackgroundView);
        if (i2 == 0) {
            if (indexOfChild != -1) {
                return indexOfChild;
            }
            if (indexOfChild2 != -1) {
                return indexOfChild2;
            }
        } else if (!(i2 != 1 || indexOfChild == -1 || indexOfChild2 == -1)) {
            return indexOfChild2;
        }
        int i3 = 0;
        while (i3 < i) {
            if (!(i3 == indexOfChild || i3 == indexOfChild2)) {
                int i4 = i3 < indexOfChild ? i3 + 1 : i3;
                if (i3 < indexOfChild2) {
                    i4++;
                }
                if (i4 == i2) {
                    return i3;
                }
            }
            i3++;
        }
        return super.getChildDrawingOrder(i, i2);
    }

    @Override // com.miui.internal.view.menu.ActionMenuView
    public int getCollapsedHeight() {
        int i = this.mMenuItemHeight;
        if (i == 0) {
            return 0;
        }
        return (i + this.mBackgroundPadding.top) - this.mSplitActionBarOverlayHeight;
    }

    @Override // com.miui.internal.view.menu.ActionMenuView, com.miui.internal.view.menu.MenuView
    public boolean hasBackgroundView() {
        return getChildAt(0) == this.mBackgroundView || getChildAt(1) == this.mBackgroundView;
    }

    @Override // com.miui.internal.view.menu.ActionMenuView, com.miui.internal.view.menu.MenuView
    public boolean hasBlurBackgroundView() {
        return getChildAt(0) == this.mBlurBackgroundView || getChildAt(1) == this.mBlurBackgroundView;
    }

    public boolean hideOverflowMenu() {
        OverflowMenuState overflowMenuState = this.mOverflowMenuState;
        if (overflowMenuState == OverflowMenuState.Collapsing || overflowMenuState == OverflowMenuState.Collapsed) {
            return false;
        }
        OverflowMenuViewAnimator overflowMenuViewAnimator = getOverflowMenuViewAnimator();
        if (overflowMenuState == OverflowMenuState.Expanded) {
            this.mOverflowMenuState = OverflowMenuState.Collapsing;
            overflowMenuViewAnimator.hide();
            return true;
        } else if (overflowMenuState != OverflowMenuState.Expanding) {
            return true;
        } else {
            overflowMenuViewAnimator.reverse();
            return true;
        }
    }

    public boolean isBlurEnable() {
        return this.isShowBlurBackground;
    }

    public boolean isOverflowMenuShowing() {
        OverflowMenuState overflowMenuState = this.mOverflowMenuState;
        return overflowMenuState == OverflowMenuState.Expanding || overflowMenuState == OverflowMenuState.Expanded;
    }

    public boolean isSearchStubSupportBlur() {
        return this.mBlurBackgroundView.isSupportBlur();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = i3 - i;
        int i8 = i4 - i2;
        View view = this.mOverflowMenuView;
        if (view != null) {
            int measuredHeight = view.getMeasuredHeight();
            ViewUtils.layoutChildView(this, this.mOverflowMenuView, 0, 0, i7, measuredHeight);
            i5 = measuredHeight - this.mBackgroundPadding.top;
        } else {
            i5 = 0;
        }
        ViewUtils.layoutChildView(this, this.mBackgroundView, 0, i5, i7, i8);
        ViewUtils.layoutChildView(this, this.mBlurBackgroundView, 0, i5, i7, i8);
        int childCount = getChildCount();
        int i9 = 0;
        boolean z2 = false;
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt = getChildAt(i10);
            if (!(childAt == this.mOverflowMenuView || childAt == this.mBackgroundView || childAt == this.mBlurBackgroundView)) {
                if (childAt.getMeasuredWidth() >= this.mAverageActionButtonWidth) {
                    z2 = true;
                }
                i9++;
            }
        }
        int i11 = (i7 - (z2 ? this.mAverageActionButtonWidth * i9 : this.mMenuItemWidth)) >> 1;
        for (int i12 = 0; i12 < childCount; i12++) {
            View childAt2 = getChildAt(i12);
            if (!(childAt2 == this.mOverflowMenuView || childAt2 == this.mBackgroundView || childAt2 == this.mBlurBackgroundView)) {
                int measuredWidth = childAt2.getMeasuredWidth();
                if (z2) {
                    int i13 = (this.mAverageActionButtonWidth - measuredWidth) >> 1;
                    int i14 = i11 + i13;
                    ViewUtils.layoutChildView(this, childAt2, i14, i5, i14 + measuredWidth, i8);
                    i11 = i14 - i13;
                    i6 = this.mAverageActionButtonWidth;
                } else {
                    ViewUtils.layoutChildView(this, childAt2, i11, i5, i11 + measuredWidth, i8);
                    i6 = childAt2.getMeasuredWidth();
                }
                i11 += i6;
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.view.menu.ActionMenuView
    public void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int childCount2 = getChildCount();
        if (indexOfChild(this.mOverflowMenuView) != -1) {
            childCount2--;
        }
        if (indexOfChild(this.mBackgroundView) != -1) {
            childCount2--;
        }
        if (childCount == 0 || childCount2 == 0) {
            this.mMenuItemHeight = 0;
            setMeasuredDimension(0, 0);
            return;
        }
        this.mAverageActionButtonWidth = Math.min(View.MeasureSpec.getSize(i) / childCount2, this.mMaxActionButtonWidth);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mAverageActionButtonWidth, Integer.MIN_VALUE);
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (!(childAt == this.mOverflowMenuView || childAt == this.mBackgroundView || childAt == this.mBlurBackgroundView)) {
                measureChildWithMargins(childAt, makeMeasureSpec, 0, i2, 0);
                i3 += childAt.getMeasuredWidth();
                i4 = Math.max(i4, childAt.getMeasuredHeight());
            }
        }
        this.mMenuItemWidth = i3;
        this.mMenuItemHeight = i4;
        View view = this.mOverflowMenuView;
        if (view != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.topMargin = getStatusBarHeight();
            marginLayoutParams.bottomMargin = this.mMenuItemHeight;
            measureChildWithMargins(this.mOverflowMenuView, i, 0, i2, 0);
            i3 = Math.max(i3, this.mOverflowMenuView.getMeasuredWidth());
            i4 += this.mOverflowMenuView.getMeasuredHeight();
            OverflowMenuState overflowMenuState = this.mOverflowMenuState;
            if (overflowMenuState == OverflowMenuState.Expanded) {
                this.mOverflowMenuView.setTranslationY(0.0f);
            } else if (overflowMenuState == OverflowMenuState.Collapsed) {
                this.mOverflowMenuView.setTranslationY((float) i4);
            }
        }
        if (this.mOverflowMenuView == null) {
            i4 += this.mBackgroundPadding.top;
        }
        if (!this.isShowBlurBackground) {
            this.mBackgroundView.setBackground(this.mOverflowMenuState == OverflowMenuState.Collapsed ? this.mCollapseBackground : this.mExpandBackground);
        }
        setMeasuredDimension(Math.max(i3, View.MeasureSpec.getSize(i)), i4);
    }

    @Override // com.miui.internal.view.menu.ActionMenuView
    public void onPageScrolled(int i, float f, boolean z, boolean z2) {
        if (DeviceHelper.FEATURE_WHOLE_ANIM) {
            setAlpha(computeAlpha(f, z, z2));
        }
        float computeTranslationY = computeTranslationY(f, z, z2);
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (!(childAt == this.mOverflowMenuView || childAt == this.mBackgroundView || childAt == this.mBlurBackgroundView)) {
                childAt.setTranslationY(computeTranslationY);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float y = motionEvent.getY();
        View view = this.mOverflowMenuView;
        return y > (view == null ? 0.0f : view.getTranslationY()) || super.onTouchEvent(motionEvent);
    }

    public void setAlpha(float f) {
        if (this.isShowBlurBackground) {
            for (int i = 0; i < getChildCount(); i++) {
                if (!(getChildAt(i) instanceof BlurBackgroundView)) {
                    getChildAt(i).setAlpha(f);
                }
            }
            return;
        }
        super.setAlpha(f);
    }

    public void setBackground(Drawable drawable) {
        if (this.mCollapseBackground != drawable) {
            this.mCollapseBackground = drawable;
            extractBackground();
        }
    }

    public boolean setBlurBackground(boolean z) {
        boolean blurBackground = this.mBlurBackgroundView.setBlurBackground(z);
        if (blurBackground) {
            this.isShowBlurBackground = z;
            ExpandedMenuBlurView expandedMenuBlurView = this.mExpandedMenuBlurView;
            if (expandedMenuBlurView != null) {
                expandedMenuBlurView.setBlurBackground(z);
            }
            if (this.isShowBlurBackground) {
                this.mBackgroundView.setAlpha(0.0f);
                ExpandedMenuBlurView expandedMenuBlurView2 = this.mExpandedMenuBlurView;
                if (expandedMenuBlurView2 != null && expandedMenuBlurView2.getChildCount() > 1) {
                    this.mOverflowBackgroundBackup = this.mExpandedMenuBlurView.getChildAt(1).getBackground();
                    this.mExpandedMenuBlurView.getChildAt(1).setBackground(null);
                }
                setBackground(null);
            } else {
                this.mBackgroundView.setAlpha(1.0f);
                ExpandedMenuBlurView expandedMenuBlurView3 = this.mExpandedMenuBlurView;
                if (!(expandedMenuBlurView3 == null || expandedMenuBlurView3.getChildCount() <= 1 || this.mOverflowBackgroundBackup == null)) {
                    this.mExpandedMenuBlurView.getChildAt(1).setBackground(this.mOverflowBackgroundBackup);
                }
            }
        }
        return blurBackground;
    }

    public void setOverflowMenuView(View view) {
        ExpandedMenuBlurView expandedMenuBlurView = this.mExpandedMenuBlurView;
        if (((expandedMenuBlurView == null || expandedMenuBlurView.getChildCount() <= 1) ? null : this.mExpandedMenuBlurView.getChildAt(1)) != view) {
            View view2 = this.mOverflowMenuView;
            if (view2 != null) {
                if (view2.getAnimation() != null) {
                    this.mOverflowMenuView.clearAnimation();
                }
                ExpandedMenuBlurView expandedMenuBlurView2 = this.mExpandedMenuBlurView;
                if (expandedMenuBlurView2 != null) {
                    expandedMenuBlurView2.removeAllViews();
                    removeView(this.mExpandedMenuBlurView);
                    this.mExpandedMenuBlurView = null;
                }
            }
            if (view != null) {
                if (this.mExpandedMenuBlurView == null) {
                    this.mExpandedMenuBlurView = new ExpandedMenuBlurView(this.mContext);
                }
                this.mExpandedMenuBlurView.addView(view);
                addView(this.mExpandedMenuBlurView);
            }
            this.mOverflowMenuView = this.mExpandedMenuBlurView;
            setBlurBackground(this.isShowBlurBackground);
            extractBackground();
        }
    }

    public void setValue(float f) {
        View view = this.mOverflowMenuView;
        if (view != null) {
            view.setTranslationY(f * ((float) getMeasuredHeight()));
        }
    }

    public boolean showOverflowMenu() {
        OverflowMenuState overflowMenuState = this.mOverflowMenuState;
        if (overflowMenuState == OverflowMenuState.Expanding || overflowMenuState == OverflowMenuState.Expanded || this.mOverflowMenuView == null) {
            return false;
        }
        if (!this.isShowBlurBackground) {
            this.mBackgroundView.setBackground(this.mExpandBackground);
        }
        OverflowMenuViewAnimator overflowMenuViewAnimator = getOverflowMenuViewAnimator();
        if (overflowMenuState == OverflowMenuState.Collapsed) {
            this.mOverflowMenuState = OverflowMenuState.Expanding;
            overflowMenuViewAnimator.show();
        } else if (overflowMenuState == OverflowMenuState.Collapsing) {
            overflowMenuViewAnimator.reverse();
        }
        postInvalidateOnAnimation();
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        return true;
    }

    public void updateBackground(boolean z) {
        this.isShowBlurBackground = z;
        if (z) {
            clearBackground();
        } else {
            resetBackground();
        }
        setBlurBackground(z);
    }
}
