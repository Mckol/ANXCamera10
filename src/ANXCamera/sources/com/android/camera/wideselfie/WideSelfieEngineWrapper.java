package com.android.camera.wideselfie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MiuiSettings.ScreenEffect;
import android.text.TextUtils;
import android.util.SizeF;
import com.android.camera.R;
import com.android.camera.log.Log;
import com.arcsoft.camera.wideselfie.AwsInitParameter;
import com.arcsoft.camera.wideselfie.ProcessResult;
import com.arcsoft.camera.wideselfie.WideSelfieCallback;
import com.arcsoft.camera.wideselfie.WideSelfieEngine;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

public class WideSelfieEngineWrapper {
    private static final int AWS_STATE_CAPTURING = 8194;
    private static final int AWS_STATE_PREVIEW = 8193;
    private static final int AWS_STATE_STITCHING = 8195;
    public static final int DIRECTION_MOVE_LEFT = 1;
    public static final int DIRECTION_MOVE_RIGHT = 0;
    public static final int DIRECTION_NONE = -1;
    private static final String TAG = "WideSelfieEngine";
    private SizeF mAngleSize;
    private volatile boolean mCanStopCapture = false;
    private Context mContext = null;
    /* access modifiers changed from: private */
    public volatile boolean mEngineInitialized = false;
    private int mFullImageHeight = 0;
    private int mFullImageWidth = 0;
    @SuppressLint({"HandlerLeak"})
    private Handler mHandle = new Handler(Looper.getMainLooper()) {
        /* JADX WARNING: Removed duplicated region for block: B:25:0x0103  */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x0108  */
        /* JADX WARNING: Removed duplicated region for block: B:29:0x011f  */
        public void handleMessage(Message message) {
            Rect rect;
            int i;
            ProcessResult processResult = (ProcessResult) message.obj;
            int i2 = message.arg1;
            int i3 = message.arg2;
            String str = WideSelfieEngineWrapper.TAG;
            if (i2 != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("ProcessResult resultCode ");
                sb.append(i2);
                Log.w(str, sb.toString());
            }
            WideSelfStateCallback wideSelfStateCallback = (WideSelfStateCallback) WideSelfieEngineWrapper.this.mWideSelfCallbackRef.get();
            if (i3 == 0) {
                if (WideSelfieEngineWrapper.this.mWideSelfieOrientation % 180 == 90) {
                    rect = new Rect(processResult.progressBarThumbOffset.x, WideSelfieEngineWrapper.this.mPreviewRect.top + processResult.progressBarThumbOffset.y, WideSelfieEngineWrapper.this.mPreviewRect.width() + processResult.progressBarThumbOffset.x, WideSelfieEngineWrapper.this.mPreviewRect.height() + WideSelfieEngineWrapper.this.mPreviewRect.top + processResult.progressBarThumbOffset.y);
                } else {
                    int i4 = WideSelfieEngineWrapper.this.mPreviewRect.left;
                    Point point = processResult.progressBarThumbOffset;
                    rect = new Rect(i4 + point.x, -point.y, WideSelfieEngineWrapper.this.mPreviewRect.width() + WideSelfieEngineWrapper.this.mPreviewRect.left + processResult.progressBarThumbOffset.x, WideSelfieEngineWrapper.this.mPreviewRect.height() - processResult.progressBarThumbOffset.y);
                }
                Rect rect2 = rect;
                if (wideSelfStateCallback != null) {
                    int i5 = WideSelfieEngineWrapper.this.mWideSelfieOrientation % 180 == 90 ? processResult.progressBarThumbOffset.y : -processResult.progressBarThumbOffset.x;
                    if (i5 > 0) {
                        if (i5 > WideSelfieEngineWrapper.this.mPreviewRect.top) {
                            i = i5 - WideSelfieEngineWrapper.this.mPreviewRect.top;
                            int i6 = WideSelfieEngineWrapper.this.mWideSelfieOrientation % 180 == 90 ? processResult.progressBarThumbOffset.x : -processResult.progressBarThumbOffset.y;
                            wideSelfStateCallback.onPreviewUpdate(processResult.progressBarThumbArray, processResult.progressBarThumbImageWidth, processResult.progressBarThumbImageHeight, processResult.progressBarThumbRect, rect2);
                            wideSelfStateCallback.onMove(processResult.direction, processResult.progress, new Point(i, i6), processResult.progressBarThumbOffset, processResult.progress != 100 || i2 == 28679);
                        }
                    } else if ((-i5) > WideSelfieEngineWrapper.this.mPreviewRect.top) {
                        i = i5 + WideSelfieEngineWrapper.this.mPreviewRect.top;
                        if (WideSelfieEngineWrapper.this.mWideSelfieOrientation % 180 == 90) {
                        }
                        wideSelfStateCallback.onPreviewUpdate(processResult.progressBarThumbArray, processResult.progressBarThumbImageWidth, processResult.progressBarThumbImageHeight, processResult.progressBarThumbRect, rect2);
                        wideSelfStateCallback.onMove(processResult.direction, processResult.progress, new Point(i, i6), processResult.progressBarThumbOffset, processResult.progress != 100 || i2 == 28679);
                    }
                    i = 0;
                    if (WideSelfieEngineWrapper.this.mWideSelfieOrientation % 180 == 90) {
                    }
                    wideSelfStateCallback.onPreviewUpdate(processResult.progressBarThumbArray, processResult.progressBarThumbImageWidth, processResult.progressBarThumbImageHeight, processResult.progressBarThumbRect, rect2);
                    wideSelfStateCallback.onMove(processResult.direction, processResult.progress, new Point(i, i6), processResult.progressBarThumbOffset, processResult.progress != 100 || i2 == 28679);
                }
            } else if (i3 == 2) {
                WideSelfieEngineWrapper.this.mWideSelfieCurrentCommand = -1;
                if (wideSelfStateCallback != null) {
                    byte[] bArr = processResult.resultImageArray;
                    if (bArr != null) {
                        wideSelfStateCallback.onNv21Available(bArr, processResult.resultImageWidth, processResult.resultImageHeight, WideSelfieEngineWrapper.this.mStitchResult);
                        WideSelfieEngine.getSingleInstance().uninit();
                        WideSelfieEngine.getSingleInstance().setOnCallback(null);
                        WideSelfieEngineWrapper.this.mEngineInitialized = false;
                        Log.d(str, "WideSelfieEngine uninit");
                    }
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append("not save, callback ");
                sb2.append(wideSelfStateCallback);
                sb2.append(", resultImageArray = ");
                sb2.append(processResult.resultImageArray);
                Log.w(str, sb2.toString());
                WideSelfieEngine.getSingleInstance().uninit();
                WideSelfieEngine.getSingleInstance().setOnCallback(null);
                WideSelfieEngineWrapper.this.mEngineInitialized = false;
                Log.d(str, "WideSelfieEngine uninit");
            }
        }
    };
    private int mPreviewHeight = 0;
    /* access modifiers changed from: private */
    public Rect mPreviewRect;
    private int mPreviewWidth = 0;
    private Sensor mSensor;
    private SensorEventListener mSensorEventListener = new MySensorEventListener();
    private SensorManager mSensorManager;
    private boolean mSensorRegisted = false;
    private AtomicInteger mState = new AtomicInteger(AWS_STATE_PREVIEW);
    /* access modifiers changed from: private */
    public int mStitchResult = 0;
    private int mThumbBgWidth;
    /* access modifiers changed from: private */
    public WeakReference<WideSelfStateCallback> mWideSelfCallbackRef;
    private final WideSelfieCallback mWideSelfieCallback = new MyWideSelfieCallback(this);
    /* access modifiers changed from: private */
    public int mWideSelfieCurrentCommand;
    /* access modifiers changed from: private */
    public int mWideSelfieOrientation = 90;
    private int nThumbnailHeight = 0;
    private int nThumbnailWidth = 0;

    private static class MySensorEventListener implements SensorEventListener {
        private MySensorEventListener() {
        }

        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            Sensor sensor = sensorEvent.sensor;
            if (sensor != null && sensorEvent.accuracy != 0 && sensor.getType() == 4) {
                WideSelfieEngine.getSingleInstance().setSensorData(sensorEvent.values, sensorEvent.timestamp, sensorEvent.sensor.getType());
            }
        }
    }

