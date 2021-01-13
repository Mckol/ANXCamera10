package miui.animation.base;

import java.util.ArrayList;
import java.util.List;
import miui.animation.property.FloatProperty;
import miui.animation.utils.CommonUtils;
import miui.animation.utils.EaseManager;

public class AnimConfigLink {
    private static IMapOperation<Long> mDelayOp = new IMapOperation<Long>() {
        /* class miui.animation.base.AnimConfigLink.AnonymousClass1 */

        public Long process(AnimConfig animConfig, FloatProperty floatProperty, Long l) {
            return Long.valueOf(l.longValue() + animConfig.delay);
        }
    };
    private static IMapOperation<Long> mFlagsOp = new IMapOperation<Long>() {
        /* class miui.animation.base.AnimConfigLink.AnonymousClass3 */

        public Long process(AnimConfig animConfig, FloatProperty floatProperty, Long l) {
            return Long.valueOf(animConfig.flags | l.longValue());
        }
    };
    private static IMapOperation<Float> mFromSpeedOp = new IMapOperation<Float>() {
        /* class miui.animation.base.AnimConfigLink.AnonymousClass4 */

        public Float process(AnimConfig animConfig, FloatProperty floatProperty, Float f) {
            return animConfig.fromSpeed == Float.MAX_VALUE ? f : f.floatValue() == Float.MAX_VALUE ? Float.valueOf(animConfig.fromSpeed) : Float.valueOf(Math.max(f.floatValue(), animConfig.fromSpeed));
        }
    };
    private static IMapOperation<Long> mMinDurationOp = new IMapOperation<Long>() {
        /* class miui.animation.base.AnimConfigLink.AnonymousClass5 */

        public Long process(AnimConfig animConfig, FloatProperty floatProperty, Long l) {
            return Long.valueOf(Math.max(l.longValue(), animConfig.minDuration));
        }
    };
    private static IMapOperation<Integer> mTintModeOp = new IMapOperation<Integer>() {
        /* class miui.animation.base.AnimConfigLink.AnonymousClass2 */

        public Integer process(AnimConfig animConfig, FloatProperty floatProperty, Integer num) {
            return Integer.valueOf(Math.max(num.intValue(), animConfig.tintMode));
        }
    };
    public List<AnimConfig> configList = new ArrayList();

    /* access modifiers changed from: private */
    public interface IMapOperation<T> {
        T process(AnimConfig animConfig, FloatProperty floatProperty, T t);
    }

    private static boolean canNotApply(AnimConfig animConfig, FloatProperty floatProperty) {
        return !CommonUtils.isArrayEmpty(animConfig.relatedProperty) && !CommonUtils.inArray(animConfig.relatedProperty, floatProperty);
    }

    private boolean isSameTag(Object obj, Object obj2) {
        return obj.getClass() == obj2.getClass() && obj.toString().equals(obj2.toString());
    }

    public static AnimConfigLink linkConfig(AnimConfig... animConfigArr) {
        AnimConfigLink animConfigLink = new AnimConfigLink();
        for (AnimConfig animConfig : animConfigArr) {
            animConfigLink.add(animConfig);
        }
        return animConfigLink;
    }

    private <T> T mapLink(Object obj, FloatProperty floatProperty, IMapOperation<T> iMapOperation, T t) {
        Object obj2;
        for (AnimConfig animConfig : this.configList) {
            if (obj == null || (obj2 = animConfig.tag) == null || !isSameTag(obj2, obj)) {
                if (CommonUtils.isArrayEmpty(animConfig.relatedProperty)) {
                    t = iMapOperation.process(animConfig, null, t);
                } else if (CommonUtils.inArray(animConfig.relatedProperty, floatProperty)) {
                    t = iMapOperation.process(animConfig, floatProperty, t);
                }
            }
        }
        return t;
    }

    private static boolean srcPreferProperty(AnimConfig animConfig, AnimConfig animConfig2) {
        return animConfig == null ? animConfig2 != null : CommonUtils.isArrayEmpty(animConfig.relatedProperty) && !CommonUtils.isArrayEmpty(animConfig2.relatedProperty);
    }

    public void add(AnimConfig animConfig) {
        if (animConfig != null && !this.configList.contains(animConfig)) {
            this.configList.add(new AnimConfig(animConfig));
        }
    }

    public void add(AnimConfigLink animConfigLink) {
        if (animConfigLink != null) {
            for (AnimConfig animConfig : animConfigLink.configList) {
                add(animConfig);
            }
        }
    }

    public void clear() {
        this.configList.clear();
    }

    public long getDelay(Object obj, FloatProperty floatProperty) {
        return ((Long) mapLink(obj, floatProperty, mDelayOp, 0L)).longValue();
    }

    public EaseManager.EaseStyle getEase(FloatProperty floatProperty) {
        EaseManager.EaseStyle easeStyle = null;
        AnimConfig animConfig = null;
        for (AnimConfig animConfig2 : this.configList) {
            if (animConfig2.ease != null && animConfig2.effectFromValue <= 0.0d && !canNotApply(animConfig2, floatProperty)) {
                if (easeStyle == null || srcPreferProperty(animConfig, animConfig2) || EaseManager.isPhysicsStyle(animConfig2.ease.style)) {
                    easeStyle = animConfig2.ease;
                    animConfig = animConfig2;
                }
            }
        }
        return easeStyle == null ? CommonUtils.sDefEase : easeStyle;
    }

    public EaseManager.EaseStyle getEaseEffectFromValue(FloatProperty floatProperty, double d2, double d3) {
        for (AnimConfig animConfig : this.configList) {
            if (!(animConfig.ease == null || animConfig.effectFromValue == 0.0d || canNotApply(animConfig, floatProperty))) {
                double d4 = animConfig.effectFromValue;
                if (d4 > d2 && d4 <= d3) {
                    return animConfig.ease;
                }
            }
        }
        return null;
    }

    public long getFlags(Object obj, FloatProperty floatProperty) {
        return ((Long) mapLink(obj, floatProperty, mFlagsOp, 0L)).longValue();
    }

    public float getFromSpeed(Object obj, FloatProperty floatProperty) {
        return ((Float) mapLink(obj, floatProperty, mFromSpeedOp, Float.valueOf(Float.MAX_VALUE))).floatValue();
    }

    public AnimConfig getHead() {
        return this.configList.get(0);
    }

    public long getMinDuration(Object obj, FloatProperty floatProperty) {
        return ((Long) mapLink(obj, floatProperty, mMinDurationOp, 0L)).longValue();
    }

    public int getTintMode(Object obj, FloatProperty floatProperty) {
        return ((Integer) mapLink(obj, floatProperty, mTintModeOp, 0)).intValue();
    }

    public void remove(AnimConfig animConfig) {
        this.configList.remove(animConfig);
    }

    public int size() {
        return this.configList.size();
    }
}
