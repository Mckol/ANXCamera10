package io.reactivex.android.schedulers;

import android.os.Handler;
import android.os.Looper;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import java.util.concurrent.Callable;

public final class AndroidSchedulers {
    private static final Scheduler MAIN_THREAD = RxAndroidPlugins.initMainThreadScheduler(new Callable<Scheduler>() {
        /* class io.reactivex.android.schedulers.AndroidSchedulers.AnonymousClass1 */

        @Override // java.util.concurrent.Callable
        public Scheduler call() throws Exception {
            return MainHolder.DEFAULT;
        }
    });

    /* access modifiers changed from: private */
    public static final class MainHolder {
        static final Scheduler DEFAULT = new HandlerScheduler(new Handler(Looper.getMainLooper()));

        private MainHolder() {
        }
    }

    private AndroidSchedulers() {
        throw new AssertionError("No instances.");
    }

    public static Scheduler from(Looper looper) {
        if (looper != null) {
            return new HandlerScheduler(new Handler(looper));
        }
        throw new NullPointerException("looper == null");
    }

    public static Scheduler mainThread() {
        return RxAndroidPlugins.onMainThreadScheduler(MAIN_THREAD);
    }
}
