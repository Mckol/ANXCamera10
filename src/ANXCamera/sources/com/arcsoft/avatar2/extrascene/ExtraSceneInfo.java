package com.arcsoft.avatar2.extrascene;

import java.util.ArrayList;

public class ExtraSceneInfo {
    public static final String ANGEL_WINGS_CAP = "AngelWingsCap";
    public static final String ASTRONAUT_HELMET = "AstronautHelmet";
    public static final String CHICKEN_HAT = "ChickenHat";
    public static final String CLOWN_HAT = "ClownHat";
    public static final String EMOJI_HELMET = "EmojiHelmet";
    public static final String EXTRA_ANGELWINGS_NAME = "AngelWings";
    public static final String EXTRA_CLOWNHAT_NAME = "ClownHat";
    public static final String EXTRA_RABBITTEETH_NAME = "RabbitTeeth";
    public static final int EXTRA_STATUS_AGAIN = 4;
    public static final int EXTRA_STATUS_BEGIN = 1;
    public static final int EXTRA_STATUS_END = 3;
    public static final int EXTRA_STATUS_RUN = 2;
    public static final int HEAD_PITCH_STATUS_DOWN = 2;
    public static final int HEAD_PITCH_STATUS_NORMAL = 0;
    public static final int HEAD_PITCH_STATUS_UP = 1;
    public static final int LOOP_LAST_EXP_ID_STATUS = 2;
    public static final int LOOP_NORAML_STATUS = 1;
    public static final int LOOP_ONCE_STATUS = 3;
    public static final String MI_RABBIT_HAT = "MiRabbitHat";
    public static final String PRINCESS_HAT = "PrincessHat";
    public static final String RABBIT_EARS = "RabbitEars";
    public static final String SHIBAINU_HAT = "ShibaInuHat";
    public static final String WIZARD_HAT = "WizardHat";
    private ArrayList<HairMaskInfo> A = null;
    private int[] B = null;
    private int[] C = null;

    /* renamed from: a  reason: collision with root package name */
    private String f202a = "";

    /* renamed from: b  reason: collision with root package name */
    private int f203b = -1;

    /* renamed from: c  reason: collision with root package name */
    private int f204c = 0;

    /* renamed from: d  reason: collision with root package name */
    private boolean f205d = false;

    /* renamed from: e  reason: collision with root package name */
    private ArrayList<ExpressInfo> f206e = null;
    private int f = 0;
    private String g = "";
    private ArrayList<String> h = null;
    private String i = "";
    private String j = "";
    private ArrayList<int[]> k = null;
    private ArrayList<float[]> l = null;
    private int m = 0;
    private int n = 1;
    private boolean o = true;
    private int p = -1;
    private int q = 0;
    private int r = 0;
    private int s = 0;
    private int t = 0;
    private int u = 0;
    private int v = 1;
    private int w = 0;
    private int x = 0;
    private int y = -1;
    private String z = "";

    public class ExpressInfo {
        public static final int LOOP_BEGIN_FRAME = 1;
        public static final int LOOP_END_FRAME = 2;

        /* renamed from: b  reason: collision with root package name */
        private int[] f208b = null;

        /* renamed from: c  reason: collision with root package name */
        private ArrayList<Float> f209c = null;

        /* renamed from: d  reason: collision with root package name */
        private boolean f210d = false;

        /* renamed from: e  reason: collision with root package name */
        private int f211e = 0;
        private boolean f = false;

        public ExpressInfo() {
        }

        public int[] getExpressIndex() {
            return this.f208b;
        }

        public ArrayList<Float> getExpressValue() {
            return this.f209c;
        }

        public int getLoopStatus() {
            return this.f211e;
        }

        public boolean isLoopFrame() {
            return this.f210d;
        }

        public boolean isPauseFrame() {
            return this.f;
        }

        public void setExpressIndex(int[] iArr) {
            this.f208b = iArr;
        }

        public void setExpressValue(float f2) {
            if (this.f209c == null) {
                this.f209c = new ArrayList<>();
            }
            this.f209c.add(Float.valueOf(f2));
        }

        public void setLoopFrame(boolean z) {
            this.f210d = z;
        }

        public void setLoopStatus(int i) {
            this.f211e = i;
        }

        public void setPauseStatus(boolean z) {
            this.f = z;
        }
    }

    public class HairMaskInfo {

        /* renamed from: b  reason: collision with root package name */
        private int f213b = -1;

        /* renamed from: c  reason: collision with root package name */
        private int f214c = -1;

        /* renamed from: d  reason: collision with root package name */
        private boolean f215d = false;

        /* renamed from: e  reason: collision with root package name */
        private boolean f216e = false;

        public HairMaskInfo() {
        }

        public boolean getBlendShape() {
            return this.f215d;
        }

        public int getExpId() {
            return this.f214c;
        }

        public int getHairId() {
            return this.f213b;
        }

        public boolean getMask() {
            return this.f216e;
        }

        public void setBlendShape(boolean z) {
            this.f215d = z;
        }

        public void setExpId(int i) {
            this.f214c = i;
        }

        public void setHairId(int i) {
            this.f213b = i;
        }

