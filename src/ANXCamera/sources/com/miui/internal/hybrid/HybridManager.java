package com.miui.internal.hybrid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import miui.hybrid.Callback;
import miui.hybrid.HybridChromeClient;
import miui.hybrid.HybridFeature;
import miui.hybrid.HybridSettings;
import miui.hybrid.HybridView;
import miui.hybrid.HybridViewClient;
import miui.hybrid.LifecycleListener;
import miui.hybrid.NativeInterface;
import miui.hybrid.PageContext;
import miui.hybrid.Request;
import miui.hybrid.Response;

public class HybridManager {
    public static final String TAG = "hybrid";
    private static ExecutorService sPool = Executors.newCachedThreadPool();
    private static String sUserAgent;
    private Activity mActivity;
    private boolean mDetached;
    private FeatureManager mFM;
    private Set<LifecycleListener> mListeners = new CopyOnWriteArraySet();
    private NativeInterface mNativeInterface;
    private PermissionManager mPM;
    private PageContext mPageContext;
    private ConcurrentHashMap<String, Request> mReqMap = new ConcurrentHashMap<>();
    private HybridView mView;

    /* access modifiers changed from: private */
    public static class AsyncInvocation implements Runnable {
        private HybridFeature mFeature;
        private WeakReference<HybridManager> mHybridManager;
        private String mJsCallback;
        private String mRequestKey;

        public AsyncInvocation(HybridManager hybridManager, HybridFeature hybridFeature, String str, String str2) {
            this.mHybridManager = new WeakReference<>(hybridManager);
            this.mFeature = hybridFeature;
            this.mRequestKey = str;
            this.mJsCallback = str2;
        }

        public void run() {
            Request request;
            HybridManager hybridManager = this.mHybridManager.get();
            if (hybridManager != null && (request = (Request) hybridManager.mReqMap.remove(this.mRequestKey)) != null && !hybridManager.getActivity().isFinishing() && !hybridManager.getActivity().isDestroyed()) {
                Response invoke = this.mFeature.invoke(request);
                if (this.mFeature.getInvocationMode(request) == HybridFeature.Mode.ASYNC) {
                    hybridManager.callback(invoke, request.getPageContext(), this.mJsCallback);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public class JsInvocation implements Runnable {
        private String mJsCallback;
        private Response mResponse;

        public JsInvocation(Response response, String str) {
            this.mResponse = response;
            this.mJsCallback = str;
        }

        public void run() {
            String buildCallbackJavascript = HybridManager.this.buildCallbackJavascript(this.mResponse, this.mJsCallback);
            HybridView hybridView = HybridManager.this.mView;
            hybridView.loadUrl("javascript:" + buildCallbackJavascript);
        }
    }

    public HybridManager(Activity activity, HybridView hybridView) {
        this.mActivity = activity;
        this.mView = hybridView;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String buildCallbackJavascript(Response response, String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return str + "('" + response.toString().replace("\\", "\\\\").replace("'", "\\'") + "');";
    }

    private HybridFeature buildFeature(String str) throws HybridException {
        if (this.mPM.isValid(this.mPageContext.getUrl())) {
            return this.mFM.lookupFeature(str);
        }
        throw new HybridException(203, "feature not permitted: " + str);
    }

    private Request buildRequest(String str, String str2, String str3) {
        Request request = new Request();
        request.setAction(str2);
        request.setRawParams(str3);
        request.setPageContext(this.mPageContext);
        request.setView(this.mView);
        request.setNativeInterface(this.mNativeInterface);
        return request;
    }

    private String config(Config config, boolean z) {
        if (z) {
            SecurityManager securityManager = new SecurityManager(config, this.mActivity.getApplicationContext());
            if (securityManager.isExpired() || !securityManager.isValidSignature()) {
                return new Response(202).toString();
            }
        }
        this.mFM = new FeatureManager(config, this.mActivity.getClassLoader());
        this.mPM = new PermissionManager(config);
        return new Response(0).toString();
    }

    private String getKey(String str, String str2, String str3, String str4) {
        return str + str2 + str3 + str4;
    }

    private static PackageInfo getPackageInfo(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 128);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private String getUserAgent(String str) {
        if (sUserAgent == null) {
            StringBuilder sb = new StringBuilder(str);
            sb.append(" XiaoMi/HybridView/");
            sb.append(getPackageInfo(this.mActivity, "com.miui.core").versionName);
            sb.append(" ");
            sb.append(this.mActivity.getPackageName());
            sb.append("/");
            Activity activity = this.mActivity;
            sb.append(getPackageInfo(activity, activity.getPackageName()).versionName);
            sUserAgent = sb.toString();
        }
        return sUserAgent;
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void initSettings(HybridSettings hybridSettings) {
        hybridSettings.setJavaScriptEnabled(true);
        hybridSettings.setUserAgentString(getUserAgent(hybridSettings.getUserAgentString()));
    }

    private void initView() {
        initSettings(this.mView.getSettings());
        this.mView.setHybridViewClient(new HybridViewClient());
        this.mView.setHybridChromeClient(new HybridChromeClient());
        this.mView.addJavascriptInterface(new JsInterface(this), JsInterface.INTERFACE_NAME);
        this.mView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            /* class com.miui.internal.hybrid.HybridManager.AnonymousClass1 */

            public void onViewAttachedToWindow(View view) {
                HybridManager.this.mDetached = false;
            }

            public void onViewDetachedFromWindow(View view) {
                HybridManager.this.mDetached = true;
            }
        });
    }

    private Config loadConfig(int i) {
        XmlConfigParser xmlConfigParser;
        if (i == 0) {
            try {
                xmlConfigParser = XmlConfigParser.create(this.mActivity);
            } catch (HybridException e2) {
                throw new RuntimeException("cannot load config: " + e2.getMessage());
            }
        } else {
            xmlConfigParser = XmlConfigParser.createFromResId(this.mActivity, i);
        }
        return xmlConfigParser.parse(null);
    }

    private String resolveUri(String str) {
        if (Pattern.compile("^[a-z-]+://").matcher(str).find()) {
            return str;
        }
        if (str.charAt(0) == '/') {
            str = str.substring(1);
        }
        return "file:///android_asset/hybrid/" + str;
    }

    public void addLifecycleListener(LifecycleListener lifecycleListener) {
        this.mListeners.add(lifecycleListener);
    }

    public void callback(Response response, PageContext pageContext, String str) {
        if (response != null && !TextUtils.isEmpty(str) && pageContext.equals(this.mPageContext) && !this.mDetached && !this.mActivity.isFinishing()) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "non-blocking response is " + response.toString());
            }
            this.mActivity.runOnUiThread(new JsInvocation(response, str));
        }
    }

