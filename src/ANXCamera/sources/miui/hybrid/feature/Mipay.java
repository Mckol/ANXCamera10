package miui.hybrid.feature;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.Map;
import miui.hybrid.HybridFeature;
import miui.hybrid.LifecycleListener;
import miui.hybrid.NativeInterface;
import miui.hybrid.Request;
import miui.hybrid.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class Mipay implements HybridFeature {
    private static final String ACTION_MIPAY_COUNTER = "com.xiaomi.action.MIPAY_PAY_ORDER";
    private static final String ACTION_PAY = "pay";
    private static final int ERROR_CODE_INVALID = -1;
    private static final String KEY_CODE = "code";
    private static final String KEY_EXTRA = "extra";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_ORDER = "order";
    private static final String KEY_ORDER_INFO = "orderInfo";
    private static final String KEY_RESULT = "result";
    private static final String PACKAGE_MIPAY_WALLET = "com.mipay.wallet";
    private static final int REQUEST_MIPAY = 20140424;

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private JSONObject makeResult(Intent intent) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(KEY_CODE, intent.getIntExtra(KEY_CODE, -1));
            String stringExtra = intent.getStringExtra(KEY_MESSAGE);
            if (!TextUtils.isEmpty(stringExtra)) {
                jSONObject.put(KEY_MESSAGE, stringExtra);
            }
            String stringExtra2 = intent.getStringExtra(KEY_RESULT);
            if (!TextUtils.isEmpty(stringExtra2)) {
                jSONObject.put(KEY_RESULT, stringExtra2);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    private Response pay(Activity activity, String str, Bundle bundle) {
        if (TextUtils.isEmpty(str)) {
            return new Response(204, "order cannot be empty");
        }
        Intent intent = new Intent(ACTION_MIPAY_COUNTER);
        intent.setPackage(PACKAGE_MIPAY_WALLET);
        if (activity.getPackageManager().resolveActivity(intent, 0) == null) {
            return new Response(204, "mipay feature not available");
        }
        intent.putExtra(KEY_ORDER, str);
        intent.putExtra(KEY_EXTRA, bundle);
        activity.startActivityForResult(intent, REQUEST_MIPAY);
        return null;
    }

    @Override // miui.hybrid.HybridFeature
    public HybridFeature.Mode getInvocationMode(Request request) {
        if (TextUtils.equals(request.getAction(), ACTION_PAY)) {
            return HybridFeature.Mode.CALLBACK;
        }
        return null;
    }

    @Override // miui.hybrid.HybridFeature
    public Response invoke(final Request request) {
        String str;
        if (!TextUtils.equals(request.getAction(), ACTION_PAY)) {
            return new Response(204, "no such action");
        }
        final NativeInterface nativeInterface = request.getNativeInterface();
        Activity activity = nativeInterface.getActivity();
        nativeInterface.addLifecycleListener(new LifecycleListener() {
            /* class miui.hybrid.feature.Mipay.AnonymousClass1 */

            @Override // miui.hybrid.LifecycleListener
            public void onActivityResult(int i, int i2, Intent intent) {
                if (i == Mipay.REQUEST_MIPAY) {
                    nativeInterface.removeLifecycleListener(this);
                    request.getCallback().callback(i2 == -1 ? new Response(0, Mipay.this.makeResult(intent)) : i2 == 0 ? new Response(100, Mipay.this.makeResult(intent)) : new Response(200));
                }
            }
        });
        try {
            str = new JSONObject(request.getRawParams()).getString(KEY_ORDER_INFO);
        } catch (JSONException e2) {
            e2.printStackTrace();
            str = null;
        }
        return pay(activity, str, null);
    }

    @Override // miui.hybrid.HybridFeature
    public void setParams(Map<String, String> map) {
    }
}
