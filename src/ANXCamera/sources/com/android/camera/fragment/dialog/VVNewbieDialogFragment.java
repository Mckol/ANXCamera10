package com.android.camera.fragment.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.android.camera.R;

public class VVNewbieDialogFragment extends AiSceneNewbieDialogFragment {
    public static final String TAG = "VVHint";

    @Override // com.android.camera.fragment.dialog.AiSceneNewbieDialogFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_dialog_vv_hint, viewGroup, false);
        initViewOnTouchListener(inflate);
        adjustViewHeight(inflate.findViewById(R.id.vv_use_hint_layout));
        return inflate;
    }
}
