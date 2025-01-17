package com.android.camera.fragment.mimoji;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;

public class BubbleEditMimojiPresenter {
    private static final int INVISIBLE_STATE = -1;
    public static final int RESET_STATE = -2;
    private static final String TAG = "BubbleEditMimojiPresenter";
    private static final int VISIBLE_STATE = 1;
    BubblePop bubblePop1;
    private int downMove;
    private boolean isAnimationingDele = false;
    private boolean isAnimationingEdit = false;
    private double leftMove;
    Context mContext;
    private int mHashCodeBubble = -1;
    private boolean mIsRTL;
    public int[] mShowBubbleState = new int[3];
    public View mTargetView;
    private double rightMove;
    private int topMove;

    /* access modifiers changed from: package-private */
    public class BubblePop {
        public static final int DELETE_PROCESS = 102;
        public static final int EDIT_PROCESS = 101;
        public static final int HIDE_STATE = 104;
        public static final int SHOW_STATE = 103;
        private boolean hasAddView = false;
        private RelativeLayout.LayoutParams layoutParamsDelete;
        private RelativeLayout.LayoutParams layoutParamsEdit;
        Context mContext;
        private int mHashCode;
        ImageView mIvDeleteFisrt;
        ImageView mIvEditFirst;
        public int[] mLocationSelect = new int[3];
        private RelativeLayout mRootView;
        private int processState = 104;

