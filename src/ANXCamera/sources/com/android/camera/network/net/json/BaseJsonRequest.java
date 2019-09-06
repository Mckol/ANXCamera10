package com.android.camera.network.net.json;

import com.android.camera.network.net.base.ErrorCode;
import com.android.camera.network.net.base.VolleyRequest;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public abstract class BaseJsonRequest<T> extends VolleyRequest<JSONObject, T> {
    private Map<String, String> mHeaders;
    private int mMethod = 1;
    private String mUrl = null;

    public BaseJsonRequest(int i, String str) {
        this.mMethod = i;
        this.mUrl = str;
    }

    private String appendUrlParams() {
        if (this.mUrl != null) {
            Map<String, String> map = this.mParams;
            if (map != null && !map.isEmpty()) {
                StringBuilder sb = new StringBuilder(this.mUrl);
                String str = "UTF-8";
                String str2 = "?";
                if (this.mUrl.indexOf(63) > 0) {
                    if (!this.mUrl.endsWith(str2)) {
                        String str3 = "&";
                        if (!this.mUrl.endsWith(str3)) {
                            sb.append(str3);
                        }
                    }
                    sb.append(encodeParameters(this.mParams, str));
                    return sb.toString();
                }
                sb.append(str2);
                sb.append(encodeParameters(this.mParams, str));
                return sb.toString();
            }
        }
        return this.mUrl;
    }

    private String encodeParameters(Map<String, String> map, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            for (Entry entry : map.entrySet()) {
                sb.append(URLEncoder.encode((String) entry.getKey(), str));
                sb.append('=');
                sb.append(URLEncoder.encode((String) entry.getValue(), str));
                sb.append('&');
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Encoding not supported: ");
            sb2.append(str);
            throw new RuntimeException(sb2.toString(), e2);
        }
    }

    public final void addHeader(String str, String str2) {
        if (this.mHeaders == null) {
            this.mHeaders = new HashMap();
        }
        this.mHeaders.put(str, str2);
    }

    /* access modifiers changed from: protected */
    public final Request<JSONObject> createVolleyRequest(Listener<JSONObject> listener, ErrorListener errorListener) {
        String str = this.mUrl;
        String appendUrlParams = appendUrlParams();
        if (this.mMethod == 0) {
            str = appendUrlParams;
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(this.mMethod, str, listener, errorListener);
        Map<String, String> map = this.mParams;
        if (map != null) {
            jsonObjectRequest.setParams(map);
        }
        Map<String, String> map2 = this.mHeaders;
        if (map2 != null) {
            jsonObjectRequest.setHeaders(map2);
        }
        jsonObjectRequest.setCacheKey(getCacheKey());
        return jsonObjectRequest;
    }

    public String getCacheKey() {
        return appendUrlParams();
    }

    public final String getUrl() {
        return this.mUrl;
    }

    public void onRequestError(ErrorCode errorCode, String str, Object obj) {
        deliverError(errorCode, str, obj);
    }

    /* access modifiers changed from: protected */
    public void onRequestSuccess(T t) throws Exception {
        deliverResponse(t);
    }

    public final void setUrl(String str) {
        this.mUrl = str;
    }
}
