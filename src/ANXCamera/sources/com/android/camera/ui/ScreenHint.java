package com.android.camera.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import b.c.a.c;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraSettings;
import com.android.camera.LocationManager;
import com.android.camera.OnScreenHint;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.WebViewActivity;
import com.android.camera.data.DataRepository;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.fragment.CtaNoticeFragment;
import com.android.camera.permission.PermissionManager;
import com.android.camera.storage.Storage;
import java.util.Locale;
import miui.app.AlertDialog;

public class ScreenHint {
    private final Activity mActivity;
    private boolean mAllowLocationAccess;
    private OnScreenHint mStorageHint;
    private long mStorageSpace;
    private AlertDialog mSystemChoiceDialog;

    public ScreenHint(Activity activity) {
        this.mActivity = activity;
    }

    public static void setLinkClickEvent(TextView textView, final Activity activity) {
        CharSequence text = textView.getText();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        URLSpan[] uRLSpanArr = (URLSpan[]) spannableStringBuilder.getSpans(0, text.length(), URLSpan.class);
        for (final URLSpan uRLSpan : uRLSpanArr) {
            spannableStringBuilder.setSpan(new ClickableSpan() {
                /* class com.android.camera.ui.ScreenHint.AnonymousClass2 */

                public void onClick(View view) {
                    String url = uRLSpan.getURL();
                    try {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.parse(url));
                        activity.startActivity(intent);
                    } catch (ActivityNotFoundException unused) {
                        Intent intent2 = new Intent(activity, WebViewActivity.class);
                        intent2.putExtra(CameraIntentManager.EXTRA_CTA_WEBVIEW_LINK, url);
                        activity.startActivity(intent2);
                    }
                }
            }, spannableStringBuilder.getSpanStart(uRLSpan), spannableStringBuilder.getSpanEnd(uRLSpan), spannableStringBuilder.getSpanFlags(uRLSpan));
        }
        textView.setText(spannableStringBuilder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void cancelHint() {
        OnScreenHint onScreenHint = this.mStorageHint;
        if (onScreenHint != null) {
            onScreenHint.cancel();
            this.mStorageHint = null;
        }
    }

    public void dismissSystemChoiceDialog() {
        AlertDialog alertDialog = this.mSystemChoiceDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mSystemChoiceDialog = null;
        }
    }

    public void hideToast() {
        RotateTextToast instance = RotateTextToast.getInstance();
        if (instance != null) {
            instance.show(0, 0);
        }
    }

    public /* synthetic */ void i(DialogInterface dialogInterface, int i) {
        this.mAllowLocationAccess = true;
        CameraSettings.updateCTAPreference(true);
        CtaNoticeFragment.CTA.setCanConnectNetwork(true);
        recordFirstUse(false);
    }

    public boolean isScreenHintVisible() {
        OnScreenHint onScreenHint = this.mStorageHint;
        return onScreenHint != null && onScreenHint.getHintViewVisibility() == 0;
    }

    public /* synthetic */ void j(DialogInterface dialogInterface, int i) {
        this.mAllowLocationAccess = false;
        CameraSettings.updateCTAPreference(false);
        recordFirstUse(false);
    }

    public void recordFirstUse(boolean z) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemGlobal().editor();
        editor.putBoolean("pref_camera_first_use_hint_shown_key", z);
        editor.putBoolean(CameraSettings.KEY_CAMERA_CONFIRM_LOCATION_SHOWN, z);
        editor.apply();
    }

    public void showConfirmMessage(int i, int i2) {
        Activity activity = this.mActivity;
        RotateDialogController.showSystemAlertDialog(activity, activity.getString(i), this.mActivity.getString(i2), this.mActivity.getString(17039370), null, null, null, null, null);
    }

    public void showFirstUseHint(final Runnable runnable) {
        AlertDialog alertDialog = this.mSystemChoiceDialog;
        if (alertDialog != null) {
            if (!alertDialog.isShowing()) {
                dismissSystemChoiceDialog();
            } else {
                return;
            }
        }
        if (CameraSettings.isShowFirstUseHint()) {
            boolean contains = DataRepository.dataItemGlobal().contains(CameraSettings.KEY_RECORD_LOCATION);
            if (c.Kn() && !contains) {
                this.mAllowLocationAccess = false;
                String format = String.format("%s_%s", Locale.getDefault().getLanguage(), Locale.getDefault().getCountry());
                String string = this.mActivity.getString(R.string.link_user_agreement, new Object[]{format});
                String string2 = this.mActivity.getString(R.string.link_privacy_policy, new Object[]{format});
                Spanned fromHtml = Html.fromHtml(this.mActivity.getString(R.string.cta_user_tips, new Object[]{string, string2}), 63);
                AlertDialog.Builder builder = new AlertDialog.Builder(this.mActivity);
                View inflate = this.mActivity.getLayoutInflater().inflate(R.layout.dialog_cta, (ViewGroup) null);
                TextView textView = (TextView) inflate.findViewById(R.id.tv_cta_user_tips);
                textView.setText(fromHtml);
                setLinkClickEvent(textView, this.mActivity);
                builder.setView(inflate);
                builder.setPositiveButton(R.string.location_access_allow, new i(this));
                builder.setNegativeButton(R.string.user_disagree, new h(this));
                this.mSystemChoiceDialog = builder.create();
                this.mSystemChoiceDialog.show();
                this.mSystemChoiceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    /* class com.android.camera.ui.ScreenHint.AnonymousClass1 */

                    public void onDismiss(DialogInterface dialogInterface) {
                        if (!ScreenHint.this.mAllowLocationAccess) {
                            runnable.run();
                        } else if (PermissionManager.requestCameraLocationPermissions(ScreenHint.this.mActivity)) {
                            CameraSettings.updateRecordLocationPreference(true);
                            LocationManager.instance().recordLocation(true);
                            runnable.run();
                        }
                        ScreenHint.this.mSystemChoiceDialog = null;
                    }
                });
            }
        }
    }

    public void showObjectTrackHint() {
        DataProvider.ProviderEditor editor = DataRepository.dataItemGlobal().editor();
        editor.putBoolean(CameraSettings.KEY_CAMERA_OBJECT_TRACK_HINT_SHOWN, false);
        editor.apply();
        RotateTextToast.getInstance(this.mActivity).show(R.string.object_track_enable_toast, 0);
    }

    public void updateHint() {
        Storage.switchStoragePathIfNeeded();
        this.mStorageSpace = Storage.getAvailableSpace();
        if (DataRepository.dataItemFeature().lh()) {
            Storage.getAvailableSpace(Storage.RAW_DIRECTORY);
        }
        long j = this.mStorageSpace;
        String string = j == -1 ? this.mActivity.getString(R.string.no_storage) : j == -2 ? this.mActivity.getString(R.string.preparing_sd) : j == -3 ? this.mActivity.getString(R.string.access_sd_fail) : j < Storage.LOW_STORAGE_THRESHOLD ? Storage.isPhoneStoragePriority() ? this.mActivity.getString(R.string.spaceIsLow_content_primary_storage_priority) : this.mActivity.getString(R.string.spaceIsLow_content_external_storage_priority) : null;
        if (string != null) {
            OnScreenHint onScreenHint = this.mStorageHint;
            if (onScreenHint == null) {
                this.mStorageHint = OnScreenHint.makeText(this.mActivity, string);
            } else {
                onScreenHint.setText(string);
            }
            this.mStorageHint.show();
            return;
        }
        OnScreenHint onScreenHint2 = this.mStorageHint;
        if (onScreenHint2 != null) {
            onScreenHint2.cancel();
            this.mStorageHint = null;
        }
    }
}
