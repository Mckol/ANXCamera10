package com.android.camera.fragment.top;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.config.ComponentRunningMacroMode;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.config.SupportedConfigs;
import com.android.camera.data.data.extra.DataItemLive;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;
import com.android.camera.data.data.runing.ComponentRunningAutoZoom;
import com.android.camera.data.data.runing.ComponentRunningColorEnhance;
import com.android.camera.data.data.runing.ComponentRunningSubtitle;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.ui.ColorCircleBackgroundView;
import com.android.camera.ui.SlideSwitchButton;
import java.util.ArrayList;
import java.util.List;
import miui.view.animation.CubicEaseOutInterpolator;

public class ExtraAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> implements View.OnClickListener {
    public static final int ITEM_TYPE_MULTI = 1;
    public static final int ITEM_TYPE_TOGGLE = 2;
    private int mAnimateHeight;
    private long mAnimationStartTime;
    private Context mContext;
    private int mCurrentRow = -1;
    private DataItemConfig mDataItemConfig;
    private DataItemLive mDataItemLive;
    private DataItemRunning mDataItemRunning;
    private View.OnClickListener mOnClickListener;
    private List<View> mRowLists = new ArrayList();
    private SlideSwitchButton.SlideSwitchListener mSlideSwitchListener;
    private SupportedConfigs mSupportedConfigs;
    private int mTAG = -1;

    public ExtraAdapter(Context context, SupportedConfigs supportedConfigs, View.OnClickListener onClickListener, SlideSwitchButton.SlideSwitchListener slideSwitchListener) {
        this.mContext = context;
        this.mSupportedConfigs = supportedConfigs;
        this.mOnClickListener = onClickListener;
        this.mSlideSwitchListener = slideSwitchListener;
        this.mDataItemRunning = DataRepository.dataItemRunning();
        this.mDataItemConfig = DataRepository.dataItemConfig();
        this.mDataItemLive = DataRepository.dataItemLive();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001c, code lost:
        if (((r0 - r1) % 4) == 0) goto L_0x0011;
     */
    private int getRow(int i) {
        int i2 = 0;
        int i3 = -1;
        int i4 = 0;
        while (i2 < getItemCount()) {
            if (getItemViewType(i2) != 1) {
                if (i4 == 0) {
                    i4 = i3 + 1;
                }
            }
            i3++;
            if (i == i2) {
                break;
            }
            i2++;
        }
        return i3;
    }

    private int getTotalRow() {
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < getItemCount(); i3++) {
            if (getItemViewType(i3) != 1) {
                if (i2 == 0) {
                    i2 = i;
                }
                if ((i3 - i2) % 4 != 0) {
                }
            }
            i++;
        }
        return i;
    }

    private void setAnimation(List<View> list, int i, int i2) {
        this.mAnimationStartTime = System.currentTimeMillis();
        for (View view : list) {
            int i3 = i2 - 1;
            float f = (float) (i3 - i);
            ViewCompat.setTranslationY(view, (float) (-this.mAnimateHeight));
            int i4 = (i3 * 20) - (i * 20);
            ViewCompat.animate(view).setInterpolator(new PathInterpolator(0.18f - (0.01f * f), 1.36f - (f * 0.02f), 0.3f, 1.0f)).translationY(0.0f).setDuration((long) (520 - i4)).setStartDelay((long) i4).start();
            ViewCompat.setAlpha(view, 0.0f);
            ViewCompat.animate(view).setInterpolator(new CubicEaseOutInterpolator()).alpha(1.0f).setDuration(400).setStartDelay((long) ((i3 * 50) - (i * 50))).start();
        }
    }

    public boolean animationRunning() {
        return System.currentTimeMillis() - this.mAnimationStartTime < 500;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mSupportedConfigs.getLength();
    }

