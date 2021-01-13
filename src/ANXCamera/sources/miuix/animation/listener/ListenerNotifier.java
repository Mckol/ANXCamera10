package miuix.animation.listener;

import android.util.ArrayMap;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import miuix.animation.IAnimTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimConfigLink;
import miuix.animation.internal.AnimRunner;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.LogUtils;
import miuix.animation.utils.ObjectPool;

public class ListenerNotifier {
    private static final int NOTIFY_ANIM_CANCEL = 6;
    private static final int NOTIFY_ANIM_END = 5;
    private static final int NOTIFY_BEGIN = 0;
    private static final int NOTIFY_CANCEL_ALL = 7;
    private static final int NOTIFY_END_ALL = 8;
    private static final int NOTIFY_PROPERTY_BEGIN = 1;
    private static final int NOTIFY_PROPERTY_END = 4;
    private static final int NOTIFY_UPDATE = 2;
    private static final int NOTIFY_UPDATE_LIST = 3;
    private static BaseNotifier[] sNotifiers = {new BaseNotifier() {
        /* class miuix.animation.listener.ListenerNotifier.AnonymousClass1 */

        /* access modifiers changed from: package-private */
        @Override // miuix.animation.listener.ListenerNotifier.BaseNotifier
        public void doNotify(ListenerNode listenerNode, Object obj, Collection<UpdateInfo> collection) {
            listenerNode.listener.onBegin(obj);
        }
    }, new SingleNotifier() {
        /* class miuix.animation.listener.ListenerNotifier.AnonymousClass2 */

        /* access modifiers changed from: package-private */
        @Override // miuix.animation.listener.ListenerNotifier.SingleNotifier
        public void doNotify(ListenerNode listenerNode, Object obj, UpdateInfo updateInfo) {
            listenerNode.listener.onBegin(obj, updateInfo);
        }
    }, new SingleNotifier() {
        /* class miuix.animation.listener.ListenerNotifier.AnonymousClass3 */

        /* access modifiers changed from: package-private */
        @Override // miuix.animation.listener.ListenerNotifier.SingleNotifier
        public void doNotify(ListenerNode listenerNode, Object obj, UpdateInfo updateInfo) {
            if (!updateInfo.isCompleted || updateInfo.endTime == AnimRunner.getInst().getRunningTime()) {
                ListenerNotifier.notifyOnUpdate(listenerNode, obj, updateInfo);
            }
        }
    }, new BaseNotifier() {
        /* class miuix.animation.listener.ListenerNotifier.AnonymousClass4 */

        /* access modifiers changed from: package-private */
        @Override // miuix.animation.listener.ListenerNotifier.BaseNotifier
        public void doNotify(ListenerNode listenerNode, Object obj, Collection<UpdateInfo> collection) {
            listenerNode.listener.onUpdate(obj, collection);
        }
    }, new SingleNotifier() {
        /* class miuix.animation.listener.ListenerNotifier.AnonymousClass5 */

        /* access modifiers changed from: package-private */
        @Override // miuix.animation.listener.ListenerNotifier.SingleNotifier
        public void doNotify(ListenerNode listenerNode, Object obj, UpdateInfo updateInfo) {
            if (updateInfo.isCompleted && !updateInfo.isCanceled && !updateInfo.isEndByUser && updateInfo.endTime == AnimRunner.getInst().getRunningTime()) {
                listenerNode.listener.onComplete(obj, updateInfo);
            }
        }
    }, new SingleNotifier() {
        /* class miuix.animation.listener.ListenerNotifier.AnonymousClass6 */

        /* access modifiers changed from: package-private */
        @Override // miuix.animation.listener.ListenerNotifier.SingleNotifier
        public void doNotify(ListenerNode listenerNode, Object obj, UpdateInfo updateInfo) {
            listenerNode.listener.onComplete(obj, updateInfo);
        }
    }, new SingleNotifier() {
        /* class miuix.animation.listener.ListenerNotifier.AnonymousClass7 */

        /* access modifiers changed from: package-private */
        @Override // miuix.animation.listener.ListenerNotifier.SingleNotifier
        public void doNotify(ListenerNode listenerNode, Object obj, UpdateInfo updateInfo) {
            listenerNode.listener.onCancel(obj, updateInfo);
        }
    }, new BaseNotifier() {
        /* class miuix.animation.listener.ListenerNotifier.AnonymousClass8 */

        /* access modifiers changed from: package-private */
        @Override // miuix.animation.listener.ListenerNotifier.BaseNotifier
        public void doNotify(ListenerNode listenerNode, Object obj, Collection<UpdateInfo> collection) {
            listenerNode.listener.onCancel(obj);
            ListenerNotifier.removeNode(listenerNode, obj);
        }
    }, new BaseNotifier() {
        /* class miuix.animation.listener.ListenerNotifier.AnonymousClass9 */

        /* access modifiers changed from: package-private */
        @Override // miuix.animation.listener.ListenerNotifier.BaseNotifier
        public void doNotify(ListenerNode listenerNode, Object obj, Collection<UpdateInfo> collection) {
            listenerNode.listener.onComplete(obj);
            ListenerNotifier.removeNode(listenerNode, obj);
        }
    }};
    private PendingNotify mCurNotify;
    private List<TransitionListener> mExistListeners = new ArrayList();
    private ListenerNode mHead = new ListenerNode();
    private ArrayMap<Object, SparseArray<NotifyData>> mNotifyData = new ArrayMap<>();
    private ConcurrentLinkedQueue<PendingNotify> mPendingList = new ConcurrentLinkedQueue<>();
    private final IAnimTarget mTarget;

