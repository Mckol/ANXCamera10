package miuix.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.ViewProperty;
import miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator;

public class MiuiDefaultItemAnimator extends MiuiBaseDefaultItemAnimator {
    public static View.OnAttachStateChangeListener sAttachedListener = new View.OnAttachStateChangeListener() {
        /* class miuix.recyclerview.widget.MiuiDefaultItemAnimator.AnonymousClass1 */

        public void onViewAttachedToWindow(View view) {
            MiuiBaseDefaultItemAnimator.resetAnimation(view);
        }

        public void onViewDetachedFromWindow(View view) {
        }
    };
    public static AnimConfig sSpeedConfig = new AnimConfig().setFromSpeed(0.0f);

    /* access modifiers changed from: package-private */
    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void animateAddImpl(final RecyclerView.ViewHolder viewHolder) {
        notifyAddStarting(viewHolder);
        IStateStyle state = Folme.useAt(viewHolder.itemView).state();
        Float valueOf = Float.valueOf(1.0f);
        state.to(ViewProperty.ALPHA, valueOf, sSpeedConfig);
        viewHolder.itemView.postDelayed(new Runnable() {
            /* class miuix.recyclerview.widget.MiuiDefaultItemAnimator.AnonymousClass4 */

            public void run() {
                MiuiDefaultItemAnimator.this.notifyAddFinished(viewHolder);
            }
        }, Folme.useAt(viewHolder.itemView).state().predictDuration(ViewProperty.ALPHA, valueOf));
    }

    /* access modifiers changed from: package-private */
    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void animateChangeImpl(MiuiBaseDefaultItemAnimator.ChangeInfo changeInfo) {
        final RecyclerView.ViewHolder viewHolder = changeInfo.oldHolder;
        View view = null;
        View view2 = viewHolder == null ? null : viewHolder.itemView;
        final RecyclerView.ViewHolder viewHolder2 = changeInfo.newHolder;
        if (viewHolder2 != null) {
            view = viewHolder2.itemView;
        }
        if (view2 != null) {
            notifyChangeStarting(viewHolder, true);
            view2.addOnAttachStateChangeListener(sAttachedListener);
            Folme.useAt(view2).state().to(ViewProperty.TRANSLATION_X, Integer.valueOf(changeInfo.toX - changeInfo.fromX), ViewProperty.TRANSLATION_Y, Integer.valueOf(changeInfo.toY - changeInfo.fromY), sSpeedConfig);
            view2.postDelayed(new Runnable() {
                /* class miuix.recyclerview.widget.MiuiDefaultItemAnimator.AnonymousClass5 */

                public void run() {
                    MiuiDefaultItemAnimator.this.notifyChangeFinished(viewHolder, true);
                }
            }, Folme.useAt(view2).state().predictDuration(ViewProperty.TRANSLATION_X, Integer.valueOf(changeInfo.toX - changeInfo.fromX), ViewProperty.TRANSLATION_Y, Integer.valueOf(changeInfo.toY - changeInfo.fromY)));
        }
        if (view != null) {
            notifyChangeStarting(viewHolder2, false);
            Folme.useAt(view).state().to(ViewProperty.TRANSLATION_X, 0, ViewProperty.TRANSLATION_Y, 0, sSpeedConfig);
            view.postDelayed(new Runnable() {
                /* class miuix.recyclerview.widget.MiuiDefaultItemAnimator.AnonymousClass6 */

                public void run() {
                    MiuiDefaultItemAnimator.this.notifyChangeFinished(viewHolder2, false);
                }
            }, Folme.useAt(view).state().predictDuration(ViewProperty.TRANSLATION_X, 0, ViewProperty.TRANSLATION_Y, 0));
        }
    }

