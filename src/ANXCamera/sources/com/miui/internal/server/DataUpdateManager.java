package com.miui.internal.server;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.google.android.apps.photos.api.PhotosOemApi;
import com.miui.internal.net.KeyValuePair;
import com.miui.internal.net.URLConnectionPostBuilder;
import com.miui.internal.util.DataUpdateUtils;
import com.miui.internal.util.DeviceHelper;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import miui.R;
import miui.util.AppConstants;
import miui.util.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataUpdateManager {
    public static final String ATTRIBUTES_NAME = "data-update";
    private static final String BASE_URL = (DeviceHelper.IS_ALPHA_BUILD ? STAGING_BASE_URL : FORMAL_BASE_URL);
    private static final long CHECK_UPDATE_INTERVAL = 604800000;
    public static final String CHECK_UPDATE_ONLY_WIFI_AVAILABLE = "check_update_only_wifi_available";
    public static final int CHECK_UPDATE_ONLY_WIFI_AVAILABLE_DEFAULT = 1;
    public static final String DATA_UPDATE_RECEIVE = "com.xiaomi.dataUpdate.RECEIVE";
    public static final String DATA_UPDATE_REGISTRATION = "com.xiaomi.dataUpdate.REGISTRATION";
    private static final boolean DBG = true;
    public static final String EXTRA_SERVICE_NAME = "service_name";
    public static final String EXTRA_WATER_MARK = "water_mark";
    private static final String FORMAL_BASE_URL = "aHR0cHM6Ly9hcGkuY29tbS5taXVpLmNvbQ==";
    private static final int HTTP_REQUEST_TIMEOUT_MS = 30000;
    public static final String LAST_UPDATE_TIME = "last_update_time";
    public static final String RECEIVER_META_DATA = "com.xiaomi.dataUpdate";
    private static final String SECRET = "f47c473853236172bf1a709714bda7a9";
    private static final String STAGING_BASE_URL = "aHR0cDovL3RyaWFsLmFwaS5jb21tLm1pdWkuY29t";
    private static final String TAG = "DataUpdateManager";
    public static final String URL = (getBaseUrl() + "/cspmisc/service/version");
    private Context mContext;
    private Map<String, String> mDataUpdateServices;
    private String mUserAgent;

    /* access modifiers changed from: private */
    public class DataUpdateResponse {
        public int mCode;
        public JSONObject mData;
        public String mDescription;
        public String mReason;
        public String mResult;

        public DataUpdateResponse(String str) throws JSONException {
            JSONObject jSONObject = new JSONObject(str);
            this.mResult = jSONObject.getString("result");
            this.mDescription = jSONObject.getString("description");
            this.mCode = jSONObject.getInt("code");
            if (this.mCode == 0) {
                this.mData = jSONObject.getJSONObject(PhotosOemApi.PATH_SPECIAL_TYPE_DATA);
            } else if (jSONObject.has("reason")) {
                this.mReason = jSONObject.getString("reason");
            }
        }

        public String toString() {
            if (this.mCode == 0) {
                return "{code:" + this.mCode + ", result:" + this.mResult + ", description:" + this.mDescription + ", data:" + this.mData.toString() + "}";
            }
            return "{code:" + this.mCode + ", result:" + this.mResult + ", reason:" + this.mReason + ", description:" + this.mDescription + "}";
        }
    }

    /* access modifiers changed from: private */
    public static class Holder {
        static final DataUpdateManager INSTANCE = new DataUpdateManager(AppConstants.getCurrentApplication());

        private Holder() {
        }
    }

    private DataUpdateManager(Context context) {
        this.mContext = context;
    }

    public static String getBaseUrl() {
        return DataUpdateUtils.decodeBase64(BASE_URL);
    }

    private String getContent() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("version", Build.VERSION.INCREMENTAL);
        String deviceId = ((TelephonyManager) this.mContext.getSystemService("phone")).getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = getOAID(this.mContext);
        }
        jSONObject.put("imei", deviceId.hashCode());
        JSONArray jSONArray = new JSONArray();
        for (String str : this.mDataUpdateServices.keySet()) {
            jSONArray.put(str);
        }
        jSONObject.put(PhotosOemApi.PATH_SPECIAL_TYPE_DATA, jSONArray);
        return jSONObject.toString();
    }

    public static DataUpdateManager getInstance() {
        return Holder.INSTANCE;
    }

    private String getOAID(Context context) {
        if (context == null) {
            return "";
        }
        try {
            Class<?> cls = Class.forName("com.android.id.IdentifierManager");
            Object invoke = cls.getMethod("getOAID", Context.class).invoke(cls.newInstance(), context);
            return invoke instanceof String ? (String) invoke : "";
        } catch (Exception e2) {
            Log.e(TAG, "invoke IdentifierManager for OAID error. ", e2);
            return "";
        }
    }

    private String getUserAgent() {
        if (this.mUserAgent == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(Build.MODEL);
            sb.append("; MIUI/");
            sb.append(Build.VERSION.INCREMENTAL);
            if (DeviceHelper.IS_ALPHA_BUILD) {
                sb.append(' ');
                sb.append("ALPHA");
            }
            this.mUserAgent = sb.toString();
        }
        return this.mUserAgent;
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0116 A[SYNTHETIC, Splitter:B:39:0x0116] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0124  */
    private String httpPost() throws JSONException, IOException {
        Throwable th;
        InputStream inputStream;
        HttpURLConnection httpURLConnection;
        Throwable th2;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("http.useragent", getUserAgent());
        linkedHashMap.put("Accept-Language", Locale.getDefault().toString());
        String content = getContent();
        Log.d(TAG, content);
        LinkedList linkedList = new LinkedList();
        HashMap hashMap = new HashMap();
        hashMap.put("content", content);
        linkedList.add(new KeyValuePair("content", content));
        String genUrlSign = DataUpdateUtils.genUrlSign(hashMap, this.mContext.getPackageName(), SECRET);
        linkedList.add(new KeyValuePair("appkey", this.mContext.getPackageName()));
        linkedList.add(new KeyValuePair("sign", genUrlSign));
        try {
            URLConnectionPostBuilder uRLConnectionPostBuilder = new URLConnectionPostBuilder(URL);
            httpURLConnection = uRLConnectionPostBuilder.setTimeout(30000).setHeadParams(linkedHashMap).setDoInputOutput(true).setRequestMethod("POST").build();
            try {
                uRLConnectionPostBuilder.post(linkedList);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    inputStream = httpURLConnection.getInputStream();
                    try {
                        String iOUtils = IOUtils.toString(inputStream);
                        if (iOUtils != null) {
                            JSONObject jSONObject = new JSONObject(iOUtils);
                            if (jSONObject.getString("result").equals("ok")) {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e2) {
                                        Log.e(TAG, e2.getMessage());
                                    }
                                }
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                }
                                return iOUtils;
                            } else if (jSONObject.getString("result").equals("error")) {
                                Log.i(TAG, "Failed to send to server, " + jSONObject.getString("description") + "," + jSONObject.getInt("code"));
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        if (inputStream != null) {
                        }
                        if (httpURLConnection != null) {
                        }
                        throw th;
                    }
                } else {
                    inputStream = null;
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        Log.e(TAG, e3.getMessage());
                    }
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return null;
            } catch (Throwable th4) {
                th2 = th4;
                inputStream = null;
                th = th2;
                if (inputStream != null) {
                }
                if (httpURLConnection != null) {
                }
                throw th;
            }
        } catch (Throwable th5) {
            th2 = th5;
            httpURLConnection = null;
            inputStream = null;
            th = th2;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    Log.e(TAG, e4.getMessage());
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            throw th;
        }
    }

    private boolean isCheckUpdateOnlyWifiAvailable() {
        return Settings.System.getInt(this.mContext.getContentResolver(), CHECK_UPDATE_ONLY_WIFI_AVAILABLE, 1) == 1;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && (!isCheckUpdateOnlyWifiAvailable() || !connectivityManager.isActiveNetworkMetered());
    }

    private boolean isUpdateExpired() {
        return Math.abs(System.currentTimeMillis() - PreferenceManager.getDefaultSharedPreferences(this.mContext).getLong(LAST_UPDATE_TIME, 0)) > CHECK_UPDATE_INTERVAL;
    }

    private synchronized void queryDataUpdateService() {
        if (this.mDataUpdateServices == null) {
            this.mDataUpdateServices = new HashMap();
            PackageManager packageManager = this.mContext.getPackageManager();
            for (ResolveInfo resolveInfo : packageManager.queryBroadcastReceivers(new Intent(DATA_UPDATE_REGISTRATION), 128)) {
                try {
                    ActivityInfo activityInfo = resolveInfo.activityInfo;
                    XmlResourceParser loadXmlMetaData = activityInfo.loadXmlMetaData(packageManager, RECEIVER_META_DATA);
                    AttributeSet asAttributeSet = Xml.asAttributeSet(loadXmlMetaData);
                    while (true) {
                        int next = loadXmlMetaData.next();
                        if (next == 1 || next == 2) {
                        }
                    }
                    if (!ATTRIBUTES_NAME.equals(loadXmlMetaData.getName())) {
                        Log.d(TAG, "Meta-data does not start with data-update tag");
                    }
                    TypedArray obtainAttributes = packageManager.getResourcesForApplication(activityInfo.applicationInfo).obtainAttributes(asAttributeSet, R.styleable.DataUpdate);
                    try {
                        for (String str : obtainAttributes.getString(R.styleable.DataUpdate_serviceName).split(",")) {
                            this.mDataUpdateServices.put(str.trim(), activityInfo.packageName);
                        }
                    } catch (IllegalArgumentException unused) {
                        Log.d(TAG, activityInfo.packageName + " micloud-push attrs error");
                    } catch (Throwable th) {
                        obtainAttributes.recycle();
                        throw th;
                    }
                    obtainAttributes.recycle();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            for (String str2 : this.mDataUpdateServices.keySet()) {
                Log.d(TAG, "{serviceName:" + str2 + ", packageName:" + this.mDataUpdateServices.get(str2) + "}");
            }
        }
    }

    private void tryUpdate() {
        queryDataUpdateService();
        if (this.mDataUpdateServices.size() != 0) {
            String str = null;
            try {
                str = httpPost();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (str != null) {
                try {
                    DataUpdateResponse dataUpdateResponse = new DataUpdateResponse(str);
                    Log.d(TAG, dataUpdateResponse.toString());
                    if (dataUpdateResponse.mCode == 0) {
                        for (String str2 : this.mDataUpdateServices.keySet()) {
                            if (dataUpdateResponse.mData.has(str2)) {
                                long j = dataUpdateResponse.mData.getLong(str2);
                                Intent intent = new Intent(DATA_UPDATE_RECEIVE);
                                intent.setPackage(this.mDataUpdateServices.get(str2));
                                intent.putExtra(EXTRA_WATER_MARK, j);
                                intent.putExtra(EXTRA_SERVICE_NAME, str2);
                                this.mContext.sendBroadcast(intent);
                                Log.d(TAG, "tryUpdate success -> Send DATA_UPDATE_RECEIVE");
                            }
                        }
                        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
                        long j2 = defaultSharedPreferences.getLong(LAST_UPDATE_TIME, 0);
                        if (j2 > 0) {
                            Log.d(TAG, "The distance last update time:" + (System.currentTimeMillis() - j2));
                        }
                        defaultSharedPreferences.edit().putLong(LAST_UPDATE_TIME, System.currentTimeMillis()).apply();
                    }
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public void update() {
        if (DeviceHelper.isDeviceProvisioned(this.mContext) && isUpdateExpired() && isNetworkAvailable()) {
            tryUpdate();
        }
    }
}
