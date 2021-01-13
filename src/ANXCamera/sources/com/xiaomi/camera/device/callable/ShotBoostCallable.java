package com.xiaomi.camera.device.callable;

import androidx.annotation.NonNull;
import com.android.camera.log.Log;
import java.util.function.Consumer;

public class ShotBoostCallable extends CameraCallable {
    public static final String TAG = "ShotBoost";
    private Consumer<Boolean> mParam;
    private boolean mSuccess;

    public ShotBoostCallable(String str, boolean z, Consumer<Boolean> consumer) {
        super(str, null);
        this.mSuccess = z;
        this.mParam = consumer;
    }

    @Override // com.xiaomi.camera.device.callable.CameraCallable
    @NonNull
    public CallableReturn call() {
        return null;
    }

    /* access modifiers changed from: protected */
    @Override // com.xiaomi.camera.device.callable.CameraCallable
    public String getTag() {
        return TAG;
    }

    @Override // com.xiaomi.camera.device.callable.CameraCallable
    public void run() {
        if (this.mParam != null) {
            Log.d(TAG, "shotboost enable UI");
            this.mParam.accept(Boolean.valueOf(this.mSuccess));
        }
    }
}
