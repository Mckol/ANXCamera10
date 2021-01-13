package com.android.camera.aiwatermark.handler;

import android.os.SystemProperties;
import com.android.camera.aiwatermark.data.AbstractWatermarkData;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.module.BaseModule;

public abstract class AbstractHandler {
    private static Boolean sDebugGPS;
    protected AbstractWatermarkData mData = null;
    private boolean mIsConsume;
    protected BaseModule mModule = null;
    private AbstractHandler mNextHandler;

    public AbstractHandler(boolean z) {
        this.mIsConsume = z;
    }

    protected static boolean debugGPS() {
        if (sDebugGPS == null) {
            sDebugGPS = Boolean.valueOf(SystemProperties.getBoolean("cam.app.use.debug.gps", false));
        }
        return sDebugGPS.booleanValue();
    }

    private WatermarkItem nextHandlerRequest() {
        AbstractHandler abstractHandler = this.mNextHandler;
        if (abstractHandler != null) {
            return abstractHandler.handleRequest();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract WatermarkItem findWatermark();

    public WatermarkItem handleRequest() {
        if (!this.mIsConsume) {
            return nextHandlerRequest();
        }
        WatermarkItem findWatermark = findWatermark();
        return findWatermark != null ? findWatermark : nextHandlerRequest();
    }

    public void setNextHandler(AbstractHandler abstractHandler) {
        this.mNextHandler = abstractHandler;
    }
}
