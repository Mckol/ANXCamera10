package com.miui.internal.graphics.gif;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.miui.internal.graphics.gif.DecodeGifImageHelper;
import miui.io.ResettableInputStream;

/* access modifiers changed from: package-private */
/* compiled from: DecodeGifImageHelper */
public class DecodeGifFrames extends Handler {
    private static final int MESSAGE_WHAT_START = 1;
    protected static final String TAG = "DecodeGifFrames";
    private Handler mCallerHandler;
    DecodeGifImageHelper.GifDecodeResult mDecodeResult;
    private ResettableInputStream mGifSource;
    HandlerThread mHandlerThread;
    private long mMaxDecodeSize;

    public DecodeGifFrames(HandlerThread handlerThread, ResettableInputStream resettableInputStream, long j, Handler handler) {
        super(handlerThread.getLooper());
        this.mHandlerThread = handlerThread;
        this.mMaxDecodeSize = j;
        this.mGifSource = resettableInputStream;
        this.mCallerHandler = handler;
    }

    public static DecodeGifFrames createInstance(ResettableInputStream resettableInputStream, long j, Handler handler) {
        HandlerThread handlerThread = new HandlerThread("handler thread to decode GIF frames");
        handlerThread.start();
        return new DecodeGifFrames(handlerThread, resettableInputStream, j, handler);
    }

    public void decode(int i) {
        if (this.mDecodeResult == null) {
            this.mDecodeResult = new DecodeGifImageHelper.GifDecodeResult();
            sendMessage(obtainMessage(1, i, 0));
        }
    }

    public void destroy() {
        this.mHandlerThread.quit();
    }

    /* access modifiers changed from: protected */
    @Override // java.lang.Object
    public void finalize() throws Throwable {
        this.mHandlerThread.quit();
        super.finalize();
    }

    public DecodeGifImageHelper.GifDecodeResult getAndClearDecodeResult() {
        DecodeGifImageHelper.GifDecodeResult gifDecodeResult = this.mDecodeResult;
        this.mDecodeResult = null;
        return gifDecodeResult;
    }

    public void handleMessage(Message message) {
        if (message.what == 1) {
            DecodeGifImageHelper.GifDecodeResult decode = DecodeGifImageHelper.decode(this.mGifSource, this.mMaxDecodeSize, message.arg1);
            DecodeGifImageHelper.GifDecodeResult gifDecodeResult = this.mDecodeResult;
            gifDecodeResult.mGifDecoder = decode.mGifDecoder;
            gifDecodeResult.mIsDecodeOk = decode.mIsDecodeOk;
            this.mCallerHandler.sendEmptyMessage(1);
        }
    }
}
