package com.android.camera.fragment.top;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.StringRes;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import b.c.a.c;
import com.android.camera.ActivityBase;
import com.android.camera.Camera;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.customization.TintColor;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.config.ComponentConfigFlash;
import com.android.camera.data.data.config.ComponentConfigHdr;
import com.android.camera.data.data.config.ComponentConfigMeter;
import com.android.camera.data.data.config.ComponentConfigUltraWide;
import com.android.camera.data.data.config.ComponentConfigVideoQuality;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.android.camera.data.data.config.SupportedConfigs;
import com.android.camera.data.data.config.TopConfigItem;
import com.android.camera.data.data.runing.ComponentRunningAiAudio;
import com.android.camera.data.data.runing.ComponentRunningColorEnhance;
import com.android.camera.data.data.runing.ComponentRunningEisPro;
import com.android.camera.data.data.runing.ComponentRunningMoon;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.music.LiveMusicActivity;
import com.android.camera.fragment.top.TopExpandAdapter;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.timerburst.CustomSeekBar;
import com.android.camera.timerburst.TimerBurstController;
import com.android.camera.ui.ColorImageView;
import com.android.camera.ui.ShapeBackGroundView;
import com.android.camera.ui.SlideSwitchButton;
import com.android.camera.ui.VideoTagView;
import com.android.camera.ui.drawable.snap.PaintConditionReferred;
import com.xiaomi.stat.MiStat;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import miui.view.animation.CubicEaseInOutInterpolator;
import miui.view.animation.CubicEaseOutInterpolator;
import miui.view.animation.QuadraticEaseInOutInterpolator;

public class FragmentTopConfig extends BaseFragment implements View.OnClickListener, TopExpandAdapter.ExpandListener, ModeProtocol.TopAlert, ModeProtocol.SnapShotIndicator, ModeProtocol.HandleBackTrace, ModeProtocol.HandleBeautyRecording, SlideSwitchButton.SlideSwitchListener {
    private static final int EXPAND_STATE_CENTER = 2;
    private static final int EXPAND_STATE_LEFT = 0;
    private static final int EXPAND_STATE_LEFT_FROM_SIBLING = 1;
    private static final int EXPAND_STATE_RIGHT = 4;
    private static final int EXPAND_STATE_RIGHT_FROM_SIBLING = 3;
    private static final String TAG = "FragmentTopConfig";
    public static final int TIP_HINT_DURATION_1S = 1000;
    public static final int TIP_HINT_DURATION_2S = 2000;
    public static final int TIP_HINT_DURATION_3S = 3000;
    private int[] mAiSceneResources;
    private int[] mAutoZoomResources;
    private TextView mCaptureDelayNumber;
    private int[] mCinematicRatioResources;
    private List<ImageView> mConfigViews;
    private int mCurrentAiSceneLevel;
    private CustomSeekBar mCustomSeekBarCount;
    private CustomSeekBar mCustomSeekBarInterval;
    private SparseBooleanArray mDisabledFunctionMenu;
    private int mDisplayRectTopMargin;
    private int[] mDocumentResources;
    private ExtraAdapter mExtraAdapter;
    private ValueAnimator mExtraMenuHideAnimator;
    private FragmentTopAlert mFragmentTopAlert;
    private int mGifResource;
    private ImageView mImageViewBack;
    private boolean mIsRTL;
    private boolean mIsShowExtraMenu;
    private boolean mIsShowExtraTimerMenu = false;
    private boolean mIsShowTopLyingDirectHint;
    private int[] mLightingResource;
    private ObjectAnimator mLiveShotAnimator;
    private int[] mLiveShotResource;
    private LinearLayout mLlTimerMenu;
    private int[] mMoreResources;
    private TextView mMultiSnapNum;
    private TextAppearanceSpan mSnapStyle;
    private int mSpacesItemWidth;
    private SpannableStringBuilder mStringBuilder;
    private int[] mSuperEISResources;
    private int[] mSuperMacroResources;
    private SupportedConfigs mSupportedConfigs;
    private Map<String, Boolean> mTipsState = new HashMap();
    private int mTopBackgroundHeight;
    private ShapeBackGroundView mTopBackgroundView;
    private TopBarAnimationComponent mTopBarAnimationComponent;
    private View mTopConfigMenu;
    private int mTopConfigMenuHeight;
    private ViewGroup mTopConfigViewGroup;
    private TopExpendView mTopExpandView;
    private RecyclerView mTopExtraMenu;
    private int mTopExtraMenuHeight;
    private int mTotalWidth;
    private TextView mTvShotInterval;
    private int[] mUltraPixelPhotographyIconResources;
    private String[] mUltraPixelPhotographyTipString;
    private int[] mUltraPixelPortraitResources;
    private int[] mUltraWideBokehResources;
    private int[] mVideo8KResource;
    private int[] mVideoBokehResource;
    private AnimatorSet mZoomInAnimator;
    private AnimatorSet mZoomOutAnimator;

    private void adjustViewBackground() {
        ExtraAdapter extraAdapter = this.mExtraAdapter;
        if ((extraAdapter == null || !extraAdapter.animationRunning()) && ((Integer) this.mTopBackgroundView.getTag()).intValue() != this.mTopBackgroundView.getCurrentMaskHeight()) {
            ShapeBackGroundView shapeBackGroundView = this.mTopBackgroundView;
            shapeBackGroundView.setMaskSpecificHeight(((Integer) shapeBackGroundView.getTag()).intValue(), true);
        }
    }

