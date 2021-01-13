package com.android.camera;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import miui.app.ActionBar;
import miui.app.Activity;

public class WebViewActivity extends Activity {
    private WebView mWebView;

    private void initWebPage(String str) {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.user_notice_title);
            this.mWebView = (WebView) findViewById(R.id.cta_webview);
            this.mWebView.getSettings().setJavaScriptEnabled(true);
            this.mWebView.setWebViewClient(new WebViewClient() {
                /* class com.android.camera.WebViewActivity.AnonymousClass1 */

                @Override // android.webkit.WebViewClient
                public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
                    String uri = Build.VERSION.SDK_INT >= 21 ? webResourceRequest.getUrl().toString() : webResourceRequest.toString();
                    if (!uri.startsWith("mailto:")) {
                        return false;
                    }
                    try {
                        WebViewActivity.this.startActivity(new Intent("android.intent.action.SENDTO", Uri.parse(uri)));
                        return true;
                    } catch (ActivityNotFoundException e2) {
                        e2.printStackTrace();
                        return true;
                    }
                }
            });
            this.mWebView.loadUrl(str);
        }
    }

    /* access modifiers changed from: protected */
    @Override // miui.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_cta_webview);
        if (getIntent().getBooleanExtra("StartActivityWhenLocked", false)) {
            setShowWhenLocked(true);
        }
        initWebPage(getIntent().getStringExtra(CameraIntentManager.EXTRA_CTA_WEBVIEW_LINK));
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return true;
        }
        if (!this.mWebView.canGoBack()) {
            super.onKeyDown(i, keyEvent);
            return true;
        } else if (this.mWebView.copyBackForwardList().getSize() < 2) {
            super.onKeyDown(i, keyEvent);
            return true;
        } else {
            this.mWebView.goBack();
            return true;
        }
    }
}
