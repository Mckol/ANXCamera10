package b.c.a;

import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import c.a;
import com.android.camera.Util;
import com.android.camera.constant.FastMotionConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.statistic.MistatsConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import miui.os.Build;

/* compiled from: Device */
public class c {
    private static ArrayList<String> Ah = null;
    public static final String Bg = Build.DEVICE;
    private static final String[] Bh = {"KR", "JP"};
    public static final String Cg = "qcom";
    private static final String Ch = "ro.boot.hwversion";
    public static final String Dg = "mediatek";
    private static final int Eg = 100;
    public static final String Fg = Build.MODEL;
    public static final boolean Gg = "beryllium".equals(Bg);
    public static final boolean Hg = "lavender".equals(Bg);
    public static final boolean IS_HONGMI = hn();
    private static final AtomicReference<Optional<Boolean>> IS_MTK_PLATFORM = new AtomicReference<>(Optional.empty());
    public static final boolean IS_XIAOMI = eo();
    public static final boolean Ig = "violet".equals(Bg);
    public static final boolean Jg = "polaris".equals(Bg);
    public static final boolean Kg = "sirius".equals(Bg);
    public static final boolean Lg = "dipper".equals(Bg);
    public static final boolean Mg = "andromeda".equals(Bg);
    public static final boolean Ng = "perseus".equals(Bg);
    public static final boolean Og = "cepheus".equals(Bg);
    public static final boolean Pg = "raphael".equals(Bg);
    public static final boolean Qg = "grus".equals(Bg);
    public static final boolean Rg = "begonia".equals(Bg);
    public static final boolean Sg = ("phoenix".equals(Bg) || "phoenixin".equals(Bg));
    public static final boolean Tg = "begoniain".equals(Bg);
    public static final boolean Ug = "ginkgo".equals(Bg);
    public static final boolean Vg = "pyxis".equals(Bg);
    public static final boolean Wg = "vela".equals(Bg);
    public static final boolean Xg = "laurus".equals(Bg);
    public static final boolean Yg = "laurel_sprout".equals(Bg);
    public static final boolean Zg = "tucana".equals(Bg);
    public static final boolean _g = "umi".equals(Bg);
    public static final boolean bh = "cmi".equals(Bg);
    public static final boolean dh = "cas".equals(Bg);
    public static final boolean eh = ("apollo".equals(Bg) || "apolloin".equals(Bg));
    public static final boolean fh = ("bomb".equals(Bg) || "banana".equals(Bg));
    public static final boolean gh = ("lmi".equals(Bg) || "lmiin".equals(Bg));
    public static final boolean hh = ("lmipro".equals(Bg) || "lmiinpro".equals(Bg));
    public static final boolean jh = "draco".equals(Bg);
    public static final boolean kh = ("picasso".equals(Bg) || "picassoin".equals(Bg));
    public static final boolean lh = "picasso".equals(Bg);
    public static final boolean mh = ("gauguin".equals(Bg) || "gauguinpro".equals(Bg));
    public static final boolean nh = "cezanne".equals(Bg);
    public static final boolean oh = "crux".equals(Bg);
    public static final boolean qh = ("curtana".equals(Bg) || "durandal".equals(Bg) || "excalibur".equals(Bg) || "joyeuse".equals(Bg) || "gram".equals(Bg));
    public static final boolean rh = "dandelion".equals(Bg);
    public static final boolean sh = ("angelica".equals(Bg) || "angelican".equals(Bg) || "angelicain".equals(Bg) || "cattail".equals(Bg));
    public static final boolean th;
    public static final boolean uh = Build.IS_STABLE_VERSION;
    public static final boolean vh = Build.IS_CM_CUSTOMIZATION_TEST;
    public static final boolean wh = Build.IS_CM_CUSTOMIZATION;
    private static final int xh = 1;
    private static final int yh = 4;
    private static final int zh = 8;

    static {
        boolean z = false;
        if (TextUtils.equals(Bg, "merlin") || TextUtils.equals(Bg, "merlinnfc")) {
            z = true;
        }
        th = z;
    }

    @a(key = g.Jh, value = FastMotionConstant.FAST_MOTION_DURATION_100)
    public static int Am() {
        return DataRepository.dataItemFeature().getConfig().Am();
    }

