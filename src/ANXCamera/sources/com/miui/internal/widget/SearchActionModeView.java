package com.miui.internal.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.miui.internal.R;
import com.miui.internal.app.ActionBarImpl;
import com.miui.internal.util.DeviceHelper;
import com.miui.internal.util.FolmeAnimHelper;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import miui.util.ActionBarUtils;
import miui.util.AttributeResolver;
import miui.view.ActionModeAnimationListener;
import miui.view.ViewPager;
import miui.view.inputmethod.InputMethodHelper;

public class SearchActionModeView extends FrameLayout implements Animator.AnimatorListener, ActionModeView, TextWatcher, View.OnClickListener, MessageQueue.IdleHandler {
    private ActionBarContainer mActionBarContainer;
    private int mActionBarHeight;
    private int mActionBarLocation = Integer.MAX_VALUE;
    private int mActionBarTopMargin;
    private ActionBarView mActionBarView;
    private WeakReference<View> mAnchorView;
    private boolean mAnimateToVisible;
    private WeakReference<View> mAnimateView;
    private int mAnimateViewTranslationYLength;
    private int mAnimateViewTranslationYStart;
    private boolean mAnimationCanceled;
    private List<ActionModeAnimationListener> mAnimationListeners;
    private float mAnimationProgress;
    private int mContentOriginPaddingBottom;
    private int mContentOriginPaddingTop;
    private ObjectAnimator mCurrentAnimation;
    private View mDimView;
    private int mInputPaddingRight;
    private int mInputPaddingTop;
    private EditText mInputView;
    private int mInputViewTranslationYLength;
    private int mInputViewTranslationYStart;
    private int[] mLocation = new int[2];
    private int mOriginalPaddingTop;
    private boolean mRequestAnimation;
    private WeakReference<View> mResultView;
    private int mResultViewOriginMarginBottom;
    private int mResultViewOriginMarginTop;
    private boolean mResultViewSet;
    private SearchActionModeViewHelper mSearchActionModeViewHelper;
    private ActionBarContainer mSplitActionBarContainer;
    private int mStatusBarPaddingTop;
    private int mTextLengthBeforeChanged;

    /* access modifiers changed from: package-private */
    public class ActionBarAnimationProcessor implements ActionModeAnimationListener {
        ActionBarAnimationProcessor() {
        }

        @Override // miui.view.ActionModeAnimationListener
        public void onStart(boolean z) {
        }

        @Override // miui.view.ActionModeAnimationListener
        public void onStop(boolean z) {
            View tabContainer;
            if (z && (tabContainer = SearchActionModeView.this.getActionBarContainer().getTabContainer()) != null) {
                tabContainer.setVisibility(8);
            }
        }

        @Override // miui.view.ActionModeAnimationListener
        public void onUpdate(boolean z, float f) {
        }
    }

    /* access modifiers changed from: package-private */
    public class ContentViewAnimationProcessor implements ActionModeAnimationListener {
        boolean mDimViewVisible;
        ViewPager mViewPager;

        ContentViewAnimationProcessor() {
        }

