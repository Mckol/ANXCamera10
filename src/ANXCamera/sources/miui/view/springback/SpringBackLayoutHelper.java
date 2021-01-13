package miui.view.springback;

import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

public class SpringBackLayoutHelper {
    private static final int INVALID_POINTER = -1;
    int mActivePointerId = -1;
    float mInitialDownX;
    float mInitialDownY;
    int mScrollOrientation;
    private ViewGroup mTarget;
    int mTargetScrollOrientation;
    private int mTouchSlop;

    public SpringBackLayoutHelper(ViewGroup viewGroup, int i) {
        this.mTarget = viewGroup;
        this.mTargetScrollOrientation = i;
        this.mTouchSlop = ViewConfiguration.get(viewGroup.getContext()).getScaledTouchSlop();
    }

    /* access modifiers changed from: package-private */
    public void checkOrientation(MotionEvent motionEvent) {
        int findPointerIndex;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            int i = 1;
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    int i2 = this.mActivePointerId;
                    if (i2 != -1 && (findPointerIndex = motionEvent.findPointerIndex(i2)) >= 0) {
                        float y = motionEvent.getY(findPointerIndex);
                        float x = motionEvent.getX(findPointerIndex);
                        float f = y - this.mInitialDownY;
                        float f2 = x - this.mInitialDownX;
                        if (Math.abs(f2) > ((float) this.mTouchSlop) || Math.abs(f) > ((float) this.mTouchSlop)) {
                            if (Math.abs(f2) <= Math.abs(f)) {
                                i = 2;
                            }
                            this.mScrollOrientation = i;
                            return;
                        }
                        return;
                    }
                    return;
                } else if (actionMasked != 3) {
                    return;
                }
            }
            this.mScrollOrientation = 0;
            this.mTarget.requestDisallowInterceptTouchEvent(false);
            return;
        }
        this.mActivePointerId = motionEvent.getPointerId(0);
        int findPointerIndex2 = motionEvent.findPointerIndex(this.mActivePointerId);
        if (findPointerIndex2 >= 0) {
            this.mInitialDownY = motionEvent.getY(findPointerIndex2);
            this.mInitialDownX = motionEvent.getX(findPointerIndex2);
            this.mScrollOrientation = 0;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        checkOrientation(motionEvent);
        int i = this.mScrollOrientation;
        if (i == 0 || i == this.mTargetScrollOrientation) {
            this.mTarget.requestDisallowInterceptTouchEvent(false);
            return true;
        }
        this.mTarget.requestDisallowInterceptTouchEvent(true);
        return false;
    }
}
