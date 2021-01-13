package miui.autoinstall.config.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.miui.internal.R;
import miui.app.AlertDialog;
import miui.autoinstall.config.download.AutoInstallNotification;
import miui.autoinstall.config.pm.PackageManagerCompat;
import miui.autoinstall.config.service.AutoInstallService;

public class RestoreFailedDialogActivity extends Activity {
    private boolean mNotNow;
    private PackageManagerCompat mPmCompat;
    private boolean mRightNow;

    private void configDialog() {
        new AlertDialog.Builder(this).setTitle(R.string.system_app_restore_failed).setMessage(R.string.system_app_restore_failed_detail).setCancelable(false).setPositiveButton(getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
            /* class miui.autoinstall.config.activity.RestoreFailedDialogActivity.AnonymousClass3 */

            public void onClick(DialogInterface dialogInterface, int i) {
                RestoreFailedDialogActivity.this.mRightNow = true;
                RestoreFailedDialogActivity.this.restore();
            }
        }).setNegativeButton(getResources().getString(R.string.do_not_restore_temporarily), new DialogInterface.OnClickListener() {
            /* class miui.autoinstall.config.activity.RestoreFailedDialogActivity.AnonymousClass2 */

            public void onClick(DialogInterface dialogInterface, int i) {
                RestoreFailedDialogActivity.this.mNotNow = true;
                RestoreFailedDialogActivity.this.donotRestore();
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            /* class miui.autoinstall.config.activity.RestoreFailedDialogActivity.AnonymousClass1 */

            public void onDismiss(DialogInterface dialogInterface) {
                RestoreFailedDialogActivity.this.finish();
            }
        }).create().show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void donotRestore() {
        new AutoInstallNotification(this).showNotRestoreNotification();
        this.mPmCompat.recordRestoreNotNow(true);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void restore() {
        Intent intent = new Intent(this, AutoInstallService.class);
        intent.setAction(AutoInstallService.ACTION_START_PROCEDURE);
        startService(intent);
        this.mPmCompat.recordRestoreNotNow(false);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mPmCompat = new PackageManagerCompat(this);
        configDialog();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (!this.mNotNow && !this.mRightNow) {
            this.mNotNow = false;
            this.mRightNow = false;
            donotRestore();
        }
    }
}
