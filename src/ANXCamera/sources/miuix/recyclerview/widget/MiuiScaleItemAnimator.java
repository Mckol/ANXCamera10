package miuix.recyclerview.widget;

import android.util.TypedValue;
import androidx.recyclerview.widget.RecyclerView;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.property.ViewProperty;

public class MiuiScaleItemAnimator extends MiuiDefaultItemAnimator {
    private static final float DEFAULT_SCALE = 0.8f;
    private static final int SCALE_DIS = 20;
    private float mScaleDist = Float.MIN_VALUE;

    private float getFlomeScale(RecyclerView.ViewHolder viewHolder) {
        if (this.mScaleDist == Float.MIN_VALUE) {
            this.mScaleDist = TypedValue.applyDimension(1, 20.0f, viewHolder.itemView.getResources().getDisplayMetrics());
        }
        float max = (float) Math.max(viewHolder.itemView.getWidth(), viewHolder.itemView.getHeight());
        return Math.max((max - this.mScaleDist) / max, (float) DEFAULT_SCALE);
    }

    /* access modifiers changed from: package-private */
    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator, miuix.recyclerview.widget.MiuiDefaultItemAnimator
    public void animateAddImpl(final RecyclerView.ViewHolder viewHolder) {
        notifyAddStarting(viewHolder);
        IStateStyle state = Folme.useAt(viewHolder.itemView).state();
        Float valueOf = Float.valueOf(1.0f);
        state.to(ViewProperty.ALPHA, valueOf, ViewProperty.SCALE_X, valueOf, ViewProperty.SCALE_Y, valueOf, MiuiDefaultItemAnimator.sSpeedConfig);
        viewHolder.itemView.postDelayed(new Runnable() {
            /* class miuix.recyclerview.widget.MiuiScaleItemAnimator.AnonymousClass1 */

            public void run() {
                MiuiScaleItemAnimator.this.notifyAddFinished(viewHolder);
            }
        }, Folme.useAt(viewHolder.itemView).state().predictDuration(ViewProperty.ALPHA, valueOf, ViewProperty.SCALE_X, valueOf, ViewProperty.SCALE_Y, valueOf));
    }

    /* access modifiers changed from: package-private */
    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator, miuix.recyclerview.widget.MiuiDefaultItemAnimator
    public void animateRemoveImpl(final RecyclerView.ViewHolder viewHolder) {
        float flomeScale = getFlomeScale(viewHolder);
        notifyRemoveStarting(viewHolder);
        viewHolder.itemView.addOnAttachStateChangeListener(MiuiDefaultItemAnimator.sAttachedListener);
        IStateStyle state = Folme.useAt(viewHolder.itemView).state();
        Float valueOf = Float.valueOf(0.0f);
        state.to(ViewProperty.ALPHA, valueOf, ViewProperty.SCALE_X, Float.valueOf(flomeScale), ViewProperty.SCALE_Y, Float.valueOf(flomeScale), MiuiDefaultItemAnimator.sSpeedConfig);
        viewHolder.itemView.postDelayed(new Runnable() {
            /* class miuix.recyclerview.widget.MiuiScaleItemAnimator.AnonymousClass2 */

            public void run() {
                MiuiScaleItemAnimator.this.notifyRemoveFinished(viewHolder);
            }
        }, Folme.useAt(viewHolder.itemView).state().predictDuration(ViewProperty.ALPHA, valueOf, ViewProperty.SCALE_X, Float.valueOf(flomeScale), ViewProperty.SCALE_Y, Float.valueOf(flomeScale)));
    }

    /* access modifiers changed from: package-private */
    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator, miuix.recyclerview.widget.MiuiDefaultItemAnimator
    public void prepareAdd(RecyclerView.ViewHolder viewHolder) {
        super.prepareAdd(viewHolder);
        float flomeScale = getFlomeScale(viewHolder);
        viewHolder.itemView.setScaleX(flomeScale);
        viewHolder.itemView.setScaleY(flomeScale);
    }

    /* access modifiers changed from: package-private */
    @Override // miuix.recyclerview.widget.MiuiBaseDefaultItemAnimator, miuix.recyclerview.widget.MiuiDefaultItemAnimator
    public void resetAnimation(RecyclerView.ViewHolder viewHolder) {
        super.resetAnimation(viewHolder);
        if (viewHolder != null) {
            Folme.useAt(viewHolder.itemView).state().cancel(ViewProperty.SCALE_X, ViewProperty.SCALE_Y);
            viewHolder.itemView.setScaleX(1.0f);
            viewHolder.itemView.setScaleY(1.0f);
        }
    }
}
