package miui.animation.controller;

import android.util.ArrayMap;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import miui.animation.Folme;
import miui.animation.IAnimTarget;
import miui.animation.IStateStyle;
import miui.animation.base.AnimConfig;
import miui.animation.base.AnimConfigLink;
import miui.animation.internal.AnimRunner;
import miui.animation.internal.AnimTask;
import miui.animation.internal.AnimValueUtils;
import miui.animation.listener.TransitionListener;
import miui.animation.property.FloatProperty;
import miui.animation.property.IIntValueProperty;
import miui.animation.property.IntValueProperty;
import miui.animation.property.ValueProperty;
import miui.animation.utils.CommonUtils;
import miui.animation.utils.EaseManager;
import miui.animation.utils.LogUtils;
import miui.animation.utils.StyleComposer;

public class FolmeState implements IFolmeStateStyle {
    private static final IntValueProperty DEFAULT_INT_PROPERTY = new IntValueProperty("defaultIntProperty");
    private static final ValueProperty DEFAULT_PROPERTY = new ValueProperty("defaultProperty");
    private static final String METHOD_GET_STATE = "getState";
    private static final String TAG_AUTO_SET_TO = "autoSetTo";
    private static final String TAG_PREDICT_FROM = "predictFrom";
    private static final String TAG_PREDICT_TO = "predictTo";
    private static final String TAG_SET_TO = "defaultSetTo";
    private static final String TAG_TO = "defaultTo";
    private static final String TARGET_PREDICT = "predictTarget";
    private static final StyleComposer.IInterceptor<IFolmeStateStyle> sInterceptor = new StyleComposer.IInterceptor<IFolmeStateStyle>() {
        /* class miui.animation.controller.FolmeState.AnonymousClass1 */

        public Object onMethod(Method method, Object[] objArr, IFolmeStateStyle[] iFolmeStateStyleArr) {
            if (iFolmeStateStyleArr.length <= 0 || objArr.length <= 0) {
                return null;
            }
            AnimState state = iFolmeStateStyleArr[0].getState(objArr[0]);
            for (int i = 1; i < iFolmeStateStyleArr.length; i++) {
                iFolmeStateStyleArr[i].addState(state);
            }
            return state;
        }

        @Override // miui.animation.utils.StyleComposer.IInterceptor
        public boolean shouldIntercept(Method method, Object[] objArr) {
            return method.getName().equals(FolmeState.METHOD_GET_STATE);
        }
    };
    private AnimState mAutoSetToState = new AnimState(TAG_AUTO_SET_TO);
    private Object mCurTag = this.mToState;
    private List<FloatProperty> mDelList = new ArrayList();
    private boolean mEnableAnim = true;
    private AnimState mPredictFrom;
    private IAnimTarget mPredictTarget;
    private AnimState mPredictTo;
    private AnimState mSetToState = new AnimState(TAG_SET_TO);
    private Map<Object, AnimState> mStateMap = new ArrayMap();
    private IAnimTarget mTarget;
    private AnimState mToState = new AnimState(TAG_TO);

    FolmeState(IAnimTarget iAnimTarget) {
        this.mTarget = iAnimTarget;
    }

    private boolean addConfigToLink(AnimConfigLink animConfigLink, Object obj) {
        if (obj instanceof AnimConfig) {
            animConfigLink.add((AnimConfig) obj);
            return true;
        } else if (!(obj instanceof AnimConfigLink)) {
            return false;
        } else {
            animConfigLink.add((AnimConfigLink) obj);
            return false;
        }
    }

    private int addProperty(AnimState animState, FloatProperty floatProperty, int i, Object... objArr) {
        Object propertyValue;
        if (floatProperty == null || (propertyValue = getPropertyValue(i, objArr)) == null || !addPropertyValue(animState, floatProperty, propertyValue)) {
            return 0;
        }
        return setInitVelocity(floatProperty, i + 1, objArr) ? 2 : 1;
    }

    private <T> void addProperty(Object obj, FloatProperty floatProperty, T t, long... jArr) {
        AnimState state = getState(obj);
        if (floatProperty instanceof IIntValueProperty) {
            state.add(floatProperty, CommonUtils.toIntValue(t), jArr);
        } else {
            state.add(floatProperty, CommonUtils.toFloatValue(t), jArr);
        }
    }

