package com.android.camera.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.bottom.BottomAnimationConfig;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.log.Log;
import com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable;
import com.android.camera.ui.drawable.snap.PaintConditionReferred;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CameraSnapView extends View {
    private static final int LONG_PRESS_TIME = 800;
    private static final int MSG_START_CLICK = 1;
    private static final int MSG_START_LONG_PRESS = 2;
    private static final int MSG_START_LONG_PRESS_CANCEL_IN = 4;
    private static final int MSG_START_LONG_PRESS_CANCEL_OUT = 3;
    private static final float RECT_WIDTH = 0.75f;
    private static final String TAG = "CameraSnapView";
    private CameraSnapAnimateDrawable cameraSnapAnimateDrawable;
    private PaintConditionReferred mCurrentCondition;
    private int mCurrentMode;
    private boolean mEnableSnapClick = true;
    private Bitmap mExtraBitmap;
    private Matrix mExtraBitmapMatrix;
    private Paint mExtraBitmapPaint;
    private Handler mHandler = new Handler() {
        /* class com.android.camera.ui.CameraSnapView.AnonymousClass1 */

        public void handleMessage(Message message) {
            if (CameraSnapView.this.mSnapListener != null) {
                int i = message.what;
                if (i == 1) {
                    CameraSnapView.this.mSnapListener.onSnapClick();
                } else if (i == 2) {
                    CameraSnapView.this.mSnapListener.onSnapLongPress();
                } else if (i == 3) {
                    CameraSnapView.this.mHasCancelByOutside = true;
                    CameraSnapView.this.mSnapListener.onSnapLongPressCancelOut();
                } else if (i == 4) {
                    CameraSnapView.this.mSnapListener.onSnapLongPressCancelIn();
                }
            }
        }
    };
    private boolean mHasCancelByOutside = false;
    private int mHeight;
    private int mLongPressTime = 800;
    private boolean mMissTaken;
    private long mPressDownTime;
    private long mPressUpTime;
    private SnapListener mSnapListener;
    private int mWidth;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SnapEvent {
    }

    public interface SnapListener {
        boolean canSnap();

        void onSnapClick();

        void onSnapLongPress();

        void onSnapLongPressCancelIn();

        void onSnapLongPressCancelOut();

        void onSnapPrepare();

        void onTrackSnapMissTaken(long j);

        void onTrackSnapTaken(long j);
    }

    public CameraSnapView(Context context) {
        super(context);
        initView();
    }

    public CameraSnapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public CameraSnapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    private void initView() {
    }

    private boolean onTouchDownDefaultHandler(int i) {
        if (!this.mSnapListener.canSnap()) {
            if (!this.mMissTaken) {
                this.mMissTaken = true;
                if (this.mPressUpTime > 0) {
                    this.mSnapListener.onTrackSnapMissTaken(System.currentTimeMillis() - this.mPressUpTime);
                }
            }
            Log.d(TAG, "can not snap");
            return false;
        }
        ViberatorContext.getInstance(getContext().getApplicationContext()).performSnapClick();
        this.cameraSnapAnimateDrawable.startScaleDownAnimation(i);
        return true;
    }

    private void recycleBitmap() {
        Bitmap bitmap = this.mExtraBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mExtraBitmap = null;
        }
    }

    private void setImageBitmap(Bitmap bitmap) {
        this.mExtraBitmap = bitmap;
        float width = ((float) getWidth()) * 0.75f;
        float height = ((float) getHeight()) * 0.75f;
        float height2 = width / height > ((float) bitmap.getWidth()) / ((float) bitmap.getHeight()) ? height / ((float) bitmap.getHeight()) : width / ((float) bitmap.getWidth());
        this.mExtraBitmapMatrix = new Matrix();
        this.mExtraBitmapMatrix.postScale(height2, height2);
        this.mExtraBitmapMatrix.postTranslate((((float) getWidth()) - width) / 2.0f, (((float) getHeight()) - height) / 2.0f);
        this.mExtraBitmapPaint = new Paint();
        this.mExtraBitmapPaint.setAntiAlias(true);
        this.mExtraBitmapPaint.setFilterBitmap(true);
    }

    public void addSegmentNow() {
        this.cameraSnapAnimateDrawable.addSegmentNow();
    }

    public void directFinishRecord() {
        this.cameraSnapAnimateDrawable.directFinishRecord();
    }

    public boolean hasSegments() {
        return this.cameraSnapAnimateDrawable.hasSegments();
    }

    public void hideCirclePaintItem() {
        this.cameraSnapAnimateDrawable.hideCirclePaintItem();
        invalidate();
    }

    public void hidePaintCenterVVItem() {
        this.cameraSnapAnimateDrawable.hidePaintCenterVVItem();
    }

    public void hideRoundPaintItem() {
        this.cameraSnapAnimateDrawable.hideRoundPaintItem();
        invalidate();
    }

    public boolean inRegion(int i, int i2) {
        if ("hercules".equals(Build.DEVICE)) {
            i2 -= 96;
        }
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        return rect.contains(i, i2);
    }

    public void intoPattern(PaintConditionReferred paintConditionReferred) {
        CameraSnapAnimateDrawable cameraSnapAnimateDrawable2 = this.cameraSnapAnimateDrawable;
        if (cameraSnapAnimateDrawable2 != null) {
            cameraSnapAnimateDrawable2.intoPattern(paintConditionReferred);
            this.cameraSnapAnimateDrawable.startModeChangeAnimation();
        }
    }

    public void invalidateDrawable(Drawable drawable) {
        invalidate();
    }

    public boolean isBottomVisible() {
        return this.cameraSnapAnimateDrawable.isBottomVisible();
    }

    public boolean isFullScreen() {
        return this.cameraSnapAnimateDrawable.isFullScreen();
    }

    public boolean isSnapEnableClick() {
        return this.mEnableSnapClick;
    }

    public void longExposePrepare(BottomAnimationConfig bottomAnimationConfig) {
        this.cameraSnapAnimateDrawable.prepareRecording(bottomAnimationConfig);
    }

    public void longExposeStart(BottomAnimationConfig bottomAnimationConfig) {
        this.cameraSnapAnimateDrawable.startRecord(bottomAnimationConfig);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        CameraSnapAnimateDrawable cameraSnapAnimateDrawable2 = this.cameraSnapAnimateDrawable;
        if (cameraSnapAnimateDrawable2 != null) {
            cameraSnapAnimateDrawable2.cancelAnimation();
            this.cameraSnapAnimateDrawable.setCallback(null);
        }
        recycleBitmap();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        CameraSnapAnimateDrawable cameraSnapAnimateDrawable2 = this.cameraSnapAnimateDrawable;
        if (cameraSnapAnimateDrawable2 != null) {
            cameraSnapAnimateDrawable2.draw(canvas);
        }
        Bitmap bitmap = this.mExtraBitmap;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, this.mExtraBitmapMatrix, this.mExtraBitmapPaint);
        }
    }

    public void onForceVideoStateChange(PaintConditionReferred paintConditionReferred) {
        PaintConditionReferred paintConditionReferred2 = this.mCurrentCondition;
        if (paintConditionReferred.forceVideoPattern != (paintConditionReferred2 != null ? paintConditionReferred2.forceVideoPattern : false)) {
            this.mCurrentCondition = paintConditionReferred;
            this.cameraSnapAnimateDrawable.intoPattern(paintConditionReferred);
            this.cameraSnapAnimateDrawable.startModeChangeAnimation();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getMode(i) != 1073741824) {
            super.onMeasure(i, i2);
            return;
        }
        this.mWidth = View.MeasureSpec.getSize(i);
        this.mHeight = View.MeasureSpec.getSize(i2);
        setMeasuredDimension(this.mWidth, this.mHeight);
        CameraSnapAnimateDrawable cameraSnapAnimateDrawable2 = this.cameraSnapAnimateDrawable;
        if (cameraSnapAnimateDrawable2 != null) {
            cameraSnapAnimateDrawable2.setWidthHeight((float) this.mWidth, (float) this.mHeight);
        }
    }

    public void onTimeOut() {
        if (this.cameraSnapAnimateDrawable != null) {
            showCirclePaintItem();
            showRoundPaintItem();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0051, code lost:
        if (r2 != 6) goto L_0x0225;
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        PaintConditionReferred paintConditionReferred;
        PaintConditionReferred paintConditionReferred2;
        if (!isSnapEnableClick()) {
            String str = TAG;
            Log.d(str, "this view is disabled. action=" + motionEvent.getAction());
            return super.onTouchEvent(motionEvent);
        }
        boolean inRegion = inRegion((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action != 2) {
                    if (action != 3) {
                    }
                } else if (inRegion || this.mHandler.hasMessages(3) || this.mHasCancelByOutside) {
                    return false;
                }
            }
            this.mHandler.removeCallbacksAndMessages(null);
            this.mPressUpTime = System.currentTimeMillis();
            if (this.mPressUpTime - this.mPressDownTime < ((long) this.mLongPressTime)) {
                if (inRegion) {
                    Log.d(TAG, "snap click action_up");
                    this.mHandler.sendEmptyMessage(1);
                } else {
                    Log.d(TAG, "out of shutter button when you touch up");
                }
            }
            if (this.mHasCancelByOutside) {
                return true;
            }
            this.mPressUpTime = System.currentTimeMillis();
            long j = this.mPressUpTime - this.mPressDownTime;
            String str2 = TAG;
            Log.d(str2, "timeDiffer = " + j);
            if (j >= ((long) this.mLongPressTime)) {
                if (inRegion) {
                    this.mHandler.sendEmptyMessage(4);
                } else {
                    this.mHandler.sendEmptyMessage(3);
                }
            }
            long j2 = j > 120 ? 0 : 120 - j;
            int i = this.mCurrentMode;
            if (!(i == 161 || i == 162)) {
                if (i == 166) {
                    this.cameraSnapAnimateDrawable.startScaleUpAnimation(j2, null);
                } else if (i != 169) {
                    if (i == 176) {
                        this.cameraSnapAnimateDrawable.startScaleUpAnimation(j2, null);
                    } else if (i != 187) {
                        if (i != 204) {
                            if (!(i == 179 || i == 180)) {
                                switch (i) {
                                    case 172:
                                    case 174:
                                        break;
                                    case 173:
                                        if (DataRepository.dataItemGlobal().isOnSuperNightAlgoUpAndQuickShot()) {
                                            ViberatorContext.getInstance(getContext().getApplicationContext()).performSnapClick();
                                            this.cameraSnapAnimateDrawable.startScaleUpAnimation(j2, null);
                                            break;
                                        }
                                        break;
                                    default:
                                        switch (i) {
                                            case 183:
                                            case 185:
                                                break;
                                            case 184:
                                                PaintConditionReferred paintConditionReferred3 = this.mCurrentCondition;
                                                if (paintConditionReferred3 != null && !paintConditionReferred3.forceVideoPattern) {
                                                    this.cameraSnapAnimateDrawable.startScaleUpAnimation(j2, null);
                                                    break;
                                                }
                                            default:
                                                ViberatorContext.getInstance(getContext().getApplicationContext()).performSnapClick();
                                                this.cameraSnapAnimateDrawable.startScaleUpAnimation(j2, null);
                                                break;
                                        }
                                        break;
                                }
                            }
                        } else if (DataRepository.dataItemRunning().getComponentRunningDualVideo().ismShouldDraw6Patch() && (paintConditionReferred2 = this.mCurrentCondition) != null && !paintConditionReferred2.forceVideoPattern) {
                            this.cameraSnapAnimateDrawable.startScaleUpAnimation(j2, null);
                        }
                    }
                }
            }
        } else {
            int i2 = this.mCurrentMode;
            if (!(i2 == 161 || i2 == 162)) {
                if (i2 != 166) {
                    if (i2 != 169) {
                        if (i2 != 176) {
                            if (i2 != 187) {
                                if (i2 != 204) {
                                    if (!(i2 == 179 || i2 == 180)) {
                                        switch (i2) {
                                            case 172:
                                            case 174:
                                                break;
                                            case 173:
                                                if (DataRepository.dataItemGlobal().isOnSuperNightAlgoUpAndQuickShot() && !onTouchDownDefaultHandler(this.mCurrentMode)) {
                                                    return false;
                                                }
                                            default:
                                                switch (i2) {
                                                    case 183:
                                                    case 185:
                                                        break;
                                                    case 184:
                                                        PaintConditionReferred paintConditionReferred4 = this.mCurrentCondition;
                                                        if (paintConditionReferred4 != null && !paintConditionReferred4.forceVideoPattern) {
                                                            this.cameraSnapAnimateDrawable.startScaleDownAnimation(i2);
                                                            break;
                                                        }
                                                    default:
                                                        if (DataRepository.dataItemLive().getTimerBurstController().isInTimerBurstShotting()) {
                                                            this.cameraSnapAnimateDrawable.startScaleDownAnimation(this.mCurrentMode);
                                                            break;
                                                        } else if (!onTouchDownDefaultHandler(this.mCurrentMode)) {
                                                            return false;
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                    }
                                } else if (DataRepository.dataItemRunning().getComponentRunningDualVideo().ismShouldDraw6Patch() && (paintConditionReferred = this.mCurrentCondition) != null && !paintConditionReferred.forceVideoPattern) {
                                    this.cameraSnapAnimateDrawable.startScaleDownAnimation(this.mCurrentMode);
                                }
                            }
                        }
                    }
                }
                this.cameraSnapAnimateDrawable.startScaleDownAnimation(this.mCurrentMode);
            }
            Log.d(TAG, "snap click action_down");
            this.mMissTaken = false;
            this.mSnapListener.onSnapPrepare();
            this.mPressDownTime = System.currentTimeMillis();
            long j3 = this.mPressUpTime;
            if (j3 > 0) {
                this.mSnapListener.onTrackSnapTaken(this.mPressDownTime - j3);
            }
            this.mHandler.sendEmptyMessageDelayed(2, (long) this.mLongPressTime);
            this.mHasCancelByOutside = false;
        }
        return true;
    }

    public void pauseRecording() {
        this.cameraSnapAnimateDrawable.pauseRecording();
    }

    public boolean performClick() {
        if (!Util.isAccessible()) {
            return super.performClick();
        }
        super.performClick();
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler.sendEmptyMessage(1);
        return true;
    }

    public void prepareRecording(BottomAnimationConfig bottomAnimationConfig) {
        this.cameraSnapAnimateDrawable.prepareRecording(bottomAnimationConfig);
    }

    public void removeLastSegment() {
        this.cameraSnapAnimateDrawable.removeLastSegment();
    }

    public void resumeRecording() {
        this.cameraSnapAnimateDrawable.resumeRecording();
    }

    public void setDurationText(String str) {
        this.cameraSnapAnimateDrawable.setDurationText(str);
    }

    public void setParameters(PaintConditionReferred paintConditionReferred) {
        this.mCurrentCondition = paintConditionReferred;
        this.mPressUpTime = 0;
        this.mCurrentMode = paintConditionReferred.targetMode;
        CameraSnapAnimateDrawable cameraSnapAnimateDrawable2 = this.cameraSnapAnimateDrawable;
        if (cameraSnapAnimateDrawable2 == null) {
            this.cameraSnapAnimateDrawable = new CameraSnapAnimateDrawable(getContext());
            this.cameraSnapAnimateDrawable.setCallback(this);
            this.cameraSnapAnimateDrawable.intoPatternFromParameters(paintConditionReferred);
        } else {
            cameraSnapAnimateDrawable2.resetRecordingState();
            if (paintConditionReferred.needSnapButtonAnimation) {
                this.cameraSnapAnimateDrawable.intoPattern(paintConditionReferred);
                this.cameraSnapAnimateDrawable.startModeChangeAnimation();
            } else {
                this.cameraSnapAnimateDrawable.intoPatternFromParameters(paintConditionReferred);
                invalidate();
            }
        }
        this.mLongPressTime = 550;
    }

    public void setSnapClickEnable(boolean z) {
        String str = TAG;
        Log.d(str, "setClickEnable: " + z);
        this.mEnableSnapClick = z;
        if (!z) {
            this.mHandler.removeCallbacksAndMessages(null);
        }
    }

    public void setSnapListener(SnapListener snapListener) {
        this.mSnapListener = snapListener;
    }

    public void setStopButtonEnable(boolean z, boolean z2) {
        this.cameraSnapAnimateDrawable.setStopButtonEnable(z, z2);
    }

    public void showCirclePaintItem() {
        this.cameraSnapAnimateDrawable.showCirclePaintItem();
        invalidate();
    }

    public void showPaintCenterVVItem() {
        this.cameraSnapAnimateDrawable.showPaintCenterVVItem();
    }

    public void showRoundPaintItem() {
        this.cameraSnapAnimateDrawable.showRoundPaintItem();
        invalidate();
    }

    public void startRing() {
        setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.v6_ic_audio_shutter_button_normal));
        this.cameraSnapAnimateDrawable.startRingAnimation();
    }

    public void startSpeech() {
        this.cameraSnapAnimateDrawable.showBitmap(getContext(), this.cameraSnapAnimateDrawable.isFullScreen() ? R.drawable.ic_speech_shutter_button_full : R.drawable.ic_speech_shutter_button_half);
    }

    public void stopRing() {
        recycleBitmap();
        this.cameraSnapAnimateDrawable.stopRingAnimation();
    }

    public void stopSpeech() {
        CameraSnapAnimateDrawable cameraSnapAnimateDrawable2 = this.cameraSnapAnimateDrawable;
        if (cameraSnapAnimateDrawable2 != null) {
            cameraSnapAnimateDrawable2.clearBitmap();
        }
    }

    public void triggerAnimation(BottomAnimationConfig bottomAnimationConfig) {
        CameraSnapAnimateDrawable cameraSnapAnimateDrawable2;
        switch (this.mCurrentMode) {
            case 161:
            case 163:
            case 165:
            case 167:
            case 169:
            case 172:
            case 173:
            case 174:
            case 177:
            case 179:
            case 183:
            case 184:
            case 185:
            case 187:
                CameraSnapAnimateDrawable cameraSnapAnimateDrawable3 = this.cameraSnapAnimateDrawable;
                if (cameraSnapAnimateDrawable3 != null) {
                    cameraSnapAnimateDrawable3.startRecordAnimation(bottomAnimationConfig);
                    return;
                }
                return;
            case 162:
            case 166:
            case 176:
            case 180:
            case 204:
                if ((bottomAnimationConfig.mIsVideoBokeh || !bottomAnimationConfig.mIsStart) && (cameraSnapAnimateDrawable2 = this.cameraSnapAnimateDrawable) != null) {
                    cameraSnapAnimateDrawable2.startRecordAnimation(bottomAnimationConfig);
                    return;
                }
                return;
            default:
                return;
        }
    }
}
