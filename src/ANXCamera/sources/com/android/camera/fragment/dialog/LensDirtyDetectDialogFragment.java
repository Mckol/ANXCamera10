package com.android.camera.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

public class LensDirtyDetectDialogFragment extends DialogFragment implements DialogInterface.OnKeyListener, View.OnClickListener, ModeProtocol.HandleBackTrace {
    public static final String TAG = "LensDirtyDetectDialog";

    private void adjustView(View view) {
        Rect displayRect = Util.getDisplayRect();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = displayRect.top;
        layoutParams.width = displayRect.width();
        layoutParams.height = (displayRect.width() * 4) / 3;
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public final boolean canProvide() {
        return isAdded();
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        dismissAllowingStateLoss();
        return i == 1 || i == 2;
    }

    public void onClick(View view) {
        onBackEvent(5);
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
        boolean z = false;
        View inflate = layoutInflater.inflate(R.layout.fragment_dialog_lens_dirty_detect_hint, viewGroup, false);
        inflate.setOnClickListener(this);
        TextView textView = (TextView) inflate.findViewById(R.id.lens_dirty_detected_title);
        if (DataRepository.dataItemGlobal().getCurrentCameraId() == 1) {
            z = true;
        }
        textView.setText(z ? R.string.lens_dirty_detected_title_front : R.string.lens_dirty_detected_title_back);
        adjustView(inflate.findViewById(R.id.lens_dirty_detected_cover));
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
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setOnKeyListener(this);
        }
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
