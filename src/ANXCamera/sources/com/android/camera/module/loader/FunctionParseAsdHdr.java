package com.android.camera.module.loader;

import android.hardware.camera2.CaptureResult;
import com.android.camera.log.Log;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CaptureResultParser;
import io.reactivex.functions.Function;
import java.lang.ref.WeakReference;

public class FunctionParseAsdHdr implements Function<CaptureResult, CaptureResult> {
    private static final String TAG = "FunctionParseAsdHdr";
    private WeakReference<Camera2Proxy.HdrCheckerCallback> mHdrCheckerCallback;
    private boolean mSupportHdrMotion;

    public FunctionParseAsdHdr(Camera2Proxy.HdrCheckerCallback hdrCheckerCallback, boolean z) {
        this.mHdrCheckerCallback = new WeakReference<>(hdrCheckerCallback);
        this.mSupportHdrMotion = z;
    }

    public CaptureResult apply(CaptureResult captureResult) throws Exception {
        boolean z;
        Camera2Proxy.HdrCheckerCallback hdrCheckerCallback = this.mHdrCheckerCallback.get();
        if (hdrCheckerCallback == null || !hdrCheckerCallback.isHdrSceneDetectionStarted()) {
            return captureResult;
        }
        boolean z2 = false;
        if (this.mSupportHdrMotion) {
            z = CaptureResultParser.isHdrMotionDetected(captureResult);
            hdrCheckerCallback.onHdrMotionDetectionResult(z);
        } else {
            z = false;
        }
        int hdrDetectedScene = CaptureResultParser.getHdrDetectedScene(captureResult);
        Log.c(TAG, "apply() called with: hdrMode = " + hdrDetectedScene + ", isMotionDetected = " + z);
        if (hdrDetectedScene == 1 && !z) {
            z2 = true;
        }
        hdrCheckerCallback.onHdrSceneChanged(z2);
        return captureResult;
    }
}
