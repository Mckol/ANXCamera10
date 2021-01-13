package miuix.animation.controller;

import android.app.UiModeManager;
import android.graphics.Color;
import android.util.ArrayMap;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import miuix.animation.IAnimTarget;
import miuix.animation.ITouchStyle;
import miuix.animation.ViewTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.EaseManager;
import miuix.animation.utils.LogUtils;
import miuix.folme.R;

public class FolmeTouch extends FolmeBase implements ITouchStyle {
    private static final float DEFAULT_SCALE = 0.9f;
    private static final int SCALE_DIS = 10;
    private static WeakHashMap<View, InnerViewTouchListener> sTouchRecord = new WeakHashMap<>();
    private AnimConfig mAlphaConfig;
    private boolean mClickInvoked;
    private View.OnClickListener mClickListener;
    private AnimConfig mDownConfig = new AnimConfig();
    private int mDownWeight;
    private float mDownX;
    private float mDownY;
    private FolmeFont mFontStyle;
    private boolean mIsDown;
    private WeakReference<View> mListView;
    private int[] mLocation = new int[2];
    private boolean mLongClickInvoked;
    private View.OnLongClickListener mLongClickListener;
    private LongClickTask mLongClickTask;
    private float mScaleDist;
    private Map<ITouchStyle.TouchType, Boolean> mScaleSetMap = new ArrayMap();
    private boolean mSetTint;
    private int mTouchIndex;
    private WeakReference<View> mTouchView;
    private AnimConfig mUpConfig = new AnimConfig();
    private int mUpWeight;

    /* access modifiers changed from: private */
    public static class InnerListViewTouchListener implements View.OnTouchListener {
        private AnimConfig[] mConfigs;
        private WeakReference<FolmeTouch> mFolmeTouchRef;

        InnerListViewTouchListener(FolmeTouch folmeTouch, AnimConfig... animConfigArr) {
            this.mFolmeTouchRef = new WeakReference<>(folmeTouch);
            this.mConfigs = animConfigArr;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            WeakReference<FolmeTouch> weakReference = this.mFolmeTouchRef;
            FolmeTouch folmeTouch = weakReference == null ? null : weakReference.get();
            if (folmeTouch == null) {
                return false;
            }
            if (motionEvent == null) {
                folmeTouch.onEventUp(this.mConfigs);
                return false;
            }
            folmeTouch.handleMotionEvent(view, motionEvent, this.mConfigs);
            return false;
        }
    }

    /* access modifiers changed from: private */
    public static class InnerViewTouchListener implements View.OnTouchListener {
        private WeakHashMap<FolmeTouch, AnimConfig[]> mTouchMap;

        private InnerViewTouchListener() {
            this.mTouchMap = new WeakHashMap<>();
        }

