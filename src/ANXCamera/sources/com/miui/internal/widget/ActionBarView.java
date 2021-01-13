package com.miui.internal.widget;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.CollapsibleActionView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import com.miui.internal.R;
import com.miui.internal.app.ActionBarDelegateImpl;
import com.miui.internal.util.DeviceHelper;
import com.miui.internal.util.FolmeAnimHelper;
import com.miui.internal.view.ActionBarPolicy;
import com.miui.internal.view.menu.ActionMenuItem;
import com.miui.internal.view.menu.ActionMenuPresenter;
import com.miui.internal.view.menu.ActionMenuView;
import com.miui.internal.view.menu.MenuBuilder;
import com.miui.internal.view.menu.MenuItemImpl;
import com.miui.internal.view.menu.MenuPresenter;
import com.miui.internal.view.menu.MenuView;
import com.miui.internal.view.menu.SubMenuBuilder;
import com.miui.internal.widget.AbsActionBarView;
import java.lang.ref.WeakReference;
import miui.animation.Folme;
import miui.animation.IStateStyle;
import miui.animation.base.AnimConfig;
import miui.animation.listener.TransitionListener;
import miui.animation.property.IIntValueProperty;
import miui.util.AttributeResolver;
import miui.util.ViewUtils;
import miui.view.springback.SpringBackLayout;

