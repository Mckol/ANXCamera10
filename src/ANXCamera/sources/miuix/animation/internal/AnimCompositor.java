package miuix.animation.internal;

import android.util.ArrayMap;
import java.util.Map;
import miuix.animation.IAnimTarget;
import miuix.animation.property.FloatProperty;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.LogUtils;

/* access modifiers changed from: package-private */
public class AnimCompositor {
    private static final String MAP_PREFIX = "    ";

    private AnimCompositor() {
    }

    static Map<FloatProperty, AnimRunningInfo> createAnimInfo(IAnimTarget iAnimTarget, TransitionInfo transitionInfo) {
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("createAnimInfo, target = " + iAnimTarget.getTargetObject(), "tag = " + transitionInfo.toTag, "from = " + ((Object) CommonUtils.mapToString(transitionInfo.fromPropValues, MAP_PREFIX)), "to = " + ((Object) CommonUtils.mapToString(transitionInfo.toPropValues, MAP_PREFIX)));
        }
        ArrayMap arrayMap = new ArrayMap();
        for (FloatProperty floatProperty : transitionInfo.toPropValues.keySet()) {
            arrayMap.put(floatProperty, createRunningInfo(iAnimTarget, floatProperty, transitionInfo));
        }
        return arrayMap;
    }

    private static AnimRunningInfo createRunningInfo(IAnimTarget iAnimTarget, FloatProperty floatProperty, TransitionInfo transitionInfo) {
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("createAnimInfo for " + iAnimTarget.getTargetObject(), floatProperty.getName(), "toTag = " + transitionInfo.toTag);
        }
        AnimRunningInfo animRunningInfo = new AnimRunningInfo();
        animRunningInfo.transId = transitionInfo.transitionId;
        animRunningInfo.target = iAnimTarget;
        animRunningInfo.property = floatProperty;
        animRunningInfo.setFromInfo(transitionInfo);
        animRunningInfo.setToInfo(transitionInfo);
        animRunningInfo.setConfig(transitionInfo.config);
        return animRunningInfo;
    }
}
