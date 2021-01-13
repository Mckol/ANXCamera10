package com.android.camera.network.net.base;

public abstract class SimpleResponseListener<T> implements ResponseListener {
    public abstract void onResponse(T t);

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: com.android.camera.network.net.base.SimpleResponseListener<T> */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.camera.network.net.base.ResponseListener
    public final void onResponse(Object... objArr) {
        if (objArr == null || objArr.length <= 0) {
            onResponse((Object) null);
        } else {
            onResponse(objArr[0]);
        }
    }

    @Override // com.android.camera.network.net.base.ResponseListener
    public void onResponseError(ErrorCode errorCode, String str, Object obj) {
    }
}
