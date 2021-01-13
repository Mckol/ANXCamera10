package miui.animation.controller;

import android.util.ArrayMap;
import java.util.Map;
import java.util.Set;
import miui.animation.IAnimTarget;
import miui.animation.base.AnimConfig;
import miui.animation.base.AnimConfigLink;
import miui.animation.internal.AnimValueUtils;
import miui.animation.property.FloatProperty;
import miui.animation.property.IIntValueProperty;
import miui.animation.property.IntValueProperty;
import miui.animation.property.ValueProperty;
import miui.animation.property.ViewProperty;
import miui.animation.utils.CommonUtils;

public class AnimState {
    public static final long FLAG_DELTA = 1;
    public static final long FLAG_INIT = 2;
    private static int STEP = 100;
    public static final int VIEW_POS = (STEP + 1000000);
    public static final int VIEW_SIZE = 1000000;
    private AnimConfig mGlobalConfig = new AnimConfig();
    private Map<FloatProperty, StateValue> mMap = new ArrayMap();
    private Object mTag;

    /* access modifiers changed from: private */
    public static class StateValue {
        AnimConfig config;
        boolean enable = true;
        long flags;
        int intValue;
        float value;

        StateValue() {
        }

        StateValue(StateValue stateValue) {
            if (stateValue != null) {
                this.value = stateValue.value;
                this.intValue = stateValue.intValue;
                this.enable = stateValue.enable;
                this.flags = stateValue.flags;
                this.config = new AnimConfig(this.config);
            }
        }

        /* access modifiers changed from: package-private */
        public StateValue setFlags(long j) {
            this.flags = j;
            return this;
        }

        /* access modifiers changed from: package-private */
        public StateValue setIntValue(int i) {
            this.intValue = i;
            return this;
        }

        /* access modifiers changed from: package-private */
        public StateValue setValue(float f) {
            this.value = f;
            return this;
        }

        public String toString() {
            return "StateValue{value=" + this.value + ", intValue = " + this.intValue + ", enable=" + this.enable + ", flags = " + this.flags + '}';
        }
    }

    public AnimState(Object obj) {
        if (obj != null) {
            this.mTag = obj;
            return;
        }
        throw new IllegalArgumentException("tag mustn't be null");
    }

    private static void addPropertyTo(AnimState animState, IAnimTarget iAnimTarget, FloatProperty floatProperty) {
        if (floatProperty instanceof IIntValueProperty) {
            animState.add(floatProperty, iAnimTarget.getIntValue((IIntValueProperty) floatProperty), new long[0]);
        } else {
            animState.add(floatProperty, iAnimTarget.getValue(floatProperty), new long[0]);
        }
    }

    public static void alignState(IAnimTarget iAnimTarget, AnimState animState, AnimState animState2) {
        for (FloatProperty floatProperty : animState2.keySet()) {
            float f = animState2.getStateValue(floatProperty).value;
            if (!(f == 1000000.0f || f == ((float) VIEW_POS) || animState.contains(floatProperty))) {
                addPropertyTo(animState, iAnimTarget, floatProperty);
            }
        }
    }

    private void append(AnimState animState) {
        this.mGlobalConfig = animState.mGlobalConfig;
        this.mMap.clear();
        for (FloatProperty floatProperty : animState.mMap.keySet()) {
            this.mMap.put(floatProperty, new StateValue(animState.mMap.get(floatProperty)));
        }
    }

    private StateValue getStateValue(FloatProperty floatProperty) {
        StateValue stateValue = this.mMap.get(floatProperty);
        if (stateValue != null) {
            return stateValue;
        }
        StateValue stateValue2 = new StateValue();
        this.mMap.put(floatProperty, stateValue2);
        return stateValue2;
    }

    public AnimState add(String str, float f, long... jArr) {
        return add(new ValueProperty(str), f, jArr);
    }

    public AnimState add(String str, int i, long... jArr) {
        return add((FloatProperty) new IntValueProperty(str), i, jArr);
    }

    public AnimState add(FloatProperty floatProperty, float f, long... jArr) {
        StateValue stateValue = this.mMap.get(floatProperty);
        if (stateValue == null) {
            stateValue = new StateValue();
            this.mMap.put(floatProperty, stateValue);
        }
        stateValue.setValue(f).setFlags(jArr.length > 0 ? jArr[0] : 0);
        return this;
    }

