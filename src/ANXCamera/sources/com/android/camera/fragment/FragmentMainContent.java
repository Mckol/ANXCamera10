package com.android.camera.fragment;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.camera2.params.MeteringRectangle;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Size;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.aiwatermark.DragListener;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.SlideInOnSubscribe;
import com.android.camera.animation.type.SlideOutOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.widget.helper.MimojiHelper2;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.fragment.mimoji.MimojiHelper;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.impl.component.MimojiStatusManager;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.ui.AfRegionsView;
import com.android.camera.ui.DocumentView;
import com.android.camera.ui.FaceView;
import com.android.camera.ui.FocusIndicator;
import com.android.camera.ui.FocusView;
import com.android.camera.ui.LightingView;
import com.android.camera.ui.ObjectView;
import com.android.camera.ui.V6EffectCropView;
import com.android.camera.ui.V6GestureRecognizer;
import com.android.camera.ui.V6PreviewFrame;
import com.android.camera.ui.V6PreviewPanel;
import com.android.camera.ui.ZoomView;
import com.android.camera.watermark.WaterMarkData;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CameraHardwareFace;
import com.android.camera2.autozoom.AutoZoomCaptureResult;
import com.android.camera2.autozoom.AutoZoomView;
import com.bumptech.glide.Glide;
import com.xiaomi.ocr.sdk.imgprocess.DocumentProcess;
import io.reactivex.Completable;
import java.util.List;
import java.util.Locale;
import miuix.view.animation.CubicEaseOutInterpolator;

public class FragmentMainContent extends BaseFragment implements ModeProtocol.MainContentProtocol, ModeProtocol.AutoZoomViewProtocol, ModeProtocol.HandleBackTrace, ZoomView.zoomValueChangeListener {
    public static final int FRAGMENT_INFO = 243;
    public static final int FRONT_CAMERA_ID = 1;
    private static final String TAG = "FragmentMainContent";
    private long lastConfirmTime;
    private int lastFaceResult;
    private int mActiveIndicator = 2;
    private AfRegionsView mAfRegionsView;
    private AutoZoomView mAutoZoomOverlay;
    private ImageView mCenterHintIcon;
    private TextView mCenterHintText;
    private ViewGroup mCoverParent;
    private int mCurrentMimojiFaceResult;
    private int mDisplayRectTopMargin;
    private DocumentView mDocumentView;
    private V6EffectCropView mEffectCropView;
    private FaceView mFaceView;
    private FocusView mFocusView;
    private Handler mHandler = new Handler();
    private ValueAnimator mHistogramAnimator;
    private boolean mIsHorizontal;
    private boolean mIsIntentAction;
    private boolean mIsMimojiCreateLowLight;
    private boolean mIsMimojiFaceDetectTip;
    private boolean mIsRecording;
    private boolean mIsShowMainLyingDirectHint;
    private int mLastCameraId = -1;
    private boolean mLastFaceSuccess;
    private int mLastTranslateY;
    private View mLeftCover;
    private LightingView mLightingView;
    private DragListener mListener = null;
    private TextView mLyingDirectHint;
    private int mMimojiDetectTipType;
    private int mMimojiFaceDetect;
    private boolean mMimojiLastFaceSuccess;
    private LightingView mMimojiLightingView;
    private View mMoreModeMaskView;
    private int mNormalCoverHeight;
    private ObjectView mObjectView;
    private ViewGroup mPreviewCenterHint;
    private V6PreviewFrame mPreviewFrame;
    private ViewGroup mPreviewPage;
    private V6PreviewPanel mPreviewPanel;
    private View mRightCover;
    private FrameLayout mWatermarkBackgroundLayout;
    private ViewStub mWatermarkBackgroundViewStub;
    private int mWatermarkDegree = -1;
    private FrameLayout mWatermarkExtendLayout;
    private ViewStub mWatermarkExtendViewStub;
    private FrameLayout mWatermarkLayout;
    private ViewStub mWatermarkViewStub;
    private ZoomView mZoomView;
    private ZoomView mZoomViewHorizontal;
    private RectF mergeRectF = new RectF();

    /* access modifiers changed from: package-private */
    /* renamed from: com.android.camera.fragment.FragmentMainContent$5  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$android$camera$fragment$FragmentMainContent$CoverState = new int[CoverState.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            $SwitchMap$com$android$camera$fragment$FragmentMainContent$CoverState[CoverState.NONE.ordinal()] = 1;
            $SwitchMap$com$android$camera$fragment$FragmentMainContent$CoverState[CoverState.TB.ordinal()] = 2;
            try {
                $SwitchMap$com$android$camera$fragment$FragmentMainContent$CoverState[CoverState.LR.ordinal()] = 3;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public enum CoverState {
        NONE,
        TB,
        LR,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    private void adjustViewHeight() {
        if (getContext() != null && this.mPreviewPanel != null) {
            ViewGroup viewGroup = this.mPreviewPage;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mPreviewPanel.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) this.mPreviewCenterHint.getLayoutParams();
            Rect previewRect = Util.getPreviewRect(getContext());
            if (marginLayoutParams2.height != previewRect.height() || previewRect.top != this.mDisplayRectTopMargin) {
                this.mDisplayRectTopMargin = previewRect.top;
                marginLayoutParams2.height = previewRect.height();
                marginLayoutParams2.topMargin = previewRect.top;
                this.mPreviewPanel.setLayoutParams(marginLayoutParams2);
                marginLayoutParams3.height = (previewRect.width() * 4) / 3;
                this.mPreviewCenterHint.setLayoutParams(marginLayoutParams3);
                marginLayoutParams.height = previewRect.height() + this.mDisplayRectTopMargin;
                viewGroup.setLayoutParams(marginLayoutParams);
                setDisplaySize(previewRect.width(), previewRect.height());
            }
        }
    }

    private FrameLayout.LayoutParams calculateLP(WatermarkItem watermarkItem, int i, FrameLayout.LayoutParams layoutParams) {
        if (needMoveWatermark(watermarkItem, i)) {
            ModeProtocol.WatermarkProtocol watermarkProtocol = (ModeProtocol.WatermarkProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(253);
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if (watermarkProtocol != null && watermarkProtocol.isWatermarkPanelShow()) {
                layoutParams.topMargin = (layoutParams.topMargin - getContext().getResources().getDimensionPixelSize(R.dimen.wm_item_width)) - getContext().getResources().getDimensionPixelSize(R.dimen.beautycamera_makeup_item_margin);
            } else if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
                String currentType = DataRepository.dataItemRunning().getComponentRunningShine().getCurrentType();
                char c2 = 65535;
                int hashCode = currentType.hashCode();
                if (hashCode != 49) {
                    if (hashCode != 50) {
                        if (hashCode != 55) {
                            if (hashCode == 1573 && currentType.equals("16")) {
                                c2 = 3;
                            }
                        } else if (currentType.equals("7")) {
                            c2 = 0;
                        }
                    } else if (currentType.equals("2")) {
                        c2 = 2;
                    }
                } else if (currentType.equals("1")) {
                    c2 = 1;
                }
                if (c2 == 0 || c2 == 1 || c2 == 2 || c2 == 3) {
                    layoutParams.topMargin -= getContext().getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height);
                } else {
                    layoutParams.topMargin = (layoutParams.topMargin - getContext().getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height)) - getContext().getResources().getDimensionPixelSize(R.dimen.beautycamera_popup_fragment_height);
                }
            }
        }
        return layoutParams;
    }

    private void consumeResult(int i, boolean z) {
        if (System.currentTimeMillis() - this.lastConfirmTime >= ((long) (z ? Util.KEYCODE_SLIDE_ON : 1000))) {
            this.lastConfirmTime = System.currentTimeMillis();
            Log.d("faceResult:", i + "");
            if (z) {
                mimojiFaceDetectSync(161, i);
                return;
            }
            final ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (this.lastFaceResult != i || topAlert == null || !topAlert.isContainAlertLightingTip(i)) {
                this.lastFaceResult = i;
                final LightingView lightingView = this.mLightingView;
                if (lightingView != null) {
                    lightingView.post(new Runnable() {
                        /* class com.android.camera.fragment.FragmentMainContent.AnonymousClass1 */

