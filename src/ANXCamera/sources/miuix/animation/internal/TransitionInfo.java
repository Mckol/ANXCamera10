package miuix.animation.internal;

import android.util.ArrayMap;
import java.util.concurrent.atomic.AtomicLong;
import miuix.animation.IAnimTarget;
import miuix.animation.base.AnimConfigLink;
import miuix.animation.controller.AnimState;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.utils.CommonUtils;

/* access modifiers changed from: package-private */
public class TransitionInfo {
    private static AtomicLong sIdGenerator = new AtomicLong(0);
    AnimConfigLink config;
    ArrayMap<FloatProperty, Number> fromPropValues = new ArrayMap<>();
    IAnimTarget target;
    ArrayMap<FloatProperty, Long> toFlags = new ArrayMap<>();
    ArrayMap<FloatProperty, Number> toPropValues = new ArrayMap<>();
    Object toTag;
    long transitionId = sIdGenerator.getAndIncrement();

    TransitionInfo(IAnimTarget iAnimTarget, AnimState animState, AnimState animState2, AnimConfigLink animConfigLink) {
        this.target = iAnimTarget;
        toPropValues(this.fromPropValues, animState);
        toPropValues(this.toPropValues, animState2);
        this.toTag = animState2.getTag();
        this.config = animConfigLink;
        animState2.getAllConfig(this.config);
    }

    private Number getValue(AnimState animState, FloatProperty floatProperty) {
        return floatProperty instanceof IIntValueProperty ? Integer.valueOf(animState.getInt(floatProperty)) : Float.valueOf(animState.getFloat(floatProperty));
    }

    private void toPropValues(ArrayMap<FloatProperty, Number> arrayMap, AnimState animState) {
        if (animState != null) {
            for (FloatProperty floatProperty : animState.keySet()) {
                arrayMap.put(floatProperty, getValue(animState, floatProperty));
                long flags = animState.getFlags(floatProperty);
                if (flags != 0) {
                    this.toFlags.put(floatProperty, Long.valueOf(flags));
                }
            }
        }
    }

    public String toString() {
        return "TransitionInfo{target=" + this.target + ", from=" + ((Object) CommonUtils.mapToString(this.fromPropValues, "    ")) + ", to=" + ((Object) CommonUtils.mapToString(this.toPropValues, "    ")) + ", config=" + this.config + '}';
    }
}