        BubblePop(Context context, View.OnClickListener onClickListener, RelativeLayout relativeLayout) {
            this.mContext = context;
            this.mRootView = relativeLayout;
            this.mIvDeleteFisrt = new ImageView(this.mContext);
            this.mIvDeleteFisrt.setImageDrawable(this.mContext.getDrawable(R.drawable.mimoji_delete));
            this.mIvDeleteFisrt.setBackgroundResource(R.drawable.bg_popup_indicator_no_stroke);
            this.mIvDeleteFisrt.setTag(102);
            this.mIvDeleteFisrt.setOnClickListener(onClickListener);
            this.mIvEditFirst = new ImageView(this.mContext);
            this.mIvEditFirst.setImageDrawable(this.mContext.getDrawable(R.drawable.mimoji_edit));
            this.mIvEditFirst.setBackgroundResource(R.drawable.bg_popup_indicator_no_stroke);
            this.mIvEditFirst.setTag(101);
            this.mIvEditFirst.setOnClickListener(onClickListener);
            FolmeUtils.touchScaleTint(this.mIvDeleteFisrt, this.mIvEditFirst);
            int[] iArr = this.mLocationSelect;
            iArr[0] = -1;
            iArr[1] = -1;
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.manually_indicator_layout_width);
            int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.manually_indicator_layout_width);
            this.layoutParamsEdit = new RelativeLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize2);
            this.layoutParamsDelete = new RelativeLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize2);
        }

        public int getProcessState() {
            return this.processState;
        }

        public void hideBubbleAni() {
            ImageView imageView;
            if (this.mIvDeleteFisrt != null && (imageView = this.mIvEditFirst) != null) {
                this.processState = 104;
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(imageView, View.SCALE_X, 1.0f, 0.0f);
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mIvEditFirst, View.SCALE_Y, 1.0f, 0.0f);
                ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mIvEditFirst, View.ALPHA, 1.0f, 0.0f);
                ImageView imageView2 = this.mIvEditFirst;
                float[] fArr = new float[2];
                fArr[0] = 0.0f;
                fArr[1] = (float) (BubbleEditMimojiPresenter.this.mIsRTL ? BubbleEditMimojiPresenter.this.leftMove : BubbleEditMimojiPresenter.this.rightMove);
                ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(imageView2, "translationX", fArr);
                ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(this.mIvEditFirst, "translationY", 0.0f, (float) BubbleEditMimojiPresenter.this.downMove);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3, ofFloat4, ofFloat5);
                animatorSet.setDuration(120L);
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    /* class com.android.camera.fragment.mimoji.BubbleEditMimojiPresenter.BubblePop.AnonymousClass3 */

                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        if (BubbleEditMimojiPresenter.this.mIsRTL) {
                            RelativeLayout.LayoutParams layoutParams = BubblePop.this.layoutParamsEdit;
                            BubblePop bubblePop = BubblePop.this;
                            layoutParams.setMargins(0, bubblePop.mLocationSelect[1] + BubbleEditMimojiPresenter.this.downMove + BubbleEditMimojiPresenter.this.topMove, BubblePop.this.mLocationSelect[0], 0);
                        } else {
                            RelativeLayout.LayoutParams layoutParams2 = BubblePop.this.layoutParamsEdit;
                            BubblePop bubblePop2 = BubblePop.this;
                            int[] iArr = bubblePop2.mLocationSelect;
                            layoutParams2.setMargins(iArr[0], iArr[1] + BubbleEditMimojiPresenter.this.downMove + BubbleEditMimojiPresenter.this.topMove, 0, 0);
                        }
                        BubblePop bubblePop3 = BubblePop.this;
                        bubblePop3.mIvEditFirst.setLayoutParams(bubblePop3.layoutParamsEdit);
                        BubblePop.this.mIvEditFirst.setVisibility(4);
                        BubbleEditMimojiPresenter.this.isAnimationingEdit = false;
                    }

                    public void onAnimationStart(Animator animator) {
                        super.onAnimationStart(animator);
                        RelativeLayout.LayoutParams layoutParams = BubblePop.this.layoutParamsEdit;
                        BubblePop bubblePop = BubblePop.this;
                        layoutParams.setMarginStart((int) (((double) bubblePop.mLocationSelect[0]) + BubbleEditMimojiPresenter.this.leftMove));
                        RelativeLayout.LayoutParams layoutParams2 = BubblePop.this.layoutParamsEdit;
                        BubblePop bubblePop2 = BubblePop.this;
                        layoutParams2.topMargin = bubblePop2.mLocationSelect[1] + BubbleEditMimojiPresenter.this.topMove;
                        BubblePop bubblePop3 = BubblePop.this;
                        bubblePop3.mIvEditFirst.setLayoutParams(bubblePop3.layoutParamsEdit);
                        BubblePop.this.mIvEditFirst.setVisibility(0);
                        BubbleEditMimojiPresenter.this.isAnimationingEdit = true;
                    }
                });
                AnimatorSet animatorSet2 = new AnimatorSet();
                ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(this.mIvDeleteFisrt, View.SCALE_X, 1.0f, 0.0f);
                ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(this.mIvDeleteFisrt, View.SCALE_Y, 1.0f, 0.0f);
                ObjectAnimator ofFloat8 = ObjectAnimator.ofFloat(this.mIvDeleteFisrt, View.ALPHA, 1.0f, 0.0f);
                ImageView imageView3 = this.mIvDeleteFisrt;
                float[] fArr2 = new float[2];
                fArr2[0] = 0.0f;
                fArr2[1] = (float) (BubbleEditMimojiPresenter.this.mIsRTL ? BubbleEditMimojiPresenter.this.rightMove : BubbleEditMimojiPresenter.this.leftMove);
                animatorSet2.playTogether(ofFloat6, ofFloat7, ofFloat8, ObjectAnimator.ofFloat(imageView3, "translationX", fArr2), ObjectAnimator.ofFloat(this.mIvDeleteFisrt, "translationY", 0.0f, (float) BubbleEditMimojiPresenter.this.downMove));
                animatorSet2.setDuration(120L);
                animatorSet2.addListener(new AnimatorListenerAdapter() {
                    /* class com.android.camera.fragment.mimoji.BubbleEditMimojiPresenter.BubblePop.AnonymousClass4 */

                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        if (BubbleEditMimojiPresenter.this.mIsRTL) {
                            RelativeLayout.LayoutParams layoutParams = BubblePop.this.layoutParamsEdit;
                            BubblePop bubblePop = BubblePop.this;
                            layoutParams.setMargins(0, bubblePop.mLocationSelect[1] + BubbleEditMimojiPresenter.this.downMove + BubbleEditMimojiPresenter.this.topMove, BubblePop.this.mLocationSelect[0], 0);
                        } else {
                            RelativeLayout.LayoutParams layoutParams2 = BubblePop.this.layoutParamsDelete;
                            BubblePop bubblePop2 = BubblePop.this;
                            int[] iArr = bubblePop2.mLocationSelect;
                            layoutParams2.setMargins(iArr[0], iArr[1] + BubbleEditMimojiPresenter.this.downMove + BubbleEditMimojiPresenter.this.topMove, 0, 0);
                        }
                        BubblePop.this.mIvDeleteFisrt.setVisibility(4);
                        BubblePop bubblePop3 = BubblePop.this;
                        bubblePop3.mIvDeleteFisrt.setLayoutParams(bubblePop3.layoutParamsDelete);
                        BubblePop bubblePop4 = BubblePop.this;
                        int[] iArr2 = bubblePop4.mLocationSelect;
                        iArr2[0] = -1;
                        iArr2[1] = -1;
                        iArr2[2] = -1;
                        BubbleEditMimojiPresenter.this.isAnimationingDele = false;
                    }

                    public void onAnimationStart(Animator animator) {
                        super.onAnimationStart(animator);
                        RelativeLayout.LayoutParams layoutParams = BubblePop.this.layoutParamsDelete;
                        BubblePop bubblePop = BubblePop.this;
                        layoutParams.setMarginStart((int) (((double) bubblePop.mLocationSelect[0]) + BubbleEditMimojiPresenter.this.rightMove));
                        RelativeLayout.LayoutParams layoutParams2 = BubblePop.this.layoutParamsDelete;
                        BubblePop bubblePop2 = BubblePop.this;
                        layoutParams2.topMargin = bubblePop2.mLocationSelect[1] + BubbleEditMimojiPresenter.this.topMove;
                        BubblePop bubblePop3 = BubblePop.this;
                        bubblePop3.mIvDeleteFisrt.setLayoutParams(bubblePop3.layoutParamsDelete);
                        BubbleEditMimojiPresenter.this.isAnimationingDele = true;
                    }
                });
                animatorSet.start();
                animatorSet2.start();
            }
        }

        public void processBubbleAni(int i, int i2, int i3) {
            this.mHashCode = i3;
            if (!this.hasAddView) {
                this.mRootView.addView(this.mIvEditFirst);
                this.mRootView.addView(this.mIvDeleteFisrt);
                this.hasAddView = true;
            }
            if (!BubbleEditMimojiPresenter.this.isAnimationingEdit && !BubbleEditMimojiPresenter.this.isAnimationingDele) {
                if (this.mLocationSelect[2] > 0) {
                    hideBubbleAni();
                    return;
                }
                BubbleEditMimojiPresenter.this.setmHashCodeBubble(this.mHashCode);
                showBubbleAni(i, i2);
            }
        }

        public void showBubbleAni(final int i, final int i2) {
            ObjectAnimator objectAnimator;
            double d2;
            this.processState = 103;
            int[] iArr = this.mLocationSelect;
            iArr[0] = i;
            iArr[1] = i2;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mIvEditFirst, View.SCALE_X, 0.0f, 1.0f);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mIvEditFirst, View.SCALE_Y, 0.0f, 1.0f);
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mIvEditFirst, View.ALPHA, 0.0f, 1.0f);
            ImageView imageView = this.mIvEditFirst;
            float[] fArr = new float[2];
            fArr[0] = 0.0f;
            fArr[1] = (float) (BubbleEditMimojiPresenter.this.mIsRTL ? BubbleEditMimojiPresenter.this.rightMove : BubbleEditMimojiPresenter.this.leftMove);
            ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(imageView, "translationX", fArr);
            ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(this.mIvEditFirst, "translationY", 0.0f, (float) BubbleEditMimojiPresenter.this.topMove);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3, ofFloat4, ofFloat5);
            animatorSet.setDuration(200L);
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.addListener(new AnimatorListenerAdapter() {
                /* class com.android.camera.fragment.mimoji.BubbleEditMimojiPresenter.BubblePop.AnonymousClass1 */

                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    BubblePop.this.mIvEditFirst.setVisibility(0);
                    BubbleEditMimojiPresenter.this.isAnimationingEdit = false;
                }

                public void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    BubblePop.this.layoutParamsEdit.topMargin = i2;
                    BubblePop.this.layoutParamsEdit.setMarginStart(i);
                    BubblePop bubblePop = BubblePop.this;
                    bubblePop.mIvEditFirst.setLayoutParams(bubblePop.layoutParamsEdit);
                    BubblePop.this.mIvEditFirst.setVisibility(0);
                    BubbleEditMimojiPresenter.this.isAnimationingEdit = true;
                }
            });
            AnimatorSet animatorSet2 = new AnimatorSet();
            ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(this.mIvDeleteFisrt, View.SCALE_X, 0.0f, 1.0f);
            ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(this.mIvDeleteFisrt, View.SCALE_Y, 0.0f, 1.0f);
            ObjectAnimator ofFloat8 = ObjectAnimator.ofFloat(this.mIvDeleteFisrt, View.ALPHA, 0.0f, 1.0f);
            ImageView imageView2 = this.mIvDeleteFisrt;
            float[] fArr2 = new float[2];
            fArr2[0] = 0.0f;
            if (BubbleEditMimojiPresenter.this.mIsRTL) {
                objectAnimator = ofFloat6;
                d2 = BubbleEditMimojiPresenter.this.leftMove;
            } else {
                objectAnimator = ofFloat6;
                d2 = BubbleEditMimojiPresenter.this.rightMove;
            }
            fArr2[1] = (float) d2;
            animatorSet2.playTogether(objectAnimator, ofFloat7, ofFloat8, ObjectAnimator.ofFloat(imageView2, "translationX", fArr2), ObjectAnimator.ofFloat(this.mIvDeleteFisrt, "translationY", 0.0f, (float) BubbleEditMimojiPresenter.this.topMove));
            animatorSet2.setDuration(200L);
            animatorSet2.addListener(new AnimatorListenerAdapter() {
                /* class com.android.camera.fragment.mimoji.BubbleEditMimojiPresenter.BubblePop.AnonymousClass2 */

                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    BubblePop bubblePop = BubblePop.this;
                    bubblePop.mLocationSelect[2] = 1;
                    bubblePop.mIvDeleteFisrt.setVisibility(0);
                    BubbleEditMimojiPresenter.this.isAnimationingDele = false;
                }

                public void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    BubblePop.this.layoutParamsDelete.topMargin = i2;
                    BubblePop.this.layoutParamsDelete.setMarginStart(i);
                    BubblePop bubblePop = BubblePop.this;
                    bubblePop.mIvDeleteFisrt.setLayoutParams(bubblePop.layoutParamsDelete);
                    BubblePop.this.mIvDeleteFisrt.setVisibility(0);
                    BubbleEditMimojiPresenter.this.isAnimationingDele = true;
                }
            });
            animatorSet.start();
            animatorSet2.start();
        }
    }

    BubbleEditMimojiPresenter(Context context, View.OnClickListener onClickListener, RelativeLayout relativeLayout) {
        this.mContext = context;
        this.mIsRTL = Util.isLayoutRTL(this.mContext);
        this.bubblePop1 = new BubblePop(this.mContext, onClickListener, relativeLayout);
    }

    public void processBubbleAni(int i, int i2, View view) {
        if (-2 != i || -2 != i2) {
            this.mTargetView = view;
            this.mTargetView.getHeight();
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.mimoji_edit_bubble_width);
            this.rightMove = ((double) dimensionPixelSize) * 0.75d;
            this.leftMove = -this.rightMove;
            this.topMove = -dimensionPixelSize;
            this.downMove = dimensionPixelSize / 2;
            Log.i(TAG, "calculate vector leftMove:" + this.leftMove + " rightMove:" + this.rightMove + "  topMove:" + this.topMove + "  downMove:" + this.downMove);
            BubblePop bubblePop = this.bubblePop1;
            bubblePop.processBubbleAni(i, i2, bubblePop.hashCode());
        } else if (this.bubblePop1.getProcessState() == 103) {
            BubblePop bubblePop2 = this.bubblePop1;
            bubblePop2.processBubbleAni(i, i2, bubblePop2.hashCode());
        }
    }

    public void setmHashCodeBubble(int i) {
        this.mHashCodeBubble = i;
    }
}
