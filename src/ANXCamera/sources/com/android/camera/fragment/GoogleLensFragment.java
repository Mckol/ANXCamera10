package com.android.camera.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import com.android.camera.R;
import miui.app.AlertDialog.Builder;

public class GoogleLensFragment extends DialogFragment {
    public static final String TAG = "GoogleLensFragment";
    private OnClickListener mClickListener;

    public interface OnClickListener {
        void onOptionClick(int i);
    }

    public GoogleLensFragment() {
    }

    public GoogleLensFragment(OnClickListener onClickListener) {
        this.mClickListener = onClickListener;
    }

    public static GoogleLensFragment showOptions(FragmentManager fragmentManager, OnClickListener onClickListener) {
        String str = TAG;
        Fragment findFragmentByTag = fragmentManager.findFragmentByTag(str);
        if (findFragmentByTag != null) {
            return (GoogleLensFragment) findFragmentByTag;
        }
        GoogleLensFragment googleLensFragment = new GoogleLensFragment(onClickListener);
        googleLensFragment.show(fragmentManager, str);
        return googleLensFragment;
    }

    public /* synthetic */ void a(DialogInterface dialogInterface, int i) {
        OnClickListener onClickListener = this.mClickListener;
        if (onClickListener != null) {
            onClickListener.onOptionClick(i);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public Dialog onCreateDialog(Bundle bundle) {
        return new Builder(getActivity()).setTitle(R.string.pref_camera_long_press_viewfinder_title).setItems(R.array.dialog_camera_long_press_viewfinder_options, new e(this)).setNegativeButton(17039360, null).create();
    }
}