    private static class MyWideSelfieCallback implements WideSelfieCallback {
        private WeakReference<WideSelfieEngineWrapper> mOuterRef;

        public MyWideSelfieCallback(WideSelfieEngineWrapper wideSelfieEngineWrapper) {
            this.mOuterRef = new WeakReference<>(wideSelfieEngineWrapper);
        }

        public void onProcessCallback(int i, ProcessResult processResult) {
            if (processResult != null) {
                WideSelfieEngineWrapper wideSelfieEngineWrapper = (WideSelfieEngineWrapper) this.mOuterRef.get();
                if (wideSelfieEngineWrapper != null) {
                    wideSelfieEngineWrapper.handleProcessCallback(i, processResult);
                }
            }
        }
    }

    public interface WideSelfStateCallback {
        void onMove(int i, int i2, Point point, Point point2, boolean z);

        void onNv21Available(byte[] bArr, int i, int i2, int i3);

        void onPreviewUpdate(byte[] bArr, int i, int i2, Rect rect, Rect rect2);

        void onWideSelfCompleted();
    }

    public WideSelfieEngineWrapper(Context context, WideSelfStateCallback wideSelfStateCallback) {
        this.mContext = context;
        this.mWideSelfCallbackRef = new WeakReference<>(wideSelfStateCallback);
        this.mSensorManager = (SensorManager) context.getSystemService("sensor");
        this.mSensor = this.mSensorManager.getDefaultSensor(4);
        this.mThumbBgWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.wide_selfie_progress_thumbnail_background_width);
    }

    private SizeF getAngleValue(CameraCharacteristics cameraCharacteristics) {
        SizeF sizeF = (SizeF) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
        float[] fArr = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
        if (fArr == null || fArr.length <= 0) {
            return null;
        }
        return new SizeF((float) Math.toDegrees(StrictMath.atan((double) (sizeF.getWidth() / (fArr[0] * 2.0f))) * 2.0d), (float) Math.toDegrees(StrictMath.atan((double) (sizeF.getHeight() / (fArr[0] * 2.0f))) * 2.0d));
    }

    /* access modifiers changed from: private */
    public void handleProcessCallback(int i, ProcessResult processResult) {
        Message obtainMessage = this.mHandle.obtainMessage();
        obtainMessage.obj = processResult;
        obtainMessage.arg1 = i;
        obtainMessage.arg2 = this.mWideSelfieCurrentCommand;
        this.mHandle.sendMessage(obtainMessage);
    }

    public void onBurstCapture(byte[] bArr) {
        boolean z = this.mEngineInitialized;
        String str = TAG;
        if (!z) {
            Log.w(str, "onBurstCapture mEngineInitialized false");
            return;
        }
        if (this.mState.get() == 8194) {
            Log.d(str, "onBurstCapture AWS_STATE_CAPTURING");
            this.mWideSelfieCurrentCommand = 0;
            WideSelfieEngine.getSingleInstance().process(0, bArr);
            if (!this.mCanStopCapture) {
                this.mCanStopCapture = true;
            }
        } else if (this.mState.compareAndSet(AWS_STATE_STITCHING, AWS_STATE_PREVIEW)) {
            Log.d(str, "onBurstCapture STITCHING E");
            byte[] bArr2 = new byte[1];
            WideSelfStateCallback wideSelfStateCallback = (WideSelfStateCallback) this.mWideSelfCallbackRef.get();
            if (wideSelfStateCallback != null) {
                wideSelfStateCallback.onWideSelfCompleted();
            }
            this.mWideSelfieCurrentCommand = 1;
            this.mStitchResult = WideSelfieEngine.getSingleInstance().process(1, bArr2);
            StringBuilder sb = new StringBuilder();
            sb.append("onBurstCapture mStitchResult ");
            sb.append(this.mStitchResult);
            Log.d(str, sb.toString());
            this.mWideSelfieCurrentCommand = 2;
            WideSelfieEngine.getSingleInstance().process(2, bArr2);
            Log.d(str, "onBurstCapture STITCHING X");
            this.mCanStopCapture = false;
        }
    }

    public void onDestroy() {
        this.mHandle.removeCallbacksAndMessages(null);
    }

    public void pause() {
        if (this.mSensorRegisted) {
            this.mSensorManager.unregisterListener(this.mSensorEventListener);
            this.mSensorRegisted = false;
        }
    }

    public void resume() {
        if (!this.mSensorRegisted) {
            this.mSensorManager.registerListener(this.mSensorEventListener, this.mSensor, 10000);
            this.mSensorRegisted = true;
        }
    }

    public void setCameraParameter(String str, int i, int i2, int i3, int i4) {
        if (!TextUtils.isEmpty(str)) {
            this.mPreviewWidth = i;
            this.mPreviewHeight = i2;
            this.mFullImageWidth = i3;
            this.mFullImageHeight = i4;
            float dimensionPixelSize = ((float) i) / ((float) this.mContext.getResources().getDimensionPixelSize(R.dimen.wide_selfie_progress_thumbnail_background_height));
            this.nThumbnailWidth = (int) (((float) this.mPreviewWidth) / dimensionPixelSize);
            this.nThumbnailHeight = (int) (((float) this.mPreviewHeight) / dimensionPixelSize);
            StringBuilder sb = new StringBuilder();
            sb.append("mFullImageWidth = ");
            sb.append(this.mFullImageWidth);
            sb.append(", mFullImageHeight = ");
            sb.append(this.mFullImageHeight);
            Log.d(TAG, sb.toString());
            try {
                this.mAngleSize = getAngleValue(((CameraManager) this.mContext.getSystemService("camera")).getCameraCharacteristics(str));
            } catch (CameraAccessException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void setOrientation(int i) {
        this.mWideSelfieOrientation = i;
    }

    public int startCapture() {
        int i;
        StringBuilder sb = new StringBuilder();
        sb.append("startCapture orientation = ");
        sb.append(this.mWideSelfieOrientation);
        String sb2 = sb.toString();
        String str = TAG;
        Log.d(str, sb2);
        AwsInitParameter defaultInitParams = AwsInitParameter.getDefaultInitParams(this.mFullImageWidth, this.mFullImageHeight, 2050, this.mWideSelfieOrientation);
        WideSelfieConfig instance = WideSelfieConfig.getInstance(this.mContext);
        if (this.mWideSelfieOrientation % 180 == 90) {
            i = (this.mFullImageHeight * (instance.getThumbBgWidth() - 2)) / instance.getStillPreviewWidth();
            int i2 = this.mThumbBgWidth;
            int i3 = this.nThumbnailHeight;
            int i4 = (i2 - i3) / 2;
            this.mPreviewRect = new Rect(0, i4, this.nThumbnailWidth, i3 + i4);
        } else {
            i = (this.mFullImageWidth * (instance.getThumbBgHeightVertical() - 2)) / instance.getStillPreviewHeight();
            int i5 = this.mThumbBgWidth;
            int i6 = this.nThumbnailWidth;
            int i7 = (i5 - i6) / 2;
            this.mPreviewRect = new Rect(i7, 0, i6 + i7, this.nThumbnailHeight);
        }
        int i8 = this.mThumbBgWidth;
        int i9 = this.nThumbnailHeight;
        int i10 = (i8 - i9) / 2;
        this.mPreviewRect = new Rect(0, i10, this.nThumbnailWidth, i9 + i10);
        defaultInitParams.maxResultWidth = i;
        defaultInitParams.progressBarThumbHeight = this.mWideSelfieOrientation % 180 == 90 ? this.nThumbnailWidth : this.nThumbnailHeight;
        defaultInitParams.thumbnailWidth = 480;
        defaultInitParams.thumbnailHeight = ScreenEffect.SCREEN_PAPER_MODE_TWILIGHT_START_DEAULT;
        defaultInitParams.guideStopBarThumbHeight = 0;
        defaultInitParams.cameraViewAngleForWidth = this.mAngleSize.getWidth();
        defaultInitParams.cameraViewAngleForHeight = this.mAngleSize.getHeight();
        Log.d(str, String.format("startCapture maxResultWidth = %d, progressBarThumbHeight = %d, thumbnailWidth = %d, thumbnailHeight = %d,  guideStopBarThumbHeight = %d", new Object[]{Integer.valueOf(i), Integer.valueOf(defaultInitParams.progressBarThumbHeight), Integer.valueOf(defaultInitParams.thumbnailWidth), Integer.valueOf(defaultInitParams.thumbnailHeight), Integer.valueOf(defaultInitParams.guideStopBarThumbHeight)}));
        int init = WideSelfieEngine.getSingleInstance().init(defaultInitParams);
        this.mEngineInitialized = true;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("WideSelfieEngine init, result = ");
        sb3.append(init);
        Log.d(str, sb3.toString());
        WideSelfieEngine.getSingleInstance().setOnCallback(this.mWideSelfieCallback);
        this.mState.set(8194);
        return init;
    }

    public boolean stopCapture() {
        String str = TAG;
        Log.d(str, "stopCapture E");
        if (!this.mCanStopCapture) {
            Log.w(str, "stopCapture failed, can't stop");
            return false;
        } else if (!this.mState.compareAndSet(8194, AWS_STATE_STITCHING)) {
            Log.w(str, "stopCapture failed, error state");
            return false;
        } else {
            Log.d(str, "stopCapture X");
            return true;
        }
    }
}
