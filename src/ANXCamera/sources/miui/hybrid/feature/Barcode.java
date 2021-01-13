package miui.hybrid.feature;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import java.util.Map;
import miui.hybrid.HybridFeature;
import miui.hybrid.LifecycleListener;
import miui.hybrid.NativeInterface;
import miui.hybrid.Request;
import miui.hybrid.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class Barcode implements HybridFeature {
    private static final String ACTION_SCAN_BARCODE = "scan";
    private static final String INTENT_ACTION_SCAN_BARCODE = "android.intent.action.scanbarcode";
    private static final String INTENT_CATEGORY_SYSAPP_TOOL = "miui.intent.category.SYSAPP_TOOL";
    private static final String INTENT_EXTRA_IS_BACKTO_THIRDAPP = "isBackToThirdApp";
    private static final String INTENT_EXTRA_RESULT = "result";
    private static final String INTENT_EXTRA_TYPE = "type";
    private static final String KEY_RESULT = "result";
    private static final String KEY_TYPE = "type";
    private static final int REQUEST_SCAN_BARCODE = "REQUEST_SCAN_BARCODE".hashCode();

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private JSONObject makeResult(Intent intent) {
        if (intent == null) {
            return null;
        }
        int intExtra = intent.getIntExtra("type", -1);
        String stringExtra = intent.getStringExtra("result");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", intExtra);
            jSONObject.put("result", stringExtra);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    @Override // miui.hybrid.HybridFeature
    public HybridFeature.Mode getInvocationMode(Request request) {
        if (TextUtils.equals(request.getAction(), ACTION_SCAN_BARCODE)) {
            return HybridFeature.Mode.CALLBACK;
        }
        return null;
    }

    @Override // miui.hybrid.HybridFeature
    public Response invoke(final Request request) {
        if (!TextUtils.equals(request.getAction(), ACTION_SCAN_BARCODE)) {
            return new Response(204, "no such action");
        }
        final NativeInterface nativeInterface = request.getNativeInterface();
        Activity activity = nativeInterface.getActivity();
        Intent intent = new Intent(INTENT_ACTION_SCAN_BARCODE);
        intent.addCategory(INTENT_CATEGORY_SYSAPP_TOOL);
        intent.putExtra(INTENT_EXTRA_IS_BACKTO_THIRDAPP, true);
        if (activity.getPackageManager().resolveActivity(intent, 0) == null) {
            request.getCallback().callback(new Response(204, "can't find barcode scanner activity"));
            return null;
        }
        nativeInterface.addLifecycleListener(new LifecycleListener() {
            /* class miui.hybrid.feature.Barcode.AnonymousClass1 */

            @Override // miui.hybrid.LifecycleListener
            public void onActivityResult(int i, int i2, Intent intent) {
                if (i == Barcode.REQUEST_SCAN_BARCODE) {
                    nativeInterface.removeLifecycleListener(this);
                    request.getCallback().callback(i2 == -1 ? new Response(0, Barcode.this.makeResult(intent)) : i2 == 0 ? new Response(100) : new Response(200));
                }
            }
        });
        activity.startActivityForResult(intent, REQUEST_SCAN_BARCODE);
        return null;
    }

    @Override // miui.hybrid.HybridFeature
    public void setParams(Map<String, String> map) {
    }
}
