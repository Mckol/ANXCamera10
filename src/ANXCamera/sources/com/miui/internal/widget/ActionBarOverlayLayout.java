package com.miui.internal.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import com.miui.internal.R;
import com.miui.internal.util.ContextHelper;
import com.miui.internal.util.DeviceHelper;
import com.miui.internal.view.menu.ContextMenuBuilder;
import com.miui.internal.view.menu.MenuBuilder;
import com.miui.internal.view.menu.MenuDialogHelper;
import com.miui.internal.view.menu.MenuPresenter;
import com.miui.internal.view.menu.context.ContextMenuPopupWindowHelper;
import miui.app.ActionBar;
import miui.app.OnStatusBarChangeListener;
import miui.core.view.NestedScrollingParent3;
import miui.reflect.Method;
import miui.util.AttributeResolver;
import miui.util.Log;
import miui.view.RoundedFrameLayout;
import miui.view.SearchActionMode;

public class ActionBarOverlayLayout extends RoundedFrameLayout implements NestedScrollingParent3 {
    private static final String TAG = "ActionBarOverlayLayout";
    private boolean isBackupPadding;
    private ActionBar mActionBar;
    private ActionBarContainer mActionBarBottom;
    private ActionBarContextView mActionBarContextView;
    protected ActionBarContainer mActionBarTop;
    protected ActionBarView mActionBarView;
    private ActionMode mActionMode;
    private boolean mAnimating;
    private Rect mBaseContentInsets;
    private Rect mBaseInnerInsets;
    private Window.Callback mCallback;
    private boolean mContentAutoFitSystemWindow;
    private Drawable mContentHeaderBackground;
    private Rect mContentInsets;
    private View mContentMask;
    private Rect mContentMaskInsets;
    protected View mContentView;
    private int mContentViewPaddingTopBackup;
    private int mContentViewePaddingBottomBackup;
    private ContextMenuBuilder mContextMenu;
    private ContextMenuCallback mContextMenuCallback;
    private MenuDialogHelper mContextMenuHelper;
    private ContextMenuPopupWindowHelper mContextMenuPopupWindowHelper;
    private TypedValue mFixedHeightMajor;
    private TypedValue mFixedHeightMinor;
    private TypedValue mFixedWidthMajor;
    private TypedValue mFixedWidthMinor;
    private Rect mInnerInsets;
    private boolean mIsAttachedToFloatingWindow;
    private Rect mLastBaseContentInsets;
    private Rect mLastInnerInsets;
    private Bitmap mMask1;
    private Bitmap mMask2;
    private Bitmap mMask3;
    private Bitmap mMask4;
    private int[] mOffsetInWindow;
    private OnStatusBarChangeListener mOnStatusBarChangeListener;
    private boolean mOverlayMode;
    private Paint mPaint;
    private boolean mRequestFitSystemWindow;
    private boolean mRootSubDecor;
    private int mTranslucentStatus;

    /* access modifiers changed from: private */
    public class ActionModeCallbackWrapper implements ActionMode.Callback {
        private ActionMode.Callback mWrapped;

        public ActionModeCallbackWrapper(ActionMode.Callback callback) {
            this.mWrapped = callback;
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onCreateActionMode(actionMode, menu);
        }

