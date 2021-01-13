package com.android.camera.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.android.camera.Camera;
import com.android.camera.Util;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.protocol.ModeProtocol;

public class BaseDialogFragment extends DialogFragment implements DialogInterface.OnKeyListener, ModeProtocol.HandleBackTrace {
    private GestureDetector gesture;
    private DismissCallback mDismissCallback;

    public interface DismissCallback {
        void onDismiss();
    }

    /* access modifiers changed from: private */
    public class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        private MyOnGestureListener() {
        }

        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            BaseDialogFragment.this.onBackEvent(5);
            return super.onScroll(motionEvent, motionEvent2, f, f2);
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            BaseDialogFragment.this.onBackEvent(5);
            return super.onSingleTapUp(motionEvent);
        }
    }

    public /* synthetic */ boolean a(View view, MotionEvent motionEvent) {
        return this.gesture.onTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void adjustViewHeight(View view) {
        Rect displayRect = Util.getDisplayRect(0);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.width = displayRect.width();
        marginLayoutParams.height = displayRect.height();
        marginLayoutParams.topMargin = displayRect.top;
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean canProvide() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void initViewOnTouchListener(View view) {
        this.gesture = new GestureDetector(getActivity(), new MyOnGestureListener());
        view.setOnTouchListener(new a(this));
    }

    @Override // androidx.fragment.app.Fragment, androidx.fragment.app.DialogFragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            window.setGravity(48);
            window.setLayout(-1, -1);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.type = 1;
            window.setAttributes(attributes);
            if (Util.isContentViewExtendToTopEdges()) {
                CompatibilityUtils.setCutoutModeShortEdges(window);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        Camera camera = (Camera) getActivity();
        if (camera != null) {
            camera.showNewNotification();
            camera.getCameraScreenNail().drawBlackFrame(false);
        }
        return false;
    }

    @Override // androidx.fragment.app.DialogFragment
    public void onDismiss(@NonNull DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        DismissCallback dismissCallback = this.mDismissCallback;
        if (dismissCallback != null) {
            dismissCallback.onDismiss();
        }
    }

    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        return false;
    }

    public void setDismissCallback(DismissCallback dismissCallback) {
        this.mDismissCallback = dismissCallback;
    }
}
