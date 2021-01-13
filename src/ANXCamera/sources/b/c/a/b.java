package b.c.a;

import android.os.Build;
import android.text.TextUtils;
import android.util.Size;
import c.a;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.Util;
import com.android.camera.constant.DurationConstant;
import com.android.camera.constant.FastMotionConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigRatio;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera2.CameraCapabilities;
import com.mi.device.Common;
import com.xiaomi.camera.util.SystemProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.regex.PatternSyntaxException;
import miui.os.Build;
import miui.telephony.phonenumber.CountryCodeConverter;
import miui.widget.AlphabetFastIndexer;

/* compiled from: DataItemFeature */
public class b implements f {
    private static final boolean Ag = false;
    private static final int PARALLEL_PERFORMANCE_SETTING = 0;
    private static final int PARALLEL_QUALITY_SETTING = 1;
    private static final int PARALLEL_QUEUE_SIZE = 2;
    private static final String TAG = "DataFeature";
    private static final boolean zg;
    private Common mConfig = c.b.aa(c.Bg + c.Bm());
    private String yg;

    static {
        boolean z = false;
        if (Util.DEBUG && SystemProperties.getBoolean("camera.feature.clone", false)) {
            z = true;
        }
        zg = z;
    }

    public b() {
        Log.d(TAG, "DataItemFeature: init proxy = " + this.mConfig.getClass());
    }

    static /* synthetic */ Boolean b(CameraCapabilities cameraCapabilities) {
        return Boolean.valueOf(cameraCapabilities.isMtkPipDevicesSupported());
    }

    private boolean c(CameraCapabilities cameraCapabilities) {
        Size Zh;
        List<CameraSize> supportedOutputSizeWithAssignedMode;
        if (!(cameraCapabilities == null || (Zh = Zh()) == null || (supportedOutputSizeWithAssignedMode = cameraCapabilities.getSupportedOutputSizeWithAssignedMode(32, 33011)) == null || supportedOutputSizeWithAssignedMode.size() <= 0)) {
            for (CameraSize cameraSize : supportedOutputSizeWithAssignedMode) {
                if (cameraSize.width >= Zh.getWidth() && cameraSize.height >= Zh.getHeight()) {
                    Log.d(TAG, "isSupportUltraPixelRaw size:" + cameraSize.toString());
                    return true;
                }
            }
        }
        return false;
    }

    private int ga(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        char charAt = str.charAt(0);
        if (Character.isDigit(charAt)) {
            return Integer.parseInt(String.valueOf(charAt));
        }
        return -1;
    }

    private Size ha(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String substring = str.substring(str.indexOf(58) + 1);
        if (TextUtils.isEmpty(substring)) {
            return null;
        }
        String[] split = substring.replace(" ", "").split("x");
        if (split.length >= 2) {
            return new Size(Integer.valueOf(split[0]).intValue(), Integer.valueOf(split[1]).intValue());
        }
        return null;
    }

    private String t(String str, String str2) {
        String Xh = Xh();
        if (!Xh.contains(str)) {
            return str2;
        }
        for (String str3 : Arrays.asList(Xh.toLowerCase(Locale.ENGLISH).split(";"))) {
            if (str3.contains(str)) {
                return str3.toLowerCase(Locale.ENGLISH).trim().replace(str + ":", "");
            }
        }
        return str2;
    }

    @a(key = f.Do, value = "0")
    private int uo() {
        return this.mConfig.uo();
    }