    private void alertHDR(int i, boolean z, boolean z2, boolean z3) {
        ImageView topImage;
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert) && !topAlert.isShowMoonSelector()) {
            if (z3) {
                if (i != 0) {
                    reverseExpandTopBar(true);
                } else if (z2 && (topImage = getTopImage(194)) != null) {
                    topImage.performClick();
                }
            }
            alertSwitchTip(FragmentTopAlert.TIP_HDR, i, R.string.hdr_tip);
        }
    }

    private void alertTopMusicHint(int i, String str) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.alertMusicTip(i, str);
        }
    }

    private void animatorExtraMenu(final boolean z) {
        final int i;
        final int i2;
        ValueAnimator valueAnimator;
        if (this.mTopExtraMenu.getVisibility() != 0 && !z && !this.mIsShowExtraTimerMenu) {
            return;
        }
        if (z || (valueAnimator = this.mExtraMenuHideAnimator) == null || !valueAnimator.isRunning()) {
            int i3 = 0;
            if (DataRepository.dataItemRunning().getUiStyle() == 3) {
                i = 153;
                i2 = 0;
            } else {
                i2 = 255;
                i = 255;
            }
            AnonymousClass2 r12 = new Animator.AnimatorListener() {
                /* class com.android.camera.fragment.top.FragmentTopConfig.AnonymousClass2 */

                public void onAnimationCancel(Animator animator) {
                    FragmentTopConfig.this.setClickEnable(true);
                    if (!z) {
                        FragmentTopConfig.this.directHiddenExtraMenu();
                    }
                    FragmentTopConfig.this.mExtraMenuHideAnimator = null;
                }

                public void onAnimationEnd(Animator animator) {
                    FragmentTopConfig.this.setClickEnable(true);
                    if (!z) {
                        FragmentTopConfig.this.directHiddenExtraMenu();
                    }
                    FragmentTopConfig.this.mExtraMenuHideAnimator = null;
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                    FragmentTopConfig.this.setClickEnable(false);
                }
            };
            final int blackOriginHeight = this.mTopBackgroundView.getBlackOriginHeight();
            if (z) {
                initExtraTimerBurstMenu();
                this.mTopConfigMenu.setVisibility(8);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mTopConfigViewGroup.getLayoutParams();
                marginLayoutParams.height = this.mTopExtraMenuHeight;
                this.mTopConfigViewGroup.setLayoutParams(marginLayoutParams);
                ShapeBackGroundView shapeBackGroundView = this.mTopBackgroundView;
                if (blackOriginHeight != 0) {
                    i3 = this.mTopExtraMenuHeight;
                }
                shapeBackGroundView.setTag(Integer.valueOf(i3));
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mTopBackgroundView.getLayoutParams();
                marginLayoutParams2.height = this.mTopExtraMenuHeight;
                this.mTopBackgroundView.setLayoutParams(marginLayoutParams2);
                this.mTopBackgroundView.startBackGroundAnimator(i2, i, blackOriginHeight, this.mTopExtraMenuHeight, 0, 66, 200, r12);
                return;
            }
            this.mTopBackgroundView.setTag(Integer.valueOf(blackOriginHeight));
            this.mTopConfigMenu.setVisibility(0);
            this.mTopExtraMenu.setVisibility(8);
            ViewCompat.setAlpha(this.mTopConfigMenu, 0.0f);
            ViewCompat.animate(this.mTopConfigMenu).alpha(1.0f).setInterpolator(new CubicEaseOutInterpolator()).setDuration(200).setStartDelay(100).start();
            if (this.mExtraMenuHideAnimator == null) {
                this.mExtraMenuHideAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
                this.mExtraMenuHideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    /* class com.android.camera.fragment.top.FragmentTopConfig.AnonymousClass3 */

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) FragmentTopConfig.this.mTopConfigViewGroup.getLayoutParams();
                        marginLayoutParams.height = (int) (((float) FragmentTopConfig.this.mTopConfigMenuHeight) + (((float) (FragmentTopConfig.this.mTopExtraMenuHeight - FragmentTopConfig.this.mTopConfigMenuHeight)) * floatValue));
                        FragmentTopConfig.this.mTopConfigViewGroup.setLayoutParams(marginLayoutParams);
                        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) FragmentTopConfig.this.mTopBackgroundView.getLayoutParams();
                        marginLayoutParams2.height = (int) (((float) FragmentTopConfig.this.mTopBackgroundHeight) + (((float) (FragmentTopConfig.this.mTopExtraMenuHeight - FragmentTopConfig.this.mTopBackgroundHeight)) * floatValue));
                        FragmentTopConfig.this.mTopBackgroundView.setLayoutParams(marginLayoutParams2);
                        int i = FragmentTopConfig.this.mTopExtraMenuHeight;
                        int i2 = blackOriginHeight;
                        int i3 = (int) (((float) blackOriginHeight) + (((float) (i - i2)) * floatValue));
                        int i4 = i2 == 0 ? 0 : i3;
                        ShapeBackGroundView shapeBackGroundView = FragmentTopConfig.this.mTopBackgroundView;
                        int i5 = i2;
                        shapeBackGroundView.setBackgroundAlphaAndRadius((int) (((float) i5) + (((float) (i - i5)) * floatValue)), (int) (floatValue * 66.0f), i3, i4);
                    }
                });
                this.mExtraMenuHideAnimator.addListener(r12);
                this.mExtraMenuHideAnimator.setInterpolator(new CubicEaseOutInterpolator());
                this.mExtraMenuHideAnimator.setDuration(200L);
            }
            this.mExtraMenuHideAnimator.start();
        }
    }

    private void configBottomPopupTips(boolean z) {
        ModeProtocol.BottomPopupTips bottomPopupTips;
        if (DataRepository.dataItemFeature().Gj() && (bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)) != null) {
            bottomPopupTips.showIDCardTip(z);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void directHiddenExtraMenu() {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mTopConfigViewGroup.getLayoutParams();
        int i = marginLayoutParams.height;
        int i2 = this.mTopConfigMenuHeight;
        if (i != i2) {
            marginLayoutParams.height = i2;
            this.mTopConfigViewGroup.setLayoutParams(marginLayoutParams);
            int blackOriginHeight = this.mTopBackgroundView.getBlackOriginHeight();
            this.mTopBackgroundView.setTag(Integer.valueOf(blackOriginHeight));
            this.mTopBackgroundView.setCurrentHeight(blackOriginHeight);
            this.mTopBackgroundView.setMaskSpecificHeight(blackOriginHeight, false);
            this.mTopBackgroundView.setCurrentRadius(0);
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mTopBackgroundView.getLayoutParams();
            marginLayoutParams2.height = this.mTopBackgroundHeight;
            this.mTopBackgroundView.setLayoutParams(marginLayoutParams2);
            this.mTopConfigMenu.setVisibility(0);
            this.mTopExtraMenu.setVisibility(8);
            this.mLlTimerMenu.setVisibility(8);
            this.mIsShowExtraTimerMenu = false;
        }
    }

    private void enableAllDisabledMenuItem() {
        SparseBooleanArray sparseBooleanArray = this.mDisabledFunctionMenu;
        if (!(sparseBooleanArray == null || sparseBooleanArray.size() == 0)) {
            int size = this.mDisabledFunctionMenu.size();
            for (int i = 0; i < size; i++) {
                ImageView topImage = getTopImage(this.mDisabledFunctionMenu.keyAt(i));
                if (topImage != null) {
                    AlphaInOnSubscribe.directSetResult(topImage);
                }
            }
            this.mDisabledFunctionMenu.clear();
        }
    }

    private void expandExtraView(ComponentData componentData, View view, int i) {
        this.mFragmentTopAlert.hideRecordingTime();
        if (!reverseExpandTopBar(true)) {
            TopExpandAdapter topExpandAdapter = new TopExpandAdapter(componentData, this);
            topExpandAdapter.setAnchorViewX(this.mIsRTL ? view.getRight() : view.getLeft());
            if (ModuleManager.isLandscapeMode()) {
                this.mTopExpandView.setRotation(90);
            } else {
                this.mTopExpandView.setRotation(this.mDegree);
            }
            this.mTopExpandView.setAdapter(topExpandAdapter);
            this.mTopExpandView.setOnClickListener(new l(this));
            TopBarAnimationComponent topBarAnimationComponent = this.mTopBarAnimationComponent;
            topBarAnimationComponent.mTopExpendView = this.mTopExpandView;
            topBarAnimationComponent.mReverseLeft = view.getLeft();
            this.mTopBarAnimationComponent.hideOtherViews(i, this.mConfigViews);
            TopBarAnimationComponent topBarAnimationComponent2 = this.mTopBarAnimationComponent;
            topBarAnimationComponent2.mAnchorView = view;
            topBarAnimationComponent2.spacesItemWidth = this.mSpacesItemWidth;
            topBarAnimationComponent2.showExpendView();
        }
    }

    private Drawable getAiSceneDrawable(int i) {
        TypedArray obtainTypedArray = getResources().obtainTypedArray(R.array.ai_scene_drawables);
        Drawable drawable = null;
        if (i >= 0 && i < obtainTypedArray.length()) {
            drawable = obtainTypedArray.getDrawable(i);
        }
        obtainTypedArray.recycle();
        return drawable;
    }

    private int[] getAiSceneResources() {
        return new int[]{R.drawable.ic_new_ai_scene_off, R.drawable.ic_new_ai_scene_off_shadow};
    }

    private Drawable getAiSceneShadowDrawable(int i) {
        TypedArray obtainTypedArray = getResources().obtainTypedArray(R.array.ai_scene_shadow_drawables);
        Drawable drawable = null;
        if (i >= 0 && i < obtainTypedArray.length()) {
            drawable = obtainTypedArray.getDrawable(i);
        }
        obtainTypedArray.recycle();
        return drawable;
    }

    private int[] getAutoZoomResources() {
        return new int[]{R.drawable.ic_autozoom_off, R.drawable.ic_autozoom_on};
    }

    private int[] getCinematicRatioResources() {
        return new int[]{R.drawable.ic_cinematic_aspect_ratio_off, R.drawable.ic_cinematic_aspect_ratio_on};
    }

    private int[] getDocumentResources() {
        return new int[]{R.drawable.document_mode_normal, R.drawable.document_mode_selected};
    }

    private int[] getExposureFeedbackResources() {
        return new int[]{R.drawable.ic_config_exposure_feedback_off, R.drawable.ic_config_exposure_feedback_on};
    }

    private int getGifRecource() {
        return R.drawable.ic_vector_new_config_gif;
    }

    private int getInitialMargin(TopConfigItem topConfigItem, ImageView imageView, int i) {
        SupportedConfigs supportedConfigs = this.mSupportedConfigs;
        int configsSize = supportedConfigs == null ? 0 : supportedConfigs.getConfigsSize();
        if (configsSize <= 0) {
            return 0;
        }
        int i2 = topConfigItem.index;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.gravity = 0;
        if (configsSize == 1) {
            layoutParams.leftMargin = 0;
            int i3 = topConfigItem.gravity;
            if (i3 == 0) {
                i3 = 8388613;
            }
            layoutParams.gravity = i3;
            imageView.setLayoutParams(layoutParams);
            return 0;
        } else if (configsSize == 2) {
            if (i2 == 0) {
                layoutParams.leftMargin = 0;
                int i4 = topConfigItem.gravity;
                if (i4 == 0) {
                    i4 = 8388611;
                }
                layoutParams.gravity = i4;
            } else if (i2 == 1) {
                layoutParams.leftMargin = 0;
                int i5 = topConfigItem.gravity;
                if (i5 == 0) {
                    i5 = 8388613;
                }
                layoutParams.gravity = i5;
            }
            this.mSpacesItemWidth = (this.mTotalWidth - (this.mSupportedConfigs.getConfigsSize() * i)) / (configsSize - 1);
            imageView.setLayoutParams(layoutParams);
            return 0;
        } else if (i2 == 0) {
            layoutParams.leftMargin = 0;
            layoutParams.gravity = GravityCompat.START;
            imageView.setLayoutParams(layoutParams);
            return 0;
        } else {
            int i6 = configsSize - 1;
            if (i2 == i6) {
                layoutParams.leftMargin = 0;
                layoutParams.gravity = GravityCompat.END;
                imageView.setLayoutParams(layoutParams);
                return 0;
            }
            this.mSpacesItemWidth = (this.mTotalWidth - (this.mSupportedConfigs.getConfigsSize() * i)) / i6;
            return (this.mSpacesItemWidth * i2) + (i2 * i);
        }
    }

    private int[] getLightingResources() {
        return new int[]{R.drawable.ic_new_lighting_off, R.drawable.ic_new_lighting_on};
    }

    private int[] getLiveShotResources() {
        return new int[]{R.drawable.ic_motionphoto, R.drawable.ic_motionphoto_shadow};
    }

    private int[] getMoreResources() {
        return new int[]{R.drawable.ic_new_more, R.drawable.ic_new_more_shadow};
    }

    private int getPortraitResources() {
        return R.drawable.ic_new_portrait_button_normal;
    }

    private int getSettingResources() {
        return R.drawable.ic_new_config_setting;
    }

    private int[] getSuperEISResources() {
        return new int[]{R.drawable.ic_config_super_eis_off, R.drawable.ic_config_super_eis_on};
    }

    private int[] getSuperMacroResources() {
        return new int[]{R.drawable.ic_config_super_macro_mode, R.drawable.ic_config_super_macro_mode_shadow};
    }

    private FragmentTopAlert getTopAlert() {
        FragmentTopAlert fragmentTopAlert = this.mFragmentTopAlert;
        if (fragmentTopAlert == null) {
            Log.d(TAG, "getTopAlert(): fragment is null");
            return null;
        } else if (fragmentTopAlert.isAdded()) {
            return this.mFragmentTopAlert;
        } else {
            Log.d(TAG, "getTopAlert(): fragment is not added yet");
            return null;
        }
    }

    private int[] getUltraPixelPortraitResources() {
        return new int[]{R.drawable.ic_config_ultrapixelportrait_off, R.drawable.ic_config_ultrapixelportrait_off};
    }

    private int[] getUltraWideBokehResources() {
        return new int[]{R.drawable.ic_ultra_wide_bokeh, R.drawable.ic_ultra_wide_bokeh_shadow};
    }

    private int[] getVideo8KRecource() {
        return new int[]{R.drawable.ic_config_video_8k_normal, R.drawable.ic_config_video_8k_highlight};
    }

    private int[] getVideoBokehResources() {
        return new int[]{R.drawable.ic_new_portrait_button_normal, R.drawable.ic_new_portrait_button_on};
    }

    private void initExtraMenu() {
        int currentCameraId = DataRepository.dataItemGlobal().getCurrentCameraId();
        SupportedConfigs supportedExtraConfigs = SupportedConfigFactory.getSupportedExtraConfigs(this.mCurrentMode, currentCameraId, Camera2DataContainer.getInstance().getCapabilitiesByBogusCameraId(currentCameraId, this.mCurrentMode), DataRepository.dataItemGlobal().isNormalIntent());
        final int integer = getResources().getInteger(R.integer.back_top_extra_column_count);
        int integer2 = getResources().getInteger(R.integer.front_top_extra_column_count);
        if (DataRepository.dataItemGlobal().getDisplayMode() == 2) {
            integer = integer2;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), integer);
        this.mExtraAdapter = new ExtraAdapter(getContext(), supportedExtraConfigs, this, this);
        this.mTopExtraMenu.getRecycledViewPool().setMaxRecycledViews(2, this.mExtraAdapter.getItemCount(2));
        this.mTopExtraMenu.getRecycledViewPool().setMaxRecycledViews(1, this.mExtraAdapter.getItemCount(1));
        ((SimpleItemAnimator) this.mTopExtraMenu.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mTopExtraMenu.setAdapter(this.mExtraAdapter);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            /* class com.android.camera.fragment.top.FragmentTopConfig.AnonymousClass1 */

            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i) {
                if (FragmentTopConfig.this.mExtraAdapter.getItemViewType(i) == 1) {
                    return integer;
                }
                return 1;
            }
        });
        this.mTopExtraMenu.setLayoutManager(gridLayoutManager);
        this.mTopExtraMenu.setFocusable(false);
        this.mTopExtraMenuHeight = getResources().getDimensionPixelSize(R.dimen.top_config_extra_recyclerview_marginBottom) + getResources().getDimensionPixelSize(R.dimen.top_config_extra_recyclerview_margintop) + (this.mExtraAdapter.getTotalRow(1) * getResources().getDimensionPixelSize(R.dimen.top_config_extra_mulit_item_height)) + (this.mExtraAdapter.getTotalRow(2) * getResources().getDimensionPixelSize(R.dimen.top_config_extra_toggle_item_height));
        this.mExtraAdapter.setAnimateHeight(this.mTopExtraMenuHeight - this.mTopConfigMenuHeight);
    }

    private void initExtraTimerBurstMenu() {
        if (!this.mIsShowExtraMenu) {
            this.mTopExtraMenu.setVisibility(8);
            this.mLlTimerMenu.setVisibility(8);
            return;
        }
        boolean z = this.mIsShowExtraTimerMenu;
        if (z) {
            this.mTopExtraMenu.setVisibility(8);
            this.mLlTimerMenu.setVisibility(0);
        } else if (!z) {
            this.mTopExtraMenu.setVisibility(0);
            this.mLlTimerMenu.setVisibility(8);
        }
    }

    private void initSnapNumAnimator() {
        this.mZoomInAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.zoom_button_zoom_in);
        this.mZoomInAnimator.setTarget(this.mMultiSnapNum);
        this.mZoomInAnimator.setInterpolator(new QuadraticEaseInOutInterpolator());
        this.mZoomOutAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.zoom_button_zoom_out);
        this.mZoomOutAnimator.setTarget(this.mMultiSnapNum);
        this.mZoomOutAnimator.setInterpolator(new QuadraticEaseInOutInterpolator());
    }

    private void initTopView() {
        ColorImageView colorImageView = (ColorImageView) this.mTopConfigMenu.findViewById(R.id.top_config_00);
        ColorImageView colorImageView2 = (ColorImageView) this.mTopConfigMenu.findViewById(R.id.top_config_01);
        ColorImageView colorImageView3 = (ColorImageView) this.mTopConfigMenu.findViewById(R.id.top_config_02);
        ColorImageView colorImageView4 = (ColorImageView) this.mTopConfigMenu.findViewById(R.id.top_config_03);
        ColorImageView colorImageView5 = (ColorImageView) this.mTopConfigMenu.findViewById(R.id.top_config_04);
        ColorImageView colorImageView6 = (ColorImageView) this.mTopConfigMenu.findViewById(R.id.top_config_05);
        ColorImageView colorImageView7 = (ColorImageView) this.mTopConfigMenu.findViewById(R.id.top_config_06);
        ColorImageView colorImageView8 = (ColorImageView) this.mTopConfigMenu.findViewById(R.id.top_config_07);
        ColorImageView colorImageView9 = (ColorImageView) this.mTopConfigMenu.findViewById(R.id.top_config_08);
        ColorImageView colorImageView10 = (ColorImageView) this.mTopConfigMenu.findViewById(R.id.top_config_09);
        ColorImageView colorImageView11 = (ColorImageView) this.mTopConfigMenu.findViewById(R.id.top_config_10);
        colorImageView.setOnClickListener(this);
        colorImageView2.setOnClickListener(this);
        colorImageView3.setOnClickListener(this);
        colorImageView4.setOnClickListener(this);
        colorImageView5.setOnClickListener(this);
        colorImageView6.setOnClickListener(this);
        colorImageView7.setOnClickListener(this);
        colorImageView8.setOnClickListener(this);
        colorImageView9.setOnClickListener(this);
        colorImageView10.setOnClickListener(this);
        colorImageView11.setOnClickListener(this);
        FolmeUtils.touchTint(colorImageView, colorImageView2, colorImageView3, colorImageView4, colorImageView5, colorImageView6, colorImageView7, colorImageView8, colorImageView9, colorImageView10, colorImageView11);
        this.mConfigViews = new ArrayList();
        this.mConfigViews.add(colorImageView);
        this.mConfigViews.add(colorImageView2);
        this.mConfigViews.add(colorImageView3);
        this.mConfigViews.add(colorImageView4);
        this.mConfigViews.add(colorImageView5);
        this.mConfigViews.add(colorImageView6);
        this.mConfigViews.add(colorImageView7);
        this.mConfigViews.add(colorImageView8);
        this.mConfigViews.add(colorImageView9);
        this.mConfigViews.add(colorImageView10);
        this.mConfigViews.add(colorImageView11);
    }

    private void notifyExtraMenuVisibilityChange(boolean z) {
        ModeProtocol.TopConfigProtocol topConfigProtocol = (ModeProtocol.TopConfigProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(193);
        if (topConfigProtocol != null) {
            topConfigProtocol.onExtraMenuVisibilityChange(z);
        }
    }

    private void onClickByExtraMenu(View view, ModeProtocol.ConfigChanges configChanges) {
        int intValue = ((Integer) view.getTag()).intValue();
        if (intValue == 170 && view.getId() == R.id.rl_item_top_config_description) {
            showExtraTimerBurstMenu();
            CameraSettings.setTimerBurstEnable(false);
            configChanges.onConfigChanged(170);
            return;
        }
        configChanges.onConfigChanged(intValue);
        if (Util.isAccessible()) {
            this.mExtraAdapter.setOnClictTag(intValue);
        }
        if (intValue == 225) {
            removeExtraMenu(5);
        } else if (intValue != 242) {
            this.mExtraAdapter.notifyDataSetChanged();
        } else {
            hideExtraMenu();
            ((ModeProtocol.TopConfigProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(193)).startAiLens();
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.AlgoAttr.VAULE_START_AI_DETECT_BUTTON);
            MistatsWrapper.mistatEvent(MistatsConstants.GoogleLens.KEY_GOOGLE_LENS, hashMap);
        }
    }

    private void reConfigTipOfFlash(boolean z) {
        if (!isExtraMenuShowing() && this.mCurrentMode != 182) {
            ComponentConfigFlash componentFlash = DataRepository.dataItemConfig().getComponentFlash();
            FragmentTopAlert topAlert = getTopAlert();
            if (!componentFlash.isEmpty()) {
                String componentValue = componentFlash.getComponentValue(this.mCurrentMode);
                if ("1".equals(componentValue) || ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_ON.equals(componentValue)) {
                    alertFlash(0, "1", false, z);
                } else if ("2".equals(componentValue)) {
                    alertFlash(0, "2", false, z);
                } else if ("5".equals(componentValue)) {
                    alertFlash(0, "5", false, z);
                } else if (topAlert != null && !topAlert.isFlashShowing()) {
                    alertFlash(8, "1", false, z);
                }
            }
        }
    }

    private void reConfigTipOfHdr(boolean z) {
        ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
        if (!componentHdr.isEmpty()) {
            String componentValue = componentHdr.getComponentValue(this.mCurrentMode);
            if ("on".equals(componentValue) || "normal".equals(componentValue)) {
                alertHDR(0, false, false, z);
            } else if ("live".equals(componentValue)) {
                alertHDR(0, true, false, z);
            } else if (isHDRShowing()) {
                alertHDR(8, false, false, z);
            }
        }
    }

    private void reConfigTipOfMusicHint(boolean z) {
        int i = this.mCurrentMode;
        if (i != 174 && i != 183) {
            alertTopMusicHint(8, null);
        } else if (!isExtraMenuShowing()) {
            String[] currentLiveMusic = CameraSettings.getCurrentLiveMusic();
            if (!currentLiveMusic[1].isEmpty()) {
                alertTopMusicHint(0, currentLiveMusic[1]);
            }
        }
    }

    private void reConfigTipOfSubtitle() {
        if (DataRepository.dataItemRunning().getComponentRunningSubtitle().isSwitchOn(this.mCurrentMode)) {
            alertSubtitleHint(0, R.string.pref_video_subtitle);
        } else {
            alertSubtitleHint(8, R.string.pref_video_subtitle);
        }
    }

    private void reConfigTipOfSuperNightSe() {
        if (!isExtraMenuShowing()) {
            alertSuperNightSeTip(CameraSettings.isSuperNightOn() ? 0 : 8);
        }
    }

    private void resetImages() {
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        this.mSupportedConfigs = SupportedConfigFactory.getSupportedTopConfigs(this.mCurrentMode, DataRepository.dataItemGlobal().getCurrentCameraId(), DataRepository.dataItemGlobal().isNormalIntent());
        for (int i = 0; i < this.mConfigViews.size(); i++) {
            ImageView imageView = this.mConfigViews.get(i);
            imageView.setEnabled(true);
            imageView.setColorFilter((ColorFilter) null);
            TopConfigItem configItem = this.mSupportedConfigs.getConfigItem(i);
            boolean topImageResource = setTopImageResource(configItem, imageView, this.mCurrentMode, dataItemConfig, false);
            TopConfigItem topConfigItem = (TopConfigItem) imageView.getTag();
            if (topConfigItem == null || topConfigItem.configItem != configItem.configItem) {
                imageView.setTag(configItem);
                imageView.clearAnimation();
                imageView.setVisibility(0);
                if (topImageResource) {
                    ViewCompat.setAlpha(imageView, 0.0f);
                    ViewCompat.animate(imageView).alpha(1.0f).setDuration(150).setStartDelay(150).start();
                } else {
                    imageView.setVisibility(4);
                }
            } else {
                imageView.setTag(configItem);
            }
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    @SuppressLint({"ResourceType"})
    private boolean setTopImageResource(TopConfigItem topConfigItem, ImageView imageView, int i, DataItemConfig dataItemConfig, boolean z) {
        boolean z2;
        CharSequence charSequence;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z3;
        int valueSelectedDrawableIgnoreClose;
        boolean z4;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        boolean z5;
        int i15;
        boolean equals;
        boolean z6;
        int i16 = topConfigItem.configItem;
        int i17 = R.string.autozoom_hint;
        switch (i16) {
            case 162:
                z3 = CameraSettings.isGifOn();
                i17 = R.string.mimoji_gif;
                i8 = this.mGifResource;
                i7 = R.drawable.ic_vector_new_config_gif_shadow;
                charSequence = null;
                z2 = true;
                i2 = z3;
                i4 = i8;
                i3 = i7;
                break;
            case 164:
                i4 = R.drawable.ic_config_clone_use_guide;
                charSequence = null;
                i3 = 0;
                i6 = i3;
                i17 = i6;
                i5 = i6;
                z2 = true;
                i2 = i5;
                break;
            case 165:
                ComponentRunningEisPro componentRunningEisPro = DataRepository.dataItemRunning().getComponentRunningEisPro();
                if (!componentRunningEisPro.isEmpty()) {
                    valueSelectedDrawableIgnoreClose = componentRunningEisPro.getValueSelectedDrawableIgnoreClose(i);
                    i17 = componentRunningEisPro.getValueSelectedStringIdIgnoreClose(i);
                    z4 = !TextUtils.equals("off", componentRunningEisPro.getComponentValue(i));
                    charSequence = null;
                    i3 = 0;
                    z2 = true;
                    i14 = valueSelectedDrawableIgnoreClose;
                    z5 = z4;
                    i4 = i14;
                    i2 = z5;
                    break;
                }
                charSequence = null;
                i4 = 0;
                i3 = 0;
                i6 = i3;
                i17 = i6;
                i5 = i6;
                z2 = true;
                i2 = i5;
                break;
            case 166:
                i4 = R.drawable.ic_ai_scene_id_card;
                i3 = R.drawable.ic_ai_scene_id_card_shadow;
                charSequence = null;
                i6 = 0;
                i17 = i6;
                i5 = i6;
                z2 = true;
                i2 = i5;
                break;
            case 168:
                ComponentRunningAiAudio componentRunningAiAudio = DataRepository.dataItemRunning().getComponentRunningAiAudio();
                if (!componentRunningAiAudio.isEmpty()) {
                    int valueSelectedDrawableIgnoreClose2 = componentRunningAiAudio.getValueSelectedDrawableIgnoreClose(i);
                    int valueSelectedStringIdIgnoreClose = componentRunningAiAudio.getValueSelectedStringIdIgnoreClose(i);
                    i9 = !TextUtils.equals("normal", componentRunningAiAudio.getComponentValue(i)) ? 1 : 0;
                    i4 = valueSelectedDrawableIgnoreClose2;
                    i17 = valueSelectedStringIdIgnoreClose;
                    charSequence = null;
                    i3 = 0;
                    i5 = i9;
                    z2 = true;
                    i2 = i5;
                    break;
                }
                charSequence = null;
                i4 = 0;
                i3 = 0;
                i6 = i3;
                i17 = i6;
                i5 = i6;
                z2 = true;
                i2 = i5;
            case 171:
                z3 = DataRepository.dataItemRunning().getAi108Running();
                i8 = R.drawable.ic_ultra_pixel_photography_108mp;
                i7 = R.drawable.ic_ultra_pixel_photography_108mp_shadow;
                charSequence = null;
                i17 = 0;
                z2 = true;
                i2 = z3;
                i4 = i8;
                i3 = i7;
                break;
            case 176:
                return false;
            case 177:
                i4 = R.drawable.ic_new_config_flash_off;
                charSequence = null;
                i3 = 0;
                i6 = i3;
                i17 = i6;
                i5 = i6;
                z2 = true;
                i2 = i5;
                break;
            case 193:
                ComponentConfigFlash componentFlash = dataItemConfig.getComponentFlash();
                if (!componentFlash.isEmpty()) {
                    i12 = ("1".equals(componentFlash.getComponentValue(i)) || "2".equals(componentFlash.getComponentValue(i)) || ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_ON.equals(componentFlash.getComponentValue(i)) || "5".equals(componentFlash.getComponentValue(i))) ? 1 : 0;
                    i11 = componentFlash.getValueSelectedDrawableIgnoreClose(i);
                    i10 = componentFlash.getValueSelectedShadowDrawable(i);
                    i13 = componentFlash.getValueSelectedStringIdIgnoreClose(i);
                    if (!z) {
                        reConfigTipOfFlash(true);
                    } else if (z && this.mCurrentMode == 167) {
                        reConfigTipOfFlash(true);
                    }
                } else {
                    i13 = 0;
                    i12 = 0;
                    i11 = 0;
                    i10 = 0;
                }
                topConfigItem.enable = !componentFlash.isDisabled(this.mCurrentMode);
                i3 = i10;
                charSequence = null;
                z2 = true;
                i14 = i11;
                i17 = i13;
                z5 = i12;
                i4 = i14;
                i2 = z5;
                break;
            case 194:
                ComponentConfigHdr componentHdr = dataItemConfig.getComponentHdr();
                if (!componentHdr.isEmpty()) {
                    ModeProtocol.TopConfigProtocol topConfigProtocol = (ModeProtocol.TopConfigProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(193);
                    i12 = ("on".equals(componentHdr.getComponentValue(i)) || "normal".equals(componentHdr.getComponentValue(i)) || ("auto".equals(componentHdr.getComponentValue(i)) && topConfigProtocol != null && topConfigProtocol.getAutoHDRTargetState())) ? 1 : 0;
                    i11 = componentHdr.getValueSelectedDrawableIgnoreClose(i);
                    i10 = componentHdr.getValueSelectedShadowDrawable(i);
                    i13 = componentHdr.getValueSelectedStringIdIgnoreClose(i);
                    i3 = i10;
                    charSequence = null;
                    z2 = true;
                    i14 = i11;
                    i17 = i13;
                    z5 = i12;
                    i4 = i14;
                    i2 = z5;
                    break;
                }
                charSequence = null;
                i4 = 0;
                i3 = 0;
                i6 = i3;
                i17 = i6;
                i5 = i6;
                z2 = true;
                i2 = i5;
                break;
            case 195:
                i4 = getPortraitResources();
                i17 = R.string.accessibility_protrait;
                charSequence = null;
                i3 = 0;
                i5 = 0;
                z2 = true;
                i2 = i5;
                break;
            case 197:
                int[] iArr = this.mMoreResources;
                int i18 = iArr[0];
                int i19 = iArr[1];
                i17 = R.string.accessibility_more;
                charSequence = null;
                z5 = 0;
                z2 = true;
                i14 = i18;
                i3 = i19;
                i4 = i14;
                i2 = z5;
                break;
            case 200:
                String componentValue = dataItemConfig.getComponentBokeh().getComponentValue(i);
                i15 = "on".equals(componentValue) ? R.drawable.ic_portrait_button_on : R.drawable.ic_portrait_button_normal;
                i17 = "on".equals(componentValue) ? R.string.accessibility_bokeh_on : R.string.accessibility_bokeh_normal;
                equals = "on".equals(componentValue);
                int i20 = equals ? 1 : 0;
                boolean z7 = equals ? 1 : 0;
                boolean z8 = equals ? 1 : 0;
                boolean z9 = equals ? 1 : 0;
                boolean z10 = equals ? 1 : 0;
                boolean z11 = equals ? 1 : 0;
                boolean z12 = equals ? 1 : 0;
                boolean z13 = equals ? 1 : 0;
                boolean z14 = equals ? 1 : 0;
                boolean z15 = equals ? 1 : 0;
                boolean z16 = equals ? 1 : 0;
                i9 = i20;
                i4 = i15;
                charSequence = null;
                i3 = 0;
                i5 = i9;
                z2 = true;
                i2 = i5;
                break;
            case 201:
                boolean aiSceneOpen = CameraSettings.getAiSceneOpen(i);
                int[] iArr2 = this.mAiSceneResources;
                int i21 = iArr2[0];
                int i22 = iArr2[1];
                int i23 = CameraSettings.getAiSceneOpen(i) ? R.string.accessibility_ai_scene_on : R.string.accessibility_ai_scene_off;
                configBottomPopupTips(false);
                charSequence = null;
                z2 = true;
                i17 = i23;
                i4 = i21;
                i2 = aiSceneOpen ? 1 : 0;
                i3 = i22;
                break;
            case 205:
                ComponentConfigUltraWide componentConfigUltraWide = dataItemConfig.getComponentConfigUltraWide();
                if (!componentConfigUltraWide.isEmpty()) {
                    int valueSelectedDrawableIgnoreClose3 = componentConfigUltraWide.getValueSelectedDrawableIgnoreClose(i);
                    i17 = componentConfigUltraWide.getValueSelectedStringIdIgnoreClose(i);
                    i4 = valueSelectedDrawableIgnoreClose3;
                    charSequence = null;
                    i3 = 0;
                    i2 = 1;
                    z2 = true;
                    break;
                }
                charSequence = null;
                i4 = 0;
                i3 = 0;
                i6 = i3;
                i17 = i6;
                i5 = i6;
                z2 = true;
                i2 = i5;
                break;
            case 206:
                z4 = CameraSettings.isLiveShotOn();
                int[] iArr3 = this.mLiveShotResource;
                valueSelectedDrawableIgnoreClose = iArr3[0];
                i3 = iArr3[1];
                i17 = z4 ? R.string.accessibility_camera_liveshot_on : R.string.accessibility_camera_liveshot_off;
                charSequence = null;
                z2 = true;
                i14 = valueSelectedDrawableIgnoreClose;
                z5 = z4;
                i4 = i14;
                i2 = z5;
                break;
            case 207:
                z4 = DataRepository.dataItemRunning().isSwitchOn("pref_ultra_wide_bokeh_enabled");
                int[] iArr4 = this.mUltraWideBokehResources;
                valueSelectedDrawableIgnoreClose = iArr4[0];
                i3 = iArr4[1];
                i17 = z4 ? R.string.accessibility_camera_ultra_wide_bokeh_on : R.string.accessibility_camera_ultra_wide_bokeh_off;
                charSequence = null;
                z2 = true;
                i14 = valueSelectedDrawableIgnoreClose;
                z5 = z4;
                i4 = i14;
                i2 = z5;
                break;
            case 209:
                z4 = CameraSettings.isUltraPixelOn();
                int[] iArr5 = this.mUltraPixelPhotographyIconResources;
                valueSelectedDrawableIgnoreClose = iArr5[0];
                i3 = iArr5[1];
                charSequence = z4 ? this.mUltraPixelPhotographyTipString[1] : this.mUltraPixelPhotographyTipString[0];
                i17 = 0;
                z2 = true;
                i14 = valueSelectedDrawableIgnoreClose;
                z5 = z4;
                i4 = i14;
                i2 = z5;
                break;
            case 212:
                ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
                i17 = R.string.accessibility_filter_open_panel;
                valueSelectedDrawableIgnoreClose = componentRunningShine.getTopConfigEntryRes(i);
                int topConfigEntryShadowRes = componentRunningShine.getTopConfigEntryShadowRes(valueSelectedDrawableIgnoreClose);
                z2 = !componentRunningShine.isTopFilterEntry();
                z4 = componentRunningShine.determineStatus(i);
                i3 = topConfigEntryShadowRes;
                charSequence = null;
                i14 = valueSelectedDrawableIgnoreClose;
                z5 = z4;
                i4 = i14;
                i2 = z5;
                break;
            case 214:
                ComponentConfigMeter componentConfigMeter = dataItemConfig.getComponentConfigMeter();
                if (!componentConfigMeter.isEmpty()) {
                    int valueSelectedDrawableIgnoreClose4 = componentConfigMeter.getValueSelectedDrawableIgnoreClose(i);
                    int valueSelectedShadowDrawableId = componentConfigMeter.getValueSelectedShadowDrawableId(i);
                    int valueSelectedStringIdIgnoreClose2 = componentConfigMeter.getValueSelectedStringIdIgnoreClose(i);
                    i3 = valueSelectedShadowDrawableId;
                    charSequence = null;
                    z2 = true;
                    i17 = valueSelectedStringIdIgnoreClose2;
                    i4 = valueSelectedDrawableIgnoreClose4;
                    i2 = 0;
                    break;
                }
                charSequence = null;
                i4 = 0;
                i3 = 0;
                i6 = i3;
                i17 = i6;
                i5 = i6;
                z2 = true;
                i2 = i5;
                break;
            case 215:
                equals = CameraSettings.isUltraPixelPortraitFrontOn();
                i15 = CameraSettings.isUltraPixelPortraitFrontOn() ? this.mUltraPixelPortraitResources[1] : this.mUltraPixelPortraitResources[0];
                i17 = R.string.ultra_pixel_portrait_hint;
                int i202 = equals ? 1 : 0;
                boolean z72 = equals ? 1 : 0;
                boolean z82 = equals ? 1 : 0;
                boolean z92 = equals ? 1 : 0;
                boolean z102 = equals ? 1 : 0;
                boolean z112 = equals ? 1 : 0;
                boolean z122 = equals ? 1 : 0;
                boolean z132 = equals ? 1 : 0;
                boolean z142 = equals ? 1 : 0;
                boolean z152 = equals ? 1 : 0;
                boolean z162 = equals ? 1 : 0;
                i9 = i202;
                i4 = i15;
                charSequence = null;
                i3 = 0;
                i5 = i9;
                z2 = true;
                i2 = i5;
                break;
            case 217:
                i4 = this.mCurrentMode == 204 ? R.drawable.ic_dual_video_black_top_config : R.drawable.ic_back;
                charSequence = null;
                i3 = 0;
                i6 = i3;
                i17 = i6;
                i5 = i6;
                z2 = true;
                i2 = i5;
                break;
            case 218:
                i15 = CameraSettings.isSuperEISEnabled(i) ? this.mSuperEISResources[1] : this.mSuperEISResources[0];
                i17 = R.string.super_eis;
                equals = CameraSettings.isSuperEISEnabled(i);
                int i2022 = equals ? 1 : 0;
                boolean z722 = equals ? 1 : 0;
                boolean z822 = equals ? 1 : 0;
                boolean z922 = equals ? 1 : 0;
                boolean z1022 = equals ? 1 : 0;
                boolean z1122 = equals ? 1 : 0;
                boolean z1222 = equals ? 1 : 0;
                boolean z1322 = equals ? 1 : 0;
                boolean z1422 = equals ? 1 : 0;
                boolean z1522 = equals ? 1 : 0;
                boolean z1622 = equals ? 1 : 0;
                i9 = i2022;
                i4 = i15;
                charSequence = null;
                i3 = 0;
                i5 = i9;
                z2 = true;
                i2 = i5;
                break;
            case 220:
                z6 = CameraSettings.isSubtitleEnabled(i);
                i4 = CameraSettings.isSubtitleEnabled(i) ? this.mAutoZoomResources[1] : this.mAutoZoomResources[0];
                i17 = R.string.pref_video_subtitle;
                if (!z) {
                    reConfigTipOfSubtitle();
                }
                int i24 = z6 ? 1 : 0;
                boolean z17 = z6 ? 1 : 0;
                boolean z18 = z6 ? 1 : 0;
                boolean z19 = z6 ? 1 : 0;
                i9 = i24;
                charSequence = null;
                i3 = 0;
                i5 = i9;
                z2 = true;
                i2 = i5;
                break;
            case 221:
                z6 = CameraSettings.isDocumentModeOn(i);
                i4 = CameraSettings.isDocumentModeOn(i) ? this.mDocumentResources[1] : this.mDocumentResources[0];
                i17 = R.string.pref_document_mode;
                int i242 = z6 ? 1 : 0;
                boolean z172 = z6 ? 1 : 0;
                boolean z182 = z6 ? 1 : 0;
                boolean z192 = z6 ? 1 : 0;
                i9 = i242;
                charSequence = null;
                i3 = 0;
                i5 = i9;
                z2 = true;
                i2 = i5;
                break;
            case 225:
                i4 = getSettingResources();
                i17 = R.string.accessibility_setting;
                charSequence = null;
                i3 = 0;
                i5 = 0;
                z2 = true;
                i2 = i5;
                break;
            case 227:
                ComponentRunningColorEnhance componentRunningColorEnhance = DataRepository.dataItemRunning().getComponentRunningColorEnhance();
                int resIcon = componentRunningColorEnhance.getResIcon(componentRunningColorEnhance.isEnabled(this.mCurrentMode));
                i5 = componentRunningColorEnhance.isEnabled(this.mCurrentMode);
                i4 = resIcon;
                charSequence = null;
                i3 = 0;
                i17 = 0;
                z2 = true;
                i2 = i5;
                break;
            case 239:
                equals = i != 174 ? CameraSettings.isFaceBeautyOn(this.mCurrentMode, null) : CameraSettings.isLiveBeautyOpen();
                i15 = R.drawable.ic_beauty_off;
                i17 = R.string.accessibility_beauty_panel_open;
                int i20222 = equals ? 1 : 0;
                boolean z7222 = equals ? 1 : 0;
                boolean z8222 = equals ? 1 : 0;
                boolean z9222 = equals ? 1 : 0;
                boolean z10222 = equals ? 1 : 0;
                boolean z11222 = equals ? 1 : 0;
                boolean z12222 = equals ? 1 : 0;
                boolean z13222 = equals ? 1 : 0;
                boolean z14222 = equals ? 1 : 0;
                boolean z15222 = equals ? 1 : 0;
                boolean z16222 = equals ? 1 : 0;
                i9 = i20222;
                i4 = i15;
                charSequence = null;
                i3 = 0;
                i5 = i9;
                z2 = true;
                i2 = i5;
                break;
            case 242:
                if (Util.isGlobalVersion()) {
                    i4 = R.drawable.ic_config_ai_glens_outer;
                    i3 = R.drawable.ic_config_ai_glens_outer_shadow;
                    i17 = R.string.pref_google_lens;
                    charSequence = null;
                    i5 = 0;
                    z2 = true;
                    i2 = i5;
                    break;
                } else {
                    i4 = R.drawable.ic_config_ai_detect_unselected;
                    i17 = R.string.pref_ai_detect;
                    charSequence = null;
                    i3 = 0;
                    i5 = 0;
                    z2 = true;
                    i2 = i5;
                }
            case 243:
                equals = CameraSettings.isVideoBokehOn();
                Log.d(TAG, "setTopImageResource: VIDEO_BOKEH isSwitchOn = " + equals);
                i15 = equals ? this.mVideoBokehResource[1] : this.mVideoBokehResource[0];
                i17 = equals ? R.string.pref_camera_video_bokeh_on : R.string.pref_camera_video_bokeh_off;
                int i202222 = equals ? 1 : 0;
                boolean z72222 = equals ? 1 : 0;
                boolean z82222 = equals ? 1 : 0;
                boolean z92222 = equals ? 1 : 0;
                boolean z102222 = equals ? 1 : 0;
                boolean z112222 = equals ? 1 : 0;
                boolean z122222 = equals ? 1 : 0;
                boolean z132222 = equals ? 1 : 0;
                boolean z142222 = equals ? 1 : 0;
                boolean z152222 = equals ? 1 : 0;
                boolean z162222 = equals ? 1 : 0;
                i9 = i202222;
                i4 = i15;
                charSequence = null;
                i3 = 0;
                i5 = i9;
                z2 = true;
                i2 = i5;
                break;
            case 245:
                if (CameraSettings.getCurrentLiveMusic()[1].isEmpty()) {
                    i4 = R.drawable.ic_vector_live_music;
                    i3 = R.drawable.ic_vector_live_music_bg;
                    charSequence = null;
                    i6 = 0;
                    i17 = i6;
                    i5 = i6;
                    z2 = true;
                    i2 = i5;
                    break;
                } else {
                    i4 = R.drawable.ic_vector_live_music;
                    i3 = R.drawable.ic_vector_live_music_bg;
                    charSequence = null;
                    i17 = 0;
                    i2 = 1;
                    z2 = true;
                    break;
                }
            case 251:
                boolean isCinematicAspectRatioEnabled = CameraSettings.isCinematicAspectRatioEnabled(i);
                int i25 = CameraSettings.isCinematicAspectRatioEnabled(i) ? this.mCinematicRatioResources[1] : this.mCinematicRatioResources[0];
                i17 = CameraSettings.getAiSceneOpen(i) ? R.string.accessibility_mimovie_on : R.string.accessibility_mimovie_off;
                i4 = i25;
                charSequence = null;
                z2 = true;
                i2 = isCinematicAspectRatioEnabled ? 1 : 0;
                i3 = 0;
                break;
            case 253:
                z6 = CameraSettings.isAutoZoomEnabled(i);
                i4 = CameraSettings.isAutoZoomEnabled(i) ? this.mAutoZoomResources[1] : this.mAutoZoomResources[0];
                int i2422 = z6 ? 1 : 0;
                boolean z1722 = z6 ? 1 : 0;
                boolean z1822 = z6 ? 1 : 0;
                boolean z1922 = z6 ? 1 : 0;
                i9 = i2422;
                charSequence = null;
                i3 = 0;
                i5 = i9;
                z2 = true;
                i2 = i5;
                break;
            case 255:
                z4 = CameraSettings.isMacroModeEnabled(i);
                int[] iArr6 = this.mSuperMacroResources;
                valueSelectedDrawableIgnoreClose = iArr6[0];
                i3 = iArr6[1];
                charSequence = null;
                z2 = true;
                i14 = valueSelectedDrawableIgnoreClose;
                z5 = z4;
                i4 = i14;
                i2 = z5;
                break;
            case 256:
                String componentValue2 = DataRepository.dataItemConfig().getComponentConfigVideoQuality().getComponentValue(this.mCurrentMode);
                equals = CameraSettings.isVideoQuality8KOpen(this.mCurrentMode) || ComponentConfigVideoQuality.QUALITY_8K.equalsIgnoreCase(componentValue2) || ComponentConfigVideoQuality.QUALITY_8K_24FPS.equalsIgnoreCase(componentValue2);
                i17 = equals ? R.string.accessibility_8k_on : R.string.accessibility_8k_off;
                i15 = this.mVideo8KResource[0];
                int i2022222 = equals ? 1 : 0;
                boolean z722222 = equals ? 1 : 0;
                boolean z822222 = equals ? 1 : 0;
                boolean z922222 = equals ? 1 : 0;
                boolean z1022222 = equals ? 1 : 0;
                boolean z1122222 = equals ? 1 : 0;
                boolean z1222222 = equals ? 1 : 0;
                boolean z1322222 = equals ? 1 : 0;
                boolean z1422222 = equals ? 1 : 0;
                boolean z1522222 = equals ? 1 : 0;
                boolean z1622222 = equals ? 1 : 0;
                i9 = i2022222;
                i4 = i15;
                charSequence = null;
                i3 = 0;
                i5 = i9;
                z2 = true;
                i2 = i5;
                break;
            default:
                charSequence = null;
                i4 = 0;
                i3 = 0;
                i6 = i3;
                i17 = i6;
                i5 = i6;
                z2 = true;
                i2 = i5;
                break;
        }
        if (i4 > 0) {
            Drawable drawable = getResources().getDrawable(i4);
            topConfigItem.margin = getInitialMargin(topConfigItem, imageView, drawable.getIntrinsicWidth());
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
            int i26 = topConfigItem.margin;
            if (i26 > 0) {
                layoutParams.gravity |= GravityCompat.START;
                if (this.mIsRTL) {
                    layoutParams.rightMargin = i26;
                } else {
                    layoutParams.leftMargin = i26;
                }
            } else {
                layoutParams.setMarginStart(0);
            }
            layoutParams.gravity |= 16;
            imageView.setLayoutParams(layoutParams);
            if (topConfigItem.configItem == 177) {
                imageView.setImageDrawable(null);
            } else {
                imageView.setImageDrawable(drawable);
            }
            if (i3 > 0) {
                imageView.setBackgroundResource(i3);
            } else {
                imageView.setBackground(null);
            }
            if (z2) {
                imageView.setColorFilter(i2 != 0 ? TintColor.tintColor() : -1);
            } else {
                imageView.setColorFilter(0);
            }
            if (topConfigItem.configItem == 193) {
                if (!topConfigItem.enable) {
                    imageView.setAlpha(0.4f);
                } else if (imageView.getAlpha() == 0.4f) {
                    imageView.setAlpha(1.0f);
                }
            }
            if (ModuleManager.isLandscapeMode()) {
                imageView.setRotation(90.0f);
            } else {
                imageView.setRotation((float) this.mDegree);
            }
            if (Util.isAccessible() || Util.isSetContentDesc()) {
                if (i17 > 0) {
                    imageView.setContentDescription(getString(i17));
                } else if (!TextUtils.isEmpty(charSequence)) {
                    imageView.setContentDescription(charSequence);
                }
            }
        }
        return true;
    }

    private void setTopTipMarin(View view) {
        long j;
        long round;
        int topHeight = Util.getTopHeight();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.capture_delay_number_text_size);
        int round2 = Math.round(((float) dimensionPixelSize) * 1.327f);
        int i = round2 - dimensionPixelSize;
        if (this.mCurrentMode == 165) {
            j = (long) topHeight;
            round = Math.round(((double) Util.sWindowWidth) * 0.0148d);
        } else if (this.mDisplayRectTopMargin == 0) {
            j = (long) topHeight;
            round = Math.round(((double) Util.sWindowWidth) * 0.0574d);
        } else {
            j = (long) topHeight;
            round = Math.round(((double) Util.sWindowWidth) * 0.0889d);
        }
        int i2 = ((int) (j + round)) - i;
        Log.d(TAG, "showDelayNumber: topMargin = " + i2 + ", topHeight = " + Util.getTopHeight() + ", fontHeight = " + dimensionPixelSize + ", viewHeight = " + round2 + ", offset = " + i);
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin = i2;
        int i3 = this.mDegree;
        if (i3 > 0) {
            ViewCompat.setRotation(view, (float) i3);
        }
    }

    private void showExtraMenu() {
        hideSwitchTip();
        hideAlert();
        this.mIsShowExtraMenu = true;
        initExtraMenu();
        animatorExtraMenu(true);
        notifyExtraMenuVisibilityChange(true);
    }

    private void showExtraTimerBurstMenu() {
        this.mIsShowExtraMenu = true;
        switchExtraTimerBurstMenu();
    }

    private void showTips(ModeProtocol.ConfigChanges configChanges, boolean z) {
        if (getTipsState(FragmentTopAlert.TIP_ULTRA_PIXEL)) {
            setTipsState(FragmentTopAlert.TIP_ULTRA_PIXEL, false);
            alertSwitchTip(FragmentTopAlert.TIP_ULTRA_PIXEL, 0, DataRepository.dataItemRunning().getComponentUltraPixel().getUltraPixelOpenTip());
        }
        configChanges.reCheckModuleUltraPixel();
        if (getTipsState(FragmentTopAlert.TIP_VIDEO_BEAUTIFY)) {
            setTipsState(FragmentTopAlert.TIP_VIDEO_BEAUTIFY, false);
            configChanges.reCheckVideoBeautify();
        }
        boolean z2 = true;
        if (getTipsState("ai_watermark")) {
            setTipsState("ai_watermark", false);
            configChanges.reCheckAIWatermark(true);
        }
        if (getTipsState(FragmentTopAlert.TIP_SUPER_EIS)) {
            setTipsState(FragmentTopAlert.TIP_SUPER_EIS, false);
            configChanges.reCheckSuperEIS();
        }
        if (getTipsState("super_eis_pro")) {
            setTipsState("super_eis_pro", false);
            configChanges.reCheckSuperEISPro();
        }
        if (getTipsState(FragmentTopAlert.TIP_LIVE_SHOT)) {
            setTipsState(FragmentTopAlert.TIP_LIVE_SHOT, false);
            configChanges.reCheckLiveShot();
        }
        if (!isExtraMenuShowing()) {
            configChanges.reCheckColorEnhance();
        }
        if (getTipsState(FragmentTopAlert.TIP_ULTRA_WIDE_BOKEH)) {
            setTipsState(FragmentTopAlert.TIP_ULTRA_WIDE_BOKEH, false);
            configChanges.reCheckUltraWideBokeh();
        }
        if (getTipsState(FragmentTopAlert.TIP_AI_AUDIO)) {
            setTipsState(FragmentTopAlert.TIP_AI_AUDIO, false);
            configChanges.reCheckAiAudio();
        }
        if (!isExtraMenuShowing()) {
            configChanges.reCheckEyeLight();
        }
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (this.mCurrentMode != 162 || cameraAction == null || !cameraAction.isRecording()) {
            z2 = false;
        }
        if (!isExtraMenuShowing() && !z2) {
            setTipsState("macro", false);
            configChanges.reCheckMacroMode();
        }
        if (!isExtraMenuShowing()) {
            setTipsState(FragmentTopAlert.TIP_TIMER_BURST, false);
            configChanges.reCheckTimerBurst();
        }
        if (!isExtraMenuShowing()) {
            configChanges.reCheckFrontBokenTip();
        }
        if (!isExtraMenuShowing()) {
            configChanges.reCheckHandGesture();
        }
        if (!isExtraMenuShowing()) {
            configChanges.reCheckSubtitleMode();
        }
        ModeProtocol.CameraModuleSpecial cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
        if (isExtraMenuShowing()) {
            return;
        }
        if (cameraModuleSpecial == null || !cameraModuleSpecial.isStartCountCapture()) {
            configChanges.reCheckTilt();
        }
    }

    private void switchExtraTimerBurstMenu() {
        this.mIsShowExtraTimerMenu = !this.mIsShowExtraTimerMenu;
        if (this.mIsShowExtraTimerMenu) {
            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_GOTO_TIMER_BURST_MENU, null, null);
            this.mTopExtraMenu.setVisibility(8);
            this.mLlTimerMenu.setVisibility(0);
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f, 1, 0.5f, 1, 1.0f);
            scaleAnimation.setDuration(300);
            scaleAnimation.setInterpolator(new CubicEaseOutInterpolator());
            this.mLlTimerMenu.startAnimation(scaleAnimation);
            int timerBurstInterval = CameraSettings.getTimerBurstInterval();
            if (this.mCurrentMode == 167) {
                this.mTvShotInterval.setText(getString(R.string.timer_burst_manu_param_interval));
            } else {
                this.mTvShotInterval.setText(getString(R.string.timer_burst_param_interval));
            }
            this.mCustomSeekBarInterval.initSeekBarConfig(TimerBurstController.timeInterval, timerBurstInterval, 1, "S", DataRepository.dataItemLive().getTimerBurstController());
            this.mCustomSeekBarCount.initSeekBarConfig(TimerBurstController.shotCount, CameraSettings.getTimerBurstTotalCount() / 10, 10, "", DataRepository.dataItemLive().getTimerBurstController());
            return;
        }
        this.mTopExtraMenu.setVisibility(0);
        this.mLlTimerMenu.setVisibility(8);
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.065f, 1.0f, 1.065f, 1.0f, 1, 0.5f, 1, 1.0f);
        scaleAnimation2.setDuration(300);
        scaleAnimation2.setInterpolator(new CubicEaseOutInterpolator());
        this.mTopExtraMenu.startAnimation(scaleAnimation2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertAiAudio(int i, @StringRes int i2) {
        if (Util.isWiredHeadsetOn()) {
            i = 8;
        }
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            boolean z = true;
            if (!this.mIsShowExtraMenu) {
                z = false;
            }
            topAlert.alertAiAudio(i, i2, z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertAiAudioBGHint(int i, int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertAiAudioBGHint(i, i2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertAiAudioMutexToastIfNeed(Context context, int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.alertAiAudioMutexToastIfNeed(context, i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertAiDetectTipHint(int i, int i2, long j) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertRecommendTipHint(i, i2, j);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertAiSceneSelector(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertAiSceneSelector(i);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertFastmotionIndicator(int i, String str, String str2, String str3, boolean z) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.alertFastmotionIndicator(i, str, str2, str3, z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertFastmotionValue(String str, String str2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertFastmotionTip(str, str2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertFlash(int i, String str, boolean z) {
        alertFlash(i, str, z, true);
    }

    public void alertFlash(int i, String str, boolean z, boolean z2) {
        ImageView topImage;
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            if (z2) {
                if (i != 0) {
                    reverseExpandTopBar(true);
                } else if (z && (topImage = getTopImage(193)) != null) {
                    topImage.performClick();
                }
            }
            topAlert.alertFlash(i, str);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertHDR(int i, boolean z, boolean z2) {
        alertHDR(i, z, z2, true);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertHandGestureHint(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertHandGestureHint(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertLightingTip(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertLightingTip(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertLiveShotHint(int i, int i2) {
        alertSwitchTip(FragmentTopAlert.TIP_LIVE_SHOT, i, i2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertMacroModeHint(int i, int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertMacroModeHint(i, i2, isTopConfig(255));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertMimojiFaceDetect(boolean z, int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertMimojiFaceDetect(z, i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertMoonModeSelector(int i, boolean z) {
        ModeProtocol.CameraModuleSpecial cameraModuleSpecial;
        if (isTopAlertShowing(getTopAlert())) {
            DataRepository.dataItemRunning().getComponentRunningMoon().setComponentValue(this.mCurrentMode, z ? ComponentRunningMoon.MOON : ComponentRunningMoon.NIGHT);
            boolean z2 = true;
            alertSlideSwitchLayout(i == 0, 246);
            if (this.mCurrentMode == 163 && (cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195)) != null) {
                if (i == 0) {
                    z2 = false;
                }
                cameraModuleSpecial.showOrHideChip(z2);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertMusicClose(boolean z) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.alertMusicClose(z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertParameterDescriptionTip(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.alertParameterDescriptionTip(i, true);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertParameterResetTip(boolean z, int i, @StringRes int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.alertParameterResetTip(z, i, i2, Util.getDisplayRect(0).top + getResources().getDimensionPixelSize(R.dimen.reset_manually_parameter_tip_margin_top), !(this.mIsShowExtraMenu ^ true));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertProColourHint(int i, int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertProColourHint(i, i2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertRecommendDescTip(String str, int i, int i2) {
        alertRecommendDescTip(str, i, i2, 3000);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertRecommendDescTip(String str, int i, int i2, long j) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertRecommendDescTip(str, i, i2, j);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertSlideSwitchLayout(boolean z, int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertSlideSwitchLayout(z, i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertSubtitleHint(int i, int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertSubtitleHint(i, i2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertSuperNightSeTip(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertSuperNightSeTip(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertSwitchTip(String str, int i, int i2) {
        alertSwitchTip(str, i, getString(i2));
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertSwitchTip(String str, int i, int i2, int i3, long j) {
        alertSwitchTip(str, i, i2, getString(i3), j);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertSwitchTip(String str, int i, int i2, String str2, long j) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertSwitchTip(str, i, i2, str2, j);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertSwitchTip(String str, int i, String str2) {
        alertSwitchTip(str, i, 1, str2, 3000);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertTimerBurstHint(int i, int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertTimerBurstHint(i, i2, isTopConfig(170));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertTopHint(int i, @StringRes int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertTopHint(i, i2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertTopHint(int i, int i2, long j) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertTopHint(i, i2, j);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertTopHint(int i, String str) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertTopHint(i, str);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertTopHint(int i, String str, long j) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertTopHint(i, str, j);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertUpdateValue(int i, String str) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.alertUpdateValue(i, str);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void alertVideoUltraClear(int i, @StringRes int i2) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            boolean z = true;
            if (!this.mIsShowExtraMenu) {
                z = false;
            }
            topAlert.alertVideoUltraClear(i, i2, z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void clearAlertStatus() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.clearAlertStatus();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void clearAllTipsState() {
        this.mTipsState.clear();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void clearFastmotionValue() {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.clearFastmotionTip();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void clearVideoUltraClear() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.clearVideoUltraClear();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean containShortDurationDescriptionTips(String str) {
        FragmentTopAlert topAlert = getTopAlert();
        if (!isTopAlertShowing(topAlert)) {
            return false;
        }
        return topAlert.containShortDurationDescriptionTips(str);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LyingDirectHint
    public void directHideLyingDirectHint() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void disableMenuItem(boolean z, int... iArr) {
        ImageView topImage;
        if (!(iArr == null || this.mDisabledFunctionMenu == null)) {
            for (int i : iArr) {
                this.mDisabledFunctionMenu.put(i, z);
                if (z && (topImage = getTopImage(i)) != null) {
                    AlphaOutOnSubscribe.directSetResult(topImage);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void enableMenuItem(boolean z, int... iArr) {
        ImageView topImage;
        SparseBooleanArray sparseBooleanArray = this.mDisabledFunctionMenu;
        if (!(sparseBooleanArray == null || sparseBooleanArray.size() == 0)) {
            for (int i : iArr) {
                this.mDisabledFunctionMenu.delete(i);
                if (z && (topImage = getTopImage(i)) != null) {
                    AlphaInOnSubscribe.directSetResult(topImage);
                }
            }
        }
    }

    @Override // com.android.camera.ui.SlideSwitchButton.SlideSwitchListener
    public boolean enableSwitch() {
        if (!isEnableClick()) {
            return false;
        }
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        return cameraAction == null || !cameraAction.isDoingAction();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean getAlertIsShow() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert == null) {
            return false;
        }
        return topAlert.isShow();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public int getCurrentAiSceneLevel() {
        return this.mCurrentAiSceneLevel;
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 244;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_top_config;
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean getTipsState(String str) {
        Boolean bool;
        return !isExtraMenuShowing() && (bool = this.mTipsState.get(str)) != null && bool.booleanValue();
    }

    @Override // com.android.camera.fragment.top.TopExpandAdapter.ExpandListener
    public ImageView getTopImage(int i) {
        for (ImageView imageView : this.mConfigViews) {
            TopConfigItem topConfigItem = (TopConfigItem) imageView.getTag();
            if (topConfigItem != null && topConfigItem.configItem == i) {
                return imageView;
            }
        }
        return null;
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public VideoTagView getVideoTag() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert == null) {
            return null;
        }
        return topAlert.getVideoTag();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void hideAlert() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.clear(true);
            topAlert.setShow(false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void hideConfigMenu() {
        Completable.create(new AlphaOutOnSubscribe(this.mTopConfigMenu)).subscribe();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void hideDelayNumber() {
        if (this.mCaptureDelayNumber.getVisibility() != 8) {
            this.mCaptureDelayNumber.setVisibility(8);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void hideExtraMenu() {
        onBackEvent(6);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void hideRecommendDescTip(String str) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.hideRecommendDescTip(str);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void hideSwitchTip() {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.hideSwitchTip();
        }
    }

    public /* synthetic */ void i(View view) {
        onBackEvent(6);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mMoreResources = getMoreResources();
        this.mAiSceneResources = getAiSceneResources();
        this.mCinematicRatioResources = getCinematicRatioResources();
        this.mAutoZoomResources = getAutoZoomResources();
        this.mUltraWideBokehResources = getUltraWideBokehResources();
        this.mUltraPixelPhotographyIconResources = ComponentRunningUltraPixel.getUltraPixelTopMenuResources();
        this.mUltraPixelPhotographyTipString = ComponentRunningUltraPixel.getUltraPixelSwitchTipsString();
        this.mLiveShotResource = getLiveShotResources();
        this.mLightingResource = getLightingResources();
        this.mVideoBokehResource = getVideoBokehResources();
        this.mSuperMacroResources = getSuperMacroResources();
        this.mUltraPixelPortraitResources = getUltraPixelPortraitResources();
        this.mSuperEISResources = getSuperEISResources();
        this.mVideo8KResource = getVideo8KRecource();
        this.mGifResource = getGifRecource();
        this.mDocumentResources = getDocumentResources();
        this.mIsRTL = Util.isLayoutRTL(getContext());
        this.mTopBarAnimationComponent = new TopBarAnimationComponent();
        this.mMultiSnapNum = (TextView) view.findViewById(R.id.v6_multi_snap_number);
        this.mCaptureDelayNumber = (TextView) view.findViewById(R.id.v6_capture_delay_number);
        this.mDisabledFunctionMenu = new SparseBooleanArray(1);
        this.mTopBackgroundView = (ShapeBackGroundView) view.findViewById(R.id.top_config_background);
        this.mTopConfigViewGroup = (ViewGroup) view.findViewById(R.id.top_config);
        this.mTopExtraMenu = (RecyclerView) view.findViewById(R.id.top_config_extra_recyclerview);
        this.mTopConfigMenu = view.findViewById(R.id.top_config_menu);
        this.mLlTimerMenu = (LinearLayout) view.findViewById(R.id.layout_burst_menu);
        this.mImageViewBack = (ImageView) view.findViewById(R.id.iv_timer_burst_back);
        FolmeUtils.touchTint(this.mImageViewBack);
        this.mImageViewBack.setOnClickListener(this);
        this.mTvShotInterval = (TextView) view.findViewById(R.id.tv_shot_interval);
        this.mCustomSeekBarInterval = (CustomSeekBar) view.findViewById(R.id.csb_interval);
        this.mCustomSeekBarCount = (CustomSeekBar) view.findViewById(R.id.csb_count);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mTopConfigMenu.getLayoutParams();
        marginLayoutParams.topMargin = Util.sTopMargin;
        marginLayoutParams.height = Util.sTopBarHeight;
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mTopConfigViewGroup.getLayoutParams();
        marginLayoutParams2.height = marginLayoutParams.topMargin + marginLayoutParams.height;
        this.mTopConfigMenuHeight = marginLayoutParams2.height;
        ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) this.mTopBackgroundView.getLayoutParams();
        this.mTopBackgroundHeight = Util.getTopCoverHeight();
        marginLayoutParams3.height = this.mTopBackgroundHeight;
        this.mTopBackgroundView.initHeight(marginLayoutParams3.height);
        this.mTopExpandView = (TopExpendView) view.findViewById(R.id.top_config_expand_view);
        this.mTotalWidth = Util.sWindowWidth - (getResources().getDimensionPixelSize(R.dimen.top_bar_margin) * 2);
        initTopView();
        if (((ActivityBase) getContext()).getCameraIntentManager().isFromScreenSlide().booleanValue()) {
            Util.startScreenSlideAlphaInAnimation(this.mTopConfigMenu);
        }
        provideAnimateElement(this.mCurrentMode, null, 2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void insertConfigItem(int i) {
        resetImages();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean isContainAlertLightingTip(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (!isTopAlertShowing(topAlert)) {
            return false;
        }
        return topAlert.isContainAlertRecommendTip(topAlert.parseLightingRes(i));
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean isContainAlertRecommendTip(int... iArr) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert == null) {
            return false;
        }
        return topAlert.isContainAlertRecommendTip(iArr);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean isCurrentRecommendTipText(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (!isTopAlertShowing(topAlert)) {
            return false;
        }
        return topAlert.isCurrentRecommendTipText(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean isExtraMenuShowing() {
        return this.mIsShowExtraMenu;
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean isHDRShowing() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert == null) {
            return false;
        }
        return topAlert.isHDRShowing();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean isShowBacklightSelector() {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert == null) {
            return false;
        }
        return topAlert.isShowBacklightSelector();
    }

    public boolean isTopAlertShowing(FragmentTopAlert fragmentTopAlert) {
        return fragmentTopAlert != null && fragmentTopAlert.isShow() && !isExtraMenuShowing();
    }

    public boolean isTopConfig(int i) {
        SupportedConfigs supportedConfigs = this.mSupportedConfigs;
        return supportedConfigs != null && supportedConfigs.getLength() > 0 && this.mSupportedConfigs.contains(i);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public boolean needViewClear() {
        return true;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        ExtraAdapter extraAdapter;
        super.notifyAfterFrameAvailable(i);
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.notifyAfterFrameAvailable(i);
        }
        int i2 = this.mCurrentMode;
        if (!(i2 == 254 || i2 == 209 || i2 == 210 || getTopAlert() == null || ((this.mCurrentMode == 177 && DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiCreate()) || (this.mCurrentMode == 184 && DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate())))) {
            clearVideoUltraClear();
            reConfigTipOfFlash(false);
            reConfigTipOfMusicHint(false);
            alertUpdateValue(0, null);
        }
        adjustViewBackground();
        if (this.mIsShowExtraMenu) {
            RecyclerView recyclerView = this.mTopExtraMenu;
            if (recyclerView != null && recyclerView.getAdapter() != null && (extraAdapter = this.mExtraAdapter) != null && !extraAdapter.animationRunning()) {
                int totalRow = this.mExtraAdapter.getTotalRow(1);
                if (totalRow != 0) {
                    this.mExtraAdapter.notifyItemRangeChanged(0, totalRow);
                }
            } else {
                return;
            }
        }
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (this.mCurrentMode != 254 && configChanges != null) {
            showTips(configChanges, false);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        int i3 = this.mCurrentMode;
        int i4 = 7;
        if (this.mResetType != 7) {
            i4 = 2;
        }
        provideAnimateElement(i3, null, i4);
        if (this.mFragmentTopAlert == null) {
            this.mFragmentTopAlert = new FragmentTopAlert();
            this.mFragmentTopAlert.setShow(!isExtraMenuShowing());
            this.mFragmentTopAlert.setDegree(this.mDegree);
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentTopAlert fragmentTopAlert = this.mFragmentTopAlert;
            FragmentUtils.addFragmentWithTag(childFragmentManager, (int) R.id.top_alert, fragmentTopAlert, fragmentTopAlert.getFragmentTag());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBeautyRecording
    public void onAngleChanged(float f) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        LinearLayout linearLayout;
        if (reverseExpandTopBar(i != 4)) {
            this.mFragmentTopAlert.showRecordingTime();
            return true;
        }
        boolean z = this.mIsShowExtraMenu || this.mIsShowExtraTimerMenu;
        RecyclerView recyclerView = this.mTopExtraMenu;
        if ((recyclerView == null || recyclerView.getVisibility() != 0) && ((linearLayout = this.mLlTimerMenu) == null || linearLayout.getVisibility() != 0)) {
            return false;
        }
        if (i == 1 || i == 2) {
            if (!this.mIsShowExtraMenu) {
                return false;
            }
            animatorExtraMenu(false);
        } else if (i == 4 || i == 6) {
            animatorExtraMenu(false);
        } else {
            if (i != 7) {
                directHiddenExtraMenu();
            }
            if (!(i == 4 || i == 7)) {
                reInitAlert(true);
            }
            this.mIsShowExtraMenu = z;
            if (!this.mIsShowExtraMenu && i != 3) {
                notifyExtraMenuVisibilityChange(false);
                this.mIsShowExtraTimerMenu = false;
                initExtraTimerBurstMenu();
            }
            return true;
        }
        z = false;
        reInitAlert(true);
        this.mIsShowExtraMenu = z;
        notifyExtraMenuVisibilityChange(false);
        this.mIsShowExtraTimerMenu = false;
        initExtraTimerBurstMenu();
        return true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBeautyRecording
    public void onBeautyRecordingStart() {
        onBackEvent(5);
        ViewCompat.animate(this.mTopConfigMenu).alpha(0.0f).start();
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBeautyRecording
    public void onBeautyRecordingStop() {
        ViewCompat.animate(this.mTopConfigMenu).alpha(1.0f).start();
    }

    public void onClick(View view) {
        Log.d(TAG, "top config onclick");
        if (isEnableClick()) {
            if (view.getId() != R.id.iv_timer_burst_back) {
                ModeProtocol.MoreModePopupController moreModePopupController = (ModeProtocol.MoreModePopupController) ModeCoordinatorImpl.getInstance().getAttachProtocol(2561);
                if (moreModePopupController == null || !moreModePopupController.isExpanded()) {
                    ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                    if (configChanges != null) {
                        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
                        if (cameraAction != null && cameraAction.isDoingAction()) {
                            return;
                        }
                        if (CameraSettings.isFrontCamera() && ((Camera) getContext()).isScreenSlideOff()) {
                            return;
                        }
                        if (this.mIsShowExtraMenu) {
                            onClickByExtraMenu(view, configChanges);
                            return;
                        }
                        TopConfigItem topConfigItem = (TopConfigItem) view.getTag();
                        if (topConfigItem != null) {
                            if (this.mDisabledFunctionMenu.size() <= 0 || this.mDisabledFunctionMenu.indexOfKey(topConfigItem.configItem) < 0) {
                                ModeProtocol.CameraClickObservable cameraClickObservable = (ModeProtocol.CameraClickObservable) ModeCoordinatorImpl.getInstance().getAttachProtocol(227);
                                int i = topConfigItem.configItem;
                                if (i == 200) {
                                    DataItemConfig dataItemConfig = (DataItemConfig) DataRepository.provider().dataConfig();
                                    dataItemConfig.getComponentBokeh().toggle(this.mCurrentMode);
                                    String componentValue = dataItemConfig.getComponentBokeh().getComponentValue(this.mCurrentMode);
                                    CameraStatUtils.tarckBokenChanged(this.mCurrentMode, componentValue);
                                    updateConfigItem(200);
                                    if (dataItemConfig.reConfigHdrIfBokehChanged(this.mCurrentMode, componentValue)) {
                                        updateConfigItem(194);
                                    }
                                    configChanges.configBokeh(componentValue);
                                } else if (i == 201) {
                                    configChanges.onConfigChanged(201);
                                    if (cameraClickObservable != null) {
                                        cameraClickObservable.subscribe(166);
                                    }
                                } else if (i == 242) {
                                    ModeProtocol.TopConfigProtocol topConfigProtocol = (ModeProtocol.TopConfigProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(193);
                                    if (topConfigProtocol != null) {
                                        topConfigProtocol.startAiLens();
                                    }
                                    MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_AI_DETECT_CHANGED, null, null);
                                } else if (i == 243) {
                                    configChanges.onConfigChanged(243);
                                } else if (i == 255) {
                                    configChanges.onConfigChanged(255);
                                } else if (i != 256) {
                                    switch (i) {
                                        case 162:
                                            configChanges.onConfigChanged(162);
                                            return;
                                        case 168:
                                            ComponentData componentRunningAiAudio = DataRepository.dataItemRunning().getComponentRunningAiAudio();
                                            if (componentRunningAiAudio != null) {
                                                expandExtraView(componentRunningAiAudio, view, 168);
                                                return;
                                            }
                                            return;
                                        case 171:
                                            configChanges.onConfigChanged(171);
                                            return;
                                        case 203:
                                            configChanges.onConfigChanged(203);
                                            return;
                                        case 209:
                                            configChanges.onConfigChanged(209);
                                            return;
                                        case 212:
                                            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BeautyAttr.VALUE_BEAUTY_TOP_BUTTON, null, null);
                                            configChanges.onConfigChanged(212);
                                            return;
                                        case 220:
                                            configChanges.onConfigChanged(220);
                                            return;
                                        case 225:
                                            configChanges.showSetting();
                                            return;
                                        case 227:
                                            configChanges.onConfigChanged(227);
                                            return;
                                        case 239:
                                            configChanges.onConfigChanged(239);
                                            return;
                                        case 245:
                                            int i2 = this.mCurrentMode;
                                            if (i2 == 174) {
                                                CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_MUSIC_ICON_CLICK);
                                            } else if (i2 == 183) {
                                                CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_MUSIC);
                                            }
                                            Camera camera = (Camera) getActivity();
                                            Intent intent = new Intent(camera, LiveMusicActivity.class);
                                            intent.putExtra("StartActivityWhenLocked", camera.startFromKeyguard());
                                            camera.startActivity(intent);
                                            camera.setJumpFlag(8);
                                            return;
                                        case 251:
                                            configChanges.onConfigChanged(251);
                                            if (cameraClickObservable != null) {
                                                cameraClickObservable.subscribe(169);
                                                return;
                                            }
                                            return;
                                        case 253:
                                            configChanges.onConfigChanged(253);
                                            return;
                                        default:
                                            switch (i) {
                                                case 164:
                                                    if (this.mCurrentMode != 204) {
                                                        ModeProtocol.CloneAction cloneAction = (ModeProtocol.CloneAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(419);
                                                        if (cloneAction != null) {
                                                            cloneAction.onCloneGuideClicked();
                                                        }
                                                        configChanges.onConfigChanged(164);
                                                        return;
                                                    }
                                                    return;
                                                case 165:
                                                    ComponentData componentRunningEisPro = DataRepository.dataItemRunning().getComponentRunningEisPro();
                                                    if (componentRunningEisPro != null) {
                                                        expandExtraView(componentRunningEisPro, view, 165);
                                                        return;
                                                    }
                                                    return;
                                                case 166:
                                                    configChanges.onConfigChanged(166);
                                                    return;
                                                default:
                                                    switch (i) {
                                                        case 193:
                                                            ComponentConfigFlash componentFlash = ((DataItemConfig) DataRepository.provider().dataConfig()).getComponentFlash();
                                                            if (!componentFlash.isDisabled(this.mCurrentMode)) {
                                                                MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BaseEvent.FEATURE_NAME, MistatsConstants.FlashAttr.VALUE_FLASH_OUT_BUTTON, null);
                                                                if (componentFlash.disableUpdate()) {
                                                                    int disableReasonString = componentFlash.getDisableReasonString();
                                                                    if (disableReasonString != 0) {
                                                                        ToastUtils.showToast(getActivity(), disableReasonString);
                                                                    }
                                                                    Log.w(TAG, "ignore click flash for disable update");
                                                                } else if (this.mCurrentMode != 171 || !DataRepository.dataItemFeature().wj() || !CameraSettings.isBackCamera()) {
                                                                    expandExtraView(componentFlash, view, topConfigItem.configItem);
                                                                } else {
                                                                    String componentValue2 = componentFlash.getComponentValue(this.mCurrentMode);
                                                                    String str = componentValue2 == "0" ? "5" : "0";
                                                                    componentFlash.setComponentValue(this.mCurrentMode, str);
                                                                    onExpandValueChange(componentFlash, componentValue2, str);
                                                                }
                                                                if (cameraClickObservable != null) {
                                                                    cameraClickObservable.subscribe(161);
                                                                    return;
                                                                }
                                                                return;
                                                            }
                                                            return;
                                                        case 194:
                                                            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BaseEvent.FEATURE_NAME, MistatsConstants.AlgoAttr.VALUE_HDR_OUT_BUTTON, null);
                                                            expandExtraView(((DataItemConfig) DataRepository.provider().dataConfig()).getComponentHdr(), view, topConfigItem.configItem);
                                                            if (cameraClickObservable != null) {
                                                                cameraClickObservable.subscribe(162);
                                                                return;
                                                            }
                                                            return;
                                                        case 195:
                                                            configChanges.onConfigChanged(195);
                                                            return;
                                                        case 196:
                                                            configChanges.onConfigChanged(196);
                                                            return;
                                                        case 197:
                                                            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_MENU_MORE, 1, null);
                                                            showExtraMenu();
                                                            if (cameraClickObservable != null) {
                                                                cameraClickObservable.subscribe(164);
                                                                return;
                                                            }
                                                            return;
                                                        default:
                                                            switch (i) {
                                                                case 205:
                                                                    configChanges.onConfigChanged(205);
                                                                    return;
                                                                case 206:
                                                                    MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.LiveShotAttr.VALUE_TOPMENU_LIVESHOT_CLICK, null, null);
                                                                    configChanges.onConfigChanged(206);
                                                                    if (cameraClickObservable != null) {
                                                                        cameraClickObservable.subscribe(163);
                                                                        return;
                                                                    }
                                                                    return;
                                                                case 207:
                                                                    configChanges.onConfigChanged(207);
                                                                    return;
                                                                default:
                                                                    switch (i) {
                                                                        case 214:
                                                                            CameraStatUtils.trackMeterClick();
                                                                            expandExtraView(((DataItemConfig) DataRepository.provider().dataConfig()).getComponentConfigMeter(), view, topConfigItem.configItem);
                                                                            return;
                                                                        case 215:
                                                                            configChanges.onConfigChanged(215);
                                                                            return;
                                                                        case 216:
                                                                            CameraStatUtils.trackVVClick(MistatsConstants.VLogAttr.VALUE_VV_ICON_CLICK);
                                                                            configChanges.onConfigChanged(216);
                                                                            return;
                                                                        case 217:
                                                                            configChanges.onConfigChanged(217);
                                                                            return;
                                                                        case 218:
                                                                            configChanges.onConfigChanged(218);
                                                                            return;
                                                                        default:
                                                                            return;
                                                                    }
                                                            }
                                                    }
                                            }
                                    }
                                } else {
                                    configChanges.onConfigChanged(256);
                                }
                            }
                        }
                    }
                } else {
                    moreModePopupController.shrink(true);
                }
            } else {
                switchExtraTimerBurstMenu();
                this.mExtraAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override // com.android.camera.fragment.top.TopExpandAdapter.ExpandListener
    public void onExpandValueChange(ComponentData componentData, String str, String str2) {
        if (isEnableClick()) {
            DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
            ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            if (configChanges != null) {
                this.mFragmentTopAlert.showRecordingTime();
                if (str == str2) {
                    reverseExpandTopBar(true);
                    return;
                }
                switch (componentData.getDisplayTitleString()) {
                    case R.string.pref_camera_ai_audio:
                        updateConfigItem(168);
                        configChanges.onConfigChanged(168);
                        break;
                    case R.string.pref_camera_autoexposure_title:
                        MistatsWrapper.moduleUIClickEvent(componentData.getKey(this.mCurrentMode), MistatsConstants.Manual.AUTOEXPOSURE, str2);
                        updateConfigItem(214);
                        configChanges.configMeter(str2);
                        break;
                    case R.string.pref_camera_eis_title:
                        DataRepository.dataItemRunning().getComponentRunningEisPro().setComponentPreValue(str);
                        updateConfigItem(165);
                        configChanges.onConfigChanged(165);
                        break;
                    case R.string.pref_camera_flashmode_title:
                        if (componentData.getDisplayTitleString() == R.string.pref_camera_flashmode_title && !((str != "5" && str2 != "5") || str2 == "0" || str2 == "200")) {
                            configChanges.configBackSoftLightSwitch("0");
                            CameraStatUtils.trackFlashChanged(this.mCurrentMode, "0");
                        }
                        CameraStatUtils.trackFlashChanged(this.mCurrentMode, str2);
                        updateConfigItem(193);
                        boolean reConfigHhrIfFlashChanged = dataItemConfig.reConfigHhrIfFlashChanged(this.mCurrentMode, str, str2);
                        if (reConfigHhrIfFlashChanged) {
                            updateConfigItem(194);
                        }
                        configChanges.configFlash(str2, reConfigHhrIfFlashChanged);
                        break;
                    case R.string.pref_camera_hdr_title:
                        CameraStatUtils.trackHdrChanged(this.mCurrentMode, str2);
                        updateConfigItem(194);
                        configChanges.restoreMutexFlash("e");
                        if (dataItemConfig.reConfigFlashIfHdrChanged(this.mCurrentMode, str2)) {
                            updateConfigItem(193);
                        }
                        if (dataItemConfig.reConfigBokehIfHdrChanged(this.mCurrentMode, str2)) {
                            updateConfigItem(200);
                        }
                        configChanges.configHdr(str2);
                        break;
                }
                reverseExpandTopBar(true);
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        int i3 = this.mCurrentMode;
        boolean z = true;
        boolean z2 = i2 == 3;
        super.provideAnimateElement(i, list, i2);
        if (isInModeChanging() || i2 == 3) {
            this.mIsShowTopLyingDirectHint = false;
        }
        setSnapNumVisible(false, true);
        hideDelayNumber();
        if (i3 == 161 ? i != 161 : i3 == 162 ? i != 162 : i3 == 169 ? i != 169 : i3 == 174 || (i3 == 180 ? i != 180 : !(i3 == 183 && i == 183))) {
            int i4 = 7;
            if (i2 != 7) {
                i4 = 4;
            }
            onBackEvent(i4);
        }
        int topMaskTargetHeight = PaintConditionReferred.createModeChange(this.mCurrentMode, false, false).getTopMaskTargetHeight(i);
        this.mTopBackgroundView.setBlackOriginHeight(topMaskTargetHeight);
        if (isExtraMenuShowing()) {
            topMaskTargetHeight = topMaskTargetHeight == 0 ? 0 : this.mTopExtraMenuHeight;
            this.mTopBackgroundView.setBackgroundAlpha(153);
        } else {
            this.mTopBackgroundView.setCurrentRadius(0);
            this.mTopBackgroundView.setBackgroundAlpha(0);
        }
        Integer num = (Integer) this.mTopBackgroundView.getTag();
        if (num == null || num.intValue() != topMaskTargetHeight) {
            this.mTopBackgroundView.setTag(Integer.valueOf(topMaskTargetHeight));
            if (topMaskTargetHeight > this.mTopBackgroundView.getCurrentMaskHeight()) {
                this.mTopBackgroundView.setMaskSpecificHeight(topMaskTargetHeight, list != null);
            } else if (list == null) {
                this.mTopBackgroundView.setMaskSpecificHeight(topMaskTargetHeight, list != null);
            } else if (i3 == 165 && i == 254) {
                this.mTopBackgroundView.setMaskSpecificHeight(topMaskTargetHeight, list != null);
            }
        }
        if (z2) {
            enableAllDisabledMenuItem();
        }
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.provideAnimateElement(i, list, i2);
        }
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        if (z2 && this.mTopConfigMenu.getVisibility() != 0) {
            AlphaInOnSubscribe.directSetResult(this.mTopConfigMenu);
        }
        this.mSupportedConfigs = SupportedConfigFactory.getSupportedTopConfigs(this.mCurrentMode, DataRepository.dataItemGlobal().getCurrentCameraId(), DataRepository.dataItemGlobal().isNormalIntent());
        if (this.mSupportedConfigs != null) {
            int i5 = 0;
            while (i5 < this.mConfigViews.size()) {
                ImageView imageView = this.mConfigViews.get(i5);
                imageView.setEnabled(z);
                TopConfigItem configItem = this.mSupportedConfigs.getConfigItem(i5);
                boolean topImageResource = setTopImageResource(configItem, imageView, i, dataItemConfig, list != null ? z : false);
                if (!topImageResource || this.mDisabledFunctionMenu.indexOfKey(configItem.configItem) < 0 || !this.mDisabledFunctionMenu.get(configItem.configItem)) {
                    TopConfigItem topConfigItem = (TopConfigItem) imageView.getTag();
                    if (topConfigItem == null || topConfigItem.configItem != configItem.configItem) {
                        imageView.setTag(configItem);
                        if (list == null) {
                            if (topImageResource) {
                                AlphaInOnSubscribe.directSetResult(imageView);
                            } else {
                                AlphaOutOnSubscribe.directSetResult(imageView);
                            }
                        } else if (topImageResource) {
                            AlphaInOnSubscribe alphaInOnSubscribe = new AlphaInOnSubscribe(imageView);
                            if (this.mCurrentMode == 167 && 193 == configItem.configItem) {
                                alphaInOnSubscribe.setTargetAlpha(configItem.enable ? 1.0f : 0.4f);
                            }
                            alphaInOnSubscribe.setStartDelayTime(150).setDurationTime(150);
                            list.add(Completable.create(alphaInOnSubscribe));
                        } else if (i3 == 165 || this.mCurrentMode == 165) {
                            AlphaOutOnSubscribe.directSetResult(imageView);
                        } else {
                            list.add(Completable.create(new AlphaOutOnSubscribe(imageView).setDurationTime(150)));
                        }
                    } else {
                        imageView.setTag(configItem);
                    }
                }
                i5++;
                z = true;
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (!ModuleManager.isLandscapeMode()) {
            list.addAll(this.mConfigViews);
        }
        list.add(this.mMultiSnapNum);
        list.add(this.mCaptureDelayNumber);
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.provideRotateItem(list, i);
        }
        TopExpendView topExpendView = this.mTopExpandView;
        if (topExpendView != null) {
            topExpendView.provideRotateItem(list, i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void reInitAlert(boolean z) {
        if (!CameraSettings.isHandGestureOpen() || DataRepository.dataItemRunning().getHandGestureRunning()) {
            AndroidSchedulers.mainThread().scheduleDirect(new m(this, z), this.mIsShowExtraMenu ? 120 : 0, TimeUnit.MILLISECONDS);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void refreshExtraMenu() {
        RecyclerView recyclerView;
        if (this.mIsShowExtraMenu && (recyclerView = this.mTopExtraMenu) != null && recyclerView.getAdapter() != null) {
            this.mTopExtraMenu.getAdapter().notifyDataSetChanged();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void refreshHistogramStatsView() {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.refreshHistogramStatsView();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
        modeCoordinator.attachProtocol(172, this);
        if (!c.isSupportedOpticalZoom() && !HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            modeCoordinator.attachProtocol(184, this);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void removeConfigItem(int i) {
        resetImages();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void removeExtraMenu(int i) {
        onBackEvent(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public boolean reverseExpandTopBar(boolean z) {
        return this.mTopBarAnimationComponent.reverse(z);
    }

    public void rotate() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void setAiSceneImageLevel(int i) {
        if (i == 25) {
            i = 23;
        }
        this.mCurrentAiSceneLevel = i;
        Drawable aiSceneDrawable = getAiSceneDrawable(i);
        Drawable aiSceneShadowDrawable = getAiSceneShadowDrawable(i);
        boolean z = true;
        if (aiSceneDrawable == null || aiSceneShadowDrawable == null) {
            aiSceneDrawable = getResources().getDrawable(this.mAiSceneResources[0]);
            aiSceneShadowDrawable = getResources().getDrawable(this.mAiSceneResources[1]);
        }
        ImageView topImage = getTopImage(201);
        if (aiSceneDrawable != null && topImage != null && aiSceneShadowDrawable != null) {
            topImage.setImageDrawable(aiSceneDrawable);
            topImage.setBackground(aiSceneShadowDrawable);
            if (i != 38) {
                z = false;
            }
            configBottomPopupTips(z);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reConfigQrCodeTip();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void setAlertAnim(boolean z) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.setAlertAnim(z);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void setClickEnable(boolean z) {
        super.setClickEnable(z);
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.setClickEnable(z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void setRecordingTimeState(int i) {
        setRecordingTimeState(i, false);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void setRecordingTimeState(int i, boolean z) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.setRecordingTimeState(i, z);
        } else {
            FragmentTopAlert.setPendingRecordingState(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void setShow(boolean z) {
        if (getTopAlert() != null) {
            getTopAlert().setShow(z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.SnapShotIndicator
    @TargetApi(21)
    public void setSnapNumValue(int i) {
        if (this.mSnapStyle == null) {
            this.mSnapStyle = new TextAppearanceSpan(getContext(), R.style.SnapTipTextStyle);
        }
        SpannableStringBuilder spannableStringBuilder = this.mStringBuilder;
        if (spannableStringBuilder == null) {
            this.mStringBuilder = new SpannableStringBuilder();
        } else {
            spannableStringBuilder.clear();
        }
        this.mStringBuilder.append(String.format("%02d", Integer.valueOf(i)), this.mSnapStyle, 33);
        this.mMultiSnapNum.setText(this.mStringBuilder);
    }

    @Override // com.android.camera.protocol.ModeProtocol.SnapShotIndicator
    public void setSnapNumVisible(boolean z, boolean z2) {
        if (z != (this.mMultiSnapNum.getVisibility() == 0)) {
            if (this.mZoomInAnimator == null) {
                initSnapNumAnimator();
            }
            if (z) {
                setTopTipMarin(this.mMultiSnapNum);
                AlphaInOnSubscribe.directSetResult(this.mMultiSnapNum);
                setSnapNumValue(0);
                this.mZoomInAnimator.start();
                return;
            }
            this.mZoomOutAnimator.start();
            Completable.create(new AlphaOutOnSubscribe(this.mMultiSnapNum).setStartDelayTime(500)).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void setTipsState(String str, boolean z) {
        this.mTipsState.put(str, Boolean.valueOf(z));
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void showConfigMenu() {
        Completable.create(new AlphaInOnSubscribe(this.mTopConfigMenu)).subscribe();
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void showDelayNumber(int i) {
        if (this.mCaptureDelayNumber.getVisibility() != 0) {
            setTopTipMarin(this.mCaptureDelayNumber);
            Completable.create(new AlphaInOnSubscribe(this.mCaptureDelayNumber)).subscribe();
        }
        this.mCaptureDelayNumber.setText(String.valueOf(i));
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void showDocumentStateButton(int i) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.showDocumentStateButton(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void startLiveShotAnimation() {
        ImageView topImage = getTopImage(206);
        if (topImage != null) {
            Drawable drawable = topImage.getDrawable();
            if (drawable instanceof LayerDrawable) {
                RotateDrawable rotateDrawable = (RotateDrawable) ((LayerDrawable) drawable).getDrawable(0);
                ObjectAnimator objectAnimator = this.mLiveShotAnimator;
                if (objectAnimator == null || objectAnimator.getTarget() != rotateDrawable) {
                    this.mLiveShotAnimator = ObjectAnimator.ofInt(rotateDrawable, MiStat.Param.LEVEL, 0, 10000);
                    this.mLiveShotAnimator.setDuration(1000L);
                    this.mLiveShotAnimator.setInterpolator(new CubicEaseInOutInterpolator());
                }
                if (this.mLiveShotAnimator.isRunning()) {
                    this.mLiveShotAnimator.cancel();
                }
                this.mLiveShotAnimator.start();
            }
        }
    }

    @Override // com.android.camera.ui.SlideSwitchButton.SlideSwitchListener
    public void toSlideSwitch(int i, String str) {
        ModeProtocol.ConfigChanges configChanges;
        if (isEnableClick() && (configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)) != null) {
            configChanges.onConfigValueChanged(i, str);
            ExtraAdapter extraAdapter = this.mExtraAdapter;
            if (extraAdapter != null) {
                extraAdapter.notifyDataSetChanged();
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
        modeCoordinator.detachProtocol(172, this);
        if (!c.isSupportedOpticalZoom() && !HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            modeCoordinator.detachProtocol(184, this);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void updateConfigItem(int... iArr) {
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        for (int i : iArr) {
            ImageView topImage = getTopImage(i);
            if (topImage != null) {
                setTopImageResource((TopConfigItem) topImage.getTag(), topImage, this.mCurrentMode, dataItemConfig, false);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void updateContentDescription() {
        ImageView topImage = getTopImage(196);
        if (topImage != null) {
            topImage.setContentDescription(getString(R.string.accessibility_filter_open_panel));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void updateHistogramStatsData(int[] iArr) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.updateHistogramStatsData(iArr);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void updateHistogramStatsData(int[] iArr, int[] iArr2, int[] iArr3) {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.updateHistogramStatsData(iArr, iArr2, iArr3);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LyingDirectHint
    public void updateLyingDirectHint(boolean z, boolean z2) {
        if (!z2) {
            this.mIsShowTopLyingDirectHint = z;
        }
        if (!isExtraMenuShowing()) {
            FragmentTopAlert topAlert = getTopAlert();
            if (isTopAlertShowing(topAlert)) {
                topAlert.updateLyingDirectHint(this.mIsShowTopLyingDirectHint);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void updateProVideoRecordingSimpleView(boolean z) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.updateProVideoRecordingSimpleView(z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void updateRGBHistogramSwitched(boolean z) {
        if (!isTopAlertShowing(getTopAlert())) {
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void updateRecordingTime(String str) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.updateRecordingTime(str);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void updateRecordingTimeStyle(boolean z) {
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.updateRecordingTimeStyle(z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopAlert
    public void updateTopAlertLayout() {
        FragmentTopAlert topAlert = getTopAlert();
        if (isTopAlertShowing(topAlert)) {
            topAlert.updateTopAlertLayout();
        }
    }

    public /* synthetic */ void x(boolean z) {
        int i;
        FragmentTopAlert topAlert = getTopAlert();
        if (topAlert != null) {
            topAlert.setShow(true);
            reConfigTipOfFlash(z);
            reConfigTipOfMusicHint(z);
            alertUpdateValue(0, null);
            updateLyingDirectHint(false, true);
            if (CameraSettings.isProVideoHistogramOpen(this.mCurrentMode)) {
                topAlert.alertHistogram(0);
            }
            ModeProtocol.TopConfigProtocol topConfigProtocol = (ModeProtocol.TopConfigProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(193);
            if (topConfigProtocol != null && ((i = this.mCurrentMode) == 162 || i == 163)) {
                topConfigProtocol.reShowMoon();
            }
            ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            if (configChanges != null) {
                showTips(configChanges, true);
                configChanges.reCheckVideoUltraClearTip();
                configChanges.reCheckVideoHDR10Tip();
                configChanges.reCheckParameterResetTip(true);
                configChanges.reCheckParameterDescriptionTip();
                configChanges.reCheckVideoLog();
                configChanges.reCheckRaw();
                configChanges.reCheckFastMotion(true);
                configChanges.recheckVideoFps(false);
                configChanges.reCheckDocumentMode();
                configChanges.reCheckHanGestureDescTip();
                configChanges.reCheckSpeechShutterDescTip();
                configChanges.reCheckAmbilight();
            }
        }
    }
}
