package com.android.camera.module.loader.camera2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.OutputConfiguration;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Surface;
import androidx.annotation.NonNull;
import com.android.camera.CameraAppImpl;
import com.android.camera.constant.ExceptionConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressLint({"MissingPermission"})
@TargetApi(21)
public class ParallelSnapshotManager {
    private static final int MANAGER_MSG_CLOSE_CAMERA = 2;
    private static final int MANAGER_MSG_CLOSE_FINISH = 3;
    private static final int MANAGER_MSG_CLOSE_SESSION = 6;
    private static final int MANAGER_MSG_CREATE_SESSION = 5;
    private static final int MANAGER_MSG_OPEN_FINISH = 4;
    private static final int MANAGER_MSG_REQUEST_CAMERA = 1;
    private static final int MANAGER_MSG_SET_CLOSE_STATE = 7;
    private static final int MANAGER_STATE_IDLE = 1;
    private static final int MANAGER_STATE_PENDING_CREATE_SESSION = 5;
    private static final int MANAGER_STATE_WAITING_CLOSE = 2;
    private static final int MANAGER_STATE_WAITING_CREATE_SESSION = 4;
    private static final int MANAGER_STATE_WAITING_OPEN = 3;
    public static int PARALLEL_PERFORMANCE_FEATURE_SETTING = 0;
    private static final int PARALLEL_PERFORMANCE_SETTING = 0;
    public static int PARALLEL_QUALITY_FEATURE_SETTING = 0;
    private static final int PARALLEL_QUALITY_SETTING = 1;
    private static final int PARALLEL_QUEUE_SIZE = 2;
    public static final int REASON_DISCONNECTED = 1;
    private static final String TAG = "ParallelSnapshotManager";
    public static boolean isParallelTagOpen;
    private static ParallelSnapshotManager sInstance;
    private int CAMERA_ID_VIRTUAL_PARALLEL;
    private CameraCaptureSession mCameraCaptureSession;
    private CameraDevice mCameraDevice;
    private Handler mCameraHandler;
    private CameraManager mCameraManager;
    private final CameraDevice.StateCallback mCameraOpenCallback = new CameraDevice.StateCallback() {
        /* class com.android.camera.module.loader.camera2.ParallelSnapshotManager.AnonymousClass2 */

        public void onClosed(@NonNull CameraDevice cameraDevice) {
            String str = ParallelSnapshotManager.TAG;
            Log.d(str, "CameraOpenCallback: closed " + cameraDevice.getId());
            ParallelSnapshotManager.this.mCameraHandler.sendEmptyMessage(3);
        }

        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            String str = ParallelSnapshotManager.TAG;
            Log.d(str, "CameraOpenCallback: onDisconnected " + cameraDevice.getId());
            if (ParallelSnapshotManager.this.mCameraDevice != null) {
                Message obtainMessage = ParallelSnapshotManager.this.mCameraHandler.obtainMessage();
                obtainMessage.what = 2;
                obtainMessage.arg1 = 1;
                ParallelSnapshotManager.this.mCameraHandler.sendMessageAtFrontOfQueue(obtainMessage);
            }
        }

        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            String str = "onError: cameraId=" + cameraDevice.getId() + " error=" + i;
            Log.e(ParallelSnapshotManager.TAG, "CameraOpenCallback: " + str);
            ParallelSnapshotManager.this.removeAllAppMessages();
            synchronized (ParallelSnapshotManager.this.mCaptureLock) {
                ParallelSnapshotManager.this.mIsCameraOpened = false;
                ParallelSnapshotManager.this.mCurrentSurfaceList = null;
                ParallelSnapshotManager.this.mPendingSurfaceList = null;
                ParallelSnapshotManager.this.mCameraDevice = null;
                ParallelSnapshotManager.this.mCameraCaptureSession = null;
                ParallelSnapshotManager.this.setManagerState(1);
            }
            ParallelSnapshotManager.this.onCameraOpenFailed(ExceptionConstant.transFromCamera2Error(i), str);
        }

        public void onOpened(@NonNull CameraDevice cameraDevice) {
            int parseInt = Integer.parseInt(cameraDevice.getId());
            CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(parseInt);
            String str = "CameraOpenCallback: camera " + parseInt + " was opened successfully";
            if (capabilities != null) {
                Message obtain = Message.obtain();
                obtain.what = 4;
                obtain.obj = cameraDevice;
                ParallelSnapshotManager.this.mCameraHandler.sendMessage(obtain);
                return;
            }
            String str2 = str + ", but corresponding CameraCapabilities is null";
            Log.e(ParallelSnapshotManager.TAG, str2);
            ParallelSnapshotManager.this.onCameraOpenFailed(231, str2);
        }
    };
    private final Object mCaptureLock = new Object();
    private int mCurrentActivityCode = 0;
    private int mCurrentState = 1;
    private List<Surface> mCurrentSurfaceList;
    private boolean mIsCameraOpened;
    private boolean mIsNeedCloseCamera;
    private boolean mIsNeedReOpenCamera;
    private final int mMaxQueueSize;
    private List<Surface> mPendingSurfaceList;
    private int mTeleParallelSurfaceIndex;
    private int mUltraTeleParallelSurfaceIndex;
    private int mUltraWideParallelSurfaceIndex;
    private int mWideParallelSurfaceIndex;

    /* access modifiers changed from: private */
    public class CaptureSessionStateCallback extends CameraCaptureSession.StateCallback {
        private CaptureSessionStateCallback() {
        }

        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
            Log.d(ParallelSnapshotManager.TAG, "onConfigureFailed");
            synchronized (ParallelSnapshotManager.this.mCaptureLock) {
                ParallelSnapshotManager.this.mCameraCaptureSession = null;
            }
            ParallelSnapshotManager.this.setManagerState(1);
        }

        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
            Log.d(ParallelSnapshotManager.TAG, "onConfigured>>");
            synchronized (ParallelSnapshotManager.this.mCaptureLock) {
                ParallelSnapshotManager.this.mCameraCaptureSession = cameraCaptureSession;
            }
            ParallelSnapshotManager.this.setManagerState(1);
            Log.d(ParallelSnapshotManager.TAG, "onConfigured<<");
        }
    }

    @interface ManagerState {
    }

    private ParallelSnapshotManager() {
        HandlerThread handlerThread = new HandlerThread("VT Camera Handler Thread");
        handlerThread.start();
        this.mCameraHandler = new Handler(handlerThread.getLooper()) {
            /* class com.android.camera.module.loader.camera2.ParallelSnapshotManager.AnonymousClass1 */

            public void handleMessage(Message message) {
                ParallelSnapshotManager.this.onMessage(message);
            }
        };
        this.mCameraManager = (CameraManager) CameraAppImpl.getAndroidContext().getSystemService("camera");
        this.CAMERA_ID_VIRTUAL_PARALLEL = Camera2DataContainer.getInstance().getParallelVirtualCameraId();
        Map<Integer, Integer> Nh = DataRepository.dataItemFeature().Nh();
        PARALLEL_PERFORMANCE_FEATURE_SETTING = Nh.get(0).intValue();
        PARALLEL_QUALITY_FEATURE_SETTING = Nh.get(1).intValue();
        this.mMaxQueueSize = Nh.get(2).intValue();
        String str = TAG;
        Log.d(str, "PARALLEL_PERFORMANCE_FEATURE_SETTING:" + PARALLEL_PERFORMANCE_FEATURE_SETTING + " PARALLEL_QUALITY_FEATURE_SETTING:" + PARALLEL_QUALITY_FEATURE_SETTING + " mMaxQueueSize:" + this.mMaxQueueSize);
    }

    public static synchronized ParallelSnapshotManager getInstance() {
        ParallelSnapshotManager parallelSnapshotManager;
        synchronized (ParallelSnapshotManager.class) {
            if (sInstance == null) {
                sInstance = new ParallelSnapshotManager();
            }
            parallelSnapshotManager = sInstance;
        }
        return parallelSnapshotManager;
    }

    @ManagerState
    private int getManagerState() {
        return this.mCurrentState;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onCameraOpenFailed(int i, String str) {
        String str2 = TAG;
        Log.e(str2, "onCameraOpenFailed: " + i + " msg:" + str);
        setManagerState(1);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onMessage(Message message) {
        switch (message.what) {
            case 1:
                if (getManagerState() == 1) {
                    try {
                        Log.d(TAG, "open start");
                        setManagerState(3);
                        this.mCameraManager.openCamera(String.valueOf(this.CAMERA_ID_VIRTUAL_PARALLEL), this.mCameraOpenCallback, this.mCameraHandler);
                        return;
                    } catch (CameraAccessException | IllegalArgumentException | SecurityException e2) {
                        e2.printStackTrace();
                        onCameraOpenFailed(230, e2.getClass().getSimpleName());
                        String str = TAG;
                        Log.e(str, "openCamera: failed to open camera " + this.CAMERA_ID_VIRTUAL_PARALLEL, e2);
                        return;
                    }
                } else if (getManagerState() == 2) {
                    this.mIsNeedReOpenCamera = true;
                    return;
                } else {
                    return;
                }
            case 2:
                if (getManagerState() != 1) {
                    String str2 = TAG;
                    Log.w(str2, "not idle, break on msg.what " + message.what + ", mCurrentState " + this.mCurrentState);
                    return;
                }
                setManagerState(2);
                int i = message.arg1;
                String str3 = TAG;
                Log.d(str3, "force close start reason " + i);
                this.mCameraDevice.close();
                this.mCameraDevice = null;
                this.mCameraCaptureSession = null;
                this.mCurrentSurfaceList = null;
                return;
            case 3:
                synchronized (this.mCaptureLock) {
                    this.mIsCameraOpened = false;
                    this.mCameraDevice = null;
                    this.mCameraCaptureSession = null;
                    this.mCurrentSurfaceList = null;
                    setManagerState(1);
                    Log.d(TAG, "close finish");
                    if (this.mIsNeedReOpenCamera) {
                        if (!this.mIsNeedCloseCamera) {
                            this.mIsCameraOpened = true;
                            Log.d(TAG, "receive open request, need to reopen camera device");
                            this.mCameraHandler.sendEmptyMessage(1);
                        }
                        this.mIsNeedReOpenCamera = false;
                    }
                }
                return;
            case 4:
                Log.d(TAG, "open finish");
                this.mCameraDevice = (CameraDevice) message.obj;
                if (getManagerState() == 5) {
                    Log.w(TAG, "try to recreate session");
                    createCaptureSession(this.mPendingSurfaceList);
                    this.mPendingSurfaceList = null;
                }
                setManagerState(1);
                return;
            case 5:
                Log.d(TAG, "create CaptureSession >>>>>>");
                if (getManagerState() != 1) {
                    String str4 = TAG;
                    Log.w(str4, "MANAGER_MSG_CREATE_SESSION manager state:" + getManagerState());
                    if (getManagerState() == 3) {
                        this.mPendingSurfaceList = (List) message.obj;
                        setManagerState(5);
                        Log.w(TAG, "waiting camera open finish to recreate session");
                        return;
                    }
                    return;
                }
                synchronized (this.mCaptureLock) {
                    if (this.mCurrentSurfaceList != null && this.mCurrentSurfaceList.equals((List) message.obj)) {
                        Log.d(TAG, "the same surface, skip");
                        return;
                    } else if (this.mCameraCaptureSession != null) {
                        Log.d(TAG, "cameraCaptureSession is not null");
                        this.mPendingSurfaceList = (List) message.obj;
                        return;
                    } else {
                        if (!this.mIsNeedCloseCamera) {
                            if (this.mCameraDevice != null) {
                                setManagerState(4);
                                try {
                                    this.mCameraDevice.createCaptureRequest(2);
                                    ArrayList arrayList = new ArrayList();
                                    List list = (List) message.obj;
                                    String str5 = TAG;
                                    Log.d(str5, "surfaces.size:" + list.size());
                                    int size = list.size();
                                    for (int i2 = 0; i2 < size; i2++) {
                                        arrayList.add(new OutputConfiguration((Surface) list.get(i2)));
                                    }
                                    this.mCameraDevice.createCustomCaptureSession(null, arrayList, CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_SAT, new CaptureSessionStateCallback(), this.mCameraHandler);
                                } catch (CameraAccessException e3) {
                                    e3.printStackTrace();
                                }
                                this.mCurrentSurfaceList = (List) message.obj;
                                Log.d(TAG, "createSession<<");
                                return;
                            }
                        }
                        Log.w(TAG, "camera app is not active and surface list maybe null, so dont create session");
                        this.mCurrentSurfaceList = null;
                        this.mPendingSurfaceList = null;
                        return;
                    }
                }
            case 6:
                if (getManagerState() != 1) {
                    String str6 = TAG;
                    Log.w(str6, "MANAGER_MSG_CLOSE_SESSION manager state:" + getManagerState());
                    return;
                }
                synchronized (this.mCaptureLock) {
                    if (this.mCameraCaptureSession != null) {
                        this.mCameraCaptureSession.close();
                        this.mCameraCaptureSession = null;
                        this.mCurrentSurfaceList = null;
                        Log.d(TAG, "CaptureSession close");
                    }
                    if (this.mIsNeedCloseCamera) {
                        Log.d(TAG, "begin to close camera");
                        closeCamera();
                        return;
                    }
                    if (this.mPendingSurfaceList != null && !this.mIsNeedCloseCamera) {
                        Log.d(TAG, " have surface pending list, need to recreate session");
                        createCaptureSession(this.mPendingSurfaceList);
                        this.mPendingSurfaceList = null;
                    }
                    return;
                }
            case 7:
                this.mIsNeedCloseCamera = Boolean.parseBoolean(message.obj.toString());
                String str7 = TAG;
                Log.d(str7, "set close camera device state mIsNeedCloseCamera:" + this.mIsNeedCloseCamera);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void removeAllAppMessages() {
        this.mCameraHandler.removeMessages(1);
        this.mCameraHandler.removeMessages(3);
        this.mCameraHandler.removeMessages(4);
        this.mCameraHandler.removeMessages(2);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setManagerState(@ManagerState int i) {
        this.mCurrentState = i;
    }

    public void closeCamera() {
        synchronized (this.mCaptureLock) {
            if (this.mIsCameraOpened) {
                this.mCameraHandler.sendEmptyMessage(2);
                this.mIsCameraOpened = false;
            }
        }
    }

    public void closeCaptureSession(List<Surface> list) {
        synchronized (this.mCaptureLock) {
            if (list != null) {
                if (this.mCurrentSurfaceList != null && list.containsAll(this.mCurrentSurfaceList)) {
                    this.mCameraHandler.sendEmptyMessage(6);
                }
            }
        }
    }

    public void createCaptureSession(List<Surface> list) {
        Message obtain = Message.obtain();
        obtain.obj = list;
        obtain.what = 5;
        this.mCameraHandler.sendMessage(obtain);
    }

    public CameraDevice getCameraDevice() {
        CameraDevice cameraDevice;
        synchronized (this.mCaptureLock) {
            cameraDevice = this.mCameraDevice;
        }
        return cameraDevice;
    }

    public CameraCaptureSession getCaptureSession() {
        CameraCaptureSession cameraCaptureSession;
        synchronized (this.mCaptureLock) {
            cameraCaptureSession = this.mCameraCaptureSession;
        }
        return cameraCaptureSession;
    }

    public Surface getCaptureSurface(int i) {
        if (i == 1) {
            return this.mCurrentSurfaceList.get(this.mUltraWideParallelSurfaceIndex);
        }
        if (i == 2) {
            return this.mCurrentSurfaceList.get(this.mWideParallelSurfaceIndex);
        }
        if (i == 3) {
            return this.mCurrentSurfaceList.get(this.mTeleParallelSurfaceIndex);
        }
        if (i != 4) {
            return null;
        }
        return this.mCurrentSurfaceList.get(this.mUltraTeleParallelSurfaceIndex);
    }

    public int getMaxQueueSize() {
        return this.mMaxQueueSize;
    }

    public boolean isParallelSessionReady() {
        return this.mPendingSurfaceList == null && this.mCameraCaptureSession != null;
    }

    public void openCamera() {
        String str = TAG;
        Log.d(str, " CAMERA_ID_VIRTUAL_PARALLEL = " + this.CAMERA_ID_VIRTUAL_PARALLEL + " mIsCameraOpened:" + this.mIsCameraOpened);
        synchronized (this.mCaptureLock) {
            if (!this.mIsCameraOpened) {
                this.mCameraHandler.sendEmptyMessage(1);
                this.mIsCameraOpened = true;
            }
        }
    }

    public void release() {
        this.mCameraHandler.removeMessages(1);
        Handler handler = this.mCameraHandler;
        handler.sendMessage(handler.obtainMessage(2));
    }

    public void setCameraCloseState(boolean z, int i) {
        synchronized (this.mCaptureLock) {
            if (!z) {
                this.mCurrentActivityCode = i;
                Message obtain = Message.obtain();
                obtain.obj = Boolean.valueOf(z);
                obtain.what = 7;
                this.mCameraHandler.sendMessage(obtain);
            } else if (this.mCurrentActivityCode == i) {
                Message obtain2 = Message.obtain();
                obtain2.obj = Boolean.valueOf(z);
                obtain2.what = 7;
                this.mCameraHandler.sendMessage(obtain2);
            }
        }
    }

    public void setSurfaceIndex(int i, int i2) {
        if (i == 1) {
            this.mUltraWideParallelSurfaceIndex = i2;
        } else if (i == 2) {
            this.mWideParallelSurfaceIndex = i2;
        } else if (i == 3) {
            this.mTeleParallelSurfaceIndex = i2;
        } else if (i == 4) {
            this.mUltraTeleParallelSurfaceIndex = i2;
        }
    }
}
