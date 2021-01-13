package com.xiaomi.camera.util;

import android.os.Handler;
import android.os.SystemClock;
import com.xiaomi.camera.rx.CameraSchedulers;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {

    public interface BlockingOperation {
        void run() throws InterruptedException;
    }

    public static void assertCameraHandlerThread() {
        if (!CameraSchedulers.isOnCameraHandlerThread()) {
            throw new IllegalStateException("Expected to be called on the camera handler thread but was " + Thread.currentThread().getName());
        }
    }

    public static void assertCameraSetupThread() {
        if (!CameraSchedulers.isOnCameraSetupThread()) {
            throw new IllegalStateException("Expected to be called on the camera setup thread but was " + Thread.currentThread().getName());
        }
    }

    public static void assertMainThread() {
        if (!CameraSchedulers.isOnMainThread()) {
            throw new IllegalStateException("Expected to be called on the main thread but was " + Thread.currentThread().getName());
        }
    }

    public static void awaitUninterruptibly(final CountDownLatch countDownLatch) {
        executeUninterruptibly(new BlockingOperation() {
            /* class com.xiaomi.camera.util.ThreadUtils.AnonymousClass2 */

            @Override // com.xiaomi.camera.util.ThreadUtils.BlockingOperation
            public void run() throws InterruptedException {
                countDownLatch.await();
            }
        });
    }

    public static boolean awaitUninterruptibly(CountDownLatch countDownLatch, long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        boolean z = false;
        long j2 = j;
        boolean z2 = false;
        do {
            try {
                z = countDownLatch.await(j2, TimeUnit.MILLISECONDS);
                break;
            } catch (InterruptedException unused) {
                z2 = true;
                j2 = j - (SystemClock.elapsedRealtime() - elapsedRealtime);
                if (j2 <= 0) {
                }
            }
        } while (j2 <= 0);
        if (z2) {
            Thread.currentThread().interrupt();
        }
        return z;
    }

    static StackTraceElement[] concatStackTraces(StackTraceElement[] stackTraceElementArr, StackTraceElement[] stackTraceElementArr2) {
        StackTraceElement[] stackTraceElementArr3 = new StackTraceElement[(stackTraceElementArr.length + stackTraceElementArr2.length)];
        System.arraycopy(stackTraceElementArr, 0, stackTraceElementArr3, 0, stackTraceElementArr.length);
        System.arraycopy(stackTraceElementArr2, 0, stackTraceElementArr3, stackTraceElementArr.length, stackTraceElementArr2.length);
        return stackTraceElementArr3;
    }

    public static void executeUninterruptibly(BlockingOperation blockingOperation) {
        boolean z = false;
        while (true) {
            try {
                blockingOperation.run();
                break;
            } catch (InterruptedException unused) {
                z = true;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
    }

    public static <V> V invokeAtFrontUninterruptibly(Handler handler, final Callable<V> callable) {
        if (handler.getLooper().getThread() == Thread.currentThread()) {
            try {
                return callable.call();
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        } else {
            final AnonymousClass1Result r0 = new Object() {
                /* class com.xiaomi.camera.util.ThreadUtils.AnonymousClass1Result */
                public V value;
            };
            final AnonymousClass1CaughtException r1 = new Object() {
                /* class com.xiaomi.camera.util.ThreadUtils.AnonymousClass1CaughtException */

                /* renamed from: e  reason: collision with root package name */
                Exception f387e;
            };
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            handler.post(new Runnable() {
                /* class com.xiaomi.camera.util.ThreadUtils.AnonymousClass3 */

                public void run() {
                    try {
                        AnonymousClass1Result.this.value = (V) callable.call();
                    } catch (Exception e2) {
                        r1.f387e = e2;
                    }
                    countDownLatch.countDown();
                }
            });
            awaitUninterruptibly(countDownLatch);
            Exception exc = r1.f387e;
            if (exc == null) {
                return r0.value;
            }
            RuntimeException runtimeException = new RuntimeException(exc);
            runtimeException.setStackTrace(concatStackTraces(r1.f387e.getStackTrace(), runtimeException.getStackTrace()));
            throw runtimeException;
        }
    }

    public static void invokeAtFrontUninterruptibly(Handler handler, final Runnable runnable) {
        invokeAtFrontUninterruptibly(handler, new Callable<Void>() {
            /* class com.xiaomi.camera.util.ThreadUtils.AnonymousClass4 */

            @Override // java.util.concurrent.Callable
            public Void call() {
                runnable.run();
                return null;
            }
        });
    }

    public static void joinUninterruptibly(final Thread thread) {
        executeUninterruptibly(new BlockingOperation() {
            /* class com.xiaomi.camera.util.ThreadUtils.AnonymousClass1 */

            @Override // com.xiaomi.camera.util.ThreadUtils.BlockingOperation
            public void run() throws InterruptedException {
                thread.join();
            }
        });
    }

    public static boolean joinUninterruptibly(Thread thread, long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        boolean z = false;
        long j2 = j;
        while (j2 > 0) {
            try {
                thread.join(j2);
                break;
            } catch (InterruptedException unused) {
                j2 = j - (SystemClock.elapsedRealtime() - elapsedRealtime);
                z = true;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return !thread.isAlive();
    }
}
