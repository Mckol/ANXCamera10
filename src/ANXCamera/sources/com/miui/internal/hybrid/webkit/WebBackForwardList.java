package com.miui.internal.hybrid.webkit;

import miui.hybrid.HybridBackForwardList;
import miui.hybrid.HybridHistoryItem;

public class WebBackForwardList extends HybridBackForwardList {
    private android.webkit.WebBackForwardList mWebBackForwardList;

    public WebBackForwardList(android.webkit.WebBackForwardList webBackForwardList) {
        this.mWebBackForwardList = webBackForwardList;
    }

    @Override // miui.hybrid.HybridBackForwardList
    public int getCurrentIndex() {
        return this.mWebBackForwardList.getCurrentIndex();
    }

    @Override // miui.hybrid.HybridBackForwardList
    public HybridHistoryItem getCurrentItem() {
        return new WebHistoryItem(this.mWebBackForwardList.getCurrentItem());
    }

    @Override // miui.hybrid.HybridBackForwardList
    public HybridHistoryItem getItemAtIndex(int i) {
        return new WebHistoryItem(this.mWebBackForwardList.getItemAtIndex(i));
    }

    @Override // miui.hybrid.HybridBackForwardList
    public int getSize() {
        return this.mWebBackForwardList.getSize();
    }
}
