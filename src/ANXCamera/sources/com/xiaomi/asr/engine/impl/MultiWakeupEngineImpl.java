package com.xiaomi.asr.engine.impl;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import com.google.android.apps.photos.api.PhotosOemApi;
import com.xiaomi.asr.engine.PhraseWakeupResult;
import com.xiaomi.asr.engine.WVPListener;
import com.xiaomi.asr.engine.jni.MultiWakeupEngineInterface;
import com.xiaomi.asr.engine.jni.WakeupInfo;
import com.xiaomi.asr.engine.record.VoiceRecord;
import com.xiaomi.asr.engine.utils.CircleBuffer;
import com.xiaomi.asr.engine.utils.SaveVoiceUtil;

public class MultiWakeupEngineImpl {
    private static final int FEED_RESULT_DETECTED_END = 2;
    private static final int FEED_RESULT_DETECTED_LEVEL_ONE = 1;
    private static final String SDK_VERSION = "wakeup_sdk_202006011636";
    private static final String TAG = "WakeupEngineImpl";
    private static final int WAKEUP_BUFFER_SIZE = 640;
    private static MultiWakeupEngineImpl mInstance;
    private boolean init = false;
    private boolean isPrintLog = false;
    private boolean isWakeup = false;
    private boolean isWriteBackward;
    private CircleBuffer mBackwardData;
    private byte[] mCacheData;
    private CircleBuffer mForwardData;
    private WVPListener mOutListener;
    private VoiceRecord mVoiceRecord;
    private MultiWakeupEngineInterface mWakeupEngineInterface;
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
            if (MultiWakeupEngineImpl.this.isPrintLog) {
                Log.d(MultiWakeupEngineImpl.TAG, "start msg.what:" + message.what);
            }
            int i = message.what;
            if (i == 1) {
                if (MultiWakeupEngineImpl.this.mWakeupEngineInterface.wakeupInit("") == 0) {
                    MultiWakeupEngineImpl.this.init = true;
                }
                if (!MultiWakeupEngineImpl.this.init) {
                    Log.e(MultiWakeupEngineImpl.TAG, "init failed");
                }
                if (MultiWakeupEngineImpl.this.mOutListener != null) {
                    MultiWakeupEngineImpl.this.mOutListener.onInit(MultiWakeupEngineImpl.this.init);
                }
            } else if (i != 2) {
                if (i == 3) {
                    try {
                        if (MultiWakeupEngineImpl.this.init) {
                            byte[] byteArray = message.getData().getByteArray(PhotosOemApi.PATH_SPECIAL_TYPE_DATA);
                            int i2 = message.arg1;
                            byte[] bArr = new byte[(i2 / 2)];
                            int i3 = 0;
                            int i4 = 0;
                            while (i3 < i2) {
                                bArr[i4] = byteArray[i3];
                                bArr[i4 + 1] = byteArray[i3 + 1];
                                i3 += 4;
                                i4 += 2;
                            }
                            MultiWakeupEngineImpl.this.mForwardData.writeBuffer(bArr);
                            if (MultiWakeupEngineImpl.this.saveRecord) {
                                MultiWakeupEngineImpl.this.saveVoiceUtil.writeFile(bArr);
                            }
                            WakeupInfo wakeupInfo = new WakeupInfo();
                            MultiWakeupEngineImpl.this.mWakeupEngineInterface.wakeupFeedData(bArr, i2 / 2, 0, wakeupInfo);
                            int i5 = wakeupInfo.wakeup;
                            if (MultiWakeupEngineImpl.this.isPrintLog) {
                                Log.d(MultiWakeupEngineImpl.TAG, "wakeup feed return:" + i5);
                            }
                            if (i5 == 1) {
                                Log.d(MultiWakeupEngineImpl.TAG, "Voice wakeup: r == FEED_RESULT_DETECTED_LEVEL_ONE");
                                MultiWakeupEngineImpl.this.isWakeup = false;
                                MultiWakeupEngineImpl.this.mCacheData = MultiWakeupEngineImpl.this.mForwardData.readBuffer();
                                MultiWakeupEngineImpl.this.mForwardData.reset();
                                MultiWakeupEngineImpl.this.isWriteBackward = true;
                            }
                            if (i5 == 2) {
                                Log.d(MultiWakeupEngineImpl.TAG, "Voice wakeup: r == FEED_RESULT_DETECTED_END");
                                MultiWakeupEngineImpl.this.isWakeup = true;
                                if (MultiWakeupEngineImpl.this.mOutListener != null) {
                                    PhraseWakeupResult phraseWakeupResult = new PhraseWakeupResult();
                                    phraseWakeupResult.setVoconWakeupPassed(true);
                                    phraseWakeupResult.setVoconPhrase(wakeupInfo.text);
                                    phraseWakeupResult.setScore(0.0f);
                                    phraseWakeupResult.setAec(false);
                                    Log.d(MultiWakeupEngineImpl.TAG, "Voice wakeup has been successful");
                                    MultiWakeupEngineImpl.this.mOutListener.onPhraseSpotted(phraseWakeupResult);
                                }
                            }
                            if (MultiWakeupEngineImpl.this.isWriteBackward) {
                                MultiWakeupEngineImpl.this.mBackwardData.writeBuffer(bArr);
                            }
                            if (MultiWakeupEngineImpl.this.mBackwardData.isFull()) {
                                if (!MultiWakeupEngineImpl.this.isWakeup) {
                                    if (MultiWakeupEngineImpl.this.mOutListener != null) {
                                        PhraseWakeupResult phraseWakeupResult2 = new PhraseWakeupResult();
                                        phraseWakeupResult2.setVoconWakeupPassed(false);
                                        phraseWakeupResult2.setVoconPhrase("");
                                        MultiWakeupEngineImpl.this.mOutListener.onPhraseSpotted(phraseWakeupResult2);
                                    }
                                    MultiWakeupEngineImpl.this.mWakeupEngineInterface.wakeupReset();
                                }
                                byte[] readBuffer = MultiWakeupEngineImpl.this.mBackwardData.readBuffer();
                                if (MultiWakeupEngineImpl.this.mOutListener != null) {
                                    byte[] bArr2 = new byte[(MultiWakeupEngineImpl.this.mCacheData.length + readBuffer.length)];
                                    System.arraycopy(MultiWakeupEngineImpl.this.mCacheData, 0, bArr2, 0, MultiWakeupEngineImpl.this.mCacheData.length);
                                    System.arraycopy(readBuffer, 0, bArr2, MultiWakeupEngineImpl.this.mCacheData.length, readBuffer.length);
                                    MultiWakeupEngineImpl.this.mOutListener.onAudioData(bArr2);
                                }
                                MultiWakeupEngineImpl.this.mBackwardData.reset();
                                MultiWakeupEngineImpl.this.mCacheData = null;
                                MultiWakeupEngineImpl.this.isWriteBackward = false;
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        Log.e(MultiWakeupEngineImpl.TAG, "case 0x03 process failed !!!");
                    }
                } else if (i != 5) {
                    if (i == 6 && MultiWakeupEngineImpl.this.init && MultiWakeupEngineImpl.this.running) {
                        MultiWakeupEngineImpl.this.mVoiceRecord.stopRecord();
                        MultiWakeupEngineImpl.this.running = false;
                    }
                }
                if (MultiWakeupEngineImpl.this.init) {
                    if (MultiWakeupEngineImpl.this.running) {
                        MultiWakeupEngineImpl.this.mVoiceRecord.stopRecord();
                        MultiWakeupEngineImpl.this.running = false;
                    }
                    MultiWakeupEngineImpl.this.mWakeupEngineInterface.wakeupDestroy();
                    MultiWakeupEngineImpl.this.init = false;
                    if (MultiWakeupEngineImpl.this.mOutListener != null) {
                        MultiWakeupEngineImpl.this.mOutListener.onRelease();
                    }
                }
            } else if (MultiWakeupEngineImpl.this.init && !MultiWakeupEngineImpl.this.running) {
                MultiWakeupEngineImpl.this.mVoiceRecord.startRecord();
                MultiWakeupEngineImpl.this.running = true;
                MultiWakeupEngineImpl.this.mWakeupEngineInterface.wakeupReset();
            }
            if (MultiWakeupEngineImpl.this.isPrintLog) {
                Log.d(MultiWakeupEngineImpl.TAG, "end msg.what:" + message.what);
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
            MultiWakeupEngineImpl.this.running = false;
            if (MultiWakeupEngineImpl.this.mOutListener != null) {
                MultiWakeupEngineImpl.this.mOutListener.onConflictAudio();
            }
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecordRelease() {
            if (MultiWakeupEngineImpl.this.mOutListener != null) {
                MultiWakeupEngineImpl.this.mOutListener.onStopAudio();
            }
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecording(byte[] bArr, int i) {
            if (!MultiWakeupEngineImpl.this.pause) {
                byte[] bArr2 = new byte[i];
                System.arraycopy(bArr, 0, bArr2, 0, i);
                Message obtainMessage = MultiWakeupEngineImpl.this.mWorkHandler.obtainMessage(3);
                Bundle bundle = new Bundle();
                bundle.putByteArray(PhotosOemApi.PATH_SPECIAL_TYPE_DATA, bArr2);
                obtainMessage.setData(bundle);
                obtainMessage.arg1 = i;
                MultiWakeupEngineImpl.this.mWorkHandler.sendMessage(obtainMessage);
            }
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecordingEnd() {
            if (MultiWakeupEngineImpl.this.saveRecord) {
                MultiWakeupEngineImpl.this.saveVoiceUtil.closeFile();
            }
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecordingFailed() {
            MultiWakeupEngineImpl.this.running = false;
            if (MultiWakeupEngineImpl.this.mOutListener != null) {
                MultiWakeupEngineImpl.this.mOutListener.onConflictAudio();
            }
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecordingStart() {
            if (MultiWakeupEngineImpl.this.mOutListener != null) {
                MultiWakeupEngineImpl.this.mOutListener.onStartAudio();
            }
            if (MultiWakeupEngineImpl.this.saveRecord) {
                MultiWakeupEngineImpl.this.saveVoiceUtil.createFile("record");
            }
        }
    }

    private MultiWakeupEngineImpl() {
        MultiWakeupEngineInterface.loadLibrary("multi-wakeup-engine");
        this.mWakeupEngineInterface = new MultiWakeupEngineInterface();
        this.mVoiceRecord = new VoiceRecord(new VoiceRecordListener());
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

    public static MultiWakeupEngineImpl getInstance() {
        if (mInstance == null) {
            synchronized (MultiWakeupEngineImpl.class) {
                if (mInstance == null) {
                    mInstance = new MultiWakeupEngineImpl();
                }
            }
        }
        return mInstance;
    }

    public void init() {
        this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(1));
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
