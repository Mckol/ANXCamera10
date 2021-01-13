package com.android.camera.module.loader;

import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.module.BaseModule;
import io.reactivex.annotations.NonNull;

public class FunctionResumeModule extends Func1Base<BaseModule, BaseModule> {
    public FunctionResumeModule(int i) {
        super(i);
    }

    public NullHolder<BaseModule> apply(@NonNull NullHolder<BaseModule> nullHolder) throws Exception {
        if (!nullHolder.isPresent()) {
            return nullHolder;
        }
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        DataRepository.getInstance().backUp().revertOrCreateRunning(DataRepository.dataItemRunning(), dataItemGlobal.getDataBackUpKey(this.mTargetMode, dataItemGlobal.getCurrentCameraId()));
        BaseModule baseModule = nullHolder.get();
        baseModule.onCreate(this.mTargetMode, dataItemGlobal.getCurrentCameraId());
        baseModule.onResume();
        baseModule.registerProtocol();
        return nullHolder;
    }
}
