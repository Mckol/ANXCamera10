package com.android.camera.module.loader.camera2;

import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.camera2.params.MeteringRectangle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.FocusManagerAbstract;
import com.android.camera.Util;
import com.android.camera.constant.AutoFocus;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.module.loader.FunctionParseBeautyBodySlimCount;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.CameraCapabilities;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.lang.ref.WeakReference;
import java.util.List;
import miui.date.Calendar;

@TargetApi(21)
public class FocusManager2 extends FocusManagerAbstract {
    private static final int FOCUS_TIME_OUT = 4000;
    private static final int FORCE_RESET_TOUCH_FOCUS = 1;
    private static int FORCE_RESET_TOUCH_FOCUS_DELAY = 5000;
    private static final int MAX_FACE_MOVE = 80;
    private static final int RESET_TOUCH_FOCUS = 0;
    private static final int RESET_TOUCH_FOCUS_DELAY = 3000;
    private static final String TAG = "FocusManager";
    private static final int TAP_ACTION_AE = 1;
    private static final int TAP_ACTION_AE_AND_AF = 2;
    private boolean mAELockOnlySupported;
    private boolean mAeAwbLock;
    private long mCafStartTime;
    private Rect mCameraFocusArea;
    private Rect mCameraMeteringArea;
    private boolean mDestroyed;
    private boolean mFocusAreaSupported;
    private String mFocusMode;
    private Point mFocusPoint;
    private Consumer<FocusTask> mFocusResultConsumer = new Consumer<FocusTask>() {
        /* class com.android.camera.module.loader.camera2.FocusManager2.AnonymousClass2 */

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x005e, code lost:
            if (r5.getFocusTrigger() != 3) goto L_0x0062;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0069, code lost:
            if (((com.android.camera.FocusManagerAbstract) r4.this$0).mState != 2) goto L_0x00a1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x006f, code lost:
            if (r5.isSuccess() == false) goto L_0x007c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0071, code lost:
            r4.this$0.setFocusState(3);
            r4.this$0.setLastFocusState(3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x007c, code lost:
            r4.this$0.setFocusState(4);
            r4.this$0.setLastFocusState(4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0086, code lost:
            r4.this$0.updateFocusUI();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0091, code lost:
            if (r4.this$0.mPendingMultiCapture == false) goto L_0x009a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0093, code lost:
            r4.this$0.multiCapture();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x009a, code lost:
            r4.this$0.capture();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00a8, code lost:
            if (((com.android.camera.FocusManagerAbstract) r4.this$0).mState != 1) goto L_0x0109;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x00ae, code lost:
            if (r5.isSuccess() == false) goto L_0x00e2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x00b0, code lost:
            r4.this$0.setFocusState(3);
            r4.this$0.setLastFocusState(3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x00c6, code lost:
            if ("auto".equals(r4.this$0.mFocusMode) == false) goto L_0x00f5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x00ce, code lost:
            if (r4.this$0.mLastFocusFrom == 1) goto L_0x00f5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x00d0, code lost:
            r5 = (com.android.camera.module.loader.camera2.FocusManager2.Listener) r4.this$0.mListener.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00dc, code lost:
            if (r5 == null) goto L_0x00f5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00de, code lost:
            r5.playFocusSound(1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x00e2, code lost:
            r5 = r4.this$0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x00e8, code lost:
            if (((com.android.camera.FocusManagerAbstract) r5).mMirror == false) goto L_0x00ec;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x00ea, code lost:
            r0 = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x00ec, code lost:
            r0 = 4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ed, code lost:
            r5.setFocusState(r0);
            r4.this$0.setLastFocusState(4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00f5, code lost:
            r4.this$0.updateFocusUI();
            r4.this$0.mHandler.removeMessages(1);
            ((com.android.camera.FocusManagerAbstract) r4.this$0).mCancelAutoFocusIfMove = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x0109, code lost:
            r0 = ((com.android.camera.FocusManagerAbstract) r4.this$0).mState;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x010f, code lost:
            r4.this$0.onAutoFocusMoving(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x0114, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0012, code lost:
            com.android.camera.log.Log.v(com.android.camera.module.loader.camera2.FocusManager2.TAG, "focusResult: " + r5.getFocusTrigger() + "|" + r5.isSuccess() + "|" + r5.isFocusing() + "|" + ((com.android.camera.FocusManagerAbstract) r4.this$0).mState);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0057, code lost:
            if (r5.getFocusTrigger() == 2) goto L_0x010f;
         */
        public void accept(FocusTask focusTask) throws Exception {
            synchronized (FocusManager2.this.mLock) {
                if (FocusManager2.this.mDestroyed) {
                }
            }
        }
    };
    private Disposable mFocusResultDisposable;
    private ObservableEmitter<FocusTask> mFocusResultEmitter;
    private Handler mHandler;
    private boolean mKeepFocusUIState;
    private int mLastFocusFrom = -1;
    private int mLastState = 0;
    private RectF mLatestFocusFace;
    private long mLatestFocusTime;
    private WeakReference<Listener> mListener;
    private final Object mLock = new Object();
    private boolean mLockAeAwbNeeded;
    private boolean mMeteringAreaSupported;
    private Point mMeteringPoint;
    private String mOverrideFocusMode;
    private boolean mPendingMultiCapture;
    private List<String> mSupportedFocusModes;

    public interface Listener {
        void cancelFocus(boolean z);

        boolean multiCapture();

        default void notifyFocusAreaUpdate() {
        }

        boolean onWaitingFocusFinished();

        void playFocusSound(int i);

        boolean shouldCaptureDirectly();

        default void startFaceDetection() {
        }

        void startFocus();

        default void stopFaceDetection(boolean z) {
        }

        default void stopObjectTracking(boolean z) {
        }
    }

    private class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0 || i == 1) {
                ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
                if (mainContentProtocol != null) {
                    mainContentProtocol.reShowFaceRect();
                }
                if (message.what != 1 || !FocusManager2.this.isFocusingSnapOnFinish()) {
                    FocusManager2.this.cancelFocus();
                    return;
                }
                FocusManager2.this.setFocusState(4);
                FocusManager2.this.setLastFocusState(4);
                if (FocusManager2.this.mPendingMultiCapture) {
                    FocusManager2.this.multiCapture();
                } else {
                    FocusManager2.this.capture();
                }
            }
        }
    }

    public FocusManager2(CameraCapabilities cameraCapabilities, Listener listener, boolean z, Looper looper) {
        if (CameraSettings.isCameraTouchFocusDelayEnable()) {
            FORCE_RESET_TOUCH_FOCUS_DELAY = Calendar.MILLISECOND_OF_HOUR;
        }
        this.mHandler = new MainHandler(looper);
        setCharacteristics(cameraCapabilities);
        this.mListener = new WeakReference<>(listener);
        setMirror(z);
        this.mFocusResultDisposable = Observable.create(new ObservableOnSubscribe<FocusTask>() {
            /* class com.android.camera.module.loader.camera2.FocusManager2.AnonymousClass1 */

            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<FocusTask> observableEmitter) throws Exception {
                FocusManager2.this.mFocusResultEmitter = observableEmitter;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(this.mFocusResultConsumer);
    }

    private MeteringRectangle[] afaeRectangle(Rect rect, Rect rect2, Rect rect3) {
        RectF rectF = new RectF(rect);
        this.mMatrix.mapRect(rectF);
        Matrix matrix = new Matrix();
        matrix.postTranslate(((float) rect3.width()) / 2.0f, ((float) rect3.height()) / 2.0f);
        matrix.mapRect(rectF);
        float width = ((float) rect2.width()) / ((float) rect3.width());
        float height = ((float) rect2.height()) / ((float) rect3.height());
        int i = rect2.left;
        rectF.left = (rectF.left * width) + ((float) i);
        int i2 = rect2.top;
        rectF.top = (rectF.top * height) + ((float) i2);
        rectF.right = (rectF.right * width) + ((float) i);
        rectF.bottom = (rectF.bottom * height) + ((float) i2);
        Rect rect4 = new Rect();
        Util.rectFToRect(rectF, rect4);
        rect4.left = Util.clamp(rect4.left, rect2.left, rect2.right);
        rect4.top = Util.clamp(rect4.top, rect2.top, rect2.bottom);
        rect4.right = Util.clamp(rect4.right, rect2.left, rect2.right);
        rect4.bottom = Util.clamp(rect4.bottom, rect2.top, rect2.bottom);
        return new MeteringRectangle[]{Util.createMeteringRectangleFrom(rect4, 1)};
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void capture() {
        Listener listener = this.mListener.get();
        if (listener != null && listener.onWaitingFocusFinished()) {
            this.mPendingMultiCapture = false;
            this.mHandler.removeMessages(0);
        }
    }

    private void focusPoint(int i, int i2, int i3, boolean z) {
        if (this.mInitialized && this.mState != 2) {
            String str = this.mOverrideFocusMode;
            if (str == null || isAutoFocusMode(str)) {
                ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
                if (isNeedCancelAutoFocus()) {
                    cancelFocus();
                }
                initializeParameters(i, i2, i3, z);
                initializeFocusIndicator(1, i, i2);
                Listener listener = this.mListener.get();
                if (listener != null) {
                    listener.notifyFocusAreaUpdate();
                }
                boolean z2 = i3 == 5 && this.mAELockOnlySupported;
                if ((!this.mFocusAreaSupported || z) && !z2) {
                    if (this.mMeteringAreaSupported) {
                        if (3 == i3 && isFocusValid(i3)) {
                            if (listener != null) {
                                listener.playFocusSound(1);
                            }
                            this.mCancelAutoFocusIfMove = true;
                        }
                        this.mLastFocusFrom = i3;
                        setFocusState(1);
                        updateFocusUI();
                        setFocusState(3);
                        updateFocusUI();
                        this.mHandler.removeMessages(0);
                    }
                } else if (isFocusValid(i3)) {
                    startFocus(i3);
                }
            }
        }
    }

    private int getTapAction() {
        String focusMode = getFocusMode();
        return (focusMode.equals(AutoFocus.LEGACY_EDOF) || focusMode.equals("manual")) ? 1 : 2;
    }

    private void initializeFocusAreas(int i, int i2, int i3, int i4, int i5, int i6) {
        if (this.mCameraFocusArea == null) {
            this.mCameraFocusArea = new Rect();
        }
        Point point = this.mFocusPoint;
        if (point == null) {
            this.mFocusPoint = new Point(i3, i4);
        } else {
            point.x = i3;
            point.y = i4;
        }
        calculateTapArea(i, i2, 1.0f, i3, i4, i5, i6, this.mCameraFocusArea);
    }

    private void initializeFocusIndicator(int i, int i2, int i3) {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol != null) {
            mainContentProtocol.setFocusViewPosition(i, i2, i3);
        }
    }

    private void initializeMeteringAreas(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (i7 != 1 || mainContentProtocol == null || mainContentProtocol.isNeedExposure(1)) {
            if (this.mCameraMeteringArea == null) {
                this.mCameraMeteringArea = new Rect();
            }
            Point point = this.mMeteringPoint;
            if (point == null) {
                this.mMeteringPoint = new Point(i3, i4);
            } else {
                point.x = i3;
                point.y = i4;
            }
            calculateTapArea(i, i2, 1.8f, i3, i4, i5, i6, this.mCameraMeteringArea);
            return;
        }
        this.mCameraMeteringArea = null;
    }

    private void initializeParameters(int i, int i2, int i3, boolean z) {
        if (!this.mFocusAreaSupported) {
            this.mFocusPoint = null;
        }
        if (!this.mMeteringAreaSupported) {
            this.mMeteringPoint = null;
        }
        if (this.mFocusAreaSupported && !z) {
            initializeFocusAreas(this.FOCUS_AREA_WIDTH, this.FOCUS_AREA_HEIGHT, i, i2, this.mPreviewWidth, this.mPreviewHeight);
        }
        if (this.mMeteringAreaSupported) {
            initializeMeteringAreas(this.FOCUS_AREA_WIDTH, this.FOCUS_AREA_HEIGHT, i, i2, this.mPreviewWidth, this.mPreviewHeight, i3);
        }
    }

    private boolean isAutoFocusMode(String str) {
        return "auto".equals(str) || "macro".equals(str);
    }

    private boolean isFocusEnabled() {
        int i;
        return this.mInitialized && (i = this.mState) != 2 && i != 1 && needAutoFocusCall();
    }

    private boolean isFocusValid(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        int i2 = this.mLastFocusFrom;
        long j = (i2 == 3 || i2 == 4) ? 5000 : FunctionParseBeautyBodySlimCount.TIP_TIME;
        if (i >= 3 || i >= this.mLastFocusFrom || Util.isTimeout(currentTimeMillis, this.mLatestFocusTime, j)) {
            this.mLatestFocusTime = System.currentTimeMillis();
            return true;
        } else if (this.mLastFocusFrom != 1) {
            return false;
        } else {
            resetTouchFocus(7);
            return false;
        }
    }

    private boolean isMeteringFocusSplit() {
        Point point;
        Point point2 = this.mMeteringPoint;
        return (point2 == null || (point = this.mFocusPoint) == null || point.equals(point2)) ? false : true;
    }

    private void lockAeAwbIfNeeded() {
        if (this.mLockAeAwbNeeded && !this.mAeAwbLock) {
            this.mAeAwbLock = true;
            Listener listener = this.mListener.get();
            if (listener != null) {
                listener.notifyFocusAreaUpdate();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void multiCapture() {
        Listener listener = this.mListener.get();
        if (listener != null && listener.multiCapture()) {
            setFocusState(0);
            this.mPendingMultiCapture = false;
            this.mHandler.removeMessages(0);
        }
    }

    private boolean needAutoFocusCall() {
        return 2 == getTapAction() && this.mFocusAreaSupported;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onAutoFocusMoving(FocusTask focusTask) {
        boolean isFocusing = focusTask.isFocusing();
        boolean isSuccess = focusTask.isSuccess();
        if (!this.mInitialized) {
            Log.d(TAG, "onAutoFocusMoving");
            return;
        }
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        boolean z = !focusTask.isIsDepthFocus();
        if (mainContentProtocol != null && mainContentProtocol.isFaceExists(mainContentProtocol.getActiveIndicator())) {
            mainContentProtocol.clearFocusView(3);
            z = false;
        }
        Log.d(TAG, "onAutoFocusMoving: mode=" + getFocusMode() + " show=" + z);
        if (this.mCameraFocusArea == null && !"auto".equals(getFocusMode())) {
            if (mainContentProtocol != null) {
                mainContentProtocol.setFocusViewType(false);
            }
            if (isFocusing) {
                if (this.mState != 2) {
                    setFocusState(1);
                    this.mHandler.removeMessages(0);
                    this.mHandler.removeMessages(1);
                    this.mHandler.sendEmptyMessageDelayed(1, (long) FORCE_RESET_TOUCH_FOCUS_DELAY);
                }
                Log.v(TAG, "Camera KPI: CAF start");
                this.mCafStartTime = System.currentTimeMillis();
                if (z && mainContentProtocol != null) {
                    mainContentProtocol.showIndicator(2, 1);
                    return;
                }
                return;
            }
            int i = this.mState;
            Log.v(TAG, "Camera KPI: CAF stop: Focus time: " + (System.currentTimeMillis() - this.mCafStartTime));
            if (isSuccess) {
                setFocusState(3);
                setLastFocusState(3);
            } else {
                setFocusState(4);
                setLastFocusState(4);
            }
            this.mHandler.removeMessages(0);
            this.mHandler.removeMessages(1);
            if (z && mainContentProtocol != null) {
                mainContentProtocol.showIndicator(2, isSuccess ? 2 : 3);
            }
            if (i == 2) {
                setFocusState(3);
                if (this.mPendingMultiCapture) {
                    multiCapture();
                } else {
                    capture();
                }
            }
        }
    }

    private boolean onlyAe() {
        return getTapAction() == 1;
    }

    private void resetFocusAreaToCenter() {
        int i = this.FOCUS_AREA_WIDTH;
        int i2 = this.FOCUS_AREA_HEIGHT;
        int i3 = this.mPreviewWidth;
        int i4 = this.mPreviewHeight;
        initializeFocusAreas(i, i2, i3 / 2, i4 / 2, i3, i4);
        initializeFocusIndicator(5, this.mPreviewWidth / 2, this.mPreviewHeight / 2);
    }

    private boolean resetFocusAreaToFaceArea() {
        RectF focusRect;
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol == null || !mainContentProtocol.isFaceExists(mainContentProtocol.getActiveIndicator()) || (focusRect = mainContentProtocol.getFocusRect(mainContentProtocol.getActiveIndicator())) == null) {
            return false;
        }
        this.mLatestFocusFace = focusRect;
        initializeFocusAreas(this.FOCUS_AREA_WIDTH, this.FOCUS_AREA_HEIGHT, (int) ((focusRect.left + focusRect.right) / 2.0f), (int) ((focusRect.top + focusRect.bottom) / 2.0f), this.mPreviewWidth, this.mPreviewHeight);
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setFocusState(int i) {
        Log.v(TAG, "setFocusState: " + i);
        this.mState = i;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setLastFocusState(int i) {
        this.mLastState = i;
    }

    private void startFocus(int i) {
        Log.d(TAG, "startFocus: " + i);
        setFocusMode("auto");
        Listener listener = this.mListener.get();
        this.mLastFocusFrom = i;
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if ((i != 1 || (mainContentProtocol != null && 1 == mainContentProtocol.getActiveIndicator())) && listener != null) {
            listener.stopObjectTracking(false);
        }
        if (listener != null) {
            listener.startFocus();
        }
        setFocusState(1);
        updateFocusUI();
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessageDelayed(1, (long) FORCE_RESET_TOUCH_FOCUS_DELAY);
    }

    private void unlockAeAwbIfNeeded() {
        if (this.mLockAeAwbNeeded && this.mAeAwbLock && this.mState != 2) {
            this.mAeAwbLock = false;
            Listener listener = this.mListener.get();
            if (listener != null) {
                listener.notifyFocusAreaUpdate();
            }
        }
    }

    public boolean canRecord() {
        if (!isFocusing()) {
            return true;
        }
        setFocusState(2);
        return false;
    }

    public void cancelFocus() {
        setFocusMode(CameraSettings.getFocusMode());
        resetTouchFocus(2);
        Listener listener = this.mListener.get();
        if (needAutoFocusCall()) {
            if (listener != null) {
                listener.cancelFocus(true);
            }
        } else if (listener != null) {
            listener.notifyFocusAreaUpdate();
        }
        if (2 != this.mState) {
            setFocusState(0);
        } else {
            Log.e(TAG, "waiting focus timeout!");
        }
        updateFocusUI();
        this.mCancelAutoFocusIfMove = false;
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
        Log.d(TAG, "cancelFocus");
    }

    public void cancelLongPressedAutoFocus() {
        if (!this.mCancelAutoFocusIfMove) {
            setLastFocusState(0);
        }
        this.mHandler.sendEmptyMessage(0);
    }

    public boolean cancelMultiSnapPending() {
        if (this.mState != 2 || !this.mPendingMultiCapture) {
            return false;
        }
        this.mPendingMultiCapture = false;
        return true;
    }

    public void destroy() {
        synchronized (this.mLock) {
            this.mDestroyed = true;
        }
        removeMessages();
        this.mFocusResultDisposable.dispose();
    }

    public void doMultiSnap(boolean z) {
        if (this.mInitialized) {
            if (!z) {
                multiCapture();
            }
            int i = this.mState;
            if (i == 3 || i == 4 || !needAutoFocusCall()) {
                multiCapture();
                return;
            }
            int i2 = this.mState;
            if (i2 == 1) {
                setFocusState(2);
                this.mPendingMultiCapture = true;
            } else if (i2 == 0) {
                multiCapture();
            }
        }
    }

    public void doSnap() {
        if (this.mInitialized) {
            int i = this.mState;
            if (i == 3 || i == 4 || !needAutoFocusCall()) {
                capture();
                return;
            }
            int i2 = this.mState;
            if (i2 == 1) {
                Listener listener = this.mListener.get();
                if (listener == null || !listener.shouldCaptureDirectly()) {
                    setFocusState(2);
                } else {
                    capture();
                }
            } else if (i2 == 0) {
                capture();
            }
        }
    }

    public boolean focusFaceArea() {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (isAutoFocusMode(getFocusMode()) && (mainContentProtocol == null || 2 != mainContentProtocol.getActiveIndicator())) {
            RectF focusRect = mainContentProtocol != null ? mainContentProtocol.getFocusRect(mainContentProtocol.getActiveIndicator()) : null;
            if (focusRect != null && !focusRect.isEmpty()) {
                RectF rectF = this.mLatestFocusFace;
                if (rectF != null && this.mLastFocusFrom == 1 && Math.abs(focusRect.left - rectF.left) < 80.0f) {
                    float f = focusRect.right - focusRect.left;
                    RectF rectF2 = this.mLatestFocusFace;
                    if (Math.abs(f - (rectF2.right - rectF2.left)) < 80.0f) {
                        return false;
                    }
                }
                this.mLatestFocusFace = focusRect;
                focusPoint((int) ((focusRect.left + focusRect.right) / 2.0f), (int) ((focusRect.top + focusRect.bottom) / 2.0f), 1, false);
                return true;
            }
        }
        return false;
    }

    public boolean getAeAwbLock() {
        return this.mAeAwbLock;
    }

    public int getCurrentFocusState() {
        return this.mState;
    }

    public MeteringRectangle[] getFocusAreas(Rect rect, Rect rect2) {
        Rect rect3 = this.mCameraFocusArea;
        if (rect3 == null) {
            return null;
        }
        return afaeRectangle(rect3, rect, rect2);
    }

    public String getFocusMode() {
        String str = this.mOverrideFocusMode;
        if (str != null) {
            return str;
        }
        if (this.mFocusMode == null) {
            this.mFocusMode = CameraSettings.getFocusMode();
        }
        Log.d(TAG, "getFocusMode=" + this.mFocusMode);
        return this.mFocusMode;
    }

    public int getLastFocusState() {
        return this.mLastState;
    }

    public MeteringRectangle[] getMeteringAreas(Rect rect, Rect rect2) {
        Rect rect3 = this.mCameraMeteringArea;
        if (rect3 == null) {
            return null;
        }
        return afaeRectangle(rect3, rect, rect2);
    }

    public MeteringRectangle[] getMeteringOrFocusAreas(int i, int i2, Rect rect, Rect rect2, boolean z) {
        float f;
        int i3;
        int i4;
        if (z) {
            i4 = this.FOCUS_AREA_WIDTH;
            i3 = this.FOCUS_AREA_HEIGHT;
            f = 1.0f;
            Point point = this.mFocusPoint;
            if (point == null) {
                this.mFocusPoint = new Point(i, i2);
            } else {
                point.x = i;
                point.y = i2;
            }
        } else {
            i4 = this.FOCUS_AREA_WIDTH;
            i3 = this.FOCUS_AREA_HEIGHT;
            f = 1.8f;
            Point point2 = this.mMeteringPoint;
            if (point2 == null) {
                this.mMeteringPoint = new Point(i, i2);
            } else {
                point2.x = i;
                point2.y = i2;
            }
        }
        Rect rect3 = new Rect();
        calculateTapArea(i4, i3, f, i, i2, this.mPreviewWidth, this.mPreviewHeight, rect3);
        return afaeRectangle(rect3, rect, rect2);
    }

    public boolean isFocusCompleted() {
        int i = this.mState;
        return i == 3 || i == 4;
    }

    public boolean isFocusing() {
        int i = this.mState;
        return i == 1 || i == 2;
    }

    public boolean isFocusingSnapOnFinish() {
        return this.mState == 2;
    }

    public boolean isFromTouch() {
        return this.mLastFocusFrom == 3;
    }

    public boolean isNeedCancelAutoFocus() {
        return this.mHandler.hasMessages(0) || this.mHandler.hasMessages(1) || this.mCancelAutoFocusIfMove;
    }

    public void onCameraReleased() {
        onPreviewStopped();
    }

    public void onDeviceBecomeStable() {
    }

    public void onDeviceKeepMoving(double d2) {
        if (Util.isTimeout(System.currentTimeMillis(), this.mLatestFocusTime, 3000) && !isMeteringFocusSplit()) {
            setLastFocusState(0);
            if (this.mCancelAutoFocusIfMove) {
                this.mHandler.sendEmptyMessage(0);
            }
        }
    }

    public void onFocusResult(FocusTask focusTask) {
        if (!this.mFocusResultDisposable.isDisposed()) {
            this.mFocusResultEmitter.onNext(focusTask);
        }
    }

    public void onPreviewStarted() {
        setFocusState(0);
    }

    public void onPreviewStopped() {
        setFocusState(0);
        resetTouchFocus(7);
        updateFocusUI();
    }

    public void onShutter() {
        updateFocusUI();
        this.mAeAwbLock = false;
    }

    public void onShutterDown() {
    }

    public void onShutterUp() {
    }

    public void onSingleTapUp(int i, int i2, boolean z) {
        focusPoint(i, i2, z ? 5 : 3, onlyAe());
    }

    public void overrideFocusMode(String str) {
        this.mOverrideFocusMode = str;
    }

    public void prepareCapture(boolean z, int i) {
        if (this.mInitialized) {
            String focusMode = getFocusMode();
            boolean z2 = false;
            boolean z3 = i != 2 || (!"auto".equals(focusMode) && !"macro".equals(focusMode)) || (this.mLastState != 3 && z);
            Log.v(TAG, "prepareCapture: " + z + "|" + i + "|" + focusMode);
            boolean equals = AutoFocus.LEGACY_CONTINUOUS_PICTURE.equals(focusMode);
            if (isFocusEnabled() && !equals && z3) {
                int i2 = this.mState;
                if (i2 != 3 && i2 != 4) {
                    ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
                    if (mainContentProtocol == null || !mainContentProtocol.isFaceExists(mainContentProtocol.getActiveIndicator())) {
                        resetFocusAreaToCenter();
                        startFocus(0);
                    } else {
                        focusFaceArea();
                    }
                } else if (z && this.mCameraFocusArea != null) {
                    this.mKeepFocusUIState = true;
                    startFocus(this.mLastFocusFrom);
                    this.mKeepFocusUIState = false;
                }
                z2 = true;
            }
            if (!z2 && z && equals) {
                if (!c.fn()) {
                    requestAutoFocus();
                } else if (this.mState == 1) {
                    cancelFocus();
                }
            }
        }
    }

    public void removeMessages() {
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
    }

    public void requestAutoFocus() {
        if (needAutoFocusCall() && this.mInitialized && this.mState != 2) {
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            int i = 4;
            if (isNeedCancelAutoFocus()) {
                Listener listener = this.mListener.get();
                if (listener != null) {
                    listener.cancelFocus(false);
                }
                if (mainContentProtocol != null) {
                    mainContentProtocol.clearFocusView(2);
                }
                setFocusState(0);
                this.mCancelAutoFocusIfMove = false;
                this.mHandler.removeMessages(0);
                this.mHandler.removeMessages(1);
            }
            if (resetFocusAreaToFaceArea()) {
                if (mainContentProtocol != null) {
                    mainContentProtocol.clearFocusView(9);
                }
                i = 1;
            } else {
                resetFocusAreaToCenter();
            }
            this.mAeAwbLock = false;
            Listener listener2 = this.mListener.get();
            if (listener2 != null) {
                listener2.notifyFocusAreaUpdate();
            }
            startFocus(i);
        }
    }

    public void resetAfterCapture(boolean z) {
        if (!z) {
            return;
        }
        if (this.mLastFocusFrom == 4) {
            Listener listener = this.mListener.get();
            if (listener != null) {
                listener.cancelFocus(false);
            }
            resetTouchFocus(7);
            removeMessages();
            return;
        }
        setLastFocusState(0);
    }

    public void resetFocusIndicator(int i) {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol != null) {
            mainContentProtocol.clearFocusView(i);
        }
    }

    public void resetFocusStateIfNeeded() {
        if (!isMeteringFocusSplit() && !this.mAeAwbLock) {
            this.mCameraFocusArea = null;
            this.mCameraMeteringArea = null;
            setFocusState(0);
            setLastFocusState(0);
            this.mCancelAutoFocusIfMove = false;
            if (!this.mHandler.hasMessages(0)) {
                this.mHandler.sendEmptyMessage(0);
            }
        }
    }

    public void resetFocused() {
        setFocusState(0);
    }

    public void resetTouchFocus(int i) {
        if (this.mInitialized) {
            this.mCameraFocusArea = null;
            this.mCameraMeteringArea = null;
            this.mCancelAutoFocusIfMove = false;
            resetFocusIndicator(i);
        }
    }

    public void setAeAwbLock(boolean z) {
        this.mAeAwbLock = z;
    }

    public void setCharacteristics(CameraCapabilities cameraCapabilities) {
        this.mFocusAreaSupported = cameraCapabilities.isAFRegionSupported();
        this.mMeteringAreaSupported = cameraCapabilities.isAERegionSupported();
        boolean z = false;
        this.mLockAeAwbNeeded = cameraCapabilities.isAELockSupported() || cameraCapabilities.isAWBLockSupported();
        this.mSupportedFocusModes = AutoFocus.convertToLegacyFocusModes(cameraCapabilities.getSupportedFocusModes());
        this.mActiveArraySize = cameraCapabilities.getActiveArraySize();
        if (DataRepository.dataItemFeature().si() && !this.mFocusAreaSupported && this.mMeteringAreaSupported && cameraCapabilities.isAELockSupported()) {
            z = true;
        }
        this.mAELockOnlySupported = z;
        Log.d(TAG, "setCharacteristics: mFocusAreaSupported = " + this.mFocusAreaSupported + ", mAELockOnlySupported = " + this.mAELockOnlySupported);
    }

    public String setFocusMode(String str) {
        if (str == null) {
            Log.e(TAG, "setFocusMode: null focus mode", new RuntimeException());
            return str;
        }
        if ("auto".equals(str) || !Util.isSupported(str, this.mSupportedFocusModes)) {
            this.mFocusMode = "auto";
        } else {
            this.mFocusMode = str;
        }
        if (AutoFocus.LEGACY_CONTINUOUS_PICTURE.equals(this.mFocusMode) || AutoFocus.LEGACY_CONTINUOUS_VIDEO.equals(this.mFocusMode)) {
            this.mLastFocusFrom = -1;
        }
        return this.mFocusMode;
    }

    @Override // com.android.camera.FocusManagerAbstract
    public void setPreviewSize(int i, int i2) {
        if (this.mPreviewWidth != i || this.mPreviewHeight != i2) {
            this.mPreviewWidth = i;
            this.mPreviewHeight = i2;
            Log.d(TAG, "setPreviewSize: " + this.mPreviewWidth + "x" + this.mPreviewHeight);
            setMatrix();
        }
    }

    public void updateFocusUI() {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (this.mInitialized && !this.mKeepFocusUIState && mainContentProtocol != null) {
            int activeIndicator = 1 == this.mLastFocusFrom ? mainContentProtocol.getActiveIndicator() : 2;
            int i = this.mState;
            if (i != 0) {
                if (i == 1 || i == 2) {
                    mainContentProtocol.showIndicator(activeIndicator, 1);
                    return;
                }
                int i2 = 3;
                if (i == 3) {
                    mainContentProtocol.showIndicator(activeIndicator, 2);
                } else if (i == 4) {
                    if (AutoFocus.LEGACY_CONTINUOUS_PICTURE.equals(this.mFocusMode) || AutoFocus.LEGACY_CONTINUOUS_VIDEO.equals(this.mFocusMode)) {
                        i2 = 2;
                    }
                    mainContentProtocol.showIndicator(activeIndicator, i2);
                }
            } else if (activeIndicator == 2) {
                mainContentProtocol.clearFocusView(7);
            } else {
                mainContentProtocol.clearIndicator(activeIndicator);
            }
        }
    }
}
