package miui.util;

import android.text.TextUtils;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import miui.os.SystemProperties;
import miui.view.MiuiHapticFeedbackConstants;

public class HapticFeedbackUtil {
    public static final String EFFECT_KEY_FLICK = "flick";
    public static final String EFFECT_KEY_LONG_PRESS = "long_press";
    public static final String EFFECT_KEY_MESH_HEAVY = "mesh_heavy";
    public static final String EFFECT_KEY_MESH_LIGHT = "mesh_light";
    public static final String EFFECT_KEY_MESH_NORMAL = "mesh_normal";
    public static final String EFFECT_KEY_POPUP_LIGHT = "popup_light";
    public static final String EFFECT_KEY_POPUP_NORMAL = "popup_normal";
    public static final String EFFECT_KEY_SWITCH = "switch";
    public static final String EFFECT_KEY_TAP_LIGHT = "tap_light";
    public static final String EFFECT_KEY_TAP_NORMAL = "tap_normal";
    public static final String EFFECT_KEY_VIRTUAL_KEY_DOWN = "virtual_key_down";
    public static final String EFFECT_KEY_VIRTUAL_KEY_LONGPRESS = "virtual_key_longpress";
    public static final String EFFECT_KEY_VIRTUAL_KEY_TAP = "virtual_key_tap";
    public static final String EFFECT_KEY_VIRTUAL_KEY_UP = "virtual_key_up";
    private static final SparseArray<String> ID_TO_KEY = new SparseArray<>();
    private static final HashMap<String, String> PROPERTY_KEY = new HashMap<>();
    private static final List<String> PROPERTY_MOTOR_KEY = new ArrayList();
    private static final int VIRTUAL_RELEASED = 2;

    static {
        ID_TO_KEY.put(1, EFFECT_KEY_VIRTUAL_KEY_DOWN);
        ID_TO_KEY.put(0, EFFECT_KEY_VIRTUAL_KEY_LONGPRESS);
        ID_TO_KEY.put(3, EFFECT_KEY_VIRTUAL_KEY_TAP);
        ID_TO_KEY.put(2, EFFECT_KEY_VIRTUAL_KEY_UP);
        ID_TO_KEY.put(268435456, EFFECT_KEY_TAP_NORMAL);
        ID_TO_KEY.put(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_TAP_LIGHT, EFFECT_KEY_TAP_LIGHT);
        ID_TO_KEY.put(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_FLICK, EFFECT_KEY_FLICK);
        ID_TO_KEY.put(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_SWITCH, EFFECT_KEY_SWITCH);
        ID_TO_KEY.put(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_MESH_HEAVY, EFFECT_KEY_MESH_HEAVY);
        ID_TO_KEY.put(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_MESH_NORMAL, EFFECT_KEY_MESH_NORMAL);
        ID_TO_KEY.put(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_MESH_LIGHT, EFFECT_KEY_MESH_LIGHT);
        ID_TO_KEY.put(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_LONG_PRESS, EFFECT_KEY_LONG_PRESS);
        ID_TO_KEY.put(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_POPUP_NORMAL, EFFECT_KEY_POPUP_NORMAL);
        ID_TO_KEY.put(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_POPUP_LIGHT, EFFECT_KEY_POPUP_LIGHT);
        PROPERTY_KEY.put(EFFECT_KEY_VIRTUAL_KEY_DOWN, "sys.haptic.down");
        PROPERTY_KEY.put(EFFECT_KEY_VIRTUAL_KEY_LONGPRESS, "sys.haptic.long");
        PROPERTY_KEY.put(EFFECT_KEY_VIRTUAL_KEY_TAP, "sys.haptic.tap");
        PROPERTY_KEY.put(EFFECT_KEY_VIRTUAL_KEY_UP, "sys.haptic.up");
        PROPERTY_KEY.put(EFFECT_KEY_TAP_NORMAL, "sys.haptic.tap.normal");
        PROPERTY_KEY.put(EFFECT_KEY_TAP_LIGHT, "sys.haptic.tap.light");
        PROPERTY_KEY.put(EFFECT_KEY_FLICK, "sys.haptic.flick");
        PROPERTY_KEY.put(EFFECT_KEY_SWITCH, "sys.haptic.switch");
        PROPERTY_KEY.put(EFFECT_KEY_MESH_HEAVY, "sys.haptic.mesh.heavy");
        PROPERTY_KEY.put(EFFECT_KEY_MESH_NORMAL, "sys.haptic.mesh.normal");
        PROPERTY_KEY.put(EFFECT_KEY_MESH_LIGHT, "sys.haptic.mesh.light");
        PROPERTY_KEY.put(EFFECT_KEY_LONG_PRESS, "sys.haptic.long.press");
        PROPERTY_KEY.put(EFFECT_KEY_POPUP_NORMAL, "sys.haptic.popup.normal");
        PROPERTY_KEY.put(EFFECT_KEY_POPUP_LIGHT, "sys.haptic.popup.light");
        PROPERTY_MOTOR_KEY.add(EFFECT_KEY_TAP_NORMAL);
        PROPERTY_MOTOR_KEY.add(EFFECT_KEY_TAP_LIGHT);
        PROPERTY_MOTOR_KEY.add(EFFECT_KEY_FLICK);
        PROPERTY_MOTOR_KEY.add(EFFECT_KEY_SWITCH);
        PROPERTY_MOTOR_KEY.add(EFFECT_KEY_MESH_HEAVY);
        PROPERTY_MOTOR_KEY.add(EFFECT_KEY_MESH_NORMAL);
        PROPERTY_MOTOR_KEY.add(EFFECT_KEY_MESH_LIGHT);
        PROPERTY_MOTOR_KEY.add(EFFECT_KEY_LONG_PRESS);
        PROPERTY_MOTOR_KEY.add(EFFECT_KEY_POPUP_NORMAL);
        PROPERTY_MOTOR_KEY.add(EFFECT_KEY_POPUP_LIGHT);
    }

    public static boolean isSupportLinearMotorVibrate() {
        return "linear".equals(SystemProperties.get("sys.haptic.motor"));
    }

    public static boolean isSupportLinearMotorVibrate(int i) {
        if (!isSupportLinearMotorVibrate()) {
            return false;
        }
        String str = ID_TO_KEY.get(i);
        return PROPERTY_MOTOR_KEY.contains(str) && !TextUtils.isEmpty(SystemProperties.get(PROPERTY_KEY.get(str)));
    }
}
