package com.android.camera.videoplayer.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import java.lang.Thread;

public class HandlerThreadExtension extends HandlerThread {
    private static final boolean SHOW_LOGS = false;
    private static final String TAG = "HandlerThreadExtension";
    private Handler mHandler;
    private final Object mStart = new Object();

    public HandlerThreadExtension(String str, boolean z) {
        super(str);
        if (z) {
            setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                /* class com.android.camera.videoplayer.utils.HandlerThreadExtension.AnonymousClass1 */

                public void uncaughtException(Thread thread, Throwable th) {
                    th.printStackTrace();
                    System.exit(0);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onLooperPrepared() {
        this.mHandler = new Handler();
        this.mHandler.post(new Runnable() {
            /* class com.android.camera.videoplayer.utils.HandlerThreadExtension.AnonymousClass2 */

            public void run() {
                synchronized (HandlerThreadExtension.this.mStart) {
                    HandlerThreadExtension.this.mStart.notifyAll();
                }
            }
        });
    }

    public void post(Runnable runnable) {
        this.mHandler.post(runnable);
    }

    public void postAtFrontOfQueue(Runnable runnable) {
        this.mHandler.postAtFrontOfQueue(runnable);
    }

    public void postQuit() {
        this.mHandler.post(new Runnable() {
            /* class com.android.camera.videoplayer.utils.HandlerThreadExtension.AnonymousClass3 */

            public void run() {
                Looper.myLooper().quit();
            }
        });
    }

    public void remove(Runnable runnable) {
        this.mHandler.removeCallbacks(runnable);
    }

    public void startThread() {
        synchronized (this.mStart) {
            start();
            try {
                this.mStart.wait();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }
}