    @a(key = g.Bi, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean An() {
        return DataRepository.dataItemFeature().getConfig().An();
    }

    public static String Bm() {
        int i = SystemProperties.getInt("ro.boot.camera.config", 1);
        if (i == 0) {
            return "_pro";
        }
        if (i != 1) {
        }
        return "";
    }

    public static boolean Bn() {
        return un() || An();
    }

    public static ArrayList<String> Cm() {
        if (Ah == null) {
            Ah = new ArrayList<>();
            String[] Dm = Dm();
            if (Dm != null) {
                Collections.addAll(Ah, Dm);
            }
        }
        return Ah;
    }

    @a(key = g.yi, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public static boolean Cn() {
        return DataRepository.dataItemFeature().getConfig().Cn();
    }

    @a(key = g.Gh)
    public static String[] Dm() {
        return DataRepository.dataItemFeature().getConfig().Dm().split(",");
    }

    public static boolean Dn() {
        return !DataRepository.dataItemFeature().dj() && IS_HONGMI;
    }

    public static String Em() {
        return lh ? Vm() ? "_48m" : Wm() ? "_speed" : "" : Om() ? "_l" : (in() || kn()) ? "_in" : !DataRepository.dataItemFeature().fi() ? "" : (android.os.Build.MODEL.contains("BROWN EDITION") || android.os.Build.MODEL.contains("Explorer")) ? "_a" : android.os.Build.MODEL.contains("ROY") ? "_b" : Qm() ? "_s" : (en() || dn() || _m() || Zm() || an()) ? "_global" : on() ? "_premium" : bn() ? pn() ? "_global_pro" : "_global" : "";
    }

    @a(key = g.xi, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean En() {
        return DataRepository.dataItemFeature().getConfig().En();
    }

    public static boolean Fj() {
        return DataRepository.dataItemFeature().Fj();
    }

    @a(key = g.HIBERNATION_TIMEOUT, value = "3")
    public static int Fm() {
        return DataRepository.dataItemFeature().getConfig().Fm();
    }

    public static boolean Fn() {
        return (ho() & 13) != 0;
    }

    @a(key = g.Ji)
    public static int[] Gm() {
        String Gm = DataRepository.dataItemFeature().getConfig().Gm();
        if (TextUtils.isEmpty(Gm)) {
            return new int[0];
        }
        String[] split = Gm.split(",");
        int[] iArr = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            iArr[i] = Integer.parseInt(split[i]);
        }
        return iArr;
    }

    @a(key = g.Rh, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Gn() {
        return DataRepository.dataItemFeature().getConfig().Gn();
    }

    @a(key = g.Ii, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Hm() {
        return DataRepository.dataItemFeature().getConfig().Hm();
    }

    public static boolean Hn() {
        return (ho() & 1) != 0;
    }

    @a(key = g.ni, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Im() {
        return ((float) Util.sWindowHeight) / ((float) Util.sWindowWidth) >= 2.0f && DataRepository.dataItemFeature().getConfig().Im();
    }

    @a(key = g.Sh, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean In() {
        return DataRepository.dataItemFeature().getConfig().In();
    }

    public static boolean Jm() {
        return !vo() && DataRepository.dataItemFeature().vj() && Cm() != null && !Cm().isEmpty();
    }

    @a(key = g.Fh, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Jn() {
        return DataRepository.dataItemFeature().getConfig().Jn();
    }

    public static boolean Km() {
        return DataRepository.dataItemFeature().ll() || Sg;
    }

    @a(key = g.Mh, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Kn() {
        return DataRepository.dataItemFeature().getConfig().Kn();
    }

    public static boolean Lm() {
        if (Build.IS_INTERNATIONAL_BUILD) {
            return ia(getCountry());
        }
        return false;
    }

    @a(key = g.Ih, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Ln() {
        return DataRepository.dataItemFeature().getConfig().Ln();
    }

    @a(key = g.hi, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Mm() {
        return DataRepository.dataItemFeature().getConfig().Mm();
    }

    @a(key = g.Xh, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public static boolean Mn() {
        return DataRepository.dataItemFeature().getConfig().Mn();
    }

    public static boolean N(boolean z) {
        String str = SystemProperties.get("ro.miui.customized.region");
        if ("fr_sfr".equals(str) || "fr_orange".equals(str)) {
            return false;
        }
        if (!"es_vodafone".equals(str) || !"NL".equals(getCountry())) {
            return z;
        }
        return false;
    }

    public static boolean Nm() {
        String str = SystemProperties.get(Ch);
        return oh && (TextUtils.equals(str, "7.1.7") || TextUtils.equals(str, "7.2.0"));
    }

    @a(key = g.Kh, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Nn() {
        if (Om()) {
            return false;
        }
        return DataRepository.dataItemFeature().getConfig().Nn();
    }

    public static boolean Om() {
        if (!"onc".equals(Bg)) {
            return false;
        }
        String str = SystemProperties.get(Ch);
        return !TextUtils.isEmpty(str) && '2' == str.charAt(0);
    }

    public static boolean On() {
        return !Lm();
    }

    public static boolean Pl() {
        return DataRepository.dataItemFeature().Pl();
    }

    public static boolean Pm() {
        return Hg && "India".equalsIgnoreCase(SystemProperties.get("ro.boot.hwc"));
    }

    @a(key = g.Oh, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Pn() {
        return DataRepository.dataItemFeature().getConfig().Pn();
    }

    public static boolean Qm() {
        return Hg && "India_48_5".equalsIgnoreCase(SystemProperties.get("ro.boot.hwc"));
    }

    @a(key = g.Th, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Qn() {
        return DataRepository.dataItemFeature().getConfig().Qn();
    }

    public static boolean Rm() {
        return Jg;
    }

    @a(key = g._h, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Rn() {
        return DataRepository.dataItemFeature().getConfig().Rn();
    }

    public static boolean Sm() {
        return Xm() || Vg || Qg || DataRepository.dataItemFeature().ll();
    }

    @a(key = g.ji, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Sn() {
        return !Build.IS_INTERNATIONAL_BUILD && DataRepository.dataItemFeature().getConfig().Sn();
    }

    @a(key = g.oi, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public static boolean Tm() {
        return DataRepository.dataItemFeature().getConfig().Tm();
    }

    @a(key = g.Eh, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Tn() {
        return DataRepository.dataItemFeature().getConfig().Tn();
    }

    public static boolean Um() {
        return Rg || Tg;
    }

    @a(key = g.Hh, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Un() {
        return DataRepository.dataItemFeature().getConfig().Un();
    }

    public static boolean Vm() {
        if (!lh) {
            return false;
        }
        return "picasso_48m".equalsIgnoreCase(SystemProperties.get("ro.product.name"));
    }

    @a(key = g.Lh, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Vn() {
        return DataRepository.dataItemFeature().getConfig().Vn();
    }

    public static boolean Wm() {
        if (!lh) {
            return false;
        }
        String str = SystemProperties.get(Ch);
        for (String str2 : new String[]{"3.9.3", "3.9.5"}) {
            if (str2.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    @a(key = g.ui, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Wn() {
        return DataRepository.dataItemFeature().getConfig().Wn();
    }

    public static boolean Xm() {
        return Ng && Build.IS_INTERNATIONAL_BUILD;
    }

    @a(key = g.ii, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Xn() {
        return DataRepository.dataItemFeature().getConfig().Xn();
    }

    public static boolean Ym() {
        return Og && Build.IS_INTERNATIONAL_BUILD;
    }

    @a(key = g.Nh, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean Yn() {
        return DataRepository.dataItemFeature().getConfig().Yn();
    }

    public static boolean Zm() {
        return Bg.equalsIgnoreCase("davinci") && Build.IS_INTERNATIONAL_BUILD;
    }

    @a(key = g.Yh, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public static boolean Zn() {
        return DataRepository.dataItemFeature().getConfig().Zn();
    }

    public static boolean _m() {
        return Bg.equalsIgnoreCase("raphael") && Build.IS_INTERNATIONAL_BUILD;
    }

    @a(key = g.Ph, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean _n() {
        return DataRepository.dataItemFeature().getConfig()._n();
    }

    public static boolean an() {
        return Vg && Build.IS_INTERNATIONAL_BUILD;
    }

    @a(key = g.Uh, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean ao() {
        return DataRepository.dataItemFeature().getConfig().ao();
    }

    public static boolean bn() {
        return Zg && Build.IS_INTERNATIONAL_BUILD;
    }

    @a(key = g.ri, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean bo() {
        return DataRepository.dataItemFeature().getConfig().bo();
    }

    public static boolean cn() {
        return "toco".equals(Bg);
    }

    public static boolean co() {
        return !IS_XIAOMI && !IS_HONGMI;
    }

    public static boolean dn() {
        return "lmi".equals(Bg) && Build.IS_INTERNATIONAL_BUILD;
    }

    @a(key = g.qi, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    /* renamed from: do  reason: not valid java name */
    public static boolean m0do() {
        return DataRepository.dataItemFeature().getConfig().m4do();
    }

    private static boolean dp() {
        return SystemProperties.getBoolean("ro.hardware.fp.fod", false);
    }

    public static boolean en() {
        return th && Build.IS_INTERNATIONAL_BUILD;
    }

    @a(key = g.IS_XIAOMI, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean eo() {
        return DataRepository.dataItemFeature().getConfig().eo();
    }

    @a(key = g.ti, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean fn() {
        return DataRepository.dataItemFeature().getConfig().fn();
    }

    @a(key = g.fi, value = "0")
    public static int fo() {
        return DataRepository.dataItemFeature().getConfig().fo();
    }

    public static String getCountry() {
        String str = Util.sRegion;
        return !TextUtils.isEmpty(str) ? str : Locale.getDefault().getCountry();
    }

    @a(key = g.Zh, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public static boolean gn() {
        return DataRepository.dataItemFeature().getConfig().gn();
    }

    public static boolean hk() {
        return DataRepository.dataItemFeature().hk();
    }

    @a(key = g.IS_HONGMI, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean hn() {
        return DataRepository.dataItemFeature().getConfig().hn();
    }

    @a(key = g.Vh, value = "0")
    public static int ho() {
        return DataRepository.dataItemFeature().getConfig().ho();
    }

    private static boolean ia(String str) {
        for (String str2 : Bh) {
            if (TextUtils.equals(str, str2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean in() {
        return Gg && "India".equalsIgnoreCase(SystemProperties.get("ro.boot.hwc"));
    }

    public static boolean isGlobal() {
        return Build.IS_INTERNATIONAL_BUILD;
    }

    public static boolean isMTKPlatform() {
        if (!IS_MTK_PLATFORM.get().isPresent()) {
            synchronized (IS_MTK_PLATFORM) {
                if (!IS_MTK_PLATFORM.get().isPresent()) {
                    IS_MTK_PLATFORM.set(Optional.of(Boolean.valueOf(Dg.equals(no()))));
                }
            }
        }
        return IS_MTK_PLATFORM.get().get().booleanValue();
    }

    @a(key = g.Dh, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean isPad() {
        return DataRepository.dataItemFeature().getConfig().isPad();
    }

    @a(key = g.Ei, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean isSupportSuperResolution() {
        return DataRepository.dataItemFeature().getConfig().isSupportSuperResolution();
    }

    @a(key = g.vi, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean isSupportedOpticalZoom() {
        return DataRepository.dataItemFeature().getConfig().isSupportedOpticalZoom();
    }

    @a(key = g.li, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public static boolean jn() {
        return DataRepository.dataItemFeature().getConfig().jn();
    }

    public static boolean jo() {
        return SystemProperties.getInt("ro.boot.camera.config", -1) != -1;
    }

    public static boolean kn() {
        return qh && "India".equalsIgnoreCase(SystemProperties.get("ro.boot.hwc"));
    }

    public static boolean ko() {
        int[] Gm = Gm();
        return Gm != null && Gm.length > 0;
    }

    public static boolean ln() {
        return gh || hh;
    }

    @a(key = g.Gi, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean lo() {
        return DataRepository.dataItemFeature().getConfig().lo();
    }

    public static boolean mn() {
        if (!Zg) {
            return false;
        }
        return Build.VERSION.SDK_INT >= 29 ? SystemProperties.get("persist.vendor.camera.rearMain.vendorID", "03").equals("03") : SystemProperties.get("persist.camera.rearMain.vendorID", "03").equals("03");
    }

    @a(key = g.Fi, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public static boolean mo() {
        return DataRepository.dataItemFeature().getConfig().mo();
    }

    @a(key = g.di, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean nn() {
        return DataRepository.dataItemFeature().getConfig().nn();
    }

    @a(key = g.VENDOR)
    public static String no() {
        return DataRepository.dataItemFeature().getConfig().no();
    }

    public static boolean on() {
        return Bg.equalsIgnoreCase("raphael") && miui.os.Build.MODEL.endsWith("Premium Edition");
    }

    public static boolean pn() {
        return Zg && !mn();
    }

    public static boolean qn() {
        return Cg.equals(no());
    }

    @a(key = g.wi, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public static boolean rn() {
        return DataRepository.dataItemFeature().getConfig().rn();
    }

    @a(key = g.Wh, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean sn() {
        return DataRepository.dataItemFeature().getConfig().sn();
    }

    @a(key = g.ei, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean tn() {
        return DataRepository.dataItemFeature().getConfig().tn();
    }

    @a(key = g.Ai, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean un() {
        return DataRepository.dataItemFeature().getConfig().un();
    }

    @a(key = g.Hi, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean vn() {
        return DataRepository.dataItemFeature().getConfig().vn();
    }

    @a(key = g.mi, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    private static boolean vo() {
        return DataRepository.dataItemFeature().getConfig().vo() || dp();
    }

    @a(key = g.Di, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean wn() {
        return DataRepository.dataItemFeature().getConfig().wn();
    }

    @a(key = g.Qh, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean xm() {
        return !vh && DataRepository.dataItemFeature().getConfig().xm();
    }

    @a(key = g.zi, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean xn() {
        return DataRepository.dataItemFeature().getConfig().xn();
    }

    @a(key = g.Ci, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean ym() {
        return DataRepository.dataItemFeature().getConfig().ym();
    }

    @a(key = g.ki, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean yn() {
        return DataRepository.dataItemFeature().getConfig().yn();
    }

    @a(key = g.gi, value = "12")
    public static float zm() {
        return DataRepository.dataItemFeature().getConfig().zm();
    }

    @a(key = g.bi, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public static boolean zn() {
        return DataRepository.dataItemFeature().getConfig().zn();
    }
}
