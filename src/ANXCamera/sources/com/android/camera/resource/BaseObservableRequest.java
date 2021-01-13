package com.android.camera.resource;

import android.content.Context;
import androidx.annotation.NonNull;
import com.android.camera.log.Log;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Scheduler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class BaseObservableRequest<T> implements ResponseListener<T> {
    protected ObservableEmitter mEmitter;
    private Observable<T> mOutPutObservable;

    @NonNull
    private final T create(@NonNull Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException unused) {
            Log.e("newInstanceError", "Cannot create an instance of " + cls);
            return null;
        } catch (IllegalAccessException unused2) {
            Log.e("newInstanceError", "Cannot create an instance of " + cls);
            return null;
        }
    }

    public /* synthetic */ void a(Class cls, ObservableEmitter observableEmitter) throws Exception {
        this.mEmitter = observableEmitter;
        scheduleRequest(this, create(cls));
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: java.lang.Object */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void a(Object obj, ObservableEmitter observableEmitter) throws Exception {
        this.mEmitter = observableEmitter;
        scheduleRequest(this, obj);
    }

    /* access modifiers changed from: protected */
    public Scheduler getWorkThread() {
        return null;
    }

    @Override // com.android.camera.resource.ResponseListener
    public void onResponse(T t, boolean z) {
        ObservableEmitter observableEmitter = this.mEmitter;
        if (observableEmitter != null) {
            if (!z) {
                observableEmitter.onNext(t);
            }
            this.mEmitter.onComplete();
        }
    }

    @Override // com.android.camera.resource.ResponseListener
    public void onResponseError(int i, String str, Object obj) {
        this.mEmitter.onError(new BaseRequestException(i, str));
    }

    /* access modifiers changed from: protected */
    public abstract void scheduleRequest(ResponseListener<T> responseListener, T t);

    public Observable<T> startObservable(@NonNull Class<T> cls) {
        this.mOutPutObservable = Observable.create(new b(this, cls));
        return this.mOutPutObservable;
    }

    public Observable<T> startObservable(@NonNull T t) {
        if (t != null) {
            this.mOutPutObservable = Observable.create(new a(this, t));
            return this.mOutPutObservable;
        }
        throw new IllegalArgumentException("no null!");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0049 A[SYNTHETIC, Splitter:B:29:0x0049] */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    public final void writeToCache(String str, Context context, String str2) {
        Throwable th;
        FileNotFoundException e2;
        IOException e3;
        File file = new File(context.getCacheDir(), str);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                fileOutputStream2.write(str2.getBytes());
                try {
                    fileOutputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            } catch (FileNotFoundException e5) {
                e2 = e5;
                fileOutputStream = fileOutputStream2;
                e2.printStackTrace();
                if (fileOutputStream == null) {
                    fileOutputStream.close();
                }
            } catch (IOException e6) {
                e3 = e6;
                fileOutputStream = fileOutputStream2;
                try {
                    e3.printStackTrace();
                    if (fileOutputStream == null) {
                        fileOutputStream.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileOutputStream != null) {
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e8) {
            e2 = e8;
            e2.printStackTrace();
            if (fileOutputStream == null) {
            }
        } catch (IOException e9) {
            e3 = e9;
            e3.printStackTrace();
            if (fileOutputStream == null) {
            }
        }
    }
}
