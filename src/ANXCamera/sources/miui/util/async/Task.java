package miui.util.async;

import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Task<T> {
    private ArrayList<Listener> mListeners;
    private Priority mPriority;
    private volatile Status mStatus;
    private final AtomicBoolean mSuccessorSync;
    private HashSet<Task<?>> mSuccessorTasks;
    private WeakReference<TaskManager> mTaskManager;
    private Thread mThread;

    /* access modifiers changed from: package-private */
    /* renamed from: miui.util.async.Task$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$miui$util$async$Task$Delivery = new int[Delivery.values().length];
        static final /* synthetic */ int[] $SwitchMap$miui$util$async$Task$Priority = new int[Priority.values().length];
        static final /* synthetic */ int[] $SwitchMap$miui$util$async$Task$Status = new int[Status.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(26:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|(2:13|14)|15|17|18|19|20|21|22|(2:23|24)|25|27|28|29|30|31|32|33|34|(3:35|36|38)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(29:0|1|2|3|(2:5|6)|7|(2:9|10)|11|13|14|15|17|18|19|20|21|22|23|24|25|27|28|29|30|31|32|33|34|(3:35|36|38)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(32:0|1|2|3|5|6|7|(2:9|10)|11|13|14|15|17|18|19|20|21|22|23|24|25|27|28|29|30|31|32|33|34|35|36|38) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0048 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0052 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x005c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0079 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x0083 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x008d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x0097 */
        static {
            try {
                $SwitchMap$miui$util$async$Task$Delivery[Delivery.Prepare.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$miui$util$async$Task$Delivery[Delivery.Finalize.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$miui$util$async$Task$Delivery[Delivery.Result.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$miui$util$async$Task$Delivery[Delivery.Progress.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $SwitchMap$miui$util$async$Task$Priority[Priority.Low.ordinal()] = 1;
            $SwitchMap$miui$util$async$Task$Priority[Priority.Normal.ordinal()] = 2;
            $SwitchMap$miui$util$async$Task$Priority[Priority.High.ordinal()] = 3;
            try {
                $SwitchMap$miui$util$async$Task$Priority[Priority.RealTime.ordinal()] = 4;
            } catch (NoSuchFieldError unused5) {
            }
            $SwitchMap$miui$util$async$Task$Status[Status.Queued.ordinal()] = 1;
            $SwitchMap$miui$util$async$Task$Status[Status.Canceled.ordinal()] = 2;
            $SwitchMap$miui$util$async$Task$Status[Status.Executing.ordinal()] = 3;
            $SwitchMap$miui$util$async$Task$Status[Status.Done.ordinal()] = 4;
            try {
                $SwitchMap$miui$util$async$Task$Status[Status.New.ordinal()] = 5;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public enum Delivery {
        Prepare,
        Result,
        Progress,
        Finalize
    }

    public interface Listener {
        void onCanceled(TaskManager taskManager, Task<?> task);

        void onException(TaskManager taskManager, Task<?> task, Exception exc);

        void onFinalize(TaskManager taskManager, Task<?> task);

        void onPrepare(TaskManager taskManager, Task<?> task);

        void onProgress(TaskManager taskManager, Task<?> task, int i, int i2);

        Object onResult(TaskManager taskManager, Task<?> task, Object obj);
    }

    public enum Priority {
        Low,
        Normal,
        High,
        RealTime
    }

    public enum Status {
        New,
        Queued,
        Executing,
        Done,
        Canceled
    }

    public Task() {
        this(Priority.Normal);
    }

    public Task(Priority priority) {
        this.mSuccessorSync = new AtomicBoolean(false);
        this.mStatus = Status.New;
        this.mPriority = priority;
    }

    private boolean canRestart() {
        if (this.mStatus == Status.Executing) {
            return false;
        }
        synchronized (this.mSuccessorSync) {
            if (this.mStatus != Status.Done || this.mSuccessorSync.get()) {
                if (this.mSuccessorTasks != null) {
                    Iterator<Task<?>> it = this.mSuccessorTasks.iterator();
                    while (it.hasNext()) {
                        if (!it.next().canRestart()) {
                            return false;
                        }
                    }
                }
                return true;
            }
            Log.e("async", "Task " + this + " is DONE but successor not done yet");
            return false;
        }
    }

    private void enqueueSuccessorTasks(TaskManager taskManager) {
        synchronized (this.mSuccessorSync) {
            if (this.mSuccessorTasks != null) {
                Iterator<Task<?>> it = this.mSuccessorTasks.iterator();
                while (it.hasNext()) {
                    taskManager.add(it.next());
                }
            }
            this.mSuccessorSync.set(true);
        }
    }

    private final boolean isCanceled() {
        return this.mStatus == Status.Canceled;
    }

    private void onCanceledInternal(TaskManager taskManager) {
        ArrayList<Listener> arrayList = this.mListeners;
        if (arrayList != null) {
            Iterator<Listener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onCanceled(taskManager, this);
            }
        }
        onCanceled(taskManager);
    }

    private void onExceptionInternal(TaskManager taskManager, Exception exc) {
        ArrayList<Listener> arrayList = this.mListeners;
        if (arrayList != null) {
            Iterator<Listener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onException(taskManager, this, exc);
            }
        }
        onException(taskManager, exc);
    }

    private void onFinalizeInternal(TaskManager taskManager) {
        ArrayList<Listener> arrayList = this.mListeners;
        if (arrayList != null) {
            Iterator<Listener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onFinalize(taskManager, this);
            }
        }
        onFinalize(taskManager);
        this.mTaskManager.clear();
    }

    private void onPrepareInternal(TaskManager taskManager) {
        ArrayList<Listener> arrayList = this.mListeners;
        if (arrayList != null) {
            Iterator<Listener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onPrepare(taskManager, this);
            }
        }
        onPrepare(taskManager);
    }

    private void onProgressInternal(TaskManager taskManager, int i, int i2) {
        ArrayList<Listener> arrayList = this.mListeners;
        if (arrayList != null) {
            Iterator<Listener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onProgress(taskManager, this, i, i2);
            }
        }
        onProgress(taskManager, i, i2);
    }

    private void onResultInternal(TaskManager taskManager, T t) {
        ArrayList<Listener> arrayList = this.mListeners;
        if (arrayList != null) {
            Iterator<Listener> it = arrayList.iterator();
            while (it.hasNext()) {
                t = (T) it.next().onResult(taskManager, this, t);
            }
        }
        onResult(taskManager, t);
    }

    private void postDelivery(Delivery delivery, Object obj) {
        TaskManager taskManager = this.mTaskManager.get();
        if (taskManager != null) {
            taskManager.postDelivery(this, delivery, obj);
            return;
        }
        Log.e("async", "Task has delivery " + delivery + ", but has no task manager");
    }

    public final Task<T> addListener(Listener listener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        this.mListeners.add(listener);
        return this;
    }

    public final void cancel() {
        TaskManager taskManager;
        if (this.mStatus != Status.Done) {
            if (this.mStatus == Status.Queued && (taskManager = this.mTaskManager.get()) != null) {
                taskManager.remove(this);
            }
            setStatus(Status.Canceled, null);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: java.lang.Object */
    /* JADX WARN: Multi-variable type inference failed */
    /* access modifiers changed from: package-private */
    public final void deliver(TaskManager taskManager, Delivery delivery, Object obj) {
        int i = AnonymousClass1.$SwitchMap$miui$util$async$Task$Delivery[delivery.ordinal()];
        if (i == 1) {
            onPrepareInternal(taskManager);
        } else if (i == 2) {
            onFinalizeInternal(taskManager);
        } else if (i != 3) {
            if (i == 4) {
                int[] iArr = (int[]) obj;
                onProgressInternal(taskManager, iArr[0], iArr[1]);
            }
        } else if (obj == 0 || isCanceled()) {
            onCanceledInternal(taskManager);
        } else if (obj instanceof TaskExecutingException) {
            onExceptionInternal(taskManager, (Exception) ((TaskExecutingException) obj).getCause());
        } else {
            try {
                onResultInternal(taskManager, obj);
                enqueueSuccessorTasks(taskManager);
            } catch (ClassCastException e2) {
                Log.e("async", "Task " + this + " return result cannot cast to expectation class");
                onExceptionInternal(taskManager, e2);
            }
        }
    }

    public final void depends(Task<T> task) {
        synchronized (this.mSuccessorSync) {
            if (this.mSuccessorSync.get()) {
                if (task.mSuccessorTasks == null) {
                    task.mSuccessorTasks = new HashSet<>();
                }
                task.mSuccessorTasks.add(this);
            } else {
                TaskManager taskManager = this.mTaskManager.get();
                if (taskManager != null) {
                    taskManager.add(this);
                } else {
                    Log.e("async", "Task " + this + " is done but has no task manager to execute task " + task);
                }
            }
        }
    }

    public abstract T doLoad() throws Exception;

    public String getDescription() {
        return null;
    }

    public final Priority getPriority() {
        return this.mPriority;
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final boolean isRunning() {
        return this.mStatus == Status.Queued || this.mStatus == Status.Executing;
    }

    public void onCanceled(TaskManager taskManager) {
    }

    public void onException(TaskManager taskManager, Exception exc) {
    }

    public void onFinalize(TaskManager taskManager) {
    }

    public void onPrepare(TaskManager taskManager) {
    }

    public void onProgress(TaskManager taskManager, int i, int i2) {
    }

    public void onResult(TaskManager taskManager, T t) {
    }

    public final void publishProgress(int i, int i2) {
        postDelivery(Delivery.Progress, new int[]{i, i2});
    }

    public final Task<T> removeListener(Listener listener) {
        ArrayList<Listener> arrayList = this.mListeners;
        if (arrayList != null) {
            arrayList.remove(listener);
        }
        return this;
    }

    public final boolean restart() {
        TaskManager taskManager;
        if (!canRestart()) {
            return false;
        }
        if ((this.mStatus == Status.Queued && ((taskManager = this.mTaskManager.get()) == null || !taskManager.remove(this))) || this.mStatus == Status.Executing) {
            return false;
        }
        synchronized (this.mSuccessorSync) {
            if (this.mSuccessorTasks != null) {
                Iterator<Task<?>> it = this.mSuccessorTasks.iterator();
                while (it.hasNext()) {
                    it.next().restart();
                }
            }
            this.mSuccessorSync.set(true);
        }
        this.mStatus = Status.New;
        return true;
    }

    public final Task<T> setPriority(Priority priority) {
        TaskManager taskManager;
        if (this.mPriority != priority) {
            if (this.mStatus == Status.Queued && (taskManager = this.mTaskManager.get()) != null && taskManager.remove(this)) {
                this.mPriority = priority;
                this.mStatus = Status.New;
                taskManager.add(this);
            }
            if (this.mStatus == Status.Executing) {
                setThread(this.mThread);
            }
            this.mPriority = priority;
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public final void setStatus(Status status, Object obj) {
        int i = AnonymousClass1.$SwitchMap$miui$util$async$Task$Status[this.mStatus.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    int i2 = AnonymousClass1.$SwitchMap$miui$util$async$Task$Status[status.ordinal()];
                    if (i2 == 2) {
                        postDelivery(Delivery.Result, null);
                        postDelivery(Delivery.Finalize, null);
                    } else if (i2 != 4) {
                        Log.w("async", "Task " + this + " error status change=> " + status);
                        return;
                    } else {
                        postDelivery(Delivery.Result, obj);
                        postDelivery(Delivery.Finalize, null);
                    }
                } else if (i != 4) {
                    if (i == 5) {
                        int i3 = AnonymousClass1.$SwitchMap$miui$util$async$Task$Status[status.ordinal()];
                        if (i3 == 1) {
                            postDelivery(Delivery.Prepare, null);
                        } else if (i3 != 2) {
                            Log.w("async", "Task " + this + " error status change=> " + status);
                            return;
                        } else {
                            postDelivery(Delivery.Result, null);
                            postDelivery(Delivery.Finalize, null);
                        }
                    }
                }
            }
            Log.w("async", "Task " + this + " error status change=> " + status);
            return;
        }
        int i4 = AnonymousClass1.$SwitchMap$miui$util$async$Task$Status[status.ordinal()];
        if (i4 == 2) {
            postDelivery(Delivery.Result, null);
            postDelivery(Delivery.Finalize, null);
        } else if (i4 != 3) {
            Log.w("async", "Task " + this + " error status change=> " + status);
            return;
        }
        this.mStatus = status;
    }

    /* access modifiers changed from: package-private */
    public final boolean setTaskManager(TaskManager taskManager) {
        WeakReference<TaskManager> weakReference = this.mTaskManager;
        if (weakReference != null && weakReference.get() != null) {
            return false;
        }
        this.mTaskManager = new WeakReference<>(taskManager);
        return true;
    }

    /* access modifiers changed from: package-private */
    public final void setThread(Thread thread) {
        this.mThread = thread;
        if (thread != null) {
            int i = AnonymousClass1.$SwitchMap$miui$util$async$Task$Priority[this.mPriority.ordinal()];
            if (i == 1) {
                thread.setPriority(1);
            } else if (i == 2) {
                thread.setPriority(5);
            } else if (i == 3 || i == 4) {
                thread.setPriority(10);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        String description = getDescription();
        if (description != null) {
            sb.append('<');
            sb.append(description);
            sb.append('>');
        }
        sb.append(": Status=");
        sb.append(this.mStatus);
        sb.append(", Priority=");
        sb.append(this.mPriority);
        return sb.toString();
    }
}
