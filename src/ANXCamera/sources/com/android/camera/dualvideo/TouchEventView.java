package com.android.camera.dualvideo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

public class TouchEventView extends View {
    private static final String TAG = "TouchEventView";
    private TouchEventListener mListener;

    public interface TouchEventListener {
        boolean onTouchEvent(MotionEvent motionEvent);
    }

    public TouchEventView(Context context) {
        super(context);
    }

    public TouchEventView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TouchEventView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public TouchEventView(Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    private void handleBackStack() {
        ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
        if (backStack == null || backStack.handleBackStackFromKeyBack()) {
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        TouchEventListener touchEventListener = this.mListener;
        if (touchEventListener == null) {
            return super.onTouchEvent(motionEvent);
        }
        boolean onTouchEvent = touchEventListener.onTouchEvent(motionEvent);
        if (onTouchEvent) {
            handleBackStack();
        }
        return onTouchEvent;
    }

    public void setListener(TouchEventListener touchEventListener) {
        this.mListener = touchEventListener;
    }
}