    private boolean addPropertyValue(AnimState animState, FloatProperty floatProperty, Object obj) {
        boolean z = obj instanceof Integer;
        if (!z && !(obj instanceof Float) && !(obj instanceof Double)) {
            return false;
        }
        if (floatProperty instanceof IIntValueProperty) {
            animState.add(floatProperty, toInt(obj, z), new long[0]);
            return true;
        }
        animState.add(floatProperty, toFloat(obj, z), new long[0]);
        return true;
    }

    private boolean checkAndSetAnimConfig(AnimConfigLink animConfigLink, Object obj) {
        if ((obj instanceof TransitionListener) || (obj instanceof EaseManager.EaseStyle)) {
            setTempConfig(animConfigLink.getHead(), obj);
            return true;
        } else if (!obj.getClass().isArray()) {
            return addConfigToLink(animConfigLink, obj);
        } else {
            int length = Array.getLength(obj);
            boolean z = false;
            for (int i = 0; i < length; i++) {
                z = addConfigToLink(animConfigLink, Array.get(obj, i)) || z;
            }
            return z;
        }
    }

    private void clearDefaultState(AnimState animState) {
        if (animState == this.mSetToState || animState == this.mToState || animState == this.mAutoSetToState) {
            animState.clear();
        }
    }

    public static IFolmeStateStyle composeStyle(IAnimTarget... iAnimTargetArr) {
        if (iAnimTargetArr == null || iAnimTargetArr.length == 0) {
            return null;
        }
        if (iAnimTargetArr.length == 1) {
            return new FolmeState(iAnimTargetArr[0]);
        }
        FolmeState[] folmeStateArr = new FolmeState[iAnimTargetArr.length];
        for (int i = 0; i < iAnimTargetArr.length; i++) {
            folmeStateArr[i] = new FolmeState(iAnimTargetArr[i]);
        }
        return (IFolmeStateStyle) StyleComposer.compose(IFolmeStateStyle.class, sInterceptor, folmeStateArr);
    }

    private IStateStyle fromTo(Object obj, Object obj2, AnimConfigLink animConfigLink) {
        if (this.mEnableAnim) {
            this.mCurTag = obj2;
            AnimState state = getState(obj2);
            AnimState animState = this.mToState;
            if (state != animState) {
                animState.getAllConfig(animConfigLink);
            }
            AnimRunner.getInst().run(this.mTarget, obj != null ? getState(obj) : null, getState(obj2), animConfigLink);
        }
        return this;
    }

    private FloatProperty getProperty(Object obj, Object obj2) {
        Class<?> cls = null;
        if (obj instanceof FloatProperty) {
            return (FloatProperty) obj;
        }
        if (obj instanceof String) {
            if (obj2 != null) {
                cls = obj2.getClass();
            }
            return getTarget().createProperty((String) obj, cls);
        } else if (obj instanceof Float) {
            return DEFAULT_PROPERTY;
        } else {
            if (!(obj instanceof Integer)) {
                return null;
            }
            FloatProperty property = getTarget().getProperty(((Integer) obj).intValue());
            return property == null ? DEFAULT_INT_PROPERTY : property;
        }
    }

    private Object getPropertyValue(int i, Object... objArr) {
        if (i < objArr.length) {
            return objArr[i];
        }
        return null;
    }

    private AnimState getState(Object obj, boolean z) {
        if (obj instanceof AnimState) {
            return (AnimState) obj;
        }
        AnimState animState = this.mStateMap.get(obj);
        if (animState != null || !z) {
            return animState;
        }
        AnimState animState2 = new AnimState(obj);
        addState(animState2);
        return animState2;
    }

    private AnimState getStateByArgs(Object obj, Object... objArr) {
        AnimState animState;
        if (objArr.length > 0) {
            animState = getState(objArr[0], false);
            if (animState == null) {
                animState = getStateByName(objArr);
            }
        } else {
            animState = null;
        }
        return animState == null ? getState(obj) : animState;
    }

    private AnimState getStateByName(Object... objArr) {
        Object obj = objArr[0];
        Object obj2 = objArr.length > 1 ? objArr[1] : null;
        if (!(obj instanceof String) || !(obj2 instanceof String)) {
            return null;
        }
        return getState(obj, true);
    }

    private void handleFloatProperty(IAnimTarget iAnimTarget, FloatProperty floatProperty) {
        float f = this.mAutoSetToState.get(iAnimTarget, floatProperty);
        if (Math.abs(iAnimTarget.getValue(floatProperty) - f) > iAnimTarget.getMinVisibleChange(floatProperty)) {
            getCurrentState().add(floatProperty, f, new long[0]);
            this.mDelList.add(floatProperty);
        }
    }

