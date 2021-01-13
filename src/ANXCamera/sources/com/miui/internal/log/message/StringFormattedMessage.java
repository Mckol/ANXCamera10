package com.miui.internal.log.message;

import android.util.Log;
import com.miui.internal.log.util.AppendableFormatter;
import java.io.IOException;
import java.util.Locale;

public class StringFormattedMessage extends AbstractMessage {
    private static final String TAG = "StringFormattedMessage";
    private String mFormat;
    private AppendableFormatter mFormatter = new AppendableFormatter();
    private Object[] mParameters;
    private Throwable mThrowable;

    public static StringFormattedMessage obtain() {
        return (StringFormattedMessage) MessageFactory.obtain(StringFormattedMessage.class);
    }

    @Override // com.miui.internal.log.message.AbstractMessage, com.miui.internal.log.message.Message
    public void format(Appendable appendable) {
        Object[] objArr;
        if (this.mFormat == null || (objArr = this.mParameters) == null || objArr.length == 0) {
            try {
                appendable.append(this.mFormat);
            } catch (IOException e2) {
                Log.e(TAG, "Fail to format message", e2);
            }
        } else {
            this.mFormatter.setAppendable(appendable);
            this.mFormatter.format(Locale.US, this.mFormat, this.mParameters);
        }
    }

    public String getFormat() {
        return this.mFormat;
    }

    public Object[] getParameters() {
        return this.mParameters;
    }

    @Override // com.miui.internal.log.message.AbstractMessage, com.miui.internal.log.message.Message
    public Throwable getThrowable() {
        return this.mThrowable;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.log.message.AbstractMessage
    public void onRecycle() {
        this.mFormat = null;
        this.mParameters = null;
        this.mThrowable = null;
        this.mFormatter.setAppendable(null);
    }

    public StringFormattedMessage setFormat(String str) {
        this.mFormat = str;
        return this;
    }

    public StringFormattedMessage setParameters(Object[] objArr) {
        this.mParameters = objArr;
        return this;
    }

    public StringFormattedMessage setThrowable(Throwable th) {
        this.mThrowable = th;
        return this;
    }
}
