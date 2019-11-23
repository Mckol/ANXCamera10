package com.android.camera.fragment.mimoji;

import android.content.Context;
import android.content.res.Resources;
import com.android.camera.R;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.log.Log;
import com.android.camera.statistic.CameraStat;
import com.arcsoft.avatar.AvatarConfig;
import com.arcsoft.avatar.AvatarEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AvatarEngineManager {
    public static final String BearTemplatePath = (MimojiHelper.MODEL_PATH + "bear_v_0_0_0_5");
    public static final int CONFIGTYPE_EARRING = 18;
    public static final int CONFIGTYPE_EAR_SHAPE = 29;
    public static final int CONFIGTYPE_EYEBROW_COLOR = 20;
    public static final int CONFIGTYPE_EYEBROW_SHAPE = 30;
    public static final int CONFIGTYPE_EYEGLASS = 9;
    public static final int CONFIGTYPE_EYEGLASS_COLOR = 10;
    public static final int CONFIGTYPE_EYELASH = 19;
    public static final int CONFIGTYPE_EYE_COLOR = 4;
    public static final int CONFIGTYPE_EYE_SHAPE = 22;
    public static final int CONFIGTYPE_FACE_COLOR = 3;
    public static final int CONFIGTYPE_FEATURED_FACE = 21;
    public static final int CONFIGTYPE_FRECKLE = 7;
    public static final int CONFIGTYPE_HAIR_COLOR = 2;
    public static final int CONFIGTYPE_HAIR_STYLE = 1;
    public static final int CONFIGTYPE_HEADWEAR = 12;
    public static final int CONFIGTYPE_HEADWEAR_COLOR = 13;
    public static final int CONFIGTYPE_LENS_COLOR = 11;
    public static final int CONFIGTYPE_LIPS_COLOR = 5;
    public static final int CONFIGTYPE_MOUSE_SHAPE = 23;
    public static final int CONFIGTYPE_MUSTACHE = 16;
    public static final int CONFIGTYPE_MUSTACHE_COLOR = 17;
    public static final int CONFIGTYPE_NEVUS = 8;
    public static final int CONFIGTYPE_NOSE_SHAPE = 26;
    public static final String FACE_MODEL = (MimojiHelper.DATA_DIR + "config.txt");
    public static final String FAKE_BEAR_CONFIGPATH = "bear";
    public static final String FAKE_PIG_CONFIGPATH = "pig";
    public static final String FAKE_RABBIT_CONFIGPATH = "rabbit";
    public static final String FAKE_ROYAN_CONFIGPATH = "royan";
    public static final String PersonTemplatePath = (MimojiHelper.MODEL_PATH + "cartoon_xiaomi_v_0_0_0_27");
    public static final String PigTemplatePath = (MimojiHelper.MODEL_PATH + "pig_v_0_0_0_3");
    public static final String RabbitTemplatePath = (MimojiHelper.MODEL_PATH + "rabbit_v_0_0_0_4");
    public static final String RoyanTemplatePath = (MimojiHelper.MODEL_PATH + "royan_v_0_0_0_7");
    public static final int THUMB_HEIGHT = 200;
    public static final int THUMB_WIDTH = 200;
    public static final String TRACK_DATA = (MimojiHelper.DATA_DIR + "track_data.dat");
    public static final String TempEditConfigPath = (MimojiHelper.DATA_DIR + "edit_config.dat");
    public static final String TempOriginalConfigPath = (MimojiHelper.DATA_DIR + "origin_config.dat");
    private static AvatarEngineManager mInstance;
    private AvatarConfig.ASAvatarConfigValue mASAvatarConfigValue;
    private AvatarConfig.ASAvatarConfigValue mASAvatarConfigValueDefault;
    private boolean mAllNeedUpdate = false;
    private AvatarEngine mAvatar;
    private int mAvatarRef = 0;
    private Map<Integer, LinearLayoutManagerWrapper> mColorLayoutManagerMap = new ConcurrentHashMap();
    private Map<Integer, ArrayList<AvatarConfig.ASAvatarConfigInfo>> mConfigMap = new ConcurrentHashMap();
    private Map<Integer, Float> mInnerConfigSelectMap = new ConcurrentHashMap();
    private Map<Integer, Integer> mInterruptMap = new ConcurrentHashMap();
    private boolean mIsColorSelected = false;
    private Map<Integer, Boolean> mNeedUpdateMap = new ConcurrentHashMap();
    private int mSelectTabIndex = 0;
    private int mSelectType = 0;
    private CopyOnWriteArrayList<MimojiLevelBean> mSubConfigs = new CopyOnWriteArrayList<>();
    private ArrayList<AvatarConfig.ASAvatarConfigType> mTypeList = new ArrayList<>();

    public static boolean filterTypeTitle(int i) {
        if (i == 1 || i == 12 || i == 16 || i == 30) {
            return false;
        }
        switch (i) {
            case 8:
            case 9:
                return false;
            default:
                switch (i) {
                    case 21:
                    case 22:
                    case 23:
                        return false;
                    default:
                        return true;
                }
        }
    }

    public static synchronized AvatarEngineManager getInstance() {
        AvatarEngineManager avatarEngineManager;
        synchronized (AvatarEngineManager.class) {
            if (mInstance == null) {
                mInstance = new AvatarEngineManager();
            }
            avatarEngineManager = mInstance;
        }
        return avatarEngineManager;
    }

    public static Map<String, String> getMimojiConfigValue(AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        HashMap hashMap = new HashMap();
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_HARISTYLE, String.valueOf(aSAvatarConfigValue.configHairStyleID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_FEATURE_FACE, String.valueOf(aSAvatarConfigValue.configFaceShapeID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_EYE_SHAPE, String.valueOf(aSAvatarConfigValue.configEyeShapeID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_MOUTH_SHAPE, String.valueOf(aSAvatarConfigValue.configMouthShapeID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_MUSTACHE, String.valueOf(aSAvatarConfigValue.configBeardStyleID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_FRECKLE, String.valueOf(aSAvatarConfigValue.configFrecklesID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_EYEGLASS, String.valueOf(aSAvatarConfigValue.configEyewearStyleID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_HEADWEAR, String.valueOf(aSAvatarConfigValue.configHeadwearStyleID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_EAR, String.valueOf(aSAvatarConfigValue.configEarShapeID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_EYELASH, String.valueOf(aSAvatarConfigValue.configEyelashStyleID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_EYEBROW_SHAPE, String.valueOf(aSAvatarConfigValue.configEyebrowShapeID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_NOSE, String.valueOf(aSAvatarConfigValue.configNoseShapeID));
        hashMap.put(CameraStat.PARAM_MIMOJI_CONFIG_EARING, String.valueOf(aSAvatarConfigValue.configEarringStyleID));
        return hashMap;
    }

    public static boolean isPrefabModel(String str) {
        return str.equals(FAKE_PIG_CONFIGPATH) || str.equals(FAKE_BEAR_CONFIGPATH) || str.equals(FAKE_ROYAN_CONFIGPATH) || str.equals(FAKE_RABBIT_CONFIGPATH);
    }

    public static String replaceTabTitle(Context context, int i) {
        Resources resources = context.getResources();
        if (i == 1) {
            return resources.getString(R.string.mimoji_hairstyle);
        }
        if (i == 12) {
            return resources.getString(R.string.mimoji_ornament);
        }
        if (i == 16) {
            return resources.getString(R.string.mimoji_mustache);
        }
        if (i == 30) {
            return resources.getString(R.string.mimoji_eyebrow);
        }
        switch (i) {
            case 8:
                return resources.getString(R.string.mimoji_freckle);
            case 9:
                return resources.getString(R.string.mimoji_eyeglass);
            default:
                switch (i) {
                    case 21:
                        return resources.getString(R.string.mimoji_featured_face);
                    case 22:
                        return resources.getString(R.string.mimoji_eye);
                    case 23:
                        return resources.getString(R.string.mimoji_nose_lisps);
                    default:
                        return "";
                }
        }
    }

    public static boolean showConfigTypeName(int i) {
        if (i == 1 || i == 7 || i == 9 || i == 12 || i == 16 || i == 26 || i == 30) {
            return false;
        }
        switch (i) {
            case 21:
            case 22:
                return false;
            default:
                return true;
        }
    }

    public synchronized AvatarEngine addAvatarConfig(AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo) {
        if (aSAvatarConfigInfo == null) {
            Log.d("AvatarEngineManager", "AvatarConfig.ASAvatarConfigInfo is null");
            return null;
        }
        AvatarEngine queryAvatar = queryAvatar();
        queryAvatar.setConfig(aSAvatarConfigInfo);
        return queryAvatar;
    }

    public void clear() {
        this.mSelectType = 0;
        this.mSelectTabIndex = 0;
        this.mSubConfigs.clear();
        this.mColorLayoutManagerMap.clear();
    }

    public AvatarConfig.ASAvatarConfigValue getASAvatarConfigValue() {
        return this.mASAvatarConfigValue;
    }

    public LinearLayoutManagerWrapper getColorLayoutManagerMap(int i) {
        return this.mColorLayoutManagerMap.get(Integer.valueOf(i));
    }

    public int getColorType(int i) {
        if (i == 1) {
            return 2;
        }
        if (i == 12) {
            return 13;
        }
        if (i == 16) {
            return 17;
        }
        if (i == 21) {
            return 3;
        }
        if (i != 23) {
            return i != 30 ? -1 : 20;
        }
        return 5;
    }

    public synchronized ArrayList<AvatarConfig.ASAvatarConfigInfo> getConfigList(int i) {
        return this.mConfigMap.size() <= 0 ? null : this.mConfigMap.get(Integer.valueOf(i));
    }

    public AvatarConfig.ASAvatarConfigType getConfigTypeForIndex(int i) {
        if (this.mTypeList == null || this.mTypeList.size() <= 0) {
            return null;
        }
        return this.mTypeList.get(i);
    }

    public ArrayList<AvatarConfig.ASAvatarConfigType> getConfigTypeList() {
        return this.mTypeList;
    }

    public float getInnerConfigSelectIndex(int i) {
        if (this.mInnerConfigSelectMap.get(Integer.valueOf(i)) == null) {
            return -1.0f;
        }
        return this.mInnerConfigSelectMap.get(Integer.valueOf(i)).floatValue();
    }

    public int getInterruptIndex(int i) {
        Integer num = this.mInterruptMap.get(Integer.valueOf(i));
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public ArrayList<AvatarConfig.ASAvatarConfigInfo> getSelectConfigList() {
        if (this.mConfigMap.size() <= 0) {
            return null;
        }
        return this.mConfigMap.get(Integer.valueOf(this.mSelectType));
    }

    public int getSelectType() {
        return this.mSelectType;
    }

    public int getSelectTypeIndex() {
        return this.mSelectTabIndex;
    }

    public ArrayList<AvatarConfig.ASAvatarConfigInfo> getSubConfigColorList(int i) {
        if (i == 1) {
            return getConfigList(2);
        }
        if (i == 12) {
            return getConfigList(13);
        }
        if (i == 16) {
            return getConfigList(17);
        }
        if (i == 30) {
            return getConfigList(20);
        }
        switch (i) {
            case 21:
                return getConfigList(3);
            case 22:
                return getConfigList(4);
            case 23:
                return getConfigList(5);
            default:
                return null;
        }
    }

    public CopyOnWriteArrayList<MimojiLevelBean> getSubConfigList(Context context) {
        return getSubConfigList(context, this.mSelectType);
    }

    public CopyOnWriteArrayList<MimojiLevelBean> getSubConfigList(Context context, int i) {
        this.mSubConfigs.clear();
        Resources resources = context.getResources();
        if (i == 1) {
            MimojiLevelBean mimojiLevelBean = new MimojiLevelBean();
            mimojiLevelBean.thumnails = getConfigList(1);
            if (mimojiLevelBean.thumnails != null && mimojiLevelBean.thumnails.size() > 0) {
                mimojiLevelBean.configType = 1;
                mimojiLevelBean.configTypeName = resources.getString(R.string.mimoji_hairstyle);
                this.mSubConfigs.add(mimojiLevelBean);
            }
        } else if (i == 12) {
            MimojiLevelBean mimojiLevelBean2 = new MimojiLevelBean();
            mimojiLevelBean2.thumnails = getConfigList(12);
            if (mimojiLevelBean2.thumnails != null && mimojiLevelBean2.thumnails.size() > 0) {
                mimojiLevelBean2.configType = 12;
                mimojiLevelBean2.configTypeName = resources.getString(R.string.mimoji_headwear);
                this.mSubConfigs.add(mimojiLevelBean2);
            }
            MimojiLevelBean mimojiLevelBean3 = new MimojiLevelBean();
            mimojiLevelBean3.thumnails = getConfigList(18);
            if (mimojiLevelBean3.thumnails != null && mimojiLevelBean3.thumnails.size() > 0) {
                mimojiLevelBean3.configType = 18;
                mimojiLevelBean3.configTypeName = resources.getString(R.string.mimoji_earring);
                this.mSubConfigs.add(mimojiLevelBean3);
            }
        } else if (i == 16) {
            MimojiLevelBean mimojiLevelBean4 = new MimojiLevelBean();
            mimojiLevelBean4.thumnails = getConfigList(16);
            if (mimojiLevelBean4.thumnails != null && mimojiLevelBean4.thumnails.size() > 0) {
                mimojiLevelBean4.configType = 16;
                mimojiLevelBean4.configTypeName = resources.getString(R.string.mimoji_mustache);
                this.mSubConfigs.add(mimojiLevelBean4);
            }
        } else if (i != 30) {
            switch (i) {
                case 8:
                    MimojiLevelBean mimojiLevelBean5 = new MimojiLevelBean();
                    mimojiLevelBean5.thumnails = getConfigList(7);
                    if (mimojiLevelBean5.thumnails != null && mimojiLevelBean5.thumnails.size() > 0) {
                        mimojiLevelBean5.configType = 7;
                        mimojiLevelBean5.configTypeName = resources.getString(R.string.mimoji_freckle);
                        this.mSubConfigs.add(mimojiLevelBean5);
                    }
                    MimojiLevelBean mimojiLevelBean6 = new MimojiLevelBean();
                    mimojiLevelBean6.thumnails = getConfigList(8);
                    if (mimojiLevelBean6.thumnails != null && mimojiLevelBean6.thumnails.size() > 0) {
                        mimojiLevelBean6.configType = 8;
                        mimojiLevelBean6.configTypeName = resources.getString(R.string.mimoji_mole);
                        this.mSubConfigs.add(mimojiLevelBean6);
                        break;
                    }
                case 9:
                    MimojiLevelBean mimojiLevelBean7 = new MimojiLevelBean();
                    mimojiLevelBean7.thumnails = getConfigList(9);
                    if (mimojiLevelBean7.thumnails != null && mimojiLevelBean7.thumnails.size() > 0) {
                        mimojiLevelBean7.configType = 9;
                        mimojiLevelBean7.configTypeName = resources.getString(R.string.mimoji_eyeglass);
                        this.mSubConfigs.add(mimojiLevelBean7);
                        break;
                    }
                default:
                    switch (i) {
                        case 21:
                            MimojiLevelBean mimojiLevelBean8 = new MimojiLevelBean();
                            mimojiLevelBean8.thumnails.addAll(getConfigList(21));
                            if (mimojiLevelBean8.thumnails != null && mimojiLevelBean8.thumnails.size() > 0) {
                                mimojiLevelBean8.configType = 21;
                                mimojiLevelBean8.configTypeName = resources.getString(R.string.mimoji_featured_face);
                                this.mSubConfigs.add(mimojiLevelBean8);
                            }
                            MimojiLevelBean mimojiLevelBean9 = new MimojiLevelBean();
                            mimojiLevelBean9.thumnails = getConfigList(29);
                            if (mimojiLevelBean9.thumnails != null && mimojiLevelBean9.thumnails.size() > 0) {
                                mimojiLevelBean9.configType = 29;
                                mimojiLevelBean9.configTypeName = resources.getString(R.string.mimoji_ear);
                                this.mSubConfigs.add(mimojiLevelBean9);
                                break;
                            }
                        case 22:
                            MimojiLevelBean mimojiLevelBean10 = new MimojiLevelBean();
                            mimojiLevelBean10.thumnails = getConfigList(22);
                            if (mimojiLevelBean10.thumnails != null && mimojiLevelBean10.thumnails.size() > 0) {
                                mimojiLevelBean10.configType = 22;
                                mimojiLevelBean10.configTypeName = resources.getString(R.string.mimoji_eye_shape);
                                this.mSubConfigs.add(mimojiLevelBean10);
                            }
                            MimojiLevelBean mimojiLevelBean11 = new MimojiLevelBean();
                            mimojiLevelBean11.thumnails = getConfigList(19);
                            if (mimojiLevelBean11.thumnails != null && mimojiLevelBean11.thumnails.size() > 0) {
                                mimojiLevelBean11.configType = 19;
                                mimojiLevelBean11.configTypeName = resources.getString(R.string.mimoji_eyelash);
                                this.mSubConfigs.add(mimojiLevelBean11);
                                break;
                            }
                        case 23:
                            MimojiLevelBean mimojiLevelBean12 = new MimojiLevelBean();
                            mimojiLevelBean12.thumnails = getConfigList(26);
                            if (mimojiLevelBean12.thumnails != null && mimojiLevelBean12.thumnails.size() > 0) {
                                mimojiLevelBean12.configType = 26;
                                mimojiLevelBean12.configTypeName = resources.getString(R.string.mimoji_nose);
                                this.mSubConfigs.add(mimojiLevelBean12);
                            }
                            MimojiLevelBean mimojiLevelBean13 = new MimojiLevelBean();
                            mimojiLevelBean13.thumnails = getConfigList(23);
                            if (mimojiLevelBean13.thumnails != null && mimojiLevelBean13.thumnails.size() > 0) {
                                mimojiLevelBean13.configType = 23;
                                mimojiLevelBean13.configTypeName = resources.getString(R.string.mimoji_mouth_type);
                                this.mSubConfigs.add(mimojiLevelBean13);
                                break;
                            }
                    }
                    break;
            }
        } else {
            MimojiLevelBean mimojiLevelBean14 = new MimojiLevelBean();
            mimojiLevelBean14.thumnails = getConfigList(30);
            if (mimojiLevelBean14.thumnails != null && mimojiLevelBean14.thumnails.size() > 0) {
                mimojiLevelBean14.configType = 30;
                mimojiLevelBean14.configTypeName = resources.getString(R.string.mimoji_eyebrow_shape);
                this.mSubConfigs.add(mimojiLevelBean14);
            }
        }
        return this.mSubConfigs;
    }

    public void initUpdatePara() {
        this.mInterruptMap.clear();
        this.mNeedUpdateMap.clear();
        this.mAllNeedUpdate = true;
    }

    public boolean isColorSelected() {
        return this.mIsColorSelected;
    }

    public boolean isNeedUpdate(int i) {
        Boolean bool = this.mNeedUpdateMap.get(Integer.valueOf(i));
        if (bool != null) {
            return bool.booleanValue() || this.mAllNeedUpdate;
        }
        this.mNeedUpdateMap.put(Integer.valueOf(i), false);
        return true;
    }

    public void putColorLayoutManagerMap(int i, LinearLayoutManagerWrapper linearLayoutManagerWrapper) {
        this.mColorLayoutManagerMap.put(Integer.valueOf(i), linearLayoutManagerWrapper);
    }

    public void putConfigList(int i, ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList) {
        if (!this.mConfigMap.containsKey(Integer.valueOf(i))) {
            this.mConfigMap.put(Integer.valueOf(i), arrayList);
        }
    }

    public synchronized AvatarEngine queryAvatar() {
        if (this.mAvatar == null) {
            Log.d("AvatarEngineManager", "avatar create");
            this.mAvatar = new AvatarEngine();
        }
        this.mAvatarRef++;
        return this.mAvatar;
    }

    public synchronized void releaseAvatar() {
        Log.d("AvatarEngineManager", "avatar destroy");
        this.mAvatar.destroy();
        this.mAvatar = null;
    }

    public void resetData() {
        this.mInnerConfigSelectMap.clear();
        this.mASAvatarConfigValue = (AvatarConfig.ASAvatarConfigValue) this.mASAvatarConfigValueDefault.clone();
        setASAvatarConfigValue(this.mASAvatarConfigValue);
        initUpdatePara();
    }

    public void setASAvatarConfigValue(AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        this.mASAvatarConfigValue = aSAvatarConfigValue;
        if (aSAvatarConfigValue != null) {
            String str = FragmentMimojiEdit.TAG;
            Log.i(str, "value 属性:gender = " + aSAvatarConfigValue.gender + " configHairStyleID = " + aSAvatarConfigValue.configHairStyleID + " configHairColorID = " + aSAvatarConfigValue.configHairColorID + " configHairColorValue = " + aSAvatarConfigValue.configHairColorValue + " configFaceColorID = " + aSAvatarConfigValue.configFaceColorID + " configFaceColorValue = " + aSAvatarConfigValue.configFaceColorValue + " configEyeColorID = " + aSAvatarConfigValue.configEyeColorID + " configEyeColorValue = " + aSAvatarConfigValue.configEyeColorValue + " configLipColorID = " + aSAvatarConfigValue.configLipColorID + " configLipColorValue = " + aSAvatarConfigValue.configLipColorValue + " configHairHighlightColorID = " + aSAvatarConfigValue.configHairHighlightColorID + " configHairHighlightColorValue = " + aSAvatarConfigValue.configHairHighlightColorValue + " configFrecklesID = " + aSAvatarConfigValue.configFrecklesID + " configNevusID = " + aSAvatarConfigValue.configNevusID + " configEyewearStyleID = " + aSAvatarConfigValue.configEyewearStyleID + " configEyewearFrameID = " + aSAvatarConfigValue.configEyewearFrameID + " configEyewearFrameValue = " + aSAvatarConfigValue.configEyewearFrameValue + " configEyewearLensesID = " + aSAvatarConfigValue.configEyewearLensesID + " configEyewearLensesValue = " + aSAvatarConfigValue.configEyewearLensesValue + " configHeadwearStyleID = " + aSAvatarConfigValue.configHeadwearStyleID + " configHeadwearColorID = " + aSAvatarConfigValue.configHeadwearColorID + " configHeadwearColorValue = " + aSAvatarConfigValue.configHeadwearColorValue + " configBeardStyleID = " + aSAvatarConfigValue.configBeardStyleID + " configBeardColorID = " + aSAvatarConfigValue.configBeardColorID + " configBeardColorValue = " + aSAvatarConfigValue.configBeardColorValue + " configEarringStyleID = " + aSAvatarConfigValue.configEarringStyleID + " configEyelashStyleID = " + aSAvatarConfigValue.configEyelashStyleID + " configEyebrowColorID = " + aSAvatarConfigValue.configEyebrowColorID + " configEyebrowColorValue = " + aSAvatarConfigValue.configEyebrowColorValue + " configFaceShapeID = " + aSAvatarConfigValue.configFaceShapeID + " configFaceShapeValue = " + aSAvatarConfigValue.configFaceShapeValue + " configEyeShapeID = " + aSAvatarConfigValue.configEyeShapeID + " configEyeShapeValue = " + aSAvatarConfigValue.configEyeShapeValue + " configMouthShapeID = " + aSAvatarConfigValue.configMouthShapeID + " configMouthShapeValue = " + aSAvatarConfigValue.configMouthShapeValue + " configNoseShapeID = " + aSAvatarConfigValue.configNoseShapeID + " configNoseShapeValue = " + aSAvatarConfigValue.configNoseShapeValue + " configEarShapeID = " + aSAvatarConfigValue.configEarShapeID + " configEarShapeValue = " + aSAvatarConfigValue.configEarShapeValue + " configEyebrowShapeID = " + aSAvatarConfigValue.configEyebrowShapeID + " configEyebrowShapeValue = " + aSAvatarConfigValue.configEyebrowShapeValue);
            this.mInnerConfigSelectMap.put(1, Float.valueOf((float) aSAvatarConfigValue.configHairStyleID));
            this.mInnerConfigSelectMap.put(2, Float.valueOf((float) aSAvatarConfigValue.configHairColorID));
            this.mInnerConfigSelectMap.put(3, Float.valueOf((float) aSAvatarConfigValue.configFaceColorID));
            this.mInnerConfigSelectMap.put(21, Float.valueOf((float) aSAvatarConfigValue.configFaceShapeID));
            this.mInnerConfigSelectMap.put(4, Float.valueOf(aSAvatarConfigValue.configEyeColorValue));
            this.mInnerConfigSelectMap.put(5, Float.valueOf((float) aSAvatarConfigValue.configLipColorID));
            this.mInnerConfigSelectMap.put(7, Float.valueOf((float) aSAvatarConfigValue.configFrecklesID));
            this.mInnerConfigSelectMap.put(8, Float.valueOf((float) aSAvatarConfigValue.configNevusID));
            this.mInnerConfigSelectMap.put(9, Float.valueOf((float) aSAvatarConfigValue.configEyewearStyleID));
            this.mInnerConfigSelectMap.put(16, Float.valueOf((float) aSAvatarConfigValue.configBeardStyleID));
            this.mInnerConfigSelectMap.put(17, Float.valueOf((float) aSAvatarConfigValue.configBeardColorID));
            this.mInnerConfigSelectMap.put(19, Float.valueOf((float) aSAvatarConfigValue.configEyelashStyleID));
            this.mInnerConfigSelectMap.put(20, Float.valueOf((float) aSAvatarConfigValue.configEyebrowColorID));
            this.mInnerConfigSelectMap.put(22, Float.valueOf((float) aSAvatarConfigValue.configEyeShapeID));
            this.mInnerConfigSelectMap.put(23, Float.valueOf((float) aSAvatarConfigValue.configMouthShapeID));
            this.mInnerConfigSelectMap.put(26, Float.valueOf((float) aSAvatarConfigValue.configNoseShapeID));
            this.mInnerConfigSelectMap.put(29, Float.valueOf((float) aSAvatarConfigValue.configEarShapeID));
            this.mInnerConfigSelectMap.put(30, Float.valueOf((float) aSAvatarConfigValue.configEyebrowShapeID));
            this.mInnerConfigSelectMap.put(13, Float.valueOf((float) aSAvatarConfigValue.configHeadwearColorID));
            this.mInnerConfigSelectMap.put(12, Float.valueOf((float) aSAvatarConfigValue.configHeadwearStyleID));
        }
    }

    public void setASAvatarConfigValueDefault(AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        this.mASAvatarConfigValueDefault = (AvatarConfig.ASAvatarConfigValue) aSAvatarConfigValue.clone();
    }

    public void setAllNeedUpdate(boolean z, boolean z2) {
        this.mAllNeedUpdate = z;
        this.mIsColorSelected = z2;
        this.mInterruptMap.clear();
    }

    public void setConfigTypeList(ArrayList<AvatarConfig.ASAvatarConfigType> arrayList) {
        this.mTypeList = arrayList;
    }

    public void setInnerConfigSelectIndex(int i, float f) {
        this.mInnerConfigSelectMap.put(Integer.valueOf(i), Float.valueOf(f));
    }

    public void setInterruptIndex(int i, int i2) {
        this.mInterruptMap.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public void setIsColorSelected(boolean z) {
        this.mIsColorSelected = z;
    }

    public void setSelectType(int i) {
        this.mSelectType = i;
    }

    public void setSelectTypeIndex(int i) {
        this.mSelectTabIndex = i;
    }

    public void setTypeNeedUpdate(int i, boolean z) {
        this.mNeedUpdateMap.put(Integer.valueOf(i), Boolean.valueOf(z));
    }
}
