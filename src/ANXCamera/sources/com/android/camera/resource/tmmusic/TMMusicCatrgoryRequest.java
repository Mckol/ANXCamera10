package com.android.camera.resource.tmmusic;

import com.android.camera.CameraAppImpl;
import com.android.camera.resource.AESUtils;
import com.android.camera.resource.BaseRequestException;
import com.android.camera.resource.RequestHelper;
import com.android.camera.resource.SimpleNetworkJsonRequest;
import java.util.Random;
import miui.telephony.phonenumber.TelocationProvider;
import org.json.JSONException;
import org.json.JSONObject;

public class TMMusicCatrgoryRequest extends SimpleNetworkJsonRequest<TMMusicList> {
    private static final String APP_ID = "RM";
    private static final String BASE_URL = "https://sapi.tingmall.com/SkymanWS/Category/Items";

    public TMMusicCatrgoryRequest(String str) {
        super(BASE_URL);
        String encryString = AESUtils.getEncryString(APP_ID, RequestHelper.md5((long) new Random().nextInt(100), System.currentTimeMillis()).substring(0, 16), RequestHelper.getTMMusicAccessKey());
        addHeaders("oauth_token", APP_ID + encryString);
        addParam("offset", "0");
        addParam(TelocationProvider.Contract.Params.LENGTH, "20");
        addParam("categoryid", str);
    }

    /* access modifiers changed from: protected */
    public TMMusicList parseJson(JSONObject jSONObject, TMMusicList tMMusicList) throws BaseRequestException, JSONException {
        JSONObject optJSONObject = jSONObject.optJSONObject("response").optJSONObject("docs");
        writeToCache(TMMusicList.CACHE_LIST, CameraAppImpl.getAndroidContext(), optJSONObject.toString());
        tMMusicList.createResourcesList(optJSONObject);
        return tMMusicList;
    }
}
