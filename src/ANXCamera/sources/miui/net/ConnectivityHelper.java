package miui.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.miui.internal.variable.Android_Net_ConnectivityManager_class;
import miui.util.AppConstants;
import miui.util.SoftReferenceSingleton;

public class ConnectivityHelper {
    private static final SoftReferenceSingleton<ConnectivityHelper> INSTANCE = new SoftReferenceSingleton<ConnectivityHelper>() {
        /* class miui.net.ConnectivityHelper.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // miui.util.SoftReferenceSingleton
        public ConnectivityHelper createInstance() {
            return new ConnectivityHelper();
        }
    };
    private static final String TAG = "ConnectivityHelper";
    private ConnectivityManager mConnectivityManager;
    private Context mContext;
    private String mMacAddress;
    private WifiManager mWifiManager;

    private ConnectivityHelper() {
        this.mContext = AppConstants.getCurrentApplication();
        this.mConnectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
    }

    public static ConnectivityHelper getInstance() {
        return INSTANCE.get();
    }

    public String getMacAddress() {
        String str = this.mMacAddress;
        if (str != null) {
            return str;
        }
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) this.mContext.getSystemService("wifi");
        }
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        if (connectionInfo != null) {
            this.mMacAddress = connectionInfo.getMacAddress();
        }
        return this.mMacAddress;
    }

    public ConnectivityManager getManager() {
        return this.mConnectivityManager;
    }

    public boolean isNetworkConnected() {
        NetworkInfo activeNetworkInfo = this.mConnectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean isUnmeteredNetworkConnected() {
        NetworkInfo activeNetworkInfo = this.mConnectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() && !this.mConnectivityManager.isActiveNetworkMetered();
    }

    public boolean isWifiConnected() {
        NetworkInfo activeNetworkInfo = this.mConnectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getType() == 1;
    }

    public boolean isWifiOnly() {
        return !Android_Net_ConnectivityManager_class.Factory.getInstance().get().isNetworkSupported(this.mConnectivityManager, 0);
    }
}