    public void clear() {
        this.mReqMap.clear();
    }

    public String config(String str) {
        try {
            return config(JsonConfigParser.createFromString(str).parse(null), true);
        } catch (HybridException e2) {
            return new Response(201, e2.getMessage()).toString();
        }
    }

    public Activity getActivity() {
        return this.mActivity;
    }

    public HybridView getHybridView() {
        return this.mView;
    }

    public void init(int i, String str) {
        this.mNativeInterface = new NativeInterface(this);
        Config loadConfig = loadConfig(i);
        config(loadConfig, false);
        initView();
        if (str == null && !TextUtils.isEmpty(loadConfig.getContent())) {
            str = resolveUri(loadConfig.getContent());
        }
        if (str != null) {
            this.mView.loadUrl(str);
        }
    }

    public String invoke(String str, String str2, String str3, String str4) {
        try {
            HybridFeature buildFeature = buildFeature(str);
            Request buildRequest = buildRequest(str, str2, str3);
            HybridFeature.Mode invocationMode = buildFeature.getInvocationMode(buildRequest);
            if (invocationMode == HybridFeature.Mode.SYNC) {
                callback(new Response(1), this.mPageContext, str4);
                return buildFeature.invoke(buildRequest).toString();
            }
            String key = getKey(str, str2, str3, str4);
            this.mReqMap.put(key, buildRequest);
            if (invocationMode == HybridFeature.Mode.ASYNC) {
                sPool.execute(new AsyncInvocation(this, buildFeature, key, str4));
                return new Response(2).toString();
            }
            buildRequest.setCallback(new Callback(this, this.mPageContext, str4));
            sPool.execute(new AsyncInvocation(this, buildFeature, key, str4));
            return new Response(3).toString();
        } catch (HybridException e2) {
            Response response = e2.getResponse();
            callback(response, this.mPageContext, str4);
            return response.toString();
        }
    }

    public boolean isDetached() {
        return this.mDetached;
    }

    public String lookup(String str, String str2) {
        try {
            if (buildFeature(str).getInvocationMode(buildRequest(str, str2, null)) != null) {
                return new Response(0).toString();
            }
            return new Response(205, "action not supported: " + str2).toString();
        } catch (HybridException e2) {
            return e2.getResponse().toString();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        for (LifecycleListener lifecycleListener : this.mListeners) {
            lifecycleListener.onActivityResult(i, i2, intent);
        }
    }

    public void onDestroy() {
        for (LifecycleListener lifecycleListener : this.mListeners) {
            lifecycleListener.onDestroy();
        }
    }

    public void onPageChange() {
        for (LifecycleListener lifecycleListener : this.mListeners) {
            lifecycleListener.onPageChange();
        }
    }

    public void onPause() {
        for (LifecycleListener lifecycleListener : this.mListeners) {
            lifecycleListener.onPause();
        }
    }

    public void onResume() {
        for (LifecycleListener lifecycleListener : this.mListeners) {
            lifecycleListener.onResume();
        }
    }

    public void onStart() {
        for (LifecycleListener lifecycleListener : this.mListeners) {
            lifecycleListener.onStart();
        }
    }

    public void onStop() {
        for (LifecycleListener lifecycleListener : this.mListeners) {
            lifecycleListener.onStop();
        }
    }

    public void removeLifecycleListener(LifecycleListener lifecycleListener) {
        this.mListeners.remove(lifecycleListener);
    }

    public void setPageContext(PageContext pageContext) {
        this.mPageContext = pageContext;
    }
}