                        public void run() {
                            ModeProtocol.TopAlert topAlert = topAlert;
                            if (topAlert != null) {
                                topAlert.alertLightingTip(FragmentMainContent.this.lastFaceResult);
                            }
                        }
                    });
                }
                boolean z2 = i == 6;
                if (this.mLastFaceSuccess != z2) {
                    this.mLastFaceSuccess = z2;
                    if (lightingView != null) {
                        lightingView.post(new Runnable() {
                            /* class com.android.camera.fragment.FragmentMainContent.AnonymousClass2 */

                            public void run() {
                                if (FragmentMainContent.this.mLastFaceSuccess) {
                                    lightingView.triggerAnimateSuccess();
                                } else {
                                    lightingView.triggerAnimateFocusing();
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    private FrameLayout.LayoutParams getLayoutParams(FrameLayout.LayoutParams layoutParams, int i, Rect rect, Size size, int i2) {
        if (i < 0) {
            getLayoutParamsForExtent(layoutParams, rect, size, i, i2);
            return layoutParams;
        }
        if (i2 == -1 || i2 == 0) {
            int i3 = rect.left;
            int i4 = rect.top;
            size.getWidth();
            size.getHeight();
            if ((i & 1) != 0) {
                i3 = rect.left;
                size.getWidth();
            }
            if ((i & 2) != 0) {
                i4 = rect.top;
                size.getHeight();
            }
            if ((i & 4) != 0) {
                i3 = rect.right - size.getWidth();
            }
            if ((i & 8) != 0) {
                i4 = rect.bottom - size.getHeight();
            }
            if ((i & 16) != 0) {
                int i5 = rect.top;
                i4 = i5 + (((rect.bottom - i5) - size.getHeight()) / 2);
                size.getHeight();
            }
            if ((i & 32) != 0) {
                int i6 = rect.left;
                i3 = i6 + (((rect.right - i6) - size.getWidth()) / 2);
                size.getWidth();
            }
            layoutParams.leftMargin = i3;
            layoutParams.topMargin = i4;
            layoutParams.width = size.getWidth();
            layoutParams.height = size.getHeight();
        } else if (i2 == 90) {
            int i7 = rect.left;
            int i8 = rect.top;
            size.getHeight();
            size.getWidth();
            if ((i & 1) != 0) {
                i8 = rect.top;
                size.getWidth();
            }
            if ((i & 2) != 0) {
                i7 = rect.right - size.getHeight();
            }
            if ((i & 4) != 0) {
                i8 = rect.bottom - size.getWidth();
            }
            if ((i & 8) != 0) {
                i7 = rect.left;
                size.getHeight();
            }
            if ((i & 16) != 0) {
                int i9 = rect.left;
                i7 = i9 + (((rect.right - i9) - size.getHeight()) / 2);
                size.getHeight();
            }
            if ((i & 32) != 0) {
                int i10 = rect.top;
                i8 = i10 + (((rect.bottom - i10) - size.getWidth()) / 2);
                size.getWidth();
            }
            layoutParams.leftMargin = i7;
            layoutParams.topMargin = i8;
            layoutParams.width = size.getHeight();
            layoutParams.height = size.getWidth();
        } else if (i2 == 180) {
            int i11 = rect.left;
            int i12 = rect.top;
            size.getWidth();
            size.getHeight();
            if ((i & 1) != 0) {
                i11 = rect.right - size.getWidth();
            }
            if ((i & 2) != 0) {
                i12 = rect.bottom - size.getHeight();
            }
            if ((i & 4) != 0) {
                i11 = rect.left;
                size.getWidth();
            }
            if ((i & 8) != 0) {
                i12 = rect.top;
                size.getHeight();
            }
            if ((i & 16) != 0) {
                int i13 = rect.top;
                i12 = i13 + (((rect.bottom - i13) - size.getHeight()) / 2);
                size.getHeight();
            }
            if ((i & 32) != 0) {
                int i14 = rect.left;
                i11 = i14 + (((rect.right - i14) - size.getWidth()) / 2);
                size.getWidth();
            }
            layoutParams.leftMargin = i11;
            layoutParams.topMargin = i12;
            layoutParams.width = size.getWidth();
            layoutParams.height = size.getHeight();
        } else if (i2 == 270) {
            int i15 = rect.left;
            int i16 = rect.top;
            size.getHeight();
            size.getWidth();
            if ((i & 1) != 0) {
                i16 = rect.bottom - size.getWidth();
            }
            if ((i & 2) != 0) {
                i15 = rect.left;
                size.getHeight();
            }
            if ((i & 4) != 0) {
                i16 = rect.top;
                size.getWidth();
            }
            if ((i & 8) != 0) {
                i15 = rect.right - size.getHeight();
            }
            if ((i & 16) != 0) {
                int i17 = rect.left;
                i15 = i17 + (((rect.right - i17) - size.getHeight()) / 2);
                size.getHeight();
            }
            if ((i & 32) != 0) {
                int i18 = rect.top;
                i16 = i18 + (((rect.bottom - i18) - size.getWidth()) / 2);
                size.getWidth();
            }
            layoutParams.leftMargin = i15;
            layoutParams.topMargin = i16;
            layoutParams.width = size.getHeight();
            layoutParams.height = size.getWidth();
        }
        return layoutParams;
    }

    private FrameLayout.LayoutParams getLayoutParamsForExtent(FrameLayout.LayoutParams layoutParams, Rect rect, Size size, int i, int i2) {
        int i3 = rect.left;
        int i4 = rect.top;
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.extend_watermark_text_margin_bottom);
        if (i2 == -1 || i2 == 0) {
            if (i == -1) {
                int i5 = rect.top;
                i4 = i5 + (((rect.bottom - i5) - size.getHeight()) / 2);
            } else {
                i4 = rect.bottom - (dimensionPixelOffset + size.getHeight());
            }
            int i6 = rect.left;
            i3 = i6 + (((rect.right - i6) - size.getWidth()) / 2);
            layoutParams.width = size.getWidth();
            layoutParams.height = size.getHeight();
        } else {
            Log.w(TAG, "getLayoutParamsForExtent failed, unsupported degree:" + i2);
        }
        layoutParams.leftMargin = i3;
        layoutParams.topMargin = i4;
        return layoutParams;
    }

    private RectF getMergeRect(RectF rectF, RectF rectF2) {
        float max = Math.max(rectF.left, rectF2.left);
        float min = Math.min(rectF.right, rectF2.right);
        this.mergeRectF.set(max, Math.max(rectF.top, rectF2.top), min, Math.min(rectF.bottom, rectF2.bottom));
        return this.mergeRectF;
    }

    private FrameLayout initWatermarkLayout(ViewStub viewStub) {
        if (viewStub == null) {
            return null;
        }
        FrameLayout frameLayout = (FrameLayout) viewStub.inflate();
        Rect displayRect = Util.getDisplayRect();
        if (DataRepository.dataItemRunning().getComponentRunningAIWatermark().isFixedLocation()) {
            return frameLayout;
        }
        this.mListener = new DragListener(displayRect);
        frameLayout.setOnTouchListener(this.mListener);
        return frameLayout;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001e, code lost:
        if (r8 != 270) goto L_0x0046;
     */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x004a  */
    private int[] initWatermarkLocation(FrameLayout.LayoutParams layoutParams, Size size, Rect rect, int i) {
        DragListener dragListener;
        int[] iArr = new int[4];
        iArr[0] = layoutParams.leftMargin;
        iArr[1] = layoutParams.topMargin;
        if (!(i == -1 || i == 0)) {
            if (i != 90) {
                if (i != 180) {
                }
            }
            iArr[2] = layoutParams.leftMargin + size.getHeight();
            iArr[3] = layoutParams.topMargin + size.getWidth();
            dragListener = this.mListener;
            if (dragListener != null) {
                dragListener.reInit(rect, iArr);
            }
            return iArr;
        }
        iArr[2] = layoutParams.leftMargin + size.getWidth();
        iArr[3] = layoutParams.topMargin + size.getHeight();
        dragListener = this.mListener;
        if (dragListener != null) {
        }
        return iArr;
    }

    private boolean isMimojiFaceDetectTip() {
        boolean z = this.mIsMimojiFaceDetectTip;
        this.mIsMimojiFaceDetectTip = false;
        return z;
    }

    private boolean isRectIntersect(RectF rectF, RectF rectF2) {
        return rectF2.right >= rectF.left && rectF2.left <= rectF.right && rectF2.bottom >= rectF.top && rectF2.top <= rectF.bottom;
    }

    private boolean isReferenceLineEnabled() {
        return DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_REFERENCE_LINE, false);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private synchronized void mimojiFaceDetectSync(int i, int i2) {
        int i3;
        int i4;
        if (this.mCurrentMode == 184) {
            i4 = MimojiHelper2.getTipsResIdFace(i2);
            i3 = MimojiHelper2.getTipsResId(i2);
        } else {
            i4 = MimojiHelper.getTipsResIdFace(i2);
            i3 = MimojiHelper.getTipsResId(i2);
        }
        if (160 == i && i3 == -1 && i2 != 6) {
            Log.c(TAG, "mimojiFaceDetectSync 0, faceResult = " + i2 + ", mimoji tips resId = " + i3);
        } else if (161 == i && i4 == -1 && i2 != 6) {
            Log.c(TAG, "mimojiFaceDetectSync 1, faceResult = " + i2 + ", miface tips resId = " + i4);
        } else if (i2 == this.mMimojiFaceDetect && i == this.mMimojiDetectTipType) {
            setMimojiFaceDetectTip();
            Log.c(TAG, "mimojiFaceDetectSync 2, faceResult = " + i2 + "type:" + i);
        } else {
            this.mMimojiDetectTipType = i;
            this.mMimojiFaceDetect = i2;
            setMimojiFaceDetectTip();
            if (this.mCurrentMode == 184) {
                if (i2 == 6 && MimojiHelper2.getTipsResId(this.mCurrentMimojiFaceResult) == -1) {
                    this.mLastFaceSuccess = true;
                } else {
                    this.mLastFaceSuccess = false;
                }
            } else if (i2 == 6 && MimojiHelper.getTipsResId(this.mCurrentMimojiFaceResult) == -1) {
                this.mLastFaceSuccess = true;
            } else {
                this.mLastFaceSuccess = false;
            }
            Log.d("mimojiFaceDetectSync", "face_detect_type:" + i + ",result:" + i2 + ",is_face_location_ok:" + this.mLastFaceSuccess);
        }
    }

    private boolean needMoveWatermark(WatermarkItem watermarkItem, int i) {
        return i == 0 && watermarkItem != null && (watermarkItem.getLocation() & 8) != 0 && !watermarkItem.hasMove();
    }

    private boolean needReferenceLineMode() {
        int i = this.mCurrentMode;
        return (i == 254 || i == 204) ? false : true;
    }

    private boolean needShowZoomView(int i) {
        if (i != 180) {
            return i == 162 && this.mIsRecording && DataRepository.dataItemFeature().z(i) && !isAutoZoomEnabled() && !CameraSettings.isSuperEISEnabled(162) && DataRepository.dataItemGlobal().getCurrentCameraId() != 1;
        }
        return true;
    }

    private void onZoomViewOrientationChanged(int i) {
        boolean z = (i + 180) % 180 != 0;
        if (z == this.mIsHorizontal || !needShowZoomView(this.mCurrentMode)) {
            this.mIsHorizontal = z;
            return;
        }
        if (this.mIsHorizontal) {
            this.mZoomView.show(this.mDegree);
            this.mZoomViewHorizontal.hide();
            this.mZoomView.setCurrentZoomRatio(this.mZoomViewHorizontal.getCurrentZoomRatio());
        } else {
            this.mZoomView.hide();
            this.mZoomViewHorizontal.show(this.mDegree);
            this.mZoomViewHorizontal.setCurrentZoomRatio(this.mZoomView.getCurrentZoomRatio());
        }
        this.mIsHorizontal = z;
    }

    private void resetSmoothZoom() {
        ZoomView zoomView = this.mZoomViewHorizontal;
        if (zoomView != null) {
            zoomView.hide();
        }
        ZoomView zoomView2 = this.mZoomView;
        if (zoomView2 != null) {
            zoomView2.hide();
        }
        ModeProtocol.KeyEvent keyEvent = (ModeProtocol.KeyEvent) ModeCoordinatorImpl.getInstance().getAttachProtocol(239);
        if (keyEvent != null) {
            keyEvent.resetZoomKeyEvent();
        }
    }

    private void setMimojiFaceDetectTip() {
        this.mIsMimojiFaceDetectTip = true;
    }

    private void setTimeWatermark(View view, String[] strArr, int i) {
        if (i == 2) {
            ((TextView) view.findViewById(R.id.extend_hour)).setText(strArr[0]);
            ((TextView) view.findViewById(R.id.extend_month)).setText(strArr[1]);
        } else if (i != 3) {
            ((TextView) view.findViewById(R.id.month)).setText(strArr[0].toUpperCase(Locale.ENGLISH));
            ((TextView) view.findViewById(R.id.date)).setText(strArr[1]);
            ((TextView) view.findViewById(R.id.hour)).setText(strArr[2]);
        } else {
            ((TextView) view.findViewById(R.id.extend_day)).setText(strArr[0]);
            ((TextView) view.findViewById(R.id.extend_week)).setText(strArr[1].toUpperCase(Locale.ENGLISH));
        }
    }

    private void setViewMargin(View view, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.topMargin = i;
        view.setLayoutParams(marginLayoutParams);
        ViewCompat.setTranslationY(view, 0.0f);
    }

    private void showIndicator(FocusIndicator focusIndicator, int i) {
        if (i == 1) {
            focusIndicator.showStart();
        } else if (i == 2) {
            focusIndicator.showSuccess();
        } else if (i == 3) {
            focusIndicator.showFail();
        }
    }

    private void showWatermarkItem(FrameLayout frameLayout, boolean z) {
        boolean isDualWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark().isDualWatermark();
        int i = 8;
        int i2 = (!isDualWatermark || !z) ? 8 : 0;
        Log.d(TAG, "showWatermarkItem visible:" + z + " isDual:" + isDualWatermark + " visibility:" + i2);
        if (frameLayout != null) {
            frameLayout.findViewById(R.id.watermark_sample_image_f).setVisibility(z ? 0 : 8);
            View findViewById = frameLayout.findViewById(R.id.time_watermark);
            if (!isDualWatermark && z) {
                i = 0;
            }
            findViewById.setVisibility(i);
            frameLayout.findViewById(R.id.extend_time_watermark_1).setVisibility(i2);
            frameLayout.findViewById(R.id.extend_time_watermark_2).setVisibility(i2);
        }
    }

    private void showWatermarkTipsIfNeeded(WatermarkItem watermarkItem, WatermarkItem watermarkItem2) {
        ModeProtocol.TopAlert topAlert;
        if (watermarkItem != null && watermarkItem.getType() == 11 && !TextUtils.equals(watermarkItem.getKey(), WatermarkConstant.SUPER_MOON_RESET)) {
            if ((watermarkItem2 == null || TextUtils.equals(watermarkItem2.getKey(), WatermarkConstant.SUPER_MOON_RESET)) && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
                topAlert.alertAiDetectTipHint(0, R.string.super_moon_improve_effect_tips, 3000);
            }
        }
    }

    private void updateTimeWatermarkItemRotation(FrameLayout frameLayout, int i, ComponentRunningAIWatermark componentRunningAIWatermark, WatermarkItem watermarkItem) {
        Log.d(TAG, "updateTimeWatermarkItemRotation E");
        int location = watermarkItem.getLocation();
        int timeWatermarkType = watermarkItem.getTimeWatermarkType();
        Rect displayRect = Util.getDisplayRect();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
        LinearLayout linearLayout = timeWatermarkType != 2 ? timeWatermarkType != 3 ? (LinearLayout) frameLayout.findViewById(R.id.time_watermark) : (LinearLayout) frameLayout.findViewById(R.id.extend_time_watermark_2) : (LinearLayout) frameLayout.findViewById(R.id.extend_time_watermark_1);
        Size size = new Size(linearLayout.getWidth(), linearLayout.getHeight());
        getLayoutParams(layoutParams, location, displayRect, size, this.mWatermarkDegree);
        int[] initWatermarkLocation = initWatermarkLocation(layoutParams, size, displayRect, i);
        if (componentRunningAIWatermark.isFixedLocation()) {
            componentRunningAIWatermark.updateLocation(initWatermarkLocation, displayRect, watermarkItem.getType());
            watermarkItem.setCaptureCoordinate(initWatermarkLocation);
        } else {
            calculateLP(watermarkItem, i, layoutParams);
        }
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.invalidate();
        showWatermarkItem(frameLayout, false);
        setTimeWatermark(linearLayout, watermarkItem.updateTimeWatermark(), timeWatermarkType);
        linearLayout.setRotation((float) i);
        linearLayout.setVisibility(0);
        watermarkItem.updateTimeBitmap(linearLayout);
        Log.d(TAG, "updateTimeWatermarkItemRotation X");
    }

    private void updateWatermarkItemBackground(ComponentRunningAIWatermark componentRunningAIWatermark) {
        int i;
        int i2;
        Log.d(TAG, "updateWatermarkItemBackground E");
        if (this.mWatermarkBackgroundViewStub != null && this.mWatermarkBackgroundLayout != null) {
            WatermarkItem majorWatermarkItem = componentRunningAIWatermark.getMajorWatermarkItem();
            WatermarkItem minorWatermarkItem = componentRunningAIWatermark.getMinorWatermarkItem();
            boolean z = (majorWatermarkItem != null && !TextUtils.equals(WatermarkConstant.SUPER_MOON_RESET, majorWatermarkItem.getKey())) || (minorWatermarkItem != null && !TextUtils.equals(WatermarkConstant.SUPER_MOON_RESET, minorWatermarkItem.getKey()));
            if (!componentRunningAIWatermark.isDualWatermark() || !z) {
                this.mWatermarkBackgroundLayout.setVisibility(8);
                this.mWatermarkBackgroundLayout.findViewById(R.id.watermark_sample_background_image_f).setVisibility(8);
            } else {
                Log.d(TAG, "updateWatermarkItemBackground type:" + majorWatermarkItem.getType());
                Rect displayRect = Util.getDisplayRect();
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mWatermarkBackgroundLayout.getLayoutParams();
                if (TextUtils.equals(majorWatermarkItem.getKey(), WatermarkConstant.SUPER_MOON_WINDOW)) {
                    i2 = getResources().getDimensionPixelOffset(R.dimen.extend_watermark_special_background_margin_left);
                    i = getResources().getDimensionPixelOffset(R.dimen.extend_watermark_special_background_margin_top);
                } else {
                    i2 = getResources().getDimensionPixelOffset(R.dimen.extend_watermark_background_margin_left);
                    i = getResources().getDimensionPixelOffset(R.dimen.extend_watermark_background_margin_top);
                }
                layoutParams.leftMargin = displayRect.left + i2;
                layoutParams.topMargin = displayRect.top + i;
                this.mWatermarkBackgroundLayout.setLayoutParams(layoutParams);
                this.mWatermarkBackgroundLayout.invalidate();
                this.mWatermarkBackgroundLayout.setVisibility(0);
                this.mWatermarkBackgroundLayout.findViewById(R.id.watermark_sample_background_image_f).setVisibility(0);
            }
            Log.d(TAG, "updateWatermarkItemBackground X");
        }
    }

    private void updateWatermarkItemRotation(ViewStub viewStub, FrameLayout frameLayout, int i, ComponentRunningAIWatermark componentRunningAIWatermark, WatermarkItem watermarkItem) {
        Log.d(TAG, "updateWatermarkItemRotation E");
        if (viewStub != null && frameLayout != null && watermarkItem != null) {
            watermarkItem.setHasMove(false);
            Log.d(TAG, "updateWatermarkItemRotation item:" + watermarkItem.getType() + " ,key:" + watermarkItem.getKey());
            if (watermarkItem.isTimeWatermark()) {
                updateTimeWatermarkItemRotation(frameLayout, i, componentRunningAIWatermark, watermarkItem);
                return;
            }
            int location = watermarkItem.getLocation();
            Rect displayRect = Util.getDisplayRect();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
            Size bitmapSize = Util.getBitmapSize(watermarkItem.getResId());
            getLayoutParams(layoutParams, location, displayRect, bitmapSize, this.mWatermarkDegree);
            int[] initWatermarkLocation = initWatermarkLocation(layoutParams, bitmapSize, displayRect, i);
            if (componentRunningAIWatermark.isFixedLocation()) {
                componentRunningAIWatermark.updateLocation(initWatermarkLocation, displayRect, watermarkItem.getType());
                watermarkItem.setCaptureCoordinate(initWatermarkLocation);
            } else {
                calculateLP(watermarkItem, i, layoutParams);
            }
            frameLayout.setLayoutParams(layoutParams);
            frameLayout.invalidate();
            showWatermarkItem(frameLayout, false);
            ImageView imageView = (ImageView) frameLayout.findViewById(R.id.watermark_sample_image_f);
            imageView.setImageBitmap(Util.rotate(Util.convertResToBitmap(watermarkItem.getResId()), i));
            imageView.setVisibility(0);
            Log.d(TAG, "updateWatermarkItemRotation X");
        }
    }

    private void updateWatermarkRotation(int i) {
        Log.d(TAG, "updateWatermarkRotation degree = " + i);
        ComponentRunningAIWatermark componentRunningAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
        updateWatermarkItemRotation(this.mWatermarkViewStub, this.mWatermarkLayout, this.mWatermarkDegree, componentRunningAIWatermark, componentRunningAIWatermark.getMajorWatermarkItem());
        updateWatermarkItemRotation(this.mWatermarkExtendViewStub, this.mWatermarkExtendLayout, this.mWatermarkDegree, componentRunningAIWatermark, componentRunningAIWatermark.getMinorWatermarkItem());
    }

    public /* synthetic */ void Ba() {
        this.mPreviewFrame.hidePreviewReferenceLine();
        this.mPreviewFrame.hidePreviewGradienter();
    }

    public /* synthetic */ void Ca() {
        this.mCoverParent.getLayoutParams().height = this.mNormalCoverHeight;
        this.mCoverParent.requestLayout();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void checkTouchRegionContainSplitFocusExposure(MotionEvent motionEvent) {
        FocusView focusView = this.mFocusView;
        if (focusView != null) {
            focusView.checkTouchRegionContainSplitFocusExposure(motionEvent);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void clearFocusView(int i) {
        this.mFocusView.clear(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void clearIndicator(int i) {
        if (i == 1) {
            this.mFaceView.clear();
        } else if (i == 2) {
            throw new RuntimeException("not allowed call in this method");
        } else if (i == 3) {
            this.mObjectView.clear();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public void destroyEffectCropView() {
        this.mEffectCropView.onDestroy();
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomViewProtocol
    public void feedData(AutoZoomCaptureResult autoZoomCaptureResult) {
        this.mAutoZoomOverlay.feedData(autoZoomCaptureResult);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public int getActiveIndicator() {
        return this.mActiveIndicator;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public List<WaterMarkData> getFaceWaterMarkInfos() {
        return this.mFaceView.getFaceWaterMarkInfos();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public CameraHardwareFace[] getFaces() {
        return this.mFaceView.getFaces();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public RectF getFocusRect(int i) {
        if (i == 1) {
            return this.mFaceView.getFocusRect();
        }
        if (i == 3) {
            return this.mObjectView.getFocusRect();
        }
        Log.w(TAG, getFragmentTag() + ": unexpected type " + i);
        return new RectF();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public RectF getFocusRectInPreviewFrame() {
        return this.mObjectView.getFocusRectInPreviewFrame();
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 243;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_main_content;
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public RectF[] getViewRects(CameraSize cameraSize) {
        return this.mFaceView.getViewRects(cameraSize);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void hideFaceAnimator() {
        FaceView faceView = this.mFaceView;
        if (faceView != null) {
            faceView.attemptHideFaceRect(300);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void hideOrShowDocument(boolean z) {
        DocumentView documentView = this.mDocumentView;
        if (documentView != null) {
            documentView.hideOrShowPath(z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void hideReferenceGradienter() {
        if (this.mPreviewFrame != null) {
            this.mHandler.post(new i(this));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void hideReviewViews() {
        if (this.mPreviewPanel.mVideoReviewImage.getVisibility() == 0) {
            Util.fadeOut(this.mPreviewPanel.mVideoReviewImage);
        }
        Util.fadeOut(this.mPreviewPanel.mVideoReviewPlay);
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public void initEffectCropView() {
        this.mEffectCropView.onCreate();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mCoverParent = (ViewGroup) view.findViewById(R.id.cover_parent);
        this.mLeftCover = this.mCoverParent.findViewById(R.id.left_cover_layout);
        this.mRightCover = this.mCoverParent.findViewById(R.id.right_cover_layout);
        this.mMoreModeMaskView = view.findViewById(R.id.more_mode_mask);
        this.mPreviewPage = (ViewGroup) view.findViewById(R.id.v6_preview_page);
        this.mPreviewPanel = (V6PreviewPanel) this.mPreviewPage.findViewById(R.id.v6_preview_panel);
        this.mPreviewFrame = (V6PreviewFrame) this.mPreviewPanel.findViewById(R.id.v6_frame_layout);
        this.mPreviewCenterHint = (ViewGroup) this.mPreviewPanel.findViewById(R.id.center_hint_placeholder);
        this.mCenterHintIcon = (ImageView) this.mPreviewCenterHint.findViewById(R.id.center_hint_icon);
        this.mCenterHintText = (TextView) this.mPreviewCenterHint.findViewById(R.id.center_hint_text);
        this.mEffectCropView = (V6EffectCropView) this.mPreviewPanel.findViewById(R.id.v6_effect_crop_view);
        this.mFaceView = (FaceView) this.mPreviewPanel.findViewById(R.id.v6_face_view);
        this.mDocumentView = (DocumentView) this.mPreviewPanel.findViewById(R.id.document_view);
        this.mFocusView = (FocusView) this.mPreviewPanel.findViewById(R.id.v6_focus_view);
        this.mZoomView = (ZoomView) view.findViewById(R.id.v6_zoom_view);
        this.mZoomViewHorizontal = (ZoomView) view.findViewById(R.id.v6_zoom_view_horizontal);
        this.mZoomViewHorizontal.setIsHorizonal(true);
        setViewMargin(this.mZoomViewHorizontal, ((Util.getDisplayRect(0).bottom + getResources().getDimensionPixelSize(R.dimen.mode_select_margin_top)) + (Math.round(((float) Util.sBottomBarHeight) * 0.3f) / 2)) - (getResources().getDimensionPixelSize(R.dimen.pro_view_zoom_view_width) / 2));
        this.mZoomView.setZoomValueChangeListener(this);
        this.mZoomViewHorizontal.setZoomValueChangeListener(this);
        this.mAutoZoomOverlay = (AutoZoomView) this.mPreviewPanel.findViewById(R.id.autozoom_overlay);
        if (DataRepository.dataItemRunning().getComponentRunningLighting().getPortraitLightVersion() == 1) {
            this.mLightingView = (LightingView) this.mPreviewPanel.findChildrenById(R.id.lighting_view);
        }
        this.mObjectView = (ObjectView) this.mPreviewPanel.findViewById(R.id.object_view);
        this.mAfRegionsView = (AfRegionsView) this.mPreviewPanel.findViewById(R.id.afregions_view);
        this.mMimojiLightingView = (LightingView) this.mPreviewPanel.findChildrenById(R.id.mimoji_lighting_view);
        this.mMimojiLightingView.setCircleRatio(1.18f);
        this.mMimojiLightingView.setCircleHeightRatio(1.12f);
        boolean il = DataRepository.dataItemFeature().il();
        boolean mk = DataRepository.dataItemFeature().mk();
        if (il || mk) {
            this.mWatermarkViewStub = (ViewStub) view.findViewById(R.id.watermark_viewstub);
            this.mWatermarkLayout = initWatermarkLayout(this.mWatermarkViewStub);
            if (mk) {
                this.mWatermarkExtendViewStub = (ViewStub) view.findViewById(R.id.watermark_extend_viewstub);
                this.mWatermarkExtendLayout = initWatermarkLayout(this.mWatermarkExtendViewStub);
                this.mWatermarkBackgroundViewStub = (ViewStub) view.findViewById(R.id.watermark_background_viewstub);
                ViewStub viewStub = this.mWatermarkBackgroundViewStub;
                if (viewStub != null) {
                    this.mWatermarkBackgroundLayout = (FrameLayout) viewStub.inflate();
                }
            }
        }
        LightingView lightingView = this.mLightingView;
        if (lightingView != null) {
            lightingView.setRotation(this.mDegree);
        }
        adjustViewHeight();
        this.mNormalCoverHeight = Util.sWindowHeight - Util.getBottomHeight();
        this.mCoverParent.getLayoutParams().height = this.mNormalCoverHeight;
        this.mLeftCover.getLayoutParams().width = Util.getCinematicAspectRatioMargin();
        this.mRightCover.getLayoutParams().width = Util.getCinematicAspectRatioMargin();
        this.mIsIntentAction = DataRepository.dataItemGlobal().isIntentAction();
        provideAnimateElement(this.mCurrentMode, null, 2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void initializeFocusView(FocusView.ExposureViewListener exposureViewListener) {
        this.mFocusView.initialize(exposureViewListener);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean initializeObjectTrack(RectF rectF, boolean z) {
        this.mFocusView.clear();
        this.mObjectView.clear();
        this.mObjectView.setVisibility(0);
        return this.mObjectView.initializeTrackView(rectF, z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean initializeObjectView(RectF rectF, boolean z) {
        return this.mObjectView.initializeTrackView(rectF, z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isAdjustingObjectView() {
        return this.mObjectView.isAdjusting();
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomViewProtocol
    public boolean isAutoZoomActive() {
        return this.mAutoZoomOverlay.isViewActive();
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomViewProtocol
    public boolean isAutoZoomEnabled() {
        return this.mAutoZoomOverlay.isViewEnabled();
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public boolean isAutoZoomViewEnabled() {
        return this.mAutoZoomOverlay.isViewEnabled();
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public boolean isEffectViewMoved() {
        return this.mEffectCropView.isMoved();
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public boolean isEffectViewVisible() {
        return this.mEffectCropView.isVisible();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isEvAdjusted(boolean z) {
        return z ? this.mFocusView.isEvAdjustedTime() : this.mFocusView.isEvAdjusted();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isFaceExists(int i) {
        if (i == 1) {
            return this.mFaceView.faceExists();
        }
        if (i != 3) {
            return false;
        }
        return this.mObjectView.faceExists();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public boolean isFaceLocationOK() {
        return this.mLastFaceSuccess;
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isFaceStable(int i) {
        if (i == 1) {
            return this.mFaceView.isFaceStable();
        }
        if (i != 3) {
            return false;
        }
        return this.mObjectView.isFaceStable();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public boolean isFocusViewMoving() {
        return this.mFocusView.isFocusViewMoving();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isFocusViewVisible() {
        return this.mFocusView.isVisible();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isIndicatorVisible(int i) {
        return i != 1 ? i != 2 ? i == 3 && this.mObjectView.getVisibility() == 0 : this.mFocusView.getVisibility() == 0 : this.mFaceView.getVisibility() == 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isNeedExposure(int i) {
        if (i == 1) {
            return this.mFaceView.isNeedExposure();
        }
        if (i != 3) {
            return false;
        }
        return this.mObjectView.isNeedExposure();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isObjectTrackFailed() {
        return this.mObjectView.isTrackFailed();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public boolean isShowReviewViews() {
        return this.mPreviewPanel.mVideoReviewImage.getVisibility() == 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public boolean isSplitFocusExposureDown() {
        FocusView focusView = this.mFocusView;
        if (focusView != null) {
            return focusView.isSplitFocusExposureDown();
        }
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public boolean isZoomAdjustVisible() {
        return this.mZoomView.isVisible() || this.mZoomViewHorizontal.isVisible();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public boolean isZoomViewMoving() {
        return this.mZoomView.isZoomMoving() || this.mZoomViewHorizontal.isZoomMoving();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void lightingCancel() {
        LightingView lightingView = this.mLightingView;
        if (lightingView != null) {
            lightingView.triggerAnimateExit();
        }
        this.lastConfirmTime = -1;
        this.mFaceView.setLightingOn(false);
        this.mAfRegionsView.setLightingOn(false);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void lightingDetectFace(CameraHardwareFace[] cameraHardwareFaceArr, boolean z) {
        LightingView lightingView = z ? this.mMimojiLightingView : this.mLightingView;
        if (lightingView != null) {
            int i = 5;
            if (cameraHardwareFaceArr == null || cameraHardwareFaceArr.length == 0 || cameraHardwareFaceArr.length > 1) {
                consumeResult(5, z);
            } else if (this.lastConfirmTime != -1) {
                this.mFaceView.transToViewRect(cameraHardwareFaceArr[0].rect, lightingView.getFaceViewRectF());
                RectF faceViewRectF = lightingView.getFaceViewRectF();
                RectF focusRectF = lightingView.getFocusRectF();
                if (isRectIntersect(faceViewRectF, focusRectF)) {
                    getMergeRect(faceViewRectF, focusRectF);
                    float width = faceViewRectF.width() * faceViewRectF.height();
                    float width2 = this.mergeRectF.width() * this.mergeRectF.height();
                    float width3 = focusRectF.width() * focusRectF.height();
                    float f = 1.0f;
                    float f2 = z ? 0.5f : 1.0f;
                    if (z) {
                        f = 1.5f;
                    }
                    float f3 = 0.2f * width3 * f2;
                    float f4 = width3 * 0.5f * f;
                    if (width2 >= 0.5f * width) {
                        i = width2 < f3 ? 4 : (width2 >= f4 || width >= f4) ? 3 : 6;
                    }
                }
                consumeResult(i, z);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void lightingFocused() {
        LightingView lightingView = this.mLightingView;
        if (lightingView != null) {
            lightingView.triggerAnimateSuccess();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void lightingStart() {
        LightingView lightingView = this.mLightingView;
        if (lightingView != null) {
            lightingView.setCinematicAspectRatio(CameraSettings.isCinematicAspectRatioEnabled(this.mCurrentMode));
            this.mLightingView.triggerAnimateStart();
        }
        this.lastFaceResult = -1;
        this.mLastFaceSuccess = false;
        this.lastConfirmTime = System.currentTimeMillis();
        this.mFaceView.setLightingOn(true);
        this.mAfRegionsView.setLightingOn(true);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void mimojiEnd() {
        this.mMimojiLightingView.triggerAnimateExit();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void mimojiFaceDetect(final int i) {
        this.mMimojiLightingView.post(new Runnable() {
            /* class com.android.camera.fragment.FragmentMainContent.AnonymousClass3 */

            public void run() {
                FragmentMainContent.this.mCurrentMimojiFaceResult = i;
                FragmentMainContent.this.mimojiFaceDetectSync(160, i);
            }
        });
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void mimojiStart() {
        this.lastFaceResult = -1;
        this.mLastFaceSuccess = false;
        this.lastConfirmTime = System.currentTimeMillis();
        this.mFaceView.setLightingOn(true);
        this.mAfRegionsView.setLightingOn(true);
        this.mMimojiLightingView.triggerAnimateStart();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void moveWatermarkLayout(int i, int i2) {
        int height;
        ComponentRunningAIWatermark componentRunningAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
        if (this.mWatermarkLayout != null && !componentRunningAIWatermark.isDualWatermark() && needMoveWatermark(componentRunningAIWatermark.getMajorWatermarkItem(), this.mWatermarkDegree)) {
            this.mWatermarkLayout.setAlpha(0.0f);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mWatermarkLayout.getLayoutParams();
            if (i == 1) {
                int height2 = (Util.getDisplayRect().bottom - this.mWatermarkLayout.getHeight()) - i2;
                if (height2 >= 0 && height2 <= Util.getDisplayRect().bottom - this.mWatermarkLayout.getHeight()) {
                    layoutParams.topMargin = height2;
                }
            } else if (i == 2 && (height = Util.getDisplayRect().bottom - this.mWatermarkLayout.getHeight()) >= 0) {
                layoutParams.topMargin = height;
            }
            this.mWatermarkLayout.setLayoutParams(layoutParams);
            this.mWatermarkLayout.invalidate();
            Completable.create(new AlphaInOnSubscribe(this.mWatermarkLayout).setDurationTime(200).setInterpolator(new CubicEaseOutInterpolator())).subscribe();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public boolean needViewClear() {
        return true;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        View view;
        super.notifyAfterFrameAvailable(i);
        if (!(this.mCurrentMode == 254 || (view = this.mMoreModeMaskView) == null || view.getVisibility() != 0)) {
            this.mMoreModeMaskView.setVisibility(8);
        }
        this.mPreviewFrame.updateReferenceLineAccordSquare();
        CameraCapabilities capabilitiesByBogusCameraId = Camera2DataContainer.getInstance().getCapabilitiesByBogusCameraId(DataRepository.dataItemGlobal().getCurrentCameraId(), this.mCurrentMode);
        if (DataRepository.dataItemGlobal().isNormalIntent() || !capabilitiesByBogusCameraId.isSupportLightTripartite()) {
            updateReferenceGradienterSwitched();
        }
        updateCinematicAspectRatioSwitched(CameraSettings.isCinematicAspectRatioEnabled(this.mCurrentMode));
        this.mFocusView.reInit();
        int i2 = this.mCurrentMode;
        if (i2 == 185 || i2 == 210) {
            this.mFocusView.setEvAdjustable(false);
        }
        this.mZoomView.reInit();
        this.mZoomViewHorizontal.reInit();
        this.mEffectCropView.updateVisible();
        updateFocusMode(CameraSettings.getFocusMode());
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        boolean isIntentAction = DataRepository.dataItemGlobal().isIntentAction();
        if (isIntentAction != this.mIsIntentAction) {
            this.mIsIntentAction = isIntentAction;
            hideReviewViews();
        }
        if (DataRepository.dataItemGlobal().getCurrentCameraId() != this.mLastCameraId) {
            this.mLastCameraId = DataRepository.dataItemGlobal().getCurrentCameraId();
            if (Util.isAccessible()) {
                if (this.mLastCameraId != 1) {
                    this.mPreviewFrame.setContentDescription(getString(R.string.accessibility_back_preview_status));
                    this.mPreviewFrame.announceForAccessibility(getString(R.string.accessibility_back_preview_status));
                } else if (Util.isScreenSlideOff(getActivity())) {
                    this.mPreviewFrame.setContentDescription(getString(R.string.accessibility_pull_down_to_open_camera));
                    this.mPreviewFrame.announceForAccessibility(getString(R.string.accessibility_pull_down_to_open_camera));
                } else {
                    this.mPreviewFrame.setContentDescription(getString(R.string.accessibility_front_preview_status));
                    this.mPreviewFrame.announceForAccessibility(getString(R.string.accessibility_front_preview_status));
                }
            }
        }
        if (i == 2) {
            adjustViewHeight();
        } else if (i == 3) {
            adjustViewHeight();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomViewProtocol
    public void onAutoZoomStarted() {
        if (!this.mAutoZoomOverlay.isViewEnabled()) {
            this.mAutoZoomOverlay.setViewEnable(true);
            this.mAutoZoomOverlay.setViewActive(false);
            this.mAutoZoomOverlay.clear(0);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomViewProtocol
    public void onAutoZoomStopped() {
        if (this.mAutoZoomOverlay.isViewEnabled()) {
            this.mAutoZoomOverlay.setViewEnable(false);
            this.mAutoZoomOverlay.setViewActive(false);
            this.mAutoZoomOverlay.clear(4);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        destroyEffectCropView();
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public boolean onEffectViewTouchEvent(MotionEvent motionEvent) {
        return this.mEffectCropView.onTouchEvent(motionEvent);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void onKeyEventSmoothZoom(int i, KeyEvent keyEvent) {
        ZoomView zoomView = this.mZoomView;
        if (zoomView != null) {
            zoomView.onKeyEventSmoothZoom(i, keyEvent);
        }
        ZoomView zoomView2 = this.mZoomViewHorizontal;
        if (zoomView2 != null) {
            zoomView2.onKeyEventSmoothZoom(i, keyEvent);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mLastFaceSuccess = false;
        FaceView faceView = this.mFaceView;
        if (faceView != null) {
            faceView.setVisibility(8);
        }
        this.mHandler.removeCallbacksAndMessages(null);
        resetSmoothZoom();
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        FaceView faceView = this.mFaceView;
        if (faceView != null) {
            faceView.setVisibility(0);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        LightingView lightingView = this.mLightingView;
        if (lightingView != null) {
            lightingView.clear();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void onStopObjectTrack() {
        this.mObjectView.clear();
        this.mObjectView.setVisibility(8);
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomViewProtocol
    public void onTrackingStarted(RectF rectF) {
        ModeProtocol.AutoZoomModuleProtocol autoZoomModuleProtocol = (ModeProtocol.AutoZoomModuleProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(215);
        if (autoZoomModuleProtocol != null) {
            autoZoomModuleProtocol.startTracking(rectF);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomViewProtocol
    public void onTrackingStopped(int i) {
        if (this.mAutoZoomOverlay.isViewActive()) {
            this.mAutoZoomOverlay.setViewActive(false);
            this.mAutoZoomOverlay.clear(0);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public boolean onViewTouchEvent(int i, MotionEvent motionEvent) {
        if (i == this.mFocusView.getId()) {
            return this.mFocusView.onViewTouchEvent(motionEvent);
        }
        if (i == this.mEffectCropView.getId()) {
            return this.mEffectCropView.onViewTouchEvent(motionEvent);
        }
        if (i == this.mAutoZoomOverlay.getId()) {
            return this.mAutoZoomOverlay.onViewTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // com.android.camera.ui.ZoomView.zoomValueChangeListener
    public void onZoomTouchDown() {
        CameraStatUtils.trackVideoSmoothZoom(CameraStatUtils.modeIdToName(this.mCurrentMode));
        ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
        if (manuallyValueChanged != null) {
            manuallyValueChanged.updateSATIsZooming(true);
        }
    }

    @Override // com.android.camera.ui.ZoomView.zoomValueChangeListener
    public void onZoomTouchUp() {
        ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
        if (manuallyValueChanged != null) {
            manuallyValueChanged.updateSATIsZooming(false);
        }
    }

    @Override // com.android.camera.ui.ZoomView.zoomValueChangeListener
    public void onZoomValueChanged(float f) {
        ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
        if (manuallyValueChanged != null) {
            manuallyValueChanged.onDualZoomValueChanged(f, 1);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void performHapticFeedback(int i) {
        this.mPreviewFrame.performHapticFeedback(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void processingFinish() {
        setZoomViewVisible(false);
        this.mFocusView.processingFinish();
        this.mIsRecording = false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void processingStart(String str) {
        this.mIsRecording = true;
        if (needShowZoomView(this.mCurrentMode)) {
            setZoomViewVisible(true);
        }
        this.mFocusView.processingStart();
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00d5  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00ff  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0119  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0133  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0191  */
    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        CoverState coverState;
        FrameLayout frameLayout;
        FrameLayout frameLayout2;
        LightingView lightingView;
        int i3;
        CoverState coverState2;
        super.provideAnimateElement(i, list, i2);
        boolean z = true;
        animateViews(i == 254 ? 1 : -1, list, this.mMoreModeMaskView);
        CoverState coverState3 = CoverState.NONE;
        boolean isFixedLocation = DataRepository.dataItemRunning().getComponentRunningAIWatermark().isFixedLocation();
        int i4 = 0;
        if (i != 162) {
            if (i != 163) {
                if (i == 165) {
                    coverState = CoverState.TB;
                } else if (i == 169 || i == 171 || i == 173) {
                    coverState = (!CameraSettings.isCinematicAspectRatioEnabled(this.mCurrentMode) || i2 == 3) ? CoverState.NONE : CoverState.LR;
                } else if (i != 180) {
                    if (i == 188) {
                        coverState = CoverState.NONE;
                    } else if (i != 204) {
                        coverState = CoverState.NONE;
                    }
                }
                z = false;
                if (!z) {
                    i4 = 4;
                }
                setWatermarkVisible(i4);
                frameLayout = this.mWatermarkLayout;
                DragListener dragListener = null;
                if (frameLayout != null) {
                    frameLayout.setOnTouchListener(isFixedLocation ? null : this.mListener);
                }
                frameLayout2 = this.mWatermarkExtendLayout;
                if (frameLayout2 != null) {
                    if (!isFixedLocation) {
                        dragListener = this.mListener;
                    }
                    frameLayout2.setOnTouchListener(dragListener);
                }
                this.mPreviewFrame.hidePreviewReferenceLine();
                this.mPreviewFrame.hidePreviewGradienter();
                this.mFaceView.clear();
                this.mDocumentView.clear();
                this.mFaceView.clearFaceFlags();
                this.mFocusView.clear();
                lightingView = this.mLightingView;
                if (lightingView != null) {
                    lightingView.clear();
                }
                this.mAfRegionsView.clear();
                this.mMimojiLightingView.clear();
                this.mFocusView.releaseListener();
                if (this.mLeftCover.getTag() != null || this.mLeftCover.getTag() != coverState) {
                    this.mLeftCover.setTag(coverState);
                    i3 = AnonymousClass5.$SwitchMap$com$android$camera$fragment$FragmentMainContent$CoverState[coverState.ordinal()];
                    if (i3 != 2) {
                        if (this.mCoverParent.getLayoutParams().height != this.mNormalCoverHeight) {
                            this.mCoverParent.getLayoutParams().height = this.mNormalCoverHeight;
                            this.mCoverParent.requestLayout();
                        }
                        SlideOutOnSubscribe.directSetResult(this.mLeftCover, 3);
                        SlideOutOnSubscribe.directSetResult(this.mRightCover, 5);
                        return;
                    } else if (i3 != 3) {
                        if (list == null) {
                            SlideOutOnSubscribe.directSetResult(this.mLeftCover, 3);
                            SlideOutOnSubscribe.directSetResult(this.mRightCover, 5);
                        } else {
                            if (this.mLeftCover.getVisibility() == 0) {
                                list.add(Completable.create(new SlideOutOnSubscribe(this.mLeftCover, 3).setDurationTime(200)));
                            }
                            if (this.mRightCover.getVisibility() == 0) {
                                list.add(Completable.create(new SlideOutOnSubscribe(this.mRightCover, 5).setDurationTime(200)));
                            }
                        }
                        if (this.mCoverParent.getLayoutParams().height != this.mNormalCoverHeight) {
                            this.mHandler.postDelayed(new h(this), 200);
                            return;
                        }
                        return;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } else {
                coverState2 = (!CameraSettings.isCinematicAspectRatioEnabled(this.mCurrentMode) || i2 == 3) ? CoverState.NONE : CoverState.LR;
                boolean isMacroModeEnabled = CameraSettings.isMacroModeEnabled(i);
                if (CameraSettings.isAIWatermarkOn() && !isMacroModeEnabled) {
                    coverState = coverState2;
                    if (!z) {
                    }
                    setWatermarkVisible(i4);
                    frameLayout = this.mWatermarkLayout;
                    DragListener dragListener2 = null;
                    if (frameLayout != null) {
                    }
                    frameLayout2 = this.mWatermarkExtendLayout;
                    if (frameLayout2 != null) {
                    }
                    this.mPreviewFrame.hidePreviewReferenceLine();
                    this.mPreviewFrame.hidePreviewGradienter();
                    this.mFaceView.clear();
                    this.mDocumentView.clear();
                    this.mFaceView.clearFaceFlags();
                    this.mFocusView.clear();
                    lightingView = this.mLightingView;
                    if (lightingView != null) {
                    }
                    this.mAfRegionsView.clear();
                    this.mMimojiLightingView.clear();
                    this.mFocusView.releaseListener();
                    if (this.mLeftCover.getTag() != null) {
                    }
                    this.mLeftCover.setTag(coverState);
                    i3 = AnonymousClass5.$SwitchMap$com$android$camera$fragment$FragmentMainContent$CoverState[coverState.ordinal()];
                    if (i3 != 2) {
                    }
                }
                coverState = coverState2;
                z = false;
                if (!z) {
                }
                setWatermarkVisible(i4);
                frameLayout = this.mWatermarkLayout;
                DragListener dragListener22 = null;
                if (frameLayout != null) {
                }
                frameLayout2 = this.mWatermarkExtendLayout;
                if (frameLayout2 != null) {
                }
                this.mPreviewFrame.hidePreviewReferenceLine();
                this.mPreviewFrame.hidePreviewGradienter();
                this.mFaceView.clear();
                this.mDocumentView.clear();
                this.mFaceView.clearFaceFlags();
                this.mFocusView.clear();
                lightingView = this.mLightingView;
                if (lightingView != null) {
                }
                this.mAfRegionsView.clear();
                this.mMimojiLightingView.clear();
                this.mFocusView.releaseListener();
                if (this.mLeftCover.getTag() != null) {
                }
                this.mLeftCover.setTag(coverState);
                i3 = AnonymousClass5.$SwitchMap$com$android$camera$fragment$FragmentMainContent$CoverState[coverState.ordinal()];
                if (i3 != 2) {
                }
            }
        }
        coverState2 = (!CameraSettings.isCinematicAspectRatioEnabled(this.mCurrentMode) || i2 == 3) ? CoverState.NONE : CoverState.LR;
        if (DataRepository.dataItemFeature().z(i) || this.mCurrentMode == 180) {
            boolean supportVideoSATForVideoQuality = DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(i);
            this.mZoomViewHorizontal.init();
            this.mZoomViewHorizontal.setSupportVideoSat(supportVideoSATForVideoQuality);
            this.mZoomView.init();
            this.mZoomView.setSupportVideoSat(supportVideoSATForVideoQuality);
        }
        coverState = coverState2;
        z = false;
        if (!z) {
        }
        setWatermarkVisible(i4);
        frameLayout = this.mWatermarkLayout;
        DragListener dragListener222 = null;
        if (frameLayout != null) {
        }
        frameLayout2 = this.mWatermarkExtendLayout;
        if (frameLayout2 != null) {
        }
        this.mPreviewFrame.hidePreviewReferenceLine();
        this.mPreviewFrame.hidePreviewGradienter();
        this.mFaceView.clear();
        this.mDocumentView.clear();
        this.mFaceView.clearFaceFlags();
        this.mFocusView.clear();
        lightingView = this.mLightingView;
        if (lightingView != null) {
        }
        this.mAfRegionsView.clear();
        this.mMimojiLightingView.clear();
        this.mFocusView.releaseListener();
        if (this.mLeftCover.getTag() != null) {
        }
        this.mLeftCover.setTag(coverState);
        i3 = AnonymousClass5.$SwitchMap$com$android$camera$fragment$FragmentMainContent$CoverState[coverState.ordinal()];
        if (i3 != 2) {
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (this.mWatermarkDegree != i) {
            if (DataRepository.dataItemRunning().getComponentRunningAIWatermark().isFixedOrientation()) {
                this.mWatermarkDegree = 0;
            } else {
                this.mWatermarkDegree = i;
            }
            Log.d(TAG, String.format("provideRotateItem newDegree: %d mWatermarkDegree: %d", Integer.valueOf(i), Integer.valueOf(this.mWatermarkDegree)));
            updateWatermarkRotation(this.mWatermarkDegree);
        }
        this.mFaceView.setOrientation((360 - i) % 360, false);
        this.mAfRegionsView.setOrientation(i, false);
        LightingView lightingView = this.mLightingView;
        if (lightingView != null) {
            lightingView.setOrientation(i, false);
        }
        this.mFocusView.setOrientation(i, false);
        this.mZoomViewHorizontal.setOrientation(i);
        list.add(this.mFocusView);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void reShowFaceRect() {
        this.mFaceView.reShowFaceRect();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(166, this);
        modeCoordinator.attachProtocol(214, this);
        registerBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public void removeTiltShiftMask() {
        this.mEffectCropView.removeTiltShiftMask();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setActiveIndicator(int i) {
        this.mActiveIndicator = i;
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setAfRegionView(MeteringRectangle[] meteringRectangleArr, Rect rect, float f) {
        this.mAfRegionsView.setAfRegionRect(meteringRectangleArr, rect, f);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setCameraDisplayOrientation(int i) {
        FaceView faceView = this.mFaceView;
        if (faceView != null && this.mAfRegionsView != null) {
            faceView.setCameraDisplayOrientation(i);
            this.mAfRegionsView.setCameraDisplayOrientation(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void setCenterHint(int i, String str, String str2, int i2) {
        this.mHandler.removeCallbacksAndMessages(this.mPreviewCenterHint);
        if (i == 0) {
            this.mCenterHintText.setText(str);
            if (str == null || str.equals("")) {
                this.mCenterHintText.setVisibility(8);
            } else {
                this.mCenterHintText.setVisibility(0);
            }
            if (str2 == null || str2.equals("")) {
                this.mCenterHintIcon.setVisibility(8);
            } else {
                Glide.with(getActivity()).load(str2).into(this.mCenterHintIcon);
                this.mCenterHintIcon.setVisibility(0);
            }
            if (i2 > 0) {
                this.mHandler.postAtTime(new Runnable() {
                    /* class com.android.camera.fragment.FragmentMainContent.AnonymousClass4 */

                    public void run() {
                        FragmentMainContent.this.mPreviewCenterHint.setVisibility(8);
                    }
                }, this.mPreviewCenterHint, SystemClock.uptimeMillis() + ((long) i2));
            }
        }
        this.mPreviewCenterHint.setVisibility(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setDisplaySize(int i, int i2) {
        this.mObjectView.setDisplaySize(i, i2);
        DocumentView documentView = this.mDocumentView;
        if (documentView != null) {
            documentView.setDisplaySize(i, i2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public void setEffectViewVisible(boolean z) {
        if (z) {
            this.mEffectCropView.show();
        } else {
            this.mEffectCropView.hide();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setEvAdjustable(boolean z) {
        this.mFocusView.setEvAdjustable(z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean setFaces(int i, CameraHardwareFace[] cameraHardwareFaceArr, Rect rect, Rect rect2) {
        if (i != 1) {
            if (i != 3) {
                return false;
            }
            if (cameraHardwareFaceArr != null && cameraHardwareFaceArr.length > 0) {
                this.mObjectView.setObject(cameraHardwareFaceArr[0]);
            }
            return true;
        } else if (this.mCurrentMode != 166) {
            return this.mFaceView.setFaces(cameraHardwareFaceArr, rect, rect2);
        } else {
            Log.d(TAG, "current mode is panorama return false");
            return false;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setFocusViewPosition(int i, int i2, int i3) {
        this.mFocusView.setPosition(i, i2, i3);
        this.mFaceView.forceHideRect();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setFocusViewType(boolean z) {
        this.mFocusView.setFocusType(z);
    }

    @Override // com.android.camera.ui.ZoomView.zoomValueChangeListener
    public void setGestureDetectorEnable(boolean z) {
        V6GestureRecognizer.getInstance((ActivityBase) getContext()).setGestureDetectorEnable(false);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void setMimojiDetectTipType(int i) {
        if (i != this.mMimojiDetectTipType) {
            this.mMimojiDetectTipType = i;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setObjectViewListener(ObjectView.ObjectViewListener objectViewListener) {
        ObjectView objectView = this.mObjectView;
        if (objectView != null) {
            objectView.setObjectViewListener(objectViewListener);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void setPreviewAspectRatio(float f) {
        adjustViewHeight();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setPreviewSize(int i, int i2) {
        AutoZoomView autoZoomView = this.mAutoZoomOverlay;
        if (autoZoomView != null) {
            autoZoomView.setPreviewSize(new Size(i, i2));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setShowGenderAndAge(boolean z) {
        this.mFaceView.setShowGenderAndAge(z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setShowMagicMirror(boolean z) {
        this.mFaceView.setShowMagicMirror(z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setSkipDrawFace(boolean z) {
        this.mFaceView.setSkipDraw(z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void setWatermarkVisible(int i) {
        boolean isDualWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark().isDualWatermark();
        FrameLayout frameLayout = this.mWatermarkLayout;
        if (frameLayout != null) {
            frameLayout.setVisibility(i);
        }
        FrameLayout frameLayout2 = this.mWatermarkExtendLayout;
        if (frameLayout2 != null) {
            frameLayout2.setVisibility(isDualWatermark ? i : 8);
        }
        FrameLayout frameLayout3 = this.mWatermarkBackgroundLayout;
        if (frameLayout3 != null) {
            if (!isDualWatermark) {
                i = 8;
            }
            frameLayout3.setVisibility(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public void setZoomViewVisible(boolean z) {
        if (z) {
            this.mZoomViewHorizontal.show(this.mDegree);
            this.mZoomViewHorizontal.setCurrentZoomRatio(CameraSettings.readZoom());
            return;
        }
        this.mZoomViewHorizontal.hide();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void showIndicator(int i, int i2) {
        if (i == 1) {
            showIndicator(this.mFaceView, i2);
        } else if (i == 2) {
            showIndicator(this.mFocusView, i2);
        } else if (i == 3) {
            showIndicator(this.mObjectView, i2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void showReviewViews(Bitmap bitmap) {
        if (bitmap != null) {
            this.mPreviewPanel.mVideoReviewImage.setImageBitmap(bitmap);
            this.mPreviewPanel.mVideoReviewImage.setVisibility(0);
        }
        Util.fadeIn(this.mPreviewPanel.mVideoReviewPlay);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(166, this);
        unRegisterBackStack(modeCoordinator, this);
        modeCoordinator.detachProtocol(214, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateCinematicAspectRatioSwitched(boolean z) {
        Log.i(TAG, "updateCinematicPhotoSwitched isSwitchOn : " + z);
        if (z) {
            if (this.mCoverParent.getLayoutParams().height == this.mNormalCoverHeight) {
                this.mCoverParent.getLayoutParams().height = -1;
                this.mCoverParent.requestLayout();
            }
            Completable.create(new SlideInOnSubscribe(this.mLeftCover, 3)).subscribe();
            Completable.create(new SlideInOnSubscribe(this.mRightCover, 5)).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateContentDescription() {
        this.mPreviewFrame.setContentDescription(getString(R.string.accessibility_front_preview_status));
        this.mPreviewFrame.announceForAccessibility(getString(R.string.accessibility_front_preview_status));
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateCurrentZoomRatio(float f) {
        ZoomView zoomView = this.mZoomView;
        if (zoomView != null) {
            zoomView.setCurrentZoomRatio(f);
        }
        ZoomView zoomView2 = this.mZoomViewHorizontal;
        if (zoomView2 != null) {
            zoomView2.setCurrentZoomRatio(f);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void updateDocument(Pair<DocumentProcess.QuadStatus, float[]> pair) {
        if (pair == null || ((float[]) pair.second).length != 8) {
            this.mDocumentView.clear();
            this.mDocumentView.setVisibility(8);
            return;
        }
        if (this.mDocumentView.getVisibility() != 0) {
            this.mDocumentView.setVisibility(0);
        }
        this.mDocumentView.updateDocument(pair);
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public void updateEffectViewVisible() {
        this.mEffectCropView.updateVisible();
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public void updateEffectViewVisible(int i) {
        this.mEffectCropView.updateVisible(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void updateFaceView(boolean z, boolean z2, boolean z3, boolean z4, int i) {
        if (z2) {
            this.mFaceView.clear();
        }
        this.mFaceView.setVisibility(z ? 0 : 8);
        if (i > 0) {
            this.mFaceView.setCameraDisplayOrientation(i);
        }
        this.mFaceView.setMirror(z3);
        if (z4) {
            this.mFaceView.resume();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateFocusMode(String str) {
        this.mFocusView.updateFocusMode(str);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateMimojiFaceDetectResultTip(boolean z) {
        this.mMimojiLightingView.post(new g(this, z));
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateReferenceGradienterSwitched() {
        if (this.mPreviewFrame != null) {
            this.mPreviewFrame.updateReferenceGradienterSwitched(isReferenceLineEnabled() && !((ActivityBase) getContext()).getCameraIntentManager().isScanQRCodeIntent() && needReferenceLineMode(), CameraSettings.isGradienterOn(), ModuleManager.isSquareModule());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateWatermarkSample(WatermarkItem watermarkItem) {
        ComponentRunningAIWatermark componentRunningAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
        showWatermarkTipsIfNeeded(watermarkItem, componentRunningAIWatermark.getMajorWatermarkItem());
        componentRunningAIWatermark.updateWatermarkItem(watermarkItem);
        if (componentRunningAIWatermark.isDualWatermark()) {
            boolean z = true;
            showWatermarkItem(this.mWatermarkLayout, componentRunningAIWatermark.getMajorWatermarkItem() != null);
            FrameLayout frameLayout = this.mWatermarkExtendLayout;
            if (componentRunningAIWatermark.getMinorWatermarkItem() == null) {
                z = false;
            }
            showWatermarkItem(frameLayout, z);
            updateWatermarkItemBackground(componentRunningAIWatermark);
        }
        if (componentRunningAIWatermark.isFixedOrientation()) {
            this.mWatermarkDegree = 0;
        }
        if (watermarkItem != null && this.mWatermarkLayout != null) {
            updateWatermarkRotation(this.mWatermarkDegree);
            setWatermarkVisible(0);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateZoomRatio(float f, float f2) {
        ZoomView zoomView = this.mZoomView;
        if (zoomView != null) {
            zoomView.updateZoomRatio(f, f2);
        }
        ZoomView zoomView2 = this.mZoomViewHorizontal;
        if (zoomView2 != null) {
            zoomView2.updateZoomRatio(f, f2);
        }
    }

    public /* synthetic */ void w(boolean z) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        boolean z2 = true;
        switch (this.mMimojiDetectTipType) {
            case 160:
                if (isMimojiFaceDetectTip()) {
                    int tipsResId = this.mCurrentMode == 184 ? MimojiHelper2.getTipsResId(this.mMimojiFaceDetect) : MimojiHelper.getTipsResId(this.mMimojiFaceDetect);
                    if (topAlert != null && tipsResId > 0) {
                        topAlert.alertMimojiFaceDetect(true, tipsResId);
                        break;
                    }
                } else {
                    return;
                }
            case 161:
                if (isMimojiFaceDetectTip()) {
                    int tipsResIdFace = this.mCurrentMode == 184 ? MimojiHelper2.getTipsResIdFace(this.mMimojiFaceDetect) : MimojiHelper.getTipsResIdFace(this.mMimojiFaceDetect);
                    if (topAlert != null && tipsResIdFace > 0) {
                        topAlert.alertMimojiFaceDetect(true, tipsResIdFace);
                        break;
                    }
                } else {
                    return;
                }
            case 162:
                int tipsResIdFace2 = this.mCurrentMode == 184 ? MimojiHelper2.getTipsResIdFace(7) : MimojiHelper.getTipsResIdFace(7);
                if (!(topAlert == null || tipsResIdFace2 == -1 || !z)) {
                    topAlert.alertMimojiFaceDetect(true, tipsResIdFace2);
                    break;
                }
        }
        if (this.mCurrentMode == 184) {
            MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
            if (mimojiAvatarEngine2 != null) {
                mimojiAvatarEngine2.setDetectSuccess(this.mLastFaceSuccess);
                if (this.mLastFaceSuccess && !z) {
                    MimojiStatusManager2 mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
                    if (topAlert != null) {
                        if (mimojiStatusManager2.getMimojiPanelState() == 0 || mimojiStatusManager2.isInMimojiCreate()) {
                            topAlert.alertMimojiFaceDetect(true, R.string.mimoji_check_normal);
                        } else {
                            topAlert.alertMimojiFaceDetect(false, R.string.mimoji_check_normal);
                        }
                    }
                }
            }
        } else {
            ModeProtocol.MimojiAvatarEngine mimojiAvatarEngine = (ModeProtocol.MimojiAvatarEngine) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
            if (mimojiAvatarEngine != null) {
                mimojiAvatarEngine.setDetectSuccess(this.mLastFaceSuccess);
                if (this.mLastFaceSuccess && !z) {
                    MimojiStatusManager mimojiStatusManager = DataRepository.dataItemLive().getMimojiStatusManager();
                    if (topAlert != null) {
                        if (mimojiStatusManager.getMimojiPannelState() || mimojiStatusManager.IsInMimojiCreate()) {
                            topAlert.alertMimojiFaceDetect(true, R.string.mimoji_check_normal);
                        } else {
                            topAlert.alertMimojiFaceDetect(false, R.string.mimoji_check_normal);
                        }
                    }
                }
            }
        }
        if (!this.mLastFaceSuccess || z) {
            z2 = false;
        }
        if (z2) {
            this.mMimojiLightingView.triggerAnimateSuccess();
        } else if (!(this.mMimojiLastFaceSuccess == this.mLastFaceSuccess && this.mIsMimojiCreateLowLight == z)) {
            this.mMimojiLightingView.triggerAnimateStart();
        }
        this.mIsMimojiCreateLowLight = z;
        this.mMimojiLastFaceSuccess = this.mLastFaceSuccess;
    }
}