    private void handleIntProperty(IAnimTarget iAnimTarget, FloatProperty floatProperty) {
        int i = this.mAutoSetToState.getInt(floatProperty);
        if (((float) Math.abs(iAnimTarget.getIntValue((IIntValueProperty) floatProperty) - i)) > iAnimTarget.getMinVisibleChange(floatProperty)) {
            getCurrentState().add(floatProperty, i, new long[0]);
            this.mDelList.add(floatProperty);
        }
    }

    private void initPredictTarget() {
        if (this.mPredictTarget == null) {
            this.mPredictTarget = Folme.getValueTarget(TARGET_PREDICT);
            this.mPredictFrom = new AnimState(TAG_PREDICT_FROM);
            this.mPredictTo = new AnimState(TAG_PREDICT_TO);
        } else {
            this.mPredictFrom.clear();
            this.mPredictTo.clear();
        }
        IAnimTarget target = getTarget();
        for (FloatProperty floatProperty : this.mPredictTo.keySet()) {
            this.mPredictTarget.setMinVisibleChange(floatProperty, target.getMinVisibleChange(floatProperty));
        }
    }

    private boolean isDefaultProperty(FloatProperty floatProperty) {
        return floatProperty == DEFAULT_PROPERTY || floatProperty == DEFAULT_INT_PROPERTY;
    }

    private FloatProperty[] nameToProperty(String... strArr) {
        FloatProperty[] floatPropertyArr = new FloatProperty[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            floatPropertyArr[i] = new ValueProperty(strArr[i]);
        }
        return floatPropertyArr;
    }

    private void parse(AnimState animState, AnimConfigLink animConfigLink, Object... objArr) {
        if (objArr.length != 0) {
            int equals = animState.getTag().equals(objArr[0]);
            while (equals < objArr.length) {
                int i = equals + 1;
                equals = setPropertyAndValue(animState, animConfigLink, objArr[equals], i < objArr.length ? objArr[i] : null, equals, objArr);
            }
        }
    }

    private AnimConfigLink setAnimState(AnimState animState, Object... objArr) {
        AnimConfigLink animConfigLink = new AnimConfigLink();
        animConfigLink.add(new AnimConfig());
        clearDefaultState(animState);
        parse(animState, animConfigLink, objArr);
        return animConfigLink;
    }

    private boolean setInitVelocity(FloatProperty floatProperty, int i, Object... objArr) {
        if (i >= objArr.length) {
            return false;
        }
        Object obj = objArr[i];
        if (!(obj instanceof Float)) {
            return false;
        }
        getTarget().setVelocity(floatProperty, (double) ((Float) obj).floatValue());
        return true;
    }

    private int setPropertyAndValue(AnimState animState, AnimConfigLink animConfigLink, Object obj, Object obj2, int i, Object... objArr) {
        int i2;
        FloatProperty property;
        if (checkAndSetAnimConfig(animConfigLink, obj) || (property = getProperty(obj, obj2)) == null) {
            i2 = 0;
        } else {
            if (!isDefaultProperty(property)) {
                i++;
            }
            i2 = addProperty(animState, property, i, objArr);
        }
        return i2 > 0 ? i + i2 : i + 1;
    }

    private void setTempConfig(AnimConfig animConfig, Object obj) {
        if (obj instanceof TransitionListener) {
            animConfig.addListeners((TransitionListener) obj);
        } else if (obj instanceof EaseManager.EaseStyle) {
            animConfig.setEase((EaseManager.EaseStyle) obj);
        }
    }

    private IStateStyle setTo(final Object obj, final AnimConfigLink animConfigLink) {
        IAnimTarget iAnimTarget = this.mTarget;
        if (iAnimTarget == null) {
            return this;
        }
        if ((obj instanceof Integer) || (obj instanceof Float)) {
            return setTo(obj, animConfigLink);
        }
        iAnimTarget.executeOnInitialized(new Runnable() {
            /* class miui.animation.controller.FolmeState.AnonymousClass2 */

            public void run() {
                AnimState state = FolmeState.this.getState(obj);
                IAnimTarget target = FolmeState.this.getTarget();
                AnimTask animTask = target.getAnimTask();
                if (animTask.isValid()) {
                    animTask.cancel((FloatProperty[]) state.keySet().toArray(new FloatProperty[0]));
                }
                if (LogUtils.isLogEnabled()) {
                    LogUtils.debug("FolmeState.setTo, state = " + state, new Object[0]);
                }
                AnimValueUtils.applyProperty(FolmeState.this.mTarget, state, new long[0]);
                for (FloatProperty floatProperty : state.keySet()) {
                    target.trackVelocity(floatProperty, (double) (floatProperty instanceof IIntValueProperty ? target.getIntValue((IIntValueProperty) floatProperty) : target.getValue(floatProperty)));
                }
                animTask.setToNotify(state, animConfigLink);
            }
        });
        return this;
    }

