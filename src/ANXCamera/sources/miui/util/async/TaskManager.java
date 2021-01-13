package miui.util.async;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import miui.os.Environment;
import miui.util.SoftReferenceSingleton;
import miui.util.async.Task;
import miui.util.cache.Cache;
import miui.util.cache.LruCache;

public class TaskManager {
    public static final int DEFAULT_CACHE_SIZE = -1;
    private static final int DEFAULT_QUEUE_SIZE = 10;
    private static final int DEFAULT_THREAD_SIZE = -1;
    private static final SoftReferenceSingleton<TaskManager> INSTANCE = new SoftReferenceSingleton<TaskManager>() {
        /* class miui.util.async.TaskManager.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // miui.util.SoftReferenceSingleton
        public TaskManager createInstance() {
            return new TaskManager();
        }
    };
    static final boolean LOG_DEBUG = false;
    static final boolean LOG_ERROR = true;
    static final boolean LOG_INFO = false;
    static final boolean LOG_VERBOSE = false;
    static final boolean LOG_WARNING = true;
    static final String TAG = "async";
    private Cache<String, Object> mCache;
    private TaskInfoDeliverer mDeliverer;
    private Object mFinalizeGuardian;
    private final TaskQueue mQueue;
    private volatile boolean mShutdown;
    private ArrayList<TaskThread> mTaskThreads;

    public TaskManager() {
        this(10, -1, -1);
    }

    public TaskManager(int i, int i2, int i3) {
        this.mFinalizeGuardian = new Object() {
            /* class miui.util.async.TaskManager.AnonymousClass2 */

            /* access modifiers changed from: protected */
            public void finalize() throws Throwable {
                try {
                    TaskManager.this.shutdown();
                } finally {
                    super.finalize();
                }
            }
        };
        this.mShutdown = false;
        this.mQueue = new TaskQueue(this, i);
        if (i2 < 0 && (i2 = Environment.getCpuCount()) <= 0) {
            i2 = 4;
        }
        this.mTaskThreads = new ArrayList<>(i2);
        for (int i4 = 0; i4 < i2; i4++) {
            this.mTaskThreads.add(new TaskThread(this, this.mQueue, i4));
            this.mTaskThreads.get(i4).start();
        }
        this.mDeliverer = new TaskInfoDeliverer(this);
        this.mCache = new LruCache(i3);
    }

    private void clearQueue() {
        while (!this.mQueue.isEmpty()) {
            Task<?> task = this.mQueue.get();
            if (task != null) {
                task.setStatus(Task.Status.Canceled, null);
            }
        }
    }

    public static TaskManager getDefault() {
        return INSTANCE.get();
    }

    public void add(Task<?> task) {
        Object obj;
        if (this.mShutdown) {
            Log.e(TAG, "Cannot add task into a shut down task manager");
        } else if (task.isRunning()) {
            String str = "Task " + task + " has already added into task manager and not finish yet";
            Log.e(TAG, str);
            throw new IllegalArgumentException(str);
        } else if (task.getStatus() != Task.Status.New && !task.restart()) {
            throw new IllegalArgumentException("Status of task " + task + " is not New, and cannot restart.");
        } else if (task.setTaskManager(this)) {
            Cache<String, Object> cache = this.mCache;
            if (cache != null && (task instanceof Cacheable) && (obj = cache.get(((Cacheable) task).getCacheKey())) != null) {
                task.setStatus(Task.Status.Queued, null);
                task.setStatus(Task.Status.Executing, null);
                task.setStatus(Task.Status.Done, obj);
            } else if (task.getPriority() == Task.Priority.RealTime) {
                task.setStatus(Task.Status.Queued, null);
                TaskThread.runRealTimeTask(this, task);
            } else {
                this.mQueue.put(task);
            }
        } else {
            throw new IllegalArgumentException("Task " + task + " has already added into task manager and not finish yet");
        }
    }

    /* access modifiers changed from: package-private */
    public Cache<String, Object> getCache() {
        return this.mCache;
    }

    /* access modifiers changed from: package-private */
    public boolean isShutdown() {
        return this.mShutdown;
    }

    public void pause() {
        this.mQueue.pause();
    }

    /* access modifiers changed from: package-private */
    public void postDelivery(Task<?> task, Task.Delivery delivery, Object obj) {
        this.mDeliverer.postDeliver(task, delivery, obj);
    }

    /* access modifiers changed from: package-private */
    public boolean remove(Task<?> task) {
        return this.mQueue.remove(task);
    }

    public void resume() {
        this.mQueue.resume();
    }

    public void setCallbackThread(boolean z) {
        this.mDeliverer.setCallbackThread(z);
    }

    public void setMaxCache(int i) {
        this.mCache.setMaxSize(i);
    }

    public void setThreadCount(int i) {
        if (this.mShutdown) {
            Log.e(TAG, "Cannot add task into a shut down task manager");
            return;
        }
        if (i < 0 && (i = Environment.getCpuCount()) <= 0) {
            i = 4;
        }
        int size = this.mTaskThreads.size();
        if (i > size) {
            while (size < i) {
                TaskThread taskThread = new TaskThread(this, this.mQueue, size);
                taskThread.start();
                this.mTaskThreads.add(taskThread);
                size++;
            }
        } else if (i < size) {
            for (int i2 = size - 1; i2 >= i; i2--) {
                this.mTaskThreads.get(i2).shutdown();
                this.mTaskThreads.remove(i2);
            }
        }
    }

    public void shutdown() {
        if (this != getDefault() && !this.mShutdown) {
            this.mShutdown = true;
            Iterator<TaskThread> it = this.mTaskThreads.iterator();
            while (it.hasNext()) {
                it.next().shutdown();
            }
            this.mTaskThreads.clear();
            this.mCache.clear();
            clearQueue();
        }
    }
}
