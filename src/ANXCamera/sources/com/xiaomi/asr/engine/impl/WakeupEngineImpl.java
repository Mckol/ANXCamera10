package com.xiaomi.asr.engine.impl;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import com.google.android.apps.photos.api.PhotosOemApi;
import com.xiaomi.asr.engine.PhraseWakeupResult;
import com.xiaomi.asr.engine.WVPListener;
import com.xiaomi.asr.engine.jni.WakeupEngineInterface;
import com.xiaomi.asr.engine.record.VoiceRecord;
import com.xiaomi.asr.engine.utils.CircleBuffer;
import com.xiaomi.asr.engine.utils.SaveVoiceUtil;

public class WakeupEngineImpl {
    private static final int FEED_RESULT_DETECTED_END = 2;
    private static final int FEED_RESULT_DETECTED_LEVEL_ONE = 1;
    private static final String SDK_VERSION = "wakeup_sdk_202006011636";
    private static final String TAG = "WakeupEngineImpl";
    private static final int WAKEUP_BUFFER_SIZE = 640;
    private static WakeupEngineImpl mInstance;
    private String ModelPath = "ModelPath";
    private boolean init = false;
    private boolean isPrintLog = false;
    private boolean isWakeup = false;
    private boolean isWriteBackward;
    private CircleBuffer mBackwardData;
    private byte[] mCacheData;
    private CircleBuffer mForwardData;
    private WVPListener mOutListener;
    private VoiceRecord mVoiceRecord = new VoiceRecord(new VoiceRecordListener());
    private WakeupEngineInterface mWakeupEngineInterface = new WakeupEngineInterface();
    private Handler mWorkHandler;
    private HandlerThread mWorkHandlerThread;
    private boolean pause = false;
    private boolean running = false;
    private boolean saveRecord = false;
    private SaveVoiceUtil saveVoiceUtil;

    class HandlerCallback implements Handler.Callback {
        HandlerCallback() {
        }

