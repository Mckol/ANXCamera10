package miui.hybrid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.KeyEvent;
import com.miui.internal.hybrid.HybridManager;
import miui.hybrid.GeolocationPermissions;

public class HybridChromeClient {
    private HybridManager mManager;

    public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
    }

    public boolean onJsAlert(HybridView hybridView, String str, String str2, final JsResult jsResult) {
        new AlertDialog.Builder(this.mManager.getActivity()).setMessage(str2).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            /* class miui.hybrid.HybridChromeClient.AnonymousClass3 */

            public void onClick(DialogInterface dialogInterface, int i) {
                jsResult.confirm();
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            /* class miui.hybrid.HybridChromeClient.AnonymousClass2 */

            public void onCancel(DialogInterface dialogInterface) {
                jsResult.cancel();
            }
        }).setOnKeyListener(new DialogInterface.OnKeyListener() {
            /* class miui.hybrid.HybridChromeClient.AnonymousClass1 */

            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i != 4) {
                    return true;
                }
                jsResult.confirm();
                return false;
            }
        }).show();
        return true;
    }

    public boolean onJsConfirm(HybridView hybridView, String str, String str2, final JsResult jsResult) {
        new AlertDialog.Builder(this.mManager.getActivity()).setMessage(str2).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            /* class miui.hybrid.HybridChromeClient.AnonymousClass7 */

            public void onClick(DialogInterface dialogInterface, int i) {
                jsResult.confirm();
            }
        }).setNegativeButton(17039360, new DialogInterface.OnClickListener() {
            /* class miui.hybrid.HybridChromeClient.AnonymousClass6 */

            public void onClick(DialogInterface dialogInterface, int i) {
                jsResult.cancel();
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            /* class miui.hybrid.HybridChromeClient.AnonymousClass5 */

            public void onCancel(DialogInterface dialogInterface) {
                jsResult.cancel();
            }
        }).setOnKeyListener(new DialogInterface.OnKeyListener() {
            /* class miui.hybrid.HybridChromeClient.AnonymousClass4 */

            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i != 4) {
                    return true;
                }
                jsResult.confirm();
                return false;
            }
        }).show();
        return true;
    }

    public void onProgressChanged(HybridView hybridView, int i) {
        hybridView.setProgress(i);
    }

    public void onReceivedTitle(HybridView hybridView, String str) {
    }

    public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
        valueCallback.onReceiveValue(null);
    }

    public void setHybridManager(HybridManager hybridManager) {
        this.mManager = hybridManager;
    }
}
