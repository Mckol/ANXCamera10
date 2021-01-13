package com.android.camera.module.loader;

import android.hardware.camera2.CaptureResult;
import b.c.a.c;
import com.android.camera.module.BaseModule;
import com.android.camera.module.VideoModule;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.CaptureResultParser;
import io.reactivex.functions.Function;
import java.lang.ref.WeakReference;

public class FunctionParseHistogramStats implements Function<CaptureResult, int[]> {
    private WeakReference<BaseModule> mModule;
    private int[] mStats = new int[0];
    private WeakReference<ModeProtocol.TopAlert> mTopAlertProtocolWeakReference;

    public FunctionParseHistogramStats(BaseModule baseModule, ModeProtocol.TopAlert topAlert) {
        this.mModule = new WeakReference<>(baseModule);
        this.mTopAlertProtocolWeakReference = new WeakReference<>(topAlert);
    }

    public int[] apply(CaptureResult captureResult) throws Exception {
        WeakReference<ModeProtocol.TopAlert> weakReference = this.mTopAlertProtocolWeakReference;
        if (weakReference == null) {
            return this.mStats;
        }
        ModeProtocol.TopAlert topAlert = weakReference.get();
        if (topAlert == null) {
            return this.mStats;
        }
        if (this.mModule.get() == null) {
            return this.mStats;
        }
        if (!(this.mModule.get() instanceof VideoModule)) {
            return this.mStats;
        }
        VideoModule videoModule = (VideoModule) this.mModule.get();
        if (c.isMTKPlatform() && !CaptureResultParser.isHistogramStatsEnabled(videoModule.getCameraCapabilities(), captureResult)) {
            return this.mStats;
        }
        int[] histogramStats = CaptureResultParser.getHistogramStats(captureResult);
        if (histogramStats != null) {
            this.mStats = histogramStats;
            int[] iArr = new int[256];
            for (int i = 0; i < 256; i++) {
                iArr[i] = this.mStats[c.isMTKPlatform() ? i : i * 3];
            }
            topAlert.updateHistogramStatsData(iArr);
        }
        return this.mStats;
    }
}
