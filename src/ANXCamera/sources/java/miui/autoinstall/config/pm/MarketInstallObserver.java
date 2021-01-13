package java.miui.autoinstall.config.pm;

import android.os.Bundle;
import android.os.ResultReceiver;

public class MarketInstallObserver extends ResultReceiver {
    private static final int Aa = 0;
    private static final int Ba = 1;
    private static final int Ca = 2;
    private static final String Da = "returnCode";
    private static final String KEY_PACKAGE_NAME = "packageName";
    private final a mListener;

    public static class a implements a {
        private final ResultReceiver mRemote;

        public a(ResultReceiver resultReceiver) {
            this.mRemote = resultReceiver;
        }

        @Override // java.miui.autoinstall.config.pm.a
        public void onRefuseInstall(String str, int i) {
            this.mRemote.send(1, MarketInstallObserver.h(str, i));
        }

        @Override // java.miui.autoinstall.config.pm.a
        public void onServiceDead() {
            this.mRemote.send(2, null);
        }

        @Override // java.miui.autoinstall.config.pm.a
        public void packageInstalled(String str, int i) {
            this.mRemote.send(0, MarketInstallObserver.h(str, i));
        }
    }

    public MarketInstallObserver(a aVar) {
        super(null);
        this.mListener = aVar;
    }

    private static int a(Bundle bundle) {
        return bundle.getInt(Da);
    }

    private static String b(Bundle bundle) {
        return bundle.getString(KEY_PACKAGE_NAME);
    }

    /* access modifiers changed from: private */
    public static Bundle h(String str, int i) {
        Bundle bundle = new Bundle(2);
        bundle.putString(KEY_PACKAGE_NAME, str);
        bundle.putInt(Da, i);
        return bundle;
    }

    /* access modifiers changed from: protected */
    public void onReceiveResult(int i, Bundle bundle) {
        super.onReceiveResult(i, bundle);
        a aVar = this.mListener;
        if (aVar == null) {
            return;
        }
        if (i == 0) {
            aVar.packageInstalled(b(bundle), a(bundle));
        } else if (i == 1) {
            aVar.onRefuseInstall(b(bundle), a(bundle));
        } else if (i == 2) {
            aVar.onServiceDead();
        }
    }
}
