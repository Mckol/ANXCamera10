package miui.telephony;

import android.content.Context;
import android.telephony.TelephonyManager;
import miui.net.ConnectivityHelper;
import miui.util.AppConstants;
import miui.util.SoftReferenceSingleton;

public class TelephonyHelper {
    private static final SoftReferenceSingleton<TelephonyHelper> INSTANCE = new SoftReferenceSingleton<TelephonyHelper>() {
        /* class miui.telephony.TelephonyHelper.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // miui.util.SoftReferenceSingleton
        public TelephonyHelper createInstance() {
            return new TelephonyHelper();
        }
    };
    private Context mContext;
    private String mDeviceId;
    private TelephonyManager mTelephonyManager;

    private TelephonyHelper() {
        this.mContext = AppConstants.getCurrentApplication();
        this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
    }

    public static TelephonyHelper getInstance() {
        return INSTANCE.get();
    }

    public String getDeviceId() {
        String str = this.mDeviceId;
        if (str != null) {
            return str;
        }
        this.mDeviceId = this.mTelephonyManager.getDeviceId();
        String str2 = this.mDeviceId;
        if (str2 != null) {
            return str2;
        }
        if (ConnectivityHelper.getInstance().isWifiOnly()) {
            this.mDeviceId = ConnectivityHelper.getInstance().getMacAddress();
        }
        return this.mDeviceId;
    }
}
