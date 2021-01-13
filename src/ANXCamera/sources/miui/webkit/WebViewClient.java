package miui.webkit;

import android.graphics.Bitmap;
import android.webkit.WebView;
import com.miui.internal.webkit.WebViewClientDelegate;
import com.miui.internal.webkit.WebViewWrapper;

public class WebViewClient extends android.webkit.WebViewClient {
    public static final int FEATURE_AUTO_LOGIN = 2;
    public static final int FEATURE_DEEP_LINK = 1;
    private WebViewClientDelegate mDelegate;

    public WebViewClient() {
        this.mDelegate = new WebViewClientDelegate();
    }

    public WebViewClient(int i) {
        this.mDelegate = new WebViewClientDelegate(i);
    }

    public WebViewClient(int i, int i2) {
        this.mDelegate = new WebViewClientDelegate(i, i2);
    }

    public void onPageFinished(WebView webView, String str) {
        this.mDelegate.onPageFinished(new WebViewWrapper(webView), str);
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        this.mDelegate.onPageStarted(new WebViewWrapper(webView), str, bitmap);
    }

    public void onReceivedLoginRequest(WebView webView, String str, String str2, String str3) {
        this.mDelegate.onReceivedLoginRequest(new WebViewWrapper(webView), str, str2, str3);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        return this.mDelegate.shouldOverrideUrlLoading(new WebViewWrapper(webView), str);
    }
}
