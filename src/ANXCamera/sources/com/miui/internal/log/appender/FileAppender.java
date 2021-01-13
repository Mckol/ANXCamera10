package com.miui.internal.log.appender;

import android.util.Log;
import com.miui.internal.log.Level;
import com.miui.internal.log.format.Formatter;
import com.miui.internal.log.message.Message;

public class FileAppender implements Appender {
    private static final String TAG = "FileAppender";
    private FileManager mFileManager;
    private Formatter mFormatter;

    private void doAppend(String str, String str2, long j, Level level, String str3, Throwable th, Message message) {
        Formatter formatter = this.mFormatter;
        if (formatter == null) {
            Log.e(TAG, "Fail to append log for formatter is null");
            return;
        }
        FileManager fileManager = this.mFileManager;
        if (fileManager == null) {
            Log.e(TAG, "Fail to append log for FileManager is null");
        } else if (str3 == null) {
            fileManager.write(formatter.format(str, str2, j, level, message));
        } else {
            fileManager.write(formatter.format(str, str2, j, level, str3, th));
        }
    }

    @Override // com.miui.internal.log.appender.Appender
    public void append(String str, String str2, long j, Level level, Message message) {
        doAppend(str, str2, j, level, null, null, message);
    }

    @Override // com.miui.internal.log.appender.Appender
    public void append(String str, String str2, long j, Level level, String str3, Throwable th) {
        doAppend(str, str2, j, level, str3, th, null);
    }

    @Override // com.miui.internal.log.appender.Appender
    public void close() {
        FileManager fileManager = this.mFileManager;
        if (fileManager != null) {
            fileManager.close();
            this.mFileManager = null;
        }
    }

    public FileManager getFileManager() {
        return this.mFileManager;
    }

    @Override // com.miui.internal.log.appender.Appender
    public Formatter getFormatter() {
        return this.mFormatter;
    }

    public void setFileManager(FileManager fileManager) {
        if (this.mFileManager != fileManager) {
            close();
            this.mFileManager = fileManager;
        }
    }

    @Override // com.miui.internal.log.appender.Appender
    public void setFormatter(Formatter formatter) {
        this.mFormatter = formatter;
    }
}
