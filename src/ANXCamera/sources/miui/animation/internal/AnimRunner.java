package miui.animation.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import miui.animation.Folme;
import miui.animation.IAnimTarget;
import miui.animation.base.AnimConfigLink;
import miui.animation.controller.AnimState;
import miui.animation.physics.AnimationHandler;
import miui.animation.property.FloatProperty;
import miui.animation.utils.LogUtils;

public class AnimRunner implements AnimationHandler.AnimationFrameCallback {
    public static final long MAX_DELTA = 16;
    private static final int MAX_RECORD = 5;
    private static final int MSG_START = 0;
    private static final Handler sMainHandler = new Handler(Looper.getMainLooper()) {
        /* class miui.animation.internal.AnimRunner.AnonymousClass1 */

        public void handleMessage(Message message) {
            if (message.what == 0) {
                AnimRunner.startAnimRunner();
            } else {
                super.handleMessage(message);
            }
        }
    };
    private long[] mDeltaRecord = {0, 0, 0, 0, 0};
    private boolean mIsRunning;
    private long mLastFrameTime;
    private volatile float mRatio = 1.0f;
    private int mRecordCount = 0;
    private AtomicLong mRunningTime = new AtomicLong();
    private List<IAnimTarget> mTargetList = new ArrayList();

    /* access modifiers changed from: private */
    public static class Holder {
        static final AnimRunner inst = new AnimRunner();

        private Holder() {
        }
    }

    private long average(long[] jArr) {
        int i = 0;
        long j = 0;
        for (long j2 : jArr) {
            j += j2;
            if (j2 > 0) {
                i++;
            }
        }
        if (i > 0) {
            return j / ((long) i);
        }
        return 0;
    }

    private void cleanOneshotTarget(IAnimTarget iAnimTarget) {
        AnimTask animTask = iAnimTarget.getAnimTask();
        if (!iAnimTarget.hasFlags(1)) {
            return;
        }
        if (!animTask.isValid() || animTask.isFinished()) {
            Folme.clean(iAnimTarget);
        }
    }

    private boolean endAnimation() {
        if (isRunning()) {
            return false;
        }
        if (LogUtils.isLogEnabled()) {
            LogUtils.debug("AnimRunner.endAnimation", new Object[0]);
        }
        this.mIsRunning = false;
        this.mRunningTime.set(0);
        this.mLastFrameTime = 0;
        AnimationHandler.getInstance().removeCallback(this);
        return true;
    }

    public static AnimTask getAnimTask(IAnimTarget iAnimTarget, long j, AnimState animState, AnimState animState2, AnimConfigLink animConfigLink) {
        AnimTask animTask = iAnimTarget.getAnimTask();
        animTask.addTransition(j, new TransitionInfo(iAnimTarget, animState, animState2, animConfigLink));
        return animTask;
    }

    public static AnimRunner getInst() {
        return Holder.inst;
    }

    private boolean isAnimRunning(IAnimTarget iAnimTarget) {
        return !iAnimTarget.getAnimTask().isFinished();
    }

    private boolean isRunning() {
        boolean z;
        Iterator<IAnimTarget> it = this.mTargetList.iterator();
        while (true) {
            if (it.hasNext()) {
                if (isAnimRunning(it.next())) {
                    z = true;
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        this.mTargetList.clear();
        return z;
    }

    private void runAnim(AnimTask animTask, long j, long j2, long... jArr) {
        animTask.run(j, j2, jArr);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void start() {
        if (!this.mIsRunning) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                startAnimRunner();
            } else {
                sMainHandler.sendEmptyMessage(0);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void startAnimRunner() {
        AnimRunner inst = getInst();
        if (!inst.mIsRunning) {
            if (LogUtils.isLogEnabled()) {
                LogUtils.debug("AnimRunner.start", new Object[0]);
            }
            inst.mIsRunning = true;
            AnimationHandler.getInstance().addAnimationFrameCallback(inst, 0);
        }
    }

    private long updateRunningTime(long j) {
        long j2 = this.mLastFrameTime;
        long j3 = 0;
        if (j2 == 0) {
            this.mLastFrameTime = j;
        } else {
            j3 = j - j2;
            this.mLastFrameTime = j;
        }
        int i = this.mRecordCount;
        this.mDeltaRecord[i % 5] = j3;
        this.mRecordCount = i + 1;
        long averageDelta = getAverageDelta(j3);
        this.mRunningTime.addAndGet(averageDelta);
        return averageDelta;
    }

    public void cancel(final IAnimTarget iAnimTarget, final FloatProperty... floatPropertyArr) {
        iAnimTarget.post(new Runnable() {
            /* class miui.animation.internal.AnimRunner.AnonymousClass2 */

            public void run() {
                iAnimTarget.getAnimTask().cancel(floatPropertyArr);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void doAnimOnTarget(IAnimTarget iAnimTarget, long j, long j2) {
        AnimTask animTask = iAnimTarget.getAnimTask();
        runAnim(animTask, j, j2, new long[0]);
        iAnimTarget.onFrameEnd(animTask.isFinished());
        cleanOneshotTarget(iAnimTarget);
    }

    @Override // miui.animation.physics.AnimationHandler.AnimationFrameCallback
    public boolean doAnimationFrame(long j) {
        long updateRunningTime = updateRunningTime(j);
        long j2 = this.mRunningTime.get();
        Folme.getTargets(this.mTargetList);
        for (IAnimTarget iAnimTarget : this.mTargetList) {
            if (iAnimTarget.allowAnimRun() && iAnimTarget.getAnimTask().isValid()) {
                iAnimTarget.executeAnim(j2, updateRunningTime);
            }
            cleanOneshotTarget(iAnimTarget);
        }
        return endAnimation();
    }

    public void end(final IAnimTarget iAnimTarget, final FloatProperty... floatPropertyArr) {
        iAnimTarget.post(new Runnable() {
            /* class miui.animation.internal.AnimRunner.AnonymousClass3 */

            public void run() {
                iAnimTarget.getAnimTask().end(floatPropertyArr);
            }
        });
    }

    public long getAverageDelta(long j) {
        long average = average(this.mDeltaRecord);
        if (average > 0) {
            j = average;
        }
        if (j > 16) {
            j = 16;
        }
        return (long) Math.ceil((double) (((float) j) / this.mRatio));
    }

    public long getRunningTime() {
        return this.mRunningTime.get();
    }

    public void run(final IAnimTarget iAnimTarget, final AnimState animState, final AnimState animState2, final AnimConfigLink animConfigLink) {
        iAnimTarget.executeOnInitialized(new Runnable() {
            /* class miui.animation.internal.AnimRunner.AnonymousClass4 */

            public void run() {
                AnimRunner.getAnimTask(iAnimTarget, AnimRunner.this.mRunningTime.get(), animState, animState2, animConfigLink);
                AnimRunner.this.start();
            }
        });
    }

    public void setTimeRatio(float f) {
        this.mRatio = f;
    }
}