    /* access modifiers changed from: package-private */
    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void animateMoveImpl(MiuiBaseDefaultItemAnimator.MoveInfo moveInfo) {
        notifyMoveStarting(moveInfo.holder);
        final RecyclerView.ViewHolder viewHolder = moveInfo.holder;
        Folme.useAt(viewHolder.itemView).state().to(ViewProperty.TRANSLATION_X, 0, ViewProperty.TRANSLATION_Y, 0, sSpeedConfig);
        moveInfo.holder.itemView.postDelayed(new Runnable() {
            /* class miuix.recyclerview.widget.MiuiDefaultItemAnimator.AnonymousClass3 */

            public void run() {
                MiuiDefaultItemAnimator.this.notifyMoveFinished(viewHolder);
            }
        }, Folme.useAt(moveInfo.holder.itemView).state().predictDuration(ViewProperty.TRANSLATION_X, 0, ViewProperty.TRANSLATION_Y, 0));
    }

    /* access modifiers changed from: package-private */
    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void animateRemoveImpl(final RecyclerView.ViewHolder viewHolder) {
        notifyRemoveStarting(viewHolder);
        viewHolder.itemView.addOnAttachStateChangeListener(sAttachedListener);
        IStateStyle state = Folme.useAt(viewHolder.itemView).state();
        Float valueOf = Float.valueOf(0.0f);
        state.to(ViewProperty.ALPHA, valueOf, sSpeedConfig);
        viewHolder.itemView.postDelayed(new Runnable() {
            /* class miuix.recyclerview.widget.MiuiDefaultItemAnimator.AnonymousClass2 */

            public void run() {
                MiuiDefaultItemAnimator.this.notifyRemoveFinished(viewHolder);
            }
        }, Folme.useAt(viewHolder.itemView).state().predictDuration(ViewProperty.ALPHA, valueOf));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public long getAddDuration() {
        return 300;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public long getChangeDuration() {
        return 300;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public long getMoveDuration() {
        return 300;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public long getRemoveDuration() {
        return 300;
    }

    /* access modifiers changed from: package-private */
    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void prepareAdd(RecyclerView.ViewHolder viewHolder) {
        resetAnimation(viewHolder);
        viewHolder.itemView.setAlpha(0.0f);
    }

    /* access modifiers changed from: package-private */
    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void prepareChange(MiuiBaseDefaultItemAnimator.ChangeInfo changeInfo) {
        float translationX = changeInfo.oldHolder.itemView.getTranslationX();
        float translationY = changeInfo.oldHolder.itemView.getTranslationY();
        resetAnimation(changeInfo.oldHolder);
        int i = (int) (((float) (changeInfo.toX - changeInfo.fromX)) - translationX);
        int i2 = (int) (((float) (changeInfo.toY - changeInfo.fromY)) - translationY);
        changeInfo.oldHolder.itemView.setTranslationX(translationX);
        changeInfo.oldHolder.itemView.setTranslationY(translationY);
        RecyclerView.ViewHolder viewHolder = changeInfo.newHolder;
        if (viewHolder != null) {
            resetAnimation(viewHolder);
            changeInfo.newHolder.itemView.setTranslationX((float) (-i));
            changeInfo.newHolder.itemView.setTranslationY((float) (-i2));
        }
    }

    /* access modifiers changed from: package-private */
    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void prepareMove(MiuiBaseDefaultItemAnimator.MoveInfo moveInfo) {
        moveInfo.holder.itemView.setTranslationX((float) (moveInfo.fromX - moveInfo.toX));
        moveInfo.holder.itemView.setTranslationY((float) (moveInfo.fromY - moveInfo.toY));
    }

    /* access modifiers changed from: package-private */
    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator
    public void resetAnimation(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder != null) {
            Folme.useAt(viewHolder.itemView).state().cancel(ViewProperty.TRANSLATION_X, ViewProperty.TRANSLATION_Y, ViewProperty.ALPHA);
            MiuiBaseDefaultItemAnimator.resetAnimation(viewHolder.itemView);
        }
    }
}