    /* access modifiers changed from: private */
    public static abstract class BaseNotifier {
        private List<UpdateInfo> mUpdateList;

        private BaseNotifier() {
            this.mUpdateList = new ArrayList();
        }

        /* access modifiers changed from: package-private */
        public abstract void doNotify(ListenerNode listenerNode, Object obj, Collection<UpdateInfo> collection);

        /* access modifiers changed from: package-private */
        public void notify(ListenerNode listenerNode, Object obj, Collection<UpdateInfo> collection) {
            if (CommonUtils.isArrayEmpty(listenerNode.relatedProperty)) {
                doNotify(listenerNode, obj, collection);
                return;
            }
            for (UpdateInfo updateInfo : collection) {
                FloatProperty[] floatPropertyArr = listenerNode.relatedProperty;
                if (floatPropertyArr == null || CommonUtils.inArray(floatPropertyArr, updateInfo.property)) {
                    this.mUpdateList.add(updateInfo);
                }
            }
            doNotify(listenerNode, obj, this.mUpdateList);
            this.mUpdateList.clear();
        }
    }

    /* access modifiers changed from: private */
    public static class ListenerNode {
        Object bindTag;
        TransitionListener listener;
        ListenerNode next;
        ListenerNode prev;
        FloatProperty[] relatedProperty;
        Object toTag;

        private ListenerNode() {
        }
    }

    /* access modifiers changed from: package-private */
    public static class NotifyData {
        ConcurrentLinkedQueue<UpdateInfo> updateList = new ConcurrentLinkedQueue<>();

        NotifyData() {
        }

        /* access modifiers changed from: package-private */
        public void addList(Collection<UpdateInfo> collection) {
            for (UpdateInfo updateInfo : collection) {
                addUpdate(updateInfo);
            }
        }

        /* access modifiers changed from: package-private */
        public void addUpdate(UpdateInfo updateInfo) {
            if (!this.updateList.contains(updateInfo)) {
                this.updateList.add(updateInfo);
            }
        }
    }

    /* access modifiers changed from: private */
    public static class PendingNotify {
        Object tag;
        int what = -1;

        private PendingNotify() {
        }

        /* access modifiers changed from: package-private */
        public boolean isSame(Object obj, int i) {
            Object obj2 = this.tag;
            return obj2 != null && this.what == i && obj2.equals(obj);
        }

        /* access modifiers changed from: package-private */
        public void setData(Object obj, int i) {
            this.tag = obj;
            this.what = i;
        }
    }