    private float toFloat(Object obj, boolean z) {
        return z ? (float) ((Integer) obj).intValue() : ((Float) obj).floatValue();
    }

    private int toInt(Object obj, boolean z) {
        return z ? ((Integer) obj).intValue() : (int) ((Float) obj).floatValue();
    }

    @Override // miui.animation.IStateStyle
    public <T> IStateStyle add(String str, T t, long... jArr) {
        return add(new ValueProperty(str), t, jArr);
    }

    @Override // miui.animation.IStateStyle
    public <T> IStateStyle add(FloatProperty floatProperty, T t, long... jArr) {
        addProperty(getCurrentState(), floatProperty, t, jArr);
        return this;
    }

    @Override // miui.animation.IStateContainer
    public void addConfig(Object obj, AnimConfig... animConfigArr) {
        getState(obj).addConfig(animConfigArr);
    }

    @Override // miui.animation.IStateStyle
    public <T> IStateStyle addInitProperty(String str, T t) {
        return addInitProperty(new ValueProperty(str), t);
    }

    @Override // miui.animation.IStateStyle
    public <T> IStateStyle addInitProperty(FloatProperty floatProperty, T t) {
        addProperty(this.mCurTag, floatProperty, t, 2);
        return this;
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle addListener(TransitionListener transitionListener) {
        getCurrentState().getGlobalConfig().addListeners(transitionListener);
        return this;
    }

    @Override // miui.animation.controller.IFolmeStateStyle
    public void addState(AnimState animState) {
        this.mStateMap.put(animState.getTag(), animState);
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle autoSetTo(Object... objArr) {
        IAnimTarget target = getTarget();
        AnimConfigLink animState = setAnimState(this.mAutoSetToState, objArr);
        getCurrentState().clear();
        this.mDelList.clear();
        for (FloatProperty floatProperty : this.mAutoSetToState.keySet()) {
            if (floatProperty instanceof IIntValueProperty) {
                handleIntProperty(target, floatProperty);
            } else {
                handleFloatProperty(target, floatProperty);
            }
        }
        for (FloatProperty floatProperty2 : this.mDelList) {
            this.mAutoSetToState.remove(floatProperty2);
        }
        to(getCurrentState(), animState);
        setTo(this.mAutoSetToState, animState);
        return this;
    }

    @Override // miui.animation.ICancelableStyle
    public void cancel() {
        AnimRunner.getInst().cancel(this.mTarget, new FloatProperty[0]);
    }

    @Override // miui.animation.ICancelableStyle
    public void cancel(String... strArr) {
        cancel(nameToProperty(strArr));
    }

    @Override // miui.animation.ICancelableStyle
    public void cancel(FloatProperty... floatPropertyArr) {
        AnimRunner.getInst().cancel(this.mTarget, floatPropertyArr);
    }

    @Override // miui.animation.IStateContainer
    public void clean() {
        cancel();
    }

    public void clear() {
        this.mStateMap.clear();
    }

    @Override // miui.animation.IStateContainer
    public void enableDefaultAnim(boolean z) {
        this.mEnableAnim = z;
    }

    @Override // miui.animation.ICancelableStyle
    public void end(Object... objArr) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : objArr) {
            if (obj instanceof FloatProperty) {
                arrayList.add((FloatProperty) obj);
            } else if (obj instanceof String) {
                arrayList.add(new ValueProperty((String) obj));
            }
        }
        AnimRunner.getInst().end(this.mTarget, (FloatProperty[]) arrayList.toArray(new FloatProperty[0]));
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle fromTo(Object obj, Object obj2, AnimConfig... animConfigArr) {
        fromTo(obj, obj2, AnimConfigLink.linkConfig(animConfigArr));
        return this;
    }

    @Override // miui.animation.IStateStyle
    public AnimState getCurrentState() {
        if (this.mCurTag == null) {
            this.mCurTag = this.mToState;
        }
        return getState(this.mCurTag);
    }

    @Override // miui.animation.controller.IFolmeStateStyle
    public AnimState getState(Object obj) {
        return getState(obj, true);
    }

    @Override // miui.animation.controller.IFolmeStateStyle
    public IAnimTarget getTarget() {
        return this.mTarget;
    }

    @Override // miui.animation.IStateStyle
    public long predictDuration(Object... objArr) {
        initPredictTarget();
        AnimConfigLink animState = setAnimState(this.mPredictTo, objArr);
        AnimState.alignState(getTarget(), this.mPredictFrom, this.mPredictTo);
        IAnimTarget iAnimTarget = this.mPredictTarget;
        AnimState animState2 = this.mPredictFrom;
        AnimState animState3 = this.mPredictTo;
        long j = 0;
        AnimTask animTask = AnimRunner.getAnimTask(iAnimTarget, 0, animState2, animState3, animState);
        long averageDelta = AnimRunner.getInst().getAverageDelta(16);
        while (animTask.isValid() && !animTask.isFinished()) {
            animTask.run(j, averageDelta, new long[0]);
            j += averageDelta;
        }
        return j;
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle removeListener(TransitionListener transitionListener) {
        getCurrentState().getGlobalConfig().removeListeners(transitionListener);
        return this;
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle set(Object obj) {
        return setup(obj);
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle setConfig(AnimConfig animConfig, FloatProperty... floatPropertyArr) {
        AnimState currentState = getCurrentState();
        if (floatPropertyArr.length > 0) {
            AnimConfig animConfig2 = new AnimConfig(animConfig);
            animConfig2.relatedProperty = floatPropertyArr;
            currentState.addConfig(animConfig2);
        } else {
            currentState.setGlobalConfig(animConfig);
        }
        return this;
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle setEase(int i, float... fArr) {
        getCurrentState().getGlobalConfig().ease = EaseManager.getStyle(i, fArr);
        return this;
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle setEase(FloatProperty floatProperty, int i, float... fArr) {
        getCurrentState().getConfig(floatProperty).ease = EaseManager.getStyle(i, fArr);
        return this;
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle setEase(EaseManager.EaseStyle easeStyle, FloatProperty... floatPropertyArr) {
        if (floatPropertyArr.length == 0) {
            getCurrentState().getGlobalConfig().ease = easeStyle;
        } else {
            getCurrentState().getConfig(floatPropertyArr[0]).ease = easeStyle;
        }
        return this;
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle setTo(Object obj) {
        return setTo(obj, new AnimConfig[0]);
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle setTo(Object obj, AnimConfig... animConfigArr) {
        return setTo(obj, AnimConfigLink.linkConfig(animConfigArr));
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle setTo(Object... objArr) {
        AnimState stateByArgs = getStateByArgs(this.mSetToState, objArr);
        setTo(stateByArgs, setAnimState(stateByArgs, objArr));
        return this;
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle setTransitionFlags(long j, FloatProperty... floatPropertyArr) {
        AnimState currentState = getCurrentState();
        (floatPropertyArr.length == 0 ? currentState.getGlobalConfig() : currentState.getConfig(floatPropertyArr[0])).flags = j;
        return this;
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle setup(Object obj) {
        this.mCurTag = obj;
        return this;
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle then(Object obj, AnimConfig... animConfigArr) {
        AnimConfig animConfig = new AnimConfig();
        animConfig.flags = 1;
        return to(obj, (AnimConfig[]) CommonUtils.mergeArray(animConfigArr, animConfig));
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle then(Object... objArr) {
        AnimConfig animConfig = new AnimConfig();
        animConfig.flags = 1;
        return to(objArr, animConfig);
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle to(Object obj, AnimConfig... animConfigArr) {
        if ((obj instanceof AnimState) || this.mStateMap.get(obj) != null) {
            return fromTo((Object) null, getState(obj), animConfigArr);
        }
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            Object[] objArr = new Object[(animConfigArr.length + length)];
            System.arraycopy(obj, 0, objArr, 0, length);
            System.arraycopy(animConfigArr, 0, objArr, length, animConfigArr.length);
            return to(objArr);
        }
        return to(obj, animConfigArr);
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle to(Object... objArr) {
        AnimState stateByArgs = getStateByArgs(getCurrentState(), objArr);
        fromTo((Object) null, stateByArgs, setAnimState(stateByArgs, objArr));
        return this;
    }

    @Override // miui.animation.IStateStyle
    public IStateStyle to(AnimConfig... animConfigArr) {
        return to(getCurrentState(), animConfigArr);
    }
}
