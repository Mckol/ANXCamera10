package miuix.springback.trigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.animation.utils.VelocityMonitor;
import miuix.springback.R;
import miuix.springback.view.SpringBackLayout;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

public class DefaultTrigger {
    private static final float OFFSET_RESET_STATE = 0.25f;
    private static final float OFFSET_SIMPLE_VELOCITY_Y = 1000.0f;
    private static final String TAG = "DefaultTrigger";
    private static int mIndeterminateDistance;
    private static int mIndeterminateTop;
    private static int mSimpleEnter;
    private static int mSimpleTrigger;
    private static int mUpIndeterminateDistance;
    private final ActionComplete mActionComplete = new ActionComplete();
    private int mActionIndex = -1;
    private final ActionStart mActionStart = new ActionStart();
    private final ActionTriggered mActionTriggered = new ActionTriggered();
    private List<Action> mActions = new ArrayList();
    private IndeterminateAction.OnActionCompleteListener mCompleteListener = new IndeterminateAction.OnActionCompleteListener() {
        /* class miuix.springback.trigger.DefaultTrigger.AnonymousClass5 */

        @Override // miuix.springback.trigger.DefaultTrigger.IndeterminateAction.OnActionCompleteListener
        public void onActionComplete(IndeterminateAction indeterminateAction) {
            if (DefaultTrigger.this.mCurrentState == DefaultTrigger.this.mActionStart && DefaultTrigger.this.mCurrentAction == indeterminateAction) {
                if (DefaultTrigger.this.mLayout.getScrollY() != 0) {
                    DefaultTrigger defaultTrigger = DefaultTrigger.this;
                    defaultTrigger.transition(defaultTrigger.mActionComplete);
                    if (DefaultTrigger.this.mScrollState == 0) {
                        DefaultTrigger.this.mLayout.smoothScrollTo(0, 0);
                    }
                } else {
                    DefaultTrigger defaultTrigger2 = DefaultTrigger.this;
                    defaultTrigger2.transition(defaultTrigger2.mIdle);
                }
                DefaultTrigger.this.mLoadingIndicator.setVisibility(8);
                DefaultTrigger.this.mTrackingIndicatorLabel.setText(indeterminateAction.mTriggerTexts[3]);
            }
            HapticCompat.performHapticFeedback(DefaultTrigger.this.mLayout, HapticFeedbackConstants.MIUI_SCROLL_EDGE);
        }

        @Override // miuix.springback.trigger.DefaultTrigger.IndeterminateAction.OnActionCompleteListener
        public void onActionStart(IndeterminateAction indeterminateAction) {
            Action action;
            if (DefaultTrigger.this.mActions.size() > 0 && (action = (Action) DefaultTrigger.this.mActions.get(0)) == indeterminateAction && DefaultTrigger.this.mCurrentAction == null && DefaultTrigger.this.mCurrentState == DefaultTrigger.this.mIdle) {
                DefaultTrigger defaultTrigger = DefaultTrigger.this;
                defaultTrigger.transition(defaultTrigger.mTracking);
                DefaultTrigger.this.mCurrentAction = action;
                DefaultTrigger.this.mLoadingIndicator.setVisibility(0);
                DefaultTrigger.this.mTrackingIndicator.setVisibility(0);
                DefaultTrigger.this.mTrackingIndicatorLabel.setVisibility(0);
                DefaultTrigger.this.mTrackingIndicatorLabel.setText(indeterminateAction.mTriggerTexts[2]);
                DefaultTrigger.this.mLayout.smoothScrollTo(0, -DefaultTrigger.this.mCurrentAction.mTriggerPoint);
                DefaultTrigger.this.mContainer.layout(0, -DefaultTrigger.this.mCurrentAction.mTriggerPoint, DefaultTrigger.this.mContainer.getWidth(), 0);
                DefaultTrigger defaultTrigger2 = DefaultTrigger.this;
                defaultTrigger2.transition(defaultTrigger2.mWaitForIndeterminate);
            }
        }

        @Override // miuix.springback.trigger.DefaultTrigger.IndeterminateAction.OnActionCompleteListener
        public void onUpdateTriggerTextIndex(IndeterminateAction indeterminateAction, int i, String str) {
            indeterminateAction.mTriggerTexts[i] = str;
        }
    };
    private RelativeLayout mContainer;
    private Context mContext;
    private Action mCurrentAction;
    private TriggerState mCurrentState = this.mIdle;
    private final Idle mIdle = new Idle();
    private ViewGroup mIndicatorContainer;
    private int mLastScrollDistance;
    private SpringBackLayout mLayout;
    private View.OnLayoutChangeListener mLayoutChangeListener = new View.OnLayoutChangeListener() {
        /* class miuix.springback.trigger.DefaultTrigger.AnonymousClass3 */

        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.getWidth(), 1073741824);
            int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(-view.getScrollY(), 1073741824);
            DefaultTrigger.this.mContainer.measure(makeMeasureSpec, makeMeasureSpec2);
            DefaultTrigger.this.mUpContainer.measure(makeMeasureSpec, makeMeasureSpec2);
            DefaultTrigger.this.mContainer.layout(0, view.getScrollY(), view.getWidth(), 0);
            DefaultTrigger.this.mUpContainer.layout(0, DefaultTrigger.this.mLayout.getBottom(), view.getWidth(), DefaultTrigger.this.mLayout.getBottom() + view.getScrollY());
            for (int i9 = 0; i9 < DefaultTrigger.this.mActions.size(); i9++) {
                Action action = (Action) DefaultTrigger.this.mActions.get(i9);
                if (action instanceof IndeterminateAction) {
                    IndeterminateAction indeterminateAction = (IndeterminateAction) action;
                    if (DefaultTrigger.mIndeterminateTop >= DefaultTrigger.this.mTrackingIndicator.getTop()) {
                        DefaultTrigger.this.mLoadingIndicator.offsetTopAndBottom(indeterminateAction.mEnterPoint - 0);
                        DefaultTrigger.this.mTrackingIndicator.offsetTopAndBottom(indeterminateAction.mEnterPoint - 0);
                        DefaultTrigger.this.mTrackingIndicatorLabel.offsetTopAndBottom(indeterminateAction.mEnterPoint - 0);
                    }
                }
            }
            if (DefaultTrigger.this.mTrackingIndicator.getVisibility() == 0 && DefaultTrigger.this.mCurrentAction != null && (DefaultTrigger.this.mCurrentAction instanceof IndeterminateAction)) {
                if (DefaultTrigger.this.mTrackingIndicatorBottom <= 0) {
                    DefaultTrigger defaultTrigger = DefaultTrigger.this;
                    defaultTrigger.mTrackingIndicatorBottom = defaultTrigger.mTrackingIndicator.getBottom();
                }
                if (DefaultTrigger.this.mTrackingIndicatorLabelTop <= 0 || DefaultTrigger.this.mTrackingIndicatorLabelBottom <= 0) {
                    DefaultTrigger defaultTrigger2 = DefaultTrigger.this;
                    defaultTrigger2.mTrackingIndicatorLabelTop = defaultTrigger2.mTrackingIndicatorLabel.getTop();
                    DefaultTrigger defaultTrigger3 = DefaultTrigger.this;
                    defaultTrigger3.mTrackingIndicatorLabelBottom = defaultTrigger3.mTrackingIndicatorLabel.getBottom();
                }
                if ((DefaultTrigger.this.mLoadingIndicator.getVisibility() == 8 || DefaultTrigger.this.mLoadingIndicator.getVisibility() == 4) && DefaultTrigger.this.mCurrentState != DefaultTrigger.this.mActionComplete && DefaultTrigger.this.mContainer.getHeight() > DefaultTrigger.this.mCurrentAction.mTriggerPoint) {
                    DefaultTrigger.this.mTrackingIndicator.setBottom(DefaultTrigger.this.mTrackingIndicatorBottom + (DefaultTrigger.this.mContainer.getHeight() - DefaultTrigger.this.mCurrentAction.mTriggerPoint));
                }
            }
            if (DefaultTrigger.this.mIndicatorContainer.getVisibility() == 0) {
                DefaultTrigger.this.mIndicatorContainer.offsetTopAndBottom(DefaultTrigger.this.mScrollDistance - DefaultTrigger.this.mIndicatorContainer.getMeasuredHeight());
            }
        }
    };
    private LayoutInflater mLayoutInflater;
    private ProgressBar mLoadingIndicator;
    private SpringBackLayout.OnScrollListener mOnScrollListener = new SpringBackLayout.OnScrollListener() {
        /* class miuix.springback.trigger.DefaultTrigger.AnonymousClass1 */

        @Override // miuix.springback.view.SpringBackLayout.OnScrollListener
        public void onScrolled(SpringBackLayout springBackLayout, int i, int i2) {
            DefaultTrigger defaultTrigger = DefaultTrigger.this;
            defaultTrigger.mLastScrollDistance = defaultTrigger.mScrollDistance;
            DefaultTrigger.this.mScrollDistance = -springBackLayout.getScrollY();
            DefaultTrigger.this.mVelocityMonitor.update((float) DefaultTrigger.this.mScrollDistance);
            DefaultTrigger defaultTrigger2 = DefaultTrigger.this;
            defaultTrigger2.mVelocityY = defaultTrigger2.mVelocityMonitor.getVelocity(0);
            DefaultTrigger.this.mContainer.setTop(springBackLayout.getScrollY());
            DefaultTrigger.this.mUpContainer.setBottom(DefaultTrigger.this.mLayout.getBottom() + springBackLayout.getScrollY());
            if (DefaultTrigger.this.mScrollDistance < 0 && DefaultTrigger.this.mCurrentAction == DefaultTrigger.this.mUpAction && DefaultTrigger.this.mUpAction != null) {
                float f = (((float) (DefaultTrigger.this.mUpAction.mTriggerPoint - DefaultTrigger.this.mUpAction.mEnterPoint)) * DefaultTrigger.OFFSET_RESET_STATE) + ((float) DefaultTrigger.this.mCurrentAction.mEnterPoint);
                if (DefaultTrigger.this.mScrollState == 1 && ((((float) Math.abs(DefaultTrigger.this.mLastScrollDistance)) < f || ((float) Math.abs(DefaultTrigger.this.mScrollDistance)) < f) && DefaultTrigger.this.mCurrentState == DefaultTrigger.this.mActionComplete)) {
                    DefaultTrigger defaultTrigger3 = DefaultTrigger.this;
                    defaultTrigger3.transition(defaultTrigger3.mTracking);
                }
                DefaultTrigger.this.mUpTrackingContainer.setTranslationY((float) Math.max(DefaultTrigger.this.mUpContainer.getHeight() - DefaultTrigger.this.mUpAction.mTriggerPoint, 0));
            }
            if (DefaultTrigger.this.mCurrentAction != null && (DefaultTrigger.this.mCurrentAction instanceof IndeterminateAction)) {
                float f2 = (((float) (DefaultTrigger.this.mCurrentAction.mTriggerPoint - DefaultTrigger.this.mCurrentAction.mEnterPoint)) * DefaultTrigger.OFFSET_RESET_STATE) + ((float) DefaultTrigger.this.mCurrentAction.mEnterPoint);
                if (DefaultTrigger.this.mScrollState == 1 && ((((float) Math.abs(DefaultTrigger.this.mLastScrollDistance)) < f2 || ((float) Math.abs(DefaultTrigger.this.mScrollDistance)) < f2) && DefaultTrigger.this.mCurrentState == DefaultTrigger.this.mActionComplete)) {
                    DefaultTrigger defaultTrigger4 = DefaultTrigger.this;
                    defaultTrigger4.transition(defaultTrigger4.mTracking);
                    DefaultTrigger.this.mIndicatorContainer.setVisibility(0);
                    DefaultTrigger.this.mLoadingIndicator.setVisibility(8);
                    DefaultTrigger.this.mTrackingIndicator.setVisibility(0);
                    DefaultTrigger.this.mTrackingIndicatorLabel.setText(((IndeterminateAction) DefaultTrigger.this.mCurrentAction).mTriggerTexts[0]);
                }
                if (DefaultTrigger.this.mScrollState == 1 && DefaultTrigger.this.mCurrentState == DefaultTrigger.this.mWaitForIndeterminate && Math.abs(DefaultTrigger.this.mLastScrollDistance) > DefaultTrigger.this.mCurrentAction.mEnterPoint) {
                    DefaultTrigger defaultTrigger5 = DefaultTrigger.this;
                    defaultTrigger5.transition(defaultTrigger5.mTracking);
                }
                if (DefaultTrigger.this.mTrackingIndicator.getVisibility() == 0) {
                    DefaultTrigger defaultTrigger6 = DefaultTrigger.this;
                    defaultTrigger6.mTrackingIndicatorBottom = defaultTrigger6.mTrackingIndicator.getTop() + DefaultTrigger.this.mTrackingIndicator.getWidth();
                    DefaultTrigger defaultTrigger7 = DefaultTrigger.this;
                    defaultTrigger7.mTrackingIndicatorLabelTop = defaultTrigger7.mTrackingIndicatorLabel.getTop();
                    DefaultTrigger defaultTrigger8 = DefaultTrigger.this;
                    defaultTrigger8.mTrackingIndicatorLabelBottom = defaultTrigger8.mTrackingIndicatorLabel.getBottom();
                    float f3 = (float) DefaultTrigger.this.mCurrentAction.mTriggerPoint;
                    float max = Math.max(0.0f, Math.min(((float) DefaultTrigger.this.mContainer.getHeight()) / f3, 1.0f));
                    float f4 = 0.5f * f3;
                    float max2 = Math.max(0.0f, ((float) DefaultTrigger.this.mContainer.getHeight()) < f4 ? 0.0f : Math.min((((float) DefaultTrigger.this.mContainer.getHeight()) - f4) / f4, 1.0f));
                    float max3 = Math.max(0.0f, ((float) DefaultTrigger.this.mContainer.getHeight()) < f4 ? 0.0f : Math.min((((float) DefaultTrigger.this.mContainer.getHeight()) - (0.7f * f3)) / (f3 * 0.3f), 1.0f));
                    float f5 = ((float) (-DefaultTrigger.this.mTrackingIndicator.getWidth())) * (1.0f - max);
                    DefaultTrigger.this.mTrackingIndicator.setAlpha(max2);
                    DefaultTrigger.this.mTrackingIndicator.setScaleX(max);
                    DefaultTrigger.this.mTrackingIndicator.setScaleY(max);
                    DefaultTrigger.this.mTrackingIndicatorLabel.setAlpha(max3);
                    DefaultTrigger.this.mTrackingIndicatorLabel.setTop(DefaultTrigger.this.mTrackingIndicatorLabelTop);
                    DefaultTrigger.this.mTrackingIndicatorLabel.setBottom(DefaultTrigger.this.mTrackingIndicatorLabelBottom);
                    if (DefaultTrigger.this.mLoadingIndicator.getVisibility() == 0) {
                        DefaultTrigger.this.mLoadingIndicator.setAlpha(max2);
                        DefaultTrigger.this.mLoadingIndicator.setScaleX(max);
                        DefaultTrigger.this.mLoadingIndicator.setScaleY(max);
                    }
                    if (DefaultTrigger.this.mContainer.getHeight() < DefaultTrigger.this.mCurrentAction.mTriggerPoint) {
                        if (max3 > 0.0f) {
                            DefaultTrigger.this.mTrackingIndicatorLabel.setTranslationY(f5);
                        }
                        if (DefaultTrigger.this.mCurrentState == DefaultTrigger.this.mTracking) {
                            DefaultTrigger.this.mTrackingIndicatorLabel.setText(((IndeterminateAction) DefaultTrigger.this.mCurrentAction).mTriggerTexts[0]);
                        }
                        DefaultTrigger.this.mTrackingIndicator.setBottom(DefaultTrigger.this.mTrackingIndicatorBottom);
                    } else if (DefaultTrigger.this.mContainer.getHeight() >= DefaultTrigger.this.mCurrentAction.mTriggerPoint) {
                        int height = DefaultTrigger.this.mTrackingIndicatorBottom + (DefaultTrigger.this.mContainer.getHeight() - DefaultTrigger.this.mCurrentAction.mTriggerPoint);
                        if (DefaultTrigger.this.mLoadingIndicator.getVisibility() == 0 || DefaultTrigger.this.mCurrentState == DefaultTrigger.this.mActionComplete) {
                            DefaultTrigger.this.mTrackingIndicatorLabel.setTranslationY(0.0f);
                        } else {
                            DefaultTrigger.this.mTrackingIndicator.setBottom(height);
                            DefaultTrigger.this.mTrackingIndicatorLabel.setTranslationY((float) (DefaultTrigger.this.mContainer.getHeight() - DefaultTrigger.this.mCurrentAction.mTriggerPoint));
                        }
                        if (DefaultTrigger.this.mCurrentState == DefaultTrigger.this.mTracking) {
                            DefaultTrigger.this.mTrackingIndicatorLabel.setText(((IndeterminateAction) DefaultTrigger.this.mCurrentAction).mTriggerTexts[1]);
                        }
                    }
                }
            }
            DefaultTrigger.this.mCurrentState.handleScrolled(i2, springBackLayout.getScrollY());
            if (DefaultTrigger.this.mCurrentAction != null && (DefaultTrigger.this.mCurrentAction instanceof SimpleAction) && DefaultTrigger.this.mContainer.getHeight() < DefaultTrigger.this.mCurrentAction.mEnterPoint) {
                DefaultTrigger.this.mIndicatorContainer.setVisibility(8);
            } else if (DefaultTrigger.this.mCurrentAction != null && (DefaultTrigger.this.mCurrentAction instanceof SimpleAction) && DefaultTrigger.this.mContainer.getHeight() >= DefaultTrigger.this.mCurrentAction.mEnterPoint && DefaultTrigger.this.mIndicatorContainer.getVisibility() == 8) {
                DefaultTrigger.this.mIndicatorContainer.setVisibility(0);
                DefaultTrigger defaultTrigger9 = DefaultTrigger.this;
                defaultTrigger9.viewShow(defaultTrigger9.mIndicatorContainer, DefaultTrigger.this.mSimpleActionView);
            }
            if (DefaultTrigger.this.mIndicatorContainer.getVisibility() == 0 && DefaultTrigger.this.mCurrentAction != null) {
                DefaultTrigger.this.mIndicatorContainer.offsetTopAndBottom(-i2);
            }
        }

        @Override // miuix.springback.view.SpringBackLayout.OnScrollListener
        public void onStateChanged(int i, int i2, boolean z) {
            DefaultTrigger.this.mScrollState = i2;
            DefaultTrigger.this.mScrollerFinished = z;
            DefaultTrigger.this.mCurrentState.handleScrollStateChange(i, i2);
            if (DefaultTrigger.this.mCurrentState != DefaultTrigger.this.mIdle) {
                DefaultTrigger.this.mContainer.setVisibility(0);
                if (DefaultTrigger.this.mUpAction != null) {
                    DefaultTrigger.this.mUpTrackingContainer.setVisibility(0);
                }
            } else if (DefaultTrigger.this.mCurrentState != DefaultTrigger.this.mWaitForIndeterminate) {
                DefaultTrigger.this.mContainer.setVisibility(8);
                if (DefaultTrigger.this.mUpAction != null) {
                    DefaultTrigger.this.mUpTrackingContainer.setVisibility(8);
                }
            }
        }
    };
    private SpringBackLayout.OnSpringListener mOnSpringListener = new SpringBackLayout.OnSpringListener() {
        /* class miuix.springback.trigger.DefaultTrigger.AnonymousClass2 */

        @Override // miuix.springback.view.SpringBackLayout.OnSpringListener
        public boolean onSpringBack() {
            return DefaultTrigger.this.mCurrentState.handleSpringBack();
        }
    };
    private int mScrollDistance;
    private int mScrollState;
    private boolean mScrollerFinished = true;
    private View mSimpleActionView;
    private final Tracking mTracking = new Tracking();
    private View mTrackingIndicator;
    private int mTrackingIndicatorBottom = 0;
    private TextView mTrackingIndicatorLabel;
    private int mTrackingIndicatorLabelBottom = 0;
    private int mTrackingIndicatorLabelTop = 0;
    private Action mUpAction;
    private boolean mUpActionBegin = false;
    private IndeterminateUpAction.OnUpActionDataListener mUpActionDataListener = new IndeterminateUpAction.OnUpActionDataListener() {
        /* class miuix.springback.trigger.DefaultTrigger.AnonymousClass4 */

        @Override // miuix.springback.trigger.DefaultTrigger.IndeterminateUpAction.OnUpActionDataListener
        public void onActionComplete(IndeterminateUpAction indeterminateUpAction) {
            if (DefaultTrigger.this.mCurrentState != DefaultTrigger.this.mActionStart || DefaultTrigger.this.mCurrentAction != indeterminateUpAction) {
                return;
            }
            if (DefaultTrigger.this.mLayout.getScrollY() != 0) {
                DefaultTrigger defaultTrigger = DefaultTrigger.this;
                defaultTrigger.transition(defaultTrigger.mActionComplete);
                if (DefaultTrigger.this.mScrollState == 0) {
                    DefaultTrigger.this.mLayout.smoothScrollTo(0, 0);
                    return;
                }
                return;
            }
            DefaultTrigger defaultTrigger2 = DefaultTrigger.this;
            defaultTrigger2.transition(defaultTrigger2.mIdle);
        }

        @Override // miuix.springback.trigger.DefaultTrigger.IndeterminateUpAction.OnUpActionDataListener
        public void onActionLoadFail(IndeterminateUpAction indeterminateUpAction) {
            if (DefaultTrigger.this.mCurrentState == DefaultTrigger.this.mActionStart && DefaultTrigger.this.mCurrentAction == indeterminateUpAction) {
                DefaultTrigger.this.mUpLoadingIndicator.setVisibility(8);
                DefaultTrigger.this.mUpTrackingIndicator.setVisibility(8);
                DefaultTrigger.this.mUpTrackingIndicatorLabel.setText(indeterminateUpAction.mTriggerTexts[1]);
                if (DefaultTrigger.this.mLayout.getScrollY() != 0) {
                    DefaultTrigger defaultTrigger = DefaultTrigger.this;
                    defaultTrigger.transition(defaultTrigger.mActionComplete);
                    if (DefaultTrigger.this.mScrollState == 0) {
                        DefaultTrigger.this.mLayout.smoothScrollTo(0, 0);
                        return;
                    }
                    return;
                }
                DefaultTrigger defaultTrigger2 = DefaultTrigger.this;
                defaultTrigger2.transition(defaultTrigger2.mIdle);
            }
        }

        @Override // miuix.springback.trigger.DefaultTrigger.IndeterminateUpAction.OnUpActionDataListener
        public void onActionNoData(IndeterminateUpAction indeterminateUpAction, int i) {
            if (DefaultTrigger.this.mCurrentState == DefaultTrigger.this.mActionStart && DefaultTrigger.this.mCurrentAction == indeterminateUpAction) {
                if (i < 3) {
                    DefaultTrigger.this.mUpTrackingIndicatorLabel.setText(indeterminateUpAction.mTriggerTexts[2]);
                } else {
                    DefaultTrigger.this.mUpTrackingIndicatorLabel.setText(indeterminateUpAction.mTriggerTexts[3]);
                }
                DefaultTrigger.this.mUpLoadingIndicator.setVisibility(8);
                DefaultTrigger.this.mUpTrackingIndicator.setVisibility(8);
                if (DefaultTrigger.this.mLayout.getScrollY() != 0) {
                    DefaultTrigger defaultTrigger = DefaultTrigger.this;
                    defaultTrigger.transition(defaultTrigger.mActionComplete);
                    if (DefaultTrigger.this.mScrollState == 0) {
                        DefaultTrigger.this.mLayout.smoothScrollTo(0, 0);
                        return;
                    }
                    return;
                }
                DefaultTrigger defaultTrigger2 = DefaultTrigger.this;
                defaultTrigger2.transition(defaultTrigger2.mIdle);
            }
        }

        @Override // miuix.springback.trigger.DefaultTrigger.IndeterminateUpAction.OnUpActionDataListener
        public void onActionStart(IndeterminateUpAction indeterminateUpAction) {
            if (DefaultTrigger.this.mUpAction != null && DefaultTrigger.this.mUpAction == indeterminateUpAction) {
                DefaultTrigger defaultTrigger = DefaultTrigger.this;
                defaultTrigger.transition(defaultTrigger.mTracking);
                DefaultTrigger defaultTrigger2 = DefaultTrigger.this;
                defaultTrigger2.mCurrentAction = defaultTrigger2.mUpAction;
                DefaultTrigger.this.mUpContainer.setVisibility(0);
                DefaultTrigger.this.mUpTrackingContainer.setVisibility(0);
                DefaultTrigger.this.mUpLoadingIndicator.setVisibility(0);
                DefaultTrigger.this.mUpTrackingIndicatorLabel.setVisibility(0);
                DefaultTrigger.this.mUpTrackingIndicatorLabel.setText(indeterminateUpAction.mTriggerTexts[0]);
                DefaultTrigger.this.mLayout.smoothScrollTo(0, DefaultTrigger.this.mCurrentAction.mTriggerPoint);
                DefaultTrigger.this.mUpContainer.layout(0, DefaultTrigger.this.mLayout.getBottom(), DefaultTrigger.this.mLayout.getWidth(), DefaultTrigger.this.mLayout.getBottom() - DefaultTrigger.this.mCurrentAction.mTriggerPoint);
                DefaultTrigger defaultTrigger3 = DefaultTrigger.this;
                defaultTrigger3.transition(defaultTrigger3.mWaitForIndeterminate);
            }
        }

        @Override // miuix.springback.trigger.DefaultTrigger.IndeterminateUpAction.OnUpActionDataListener
        public void onUpdateTriggerTextIndex(IndeterminateUpAction indeterminateUpAction, int i, String str) {
            indeterminateUpAction.mTriggerTexts[i] = str;
        }
    };
    private RelativeLayout mUpContainer;
    private ProgressBar mUpLoadingIndicator;
    private RelativeLayout mUpTrackingContainer;
    private View mUpTrackingIndicator;
    private TextView mUpTrackingIndicatorLabel;
    private VelocityMonitor mVelocityMonitor;
    private float mVelocityY = 0.0f;
    private final WaitForIndeterminate mWaitForIndeterminate = new WaitForIndeterminate();

    public static abstract class Action {
        static final Comparator<Action> DISTANCE_COMPARATOR = new Comparator<Action>() {
            /* class miuix.springback.trigger.DefaultTrigger.Action.AnonymousClass1 */

            public int compare(Action action, Action action2) {
                return Integer.compare(action.mEnterPoint, action2.mEnterPoint);
            }
        };
        int mEnterPoint;
        int mTriggerPoint;

        Action(int i, int i2) {
            this.mEnterPoint = i;
            this.mTriggerPoint = i2;
        }

        /* access modifiers changed from: package-private */
        public void notifyActivated() {
            onActivated();
        }

        /* access modifiers changed from: package-private */
        public void notifyEntered() {
            onEntered();
        }

        /* access modifiers changed from: package-private */
        public void notifyExit() {
            onExit();
        }

        /* access modifiers changed from: package-private */
        public void notifyTriggered() {
            onTriggered();
        }

        /* access modifiers changed from: protected */
        public abstract void onActivated();

        /* access modifiers changed from: protected */
        public abstract void onEntered();

        /* access modifiers changed from: protected */
        public abstract void onExit();

        /* access modifiers changed from: protected */
        public abstract void onFinished();

        /* access modifiers changed from: protected */
        public abstract void onTriggered();
    }

    /* access modifiers changed from: private */
    public class ActionComplete extends TriggerState {
        private ActionComplete() {
            super();
        }

        /* access modifiers changed from: package-private */
        @Override // miuix.springback.trigger.DefaultTrigger.TriggerState
        public void handleScrollStateChange(int i, int i2) {
            super.handleScrollStateChange(i, i2);
            if (i2 == 0) {
                DefaultTrigger defaultTrigger = DefaultTrigger.this;
                defaultTrigger.transition(defaultTrigger.mIdle);
            }
        }
    }

    /* access modifiers changed from: private */
    public class ActionStart extends TriggerState {
        private ActionStart() {
            super();
        }

        /* access modifiers changed from: package-private */
        @Override // miuix.springback.trigger.DefaultTrigger.TriggerState
        public void handleScrollStateChange(int i, int i2) {
            super.handleScrollStateChange(i, i2);
        }

        /* access modifiers changed from: package-private */
        @Override // miuix.springback.trigger.DefaultTrigger.TriggerState
        public void handleScrolled(int i, int i2) {
            super.handleScrolled(i, i2);
        }

        /* access modifiers changed from: package-private */
        @Override // miuix.springback.trigger.DefaultTrigger.TriggerState
        public boolean handleSpringBack() {
            return super.handleSpringBack();
        }
    }

    /* access modifiers changed from: private */
    public class ActionTriggered extends TriggerState {
        private ActionTriggered() {
            super();
        }

        /* access modifiers changed from: package-private */
        @Override // miuix.springback.trigger.DefaultTrigger.TriggerState
        public void handleScrollStateChange(int i, int i2) {
            if (i2 == 0) {
                DefaultTrigger defaultTrigger = DefaultTrigger.this;
                defaultTrigger.transition(defaultTrigger.mIdle);
            }
        }

        /* access modifiers changed from: package-private */
        @Override // miuix.springback.trigger.DefaultTrigger.TriggerState
        public void handleScrolled(int i, int i2) {
            if (DefaultTrigger.this.mCurrentAction != null && (DefaultTrigger.this.mCurrentAction instanceof SimpleAction) && DefaultTrigger.this.mScrollDistance < DefaultTrigger.this.mCurrentAction.mEnterPoint && DefaultTrigger.this.mScrollState == 1) {
                DefaultTrigger.this.mActionIndex = -1;
                DefaultTrigger defaultTrigger = DefaultTrigger.this;
                defaultTrigger.transition(defaultTrigger.mTracking);
            }
        }
    }

    /* access modifiers changed from: private */
    public class Idle extends TriggerState {
        private Idle() {
            super();
        }

        @Override // miuix.springback.trigger.DefaultTrigger.TriggerState
        public void handleScrollStateChange(int i, int i2) {
            if (i != 0) {
                return;
            }
            if (i2 == 1 || i2 == 2) {
                DefaultTrigger defaultTrigger = DefaultTrigger.this;
                defaultTrigger.transition(defaultTrigger.mTracking);
            }
        }
    }

    public static abstract class IndeterminateAction extends Action {
        public static final int DEFAULT_OFFSET_POINT = 0;
        static final int[] DEFAULT_TRIGGER_TEXTIDS = {R.string.miuix_sbl_tracking_progress_labe_pull_to_refresh, R.string.miuix_sbl_tracking_progress_labe_release_to_refresh, R.string.miuix_sbl_tracking_progress_labe_refreshing, R.string.miuix_sbl_tracking_progress_labe_refreshed};
        private OnActionCompleteListener mCompleteListener;
        private int[] mTriggerTextIDs;
        private String[] mTriggerTexts;

        /* access modifiers changed from: package-private */
        public interface OnActionCompleteListener {
            void onActionComplete(IndeterminateAction indeterminateAction);

            void onActionStart(IndeterminateAction indeterminateAction);

            void onUpdateTriggerTextIndex(IndeterminateAction indeterminateAction, int i, String str);
        }

        public IndeterminateAction(int i) {
            super(i, DefaultTrigger.mIndeterminateDistance + i);
            int[] iArr = DEFAULT_TRIGGER_TEXTIDS;
            this.mTriggerTexts = new String[iArr.length];
            if (i >= 0) {
                this.mTriggerTextIDs = iArr;
                return;
            }
            throw new IllegalArgumentException("invalid offsetPoint");
        }

        public IndeterminateAction(int i, int[] iArr) {
            super(i, DefaultTrigger.mIndeterminateDistance + i);
            int[] iArr2 = DEFAULT_TRIGGER_TEXTIDS;
            this.mTriggerTexts = new String[iArr2.length];
            if (i < 0) {
                throw new IllegalArgumentException("invalid offsetPoint");
            } else if (iArr == null || iArr.length != iArr2.length) {
                throw new IllegalArgumentException("invalid text Ids");
            } else {
                this.mTriggerTextIDs = iArr;
            }
        }

        public void notifyLoadComplete() {
            OnActionCompleteListener onActionCompleteListener = this.mCompleteListener;
            if (onActionCompleteListener != null) {
                onActionCompleteListener.onActionComplete(this);
            }
        }

        public void notifyTriggerTextIndex(int i, String str) {
            if (i < DEFAULT_TRIGGER_TEXTIDS.length) {
                OnActionCompleteListener onActionCompleteListener = this.mCompleteListener;
                if (onActionCompleteListener != null) {
                    onActionCompleteListener.onUpdateTriggerTextIndex(this, i, str);
                    return;
                }
                return;
            }
            throw new IllegalArgumentException("invalid index");
        }

        public void startIndeterminateAction() {
            OnActionCompleteListener onActionCompleteListener = this.mCompleteListener;
            if (onActionCompleteListener != null) {
                onActionCompleteListener.onActionStart(this);
            }
        }
    }

    public static abstract class IndeterminateUpAction extends Action {
        public static final int DEFAULT_OFFSET_POINT = 0;
        static final int[] DEFAULT_TRIGGER_TEXTIDS = {R.string.miuix_sbl_tracking_progress_labe_up_refresh, R.string.miuix_sbl_tracking_progress_labe_up_refresh_fail, R.string.miuix_sbl_tracking_progress_labe_up_nodata, R.string.miuix_sbl_tracking_progress_labe_up_none};
        private int mCountNoData = 0;
        private int[] mTriggerTextIDs;
        private String[] mTriggerTexts;
        private OnUpActionDataListener mUpDataListener;

        /* access modifiers changed from: package-private */
        public interface OnUpActionDataListener {
            void onActionComplete(IndeterminateUpAction indeterminateUpAction);

            void onActionLoadFail(IndeterminateUpAction indeterminateUpAction);

            void onActionNoData(IndeterminateUpAction indeterminateUpAction, int i);

            void onActionStart(IndeterminateUpAction indeterminateUpAction);

            void onUpdateTriggerTextIndex(IndeterminateUpAction indeterminateUpAction, int i, String str);
        }

        public IndeterminateUpAction(int i) {
            super(i, DefaultTrigger.mUpIndeterminateDistance + i);
            int[] iArr = DEFAULT_TRIGGER_TEXTIDS;
            this.mTriggerTexts = new String[iArr.length];
            if (i >= 0) {
                this.mTriggerTextIDs = iArr;
                return;
            }
            throw new IllegalArgumentException("invalid offsetPoint");
        }

        public IndeterminateUpAction(int i, int[] iArr) {
            super(i, DefaultTrigger.mUpIndeterminateDistance + i);
            int[] iArr2 = DEFAULT_TRIGGER_TEXTIDS;
            this.mTriggerTexts = new String[iArr2.length];
            if (i < 0) {
                throw new IllegalArgumentException("invalid offsetPoint");
            } else if (iArr == null || iArr.length != iArr2.length) {
                throw new IllegalArgumentException("invalid text Ids");
            } else {
                this.mTriggerTextIDs = iArr;
            }
        }

        public int getCountNoData() {
            return this.mCountNoData;
        }

        public boolean isNoData() {
            return this.mCountNoData > 0;
        }

        public void notifyActionNoData() {
            OnUpActionDataListener onUpActionDataListener = this.mUpDataListener;
            if (onUpActionDataListener != null) {
                this.mCountNoData++;
                onUpActionDataListener.onActionNoData(this, this.mCountNoData);
            }
        }

        public void notifyLoadComplete() {
            OnUpActionDataListener onUpActionDataListener = this.mUpDataListener;
            if (onUpActionDataListener != null) {
                this.mCountNoData = 0;
                onUpActionDataListener.onActionComplete(this);
            }
        }

        public void notifyLoadFail() {
            OnUpActionDataListener onUpActionDataListener = this.mUpDataListener;
            if (onUpActionDataListener != null) {
                onUpActionDataListener.onActionLoadFail(this);
            }
        }

        public void notifyTriggerTextIndex(int i, String str) {
            if (i < DEFAULT_TRIGGER_TEXTIDS.length) {
                OnUpActionDataListener onUpActionDataListener = this.mUpDataListener;
                if (onUpActionDataListener != null) {
                    onUpActionDataListener.onUpdateTriggerTextIndex(this, i, str);
                    return;
                }
                return;
            }
            throw new IllegalArgumentException("invalid index");
        }

        public void startIndeterminateUpAction() {
            OnUpActionDataListener onUpActionDataListener = this.mUpDataListener;
            if (onUpActionDataListener != null) {
                this.mCountNoData = 0;
                onUpActionDataListener.onActionStart(this);
            }
        }
    }

    public static abstract class SimpleAction extends Action {
        public SimpleAction() {
            super(DefaultTrigger.mSimpleEnter, DefaultTrigger.mSimpleTrigger);
        }

        /* access modifiers changed from: protected */
        public View onCreateIndicator(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public class Tracking extends TriggerState {
        private boolean mLockActivated;
        private boolean mTriggerable;
        private boolean mUpTriggerable;

        private Tracking() {
            super();
            this.mTriggerable = false;
            this.mUpTriggerable = false;
            this.mLockActivated = false;
        }

        /* access modifiers changed from: package-private */
        @Override // miuix.springback.trigger.DefaultTrigger.TriggerState
        public void handleScrollStateChange(int i, int i2) {
            if (i2 == 0) {
                DefaultTrigger defaultTrigger = DefaultTrigger.this;
                defaultTrigger.transition(defaultTrigger.mIdle);
                if (!this.mTriggerable) {
                    DefaultTrigger.this.mIndicatorContainer.removeAllViews();
                    DefaultTrigger.this.mSimpleActionView = null;
                    DefaultTrigger.this.mIndicatorContainer.setVisibility(8);
                    DefaultTrigger.this.mTrackingIndicator.setVisibility(8);
                    DefaultTrigger.this.mTrackingIndicatorLabel.setVisibility(8);
                }
                this.mUpTriggerable = false;
                this.mLockActivated = false;
            }
        }

        /* access modifiers changed from: package-private */
        @Override // miuix.springback.trigger.DefaultTrigger.TriggerState
        public void handleScrolled(int i, int i2) {
            if (DefaultTrigger.this.mScrollState != 1 && DefaultTrigger.this.mScrollState != 2) {
                return;
            }
            if (DefaultTrigger.this.mScrollDistance < 0) {
                if (!DefaultTrigger.this.mUpActionBegin) {
                    this.mUpTriggerable = false;
                }
                boolean z = this.mUpTriggerable;
                if (DefaultTrigger.this.mUpAction != null) {
                    DefaultTrigger defaultTrigger = DefaultTrigger.this;
                    defaultTrigger.mCurrentAction = defaultTrigger.mUpAction;
                    if (Math.abs(DefaultTrigger.this.mScrollDistance) > DefaultTrigger.this.mUpAction.mEnterPoint && !DefaultTrigger.this.mUpActionBegin) {
                        DefaultTrigger.this.mUpActionBegin = true;
                        this.mUpTriggerable = true;
                        DefaultTrigger.this.mUpContainer.setVisibility(0);
                        DefaultTrigger.this.mUpTrackingContainer.setVisibility(0);
                        if (((IndeterminateUpAction) DefaultTrigger.this.mUpAction).isNoData()) {
                            DefaultTrigger.this.mUpTrackingIndicator.setVisibility(8);
                            DefaultTrigger.this.mUpLoadingIndicator.setVisibility(8);
                            if (((IndeterminateUpAction) DefaultTrigger.this.mUpAction).getCountNoData() < 3) {
                                DefaultTrigger.this.mUpTrackingIndicatorLabel.setText(((IndeterminateUpAction) DefaultTrigger.this.mUpAction).mTriggerTexts[2]);
                            } else {
                                DefaultTrigger.this.mUpTrackingIndicatorLabel.setText(((IndeterminateUpAction) DefaultTrigger.this.mUpAction).mTriggerTexts[3]);
                            }
                        } else {
                            DefaultTrigger.this.mUpTrackingIndicator.setVisibility(0);
                            DefaultTrigger.this.mUpLoadingIndicator.setVisibility(0);
                            DefaultTrigger.this.mUpTrackingIndicatorLabel.setVisibility(0);
                            DefaultTrigger.this.mUpTrackingIndicatorLabel.setText(((IndeterminateUpAction) DefaultTrigger.this.mUpAction).mTriggerTexts[0]);
                        }
                        DefaultTrigger.this.mUpAction.onEntered();
                    }
                    boolean z2 = this.mUpTriggerable;
                    if (z != z2 && z2) {
                        DefaultTrigger.this.mUpAction.notifyActivated();
                        if (DefaultTrigger.this.mScrollState == 2) {
                            DefaultTrigger.this.mLayout.smoothScrollTo(0, DefaultTrigger.this.mUpAction.mTriggerPoint);
                            DefaultTrigger defaultTrigger2 = DefaultTrigger.this;
                            defaultTrigger2.transition(defaultTrigger2.mWaitForIndeterminate);
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            this.mUpTriggerable = false;
            int i3 = DefaultTrigger.this.mActionIndex;
            boolean z3 = this.mTriggerable;
            Action action = DefaultTrigger.this.mCurrentAction;
            int i4 = 0;
            while (i4 < DefaultTrigger.this.mActions.size() && DefaultTrigger.this.mScrollDistance > ((Action) DefaultTrigger.this.mActions.get(i4)).mEnterPoint) {
                DefaultTrigger.this.mActionIndex = i4;
                i4++;
            }
            if (DefaultTrigger.this.mActionIndex >= 0) {
                Action action2 = (Action) DefaultTrigger.this.mActions.get(DefaultTrigger.this.mActionIndex);
                boolean z4 = action2 != null && (action2 instanceof SimpleAction);
                if ((!z4 || DefaultTrigger.this.mVelocityY >= DefaultTrigger.OFFSET_SIMPLE_VELOCITY_Y || DefaultTrigger.this.mScrollState != 1) && z4) {
                    DefaultTrigger.this.mActionIndex = i3;
                } else {
                    DefaultTrigger.this.mCurrentAction = action2;
                    this.mTriggerable = DefaultTrigger.this.mScrollDistance >= DefaultTrigger.this.mCurrentAction.mTriggerPoint;
                }
            } else {
                DefaultTrigger.this.mCurrentAction = null;
                this.mTriggerable = false;
            }
            if (i3 != DefaultTrigger.this.mActionIndex) {
                if (action != null) {
                    action.onExit();
                    DefaultTrigger.this.mIndicatorContainer.removeAllViews();
                    DefaultTrigger.this.mSimpleActionView = null;
                }
                if (DefaultTrigger.this.mCurrentAction != null) {
                    if (DefaultTrigger.this.mCurrentAction instanceof SimpleAction) {
                        SimpleAction simpleAction = (SimpleAction) DefaultTrigger.this.mCurrentAction;
                        if (DefaultTrigger.this.mIndicatorContainer.getChildCount() == 0) {
                            DefaultTrigger defaultTrigger3 = DefaultTrigger.this;
                            defaultTrigger3.mSimpleActionView = simpleAction.onCreateIndicator(defaultTrigger3.mLayoutInflater, DefaultTrigger.this.mIndicatorContainer);
                        }
                        DefaultTrigger defaultTrigger4 = DefaultTrigger.this;
                        defaultTrigger4.viewShow(defaultTrigger4.mIndicatorContainer, DefaultTrigger.this.mSimpleActionView);
                        DefaultTrigger.this.mTrackingIndicator.setVisibility(8);
                        DefaultTrigger.this.mTrackingIndicatorLabel.setVisibility(8);
                    } else {
                        DefaultTrigger.this.mTrackingIndicator.setVisibility(0);
                        DefaultTrigger.this.mTrackingIndicatorLabel.setVisibility(0);
                        DefaultTrigger.this.mIndicatorContainer.removeAllViews();
                        DefaultTrigger.this.mSimpleActionView = null;
                        DefaultTrigger.this.mIndicatorContainer.setVisibility(8);
                    }
                    DefaultTrigger.this.mCurrentAction.onEntered();
                    this.mLockActivated = false;
                    if (this.mTriggerable) {
                        if (DefaultTrigger.this.mCurrentAction instanceof SimpleAction) {
                            this.mLockActivated = true;
                        }
                        DefaultTrigger.this.mCurrentAction.onActivated();
                        HapticCompat.performHapticFeedback(DefaultTrigger.this.mLayout, HapticFeedbackConstants.MIUI_SWITCH);
                        return;
                    }
                    return;
                }
                DefaultTrigger.this.mIndicatorContainer.setVisibility(8);
            } else if (action != null && z3 != this.mTriggerable) {
                if (z3) {
                    action.onEntered();
                    this.mLockActivated = false;
                    return;
                }
                if (DefaultTrigger.this.mCurrentAction instanceof SimpleAction) {
                    this.mLockActivated = true;
                }
                HapticCompat.performHapticFeedback(DefaultTrigger.this.mLayout, HapticFeedbackConstants.MIUI_MESH_NORMAL);
                action.onActivated();
                if (DefaultTrigger.this.mCurrentAction instanceof IndeterminateAction) {
                    DefaultTrigger.this.mLoadingIndicator.setVisibility(4);
                }
            }
        }

        /* access modifiers changed from: package-private */
        @Override // miuix.springback.trigger.DefaultTrigger.TriggerState
        public boolean handleSpringBack() {
            if ((!this.mTriggerable || DefaultTrigger.this.mCurrentAction == null) && DefaultTrigger.this.mCurrentAction != null && (DefaultTrigger.this.mCurrentAction instanceof SimpleAction)) {
                DefaultTrigger.this.mIndicatorContainer.removeAllViews();
                DefaultTrigger.this.mSimpleActionView = null;
                DefaultTrigger.this.mIndicatorContainer.setVisibility(8);
            }
            if (DefaultTrigger.this.mCurrentAction == null) {
                return false;
            }
            if ((DefaultTrigger.this.mCurrentAction instanceof IndeterminateAction) && DefaultTrigger.this.mTrackingIndicator.getVisibility() != 8) {
                if (this.mTriggerable) {
                    DefaultTrigger.this.mLayout.smoothScrollTo(0, -DefaultTrigger.this.mCurrentAction.mTriggerPoint);
                    DefaultTrigger defaultTrigger = DefaultTrigger.this;
                    defaultTrigger.transition(defaultTrigger.mWaitForIndeterminate);
                } else {
                    if (Math.abs(DefaultTrigger.this.mLayout.getScaleY()) < ((float) Math.abs(DefaultTrigger.this.mCurrentAction.mTriggerPoint))) {
                        DefaultTrigger.this.mCurrentAction.onExit();
                    }
                    DefaultTrigger.this.mLayout.smoothScrollTo(0, 0);
                }
                return true;
            } else if (!(DefaultTrigger.this.mCurrentAction instanceof IndeterminateUpAction) || DefaultTrigger.this.mUpTrackingContainer.getVisibility() == 8) {
                DefaultTrigger defaultTrigger2 = DefaultTrigger.this;
                defaultTrigger2.transition(defaultTrigger2.mActionTriggered);
                if (this.mLockActivated) {
                    DefaultTrigger.this.mCurrentAction.notifyTriggered();
                } else {
                    DefaultTrigger.this.mCurrentAction.onExit();
                }
                DefaultTrigger.this.mIndicatorContainer.removeAllViews();
                DefaultTrigger.this.mSimpleActionView = null;
                DefaultTrigger.this.mIndicatorContainer.setVisibility(8);
                return false;
            } else {
                DefaultTrigger.this.mLayout.smoothScrollTo(0, DefaultTrigger.this.mCurrentAction.mTriggerPoint);
                DefaultTrigger defaultTrigger3 = DefaultTrigger.this;
                defaultTrigger3.transition(defaultTrigger3.mWaitForIndeterminate);
                return true;
            }
        }
    }

    /* access modifiers changed from: private */
    public abstract class TriggerState {
        private TriggerState() {
        }

        /* access modifiers changed from: package-private */
        public void handleScrollStateChange(int i, int i2) {
        }

        /* access modifiers changed from: package-private */
        public void handleScrolled(int i, int i2) {
        }

        /* access modifiers changed from: package-private */
        public boolean handleSpringBack() {
            return false;
        }
    }

    /* access modifiers changed from: private */
    public class WaitForIndeterminate extends TriggerState {
        private WaitForIndeterminate() {
            super();
        }

        /* access modifiers changed from: package-private */
        @Override // miuix.springback.trigger.DefaultTrigger.TriggerState
        public void handleScrollStateChange(int i, int i2) {
            if (i2 == 0) {
                DefaultTrigger defaultTrigger = DefaultTrigger.this;
                defaultTrigger.transition(defaultTrigger.mActionStart);
                if (DefaultTrigger.this.mCurrentAction != null && (DefaultTrigger.this.mCurrentAction instanceof IndeterminateAction)) {
                    DefaultTrigger.this.mCurrentAction.notifyTriggered();
                    DefaultTrigger.this.mLoadingIndicator.setVisibility(0);
                    DefaultTrigger.this.mTrackingIndicator.setVisibility(0);
                    DefaultTrigger.this.mTrackingIndicatorLabel.setVisibility(0);
                    DefaultTrigger.this.mTrackingIndicatorLabel.setText(((IndeterminateAction) DefaultTrigger.this.mCurrentAction).mTriggerTexts[2]);
                    if (DefaultTrigger.this.mLoadingIndicator.getVisibility() == 0) {
                        DefaultTrigger.this.mLoadingIndicator.setAlpha(1.0f);
                        DefaultTrigger.this.mLoadingIndicator.setScaleX(1.0f);
                        DefaultTrigger.this.mLoadingIndicator.setScaleY(1.0f);
                    }
                } else if (DefaultTrigger.this.mUpAction != null && (DefaultTrigger.this.mCurrentAction instanceof IndeterminateUpAction)) {
                    DefaultTrigger.this.mUpAction.notifyTriggered();
                    if (((IndeterminateUpAction) DefaultTrigger.this.mUpAction).isNoData()) {
                        DefaultTrigger.this.mUpTrackingIndicator.setVisibility(8);
                        DefaultTrigger.this.mUpLoadingIndicator.setVisibility(8);
                        if (((IndeterminateUpAction) DefaultTrigger.this.mUpAction).getCountNoData() < 3) {
                            DefaultTrigger.this.mUpTrackingIndicatorLabel.setText(((IndeterminateUpAction) DefaultTrigger.this.mUpAction).mTriggerTexts[2]);
                        } else {
                            DefaultTrigger.this.mUpTrackingIndicatorLabel.setText(((IndeterminateUpAction) DefaultTrigger.this.mUpAction).mTriggerTexts[3]);
                        }
                    } else {
                        DefaultTrigger.this.mUpTrackingIndicator.setVisibility(0);
                        DefaultTrigger.this.mUpLoadingIndicator.setVisibility(0);
                        DefaultTrigger.this.mUpTrackingIndicatorLabel.setText(((IndeterminateUpAction) DefaultTrigger.this.mUpAction).mTriggerTexts[0]);
                    }
                }
            }
        }
    }

    public DefaultTrigger(Context context) {
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mVelocityMonitor = new VelocityMonitor();
        this.mContainer = (RelativeLayout) this.mLayoutInflater.inflate(R.layout.miuix_sbl_trigger_layout, (ViewGroup) null);
        this.mUpContainer = (RelativeLayout) this.mLayoutInflater.inflate(R.layout.miuix_sbl_trigger_up_layout, (ViewGroup) null);
        this.mIndicatorContainer = (ViewGroup) this.mContainer.findViewById(R.id.indicator_container);
        this.mTrackingIndicator = this.mContainer.findViewById(R.id.tracking_progress);
        this.mTrackingIndicatorLabel = (TextView) this.mContainer.findViewById(R.id.tracking_progress_label);
        this.mLoadingIndicator = (ProgressBar) this.mContainer.findViewById(R.id.loading_progress);
        this.mUpTrackingContainer = (RelativeLayout) this.mUpContainer.findViewById(R.id.tracking_progress_up_container);
        this.mUpTrackingIndicator = this.mUpContainer.findViewById(R.id.tracking_progress_up);
        this.mUpTrackingIndicatorLabel = (TextView) this.mUpContainer.findViewById(R.id.tracking_progress_up_label);
        this.mUpLoadingIndicator = (ProgressBar) this.mUpContainer.findViewById(R.id.loading_progress_up);
        mIndeterminateDistance = context.getResources().getDimensionPixelSize(R.dimen.miuix_sbl_action_indeterminate_distance);
        mUpIndeterminateDistance = context.getResources().getDimensionPixelSize(R.dimen.miuix_sbl_action_upindeterminate_distance);
        mSimpleEnter = context.getResources().getDimensionPixelSize(R.dimen.miuix_sbl_action_simple_enter);
        mSimpleTrigger = mSimpleEnter;
        mIndeterminateTop = context.getResources().getDimensionPixelSize(R.dimen.miuix_sbl_tracking_progress_bg_margintop);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void transition(TriggerState triggerState) {
        Action action;
        this.mCurrentState = triggerState;
        if (triggerState == this.mIdle) {
            if (this.mScrollerFinished && (action = this.mCurrentAction) != null) {
                action.onFinished();
            }
            this.mCurrentAction = null;
            this.mActionIndex = -1;
            this.mVelocityMonitor.clear();
            this.mUpActionBegin = false;
        }
    }

    private void updateTextIdToString(Context context, int[] iArr, String[] strArr) {
        if (iArr != null) {
            for (int i = 0; i < iArr.length; i++) {
                strArr[i] = context.getResources().getString(iArr[i]);
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void viewShow(ViewGroup viewGroup, View view) {
        if (view != null) {
            if (viewGroup.getChildCount() == 0) {
                viewGroup.addView(view);
            }
            viewGroup.setVisibility(0);
            AnimState add = new AnimState("start").add(ViewProperty.ALPHA, 0.0f, new long[0]).add(ViewProperty.TRANSLATION_Y, -180, new long[0]);
            AnimState add2 = new AnimState("show").add(ViewProperty.ALPHA, 1.0f, new long[0]).add(ViewProperty.TRANSLATION_Y, 25.0f, new long[0]);
            AnimState add3 = new AnimState("hide").add(ViewProperty.ALPHA, 1.0f, new long[0]).add(ViewProperty.TRANSLATION_Y, 0.0f, new long[0]);
            Folme.useAt(viewGroup).state().fromTo(add, add2, new AnimConfig().setEase(EaseManager.getStyle(4, 120.0f, 0.99f, 0.1f))).then(add3, new AnimConfig().setEase(EaseManager.getStyle(4, 40.0f, 0.99f, 0.1f)));
        }
    }

    public void addAction(Action action) {
        if (action instanceof IndeterminateUpAction) {
            this.mUpAction = action;
            IndeterminateUpAction indeterminateUpAction = (IndeterminateUpAction) action;
            indeterminateUpAction.mUpDataListener = this.mUpActionDataListener;
            updateTextIdToString(this.mContext, indeterminateUpAction.mTriggerTextIDs, indeterminateUpAction.mTriggerTexts);
            this.mUpTrackingIndicatorLabel.setText(indeterminateUpAction.mTriggerTexts[0]);
            return;
        }
        int binarySearch = Collections.binarySearch(this.mActions, action, Action.DISTANCE_COMPARATOR);
        if (binarySearch < 0) {
            this.mActions.add((-binarySearch) - 1, action);
            if (action instanceof IndeterminateAction) {
                IndeterminateAction indeterminateAction = (IndeterminateAction) action;
                indeterminateAction.mCompleteListener = this.mCompleteListener;
                updateTextIdToString(this.mContext, indeterminateAction.mTriggerTextIDs, indeterminateAction.mTriggerTexts);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("action conflict.");
    }

    public void attach(SpringBackLayout springBackLayout) {
        if (!springBackLayout.springBackEnable()) {
            springBackLayout.setSpringBackEnable(true);
        }
        this.mLayout = springBackLayout;
        springBackLayout.addView(this.mContainer);
        springBackLayout.addView(this.mUpContainer);
        springBackLayout.addOnLayoutChangeListener(this.mLayoutChangeListener);
        springBackLayout.setOnSpringListener(this.mOnSpringListener);
        springBackLayout.addOnScrollListener(this.mOnScrollListener);
    }

    public boolean containAction(Action action) {
        return action instanceof IndeterminateUpAction ? this.mUpAction != null : action != null && this.mActions.contains(action);
    }

    public boolean isActionRunning() {
        TriggerState triggerState = this.mCurrentState;
        return (triggerState == null || triggerState == this.mIdle) ? false : true;
    }

    public boolean isActionRunning(Action action) {
        TriggerState triggerState = this.mCurrentState;
        return (triggerState == null || triggerState == this.mIdle || this.mCurrentAction != action) ? false : true;
    }

    public boolean removeAction(Action action) {
        if (isActionRunning()) {
            return false;
        }
        if (action instanceof IndeterminateUpAction) {
            ((IndeterminateUpAction) action).mUpDataListener = null;
            this.mUpAction = null;
            return true;
        }
        if (action != null && this.mActions.contains(action)) {
            if (action instanceof IndeterminateAction) {
                ((IndeterminateAction) action).mCompleteListener = null;
            }
            this.mActions.remove(action);
        }
        return true;
    }
}
