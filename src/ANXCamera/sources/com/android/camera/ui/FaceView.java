package com.android.camera.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import b.c.a.c;
import com.android.camera.ActivityBase;
import com.android.camera.CameraScreenNail;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.watermark.WaterMarkData;
import com.android.camera2.CameraHardwareFace;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import miui.view.animation.CubicEaseOutInterpolator;

public class FaceView extends FrameView {
    private static final int AUTO_HIDE_TIME_DELAY = 2000;
    private static final int GENDER_FEMALE_RECT_COLOR = -1152383;
    private static final int GENDER_MALE_RECT_COLOR = -9455628;
    private static final int LATEST_FACE_NUM = 6;
    private static final int MAGIC_MIRROR_RECT_COLOR = -18377;
    private static final int MAX_FACE_MOVE_DISTANCE = 120;
    private static final int MAX_FACE_WIDTH_DISTANCE = 90;
    public static final float MAX_GENDER_FEMALE = 0.4f;
    public static final float MIN_ANALYZE_PROB = 0.5f;
    private static final int MIN_FACE_WIDTH = 670;
    public static final float MIN_GENDER_MALE = 0.6f;
    private static final int MSG_AUTOMATIC_HIDE_FACE = 1;
    private static final int MSG_CANCEL_FACE_HIDE = 2;
    private static final int MSG_SET_FACE_VISIBLE = 3;
    private static final int SHOW_TYPE_GENDER_AGE = 1;
    private static final int SHOW_TYPE_NORMAL = 0;
    private static final int SHOW_TYPE_NULL = -1;
    private static final int SHOW_TYPE_SCORE = 4;
    private static final int SHOW_TYPE_SCORE_WINNER = 2;
    private static final int STATE_FORCE_HIDE = 4;
    private static final int STATE_HIDE = 2;
    private static final int STATE_NORMAL = 1;
    private static final int STATE_WAIT_FOR_HIDE = 3;
    private static final String TAG = "FaceView";
    private Configuration configuration;
    private Rect mActiveArraySize;
    private int mAgeFemaleHonPadding;
    private int mAgeMaleHonPadding;
    private int mAgeVerPadding;
    private Drawable mBeautyScoreIc;
    private Matrix mCamera2TranslateMatrix = new Matrix();
    private CameraScreenNail mCameraScreenNail;
    private Matrix mCanvasMatrix = new Matrix();
    private int mCorrection;
    private List<WaterMarkData> mCurrentWaterMarkDataInfos;
    private CameraHardwareFace[] mDrawingFaces;
    private Paint mEffectRectPaint;
    private String mFaceAgeFormat;
    private Paint mFaceInfoNumberPaint;
    private Paint mFaceInfoTextPaint;
    private int mFacePopupBottom;
    private Animator mFaceRectHideAnimator;
    private String mFaceScoreInfoFormat;
    private CameraHardwareFace[] mFaces;
    private Drawable mFemaleAgeInfoPop;
    private int mGap;
    private Handler mHandler = new Handler() {
        /* class com.android.camera.ui.FaceView.AnonymousClass1 */

        public void handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                if (i == 2) {
                    FaceView.this.cancelHideAnimator();
                } else if (i == 3) {
                    FaceView.this.setFaceRectVisible(0, 0);
                }
            } else if (FaceView.this.mRectState == 3) {
                FaceView.this.mRectState = 2;
                FaceView.this.setFaceRectVisible(8, 600);
            }
        }
    };
    private boolean mIsCameraFaceDetectionAutoHidden;
    private boolean mIsUpdateFaceInfos = true;
    private int mLatestFaceIndex = -1;
    private CameraHardwareFace[] mLatestFaces = new CameraHardwareFace[6];
    private boolean mLightingOn;
    private Drawable mMagicMirrorInfoPop;
    private Drawable mMaleAgeInfoPop;
    private Matrix mMatrix = new Matrix();
    private boolean mMirror;
    private Paint mNormalRectPaint;
    private int mOrientation;
    private int mPopBottomMargin;
    private RectF mRect = new RectF();
    private int mRectState = 1;
    private int mScoreHonPadding;
    private int mScoreVerPadding;
    private Drawable mSexFemaleIc;
    private Drawable mSexMaleIc;
    private boolean mShowGenderAndAge;
    private boolean mShowMagicMirror;
    private boolean mSkipDraw;
    private Pattern mSplitFaceInfoPattern;
    private List<WaterMarkData> mWaterInfos;
    private int mWinnerIndex = -1;
    private float mZoomValue;

    @Retention(RetentionPolicy.SOURCE)
    @interface FaceViewRectState {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface PopType {
    }

    public FaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (c.isMTKPlatform()) {
            setLayerType(2, null);
        }
        this.mEffectRectPaint = new Paint();
        this.mEffectRectPaint.setAntiAlias(true);
        this.mEffectRectPaint.setStrokeWidth((float) getResources().getDimensionPixelSize(R.dimen.face_rect_width));
        this.mEffectRectPaint.setStyle(Paint.Style.STROKE);
        this.mNormalRectPaint = new Paint();
        this.mNormalRectPaint.setAntiAlias(true);
        this.mNormalRectPaint.setColor(-1);
        this.mNormalRectPaint.setStyle(Paint.Style.STROKE);
        this.mNormalRectPaint.setStrokeWidth((float) Util.dpToPixel(1.0f));
        this.mCameraScreenNail = ((ActivityBase) context).getCameraScreenNail();
    }

    private String calcFacePos(Rect rect, float f, float f2) {
        int i = rect.left;
        int i2 = 2;
        int i3 = f < ((float) i) ? 1 : (f < ((float) i) || f > ((float) rect.right)) ? f > ((float) rect.right) ? 3 : 0 : 2;
        int i4 = rect.top;
        if (f2 < ((float) i4)) {
            i2 = 1;
        } else if (f2 <= ((float) i4) || f2 > ((float) rect.bottom)) {
            i2 = f2 > ((float) rect.bottom) ? 3 : 0;
        }
        return i3 + "_" + i2;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void cancelHideAnimator() {
        Animator animator = this.mFaceRectHideAnimator;
        if (animator != null && animator.isRunning()) {
            this.mFaceRectHideAnimator.cancel();
        }
    }

    private void clearAttemptHideFaceRect() {
        this.mRectState = 1;
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessage(2);
    }

    private int determineWatermarkType() {
        if (CameraSettings.isMagicMirrorOn()) {
            return 1;
        }
        return CameraSettings.showGenderAge() ? 2 : 0;
    }

    private void drawFaceInfoText(Canvas canvas, String str, int i, int i2) {
        float f;
        Matcher matcher = this.mSplitFaceInfoPattern.matcher(str);
        while (matcher.find()) {
            String group = matcher.group();
            if (group.matches("\\d+\\.?\\d*")) {
                f = this.mFaceInfoNumberPaint.measureText(group);
                canvas.drawText(group, ((float) i) + (f / 2.0f), (float) i2, this.mFaceInfoNumberPaint);
            } else {
                f = this.mFaceInfoTextPaint.measureText(group);
                canvas.drawText(group, ((float) i) + (f / 2.0f), (float) i2, this.mFaceInfoTextPaint);
            }
            i = (int) (((float) i) + f);
        }
    }

    private void drawFacePopInfo(Canvas canvas, RectF rectF, Drawable drawable, Drawable drawable2, String str, int i, int i2, int i3, int i4) {
        Matcher matcher = this.mSplitFaceInfoPattern.matcher(str);
        float f = 0.0f;
        while (matcher.find()) {
            String group = matcher.group();
            f += group.matches("\\d+\\.?\\d*") ? this.mFaceInfoNumberPaint.measureText(group) : this.mFaceInfoTextPaint.measureText(group);
        }
        float f2 = (float) i2;
        int intrinsicWidth = ((int) ((((float) ((drawable.getIntrinsicWidth() + i) + this.mGap)) + f) + ((float) i))) / 2;
        Rect rect = new Rect(((int) rectF.centerX()) - intrinsicWidth, ((((int) rectF.top) - ((int) ((3.6f * f2) + ((float) drawable.getIntrinsicHeight())))) - i4) - i3, ((int) rectF.centerX()) + intrinsicWidth, ((int) rectF.top) - i4);
        if (drawable2 != null) {
            drawable2.setBounds(rect);
            drawable2.draw(canvas);
        }
        Rect rect2 = new Rect();
        int i5 = rect.left;
        float f3 = f2 * 1.8f;
        rect2.set(i5 + i, (int) ((((float) rect.top) + f3) - ((float) this.mCorrection)), i5 + i + drawable.getIntrinsicWidth(), (int) (((((float) rect.top) + f3) - ((float) this.mCorrection)) + ((float) drawable.getIntrinsicHeight())));
        drawable.setBounds(rect2);
        drawable.draw(canvas);
        if (f != 0.0f) {
            Paint.FontMetricsInt fontMetricsInt = this.mFaceInfoTextPaint.getFontMetricsInt();
            drawFaceInfoText(canvas, str, rect2.right + this.mGap, (((rect2.bottom + rect2.top) - fontMetricsInt.bottom) - fontMetricsInt.top) / 2);
        }
    }

    private void drawFaceRect(Canvas canvas, RectF rectF, int i, CameraHardwareFace cameraHardwareFace) {
        if (i == -1) {
            return;
        }
        if (i != 0) {
            boolean z = true;
            if (i != 1) {
                if ((i == 2 || i == 4) && cameraHardwareFace.beautyscore > 0.0f) {
                    this.mEffectRectPaint.setColor(MAGIC_MIRROR_RECT_COLOR);
                    canvas.drawRoundRect(rectF, rectF.width() * 0.015f, rectF.height() * 0.015f, this.mEffectRectPaint);
                }
            } else if (isValidAGInfo(cameraHardwareFace)) {
                if (cameraHardwareFace.gender >= 0.4f) {
                    z = false;
                }
                this.mEffectRectPaint.setColor(z ? GENDER_FEMALE_RECT_COLOR : GENDER_MALE_RECT_COLOR);
                canvas.drawRoundRect(rectF, rectF.width() * 0.015f, rectF.height() * 0.015f, this.mEffectRectPaint);
            }
        } else {
            canvas.drawRoundRect(rectF, rectF.width() * 0.015f, rectF.height() * 0.015f, this.mNormalRectPaint);
        }
    }

    private String getAgeInfo(CameraHardwareFace cameraHardwareFace) {
        int i = (int) cameraHardwareFace.ageMale;
        if (cameraHardwareFace.gender < 0.4f) {
            i = (int) cameraHardwareFace.ageFemale;
        }
        return String.format(this.configuration.locale, this.mFaceAgeFormat, Integer.valueOf(i));
    }

    private void getFacePos(CameraHardwareFace cameraHardwareFace, int i) {
        String str;
        ReferenceLineDrawer referenceLineDrawer = (ReferenceLineDrawer) ((ActivityBase) getContext()).findViewById(R.id.v6_reference_grid);
        Rect displayRect = Util.getDisplayRect();
        int width = referenceLineDrawer.getWidth() / 3;
        int height = referenceLineDrawer.getHeight() / 3;
        transToViewRect(cameraHardwareFace.rect, this.mRect);
        Rect rect = new Rect();
        int i2 = this.mOrientation;
        if (i2 == 0) {
            RectF rectF = this.mRect;
            rect.set(width, height, width * 2, height * 2);
            str = calcFacePos(rect, (rectF.left + rectF.right) / 2.0f, (rectF.top + rectF.bottom) / 2.0f);
        } else if (i2 == 90) {
            int i3 = Util.sWindowHeight;
            RectF rectF2 = this.mRect;
            int i4 = displayRect.top;
            rect.set(i3 - ((height * 2) + i4), width, i3 - (i4 + height), width * 2);
            str = calcFacePos(rect, ((float) i3) + ((rectF2.left + rectF2.right) / 2.0f), (rectF2.top + rectF2.bottom) / 2.0f);
        } else if (i2 == 180) {
            RectF rectF3 = this.mRect;
            float f = ((float) Util.sWindowWidth) + ((rectF3.left + rectF3.right) / 2.0f);
            int i5 = Util.sWindowHeight;
            int i6 = displayRect.top;
            rect.set(width, i5 - ((height * 2) + i6), width * 2, i5 - (i6 + height));
            str = calcFacePos(rect, f, ((float) i5) + ((rectF3.top + rectF3.bottom) / 2.0f));
        } else if (i2 == 270) {
            RectF rectF4 = this.mRect;
            rect.set(height, width, height * 2, width * 2);
            str = calcFacePos(rect, (rectF4.left + rectF4.right) / 2.0f, ((float) Util.sWindowWidth) + ((rectF4.top + rectF4.bottom) / 2.0f));
        } else {
            str = "";
        }
        setContentDescription(i, str);
    }

    private int getPopShowType(CameraHardwareFace[] cameraHardwareFaceArr) {
        int i;
        if (cameraHardwareFaceArr == null || cameraHardwareFaceArr.length <= 0) {
            return 0;
        }
        if (!this.mShowMagicMirror) {
            return this.mShowGenderAndAge ? 1 : 0;
        }
        this.mWinnerIndex = -1;
        int i2 = 0;
        for (int i3 = 0; i3 < this.mFaces.length; i3++) {
            float f = cameraHardwareFaceArr[i3].beautyscore;
            if (f > 0.0f) {
                i2++;
                int i4 = this.mWinnerIndex;
                if (i4 == -1 || f > cameraHardwareFaceArr[i4].beautyscore) {
                    this.mWinnerIndex = i3;
                }
            }
        }
        if (i2 > 1) {
            i = 2;
        } else if (i2 <= 0) {
            return -1;
        } else {
            i = 4;
        }
        return i;
    }

    private String getScoreInfo(CameraHardwareFace cameraHardwareFace) {
        return String.format(this.configuration.locale, this.mFaceScoreInfoFormat, Float.valueOf(cameraHardwareFace.beautyscore / 10.0f));
    }

    private void initFaceInfoStyle() {
        if (this.mFaceInfoTextPaint == null) {
            this.mSplitFaceInfoPattern = Pattern.compile("(\\D+)|(\\d+\\.?\\d*)");
            Resources resources = getResources();
            this.configuration = resources.getConfiguration();
            this.mFaceAgeFormat = resources.getString(R.string.face_age_info);
            this.mFaceScoreInfoFormat = resources.getString(R.string.face_score_info);
            this.mMaleAgeInfoPop = resources.getDrawable(R.drawable.male_age_info_pop);
            this.mFemaleAgeInfoPop = resources.getDrawable(R.drawable.female_age_info_pop);
            this.mSexMaleIc = resources.getDrawable(R.drawable.ic_sex_male);
            this.mSexFemaleIc = resources.getDrawable(R.drawable.ic_sex_female);
            this.mFaceInfoTextPaint = new Paint();
            this.mFaceInfoTextPaint.setAntiAlias(true);
            this.mFaceInfoTextPaint.setColor(-1);
            float dimension = resources.getDimension(R.dimen.face_info_magic_textSize);
            this.mFaceInfoTextPaint.setTextSize(dimension);
            this.mFaceInfoTextPaint.setTextAlign(Paint.Align.CENTER);
            this.mFaceInfoTextPaint.setFakeBoldText(true);
            this.mFaceInfoNumberPaint = new Paint(this.mFaceInfoTextPaint);
            if (this.configuration.locale.equals(Locale.SIMPLIFIED_CHINESE) || this.configuration.locale.equals(Locale.TRADITIONAL_CHINESE)) {
                this.mFaceInfoNumberPaint.setFakeBoldText(false);
                this.mFaceInfoNumberPaint.setTextSize(dimension * 1.16f);
            }
            this.mMagicMirrorInfoPop = resources.getDrawable(R.drawable.magic_mirror_info_pop);
            this.mBeautyScoreIc = resources.getDrawable(R.drawable.ic_beauty_score);
            this.mAgeVerPadding = resources.getDimensionPixelSize(R.dimen.face_info_ver_padding);
            this.mGap = resources.getDimensionPixelSize(R.dimen.face_info_text_left_dis);
            this.mCorrection = resources.getDimensionPixelOffset(R.dimen.face_info_correction);
            this.mPopBottomMargin = resources.getDimensionPixelSize(R.dimen.face_pop_bottom_margin);
            this.mScoreHonPadding = resources.getDimensionPixelSize(R.dimen.face_info_score_hon_padding);
            this.mScoreVerPadding = resources.getDimensionPixelSize(R.dimen.face_info_score_ver_padding);
            this.mAgeMaleHonPadding = resources.getDimensionPixelSize(R.dimen.face_info_male_hon_padding);
            this.mAgeFemaleHonPadding = resources.getDimensionPixelSize(R.dimen.face_info_female_hon_padding);
            this.mFacePopupBottom = (int) (((double) this.mMagicMirrorInfoPop.getIntrinsicHeight()) * 0.12d);
        }
    }

    private boolean isValidAGInfo(CameraHardwareFace cameraHardwareFace) {
        if (0.5f <= cameraHardwareFace.prob) {
            float f = cameraHardwareFace.gender;
            return f != 0.0f && (f <= 0.4f || 0.6f <= f);
        }
    }

    private void performMatrix(Matrix matrix, boolean z, int i, float f, float f2, int i2, int i3) {
        float f3 = 0.0f;
        if (z) {
            matrix.setScale(-1.0f, 1.0f);
            matrix.postRotate((float) (-i));
            if (i == 90 || i == 270) {
                matrix.postScale(f2, f);
                float f4 = i == 90 ? (float) (-i3) : 0.0f;
                if (i == 270) {
                    f3 = (float) i2;
                }
                matrix.postTranslate(f4, f3);
                return;
            }
            matrix.postScale(f, f2);
            float f5 = i == 180 ? (float) (-i2) : 0.0f;
            if (i == 180) {
                f3 = (float) i3;
            }
            matrix.postTranslate(f5, f3);
            return;
        }
        matrix.setRotate((float) i);
        if (i == 90 || i == 270) {
            matrix.postScale(f2, f);
            float f6 = i == 90 ? (float) i3 : 0.0f;
            if (i == 270) {
                f3 = (float) i2;
            }
            matrix.postTranslate(f6, f3);
            return;
        }
        matrix.postScale(f, f2);
        float f7 = i == 180 ? (float) i2 : 0.0f;
        if (i == 180) {
            f3 = (float) i3;
        }
        matrix.postTranslate(f7, f3);
    }

    private void prepareMatrix() {
        this.mCamera2TranslateMatrix.reset();
        this.mMatrix.reset();
        this.mCanvasMatrix.reset();
        Util.scaleCamera2Matrix(this.mCamera2TranslateMatrix, this.mActiveArraySize, this.mZoomValue);
        Util.prepareMatrix(this.mMatrix, this.mMirror, this.mCameraDisplayOrientation, this.mCameraScreenNail.getRenderWidth(), this.mCameraScreenNail.getRenderHeight(), getWidth() / 2, getHeight() / 2, this.mActiveArraySize.width(), this.mActiveArraySize.height());
        if (!this.mLightingOn) {
            this.mMatrix.postRotate((float) this.mOrientation);
            this.mCanvasMatrix.postRotate((float) this.mOrientation);
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0065, code lost:
        if (r8.equals("2_1") != false) goto L_0x0087;
     */
    private void setContentDescription(int i, String str) {
        if (i > 0) {
            char c2 = 1;
            announceForAccessibility(getResources().getQuantityString(R.plurals.accessibility_focus_face_detect, i, Integer.valueOf(i)));
            if (str != null && i == 1) {
                int hashCode = str.hashCode();
                switch (hashCode) {
                    case 50083:
                        if (str.equals("1_1")) {
                            c2 = 0;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 50084:
                        if (str.equals("1_2")) {
                            c2 = 3;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 50085:
                        if (str.equals("1_3")) {
                            c2 = 6;
                            break;
                        }
                        c2 = 65535;
                        break;
                    default:
                        switch (hashCode) {
                            case 51044:
                                break;
                            case 51045:
                                if (str.equals("2_2")) {
                                    c2 = 4;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 51046:
                                if (str.equals("2_3")) {
                                    c2 = 7;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            default:
                                switch (hashCode) {
                                    case 52005:
                                        if (str.equals("3_1")) {
                                            c2 = 2;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    case 52006:
                                        if (str.equals("3_2")) {
                                            c2 = 5;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    case 52007:
                                        if (str.equals("3_3")) {
                                            c2 = '\b';
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    default:
                                        c2 = 65535;
                                        break;
                                }
                        }
                }
                switch (c2) {
                    case 0:
                        announceForAccessibility(getResources().getString(R.string.accessibility_focus_face_pos_left_top));
                        return;
                    case 1:
                        announceForAccessibility(getResources().getString(R.string.accessibility_focus_face_pos_top));
                        return;
                    case 2:
                        announceForAccessibility(getResources().getString(R.string.accessibility_focus_face_pos_right_top));
                        return;
                    case 3:
                        announceForAccessibility(getResources().getString(R.string.accessibility_focus_face_pos_left));
                        return;
                    case 4:
                        announceForAccessibility(getResources().getString(R.string.accessibility_focus_face_pos_mid));
                        return;
                    case 5:
                        announceForAccessibility(getResources().getString(R.string.accessibility_focus_face_pos_right));
                        return;
                    case 6:
                        announceForAccessibility(getResources().getString(R.string.accessibility_focus_face_pos_left_bottom));
                        return;
                    case 7:
                        announceForAccessibility(getResources().getString(R.string.accessibility_focus_face_pos_bottom));
                        return;
                    case '\b':
                        announceForAccessibility(getResources().getString(R.string.accessibility_focus_face_pos_right_bottom));
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private void setCurrentFaceInfos(RectF rectF, String str, int i, int i2, int i3) {
        if (!this.mIsUpdateFaceInfos) {
            Log.d(TAG, "setCurrentFaceInfos@2: updateInfo=false");
            return;
        }
        if (this.mCurrentWaterMarkDataInfos == null) {
            this.mCurrentWaterMarkDataInfos = new ArrayList();
        }
        WaterMarkData waterMarkData = new WaterMarkData();
        waterMarkData.setFaceRectF(rectF);
        waterMarkData.setInfo(str);
        waterMarkData.setFaceViewWidth(i);
        waterMarkData.setFaceViewHeight(i2);
        waterMarkData.setOrientation(i3);
        waterMarkData.setWatermarkType(determineWatermarkType());
        List<WaterMarkData> list = this.mCurrentWaterMarkDataInfos;
        if (list != null) {
            list.add(waterMarkData);
        }
    }

    private void setCurrentFaceInfos(RectF rectF, boolean z, String str, int i, int i2, int i3) {
        if (!this.mIsUpdateFaceInfos) {
            Log.d(TAG, "setCurrentFaceInfos@1: updateInfo=false");
            return;
        }
        if (this.mCurrentWaterMarkDataInfos == null) {
            this.mCurrentWaterMarkDataInfos = new ArrayList();
        }
        WaterMarkData waterMarkData = new WaterMarkData();
        waterMarkData.setFaceRectF(rectF);
        waterMarkData.setFemale(z);
        waterMarkData.setInfo(str);
        waterMarkData.setFaceViewWidth(i);
        waterMarkData.setFaceViewHeight(i2);
        waterMarkData.setOrientation(i3);
        waterMarkData.setWatermarkType(determineWatermarkType());
        this.mCurrentWaterMarkDataInfos.add(waterMarkData);
    }

    private void setToVisible() {
        if (getVisibility() != 0) {
            setVisibility(0);
        }
    }

    private void showNormalFaceRectImmediately() {
        this.mRectState = 1;
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessage(3);
    }

    private void updateFaceInfos() {
        if (!this.mIsUpdateFaceInfos) {
            Log.d(TAG, "updateFaceInfos: false");
            return;
        }
        if (this.mWaterInfos == null) {
            this.mWaterInfos = new ArrayList();
        }
        List<WaterMarkData> list = this.mCurrentWaterMarkDataInfos;
        if (list != null && !list.isEmpty()) {
            if (!this.mWaterInfos.isEmpty()) {
                this.mWaterInfos.clear();
            }
            this.mWaterInfos.addAll(this.mCurrentWaterMarkDataInfos);
        }
        List<WaterMarkData> list2 = this.mCurrentWaterMarkDataInfos;
        if (list2 != null) {
            list2.clear();
        }
    }

    private void updateLatestFaces() {
        int i = this.mLatestFaceIndex;
        int i2 = 1;
        int i3 = i >= 5 ? 0 : i + 1;
        if (!faceExists()) {
            this.mLatestFaces[i3] = null;
        } else {
            CameraHardwareFace cameraHardwareFace = this.mFaces[0];
            while (true) {
                CameraHardwareFace[] cameraHardwareFaceArr = this.mFaces;
                if (i2 >= cameraHardwareFaceArr.length) {
                    break;
                }
                int i4 = cameraHardwareFaceArr[i2].rect.right - cameraHardwareFaceArr[i2].rect.left;
                Rect rect = cameraHardwareFace.rect;
                if (i4 > rect.right - rect.left) {
                    cameraHardwareFace = cameraHardwareFaceArr[i2];
                }
                i2++;
            }
            this.mLatestFaces[i3] = cameraHardwareFace;
        }
        this.mLatestFaceIndex = i3;
    }

    public void attemptHideFaceRect(long j) {
        this.mRectState = 3;
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessageDelayed(1, j);
    }

    @Override // com.android.camera.ui.FrameView, com.android.camera.ui.FocusIndicator
    public void clear() {
        this.mFaces = null;
        this.mRectState = 1;
        clearPreviousFaces();
        postInvalidate();
    }

    public void clearFaceFlags() {
        this.mShowGenderAndAge = false;
        this.mShowMagicMirror = false;
    }

    public void clearPreviousFaces() {
        this.mLatestFaceIndex = -1;
        int i = 0;
        while (true) {
            CameraHardwareFace[] cameraHardwareFaceArr = this.mLatestFaces;
            if (i < cameraHardwareFaceArr.length) {
                cameraHardwareFaceArr[i] = null;
                i++;
            } else {
                return;
            }
        }
    }

    public boolean faceExisted() {
        int i = this.mLatestFaceIndex;
        if (i < 0) {
            return false;
        }
        int i2 = i;
        for (int i3 = 0; i3 < 3; i3++) {
            if (i2 < 0) {
                i2 += this.mLatestFaces.length;
            }
            if (this.mLatestFaces[i2] != null) {
                return true;
            }
            i2--;
        }
        return false;
    }

    @Override // com.android.camera.ui.FrameView
    public boolean faceExists() {
        CameraHardwareFace[] cameraHardwareFaceArr = this.mFaces;
        return cameraHardwareFaceArr != null && cameraHardwareFaceArr.length > 0;
    }

    public void forceHideRect() {
        if (this.mRectState != 4) {
            this.mRectState = 4;
            this.mHandler.removeMessages(1);
            if (this.mNormalRectPaint.getAlpha() > 0) {
                setFaceRectVisible(8, 200);
            }
        }
    }

    public List<WaterMarkData> getFaceWaterMarkInfos() {
        this.mDrawingFaces = getFaces();
        CameraHardwareFace[] cameraHardwareFaceArr = this.mDrawingFaces;
        if (cameraHardwareFaceArr == null || cameraHardwareFaceArr.length == 0 || this.mCameraScreenNail == null || this.mPause) {
            return null;
        }
        int popShowType = getPopShowType(cameraHardwareFaceArr);
        int i = 0;
        while (true) {
            CameraHardwareFace[] cameraHardwareFaceArr2 = this.mDrawingFaces;
            if (i < cameraHardwareFaceArr2.length) {
                this.mRect.set(cameraHardwareFaceArr2[i].rect);
                transToViewRect(this.mDrawingFaces[i].rect, this.mRect);
                if (popShowType == 1 && isValidAGInfo(this.mDrawingFaces[i])) {
                    initFaceInfoStyle();
                    boolean z = this.mDrawingFaces[i].gender < 0.4f;
                    String ageInfo = getAgeInfo(this.mDrawingFaces[i]);
                    RectF rectF = this.mRect;
                    setCurrentFaceInfos(new RectF(rectF.left, rectF.top, rectF.right, rectF.bottom), z, ageInfo, getWidth(), getHeight(), this.mOrientation);
                }
                if ((popShowType == 2 || popShowType == 4) && this.mDrawingFaces[i].beautyscore > 0.0f) {
                    RectF rectF2 = this.mRect;
                    setCurrentFaceInfos(new RectF(rectF2.left, rectF2.top, rectF2.right, rectF2.bottom), getScoreInfo(this.mDrawingFaces[i]), getWidth(), getHeight(), this.mOrientation);
                }
                i++;
            } else {
                updateFaceInfos();
                return this.mWaterInfos;
            }
        }
    }

    public CameraHardwareFace[] getFaces() {
        return this.mFaces;
    }

    @Override // com.android.camera.ui.FrameView
    public RectF getFocusRect() {
        int i;
        RectF rectF = new RectF();
        CameraScreenNail cameraScreenNail = ((ActivityBase) getContext()).getCameraScreenNail();
        if (cameraScreenNail == null || (i = this.mLatestFaceIndex) < 0 || i >= 6) {
            return null;
        }
        this.mCamera2TranslateMatrix.reset();
        this.mMatrix.reset();
        Util.scaleCamera2Matrix(this.mCamera2TranslateMatrix, this.mActiveArraySize, this.mZoomValue);
        Util.prepareMatrix(this.mMatrix, this.mMirror, this.mCameraDisplayOrientation, cameraScreenNail.getRenderWidth(), cameraScreenNail.getRenderHeight(), getWidth() / 2, getHeight() / 2, this.mActiveArraySize.width(), this.mActiveArraySize.height());
        rectF.set(this.mLatestFaces[this.mLatestFaceIndex].rect);
        this.mMatrix.postRotate((float) this.mOrientation);
        this.mCamera2TranslateMatrix.mapRect(rectF);
        this.mMatrix.mapRect(rectF);
        return rectF;
    }

    public RectF[] getViewRects(CameraSize cameraSize) {
        int i;
        int i2;
        CameraHardwareFace[] cameraHardwareFaceArr = this.mFaces;
        if (cameraHardwareFaceArr == null || cameraHardwareFaceArr.length <= 0) {
            return null;
        }
        Matrix matrix = new Matrix();
        RectF[] rectFArr = new RectF[this.mFaces.length];
        int renderWidth = this.mCameraScreenNail.getRenderWidth();
        int renderHeight = this.mCameraScreenNail.getRenderHeight();
        int uiStyle = DataRepository.dataItemRunning().getUiStyle();
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        if (uiStyle == 0 && currentMode == 165) {
            i2 = cameraSize.getHeight();
            i = cameraSize.getHeight();
        } else {
            i2 = cameraSize.getWidth();
            i = cameraSize.getHeight();
        }
        if (this.mLightingOn && this.mMirror) {
            i2 *= 2;
            i *= 2;
        }
        performMatrix(matrix, this.mMirror, this.mOrientation, ((float) i) / ((float) renderWidth), ((float) i2) / ((float) renderHeight), i, i2);
        for (int i3 = 0; i3 < this.mFaces.length; i3++) {
            rectFArr[i3] = new RectF();
            rectFArr[i3].set(this.mFaces[i3].rect);
            this.mCamera2TranslateMatrix.mapRect(rectFArr[i3]);
            this.mMatrix.mapRect(rectFArr[i3]);
            matrix.mapRect(rectFArr[i3]);
        }
        return rectFArr;
    }

    @Override // com.android.camera.ui.FrameView
    public boolean isFaceStable() {
        CameraHardwareFace[] cameraHardwareFaceArr = this.mLatestFaces;
        boolean z = false;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (CameraHardwareFace cameraHardwareFace : cameraHardwareFaceArr) {
            if (cameraHardwareFace == null) {
                i++;
                if (i >= 3) {
                    return false;
                }
            } else {
                Rect rect = cameraHardwareFace.rect;
                int i6 = rect.right;
                int i7 = rect.left;
                i2 += i6 - i7;
                int i8 = rect.bottom;
                int i9 = rect.top;
                i3 += i8 - i9;
                i4 += i7;
                i5 += i9;
            }
        }
        int length = this.mLatestFaces.length - i;
        int i10 = i2 / length;
        int i11 = i3 / length;
        int i12 = i4 / length;
        int i13 = i5 / length;
        int i14 = i10 / 3;
        if (i14 <= 90) {
            i14 = 90;
        }
        CameraHardwareFace[] cameraHardwareFaceArr2 = this.mLatestFaces;
        for (CameraHardwareFace cameraHardwareFace2 : cameraHardwareFaceArr2) {
            if (cameraHardwareFace2 != null) {
                Rect rect2 = cameraHardwareFace2.rect;
                if (Math.abs((rect2.right - rect2.left) - i10) > i14 || Math.abs(cameraHardwareFace2.rect.left - i12) > 120 || Math.abs(cameraHardwareFace2.rect.top - i13) > 120) {
                    return false;
                }
            }
        }
        if (i10 > MIN_FACE_WIDTH || i11 > MIN_FACE_WIDTH) {
            z = true;
        }
        this.mIsBigEnoughRect = z;
        return true;
    }

    public boolean isGroupCapture() {
        CameraHardwareFace[] cameraHardwareFaceArr = this.mFaces;
        return cameraHardwareFaceArr != null && cameraHardwareFaceArr.length > 1;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mHandler.removeCallbacksAndMessages(null);
        cancelHideAnimator();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (!this.mSkipDraw) {
            this.mDrawingFaces = getFaces();
            CameraHardwareFace[] cameraHardwareFaceArr = this.mDrawingFaces;
            if (cameraHardwareFaceArr != null && cameraHardwareFaceArr.length != 0 && this.mCameraScreenNail != null && !this.mPause) {
                canvas.save();
                if (!this.mLightingOn) {
                    canvas.rotate((float) (-this.mOrientation));
                }
                int popShowType = getPopShowType(this.mDrawingFaces);
                int i = 0;
                while (true) {
                    CameraHardwareFace[] cameraHardwareFaceArr2 = this.mDrawingFaces;
                    if (i < cameraHardwareFaceArr2.length) {
                        this.mRect.set(cameraHardwareFaceArr2[i].rect);
                        transToViewRect(this.mDrawingFaces[i].rect, this.mRect);
                        drawFaceRect(canvas, this.mRect, popShowType, this.mDrawingFaces[i]);
                        boolean z = true;
                        if (popShowType != 1) {
                            if (popShowType != 2) {
                                if (popShowType == 4 && this.mDrawingFaces[i].beautyscore > 0.0f) {
                                    initFaceInfoStyle();
                                    drawFacePopInfo(canvas, this.mRect, this.mBeautyScoreIc, this.mMagicMirrorInfoPop, getScoreInfo(this.mDrawingFaces[i]), this.mAgeFemaleHonPadding, this.mAgeVerPadding, this.mFacePopupBottom, this.mPopBottomMargin);
                                }
                            } else if (this.mDrawingFaces[i].beautyscore > 0.0f) {
                                initFaceInfoStyle();
                                drawFacePopInfo(canvas, this.mRect, this.mBeautyScoreIc, this.mMagicMirrorInfoPop, getScoreInfo(this.mDrawingFaces[i]), this.mAgeFemaleHonPadding, this.mAgeVerPadding, this.mFacePopupBottom, this.mPopBottomMargin);
                            }
                        } else if (isValidAGInfo(this.mDrawingFaces[i])) {
                            initFaceInfoStyle();
                            if (this.mDrawingFaces[i].gender >= 0.4f) {
                                z = false;
                            }
                            drawFacePopInfo(canvas, this.mRect, z ? this.mSexFemaleIc : this.mSexMaleIc, z ? this.mFemaleAgeInfoPop : this.mMaleAgeInfoPop, getAgeInfo(this.mDrawingFaces[i]), z ? this.mAgeFemaleHonPadding : this.mAgeMaleHonPadding, this.mAgeVerPadding, this.mFacePopupBottom, this.mPopBottomMargin);
                        }
                        i++;
                    } else {
                        canvas.restore();
                        return;
                    }
                }
            }
        }
    }

    @Override // com.android.camera.ui.FrameView
    public void pause() {
        super.pause();
        clearPreviousFaces();
    }

    public void reShowFaceRect() {
        if (this.mRectState != 1) {
            showNormalFaceRectImmediately();
        }
    }

    @Override // com.android.camera.ui.FrameView
    public void resume() {
        super.resume();
        this.mIsCameraFaceDetectionAutoHidden = CameraSettings.isCameraFaceDetectionAutoHidden();
    }

    @Override // com.android.camera.ui.FrameView
    public void setCameraDisplayOrientation(int i) {
        this.mCameraDisplayOrientation = i;
        Log.v(TAG, "mCameraDisplayOrientation=" + i);
    }

    public void setFaceRectVisible(int i, int i2) {
        cancelHideAnimator();
        if (i2 == 0) {
            this.mNormalRectPaint.setAlpha(i == 0 ? 255 : 0);
            return;
        }
        this.mFaceRectHideAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mFaceRectHideAnimator.setInterpolator(new CubicEaseOutInterpolator() {
            /* class com.android.camera.ui.FaceView.AnonymousClass2 */

            @Override // miui.view.animation.CubicEaseOutInterpolator
            public float getInterpolation(float f) {
                float interpolation = super.getInterpolation(f);
                FaceView.this.mNormalRectPaint.setAlpha((int) ((1.0f - interpolation) * 255.0f));
                FaceView.this.invalidate();
                return interpolation;
            }
        });
        this.mFaceRectHideAnimator.setDuration((long) i2);
        this.mFaceRectHideAnimator.start();
    }

    public boolean setFaces(CameraHardwareFace[] cameraHardwareFaceArr, Rect rect, Rect rect2) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("Num of faces = ");
        sb.append(cameraHardwareFaceArr == null ? 0 : cameraHardwareFaceArr.length);
        Log.c(TAG, sb.toString());
        if (this.mPause) {
            return false;
        }
        int length = cameraHardwareFaceArr != null ? cameraHardwareFaceArr.length : 0;
        CameraHardwareFace[] cameraHardwareFaceArr2 = this.mFaces;
        int length2 = cameraHardwareFaceArr2 != null ? cameraHardwareFaceArr2.length : 0;
        boolean z = length != length2;
        if (length == 0 && length2 == 0) {
            return true;
        }
        this.mFaces = cameraHardwareFaceArr;
        this.mActiveArraySize = rect;
        this.mZoomValue = HybridZoomingSystem.toZoomRatio(rect, rect2);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("setFaces: activeArraySize=");
        sb2.append(rect);
        sb2.append(" cropRegion=");
        sb2.append(rect2);
        sb2.append(" mZoomValue=");
        sb2.append(this.mZoomValue);
        sb2.append(" face=");
        CameraHardwareFace[] cameraHardwareFaceArr3 = this.mFaces;
        if (cameraHardwareFaceArr3 == null || cameraHardwareFaceArr3.length == 0) {
            str = "null";
        } else {
            str = this.mFaces[0].rect.width() + "x" + this.mFaces[0].rect.height();
        }
        sb2.append(str);
        Log.d(TAG, sb2.toString());
        updateLatestFaces();
        prepareMatrix();
        postInvalidate();
        if (this.mRectState != 4 && !this.mShowGenderAndAge && !this.mShowMagicMirror) {
            if (z) {
                if (length > 0) {
                    showNormalFaceRectImmediately();
                }
                if (Util.isAccessible()) {
                    if (length == 1) {
                        getFacePos(cameraHardwareFaceArr[0], length);
                    } else {
                        setContentDescription(length, "");
                    }
                }
            }
            if (this.mIsCameraFaceDetectionAutoHidden) {
                if (isFaceStable()) {
                    int i = this.mRectState;
                    if (!(i == 2 || i == 3)) {
                        attemptHideFaceRect(2000);
                    }
                } else if (this.mRectState != 1) {
                    clearAttemptHideFaceRect();
                }
            }
        }
        return true;
    }

    public void setLightingOn(boolean z) {
        this.mLightingOn = z;
        Log.v(TAG, "mLightingOn=" + this.mLightingOn);
    }

    @Override // com.android.camera.ui.FrameView
    public void setMirror(boolean z) {
        this.mMirror = z;
        Log.v(TAG, "mMirror=" + z);
    }

    @Override // com.android.camera.ui.FrameView, com.android.camera.ui.Rotatable
    public void setOrientation(int i, boolean z) {
        this.mOrientation = i;
        if (!this.mPause && faceExists() && !this.mSkipDraw) {
            invalidate();
        }
    }

    @Override // com.android.camera.ui.FrameView
    public void setShowGenderAndAge(boolean z) {
        boolean z2 = this.mShowGenderAndAge && !z;
        this.mShowGenderAndAge = z;
        if (z2) {
            showNormalFaceRectImmediately();
        }
        if (z) {
            setShowMagicMirror(false);
        }
    }

    public void setShowMagicMirror(boolean z) {
        boolean z2 = this.mShowMagicMirror && !z;
        this.mShowMagicMirror = z;
        if (z2) {
            showNormalFaceRectImmediately();
        }
        if (z) {
            setShowGenderAndAge(false);
        }
    }

    public void setShutterStatus(boolean z) {
        if (this.mIsUpdateFaceInfos != z) {
            this.mIsUpdateFaceInfos = z;
            if (!z) {
                List<WaterMarkData> list = this.mCurrentWaterMarkDataInfos;
                if (list != null && !list.isEmpty()) {
                    this.mCurrentWaterMarkDataInfos.clear();
                }
                List<WaterMarkData> list2 = this.mWaterInfos;
                if (list2 != null && !list2.isEmpty()) {
                    this.mWaterInfos.clear();
                }
                Log.d(TAG, "setShutterStatus: updateInfo=" + this.mIsUpdateFaceInfos);
            }
        }
    }

    public void setSkipDraw(boolean z) {
        this.mSkipDraw = z;
    }

    @Override // com.android.camera.ui.FrameView, com.android.camera.ui.FocusIndicator
    public void showFail() {
        setToVisible();
        invalidate();
    }

    @Override // com.android.camera.ui.FrameView, com.android.camera.ui.FocusIndicator
    public void showStart() {
        setToVisible();
        invalidate();
    }

    @Override // com.android.camera.ui.FrameView, com.android.camera.ui.FocusIndicator
    public void showSuccess() {
        setToVisible();
        invalidate();
    }

    public void transToViewRect(Rect rect, RectF rectF) {
        rectF.set(rect);
        this.mCamera2TranslateMatrix.mapRect(rectF);
        this.mMatrix.mapRect(rectF);
    }
}
