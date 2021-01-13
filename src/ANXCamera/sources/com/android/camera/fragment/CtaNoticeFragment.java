package com.android.camera.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.ui.ScreenHint;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import miui.app.AlertDialog;

public class CtaNoticeFragment extends DialogFragment {
    public static final String TAG = "CtaNoticeFragment";
    public static final int TYPE_LIVE_VIDEO = 1;
    public static final int TYPE_MI_LIVE_VIDEO = 4;
    public static final int TYPE_STICKER = 3;
    public static final int TYPE_VOICE_CAPTION = 2;
    private OnCtaNoticeClickListener mClickListener;
    private int mType;

    public static class CTA {
        private static boolean sCanConnectToNetworkTemp;

        public static boolean canConnectNetwork() {
            if (sCanConnectToNetworkTemp) {
                return true;
            }
            return ((DataItemGlobal) DataRepository.provider().dataGlobal()).getCTACanCollect();
        }

        public static void setCanConnectNetwork(boolean z) {
            ((DataItemGlobal) DataRepository.provider().dataGlobal()).setCTACanCollect(z);
        }
    }

    public interface OnCtaNoticeClickListener {
        void onNegativeClick(DialogInterface dialogInterface, int i);

        void onPositiveClick(DialogInterface dialogInterface, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public CtaNoticeFragment(OnCtaNoticeClickListener onCtaNoticeClickListener, int i) {
        this.mClickListener = onCtaNoticeClickListener;
        this.mType = i;
    }

    public static boolean checkCta(FragmentManager fragmentManager, int i) {
        return checkCta(fragmentManager, null, i);
    }

    public static boolean checkCta(FragmentManager fragmentManager, OnCtaNoticeClickListener onCtaNoticeClickListener, int i) {
        return showCta(fragmentManager, onCtaNoticeClickListener, i) == null;
    }

    public static CtaNoticeFragment showCta(FragmentManager fragmentManager, OnCtaNoticeClickListener onCtaNoticeClickListener, int i) {
        if (CameraSettings.isAllowCTA()) {
            return null;
        }
        Fragment findFragmentByTag = fragmentManager.findFragmentByTag(TAG);
        if (findFragmentByTag != null) {
            return (CtaNoticeFragment) findFragmentByTag;
        }
        CtaNoticeFragment ctaNoticeFragment = new CtaNoticeFragment(onCtaNoticeClickListener, i);
        ctaNoticeFragment.show(fragmentManager, TAG);
        return ctaNoticeFragment;
    }

    public /* synthetic */ void a(DialogInterface dialogInterface, int i) {
        CameraSettings.updateCTAPreference(true);
        CTA.setCanConnectNetwork(true);
        OnCtaNoticeClickListener onCtaNoticeClickListener = this.mClickListener;
        if (onCtaNoticeClickListener != null) {
            onCtaNoticeClickListener.onPositiveClick(dialogInterface, i);
        }
    }

    public /* synthetic */ void b(DialogInterface dialogInterface, int i) {
        CameraSettings.updateCTAPreference(false);
        CTA.setCanConnectNetwork(false);
        OnCtaNoticeClickListener onCtaNoticeClickListener = this.mClickListener;
        if (onCtaNoticeClickListener != null) {
            onCtaNoticeClickListener.onNegativeClick(dialogInterface, i);
        }
    }

    public void dismiss() {
        if (getFragmentManager() != null) {
            super.dismiss();
        }
    }

    public void onCancel(DialogInterface dialogInterface) {
        super.onCancel(dialogInterface);
        OnCtaNoticeClickListener onCtaNoticeClickListener = this.mClickListener;
        if (onCtaNoticeClickListener != null) {
            onCtaNoticeClickListener.onNegativeClick(dialogInterface, -2);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCancelable(false);
    }

    public Dialog onCreateDialog(Bundle bundle) {
        String format = String.format("%s_%s", Locale.getDefault().getLanguage(), Locale.getDefault().getCountry());
        String string = getString(R.string.link_user_agreement, format);
        String string2 = getString(R.string.link_privacy_policy, format);
        Log.d(TAG, "onCreateDialog: lang = " + format + ", linkUserAgreement = " + string + ", linkPrivacyPolicy = " + string2);
        Spanned fromHtml = Html.fromHtml(getString(R.string.cta_user_tips, string, string2), 63);
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.dialog_cta, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.tv_cta_user_tips);
        textView.setText(fromHtml);
        ScreenHint.setLinkClickEvent(textView, getActivity());
        return new AlertDialog.Builder(getActivity()).setView(inflate).setPositiveButton(R.string.network_access_user_notice_agree, new c(this)).setNegativeButton(R.string.user_disagree, new b(this)).create();
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCancelable(true);
        }
    }
}
