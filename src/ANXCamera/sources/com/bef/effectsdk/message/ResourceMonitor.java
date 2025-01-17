package com.bef.effectsdk.message;

import android.os.Message;
import android.text.TextUtils;

public class ResourceMonitor implements MessageInterceptor {
    private static final String TAG = "ResourceMonitor";
    private Listener mListener;

    public interface Listener {
        void onResourceLoadFinish(String str, boolean z);
    }

    @Override // com.bef.effectsdk.message.MessageInterceptor
    public void destroy() {
        this.mListener = null;
    }

    @Override // com.bef.effectsdk.message.MessageInterceptor
    public boolean intercept(Message message) {
        Listener listener;
        if (17 != message.what) {
            return false;
        }
        String str = (String) message.obj;
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        int i = message.arg1;
        if (i == 3) {
            Listener listener2 = this.mListener;
            if (listener2 != null) {
                listener2.onResourceLoadFinish(str, true);
            }
        } else if (i == 4 && (listener = this.mListener) != null) {
            listener.onResourceLoadFinish(str, false);
        }
        return true;
    }

    public void reset() {
    }

    /* access modifiers changed from: package-private */
    public void setListener(Listener listener) {
        this.mListener = listener;
    }
}