    public AnimState add(FloatProperty floatProperty, int i, long... jArr) {
        if (floatProperty instanceof IIntValueProperty) {
            StateValue stateValue = this.mMap.get(floatProperty);
            if (stateValue == null) {
                stateValue = new StateValue();
                this.mMap.put(floatProperty, stateValue);
            }
            stateValue.setIntValue(i).setFlags(jArr.length > 0 ? jArr[0] : 0);
        } else {
            add(floatProperty, (float) i, jArr);
        }
        return this;
    }

    public AnimState add(ViewProperty viewProperty, float f, long... jArr) {
        return add((FloatProperty) viewProperty, f, jArr);
    }

    public AnimState add(ViewProperty viewProperty, int i, long... jArr) {
        return add((FloatProperty) viewProperty, i, jArr);
    }

    public void addConfig(AnimConfig... animConfigArr) {
        for (AnimConfig animConfig : animConfigArr) {
            if (!CommonUtils.isArrayEmpty(animConfig.relatedProperty)) {
                for (FloatProperty floatProperty : animConfig.relatedProperty) {
                    getStateValue(floatProperty).config = animConfig;
                }
            } else {
                this.mGlobalConfig = animConfig;
            }
        }
    }

    public void clear() {
        this.mMap.clear();
    }

    public boolean contains(FloatProperty floatProperty) {
        return this.mMap.containsKey(floatProperty);
    }

    public void enable(FloatProperty floatProperty, boolean z) {
        StateValue stateValue = this.mMap.get(floatProperty);
        if (stateValue != null) {
            stateValue.enable = z;
        }
    }

    public float get(IAnimTarget iAnimTarget, FloatProperty floatProperty) {
        StateValue stateValue = this.mMap.get(floatProperty);
        if (stateValue == null) {
            return Float.MAX_VALUE;
        }
        stateValue.value = AnimValueUtils.getValue(iAnimTarget, floatProperty, stateValue.value);
        return stateValue.value;
    }

    public void getAllConfig(AnimConfigLink animConfigLink) {
        animConfigLink.add(getGlobalConfig());
        for (StateValue stateValue : this.mMap.values()) {
            AnimConfig animConfig = stateValue.config;
            if (animConfig != null) {
                animConfigLink.add(animConfig);
            }
        }
    }

    public AnimConfig getConfig(FloatProperty floatProperty) {
        StateValue stateValue = getStateValue(floatProperty);
        if (stateValue.config == null) {
            stateValue.config = new AnimConfig(floatProperty);
        }
        return stateValue.config;
    }

    public long getFlags(FloatProperty floatProperty) {
        return getStateValue(floatProperty).flags;
    }

    public float getFloat(String str) {
        return getFloat(new ValueProperty(str));
    }

    public float getFloat(FloatProperty floatProperty) {
        StateValue stateValue = this.mMap.get(floatProperty);
        if (stateValue != null) {
            return stateValue.value;
        }
        return Float.MAX_VALUE;
    }

    public AnimConfig getGlobalConfig() {
        if (this.mGlobalConfig == null) {
            this.mGlobalConfig = new AnimConfig();
        }
        return this.mGlobalConfig;
    }

    public int getInt(String str) {
        return getInt(new IntValueProperty(str));
    }

    public int getInt(FloatProperty floatProperty) {
        StateValue stateValue;
        if ((floatProperty instanceof IIntValueProperty) && (stateValue = this.mMap.get(floatProperty)) != null) {
            return stateValue.intValue;
        }
        return Integer.MAX_VALUE;
    }

    public Object getTag() {
        return this.mTag;
    }

    public boolean hasFlags(FloatProperty floatProperty, long j) {
        return CommonUtils.hasFlags(getStateValue(floatProperty).flags, j);
    }

    public boolean isEmpty() {
        return this.mMap.isEmpty();
    }

    public boolean isEnabled(FloatProperty floatProperty) {
        StateValue stateValue = this.mMap.get(floatProperty);
        return stateValue != null && stateValue.enable;
    }

    public Set<FloatProperty> keySet() {
        return this.mMap.keySet();
    }

    public AnimState remove(FloatProperty floatProperty) {
        this.mMap.remove(floatProperty);
        return this;
    }

    public void set(AnimState animState) {
        if (animState != null) {
            this.mTag = animState.mTag;
            append(animState);
        }
    }

    /* access modifiers changed from: package-private */
    public void setGlobalConfig(AnimConfig animConfig) {
        this.mGlobalConfig = animConfig;
    }

    public void setTag(Object obj) {
        this.mTag = obj;
    }

    public String toString() {
        return "\nAnimState{mTag='" + this.mTag + '\'' + ", mMaps=" + ((Object) CommonUtils.mapToString(this.mMap, "    ")) + '}';
    }
}