        @Override // miui.view.ActionModeAnimationListener
        public void onStart(boolean z) {
            View contentView = SearchActionModeView.this.getContentView();
            if (contentView != null && z) {
                SearchActionModeView.this.mContentOriginPaddingTop = contentView.getPaddingTop();
                SearchActionModeView.this.mContentOriginPaddingBottom = contentView.getPaddingBottom();
            }
            boolean z2 = true;
            if (z) {
                if (SearchActionModeView.this.mActionBarLocation == Integer.MAX_VALUE) {
                    SearchActionModeView.this.getActionBarContainer().getLocationInWindow(SearchActionModeView.this.mLocation);
                    SearchActionModeView searchActionModeView = SearchActionModeView.this;
                    searchActionModeView.mActionBarLocation = searchActionModeView.mLocation[1];
                }
                SearchActionModeView searchActionModeView2 = SearchActionModeView.this;
                searchActionModeView2.mInputViewTranslationYStart = searchActionModeView2.getActionBarContainer().getHeight();
                SearchActionModeView searchActionModeView3 = SearchActionModeView.this;
                searchActionModeView3.mInputViewTranslationYLength = -searchActionModeView3.mInputViewTranslationYStart;
                ((View) SearchActionModeView.this.mAnchorView.get()).getLocationInWindow(SearchActionModeView.this.mLocation);
                SearchActionModeView searchActionModeView4 = SearchActionModeView.this;
                searchActionModeView4.mAnimateViewTranslationYStart = (searchActionModeView4.mLocation[1] - SearchActionModeView.this.mActionBarLocation) - SearchActionModeView.this.mContentOriginPaddingTop;
                SearchActionModeView searchActionModeView5 = SearchActionModeView.this;
                searchActionModeView5.mAnimateViewTranslationYLength = searchActionModeView5.mInputViewTranslationYLength;
            } else if (SearchActionModeView.this.mActionBarContainer == null || !SearchActionModeView.this.mActionBarContainer.isBlurEnable()) {
                SearchActionModeView searchActionModeView6 = SearchActionModeView.this;
                searchActionModeView6.setContentViewTranslation(searchActionModeView6.mStatusBarPaddingTop);
                SearchActionModeView.this.setContentViewPadding(0, 0);
            } else {
                SearchActionModeView searchActionModeView7 = SearchActionModeView.this;
                searchActionModeView7.setContentViewTranslation(this.mDimViewVisible ? searchActionModeView7.mStatusBarPaddingTop : -searchActionModeView7.mContentOriginPaddingTop);
            }
            if (SearchActionModeView.this.mDimView == null || SearchActionModeView.this.mDimView.getVisibility() != 0) {
                z2 = false;
            }
            this.mDimViewVisible = z2;
            this.mViewPager = SearchActionModeView.this.getViewPager();
            ViewPager viewPager = this.mViewPager;
            if (viewPager != null) {
                viewPager.setBottomMarginProgress(z ? 0.0f : 1.0f);
            }
        }

        @Override // miui.view.ActionModeAnimationListener
        public void onStop(boolean z) {
            float f = 1.0f;
            if (!z) {
                ((View) SearchActionModeView.this.mAnimateView.get()).setTranslationY(0.0f);
                ((View) SearchActionModeView.this.mAnchorView.get()).setAlpha(1.0f);
            }
            if (SearchActionModeView.this.mAnchorView.get() != null) {
                ((View) SearchActionModeView.this.mAnchorView.get()).setEnabled(!z);
            }
            if (SearchActionModeView.this.mStatusBarPaddingTop > 0) {
                SearchActionModeView.this.setContentViewTranslation(0);
                SearchActionModeView searchActionModeView = SearchActionModeView.this;
                searchActionModeView.setContentViewPadding(z ? searchActionModeView.mStatusBarPaddingTop : 0, 0);
            }
            if (z && SearchActionModeView.this.mActionBarContainer != null && SearchActionModeView.this.mActionBarContainer.isBlurEnable()) {
                SearchActionModeView searchActionModeView2 = SearchActionModeView.this;
                searchActionModeView2.setContentViewTranslation(-searchActionModeView2.mContentOriginPaddingTop);
            }
            ViewPager viewPager = this.mViewPager;
            if (viewPager != null) {
                if (!z) {
                    f = 0.0f;
                }
                viewPager.setBottomMarginProgress(f);
            }
            this.mViewPager = null;
        }

