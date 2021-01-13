package miui.autoinstall.config.entity;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestEntity {
    public List<RequestAppInfo> appInfo;
    public String nonceStr;
    public String sign;

    public static String list2Json(List<RequestAppInfo> list) {
        if (list == null) {
            return "";
        }
        JSONArray jSONArray = new JSONArray();
        for (RequestAppInfo requestAppInfo : list) {
            if (requestAppInfo != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("pn", requestAppInfo.pn);
                    jSONObject.put("pvc", requestAppInfo.pvc);
                    jSONArray.put(jSONObject);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return jSONArray.toString();
    }

    public String toString() {
        return "appInfo=" + list2Json(this.appInfo) + "&nonceStr=" + this.nonceStr + "&sign=" + this.sign;
    }
}
