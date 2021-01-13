package com.android.camera.timerburst;

import com.android.camera.log.Log;
import com.android.camera.statistic.MistatsConstants;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;

public class CameraTimer {
    private static final String TAG = "CameraTimer";
    private long mConsumedTakeTimes;
    private Disposable mCountdownDisposable;
    private int mInitialCount;
    private int mInitialRepeatTimes;
    private Observer<Long> mOutdoorObserver;
    private long mTotalTakeTimes;

    static /* synthetic */ long access$314(CameraTimer cameraTimer, long j) {
        long j2 = cameraTimer.mConsumedTakeTimes + j;
        cameraTimer.mConsumedTakeTimes = j2;
        return j2;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void notifyNumber() {
        long j = this.mConsumedTakeTimes;
        int i = this.mInitialCount;
        long j2 = j % ((long) i);
        long j3 = ((long) i) - (1 + j2);
        Log.e(TAG, "notifyNumber: " + this.mConsumedTakeTimes + " | " + j2 + " | " + j3);
        this.mOutdoorObserver.onNext(Long.valueOf(j3));
    }

    public void dispose() {
        Disposable disposable = this.mCountdownDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mCountdownDisposable.dispose();
            this.mCountdownDisposable = null;
        }
    }

    public boolean isRunning() {
        Disposable disposable = this.mCountdownDisposable;
        return disposable != null && !disposable.isDisposed();
    }

    public void pause() {
        Log.d(TAG, MistatsConstants.BaseEvent.PAUSE);
        dispose();
    }

    public void resume() {
        Log.d(TAG, MistatsConstants.BaseEvent.RESUME);
        long j = (this.mTotalTakeTimes - this.mConsumedTakeTimes) - 1;
        if (j > 0) {
            Observable.interval(1, TimeUnit.SECONDS).take(j).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
                /* class com.android.camera.timerburst.CameraTimer.AnonymousClass1 */

                @Override // io.reactivex.Observer
                public void onComplete() {
                    CameraTimer.this.mOutdoorObserver.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                    CameraTimer.this.mOutdoorObserver.onError(th);
                }

                public void onNext(Long l) {
                    CameraTimer.access$314(CameraTimer.this, 1);
                    CameraTimer.this.notifyNumber();
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    CameraTimer.this.mCountdownDisposable = disposable;
                    CameraTimer.this.mOutdoorObserver.onSubscribe(disposable);
                    Log.d(CameraTimer.TAG, "onSubscribe");
                    CameraTimer.this.notifyNumber();
                }
            });
        }
    }

    public CameraTimer setCount(int i) {
        this.mInitialCount = i;
        return this;
    }

    public CameraTimer setRepeatTimes(int i) {
        this.mInitialRepeatTimes = i;
        return this;
    }

    public void start(Observer<Long> observer) {
        this.mOutdoorObserver = observer;
        this.mInitialCount++;
        this.mTotalTakeTimes = (long) (this.mInitialCount * this.mInitialRepeatTimes);
        this.mConsumedTakeTimes = 0;
        Log.d(TAG, "start: " + this.mInitialCount + " | " + this.mInitialRepeatTimes);
        resume();
    }
}
