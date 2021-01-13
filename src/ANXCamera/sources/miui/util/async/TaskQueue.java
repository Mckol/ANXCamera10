package miui.util.async;

import android.util.Log;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import miui.util.async.Task;
import miui.util.concurrent.ConcurrentRingQueue;
import miui.util.concurrent.Queue;

/* access modifiers changed from: package-private */
public class TaskQueue implements Queue<Task<?>> {
    private final Queue<Task<?>> mHighQueue;
    private final Queue<Task<?>> mLowQueue;
    private final Queue<Task<?>> mNormalQueue;
    private final AtomicBoolean mPause = new AtomicBoolean(false);
    private final Semaphore mSemaphore = new Semaphore(0, true);
    private final TaskManager mTaskManager;

    /* access modifiers changed from: package-private */
    /* renamed from: miui.util.async.TaskQueue$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$miui$util$async$Task$Priority = new int[Task.Priority.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            $SwitchMap$miui$util$async$Task$Priority[Task.Priority.Low.ordinal()] = 1;
            $SwitchMap$miui$util$async$Task$Priority[Task.Priority.Normal.ordinal()] = 2;
            $SwitchMap$miui$util$async$Task$Priority[Task.Priority.High.ordinal()] = 3;
            try {
                $SwitchMap$miui$util$async$Task$Priority[Task.Priority.RealTime.ordinal()] = 4;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public TaskQueue(TaskManager taskManager, int i) {
        this.mTaskManager = taskManager;
        this.mHighQueue = new ConcurrentRingQueue(i, true, true);
        this.mNormalQueue = new ConcurrentRingQueue(i, true, true);
        this.mLowQueue = new ConcurrentRingQueue(i, true, true);
    }

    private Task<?> getTask() {
        Task<?> task = this.mHighQueue.get();
        if (task == null) {
            task = this.mNormalQueue.get();
        }
        return task == null ? this.mLowQueue.get() : task;
    }

    @Override // miui.util.concurrent.Queue
    public int clear() {
        int i = 0;
        while (this.mSemaphore.tryAcquire()) {
            if (getTask().getStatus() != Task.Status.Canceled) {
                i++;
            }
        }
        return i;
    }

    @Override // miui.util.concurrent.Queue
    public Task<?> get() {
        Task<?> task;
        if (this.mTaskManager.isShutdown()) {
            task = null;
            while (this.mSemaphore.tryAcquire()) {
                task = getTask();
                if (task.getStatus() != Task.Status.Canceled) {
                    break;
                }
            }
        } else {
            loop0:
            while (true) {
                task = null;
                while (true) {
                    if (task != null) {
                        break loop0;
                    }
                    try {
                        this.mSemaphore.acquire();
                        if (this.mPause.get()) {
                            synchronized (this.mPause) {
                                while (this.mPause.get()) {
                                    try {
                                        this.mPause.wait();
                                    } catch (InterruptedException unused) {
                                        this.mSemaphore.release();
                                        return null;
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                        task = getTask();
                        if (task.getStatus() == Task.Status.Canceled) {
                        }
                    } catch (InterruptedException unused2) {
                        return null;
                    }
                }
            }
        }
        return task;
    }

    @Override // miui.util.concurrent.Queue
    public int getCapacity() {
        return -1;
    }

    @Override // miui.util.concurrent.Queue
    public boolean isEmpty() {
        return this.mSemaphore.availablePermits() == 0;
    }

    public void pause() {
        synchronized (this.mPause) {
            this.mPause.set(true);
        }
    }

    public boolean put(Task<?> task) {
        boolean z;
        int i = AnonymousClass1.$SwitchMap$miui$util$async$Task$Priority[task.getPriority().ordinal()];
        if (i == 1) {
            z = this.mLowQueue.put(task);
        } else if (i == 2) {
            z = this.mNormalQueue.put(task);
        } else if (i != 3) {
            if (i == 4) {
                Log.e("async", "Realtime task must NOT be put in Queue");
            }
            z = false;
        } else {
            z = this.mHighQueue.put(task);
        }
        if (z) {
            task.setStatus(Task.Status.Queued, null);
            this.mSemaphore.release();
        }
        return z;
    }

    @Override // miui.util.concurrent.Queue
    public int remove(Queue.Predicate<Task<?>> predicate) {
        throw new RuntimeException("no support for this method");
    }

    public boolean remove(Task<?> task) {
        if (!this.mSemaphore.tryAcquire()) {
            return false;
        }
        boolean remove = this.mHighQueue.remove(task);
        if (!remove) {
            remove = this.mNormalQueue.remove(task);
        }
        if (!remove) {
            remove = this.mLowQueue.remove(task);
        }
        if (remove) {
            return remove;
        }
        this.mSemaphore.release();
        return remove;
    }

    public void resume() {
        synchronized (this.mPause) {
            this.mPause.set(false);
            this.mPause.notifyAll();
        }
    }
}
