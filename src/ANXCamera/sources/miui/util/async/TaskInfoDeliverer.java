package miui.util.async;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import miui.util.AppConstants;
import miui.util.Pools;
import miui.util.async.Task;
import miui.util.concurrent.ConcurrentRingQueue;
import miui.util.concurrent.Queue;

/* access modifiers changed from: package-private */
public class TaskInfoDeliverer {
    private static final Pools.Pool<TaskDeliveryInfo> TASK_DELIVERY_INFO_POOL = Pools.createSimplePool(new Pools.Manager<TaskDeliveryInfo>() {
        /* class miui.util.async.TaskInfoDeliverer.AnonymousClass1 */

        @Override // miui.util.Pools.Manager
        public TaskDeliveryInfo createInstance() {
            return new TaskDeliveryInfo();
        }

        public void onRelease(TaskDeliveryInfo taskDeliveryInfo) {
            taskDeliveryInfo.clear();
        }
    }, 8);
    private DeliverHandler mHandler = new DeliverHandler(AppConstants.getCurrentApplication().getMainLooper());
    private ConcurrentRingQueue<TaskDeliveryInfo> mTaskDeliveryInfoQueue = new ConcurrentRingQueue<>(20, true, true);
    private TaskManager mTaskManager;

    /* access modifiers changed from: private */
    public static class DeliverHandler extends Handler {
        static final int CONTINUE = 0;

        public DeliverHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            ((TaskInfoDeliverer) message.obj).processAllDelivery();
            message.obj = null;
        }

        public void post(TaskInfoDeliverer taskInfoDeliverer) {
            obtainMessage(0, taskInfoDeliverer).sendToTarget();
        }
    }

    /* access modifiers changed from: package-private */
    public static class TaskDeliveryInfo {
        public Task.Delivery delivery;
        public Object info;
        public Task<?> task;

        TaskDeliveryInfo() {
        }

        public void clear() {
            this.task = null;
            this.delivery = null;
            this.info = null;
        }
    }

    public TaskInfoDeliverer(TaskManager taskManager) {
        this.mTaskManager = taskManager;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void processAllDelivery() {
        if (Looper.myLooper() == this.mHandler.getLooper()) {
            while (!this.mTaskDeliveryInfoQueue.isEmpty()) {
                TaskDeliveryInfo taskDeliveryInfo = this.mTaskDeliveryInfoQueue.get();
                if (taskDeliveryInfo != null) {
                    taskDeliveryInfo.task.deliver(this.mTaskManager, taskDeliveryInfo.delivery, taskDeliveryInfo.info);
                    TASK_DELIVERY_INFO_POOL.release(taskDeliveryInfo);
                }
            }
            return;
        }
        this.mHandler.post(this);
    }

    public void postDeliver(final Task<?> task, Task.Delivery delivery, Object obj) {
        TaskDeliveryInfo acquire = TASK_DELIVERY_INFO_POOL.acquire();
        acquire.task = task;
        acquire.delivery = delivery;
        acquire.info = obj;
        if (delivery == Task.Delivery.Result && obj == null) {
            this.mTaskDeliveryInfoQueue.remove(new Queue.Predicate<TaskDeliveryInfo>() {
                /* class miui.util.async.TaskInfoDeliverer.AnonymousClass3 */

                public boolean apply(TaskDeliveryInfo taskDeliveryInfo) {
                    return taskDeliveryInfo != null && taskDeliveryInfo.task == task;
                }
            });
        }
        this.mTaskDeliveryInfoQueue.put(acquire);
        processAllDelivery();
    }

    public void setCallbackThread(boolean z) {
        Looper mainLooper = AppConstants.getCurrentApplication().getMainLooper();
        if (z && this.mHandler.getLooper() != mainLooper) {
            this.mHandler.getLooper().quit();
            this.mHandler = new DeliverHandler(mainLooper);
        } else if (!z && this.mHandler.getLooper() == mainLooper) {
            AnonymousClass2 r3 = new Thread() {
                /* class miui.util.async.TaskInfoDeliverer.AnonymousClass2 */

                public void run() {
                    Looper.prepare();
                    TaskInfoDeliverer.this.mHandler = new DeliverHandler(Looper.myLooper());
                    Looper.loop();
                }
            };
            r3.setName("TaskInfoDeliverer-Callback");
            r3.start();
        }
    }
}
