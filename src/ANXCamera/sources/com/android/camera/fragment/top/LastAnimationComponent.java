package com.android.camera.fragment.top;

import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.TranslateXOnSubscribe;
import com.android.camera.data.data.config.TopConfigItem;
import com.ss.android.vesdk.VEResult;
import java.util.ArrayList;
import java.util.List;
import miui.view.animation.QuarticEaseInInterpolator;
import miui.view.animation.QuarticEaseOutInterpolator;

public class LastAnimationComponent {
    public View mAnchorView;
    private int mExpandOffset;
    private int mGravity;
    public List<View> mHidedViews;
    private Interpolator mQuartEaseIn = new QuarticEaseInInterpolator();
    private Interpolator mQuartEaseOut = new QuarticEaseOutInterpolator();
    public RecyclerView mRecyclerView;
    public int mReverseLeft;
    public int mReverseRecyclerViewLeft;
    private int mShowDelay;
    public boolean mShown;

    public void hideOtherViews(int i, List<ImageView> list) {
        if (!this.mShown) {
            this.mShown = true;
            List<View> list2 = this.mHidedViews;
            if (list2 == null) {
                this.mHidedViews = new ArrayList();
            } else {
                list2.clear();
            }
            for (ImageView imageView : list) {
                TopConfigItem topConfigItem = (TopConfigItem) imageView.getTag();
                if ((topConfigItem == null || topConfigItem.configItem != i) && imageView.getVisibility() == 0 && imageView.getAlpha() != 0.0f) {
                    this.mHidedViews.add(imageView);
                    ViewCompat.setAlpha(imageView, 1.0f);
                    ViewCompat.animate(imageView).setStartDelay(0).setDuration(this.mShowDelay > 0 ? 140 : 280).alpha(0.0f).translationXBy((float) this.mExpandOffset).setInterpolator(this.mShowDelay > 0 ? this.mQuartEaseIn : this.mQuartEaseOut).start();
                    imageView.setEnabled(false);
                }
            }
        }
    }

    public boolean reverse(boolean z) {
        if (!this.mShown) {
            return false;
        }
        this.mShown = false;
        List<View> list = this.mHidedViews;
        if (list != null) {
            for (View view : list) {
                if (z) {
                    ViewCompat.setAlpha(view, 0.0f);
                    ViewCompat.animate(view).setStartDelay(0).setDuration(280).alpha(1.0f).translationX(0.0f).setInterpolator(this.mQuartEaseOut).start();
                    view.setEnabled(true);
                } else {
                    ViewCompat.setTranslationX(view, 0.0f);
                    AlphaInOnSubscribe.directSetResult(view);
                }
            }
            this.mHidedViews.clear();
            this.mHidedViews = null;
        }
        View view2 = this.mAnchorView;
        if (view2 != null) {
            if (z) {
                ViewCompat.animate(view2).translationX((float) (this.mReverseLeft - this.mAnchorView.getLeft())).setStartDelay(0).setDuration(280).setInterpolator(this.mQuartEaseOut).start();
            } else {
                TranslateXOnSubscribe.directSetResult(view2, this.mReverseLeft - view2.getLeft());
            }
            this.mAnchorView = null;
        }
        if (z) {
            ViewCompat.animate(this.mRecyclerView).setStartDelay(0).alpha(0.0f).translationX((float) (this.mReverseRecyclerViewLeft - this.mRecyclerView.getLeft())).setDuration(280).setInterpolator(this.mQuartEaseOut).withEndAction(new Runnable() {
                /* class com.android.camera.fragment.top.LastAnimationComponent.AnonymousClass1 */

                public void run() {
                    LastAnimationComponent.this.mRecyclerView.setVisibility(4);
                    LastAnimationComponent.this.mRecyclerView = null;
                }
            }).start();
        } else {
            ViewCompat.setAlpha(this.mRecyclerView, 0.0f);
            RecyclerView recyclerView = this.mRecyclerView;
            ViewCompat.setTranslationX(recyclerView, (float) (this.mReverseRecyclerViewLeft - recyclerView.getLeft()));
            this.mRecyclerView.setVisibility(4);
            this.mRecyclerView = null;
        }
        return true;
    }

    public void setExpandGravity(int i) {
        this.mGravity = i;
        if (i == 3) {
            this.mExpandOffset = VEResult.TER_UNSUPPORTED;
            this.mShowDelay = 0;
            return;
        }
        this.mExpandOffset = 200;
        this.mShowDelay = 140;
    }

    public void showExtraView(int i, int i2) {
        ViewCompat.setAlpha(this.mRecyclerView, 0.0f);
        ViewCompat.setTranslationX(this.mRecyclerView, (float) i);
        ViewCompat.animate(this.mRecyclerView).setStartDelay((long) this.mShowDelay).alpha(1.0f).translationX((float) i2).setDuration(280).setInterpolator(this.mQuartEaseOut).start();
    }

    public void translateAnchorView(int i) {
        ViewCompat.animate(this.mAnchorView).translationX((float) i).setStartDelay((long) this.mShowDelay).setDuration(280).setInterpolator(this.mQuartEaseOut).start();
    }
}
