package miui.os;

import android.util.Log;
import java.io.IOException;
import java.util.Locale;

public class ProcessUtils {
    private static final String TAG = "ProcessUtils";

    protected ProcessUtils() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static String getProcessNameByPid(int i) {
        String format = String.format(Locale.US, "/proc/%d/cmdline", Integer.valueOf(i));
        try {
            String readFileAsString = FileUtils.readFileAsString(format);
            if (readFileAsString == null) {
                return null;
            }
            int indexOf = readFileAsString.indexOf(0);
            return indexOf >= 0 ? readFileAsString.substring(0, indexOf) : readFileAsString;
        } catch (IOException e2) {
            Log.e(TAG, "Fail to read cmdline: " + format, e2);
            return null;
        }
    }
}