        @Override // miui.view.ActionModeAnimationListener
        public void onUpdate(boolean z, float f) {
            if (!z) {
                f = 1.0f - f;
            }
            if (SearchActionModeView.this.mActionBarContainer != null && SearchActionModeView.this.mActionBarContainer.isBlurEnable()) {
                int i = this.mDimViewVisible ? -SearchActionModeView.this.mStatusBarPaddingTop : (-SearchActionModeView.this.mContentOriginPaddingTop) - SearchActionModeView.this.mStatusBarPaddingTop;
                if (z) {
                    i = SearchActionModeView.this.mStatusBarPaddingTop;
                }
                SearchActionModeView searchActionModeView = SearchActionModeView.this;
                int i2 = 0;
                searchActionModeView.setContentViewTranslation((searchActionModeView.mStatusBarPaddingTop > 0 ? (int) (((float) SearchActionModeView.this.mStatusBarPaddingTop) * f) : 0) + i);
                if (!this.mDimViewVisible) {
                    i2 = (-SearchActionModeView.this.mStatusBarPaddingTop) - SearchActionModeView.this.mContentOriginPaddingTop;
                }
                if (z) {
                    i2 = -SearchActionModeView.this.mStatusBarPaddingTop;
                }
                ((View) SearchActionModeView.this.mAnimateView.get()).setTranslationY(((float) (SearchActionModeView.this.mAnimateViewTranslationYStart + i2)) + (((float) SearchActionModeView.this.mAnimateViewTranslationYLength) * f));
            } else if (SearchActionModeView.this.mStatusBarPaddingTop > 0) {
                SearchActionModeView searchActionModeView2 = SearchActionModeView.this;
                searchActionModeView2.setContentViewTranslation((int) (((float) searchActionModeView2.mStatusBarPaddingTop) * f));
                ((View) SearchActionModeView.this.mAnimateView.get()).setTranslationY(((float) SearchActionModeView.this.mAnimateViewTranslationYStart) + (((float) SearchActionModeView.this.mAnimateViewTranslationYLength) * f));
            }
            ((View) SearchActionModeView.this.mAnchorView.get()).setAlpha(1.0f - f);
            SearchActionModeView searchActionModeView3 = SearchActionModeView.this;
            searchActionModeView3.setTranslationY(((float) searchActionModeView3.mInputViewTranslationYStart) + (f * ((float) SearchActionModeView.this.mInputViewTranslationYLength)));
        }
    }

    /* access modifiers changed from: package-private */
    public class DimViewAnimationProcessor implements ActionModeAnimationListener {
        DimViewAnimationProcessor() {
        }

        @Override // miui.view.ActionModeAnimationListener
        public void onStart(boolean z) {
            if (z) {
                SearchActionModeView.this.mDimView.setOnClickListener(SearchActionModeView.this);
                SearchActionModeView.this.mDimView.setVisibility(0);
                SearchActionModeView.this.mDimView.setAlpha(0.0f);
            }
        }

        @Override // miui.view.ActionModeAnimationListener
        public void onStop(boolean z) {
            if (!z) {
                SearchActionModeView.this.mDimView.setVisibility(8);
                SearchActionModeView.this.mDimView.setAlpha(1.0f);
                SearchActionModeView.this.mDimView.setTranslationY(0.0f);
            } else if (SearchActionModeView.this.mInputView.getText().length() > 0) {
                SearchActionModeView.this.mDimView.setVisibility(8);
            }
        }

