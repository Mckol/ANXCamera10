package com.android.camera;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.statistic.CameraStat;
import com.android.camera.statistic.CameraStatUtil;
import com.mi.config.b;

public class ProximitySensorLock implements SensorEventListener {
    private static final int DELAY_CHECK_TIMEOUT = 300;
    private static final int MSG_DELAY_CHECK = 2;
    private static final int MSG_TIMEOUT = 1;
    private static final int PROXIMITY_THRESHOLD = 3;
    private static final int SHORTCUT_UNLOCK = (getKeyBitmask(4) | getKeyBitmask(24));
    private static final String TAG = "ProximitySensorLock";
    private static final int TIMEOUT = 30000;
    private Context mContext;
    private final boolean mFromVolumeKey;
    /* access modifiers changed from: private */
    public View mHintView;
    private volatile boolean mJudged;
    private int mKeyPressed;
    private int mKeyPressing;
    /* access modifiers changed from: private */
    public Boolean mProximityNear = null;
    private Sensor mProximitySensor;
    /* access modifiers changed from: private */
    public volatile boolean mResumeCalled;
    private Handler mWorkerHandler;
    private HandlerThread mWorkerThread;

    public ProximitySensorLock(Context context) {
        this.mContext = context;
        if (context instanceof Activity) {
            Intent intent = ((Activity) context).getIntent();
            int flags = intent.getFlags();
            if (DataRepository.dataItemFeature().Sa()) {
                this.mFromVolumeKey = TextUtils.equals(intent.getStringExtra(CameraIntentManager.EXTRA_LAUNCH_SOURCE), "power_double_tap");
            } else {
                this.mFromVolumeKey = (8388608 & flags) == 0;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("from volume key ->");
            sb.append(this.mFromVolumeKey);
            Log.d(TAG, sb.toString());
        } else {
            this.mFromVolumeKey = false;
        }
        resetKeyStatus();
        this.mJudged = false;
        this.mWorkerThread = new HandlerThread("Proximity sensor lock");
        this.mWorkerThread.start();
        this.mWorkerHandler = new Handler(this.mWorkerThread.getLooper()) {
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    CameraStatUtil.trackPocketModeExit(CameraStat.POCKET_MODE_KEYGUARD_EXIT_TIMEOUT);
                    ProximitySensorLock.this.exit();
                } else if (i == 2) {
                    removeMessages(2);
                    if (ProximitySensorLock.this.mProximityNear == null) {
                        Log.d(ProximitySensorLock.TAG, "delay check timeout, callback not returned, take it as far");
                        CameraStatUtil.trackPocketModeSensorDelay();
                        ProximitySensorLock.this.mProximityNear = Boolean.valueOf(false);
                        if (!ProximitySensorLock.this.isFromSnap() && ProximitySensorLock.this.mResumeCalled) {
                            ProximitySensorLock.this.judge();
                        }
                    }
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public void doShow() {
        if (this.mWorkerHandler != null && !active()) {
            FrameLayout frameLayout = (FrameLayout) ((Activity) this.mContext).findViewById(16908290);
            if (frameLayout != null) {
                if (this.mHintView == null) {
                    this.mHintView = inflateHint();
                }
                frameLayout.addView(this.mHintView);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mHintView, View.ALPHA, new float[]{0.0f, 1.0f});
                ofFloat.setDuration(500);
                ofFloat.start();
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                alphaAnimation.setDuration(500);
                alphaAnimation.setRepeatCount(-1);
                alphaAnimation.setRepeatMode(2);
                alphaAnimation.setStartOffset(500);
                this.mHintView.findViewById(R.id.screen_on_proximity_sensor_hint_animation).startAnimation(alphaAnimation);
                resetKeyStatus();
                this.mWorkerHandler.sendEmptyMessageDelayed(1, 30000);
            }
        }
    }

    public static boolean enabled() {
        return supported() ? CameraSettings.isProximityLockOpen() : Util.isNonUIEnabled();
    }

    /* access modifiers changed from: private */
    public void exit() {
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        if (!(context instanceof Activity) || !((Activity) context).isFinishing()) {
            Log.d(TAG, "Finish activity, exiting.");
            ((Activity) this.mContext).finish();
        }
    }

    private static int getKeyBitmask(int i) {
        if (i == 3) {
            return 4;
        }
        if (i == 4) {
            return 8;
        }
        if (i == 82 || i == 187) {
            return 2;
        }
        switch (i) {
            case 24:
                return 64;
            case 25:
                return 32;
            case 26:
                return 16;
            default:
                return 1;
        }
    }

    private void hide() {
        resetKeyStatus();
        Handler handler = this.mWorkerHandler;
        if (handler != null) {
            handler.removeMessages(1);
        }
        Context context = this.mContext;
        if (context != null && (context instanceof Activity)) {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    if (ProximitySensorLock.this.mHintView != null) {
                        ViewGroup viewGroup = (ViewGroup) ProximitySensorLock.this.mHintView.getParent();
                        if (viewGroup != null) {
                            viewGroup.removeView(ProximitySensorLock.this.mHintView);
                        }
                    }
                }
            });
        }
    }

    private View inflateHint() {
        return LayoutInflater.from(this.mContext).inflate(R.layout.screen_on_proximity_sensor_guide, null, false);
    }

    private static boolean isEllipticProximity() {
        return VERSION.SDK_INT >= 28 ? SystemProperties.getBoolean("ro.vendor.audio.us.proximity", false) : SystemProperties.getBoolean("ro.audio.us.proximity", false);
    }

    /* access modifiers changed from: private */
    public boolean isFromSnap() {
        return !(this.mContext instanceof Activity);
    }

    /* access modifiers changed from: private */
    public void judge() {
        if (this.mFromVolumeKey && this.mProximityNear.booleanValue()) {
            CameraStatUtil.trackPocketModeEnter(CameraStat.POCKET_MODE_PSENSOR_ENTER_VOLUME);
            stopWatching();
            exit();
        } else if (this.mProximityNear.booleanValue()) {
            CameraStatUtil.trackPocketModeEnter(CameraStat.POCKET_MODE_PSENSOR_ENTER_KEYGUARD);
            show();
        } else {
            stopWatching();
        }
        this.mJudged = true;
    }

    private void resetKeyStatus() {
        this.mKeyPressed = 0;
        this.mKeyPressing = 0;
    }

    private boolean shouldBeBlocked(KeyEvent keyEvent) {
        if (keyEvent != null && active()) {
            int keyCode = keyEvent.getKeyCode();
            if (!(keyCode == 79 || keyCode == 126 || keyCode == 127)) {
                switch (keyCode) {
                    case 85:
                    case 86:
                    case 87:
                        break;
                    default:
                        return true;
                }
            }
        }
        return false;
    }

    private void show() {
        if (enabled() && !this.mFromVolumeKey && this.mWorkerHandler != null) {
            Context context = this.mContext;
            if (context != null && (context instanceof Activity)) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    public void run() {
                        ProximitySensorLock.this.doShow();
                    }
                });
            }
        }
    }

    private void stopWatching() {
        if (this.mProximitySensor != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("stopWatching proximity sensor ");
            sb.append(this.mContext);
            Log.d(TAG, sb.toString());
            ((SensorManager) this.mContext.getSystemService("sensor")).unregisterListener(this);
            this.mProximitySensor = null;
            stopWorkerThread();
        }
    }

    private void stopWorkerThread() {
        HandlerThread handlerThread = this.mWorkerThread;
        if (handlerThread != null) {
            if (VERSION.SDK_INT >= 19) {
                handlerThread.quitSafely();
            } else {
                handlerThread.quit();
            }
            this.mWorkerThread = null;
        }
        this.mWorkerHandler = null;
        this.mJudged = false;
        this.mResumeCalled = false;
    }

    public static boolean supported() {
        return b.Qi() && !isEllipticProximity();
    }

    public boolean active() {
        View view = this.mHintView;
        return (view == null || view.getParent() == null) ? false : true;
    }

    public void destroy() {
        Log.d(TAG, "destroying");
        hide();
        stopWatching();
        stopWorkerThread();
        this.mJudged = false;
        this.mResumeCalled = false;
        this.mContext = null;
    }

    public boolean intercept(KeyEvent keyEvent) {
        boolean z = false;
        if (!enabled() || !active() || !shouldBeBlocked(keyEvent)) {
            return false;
        }
        if (keyEvent.getAction() == 0) {
            z = true;
        }
        int keyBitmask = getKeyBitmask(keyEvent.getKeyCode());
        if (this.mKeyPressing == 0) {
            resetKeyStatus();
        }
        if (z) {
            this.mKeyPressed |= keyBitmask;
            this.mKeyPressing = keyBitmask | this.mKeyPressing;
        } else {
            this.mKeyPressing = (~keyBitmask) & this.mKeyPressing;
        }
        if (this.mKeyPressed == SHORTCUT_UNLOCK) {
            CameraStatUtil.trackPocketModeExit(CameraStat.POCKET_MODE_KEYGUARD_EXIT_DISMISS);
            hide();
            stopWatching();
        }
        return true;
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onResume() {
        StringBuilder sb = new StringBuilder();
        sb.append("onResume enabled ");
        sb.append(enabled());
        sb.append(", mFromVolumeKey ");
        sb.append(this.mFromVolumeKey);
        sb.append(", mProximityNear ");
        sb.append(this.mProximityNear);
        Log.d(TAG, sb.toString());
        if (enabled()) {
            this.mResumeCalled = true;
            if (this.mProximityNear != null) {
                judge();
            }
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        boolean z = true;
        boolean z2 = this.mProximityNear == null;
        float[] fArr = sensorEvent.values;
        if (fArr[0] <= 3.0f && fArr[0] != sensorEvent.sensor.getMaximumRange()) {
            z = false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("onSensorChanged near ");
        sb.append(!z);
        sb.append(", values ");
        sb.append(sensorEvent.values[0]);
        sb.append(", max ");
        sb.append(sensorEvent.sensor.getMaximumRange());
        Log.d(TAG, sb.toString());
        this.mProximityNear = Boolean.valueOf(!z);
        Handler handler = this.mWorkerHandler;
        if (handler != null) {
            boolean hasMessages = handler.hasMessages(2);
            this.mWorkerHandler.removeMessages(2);
            if (isFromSnap() || !this.mResumeCalled) {
                return;
            }
            if (!z2 || !hasMessages) {
                if (!this.mFromVolumeKey && this.mJudged) {
                    if (z) {
                        CameraStatUtil.trackPocketModeExit(CameraStat.POCKET_MODE_KEYGUARD_EXIT_UNLOCK);
                        hide();
                    } else {
                        CameraStatUtil.trackPocketModeEnter(CameraStat.POCKET_MODE_PSENSOR_ENTER_KEYGUARD);
                        show();
                    }
                }
                return;
            }
            judge();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0039  */
    public boolean shouldQuitSnap() {
        boolean z;
        StringBuilder sb = new StringBuilder();
        sb.append("shouldQuit fromSnap ");
        sb.append(isFromSnap());
        sb.append(", proximity ->");
        sb.append(this.mProximityNear);
        Log.d(TAG, sb.toString());
        if (isFromSnap()) {
            Boolean bool = this.mProximityNear;
            if (bool == null || bool.booleanValue()) {
                z = true;
                if (z) {
                    CameraStatUtil.trackPocketModeEnter(CameraStat.POCKET_MODE_PSENSOR_ENTER_SNAP);
                }
                return z;
            }
        }
        z = false;
        if (z) {
        }
        return z;
    }

    public void startWatching() {
        if (enabled() && this.mProximitySensor == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("startWatching proximity sensor ");
            sb.append(this.mContext);
            Log.d(TAG, sb.toString());
            this.mJudged = false;
            this.mResumeCalled = false;
            SensorManager sensorManager = (SensorManager) this.mContext.getSystemService("sensor");
            this.mProximitySensor = sensorManager.getDefaultSensor(8);
            sensorManager.registerListener(this, this.mProximitySensor, 0, this.mWorkerHandler);
            this.mWorkerHandler.removeMessages(2);
            this.mWorkerHandler.sendEmptyMessageDelayed(2, 300);
        }
    }
}
