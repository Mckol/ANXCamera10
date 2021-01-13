package com.android.camera.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.android.camera.data.DataRepository;
import com.android.camera.data.backup.DataBackUp;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.extra.DataItemLive;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.CameraCapabilities;
import io.reactivex.Completable;
import java.util.List;

public class DispatchFragment extends BaseFragment {
    private static final String TAG = "FragmentDispatch";

    private void reInitData(int i, int i2, boolean z, int i3) {
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        DataItemLive dataItemLive = DataRepository.dataItemLive();
        DataBackUp backUp = DataRepository.getInstance().backUp();
        DataRepository.dataItemLive().resetMimojiIfNeed(i);
        if (i3 == 3 || i3 == 6) {
            DataRepository.dataItemLive().clearAll();
            dataItemRunning.clearArrayMap();
            backUp.clearBackUp();
        } else if (dataItemRunning.getBackupKey() > 0) {
            backUp.backupRunning(dataItemRunning, dataItemRunning.getBackupKey());
        }
        backUp.revertOrCreateRunning(dataItemRunning, dataItemGlobal.getDataBackUpKey(i, dataItemGlobal.getCurrentCameraId()));
        dataItemRunning.reInitSupport(i, i2);
        CameraCapabilities capabilitiesByBogusCameraId = Camera2DataContainer.getInstance().getCapabilitiesByBogusCameraId(i2, i);
        if (capabilitiesByBogusCameraId != null) {
            dataItemRunning.reInitComponent(i, i2, capabilitiesByBogusCameraId, z);
            dataItemConfig.reInitComponent(i, i2, capabilitiesByBogusCameraId);
            dataItemLive.reInitComponent(i, i2, capabilitiesByBogusCameraId);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 13;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return 0;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public boolean needViewClear() {
        return true;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        Log.d(TAG, "frameAvailable");
        reCheck();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.mCurrentMode = DataRepository.dataItemGlobal().getCurrentMode();
        provideAnimateElement(this.mCurrentMode, null, 2);
        return null;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        Log.d(TAG, "reInit");
        reInitData(this.mCurrentMode, DataRepository.dataItemGlobal().getCurrentCameraId(), DataRepository.dataItemGlobal().isNormalIntent(), i2);
    }

    public void reCheck() {
        ModeProtocol.ConfigChanges configChanges;
        int i = this.mCurrentMode;
        if (i != 254 && i != 209 && i != 210 && (configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)) != null) {
            configChanges.reCheckMutexConfigs(this.mCurrentMode);
            configChanges.reCheckFocusPeakConfig();
            configChanges.reCheckExposureFeedbackConfig();
            configChanges.reCheckParameterResetTip(true);
            configChanges.reCheckParameterDescriptionTip();
            configChanges.reCheckRaw();
            configChanges.reCheckDocumentMode();
            configChanges.reCheckGradienter();
            configChanges.reCheckVideoUltraClearTip();
            configChanges.reCheckVideoHDR10Tip();
            configChanges.reCheckVideoLog();
            configChanges.reCheckAIWatermark(false);
            configChanges.reConfigSpeechShutter();
            configChanges.recheckVideoFps(true);
            configChanges.recheckFunAR();
            configChanges.reCheckFastMotion(true);
        }
    }
}