    private static abstract class SingleNotifier extends BaseNotifier {
        private SingleNotifier() {
            super();
        }

        /* access modifiers changed from: package-private */
        @Override // miuix.animation.listener.ListenerNotifier.BaseNotifier
        public void doNotify(ListenerNode listenerNode, Object obj, Collection<UpdateInfo> collection) {
            for (UpdateInfo updateInfo : collection) {
                doNotify(listenerNode, obj, updateInfo);
            }
        }

        /* access modifiers changed from: package-private */
        public abstract void doNotify(ListenerNode listenerNode, Object obj, UpdateInfo updateInfo);
    }

    public ListenerNotifier(IAnimTarget iAnimTarget) {
        this.mTarget = iAnimTarget;
    }

    private void addNode(Object obj, AnimConfig animConfig) {
        if (!animConfig.listeners.isEmpty()) {
            ListenerNode listenerNode = this.mHead;
            while (true) {
                ListenerNode listenerNode2 = listenerNode.next;
                if (listenerNode2 == null) {
                    break;
                }
                if (animConfig.listeners.contains(listenerNode2.listener)) {
                    this.mExistListeners.add(listenerNode2.listener);
                    updateNodeInfo(listenerNode2, obj, animConfig);
                }
                listenerNode = listenerNode2;
            }
            Iterator<TransitionListener> it = animConfig.listeners.iterator();
            while (it.hasNext()) {
                TransitionListener next = it.next();
                if (!this.mExistListeners.contains(next)) {
                    ListenerNode listenerNode3 = new ListenerNode();
                    listenerNode3.listener = next;
                    updateNodeInfo(listenerNode3, obj, animConfig);
                    listenerNode3.prev = listenerNode;
                    listenerNode.next = listenerNode3;
                    listenerNode = listenerNode3;
                }
            }
            this.mExistListeners.clear();
        }
    }

    private void executeNotify(PendingNotify pendingNotify) {
        if (pendingNotify != null) {
            notifyListener(pendingNotify.tag, pendingNotify.what, getNotifyData(pendingNotify.what, pendingNotify.tag));
            ObjectPool.release(pendingNotify);
        }
    }

    private void executePendingList() {
        while (!this.mPendingList.isEmpty()) {
            this.mCurNotify = this.mPendingList.poll();
            PendingNotify pendingNotify = this.mCurNotify;
            if (pendingNotify != null) {
                executeNotify(pendingNotify);
                this.mCurNotify = null;
            }
        }
    }

    private int getNodeCount() {
        ListenerNode listenerNode = this.mHead;
        int i = 0;
        while (true) {
            listenerNode = listenerNode.next;
            if (listenerNode == null) {
                return i;
            }
            i++;
        }
    }

    private NotifyData getNotifyData(int i, Object obj) {
        SparseArray<NotifyData> sparseArray = this.mNotifyData.get(obj);
        if (sparseArray == null) {
            sparseArray = new SparseArray<>();
            this.mNotifyData.put(obj, sparseArray);
        }
        NotifyData notifyData = sparseArray.get(i);
        if (notifyData != null) {
            return notifyData;
        }
        NotifyData notifyData2 = new NotifyData();
        sparseArray.put(i, notifyData2);
        return notifyData2;
    }

    private PendingNotify getPendingNotify(int i, Object obj) {
        PendingNotify pendingNotify = (PendingNotify) ObjectPool.acquire(PendingNotify.class, new Object[0]);
        pendingNotify.setData(obj, i);
        return pendingNotify;
    }

    private void notifyByType(int i, Object obj, UpdateInfo updateInfo, Collection<UpdateInfo> collection) {
        if (getNodeCount() != 0) {
            NotifyData notifyData = getNotifyData(i, obj);
            if (collection != null) {
                notifyData.addList(collection);
            } else if (updateInfo != null) {
                notifyData.addUpdate(updateInfo);
            }
            PendingNotify pendingNotify = this.mCurNotify;
            if (pendingNotify == null) {
                this.mCurNotify = getPendingNotify(i, obj);
                executeNotify(this.mCurNotify);
                this.mCurNotify = null;
                executePendingList();
            } else if (!pendingNotify.isSame(obj, i)) {
                Iterator<PendingNotify> it = this.mPendingList.iterator();
                while (it.hasNext()) {
                    if (it.next().isSame(obj, i)) {
                        return;
                    }
                }
                this.mPendingList.add(getPendingNotify(i, obj));
            }
        }
    }