        public boolean handleMessage(Message message) {
            if (WakeupEngineImpl.this.isPrintLog) {
                Log.d(WakeupEngineImpl.TAG, "start msg.what:" + message.what);
            }
            int i = message.what;
            if (i == 1) {
                String string = message.getData().getString(WakeupEngineImpl.this.ModelPath);
                Log.d(WakeupEngineImpl.TAG, "ModelPath:" + string);
                if (WakeupEngineImpl.this.mWakeupEngineInterface.wakeupInit(string) == 0) {
                    WakeupEngineImpl.this.init = true;
                }
                if (!WakeupEngineImpl.this.init) {
                    Log.e(WakeupEngineImpl.TAG, "init failed");
                }
                if (WakeupEngineImpl.this.mOutListener != null) {
                    WakeupEngineImpl.this.mOutListener.onInit(WakeupEngineImpl.this.init);
                }
            } else if (i != 2) {
                if (i != 3) {
                    if (i != 5) {
                        if (i == 6 && WakeupEngineImpl.this.init && WakeupEngineImpl.this.running) {
                            WakeupEngineImpl.this.mVoiceRecord.stopRecord();
                            WakeupEngineImpl.this.running = false;
                        }
                    } else if (WakeupEngineImpl.this.init) {
                        if (WakeupEngineImpl.this.running) {
                            WakeupEngineImpl.this.mVoiceRecord.stopRecord();
                            WakeupEngineImpl.this.running = false;
                        }
                        WakeupEngineImpl.this.mWakeupEngineInterface.wakeupDestroy();
                        WakeupEngineImpl.this.init = false;
                        if (WakeupEngineImpl.this.mOutListener != null) {
                            WakeupEngineImpl.this.mOutListener.onRelease();
                        }
                    }
                } else if (WakeupEngineImpl.this.init) {
                    byte[] byteArray = message.getData().getByteArray(PhotosOemApi.PATH_SPECIAL_TYPE_DATA);
                    int i2 = message.arg1;
                    int i3 = i2 / 2;
                    byte[] bArr = new byte[i3];
                    int i4 = 0;
                    int i5 = 0;
                    while (i4 < i2) {
                        bArr[i5] = byteArray[i4];
                        bArr[i5 + 1] = byteArray[i4 + 1];
                        i4 += 4;
                        i5 += 2;
                    }
                    WakeupEngineImpl.this.mForwardData.writeBuffer(bArr);
                    if (WakeupEngineImpl.this.saveRecord) {
                        WakeupEngineImpl.this.saveVoiceUtil.writeFile(bArr);
                    }
                    int wakeupFeedData = WakeupEngineImpl.this.mWakeupEngineInterface.wakeupFeedData(bArr, i3, 0);
                    if (WakeupEngineImpl.this.isPrintLog) {
                        Log.d(WakeupEngineImpl.TAG, "wakeup feed return:" + wakeupFeedData);
                    }
                    if (wakeupFeedData == 1) {
                        Log.d(WakeupEngineImpl.TAG, "Voice wakeup: r == FEED_RESULT_DETECTED_LEVEL_ONE");
                        WakeupEngineImpl.this.isWakeup = false;
                    }
                    if (wakeupFeedData == 2) {
                        Log.d(WakeupEngineImpl.TAG, "Voice wakeup: r == FEED_RESULT_DETECTED_END");
                        WakeupEngineImpl.this.isWakeup = true;
                        WakeupEngineImpl wakeupEngineImpl = WakeupEngineImpl.this;
                        wakeupEngineImpl.mCacheData = wakeupEngineImpl.mForwardData.readBuffer();
                        WakeupEngineImpl.this.mForwardData.reset();
                        WakeupEngineImpl.this.isWriteBackward = true;
                        if (WakeupEngineImpl.this.mOutListener != null) {
                            PhraseWakeupResult phraseWakeupResult = new PhraseWakeupResult();
                            phraseWakeupResult.setVoconWakeupPassed(true);
                            phraseWakeupResult.setVoconPhrase("小爱同学");
                            phraseWakeupResult.setScore(0.0f);
                            phraseWakeupResult.setAec(false);
                            Log.d(WakeupEngineImpl.TAG, "Voice wakeup has been successful");
                            WakeupEngineImpl.this.mOutListener.onPhraseSpotted(phraseWakeupResult);
                        }
                    }
                    if (WakeupEngineImpl.this.isWriteBackward) {
                        WakeupEngineImpl.this.mBackwardData.writeBuffer(bArr);
                    }
                    if (WakeupEngineImpl.this.mBackwardData.isFull()) {
                        if (!WakeupEngineImpl.this.isWakeup) {
                            if (WakeupEngineImpl.this.mOutListener != null) {
                                PhraseWakeupResult phraseWakeupResult2 = new PhraseWakeupResult();
                                phraseWakeupResult2.setVoconWakeupPassed(false);
                                phraseWakeupResult2.setVoconPhrase("");
                                WakeupEngineImpl.this.mOutListener.onPhraseSpotted(phraseWakeupResult2);
                            }
                            WakeupEngineImpl.this.mWakeupEngineInterface.wakeupReset();
                        }
                        byte[] readBuffer = WakeupEngineImpl.this.mBackwardData.readBuffer();
                        if (WakeupEngineImpl.this.mOutListener != null) {
                            byte[] bArr2 = new byte[(WakeupEngineImpl.this.mCacheData.length + readBuffer.length)];
                            System.arraycopy(WakeupEngineImpl.this.mCacheData, 0, bArr2, 0, WakeupEngineImpl.this.mCacheData.length);
                            System.arraycopy(readBuffer, 0, bArr2, WakeupEngineImpl.this.mCacheData.length, readBuffer.length);
                            WakeupEngineImpl.this.mOutListener.onAudioData(bArr2);
                        }
                        WakeupEngineImpl.this.mBackwardData.reset();
                        WakeupEngineImpl.this.mCacheData = null;
                        WakeupEngineImpl.this.isWriteBackward = false;
                    }
                }
            } else if (WakeupEngineImpl.this.init && !WakeupEngineImpl.this.running) {
                WakeupEngineImpl.this.mVoiceRecord.startRecord();
                WakeupEngineImpl.this.running = true;
                WakeupEngineImpl.this.mWakeupEngineInterface.wakeupReset();
            }
            if (WakeupEngineImpl.this.isPrintLog) {
                Log.d(WakeupEngineImpl.TAG, "end msg.what:" + message.what);
            }
            return false;
        }
    }

