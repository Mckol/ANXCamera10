package miui.animation.internal;

import java.lang.ref.WeakReference;
import miui.animation.IAnimTarget;

public class AnimRunnable implements Runnable {
    private long mDeltaT;
    private WeakReference<IAnimTarget> mTargetRef;
    private long mTotalT;

    public synchronized void init(IAnimTarget iAnimTarget, long j, long j2) {
        this.mTargetRef = new WeakReference<>(iAnimTarget);
        this.mTotalT = j;
        this.mDeltaT = j2;
    }

    public synchronized void run() {
        IAnimTarget iAnimTarget = this.mTargetRef.get();
        if (iAnimTarget != null) {
            AnimRunner.getInst().doAnimOnTarget(iAnimTarget, this.mTotalT, this.mDeltaT);
        }
    }
}