        public void onDestroyActionMode(ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode);
            if (ActionBarOverlayLayout.this.getCallback() != null) {
                ActionBarOverlayLayout.this.getCallback().onActionModeFinished(actionMode);
            }
            ActionBarOverlayLayout.this.mActionMode = null;
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }
    }

    public class ContentMaskAnimator implements Animator.AnimatorListener {
        private ObjectAnimator mHideAnimator;
        private View.OnClickListener mOnClickListener;
        private ObjectAnimator mShowAnimator;

        private ContentMaskAnimator(View.OnClickListener onClickListener) {
            this.mOnClickListener = onClickListener;
            this.mShowAnimator = ObjectAnimator.ofFloat(ActionBarOverlayLayout.this.mContentMask, "alpha", 0.0f, 1.0f);
            this.mShowAnimator.addListener(this);
            this.mHideAnimator = ObjectAnimator.ofFloat(ActionBarOverlayLayout.this.mContentMask, "alpha", 1.0f, 0.0f);
            this.mHideAnimator.addListener(this);
            if (!DeviceHelper.FEATURE_WHOLE_ANIM) {
                this.mShowAnimator.setDuration(0L);
                this.mHideAnimator.setDuration(0L);
            }
        }

        public Animator hide() {
            return this.mHideAnimator;
        }

        public void onAnimationCancel(Animator animator) {
            if (animator == this.mHideAnimator) {
                ActionBarOverlayLayout.this.mActionBarBottom.bringToFront();
                ActionBarOverlayLayout.this.mContentMask.setOnClickListener(null);
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (ActionBarOverlayLayout.this.mContentMask.getAlpha() == 0.0f) {
                ActionBarOverlayLayout.this.mActionBarBottom.bringToFront();
                ActionBarOverlayLayout.this.mContentMask.setOnClickListener(null);
                ActionBarOverlayLayout.this.mContentMask.setVisibility(8);
            }
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
            if (animator == this.mShowAnimator) {
                ActionBarOverlayLayout.this.mContentMask.setVisibility(0);
                ActionBarOverlayLayout.this.mContentMask.bringToFront();
                ActionBarOverlayLayout.this.mActionBarBottom.bringToFront();
                ActionBarOverlayLayout.this.mContentMask.setOnClickListener(this.mOnClickListener);
            }
        }

        public Animator show() {
            return this.mShowAnimator;
        }
    }

    /* access modifiers changed from: private */
    public class ContextMenuCallback implements MenuBuilder.Callback, MenuPresenter.Callback {
        private MenuDialogHelper mSubMenuHelper;

        private ContextMenuCallback() {
        }

        /* access modifiers changed from: package-private */
        public Activity getActivity() {
            return ContextHelper.getActivityContextFromView(ActionBarOverlayLayout.this);
        }

        @Override // com.miui.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            if (menuBuilder.getRootMenu() != menuBuilder) {
                onCloseSubMenu(menuBuilder);
            }
            if (z) {
                if (getActivity() != null) {
                    getActivity().onPanelClosed(6, menuBuilder);
                }
                ActionBarOverlayLayout.this.dismissContextMenu();
                MenuDialogHelper menuDialogHelper = this.mSubMenuHelper;
                if (menuDialogHelper != null) {
                    menuDialogHelper.dismiss();
                    this.mSubMenuHelper = null;
                }
            }
        }

        public void onCloseSubMenu(MenuBuilder menuBuilder) {
            if (getActivity() != null) {
                getActivity().onPanelClosed(6, menuBuilder.getRootMenu());
            }
        }

        @Override // com.miui.internal.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            if (getActivity() != null) {
                return getActivity().onMenuItemSelected(6, menuItem);
            }
            return false;
        }

        @Override // com.miui.internal.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(MenuBuilder menuBuilder) {
        }

        @Override // com.miui.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (menuBuilder == null) {
                return false;
            }
            menuBuilder.setCallback(this);
            this.mSubMenuHelper = new MenuDialogHelper(menuBuilder);
            this.mSubMenuHelper.show(null);
            return true;
        }
    }

    /* access modifiers changed from: private */
    public class SearchActionModeCallbackWrapper extends ActionModeCallbackWrapper implements SearchActionMode.Callback {
        public SearchActionModeCallbackWrapper(ActionMode.Callback callback) {
            super(callback);
        }
    }

    public ActionBarOverlayLayout(Context context) {
        this(context, null);
    }

    public ActionBarOverlayLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionBarOverlayLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRootSubDecor = true;
        this.mBaseContentInsets = new Rect();
        this.mLastBaseContentInsets = new Rect();
        this.mContentInsets = new Rect();
        this.mBaseInnerInsets = new Rect();
        this.mLastInnerInsets = new Rect();
        this.mInnerInsets = new Rect();
        this.mContentMaskInsets = new Rect();
        this.mContextMenuCallback = new ContextMenuCallback();
        this.mContentViewPaddingTopBackup = -1;
        this.mContentViewePaddingBottomBackup = -1;
        this.isBackupPadding = false;
        this.mOffsetInWindow = new int[2];
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Window, i, 0);
        if (obtainStyledAttributes.hasValue(R.styleable.Window_windowFixedWidthMajor)) {
            this.mFixedWidthMajor = new TypedValue();
            obtainStyledAttributes.getValue(R.styleable.Window_windowFixedWidthMajor, this.mFixedWidthMajor);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.Window_windowFixedWidthMinor)) {
            this.mFixedWidthMinor = new TypedValue();
            obtainStyledAttributes.getValue(R.styleable.Window_windowFixedWidthMinor, this.mFixedWidthMinor);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.Window_windowFixedHeightMajor)) {
            this.mFixedHeightMajor = new TypedValue();
            obtainStyledAttributes.getValue(R.styleable.Window_windowFixedHeightMajor, this.mFixedHeightMajor);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.Window_windowFixedHeightMinor)) {
            this.mFixedHeightMinor = new TypedValue();
            obtainStyledAttributes.getValue(R.styleable.Window_windowFixedHeightMinor, this.mFixedHeightMinor);
        }
        this.mContentAutoFitSystemWindow = obtainStyledAttributes.getBoolean(miui.R.styleable.Window_contentAutoFitSystemWindow, false);
        if (this.mContentAutoFitSystemWindow) {
            this.mContentHeaderBackground = obtainStyledAttributes.getDrawable(miui.R.styleable.Window_contentHeaderBackground);
        }
        obtainStyledAttributes.recycle();
    }

    private boolean applyInsets(View view, Rect rect, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5;
        int i;
        int i2;
        int i3;
        int i4;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        if (!z || layoutParams.leftMargin == (i4 = rect.left)) {
            z5 = false;
        } else {
            layoutParams.leftMargin = i4;
            z5 = true;
        }
        if (z2 && layoutParams.topMargin != (i3 = rect.top)) {
            layoutParams.topMargin = i3;
            z5 = true;
        }
        if (z4 && layoutParams.rightMargin != (i2 = rect.right)) {
            layoutParams.rightMargin = i2;
            z5 = true;
        }
        if (!z3 || layoutParams.bottomMargin == (i = rect.bottom)) {
            return z5;
        }
        layoutParams.bottomMargin = i;
        return true;
    }

    private void computeFitSystemInsets(Rect rect, Rect rect2) {
        boolean isRootSubDecor = isRootSubDecor();
        boolean isTranslucentStatus = isTranslucentStatus();
        rect2.set(rect);
        if ((!isRootSubDecor || isTranslucentStatus) && !this.mContentAutoFitSystemWindow) {
            rect2.top = 0;
        }
    }

    private ActionModeCallbackWrapper createActionModeCallbackWrapper(ActionMode.Callback callback) {
        return callback instanceof SearchActionMode.Callback ? new SearchActionModeCallbackWrapper(callback) : new ActionModeCallbackWrapper(callback);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    private int getHeightMeasureSpec(int i) {
        int i2;
        float fraction;
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode != Integer.MIN_VALUE) {
            return i;
        }
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int i3 = 0;
        TypedValue typedValue = displayMetrics.widthPixels < displayMetrics.heightPixels ? this.mFixedHeightMajor : this.mFixedHeightMinor;
        if (typedValue == null || (i2 = typedValue.type) == 0) {
            return i;
        }
        if (i2 == 5) {
            fraction = typedValue.getDimension(displayMetrics);
        } else {
            if (i2 == 6) {
                int i4 = displayMetrics.heightPixels;
                fraction = typedValue.getFraction((float) i4, (float) i4);
            }
            return i3 <= 0 ? View.MeasureSpec.makeMeasureSpec(Math.min(i3, size), 1073741824) : i;
        }
        i3 = (int) fraction;
        if (i3 <= 0) {
        }
    }

    private int getNavigationBarHeight() {
        Resources resources = getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    private int getWidthMeasureSpec(int i) {
        int i2;
        float fraction;
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode != Integer.MIN_VALUE) {
            return i;
        }
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int i3 = 0;
        TypedValue typedValue = displayMetrics.widthPixels < displayMetrics.heightPixels ? this.mFixedWidthMinor : this.mFixedWidthMajor;
        if (typedValue == null || (i2 = typedValue.type) == 0) {
            return i;
        }
        if (i2 == 5) {
            fraction = typedValue.getDimension(displayMetrics);
        } else {
            if (i2 == 6) {
                int i4 = displayMetrics.widthPixels;
                fraction = typedValue.getFraction((float) i4, (float) i4);
            }
            return i3 <= 0 ? View.MeasureSpec.makeMeasureSpec(Math.min(i3, size), 1073741824) : i;
        }
        i3 = (int) fraction;
        if (i3 <= 0) {
        }
    }

    private boolean internalShowContextMenu(View view, float f, float f2) {
        ContextMenuBuilder contextMenuBuilder = this.mContextMenu;
        if (contextMenuBuilder == null) {
            this.mContextMenu = new ContextMenuBuilder(getContext());
            this.mContextMenu.setCallback(this.mContextMenuCallback);
        } else {
            contextMenuBuilder.clear();
        }
        this.mContextMenuPopupWindowHelper = this.mContextMenu.show(view, view.getWindowToken(), f, f2);
        ContextMenuPopupWindowHelper contextMenuPopupWindowHelper = this.mContextMenuPopupWindowHelper;
        if (contextMenuPopupWindowHelper == null) {
            return super.showContextMenuForChild(view);
        }
        contextMenuPopupWindowHelper.setPresenterCallback(this.mContextMenuCallback);
        return true;
    }

    private boolean isAttachedToFloatingWindow() {
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (iArr[0] == 0 || iArr[1] == 0 || iArr[0] + getMeasuredWidth() == displayMetrics.widthPixels || iArr[1] + getMeasuredHeight() == displayMetrics.heightPixels) ? false : true;
    }

    private boolean isBackPressed(KeyEvent keyEvent) {
        return keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 1;
    }

    private boolean isBottomAnimating() {
        return this.mAnimating;
    }

    private boolean isLayoutHideNavigation() {
        return (getWindowSystemUiVisibility() & 512) != 0;
    }

    private void pullChildren() {
        View view;
        if (this.mContentView == null) {
            this.mContentView = findViewById(16908290);
            this.mContentMask = findViewById(R.id.content_mask);
            if (DeviceHelper.IS_OLED && (view = this.mContentMask) != null) {
                view.setBackground(getContext().getResources().getDrawable(R.drawable.window_content_mask_oled, getContext().getTheme()));
            }
            this.mActionBarTop = (ActionBarContainer) findViewById(R.id.action_bar_container);
            ActionBarContainer actionBarContainer = this.mActionBarTop;
            if (actionBarContainer != null) {
                this.mActionBarContextView = (ActionBarContextView) actionBarContainer.findViewById(R.id.action_context_bar);
                this.mActionBarView = (ActionBarView) this.mActionBarTop.findViewById(R.id.action_bar);
            }
            this.mActionBarBottom = (ActionBarContainer) findViewById(R.id.split_action_bar);
        }
    }

    public void animateContentMarginBottom(int i) {
        Rect rect = new Rect();
        Rect rect2 = this.mContentInsets;
        rect.top = rect2.top;
        rect.bottom = i;
        rect.right = rect2.right;
        rect.left = rect2.left;
        applyInsets(this.mContentView, rect, true, true, true, true);
        this.mContentView.requestLayout();
    }

    public void dismissContextMenu() {
        MenuDialogHelper menuDialogHelper = this.mContextMenuHelper;
        if (menuDialogHelper != null) {
            menuDialogHelper.dismiss();
            this.mContextMenu = null;
        }
        ContextMenuPopupWindowHelper contextMenuPopupWindowHelper = this.mContextMenuPopupWindowHelper;
        if (contextMenuPopupWindowHelper != null) {
            contextMenuPopupWindowHelper.dismiss();
            this.mContextMenuPopupWindowHelper = null;
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        Drawable drawable;
        if (this.mContentAutoFitSystemWindow && (drawable = this.mContentHeaderBackground) != null) {
            drawable.setBounds(0, 0, getRight() - getLeft(), this.mBaseContentInsets.top);
            this.mContentHeaderBackground.draw(canvas);
        }
        if (!this.mIsAttachedToFloatingWindow || isClipRoundedCorner()) {
            super.dispatchDraw(canvas);
            return;
        }
        canvas.saveLayer(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), null, 31);
        super.dispatchDraw(canvas);
        canvas.drawBitmap(this.mMask1, 0.0f, 0.0f, this.mPaint);
        canvas.drawBitmap(this.mMask2, (float) (getWidth() - this.mMask2.getWidth()), 0.0f, this.mPaint);
        canvas.drawBitmap(this.mMask3, 0.0f, (float) (getHeight() - this.mMask3.getHeight()), this.mPaint);
        canvas.drawBitmap(this.mMask4, (float) (getWidth() - this.mMask4.getWidth()), (float) (getHeight() - this.mMask4.getHeight()), this.mPaint);
        canvas.restore();
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (super.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        if (isBackPressed(keyEvent)) {
            if (this.mActionMode != null) {
                ActionBarContextView actionBarContextView = this.mActionBarContextView;
                if (actionBarContextView != null && actionBarContextView.hideOverflowMenu()) {
                    return true;
                }
                this.mActionMode.finish();
                this.mActionMode = null;
                return true;
            }
            ActionBarView actionBarView = this.mActionBarView;
            if (actionBarView != null && actionBarView.hideOverflowMenu()) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean fitSystemWindows(Rect rect) {
        boolean z;
        boolean z2;
        Window window;
        OnStatusBarChangeListener onStatusBarChangeListener = this.mOnStatusBarChangeListener;
        if (onStatusBarChangeListener != null) {
            onStatusBarChangeListener.onStatusBarHeightChange(rect.top);
        }
        this.mBaseInnerInsets.set(rect);
        Activity activityContextFromView = ContextHelper.getActivityContextFromView(this);
        boolean z3 = (activityContextFromView == null || (window = activityContextFromView.getWindow()) == null || window.getAttributes().layoutInDisplayCutoutMode != 1) ? false : true;
        if (!z3) {
            z3 = AttributeResolver.resolveInt(getContext(), 16844166, 0) == 1;
        }
        if (z3) {
            Rect rect2 = this.mBaseInnerInsets;
            rect2.left = 0;
            rect2.right = 0;
        }
        if (isRootSubDecor() || (isLayoutHideNavigation() && this.mBaseInnerInsets.bottom == getNavigationBarHeight())) {
            z = false;
        } else {
            this.mBaseInnerInsets.bottom = 0;
            z = true;
        }
        if (this.mActionBarTop != null) {
            if (isTranslucentStatus()) {
                this.mActionBarTop.setPendingInsets(rect);
            }
            z2 = applyInsets(this.mActionBarTop, this.mBaseInnerInsets, true, isRootSubDecor() && !isTranslucentStatus(), false, true);
        } else {
            z2 = false;
        }
        if (this.mActionBarBottom != null) {
            this.mContentMaskInsets.set(this.mBaseInnerInsets);
            z2 |= applyInsets(this.mActionBarBottom, this.mBaseInnerInsets, true, false, true, true);
        }
        if (!isRootSubDecor() && !z) {
            this.mBaseInnerInsets.bottom = 0;
        }
        computeFitSystemInsets(this.mBaseInnerInsets, this.mBaseContentInsets);
        if (!this.mLastBaseContentInsets.equals(this.mBaseContentInsets)) {
            this.mLastBaseContentInsets.set(this.mBaseContentInsets);
            z2 = true;
        }
        if (z2) {
            requestLayout();
        }
        return isRootSubDecor();
    }

    public ActionBar getActionBar() {
        return this.mActionBar;
    }

    public ActionBarView getActionBarView() {
        return this.mActionBarView;
    }

    /* access modifiers changed from: protected */
    public int getBottomInset() {
        ActionBarContainer actionBarContainer = this.mActionBarBottom;
        if (actionBarContainer != null) {
            return actionBarContainer.getInsetHeight();
        }
        return 0;
    }

    public Window.Callback getCallback() {
        return this.mCallback;
    }

    public ContentMaskAnimator getContentMaskAnimator(View.OnClickListener onClickListener) {
        return new ContentMaskAnimator(onClickListener);
    }

    public View getContentView() {
        return this.mContentView;
    }

    public boolean isRootSubDecor() {
        return this.mRootSubDecor;
    }

    public boolean isTranslucentStatus() {
        int windowSystemUiVisibility = getWindowSystemUiVisibility();
        return (((windowSystemUiVisibility & 256) != 0) && ((windowSystemUiVisibility & 1024) != 0)) || (this.mTranslucentStatus != 0);
    }

    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        WindowInsets onApplyWindowInsets = super.onApplyWindowInsets(windowInsets);
        if (!onApplyWindowInsets.isConsumed() && isRootSubDecor() && Build.VERSION.SDK_INT >= 28) {
            try {
                return (WindowInsets) Method.of(WindowInsets.class, "consumeDisplayCutout", "()Landroid/view/WindowInsets;").invokeObject(WindowInsets.class, onApplyWindowInsets, new Object[0]);
            } catch (Exception e2) {
                Log.w(TAG, "onApplyWindowInsets, consumeDisplayCutout failed, " + e2);
            }
        }
        return onApplyWindowInsets;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        requestFitSystemWindows();
        ActionBarContainer actionBarContainer = this.mActionBarTop;
        if (actionBarContainer != null && actionBarContainer.isBlurEnable()) {
            this.mActionBarTop.updateAllClipView();
        }
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        pullChildren();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        boolean z;
        int widthMeasureSpec = getWidthMeasureSpec(i);
        int heightMeasureSpec = getHeightMeasureSpec(i2);
        View view = this.mContentView;
        View view2 = this.mContentMask;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < getChildCount(); i8++) {
            View childAt = getChildAt(i8);
            if (!(childAt == view || childAt == view2 || childAt.getVisibility() == 8)) {
                measureChildWithMargins(childAt, widthMeasureSpec, 0, heightMeasureSpec, 0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                int max = Math.max(i5, childAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
                i6 = Math.max(i6, childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                i5 = max;
                i7 = FrameLayout.combineMeasuredStates(i7, childAt.getMeasuredState());
            }
        }
        ActionBarContainer actionBarContainer = this.mActionBarTop;
        if (actionBarContainer == null || actionBarContainer.getVisibility() != 0) {
            i4 = 0;
            i3 = 0;
        } else {
            i4 = this.mActionBarTop.getMeasuredHeight();
            ActionBarContainer actionBarContainer2 = this.mActionBarTop;
            i3 = (actionBarContainer2 == null || !actionBarContainer2.isBlurEnable()) ? 0 : i4 <= 0 ? 0 : i4;
        }
        ActionBarView actionBarView = this.mActionBarView;
        int bottomInset = (actionBarView == null || !actionBarView.isSplitActionBar()) ? 0 : getBottomInset();
        if (isTranslucentStatus() && this.mContentAutoFitSystemWindow) {
            Drawable drawable = this.mContentHeaderBackground;
            if (drawable != null) {
                drawable.setBounds(0, 0, getRight() - getLeft(), this.mBaseContentInsets.top);
            } else {
                ViewGroup viewGroup = (ViewGroup) findViewById(16908290);
                if (viewGroup != null && viewGroup.getChildCount() == 1) {
                    View childAt2 = viewGroup.getChildAt(0);
                    if (i4 <= 0) {
                        childAt2.setPadding(childAt2.getPaddingLeft(), this.mBaseInnerInsets.top, childAt2.getPaddingRight(), childAt2.getPaddingBottom());
                    } else {
                        childAt2.setPadding(childAt2.getPaddingLeft(), 0, childAt2.getPaddingRight(), childAt2.getPaddingBottom());
                    }
                }
            }
        }
        this.mInnerInsets.set(this.mBaseInnerInsets);
        this.mContentInsets.set(this.mBaseContentInsets);
        if (isTranslucentStatus() && i4 > 0) {
            this.mContentInsets.top = 0;
        }
        ActionBarContainer actionBarContainer3 = this.mActionBarTop;
        if (actionBarContainer3 == null || !actionBarContainer3.isBlurEnable()) {
            if (!this.mOverlayMode) {
                Rect rect = this.mContentInsets;
                rect.top += i4;
                rect.bottom += bottomInset;
            } else {
                if (!isTranslucentStatus()) {
                    this.mInnerInsets.top += i4;
                } else if (i4 > 0) {
                    this.mInnerInsets.top = i4;
                }
                this.mInnerInsets.bottom += bottomInset;
            }
        } else if (!this.mOverlayMode) {
            Rect rect2 = this.mContentInsets;
            rect2.top = 0;
            rect2.bottom = 0;
        } else {
            Rect rect3 = this.mInnerInsets;
            rect3.top = 0;
            this.mContentInsets.top = 0;
            rect3.bottom = 0;
        }
        if (isLayoutHideNavigation()) {
            if (getResources().getConfiguration().orientation == 1) {
                this.mContentInsets.bottom = 0;
            } else {
                Rect rect4 = this.mContentInsets;
                rect4.right = 0;
                rect4.left = 0;
                if (DeviceHelper.IS_TABLET || !DeviceHelper.isHideGestureLine(getContext())) {
                    this.mContentInsets.bottom = 0;
                }
            }
        }
        if (!isBottomAnimating()) {
            z = true;
            applyInsets(view, this.mContentInsets, true, true, true, true);
        } else {
            z = true;
        }
        ActionBarContainer actionBarContainer4 = this.mActionBarTop;
        if (actionBarContainer4 != null && actionBarContainer4.isBlurEnable() && !this.mOverlayMode) {
            if (!this.isBackupPadding) {
                this.isBackupPadding = z;
                this.mContentViewPaddingTopBackup = view.getPaddingTop();
                this.mContentViewePaddingBottomBackup = view.getPaddingBottom();
            }
            view.setPadding(view.getPaddingLeft(), i3 + this.mContentViewPaddingTopBackup, view.getPaddingRight(), bottomInset == 0 ? view.getPaddingBottom() : bottomInset + this.mContentViewePaddingBottomBackup);
        } else if (!this.mOverlayMode) {
            if (this.isBackupPadding) {
                view.setPadding(view.getPaddingLeft(), this.mContentViewPaddingTopBackup, view.getPaddingRight(), this.mContentViewePaddingBottomBackup);
            }
            this.isBackupPadding = false;
        }
        if (!this.mLastInnerInsets.equals(this.mInnerInsets) || this.mRequestFitSystemWindow) {
            this.mLastInnerInsets.set(this.mInnerInsets);
            super.fitSystemWindows(this.mInnerInsets);
            this.mRequestFitSystemWindow = false;
        }
        measureChildWithMargins(view, widthMeasureSpec, 0, heightMeasureSpec, 0);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) view.getLayoutParams();
        int max2 = Math.max(i5, view.getMeasuredWidth() + layoutParams2.leftMargin + layoutParams2.rightMargin);
        int max3 = Math.max(i6, view.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin);
        int combineMeasuredStates = FrameLayout.combineMeasuredStates(i7, view.getMeasuredState());
        if (view2 != null && view2.getVisibility() == 0) {
            applyInsets(view2, this.mContentMaskInsets, true, false, true, true);
            measureChildWithMargins(view2, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
        setMeasuredDimension(FrameLayout.resolveSizeAndState(Math.max(max2 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), widthMeasureSpec, combineMeasuredStates), FrameLayout.resolveSizeAndState(Math.max(max3 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), heightMeasureSpec, combineMeasuredStates << 16));
        this.mIsAttachedToFloatingWindow = isAttachedToFloatingWindow();
        if (this.mIsAttachedToFloatingWindow && this.mPaint == null) {
            this.mPaint = new Paint();
            this.mPaint.setAntiAlias(z);
            this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            Resources resources = getResources();
            this.mMask1 = BitmapFactory.decodeResource(resources, R.drawable.floating_window_mask_1);
            this.mMask2 = BitmapFactory.decodeResource(resources, R.drawable.floating_window_mask_2);
            this.mMask3 = BitmapFactory.decodeResource(resources, R.drawable.floating_window_mask_3);
            this.mMask4 = BitmapFactory.decodeResource(resources, R.drawable.floating_window_mask_4);
        }
    }

    @Override // miui.core.view.NestedScrollingParent2
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3) {
        int[] iArr2 = this.mOffsetInWindow;
        iArr2[1] = 0;
        ActionBarContainer actionBarContainer = this.mActionBarTop;
        if (actionBarContainer != null) {
            actionBarContainer.onNestedPreScroll(view, i, i2, iArr, i3, iArr2);
        }
        this.mContentView.offsetTopAndBottom(-this.mOffsetInWindow[1]);
    }

    @Override // miui.core.view.NestedScrollingParent2
    public void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5) {
    }

    @Override // miui.core.view.NestedScrollingParent3
    public void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        int[] iArr2 = this.mOffsetInWindow;
        iArr2[1] = 0;
        ActionBarContainer actionBarContainer = this.mActionBarTop;
        if (actionBarContainer != null) {
            actionBarContainer.onNestedScroll(view, i, i2, i3, i4, i5, iArr, iArr2);
        }
        this.mContentView.offsetTopAndBottom(-this.mOffsetInWindow[1]);
    }

    @Override // miui.core.view.NestedScrollingParent2
    public void onNestedScrollAccepted(View view, View view2, int i, int i2) {
        ActionBarContainer actionBarContainer = this.mActionBarTop;
        if (actionBarContainer != null) {
            actionBarContainer.onNestedScrollAccepted(view, view2, i, i2);
        }
    }

    @Override // miui.core.view.NestedScrollingParent2
    public boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        ActionBarContainer actionBarContainer = this.mActionBarTop;
        return actionBarContainer != null && actionBarContainer.onStartNestedScroll(view, view2, i, i2);
    }

    @Override // miui.core.view.NestedScrollingParent2
    public void onStopNestedScroll(View view, int i) {
        ActionBarContainer actionBarContainer = this.mActionBarTop;
        if (actionBarContainer != null) {
            actionBarContainer.onStopNestedScroll(view, i);
        }
    }

    public void requestFitSystemWindows() {
        super.requestFitSystemWindows();
        this.mRequestFitSystemWindow = true;
    }

    public void setActionBar(ActionBar actionBar) {
        this.mActionBar = actionBar;
    }

    public void setAnimating(boolean z) {
        this.mAnimating = z;
    }

    public void setCallback(Window.Callback callback) {
        this.mCallback = callback;
    }

    public void setContentView(View view) {
        this.mContentView = view;
    }

    public void setOnStatusBarChangeListener(OnStatusBarChangeListener onStatusBarChangeListener) {
        this.mOnStatusBarChangeListener = onStatusBarChangeListener;
    }

    public void setOverlayMode(boolean z) {
        this.mOverlayMode = z;
    }

    public void setRootSubDecor(boolean z) {
        this.mRootSubDecor = z;
    }

    public void setTranslucentStatus(int i) {
        if (this.mTranslucentStatus != i) {
            this.mTranslucentStatus = i;
            requestFitSystemWindows();
        }
    }

    public boolean showContextMenuForChild(View view) {
        ContextMenuBuilder contextMenuBuilder = this.mContextMenu;
        if (contextMenuBuilder == null) {
            this.mContextMenu = new ContextMenuBuilder(getContext());
            this.mContextMenu.setCallback(this.mContextMenuCallback);
        } else {
            contextMenuBuilder.clear();
        }
        this.mContextMenuHelper = this.mContextMenu.show(view, view.getWindowToken());
        MenuDialogHelper menuDialogHelper = this.mContextMenuHelper;
        if (menuDialogHelper == null) {
            return super.showContextMenuForChild(view);
        }
        menuDialogHelper.setPresenterCallback(this.mContextMenuCallback);
        return true;
    }

    public boolean showContextMenuForChild(View view, float f, float f2) {
        if (internalShowContextMenu(view, f, f2)) {
            return true;
        }
        return getParent() != null && getParent().showContextMenuForChild(view, f, f2);
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        ActionMode actionMode2 = null;
        this.mActionMode = null;
        if (getCallback() != null) {
            actionMode2 = getCallback().onWindowStartingActionMode(createActionModeCallbackWrapper(callback));
        }
        if (actionMode2 != null) {
            this.mActionMode = actionMode2;
        }
        if (!(this.mActionMode == null || getCallback() == null)) {
            getCallback().onActionModeStarted(this.mActionMode);
        }
        return this.mActionMode;
    }

    public ActionMode startActionMode(View view, ActionMode.Callback callback) {
        if (!(view instanceof ActionBarOverlayLayout)) {
            return startActionMode(callback);
        }
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        this.mActionMode = view.startActionMode(createActionModeCallbackWrapper(callback));
        return this.mActionMode;
    }

    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback) {
        return startActionMode(view, callback);
    }
}
