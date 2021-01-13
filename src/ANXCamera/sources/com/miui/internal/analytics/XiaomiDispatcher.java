package com.miui.internal.analytics;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import miui.security.DigestUtils;
import miui.telephony.TelephonyHelper;
import miui.text.ExtraTextUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class XiaomiDispatcher implements Dispatchable {
    private static final String APP_PACKAGE = "_app_";
    private static final String DEVICE_ID = "_di_";
    private static final String EVENT_ID = "_ei_";
    private static final String EVENT_TRACK_TIME = "_ett_";
    private static final String EVENT_TYPE = "_et_";
    private static final String EVENT_VALUE = "_ev_";
    private static final String IMEI = "imei";
    private static final String LOGS = "_logs_";
    private static final String LOG_EVENT_CLASS = "_log_event_class_";
    private static final String LOG_EVENT_MESSAGE = "_log_event_message_";
    public static final int MAX_EVENT_NUM = 10000;
    private static final String MIUI_VERSION = "_miuiver_";
    private static final String MODEL = "_mod_";
    public static final String TAG = "xiaomi";
    private Map<String, Object> mBase = new HashMap();
    private List<JSONObject> mValues = new ArrayList();
    private XiaomiServer mXiaomiServer = XiaomiServer.getInstance();

    private Map<String, Object> getValue(Event event) {
        HashMap hashMap = new HashMap();
        hashMap.put(EVENT_ID, event.getEventId());
        hashMap.put(EVENT_TRACK_TIME, Long.valueOf(event.getTrackTime()));
        return hashMap;
    }

    private void sendEvent() {
        Map<String, Object> map;
        List<JSONObject> list = this.mValues;
        if (list != null && !list.isEmpty()) {
            if (!(this.mXiaomiServer == null || (map = this.mBase) == null)) {
                map.put(LOGS, new JSONArray((Collection) this.mValues));
                this.mXiaomiServer.send(this.mBase);
                this.mBase.remove(LOGS);
            }
            this.mValues.clear();
        }
    }

    @Override // com.miui.internal.analytics.Dispatchable
    public void dispatchEvent(TrackEvent trackEvent) {
        if (!(this.mXiaomiServer == null || this.mValues == null)) {
            Map<String, Object> value = getValue(trackEvent);
            Map<String, String> param = trackEvent.getParam();
            if (param != null) {
                for (String str : param.keySet()) {
                    value.put(str, param.get(str));
                }
            }
            value.put(EVENT_VALUE, Long.valueOf(trackEvent.getValue()));
            this.mValues.add(new JSONObject(value));
            if (this.mValues.size() >= 10000) {
                sendEvent();
            }
        }
    }

    @Override // com.miui.internal.analytics.Dispatchable
    public void dispatchLog(LogEvent logEvent) {
        if (this.mXiaomiServer != null) {
            Map<String, Object> value = getValue(logEvent);
            value.put(LOG_EVENT_CLASS, logEvent.getErrorClass());
            value.put(LOG_EVENT_MESSAGE, logEvent.getMessage());
            this.mXiaomiServer.send(value);
        }
    }

    @Override // com.miui.internal.analytics.Dispatchable
    public void dispatchPageView(TrackPageViewEvent trackPageViewEvent) {
        if (this.mXiaomiServer != null) {
            this.mXiaomiServer.send(getValue(trackPageViewEvent));
        }
    }

    @Override // com.miui.internal.analytics.Dispatchable
    public void start(Context context, String str) {
        String str2;
        this.mXiaomiServer.init();
        try {
            String deviceId = TelephonyHelper.getInstance().getDeviceId();
            String str3 = "null";
            if (TextUtils.isEmpty(deviceId)) {
                str2 = str3;
            } else {
                str3 = Base64.encodeToString(DigestUtils.get(deviceId, DigestUtils.ALGORITHM_SHA_1), 8).substring(0, 16);
                str2 = ExtraTextUtils.toHexReadable(DigestUtils.get(deviceId, DigestUtils.ALGORITHM_MD5));
            }
            this.mBase.put(DEVICE_ID, str3);
            this.mBase.put(IMEI, str2);
            this.mBase.put(APP_PACKAGE, str);
            this.mBase.put(EVENT_TYPE, 2);
            this.mBase.put(MIUI_VERSION, Build.VERSION.INCREMENTAL);
            this.mBase.put(MODEL, Build.MODEL);
        } catch (Exception unused) {
        }
    }

    @Override // com.miui.internal.analytics.Dispatchable
    public void stop() {
        sendEvent();
        this.mXiaomiServer.close();
    }
}
