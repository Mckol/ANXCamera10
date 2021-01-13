package com.android.camera.module.impl.component;

import android.view.KeyEvent;
import com.android.camera.ActivityBase;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import java.util.Optional;

public class KeyEventImpl implements ModeProtocol.KeyEvent {
    public static final int KEYCODE_MEDIA_PAUSE = 127;
    public static final int KEYCODE_MEDIA_PLAY = 126;
    public static final int KEYCODE_MODE_SWITCH = 259;
    public static final int KEYCODE_SHUTTER = 24;
    public static final int KEYCODE_SWITCH_LENS = 119;
    public static final int KEYCODE_ZOOM_IN = 168;
    public static final int KEYCODE_ZOOM_OUT = 169;
    private static final String TAG = "KeyEventImpl";
    private final ActivityBase mActivity;
    private boolean mIsZoomInDown;
    private boolean mIsZoomOutDown;
    private final boolean mSupportedFrontFPS120 = DataRepository.dataItemFeature().Ak();

    public KeyEventImpl(ActivityBase activityBase) {
        this.mActivity = activityBase;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    private void changeMode() {
        ModeProtocol.ModeChangeController modeChangeController;
        if (DataRepository.dataItemGlobal().isNormalIntent()) {
            Optional<BaseModule> baseModule = getBaseModule();
            if (baseModule.isPresent() && baseModule.get().isCreated() && !baseModule.get().isBlockSnap()) {
                int moduleIndex = baseModule.get().getModuleIndex();
                if (!(moduleIndex == 161 || moduleIndex == 162 || moduleIndex == 166 || moduleIndex == 172 || moduleIndex == 174 || moduleIndex == 176 || moduleIndex == 183)) {
                    switch (moduleIndex) {
                        case 179:
                        case 180:
                        case 181:
                            break;
                        default:
                            modeChangeController = (ModeProtocol.ModeChangeController) ModeCoordinatorImpl.getInstance().getAttachProtocol(179);
                            if (modeChangeController == null) {
                                int i = 163;
                                if (DataRepository.dataItemGlobal().getCurrentMode() == 163) {
                                    i = 162;
                                }
                                modeChangeController.changeModeByNewMode(i, 0);
                                return;
                            }
                            return;
                    }
                }
                if (baseModule.get().isRecording()) {
                    return;
                }
                modeChangeController = (ModeProtocol.ModeChangeController) ModeCoordinatorImpl.getInstance().getAttachProtocol(179);
                if (modeChangeController == null) {
                }
            }
        }
    }

    private void changeZoom(KeyEvent keyEvent, int i) {
        ModeProtocol.ZoomProtocol zoomProtocol;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && baseModule.get().isCreated() && baseModule.get().isZoomEnabled() && DataRepository.dataItemGlobal().getCurrentCameraId() != Camera2DataContainer.getInstance().getFrontCameraId()) {
            boolean z = DataRepository.dataItemFeature().z(baseModule.get().getModuleIndex());
            int repeatCount = keyEvent.getRepeatCount();
            if ((z && baseModule.get().isRecording() && repeatCount > 0) || this.mIsZoomInDown || this.mIsZoomOutDown) {
                if (keyEvent.getAction() == 0) {
                    if (i != 168) {
                        if (i == 169) {
                            if (!this.mIsZoomOutDown) {
                                this.mIsZoomOutDown = true;
                            } else {
                                return;
                            }
                        }
                    } else if (!this.mIsZoomInDown) {
                        this.mIsZoomInDown = true;
                    } else {
                        return;
                    }
                } else if (keyEvent.getAction() == 1) {
                    if (i == 168) {
                        this.mIsZoomInDown = false;
                    } else if (i == 169) {
                        this.mIsZoomOutDown = false;
                    }
                }
                ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
                if (mainContentProtocol != null) {
                    mainContentProtocol.onKeyEventSmoothZoom(i, keyEvent);
                }
            } else if (keyEvent.getAction() == 0 && (zoomProtocol = (ModeProtocol.ZoomProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(170)) != null) {
                if (i == 168) {
                    zoomProtocol.zoomIn(0.1f);
                } else if (i == 169) {
                    zoomProtocol.zoomOut(0.1f);
                }
            }
        }
    }

    public static ModeProtocol.KeyEvent create(ActivityBase activityBase) {
        return new KeyEventImpl(activityBase);
    }

    private Optional<BaseModule> getBaseModule() {
        ActivityBase activityBase = this.mActivity;
        return activityBase == null ? Optional.empty() : Optional.ofNullable((BaseModule) activityBase.getCurrentModule());
    }

    private void pauseRecording() {
        ModeProtocol.BottomMenuProtocol bottomMenuProtocol = (ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
        if (bottomMenuProtocol != null) {
            bottomMenuProtocol.pauseRecording();
        }
    }

    private void switchCameraLens() {
        ModeProtocol.BottomMenuProtocol bottomMenuProtocol;
        Optional<BaseModule> baseModule = getBaseModule();
        if (baseModule.isPresent() && baseModule.get().isCreated() && !baseModule.get().isBlockSnap()) {
            switch (baseModule.get().getModuleIndex()) {
                case 161:
                case 162:
                case 163:
                case 171:
                case 174:
                case 177:
                case 181:
                case 183:
                case 184:
                    break;
                case 164:
                case 165:
                case 167:
                case 168:
                case 170:
                case 175:
                case 178:
                case 179:
                case 180:
                case 182:
                default:
                    return;
                case 166:
                    if (!DataRepository.dataItemFeature().vk()) {
                        return;
                    }
                    break;
                case 169:
                    if (!DataRepository.dataItemFeature().Dj()) {
                        return;
                    }
                    break;
                case 172:
                    if (baseModule.get().isRecording() || !this.mSupportedFrontFPS120) {
                        return;
                    }
                case 173:
                    if (!DataRepository.dataItemFeature().yl() && !DataRepository.dataItemFeature().zl()) {
                        return;
                    }
                case 176:
                    if (!DataRepository.dataItemFeature().Dk()) {
                        return;
                    }
                    break;
            }
            if (!baseModule.get().isRecording() && (bottomMenuProtocol = (ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)) != null) {
                bottomMenuProtocol.onSwitchCameraPicker();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.KeyEvent
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (!baseModule.isPresent() || !baseModule.get().isCreated() || baseModule.get().isIgnoreTouchEvent()) {
            return false;
        }
        String str = TAG;
        Log.d(str, "KeyEventImpl-onKeyDown:" + i);
        if (i == 168) {
            changeZoom(keyEvent, 168);
            return true;
        } else if (i == 169) {
            changeZoom(keyEvent, 169);
            return true;
        } else if (i != 259) {
            return false;
        } else {
            changeMode();
            return true;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.KeyEvent
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        Optional<BaseModule> baseModule = getBaseModule();
        if (!baseModule.isPresent() || !baseModule.get().isCreated() || baseModule.get().isIgnoreTouchEvent()) {
            return false;
        }
        String str = TAG;
        Log.d(str, "KeyEventImpl-onKeyUp:" + i);
        if (i == 119) {
            switchCameraLens();
            return true;
        } else if (i == 126) {
            pauseRecording();
            return true;
        } else if (i == 127) {
            pauseRecording();
            return true;
        } else if (i == 168) {
            changeZoom(keyEvent, 168);
            return true;
        } else if (i != 169) {
            return false;
        } else {
            changeZoom(keyEvent, 169);
            return true;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(239, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.KeyEvent
    public void resetZoomKeyEvent() {
        this.mIsZoomOutDown = false;
        this.mIsZoomInDown = false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(239, this);
    }
}
