package com.android.camera.fragment;

import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import b.c.a.c;
import com.android.camera.Camera;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.animation.AnimationComposite;
import com.android.camera.data.DataRepository;
import com.android.camera.features.gif.FragmentGifEdit;
import com.android.camera.features.mimoji2.fragment.FragmentMimojiFullScreen;
import com.android.camera.features.mimoji2.fragment.bottomlist.FragmentMimojiBottomList;
import com.android.camera.features.mimoji2.fragment.edit.FragmentMimojiEmoticon;
import com.android.camera.fragment.aiwatermark.FragmentWatermark;
import com.android.camera.fragment.ambilight.FragmentAmbilight;
import com.android.camera.fragment.beauty.FragmentBlankBeauty;
import com.android.camera.fragment.beauty.FragmentBlankDefault;
import com.android.camera.fragment.beauty.FragmentPopuEyeLightTip;
import com.android.camera.fragment.beauty.FragmentPopupBeauty;
import com.android.camera.fragment.beauty.FragmentPopupBeautyLevel;
import com.android.camera.fragment.bottom.action.FragmentBottomAction;
import com.android.camera.fragment.bottom.action.FragmentLighting;
import com.android.camera.fragment.clone.FragmentCloneGallery;
import com.android.camera.fragment.clone.FragmentCloneProcess;
import com.android.camera.fragment.clone.FragmentCloneUseGuide;
import com.android.camera.fragment.dialog.FragmentLiveReview;
import com.android.camera.fragment.dual.FragmentDualCameraAdjust;
import com.android.camera.fragment.dual.FragmentDualStereo;
import com.android.camera.fragment.fastmotion.FragmentFastMotion;
import com.android.camera.fragment.fullscreen.FragmentFullScreen;
import com.android.camera.fragment.idcard.FragmentIDCard;
import com.android.camera.fragment.lifeCircle.BaseLifeCircleBindFragment;
import com.android.camera.fragment.lifeCircle.BaseLifecycleListener;
import com.android.camera.fragment.live.FragmentLiveSpeed;
import com.android.camera.fragment.live.FragmentLiveSticker;
import com.android.camera.fragment.manually.FragmentManually;
import com.android.camera.fragment.mimoji.FragmentMimoji;
import com.android.camera.fragment.mode.FragmentMoreModeEdit;
import com.android.camera.fragment.mode.FragmentMoreModeNormal;
import com.android.camera.fragment.mode.FragmentMoreModePopup;
import com.android.camera.fragment.sticker.FragmentSticker;
import com.android.camera.fragment.subtitle.FragmentSubtitle;
import com.android.camera.fragment.top.FragmentTopConfig;
import com.android.camera.fragment.vv.FragmentVV;
import com.android.camera.fragment.vv.FragmentVVGallery;
import com.android.camera.fragment.vv.FragmentVVProcess;
import com.android.camera.log.Log;
import com.android.camera.module.loader.StartControl;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseFragmentDelegate implements ModeProtocol.BaseDelegate {
    public static final int BEAUTY_FRAGMENT_CONTAINER_ID = 2131296360;
    public static final int FRAGMENT_AMBILIGHT = 16777200;
    public static final int FRAGMENT_ASD_WATERMARK = 1048572;
    public static final int FRAGMENT_BASE_WATERMARK = 1048566;
    public static final int FRAGMENT_BEAUTY = 251;
    public static final int FRAGMENT_BLANK_BEAUTY = 4090;
    public static final int FRAGMENT_BLANK_DEFAULT = 14;
    public static final int FRAGMENT_BOTTOM_ACTION = 241;
    public static final int FRAGMENT_BOTTOM_INTENT_DONE = 4083;
    public static final int FRAGMENT_BOTTOM_POPUP_TIPS = 65529;
    public static final int FRAGMENT_CITY_WATERMARK = 1048573;
    public static final int FRAGMENT_CLONE_GALLERY = 1048564;
    public static final int FRAGMENT_CLONE_PROCESS = 1048563;
    public static final int FRAGMENT_CLONE_USE_GUIDE = 1048565;
    public static final int FRAGMENT_CUSTOM_TINT = 65528;
    public static final int FRAGMENT_DISPATCH = 13;
    public static final int FRAGMENT_DUAL_CAMERA_ADJUST = 4084;
    public static final int FRAGMENT_DUAL_CAMERA_BOKEH_ADJUST = 4091;
    public static final int FRAGMENT_DUAL_STEREO = 4085;
    public static final int FRAGMENT_EYE_LIGHT_POPU_TIP = 4089;
    public static final int FRAGMENT_FAST_MOTION = 16777201;
    public static final int FRAGMENT_FAST_MOTION_EXTRA = 16777202;
    public static final int FRAGMENT_FESTIVAL_WATERMARK = 1048571;
    public static final int FRAGMENT_FILTER = 250;
    public static final int FRAGMENT_GENERAL_WATERMARK = 1048567;
    public static final int FRAGMENT_GIF = 65524;
    public static final int FRAGMENT_ID_CARD = 1048560;
    public static final int FRAGMENT_INVALID = 240;
    public static final int FRAGMENT_LIGHTING = 4087;
    public static final int FRAGMENT_LIVE_REVIEW = 1048561;
    public static final int FRAGMENT_MAIN_CONTENT = 243;
    public static final int FRAGMENT_MANUALLY_EXTRA = 254;
    public static final int FRAGMENT_MIMOJI_EDIT = 65521;
    public static final int FRAGMENT_MIMOJI_EMOTICON = 65522;
    public static final int FRAGMENT_MIMOJI_LIST = 65520;
    public static final int FRAGMENT_MIMOJI_SCREEN = 65523;
    public static final int FRAGMENT_MODES_EDIT = 65527;
    public static final int FRAGMENT_MODES_MORE_NORMAL = 65525;
    public static final int FRAGMENT_MODES_MORE_POPUP = 65526;
    public static final int FRAGMENT_MODE_SELECT = 242;
    public static final int FRAGMENT_PANORAMA = 4080;
    public static final int FRAGMENT_POPUP_BEAUTY = 249;
    public static final int FRAGMENT_POPUP_BEAUTYLEVEL = 4082;
    public static final int FRAGMENT_POPUP_LIVE_SPEED = 4093;
    public static final int FRAGMENT_POPUP_LIVE_STICKER = 4092;
    public static final int FRAGMENT_POPUP_MAKEUP = 252;
    public static final int FRAGMENT_POPUP_MANUALLY = 247;
    public static final int FRAGMENT_SCREEN_LIGHT = 4086;
    public static final int FRAGMENT_SILHOUETTE_WATERMARK = 1048569;
    public static final int FRAGMENT_SPOTS_WATERMARK = 1048568;
    public static final int FRAGMENT_STICKER = 255;
    public static final int FRAGMENT_SUBTITLE = 65534;
    public static final int FRAGMENT_TEXT_WATERMARK = 1048570;
    public static final int FRAGMENT_TOP_ALERT = 253;
    public static final int FRAGMENT_TOP_CONFIG = 244;
    public static final int FRAGMENT_VERTICAL = 4088;
    public static final int FRAGMENT_VV = 65530;
    public static final int FRAGMENT_VV_GALLERY = 65531;
    public static final int FRAGMENT_VV_PREVIEW = 65532;
    public static final int FRAGMENT_VV_PROCESS = 65533;
    public static final int FRAGMENT_WATERMARK = 1048574;
    public static final int FRAGMENT_WIDESELFIE = 4094;
    public static final int MODULE_CONTENT = 1048562;
    private static final String TAG = "BaseFragmentDelegate";
    private AnimationComposite animationComposite;
    private SparseArray<List<Integer>> currentFragments;
    private Camera mActivity;
    private BaseLifecycleListener mBaseLifecycleListener = null;
    private List<BaseFragmentOperation> mLastFragmentAlias;
    private SparseIntArray mStoreFragments;
    private FragmentManager mSupportFragmentManager = null;
    private SparseIntArray originalFragments;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FragmentInto {
    }

    public BaseFragmentDelegate(Camera camera) {
        this.mActivity = camera;
        this.animationComposite = new AnimationComposite();
        this.originalFragments = new SparseIntArray();
    }

    private void applyUpdateSet(List<BaseFragmentOperation> list, BaseLifecycleListener baseLifecycleListener) {
        BaseFragment baseFragment;
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("need operation info");
        }
        Camera camera = this.mActivity;
        if (camera == null || !camera.isFinishing()) {
            FragmentManager supportFragmentManager = this.mActivity.getSupportFragmentManager();
            FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
            for (BaseFragmentOperation baseFragmentOperation : list) {
                int activeFragment = getActiveFragment(baseFragmentOperation.containerViewId);
                int i = baseFragmentOperation.pendingFragmentInfo;
                switch (baseFragmentOperation.operateType) {
                    case 1:
                        int activeFragment2 = getActiveFragment(baseFragmentOperation.containerViewId);
                        this.animationComposite.remove(activeFragment2);
                        BaseFragment constructFragment = constructFragment(false, i, activeFragment2, baseLifecycleListener);
                        beginTransaction.replace(baseFragmentOperation.containerViewId, constructFragment, constructFragment.getFragmentTag());
                        this.animationComposite.put(constructFragment.getFragmentInto(), constructFragment);
                        updateCurrentFragments(baseFragmentOperation.containerViewId, i, baseFragmentOperation.operateType);
                        break;
                    case 2:
                        this.animationComposite.remove(i);
                        BaseFragment baseFragment2 = (BaseFragment) supportFragmentManager.findFragmentByTag(String.valueOf(i));
                        if (baseFragment2 != null) {
                            baseFragment2.pendingGone(false);
                            beginTransaction.remove(baseFragment2);
                        }
                        updateCurrentFragments(baseFragmentOperation.containerViewId, i, baseFragmentOperation.operateType);
                        break;
                    case 3:
                        this.animationComposite.remove(activeFragment);
                        BaseFragment baseFragment3 = (BaseFragment) supportFragmentManager.findFragmentByTag(String.valueOf(activeFragment));
                        if (baseFragment3 != null) {
                            baseFragment3.pendingGone(false);
                            beginTransaction.remove(baseFragment3);
                        }
                        updateCurrentFragments(baseFragmentOperation.containerViewId, activeFragment, baseFragmentOperation.operateType);
                        break;
                    case 4:
                        BaseFragment baseFragment4 = (BaseFragment) supportFragmentManager.findFragmentByTag(String.valueOf(activeFragment));
                        if (baseFragment4 != null) {
                            baseFragment4.pendingGone(false);
                            baseFragment4.setNewFragmentInfo(i);
                            beginTransaction.hide(baseFragment4);
                        }
                        BaseFragment baseFragment5 = (BaseFragment) supportFragmentManager.findFragmentByTag(String.valueOf(i));
                        if (baseFragment5 != null) {
                            beginTransaction.remove(baseFragment5);
                        }
                        BaseFragment constructFragment2 = constructFragment(false, i, activeFragment, baseLifecycleListener);
                        beginTransaction.add(baseFragmentOperation.containerViewId, constructFragment2, constructFragment2.getFragmentTag());
                        this.animationComposite.put(constructFragment2.getFragmentInto(), constructFragment2);
                        updateCurrentFragments(baseFragmentOperation.containerViewId, i, baseFragmentOperation.operateType);
                        break;
                    case 5:
                        List<Integer> list2 = this.currentFragments.get(baseFragmentOperation.containerViewId);
                        for (int i2 = 0; i2 < list2.size(); i2++) {
                            int intValue = list2.get(i2).intValue();
                            if (intValue != i) {
                                this.animationComposite.remove(intValue);
                                BaseFragment baseFragment6 = (BaseFragment) supportFragmentManager.findFragmentByTag(String.valueOf(intValue));
                                if (baseFragment6 != null) {
                                    if (intValue != activeFragment) {
                                        baseFragment6.pendingGone(true);
                                    } else {
                                        baseFragment6.pendingGone(false);
                                    }
                                    beginTransaction.remove(baseFragment6);
                                }
                            }
                        }
                        BaseFragment baseFragment7 = (BaseFragment) supportFragmentManager.findFragmentByTag(String.valueOf(i));
                        if (baseFragment7 == null) {
                            Log.d(TAG, "popup null, create new");
                            baseFragment7 = constructFragment(false, i, activeFragment, baseLifecycleListener);
                        }
                        baseFragment7.setLastFragmentInfo(activeFragment);
                        baseFragment7.pendingShow();
                        beginTransaction.show(baseFragment7);
                        updateCurrentFragments(baseFragmentOperation.containerViewId, i, baseFragmentOperation.operateType);
                        break;
                    case 6:
                        if (!(activeFragment == i || (baseFragment = (BaseFragment) supportFragmentManager.findFragmentByTag(String.valueOf(activeFragment))) == null)) {
                            baseFragment.pendingGone(true);
                            beginTransaction.hide(baseFragment);
                        }
                        BaseFragment baseFragment8 = (BaseFragment) supportFragmentManager.findFragmentByTag(String.valueOf(i));
                        baseFragment8.setLastFragmentInfo(activeFragment);
                        baseFragment8.pendingShow();
                        beginTransaction.show(baseFragment8);
                        updateCurrentFragments(baseFragmentOperation.containerViewId, i, baseFragmentOperation.operateType);
                        break;
                    case 7:
                        BaseFragment baseFragment9 = (BaseFragment) supportFragmentManager.findFragmentByTag(String.valueOf(activeFragment));
                        if (baseFragment9 != null) {
                            beginTransaction.hide(baseFragment9);
                        }
                        updateCurrentFragments(baseFragmentOperation.containerViewId, activeFragment, baseFragmentOperation.operateType);
                        break;
                }
            }
            beginTransaction.commitAllowingStateLoss();
        }
    }

    @Deprecated
    public static void bindLifeCircle(Fragment fragment) {
        FragmentManager childFragmentManager = fragment.getChildFragmentManager();
        BaseLifeCircleBindFragment baseLifeCircleBindFragment = new BaseLifeCircleBindFragment();
        baseLifeCircleBindFragment.getFragmentLifecycle().addListener(new BaseLifecycleListener() {
            /* class com.android.camera.fragment.BaseFragmentDelegate.AnonymousClass1 */

            @Override // com.android.camera.fragment.lifeCircle.BaseLifecycleListener
            public void onLifeAlive() {
            }

            @Override // com.android.camera.fragment.lifeCircle.BaseLifecycleListener
            public void onLifeDestroy(String str) {
            }

            @Override // com.android.camera.fragment.lifeCircle.BaseLifecycleListener
            public void onLifeStart(String str) {
            }

            @Override // com.android.camera.fragment.lifeCircle.BaseLifecycleListener
            public void onLifeStop(String str) {
            }

            @Override // com.android.camera.fragment.lifeCircle.BaseLifecycleListener
            public void setBlockingLifeCycles(List<String> list) {
            }
        }, BaseLifeCircleBindFragment.FRAGMENT_TAG);
        childFragmentManager.beginTransaction().add(baseLifeCircleBindFragment, BaseLifeCircleBindFragment.FRAGMENT_TAG).commitAllowingStateLoss();
    }

    private BaseFragment constructFragment(boolean z, int i, int i2, BaseLifecycleListener baseLifecycleListener) {
        BaseFragment baseFragment = null;
        if (i == 13) {
            baseFragment = new DispatchFragment();
        } else if (i == 14) {
            baseFragment = new FragmentBlankDefault();
        } else if (i == 240) {
            return null;
        } else {
            if (i == 241) {
                baseFragment = new FragmentBottomAction();
            } else if (i == 243) {
                baseFragment = new FragmentMainContent();
            } else if (i != 244) {
                switch (i) {
                    case 247:
                        baseFragment = new FragmentManually();
                        break;
                    case 249:
                        baseFragment = new FragmentPopupBeauty();
                        break;
                    case 251:
                        baseFragment = new FragmentBeauty();
                        baseFragment.setDegree(this.animationComposite.getTargetDegree());
                        break;
                    case 255:
                        baseFragment = new FragmentSticker();
                        break;
                    case 4080:
                        baseFragment = new FragmentPanorama();
                        break;
                    case 4082:
                        baseFragment = new FragmentPopupBeautyLevel();
                        break;
                    case 4083:
                        baseFragment = new FragmentBottomIntentDone();
                        break;
                    case 4084:
                        baseFragment = new FragmentDualCameraAdjust();
                        break;
                    case 4085:
                        baseFragment = new FragmentDualStereo();
                        break;
                    case 4086:
                        baseFragment = new FragmentFullScreen();
                        break;
                    case FRAGMENT_LIGHTING /*{ENCODED_INT: 4087}*/:
                        baseFragment = new FragmentLighting();
                        baseFragment.setDegree(this.animationComposite.getTargetDegree());
                        break;
                    case FRAGMENT_VERTICAL /*{ENCODED_INT: 4088}*/:
                        baseFragment = new FragmentVertical();
                        break;
                    case 4089:
                        baseFragment = new FragmentPopuEyeLightTip();
                        break;
                    case 4090:
                        baseFragment = new FragmentBlankBeauty();
                        break;
                    case FRAGMENT_MIMOJI_LIST /*{ENCODED_INT: 65520}*/:
                        baseFragment = this.mActivity.getCurrentModuleIndex() == 184 ? new FragmentMimojiBottomList() : new FragmentMimoji();
                        baseFragment.setDegree(this.animationComposite.getTargetDegree());
                        break;
                    case 1048574:
                        baseFragment = new FragmentWatermark();
                        baseFragment.setDegree(this.animationComposite.getTargetDegree());
                        break;
                    default:
                        switch (i) {
                            case FRAGMENT_POPUP_LIVE_STICKER /*{ENCODED_INT: 4092}*/:
                                baseFragment = new FragmentLiveSticker();
                                baseFragment.setDegree(this.animationComposite.getTargetDegree());
                                break;
                            case FRAGMENT_POPUP_LIVE_SPEED /*{ENCODED_INT: 4093}*/:
                                baseFragment = new FragmentLiveSpeed();
                                baseFragment.setDegree(this.animationComposite.getTargetDegree());
                                break;
                            case 4094:
                                baseFragment = new FragmentWideSelfie();
                                break;
                            default:
                                switch (i) {
                                    case FRAGMENT_MIMOJI_EMOTICON /*{ENCODED_INT: 65522}*/:
                                        baseFragment = new FragmentMimojiEmoticon();
                                        break;
                                    case 65523:
                                        baseFragment = new FragmentMimojiFullScreen();
                                        break;
                                    case 65524:
                                        baseFragment = new FragmentGifEdit();
                                        break;
                                    case FRAGMENT_MODES_MORE_NORMAL /*{ENCODED_INT: 65525}*/:
                                        baseFragment = new FragmentMoreModeNormal();
                                        baseFragment.setDegree(this.animationComposite.getTargetDegree());
                                        break;
                                    case FRAGMENT_MODES_MORE_POPUP /*{ENCODED_INT: 65526}*/:
                                        baseFragment = new FragmentMoreModePopup();
                                        baseFragment.setDegree(this.animationComposite.getTargetDegree());
                                        break;
                                    case FRAGMENT_MODES_EDIT /*{ENCODED_INT: 65527}*/:
                                        baseFragment = new FragmentMoreModeEdit();
                                        break;
                                    default:
                                        switch (i) {
                                            case 65529:
                                                baseFragment = new FragmentBottomPopupTips();
                                                break;
                                            case FRAGMENT_VV /*{ENCODED_INT: 65530}*/:
                                                baseFragment = new FragmentVV();
                                                break;
                                            case FRAGMENT_VV_GALLERY /*{ENCODED_INT: 65531}*/:
                                                baseFragment = new FragmentVVGallery();
                                                break;
                                            default:
                                                switch (i) {
                                                    case FRAGMENT_VV_PROCESS /*{ENCODED_INT: 65533}*/:
                                                        baseFragment = new FragmentVVProcess();
                                                        break;
                                                    case FRAGMENT_SUBTITLE /*{ENCODED_INT: 65534}*/:
                                                        baseFragment = new FragmentSubtitle();
                                                        break;
                                                    default:
                                                        switch (i) {
                                                            case FRAGMENT_ID_CARD /*{ENCODED_INT: 1048560}*/:
                                                                baseFragment = new FragmentIDCard();
                                                                break;
                                                            case 1048561:
                                                                baseFragment = new FragmentLiveReview();
                                                                break;
                                                            case MODULE_CONTENT /*{ENCODED_INT: 1048562}*/:
                                                                baseFragment = new FragmentModuleContent();
                                                                break;
                                                            case FRAGMENT_CLONE_PROCESS /*{ENCODED_INT: 1048563}*/:
                                                                baseFragment = new FragmentCloneProcess();
                                                                baseFragment.setDegree(this.animationComposite.getTargetDegree());
                                                                break;
                                                            case FRAGMENT_CLONE_GALLERY /*{ENCODED_INT: 1048564}*/:
                                                                baseFragment = new FragmentCloneGallery();
                                                                break;
                                                            case FRAGMENT_CLONE_USE_GUIDE /*{ENCODED_INT: 1048565}*/:
                                                                baseFragment = new FragmentCloneUseGuide();
                                                                baseFragment.setDegree(this.animationComposite.getTargetDegree());
                                                                break;
                                                            default:
                                                                switch (i) {
                                                                    case FRAGMENT_AMBILIGHT /*{ENCODED_INT: 16777200}*/:
                                                                        baseFragment = new FragmentAmbilight();
                                                                        break;
                                                                    case 16777201:
                                                                        baseFragment = new FragmentFastMotion();
                                                                        baseFragment.setDegree(this.animationComposite.getTargetDegree());
                                                                        break;
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
            } else {
                baseFragment = new FragmentTopConfig();
            }
        }
        inceptFragment(baseFragment, z, i2, baseLifecycleListener);
        return baseFragment;
    }

    private void inceptFragment(BaseFragment baseFragment, boolean z, int i, BaseLifecycleListener baseLifecycleListener) {
        baseFragment.setLastFragmentInfo(i);
        baseFragment.setLifecycleListener(baseLifecycleListener);
        baseFragment.registerProtocol();
        baseFragment.setEnableClickInitValue(!z);
    }

    private void initCurrentFragments(SparseIntArray sparseIntArray) {
        int size = sparseIntArray.size();
        this.currentFragments = new SparseArray<>(size);
        for (int i = 0; i < size; i++) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(sparseIntArray.valueAt(i)));
            this.currentFragments.put(sparseIntArray.keyAt(i), arrayList);
        }
    }

    private int popStoredFragment(@IdRes int i) {
        SparseIntArray sparseIntArray = this.mStoreFragments;
        if (sparseIntArray == null) {
            return 240;
        }
        int i2 = sparseIntArray.get(i, 240);
        this.mStoreFragments.delete(i);
        return i2;
    }

    private void storeFragmentInfo(@IdRes int i, int i2) {
        if (this.mStoreFragments == null) {
            this.mStoreFragments = new SparseIntArray();
        }
        this.mStoreFragments.put(i, i2);
    }

    public void batchFragmentRequest(List<BaseFragmentOperation> list) {
        List<BaseFragmentOperation> resetFeatureFragment = resetFeatureFragment(list);
        if (list != null) {
            for (BaseFragmentOperation baseFragmentOperation : list) {
                int i = baseFragmentOperation.operateType;
                if ((i != 1 && i != 6) || getActiveFragment(baseFragmentOperation.containerViewId) != baseFragmentOperation.pendingFragmentInfo) {
                    if (resetFeatureFragment == null) {
                        resetFeatureFragment = new ArrayList<>(list.size());
                    }
                    resetFeatureFragment.add(baseFragmentOperation);
                }
            }
            if (resetFeatureFragment != null) {
                applyUpdateSet(resetFeatureFragment, null);
            }
            this.mLastFragmentAlias = list;
        } else if (resetFeatureFragment != null) {
            applyUpdateSet(resetFeatureFragment, null);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseDelegate
    public void delegateEvent(int i) {
        ArrayList arrayList = new ArrayList();
        if (i != 2) {
            if (i != 23) {
                if (i != 6) {
                    if (i != 7) {
                        switch (i) {
                            case 10:
                                if (getActiveFragment(R.id.bottom_beauty) == 4090) {
                                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).removeCurrent());
                                    break;
                                } else {
                                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).replaceWith(4090));
                                    break;
                                }
                            case 11:
                                if (getActiveFragment(R.id.full_screen_feature) == 1048561) {
                                    arrayList.add(BaseFragmentOperation.create(R.id.full_screen_feature).removeCurrent());
                                    break;
                                } else {
                                    arrayList.add(BaseFragmentOperation.create(R.id.full_screen_feature).replaceWith(1048561));
                                    break;
                                }
                            case 12:
                                if (getActiveFragment(R.id.bottom_beauty) == 4092) {
                                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).removeCurrent());
                                    break;
                                } else {
                                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).replaceWith(FRAGMENT_POPUP_LIVE_STICKER));
                                    break;
                                }
                            case 13:
                                if (getActiveFragment(R.id.bottom_beauty) == 4093) {
                                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).removeCurrent());
                                    break;
                                } else {
                                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).replaceWith(FRAGMENT_POPUP_LIVE_SPEED));
                                    break;
                                }
                            case 14:
                                if (DataRepository.dataItemRunning().getUiStyle() != 0) {
                                    if (getActiveFragment(R.id.bottom_beauty) == 65520) {
                                        arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).removeCurrent());
                                        break;
                                    } else {
                                        arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).replaceWith(FRAGMENT_MIMOJI_LIST));
                                        break;
                                    }
                                } else if (getActiveFragment(R.id.bottom_mimoji) == 65520) {
                                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_mimoji).removeCurrent());
                                    break;
                                } else {
                                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_mimoji).replaceWith(FRAGMENT_MIMOJI_LIST));
                                    break;
                                }
                            case 15:
                                if (getActiveFragment(R.id.bottom_action) == 65530) {
                                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_popup_tips).show(getOriginalFragment(R.id.bottom_popup_tips)));
                                    if (c.isSupportedOpticalZoom() || c.Wn() || HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                                        arrayList.add(BaseFragmentOperation.create(R.id.bottom_popup_dual_camera_adjust).show(getOriginalFragment(R.id.bottom_popup_dual_camera_adjust)));
                                    }
                                    arrayList.add(BaseFragmentOperation.create(R.id.full_screen_feature).popAndClearOthers(4086));
                                    break;
                                } else {
                                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_popup_tips).hideCurrent());
                                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_popup_dual_camera_adjust).hideCurrent());
                                    arrayList.add(BaseFragmentOperation.create(R.id.full_screen_feature).push(FRAGMENT_VV_PROCESS));
                                    break;
                                }
                            default:
                                switch (i) {
                                    case 19:
                                        if (getActiveFragment(R.id.fragment_full_screen_mimoji_emoticon) == 65522) {
                                            arrayList.add(BaseFragmentOperation.create(R.id.fragment_full_screen_mimoji_emoticon).removeCurrent());
                                            break;
                                        } else {
                                            arrayList.add(BaseFragmentOperation.create(R.id.fragment_full_screen_mimoji_emoticon).replaceWith(FRAGMENT_MIMOJI_EMOTICON));
                                            break;
                                        }
                                    case 20:
                                        if (getActiveFragment(R.id.fragment_full_screen_mimoji_gif) == 65524) {
                                            arrayList.add(BaseFragmentOperation.create(R.id.fragment_full_screen_mimoji_gif).removeCurrent());
                                            break;
                                        } else {
                                            arrayList.add(BaseFragmentOperation.create(R.id.fragment_full_screen_mimoji_gif).replaceWith(65524));
                                            break;
                                        }
                                    case 21:
                                        if (getActiveFragment(R.id.bottom_beauty) == 1048574) {
                                            arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).removeCurrent());
                                            break;
                                        } else {
                                            arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).replaceWith(1048574));
                                            break;
                                        }
                                    default:
                                        switch (i) {
                                            case 30:
                                                if (getActiveFragment(R.id.full_screen_feature) == 65525) {
                                                    arrayList.add(BaseFragmentOperation.create(R.id.full_screen_feature).removeCurrent());
                                                    break;
                                                } else {
                                                    arrayList.add(BaseFragmentOperation.create(R.id.full_screen_feature).replaceWith(FRAGMENT_MODES_MORE_NORMAL));
                                                    break;
                                                }
                                            case 31:
                                                if (getActiveFragment(R.id.full_screen_feature) == 65526) {
                                                    arrayList.add(BaseFragmentOperation.create(R.id.full_screen_feature).removeCurrent());
                                                    break;
                                                } else {
                                                    arrayList.add(BaseFragmentOperation.create(R.id.full_screen_feature).replaceWith(FRAGMENT_MODES_MORE_POPUP));
                                                    break;
                                                }
                                            case 32:
                                                if (getActiveFragment(R.id.full_screen_feature) == 65527) {
                                                    arrayList.add(BaseFragmentOperation.create(R.id.full_screen_feature).removeCurrent());
                                                    break;
                                                } else {
                                                    arrayList.add(BaseFragmentOperation.create(R.id.full_screen_feature).replaceWith(FRAGMENT_MODES_EDIT));
                                                    break;
                                                }
                                            case 33:
                                                if (getActiveFragment(R.id.bottom_beauty) == 4087) {
                                                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).removeCurrent());
                                                    break;
                                                } else {
                                                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).replaceWith(FRAGMENT_LIGHTING));
                                                    break;
                                                }
                                            case 34:
                                                if (getActiveFragment(R.id.bottom_beauty) == 16777201) {
                                                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).removeCurrent());
                                                    break;
                                                } else {
                                                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).replaceWith(16777201));
                                                    break;
                                                }
                                        }
                                }
                        }
                    } else {
                        int activeFragment = getActiveFragment(R.id.bottom_action);
                        if (activeFragment == 65530) {
                            delegateEvent(15);
                            return;
                        } else if (activeFragment != 241) {
                            arrayList.add(BaseFragmentOperation.create(R.id.bottom_action).popAndClearOthers(241));
                            arrayList.add(BaseFragmentOperation.create(R.id.bottom_popup_tips).replaceWith(65529));
                            arrayList.add(BaseFragmentOperation.create(R.id.full_screen_feature).removeCurrent());
                        }
                    }
                } else if (getActiveFragment(R.id.bottom_action) != 4083) {
                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_action).push(4083));
                    arrayList.add(BaseFragmentOperation.create(R.id.main_top).hideCurrent());
                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_popup_manually).hideCurrent());
                    int activeFragment2 = getActiveFragment(R.id.bottom_beauty);
                    if (activeFragment2 != 240) {
                        storeFragmentInfo(R.id.bottom_beauty, activeFragment2);
                        arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).hideCurrent());
                    }
                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_popup_tips).hideCurrent());
                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_popup_dual_camera_adjust).hideCurrent());
                } else {
                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_action).popAndClearOthers(241));
                    arrayList.add(BaseFragmentOperation.create(R.id.main_top).show(getOriginalFragment(R.id.main_top)));
                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_popup_manually).show(getOriginalFragment(R.id.bottom_popup_manually)));
                    int popStoredFragment = popStoredFragment(R.id.bottom_beauty);
                    if (popStoredFragment != 240) {
                        arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).show(popStoredFragment));
                    }
                    arrayList.add(BaseFragmentOperation.create(R.id.bottom_popup_tips).show(getOriginalFragment(R.id.bottom_popup_tips)));
                    if (c.isSupportedOpticalZoom() || c.Wn() || HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                        arrayList.add(BaseFragmentOperation.create(R.id.bottom_popup_dual_camera_adjust).show(getOriginalFragment(R.id.bottom_popup_dual_camera_adjust)));
                    }
                }
            } else if (getActiveFragment(R.id.full_screen_feature) != 1048565) {
                arrayList.add(BaseFragmentOperation.create(R.id.full_screen_feature).replaceWith(FRAGMENT_CLONE_USE_GUIDE));
            } else {
                arrayList.add(BaseFragmentOperation.create(R.id.full_screen_feature).replaceWith(FRAGMENT_CLONE_PROCESS));
            }
        } else if (getActiveFragment(R.id.bottom_beauty) != 251) {
            arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).replaceWith(251));
        } else {
            arrayList.add(BaseFragmentOperation.create(R.id.bottom_beauty).removeCurrent());
        }
        if (!arrayList.isEmpty()) {
            applyUpdateSet(arrayList, null);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseDelegate
    public Disposable delegateMode(@Nullable Completable completable, StartControl startControl, BaseLifecycleListener baseLifecycleListener) {
        if (this.mActivity == null) {
            return null;
        }
        a aVar = baseLifecycleListener != null ? new a(baseLifecycleListener) : null;
        if (!DataRepository.dataItemFeature().isSupportUltraWide()) {
            return this.animationComposite.dispose(completable, aVar, startControl);
        }
        if (completable != null) {
            if (baseLifecycleListener != null) {
                completable.subscribe(aVar);
            } else {
                completable.subscribe();
            }
        }
        return this.animationComposite.dispose(null, null, startControl);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseDelegate
    public int getActiveFragment(@IdRes int i) {
        List<Integer> list = this.currentFragments.get(i);
        if (list == null || list.isEmpty()) {
            return 240;
        }
        return list.get(list.size() - 1).intValue();
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseDelegate
    public AnimationComposite getAnimationComposite() {
        return this.animationComposite;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseDelegate
    public int getOriginalFragment(@IdRes int i) {
        return this.originalFragments.get(i, 240);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseDelegate
    public void init(FragmentManager fragmentManager, int i, BaseLifecycleListener baseLifecycleListener) {
        registerProtocol();
        this.mSupportFragmentManager = fragmentManager;
        this.mBaseLifecycleListener = baseLifecycleListener;
        BaseFragment constructFragment = constructFragment(true, 13, 240, baseLifecycleListener);
        BaseFragment constructFragment2 = constructFragment(true, 244, 240, baseLifecycleListener);
        BaseFragment constructFragment3 = constructFragment(true, 247, 240, baseLifecycleListener);
        BaseFragment constructFragment4 = constructFragment(true, 65529, 240, baseLifecycleListener);
        BaseFragment constructFragment5 = constructFragment(true, 241, 240, baseLifecycleListener);
        BaseFragment constructFragment6 = constructFragment(true, 243, 240, baseLifecycleListener);
        BaseFragment constructFragment7 = constructFragment(true, 4080, 240, baseLifecycleListener);
        BaseFragment constructFragment8 = constructFragment(true, FRAGMENT_SUBTITLE, 240, baseLifecycleListener);
        BaseFragment constructFragment9 = constructFragment(true, MODULE_CONTENT, 240, baseLifecycleListener);
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        this.animationComposite.put(constructFragment.getFragmentInto(), constructFragment);
        beginTransaction.add(constructFragment, constructFragment.getFragmentTag());
        beginTransaction.replace(R.id.bottom_popup_tips, constructFragment4, constructFragment4.getFragmentTag());
        beginTransaction.replace(R.id.bottom_action, constructFragment5, constructFragment5.getFragmentTag());
        beginTransaction.replace(R.id.main_top, constructFragment2, constructFragment2.getFragmentTag());
        beginTransaction.replace(R.id.bottom_popup_manually, constructFragment3, constructFragment3.getFragmentTag());
        beginTransaction.replace(R.id.main_content, constructFragment6, constructFragment6.getFragmentTag());
        beginTransaction.replace(R.id.panorama_content, constructFragment7, constructFragment7.getFragmentTag());
        beginTransaction.replace(R.id.main_subtitle, constructFragment8, constructFragment8.getFragmentTag());
        beginTransaction.replace(R.id.module_content, constructFragment9, constructFragment9.getFragmentTag());
        if (DataRepository.dataItemFeature().Gj()) {
            BaseFragment constructFragment10 = constructFragment(true, FRAGMENT_ID_CARD, 240, baseLifecycleListener);
            beginTransaction.replace(R.id.id_card_content, constructFragment10, constructFragment10.getFragmentTag());
            this.animationComposite.put(constructFragment10.getFragmentInto(), constructFragment10);
            this.originalFragments.put(R.id.id_card_content, constructFragment10.getFragmentInto());
        }
        BaseFragment baseFragment = null;
        if (c.isSupportedOpticalZoom() || HybridZoomingSystem.IS_3_OR_MORE_SAT || DataRepository.dataItemFeature().isSupportBokehAdjust()) {
            baseFragment = constructFragment(true, 4084, 240, baseLifecycleListener);
        } else if (c.Wn()) {
            baseFragment = constructFragment(true, 4085, 240, baseLifecycleListener);
        }
        if (baseFragment != null) {
            this.originalFragments.put(R.id.bottom_popup_dual_camera_adjust, baseFragment.getFragmentInto());
            this.animationComposite.put(baseFragment.getFragmentInto(), baseFragment);
            beginTransaction.replace(R.id.bottom_popup_dual_camera_adjust, baseFragment, baseFragment.getFragmentTag());
        } else {
            this.originalFragments.put(R.id.bottom_popup_dual_camera_adjust, 240);
        }
        this.originalFragments.put(R.id.bottom_popup_tips, constructFragment4.getFragmentInto());
        this.originalFragments.put(R.id.bottom_action, constructFragment5.getFragmentInto());
        this.originalFragments.put(R.id.main_top, constructFragment2.getFragmentInto());
        this.originalFragments.put(R.id.bottom_popup_manually, constructFragment3.getFragmentInto());
        this.originalFragments.put(R.id.main_content, constructFragment6.getFragmentInto());
        this.originalFragments.put(R.id.panorama_content, constructFragment7.getFragmentInto());
        this.originalFragments.put(R.id.bottom_beauty, 240);
        this.originalFragments.put(R.id.main_subtitle, 240);
        this.originalFragments.put(R.id.mimoji_full_screen, 240);
        this.originalFragments.put(R.id.module_content, 240);
        this.animationComposite.put(constructFragment4.getFragmentInto(), constructFragment4);
        this.animationComposite.put(constructFragment2.getFragmentInto(), constructFragment2);
        this.animationComposite.put(constructFragment3.getFragmentInto(), constructFragment3);
        this.animationComposite.put(constructFragment6.getFragmentInto(), constructFragment6);
        this.animationComposite.put(constructFragment5.getFragmentInto(), constructFragment5);
        this.animationComposite.put(constructFragment7.getFragmentInto(), constructFragment7);
        this.animationComposite.put(constructFragment8.getFragmentInto(), constructFragment8);
        if (DataRepository.dataItemFeature().vk()) {
            BaseFragment constructFragment11 = constructFragment(true, 4094, 240, baseLifecycleListener);
            beginTransaction.replace(R.id.wideselfie_content, constructFragment11, constructFragment11.getFragmentTag());
            this.animationComposite.put(constructFragment11.getFragmentInto(), constructFragment11);
        }
        initCurrentFragments(this.originalFragments);
        beginTransaction.commitAllowingStateLoss();
        baseLifecycleListener.onLifeAlive();
    }

    public void lazyLoadFragment(@IdRes int i, int i2) {
        if (this.originalFragments.get(i) != i2) {
            FragmentTransaction beginTransaction = this.mSupportFragmentManager.beginTransaction();
            BaseFragment constructFragment = constructFragment(true, i2, 240, this.mBaseLifecycleListener);
            beginTransaction.replace(i, constructFragment, constructFragment.getFragmentTag());
            this.originalFragments.put(i, constructFragment.getFragmentInto());
            this.animationComposite.put(constructFragment.getFragmentInto(), constructFragment);
            updateCurrentFragments(i, constructFragment.getFragmentInto(), 1);
            beginTransaction.commitAllowingStateLoss();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(160, this);
    }

    public List<BaseFragmentOperation> resetFeatureFragment(List<BaseFragmentOperation> list) {
        List<BaseFragmentOperation> list2 = this.mLastFragmentAlias;
        if (list2 == null) {
            return null;
        }
        ArrayList arrayList = null;
        for (BaseFragmentOperation baseFragmentOperation : list2) {
            int i = baseFragmentOperation.containerViewId;
            int originalFragment = getOriginalFragment(i);
            boolean z = false;
            if (list != null) {
                Iterator<BaseFragmentOperation> it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next().saveWith(baseFragmentOperation)) {
                            z = true;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            if (!z) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                if (originalFragment == 240) {
                    arrayList.add(BaseFragmentOperation.create(i).popAndClearOthers(4090));
                } else {
                    arrayList.add(BaseFragmentOperation.create(i).popAndClearOthers(originalFragment));
                }
            }
        }
        this.mLastFragmentAlias = null;
        return arrayList;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        List<BaseFragmentOperation> resetFeatureFragment;
        ModeCoordinatorImpl.getInstance().detachProtocol(160, this);
        Camera camera = this.mActivity;
        if (!(camera == null || camera.isFinishing() || (resetFeatureFragment = resetFeatureFragment(null)) == null)) {
            applyUpdateSet(resetFeatureFragment, null);
        }
        this.animationComposite.destroy();
        this.mActivity = null;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseDelegate
    public void updateCurrentFragments(@IdRes int i, int i2, int i3) {
        List<Integer> list = this.currentFragments.get(i);
        int i4 = 0;
        switch (i3) {
            case 1:
                if (list == null) {
                    list = new ArrayList<>();
                    this.currentFragments.put(i, list);
                } else {
                    list.clear();
                }
                list.add(Integer.valueOf(i2));
                return;
            case 2:
                while (i4 < list.size()) {
                    if (list.get(i4).intValue() == i2) {
                        list.remove(i4);
                        return;
                    }
                    i4++;
                }
                return;
            case 3:
                while (i4 < list.size()) {
                    if (list.get(i4).intValue() == i2) {
                        list.remove(i4);
                        return;
                    }
                    i4++;
                }
                return;
            case 4:
                list.add(Integer.valueOf(i2));
                return;
            case 5:
                list.clear();
                list.add(Integer.valueOf(i2));
                return;
            case 6:
                while (true) {
                    if (i4 < list.size()) {
                        if (list.get(i4).intValue() == i2) {
                            list.remove(i4);
                        } else {
                            i4++;
                        }
                    }
                }
                list.add(Integer.valueOf(i2));
                return;
            case 7:
                while (i4 < list.size()) {
                    if (list.get(i4).intValue() == i2) {
                        list.remove(i4);
                        return;
                    }
                    i4++;
                }
                return;
            default:
                return;
        }
    }
}
