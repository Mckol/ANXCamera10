package miui.animation.internal;

import android.util.ArrayMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import miui.animation.IAnimTarget;
import miui.animation.base.AnimConfigLink;
import miui.animation.controller.AnimState;
import miui.animation.listener.ListenerNotifier;
import miui.animation.listener.UpdateInfo;
import miui.animation.property.FloatProperty;
import miui.animation.property.IIntValueProperty;
import miui.animation.utils.CommonUtils;
import miui.animation.utils.LogUtils;
import miui.animation.utils.ObjectPool;

public class AnimTask {
    private List<AnimRunningInfo> mNotRunList = new ArrayList();
    private ListenerNotifier mNotifier;
    private List<TransitionInfo> mQueueList = new ArrayList();
    private ConcurrentLinkedQueue<AnimRunningInfo> mRunningList = new ConcurrentLinkedQueue<>();
    private ListenerNotifier mSetToNotifier;
    private IAnimTarget mTarget;
    private Map<Object, List<UpdateInfo>> mUpdateMap = new ArrayMap();

    public AnimTask(IAnimTarget iAnimTarget) {
        this.mTarget = iAnimTarget;
        this.mNotifier = new ListenerNotifier(iAnimTarget);
    }

    private void cancelAnim(AnimRunningInfo animRunningInfo) {
        animRunningInfo.pending = null;
        animRunningInfo.stop();
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("cancelAnim, cancel " + animRunningInfo.property.getName(), new Object[0]);
        }
    }

    private boolean checkAndNotifyEnd(Object obj, boolean z) {
        if (isTagRunningIn(this.mRunningList, obj) || isTagRunningIn(this.mNotRunList, obj)) {
            return false;
        }
        if (z) {
            this.mNotifier.notifyCancelAll(obj);
            return true;
        }
        this.mNotifier.notifyEndAll(obj);
        return true;
    }

    private void collectUpdateInfo(Collection<AnimRunningInfo> collection) {
        UpdateInfo updateToDate;
        for (AnimRunningInfo animRunningInfo : collection) {
            if (collection.isEmpty()) {
                return;
            }
            if (!(animRunningInfo.status == 0 || (updateToDate = animRunningInfo.updateToDate()) == null)) {
                List<UpdateInfo> updateList = getUpdateList(animRunningInfo.toTag);
                if (!updateList.contains(updateToDate)) {
                    updateList.add(updateToDate);
                }
                if (updateToDate.isCompleted && LogUtils.isLogEnabled()) {
                    LogUtils.debug("anim end, tag = " + animRunningInfo.toTag, "property = " + animRunningInfo.property.getName());
                }
            }
        }
    }

    private void endQueuedTransitions(boolean z, FloatProperty... floatPropertyArr) {
        ArrayList arrayList = new ArrayList();
        for (TransitionInfo transitionInfo : this.mQueueList) {
            if (endTransitionInfo(transitionInfo, z, floatPropertyArr)) {
                arrayList.add(transitionInfo);
            }
        }
        this.mQueueList.removeAll(arrayList);
    }

    private boolean endTransitionInfo(TransitionInfo transitionInfo, boolean z, FloatProperty... floatPropertyArr) {
        for (FloatProperty floatProperty : transitionInfo.toPropValues.keySet()) {
            if (floatPropertyArr.length <= 0 || !isNotIn(floatProperty, floatPropertyArr)) {
                if (z) {
                    setTargetValue(transitionInfo.target, floatProperty, transitionInfo.toPropValues.get(floatProperty));
                }
                transitionInfo.toPropValues.remove(floatProperty);
            }
        }
        return transitionInfo.toPropValues.keySet().isEmpty();
    }

    private void findAndHandleSameAnim(AnimRunningInfo animRunningInfo) {
        Iterator<AnimRunningInfo> it = this.mRunningList.iterator();
        while (it.hasNext()) {
            AnimRunningInfo next = it.next();
            if (next != animRunningInfo && !CommonUtils.hasFlags(next.flags, 2) && next.property.equals(animRunningInfo.property)) {
                handleSameAnim(next, animRunningInfo);
                return;
            }
        }
    }

    private void findByStatus(int i, List<AnimRunningInfo> list) {
        Iterator<AnimRunningInfo> it = this.mRunningList.iterator();
        while (it.hasNext()) {
            AnimRunningInfo next = it.next();
            if (next.status == i) {
                list.add(next);
            }
        }
    }

    private List<AnimRunningInfo> getTotalList() {
        ArrayList arrayList = new ArrayList();
        if (this.mNotRunList.isEmpty()) {
            arrayList.addAll(this.mRunningList);
        } else {
            arrayList.addAll(this.mRunningList);
            arrayList.addAll(this.mNotRunList);
        }
        return arrayList;
    }

    private List<UpdateInfo> getUpdateList(Object obj) {
        List<UpdateInfo> list = this.mUpdateMap.get(obj);
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        this.mUpdateMap.put(obj, arrayList);
        return arrayList;
    }

    private void handleBegin(IAnimTarget iAnimTarget, long j, long j2) {
        List<AnimRunningInfo> list = (List) ObjectPool.acquire(ArrayList.class, new Object[0]);
        List list2 = (List) ObjectPool.acquire(ArrayList.class, new Object[0]);
        try {
            findByStatus(0, list);
            if (!list.isEmpty()) {
                for (AnimRunningInfo animRunningInfo : list) {
                    if (j - animRunningInfo.initTime >= animRunningInfo.config.getDelay(animRunningInfo.toTag, animRunningInfo.property)) {
                        Object obj = animRunningInfo.toTag;
                        if (!list2.contains(obj)) {
                            list2.add(obj);
                            this.mNotifier.notifyBegin(animRunningInfo.toTag);
                        }
                        animRunningInfo.begin(iAnimTarget, j);
                        if (!stopSameRunningAnim(animRunningInfo)) {
                            this.mNotifier.notifyPropertyBegin(animRunningInfo.toTag, animRunningInfo.updateToDate());
                        }
                    }
                }
                updateAndStop(list, j, j2);
                notifyUpdateAndEnd(list);
                ObjectPool.release(list2);
                ObjectPool.release(list);
            }
        } finally {
            ObjectPool.release(list2);
            ObjectPool.release(list);
        }
    }

    private void handlePendingAnim(long j) {
        List<AnimRunningInfo> list = (List) ObjectPool.acquire(ArrayList.class, new Object[0]);
        try {
            findByStatus(2, list);
            if (!list.isEmpty()) {
                for (AnimRunningInfo animRunningInfo : list) {
                    long minDuration = animRunningInfo.config.getMinDuration(animRunningInfo.toTag, animRunningInfo.property);
                    long runningTime = animRunningInfo.getRunningTime();
                    if (runningTime >= minDuration || minDuration - runningTime < j) {
                        animRunningInfo.stop();
                    }
                }
                ObjectPool.release(list);
            }
        } finally {
            ObjectPool.release(list);
        }
    }

    private void handleQueue(long j) {
        if (this.mRunningList.isEmpty() && !this.mQueueList.isEmpty()) {
            startTransition(j, this.mQueueList.remove(0));
        }
    }

    private void handleSameAnim(AnimRunningInfo animRunningInfo, AnimRunningInfo animRunningInfo2) {
        int i = animRunningInfo.status;
        if (i == 0) {
            animRunningInfo.status = 3;
        } else if (i == 1) {
            long minDuration = animRunningInfo.config.getMinDuration(animRunningInfo.toTag, animRunningInfo.property);
            if (animRunningInfo.getRunningTime() < minDuration) {
                if (LogUtils.isLogEnabled()) {
                    LogUtils.debug("handleSameAnim", animRunningInfo.property.getName(), "prev.config.minDuration = " + minDuration, "prev.runningTime = " + animRunningInfo.getRunningTime(), "pending current info");
                }
                animRunningInfo.setPending(animRunningInfo2);
                return;
            }
            animRunningInfo.stop();
        } else if (i == 2) {
            animRunningInfo.pending.stop();
            animRunningInfo.pending = animRunningInfo2;
        }
    }

    private boolean isAnimEnd(AnimRunningInfo animRunningInfo, long j) {
        return animRunningInfo.isAnimEnd(j);
    }

    private boolean isNotIn(FloatProperty floatProperty, FloatProperty... floatPropertyArr) {
        for (FloatProperty floatProperty2 : floatPropertyArr) {
            if (floatProperty.equals(floatProperty2)) {
                return false;
            }
        }
        return true;
    }

    private boolean isRunningTag(AnimRunningInfo animRunningInfo, Object obj) {
        return animRunningInfo.status != 3 && animRunningInfo.toTag.equals(obj);
    }

    private boolean isTagRunningIn(Collection<AnimRunningInfo> collection, Object obj) {
        for (AnimRunningInfo animRunningInfo : collection) {
            if (isRunningTag(animRunningInfo, obj)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTransGoing(long j) {
        Iterator<AnimRunningInfo> it = this.mRunningList.iterator();
        while (it.hasNext()) {
            if (it.next().transId == j) {
                return true;
            }
        }
        return false;
    }

    private void notifyForUpdateAndEnd(ListenerNotifier listenerNotifier, Object obj, List<UpdateInfo> list) {
        listenerNotifier.notifyUpdate(obj, list);
        listenerNotifier.notifyUpdateList(obj, list);
        listenerNotifier.notifyPropertyEnd(obj, list);
    }

    private void notifyUpdateAndEnd(Collection<AnimRunningInfo> collection) {
        collectUpdateInfo(collection);
        ArrayList<Object> arrayList = new ArrayList();
        removeUpdateInfoOfStoppedTrans(arrayList);
        for (Map.Entry<Object, List<UpdateInfo>> entry : this.mUpdateMap.entrySet()) {
            Object key = entry.getKey();
            List<UpdateInfo> value = entry.getValue();
            if (value.isEmpty()) {
                arrayList.add(key);
            } else {
                notifyForUpdateAndEnd(this.mNotifier, key, value);
                if (checkAndNotifyEnd(key, false)) {
                    arrayList.add(key);
                }
            }
        }
        for (Object obj : arrayList) {
            this.mUpdateMap.remove(obj);
        }
    }

    private void removeStoppedAnim() {
        Iterator<AnimRunningInfo> it = this.mRunningList.iterator();
        while (it.hasNext()) {
            AnimRunningInfo next = it.next();
            if (next.status == 3) {
                this.mRunningList.remove(next);
            }
        }
    }

    private void removeUpdateInfoOfStoppedTrans(List<Object> list) {
        for (List<UpdateInfo> list2 : this.mUpdateMap.values()) {
            list.clear();
            for (UpdateInfo updateInfo : list2) {
                if (!isTransGoing(updateInfo.transId)) {
                    list.add(updateInfo);
                }
            }
            list2.removeAll(list);
        }
    }

    private void restoreAnim() {
        this.mRunningList.addAll(this.mNotRunList);
        this.mNotRunList.clear();
    }

    private void saveNotAllowedAnim(long... jArr) {
        long j = jArr.length > 0 ? jArr[0] : 0;
        if (j > 0) {
            Iterator<AnimRunningInfo> it = this.mRunningList.iterator();
            while (it.hasNext()) {
                AnimRunningInfo next = it.next();
                if (!CommonUtils.hasFlags(next.flags, j)) {
                    this.mNotRunList.add(next);
                }
            }
            this.mRunningList.removeAll(this.mNotRunList);
        }
    }

    private void setTargetValue(IAnimTarget iAnimTarget, FloatProperty floatProperty, Number number) {
        if (floatProperty instanceof IIntValueProperty) {
            int intValue = number.intValue();
            if (intValue != Integer.MAX_VALUE) {
                iAnimTarget.setIntValue((IIntValueProperty) floatProperty, intValue);
                return;
            }
            return;
        }
        float value = AnimValueUtils.getValue(iAnimTarget, floatProperty, number.floatValue());
        if (value != Float.MAX_VALUE) {
            iAnimTarget.setValue(floatProperty, value);
        }
    }

    private void startTransition(long j, TransitionInfo transitionInfo) {
        ArrayList<FloatProperty> arrayList = new ArrayList();
        Iterator<AnimRunningInfo> it = this.mRunningList.iterator();
        while (it.hasNext()) {
            AnimRunningInfo next = it.next();
            if (next.isRunning() && next.toTag.equals(transitionInfo.toTag) && transitionInfo.toPropValues.get(next.property) != null && transitionInfo.config.getDelay(next.toTag, next.property) == 0) {
                tryUpdate(next, transitionInfo, arrayList, j);
            }
        }
        for (FloatProperty floatProperty : arrayList) {
            transitionInfo.toPropValues.remove(floatProperty);
        }
        if (!transitionInfo.toPropValues.isEmpty()) {
            this.mNotifier.setListeners(transitionInfo.toTag, transitionInfo.config);
            for (AnimRunningInfo animRunningInfo : AnimCompositor.createAnimInfo(this.mTarget, transitionInfo).values()) {
                animRunningInfo.initTime = j;
                if (animRunningInfo.config.getDelay(animRunningInfo.toTag, animRunningInfo.property) > 0) {
                    animRunningInfo.flags |= 2;
                }
                if (!CommonUtils.hasFlags(animRunningInfo.flags, 2)) {
                    findAndHandleSameAnim(animRunningInfo);
                }
                this.mRunningList.add(animRunningInfo);
            }
        } else if (LogUtils.isLogEnabled()) {
            LogUtils.debug("startTransition, trans.toPropValues.isEmpty", "target = " + this.mTarget.getTargetObject(), "trans.tag = " + transitionInfo.toTag);
        }
    }

    private boolean stopSameRunningAnim(AnimRunningInfo animRunningInfo) {
        Iterator<AnimRunningInfo> it = this.mRunningList.iterator();
        boolean z = false;
        while (it.hasNext()) {
            AnimRunningInfo next = it.next();
            if (next != animRunningInfo && next.property.equals(animRunningInfo.property) && next.isRunning()) {
                z = true;
                next.stop();
                this.mRunningList.remove(next);
            }
        }
        return z;
    }

    private void tryUpdate(AnimRunningInfo animRunningInfo, TransitionInfo transitionInfo, List<FloatProperty> list, long j) {
        for (FloatProperty floatProperty : transitionInfo.toPropValues.keySet()) {
            if (animRunningInfo.property.equals(floatProperty)) {
                animRunningInfo.update(transitionInfo, j);
                list.add(floatProperty);
                return;
            }
        }
    }

    private void updateAndStop(Collection<AnimRunningInfo> collection, long j, long j2) {
        for (AnimRunningInfo animRunningInfo : collection) {
            if (animRunningInfo.isRunning()) {
                animRunningInfo.run(j2);
                if (isAnimEnd(animRunningInfo, j)) {
                    animRunningInfo.stop(!animRunningInfo.updateToDate().isCanceled);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void addTransition(long j, TransitionInfo transitionInfo) {
        if (CommonUtils.hasFlags(transitionInfo.config.getFlags(null, null), 1)) {
            this.mQueueList.add(transitionInfo);
        } else {
            startTransition(j, transitionInfo);
        }
    }

    public void cancel(FloatProperty... floatPropertyArr) {
        ArrayList<Object> arrayList = new ArrayList();
        boolean z = false;
        for (AnimRunningInfo animRunningInfo : getTotalList()) {
            if (animRunningInfo.isRunning() && (floatPropertyArr.length <= 0 || !isNotIn(animRunningInfo.property, floatPropertyArr))) {
                cancelAnim(animRunningInfo);
                Object obj = animRunningInfo.toTag;
                if (!arrayList.contains(obj)) {
                    arrayList.add(obj);
                }
                this.mNotifier.notifyAnimCancel(obj, animRunningInfo.updateToDate());
                z = true;
            }
        }
        if (z) {
            removeStoppedAnim();
            for (Object obj2 : arrayList) {
                checkAndNotifyEnd(obj2, true);
            }
            arrayList.clear();
        }
        if (floatPropertyArr.length > 0) {
            endQueuedTransitions(false, floatPropertyArr);
        }
    }

    /* access modifiers changed from: package-private */
    public void end(FloatProperty... floatPropertyArr) {
        ArrayList<Object> arrayList = new ArrayList();
        boolean z = !this.mRunningList.isEmpty();
        for (AnimRunningInfo animRunningInfo : getTotalList()) {
            if (floatPropertyArr.length <= 0 || !isNotIn(animRunningInfo.property, floatPropertyArr)) {
                if (animRunningInfo.isRunning()) {
                    animRunningInfo.stop(true);
                    animRunningInfo.updateToDate().isEndByUser = true;
                } else {
                    setTargetValue(animRunningInfo.target, animRunningInfo.property, animRunningInfo.toValue);
                }
                if (!arrayList.contains(animRunningInfo.toTag)) {
                    arrayList.add(animRunningInfo.toTag);
                }
                this.mNotifier.notifyAnimEnd(animRunningInfo.toTag, animRunningInfo.updateToDate());
            }
        }
        endQueuedTransitions(true, floatPropertyArr);
        removeStoppedAnim();
        if (z) {
            for (Object obj : arrayList) {
                checkAndNotifyEnd(obj, false);
            }
        }
        arrayList.clear();
    }

    public boolean isFinished() {
        return this.mRunningList.isEmpty();
    }

    public boolean isRunning(FloatProperty floatProperty) {
        Iterator<AnimRunningInfo> it = this.mRunningList.iterator();
        while (it.hasNext()) {
            if (it.next().property.equals(floatProperty)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValid() {
        return !this.mRunningList.isEmpty() || !this.mQueueList.isEmpty();
    }

    public void run(long j, long j2, long... jArr) {
        saveNotAllowedAnim(jArr);
        if (!this.mRunningList.isEmpty()) {
            updateAndStop(this.mRunningList, j, j2);
            handlePendingAnim(j2);
            notifyUpdateAndEnd(this.mRunningList);
            removeStoppedAnim();
            handleBegin(this.mTarget, j, j2);
            handleQueue(j);
        }
        restoreAnim();
    }

    public void setToNotify(AnimState animState, AnimConfigLink animConfigLink) {
        Object tag = animState.getTag();
        if (this.mSetToNotifier == null) {
            this.mSetToNotifier = new ListenerNotifier(this.mTarget);
        }
        if (this.mSetToNotifier.setListeners(tag, animConfigLink)) {
            this.mSetToNotifier.notifyBegin(tag);
            ArrayList<UpdateInfo> arrayList = new ArrayList();
            for (FloatProperty floatProperty : animState.keySet()) {
                UpdateInfo updateInfo = new UpdateInfo();
                updateInfo.property = floatProperty;
                updateInfo.velocity = (float) this.mTarget.getVelocity(floatProperty);
                updateInfo.setValue(Float.valueOf(floatProperty instanceof IIntValueProperty ? (float) this.mTarget.getIntValue((IIntValueProperty) floatProperty) : this.mTarget.getValue(floatProperty)));
                arrayList.add(updateInfo);
                updateInfo.setComplete(true);
            }
            for (UpdateInfo updateInfo2 : arrayList) {
                this.mSetToNotifier.notifyPropertyBegin(tag, updateInfo2);
            }
            notifyForUpdateAndEnd(this.mSetToNotifier, tag, arrayList);
            this.mSetToNotifier.notifyEndAll(tag);
        }
    }
}
