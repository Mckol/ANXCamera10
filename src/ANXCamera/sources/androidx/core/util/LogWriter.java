package androidx.core.util;

import android.util.Log;
import androidx.annotation.RestrictTo;
import java.io.Writer;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class LogWriter extends Writer {
    private StringBuilder mBuilder = new StringBuilder(128);
    private final String mTag;

    public LogWriter(String str) {
        this.mTag = str;
    }

    private void flushBuilder() {
        if (this.mBuilder.length() > 0) {
            Log.d(this.mTag, this.mBuilder.toString());
            StringBuilder sb = this.mBuilder;
            sb.delete(0, sb.length());
        }
    }

    @Override // java.io.Closeable, java.io.Writer, java.lang.AutoCloseable
    public void close() {
        flushBuilder();
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
        flushBuilder();
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            char c2 = cArr[i + i3];
            if (c2 == '\n') {
                flushBuilder();
            } else {
                this.mBuilder.append(c2);
            }
        }
    }
}