        public void setMask(boolean z) {
            this.f216e = z;
        }

        public String toString() {
            return "mHairId = " + this.f213b + ", mExpId = " + this.f214c + ", bBlendShape = " + this.f215d + ", bMask = " + this.f216e;
        }
    }

    public String getAccPath() {
        return this.z;
    }

    public int getDuration() {
        return this.f;
    }

    public int getEmojiHelmetLeftRandomNum() {
        return this.u;
    }

    public int getEmojiHelmetRightRandomNum() {
        return this.v;
    }

    public ArrayList<int[]> getExpCheckIdList() {
        return this.k;
    }

    public ArrayList<float[]> getExpCheckValueList() {
        return this.l;
    }

    public int getExpCount() {
        return this.f204c;
    }

    public int[] getExpIdInPut() {
        return this.B;
    }

    public int[] getExpIdOutPut() {
        return this.C;
    }

    public ArrayList<ExpressInfo> getExpressInfoList() {
        return this.f206e;
    }

    public int getExtraStatus() {
        return this.n;
    }

    public ArrayList<HairMaskInfo> getHairMaskInfoList() {
        return this.A;
    }

    public int getHeadPitchStatus() {
        return this.x;
    }

    public int getHeadWear() {
        return this.f203b;
    }

    public int getHeadWearColor() {
        return this.p;
    }

    public int getLoopBeginFrameIndex() {
        return this.q;
    }

    public int getLoopCount() {
        return this.s;
    }

    public int getLoopEndFrameIndex() {
        return this.r;
    }

    public int getLoopNum() {
        return this.t;
    }

    public int getLoopType() {
        return this.m;
    }

    public String getName() {
        return this.f202a;
    }

    public ArrayList<String> getNewHeadWearPathList() {
        return this.h;
    }

    public String getNewToothPath() {
        return this.j;
    }

    public String getOldHeadWearPath() {
        return this.g;
    }

    public String getOldToothPath() {
        return this.i;
    }

    public int getPauseIndex() {
        return this.y;
    }

    public int getRandom() {
        return this.w;
    }

    public boolean isNeedOutLine() {
        return this.f205d;
    }

    public boolean isOldHeadWearAcc() {
        return this.o;
    }

    public void setAccPath(String str) {
        this.z = str;
    }

    public void setDuration(int i2) {
        this.f = i2;
    }

    public void setEmojiHelmetLeftRandomNum(int i2) {
        this.u = i2;
    }

    public void setEmojiHelmetRightRandomNum(int i2) {
        this.v = i2;
    }

    public void setExpCheckId(int[] iArr) {
        if (this.k == null) {
            this.k = new ArrayList<>();
        }
        this.k.add(iArr);
    }

    public void setExpCheckValue(float[] fArr) {
        if (this.l == null) {
            this.l = new ArrayList<>();
        }
        this.l.add(fArr);
    }

    public void setExpCount(int i2) {
        this.f204c = i2;
    }

    public void setExpIdInPut(int[] iArr) {
        this.B = iArr;
    }

    public void setExpIdOutPut(int[] iArr) {
        this.C = iArr;
    }

    public void setExpressInfo(ExpressInfo expressInfo) {
        if (this.f206e == null) {
            this.f206e = new ArrayList<>();
        }
        this.f206e.add(expressInfo);
    }

    public void setExtraStatus(int i2) {
        this.n = i2;
    }

    public void setHairMaskInfo(HairMaskInfo hairMaskInfo) {
        if (this.A == null) {
            this.A = new ArrayList<>();
        }
        this.A.add(hairMaskInfo);
    }

    public void setHeadPitchStatus(int i2) {
        this.x = i2;
    }

    public void setHeadWear(int i2) {
        this.f203b = i2;
    }

    public void setHeadWearColor(int i2) {
        this.p = i2;
    }

    public void setIsOldHeadWearAcc(boolean z2) {
        this.o = z2;
    }

    public void setLoopBeginFrameIndex(int i2) {
        this.q = i2;
    }

    public void setLoopCount(int i2) {
        this.s = i2;
    }

    public void setLoopEndFrameIndex(int i2) {
        this.r = i2;
    }

    public void setLoopNum(int i2) {
        this.t = i2;
    }

    public void setLoopType(int i2) {
        this.m = i2;
    }

    public void setName(String str) {
        this.f202a = str;
    }

    public void setNeedOutLine(boolean z2) {
        this.f205d = z2;
    }

    public void setNewHeadWearPath(String str) {
        if (this.h == null) {
            this.h = new ArrayList<>();
        }
        this.h.add(str);
    }

    public void setNewToothPath(String str) {
        this.j = str;
    }

    public void setOldHeadWearPath(String str) {
        this.g = str;
    }

    public void setOldToothPath(String str) {
        this.i = str;
    }

    public void setPauseIndex(int i2) {
        this.y = i2;
    }

    public void setRandom(int i2) {
        this.w = i2;
    }

    public String toString() {
        return "name = " + this.f202a + ", headWear = " + this.f203b + ", mNeedOutLine = " + this.f205d + ", mExpressIndex = , mExpressValue = ";
    }
}
