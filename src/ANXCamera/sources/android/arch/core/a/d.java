package android.arch.core.a;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* compiled from: DefaultTaskExecutor */
public class d extends e {
    private ExecutorService ja = Executors.newFixedThreadPool(2);
    private final Object mLock = new Object();
    @Nullable
    private volatile Handler mMainHandler;

    public void a(Runnable runnable) {
        this.ja.execute(runnable);
    }

    public void c(Runnable runnable) {
        if (this.mMainHandler == null) {
            synchronized (this.mLock) {
                if (this.mMainHandler == null) {
                    this.mMainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        this.mMainHandler.post(runnable);
    }

    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