public class ActionBarView extends AbsActionBarView {
    private static final int DEFAULT_CUSTOM_GRAVITY = 8388627;
    public static final int DISPLAY_DEFAULT = 0;
    private static final int DISPLAY_RELAYOUT_MASK = 31;
    private static final int ICON_INITIALIZED = 1;
    private static final int LOGO_INITIALIZED = 2;
    private static final String TAG = "ActionBarView";
    private static final int TYPE_NON_TOUCH = 1;
    private static final int TYPE_TOUCH = 0;
    private ActionBar.OnNavigationListener mCallback;
    private FrameLayout mCollapseContainer;
    private AbsActionBarView.CollapseView mCollapseController = new AbsActionBarView.CollapseView();
    private FrameLayout mCollapseCustomContainer;
    private int mCollapseHeight;
    private int mCollapseSubtitleStyleRes;
    private TextView mCollapseSubtitleView;
    private ScrollingTabContainerView mCollapseTabs;
    private LinearLayout mCollapseTitleLayout;
    private boolean mCollapseTitleShowable = true;
    private int mCollapseTitleStyleRes;
    private TextView mCollapseTitleView;
    private Context mContext;
    private View mCustomNavView;
    private final TextWatcher mCustomTitleWatcher = new TextWatcher() {
        /* class com.miui.internal.widget.ActionBarView.AnonymousClass5 */

        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            ActionBarView.this.mExpandTitleView.setText(charSequence);
        }
    };
    private int mDisplayOptions = -1;
    private View mEndView;
    private Rect mExpandBounds = new Rect();
    private int mExpandSubtitleStyleRes;
    private TextView mExpandSubtitleView;
    private ScrollingTabContainerView mExpandTabs;
    private LinearLayout mExpandTitleLayout;
    private int mExpandTitleStyleRes;
    private TextView mExpandTitleView;
    View mExpandedActionView;
    private final View.OnClickListener mExpandedActionViewUpListener = new View.OnClickListener() {
        /* class com.miui.internal.widget.ActionBarView.AnonymousClass2 */

        public void onClick(View view) {
            MenuItemImpl menuItemImpl = ActionBarView.this.mExpandedMenuPresenter.mCurrentExpandedItem;
            if (menuItemImpl != null) {
                menuItemImpl.collapseActionView();
            }
        }
    };
    private HomeView mExpandedHomeLayout;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private Drawable mHomeAsUpIndicatorDrawable;
    private int mHomeAsUpIndicatorResId;
    private HomeView mHomeLayout;
    private final int mHomeResId;
    private Drawable mIcon;
    private int mIconLogoInitIndicator;
    private View mImmersionView;
    private boolean mInActionMode = false;
    private boolean mInSearchMode = false;
    private boolean mIncludeTabs;
    private ProgressBar mIndeterminateProgressView;
    private boolean mIsCollapsed;
    private int mItemPadding;
    private LinearLayout mListNavLayout;
    private Drawable mLogo;
    private ActionMenuItem mLogoNavItem;
    private FrameLayout mMovableContainer;
    private AbsActionBarView.CollapseView mMovableController = new AbsActionBarView.CollapseView();
    private final AdapterView.OnItemSelectedListener mNavItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        /* class com.miui.internal.widget.ActionBarView.AnonymousClass1 */

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
            if (ActionBarView.this.mCallback != null) {
                ActionBarView.this.mCallback.onNavigationItemSelected(i, j);
            }
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    private int mNavigationMode;
    private boolean mNonTouchScrolling = false;
    private MenuBuilder mOptionsMenu;
    private int mPendingHeight;
    private Runnable mPostScroll = new Runnable() {
        /* class com.miui.internal.widget.ActionBarView.AnonymousClass8 */

        public void run() {
            if (ActionBarView.this.mPostScroller.computeScrollOffset()) {
                ActionBarView actionBarView = ActionBarView.this;
                actionBarView.mPendingHeight = actionBarView.mPostScroller.getCurrY() - ActionBarView.this.mCollapseHeight;
                ActionBarView.this.requestLayout();
                if (!ActionBarView.this.mPostScroller.isFinished()) {
                    ActionBarView.this.postOnAnimation(this);
                } else if (ActionBarView.this.mPostScroller.getCurrY() == ActionBarView.this.mCollapseHeight) {
                    ActionBarView.this.setExpandState(0);
                } else if (ActionBarView.this.mPostScroller.getCurrY() == ActionBarView.this.mCollapseHeight + ActionBarView.this.mMovableContainer.getMeasuredHeight()) {
                    ActionBarView.this.setExpandState(1);
                }
            }
        }
    };
    private Scroller mPostScroller;
    private int mProgressBarPadding;
    private ProgressBar mProgressView;
    private Rect mSecondaryBounds = new Rect();
    private SpringBackLayout mSecondaryContainer;
    private AbsActionBarView.CollapseView mSecondaryController = new AbsActionBarView.CollapseView();
    private ScrollingTabContainerView mSecondaryTabs;
    private Spinner mSpinner;
    private SpinnerAdapter mSpinnerAdapter;
    private View mStartView;
    private IStateStyle mStateChangeAnimStateStyle = null;
    private CharSequence mSubtitle;
    private boolean mTempResizable;
    private CharSequence mTitle;
    private boolean mTitleCenter;
    private final View.OnClickListener mTitleClickListener = new View.OnClickListener() {
        /* class com.miui.internal.widget.ActionBarView.AnonymousClass4 */

        public void onClick(View view) {
            ActionBarView actionBarView = ActionBarView.this;
            actionBarView.mWindowCallback.onMenuItemSelected(0, actionBarView.mTitleNavItem);
        }
    };
    private ActionMenuItem mTitleNavItem;
    private View mTitleUpView;
    private boolean mTouchScrolling = false;
    private int mUncollapsePaddingH;
    private final View.OnClickListener mUpClickListener = new View.OnClickListener() {
        /* class com.miui.internal.widget.ActionBarView.AnonymousClass3 */

        public void onClick(View view) {
            ActionBarView actionBarView = ActionBarView.this;
            actionBarView.mWindowCallback.onMenuItemSelected(0, actionBarView.mLogoNavItem);
        }
    };
    private boolean mUserTitle;
    Window.Callback mWindowCallback;

    /* access modifiers changed from: private */
    public class ExpandedActionViewMenuPresenter implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;

        private ExpandedActionViewMenuPresenter() {
        }

        @Override // com.miui.internal.view.menu.MenuPresenter
        public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            View view = ActionBarView.this.mExpandedActionView;
            if (view instanceof CollapsibleActionView) {
                ((CollapsibleActionView) view).onActionViewCollapsed();
            }
            ActionBarView actionBarView = ActionBarView.this;
            actionBarView.removeView(actionBarView.mExpandedActionView);
            ActionBarView actionBarView2 = ActionBarView.this;
            actionBarView2.removeView(actionBarView2.mExpandedHomeLayout);
            ActionBarView actionBarView3 = ActionBarView.this;
            actionBarView3.mExpandedActionView = null;
            if ((actionBarView3.mDisplayOptions & 2) != 0) {
                ActionBarView.this.mHomeLayout.setVisibility(0);
            }
            if ((ActionBarView.this.mDisplayOptions & 8) != 0) {
                if (ActionBarView.this.mCollapseTitleLayout == null) {
                    ActionBarView.this.initTitle();
                } else {
                    ActionBarView.this.setTitleVisibility(true);
                }
            }
            if (ActionBarView.this.mCollapseTabs != null && ActionBarView.this.mNavigationMode == 2) {
                ActionBarView.this.mCollapseTabs.setVisibility(0);
            }
            if (ActionBarView.this.mExpandTabs != null && ActionBarView.this.mNavigationMode == 2) {
                ActionBarView.this.mExpandTabs.setVisibility(0);
            }
            if (ActionBarView.this.mSecondaryTabs != null && ActionBarView.this.mNavigationMode == 2) {
                ActionBarView.this.mSecondaryTabs.setVisibility(0);
            }
            if (ActionBarView.this.mSpinner != null && ActionBarView.this.mNavigationMode == 1) {
                ActionBarView.this.mSpinner.setVisibility(0);
            }
            if (!(ActionBarView.this.mCustomNavView == null || (ActionBarView.this.mDisplayOptions & 16) == 0)) {
                ActionBarView.this.mCustomNavView.setVisibility(0);
            }
            ActionBarView.this.mExpandedHomeLayout.setIcon(null);
            this.mCurrentExpandedItem = null;
            ActionBarView.this.requestLayout();
            menuItemImpl.setActionViewExpanded(false);
            return true;
        }

        @Override // com.miui.internal.view.menu.MenuPresenter
        public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            ActionBarView.this.mExpandedActionView = menuItemImpl.getActionView();
            ActionBarView.this.initExpandedHomeLayout();
            ActionBarView.this.mExpandedHomeLayout.setIcon(ActionBarView.this.getIcon().getConstantState().newDrawable(ActionBarView.this.getResources()));
            this.mCurrentExpandedItem = menuItemImpl;
            ViewParent parent = ActionBarView.this.mExpandedActionView.getParent();
            ActionBarView actionBarView = ActionBarView.this;
            if (parent != actionBarView) {
                actionBarView.addView(actionBarView.mExpandedActionView);
            }
            ViewParent parent2 = ActionBarView.this.mExpandedHomeLayout.getParent();
            ActionBarView actionBarView2 = ActionBarView.this;
            if (parent2 != actionBarView2) {
                actionBarView2.addView(actionBarView2.mExpandedHomeLayout);
            }
            if (ActionBarView.this.mHomeLayout != null) {
                ActionBarView.this.mHomeLayout.setVisibility(8);
            }
            if (ActionBarView.this.mCollapseTitleLayout != null) {
                ActionBarView.this.setTitleVisibility(false);
            }
            if (ActionBarView.this.mCollapseTabs != null) {
                ActionBarView.this.mCollapseTabs.setVisibility(8);
            }
            if (ActionBarView.this.mExpandTabs != null) {
                ActionBarView.this.mExpandTabs.setVisibility(8);
            }
            if (ActionBarView.this.mSecondaryTabs != null) {
                ActionBarView.this.mSecondaryTabs.setVisibility(8);
            }
            if (ActionBarView.this.mSpinner != null) {
                ActionBarView.this.mSpinner.setVisibility(8);
            }
            if (ActionBarView.this.mCustomNavView != null) {
                ActionBarView.this.mCustomNavView.setVisibility(8);
            }
            ActionBarView.this.requestLayout();
            menuItemImpl.setActionViewExpanded(true);
            View view = ActionBarView.this.mExpandedActionView;
            if (view instanceof CollapsibleActionView) {
                ((CollapsibleActionView) view).onActionViewExpanded();
            }
            return true;
        }

        @Override // com.miui.internal.view.menu.MenuPresenter
        public boolean flagActionItems() {
            return false;
        }

        @Override // com.miui.internal.view.menu.MenuPresenter
        public int getId() {
            return 0;
        }

        @Override // com.miui.internal.view.menu.MenuPresenter
        public MenuView getMenuView(ViewGroup viewGroup) {
            return null;
        }

        @Override // com.miui.internal.view.menu.MenuPresenter
        public void initForMenu(Context context, MenuBuilder menuBuilder) {
            MenuItemImpl menuItemImpl;
            MenuBuilder menuBuilder2 = this.mMenu;
            if (!(menuBuilder2 == null || (menuItemImpl = this.mCurrentExpandedItem) == null)) {
                menuBuilder2.collapseItemActionView(menuItemImpl);
            }
            this.mMenu = menuBuilder;
        }

        @Override // com.miui.internal.view.menu.MenuPresenter
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }

        @Override // com.miui.internal.view.menu.MenuPresenter
        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        @Override // com.miui.internal.view.menu.MenuPresenter
        public Parcelable onSaveInstanceState() {
            return null;
        }

        @Override // com.miui.internal.view.menu.MenuPresenter
        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            return false;
        }

        @Override // com.miui.internal.view.menu.MenuPresenter
        public void setCallback(MenuPresenter.Callback callback) {
        }

        @Override // com.miui.internal.view.menu.MenuPresenter
        public void updateMenuView(boolean z) {
            if (this.mCurrentExpandedItem != null) {
                MenuBuilder menuBuilder = this.mMenu;
                boolean z2 = false;
                if (menuBuilder != null) {
                    int size = menuBuilder.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            break;
                        } else if (this.mMenu.getItem(i) == this.mCurrentExpandedItem) {
                            z2 = true;
                            break;
                        } else {
                            i++;
                        }
                    }
                }
                if (!z2) {
                    collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static class HomeView extends FrameLayout {
        private Drawable mDefaultUpIndicator;
        private ImageView mIconView;
        private int mUpIndicatorRes;
        private ImageView mUpView;
        private int mUpWidth;

        public HomeView(Context context) {
            this(context, null);
        }

        public HomeView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            CharSequence contentDescription = getContentDescription();
            if (TextUtils.isEmpty(contentDescription)) {
                return true;
            }
            accessibilityEvent.getText().add(contentDescription);
            return true;
        }

        public int getStartOffset() {
            return 0;
        }

        /* access modifiers changed from: protected */
        public void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            int i = this.mUpIndicatorRes;
            if (i != 0) {
                setUpIndicator(i);
            }
        }

        /* access modifiers changed from: protected */
        public void onFinishInflate() {
            this.mUpView = (ImageView) findViewById(R.id.up);
            this.mIconView = (ImageView) findViewById(R.id.home);
            this.mDefaultUpIndicator = this.mUpView.getDrawable();
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int i5;
            int i6 = (i4 - i2) / 2;
            boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
            if (this.mUpView.getVisibility() != 8) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mUpView.getLayoutParams();
                int measuredHeight = this.mUpView.getMeasuredHeight();
                int measuredWidth = this.mUpView.getMeasuredWidth();
                int i7 = i6 - (measuredHeight / 2);
                ViewUtils.layoutChildView(this, this.mUpView, 0, i7, measuredWidth, i7 + measuredHeight);
                i5 = layoutParams.leftMargin + measuredWidth + layoutParams.rightMargin;
                if (isLayoutRtl) {
                    i3 -= i5;
                } else {
                    i += i5;
                }
            } else {
                i5 = 0;
            }
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
            int measuredHeight2 = this.mIconView.getMeasuredHeight();
            int measuredWidth2 = this.mIconView.getMeasuredWidth();
            int max = i5 + Math.max(layoutParams2.getMarginStart(), ((i3 - i) / 2) - (measuredWidth2 / 2));
            int max2 = Math.max(layoutParams2.topMargin, i6 - (measuredHeight2 / 2));
            ViewUtils.layoutChildView(this, this.mIconView, max, max2, max + measuredWidth2, max2 + measuredHeight2);
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int i, int i2) {
            measureChildWithMargins(this.mUpView, i, 0, i2, 0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mUpView.getLayoutParams();
            this.mUpWidth = layoutParams.leftMargin + this.mUpView.getMeasuredWidth() + layoutParams.rightMargin;
            int i3 = this.mUpView.getVisibility() == 8 ? 0 : this.mUpWidth;
            int measuredHeight = layoutParams.bottomMargin + layoutParams.topMargin + this.mUpView.getMeasuredHeight();
            measureChildWithMargins(this.mIconView, i, i3, i2, 0);
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
            int measuredWidth = i3 + layoutParams2.leftMargin + this.mIconView.getMeasuredWidth() + layoutParams2.rightMargin;
            int max = Math.max(measuredHeight, layoutParams2.topMargin + this.mIconView.getMeasuredHeight() + layoutParams2.bottomMargin);
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i);
            int size2 = View.MeasureSpec.getSize(i2);
            if (mode == Integer.MIN_VALUE) {
                measuredWidth = Math.min(measuredWidth, size);
            } else if (mode == 1073741824) {
                measuredWidth = size;
            }
            if (mode2 == Integer.MIN_VALUE) {
                max = Math.min(max, size2);
            } else if (mode2 == 1073741824) {
                max = size2;
            }
            setMeasuredDimension(measuredWidth, max);
        }

        public void setIcon(Drawable drawable) {
            this.mIconView.setImageDrawable(drawable);
        }

        public void setUp(boolean z) {
            this.mUpView.setVisibility(z ? 0 : 8);
        }

        public void setUpIndicator(int i) {
            this.mUpIndicatorRes = i;
            this.mUpView.setImageDrawable(i != 0 ? getResources().getDrawable(i) : null);
        }

        public void setUpIndicator(Drawable drawable) {
            ImageView imageView = this.mUpView;
            if (drawable == null) {
                drawable = this.mDefaultUpIndicator;
            }
            imageView.setImageDrawable(drawable);
            this.mUpIndicatorRes = 0;
        }
    }

    private static class InnerTransitionListener extends TransitionListener {
        private int mNewState;
        private WeakReference<ActionBarView> mRef;

        public InnerTransitionListener(ActionBarView actionBarView, int i) {
            this.mRef = new WeakReference<>(actionBarView);
            this.mNewState = i;
        }

        @Override // miui.animation.listener.TransitionListener
        public void onBegin(Object obj) {
            super.onBegin(obj);
            ActionBarView actionBarView = this.mRef.get();
            if (actionBarView != null) {
                actionBarView.mTempResizable = actionBarView.isResizable();
                actionBarView.setResizable(true);
                actionBarView.setExpandState(2);
                actionBarView.mCollapseController.setVisibility(4);
            }
        }

        @Override // miui.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            ActionBarView actionBarView = this.mRef.get();
            if (actionBarView != null) {
                actionBarView.setExpandState(this.mNewState);
                actionBarView.setResizable(actionBarView.mTempResizable);
                if (actionBarView.mInSearchMode) {
                    actionBarView.mCollapseController.setVisibility(4);
                } else {
                    actionBarView.mCollapseController.setVisibility(0);
                }
            }
        }

        @Override // miui.animation.listener.TransitionListener
        public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
            super.onUpdate(obj, iIntValueProperty, i, f, z);
            ActionBarView actionBarView = this.mRef.get();
            if (actionBarView != null) {
                actionBarView.mPendingHeight = i;
                actionBarView.requestLayout();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            /* class com.miui.internal.widget.ActionBarView.SavedState.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int expandedMenuItemId;
        boolean isOverflowOpen;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.expandedMenuItemId = parcel.readInt();
            this.isOverflowOpen = parcel.readInt() != 0;
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.expandedMenuItemId);
            parcel.writeInt(this.isOverflowOpen ? 1 : 0);
        }
    }

    public ActionBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.mPostScroller = new Scroller(context);
        this.mInActionMode = false;
        this.mInSearchMode = false;
        this.mUncollapsePaddingH = context.getResources().getDimensionPixelOffset(R.dimen.action_bar_title_horizontal_padding);
        this.mCollapseContainer = new FrameLayout(context);
        this.mCollapseContainer.setId(R.id.action_bar_collapse_container);
        this.mCollapseContainer.setForegroundGravity(17);
        this.mCollapseContainer.setVisibility(0);
        this.mMovableContainer = new FrameLayout(context);
        this.mMovableContainer.setId(R.id.action_bar_movable_container);
        this.mMovableContainer.setPaddingRelative(this.mUncollapsePaddingH, context.getResources().getDimensionPixelOffset(R.dimen.action_bar_title_top_padding), this.mUncollapsePaddingH, context.getResources().getDimensionPixelOffset(R.dimen.action_bar_title_bottom_padding));
        this.mMovableContainer.setVisibility(0);
        this.mSecondaryContainer = new SpringBackLayout(context);
        this.mSecondaryContainer.setId(R.id.action_bar_secondary_container);
        this.mSecondaryContainer.setScrollOrientation(1);
        this.mSecondaryContainer.setVisibility(0);
        this.mCollapseController.attach(this.mCollapseContainer);
        this.mMovableController.attach(this.mMovableContainer);
        setBackgroundResource(0);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, miui.R.styleable.ActionBar, 16843470, 0);
        this.mNavigationMode = obtainStyledAttributes.getInt(miui.R.styleable.ActionBar_android_navigationMode, 0);
        this.mTitle = obtainStyledAttributes.getText(miui.R.styleable.ActionBar_android_title);
        this.mSubtitle = obtainStyledAttributes.getText(miui.R.styleable.ActionBar_android_subtitle);
        this.mTitleCenter = obtainStyledAttributes.getBoolean(R.styleable.ActionBar_titleCenter, false);
        this.mLogo = obtainStyledAttributes.getDrawable(miui.R.styleable.ActionBar_android_logo);
        this.mIcon = obtainStyledAttributes.getDrawable(miui.R.styleable.ActionBar_android_icon);
        LayoutInflater from = LayoutInflater.from(context);
        this.mHomeResId = obtainStyledAttributes.getResourceId(miui.R.styleable.ActionBar_android_homeLayout, R.layout.action_bar_home);
        this.mCollapseTitleStyleRes = obtainStyledAttributes.getResourceId(miui.R.styleable.ActionBar_android_titleTextStyle, 0);
        this.mCollapseSubtitleStyleRes = obtainStyledAttributes.getResourceId(miui.R.styleable.ActionBar_android_subtitleTextStyle, 0);
        this.mExpandTitleStyleRes = obtainStyledAttributes.getResourceId(miui.R.styleable.ActionBar_miui_expandTitleTextStyle, 0);
        this.mExpandSubtitleStyleRes = obtainStyledAttributes.getResourceId(miui.R.styleable.ActionBar_miui_expandSubtitleTextStyle, 0);
        this.mProgressBarPadding = obtainStyledAttributes.getDimensionPixelOffset(miui.R.styleable.ActionBar_android_progressBarPadding, 0);
        this.mItemPadding = obtainStyledAttributes.getDimensionPixelOffset(miui.R.styleable.ActionBar_android_itemPadding, 0);
        setDisplayOptions(obtainStyledAttributes.getInt(miui.R.styleable.ActionBar_android_displayOptions, 0));
        int resourceId = obtainStyledAttributes.getResourceId(miui.R.styleable.ActionBar_android_customNavigationLayout, 0);
        if (resourceId != 0) {
            this.mCustomNavView = from.inflate(resourceId, (ViewGroup) this, false);
            this.mNavigationMode = 0;
        }
        this.mContentHeight = obtainStyledAttributes.getLayoutDimension(miui.R.styleable.ActionBar_android_height, 0);
        obtainStyledAttributes.recycle();
        this.mLogoNavItem = new ActionMenuItem(context, 0, 16908332, 0, 0, this.mTitle);
        this.mTitleNavItem = new ActionMenuItem(context, 0, 16908310, 0, 0, this.mTitle);
    }

    private void addTabsContainer(ScrollingTabContainerView scrollingTabContainerView, ScrollingTabContainerView scrollingTabContainerView2, ScrollingTabContainerView scrollingTabContainerView3) {
        this.mCollapseTabs = scrollingTabContainerView;
        this.mExpandTabs = scrollingTabContainerView2;
        this.mSecondaryTabs = scrollingTabContainerView3;
        if (this.mCollapseContainer.getChildCount() == 0) {
            this.mCollapseTabs.setVisibility(0);
            this.mCollapseContainer.addView(this.mCollapseTabs);
            this.mMovableContainer.removeAllViews();
            this.mExpandTabs.setVisibility(0);
            this.mMovableContainer.addView(this.mExpandTabs);
            this.mSecondaryContainer.removeAllViews();
        } else if (this.mCollapseContainer.getChildCount() == 1) {
            ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(this.mContext);
            View childAt = this.mCollapseContainer.getChildAt(0);
            if (actionBarPolicy.isTightTitle() || (childAt instanceof ScrollingTabContainerView)) {
                this.mCollapseContainer.removeAllViews();
                this.mCollapseContainer.addView(this.mCollapseTabs);
                this.mMovableContainer.removeAllViews();
                this.mMovableContainer.addView(this.mExpandTabs);
            } else {
                this.mSecondaryContainer.removeAllViews();
                this.mSecondaryContainer.addView(this.mSecondaryTabs);
                this.mSecondaryContainer.setTarget(this.mSecondaryTabs);
                this.mSecondaryController.attach(this.mSecondaryTabs);
            }
        }
        ViewGroup.LayoutParams layoutParams = this.mCollapseTabs.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = -2;
            layoutParams.height = -1;
        }
        ViewGroup.LayoutParams layoutParams2 = this.mExpandTabs.getLayoutParams();
        if (layoutParams2 != null) {
            layoutParams2.width = -2;
            layoutParams2.height = -1;
        }
        ViewGroup.LayoutParams layoutParams3 = this.mSecondaryTabs.getLayoutParams();
        if (layoutParams3 != null) {
            layoutParams3.width = -2;
            layoutParams3.height = -1;
        }
        updateTightTitle();
        updateSandwichView();
    }

    private void addTitleView(View view, View view2) {
        if (freeCollapseContainer(false)) {
            this.mCollapseContainer.addView(view);
            this.mMovableContainer.addView(view2);
        }
    }

    private void addedCustomView() {
        FrameLayout frameLayout = (FrameLayout) this.mCustomNavView.findViewById(R.id.action_bar_expand_container);
        TextView customTitleView = getCustomTitleView(frameLayout);
        if (customTitleView != null) {
            freeCollapseContainer(true);
            this.mCollapseCustomContainer = frameLayout;
            this.mCollapseController.attach(this.mCollapseCustomContainer);
            this.mExpandTitleView.setText(customTitleView.getText());
            this.mExpandTitleView.setVisibility(0);
            this.mExpandTitleLayout.setVisibility(0);
            this.mExpandSubtitleView.setVisibility(8);
            this.mMovableContainer.addView(this.mExpandTitleLayout);
            customTitleView.addTextChangedListener(this.mCustomTitleWatcher);
        }
    }

    private void animateLayoutWithProcess(float f) {
        float f2 = 1.0f;
        float min = 1.0f - Math.min(1.0f, 3.0f * f);
        int i = this.mInnerExpandState;
        if (i == 2) {
            if (min > 0.0f) {
                this.mCollapseController.animTo(0.0f, 0, 20, this.mCollapseAnimHideConfig);
            } else {
                this.mCollapseController.animTo(1.0f, 0, 0, this.mCollapseAnimShowConfig);
            }
            if (this.mInActionMode) {
                min = 0.0f;
            }
            this.mMovableController.animTo(min, 0, 0, this.mMovableAnimConfig);
            FrameLayout frameLayout = this.mMovableContainer;
            if (frameLayout != null) {
                frameLayout.setVisibility(f < 1.0f ? 0 : 4);
            }
        } else if (i == 1) {
            this.mCollapseController.setAnimFrom(0.0f, 0, 20);
            if (this.mInActionMode) {
                f2 = 0.0f;
            }
            this.mMovableController.setAnimFrom(f2, 0, 0);
        } else if (i == 0) {
            this.mCollapseController.setAnimFrom(1.0f, 0, 0);
            this.mMovableController.setAnimFrom(0.0f, 0, 0);
        }
        int width = (int) (((float) (((getWidth() - this.mSecondaryContainer.getMeasuredWidth()) / 2) - this.mUncollapsePaddingH)) * f);
        if (ViewUtils.isLayoutRtl(this)) {
            int i2 = -width;
            if (i2 <= 0) {
                this.mSecondaryController.setTransparent(i2, 0);
            }
        } else if (width >= 0) {
            this.mSecondaryController.setTransparent(width, 0);
        }
    }

    private boolean canTitleBeShown() {
        TextView textView = this.mCollapseTitleView;
        if (textView == null || this.mTitle == null) {
            return false;
        }
        return (!isResizable() && getExpandState() == 0) || textView.getPaint().measureText(this.mTitle.toString()) <= ((float) this.mCollapseTitleView.getMeasuredWidth());
    }

    private int computeTitleCenterLayoutStart(View view) {
        int width = (getWidth() - view.getMeasuredWidth()) / 2;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int i = 0;
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            i = 0 + ((LinearLayout.LayoutParams) layoutParams).getMarginStart();
        }
        return width - i;
    }

    private void configPresenters(MenuBuilder menuBuilder) {
        if (menuBuilder != null) {
            menuBuilder.addMenuPresenter(this.mActionMenuPresenter);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter);
        } else {
            this.mActionMenuPresenter.initForMenu(this.mContext, null);
            this.mExpandedMenuPresenter.initForMenu(this.mContext, null);
        }
        this.mActionMenuPresenter.updateMenuView(true);
        this.mExpandedMenuPresenter.updateMenuView(true);
    }

    private boolean freeCollapseContainer(boolean z) {
        if (this.mCollapseContainer.getChildCount() == 1) {
            ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(this.mContext);
            View childAt = this.mCollapseContainer.getChildAt(0);
            if (!(childAt instanceof ScrollingTabContainerView)) {
                childAt.setVisibility(8);
            } else if (!z && actionBarPolicy.isTightTitle()) {
                return false;
            } else {
                this.mCollapseContainer.removeAllViews();
                this.mSecondaryContainer.removeAllViews();
                this.mSecondaryContainer.addView(this.mSecondaryTabs);
                this.mSecondaryContainer.setTarget(this.mSecondaryTabs);
                this.mSecondaryController.attach(this.mSecondaryTabs);
            }
        }
        this.mMovableContainer.removeAllViews();
        removeView(this.mCollapseContainer);
        return true;
    }

    private ProgressBar getCircularProgressBar() {
        ProgressBar progressBar = this.mIndeterminateProgressView;
        if (progressBar != null) {
            progressBar.setVisibility(4);
        }
        return progressBar;
    }

    private TextView getCustomTitleView(View view) {
        if (view != null) {
            return (TextView) view.findViewById(16908310);
        }
        return null;
    }

    private ProgressBar getHorizontalProgressBar() {
        ProgressBar progressBar = this.mProgressView;
        if (progressBar != null) {
            progressBar.setVisibility(4);
        }
        return progressBar;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private Drawable getIcon() {
        if ((this.mIconLogoInitIndicator & 1) != 1) {
            Context context = this.mContext;
            if (context instanceof Activity) {
                try {
                    this.mIcon = context.getPackageManager().getActivityIcon(((Activity) this.mContext).getComponentName());
                } catch (PackageManager.NameNotFoundException e2) {
                    Log.e(TAG, "Activity component name not found!", e2);
                }
            }
            if (this.mIcon == null) {
                this.mIcon = this.mContext.getApplicationInfo().loadIcon(this.mContext.getPackageManager());
            }
            this.mIconLogoInitIndicator |= 1;
        }
        return this.mIcon;
    }

    private Drawable getLogo() {
        if ((this.mIconLogoInitIndicator & 2) != 2) {
            if (Build.VERSION.SDK_INT >= 9) {
                Context context = this.mContext;
                if (context instanceof Activity) {
                    try {
                        this.mLogo = context.getPackageManager().getActivityLogo(((Activity) this.mContext).getComponentName());
                    } catch (PackageManager.NameNotFoundException e2) {
                        Log.e(TAG, "Activity component name not found!", e2);
                    }
                }
                if (this.mLogo == null) {
                    this.mLogo = this.mContext.getApplicationInfo().loadLogo(this.mContext.getPackageManager());
                }
            }
            this.mIconLogoInitIndicator |= 2;
        }
        return this.mLogo;
    }

    private void hideProgressBars(ProgressBar progressBar, ProgressBar progressBar2) {
        if (progressBar2 != null && progressBar2.getVisibility() == 0) {
            progressBar2.setVisibility(4);
        }
        if (progressBar != null && progressBar.getVisibility() == 0) {
            progressBar.setVisibility(4);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initExpandedHomeLayout() {
        if (this.mExpandedHomeLayout == null) {
            this.mExpandedHomeLayout = (HomeView) LayoutInflater.from(this.mContext).inflate(this.mHomeResId, (ViewGroup) this, false);
            this.mExpandedHomeLayout.setUp(true);
            this.mExpandedHomeLayout.setOnClickListener(this.mExpandedActionViewUpListener);
        }
    }

    private void initHomeLayout() {
        if (this.mHomeLayout == null) {
            this.mHomeLayout = (HomeView) LayoutInflater.from(this.mContext).inflate(this.mHomeResId, (ViewGroup) this, false);
            this.mHomeLayout.setOnClickListener(this.mUpClickListener);
            this.mHomeLayout.setClickable(true);
            this.mHomeLayout.setFocusable(true);
            int i = this.mHomeAsUpIndicatorResId;
            if (i != 0) {
                this.mHomeLayout.setUpIndicator(i);
                this.mHomeAsUpIndicatorResId = 0;
            }
            Drawable drawable = this.mHomeAsUpIndicatorDrawable;
            if (drawable != null) {
                this.mHomeLayout.setUpIndicator(drawable);
                this.mHomeAsUpIndicatorDrawable = null;
            }
            addView(this.mHomeLayout);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initTitle() {
        if (this.mTitleUpView == null) {
            this.mTitleUpView = LayoutInflater.from(getContext()).inflate(R.layout.action_bar_title_up, (ViewGroup) this, false);
            this.mTitleUpView.setOnClickListener(this.mUpClickListener);
        }
        addView(this.mTitleUpView);
        if (this.mCollapseTitleLayout == null) {
            LayoutInflater from = LayoutInflater.from(getContext());
            this.mCollapseTitleLayout = (LinearLayout) from.inflate(R.layout.action_bar_title_item, (ViewGroup) null, false);
            this.mExpandTitleLayout = (LinearLayout) from.inflate(R.layout.action_bar_title_expand_item, (ViewGroup) null, false);
            this.mCollapseTitleView = (TextView) this.mCollapseTitleLayout.findViewById(R.id.action_bar_title);
            this.mExpandTitleView = (TextView) this.mExpandTitleLayout.findViewById(R.id.action_bar_title);
            this.mCollapseSubtitleView = (TextView) this.mCollapseTitleLayout.findViewById(R.id.action_bar_subtitle);
            this.mExpandSubtitleView = (TextView) this.mExpandTitleLayout.findViewById(R.id.action_bar_subtitle);
            this.mCollapseTitleLayout.setOnClickListener(this.mTitleClickListener);
            this.mExpandTitleLayout.setOnClickListener(this.mTitleClickListener);
            int i = this.mCollapseTitleStyleRes;
            if (i != 0) {
                this.mCollapseTitleView.setTextAppearance(this.mContext, i);
            }
            int i2 = this.mExpandTitleStyleRes;
            if (i2 != 0) {
                this.mExpandTitleView.setTextAppearance(this.mContext, i2);
            }
            CharSequence charSequence = this.mTitle;
            if (charSequence != null) {
                this.mCollapseTitleView.setText(charSequence);
                this.mExpandTitleView.setText(this.mTitle);
            }
            int i3 = this.mCollapseSubtitleStyleRes;
            if (i3 != 0) {
                this.mCollapseSubtitleView.setTextAppearance(this.mContext, i3);
            }
            int i4 = this.mExpandSubtitleStyleRes;
            if (i4 != 0) {
                this.mExpandSubtitleView.setTextAppearance(this.mContext, i4);
            }
            CharSequence charSequence2 = this.mSubtitle;
            if (charSequence2 != null) {
                this.mCollapseSubtitleView.setText(charSequence2);
                this.mCollapseSubtitleView.setVisibility(0);
                this.mExpandSubtitleView.setText(this.mSubtitle);
                this.mExpandSubtitleView.setVisibility(0);
            }
            int i5 = 4;
            boolean z = true;
            boolean z2 = (this.mDisplayOptions & 4) != 0;
            boolean z3 = (this.mDisplayOptions & 2) != 0;
            View view = this.mTitleUpView;
            if (z3) {
                i5 = 8;
            } else if (z2) {
                i5 = 0;
            }
            view.setVisibility(i5);
            this.mTitleUpView.setEnabled(z2 && !z3);
            this.mCollapseTitleLayout.setEnabled(z2 && !z3);
            LinearLayout linearLayout = this.mExpandTitleLayout;
            if (!z2 || z3) {
                z = false;
            }
            linearLayout.setEnabled(z);
            updateTightTitle();
        }
        addTitleView(this.mCollapseTitleLayout, this.mExpandTitleLayout);
        post(new Runnable() {
            /* class com.miui.internal.widget.ActionBarView.AnonymousClass7 */

            public void run() {
                if (ActionBarView.this.mCollapseTitleLayout != null) {
                    Rect rect = new Rect();
                    ActionBarView.this.mCollapseTitleLayout.getHitRect(rect);
                    rect.left -= AttributeResolver.resolveDimensionPixelSize(ActionBarView.this.getContext(), miui.R.attr.actionBarPaddingStart);
                    ActionBarView actionBarView = ActionBarView.this;
                    actionBarView.setTouchDelegate(new TouchDelegate(rect, actionBarView.mCollapseTitleLayout));
                }
            }
        });
        if (this.mExpandedActionView != null || (TextUtils.isEmpty(this.mTitle) && TextUtils.isEmpty(this.mSubtitle))) {
            setTitleVisibility(false);
        }
        updateSandwichView();
    }

    private boolean isShowTitle() {
        return (this.mCollapseContainer.getChildCount() == 1 && this.mCollapseContainer.getChildAt(0).getVisibility() != 8) || !(this.mCustomNavView == null || this.mCollapseCustomContainer == null);
    }

    private boolean isSimpleCustomNavView() {
        View view = this.mCustomNavView;
        if (view == null || view.getVisibility() != 0) {
            return true;
        }
        ViewGroup.LayoutParams layoutParams = this.mCustomNavView.getLayoutParams();
        ActionBar.LayoutParams layoutParams2 = layoutParams instanceof ActionBar.LayoutParams ? (ActionBar.LayoutParams) layoutParams : null;
        return layoutParams2 != null && normalizeHorizontalGravity(layoutParams2.gravity, ViewUtils.isLayoutRtl(this)) == 8388613;
    }

    private boolean isTitleCenter() {
        HomeView homeView;
        return this.mTitleCenter && isSimpleCustomNavView() && ((homeView = this.mHomeLayout) == null || homeView.getVisibility() == 8) && !isTightTitleWithEmbeddedTabs();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        return androidx.core.view.GravityCompat.START;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0012, code lost:
        if (r4 != false) goto L_0x0014;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001b, code lost:
        if (r4 != false) goto L_0x0016;
     */
    private int normalizeHorizontalGravity(int i, boolean z) {
        int i2 = 8388615 & i;
        if ((8388608 & i2) != 0) {
            return i2;
        }
        if (i2 != 3) {
            if (i2 != 5) {
                return i2;
            }
        }
        return GravityCompat.END;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:106:0x01d9, code lost:
        if (r4 == -1) goto L_0x01db;
     */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x01c9  */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x01d9  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x01e6  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x01ef  */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x01f9  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x01ff  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0221  */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x023c  */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x0243  */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x0246  */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x024c  */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x0259  */
    /* JADX WARNING: Removed duplicated region for block: B:140:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0139  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x0199  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x019c  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x019f  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01a2  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x01ab  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x01ba  */
    private void onLayoutCollapseViews(boolean z, int i, int i2, int i3, int i4) {
        int paddingEnd;
        View view;
        View view2;
        ProgressBar progressBar;
        ActionBar.LayoutParams layoutParams;
        int i5;
        int i6;
        int i7;
        int i8;
        int normalizeHorizontalGravity;
        int i9;
        int paddingStart = getPaddingStart();
        int paddingTop = getPaddingTop();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop2 = (i4 - getPaddingTop()) - getPaddingBottom();
        if (paddingTop2 > 0) {
            HomeView homeView = this.mExpandedActionView != null ? this.mExpandedHomeLayout : this.mHomeLayout;
            if (homeView == null || homeView.getVisibility() == 8) {
                View view3 = this.mStartView;
                if (view3 != null) {
                    i9 = positionChild(view3, paddingStart, paddingTop, paddingTop2);
                }
                if (this.mExpandedActionView == null) {
                    boolean isShowTitle = isShowTitle();
                    if (isShowTitle) {
                        View view4 = this.mTitleUpView;
                        if (view4 != null) {
                            paddingStart += positionChild(view4, paddingStart, paddingTop, paddingTop2);
                        }
                        if (isTitleCenter()) {
                            FrameLayout frameLayout = this.mCollapseContainer;
                            int computeTitleCenterLayoutStart = (frameLayout == null || frameLayout.getVisibility() == 8) ? paddingStart : computeTitleCenterLayoutStart(this.mCollapseContainer);
                            this.mCollapseTitleShowable = canTitleBeShown();
                            updateTightTitle();
                            int measuredWidth = computeTitleCenterLayoutStart + this.mCollapseContainer.getMeasuredWidth();
                            int measuredHeight = this.mCollapseContainer.getMeasuredHeight();
                            int i10 = paddingTop + ((paddingTop2 - measuredHeight) / 2);
                            ViewUtils.layoutChildView(this, this.mCollapseContainer, computeTitleCenterLayoutStart, i10, measuredWidth, i10 + measuredHeight);
                            paddingStart = paddingStart;
                        } else {
                            paddingStart = positionChild(this.mCollapseContainer, paddingStart, paddingTop, paddingTop2) + paddingStart;
                        }
                    }
                    int i11 = this.mNavigationMode;
                    if (i11 != 0) {
                        if (i11 != 1) {
                            if (i11 == 2) {
                                ScrollingTabContainerView scrollingTabContainerView = (this.mCollapseContainer.getChildCount() != 1 || !(this.mCollapseContainer.getChildAt(0) instanceof ScrollingTabContainerView)) ? null : (ScrollingTabContainerView) this.mCollapseContainer.getChildAt(0);
                                if (scrollingTabContainerView != null) {
                                    int measuredWidth2 = scrollingTabContainerView.getMeasuredWidth();
                                    int measuredHeight2 = scrollingTabContainerView.getMeasuredHeight();
                                    int width = (getWidth() - measuredWidth2) / 2;
                                    int width2 = (getWidth() + measuredWidth2) / 2;
                                    if (isShowTitle) {
                                        int i12 = this.mItemPadding;
                                        width += i12;
                                        width2 -= i12;
                                    }
                                    paddingStart = width2;
                                    this.mCollapseContainer.layout(isLayoutRtl ? getWidth() - paddingStart : width, paddingTop, isLayoutRtl ? getWidth() - width : paddingStart, measuredHeight2 + paddingTop);
                                }
                            }
                        } else if (this.mListNavLayout != null) {
                            if (isShowTitle) {
                                paddingStart += this.mItemPadding;
                            }
                            paddingStart += positionChild(this.mListNavLayout, paddingStart, paddingTop, paddingTop2) + this.mItemPadding;
                        }
                    }
                }
                paddingEnd = (i3 - i) - getPaddingEnd();
                ActionMenuView actionMenuView = this.mMenuView;
                if (actionMenuView != null && actionMenuView.getParent() == this) {
                    positionChildInverse(this.mMenuView, paddingEnd, paddingTop, paddingTop2);
                    paddingEnd -= this.mMenuView.getMeasuredWidth();
                }
                view = this.mEndView;
                if (view != null) {
                    positionChildInverse(view, paddingEnd, paddingTop, paddingTop2);
                    paddingEnd -= this.mEndView.getMeasuredWidth();
                }
                ProgressBar progressBar2 = this.mIndeterminateProgressView;
                if (!(progressBar2 == null || progressBar2.getVisibility() == 8)) {
                    positionChildInverse(this.mIndeterminateProgressView, paddingEnd - this.mProgressBarPadding, paddingTop, paddingTop2);
                    paddingEnd -= this.mIndeterminateProgressView.getMeasuredWidth() - (this.mProgressBarPadding * 2);
                }
                View view5 = this.mImmersionView;
                if (!(view5 == null || view5.getVisibility() == 8)) {
                    positionChildInverse(this.mImmersionView, paddingEnd, paddingTop, paddingTop2);
                    paddingEnd -= this.mImmersionView.getMeasuredWidth();
                }
                view2 = this.mExpandedActionView;
                if (view2 == null && ((this.mDisplayOptions & 16) == 0 || (view2 = this.mCustomNavView) == null)) {
                    view2 = null;
                }
                if (!(view2 == null || view2.getVisibility() == 8)) {
                    ViewGroup.LayoutParams layoutParams2 = view2.getLayoutParams();
                    layoutParams = !(layoutParams2 instanceof ActionBar.LayoutParams) ? (ActionBar.LayoutParams) layoutParams2 : null;
                    i5 = layoutParams == null ? layoutParams.gravity : DEFAULT_CUSTOM_GRAVITY;
                    int measuredWidth3 = view2.getMeasuredWidth();
                    if (layoutParams == null) {
                        paddingStart += layoutParams.getMarginStart();
                        paddingEnd -= layoutParams.getMarginEnd();
                        i6 = layoutParams.topMargin;
                        i7 = layoutParams.bottomMargin;
                    } else {
                        i7 = 0;
                        i6 = 0;
                    }
                    i8 = 8388615 & i5;
                    if (i8 != 1) {
                        int width3 = (getWidth() - measuredWidth3) / 2;
                        if (width3 >= paddingStart) {
                            if (width3 + measuredWidth3 > paddingEnd) {
                                i8 = 8388613;
                            }
                            int paddingStart2 = getPaddingStart();
                            normalizeHorizontalGravity = normalizeHorizontalGravity(i8, isLayoutRtl);
                            if (normalizeHorizontalGravity == 1) {
                                paddingStart = (getWidth() - measuredWidth3) / 2;
                            } else if (normalizeHorizontalGravity != 8388611) {
                                paddingStart = normalizeHorizontalGravity != 8388613 ? paddingStart2 : paddingEnd - measuredWidth3;
                            }
                            int i13 = i5 & 112;
                            if (i5 == -1) {
                                i13 = 16;
                            }
                            int height = i13 != 16 ? i13 != 48 ? i13 != 80 ? 0 : ((getHeight() - getPaddingBottom()) - view2.getMeasuredHeight()) - i7 : getPaddingTop() + i6 : (((getHeight() - getPaddingBottom()) - getPaddingTop()) - view2.getMeasuredHeight()) / 2;
                            int measuredWidth4 = view2.getMeasuredWidth();
                            view2.layout(isLayoutRtl ? (getWidth() - paddingStart) - measuredWidth4 : paddingStart, height, isLayoutRtl ? getWidth() - paddingStart : measuredWidth4 + paddingStart, view2.getMeasuredHeight() + height);
                        }
                    }
                    i8 = 8388611;
                    int paddingStart22 = getPaddingStart();
                    normalizeHorizontalGravity = normalizeHorizontalGravity(i8, isLayoutRtl);
                    if (normalizeHorizontalGravity == 1) {
                    }
                    int i132 = i5 & 112;
                    if (i5 == -1) {
                    }
                    if (i132 != 16) {
                    }
                    int measuredWidth42 = view2.getMeasuredWidth();
                    view2.layout(isLayoutRtl ? (getWidth() - paddingStart) - measuredWidth42 : paddingStart, height, isLayoutRtl ? getWidth() - paddingStart : measuredWidth42 + paddingStart, view2.getMeasuredHeight() + height);
                }
                progressBar = this.mProgressView;
                if (progressBar == null) {
                    progressBar.bringToFront();
                    int measuredHeight3 = this.mProgressView.getMeasuredHeight() / 2;
                    ProgressBar progressBar3 = this.mProgressView;
                    int i14 = this.mProgressBarPadding;
                    progressBar3.layout(i14, -measuredHeight3, progressBar3.getMeasuredWidth() + i14, measuredHeight3);
                    return;
                }
                return;
            }
            int startOffset = homeView.getStartOffset();
            i9 = positionChild(homeView, paddingStart + startOffset, paddingTop, paddingTop2) + startOffset;
            paddingStart += i9;
            if (this.mExpandedActionView == null) {
            }
            paddingEnd = (i3 - i) - getPaddingEnd();
            ActionMenuView actionMenuView2 = this.mMenuView;
            positionChildInverse(this.mMenuView, paddingEnd, paddingTop, paddingTop2);
            paddingEnd -= this.mMenuView.getMeasuredWidth();
            view = this.mEndView;
            if (view != null) {
            }
            ProgressBar progressBar22 = this.mIndeterminateProgressView;
            positionChildInverse(this.mIndeterminateProgressView, paddingEnd - this.mProgressBarPadding, paddingTop, paddingTop2);
            paddingEnd -= this.mIndeterminateProgressView.getMeasuredWidth() - (this.mProgressBarPadding * 2);
            View view52 = this.mImmersionView;
            positionChildInverse(this.mImmersionView, paddingEnd, paddingTop, paddingTop2);
            paddingEnd -= this.mImmersionView.getMeasuredWidth();
            view2 = this.mExpandedActionView;
            view2 = null;
            ViewGroup.LayoutParams layoutParams22 = view2.getLayoutParams();
            if (!(layoutParams22 instanceof ActionBar.LayoutParams)) {
            }
            if (layoutParams == null) {
            }
            int measuredWidth32 = view2.getMeasuredWidth();
            if (layoutParams == null) {
            }
            i8 = 8388615 & i5;
            if (i8 != 1) {
            }
            i8 = 8388611;
            int paddingStart222 = getPaddingStart();
            normalizeHorizontalGravity = normalizeHorizontalGravity(i8, isLayoutRtl);
            if (normalizeHorizontalGravity == 1) {
            }
            int i1322 = i5 & 112;
            if (i5 == -1) {
            }
            if (i1322 != 16) {
            }
            int measuredWidth422 = view2.getMeasuredWidth();
            view2.layout(isLayoutRtl ? (getWidth() - paddingStart) - measuredWidth422 : paddingStart, height, isLayoutRtl ? getWidth() - paddingStart : measuredWidth422 + paddingStart, view2.getMeasuredHeight() + height);
            progressBar = this.mProgressView;
            if (progressBar == null) {
            }
        }
    }

    private void setTitleImpl(CharSequence charSequence) {
        this.mTitle = charSequence;
        TextView textView = this.mCollapseTitleView;
        if (textView != null) {
            textView.setText(charSequence);
            this.mExpandTitleView.setText(charSequence);
            setTitleVisibility(this.mExpandedActionView == null && (this.mDisplayOptions & 8) != 0 && (!TextUtils.isEmpty(this.mTitle) || !TextUtils.isEmpty(this.mSubtitle)));
        }
        ActionMenuItem actionMenuItem = this.mLogoNavItem;
        if (actionMenuItem != null) {
            actionMenuItem.setTitle(charSequence);
        }
        ActionMenuItem actionMenuItem2 = this.mTitleNavItem;
        if (actionMenuItem2 != null) {
            actionMenuItem2.setTitle(charSequence);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setTitleVisibility(boolean z) {
        LinearLayout linearLayout = this.mCollapseTitleLayout;
        if (linearLayout != null) {
            linearLayout.setVisibility(z ? 0 : 8);
        }
        LinearLayout linearLayout2 = this.mExpandTitleLayout;
        if (linearLayout2 != null) {
            linearLayout2.setVisibility(z ? 0 : 8);
        }
        View view = this.mTitleUpView;
        if (view != null) {
            if (z) {
                int i = 4;
                boolean z2 = true;
                boolean z3 = (this.mDisplayOptions & 4) != 0;
                if ((this.mDisplayOptions & 2) == 0) {
                    z2 = false;
                }
                View view2 = this.mTitleUpView;
                if (z2) {
                    i = 8;
                } else if (z3) {
                    i = 0;
                }
                view2.setVisibility(i);
            } else {
                view.setVisibility(8);
            }
        }
        updateSandwichView();
    }

    private void showProgressBars(ProgressBar progressBar, ProgressBar progressBar2) {
        if (progressBar2 != null && progressBar2.getVisibility() == 4) {
            progressBar2.setVisibility(0);
        }
        if (progressBar != null && progressBar.getProgress() < 10000) {
            progressBar.setVisibility(0);
        }
    }

    private void updateProgressBars(int i) {
        ProgressBar circularProgressBar = getCircularProgressBar();
        ProgressBar horizontalProgressBar = getHorizontalProgressBar();
        if (i == -1) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setVisibility((horizontalProgressBar.isIndeterminate() || horizontalProgressBar.getProgress() < 10000) ? 0 : 4);
            }
            if (circularProgressBar != null) {
                circularProgressBar.setVisibility(0);
            }
        } else if (i == -2) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setVisibility(8);
            }
            if (circularProgressBar != null) {
                circularProgressBar.setVisibility(8);
            }
        } else if (i == -3) {
            horizontalProgressBar.setIndeterminate(true);
        } else if (i == -4) {
            horizontalProgressBar.setIndeterminate(false);
        } else if (i >= 0 && i <= 10000) {
            horizontalProgressBar.setProgress(i + 0);
            if (i < 10000) {
                showProgressBars(horizontalProgressBar, circularProgressBar);
            } else {
                hideProgressBars(horizontalProgressBar, circularProgressBar);
            }
        }
    }

    private void updateSandwichView() {
        updateSingleContainer(this.mCollapseContainer);
        updateSingleContainer(this.mMovableContainer);
        updateSingleContainer(this.mSecondaryContainer);
    }

    private void updateSingleContainer(ViewGroup viewGroup) {
        removeView(viewGroup);
        if (viewGroup.getChildCount() == 1 && viewGroup.getChildAt(0).getVisibility() != 8) {
            addView(viewGroup, new FrameLayout.LayoutParams(-1, -2));
        }
    }

    private void updateTightTitle() {
        boolean z = TextUtils.isEmpty(this.mTitle) && isTightTitleWithEmbeddedTabs() && ActionBarPolicy.get(this.mContext).isTightTitle();
        int i = 8;
        int i2 = (z || !this.mCollapseTitleShowable) ? 8 : 0;
        TextView textView = this.mCollapseTitleView;
        if (textView != null) {
            textView.setVisibility(i2);
        }
        if (!z && !TextUtils.isEmpty(this.mSubtitle) && this.mCollapseTitleShowable) {
            i = 0;
        }
        TextView textView2 = this.mCollapseSubtitleView;
        if (textView2 != null) {
            textView2.setVisibility(i);
        }
    }

    private void updateTitleCenter() {
        boolean isTitleCenter = isTitleCenter();
        TextView textView = this.mCollapseTitleView;
        int i = 1;
        if (textView != null) {
            ViewGroup viewGroup = (ViewGroup) textView.getParent();
            if (viewGroup instanceof LinearLayout) {
                ((LinearLayout) viewGroup).setGravity((isTitleCenter ? 1 : 8388611) | 16);
            }
            this.mCollapseTitleView.setGravity((isTitleCenter ? 1 : 8388611) | 16);
            this.mCollapseTitleView.setEllipsize(TextUtils.TruncateAt.END);
        }
        TextView textView2 = this.mCollapseSubtitleView;
        if (textView2 != null) {
            if (!isTitleCenter) {
                i = 8388611;
            }
            textView2.setGravity(i | 16);
            this.mCollapseSubtitleView.setEllipsize(TextUtils.TruncateAt.END);
        }
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void animateToVisibility(int i) {
        super.animateToVisibility(i);
    }

    public void collapseActionView() {
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        MenuItemImpl menuItemImpl = expandedActionViewMenuPresenter == null ? null : expandedActionViewMenuPresenter.mCurrentExpandedItem;
        if (menuItemImpl != null) {
            menuItemImpl.collapseActionView();
        }
    }

    /* access modifiers changed from: protected */
    public ActionMenuPresenter createActionMenuPresenter(MenuPresenter.Callback callback) {
        ActionMenuPresenter actionMenuPresenter = new ActionMenuPresenter(this.mContext, R.layout.action_menu_layout, R.layout.action_menu_item_layout, R.layout.action_bar_expanded_menu_layout, R.layout.action_bar_list_menu_item_layout);
        actionMenuPresenter.setCallback(callback);
        actionMenuPresenter.setId(R.id.action_menu_presenter);
        return actionMenuPresenter;
    }

    /* access modifiers changed from: protected */
    public ExpandedActionViewMenuPresenter createExpandedActionViewMenuPresenter() {
        return new ExpandedActionViewMenuPresenter();
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void dismissPopupMenus() {
        super.dismissPopupMenus();
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ActionBar.LayoutParams((int) DEFAULT_CUSTOM_GRAVITY);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ActionBar.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams == null ? generateDefaultLayoutParams() : layoutParams;
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ ActionMenuView getActionMenuView() {
        return super.getActionMenuView();
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ int getAnimatedVisibility() {
        return super.getAnimatedVisibility();
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ int getContentHeight() {
        return super.getContentHeight();
    }

    public View getCustomNavigationView() {
        return this.mCustomNavView;
    }

    public int getDisplayOptions() {
        return this.mDisplayOptions;
    }

    public SpinnerAdapter getDropdownAdapter() {
        return this.mSpinnerAdapter;
    }

    public int getDropdownSelectedPosition() {
        return this.mSpinner.getSelectedItemPosition();
    }

    public View getEndView() {
        return this.mEndView;
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ int getExpandState() {
        return super.getExpandState();
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ ActionMenuView getMenuView() {
        return super.getMenuView();
    }

    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    public int getSplitActionBarHeight(boolean z) {
        if (z) {
            ActionBarContainer actionBarContainer = this.mSplitView;
            if (actionBarContainer != null) {
                return actionBarContainer.getCollapsedHeight();
            }
            return 0;
        } else if (this.mSplitActionBar) {
            return this.mSplitView.getHeight();
        } else {
            return 0;
        }
    }

    public View getStartView() {
        return this.mStartView;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public boolean hasExpandedActionView() {
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        return (expandedActionViewMenuPresenter == null || expandedActionViewMenuPresenter.mCurrentExpandedItem == null) ? false : true;
    }

    public boolean hideImmersionMore() {
        View view = this.mImmersionView;
        if (view == null) {
            return false;
        }
        view.setVisibility(8);
        return true;
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean hideOverflowMenu() {
        return super.hideOverflowMenu();
    }

    public void initImmersionMore(int i, final ActionBarDelegateImpl actionBarDelegateImpl) {
        if (i <= 0) {
            Log.w(TAG, "Try to initialize invalid layout for immersion more button: " + i);
            return;
        }
        int i2 = this.mDisplayOptions;
        if ((i2 & 16) != 0) {
            Log.d(TAG, "Don't show immersion menu button for custom action bar");
        } else if (i2 == 0) {
            Log.d(TAG, "Don't show immersion menu button for null display option");
        } else {
            this.mImmersionView = LayoutInflater.from(getContext()).inflate(i, (ViewGroup) this, false);
            addView(this.mImmersionView);
            final View findViewById = this.mImmersionView.findViewById(R.id.more);
            if (findViewById != null) {
                findViewById.setOnClickListener(new View.OnClickListener() {
                    /* class com.miui.internal.widget.ActionBarView.AnonymousClass6 */

                    public void onClick(View view) {
                        actionBarDelegateImpl.showImmersionMenu(findViewById, ActionBarView.this);
                    }
                });
            }
        }
    }

    public void initIndeterminateProgress() {
        this.mIndeterminateProgressView = new ProgressBar(this.mContext, null, miui.R.attr.actionBarIndeterminateProgressStyle);
        this.mIndeterminateProgressView.setId(R.id.progress_circular);
        this.mIndeterminateProgressView.setVisibility(8);
        this.mIndeterminateProgressView.setIndeterminate(true);
        addView(this.mIndeterminateProgressView);
    }

    public void initProgress() {
        this.mProgressView = new ProgressBar(this.mContext, null, miui.R.attr.actionBarProgressStyle);
        this.mProgressView.setId(R.id.progress_horizontal);
        this.mProgressView.setMax(10000);
        this.mProgressView.setVisibility(8);
        addView(this.mProgressView);
    }

    public boolean isCollapsed() {
        return this.mIsCollapsed;
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean isOverflowMenuShowing() {
        return super.isOverflowMenuShowing();
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean isOverflowReserved() {
        return super.isOverflowReserved();
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean isResizable() {
        return super.isResizable();
    }

    public boolean isSplitActionBar() {
        return this.mSplitActionBar;
    }

    public boolean isTightTitleWithEmbeddedTabs() {
        return ActionBarPolicy.get(this.mContext).isTightTitle() && this.mIncludeTabs;
    }

    public void onActionModeEnd(boolean z) {
        this.mInActionMode = false;
        this.mInSearchMode = false;
        if (getExpandState() == 0) {
            this.mCollapseController.setAlpha(1.0f);
            this.mMovableController.setAlpha(0.0f);
        } else if (getExpandState() == 1) {
            this.mCollapseController.setAlpha(0.0f);
            this.mMovableController.setAlpha(1.0f);
        }
        if (z) {
            this.mMovableController.setAcceptAlphaChange(true);
            this.mCollapseController.setAcceptAlphaChange(true);
            this.mSecondaryController.setAcceptAlphaChange(true);
        }
        this.mSecondaryController.setAlpha(1.0f);
        if (z) {
            View view = this.mStartView;
            if (view != null) {
                view.setAlpha(1.0f);
            }
            View view2 = this.mEndView;
            if (view2 != null) {
                view2.setAlpha(1.0f);
            }
            View view3 = this.mTitleUpView;
            if (view3 != null) {
                view3.setAlpha(1.0f);
            }
        }
    }

    public void onActionModeStart(boolean z, boolean z2) {
        this.mInActionMode = true;
        this.mInSearchMode = z;
        this.mMovableController.setAlpha(0.0f);
        this.mCollapseController.setAlpha(0.0f);
        this.mSecondaryController.setAlpha(0.0f);
        if (z2) {
            View view = this.mStartView;
            if (view != null) {
                view.setAlpha(0.0f);
            }
            View view2 = this.mEndView;
            if (view2 != null) {
                view2.setAlpha(0.0f);
            }
            View view3 = this.mTitleUpView;
            if (view3 != null) {
                view3.setAlpha(0.0f);
            }
            this.mMovableController.setAcceptAlphaChange(false);
            this.mCollapseController.setAcceptAlphaChange(false);
            this.mSecondaryController.setAcceptAlphaChange(false);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.widget.AbsActionBarView
    public void onAnimatedExpandStateChanged(int i, int i2) {
        IStateStyle iStateStyle = this.mStateChangeAnimStateStyle;
        if (iStateStyle != null) {
            iStateStyle.cancel();
        }
        if (i == 1) {
            this.mPendingHeight = this.mMovableContainer.getMeasuredHeight();
        } else if (i == 0) {
            this.mPendingHeight = 0;
        }
        AnimConfig addListeners = new AnimConfig().addListeners(new InnerTransitionListener(this, i2));
        this.mStateChangeAnimStateStyle = Folme.useValue(new Object[0]).setTo(Integer.valueOf(this.mPendingHeight)).to(Integer.valueOf(i2 == 1 ? this.mMovableContainer.getMeasuredHeight() : 0), addListeners);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.widget.AbsActionBarView
    public void onConfigurationChanged(Configuration configuration) {
        ViewGroup.LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2;
        ViewGroup.LayoutParams layoutParams3;
        super.onConfigurationChanged(configuration);
        ScrollingTabContainerView scrollingTabContainerView = this.mCollapseTabs;
        if (!(scrollingTabContainerView == null || !this.mIncludeTabs || (layoutParams3 = scrollingTabContainerView.getLayoutParams()) == null)) {
            layoutParams3.width = -2;
            layoutParams3.height = -1;
        }
        ScrollingTabContainerView scrollingTabContainerView2 = this.mExpandTabs;
        if (!(scrollingTabContainerView2 == null || !this.mIncludeTabs || (layoutParams2 = scrollingTabContainerView2.getLayoutParams()) == null)) {
            layoutParams2.width = -2;
            layoutParams2.height = -1;
        }
        ScrollingTabContainerView scrollingTabContainerView3 = this.mSecondaryTabs;
        if (scrollingTabContainerView3 != null && this.mIncludeTabs && (layoutParams = scrollingTabContainerView3.getLayoutParams()) != null) {
            layoutParams.width = -2;
            layoutParams.height = -1;
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.hideOverflowMenu(false);
            this.mActionMenuPresenter.hideSubMenus();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.widget.AbsActionBarView
    public void onExpandStateChanged(int i, int i2) {
        if (i == 2) {
            this.mPendingHeight = 0;
            if (!this.mPostScroller.isFinished()) {
                this.mPostScroller.forceFinished(true);
            }
        }
        if (i2 != 1) {
            this.mCollapseController.setVisibility(0);
        }
        if (i2 != 0) {
            this.mMovableContainer.setVisibility(0);
        }
        if (i2 == 1) {
            this.mCollapseController.setVisibility(4);
        } else if (i2 == 0) {
            this.mMovableContainer.setVisibility(8);
        } else {
            this.mPendingHeight = getHeight() - this.mCollapseHeight;
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredHeight = this.mSecondaryContainer.getMeasuredHeight();
        int i5 = this.mInnerExpandState;
        int measuredHeight2 = i5 == 2 ? this.mPendingHeight : i5 == 1 ? this.mMovableContainer.getMeasuredHeight() : 0;
        int i6 = i4 - i2;
        int i7 = i6 - measuredHeight;
        int i8 = i7 - measuredHeight2;
        onLayoutCollapseViews(z, i, i2, i3, i8);
        onLayoutExpandViews(z, i, i8, i3, i7);
        onLayoutSecondaryViews(z, i, i7, i3, i6);
        animateLayoutWithProcess(((float) (this.mMovableContainer.getMeasuredHeight() - measuredHeight2)) / ((float) this.mMovableContainer.getMeasuredHeight()));
    }

    /* access modifiers changed from: protected */
    public void onLayoutExpandViews(boolean z, int i, int i2, int i3, int i4) {
        FrameLayout frameLayout = this.mMovableContainer;
        if (frameLayout != null && frameLayout.getVisibility() == 0 && this.mInnerExpandState != 0) {
            FrameLayout frameLayout2 = this.mMovableContainer;
            frameLayout2.layout(i, i4 - frameLayout2.getMeasuredHeight(), i3, i4);
            ScrollingTabContainerView scrollingTabContainerView = null;
            if (this.mMovableContainer.getChildCount() == 1 && (this.mMovableContainer.getChildAt(0) instanceof ScrollingTabContainerView)) {
                scrollingTabContainerView = (ScrollingTabContainerView) this.mMovableContainer.getChildAt(0);
            }
            if (scrollingTabContainerView != null) {
                int i5 = this.mUncollapsePaddingH;
                if (ViewUtils.isLayoutRtl(this)) {
                    i5 = (i3 - this.mUncollapsePaddingH) - scrollingTabContainerView.getMeasuredWidth();
                }
                scrollingTabContainerView.layout(i5, 0, scrollingTabContainerView.getMeasuredWidth() + i5, scrollingTabContainerView.getMeasuredHeight());
            }
            if (ViewUtils.isLayoutRtl(this)) {
                i = i3 - this.mMovableContainer.getMeasuredWidth();
            }
            this.mExpandBounds.set(i, this.mMovableContainer.getMeasuredHeight() - (i4 - i2), this.mMovableContainer.getMeasuredWidth() + i, this.mMovableContainer.getMeasuredHeight());
            this.mMovableContainer.setClipBounds(this.mExpandBounds);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayoutSecondaryViews(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        SpringBackLayout springBackLayout = this.mSecondaryContainer;
        if (springBackLayout != null && springBackLayout.getVisibility() == 0 && this.mSecondaryContainer.getChildCount() != 0) {
            SpringBackLayout springBackLayout2 = this.mSecondaryContainer;
            springBackLayout2.layout(this.mUncollapsePaddingH + i, i4 - springBackLayout2.getMeasuredHeight(), i3 - this.mUncollapsePaddingH, i4);
            ScrollingTabContainerView scrollingTabContainerView = null;
            if (this.mSecondaryContainer.getChildCount() == 1 && (this.mSecondaryContainer.getChildAt(0) instanceof ScrollingTabContainerView)) {
                scrollingTabContainerView = (ScrollingTabContainerView) this.mSecondaryContainer.getChildAt(0);
            }
            if (scrollingTabContainerView != null) {
                int measuredWidth = scrollingTabContainerView.getMeasuredWidth();
                if (ViewUtils.isLayoutRtl(this)) {
                    i6 = (i3 - (this.mUncollapsePaddingH * 2)) - scrollingTabContainerView.getMeasuredWidth();
                    i5 = i3 - (this.mUncollapsePaddingH * 2);
                } else {
                    i5 = measuredWidth;
                    i6 = 0;
                }
                scrollingTabContainerView.layout(i6, 0, i5, scrollingTabContainerView.getMeasuredHeight());
            }
            this.mSecondaryBounds.set(i, this.mSecondaryContainer.getMeasuredHeight() - (i4 - i2), i3 - this.mUncollapsePaddingH, this.mSecondaryContainer.getMeasuredHeight());
            this.mSecondaryContainer.setClipBounds(this.mSecondaryBounds);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:159:0x033f  */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x0358  */
    /* JADX WARNING: Removed duplicated region for block: B:175:0x0381  */
    /* JADX WARNING: Removed duplicated region for block: B:186:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        ProgressBar progressBar;
        int i13;
        int i14;
        int i15;
        ActionMenuView actionMenuView;
        int childCount = getChildCount();
        int i16 = 0;
        for (int i17 = 0; i17 < childCount; i17++) {
            View childAt = getChildAt(i17);
            if (!(childAt.getVisibility() == 8 || (childAt == (actionMenuView = this.mMenuView) && actionMenuView.getChildCount() == 0))) {
                i16++;
            }
        }
        if (i16 == 0) {
            setMeasuredDimension(0, 0);
            this.mIsCollapsed = true;
            return;
        }
        this.mIsCollapsed = false;
        int size = View.MeasureSpec.getSize(i);
        int i18 = this.mContentHeight;
        if (i18 <= 0) {
            i18 = View.MeasureSpec.getSize(i2);
        }
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingStart = getPaddingStart();
        int paddingEnd = getPaddingEnd();
        int i19 = i18 - paddingTop;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i19, 1073741824);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i19, Integer.MIN_VALUE);
        int i20 = (size - paddingStart) - paddingEnd;
        int i21 = i20 / 2;
        View view = this.mStartView;
        if (view != null) {
            i20 = measureChildView(view, i20, makeMeasureSpec2, 0);
            paddingStart += this.mStartView.getMeasuredWidth();
        }
        View view2 = this.mEndView;
        if (view2 != null) {
            i20 = measureChildView(view2, i20, makeMeasureSpec2, 0);
            paddingEnd += this.mEndView.getMeasuredWidth();
        }
        HomeView homeView = this.mExpandedActionView != null ? this.mExpandedHomeLayout : this.mHomeLayout;
        if (!(this.mStartView == null || homeView == null)) {
            homeView.setVisibility(8);
        }
        if (homeView == null || homeView.getVisibility() == 8) {
            i4 = 0;
            i3 = i21;
        } else {
            int i22 = homeView.getLayoutParams().width;
            homeView.measure(i22 < 0 ? View.MeasureSpec.makeMeasureSpec(i20, Integer.MIN_VALUE) : View.MeasureSpec.makeMeasureSpec(i22, 1073741824), makeMeasureSpec);
            int measuredWidth = homeView.getMeasuredWidth() + homeView.getStartOffset();
            i4 = 0;
            i20 = Math.max(0, i20 - measuredWidth);
            i3 = Math.max(0, i20 - measuredWidth);
            paddingStart += measuredWidth;
        }
        ActionMenuView actionMenuView2 = this.mMenuView;
        if (actionMenuView2 != null && actionMenuView2.getParent() == this) {
            i20 = measureChildView(this.mMenuView, i20, makeMeasureSpec2, i4);
            i21 = Math.max(i4, i21 - this.mMenuView.getMeasuredWidth());
            paddingEnd += this.mMenuView.getMeasuredWidth();
        }
        ProgressBar progressBar2 = this.mIndeterminateProgressView;
        if (!(progressBar2 == null || progressBar2.getVisibility() == 8)) {
            i20 = measureChildView(this.mIndeterminateProgressView, i20, makeMeasureSpec2, this.mProgressBarPadding * 2);
            i21 = Math.max(0, (i21 - this.mIndeterminateProgressView.getMeasuredWidth()) - (this.mProgressBarPadding * 2));
            paddingEnd += this.mIndeterminateProgressView.getMeasuredWidth();
        }
        View view3 = this.mImmersionView;
        if (!(view3 == null || view3.getVisibility() == 8)) {
            i20 = measureChildView(this.mImmersionView, i20, makeMeasureSpec2, 0);
            i21 = Math.max(0, i21 - this.mImmersionView.getMeasuredWidth());
            paddingEnd += this.mImmersionView.getMeasuredWidth();
        }
        boolean isShowTitle = isShowTitle();
        if (isShowTitle) {
            updateTitleCenter();
        }
        View view4 = this.mExpandedActionView;
        ActionBar.LayoutParams layoutParams = null;
        if (view4 == null && ((this.mDisplayOptions & 16) == 0 || (view4 = this.mCustomNavView) == null)) {
            view4 = null;
        }
        if (!((this.mStartView == null && this.mEndView == null) || view4 == null)) {
            view4.setVisibility(8);
        }
        if (view4 == null || view4.getVisibility() == 8) {
            i6 = childCount;
            i8 = i18;
            i7 = paddingTop;
            i5 = makeMeasureSpec;
        } else {
            ViewGroup.LayoutParams generateLayoutParams = generateLayoutParams(view4.getLayoutParams());
            if (generateLayoutParams instanceof ActionBar.LayoutParams) {
                layoutParams = (ActionBar.LayoutParams) generateLayoutParams;
            }
            i8 = i18;
            if (layoutParams != null) {
                i7 = paddingTop;
                i14 = layoutParams.leftMargin + layoutParams.rightMargin;
                i15 = layoutParams.bottomMargin + layoutParams.topMargin;
            } else {
                i7 = paddingTop;
                i15 = 0;
                i14 = 0;
            }
            i6 = childCount;
            int i23 = (this.mContentHeight > 0 && generateLayoutParams.height != -2) ? 1073741824 : Integer.MIN_VALUE;
            int i24 = generateLayoutParams.height;
            if (i24 >= 0) {
                i19 = Math.min(i24, i19);
            }
            int max = Math.max(0, i19 - i15);
            int i25 = generateLayoutParams.width != -2 ? 1073741824 : Integer.MIN_VALUE;
            int i26 = generateLayoutParams.width;
            i5 = makeMeasureSpec;
            int max2 = Math.max(0, (i26 >= 0 ? Math.min(i26, i20) : i20) - i14);
            if (((layoutParams != null ? layoutParams.gravity : DEFAULT_CUSTOM_GRAVITY) & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 1 && generateLayoutParams.width == -1) {
                max2 = Math.min(i3, i21) * 2;
            }
            view4.measure(View.MeasureSpec.makeMeasureSpec(max2, i25), View.MeasureSpec.makeMeasureSpec(max, i23));
            i20 -= i14 + view4.getMeasuredWidth();
        }
        if (this.mExpandedActionView != null || !isShowTitle) {
            i9 = 0;
        } else {
            int makeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824);
            View view5 = this.mTitleUpView;
            if (view5 != null) {
                i20 = measureChildView(view5, i20, makeMeasureSpec3, 0);
                paddingStart += this.mTitleUpView.getMeasuredWidth();
            }
            if (isTitleCenter()) {
                i9 = 0;
                this.mCollapseContainer.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, size - (Math.max(paddingStart, paddingEnd) * 2)), Integer.MIN_VALUE), makeMeasureSpec3);
                i20 -= this.mCollapseContainer.getMeasuredWidth();
            } else {
                i9 = 0;
                i20 = measureChildView(this.mCollapseContainer, i20, makeMeasureSpec3, 0);
            }
        }
        this.mMovableContainer.measure(View.MeasureSpec.makeMeasureSpec(size - this.mUncollapsePaddingH, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(i9, i9));
        SpringBackLayout springBackLayout = this.mSecondaryContainer;
        if (springBackLayout == null || springBackLayout.getVisibility() != 0 || this.mSecondaryContainer.getChildCount() == 0) {
            i11 = 0;
            i10 = 0;
        } else {
            this.mSecondaryContainer.measure(View.MeasureSpec.makeMeasureSpec(i9, i9), View.MeasureSpec.makeMeasureSpec(i9, i9));
            i10 = this.mSecondaryContainer.getMeasuredWidth();
            i11 = this.mSecondaryContainer.getMeasuredHeight();
        }
        if (this.mExpandedActionView == null) {
            int i27 = this.mNavigationMode;
            if (i27 != 1) {
                if (i27 == 2) {
                    if (this.mCollapseTabs != null) {
                        this.mCollapseTabs.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, this.mCollapseContainer.getMeasuredWidth()), this.mContext.getResources().getInteger(R.integer.action_bar_tab_layout_weight) == 0 ? Integer.MIN_VALUE : 1073741824), i5);
                    }
                    if (this.mExpandTabs != null) {
                        i13 = 0;
                        this.mExpandTabs.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, this.mMovableContainer.getMeasuredWidth() - this.mUncollapsePaddingH), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
                    } else {
                        i13 = 0;
                    }
                    if (this.mSecondaryTabs != null) {
                        this.mSecondaryTabs.measure(View.MeasureSpec.makeMeasureSpec(Math.max(i13, Math.min(i10, size - (this.mUncollapsePaddingH * 2))), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(i13, i13));
                    } else {
                        i12 = i13;
                        if (this.mContentHeight <= 0) {
                            int i28 = i12;
                            while (i12 < i6) {
                                int measuredHeight = getChildAt(i12).getMeasuredHeight() + i7;
                                if (measuredHeight > i28) {
                                    i28 = measuredHeight;
                                }
                                i12++;
                            }
                            setMeasuredDimension(size, i28);
                        } else {
                            int i29 = i8 + i11;
                            this.mCollapseHeight = i29;
                            int i30 = this.mInnerExpandState;
                            if (i30 == 2) {
                                setMeasuredDimension(size, i8 + this.mPendingHeight + i11);
                            } else if (i30 == 1) {
                                setMeasuredDimension(size, i8 + this.mMovableContainer.getMeasuredHeight() + i11);
                            } else {
                                setMeasuredDimension(size, i29);
                            }
                        }
                        progressBar = this.mProgressView;
                        if (progressBar == null && progressBar.getVisibility() != 8) {
                            this.mProgressView.measure(View.MeasureSpec.makeMeasureSpec(size - (this.mProgressBarPadding * 2), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), Integer.MIN_VALUE));
                            return;
                        }
                        return;
                    }
                }
            } else if (this.mListNavLayout != null) {
                int i31 = this.mItemPadding;
                if (isShowTitle) {
                    i31 *= 2;
                }
                i12 = 0;
                this.mListNavLayout.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, i20 - i31), Integer.MIN_VALUE), i5);
                if (this.mContentHeight <= 0) {
                }
                progressBar = this.mProgressView;
                if (progressBar == null) {
                    return;
                }
                return;
            }
        }
        i12 = 0;
        if (this.mContentHeight <= 0) {
        }
        progressBar = this.mProgressView;
        if (progressBar == null) {
        }
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3, int[] iArr2) {
        if (i2 > 0 && getHeight() > this.mCollapseHeight) {
            int height = getHeight() - i2;
            int i4 = this.mPendingHeight;
            int i5 = this.mCollapseHeight;
            if (height >= i5) {
                this.mPendingHeight = i4 - i2;
                iArr[1] = iArr[1] + i2;
            } else {
                int height2 = i5 - getHeight();
                this.mPendingHeight = 0;
                iArr[1] = iArr[1] + (-height2);
            }
            int i6 = this.mPendingHeight;
            if (i6 != i4) {
                iArr2[1] = i4 - i6;
                requestLayout();
            }
        }
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr, int[] iArr2) {
        if (i4 < 0 && getHeight() < this.mCollapseHeight + this.mMovableContainer.getMeasuredHeight()) {
            int i6 = this.mPendingHeight;
            if (getHeight() - i4 <= this.mCollapseHeight + this.mMovableContainer.getMeasuredHeight()) {
                this.mPendingHeight -= i4;
                iArr[1] = iArr[1] + i4;
            } else {
                int measuredHeight = (this.mCollapseHeight + this.mMovableContainer.getMeasuredHeight()) - getHeight();
                this.mPendingHeight = this.mMovableContainer.getMeasuredHeight();
                iArr[1] = iArr[1] + (-measuredHeight);
            }
            int i7 = this.mPendingHeight;
            if (i7 != i6) {
                iArr2[1] = i6 - i7;
                requestLayout();
            }
        }
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public void onNestedScrollAccepted(View view, View view2, int i, int i2) {
        if (i2 == 0) {
            this.mTouchScrolling = true;
        } else {
            this.mNonTouchScrolling = true;
        }
        if (!this.mPostScroller.isFinished()) {
            this.mPostScroller.forceFinished(true);
        }
        setExpandState(2);
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuBuilder menuBuilder;
        MenuItem findItem;
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        int i = savedState.expandedMenuItemId;
        if (!(i == 0 || this.mExpandedMenuPresenter == null || (menuBuilder = this.mOptionsMenu) == null || (findItem = menuBuilder.findItem(i)) == null)) {
            findItem.expandActionView();
        }
        if (savedState.isOverflowOpen) {
            postShowOverflowMenu();
        }
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public Parcelable onSaveInstanceState() {
        MenuItemImpl menuItemImpl;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        if (!(expandedActionViewMenuPresenter == null || (menuItemImpl = expandedActionViewMenuPresenter.mCurrentExpandedItem) == null)) {
            savedState.expandedMenuItemId = menuItemImpl.getItemId();
        }
        savedState.isOverflowOpen = isOverflowMenuShowing();
        return savedState;
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        return getContext().getResources().getConfiguration().orientation != 2 && this.mExpandedActionView == null && isShowTitle() && isResizable();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000a, code lost:
        if (r3.mNonTouchScrolling == false) goto L_0x0013;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0018  */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    @Override // com.miui.internal.widget.AbsActionBarView
    public void onStopNestedScroll(View view, int i) {
        boolean z;
        if (this.mTouchScrolling) {
            this.mTouchScrolling = false;
        } else {
            if (this.mNonTouchScrolling) {
                this.mNonTouchScrolling = false;
            }
            z = false;
            if (z) {
                return;
            }
            if (getHeight() == this.mCollapseHeight) {
                setExpandState(0);
                return;
            } else if (getHeight() == this.mCollapseHeight + this.mMovableContainer.getMeasuredHeight() || this.mPendingHeight == this.mMovableContainer.getMeasuredHeight()) {
                setExpandState(1);
                return;
            } else {
                if (getHeight() > this.mCollapseHeight + (this.mMovableContainer.getMeasuredHeight() / 2)) {
                    this.mPostScroller.startScroll(0, getHeight(), 0, (this.mCollapseHeight + this.mMovableContainer.getMeasuredHeight()) - getHeight());
                } else {
                    this.mPostScroller.startScroll(0, getHeight(), 0, this.mCollapseHeight - getHeight());
                }
                postOnAnimation(this.mPostScroll);
                return;
            }
        }
        z = true;
        if (z) {
        }
    }

    public void onWindowHide() {
        this.mSplitView.onWindowHide();
    }

    public void onWindowShow() {
        this.mSplitView.onWindowShow();
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void postShowOverflowMenu() {
        super.postShowOverflowMenu();
    }

    public void setCallback(ActionBar.OnNavigationListener onNavigationListener) {
        this.mCallback = onNavigationListener;
    }

    public void setCollapsable(boolean z) {
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setContentHeight(int i) {
        super.setContentHeight(i);
    }

    public void setCustomNavigationView(View view) {
        boolean z = (this.mDisplayOptions & 16) != 0;
        View view2 = this.mCustomNavView;
        if (view2 != null && z) {
            removeView(view2);
        }
        this.mCustomNavView = view;
        View view3 = this.mCustomNavView;
        if (view3 == null || !z) {
            this.mCollapseController.attach(this.mCollapseContainer);
            return;
        }
        addView(view3);
        addedCustomView();
    }

    public void setDisplayOptions(int i) {
        View view;
        int i2 = this.mDisplayOptions;
        int i3 = -1;
        if (i2 != -1) {
            i3 = i ^ i2;
        }
        this.mDisplayOptions = i;
        if ((i3 & 31) != 0) {
            boolean z = false;
            boolean z2 = (i & 2) != 0;
            int i4 = 8;
            if (z2) {
                initHomeLayout();
                this.mHomeLayout.setVisibility(this.mExpandedActionView == null ? 0 : 8);
                if ((i3 & 4) != 0) {
                    boolean z3 = (i & 4) != 0;
                    this.mHomeLayout.setUp(z3);
                    if (z3) {
                        setHomeButtonEnabled(true);
                    }
                }
                if ((i3 & 1) != 0) {
                    Drawable logo = getLogo();
                    boolean z4 = (logo == null || (i & 1) == 0) ? false : true;
                    HomeView homeView = this.mHomeLayout;
                    if (!z4) {
                        logo = getIcon();
                    }
                    homeView.setIcon(logo);
                }
            } else {
                HomeView homeView2 = this.mHomeLayout;
                if (homeView2 != null) {
                    removeView(homeView2);
                }
            }
            if ((i3 & 8) != 0) {
                if ((i & 8) != 0) {
                    initTitle();
                } else {
                    this.mCollapseContainer.removeView(this.mCollapseTitleLayout);
                    this.mMovableContainer.removeView(this.mExpandTitleLayout);
                    removeView(this.mTitleUpView);
                    this.mCollapseTitleLayout = null;
                    this.mExpandTitleLayout = null;
                    this.mTitleUpView = null;
                    updateSandwichView();
                }
            }
            if (!(this.mCollapseTitleLayout == null || (i3 & 6) == 0)) {
                boolean z5 = (this.mDisplayOptions & 4) != 0;
                if (this.mCollapseTitleLayout.getVisibility() == 0) {
                    View view2 = this.mTitleUpView;
                    if (!z2) {
                        i4 = z5 ? 0 : 4;
                    }
                    view2.setVisibility(i4);
                }
                this.mCollapseTitleLayout.setEnabled(!z2 && z5);
                LinearLayout linearLayout = this.mExpandTitleLayout;
                if (!z2 && z5) {
                    z = true;
                }
                linearLayout.setEnabled(z);
            }
            if (!((i3 & 16) == 0 || (view = this.mCustomNavView) == null)) {
                if ((i & 16) != 0) {
                    addView(view);
                    addedCustomView();
                } else {
                    removeView(view);
                }
            }
            requestLayout();
        } else {
            invalidate();
        }
        HomeView homeView3 = this.mHomeLayout;
        if (homeView3 == null) {
            return;
        }
        if (!homeView3.isEnabled()) {
            this.mHomeLayout.setContentDescription(null);
        } else if ((i & 4) != 0) {
            this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(R.string.abc_action_bar_up_description));
        } else {
            this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(R.string.abc_action_bar_home_description));
        }
    }

    public void setDropdownAdapter(SpinnerAdapter spinnerAdapter) {
        this.mSpinnerAdapter = spinnerAdapter;
        Spinner spinner = this.mSpinner;
        if (spinner != null) {
            spinner.setAdapter(spinnerAdapter);
        }
    }

    public void setDropdownSelectedPosition(int i) {
        this.mSpinner.setSelection(i);
    }

    public void setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView, ScrollingTabContainerView scrollingTabContainerView2, ScrollingTabContainerView scrollingTabContainerView3) {
        this.mIncludeTabs = scrollingTabContainerView != null;
        if (this.mIncludeTabs && this.mNavigationMode == 2) {
            addTabsContainer(scrollingTabContainerView, scrollingTabContainerView2, scrollingTabContainerView3);
        }
    }

    public void setEndView(View view) {
        View view2 = this.mEndView;
        if (view2 != null) {
            removeView(view2);
        }
        this.mEndView = view;
        View view3 = this.mEndView;
        if (view3 != null) {
            addView(view3);
            FolmeAnimHelper.addAlphaPressAnim(view);
        }
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public void setExpandState(int i) {
        ActionBarContextView actionBarContextView;
        super.setExpandState(i);
        if ((getParent() instanceof ActionBarContainer) && (actionBarContextView = (ActionBarContextView) ((ActionBarContainer) getParent()).findViewById(R.id.action_context_bar)) != null) {
            actionBarContextView.setExpandState(i);
        }
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setExpandState(int i, boolean z) {
        super.setExpandState(i, z);
    }

    public void setHomeAsUpIndicator(int i) {
        HomeView homeView = this.mHomeLayout;
        if (homeView != null) {
            homeView.setUpIndicator(i);
            return;
        }
        this.mHomeAsUpIndicatorDrawable = null;
        this.mHomeAsUpIndicatorResId = i;
    }

    public void setHomeAsUpIndicator(Drawable drawable) {
        HomeView homeView = this.mHomeLayout;
        if (homeView != null) {
            homeView.setUpIndicator(drawable);
            return;
        }
        this.mHomeAsUpIndicatorDrawable = drawable;
        this.mHomeAsUpIndicatorResId = 0;
    }

    public void setHomeButtonEnabled(boolean z) {
        HomeView homeView = this.mHomeLayout;
        if (homeView != null) {
            homeView.setEnabled(z);
            this.mHomeLayout.setFocusable(z);
            if (!z) {
                this.mHomeLayout.setContentDescription(null);
            } else if ((this.mDisplayOptions & 4) != 0) {
                this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(R.string.abc_action_bar_up_description));
            } else {
                this.mHomeLayout.setContentDescription(this.mContext.getResources().getText(R.string.abc_action_bar_home_description));
            }
        }
    }

    public void setIcon(int i) {
        setIcon(this.mContext.getResources().getDrawable(i));
    }

    public void setIcon(Drawable drawable) {
        HomeView homeView;
        this.mIcon = drawable;
        this.mIconLogoInitIndicator |= 1;
        if (drawable != null && (((this.mDisplayOptions & 1) == 0 || getLogo() == null) && (homeView = this.mHomeLayout) != null)) {
            homeView.setIcon(drawable);
        }
        if (this.mExpandedActionView != null) {
            this.mExpandedHomeLayout.setIcon(this.mIcon.getConstantState().newDrawable(getResources()));
        }
    }

    public void setLogo(int i) {
        setLogo(this.mContext.getResources().getDrawable(i));
    }

    public void setLogo(Drawable drawable) {
        HomeView homeView;
        this.mLogo = drawable;
        this.mIconLogoInitIndicator |= 2;
        if (drawable != null && (this.mDisplayOptions & 1) != 0 && (homeView = this.mHomeLayout) != null) {
            homeView.setIcon(drawable);
        }
    }

    public void setMenu(Menu menu, MenuPresenter.Callback callback) {
        ActionMenuView actionMenuView;
        ViewGroup viewGroup;
        MenuBuilder menuBuilder = this.mOptionsMenu;
        if (menu != menuBuilder) {
            if (menuBuilder != null) {
                menuBuilder.removeMenuPresenter(this.mActionMenuPresenter);
                this.mOptionsMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
            }
            MenuBuilder menuBuilder2 = (MenuBuilder) menu;
            this.mOptionsMenu = menuBuilder2;
            ActionMenuView actionMenuView2 = this.mMenuView;
            if (!(actionMenuView2 == null || (viewGroup = (ViewGroup) actionMenuView2.getParent()) == null)) {
                viewGroup.removeView(this.mMenuView);
            }
            if (this.mActionMenuPresenter == null) {
                this.mActionMenuPresenter = createActionMenuPresenter(callback);
                this.mExpandedMenuPresenter = createExpandedActionViewMenuPresenter();
            }
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -1);
            if (!this.mSplitActionBar) {
                this.mActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(R.bool.abc_action_bar_expanded_action_views_exclusive));
                configPresenters(menuBuilder2);
                actionMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
                ViewGroup viewGroup2 = (ViewGroup) actionMenuView.getParent();
                if (!(viewGroup2 == null || viewGroup2 == this)) {
                    viewGroup2.removeView(actionMenuView);
                }
                addView(actionMenuView, layoutParams);
            } else {
                this.mActionMenuPresenter.setExpandedActionViewsExclusive(false);
                this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
                layoutParams.width = -1;
                layoutParams.height = -2;
                layoutParams.gravity = DeviceHelper.IS_TABLET ? 17 : 80;
                configPresenters(menuBuilder2);
                actionMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
                if (this.mSplitView != null) {
                    ViewGroup viewGroup3 = (ViewGroup) actionMenuView.getParent();
                    if (!(viewGroup3 == null || viewGroup3 == this.mSplitView)) {
                        viewGroup3.removeView(actionMenuView);
                    }
                    actionMenuView.setVisibility(getAnimatedVisibility());
                    this.mSplitView.addView(actionMenuView, 1, layoutParams);
                    View findViewById = actionMenuView.findViewById(R.id.expanded_menu);
                    if (findViewById != null) {
                        findViewById.requestLayout();
                    }
                } else {
                    actionMenuView.setLayoutParams(layoutParams);
                }
            }
            this.mMenuView = actionMenuView;
        }
    }

    public void setNavigationMode(int i) {
        ScrollingTabContainerView scrollingTabContainerView;
        ScrollingTabContainerView scrollingTabContainerView2;
        LinearLayout linearLayout;
        int i2 = this.mNavigationMode;
        if (i != i2) {
            if (i2 == 1 && (linearLayout = this.mListNavLayout) != null) {
                removeView(linearLayout);
            }
            if (i == 1) {
                if (this.mSpinner == null) {
                    this.mSpinner = new Spinner(this.mContext, null, 16843479);
                    this.mListNavLayout = (LinearLayout) LayoutInflater.from(this.mContext).inflate(R.layout.action_bar_view_list_nav_layout, (ViewGroup) null);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
                    layoutParams.gravity = 17;
                    this.mListNavLayout.addView(this.mSpinner, layoutParams);
                }
                SpinnerAdapter adapter = this.mSpinner.getAdapter();
                SpinnerAdapter spinnerAdapter = this.mSpinnerAdapter;
                if (adapter != spinnerAdapter) {
                    this.mSpinner.setAdapter(spinnerAdapter);
                }
                this.mSpinner.setOnItemSelectedListener(this.mNavItemSelectedListener);
                addView(this.mListNavLayout);
            } else if (i == 2 && (scrollingTabContainerView = this.mCollapseTabs) != null && (scrollingTabContainerView2 = this.mExpandTabs) != null && this.mIncludeTabs) {
                addTabsContainer(scrollingTabContainerView, scrollingTabContainerView2, this.mSecondaryTabs);
            }
            this.mNavigationMode = i;
            requestLayout();
        }
    }

    public void setProgress(int i) {
        updateProgressBars(i + 0);
    }

    public void setProgressBarIndeterminate(boolean z) {
        updateProgressBars(z ? -3 : -4);
    }

    public void setProgressBarIndeterminateVisibility(boolean z) {
        updateProgressBars(z ? -1 : -2);
    }

    public void setProgressBarVisibility(boolean z) {
        updateProgressBars(z ? -1 : -2);
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setResizable(boolean z) {
        super.setResizable(z);
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public void setSplitActionBar(boolean z) {
        if (this.mSplitActionBar != z) {
            ActionMenuView actionMenuView = this.mMenuView;
            if (actionMenuView != null) {
                ViewGroup viewGroup = (ViewGroup) actionMenuView.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(this.mMenuView);
                }
                if (z) {
                    ActionBarContainer actionBarContainer = this.mSplitView;
                    if (actionBarContainer != null) {
                        actionBarContainer.addView(this.mMenuView);
                    }
                    this.mMenuView.getLayoutParams().width = -1;
                } else {
                    addView(this.mMenuView);
                    this.mMenuView.getLayoutParams().width = -2;
                }
                this.mMenuView.requestLayout();
            }
            ActionBarContainer actionBarContainer2 = this.mSplitView;
            if (actionBarContainer2 != null) {
                actionBarContainer2.setVisibility(z ? 0 : 8);
            }
            ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
            if (actionMenuPresenter != null) {
                if (!z) {
                    actionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(R.bool.abc_action_bar_expanded_action_views_exclusive));
                } else {
                    actionMenuPresenter.setExpandedActionViewsExclusive(false);
                    this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
                }
            }
            super.setSplitActionBar(z);
        }
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setSplitView(ActionBarContainer actionBarContainer) {
        super.setSplitView(actionBarContainer);
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setSplitWhenNarrow(boolean z) {
        super.setSplitWhenNarrow(z);
    }

    public void setStartView(View view) {
        View view2 = this.mStartView;
        if (view2 != null) {
            removeView(view2);
        }
        this.mStartView = view;
        View view3 = this.mStartView;
        if (view3 != null) {
            addView(view3);
            FolmeAnimHelper.addAlphaPressAnim(view);
        }
    }

    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
        TextView textView = this.mCollapseSubtitleView;
        if (textView != null) {
            textView.setText(charSequence);
            this.mExpandSubtitleView.setText(charSequence);
            boolean z = false;
            this.mCollapseSubtitleView.setVisibility(charSequence != null ? 0 : 8);
            this.mExpandSubtitleView.setVisibility(charSequence != null ? 0 : 8);
            if (this.mExpandedActionView == null && (this.mDisplayOptions & 8) != 0 && (!TextUtils.isEmpty(this.mTitle) || !TextUtils.isEmpty(this.mSubtitle))) {
                z = true;
            }
            setTitleVisibility(z);
        }
    }

    public void setTitle(CharSequence charSequence) {
        this.mUserTitle = true;
        setTitleImpl(charSequence);
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void setVisibility(int i) {
        super.setVisibility(i);
    }

    public void setWindowCallback(Window.Callback callback) {
        this.mWindowCallback = callback;
    }

    public void setWindowTitle(CharSequence charSequence) {
        if (!this.mUserTitle) {
            setTitleImpl(charSequence);
        }
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public boolean showImmersionMore() {
        View view = this.mImmersionView;
        if (view == null) {
            return false;
        }
        view.setVisibility(0);
        return true;
    }

    @Override // com.miui.internal.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean showOverflowMenu() {
        return super.showOverflowMenu();
    }
}
