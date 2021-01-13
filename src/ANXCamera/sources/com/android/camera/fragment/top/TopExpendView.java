package com.android.camera.fragment.top;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.customization.TintColor;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.config.ComponentConfigFlash;
import com.android.camera.data.data.config.ComponentConfigHdr;
import com.android.camera.data.data.runing.ComponentRunningAiAudio;
import com.android.camera.data.data.runing.ComponentRunningEisPro;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.module.ModuleManager;
import com.android.camera.ui.ColorImageView;
import java.util.List;
import miui.view.animation.CubicEaseOutInterpolator;

public class TopExpendView extends LinearLayout {
    private TopExpandAdapter mAdapter;
    private boolean mAnimEnable;
    private int mDegree;
    private int mEndViewRight;
    private boolean mIsAnimRuning;
    private int mStartViewLeft;

    public TopExpendView(Context context) {
        this(context, null);
    }

    public TopExpendView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAnimEnable = true;
    }

    private void addItemInAnimator(final View view, int i) {
        view.animate().cancel();
        view.clearAnimation();
        long j = (long) (TopBarAnimationComponent.DEBUG_ANIMATION_TIME_MULTIPLE * 300);
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f);
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat("translationX", (float) (-((Util.isLayoutRTL(getContext()) ? view.getRight() : view.getLeft()) - this.mAdapter.getAnchorViewX())), 0.0f);
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, i == this.mAdapter.getDefaultSelectPosition() ? new PropertyValuesHolder[]{ofFloat2} : new PropertyValuesHolder[]{ofFloat, ofFloat2});
        ofPropertyValuesHolder.setDuration(j);
        ofPropertyValuesHolder.setInterpolator(new CubicEaseOutInterpolator());
        ofPropertyValuesHolder.addListener(new AnimatorListenerAdapter() {
            /* class com.android.camera.fragment.top.TopExpendView.AnonymousClass2 */

            public void onAnimationCancel(Animator animator) {
                TopExpendView.this.mIsAnimRuning = false;
                view.setEnabled(true);
            }

            public void onAnimationEnd(Animator animator) {
                TopExpendView.this.mIsAnimRuning = false;
                view.setEnabled(true);
            }

            public void onAnimationStart(Animator animator) {
                TopExpendView.this.mIsAnimRuning = true;
                view.setVisibility(0);
                view.setEnabled(false);
            }
        });
        if (i == this.mAdapter.getDefaultSelectPosition()) {
            if (!isSelectOnColorItem()) {
                ValueAnimator ofArgb = ValueAnimator.ofArgb(getResources().getColor(R.color.white), TintColor.tintColor());
                ofArgb.setDuration(j);
                ofArgb.setInterpolator(new CubicEaseOutInterpolator());
                ofArgb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    /* class com.android.camera.fragment.top.TopExpendView.AnonymousClass3 */

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        ((LabelItemView) view).setColorAndRefresh(((Integer) valueAnimator.getAnimatedValue()).intValue());
                    }
                });
                ofArgb.start();
            } else {
                ((LabelItemView) view).setColorAndRefresh(TintColor.tintColor());
            }
        }
        ofPropertyValuesHolder.start();
    }

    private void addItemOutAnimator(final View view, int i, final Runnable runnable) {
        view.animate().cancel();
        view.clearAnimation();
        long j = (long) (TopBarAnimationComponent.DEBUG_ANIMATION_TIME_MULTIPLE * 300);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationX", 0.0f, (float) (-((Util.isLayoutRTL(getContext()) ? view.getRight() : view.getLeft()) - this.mAdapter.getAnchorViewX())));
        ofFloat.setDuration(j);
        ofFloat.setInterpolator(new CubicEaseOutInterpolator());
        ofFloat.addListener(new AnimatorListenerAdapter() {
            /* class com.android.camera.fragment.top.TopExpendView.AnonymousClass4 */

            public void onAnimationCancel(Animator animator) {
                TopExpendView.this.mIsAnimRuning = false;
                view.setVisibility(4);
                Runnable runnable = runnable;
                if (runnable != null) {
                    runnable.run();
                }
                TopExpendView.this.setOnClickListener(null);
            }

            public void onAnimationEnd(Animator animator) {
                TopExpendView.this.mIsAnimRuning = false;
                view.setVisibility(4);
                Runnable runnable = runnable;
                if (runnable != null) {
                    runnable.run();
                }
                TopExpendView.this.setOnClickListener(null);
            }

            public void onAnimationStart(Animator animator) {
                view.setEnabled(false);
                TopExpendView.this.mIsAnimRuning = true;
            }
        });
        long j2 = (long) (TopBarAnimationComponent.DEBUG_ANIMATION_TIME_MULTIPLE * 100);
        ColorImageView colorImageView = null;
        if (i != this.mAdapter.getSelectPosition()) {
            colorImageView = view;
        } else {
            ColorImageView labelView = ((LabelItemView) view).getLabelView();
            if (labelView.getVisibility() == 0) {
                colorImageView = labelView;
            }
        }
        if (colorImageView != null) {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(colorImageView, "alpha", 1.0f, 0.0f);
            ofFloat2.setDuration(j2);
            ofFloat2.setInterpolator(new CubicEaseOutInterpolator());
            ofFloat2.start();
        }
        long j3 = (long) (TopBarAnimationComponent.DEBUG_ANIMATION_TIME_MULTIPLE * 300);
        if (i == this.mAdapter.getSelectPosition() && this.mAdapter.getDefaultSelectPosition() == this.mAdapter.getSelectPosition() && !isSelectOnColorItem()) {
            ValueAnimator ofArgb = ValueAnimator.ofArgb(TintColor.tintColor(), getResources().getColor(R.color.white));
            ofArgb.setDuration(j3);
            ofArgb.setInterpolator(new CubicEaseOutInterpolator());
            ofArgb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.android.camera.fragment.top.TopExpendView.AnonymousClass5 */

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ((LabelItemView) view).setColorAndRefresh(((Integer) valueAnimator.getAnimatedValue()).intValue());
                }
            });
            ofArgb.start();
        } else if (i == this.mAdapter.getSelectPosition() && isSelectOnColorItem()) {
            ((LabelItemView) view).setColorAndRefresh(TintColor.tintColor());
        }
        ofFloat.start();
    }

    private void initView() {
        removeAllViews();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.top_expend_spaces_item_decoration);
        for (int i = 0; i < this.mAdapter.getCount(); i++) {
            CommonRecyclerViewHolder onCreateViewHolder = this.mAdapter.onCreateViewHolder((ViewGroup) this, 0);
            View view = onCreateViewHolder.itemView;
            this.mAdapter.onBindViewHolder(onCreateViewHolder, i);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.leftMargin = dimensionPixelSize;
            layoutParams.rightMargin = dimensionPixelSize;
            view.setLayoutParams(layoutParams);
            view.setVisibility(4);
            addView(view);
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /* class com.android.camera.fragment.top.TopExpendView.AnonymousClass1 */

            public void onGlobalLayout() {
                TopExpendView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                TopExpendView.this.updateUI();
            }
        });
    }

    private boolean isSelectOnColorItem() {
        ComponentData componentData;
        TopExpandAdapter topExpandAdapter = this.mAdapter;
        if (topExpandAdapter == null || (componentData = topExpandAdapter.getComponentData()) == null) {
            return false;
        }
        ComponentDataItem selectComponentDataItem = this.mAdapter.getSelectComponentDataItem();
        if ((componentData instanceof ComponentConfigHdr) && (selectComponentDataItem.mValue.equals("on") || selectComponentDataItem.mValue.equals("normal"))) {
            return true;
        }
        if ((componentData instanceof ComponentConfigFlash) && (selectComponentDataItem.mValue.equals("1") || selectComponentDataItem.mValue.equals("2") || selectComponentDataItem.mValue.equals(ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_ON) || selectComponentDataItem.mValue.equals("5"))) {
            return true;
        }
        if (!(componentData instanceof ComponentRunningEisPro) || TextUtils.equals("off", selectComponentDataItem.mValue)) {
            return (componentData instanceof ComponentRunningAiAudio) && !TextUtils.equals("normal", selectComponentDataItem.mValue);
        }
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateUI() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            childAt.setRotation((float) this.mDegree);
            if (this.mAnimEnable) {
                addItemInAnimator(childAt, i);
            }
            if (i == getChildCount() - 1) {
                this.mEndViewRight = childAt.getRight();
            } else if (i == 0) {
                this.mStartViewLeft = childAt.getLeft();
            }
        }
    }

    public int getEndViewRight() {
        return this.mEndViewRight;
    }

    public int getStartViewLeft() {
        return this.mStartViewLeft;
    }

    public boolean isAnimRuning() {
        return this.mIsAnimRuning;
    }

    public void provideRotateItem(List<View> list, int i) {
        if (!ModuleManager.isLandscapeMode()) {
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                list.add(getChildAt(i2));
            }
            this.mDegree = i;
        }
    }

    public void revertExpendView(boolean z, Runnable runnable) {
        if (!z) {
            if (runnable != null) {
                runnable.run();
            }
            setOnClickListener(null);
        }
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (!z) {
                childAt.setVisibility(4);
            } else if (this.mAdapter.getSelectPosition() == i) {
                addItemOutAnimator(childAt, i, runnable);
            } else {
                addItemOutAnimator(childAt, i, null);
            }
        }
    }

    public void setAdapter(TopExpandAdapter topExpandAdapter) {
        this.mAdapter = topExpandAdapter;
        initView();
    }

    public void setAnimEnable(boolean z) {
        this.mAnimEnable = z;
    }

    public void setRotation(int i) {
        this.mDegree = i;
    }
}