    private void notifyListener(Object obj, int i, NotifyData notifyData) {
        ListenerNode listenerNode = this.mHead;
        while (true) {
            listenerNode = listenerNode.next;
            if (listenerNode != null) {
                Object obj2 = listenerNode.bindTag;
                if (obj2 == null || obj2.equals(obj)) {
                    sNotifiers[i].notify(listenerNode, obj, notifyData.updateList);
                }
            } else {
                ObjectPool.release(notifyData.updateList);
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public static void notifyOnUpdate(ListenerNode listenerNode, Object obj, UpdateInfo updateInfo) {
        TransitionListener transitionListener = listenerNode.listener;
        transitionListener.onUpdate(obj, updateInfo.property, updateInfo.getFloatValue(), updateInfo.isCompleted);
        FloatProperty floatProperty = updateInfo.property;
        if (floatProperty instanceof IIntValueProperty) {
            transitionListener.onUpdate(obj, (IIntValueProperty) floatProperty, updateInfo.getIntValue(), updateInfo.velocity, updateInfo.isCompleted);
        } else {
            transitionListener.onUpdate(obj, floatProperty, updateInfo.getFloatValue(), updateInfo.velocity, updateInfo.isCompleted);
        }
    }

    /* access modifiers changed from: private */
    public static void removeNode(ListenerNode listenerNode, Object obj) {
        if (listenerNode.toTag.equals(obj)) {
            ListenerNode listenerNode2 = listenerNode.prev;
            listenerNode2.next = listenerNode.next;
            ListenerNode listenerNode3 = listenerNode.next;
            if (listenerNode3 != null) {
                listenerNode3.prev = listenerNode2;
            }
        }
    }

    private void updateNodeInfo(ListenerNode listenerNode, Object obj, AnimConfig animConfig) {
        listenerNode.toTag = obj;
        listenerNode.bindTag = animConfig.tag;
        listenerNode.relatedProperty = animConfig.relatedProperty;
    }

    public void clear() {
        ListenerNode listenerNode = this.mHead.next;
        if (listenerNode != null) {
            listenerNode.prev = null;
        }
        this.mHead.next = null;
        this.mNotifyData.clear();
        this.mPendingList.clear();
        this.mCurNotify = null;
    }

    public void notifyAnimCancel(Object obj, UpdateInfo updateInfo) {
        notifyByType(6, obj, updateInfo, null);
    }

    public void notifyAnimEnd(Object obj, UpdateInfo updateInfo) {
        notifyByType(5, obj, updateInfo, null);
    }

    public void notifyBegin(Object obj) {
        notifyByType(0, obj, null, null);
    }

    public void notifyCancelAll(Object obj) {
        notifyByType(7, obj, null, null);
    }

    public void notifyEndAll(Object obj) {
        notifyByType(8, obj, null, null);
    }

    public void notifyPropertyBegin(Object obj, UpdateInfo updateInfo) {
        notifyByType(1, obj, updateInfo, null);
    }

    public void notifyPropertyEnd(Object obj, List<UpdateInfo> list) {
        notifyByType(4, obj, null, list);
    }

    public void notifyUpdate(Object obj, List<UpdateInfo> list) {
        notifyByType(2, obj, null, list);
    }

    public void notifyUpdateList(Object obj, List<UpdateInfo> list) {
        notifyByType(3, obj, null, list);
    }

    public boolean setListeners(Object obj, AnimConfigLink animConfigLink) {
        for (AnimConfig animConfig : animConfigLink.configList) {
            addNode(obj, animConfig);
        }
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("setListeners for " + this.mTarget.getTargetObject(), "toTag = " + obj, "listeners = " + getNodeCount());
        }
        return this.mHead.next != null;
    }
}