        @Override // miui.view.ActionModeAnimationListener
        public void onUpdate(boolean z, float f) {
            if (!z) {
                f = 1.0f - f;
            }
            SearchActionModeView.this.mDimView.setAlpha(f);
            if (SearchActionModeView.this.shouldAnimateContent()) {
                SearchActionModeView.this.mDimView.setTranslationY(((View) SearchActionModeView.this.mAnimateView.get()).getTranslationY() + ((float) ((SearchActionModeView.this.mActionBarContainer == null || !SearchActionModeView.this.mActionBarContainer.isBlurEnable()) ? 0 : SearchActionModeView.this.mContentOriginPaddingTop)));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public class SearchViewAnimationProcessor implements ActionModeAnimationListener {
        SearchViewAnimationProcessor() {
        }

        @Override // miui.view.ActionModeAnimationListener
        public void onStart(boolean z) {
            if (z) {
                SearchActionModeView.this.mInputView.getText().clear();
                SearchActionModeView.this.mInputView.addTextChangedListener(SearchActionModeView.this);
                SearchActionModeView.this.mSearchActionModeViewHelper.translationCancelView();
            }
        }

        @Override // miui.view.ActionModeAnimationListener
        public void onStop(boolean z) {
            if (!z) {
                SearchActionModeView.this.mInputView.removeTextChangedListener(SearchActionModeView.this);
            }
        }

        @Override // miui.view.ActionModeAnimationListener
        public void onUpdate(boolean z, float f) {
            if (!z) {
                f = 1.0f - f;
            }
            if (SearchActionModeView.this.mStatusBarPaddingTop > 0) {
                SearchActionModeView searchActionModeView = SearchActionModeView.this;
                searchActionModeView.setPadding(searchActionModeView.getPaddingLeft(), (int) (((float) SearchActionModeView.this.mOriginalPaddingTop) + (((float) SearchActionModeView.this.mStatusBarPaddingTop) * f) + (((float) SearchActionModeView.this.mInputPaddingTop) * f)), SearchActionModeView.this.getPaddingRight(), SearchActionModeView.this.getPaddingBottom());
                SearchActionModeView.this.getLayoutParams().height = SearchActionModeView.this.mActionBarHeight + ((int) (((float) SearchActionModeView.this.mStatusBarPaddingTop) * f)) + ((int) (((float) SearchActionModeView.this.mInputPaddingTop) * f));
                SearchActionModeView.this.requestLayout();
            }
            SearchActionModeView.this.mSearchActionModeViewHelper.updateCancelView(f, SearchActionModeView.this.mInputPaddingRight);
        }
    }

    /* access modifiers changed from: package-private */
    public class SplitActionBarAnimationProcessor implements ActionModeAnimationListener {
        SplitActionBarAnimationProcessor() {
        }

        @Override // miui.view.ActionModeAnimationListener
        public void onStart(boolean z) {
        }

        @Override // miui.view.ActionModeAnimationListener
        public void onStop(boolean z) {
        }

        @Override // miui.view.ActionModeAnimationListener
        public void onUpdate(boolean z, float f) {
            if (!z) {
                f = 1.0f - f;
            }
            ActionBarContainer splitActionBarContainer = SearchActionModeView.this.getSplitActionBarContainer();
            if (splitActionBarContainer != null) {
                splitActionBarContainer.setTranslationY(f * ((float) splitActionBarContainer.getHeight()));
            }
        }
    }

    public SearchActionModeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setAlpha(0.0f);
        this.mActionBarHeight = AttributeResolver.resolveDimensionPixelSize(context, 16843499);
        this.mSearchActionModeViewHelper = new SearchActionModeViewHelper();
        this.mInputPaddingTop = this.mSearchActionModeViewHelper.getAnimatedPaddingTop(context);
        this.mInputPaddingRight = this.mSearchActionModeViewHelper.getAnimatedPaddingHorizontal(context);
        FolmeAnimHelper.addPressAnim(this);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private View getContentView() {
        ViewGroup actionBarOverlayLayout = ActionBarUtils.getActionBarOverlayLayout(this);
        if (actionBarOverlayLayout != null) {
            return actionBarOverlayLayout.findViewById(16908290);
        }
        return null;
    }

    private MessageQueue getMessageQueue() {
        return Looper.myQueue();
    }

    private void queueIdleHandler() {
        removeIdleHandler();
        getMessageQueue().addIdleHandler(this);
    }

    private void removeIdleHandler() {
        getMessageQueue().removeIdleHandler(this);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean shouldAnimateContent() {
        return (this.mAnchorView == null || this.mAnimateView == null) ? false : true;
    }

    @Override // com.miui.internal.widget.ActionModeView
    public void addAnimationListener(ActionModeAnimationListener actionModeAnimationListener) {
        if (actionModeAnimationListener != null) {
            if (this.mAnimationListeners == null) {
                this.mAnimationListeners = new ArrayList();
            }
            this.mAnimationListeners.add(actionModeAnimationListener);
        }
    }

    public void afterTextChanged(Editable editable) {
        View view;
        if ((editable == null ? 0 : editable.length()) == 0) {
            View view2 = this.mDimView;
            if (view2 != null) {
                view2.setVisibility(0);
            }
            InputMethodHelper.getInstance().showKeyBoard(this.mInputView);
        } else if (this.mTextLengthBeforeChanged == 0 && (view = this.mDimView) != null) {
            view.setVisibility(8);
        }
    }

    @Override // com.miui.internal.widget.ActionModeView
    public void animateToVisibility(boolean z) {
        if (this.mAnimateToVisible == z) {
            this.mRequestAnimation = false;
            return;
        }
        pollViews();
        this.mAnimateToVisible = z;
        this.mCurrentAnimation = makeAnimation();
        createAnimationListeners();
        if (z) {
            setOverlayMode(true);
        }
        notifyAnimationStart(z);
        if (shouldAnimateContent()) {
            requestLayout();
            this.mRequestAnimation = true;
        } else {
            this.mCurrentAnimation.start();
        }
        if (!this.mAnimateToVisible) {
            ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.mInputView.getWindowToken(), 0);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        this.mTextLengthBeforeChanged = charSequence == null ? 0 : charSequence.length();
    }

    @Override // com.miui.internal.widget.ActionModeView
    public void closeMode() {
        ObjectAnimator objectAnimator = this.mCurrentAnimation;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    /* access modifiers changed from: protected */
    public void createAnimationListeners() {
        if (this.mAnimationListeners == null) {
            this.mAnimationListeners = new ArrayList();
        }
        this.mAnimationListeners.add(new SearchViewAnimationProcessor());
        if (shouldAnimateContent()) {
            this.mAnimationListeners.add(new ContentViewAnimationProcessor());
            this.mAnimationListeners.add(new ActionBarAnimationProcessor());
            this.mAnimationListeners.add(new SplitActionBarAnimationProcessor());
        }
        if (getDimView() != null) {
            this.mAnimationListeners.add(new DimViewAnimationProcessor());
        }
    }

    /* access modifiers changed from: protected */
    public void finishAnimation() {
        ObjectAnimator objectAnimator = this.mCurrentAnimation;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.mCurrentAnimation = null;
        }
    }

    /* access modifiers changed from: protected */
    public ActionBarContainer getActionBarContainer() {
        if (this.mActionBarContainer == null) {
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) ActionBarUtils.getActionBarOverlayLayout(this);
            if (actionBarOverlayLayout != null) {
                int i = 0;
                while (true) {
                    if (i >= actionBarOverlayLayout.getChildCount()) {
                        break;
                    }
                    View childAt = actionBarOverlayLayout.getChildAt(i);
                    if (childAt.getId() == R.id.action_bar_container && (childAt instanceof ActionBarContainer)) {
                        this.mActionBarContainer = (ActionBarContainer) childAt;
                        break;
                    }
                    i++;
                }
            }
            ActionBarContainer actionBarContainer = this.mActionBarContainer;
            if (actionBarContainer != null) {
                this.mActionBarTopMargin = ((ViewGroup.MarginLayoutParams) actionBarContainer.getLayoutParams()).topMargin;
                if (this.mActionBarTopMargin > 0) {
                    setPadding(getPaddingLeft(), this.mOriginalPaddingTop + this.mActionBarTopMargin, getPaddingRight(), getPaddingBottom());
                }
            }
        }
        return this.mActionBarContainer;
    }

    /* access modifiers changed from: protected */
    public ActionBarView getActionBarView() {
        ViewGroup actionBarOverlayLayout;
        if (this.mActionBarView == null && (actionBarOverlayLayout = ActionBarUtils.getActionBarOverlayLayout(this)) != null) {
            this.mActionBarView = (ActionBarView) actionBarOverlayLayout.findViewById(R.id.action_bar);
        }
        return this.mActionBarView;
    }

    public float getAnimationProgress() {
        return this.mAnimationProgress;
    }

    /* access modifiers changed from: protected */
    public View getDimView() {
        ViewGroup actionBarOverlayLayout;
        if (this.mDimView == null && (actionBarOverlayLayout = ActionBarUtils.getActionBarOverlayLayout(this)) != null) {
            this.mDimView = actionBarOverlayLayout.findViewById(R.id.search_mask);
        }
        return this.mDimView;
    }

    public EditText getSearchInput() {
        return this.mInputView;
    }

    /* access modifiers changed from: protected */
    public ActionBarContainer getSplitActionBarContainer() {
        ViewGroup actionBarOverlayLayout;
        if (this.mSplitActionBarContainer == null && (actionBarOverlayLayout = ActionBarUtils.getActionBarOverlayLayout(this)) != null) {
            int i = 0;
            while (true) {
                if (i >= actionBarOverlayLayout.getChildCount()) {
                    break;
                }
                View childAt = actionBarOverlayLayout.getChildAt(i);
                if (childAt.getId() == R.id.split_action_bar && (childAt instanceof ActionBarContainer)) {
                    this.mSplitActionBarContainer = (ActionBarContainer) childAt;
                    break;
                }
                i++;
            }
        }
        return this.mSplitActionBarContainer;
    }

    /* access modifiers changed from: protected */
    public ViewPager getViewPager() {
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) ActionBarUtils.getActionBarOverlayLayout(this);
        if (((ActionBarImpl) actionBarOverlayLayout.getActionBar()).isFragmentViewPagerMode()) {
            return (ViewPager) actionBarOverlayLayout.findViewById(R.id.view_pager);
        }
        return null;
    }

    @Override // com.miui.internal.widget.ActionModeView
    public void initForMode(ActionMode actionMode) {
    }

    @Override // com.miui.internal.widget.ActionModeView
    public void killMode() {
        finishAnimation();
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this);
        }
        this.mActionBarContainer = null;
        this.mActionBarView = null;
        List<ActionModeAnimationListener> list = this.mAnimationListeners;
        if (list != null) {
            list.clear();
            this.mAnimationListeners = null;
        }
        this.mSplitActionBarContainer = null;
    }

