package com.android.camera.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.DialogFragment;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import miui.app.AlertDialog;

public class LongPressLiveFragment extends DialogFragment {
    public static final String TAG = "ThermalDialog";
    @StringRes
    private int mId = R.string.pref_camera_long_pressing_burst_shooting_entryvalue_record;

    private void selectLpFeature(@StringRes int i) {
        DataRepository.dataItemGlobal().editor().putString(CameraSettings.KEY_LONG_PRESS_SHUTTER_FEATURE, getString(i)).apply();
        if (this.mId == R.string.pref_camera_long_pressing_burst_shooting_entryvalue_record) {
            CameraStatUtils.trackLongPressDialogSelect(MistatsConstants.Other.KEY_LONG_PRESS_DIALOG_RECORD);
        } else {
            CameraStatUtils.trackLongPressDialogSelect(MistatsConstants.Other.KEY_LONG_PRESS_DIALOG_BURST);
        }
    }

    public /* synthetic */ void a(View view, View view2) {
        view.findViewById(R.id.lpl_icon_record).setSelected(false);
        view.findViewById(R.id.lpl_icon_burst).setSelected(true);
        this.mId = R.string.pref_camera_long_pressing_burst_shooting_entryvalue_burst;
    }

    public /* synthetic */ void b(View view, View view2) {
        view.findViewById(R.id.lpl_icon_record).setSelected(true);
        view.findViewById(R.id.lpl_icon_burst).setSelected(false);
        this.mId = R.string.pref_camera_long_pressing_burst_shooting_entryvalue_record;
    }

    public /* synthetic */ void d(DialogInterface dialogInterface, int i) {
        selectLpFeature(this.mId);
        dismiss();
    }

    @Override // androidx.fragment.app.DialogFragment
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle bundle) {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_lpl_dialog, (ViewGroup) null, false);
        inflate.findViewById(R.id.lpl_icon_burst).setOnClickListener(new k(this, inflate));
        FolmeUtils.touchScaleTint(inflate.findViewById(R.id.lpl_icon_burst));
        inflate.findViewById(R.id.lpl_icon_record).setOnClickListener(new j(this, inflate));
        FolmeUtils.touchScaleTint(inflate.findViewById(R.id.lpl_icon_record));
        inflate.findViewById(R.id.lpl_icon_record).setSelected(true);
        inflate.findViewById(R.id.lpl_icon_burst).setSelected(false);
        inflate.setPadding(0, 0, 0, Util.getNavigationBarHeight(getActivity()));
        return new AlertDialog.Builder(getActivity()).setTitle(R.string.lpl_dialog_title).setView(inflate).setPositiveButton(R.string.dialog_ok, new i(this)).create();
    }
}
