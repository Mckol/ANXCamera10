package com.android.camera.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.android.camera.R;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.miui.internal.widget.SearchActionModeViewHelper;
import miui.view.animation.CubicEaseInOutInterpolator;

public class FrontRotateNewbieDialogFragment extends BaseDialogFragment {
    public static final String TAG = "RotateHint";
    private AnimationDrawable mAnimationDrawable;

    public void animateOut(int i) {
        AnimationDrawable animationDrawable = this.mAnimationDrawable;
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
        dismissAllowingStateLoss();
    }

    @Override // com.android.camera.fragment.dialog.BaseDialogFragment, com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public final boolean canProvide() {
        return isAdded();
    }

    @Override // com.android.camera.fragment.dialog.BaseDialogFragment, com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        super.onBackEvent(i);
        animateOut(SearchActionModeViewHelper.ANIMATION_DURATION);
        return true;
    }

    @Override // androidx.fragment.app.DialogFragment
    public void onCancel(DialogInterface dialogInterface) {
        super.onCancel(dialogInterface);
        animateOut(SearchActionModeViewHelper.ANIMATION_DURATION);
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        Dialog onCreateDialog = super.onCreateDialog(bundle);
        onCreateDialog.getWindow().setGravity(48);
        return onCreateDialog;
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.front_camera_hint_popup, viewGroup, false);
        initViewOnTouchListener(inflate);
        this.mAnimationDrawable = (AnimationDrawable) inflate.findViewById(R.id.front_camera_hint_animation).getBackground();
        AnimationDrawable animationDrawable = this.mAnimationDrawable;
        if (animationDrawable != null) {
            animationDrawable.start();
        }
        ViewCompat.setAlpha(inflate, 0.0f);
        ViewCompat.animate(inflate).alpha(1.0f).setInterpolator(new CubicEaseInOutInterpolator()).start();
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment, androidx.fragment.app.DialogFragment
    public void onDestroyView() {
        ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
        if (backStack != null) {
            backStack.removeBackStack(this);
        }
        super.onDestroyView();
    }

    @Override // com.android.camera.fragment.dialog.BaseDialogFragment
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.getAction() != 1) {
            return false;
        }
        onBackEvent(5);
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        getDialog().setOnKeyListener(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
        if (backStack != null) {
            backStack.addInBackStack(this);
        }
    }
}
