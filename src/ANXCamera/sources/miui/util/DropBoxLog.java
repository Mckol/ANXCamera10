package miui.util;

import android.os.DropBoxManager;
import android.os.Process;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;
import miui.reflect.Field;

public class DropBoxLog {
    private static final String CLOUD_DROP_BOX_LOG_TAG = "micloud";
    private static final SoftReferenceSingleton<SimpleDateFormat> DATE_FORMAT = new SoftReferenceSingleton<SimpleDateFormat>() {
        /* class miui.util.DropBoxLog.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // miui.util.SoftReferenceSingleton
        public SimpleDateFormat createInstance() {
            return new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        }
    };
    private StringBuilder mStringBuilder = new StringBuilder();

    private void appendLog(String str, String str2, String str3, Throwable th) {
        this.mStringBuilder.append(DATE_FORMAT.get().format(new Date()));
        this.mStringBuilder.append(' ');
        this.mStringBuilder.append(str);
        this.mStringBuilder.append('/');
        this.mStringBuilder.append(str2);
        this.mStringBuilder.append('(');
        this.mStringBuilder.append(Process.myPid());
        this.mStringBuilder.append(')');
        this.mStringBuilder.append(':');
        this.mStringBuilder.append(str3);
        if (th != null) {
            this.mStringBuilder.append(':');
            this.mStringBuilder.append(Log.getStackTraceString(th));
        }
        this.mStringBuilder.append('\r');
    }

    public static void writeLog(CharSequence charSequence, String str) {
        DropBoxManager dropBoxManager = (DropBoxManager) AppConstants.getCurrentApplication().getSystemService("dropbox");
        if (charSequence != null && charSequence.length() > 0 && dropBoxManager != null && dropBoxManager.isTagEnabled(str)) {
            dropBoxManager.addText(str, charSequence.toString());
        }
    }

    public void commit(String str) {
        if (str == null) {
            str = AppConstants.getCurrentApplication().getPackageName();
        }
        StringBuilder sb = this.mStringBuilder;
        writeLog(sb, "micloud_" + str);
        this.mStringBuilder.setLength(0);
    }

    public DropBoxLog d(String str, String str2) {
        Log.d(str, str2);
        appendLog(Field.DOUBLE_SIGNATURE_PRIMITIVE, str, str2, null);
        return this;
    }

    public DropBoxLog d(String str, String str2, Throwable th) {
        Log.d(str, str2, th);
        appendLog(Field.DOUBLE_SIGNATURE_PRIMITIVE, str, str2, th);
        return this;
    }

    public DropBoxLog e(String str, String str2) {
        Log.e(str, str2);
        appendLog("E", str, str2, null);
        return this;
    }

    public DropBoxLog e(String str, String str2, Throwable th) {
        Log.e(str, str2, th);
        appendLog("E", str, str2, th);
        return this;
    }

    public String getMessage() {
        return this.mStringBuilder.toString();
    }

    public DropBoxLog i(String str, String str2) {
        Log.i(str, str2);
        appendLog(Field.INT_SIGNATURE_PRIMITIVE, str, str2, null);
        return this;
    }

    public DropBoxLog i(String str, String str2, Throwable th) {
        Log.i(str, str2, th);
        appendLog(Field.INT_SIGNATURE_PRIMITIVE, str, str2, th);
        return this;
    }

    public DropBoxLog v(String str, String str2) {
        Log.v(str, str2);
        appendLog("V", str, str2, null);
        return this;
    }

    public DropBoxLog v(String str, String str2, Throwable th) {
        Log.v(str, str2, th);
        appendLog("V", str, str2, th);
        return this;
    }

    public DropBoxLog w(String str, String str2) {
        Log.w(str, str2);
        appendLog("W", str, str2, null);
        return this;
    }

    public DropBoxLog w(String str, String str2, Throwable th) {
        Log.w(str, str2, th);
        appendLog("W", str, str2, th);
        return this;
    }
}