        /* access modifiers changed from: package-private */
        public void addTouch(FolmeTouch folmeTouch, AnimConfig... animConfigArr) {
            this.mTouchMap.put(folmeTouch, animConfigArr);
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            for (Map.Entry<FolmeTouch, AnimConfig[]> entry : this.mTouchMap.entrySet()) {
                entry.getKey().handleMotionEvent(view, motionEvent, entry.getValue());
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean removeTouch(FolmeTouch folmeTouch) {
            this.mTouchMap.remove(folmeTouch);
            return this.mTouchMap.isEmpty();
        }
    }

    /* access modifiers changed from: private */
    public static class ListViewInfo {
        View itemView;
        AbsListView listView;

        private ListViewInfo() {
        }
    }

    /* access modifiers changed from: private */
    public static final class LongClickTask implements Runnable {
        private WeakReference<FolmeTouch> mTouchRef;

        private LongClickTask() {
        }

        public void run() {
            View view;
            FolmeTouch folmeTouch = this.mTouchRef.get();
            if (folmeTouch != null) {
                IAnimTarget target = folmeTouch.mState.getTarget();
                if ((target instanceof ViewTarget) && (view = (View) target.getTargetObject()) != null && folmeTouch.mLongClickListener != null) {
                    view.performLongClick();
                    folmeTouch.invokeLongClick(view);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void start(FolmeTouch folmeTouch) {
            View targetObject;
            IAnimTarget target = folmeTouch.mState.getTarget();
            if ((target instanceof ViewTarget) && (targetObject = ((ViewTarget) target).getTargetObject()) != null) {
                this.mTouchRef = new WeakReference<>(folmeTouch);
                targetObject.postDelayed(this, (long) ViewConfiguration.getLongPressTimeout());
            }
        }

        /* access modifiers changed from: package-private */
        public void stop(FolmeTouch folmeTouch) {
            View targetObject;
            IAnimTarget target = folmeTouch.mState.getTarget();
            if ((target instanceof ViewTarget) && (targetObject = ((ViewTarget) target).getTargetObject()) != null) {
                targetObject.removeCallbacks(this);
            }
        }
    }

    public FolmeTouch(IAnimTarget... iAnimTargetArr) {
        super(iAnimTargetArr);
        initScaleDist(iAnimTargetArr.length > 0 ? iAnimTargetArr[0] : null);
        FloatProperty property = getProperty(2);
        FloatProperty property2 = getProperty(3);
        this.mState.getState(ITouchStyle.TouchType.UP).add(property, 1.0f, new long[0]).add(property2, 1.0f, new long[0]);
        this.mState.getState(ITouchStyle.TouchType.DOWN).add(property, 0.9f, new long[0]).add(property2, 0.9f, new long[0]);
        setTintColor();
        this.mDownConfig.ease = EaseManager.getStyle(-2, 0.99f, 0.15f);
        this.mDownConfig.addListeners(new TransitionListener() {
            /* class miuix.animation.controller.FolmeTouch.AnonymousClass1 */

            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj, UpdateInfo updateInfo) {
                ITouchStyle.TouchType touchType = ITouchStyle.TouchType.DOWN;
                if (obj == touchType && !FolmeTouch.this.isScaleSet(touchType)) {
                    FloatProperty floatProperty = updateInfo.property;
                    if (floatProperty == ViewProperty.SCALE_X || floatProperty == ViewProperty.SCALE_Y) {
                        IAnimTarget target = FolmeTouch.this.mState.getTarget();
                        float max = Math.max(target.getValue(6), target.getValue(5));
                        float max2 = Math.max((max - FolmeTouch.this.mScaleDist) / max, 0.9f);
                        updateInfo.anim.setValues(max2);
                    }
                }
            }
        });
        this.mUpConfig.ease = EaseManager.getStyle(-2, 0.99f, 0.3f);
        this.mAlphaConfig = new AnimConfig(getProperty(4)).setEase(-2, 0.9f, 0.2f);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean bindListView(View view, boolean z, AnimConfig... animConfigArr) {
        ListViewInfo listViewInfo;
        if (this.mState.getTarget() == null || (listViewInfo = getListViewInfo(view)) == null || listViewInfo.listView == null) {
            return false;
        }
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("handleListViewTouch for " + view, new Object[0]);
        }
        handleListViewTouch(listViewInfo.listView, view, z, animConfigArr);
        return true;
    }

    private void doHandleTouchOf(final View view, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener, final boolean z, final AnimConfig... animConfigArr) {
        setClickAndLongClickListener(onClickListener, onLongClickListener);
        handleViewTouch(view, animConfigArr);
        if (setTouchView(view)) {
            if (LogUtils.isLogEnabled()) {
                LogUtils.debug("handleViewTouch for " + view, new Object[0]);
            }
            final boolean isClickable = view.isClickable();
            view.setClickable(true);
            CommonUtils.runOnPreDraw(view, new Runnable() {
                /* class miuix.animation.controller.FolmeTouch.AnonymousClass3 */

                public void run() {
                    if (!z && FolmeTouch.this.bindListView(view, true, animConfigArr)) {
                        FolmeTouch.this.resetViewTouch(view, isClickable);
                    }
                }
            });
        }
    }

    private AnimConfig[] getDownConfig(AnimConfig... animConfigArr) {
        return (AnimConfig[]) CommonUtils.mergeArray(animConfigArr, this.mDownConfig);
    }

    private ListViewInfo getListViewInfo(View view) {
        AbsListView absListView = null;
        ListViewInfo listViewInfo = new ListViewInfo();
        ViewParent parent = view.getParent();
        while (true) {
            if (parent == null) {
                break;
            } else if (parent instanceof AbsListView) {
                absListView = (AbsListView) parent;
                break;
            } else {
                if (parent instanceof View) {
                    view = (View) parent;
                }
                parent = parent.getParent();
            }
        }
        if (absListView != null) {
            this.mListView = new WeakReference<>(listViewInfo.listView);
            listViewInfo.listView = absListView;
            listViewInfo.itemView = view;
        }
        return listViewInfo;
    }

    public static ListViewTouchListener getListViewTouchListener(AbsListView absListView) {
        return (ListViewTouchListener) absListView.getTag(R.id.miuix_animation_tag_touch_listener);
    }

    private ITouchStyle.TouchType getType(ITouchStyle.TouchType... touchTypeArr) {
        return touchTypeArr.length > 0 ? touchTypeArr[0] : ITouchStyle.TouchType.DOWN;
    }

    private AnimConfig[] getUpConfig(AnimConfig... animConfigArr) {
        return (AnimConfig[]) CommonUtils.mergeArray(animConfigArr, this.mUpConfig, this.mAlphaConfig);
    }

    private void handleClick(View view, MotionEvent motionEvent) {
        if (this.mIsDown && this.mClickListener != null && this.mTouchIndex == motionEvent.getActionIndex()) {
            IAnimTarget target = this.mState.getTarget();
            if ((target instanceof ViewTarget) && isInTouchSlop(view, motionEvent)) {
                View targetObject = ((ViewTarget) target).getTargetObject();
                targetObject.performClick();
                invokeClick(targetObject);
            }
        }
    }

    private void handleListViewTouch(AbsListView absListView, View view, boolean z, AnimConfig... animConfigArr) {
        ListViewTouchListener listViewTouchListener = getListViewTouchListener(absListView);
        if (listViewTouchListener == null) {
            listViewTouchListener = new ListViewTouchListener(absListView);
            absListView.setTag(R.id.miuix_animation_tag_touch_listener, listViewTouchListener);
        }
        if (z) {
            absListView.setOnTouchListener(listViewTouchListener);
        }
        listViewTouchListener.putListener(view, new InnerListViewTouchListener(this, animConfigArr));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleMotionEvent(View view, MotionEvent motionEvent, AnimConfig... animConfigArr) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                handleClick(view, motionEvent);
            } else if (actionMasked == 2) {
                onEventMove(motionEvent, view, animConfigArr);
                return;
            }
            onEventUp(animConfigArr);
            return;
        }
        recordDownEvent(motionEvent);
        onEventDown(animConfigArr);
    }

    private void handleViewTouch(View view, AnimConfig... animConfigArr) {
        InnerViewTouchListener innerViewTouchListener = sTouchRecord.get(view);
        if (innerViewTouchListener == null) {
            innerViewTouchListener = new InnerViewTouchListener();
            sTouchRecord.put(view, innerViewTouchListener);
        }
        view.setOnTouchListener(innerViewTouchListener);
        innerViewTouchListener.addTouch(this, animConfigArr);
    }

    private void initScaleDist(IAnimTarget iAnimTarget) {
        View targetObject = iAnimTarget instanceof ViewTarget ? ((ViewTarget) iAnimTarget).getTargetObject() : null;
        if (targetObject != null) {
            this.mScaleDist = TypedValue.applyDimension(1, 10.0f, targetObject.getResources().getDisplayMetrics());
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void invokeClick(View view) {
        if (!this.mClickInvoked && !this.mLongClickInvoked) {
            this.mClickInvoked = true;
            this.mClickListener.onClick(view);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void invokeLongClick(View view) {
        if (!this.mLongClickInvoked) {
            this.mLongClickInvoked = true;
            this.mLongClickListener.onLongClick(view);
        }
    }

    private boolean isInTouchSlop(View view, MotionEvent motionEvent) {
        return CommonUtils.getDistance(this.mDownX, this.mDownY, motionEvent.getRawX(), motionEvent.getRawY()) < ((double) CommonUtils.getTouchSlop(view));
    }

    static boolean isOnTouchView(View view, int[] iArr, MotionEvent motionEvent) {
        if (view == null) {
            return true;
        }
        view.getLocationOnScreen(iArr);
        int rawX = (int) motionEvent.getRawX();
        int rawY = (int) motionEvent.getRawY();
        return rawX >= iArr[0] && rawX <= iArr[0] + view.getWidth() && rawY >= iArr[1] && rawY <= iArr[1] + view.getHeight();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isScaleSet(ITouchStyle.TouchType touchType) {
        return Boolean.TRUE.equals(this.mScaleSetMap.get(touchType));
    }

    private void onEventDown(AnimConfig... animConfigArr) {
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("onEventDown, touchDown", new Object[0]);
        }
        this.mIsDown = true;
        touchDown(animConfigArr);
    }

    private void onEventMove(MotionEvent motionEvent, View view, AnimConfig... animConfigArr) {
        if (!this.mIsDown) {
            return;
        }
        if (!isOnTouchView(view, this.mLocation, motionEvent)) {
            touchUp(animConfigArr);
            resetTouchStatus();
        } else if (this.mLongClickTask != null && !isInTouchSlop(view, motionEvent)) {
            this.mLongClickTask.stop(this);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onEventUp(AnimConfig... animConfigArr) {
        if (this.mIsDown) {
            if (LogUtils.isLogEnabled()) {
                LogUtils.debug("onEventUp, touchUp", new Object[0]);
            }
            touchUp(animConfigArr);
            resetTouchStatus();
        }
    }

    private void recordDownEvent(MotionEvent motionEvent) {
        if (this.mClickListener != null || this.mLongClickListener != null) {
            this.mTouchIndex = motionEvent.getActionIndex();
            this.mDownX = motionEvent.getRawX();
            this.mDownY = motionEvent.getRawY();
            this.mClickInvoked = false;
            this.mLongClickInvoked = false;
            startLongClickTask();
        }
    }

    private void resetTouchStatus() {
        LongClickTask longClickTask = this.mLongClickTask;
        if (longClickTask != null) {
            longClickTask.stop(this);
        }
        this.mIsDown = false;
        this.mTouchIndex = 0;
        this.mDownX = 0.0f;
        this.mDownY = 0.0f;
    }

    private View resetView(WeakReference<View> weakReference) {
        View view = weakReference.get();
        if (view != null) {
            view.setOnTouchListener(null);
        }
        return view;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void resetViewTouch(View view, boolean z) {
        view.setClickable(z);
        view.setOnTouchListener(null);
    }

    private void setClickAndLongClickListener(View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
        IAnimTarget target = this.mState.getTarget();
        View targetObject = target instanceof ViewTarget ? ((ViewTarget) target).getTargetObject() : null;
        if (targetObject != null) {
            if (this.mClickListener != null && onClickListener == null) {
                targetObject.setOnClickListener(null);
            } else if (onClickListener != null) {
                targetObject.setOnClickListener(new View.OnClickListener() {
                    /* class miuix.animation.controller.FolmeTouch.AnonymousClass4 */

                    public void onClick(View view) {
                        FolmeTouch.this.invokeClick(view);
                    }
                });
            }
            this.mClickListener = onClickListener;
            if (this.mLongClickListener != null && onLongClickListener == null) {
                targetObject.setOnLongClickListener(null);
            } else if (onLongClickListener != null) {
                targetObject.setOnLongClickListener(new View.OnLongClickListener() {
                    /* class miuix.animation.controller.FolmeTouch.AnonymousClass5 */

                    public boolean onLongClick(View view) {
                        if (FolmeTouch.this.mLongClickInvoked) {
                            return false;
                        }
                        FolmeTouch.this.invokeLongClick(view);
                        return true;
                    }
                });
            }
            this.mLongClickListener = onLongClickListener;
        }
    }

    private void setTintColor() {
        if (!this.mSetTint) {
            int argb = Color.argb(20, 0, 0, 0);
            Object targetObject = this.mState.getTarget().getTargetObject();
            if (targetObject instanceof View) {
                View view = (View) targetObject;
                int i = R.color.miuix_folme_color_touch_tint;
                UiModeManager uiModeManager = (UiModeManager) view.getContext().getSystemService("uimode");
                if (uiModeManager != null && uiModeManager.getNightMode() == 2) {
                    i = R.color.miuix_folme_color_touch_tint_dark;
                }
                argb = view.getResources().getColor(i);
            }
            this.mState.getState(ITouchStyle.TouchType.DOWN).add(getProperty(7), argb, new long[0]);
        }
    }

    private boolean setTouchView(View view) {
        WeakReference<View> weakReference = this.mTouchView;
        if ((weakReference != null ? weakReference.get() : null) == view) {
            return false;
        }
        this.mTouchView = new WeakReference<>(view);
        return true;
    }

    private void startLongClickTask() {
        if (this.mLongClickListener != null) {
            if (this.mLongClickTask == null) {
                this.mLongClickTask = new LongClickTask();
            }
            this.mLongClickTask.start(this);
        }
    }

    @Override // miuix.animation.ITouchStyle
    public void bindViewOfListItem(final View view, final AnimConfig... animConfigArr) {
        if (setTouchView(view)) {
            CommonUtils.runOnPreDraw(view, new Runnable() {
                /* class miuix.animation.controller.FolmeTouch.AnonymousClass2 */

                public void run() {
                    FolmeTouch.this.bindListView(view, false, animConfigArr);
                }
            });
        }
    }

    @Override // miuix.animation.ICancelableStyle, miuix.animation.controller.FolmeBase
    public void cancel() {
        super.cancel();
        FolmeFont folmeFont = this.mFontStyle;
        if (folmeFont != null) {
            folmeFont.cancel();
        }
    }

    @Override // miuix.animation.controller.FolmeBase, miuix.animation.IStateContainer
    public void clean() {
        super.clean();
        FolmeFont folmeFont = this.mFontStyle;
        if (folmeFont != null) {
            folmeFont.clean();
        }
        this.mScaleSetMap.clear();
        WeakReference<View> weakReference = this.mTouchView;
        if (weakReference != null) {
            resetView(weakReference);
            this.mTouchView = null;
        }
        WeakReference<View> weakReference2 = this.mListView;
        if (weakReference2 != null) {
            View resetView = resetView(weakReference2);
            if (resetView != null) {
                resetView.setTag(R.id.miuix_animation_tag_touch_listener, null);
            }
            this.mListView = null;
        }
        resetTouchStatus();
    }

    @Override // miuix.animation.ITouchStyle
    public void handleTouchOf(View view, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener, AnimConfig... animConfigArr) {
        doHandleTouchOf(view, onClickListener, onLongClickListener, false, animConfigArr);
    }

    @Override // miuix.animation.ITouchStyle
    public void handleTouchOf(View view, View.OnClickListener onClickListener, AnimConfig... animConfigArr) {
        doHandleTouchOf(view, onClickListener, null, false, animConfigArr);
    }

    @Override // miuix.animation.ITouchStyle
    public void handleTouchOf(View view, boolean z, AnimConfig... animConfigArr) {
        doHandleTouchOf(view, null, null, z, animConfigArr);
    }

    @Override // miuix.animation.ITouchStyle
    public void handleTouchOf(View view, AnimConfig... animConfigArr) {
        handleTouchOf(view, false, animConfigArr);
    }

    @Override // miuix.animation.ITouchStyle
    public void ignoreTouchOf(View view) {
        InnerViewTouchListener innerViewTouchListener = sTouchRecord.get(view);
        if (innerViewTouchListener != null && innerViewTouchListener.removeTouch(this)) {
            sTouchRecord.remove(view);
        }
    }

    @Override // miuix.animation.ITouchStyle
    public void onMotionEvent(MotionEvent motionEvent) {
        handleMotionEvent(null, motionEvent, new AnimConfig[0]);
    }

    @Override // miuix.animation.ITouchStyle
    public void onMotionEventEx(View view, MotionEvent motionEvent, AnimConfig... animConfigArr) {
        handleMotionEvent(view, motionEvent, animConfigArr);
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle setAlpha(float f, ITouchStyle.TouchType... touchTypeArr) {
        this.mState.getState(getType(touchTypeArr)).add(getProperty(4), f, new long[0]);
        return this;
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle setBackgroundColor(float f, float f2, float f3, float f4) {
        return setBackgroundColor(Color.argb((int) (f * 255.0f), (int) (f2 * 255.0f), (int) (f3 * 255.0f), (int) (f4 * 255.0f)));
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle setBackgroundColor(int i) {
        this.mState.getState(ITouchStyle.TouchType.DOWN).add(getProperty(8), i, new long[0]);
        return this;
    }

    public void setFontStyle(FolmeFont folmeFont) {
        this.mFontStyle = folmeFont;
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle setScale(float f, ITouchStyle.TouchType... touchTypeArr) {
        ITouchStyle.TouchType type = getType(touchTypeArr);
        this.mScaleSetMap.put(type, true);
        this.mState.getState(type).add(getProperty(2), f, new long[0]).add(getProperty(3), f, new long[0]);
        return this;
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle setTint(float f, float f2, float f3, float f4) {
        return setTint(Color.argb((int) (f * 255.0f), (int) (f2 * 255.0f), (int) (f3 * 255.0f), (int) (f4 * 255.0f)));
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle setTint(int i) {
        this.mSetTint = true;
        this.mState.getState(ITouchStyle.TouchType.DOWN).add(getProperty(7), i, new long[0]);
        return this;
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle setTintMode(int i) {
        this.mDownConfig.setTintMode(i);
        this.mUpConfig.setTintMode(i);
        return this;
    }

    @Override // miuix.animation.ITouchStyle
    public void setTouchDown() {
        setTintColor();
        this.mState.setTo(ITouchStyle.TouchType.DOWN);
    }

    @Override // miuix.animation.ITouchStyle
    public void setTouchUp() {
        this.mState.setTo(ITouchStyle.TouchType.UP);
    }

    @Override // miuix.animation.ITouchStyle
    public void touchDown(AnimConfig... animConfigArr) {
        setTintColor();
        alignState(ITouchStyle.TouchType.UP, ITouchStyle.TouchType.DOWN);
        AnimConfig[] downConfig = getDownConfig(animConfigArr);
        FolmeFont folmeFont = this.mFontStyle;
        if (folmeFont != null) {
            folmeFont.to(this.mDownWeight, downConfig);
        }
        IFolmeStateStyle iFolmeStateStyle = this.mState;
        iFolmeStateStyle.to(iFolmeStateStyle.getState(ITouchStyle.TouchType.DOWN), downConfig);
    }

    @Override // miuix.animation.ITouchStyle
    public void touchUp(AnimConfig... animConfigArr) {
        alignState(ITouchStyle.TouchType.DOWN, ITouchStyle.TouchType.UP);
        AnimConfig[] upConfig = getUpConfig(animConfigArr);
        FolmeFont folmeFont = this.mFontStyle;
        if (folmeFont != null) {
            folmeFont.to(this.mUpWeight, upConfig);
        }
        IFolmeStateStyle iFolmeStateStyle = this.mState;
        iFolmeStateStyle.to(iFolmeStateStyle.getState(ITouchStyle.TouchType.UP), upConfig);
    }

    @Override // miuix.animation.ITouchStyle
    public ITouchStyle useVarFont(TextView textView, int i, int i2, int i3) {
        FolmeFont folmeFont = this.mFontStyle;
        if (folmeFont != null) {
            this.mUpWeight = i2;
            this.mDownWeight = i3;
            folmeFont.useAt(textView, i, i2);
        }
        return this;
    }
}
