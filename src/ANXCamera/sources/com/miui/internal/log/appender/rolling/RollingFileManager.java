package com.miui.internal.log.appender.rolling;

import com.miui.internal.log.appender.FileManager;

public class RollingFileManager extends FileManager {
    private String mLogPath;
    private RolloverStrategy mRolloverStrategy;

    public RollingFileManager(String str, String str2) {
        super(str, str2);
    }

    private void checkRollover() {
        RolloverStrategy rolloverStrategy;
        if (this.mLogFile != null && (rolloverStrategy = this.mRolloverStrategy) != null) {
            this.mLogPath = rolloverStrategy.rollover(this);
            if (this.mLogPath != null) {
                close();
            }
        }
    }

    public RolloverStrategy getRolloverStrategy() {
        return this.mRolloverStrategy;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.log.appender.FileManager
    public String onBuildLogPath() {
        String str = this.mLogPath;
        return str == null ? super.onBuildLogPath() : str;
    }

    public synchronized void setRolloverStrategy(RolloverStrategy rolloverStrategy) {
        this.mRolloverStrategy = rolloverStrategy;
    }

    @Override // com.miui.internal.log.appender.FileManager
    public synchronized void write(String str) {
        checkRollover();
        super.write(str);
    }
}
