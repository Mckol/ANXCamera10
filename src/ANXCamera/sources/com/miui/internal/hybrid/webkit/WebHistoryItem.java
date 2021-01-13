package com.miui.internal.hybrid.webkit;

import android.graphics.Bitmap;
import miui.hybrid.HybridHistoryItem;

public class WebHistoryItem extends HybridHistoryItem {
    private android.webkit.WebHistoryItem mWebHistoryItem;

    public WebHistoryItem(android.webkit.WebHistoryItem webHistoryItem) {
        this.mWebHistoryItem = webHistoryItem;
    }

    @Override // miui.hybrid.HybridHistoryItem
    public Bitmap getFavicon() {
        return this.mWebHistoryItem.getFavicon();
    }

    @Override // miui.hybrid.HybridHistoryItem
    public String getOriginalUrl() {
        return this.mWebHistoryItem.getOriginalUrl();
    }

    @Override // miui.hybrid.HybridHistoryItem
    public String getTitle() {
        return this.mWebHistoryItem.getTitle();
    }

    @Override // miui.hybrid.HybridHistoryItem
    public String getUrl() {
        return this.mWebHistoryItem.getUrl();
    }
}
