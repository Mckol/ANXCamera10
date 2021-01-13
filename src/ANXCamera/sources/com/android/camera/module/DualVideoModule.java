package com.android.camera.module;

import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.CameraCaptureSession;
import android.os.Looper;
import android.util.Range;
import android.util.Size;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.core.view.ViewCompat;
import b.c.a.c;
import com.android.camera.AutoLockManager;
import com.android.camera.Camera;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningDualVideo;
import com.android.camera.dualvideo.CameraIDManager;
import com.android.camera.dualvideo.DualVideoControler;
import com.android.camera.dualvideo.TouchEventView;
import com.android.camera.dualvideo.UserSelectData;
import com.android.camera.dualvideo.render.DualVideoUtil;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.module.loader.camera2.FocusTask;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.gallery3d.ui.GLCanvas;
import com.xiaomi.camera.device.CameraHandlerThread;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DualVideoModule extends VideoModule {
    private static final String TAG;
    private ImageView mBottomControlView;
    private boolean mCameraSwitched;
    private volatile int mCurrentTapId;
    private Camera2Proxy.FocusCallback mFocusCallback = new K(this);
    private boolean mMainFrameisAvailable;
    private int mRecordCaptureTimes;
    private int mRecordPausedTimes;
    protected Camera2Proxy mSubCamera2Device;
    protected boolean mSubFocusAreaSupported;
    private FocusManager2 mSubFocusManager;
    protected boolean mSubMeteringAreaSupported;
    private ImageView mTopControlView;
    public TouchEventView mTouchEventView;

    public class MyDualVideoRenderListener implements DualVideoControler.DualVideoRenderListener {
        MyDualVideoRenderListener() {
            DualVideoModule.this = r1;
        }

        @Override // com.android.camera.dualvideo.DualVideoControler.DualVideoRenderListener
        public void onRenderRequestNeeded() {
            DualVideoModule.this.getActivity().getGLView().requestRender();
            Log.d(DualVideoModule.TAG, "onRenderRequestNeeded: ");
        }

        @Override // com.android.camera.dualvideo.DualVideoControler.DualVideoRenderListener
        public void onSubImageAvailable() {
        }
    }

    private void addViewForGestureRecongnize(ViewGroup viewGroup) {
        this.mTouchEventView = new TouchEventView(viewGroup.getContext());
        this.mTouchEventView.setListener(new O(this));
        viewGroup.addView(this.mTouchEventView, new RelativeLayout.LayoutParams(-1, -1));
    }

    private void addViewForPreviewCompose(ViewGroup viewGroup) {
        boolean isEqualPreview = isEqualPreview();
        Rect displayRect = Util.getDisplayRect(1);
        this.mTopControlView = new ImageView(viewGroup.getContext());
        if (isEqualPreview) {
            this.mTopControlView.setImageResource(R.drawable.ic_dual_video_preview_expand);
            this.mTopControlView.setBackgroundResource(R.drawable.ic_dual_video_preview_expand_shadow);
        } else {
            this.mTopControlView.setImageResource(R.drawable.ic_dual_video_preview_reduce);
            this.mTopControlView.setBackgroundResource(R.drawable.ic_dual_video_preview_reduce_shadow);
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.leftMargin = 10;
        layoutParams.topMargin = displayRect.top + 10;
        viewGroup.addView(this.mTopControlView, layoutParams);
        this.mBottomControlView = new ImageView(viewGroup.getContext());
        this.mBottomControlView.setImageResource(R.drawable.ic_dual_video_preview_expand);
        this.mBottomControlView.setBackgroundResource(R.drawable.ic_dual_video_preview_expand_shadow);
        if (!isEqualPreview) {
            this.mBottomControlView.setVisibility(4);
        }
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.leftMargin = 10;
        layoutParams2.topMargin = ((displayRect.top + displayRect.bottom) / 2) + 10;
        viewGroup.addView(this.mBottomControlView, layoutParams2);
        FolmeUtils.touchTint(this.mBottomControlView, this.mTopControlView);
        this.mTopControlView.setOnClickListener(new S(this));
        this.mBottomControlView.setOnClickListener(new M(this));
    }

    private void applyZooomForDevices(Camera2Proxy camera2Proxy) {
        if (camera2Proxy.getId() == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            camera2Proxy.setZoomRatio(1.0f);
        } else if (camera2Proxy.getId() == Camera2DataContainer.getInstance().getMainBackCameraId()) {
            camera2Proxy.setZoomRatio(1.0f);
        } else if (camera2Proxy.getId() == Camera2DataContainer.getInstance().getAuxCameraId()) {
            camera2Proxy.setZoomRatio(1.0f);
        } else if (camera2Proxy.getId() == Camera2DataContainer.getInstance().getUltraTeleCameraId()) {
            Iterator<UserSelectData> it = DataRepository.dataItemRunning().getComponentRunningDualVideo().getSelectedData().iterator();
            while (it.hasNext()) {
                if (it.next().m6PatchType == 24) {
                    camera2Proxy.setZoomRatio(2.0f);
                    return;
                }
            }
            camera2Proxy.setZoomRatio(1.0f);
        } else if (camera2Proxy.getId() == Camera2DataContainer.getInstance().getFrontCameraId()) {
            camera2Proxy.setZoomRatio(1.0f);
        } else {
            camera2Proxy.setZoomRatio(1.0f);
        }
    }

    static /* synthetic */ boolean e(UserSelectData userSelectData) {
        return userSelectData.mPreviewType == 11;
    }

    private Optional<DualVideoControler> getControler() {
        return this.mProtolRender.map(L.INSTANCE);
    }

    private String getZoomGroupForTrack() {
        ArrayList<UserSelectData> selectedData = DataRepository.dataItemRunning().getComponentRunningDualVideo().getSelectedData();
        selectedData.sort(T.INSTANCE);
        StringBuilder sb = new StringBuilder();
        Iterator<UserSelectData> it = selectedData.iterator();
        while (it.hasNext()) {
            sb.append(CameraIDManager.getZoomDescription(it.next().m6PatchType));
            sb.append(":");
        }
        return sb.toString();
    }

    private void handleBackStack() {
        ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
        if (backStack == null || backStack.handleBackStackFromKeyBack()) {
        }
    }

    private void initDualVideoControler() {
        getActivity().runOnUiThread(new Y(this, ((ModeProtocol.ModuleContent) ModeCoordinatorImpl.getInstance().getAttachProtocol(431)).getParent()));
    }

    private boolean is6PatchWindow() {
        return DataRepository.dataItemRunning().getComponentRunningDualVideo().ismShouldDraw6Patch();
    }

    private boolean isEqualPreview() {
        return DataRepository.dataItemRunning().getComponentRunningDualVideo().getmSelectedData().stream().anyMatch(V.INSTANCE);
    }

    private boolean isMultiCameraMode() {
        return DataRepository.dataItemFeature().tj();
    }

    private void registDualVideoRender() {
        if (ModeCoordinatorImpl.getInstance().getAttachProtocol(430) == null) {
            getActivity().getImplFactory().initModulePersistent(getActivity(), 430);
        }
        this.mProtolRender = Optional.ofNullable((ModeProtocol.DualVideoRenderProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(430));
        getControler().get().setListener(new MyDualVideoRenderListener());
        getActivity().getCameraScreenNail().setExternalFrameProcessor(getControler().get());
    }

    private void showModeSwitchLayout(boolean z) {
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing != null) {
            this.mActivity.runOnUiThread(new U(actionProcessing, z));
        }
    }

    private boolean switch6PatchToPreview() {
        Log.d(TAG, "switch6PatchToPreview: ");
        if (getControler().get().isSwitching() || !this.mMainFrameisAvailable) {
            return true;
        }
        if (!is6PatchWindow()) {
            return false;
        }
        switchThumbnailFunction(false);
        DataRepository.dataItemRunning().getComponentRunningDualVideo().setmShouldDraw6Patch(false);
        showModeSwitchLayout(false);
        getControler().get().switch6patch2preview();
        getControler().get().enableContinuousRender(true);
        getActivity().getGLView().requestRender();
        switchCameraLens(false, false, false);
        return true;
    }

    private void switchPreviewTo6Patch() {
        if (isMultiCameraMode() && !getControler().get().isSwitching() && this.mMainFrameisAvailable) {
            ComponentRunningDualVideo componentRunningDualVideo = DataRepository.dataItemRunning().getComponentRunningDualVideo();
            if (!componentRunningDualVideo.ismShouldDraw6Patch()) {
                switchThumbnailFunction(true);
                componentRunningDualVideo.setmShouldDraw6Patch(true);
                componentRunningDualVideo.setIds(Camera2DataContainer.getInstance().getDefaultDualVideoCameraIds());
                getControler().get().switchPreviewTo6Patch();
                getControler().get().enableContinuousRender(true);
                getActivity().getGLView().requestRender();
                switchCameraLens(false, false, false);
            }
        }
    }

    private void switchThumbnailFunction(boolean z) {
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing != null) {
            this.mActivity.runOnUiThread(new W(actionProcessing, z));
        }
    }

    private void updateSubCameraFocusMode(int i) {
        Camera2Proxy camera2Proxy = this.mSubCamera2Device;
        if (camera2Proxy == null || camera2Proxy.getCapabilities() == null) {
            Log.w(TAG, "updateSubCameraFocusMode: focusMode = " + i + ", but device is null...");
        } else if (Util.isSupported(i, this.mSubCamera2Device.getCapabilities().getSupportedFocusModes())) {
            this.mSubCamera2Device.setFocusMode(i);
        }
    }

    public /* synthetic */ void a(ViewGroup viewGroup) {
        addViewForGestureRecongnize(viewGroup);
        if (!is6PatchWindow()) {
            Log.d(TAG, "initDualVideoControler");
            addViewForPreviewCompose(viewGroup);
        }
    }

    public /* synthetic */ void a(DualVideoControler dualVideoControler) {
        dualVideoControler.setOrientation(this.mOrientation);
    }

    public /* synthetic */ void a(FocusTask focusTask) {
        if (1 == focusTask.getFocusTrigger()) {
            Log.d(TAG, "focusTime=" + focusTask.getElapsedTime() + "ms focused=" + focusTask.isSuccess() + " waitForRecording=" + this.mSubFocusManager.isFocusingSnapOnFinish());
            this.mMainProtocol.setFocusViewType(true);
            this.mSubFocusManager.onFocusResult(focusTask);
            this.mActivity.getSensorStateManager().reset();
            if (focusTask.isSuccess() && this.m3ALocked) {
                this.mSubCamera2Device.lockExposure(true);
            }
        }
    }

    public /* synthetic */ boolean a(MotionEvent motionEvent) {
        return getControler().get().dispatchTouchEvent(motionEvent);
    }

    @Override // com.android.camera.module.VideoModule, com.android.camera.module.BaseModule
    public void applyZoomRatio() {
        if (!isMultiCameraMode()) {
            super.applyZoomRatio();
            return;
        }
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null && this.mSubCamera2Device != null) {
            applyZooomForDevices(camera2Proxy);
            applyZooomForDevices(this.mSubCamera2Device);
        }
    }

    public /* synthetic */ void b(DualVideoControler dualVideoControler) {
        if (!dualVideoControler.isRecording()) {
            getControler().get().startRecording(getActivity().getGLView().getEGLContext14(), this.mRecorderSurface);
            super.startVideoRecording();
            this.mRecordPausedTimes = 0;
            this.mRecordCaptureTimes = 0;
        }
    }

    public /* synthetic */ void c(DualVideoControler dualVideoControler) {
        dualVideoControler.release(getActivity().getGLView().getGLCanvas());
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener, com.android.camera.module.VideoBase
    public void cancelFocus(boolean z) {
        if (isDeviceAlive()) {
            if (!isFrameAvailable()) {
                Log.e(TAG, "cancelFocus: frame not available");
            } else if (this.mCurrentTapId == this.mCamera2Device.getId()) {
                super.cancelFocus(z);
            } else {
                Log.d(TAG, "cancelFocus: sub camera resetFocusMode = " + z);
                if (z) {
                    updateSubCameraFocusMode(3);
                }
                this.mActivity.getSensorStateManager().setFocusSensorEnabled(false);
                this.mSubCamera2Device.cancelFocus(this.mModuleIndex);
                if (getCameraState() != 3) {
                    setCameraState(1);
                }
            }
        }
    }

    @Override // com.android.camera.module.VideoModule, com.android.camera.module.BaseModule
    public int getOperatingMode() {
        return 32772;
    }

    @Override // com.android.camera.module.VideoBase
    public void initializeFocusManager() {
        super.initializeFocusManager();
        CameraCapabilities capabilities = this.mSubCamera2Device.getCapabilities();
        this.mSubFocusManager = new FocusManager2(capabilities, this, this.mSubCamera2Device.isFacingFront(), Looper.getMainLooper());
        Size size = this.mSubCamera2Device.isFacingFront() ? DualVideoUtil.FRONT_PREVIE : DualVideoUtil.BACK_PREVIE;
        this.mSubFocusManager.setRenderSize(size.getHeight(), size.getWidth());
        this.mSubFocusManager.setPreviewSize(size.getHeight(), size.getWidth());
        this.mSubCamera2Device.setFocusCallback(this.mFocusCallback);
        this.mSubFocusAreaSupported = capabilities.isAFRegionSupported();
        this.mSubMeteringAreaSupported = capabilities.isAERegionSupported();
    }

    @Override // com.android.camera.module.VideoModule, com.android.camera.module.BaseModule, com.android.camera.module.VideoBase
    public boolean isZoomEnabled() {
        if (getControler() == null || isMultiCameraMode()) {
            return false;
        }
        return getControler().get().isZoomEnabled();
    }

    public /* synthetic */ void k(View view) {
        handleBackStack();
        if (getControler().get().expandOrShrinkTop()) {
            if (this.mBottomControlView.getVisibility() == 0) {
                this.mBottomControlView.setVisibility(8);
                this.mTopControlView.setImageResource(R.drawable.ic_dual_video_preview_reduce);
                this.mTopControlView.setBackgroundResource(R.drawable.ic_dual_video_preview_reduce_shadow);
                return;
            }
            this.mBottomControlView.setVisibility(0);
            this.mTopControlView.setImageResource(R.drawable.ic_dual_video_preview_expand);
            this.mTopControlView.setBackgroundResource(R.drawable.ic_dual_video_preview_expand_shadow);
        }
    }

    public /* synthetic */ void l(View view) {
        handleBackStack();
        if (getControler().isPresent() && getControler().get().expandBottom()) {
            this.mBottomControlView.setVisibility(8);
            this.mTopControlView.setImageResource(R.drawable.ic_dual_video_preview_reduce);
            this.mTopControlView.setBackgroundResource(R.drawable.ic_dual_video_preview_reduce_shadow);
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener, com.android.camera.module.VideoBase
    public void notifyFocusAreaUpdate() {
        if (this.mCurrentTapId == this.mCamera2Device.getId()) {
            super.notifyFocusAreaUpdate();
            return;
        }
        Camera camera = this.mActivity;
        if (camera != null && !camera.isActivityPaused() && isAlive()) {
            Camera2Proxy camera2Proxy = this.mSubCamera2Device;
            if (camera2Proxy == null) {
                Log.w(TAG, "updateFocusArea: sub camera device is null!");
                return;
            }
            Rect activeArraySize = camera2Proxy.getCapabilities().getActiveArraySize();
            Rect cropRegion = HybridZoomingSystem.toCropRegion(this.mZoomRatio, activeArraySize);
            this.mActivity.getSensorStateManager().setFocusSensorEnabled(this.mSubFocusManager.getMeteringAreas(cropRegion, activeArraySize) != null);
            this.mSubCamera2Device.setAERegions(this.mSubFocusManager.getMeteringAreas(cropRegion, activeArraySize));
            if (this.mSubFocusAreaSupported) {
                this.mSubCamera2Device.setAFRegions(this.mSubFocusManager.getFocusAreas(cropRegion, activeArraySize));
            }
            String focusMode = CameraSettings.getFocusMode();
            if (!this.mSubFocusAreaSupported || "manual".equals(focusMode)) {
                this.mSubCamera2Device.resumePreview();
            }
        }
    }

    @Override // com.android.camera.module.VideoModule, com.android.camera.module.BaseModule, com.android.camera.module.Module, com.android.camera.module.VideoBase
    public boolean onBackPressed() {
        if (DataRepository.dataItemRunning().getComponentRunningDualVideo().ismShouldDraw6Patch() || this.mMediaRecorderRecording || !isMultiCameraMode()) {
            return super.onBackPressed();
        }
        switchPreviewTo6Patch();
        return true;
    }

    @Override // com.android.camera.module.VideoModule, com.android.camera.module.BaseModule, com.android.camera.module.VideoBase
    public void onCameraOpened() {
        if (c.isMTKPlatform()) {
            ConcurrentHashMap defaultDualVideoCameraIds = Camera2DataContainer.getInstance().getDefaultDualVideoCameraIds();
            int[] iArr = {((Integer) defaultDualVideoCameraIds.get(101)).intValue(), ((Integer) defaultDualVideoCameraIds.get(100)).intValue()};
            this.mSubCamera2Device.setMtkPipDevices(iArr);
            this.mCamera2Device.setMtkPipDevices(iArr);
        }
        if (Camera2DataContainer.getInstance().isFrontCameraId(this.mSubCamera2Device.getId())) {
            this.mSubCamera2Device.startFaceDetection();
        }
        super.onCameraOpened();
        this.mCurrentTapId = this.mCamera2Device.getId();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean onCameraPickerClicked(View view) {
        if (getControler().isPresent() && getControler().get().switchTopBottom()) {
            if (this.mCameraSwitched) {
                ViewCompat.animate(view).rotationBy(-180.0f).setDuration(300).start();
            } else {
                ViewCompat.animate(view).rotationBy(180.0f).setDuration(300).start();
            }
            this.mCameraSwitched = !this.mCameraSwitched;
        }
        return true;
    }

    @Override // com.android.camera.module.VideoModule, com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onCreate(int i, int i2) {
        for (CameraHandlerThread.Cookie cookie : getCookieStore().getCookies()) {
            Log.d(TAG, "enumerating: " + cookie.mCamera2Device);
            Camera2Proxy camera2Proxy = cookie.mCamera2Device;
            if (camera2Proxy != null && camera2Proxy.getId() == DataRepository.dataItemRunning().getComponentRunningDualVideo().getIds().get(101).intValue()) {
                Log.d(TAG, "setCameraDevice: " + cookie.mCamera2Device);
                this.mSubCamera2Device = cookie.mCamera2Device;
            }
        }
        registDualVideoRender();
        super.onCreate(i, i2);
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module, com.android.camera.module.VideoBase
    public void onLongPress(float f, float f2) {
    }

    @Override // com.android.camera.module.VideoModule, com.android.camera.module.BaseModule, com.android.camera.module.Module, com.android.camera.module.VideoBase
    public void onPause() {
        getActivity().runOnUiThread(new Q(((ModeProtocol.ModuleContent) ModeCoordinatorImpl.getInstance().getAttachProtocol(431)).getParent()));
        super.onPause();
    }

    @Override // com.android.camera.module.VideoModule, com.android.camera2.Camera2Proxy.CameraPreviewCallback, com.android.camera.module.VideoBase
    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        if (cameraCaptureSession.getDevice().getId().equals(DataRepository.dataItemRunning().getComponentRunningDualVideo().getIds().get(101).toString())) {
            updateSubCameraFocusMode(3);
            this.mSubCamera2Device.resumePreview();
            return;
        }
        super.onPreviewSessionSuccess(cameraCaptureSession);
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module, com.android.camera.module.VideoBase
    public void onResume() {
        super.onResume();
        registDualVideoRender();
        if (!isMultiCameraMode()) {
            return;
        }
        if (!is6PatchWindow()) {
            showModeSwitchLayout(false);
        } else {
            showModeSwitchLayout(true);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction, com.android.camera.module.VideoBase
    public void onReviewCancelClicked() {
        if (!isMultiCameraMode() || is6PatchWindow()) {
            super.onReviewCancelClicked();
        } else {
            switchPreviewTo6Patch();
        }
    }

    @Override // com.android.camera.module.VideoModule, com.android.camera.protocol.ModeProtocol.CameraAction, com.android.camera.module.VideoBase
    public void onShutterButtonClick(int i) {
        if (!isMultiCameraMode() || !switch6PatchToPreview()) {
            super.onShutterButtonClick(i);
        }
    }

    @Override // com.android.camera.module.VideoModule, com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onSingleTapUp(int i, int i2, boolean z) {
        ModeProtocol.BackStack backStack;
        if (this.mPaused || this.mCamera2Device == null || this.mSubCamera2Device == null || hasCameraException() || !this.mCamera2Device.isPreviewReady() || !this.mSubCamera2Device.isPreviewReady() || !isInTapableRect(i, i2)) {
            Log.d(TAG, "onSingleTapUp: preview ready: " + this.mCamera2Device.isPreviewReady() + " " + this.mSubCamera2Device.isPreviewReady());
        } else if (getControler().isPresent() && !getControler().get().isSwitching() && !CameraSettings.getDualVideoConfig().ismShouldDraw6Patch() && (backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)) != null && !backStack.handleBackStackFromTapDown(i, i2)) {
            boolean z2 = true;
            this.mMainProtocol.setFocusViewType(true);
            this.mTouchFocusStartingTime = System.currentTimeMillis();
            Point point = new Point(i, i2);
            mapTapCoordinate(point);
            unlockAEAF();
            setCameraState(2);
            float f = (float) i;
            float f2 = (float) i2;
            this.mCurrentTapId = getControler().get().getIdByPosition(f, f2);
            FocusManager2 focusManager2 = this.mCurrentTapId == this.mCamera2Device.getId() ? this.mFocusManager : this.mSubFocusManager;
            int renderComposeTypeByPosition = getControler().get().getRenderComposeTypeByPosition(f, f2);
            focusManager2.setRenderComposeType(renderComposeTypeByPosition);
            StringBuilder sb = new StringBuilder();
            sb.append("onSingleTapUp: id = ");
            sb.append(this.mCurrentTapId);
            sb.append(", is main camera = ");
            if (this.mCurrentTapId != this.mCamera2Device.getId()) {
                z2 = false;
            }
            sb.append(z2);
            sb.append(", type = ");
            sb.append(renderComposeTypeByPosition);
            Log.d(TAG, sb.toString());
            focusManager2.onSingleTapUp(point.x, point.y, z);
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean onSurfaceTexturePending(GLCanvas gLCanvas, DrawExtTexAttribute drawExtTexAttribute) {
        return false;
    }

    @Override // com.android.camera.module.VideoModule
    public void pauseVideoRecording() {
        super.pauseVideoRecording();
        this.mRecordPausedTimes++;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.protocol.ModeProtocol.EvChangedProtocol
    public void resetEvValue() {
        super.resetEvValue();
        this.mSubCamera2Device.setExposureCompensation(0);
        this.mSubCamera2Device.setAWBLock(false);
        CameraSettings.resetExposure();
        this.mSubCamera2Device.resumePreview();
    }

    @Override // com.android.camera.module.VideoBase
    public void resetFocusState(double d2) {
        super.resetFocusState(d2);
        StringBuilder sb = new StringBuilder();
        sb.append("resetFocusState: isRecording=");
        sb.append(isRecording());
        sb.append(", isNeedCancelAutoFocus = ");
        FocusManager2 focusManager2 = this.mSubFocusManager;
        sb.append(focusManager2 != null && focusManager2.isNeedCancelAutoFocus());
        Log.v(TAG, sb.toString());
        FocusManager2 focusManager22 = this.mSubFocusManager;
        if (focusManager22 != null && focusManager22.isNeedCancelAutoFocus() && !isRecording()) {
            this.mSubFocusManager.onDeviceKeepMoving(d2);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void setEvValue() {
        if (isAlive()) {
            if (this.mCurrentTapId == this.mCamera2Device.getId()) {
                this.mSubCamera2Device.setAWBLock(false);
                super.setEvValue();
                return;
            }
            this.mCamera2Device.setAWBLock(false);
            int i = this.mEvState;
            if (i == 2) {
                this.mSubCamera2Device.setAWBLock(false);
            } else if (i == 1 || i == 3) {
                this.mSubCamera2Device.setExposureCompensation(this.mEvValue);
                if (this.mEvState == 1) {
                    if (this.mEvValue != 0) {
                        this.mSubCamera2Device.setAWBLock(true);
                    } else {
                        this.mSubCamera2Device.setAWBLock(false);
                    }
                }
            }
            this.mSubCamera2Device.resumePreview();
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module, com.android.camera.module.VideoBase
    public void setFrameAvailable(boolean z) {
        super.setFrameAvailable(z);
        this.mMainFrameisAvailable = z;
    }

    @Override // com.android.camera.module.VideoBase
    public void setOrientationParameter() {
        super.setOrientationParameter();
        getControler().ifPresent(new N(this));
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener, com.android.camera.module.VideoBase
    public void startFocus() {
        if (isDeviceAlive()) {
            if (!isFrameAvailable()) {
                Log.e(TAG, "cancelFocus: frame not available");
            } else if (this.mCurrentTapId == this.mCamera2Device.getId()) {
                super.startFocus();
            } else {
                Log.d(TAG, "startFocus: sub camera");
                this.mActivity.getSensorStateManager().setFocusSensorEnabled(true);
                this.mSubCamera2Device.setFocusCallback(this.mFocusCallback);
                this.mSubCamera2Device.startFocus(FocusTask.create(1), this.mModuleIndex);
            }
        }
    }

    @Override // com.android.camera.module.VideoModule
    public void startVideoPreviewSession() {
        super.startVideoPreviewSession();
        initDualVideoControler();
        Surface createSubCameraSurfaceIfNeed = getControler().get().createSubCameraSurfaceIfNeed();
        getControler().get().resetFrameReady();
        this.mSubCamera2Device.startVideoPreviewSession(createSubCameraSurfaceIfNeed, 0, false, false, null, Camera2DataContainer.getInstance().isFrontCameraId(this.mSubCamera2Device.getId()) ? CameraCapabilities.SESSION_OPERATION_MODE_VIDEO_BEAUTY : 32772, false, this);
    }

    @Override // com.android.camera.module.VideoModule, com.android.camera.module.VideoBase
    public void startVideoRecording() {
        getControler().ifPresent(new X(this));
    }

    @Override // com.android.camera.module.VideoModule, com.android.camera.module.VideoBase
    public void stopVideoRecording(boolean z) {
        if (getControler() != null && getControler().get().isRecording()) {
            if (isMultiCameraMode()) {
                switchThumbnailFunction(true);
            }
            getControler().get().stopRecording();
        }
        Log.v(TAG, "stopVideoRecording>>" + this.mMediaRecorderRecording + "|" + true);
        if (this.mMediaRecorderRecording) {
            this.mMediaRecorderRecording = false;
            this.mMediaRecorderRecordingPaused = false;
            long currentTimeMillis = System.currentTimeMillis();
            ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
            if (mainContentProtocol != null) {
                mainContentProtocol.processingFinish();
            }
            enableCameraControls(false);
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onFinish();
            }
            trackDualVideo();
            this.mRecordPausedTimes = 0;
            this.mRecordCaptureTimes = 0;
            this.mVideoRecordTime = 0;
            stopRecorder();
            handleTempVideoFile();
            AutoLockManager.getInstance(this.mActivity).hibernateDelayed();
            exitSavePowerMode();
            Log.v(TAG, "stopVideoRecording<<time=" + (System.currentTimeMillis() - currentTimeMillis));
            switchPreviewTo6Patch();
        }
    }

    @Override // com.android.camera.module.VideoModule
    public boolean takeVideoSnapShoot() {
        boolean takeVideoSnapShoot = super.takeVideoSnapShoot();
        if (takeVideoSnapShoot) {
            this.mRecordCaptureTimes++;
        }
        return takeVideoSnapShoot;
    }

    @Override // com.android.camera.module.VideoModule
    public void trackDualVideo() {
        if (getControler() != null) {
            boolean tj = DataRepository.dataItemFeature().tj();
            String str = MistatsConstants.DualVideoAttr.VALUE_PREVIEW_MINI;
            if (tj) {
                CameraStatUtils.trackMultiCameraDualVideo(getControler().get().hasMiniComposeType() ? str : MistatsConstants.DualVideoAttr.VALUE_PREVIEW_EQUAL, String.valueOf(this.mVideoRecordTime), getZoomGroupForTrack(), MistatsConstants.MultiCameraAttr.VALUE_RECORD_MERGED, this.mRecordPausedTimes, this.mRecordCaptureTimes);
                return;
            }
            if (!getControler().get().hasMiniComposeType()) {
                str = MistatsConstants.DualVideoAttr.VALUE_PREVIEW_EQUAL;
            }
            CameraStatUtils.trackDualVideo(str, String.valueOf(this.mVideoRecordTime));
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void unRegisterModulePersistProtocol() {
        getActivity().getCameraScreenNail().setExternalFrameProcessor(null);
        super.unRegisterModulePersistProtocol();
        getControler().ifPresent(new P(this));
        getActivity().getImplFactory().detachModulePersistent();
    }

    @Override // com.android.camera.module.VideoBase
    public void unlockAEAF() {
        super.unlockAEAF();
        this.mSubFocusManager.setAeAwbLock(false);
        this.mSubCamera2Device.setAELock(false);
    }

    @Override // com.android.camera.module.BaseModule
    public void updateAntiBanding(String str) {
        super.updateAntiBanding(str);
        if (isDeviceAlive()) {
            this.mSubCamera2Device.setAntiBanding(this.mCamera2Device.getCameraConfigs().getAntiBanding());
        }
    }

    @Override // com.android.camera.module.VideoBase
    public void updateBeauty() {
        if (this.mBeautyValues == null) {
            this.mBeautyValues = new BeautyValues();
        }
        if (Camera2DataContainer.getInstance().isFrontCameraId(this.mSubCamera2Device.getId())) {
            BeautyValues beautyValues = this.mBeautyValues;
            beautyValues.mBeautySkinSmooth = 40;
            beautyValues.mBeautySlimFace = 40;
            this.mSubCamera2Device.setBeautyValues(beautyValues);
        }
    }

    @Override // com.android.camera.module.VideoModule
    public void updateFpsRange() {
        if (c.nh) {
            Range<Integer> create = Range.create(24, 24);
            this.mCamera2Device.setFpsRange(create);
            this.mSubCamera2Device.setFpsRange(create);
            return;
        }
        super.updateFpsRange();
    }

    @Override // com.android.camera.module.BaseModule
    public void updateModuleRelated() {
        super.updateModuleRelated();
        this.mSubCamera2Device.setModuleParameter(this.mModuleIndex, Camera2DataContainer.getInstance().isFrontCameraId(this.mSubCamera2Device.getId()) ? 1 : 0);
    }

    @Override // com.android.camera.module.VideoModule
    public void updateVideoStabilization() {
        if (isDeviceAlive()) {
            if (CameraSettings.isMovieSolidOn()) {
                Log.d(TAG, "videoStabilization: EIS");
                this.mCamera2Device.setEnableOIS(false);
                this.mCamera2Device.setEnableEIS(true);
                if (!this.mCameraCapabilities.isEISPreviewSupported()) {
                    this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(true);
                    return;
                }
                return;
            }
            Log.d(TAG, "videoStabilization: OIS");
            this.mCamera2Device.setEnableEIS(false);
            this.mCamera2Device.setEnableOIS(true);
            this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
        }
    }
}