    @a(key = f.wn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    private boolean wo() {
        return this.mConfig.wo();
    }

    @a(key = f.Um, value = "v0")
    public String Ah() {
        return this.mConfig.Ah();
    }

    @a(key = f.lo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Ai() {
        return this.mConfig.Ai();
    }

    @a(key = f.Sn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Aj() {
        return this.mConfig.Aj();
    }

    @a(key = f.jn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Ak() {
        return this.mConfig.Ak();
    }

    @a(key = f.wp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Al() {
        return this.mConfig.Al() && !c.isMTKPlatform();
    }

    @a(key = f.xo, value = "-1")
    public int Bh() {
        if (!c.hh || CameraSettings.isHighQualityPreferred()) {
            return this.mConfig.Bh();
        }
        return 0;
    }

    @a(key = f.Sm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Bi() {
        return this.mConfig.Bi();
    }

    @a(key = f.jm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Bj() {
        return this.mConfig.Bj();
    }

    @a(key = f.Em, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean Bk() {
        return this.mConfig.Bk();
    }

    @a(key = f.mp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Bl() {
        return this.mConfig.Bl();
    }

    @a(key = f.ym, value = "4.5")
    public String Ch() {
        return this.mConfig.Ch();
    }

    @a(key = f.im, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Ci() {
        return this.mConfig.Ci();
    }

    @a(key = f.eq, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Cj() {
        return this.mConfig.Cj();
    }

    @a(key = f.hn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Ck() {
        return !yk() && !xk() && this.mConfig.Ck();
    }

    @a(key = f.Wo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Cl() {
        return this.mConfig.Cl();
    }

    @a(key = f.Jn)
    public String Dh() {
        return this.mConfig.Dh();
    }

    public boolean Di() {
        if (this.yg == null) {
            this.yg = SystemProperties.get("ro.boot.hwc");
        }
        if ("india".equalsIgnoreCase(this.yg)) {
            return true;
        }
        return !TextUtils.isEmpty(this.yg) && this.yg.toLowerCase(Locale.ENGLISH).startsWith("india_");
    }

    @a(key = f.bm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Dj() {
        return this.mConfig.Dj();
    }

    @a(key = f.Lm, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean Dk() {
        if (c.Om()) {
            return false;
        }
        return this.mConfig.Dk();
    }

    @a(key = f.kp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Dl() {
        return this.mConfig.Dl();
    }

    public int Eh() {
        return ga(Dh());
    }

    public boolean Ei() {
        return Build.getRegion().endsWith("IN");
    }

    @a(key = f.Gm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Ej() {
        return this.mConfig.Ej() && !Build.IS_INTERNATIONAL_BUILD;
    }

    @a(key = f.il, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Ek() {
        return Build.VERSION.SDK_INT >= 28 && this.mConfig.Ek();
    }

    @a(key = f._p, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean El() {
        return this.mConfig.El();
    }

    @a(key = f.ml, value = "off")
    public String Fh() {
        return this.mConfig.Fh();
    }

    @a(key = f.Bl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Fi() {
        return this.mConfig.Fi();
    }

    @a(key = f.Pl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Fj() {
        return this.mConfig.Fj();
    }

    @a(key = f.Yn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Fk() {
        return this.mConfig.Fk();
    }

    @a(key = f.zo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Fl() {
        return this.mConfig.Fl();
    }

    @a(key = f.qo, value = "12.0f")
    public float Gh() {
        return this.mConfig.Gh();
    }

    @a(key = f.rl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Gi() {
        return this.mConfig.Gi();
    }

    @a(key = f.mn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Gj() {
        if (Util.isGlobalVersion() || !DataRepository.dataItemGlobal().isNormalIntent()) {
            return false;
        }
        return this.mConfig.Gj();
    }

    @a(key = f.gl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Gk() {
        return this.mConfig.Gk();
    }

    @a(key = f.Hk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Gl() {
        return this.mConfig.Gl() && Di();
    }

    @a(key = f.Un, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean H() {
        if (c._m() || c.an()) {
            return false;
        }
        return this.mConfig.H();
    }

    @a(key = f.cq, value = "0")
    public int Hh() {
        return this.mConfig.Hh();
    }

    @a(key = f.mo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Hi() {
        return this.mConfig.Hi();
    }

    @a(key = f.Ro, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Hj() {
        return this.mConfig.Hj();
    }

    public boolean Hk() {
        if (!c._g || !c.uh) {
            return Arrays.asList(ml().toUpperCase(Locale.ENGLISH).split(":")).contains("ULTRA_WIDE");
        }
        return false;
    }

    @a(key = f.Jl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Hl() {
        return this.mConfig.Hl() && Di();
    }

    @a(key = f.Vn, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean I() {
        return this.mConfig.I();
    }

    @a(key = f.Ko, value = "0")
    public int Ih() {
        return this.mConfig.Ih();
    }

    @a(key = f.Zk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Ii() {
        return this.mConfig.Ii();
    }

    @a(key = f.Fl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Ij() {
        return this.mConfig.Ij();
    }

    @a(key = f.Nl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Ik() {
        return this.mConfig.Ik();
    }

    @a(key = f.fp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Il() {
        return this.mConfig.Il();
    }

    @a(key = f._l)
    public String Jh() {
        return this.mConfig.Jh();
    }

    @a(key = f.Ho, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Ji() {
        return this.mConfig.Ji();
    }

    @a(key = f.Gl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Jj() {
        return this.mConfig.Jj();
    }

    @a(key = f.wl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Jk() {
        return this.mConfig.Jk();
    }

    @a(key = f.Vm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Jl() {
        return this.mConfig.Jl();
    }

    @a(key = f.Qm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean K(boolean z) {
        return z && this.mConfig.oo();
    }

    @a(key = f.zl, value = "0.8766")
    public float Kh() {
        return this.mConfig.Kh();
    }

    @a(key = f.jl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Ki() {
        return this.mConfig.Ki();
    }

    @a(key = f.Al, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Kj() {
        return this.mConfig.Kj();
    }

    @a(key = f.nl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public Boolean Kk() {
        return Boolean.valueOf(this.mConfig.Kk());
    }

    @a(key = f.Eo, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean Kl() {
        return this.mConfig.Kl();
    }

    public String L(boolean z) {
        return z ? Ch() : th();
    }

    @a(key = f.yl, value = "280")
    public int Lh() {
        return this.mConfig.Lh();
    }

    @a(key = f.Zl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Li() {
        return this.mConfig.Li();
    }

    @a(key = f.xl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Lj() {
        if (Util.isGlobalVersion()) {
            return false;
        }
        return this.mConfig.Lj();
    }

    @a(key = f.Jo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Lk() {
        return this.mConfig.Lk();
    }

    public boolean Ll() {
        String kh = kh();
        if (kh == null || !kh.toLowerCase(Locale.ENGLISH).contains("mfnr")) {
            return false;
        }
        String[] split = kh.toLowerCase(Locale.ENGLISH).split(";");
        if (split.length > 0) {
            for (String str : split) {
                String[] split2 = str.toLowerCase(Locale.ENGLISH).split(":");
                if (split2[0].equals("mfnr")) {
                    return split2[1].equals("1");
                }
            }
        }
        return false;
    }

    @a(key = f.Kp, value = ComponentConfigRatio.RATIO_4X3)
    public String M(boolean z) {
        return z ? ComponentConfigRatio.RATIO_4X3 : this.mConfig.po();
    }

    @a(key = f.En, value = "0")
    public int Mh() {
        return this.mConfig.Mh();
    }

    public boolean Mi() {
        return Arrays.asList(ml().toUpperCase(Locale.ENGLISH).split(":")).contains("NO_PIXEL");
    }

    public boolean Mj() {
        return H() && uo() == 1;
    }

    @a(key = f.Dk)
    public String Mk() {
        return this.mConfig.Mk();
    }

    public boolean Ml() {
        String kh = kh();
        if (kh == null || !kh.toLowerCase(Locale.ENGLISH).contains("notelemfnr")) {
            return false;
        }
        String[] split = kh.toLowerCase(Locale.ENGLISH).split(";");
        if (split.length > 0) {
            for (String str : split) {
                String[] split2 = str.toLowerCase(Locale.ENGLISH).split(":");
                if (split2[0].equals("notelemfnr")) {
                    return split2[1].equals("1");
                }
            }
        }
        return false;
    }

    @a(key = f.Yp)
    public Map<Integer, Integer> Nh() {
        String Nh = this.mConfig.Nh();
        HashMap hashMap = new HashMap();
        hashMap.put(0, 0);
        hashMap.put(1, 0);
        hashMap.put(2, 3);
        if (Nh.length() == 0) {
            return hashMap;
        }
        String[] split = Nh.toLowerCase(Locale.ENGLISH).split(";");
        int length = split.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String[] split2 = split[i].toLowerCase(Locale.ENGLISH).split(",");
            if (Util.TOTAL_MEMORY < Long.parseLong(split2[0])) {
                String[] split3 = split2[1].toLowerCase(Locale.ENGLISH).split(AlphabetFastIndexer.STARRED_TITLE);
                hashMap.put(0, Integer.valueOf(Integer.parseInt(split3[0])));
                String[] split4 = split3[1].toLowerCase(Locale.ENGLISH).split(":");
                hashMap.put(1, Integer.valueOf(Integer.parseInt(split4[0])));
                hashMap.put(2, Integer.valueOf(Integer.parseInt(split4[1])));
                break;
            }
            i++;
        }
        return hashMap;
    }

    public boolean Ni() {
        if (!c._g || !c.uh) {
            return Arrays.asList(ml().toUpperCase(Locale.ENGLISH).split(":")).contains("PRO");
        }
        return false;
    }

    public boolean Nj() {
        return H() && uo() == 0;
    }

    @a(key = f.Ek)
    public String Nk() {
        return this.mConfig.Nk();
    }

    @a(key = f.cn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Nl() {
        return this.mConfig.Nl();
    }

    @a(key = f.Ln, value = "-1")
    public int Oh() {
        return this.mConfig.Oh();
    }

    @a(key = f.km, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Oi() {
        return this.mConfig.Oi();
    }

    @a(key = f.Jp, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean Oj() {
        return this.mConfig.Oj();
    }

    @a(key = f.Ck, value = "48M")
    public String Ok() {
        return this.mConfig.Ok();
    }

    @a(key = f.Pp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Ol() {
        return this.mConfig.Ol();
    }

    @a(key = f.In)
    public String Ph() {
        return this.mConfig.Ph();
    }

    @a(key = f.ko, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Pi() {
        return this.mConfig.Pi();
    }

    @a(key = f.Up, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Pj() {
        return this.mConfig.Pj();
    }

    @a(key = f.vp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Pk() {
        return this.mConfig.Pk();
    }

    @a(key = f.Ul, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Pl() {
        if (!Util.isGlobalVersion()) {
            return false;
        }
        return this.mConfig.Pl();
    }

    public int Qh() {
        if (c.Pm()) {
            return -1;
        }
        if (c.Vm()) {
            return 1;
        }
        if (!c.mh || !c.isGlobal()) {
            return ga(Ph());
        }
        return 2;
    }

    @a(key = f.Fk, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean Qi() {
        return this.mConfig.Qi();
    }

    @a(key = f.Pn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Qj() {
        return this.mConfig.Qj();
    }

    public boolean Qk() {
        return (Di() && Hj()) || ij();
    }

    @a(key = f.Go, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Ql() {
        return this.mConfig.Ql();
    }

    @a(key = f.sn, value = "5")
    public int Rh() {
        if (c.mn()) {
            return 6;
        }
        if (!CameraSettings.isUltraPixelOn() || !DataRepository.dataItemFeature().hl()) {
            return !CameraSettings.isHighQualityPreferred() ? Sh() : this.mConfig.Rh();
        }
        return 5;
    }

    @a(key = f.pl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Ri() {
        return this.mConfig.Ri();
    }

    @a(key = f.Io, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Rj() {
        return this.mConfig.Rj();
    }

    @a(key = f.Nm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Rk() {
        return this.mConfig.Rk();
    }

    @a(key = f.Sk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Rl() {
        return this.mConfig.Rl();
    }

    @a(key = f.tn, value = "6")
    public int Sh() {
        return this.mConfig.Sh();
    }

    @a(key = f.hm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Si() {
        return this.mConfig.Si();
    }

    @a(key = f.Hm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Sj() {
        if (SystemProperties.getBoolean("miuicamera.mimoji.show", false)) {
            return true;
        }
        return this.mConfig.Sj() && !miui.os.Build.IS_INTERNATIONAL_BUILD;
    }

    public boolean Sk() {
        String rm = rm();
        return TextUtils.isEmpty(rm) ? !vm() : Boolean.parseBoolean(rm);
    }

    @a(key = f.Uk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Sl() {
        return this.mConfig.Sl();
    }

    @a(key = f.vm, value = "-1")
    public int Th() {
        return this.mConfig.Th();
    }

    @a(key = f.ol, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean Ti() {
        return this.mConfig.Ti();
    }

    @a(key = f.en, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Tj() {
        return this.mConfig.Tj();
    }

    @a(key = f.Kl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Tk() {
        return this.mConfig.cm();
    }

    @a(key = f.Qk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Tl() {
        return this.mConfig.Tl();
    }

    @a(key = f.um, value = "-1")
    public long Uh() {
        return this.mConfig.Uh();
    }

    public boolean Ui() {
        return Arrays.asList(ml().toUpperCase(Locale.ENGLISH).split(":")).contains("MACRO");
    }

    @a(key = f.yp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Uj() {
        return this.mConfig.Uj();
    }

    @a(key = f.yk, value = "30")
    public int Uk() {
        return this.mConfig.Uk();
    }

    @a(key = f.Nk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Ul() {
        return (Di() || Ei()) && this.mConfig.Ul();
    }

    @a(key = f.Bn, value = "0")
    public int Vh() {
        return this.mConfig.Vh();
    }

    @a(key = f.dm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Vi() {
        return this.mConfig.Vi();
    }

    @a(key = f.Dp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Vj() {
        return this.mConfig.Vj();
    }

    @a(key = f.Ak, value = "35")
    public int Vk() {
        return this.mConfig.Vk();
    }

    @a(key = f.Lk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Vl() {
        return this.mConfig.Vl();
    }

    @a(key = f.Dn)
    public String Wh() {
        return this.mConfig.Wh();
    }

    @a(key = f.Po, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Wi() {
        return this.mConfig.Wi();
    }

    @a(key = f.Tn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Wj() {
        return this.mConfig.Wj();
    }

    @a(key = f.zk, value = "4")
    public int Wk() {
        return this.mConfig.Wk();
    }

    @a(key = f.Mk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Wl() {
        return this.mConfig.Wl();
    }

    @a(key = f.Pm)
    public String X(String str) {
        String qo = this.mConfig.qo();
        return TextUtils.isEmpty(qo) ? str : qo;
    }

    @a(key = f.Tp)
    public String Xh() {
        return this.mConfig.Xh();
    }

    @a(key = f.Nn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Xi() {
        return this.mConfig.Xi();
    }

    @a(key = f.xn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Xj() {
        return this.mConfig.Xj();
    }

    @a(key = f.Bk, value = "4")
    public int Xk() {
        return this.mConfig.Xk();
    }

    @a(key = f.hp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Xl() {
        return this.mConfig.Xl();
    }

    @a(key = f.Sp)
    public String Y(String str) {
        String ro = this.mConfig.ro();
        return TextUtils.isEmpty(ro) ? str : ro;
    }

    @a(key = f.Gk, value = "20")
    public int Yh() {
        return this.mConfig.Yh();
    }

    @a(key = f.rm, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean Yi() {
        return this.mConfig.Yi();
    }

    @a(key = f.Op, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Yj() {
        return this.mConfig.Yj();
    }

    @a(key = f.bp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Yk() {
        return this.mConfig.Yk();
    }

    @a(key = f.Ao, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Yl() {
        return this.mConfig.Yl();
    }

    public Size Zh() {
        return c.Vm() ? new Size(8000, DurationConstant.DURATION_LANDSCAPE_HINT) : (!c.mh || !c.isGlobal()) ? ha(Ph()) : new Size(9248, 6944);
    }

    @a(key = f.sm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Zi() {
        return this.mConfig.Zi();
    }

    @a(key = f.Ml, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean Zj() {
        return this.mConfig.Zj();
    }

    @a(key = f.wk, value = "0")
    public int Zk() {
        return this.mConfig.Zk();
    }

    @a(key = f.bn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean Zl() {
        return this.mConfig.Zl();
    }

    @a(key = f.f2io, value = "0")
    public int _h() {
        return this.mConfig._h();
    }

    @a(key = f.Zn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean _i() {
        return this.mConfig._i();
    }

    @a(key = f.Rn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean _j() {
        return this.mConfig._j();
    }

    @a(key = f.Il, value = "180")
    public int _k() {
        return this.mConfig._k();
    }

    @a(key = f.Qp, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean _l() {
        if (DataRepository.dataItemGlobal().getIntentType() != 1 || !c.isMTKPlatform()) {
            return this.mConfig._l();
        }
        return false;
    }

    public List<Float> a(boolean z, boolean z2, String str) {
        String t = t("capture_inner", str);
        if (z2) {
            t = t("video_inner", t);
        }
        if (z) {
            t = HybridZoomingSystem.ZOOM_INDEXS_SUPER_MOON;
        }
        List asList = Arrays.asList(t.split(":"));
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < asList.size(); i++) {
            arrayList.add(Float.valueOf(Float.parseFloat((String) asList.get(i))));
        }
        return arrayList;
    }

    public boolean a(CameraCapabilities cameraCapabilities) {
        return lh() && !c(cameraCapabilities);
    }

    @a(key = f.f0do)
    public String ai() {
        return this.mConfig.ai();
    }

    @a(key = f.Am, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean aj() {
        return this.mConfig.aj();
    }

    @a(key = f.bq, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean ak() {
        return this.mConfig.ak();
    }

    @a(key = f.Bo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean al() {
        return this.mConfig.al();
    }

    @a(key = f.qp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean am() {
        return this.mConfig.am();
    }

    public List<Integer> b(boolean z, boolean z2, String str) {
        String t = t("capture_ruler", str);
        if (z2) {
            t = t("video_ruler", t);
        }
        if (z) {
            t = HybridZoomingSystem.ZOOM_RULER_SUPER_MOON;
        }
        List asList = Arrays.asList(t.split(":"));
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < asList.size(); i++) {
            arrayList.add(Integer.valueOf(Integer.parseInt((String) asList.get(i))));
        }
        return arrayList;
    }

    @a(key = f.xk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean bh() {
        return this.mConfig.bh();
    }

    @a(key = f.Cp)
    public String bi() {
        String bi = this.mConfig.bi();
        if (TextUtils.isEmpty(bi)) {
            return null;
        }
        return bi;
    }

    @a(key = f.yn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean bj() {
        return this.mConfig.bj();
    }

    @a(key = f.cm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean bk() {
        return this.mConfig.bk();
    }

    @a(key = f.Cm, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean bl() {
        return this.mConfig.bl();
    }

    public boolean bm() {
        String kh = kh();
        if (kh == null || !kh.toLowerCase(Locale.ENGLISH).contains("telesr")) {
            return false;
        }
        String[] split = kh.toLowerCase(Locale.ENGLISH).split(";");
        if (split.length > 0) {
            for (String str : split) {
                String[] split2 = str.toLowerCase(Locale.ENGLISH).split(":");
                if (split2[0].equals("telesr")) {
                    return split2[1].equals("1");
                }
            }
        }
        return false;
    }

    @a(key = f.Yo, value = "1")
    public int ci() {
        if (il()) {
            if (li()) {
                return 1;
            }
            if (Di()) {
                return 2;
            }
        }
        return this.mConfig.ci();
    }

    @a(key = f.Lo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean cj() {
        return this.mConfig.cj();
    }

    @a(key = f.wm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean ck() {
        return this.mConfig.ck();
    }

    @a(key = f.Xk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean cl() {
        return ((double) Math.abs((((float) Util.sWindowHeight) / ((float) Util.sWindowWidth)) - 2.0833333f)) < 0.02d && this.mConfig.cl();
    }

    @a(key = f.Wl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean cm() {
        return this.mConfig.cm();
    }

    @a(key = f.Mo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean dh() {
        return this.mConfig.dh();
    }

    @a(key = f.vn, value = "0")
    public int di() {
        return this.mConfig.di();
    }

    @a(key = f.Pk, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean dj() {
        return this.mConfig.dj();
    }

    @a(key = f.ho, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean dk() {
        return this.mConfig.dk();
    }

    @a(key = f.Vk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean dl() {
        return ((float) Util.sWindowHeight) / ((float) Util.sWindowWidth) >= 2.1666667f && this.mConfig.dl();
    }

    @a(key = f.rp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean dm() {
        return this.mConfig.dm();
    }

    @a(key = f.vk, value = "1")
    public String eh() {
        return this.mConfig.eh();
    }

    @a(key = f.Qo, value = "0")
    public int ei() {
        return this.mConfig.ei();
    }

    @a(key = f.Vh, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean ej() {
        return this.mConfig.ej();
    }

    @a(key = f.tm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean ek() {
        return this.mConfig.ek();
    }

    @a(key = f.Wk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean el() {
        return ((double) Math.abs((((float) Util.sWindowHeight) / ((float) Util.sWindowWidth)) - 2.1111112f)) <= 0.02d && this.mConfig.el();
    }

    @a(key = f.np, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean em() {
        return this.mConfig.em();
    }

    @a(key = f.sk, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean fh() {
        return this.mConfig.fh();
    }

    @a(key = f.Sl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean fi() {
        return this.mConfig.fi();
    }

    @a(key = f.Rl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean fj() {
        return this.mConfig.fj();
    }

    @a(key = f.Fn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean fk() {
        return this.mConfig.fk();
    }

    @a(key = f.Yk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean fl() {
        return ((float) Util.sWindowHeight) / ((float) Util.sWindowWidth) >= 2.2222223f && this.mConfig.fl();
    }

    @a(key = f.Xm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean fm() {
        return this.mConfig.fm();
    }

    public Common getConfig() {
        return this.mConfig;
    }

    @a(key = f.tk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean gh() {
        return this.mConfig.gh();
    }

    @a(key = f.Ll, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean gi() {
        return Qh() < 0 || this.mConfig.gi();
    }

    @a(key = f.Vp, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean gj() {
        return this.mConfig.gj();
    }

    @a(key = f.Gn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean gk() {
        return this.mConfig.gk();
    }

    @a(key = f.Ep, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean gl() {
        return this.mConfig.gl();
    }

    @a(key = f._m, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean gm() {
        return this.mConfig.gm();
    }

    @a(key = f.uk, value = "6")
    public String hh() {
        return this.mConfig.hh();
    }

    @a(key = f.Rm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean hi() {
        return this.mConfig.hi();
    }

    @a(key = f.cp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean hj() {
        return this.mConfig.hj() || zg;
    }

    @a(key = f.xp, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean hk() {
        if (Util.isGlobalVersion() || !Util.isLocaleChinese()) {
            return false;
        }
        return this.mConfig.hk();
    }

    @a(key = f.dq, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean hl() {
        return this.mConfig.hl();
    }

    @a(key = f.Mp, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean hm() {
        return this.mConfig.hm();
    }

    @a(key = f.om, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean ih() {
        return this.mConfig.ih();
    }

    @a(key = f.eo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean ii() {
        return this.mConfig.ii();
    }

    @a(key = f.So, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean ij() {
        return this.mConfig.ij();
    }

    @a(key = f.lm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean ik() {
        return this.mConfig.ik();
    }

    @a(key = f.Xo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean il() {
        return this.mConfig.il() && (Di() || li());
    }

    @a(key = f.Np, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean im() {
        return this.mConfig.im();
    }

    @a(key = f.El, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean is4K30FpsEISSupported() {
        return this.mConfig.is4K30FpsEISSupported();
    }

    @a(key = f.Xn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean isCinematicPhotoSupported() {
        if (!c.Vg || Build.VERSION.SDK_INT != 28) {
            return this.mConfig.isCinematicPhotoSupported();
        }
        return false;
    }

    @a(key = f.Zp, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean isHighQualityPreferred() {
        return this.mConfig.isHighQualityPreferred();
    }

    @a(key = f.uo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean isSRRequireReprocess() {
        return this.mConfig.isSRRequireReprocess();
    }

    @a(key = f.jo, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean isSupport960VideoEditor() {
        return this.mConfig.isSupport960VideoEditor();
    }

    @a(key = f.sl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean isSupportBeautyBody() {
        return this.mConfig.isSupportBeautyBody();
    }

    @a(key = f.fm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean isSupportBokehAdjust() {
        return this.mConfig.isSupportBokehAdjust();
    }

    @a(key = f.Bm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean isSupportMacroMode() {
        return this.mConfig.isSupportMacroMode();
    }

    @a(key = f.Cl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean isSupportNormalWideLDC() {
        return this.mConfig.isSupportNormalWideLDC();
    }

    @a(key = f.xm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean isSupportShortVideoBeautyBody() {
        return this.mConfig.isSupportShortVideoBeautyBody();
    }

    @a(key = f.ql, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean isSupportUltraWide() {
        return this.mConfig.isSupportUltraWide();
    }

    @a(key = f.Dl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean isSupportUltraWideLDC() {
        return this.mConfig.isSupportUltraWideLDC();
    }

    @a(key = f.Zo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean jh() {
        return this.mConfig.jh();
    }

    @a(key = f.un, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean ji() {
        return this.mConfig.ji();
    }

    public boolean jj() {
        return this.mConfig.jj();
    }

    @a(key = f.zn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean jk() {
        if (Util.isGlobalVersion()) {
            return false;
        }
        if (!c.Vg || Build.VERSION.SDK_INT != 28) {
            return this.mConfig.jk();
        }
        return false;
    }

    @a(key = f._k, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean jl() {
        return this.mConfig.jl();
    }

    @a(key = f.tp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean jm() {
        return this.mConfig.jm();
    }

    @a(key = f.Fo)
    public String kh() {
        return this.mConfig.kh();
    }

    @a(key = f.Vo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean ki() {
        return this.mConfig.ki();
    }

    @a(key = f.ul, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean kj() {
        return this.mConfig.kj();
    }

    @a(key = f.fo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean kk() {
        return this.mConfig.kk();
    }

    @a(key = f.cl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean kl() {
        if (!ll()) {
            return nl();
        }
        if (!this.mConfig.kl() || !ll() || (!(163 == DataRepository.dataItemGlobal().getCurrentMode() || 165 == DataRepository.dataItemGlobal().getCurrentMode()) || CameraSettings.getCameraId() != 0 || CameraSettings.isUltraPixelOn() || ((double) CameraSettings.readZoom()) < 1.0d)) {
            Log.i(TAG, "Algo up disabled for mm-camera");
            return false;
        }
        Log.i(TAG, "Algo up enabled for mm-camera");
        return true;
    }

    @a(key = f.Kk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean km() {
        return this.mConfig.km();
    }

    @a(key = f.qn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean lh() {
        return this.mConfig.lh();
    }

    public boolean li() {
        if (this.yg == null) {
            this.yg = SystemProperties.get("ro.boot.hwc");
        }
        return "cn".equalsIgnoreCase(this.yg);
    }

    @a(key = f.vl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean lj() {
        return this.mConfig.lj();
    }

    @a(key = f.go, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean lk() {
        return this.mConfig.lk();
    }

    @a(key = f.Uo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean ll() {
        return this.mConfig.ll();
    }

    @a(key = f.Xp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean lm() {
        return this.mConfig.lm();
    }

    @a(key = f.qm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean mh() {
        return this.mConfig.mh();
    }

    public boolean mi() {
        return Arrays.asList(ml().toUpperCase(Locale.ENGLISH).split(":")).contains("CAPTURE_INTENT");
    }

    @a(key = f.Tl, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean mj() {
        return this.mConfig.mj();
    }

    @a(key = f.nn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean mk() {
        return this.mConfig.mk();
    }

    @a(key = f.dl)
    public String ml() {
        return this.mConfig.ml();
    }

    @a(key = f.rn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean mm() {
        return this.mConfig.mm();
    }

    @a(key = f.Rp)
    public String nh() {
        return this.mConfig.nh();
    }

    @a(key = f.bo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean nj() {
        return this.mConfig.nj();
    }

    @a(key = f.Qn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean nk() {
        return this.mConfig.nk();
    }

    public boolean nl() {
        return Build.VERSION.SDK_INT > 28 ? wo() : this.mConfig.kl();
    }

    @a(key = f.Wm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean nm() {
        if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            return false;
        }
        return this.mConfig.nm();
    }

    @a(key = f.AEC_LUX_HEIGHT_LIGHT, value = FastMotionConstant.FAST_MOTION_SPEED_10X)
    public int oh() {
        return this.mConfig.oh();
    }

    @a(key = f.Cn, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean oi() {
        return this.mConfig.oi();
    }

    @a(key = f.em, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean oj() {
        return this.mConfig.oj();
    }

    @a(key = f.No, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean ol() {
        return this.mConfig.ol() && !SystemProperties.getBoolean("close.append.yuv", false);
    }

    @a(key = f.Tk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean om() {
        return this.mConfig.om();
    }

    @a(key = f.AEC_LUX_LAST_LIGHT, value = CountryCodeConverter.GI)
    public int ph() {
        return this.mConfig.ph();
    }

    @a(key = f.gm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean pi() {
        if (Build.VERSION.SDK_INT < 28) {
            return false;
        }
        return this.mConfig.pi();
    }

    @a(key = f.Hn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean pj() {
        return this.mConfig.pj();
    }

    @a(key = f.Bp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean pk() {
        return this.mConfig.pk();
    }

    @a(key = f.Gp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean pl() {
        return this.mConfig.pl();
    }

    @a(key = f.Rk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean pm() {
        return this.mConfig.pm();
    }

    @a(key = f.Jk, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean qh() {
        return this.mConfig.qh();
    }

    @a(key = f.so, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean qi() {
        return this.mConfig.qi();
    }

    @a(key = f.kn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean qj() {
        if (Build.VERSION.SDK_INT == 28) {
            return false;
        }
        return this.mConfig.qj();
    }

    @a(key = f.Hl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean qk() {
        return this.mConfig.qk();
    }

    @a(key = f.Hp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean ql() {
        if (!c.eh || Build.VERSION.SDK_INT <= 29) {
            return this.mConfig.ql();
        }
        return false;
    }

    @a(key = f.Oo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean qm() {
        boolean z = SystemProperties.getBoolean("miuicamera.sat.video", false);
        if (z) {
            Log.d(TAG, "sat video debug prop:" + z);
            return z;
        } else if (!c.uh || c.bh) {
            return this.mConfig.qm();
        } else {
            return false;
        }
    }

    @a(key = f._n, value = "0")
    public int rh() {
        return this.mConfig.rh();
    }

    @a(key = f.fn, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean ri() {
        return this.mConfig.ri();
    }

    @a(key = f.ln, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean rj() {
        if (!c.Ym() && Build.VERSION.SDK_INT != 28) {
            return this.mConfig.rj();
        }
        return false;
    }

    @a(key = f.Kn, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean rk() {
        return this.mConfig.rk();
    }

    @a(key = f.bl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean rl() {
        return this.mConfig.rl();
    }

    @a(key = f.Mm)
    public String rm() {
        return this.mConfig.rm();
    }

    public Size sh() {
        String nh = nh();
        if (!TextUtils.isEmpty(nh) && nh.toLowerCase(Locale.ENGLISH).contains(":")) {
            String[] split = nh.toLowerCase(Locale.ENGLISH).split(":");
            if (split.length > 1) {
                String[] split2 = split[1].toLowerCase(Locale.ENGLISH).split("x");
                if (split2.length == 2) {
                    return new Size(Integer.parseInt(split2[0]), Integer.parseInt(split2[1]));
                }
            }
        }
        return null;
    }

    @a(key = f.yo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean shouldCheckSatFallbackState() {
        return this.mConfig.shouldCheckSatFallbackState();
    }

    @a(key = f.mm, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean si() {
        return this.mConfig.si();
    }

    @a(key = f.oo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean sj() {
        Camera2DataContainer instance = Camera2DataContainer.getInstance();
        boolean booleanValue = ((Boolean) Optional.ofNullable(instance.getCapabilities(instance.getMainBackCameraId())).map(a.INSTANCE).orElse(false)).booleanValue();
        boolean z = SystemProperties.getBoolean("miuicamera.dualvideo.show", false);
        boolean sj = this.mConfig.sj();
        return c.isMTKPlatform() ? z || (booleanValue && sj) : z || sj;
    }

    @a(key = f.An, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean sk() {
        return this.mConfig.sk();
    }

    @a(key = f.tl, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean sl() {
        return this.mConfig.sl();
    }

    public boolean sm() {
        String nh = nh();
        if (TextUtils.isEmpty(nh)) {
            return false;
        }
        if (!nh.toLowerCase(Locale.ENGLISH).contains(":")) {
            return Boolean.parseBoolean(nh);
        }
        String[] split = nh.toLowerCase(Locale.ENGLISH).split(":");
        if (split.length > 0) {
            return Boolean.parseBoolean(split[0]);
        }
        return false;
    }

    @a(key = f._o, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean supportColorEnhance() {
        return this.mConfig.supportColorEnhance() && Di();
    }

    @a(key = f.zm, value = "4")
    public String th() {
        return this.mConfig.th();
    }

    @a(key = f.kl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean ti() {
        return this.mConfig.ti();
    }

    @a(key = f.po, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean tj() {
        return this.mConfig.tj();
    }

    @a(key = f.zp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean tk() {
        return this.mConfig.tk();
    }

    @a(key = f.ll, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean tl() {
        return this.mConfig.tl();
    }

    @a(key = f.Ik, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean tm() {
        return this.mConfig.tm();
    }

    @a(key = f.gq, value = "0")
    public long uh() {
        return this.mConfig.uh();
    }

    @a(key = f.Xl, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean ui() {
        return this.mConfig.ui();
    }

    @a(key = f.To, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean uj() {
        return this.mConfig.uj();
    }

    @a(key = f.Ap, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean uk() {
        return this.mConfig.uk();
    }

    @a(key = f.Wp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean ul() {
        return this.mConfig.ul();
    }

    @a(key = f.Zm, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean um() {
        return this.mConfig.um();
    }

    @a(key = f.dp, value = "0")
    public int vh() {
        return this.mConfig.vh();
    }

    @a(key = f.Yl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean vi() {
        return this.mConfig.vi();
    }

    @a(key = f.Fm, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean vj() {
        return this.mConfig.vj();
    }

    @a(key = f.nm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean vk() {
        return this.mConfig.vk();
    }

    @a(key = f.Ok, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean vl() {
        return this.mConfig.vl();
    }

    @a(key = f.gn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean vm() {
        return this.mConfig.vm();
    }

    public float wh() {
        return this.mConfig.wh();
    }

    @a(key = f.Co, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean wi() {
        return this.mConfig.wi();
    }

    @a(key = f.Wn, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean wj() {
        return this.mConfig.wj();
    }

    @a(key = f.Dm, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean wk() {
        return this.mConfig.wk();
    }

    @a(key = f.jp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean wl() {
        return this.mConfig.wl();
    }

    public boolean wm() {
        return yk() || xk() || Ck() || Ak();
    }

    @a(key = f.Km, value = "common")
    public String xh() {
        return this.mConfig.xh();
    }

    @a(key = f.vo, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean xi() {
        return this.mConfig.xi();
    }

    @a(key = f.Ql, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean xj() {
        return this.mConfig.xj();
    }

    @a(key = f.fl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean xk() {
        return !yk() && this.mConfig.xk();
    }

    @a(key = f.Tm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean xl() {
        return this.mConfig.xl();
    }

    @a(key = f.Om, value = "-1")
    public int y(int i) {
        int so = this.mConfig.so();
        return so < 0 ? i : so;
    }

    @a(key = f.Ip)
    public int[] yh() {
        String yh = this.mConfig.yh();
        if (TextUtils.isEmpty(yh)) {
            return null;
        }
        try {
            String[] split = yh.split(":");
            int[] iArr = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                iArr[i] = Integer.parseInt(split[i].trim());
            }
            return iArr;
        } catch (NumberFormatException | PatternSyntaxException unused) {
            Log.w(TAG, "get default favorite modes fails.");
            return null;
        }
    }

    @a(key = f.Jm, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean yi() {
        return this.mConfig.yi();
    }

    @a(key = f.Im, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean yj() {
        return this.mConfig.yj();
    }

    @a(key = f.el, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean yk() {
        return this.mConfig.yk();
    }

    @a(key = f.Lp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean yl() {
        return this.mConfig.yl();
    }

    @a(key = f.sp, value = MistatsConstants.BaseEvent.VALUE_TRUE)
    public boolean z(int i) {
        if (i == 180 || i == 162) {
            return this.mConfig.xo();
        }
        return false;
    }

    @a(key = f.Ol, value = "0")
    public int zh() {
        return this.mConfig.zh();
    }

    @a(key = f.Vl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean zi() {
        return this.mConfig.zi();
    }

    @a(key = f.fq, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean zj() {
        return this.mConfig.zj();
    }

    @a(key = f.hl, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean zk() {
        return this.mConfig.zk();
    }

    @a(key = f.Fp, value = MistatsConstants.BaseEvent.VALUE_FALSE)
    public boolean zl() {
        return this.mConfig.zl();
    }
}
