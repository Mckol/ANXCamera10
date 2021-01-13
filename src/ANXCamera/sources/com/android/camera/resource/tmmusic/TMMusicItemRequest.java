package com.android.camera.resource.tmmusic;

import com.android.camera.fragment.music.LiveMusicInfo;
import com.android.camera.network.resource.RequestContracts;
import com.android.camera.resource.AESUtils;
import com.android.camera.resource.BaseRequestException;
import com.android.camera.resource.RequestHelper;
import com.android.camera.resource.SimpleNetworkJsonRequest;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

public class TMMusicItemRequest extends SimpleNetworkJsonRequest<LiveMusicInfo> {
    private static final String APP_ID = "RM";
    private static final String BASE_URL = "https://sapi.tingmall.com/SkymanWS/Streaming/MusicURL";

    public TMMusicItemRequest(String str) {
        super(BASE_URL);
        String encryString = AESUtils.getEncryString(APP_ID, RequestHelper.md5((long) new Random().nextInt(100), System.currentTimeMillis()).substring(0, 16), RequestHelper.getTMMusicAccessKey());
        addHeaders("oauth_token", APP_ID + encryString);
        addParam("itemid", str);
        addParam("subitemtype", "MP3-64K-FTD-P");
        addParam("identityid", RequestHelper.getIdentityID());
    }

    /* access modifiers changed from: protected */
    public LiveMusicInfo parseJson(JSONObject jSONObject, LiveMusicInfo liveMusicInfo) throws BaseRequestException, JSONException {
        liveMusicInfo.mPlayUrl = jSONObject.optJSONObject("response").optJSONObject("docs").optString(RequestContracts.Download.JSON_KEY_URL);
        return liveMusicInfo;
    }
}
