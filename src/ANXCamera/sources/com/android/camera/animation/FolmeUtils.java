package com.android.camera.animation;

import android.view.View;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import com.android.camera.R;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.IVisibleStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

public class FolmeUtils {
    public static final int ANIMATION_DURATION = 200;
    private static final int STATE_SELECTED = 1;
    private static final int STATE_UNSELECTED = 2;

    public static void animateDeparture(View view, final Runnable runnable) {
        Folme.useAt(view).visible().hide(new AnimConfig().addListeners(new TransitionListener() {
            /* class com.android.camera.animation.FolmeUtils.AnonymousClass2 */

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                Runnable runnable = runnable;
                if (runnable != null) {
                    runnable.run();
                }
            }
        }));
    }

    public static void animateEntrance(View view) {
        AnimState add = new AnimState(IVisibleStyle.VisibleType.HIDE).add(ViewProperty.TRANSLATION_Y, 60, new long[0]).add(ViewProperty.AUTO_ALPHA, 0.0f, new long[0]);
        AnimState add2 = new AnimState(IVisibleStyle.VisibleType.SHOW).add(ViewProperty.TRANSLATION_Y, 0, new long[0]).add(ViewProperty.AUTO_ALPHA, 1.0f, new long[0]);
        Folme.useAt(view).state().fromTo(add, add2, new AnimConfig(ViewProperty.TRANSLATION_Y).setEase(6, 200.0f));
    }

    public static void animateHide(View view) {
        view.setVisibility(8);
        Folme.useAt(view).visible().setShow().hide(new AnimConfig[0]);
    }

    public static void animateShow(View view) {
        view.setVisibility(0);
        Folme.useAt(view).visible().setHide().show(new AnimConfig[0]);
    }

    public static void animateShow(View view, final Runnable runnable) {
        view.setVisibility(0);
        Folme.useAt(view).visible().setHide().show(new AnimConfig().addListeners(new TransitionListener() {
            /* class com.android.camera.animation.FolmeUtils.AnonymousClass3 */

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                Runnable runnable = runnable;
                if (runnable != null) {
                    runnable.run();
                }
            }
        }));
    }

    public static void clean(View view) {
        Folme.clean(view);
    }

    public static void handleAdapterItemSwitchState(boolean z, @Nullable View view) {
        if (view != null) {
            if (view.getVisibility() != 0) {
                view.setVisibility(0);
            }
            int i = 2;
            if (view.getTag() == null) {
                if (z) {
                    i = 1;
                }
                view.setTag(Integer.valueOf(i));
                if (z) {
                    Folme.useAt(view).visible().setShow();
                } else {
                    Folme.useAt(view).visible().setHide();
                }
            } else {
                if (((Integer) view.getTag()).intValue() != (z ? 1 : 2)) {
                    if (z) {
                        i = 1;
                    }
                    view.setTag(Integer.valueOf(i));
                    Folme.clean(view);
                    if (z) {
                        Folme.useAt(view).visible().show(new AnimConfig().setEase(6, 200.0f));
                    } else {
                        Folme.useAt(view).visible().hide(new AnimConfig().setEase(6, 200.0f));
                    }
                }
            }
        }
    }

    public static void handleListItemTouch(View view) {
        Folme.useAt(view).touch().handleTouchOf(view, new AnimConfig[0]);
    }

    public static void handleListItemTouch(View view, int i) {
        Folme.useAt(view).touch().handleTouchOf(view, new AnimConfig().setEase(i, new float[0]));
    }

    public static void handleTouchScale(final View view, final View.OnClickListener onClickListener) {
        Folme.useAt(view).touch().handleTouchOf(view, new AnimConfig().addListeners(new TransitionListener() {
            /* class com.android.camera.animation.FolmeUtils.AnonymousClass1 */

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                if (obj == ITouchStyle.TouchType.UP) {
                    onClickListener.onClick(view);
                }
            }
        }));
    }

    public static void setupAdapterItemBackgroundColor(int i, int i2, View view) {
        Folme.useAt(view).state().setup("show").add(ViewProperty.BACKGROUND, Integer.valueOf(i2), new long[0]).setup("hide").add(ViewProperty.BACKGROUND, Integer.valueOf(i), new long[0]);
    }

    public static void touchButtonTint(@ColorRes int i, View... viewArr) {
        for (View view : viewArr) {
            touchScaleTint(view, 1.0f, view.getResources().getColor(i), null);
        }
    }

    public static void touchDialogButtonTint(View... viewArr) {
        for (View view : viewArr) {
            touchScaleTint(view, 1.0f, view.getResources().getColor(R.color.dialog_button_bg_pressed), null);
        }
    }

    public static void touchItemScale(View view) {
        Folme.useAt(view).touch().handleTouchOf(view, new AnimConfig[0]);
    }

    public static void touchScale(View... viewArr) {
        for (View view : viewArr) {
            touchScaleTint(view, 0.9f, 0, null);
        }
    }

    public static void touchScaleTint(View view, float f, int i, AnimConfig animConfig) {
        Folme.useAt(view).touch().setScale(f, ITouchStyle.TouchType.DOWN).setTint(i).handleTouchOf(view, new AnimConfig[0]);
    }

    public static void touchScaleTint(View... viewArr) {
        for (View view : viewArr) {
            touchScaleTint(view, 0.9f, 855638016, null);
        }
    }

    public static void touchTint(View view) {
        touchScaleTint(view, 1.0f, 436207616, null);
    }

    public static void touchTint(View... viewArr) {
        for (View view : viewArr) {
            touchTint(view);
        }
    }

    public static void touchTintDefaultDayNight(View view) {
        Folme.useAt(view).touch().setScale(1.0f, ITouchStyle.TouchType.DOWN).handleTouchOf(view, new AnimConfig[0]);
    }
}
