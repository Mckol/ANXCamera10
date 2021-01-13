package com.android.camera.features.mimoji2.widget.baseview;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.recyclerview.widget.RecyclerView;
import miui.view.animation.CubicEaseOutInterpolator;

public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {
    private CubicEaseOutInterpolator mCubicEaseOut = new CubicEaseOutInterpolator();

    public BaseRecyclerViewHolder(@NonNull View view) {
        super(view);
    }

    public void alphaGone(int i, View... viewArr) {
        if (!(viewArr == null || viewArr.length == 0)) {
            for (View view : viewArr) {
                if (view != null) {
                    if (view.getVisibility() != 0) {
                        view.setVisibility(8);
                    } else {
                        ViewCompat.animate(view).setDuration((long) i).alpha(0.0f).setInterpolator(getCubicEaseOut()).setListener(new ViewPropertyAnimatorListener() {
                            /* class com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerViewHolder.AnonymousClass2 */

                            @Override // androidx.core.view.ViewPropertyAnimatorListener
                            public void onAnimationCancel(View view) {
                                view.setVisibility(8);
                            }

                            @Override // androidx.core.view.ViewPropertyAnimatorListener
                            public void onAnimationEnd(View view) {
                                view.setVisibility(8);
                            }

                            @Override // androidx.core.view.ViewPropertyAnimatorListener
                            public void onAnimationStart(View view) {
                            }
                        }).start();
                    }
                }
            }
        }
    }

    public void alphaShow(int i, View... viewArr) {
        if (!(viewArr == null || viewArr.length == 0)) {
            for (View view : viewArr) {
                if (view != null) {
                    ViewCompat.setAlpha(view, 0.0f);
                    ViewCompat.animate(view).setDuration((long) i).alpha(1.0f).setInterpolator(getCubicEaseOut()).setListener(new ViewPropertyAnimatorListener() {
                        /* class com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerViewHolder.AnonymousClass1 */

                        @Override // androidx.core.view.ViewPropertyAnimatorListener
                        public void onAnimationCancel(View view) {
                        }

                        @Override // androidx.core.view.ViewPropertyAnimatorListener
                        public void onAnimationEnd(View view) {
                        }

                        @Override // androidx.core.view.ViewPropertyAnimatorListener
                        public void onAnimationStart(View view) {
                            view.setVisibility(0);
                        }
                    }).start();
                }
            }
        }
    }

    public CubicEaseOutInterpolator getCubicEaseOut() {
        return this.mCubicEaseOut;
    }

    public View[] getRotateViews() {
        return null;
    }

    public void goneView(View... viewArr) {
        if (!(viewArr == null || viewArr.length == 0)) {
            for (View view : viewArr) {
                if (view != null) {
                    view.setVisibility(8);
                }
            }
        }
    }

    public void setClickListener(OnRecyclerItemClickListener<T> onRecyclerItemClickListener, T t, int i, View view) {
        this.itemView.setOnClickListener(new a(onRecyclerItemClickListener, t, i, view));
    }

    public abstract void setData(T t, int i);

    public void showView(View... viewArr) {
        if (!(viewArr == null || viewArr.length == 0)) {
            for (View view : viewArr) {
                if (view != null) {
                    view.setVisibility(0);
                    view.setAlpha(1.0f);
                }
            }
        }
    }
}
