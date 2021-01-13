package com.arcsoft.avatar2;

import android.text.TextUtils;
import com.arcsoft.avatar2.AvatarConfig;
import com.arcsoft.avatar2.extrascene.ExtraSceneInfo;
import com.arcsoft.avatar2.util.ASVLOFFSCREEN;
import com.arcsoft.avatar2.util.LOG;
import com.arcsoft.avatar2.util.TimeConsumingUtil;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class AvatarEngine implements AvatarConfig {

    /* renamed from: a  reason: collision with root package name */
    private static final String f158a = "AvatarEngine";

    /* renamed from: b  reason: collision with root package name */
    private int f159b = -1;

    /* renamed from: c  reason: collision with root package name */
    private int f160c = -1;

    /* renamed from: d  reason: collision with root package name */
    private TreeMap<String, AvatarConfig.ASAvatarConfigInfo> f161d = new TreeMap<>();

    /* renamed from: e  reason: collision with root package name */
    private AvatarConfig.ASAvatarConfigValue f162e = new AvatarConfig.ASAvatarConfigValue();
    private long f = 0;
    private String g = "";

    static {
        System.loadLibrary("mimoji_jni");
        System.loadLibrary("mimoji_avatarengine");
    }

    private int a(String str) {
        return nativeSetHeadWearGrayTexture(this.f, str);
    }

    private native int nativeAvatarProcess(long j, int i, int i2, int i3, byte[] bArr, byte[] bArr2, int i4, boolean z, int i5);

    private native int nativeAvatarProcessEx(long j, int i, int i2, int i3, ByteBuffer[] byteBufferArr, byte[] bArr, int i4, boolean z, int i5);

    private native int nativeAvatarProcessEx2(long j, ASVLOFFSCREEN asvloffscreen, byte[] bArr, int i, boolean z, int i2);

    private native int nativeAvatarProfile(long j, String str, int i, int i2, int i3, byte[] bArr, int i4, boolean z, AvatarConfig.ASAvatarProfileResult aSAvatarProfileResult, AvatarConfig.ASAvatarProfileInfo aSAvatarProfileInfo, AvatarConfig.UpdateProgressCallback updateProgressCallback);

    private native int nativeAvatarRender(long j, int i, int i2, int i3, int i4, boolean z, int[] iArr);

    private native long nativeCreate();

    private native int nativeDestroy(long j);

    private native int nativeGetConfig(long j, int i, int i2, AvatarConfig.GetConfigCallback getConfigCallback);

    private native int nativeGetConfigValue(long j, AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue);

    private native int nativeGetSupportConfigType(long j, int i, AvatarConfig.GetSupportConfigTypeCallback getSupportConfigTypeCallback);

    private native String nativeGetVersion();

    private native int nativeInit(long j, String str, String str2);

    private native boolean nativeIsSupportSwitchGender(long j);

    private native int nativeLoadColorValue(String str);

    private native int nativeLoadConfig(long j, String str);

    private native int nativeOutlineCreateEngine(long j, String str);

    private native int nativeOutlineDestroyEngine(long j);

    private native int nativeOutlineProcess(long j, byte[] bArr, int i, int i2, int i3, int i4, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo);

    private native int nativeOutlineProcessEx(long j, ASVLOFFSCREEN asvloffscreen, int i, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo);

    private native int nativeProcessOutlineExpression(long j, byte[] bArr, int i, int i2, int i3, int i4, boolean z, int i5, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo);

    private native int nativeProcessWithInfo(long j, byte[] bArr, int i, int i2, int i3, int i4, boolean z, int i5, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo);

    private native int nativeProcessWithInfoToEdit(long j, ASVLOFFSCREEN asvloffscreen, int i, boolean z, int i2, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo, boolean z2);

    private native boolean nativeProcessWithInfoToPreview(long j, ASVLOFFSCREEN asvloffscreen, int i, boolean z, int i2, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo, boolean z2);

    private native int nativeReadRGBA(long j, String str, int i, int i2, byte[] bArr);

    private native int nativeReleaseRender(long j);

    private native int nativeRenderBackgroundWithImageData(long j, ASVLOFFSCREEN asvloffscreen, int i, boolean z, int[] iArr);

    private native int nativeRenderBackgroundWithTexture(long j, int i, int i2, boolean z, float[] fArr);

    private native int nativeRenderThumb(long j, int i, int i2, int i3, int i4, byte[] bArr, int i5, int i6, int i7, float[] fArr, float f2);

    private native int nativeRenderWithBackground(long j, String str, ASVLOFFSCREEN asvloffscreen, int i, boolean z, int i2, int i3, int i4, int i5, boolean z2, int[] iArr, byte[] bArr, boolean z3);

    private native int nativeSaveConfig(long j, String str);

    private native int nativeSetAnimationParam(long j, boolean z, int i);

    private native int nativeSetConfig(long j, AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo);

    private native int nativeSetHairRenderParam(long j, boolean z, boolean z2);

    private native int nativeSetHeadWearGrayTexture(long j, String str);

    private native int nativeSetHeadWearTexture(long j, String str);

    private native int nativeSetProcessInfo(long j, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo);

    private native int nativeSetRenderScene(long j, boolean z, float f2, float[] fArr);

    private native int nativeSetTemplate(long j, String str);

    private native int nativeSetTongueAnimationParam(long j, AvatarConfig.ASAvatarTongueAnimationParam aSAvatarTongueAnimationParam);

    private native int nativeSetToothTexture(long j, String str);

    private native int nativeSwitchGender(long j, boolean z);

    private native int nativeUnInit(long j);

    public synchronized int avatarProcess(int i, int i2, int i3, byte[] bArr, byte[] bArr2, int i4, boolean z, int i5) {
        return nativeAvatarProcess(this.f, i, i2, i3, bArr, bArr2, i4, z, i5);
    }

    public synchronized int avatarProcessEx(int i, int i2, int i3, ByteBuffer[] byteBufferArr, byte[] bArr, int i4, boolean z, int i5) {
        return nativeAvatarProcessEx(this.f, i, i2, i3, byteBufferArr, bArr, i4, z, i5);
    }

    public synchronized int avatarProcessEx2(ASVLOFFSCREEN asvloffscreen, byte[] bArr, int i, boolean z, int i2) {
        return nativeAvatarProcessEx2(this.f, asvloffscreen, bArr, i, z, i2);
    }

    public int avatarProcessWithInfo(byte[] bArr, int i, int i2, int i3, int i4, boolean z, int i5, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        TimeConsumingUtil.startTheTimer("avatarProcessWithInfo");
        int nativeProcessWithInfo = nativeProcessWithInfo(this.f, bArr, i, i2, i3, i4, z, i5, aSAvatarProcessInfo);
        TimeConsumingUtil.stopTiming("performance", "avatarProcessWithInfo");
        return nativeProcessWithInfo;
    }

    public int avatarProcessWithInfoEx(ASVLOFFSCREEN asvloffscreen, int i, boolean z, int i2, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo, boolean z2) {
        return nativeProcessWithInfoToEdit(this.f, asvloffscreen, i, z, i2, aSAvatarProcessInfo, z2);
    }

    public synchronized int avatarProfile(String str, int i, int i2, int i3, byte[] bArr, int i4, boolean z, AvatarConfig.ASAvatarProfileResult aSAvatarProfileResult, AvatarConfig.ASAvatarProfileInfo aSAvatarProfileInfo, AvatarConfig.UpdateProgressCallback updateProgressCallback) {
        return nativeAvatarProfile(this.f, str, i, i2, i3, bArr, i4, z, aSAvatarProfileResult, aSAvatarProfileInfo, updateProgressCallback);
    }

    public synchronized void avatarRender(int i, int i2, int i3, int i4, boolean z, int[] iArr) {
        nativeAvatarRender(this.f, i, i2, i3, i4, z, iArr);
    }

    public int checkOutlineInfo(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        if (aSAvatarProcessInfo == null) {
            LOG.d("CheckOutLine", "null");
            return 1;
        }
        LOG.d("CheckOutLine", "faceCount = " + aSAvatarProcessInfo.getFaceCount());
        if (aSAvatarProcessInfo.getFaceCount() > 1) {
            return 10;
        }
        if (aSAvatarProcessInfo.shelterIsNull()) {
            LOG.d("CheckOutLine", "shelterFlags == null");
            return 1;
        } else if (aSAvatarProcessInfo.getFaceCount() <= 0) {
            return 1;
        } else {
            return aSAvatarProcessInfo.checkOutLineInfo();
        }
    }

    public synchronized int createOutlineEngine(String str) {
        return nativeOutlineCreateEngine(this.f, str);
    }

    public synchronized void destroy() {
        TimeConsumingUtil.startTheTimer("destroy");
        nativeDestroy(this.f);
        this.f = 0;
        TimeConsumingUtil.stopTiming("performance", "destroy");
    }

    public synchronized int destroyOutlineEngine() {
        return nativeOutlineDestroyEngine(this.f);
    }

    public synchronized ArrayList<AvatarConfig.ASAvatarConfigInfo> getConfig(final int i, int i2) {
        final ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList;
        arrayList = new ArrayList<>();
        nativeGetConfig(this.f, i, i2, new AvatarConfig.GetConfigCallback() {
            /* class com.arcsoft.avatar2.AvatarEngine.AnonymousClass2 */

            @Override // com.arcsoft.avatar2.AvatarConfig.GetConfigCallback
            public void onGetConfig(int i, int i2, int i3, int i4, String str, String str2, int i5, int i6, boolean z, boolean z2, boolean z3, float f) {
                AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo = new AvatarConfig.ASAvatarConfigInfo();
                aSAvatarConfigInfo.configID = i;
                aSAvatarConfigInfo.configType = i3;
                aSAvatarConfigInfo.gender = i4;
                aSAvatarConfigInfo.name = str;
                aSAvatarConfigInfo.configThumbPath = str2;
                aSAvatarConfigInfo.isDefault = z;
                aSAvatarConfigInfo.isValid = z2;
                aSAvatarConfigInfo.isSupportContinuous = z3;
                aSAvatarConfigInfo.continuousValue = f;
                aSAvatarConfigInfo.startColorValue = i5;
                aSAvatarConfigInfo.endColorValue = i6;
                if (i3 == 5 && i2 != -1) {
                    String num = new Integer(i2).toString();
                    if (!AvatarEngine.this.f161d.containsKey(num)) {
                        AvatarEngine.this.f161d.put(num, aSAvatarConfigInfo);
                    }
                    if (i2 != AvatarEngine.this.f159b) {
                        return;
                    }
                }
                arrayList.add(aSAvatarConfigInfo);
                LOG.d(AvatarEngine.f158a, "type = " + i + " info = " + aSAvatarConfigInfo.toString());
            }
        });
        return arrayList;
    }

    public synchronized void getConfigValue(AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        nativeGetConfigValue(this.f, aSAvatarConfigValue);
        this.f159b = aSAvatarConfigValue.configFaceColorID;
        this.f160c = aSAvatarConfigValue.configLipColorID;
    }

    public String getExtraSceneName() {
        AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue = this.f162e;
        if (aSAvatarConfigValue == null) {
            return "";
        }
        getConfigValue(aSAvatarConfigValue);
        int i = this.f162e.configHeadwearStyleID;
        if (i == 13) {
            return ExtraSceneInfo.RABBIT_EARS;
        }
        switch (i) {
            case 20:
                return ExtraSceneInfo.ANGEL_WINGS_CAP;
            case 21:
                return ExtraSceneInfo.CHICKEN_HAT;
            case 22:
                return ExtraSceneInfo.WIZARD_HAT;
            case 23:
                return ExtraSceneInfo.MI_RABBIT_HAT;
            default:
                switch (i) {
                    case 25:
                        return "ClownHat";
                    case 26:
                        return ExtraSceneInfo.EMOJI_HELMET;
                    case 27:
                        return ExtraSceneInfo.SHIBAINU_HAT;
                    case 28:
                        return ExtraSceneInfo.ASTRONAUT_HELMET;
                    case 29:
                        return ExtraSceneInfo.PRINCESS_HAT;
                    default:
                        return "";
                }
        }
    }

    public synchronized ArrayList<AvatarConfig.ASAvatarConfigType> getSupportConfigType(int i) {
        final ArrayList<AvatarConfig.ASAvatarConfigType> arrayList;
        arrayList = new ArrayList<>();
        nativeGetSupportConfigType(this.f, i, new AvatarConfig.GetSupportConfigTypeCallback() {
            /* class com.arcsoft.avatar2.AvatarEngine.AnonymousClass1 */

            @Override // com.arcsoft.avatar2.AvatarConfig.GetSupportConfigTypeCallback
            public void onGetSupportConfigType(String str, int i) {
                AvatarConfig.ASAvatarConfigType aSAvatarConfigType = new AvatarConfig.ASAvatarConfigType();
                aSAvatarConfigType.configType = i;
                aSAvatarConfigType.configTypeDesc = str;
                arrayList.add(aSAvatarConfigType);
            }
        });
        return arrayList;
    }

    public String getTemplatePath() {
        return this.g;
    }

    public String getVersion() {
        return nativeGetVersion();
    }

    public synchronized void init(String str, String str2) {
        TimeConsumingUtil.startTheTimer("init");
        this.f = nativeCreate();
        int nativeInit = nativeInit(this.f, str, str2);
        LOG.d(f158a, "init res = " + nativeInit);
        TimeConsumingUtil.stopTiming("performance", "init");
    }

    public synchronized boolean isSupportSwitchGender() {
        return nativeIsSupportSwitchGender(this.f);
    }

    public synchronized int loadColorValue(String str) {
        return nativeLoadColorValue(str);
    }

    public synchronized void loadConfig(String str) {
        TimeConsumingUtil.startTheTimer("loadConfig");
        nativeLoadConfig(this.f, str);
        TimeConsumingUtil.stopTiming("performance", "loadConfig");
    }

    public boolean nativeProcessWithInfoToPreview(ASVLOFFSCREEN asvloffscreen, int i, boolean z, int i2, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo, boolean z2) {
        return nativeProcessWithInfoToPreview(this.f, asvloffscreen, i, z, i2, aSAvatarProcessInfo, z2);
    }

    public synchronized int outlineProcess(byte[] bArr, int i, int i2, int i3, int i4) {
        AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo;
        aSAvatarProcessInfo = new AvatarConfig.ASAvatarProcessInfo();
        TimeConsumingUtil.startTheTimer("outlineProcess");
        int nativeOutlineProcess = nativeOutlineProcess(this.f, bArr, i, i2, i3, i4, aSAvatarProcessInfo);
        TimeConsumingUtil.stopTiming("performance", "outlineProcess");
        LOG.d("CheckOutLine", "nativeOutlineProcess = " + nativeOutlineProcess);
        return checkOutlineInfo(aSAvatarProcessInfo);
    }

    public synchronized int outlineProcessEx(ASVLOFFSCREEN asvloffscreen, int i) {
        AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo;
        aSAvatarProcessInfo = new AvatarConfig.ASAvatarProcessInfo();
        TimeConsumingUtil.startTheTimer("outlineProcessEx");
        int nativeOutlineProcessEx = nativeOutlineProcessEx(this.f, asvloffscreen, i, aSAvatarProcessInfo);
        TimeConsumingUtil.stopTiming("performance", "outlineProcessEx");
        LOG.d("CheckOutLine", "nativeOutlineProcess = " + nativeOutlineProcessEx);
        return checkOutlineInfo(aSAvatarProcessInfo);
    }

    public synchronized int processOutlineExpression(byte[] bArr, int i, int i2, int i3, int i4, boolean z, int i5, AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        return nativeProcessOutlineExpression(this.f, bArr, i, i2, i3, i4, z, i5, aSAvatarProcessInfo);
    }

    public int readRGBA(String str, int i, int i2, byte[] bArr) {
        return nativeReadRGBA(this.f, str, i, i2, bArr);
    }

    public synchronized void releaseRender() {
        TimeConsumingUtil.startTheTimer("releaseRender");
        nativeReleaseRender(this.f);
        TimeConsumingUtil.stopTiming("performance", "releaseRender");
    }

    public int renderBackgroundWithImageData(ASVLOFFSCREEN asvloffscreen, int i, boolean z, int[] iArr) {
        return nativeRenderBackgroundWithImageData(this.f, asvloffscreen, i, z, iArr);
    }

    public int renderBackgroundWithTexture(int i, int i2, boolean z, float[] fArr) {
        return nativeRenderBackgroundWithTexture(this.f, i, i2, z, fArr);
    }

    public synchronized int renderThumb(int i, int i2, int i3, int i4, byte[] bArr, int i5, int i6, int i7, float[] fArr, float f2) {
        int nativeRenderThumb;
        TimeConsumingUtil.startTheTimer("renderThumb");
        nativeRenderThumb = nativeRenderThumb(this.f, i, i2, i3, i4, bArr, i5, i6, i7, fArr, f2);
        TimeConsumingUtil.stopTiming("performance", "renderThumb");
        return nativeRenderThumb;
    }

    public int renderWithBackground(String str, ASVLOFFSCREEN asvloffscreen, int i, boolean z, int i2, int i3, int i4, int i5, boolean z2, int[] iArr, byte[] bArr, boolean z3) {
        return nativeRenderWithBackground(this.f, str, asvloffscreen, i, z, i2, i3, i4, i5, z2, iArr, bArr, z3);
    }

    public synchronized int saveConfig(String str) {
        return nativeSaveConfig(this.f, str);
    }

    public void setAnimationParam(boolean z, int i) {
        nativeSetAnimationParam(this.f, z, i);
    }

    public synchronized int setConfig(AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo) {
        if (aSAvatarConfigInfo.configType == 3) {
            this.f159b = aSAvatarConfigInfo.configID;
            String num = new Integer(this.f159b).toString();
            if (this.f161d.size() > 0 && this.f161d.containsKey(num)) {
                boolean z = false;
                Iterator<AvatarConfig.ASAvatarConfigInfo> it = this.f161d.values().iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next().configID == this.f160c) {
                            z = true;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (z) {
                    nativeSetConfig(this.f, this.f161d.get(num));
                }
            }
        } else if (aSAvatarConfigInfo.configType == 5) {
            this.f160c = aSAvatarConfigInfo.configID;
        }
        return nativeSetConfig(this.f, aSAvatarConfigInfo);
    }

    public int setHairRenderParam(boolean z, boolean z2) {
        return nativeSetHairRenderParam(this.f, z, z2);
    }

    public void setHeadWearGrayPath(AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo) {
        if (!TextUtils.isEmpty(this.g) && aSAvatarConfigInfo != null && aSAvatarConfigInfo.configType == 12 && aSAvatarConfigInfo.configID == 21) {
            a(this.g + "/cartoon_HEADWEAR/style21/Tex_21_gray_alpha20.png");
        }
    }

    public int setHeadWearTexture(String str) {
        return nativeSetHeadWearTexture(this.f, str);
    }

    public int setProcessInfo(AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo) {
        return nativeSetProcessInfo(this.f, aSAvatarProcessInfo);
    }

    public synchronized void setRenderScene(boolean z, float f2) {
        nativeSetRenderScene(this.f, z, f2, null);
    }

    public synchronized void setRenderScene3F(boolean z, float f2, float[] fArr) {
        nativeSetRenderScene(this.f, z, f2, fArr);
    }

    public synchronized void setTemplatePath(String str) {
        TimeConsumingUtil.startTheTimer("setTemplatePath");
        nativeSetTemplate(this.f, str);
        this.g = str;
        TimeConsumingUtil.stopTiming("performance", "setTemplatePath");
    }

    public int setTongueAnimationParam(AvatarConfig.ASAvatarTongueAnimationParam aSAvatarTongueAnimationParam) {
        return nativeSetTongueAnimationParam(this.f, aSAvatarTongueAnimationParam);
    }

    public int setToothTexture(String str) {
        return nativeSetToothTexture(this.f, str);
    }

    public synchronized void switchGender(boolean z) {
        nativeSwitchGender(this.f, z);
    }

    public synchronized void unInit() {
        TimeConsumingUtil.startTheTimer("unInit");
        int nativeUnInit = nativeUnInit(this.f);
        TimeConsumingUtil.stopTiming("performance", "unInit");
        LOG.d(f158a, "uninit res = " + nativeUnInit);
    }
}
