package com.android.camera.dualvideo;

import android.content.res.Resources;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

public class DualVideoRenderProtocolImpl implements ModeProtocol.DualVideoRenderProtocol {
    private static final String TAG = "DualVideoRenderProtocol";
    private DualVideoControler mControler = new DualVideoControler();

    public DualVideoRenderProtocolImpl(Resources resources) {
        this.mControler.setmResources(resources);
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualVideoRenderProtocol
    public DualVideoControler getControler() {
        return this.mControler;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        Log.d(TAG, "registerProtocol: ");
        ModeCoordinatorImpl.getInstance().attachProtocol(430, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        Log.d(TAG, "unRegisterProtocol: ");
        if (DataRepository.dataItemFeature().tj()) {
            DataRepository.dataItemRunning().getComponentRunningDualVideo().setmShouldDraw6Patch(true);
        }
        DataRepository.dataItemRunning().getComponentRunningDualVideo().resetIds();
        ModeCoordinatorImpl.getInstance().detachProtocol(430, this);
    }
}
