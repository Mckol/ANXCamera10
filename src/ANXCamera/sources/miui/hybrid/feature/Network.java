package miui.hybrid.feature;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import java.util.Map;
import miui.hybrid.Callback;
import miui.hybrid.HybridFeature;
import miui.hybrid.LifecycleListener;
import miui.hybrid.NativeInterface;
import miui.hybrid.Request;
import miui.hybrid.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class Network implements HybridFeature {
    private static final String ACTION_DISABLE_NOTIFICATION = "disableNotification";
    private static final String ACTION_ENABLE_NOTIFICATION = "enableNotification";
    private static final String ACTION_GET_TYPE = "getType";
    private static final String KEY_CONNECTED = "connected";
    private static final String KEY_METERED = "metered";
    private static final String LOG_TAG = "Network";
    private Callback mCallback;
    private IntentFilter mFilter = new IntentFilter();
    private LifecycleListener mLifecycleListener;
    private NetworkStateReceiver mReceiver;

    /* access modifiers changed from: private */
    public class NetworkStateReceiver extends BroadcastReceiver {
        private NetworkStateReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                boolean z = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED;
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(Network.KEY_CONNECTED, z);
                    Network.this.mCallback.callback(new Response(jSONObject));
                } catch (JSONException e2) {
                    Log.e(Network.LOG_TAG, e2.getMessage());
                }
            }
        }
    }

    public Network() {
        this.mFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    }

    private Response disableNotification(Request request) {
        unregisterNotification(request.getNativeInterface());
        return new Response(100);
    }

    private Response enableNotification(Request request) {
        final NativeInterface nativeInterface = request.getNativeInterface();
        unregisterNotification(nativeInterface);
        Activity activity = nativeInterface.getActivity();
        this.mCallback = request.getCallback();
        this.mReceiver = new NetworkStateReceiver();
        activity.registerReceiver(this.mReceiver, this.mFilter);
        this.mLifecycleListener = new LifecycleListener() {
            /* class miui.hybrid.feature.Network.AnonymousClass1 */

            private void unregister() {
                Network.this.unregisterNotification(nativeInterface);
                Network.this.mCallback.callback(new Response(100));
            }

            @Override // miui.hybrid.LifecycleListener
            public void onDestroy() {
                unregister();
            }

            @Override // miui.hybrid.LifecycleListener
            public void onPageChange() {
                unregister();
            }
        };
        nativeInterface.addLifecycleListener(this.mLifecycleListener);
        return null;
    }

    private Response isMetered(Request request) {
        boolean isActiveNetworkMetered = ((ConnectivityManager) request.getNativeInterface().getActivity().getSystemService("connectivity")).isActiveNetworkMetered();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(KEY_METERED, isActiveNetworkMetered);
        } catch (JSONException e2) {
            Log.e(LOG_TAG, e2.getMessage());
        }
        return new Response(jSONObject);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void unregisterNotification(NativeInterface nativeInterface) {
        if (this.mReceiver != null) {
            Activity activity = nativeInterface.getActivity();
            nativeInterface.removeLifecycleListener(this.mLifecycleListener);
            activity.unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
    }

    @Override // miui.hybrid.HybridFeature
    public HybridFeature.Mode getInvocationMode(Request request) {
        String action = request.getAction();
        if (ACTION_GET_TYPE.equals(action)) {
            return HybridFeature.Mode.SYNC;
        }
        if (ACTION_ENABLE_NOTIFICATION.equals(action)) {
            return HybridFeature.Mode.CALLBACK;
        }
        if (ACTION_DISABLE_NOTIFICATION.equals(action)) {
            return HybridFeature.Mode.SYNC;
        }
        return null;
    }

    @Override // miui.hybrid.HybridFeature
    public Response invoke(Request request) {
        String action = request.getAction();
        return ACTION_GET_TYPE.equals(action) ? isMetered(request) : ACTION_ENABLE_NOTIFICATION.equals(action) ? enableNotification(request) : ACTION_DISABLE_NOTIFICATION.equals(action) ? disableNotification(request) : new Response(204, "no such action");
    }

    @Override // miui.hybrid.HybridFeature
    public void setParams(Map<String, String> map) {
    }
}
