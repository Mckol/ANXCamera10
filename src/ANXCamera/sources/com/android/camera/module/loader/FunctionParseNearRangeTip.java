package com.android.camera.module.loader;

import android.hardware.camera2.CaptureResult;
import android.os.SystemProperties;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.Camera2Module;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.CaptureResultParser;
import io.reactivex.functions.Function;
import java.lang.ref.WeakReference;

public class FunctionParseNearRangeTip implements Function<CaptureResult, CaptureResult> {
    private static final boolean DEBUG;
    private static final String TAG = "FunctionParseNearRangeTip";
    private WeakReference<ModeProtocol.BottomPopupTips> mBottomPopupTips = new WeakReference<>((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175));
    private WeakReference<ModeProtocol.DualController> mDualController = new WeakReference<>((ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182));
    private WeakReference<BaseModule> mModule;

    static {
        boolean z = false;
        if (SystemProperties.getInt("near_range_dbg", 0) == 1) {
            z = true;
        }
        DEBUG = z;
    }

    public FunctionParseNearRangeTip(BaseModule baseModule) {
        this.mModule = new WeakReference<>(baseModule);
    }

    private void DEBUG(String str, String str2) {
        if (DEBUG) {
            Log.d(str, str2);
        }
    }

    public /* synthetic */ void Ud() {
        this.mBottomPopupTips.get().showNearRangeTip();
    }

    public /* synthetic */ void Vd() {
        this.mBottomPopupTips.get().hideNearRangeTip();
    }

    public CaptureResult apply(CaptureResult captureResult) throws Exception {
        boolean z;
        if (this.mModule.get() != null && (this.mModule.get() instanceof Camera2Module)) {
            Camera2Module camera2Module = (Camera2Module) this.mModule.get();
            boolean z2 = false;
            if (!camera2Module.getCameraCapabilities().supportNearRangeMode()) {
                camera2Module.setNearRangeModeUIStatus(false);
                camera2Module.setNearRangeMode(false);
                return captureResult;
            }
            boolean z3 = true;
            if (CaptureResultParser.getFallbackRoleId(camera2Module.getCameraCapabilities(), captureResult) != 1) {
                DEBUG(TAG, "NearRangeMode:Not satisfied <fallback role id UW>!");
                camera2Module.setNearRangeMode(false);
            } else {
                camera2Module.setNearRangeMode(true);
                if (camera2Module.getBogusCameraId() != 0) {
                    DEBUG(TAG, "NearRangeMode:Not satisfed <back facing>!");
                    z = false;
                } else {
                    z = true;
                }
                if (!camera2Module.isNeedNearRangeTip()) {
                    z = false;
                }
                if (!(this.mModule.get().getModuleIndex() == 163 || this.mModule.get().getModuleIndex() == 165)) {
                    DEBUG(TAG, "NearRangeMode:Not satisfed <capture mode>!");
                    z = false;
                }
                if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                    DEBUG(TAG, "NearRangeMode:Not satisfed <sat device>!");
                    z = false;
                }
                if (CameraSettings.isDocumentModeOn(camera2Module.getModuleIndex())) {
                    DEBUG(TAG, "NearRangeMode:Not satisfed <document mode>!");
                    z = false;
                }
                if (this.mDualController.get() != null && this.mDualController.get().isZoomPanelVisible()) {
                    DEBUG(TAG, "NearRangeMode:Not satisfed <zoom slide>!");
                    z = false;
                }
                ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
                    DEBUG(TAG, "NearRangeMode:Not satisfed <beauty panel>!");
                    z = false;
                }
                ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
                if (makeupProtocol == null || !makeupProtocol.isSeekBarVisible()) {
                    z3 = false;
                }
                if (z3) {
                    DEBUG(TAG, "NearRangeMode:Not satisfed <seek bar>!");
                } else {
                    z2 = z;
                }
            }
            if (z2) {
                if (this.mBottomPopupTips.get() != null && !this.mBottomPopupTips.get().isNearRangeTipShowing()) {
                    DEBUG(TAG, "NearRangeMode:Enter near range mode");
                    camera2Module.getHandler().post(new a(this));
                }
            } else if (this.mBottomPopupTips.get() != null && this.mBottomPopupTips.get().isNearRangeTipShowing()) {
                DEBUG(TAG, "NearRangeMode: hide near range mode tip");
                camera2Module.getHandler().post(new b(this));
            }
            camera2Module.setNearRangeModeUIStatus(z2);
        }
        return captureResult;
    }
}
