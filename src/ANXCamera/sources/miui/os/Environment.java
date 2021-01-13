package miui.os;

import android.content.Context;
import com.miui.internal.variable.Android_Os_Process_class;
import java.io.File;
import java.util.regex.Pattern;
import miui.util.AttributeResolver;

public class Environment extends android.os.Environment {
    private static File EXTERNAL_STORAGE_MIUI_DIRECTORY = null;
    private static final File MIUI_APP_DIRECTORY = new File(getMiuiDataDirectory(), "apps");
    private static final File MIUI_CUSTOMIZED_DIRECTORY = new File(getMiuiDataDirectory(), "current");
    private static final File MIUI_DATA_DIRECTORY = new File("/data/miui/");
    private static final String MIUI_DIRECTORY_NAME = "MIUI";
    private static final File MIUI_PRESET_APP_DIRECTORY = new File(getMiuiDataDirectory(), "preset_apps");
    private static final Android_Os_Process_class ProcessClass = Android_Os_Process_class.Factory.getInstance().get();
    private static int sCpuCount = 0;

    protected Environment() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static int getCpuCount() {
        if (sCpuCount == 0) {
            Pattern compile = Pattern.compile("cpu[0-9]*");
            int i = 0;
            for (String str : new File("/sys/devices/system/cpu/").list()) {
                if (compile.matcher(str).matches()) {
                    i++;
                }
            }
            sCpuCount = i;
        }
        return sCpuCount;
    }

    public static File getExternalStorageMiuiDirectory() {
        try {
            if (EXTERNAL_STORAGE_MIUI_DIRECTORY == null) {
                EXTERNAL_STORAGE_MIUI_DIRECTORY = new File(android.os.Environment.getExternalStorageDirectory(), MIUI_DIRECTORY_NAME);
            }
            if (!EXTERNAL_STORAGE_MIUI_DIRECTORY.exists() && android.os.Environment.getExternalStorageDirectory().exists()) {
                EXTERNAL_STORAGE_MIUI_DIRECTORY.mkdir();
            }
            return EXTERNAL_STORAGE_MIUI_DIRECTORY;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static File getMiuiAppDirectory() {
        return MIUI_APP_DIRECTORY;
    }

    public static File getMiuiCustomizedDirectory() {
        return MIUI_CUSTOMIZED_DIRECTORY;
    }

    public static File getMiuiDataDirectory() {
        return MIUI_DATA_DIRECTORY;
    }

    public static File getMiuiPresetAppDirectory() {
        return MIUI_PRESET_APP_DIRECTORY;
    }

    public static boolean isExternalStorageMounted() {
        return "mounted".equals(android.os.Environment.getExternalStorageState());
    }

    public static boolean isUsingMiui(Context context) {
        return AttributeResolver.isUsingMiuiTheme(context);
    }
}