    /* access modifiers changed from: protected */
    public ObjectAnimator makeAnimation() {
        ObjectAnimator objectAnimator = this.mCurrentAnimation;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.mCurrentAnimation = null;
            removeIdleHandler();
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "AnimationProgress", 0.0f, 1.0f);
        ofFloat.addListener(this);
        ofFloat.setDuration(DeviceHelper.FEATURE_WHOLE_ANIM ? 400 : 0);
        ofFloat.setInterpolator(this.mSearchActionModeViewHelper.obtainInterpolator());
        return ofFloat;
    }

    @Override // com.miui.internal.widget.ActionModeView
    public void notifyAnimationEnd(boolean z) {
        List<ActionModeAnimationListener> list = this.mAnimationListeners;
        if (list != null) {
            for (ActionModeAnimationListener actionModeAnimationListener : list) {
                actionModeAnimationListener.onStop(z);
            }
        }
    }

    @Override // com.miui.internal.widget.ActionModeView
    public void notifyAnimationStart(boolean z) {
        List<ActionModeAnimationListener> list = this.mAnimationListeners;
        if (list != null) {
            for (ActionModeAnimationListener actionModeAnimationListener : list) {
                actionModeAnimationListener.onStart(z);
            }
        }
    }

    @Override // com.miui.internal.widget.ActionModeView
    public void notifyAnimationUpdate(boolean z, float f) {
        List<ActionModeAnimationListener> list = this.mAnimationListeners;
        if (list != null) {
            for (ActionModeAnimationListener actionModeAnimationListener : list) {
                actionModeAnimationListener.onUpdate(z, f);
            }
        }
    }

    public void onAnimationCancel(Animator animator) {
        this.mAnimationCanceled = true;
    }

    public void onAnimationEnd(Animator animator) {
        ActionBarContainer actionBarContainer;
        if (!this.mAnimationCanceled) {
            this.mCurrentAnimation = null;
            notifyAnimationEnd(this.mAnimateToVisible);
            if (this.mAnimateToVisible) {
                InputMethodHelper.getInstance().showKeyBoard(this.mInputView);
            } else {
                InputMethodHelper.getInstance().hideKeyBoard(this.mInputView);
            }
            if (DeviceHelper.FEATURE_WHOLE_ANIM) {
                setResultViewMargin(this.mAnimateToVisible);
            } else {
                post(new Runnable() {
                    /* class com.miui.internal.widget.SearchActionModeView.AnonymousClass1 */

                    public void run() {
                        SearchActionModeView searchActionModeView = SearchActionModeView.this;
                        searchActionModeView.setResultViewMargin(searchActionModeView.mAnimateToVisible);
                    }
                });
            }
            if (!this.mAnimateToVisible || (actionBarContainer = this.mActionBarContainer) == null || !actionBarContainer.isBlurEnable()) {
                setContentViewTranslation(0);
                setContentViewPadding(this.mAnimateToVisible ? this.mStatusBarPaddingTop : 0, 0);
            } else {
                setContentViewTranslation(-this.mContentOriginPaddingTop);
            }
            if (!this.mAnimateToVisible) {
                setOverlayMode(false);
                setAlpha(0.0f);
                killMode();
            }
        }
    }

    public void onAnimationRepeat(Animator animator) {
    }

    public void onAnimationStart(Animator animator) {
        this.mAnimationCanceled = false;
        if (this.mAnimateToVisible) {
            setAlpha(1.0f);
            return;
        }
        View tabContainer = getActionBarContainer().getTabContainer();
        if (tabContainer != null) {
            tabContainer.setVisibility(0);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.search_mask) {
            this.mSearchActionModeViewHelper.performCancelViewClick();
        }
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mSearchActionModeViewHelper.initView(this);
        this.mInputView = (EditText) findViewById(16908297);
        this.mOriginalPaddingTop = getPaddingTop();
        View contentView = getContentView();
        if (contentView != null) {
            this.mContentOriginPaddingTop = contentView.getPaddingTop();
            this.mContentOriginPaddingBottom = contentView.getPaddingBottom();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mRequestAnimation) {
            if (this.mAnimateToVisible && shouldAnimateContent()) {
                this.mAnimateView.get().setTranslationY((float) this.mAnimateViewTranslationYStart);
            }
            queueIdleHandler();
            this.mRequestAnimation = false;
        }
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    /* access modifiers changed from: protected */
    public void pollViews() {
        getActionBarView();
        getActionBarContainer();
        getSplitActionBarContainer();
    }

    public boolean queueIdle() {
        this.mCurrentAnimation.start();
        return false;
    }

    @Override // com.miui.internal.widget.ActionModeView
    public void removeAnimationListener(ActionModeAnimationListener actionModeAnimationListener) {
        List<ActionModeAnimationListener> list;
        if (actionModeAnimationListener != null && (list = this.mAnimationListeners) != null) {
            list.remove(actionModeAnimationListener);
        }
    }

    public void setAnchorView(View view) {
        if (view != null) {
            this.mAnchorView = new WeakReference<>(view);
        }
    }

    public void setAnimateView(View view) {
        if (view != null) {
            this.mAnimateView = new WeakReference<>(view);
        }
    }

    public void setAnimationProgress(float f) {
        this.mAnimationProgress = f;
        notifyAnimationUpdate(this.mAnimateToVisible, this.mAnimationProgress);
    }

    /* access modifiers changed from: protected */
    public void setContentViewPadding(int i, int i2) {
        View contentView = getContentView();
        if (contentView != null) {
            contentView.setPadding(contentView.getPaddingLeft(), i + this.mContentOriginPaddingTop, contentView.getPaddingRight(), i2 + this.mContentOriginPaddingBottom);
        }
    }

    /* access modifiers changed from: protected */
    public void setContentViewTranslation(int i) {
        View contentView = getContentView();
        if (contentView != null) {
            contentView.setTranslationY((float) i);
        }
    }

    public void setOnBackClickListener(View.OnClickListener onClickListener) {
        this.mSearchActionModeViewHelper.setCancelViewClickListener(onClickListener);
    }

    /* access modifiers changed from: protected */
    public void setOverlayMode(boolean z) {
        ((ActionBarOverlayLayout) ActionBarUtils.getActionBarOverlayLayout(this)).setOverlayMode(z);
    }

    public void setResultView(View view) {
        if (view != null) {
            this.mResultView = new WeakReference<>(view);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                this.mResultViewOriginMarginTop = marginLayoutParams.topMargin;
                this.mResultViewOriginMarginBottom = marginLayoutParams.bottomMargin;
                this.mResultViewSet = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setResultViewMargin(boolean z) {
        int i;
        int i2;
        if (this.mResultView != null && this.mResultViewSet) {
            if (z) {
                i = (this.mActionBarContainer.getMeasuredHeight() - this.mStatusBarPaddingTop) - this.mActionBarTopMargin;
                i2 = 0;
            } else {
                i = this.mResultViewOriginMarginTop;
                i2 = this.mResultViewOriginMarginBottom;
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mResultView.get().getLayoutParams();
            marginLayoutParams.topMargin = i;
            marginLayoutParams.bottomMargin = i2;
        }
    }

    public void setStatusBarPaddingTop(int i) {
        this.mStatusBarPaddingTop = i;
    }
}