    class VoiceRecordListener implements VoiceRecord.RecordListener {
        VoiceRecordListener() {
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onAudioSessionId(int i) {
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecordCreateError() {
            WakeupEngineImpl.this.running = false;
            if (WakeupEngineImpl.this.mOutListener != null) {
                WakeupEngineImpl.this.mOutListener.onConflictAudio();
            }
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecordRelease() {
            if (WakeupEngineImpl.this.mOutListener != null) {
                WakeupEngineImpl.this.mOutListener.onStopAudio();
            }
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecording(byte[] bArr, int i) {
            if (!WakeupEngineImpl.this.pause) {
                byte[] bArr2 = new byte[i];
                System.arraycopy(bArr, 0, bArr2, 0, i);
                Message obtainMessage = WakeupEngineImpl.this.mWorkHandler.obtainMessage(3);
                Bundle bundle = new Bundle();
                bundle.putByteArray(PhotosOemApi.PATH_SPECIAL_TYPE_DATA, bArr2);
                obtainMessage.setData(bundle);
                obtainMessage.arg1 = i;
                WakeupEngineImpl.this.mWorkHandler.sendMessage(obtainMessage);
            }
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecordingEnd() {
            if (WakeupEngineImpl.this.saveRecord) {
                WakeupEngineImpl.this.saveVoiceUtil.closeFile();
            }
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecordingFailed() {
            WakeupEngineImpl.this.running = false;
            if (WakeupEngineImpl.this.mOutListener != null) {
                WakeupEngineImpl.this.mOutListener.onConflictAudio();
            }
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecordingStart() {
            if (WakeupEngineImpl.this.mOutListener != null) {
                WakeupEngineImpl.this.mOutListener.onStartAudio();
            }
            if (WakeupEngineImpl.this.saveRecord) {
                WakeupEngineImpl.this.saveVoiceUtil.createFile("record");
            }
        }
    }

    private WakeupEngineImpl() {
        this.mVoiceRecord.setParam(1, 1);
        this.mVoiceRecord.setParam(2, WAKEUP_BUFFER_SIZE);
        this.mVoiceRecord.setParam(0, 12);
        this.mWorkHandlerThread = new HandlerThread("wakeup-engine-impl-thread");
        this.mWorkHandlerThread.start();
        this.mWorkHandler = new Handler(this.mWorkHandlerThread.getLooper(), new HandlerCallback());
        this.mForwardData = new CircleBuffer(64000);
        this.mBackwardData = new CircleBuffer(19200);
        this.saveVoiceUtil = new SaveVoiceUtil();
        this.saveVoiceUtil.setSaveDir("/sdcard/miasr/asr/record");
    }

    public static WakeupEngineImpl getInstance() {
        if (mInstance == null) {
            synchronized (WakeupEngineImpl.class) {
                if (mInstance == null) {
                    mInstance = new WakeupEngineImpl();
                }
            }
        }
        return mInstance;
    }

    public void init(String str) {
        Message obtainMessage = this.mWorkHandler.obtainMessage(1);
        Bundle bundle = new Bundle();
        bundle.putString(this.ModelPath, str);
        obtainMessage.setData(bundle);
        this.mWorkHandler.sendMessage(obtainMessage);
    }

    public void loadLibraryWrapper(String str) {
        WakeupEngineInterface.loadLibrary(str);
    }

    public void openLog(boolean z) {
        this.isPrintLog = z;
    }

    public void release() {
        if (this.isPrintLog) {
            Log.d(TAG, "call release, releaseRecordMs:0");
        }
        this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(5));
        this.mWorkHandler.removeMessages(6);
    }

    public void restart() {
        stop(0);
        start(500);
    }

    public void saveRecord(boolean z) {
        this.saveRecord = z;
    }

    public void setListener(WVPListener wVPListener) {
        this.mOutListener = wVPListener;
    }

    public void start(int i) {
        if (this.isPrintLog) {
            Log.d(TAG, "call start, startRecordMs:" + i);
        }
        if (i <= 0) {
            this.mWorkHandler.removeMessages(3);
            this.mWorkHandler.removeMessages(6);
        }
        this.pause = false;
        this.mWorkHandler.sendMessageDelayed(this.mWorkHandler.obtainMessage(2), (long) i);
    }

    public void stop(long j) {
        if (this.isPrintLog) {
            Log.d(TAG, "call stop, stopRecordMs:" + j);
        }
        this.pause = true;
        this.mWorkHandler.removeMessages(3);
        this.mWorkHandler.sendMessageDelayed(this.mWorkHandler.obtainMessage(6), j);
    }

    public String wakeupVersion() {
        if (!this.init) {
            return "";
        }
        return "wakeup_sdk_202006011636 " + this.mWakeupEngineInterface.wakeupVersion();
    }
}
