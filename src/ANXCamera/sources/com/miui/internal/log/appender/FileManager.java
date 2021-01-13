package com.miui.internal.log.appender;

import android.os.SystemClock;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import miui.util.IOUtils;

public class FileManager {
    private static final int FILE_CHECK_INTERVAL = 1000;
    private static final String LOG_EXTENSION = ".log";
    private static final int PREPARE_RETRY_INTERVAL = 30000;
    private static final int RETRY_LIMIT = 10;
    private static final String TAG = "FileManager";
    private long mFileCheckStamp;
    protected File mLogFile;
    private long mLogLength;
    protected String mLogName;
    protected String mLogRoot;
    private FileOutputStream mOutputStream;
    private long mPrepareRetryStamp;
    private int mRetryCount;
    private OutputStreamWriter mWriter;

    public FileManager(String str, String str2) {
        this.mLogRoot = str;
        this.mLogName = str2;
    }

    private void doWrite(String str) throws IOException {
        this.mWriter.write(str);
        this.mWriter.flush();
        this.mLogLength += (long) str.length();
    }

    private void prepareWriter() {
        if (this.mRetryCount >= 10) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (elapsedRealtime - this.mPrepareRetryStamp > 30000) {
                this.mPrepareRetryStamp = elapsedRealtime;
            } else {
                return;
            }
        }
        this.mRetryCount++;
        this.mLogFile = onCreateLogFile();
        File file = this.mLogFile;
        if (file != null) {
            try {
                this.mOutputStream = new FileOutputStream(file, true);
                this.mWriter = new OutputStreamWriter(this.mOutputStream);
                this.mRetryCount = 0;
                this.mLogLength = this.mLogFile.length();
            } catch (FileNotFoundException e2) {
                Log.e(TAG, "Fail to create writer: " + this.mLogFile.getPath(), e2);
            }
        }
    }

    private void repairWriter() {
        close();
        prepareWriter();
    }

    public synchronized void close() {
        this.mLogFile = null;
        this.mRetryCount = 0;
        this.mOutputStream = null;
        IOUtils.closeQuietly((Writer) this.mWriter);
        this.mWriter = null;
        this.mLogLength = 0;
    }

    public String getLogExtension() {
        return LOG_EXTENSION;
    }

    public File getLogFile() {
        return this.mLogFile;
    }

    public String getLogName() {
        return this.mLogName;
    }

    public String getLogRoot() {
        return this.mLogRoot;
    }

    public long getLogSize() {
        return this.mLogLength;
    }

    /* access modifiers changed from: protected */
    public String onBuildLogPath() {
        return this.mLogRoot + "/" + this.mLogName + LOG_EXTENSION;
    }

    /* access modifiers changed from: protected */
    public File onCreateLogFile() {
        String onBuildLogPath = onBuildLogPath();
        if (onBuildLogPath == null) {
            Log.e(TAG, "Fail to build log path");
            return null;
        }
        File file = new File(onBuildLogPath);
        File parentFile = file.getParentFile();
        if (parentFile.exists()) {
            if (!parentFile.isDirectory()) {
                Log.e(TAG, "LogDir is not a directory: " + parentFile.getPath());
                return null;
            }
        } else if (!parentFile.mkdirs() && !parentFile.exists()) {
            Log.e(TAG, "Fail to create directory: " + parentFile.getPath());
            return null;
        }
        if (!file.exists()) {
            try {
                if (!file.createNewFile() && !file.exists()) {
                    Log.e(TAG, "Fail to create LogFile: " + onBuildLogPath);
                    return null;
                }
            } catch (IOException e2) {
                Log.e(TAG, "Fail to create LogFile: " + onBuildLogPath, e2);
                return null;
            }
        } else if (!file.isFile()) {
            Log.e(TAG, "LogFile is not a file: " + onBuildLogPath);
            return null;
        }
        return file;
    }

    public synchronized void write(String str) {
        if (this.mWriter == null) {
            prepareWriter();
        } else if (this.mWriter != null) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (elapsedRealtime - this.mFileCheckStamp > 1000) {
                this.mFileCheckStamp = elapsedRealtime;
                if (!this.mLogFile.exists()) {
                    Log.d(TAG, "Repair writer for log file is missing");
                    repairWriter();
                }
            }
        }
        if (this.mWriter == null) {
            Log.e(TAG, "Fail to append log for writer is null");
        } else {
            try {
                doWrite(str);
            } catch (IOException e2) {
                Log.w(TAG, "Retry to write log", e2);
                repairWriter();
                if (this.mWriter == null) {
                    Log.e(TAG, "Fail to append log for writer is null when retry");
                } else {
                    try {
                        doWrite(str);
                    } catch (IOException e3) {
                        Log.e(TAG, "Fail to append log for writer is null when retry", e3);
                    }
                }
            }
        }
    }
}