    public int getItemCount(int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < this.mSupportedConfigs.getLength(); i3++) {
            if (i == getItemViewType(i3)) {
                i2++;
            }
        }
        return i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        int config = this.mSupportedConfigs.getConfig(i);
        return (config == 187 || config == 204 || config == 208 || config == 210 || config == 213 || config == 226) ? 1 : 2;
    }

    public int getTotalRow(int i) {
        int i2 = 0;
        if (i == 1) {
            int i3 = 0;
            while (i2 < getItemCount() && getItemViewType(i2) == i) {
                i3++;
                i2++;
            }
            return i3;
        }
        int i4 = 0;
        int i5 = 0;
        while (i2 < getItemCount()) {
            if (getItemViewType(i2) != 1) {
                if (i5 == 0) {
                    i5 = i4;
                }
                if ((i2 - i5) % 4 != 0) {
                    i2++;
                }
            }
            i4++;
            i2++;
        }
        return i4 - i5;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r10v12, resolved type: boolean */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public void onBindViewHolder(final CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        int i2;
        int i3;
        boolean z;
        String str;
        int i4;
        boolean z2;
        ComponentData componentData;
        boolean z3;
        boolean z4;
        boolean z5;
        ComponentData componentData2;
        int i5;
        int i6;
        boolean z6;
        boolean z7;
        int i7;
        int i8;
        int i9;
        boolean z8;
        int i10;
        int i11;
        int i12;
        boolean z9;
        boolean z10;
        int i13;
        int i14;
        boolean z11;
        int i15;
        int config = this.mSupportedConfigs.getConfig(i);
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        switch (config) {
            case 170:
                boolean isTimerBurstEnable = CameraSettings.isTimerBurstEnable();
                if (isTimerBurstEnable) {
                    int timerBurstTotalCount = CameraSettings.getTimerBurstTotalCount();
                    z = isTimerBurstEnable;
                    z4 = true;
                    z3 = true;
                    i2 = R.drawable.vector_drawable_ic_timer_burst;
                    i3 = R.raw.extra_anim_timer_burst;
                    str = String.format(this.mContext.getResources().getQuantityString(R.plurals.timer_burst_setting_total_count, timerBurstTotalCount), String.valueOf(timerBurstTotalCount)) + (CameraSettings.getTimerBurstInterval() + "S");
                    componentData = null;
                    i4 = -1;
                } else {
                    i4 = R.string.timer_burst;
                    z = isTimerBurstEnable;
                    z4 = true;
                    z3 = true;
                    i2 = R.drawable.vector_drawable_ic_timer_burst;
                    i3 = R.raw.extra_anim_timer_burst;
                    componentData = null;
                    str = null;
                }
                z2 = false;
                break;
            case 187:
                componentData2 = this.mDataItemLive.getComponentLiveVideoQuality();
                componentData = componentData2;
                z3 = true;
                z4 = false;
                z5 = false;
                z = 0;
                boolean z12 = z;
                i2 = z12;
                i3 = z12;
                i4 = -1;
                str = null;
                z2 = z5;
                break;
            case 199:
                z6 = this.mDataItemRunning.isSwitchOn("pref_camera_peak_key");
                i6 = R.drawable.ic_config_focus_peak_off;
                i5 = R.raw.extra_anim_focus_peak;
                i4 = R.string.pref_camera_auxiliary_focus_peak;
                z = z6;
                z3 = true;
                z4 = false;
                i2 = i6;
                i3 = i5;
                componentData = null;
                str = null;
                z2 = z4;
                break;
            case 204:
                componentData2 = this.mDataItemConfig.getComponentConfigSlowMotion();
                componentData = componentData2;
                z3 = true;
                z4 = false;
                z5 = false;
                z = 0;
                boolean z122 = z;
                i2 = z122;
                i3 = z122;
                i4 = -1;
                str = null;
                z2 = z5;
                break;
            case 206:
                z6 = CameraSettings.isLiveShotOn();
                i6 = R.drawable.ic_motionphoto_extra;
                i5 = R.raw.extra_anim_motionphoto;
                i4 = R.string.camera_liveshot_on_tip;
                z = z6;
                z3 = true;
                z4 = false;
                i2 = i6;
                i3 = i5;
                componentData = null;
                str = null;
                z2 = z4;
                break;
            case 208:
                componentData2 = this.mDataItemConfig.getComponentConfigVideoQuality();
                componentData = componentData2;
                z3 = true;
                z4 = false;
                z5 = false;
                z = 0;
                boolean z1222 = z;
                i2 = z1222;
                i3 = z1222;
                i4 = -1;
                str = null;
                z2 = z5;
                break;
            case 209:
                ComponentRunningUltraPixel componentUltraPixel = this.mDataItemRunning.getComponentUltraPixel();
                int menuDrawable = componentUltraPixel.getMenuDrawable();
                str = componentUltraPixel.getMenuString();
                z3 = true;
                z4 = false;
                i3 = 0;
                i2 = menuDrawable;
                z = CameraSettings.isUltraPixelOn();
                componentData = null;
                i4 = -1;
                z2 = i3;
                break;
            case 210:
                componentData = this.mDataItemConfig.getComponentConfigRatio();
                z3 = true;
                z5 = true;
                z4 = false;
                z = false;
                boolean z12222 = z;
                i2 = z12222;
                i3 = z12222;
                i4 = -1;
                str = null;
                z2 = z5;
                break;
            case 213:
                componentData2 = this.mDataItemConfig.getComponentConfigSlowMotionQuality();
                componentData = componentData2;
                z3 = true;
                z4 = false;
                z5 = false;
                z = 0;
                boolean z122222 = z;
                i2 = z122222;
                i3 = z122222;
                i4 = -1;
                str = null;
                z2 = z5;
                break;
            case 219:
                i9 = R.drawable.ic_config_reference_line;
                i8 = R.raw.extra_anim_line;
                i7 = R.string.pref_camera_referenceline_title;
                z7 = DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_REFERENCE_LINE, false);
                i2 = i9;
                z3 = true;
                z4 = false;
                i3 = i8;
                z = z7;
                componentData = null;
                str = null;
                i4 = i7;
                z2 = z4;
                break;
            case 220:
                ComponentRunningSubtitle componentRunningSubtitle = this.mDataItemRunning.getComponentRunningSubtitle();
                z9 = componentRunningSubtitle.isSwitchOn(currentMode);
                i12 = R.drawable.ic_config_subtitle;
                i11 = R.raw.extra_anim_subtitle;
                i10 = componentRunningSubtitle.getResText();
                z = z9;
                z3 = true;
                z4 = false;
                i2 = i12;
                i3 = i11;
                str = null;
                z8 = false;
                i4 = i10;
                componentData = null;
                z2 = z8;
                break;
            case 221:
                z = this.mDataItemRunning.getComponentRunningDocument().isSwitchOn(currentMode);
                z3 = true;
                z4 = false;
                i3 = 0;
                i2 = R.drawable.document_mode_normal;
                i4 = R.string.pref_document_mode;
                componentData = null;
                str = null;
                z2 = i3;
                break;
            case 223:
                ComponentRunningAIWatermark componentRunningAIWatermark = this.mDataItemRunning.getComponentRunningAIWatermark();
                i10 = R.string.ai_watermark_title;
                int cameraId = CameraSettings.getCameraId();
                if (Camera2DataContainer.getInstance().getUltraWideCameraId() == cameraId || Camera2DataContainer.getInstance().getStandaloneMacroCameraId() == cameraId) {
                    this.mDataItemRunning.getComponentRunningAIWatermark().setAIWatermarkEnable(false);
                    z10 = false;
                } else {
                    z10 = true;
                }
                z = componentRunningAIWatermark.getAIWatermarkEnable();
                z4 = false;
                i3 = 0;
                z3 = z10;
                i2 = R.drawable.ic_ai_watermark_enter_normal;
                str = null;
                z8 = false;
                i4 = i10;
                componentData = null;
                z2 = z8;
                break;
            case 225:
                i2 = R.drawable.ic_config_extra_setting;
                z3 = true;
                z4 = false;
                z5 = false;
                z = false;
                i3 = 0;
                i4 = R.string.config_name_setting;
                componentData = null;
                str = null;
                z2 = z5;
                break;
            case 226:
                componentData2 = this.mDataItemRunning.getComponentRunningTimer();
                componentData = componentData2;
                z3 = true;
                z4 = false;
                z5 = false;
                z = 0;
                boolean z1222222 = z;
                i2 = z1222222;
                i3 = z1222222;
                i4 = -1;
                str = null;
                z2 = z5;
                break;
            case 227:
                ComponentRunningColorEnhance componentRunningColorEnhance = DataRepository.dataItemRunning().getComponentRunningColorEnhance();
                z11 = componentRunningColorEnhance.isEnabled(currentMode);
                i13 = componentRunningColorEnhance.getResIcon(z11);
                i14 = componentRunningColorEnhance.getDisplayTitleString();
                z = z11;
                z3 = true;
                z4 = false;
                i3 = 0;
                i4 = i14;
                i2 = i13;
                componentData = null;
                str = null;
                z2 = i3;
                break;
            case 228:
                z9 = this.mDataItemRunning.getComponentRunningTiltValue().isSwitchOn(160);
                i10 = R.string.config_name_tilt;
                i12 = R.drawable.ic_config_tilt;
                i11 = R.raw.extra_anim_tilt;
                z = z9;
                z3 = true;
                z4 = false;
                i2 = i12;
                i3 = i11;
                str = null;
                z8 = false;
                i4 = i10;
                componentData = null;
                z2 = z8;
                break;
            case 229:
                i15 = R.raw.extra_anim_gradienter;
                i4 = R.string.config_name_straighten;
                boolean isGradienterOn = CameraSettings.isGradienterOn();
                z3 = true;
                if (!CameraSettings.isAutoZoomEnabled(currentMode)) {
                    z4 = false;
                    i2 = R.drawable.ic_config_straighten;
                    i3 = R.raw.extra_anim_gradienter;
                    z = isGradienterOn;
                    componentData = null;
                    str = null;
                    z2 = z4;
                    break;
                } else {
                    z4 = false;
                    z = false;
                    i2 = R.drawable.ic_config_straighten;
                    i3 = i15;
                    componentData = null;
                    str = null;
                    z2 = z;
                    break;
                }
            case 233:
                boolean isFastMotionModule = ModuleManager.isFastMotionModule();
                i2 = R.drawable.ic_config_fast_motion;
                z3 = true;
                z4 = false;
                i3 = 0;
                i4 = R.string.pref_video_speed_fast_title;
                z = isFastMotionModule;
                componentData = null;
                str = null;
                z2 = i3;
                break;
            case 237:
                i14 = R.string.pref_camera_picture_format_entry_raw;
                z11 = this.mDataItemConfig.getComponentConfigRaw().isSwitchOn(currentMode);
                i13 = R.drawable.ic_raw_off;
                z = z11;
                z3 = true;
                z4 = false;
                i3 = 0;
                i4 = i14;
                i2 = i13;
                componentData = null;
                str = null;
                z2 = i3;
                break;
            case 242:
                if (Util.isGlobalVersion()) {
                    i15 = R.raw.extra_anim_google;
                    i2 = R.drawable.ic_config_ai_glens;
                    z3 = true;
                    z4 = false;
                    z = false;
                    i4 = R.string.pref_google_lens;
                    i3 = i15;
                    componentData = null;
                    str = null;
                    z2 = z;
                    break;
                }
                z3 = true;
                z4 = false;
                z5 = false;
                z = false;
                i3 = 0;
                i2 = 0;
                componentData = null;
                i4 = -1;
                str = null;
                z2 = z5;
                break;
            case 251:
                z6 = CameraSettings.isCinematicAspectRatioEnabled(currentMode);
                i6 = R.drawable.ic_cinematic_aspect_ratio_menu;
                i5 = R.raw.extra_anim_cinema;
                i4 = R.string.moive_frame;
                z = z6;
                z3 = true;
                z4 = false;
                i2 = i6;
                i3 = i5;
                componentData = null;
                str = null;
                z2 = z4;
                break;
            case 252:
                i9 = R.drawable.ic_config_hand_gesture;
                i8 = R.raw.extra_anim_hand_gesture;
                i7 = R.string.hand_gesture_tip;
                z7 = this.mDataItemRunning.isSwitchOn("pref_hand_gesture");
                i2 = i9;
                z3 = true;
                z4 = false;
                i3 = i8;
                z = z7;
                componentData = null;
                str = null;
                i4 = i7;
                z2 = z4;
                break;
            case 253:
                ComponentRunningAutoZoom componentRunningAutoZoom = this.mDataItemRunning.getComponentRunningAutoZoom();
                z9 = componentRunningAutoZoom.isSwitchOn(currentMode);
                i12 = R.drawable.ic_autozoom_menu_off;
                i11 = R.raw.extra_anim_auto_zoom;
                i10 = componentRunningAutoZoom.getResText();
                z = z9;
                z3 = true;
                z4 = false;
                i2 = i12;
                i3 = i11;
                str = null;
                z8 = false;
                i4 = i10;
                componentData = null;
                z2 = z8;
                break;
            case 255:
                ComponentRunningMacroMode componentRunningMacroMode = this.mDataItemRunning.getComponentRunningMacroMode();
                z9 = componentRunningMacroMode.isSwitchOn(currentMode);
                i12 = R.drawable.ic_menu_macro_mode_off;
                i11 = R.raw.extra_anim_macro_mode;
                i10 = componentRunningMacroMode.getResText();
                z = z9;
                z3 = true;
                z4 = false;
                i2 = i12;
                i3 = i11;
                str = null;
                z8 = false;
                i4 = i10;
                componentData = null;
                z2 = z8;
                break;
            case 258:
                z6 = this.mDataItemRunning.isSwitchOn("pref_camera_exposure_feedback");
                i6 = R.drawable.ic_config_exposure_feedback_off;
                i5 = R.raw.extra_anim_exposure_feedback;
                i4 = R.string.pref_camera_auxiliary_exposure_feedback;
                z = z6;
                z3 = true;
                z4 = false;
                i2 = i6;
                i3 = i5;
                componentData = null;
                str = null;
                z2 = z4;
                break;
            case 260:
                z6 = CameraSettings.isProVideoLogOpen(currentMode);
                i6 = R.drawable.ic_config_video_log_off;
                i5 = R.raw.extra_anim_log;
                i4 = R.string.log_format;
                z = z6;
                z3 = true;
                z4 = false;
                i2 = i6;
                i3 = i5;
                componentData = null;
                str = null;
                z2 = z4;
                break;
            case 261:
                z6 = CameraSettings.isProVideoHistogramOpen(currentMode);
                i6 = R.drawable.ic_config_histogram_off;
                i5 = R.raw.extra_anim_histogram;
                i4 = R.string.parameter_histogram_title;
                z = z6;
                z3 = true;
                z4 = false;
                i2 = i6;
                i3 = i5;
                componentData = null;
                str = null;
                z2 = z4;
                break;
            case 262:
                i9 = R.drawable.ic_config_speech_shutter;
                i8 = R.raw.extra_anim_speech;
                i7 = R.string.speech_shutter_tip;
                z7 = this.mDataItemRunning.isSwitchOn("pref_speech_shutter");
                i2 = i9;
                z3 = true;
                z4 = false;
                i3 = i8;
                z = z7;
                componentData = null;
                str = null;
                i4 = i7;
                z2 = z4;
                break;
            default:
                z3 = true;
                z4 = false;
                z5 = false;
                z = false;
                i3 = 0;
                i2 = 0;
                componentData = null;
                i4 = -1;
                str = null;
                z2 = z5;
                break;
        }
        if (getItemViewType(i) == 1) {
            SlideSwitchButton slideSwitchButton = (SlideSwitchButton) commonRecyclerViewHolder.getView(R.id.top_config_ratio);
            slideSwitchButton.setComponentData(componentData, config, z2);
            slideSwitchButton.setEnabled(!componentData.disableUpdate());
            slideSwitchButton.setSlideSwitchListener(this.mSlideSwitchListener);
            return;
        }
        TextView textView = (TextView) commonRecyclerViewHolder.getView(R.id.extra_tootle_item_text);
        View view = commonRecyclerViewHolder.getView(R.id.extra_tootle_item_layout);
        ColorCircleBackgroundView colorCircleBackgroundView = (ColorCircleBackgroundView) commonRecyclerViewHolder.getView(R.id.extra_tootle_item_image_bg);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) commonRecyclerViewHolder.getView(R.id.extra_tootle_item_image);
        textView.setSelected(true);
        lottieAnimationView.setTag(Integer.valueOf(config));
        ImageView imageView = (ImageView) commonRecyclerViewHolder.getView(R.id.iv_tag_config);
        if (z4) {
            imageView.setVisibility(0);
        } else {
            imageView.setVisibility(8);
        }
        RelativeLayout relativeLayout = (RelativeLayout) commonRecyclerViewHolder.getView(R.id.rl_item_top_config_description);
        relativeLayout.setTag(Integer.valueOf(config));
        relativeLayout.setOnClickListener(this.mOnClickListener);
        if (i4 != -1) {
            textView.setText(i4);
        } else {
            textView.setText(str);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(textView.getText());
        if (z) {
            sb.append(commonRecyclerViewHolder.itemView.getResources().getString(R.string.accessibility_open));
        } else {
            sb.append(commonRecyclerViewHolder.itemView.getResources().getString(R.string.accessibility_closed));
        }
        boolean z13 = z && !colorCircleBackgroundView.isSwitchOn() && view.getTag() != null && i3 != 0;
        FolmeUtils.touchItemScale(view);
        view.setContentDescription(sb);
        view.setOnClickListener(this);
        view.setTag(Integer.valueOf(config));
        view.setEnabled(z3);
        colorCircleBackgroundView.setSwitchOn(z, false);
        if (!z) {
            lottieAnimationView.cancelAnimation();
            lottieAnimationView.setImageResource(i2);
            lottieAnimationView.setColorFilter(0);
        } else if (!z13) {
            lottieAnimationView.cancelAnimation();
            lottieAnimationView.setImageResource(i2);
            lottieAnimationView.setColorFilter(ViewCompat.MEASURED_STATE_MASK);
        }
        if (z13) {
            lottieAnimationView.setImageDrawable(null);
            lottieAnimationView.setScale(0.38f);
            lottieAnimationView.k(i3);
            lottieAnimationView.setProgress(1.0f);
            lottieAnimationView.T();
        }
        if ((Util.isAccessible() || Util.isSetContentDesc()) && this.mTAG == config) {
            commonRecyclerViewHolder.itemView.postDelayed(new Runnable() {
                /* class com.android.camera.fragment.top.ExtraAdapter.AnonymousClass1 */

                public void run() {
                    commonRecyclerViewHolder.itemView.sendAccessibilityEvent(128);
                }
            }, 100);
        }
    }

    public void onClick(View view) {
        this.mOnClickListener.onClick(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommonRecyclerViewHolder(i == 1 ? LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_top_config_extra_mulit_item, viewGroup, false) : i == 2 ? LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_top_config_extra_toggle_item, viewGroup, false) : null);
    }

    public void onViewAttachedToWindow(CommonRecyclerViewHolder commonRecyclerViewHolder) {
        super.onViewAttachedToWindow((RecyclerView.ViewHolder) commonRecyclerViewHolder);
        int totalRow = getTotalRow();
        if (this.mCurrentRow != totalRow) {
            ViewCompat.setAlpha(commonRecyclerViewHolder.itemView, 0.0f);
            int row = getRow(commonRecyclerViewHolder.getAdapterPosition());
            int i = this.mCurrentRow;
            if (row > i) {
                if (i > -1) {
                    setAnimation(this.mRowLists, i, totalRow);
                }
                this.mRowLists.clear();
                this.mRowLists.add(commonRecyclerViewHolder.itemView);
                this.mCurrentRow = row;
            } else if (row == i) {
                this.mRowLists.add(commonRecyclerViewHolder.itemView);
            }
            if (commonRecyclerViewHolder.getAdapterPosition() == getItemCount() - 1) {
                setAnimation(this.mRowLists, this.mCurrentRow, totalRow);
                this.mCurrentRow++;
            }
        }
    }

    public void setAnimateHeight(int i) {
        this.mAnimateHeight = i;
    }

    public void setOnClictTag(int i) {
        this.mTAG = i;
    }
}
