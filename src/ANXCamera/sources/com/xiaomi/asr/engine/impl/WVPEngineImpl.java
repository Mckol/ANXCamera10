package com.xiaomi.asr.engine.impl;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.photos.api.PhotosOemApi;
import com.ss.android.ugc.effectmanager.effect.model.ComposerHelper;
import com.xiaomi.asr.engine.PhraseWakeupResult;
import com.xiaomi.asr.engine.WVPListener;
import com.xiaomi.asr.engine.jni.WakeupEngineInterface;
import com.xiaomi.asr.engine.record.VoiceRecord;
import com.xiaomi.asr.engine.utils.CircleBuffer;
import com.xiaomi.asr.engine.utils.SaveVoiceUtil;

public class WVPEngineImpl {
    private static final int FEED_RESULT_DETECTED_END = 2;
    private static final int FEED_RESULT_DETECTED_LEVEL_ONE = 1;
    private static final String SDK_VERSION = "wvp_sdk_202006011636";
    private static final String TAG = "WVPEngineImpl";
    private static final int WAKEUP_BUFFER_SIZE = 640;
    private static WVPEngineImpl mInstance;
    private boolean init = false;
    private boolean isPrintLog = false;
    private boolean isWakeup = false;
    private boolean isWriteBackward;
    private CircleBuffer mBackwardData;
    private byte[] mCacheData;
    private String mDebugPath = "";
    private CircleBuffer mForwardData;
    private WVPListener mOutListener;
    private int mReturnIndex = 0;
    private String mSoundVerifyStr = "";
    private long mSplitDataHandleTime = 0;
    private long mVoiceDataSize = 0;
    private VoicePrintManager mVoicePrintManager;
    private VoiceRecord mVoiceRecord;
    private WakeupEngineInterface mWakeupEngineInterface;
    private long mWakeupLevelOneEndTime = 0;
    private long mWakeupLevelOneHandleTime = 0;
    private Handler mWorkHandler;
    private HandlerThread mWorkHandlerThread;
    private boolean openVoicePrint = false;
    private boolean pause = false;
    private boolean running = false;
    private boolean saveRecord = false;
    private SaveVoiceUtil saveVoiceUtil;

    class HandlerCallback implements Handler.Callback {
        HandlerCallback() {
        }

        public boolean handleMessage(Message message) {
            int i;
            if (WVPEngineImpl.this.isPrintLog) {
                Log.d(WVPEngineImpl.TAG, "start msg.what:" + message.what);
            }
            int i2 = message.what;
            if (i2 != 16) {
                switch (i2) {
                    case 1:
                        Bundle data = message.getData();
                        String string = data.getString(ComposerHelper.CONFIG_PATH);
                        String string2 = data.getString("name");
                        if (WVPEngineImpl.this.isPrintLog) {
                            Log.d(WVPEngineImpl.TAG, "vp_model_path:" + string + ", vp_speaker:" + string2);
                        }
                        if (WVPEngineImpl.this.mWakeupEngineInterface.wakeupInit("") + WVPEngineImpl.this.mVoicePrintManager.init(string, string2) == 0) {
                            WVPEngineImpl.this.init = true;
                        }
                        if (!WVPEngineImpl.this.init) {
                            Log.e(WVPEngineImpl.TAG, "init failed");
                        }
                        if (WVPEngineImpl.this.mOutListener != null) {
                            WVPEngineImpl.this.mOutListener.onInit(WVPEngineImpl.this.init);
                            break;
                        }
                        break;
                    case 2:
                        if (WVPEngineImpl.this.init) {
                            WVPEngineImpl.this.mWorkHandler.removeMessages(6);
                            if (!WVPEngineImpl.this.running) {
                                WVPEngineImpl.this.mVoiceRecord.startRecord();
                                WVPEngineImpl.this.running = true;
                            }
                            WVPEngineImpl.this.pause = false;
                            break;
                        }
                        break;
                    case 3:
                        if (WVPEngineImpl.this.init && !WVPEngineImpl.this.pause) {
                            byte[] byteArray = message.getData().getByteArray(PhotosOemApi.PATH_SPECIAL_TYPE_DATA);
                            int i3 = message.arg1;
                            long currentTimeMillis = System.currentTimeMillis();
                            int i4 = i3 / 2;
                            byte[] bArr = new byte[i4];
                            int i5 = 0;
                            int i6 = 0;
                            while (i5 < i3) {
                                bArr[i6] = byteArray[i5];
                                bArr[i6 + 1] = byteArray[i5 + 1];
                                i5 += 4;
                                i6 += 2;
                            }
                            if (WVPEngineImpl.this.isPrintLog) {
                                WVPEngineImpl.this.mSplitDataHandleTime += System.currentTimeMillis() - currentTimeMillis;
                            }
                            WVPEngineImpl.this.mForwardData.writeBuffer(bArr);
                            long currentTimeMillis2 = System.currentTimeMillis();
                            int wakeupFeedData = WVPEngineImpl.this.mWakeupEngineInterface.wakeupFeedData(bArr, i4, 0);
                            if (WVPEngineImpl.this.isPrintLog) {
                                Log.d(WVPEngineImpl.TAG, "wakeup feed return:" + wakeupFeedData);
                                WVPEngineImpl wVPEngineImpl = WVPEngineImpl.this;
                                wVPEngineImpl.mWakeupLevelOneHandleTime = wVPEngineImpl.mWakeupLevelOneHandleTime + (System.currentTimeMillis() - currentTimeMillis2);
                                WVPEngineImpl.this.mVoiceDataSize += (long) i4;
                            }
                            if (wakeupFeedData == 1) {
                                if (WVPEngineImpl.this.isPrintLog) {
                                    String str = "wakeup-voice-level-one, voice data size:" + WVPEngineImpl.this.mVoiceDataSize + ", voice handle time:" + WVPEngineImpl.this.mWakeupLevelOneHandleTime + ", rtf:" + String.format("%.3f", Double.valueOf(((double) WVPEngineImpl.this.mWakeupLevelOneHandleTime) / (((double) WVPEngineImpl.this.mVoiceDataSize) / 32.0d))) + ", split data handle time:" + WVPEngineImpl.this.mSplitDataHandleTime + ", split rtf:" + String.format("%.3f", Double.valueOf(((double) WVPEngineImpl.this.mSplitDataHandleTime) / (((double) WVPEngineImpl.this.mVoiceDataSize) / 32.0d)));
                                    if (WVPEngineImpl.this.mOutListener != null) {
                                        WVPEngineImpl.this.mOutListener.onDebug(str + "\n");
                                    }
                                    WVPEngineImpl.this.mWakeupLevelOneEndTime = System.currentTimeMillis();
                                    WVPEngineImpl.this.mWakeupLevelOneHandleTime = 0;
                                    WVPEngineImpl.this.mSplitDataHandleTime = 0;
                                    WVPEngineImpl.this.mVoiceDataSize = 0;
                                }
                                WVPEngineImpl.this.mReturnIndex = 0;
                                WVPEngineImpl.this.isWakeup = false;
                                WVPEngineImpl.this.mSoundVerifyStr = "";
                                WVPEngineImpl.this.voicePrintReco();
                                WVPEngineImpl wVPEngineImpl2 = WVPEngineImpl.this;
                                wVPEngineImpl2.mCacheData = wVPEngineImpl2.mForwardData.readBuffer();
                                WVPEngineImpl.this.mForwardData.reset();
                                WVPEngineImpl.this.isWriteBackward = true;
                                i = 2;
                            } else {
                                i = 2;
                            }
                            if (wakeupFeedData == i) {
                                if (WVPEngineImpl.this.isPrintLog) {
                                    String str2 = "wakeup-voice-level-two, handle time:" + (System.currentTimeMillis() - WVPEngineImpl.this.mWakeupLevelOneEndTime);
                                    if (WVPEngineImpl.this.mOutListener != null) {
                                        WVPEngineImpl.this.mOutListener.onDebug(str2 + "\n");
                                    }
                                }
                                WVPEngineImpl.this.isWakeup = true;
                                Message obtainMessage = WVPEngineImpl.this.mWorkHandler.obtainMessage(7);
                                obtainMessage.arg1 = 1;
                                obtainMessage.obj = "keyword detected";
                                WVPEngineImpl.this.mWorkHandler.sendMessage(obtainMessage);
                            }
                            if (WVPEngineImpl.this.isWriteBackward) {
                                WVPEngineImpl.this.mBackwardData.writeBuffer(bArr);
                            }
                            if (WVPEngineImpl.this.mBackwardData.isFull()) {
                                if (!WVPEngineImpl.this.isWakeup) {
                                    if (WVPEngineImpl.this.mOutListener != null) {
                                        PhraseWakeupResult phraseWakeupResult = new PhraseWakeupResult();
                                        phraseWakeupResult.setVoconWakeupPassed(false);
                                        phraseWakeupResult.setVoconPhrase("");
                                        WVPEngineImpl.this.mOutListener.onPhraseSpotted(phraseWakeupResult);
                                    }
                                    WVPEngineImpl.this.mWakeupEngineInterface.wakeupReset();
                                }
                                byte[] readBuffer = WVPEngineImpl.this.mBackwardData.readBuffer();
                                if (WVPEngineImpl.this.mOutListener != null) {
                                    byte[] bArr2 = new byte[(WVPEngineImpl.this.mCacheData.length + readBuffer.length)];
                                    System.arraycopy(WVPEngineImpl.this.mCacheData, 0, bArr2, 0, WVPEngineImpl.this.mCacheData.length);
                                    System.arraycopy(readBuffer, 0, bArr2, WVPEngineImpl.this.mCacheData.length, readBuffer.length);
                                    WVPEngineImpl.this.mOutListener.onAudioData(bArr2);
                                }
                                WVPEngineImpl.this.mBackwardData.reset();
                                WVPEngineImpl.this.mCacheData = null;
                                WVPEngineImpl.this.isWriteBackward = false;
                                break;
                            }
                        }
                        break;
                    case 4:
                        if (WVPEngineImpl.this.init) {
                            WVPEngineImpl.this.pause = true;
                            break;
                        }
                        break;
                    case 5:
                        if (WVPEngineImpl.this.init) {
                            if (WVPEngineImpl.this.running) {
                                WVPEngineImpl.this.mVoiceRecord.stopRecord();
                                WVPEngineImpl.this.running = false;
                            }
                            WVPEngineImpl.this.mWakeupEngineInterface.wakeupDestroy();
                            WVPEngineImpl.this.mVoicePrintManager.release();
                            WVPEngineImpl.this.init = false;
                            WVPEngineImpl.this.pause = false;
                            if (WVPEngineImpl.this.mOutListener != null) {
                                WVPEngineImpl.this.mOutListener.onRelease();
                                break;
                            }
                        }
                        break;
                    case 6:
                        if (WVPEngineImpl.this.running) {
                            WVPEngineImpl.this.mVoiceRecord.stopRecord();
                            WVPEngineImpl.this.running = false;
                            break;
                        }
                        break;
                    case 7:
                        if (message.arg1 != 1) {
                            Log.d(WVPEngineImpl.TAG, "end cal sv");
                            WVPEngineImpl.this.mSoundVerifyStr = (String) message.obj;
                        } else if (!TextUtils.isEmpty((String) message.obj)) {
                            Log.d(WVPEngineImpl.TAG, "wakeup level two");
                            WVPEngineImpl.this.isWakeup = true;
                        }
                        WVPEngineImpl.this.mReturnIndex++;
                        if (WVPEngineImpl.this.mReturnIndex >= 2 && WVPEngineImpl.this.isWakeup) {
                            if (WVPEngineImpl.this.mSoundVerifyStr.contains("stranger") || WVPEngineImpl.this.mSoundVerifyStr.contains("error:empty") || WVPEngineImpl.this.mSoundVerifyStr.contains("recognition failed")) {
                                if (WVPEngineImpl.this.isPrintLog) {
                                    Log.d(WVPEngineImpl.TAG, "sv wakeup fail, sv res:" + WVPEngineImpl.this.mSoundVerifyStr);
                                    break;
                                }
                            } else {
                                if (WVPEngineImpl.this.isPrintLog) {
                                    Log.d(WVPEngineImpl.TAG, "sv-wakeup, res:" + WVPEngineImpl.this.mSoundVerifyStr);
                                }
                                if (WVPEngineImpl.this.mOutListener != null) {
                                    PhraseWakeupResult phraseWakeupResult2 = new PhraseWakeupResult();
                                    phraseWakeupResult2.setVoconWakeupPassed(true);
                                    phraseWakeupResult2.setVoconPhrase("小爱同学");
                                    phraseWakeupResult2.setScore(0.0f);
                                    phraseWakeupResult2.setAec(false);
                                    WVPEngineImpl.this.mOutListener.onPhraseSpotted(phraseWakeupResult2);
                                    break;
                                }
                            }
                        }
                        break;
                    case 8:
                        if (WVPEngineImpl.this.init) {
                            WVPEngineImpl.this.mVoicePrintManager.startEnrollment(message.arg1);
                            break;
                        }
                        break;
                    case 9:
                        if (WVPEngineImpl.this.init) {
                            WVPEngineImpl.this.mVoicePrintManager.removeAllRegister();
                            break;
                        }
                        break;
                }
            } else if (WVPEngineImpl.this.init) {
                WVPEngineImpl.this.mVoicePrintManager.generateModel();
            }
            if (!WVPEngineImpl.this.isPrintLog) {
                return false;
            }
            Log.d(WVPEngineImpl.TAG, "end msg.what:" + message.what);
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
            if (WVPEngineImpl.this.mOutListener != null) {
                WVPEngineImpl.this.mOutListener.onConflictAudio();
            }
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecordRelease() {
            if (WVPEngineImpl.this.mOutListener != null) {
                WVPEngineImpl.this.mOutListener.onStopAudio();
            }
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecording(byte[] bArr, int i) {
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArr, 0, bArr2, 0, i);
            Message obtainMessage = WVPEngineImpl.this.mWorkHandler.obtainMessage(3);
            Bundle bundle = new Bundle();
            bundle.putByteArray(PhotosOemApi.PATH_SPECIAL_TYPE_DATA, bArr2);
            obtainMessage.setData(bundle);
            obtainMessage.arg1 = i;
            WVPEngineImpl.this.mWorkHandler.sendMessage(obtainMessage);
            if (WVPEngineImpl.this.saveRecord) {
                WVPEngineImpl.this.saveVoiceUtil.writeFile(bArr2);
            }
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecordingEnd() {
            if (WVPEngineImpl.this.saveRecord) {
                WVPEngineImpl.this.saveVoiceUtil.closeFile();
            }
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecordingFailed() {
            if (WVPEngineImpl.this.mOutListener != null) {
                WVPEngineImpl.this.mOutListener.onConflictAudio();
            }
        }

        @Override // com.xiaomi.asr.engine.record.VoiceRecord.RecordListener
        public void onRecordingStart() {
            if (WVPEngineImpl.this.mOutListener != null) {
                WVPEngineImpl.this.mOutListener.onStartAudio();
            }
            if (WVPEngineImpl.this.saveRecord) {
                WVPEngineImpl.this.saveVoiceUtil.createFile("record");
            }
        }
    }

    private WVPEngineImpl() {
        WakeupEngineInterface.loadLibrary("wakeup-engine");
        this.mWakeupEngineInterface = new WakeupEngineInterface();
        this.mVoiceRecord = new VoiceRecord(new VoiceRecordListener());
        this.mVoiceRecord.setParam(1, 1);
        this.mVoiceRecord.setParam(2, WAKEUP_BUFFER_SIZE);
        this.mVoiceRecord.setParam(0, 12);
        this.mWorkHandlerThread = new HandlerThread("wvp-engine-impl-thread");
        this.mWorkHandlerThread.start();
        this.mWorkHandler = new Handler(this.mWorkHandlerThread.getLooper(), new HandlerCallback());
        this.mForwardData = new CircleBuffer(64000);
        this.mBackwardData = new CircleBuffer(19200);
        this.mVoicePrintManager = new VoicePrintManager("voiceprint-engine-lab");
        this.saveVoiceUtil = new SaveVoiceUtil();
    }

    public static WVPEngineImpl getInstance() {
        if (mInstance == null) {
            synchronized (WVPEngineImpl.class) {
                if (mInstance == null) {
                    mInstance = new WVPEngineImpl();
                }
            }
        }
        return mInstance;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void voicePrintReco() {
        int wakeupGetFrameSize = this.mWakeupEngineInterface.wakeupGetFrameSize() * 320;
        byte[] readBuffer = this.mForwardData.readBuffer();
        final byte[] bArr = new byte[wakeupGetFrameSize];
        System.arraycopy(readBuffer, readBuffer.length - wakeupGetFrameSize, bArr, 0, wakeupGetFrameSize);
        new Thread(new Runnable() {
            /* class com.xiaomi.asr.engine.impl.WVPEngineImpl.AnonymousClass1 */

            public void run() {
                String str;
                if (WVPEngineImpl.this.openVoicePrint) {
                    long currentTimeMillis = System.currentTimeMillis();
                    str = WVPEngineImpl.this.mVoicePrintManager.recognizeVoice(bArr, -1, 0);
                    if (WVPEngineImpl.this.isPrintLog) {
                        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                        if (WVPEngineImpl.this.mOutListener != null) {
                            WVPListener wVPListener = WVPEngineImpl.this.mOutListener;
                            wVPListener.onDebug(str + ", data len:" + bArr.length + ", handle time:" + currentTimeMillis2 + ", rtf:" + String.format("%.3f", Double.valueOf(((double) currentTimeMillis2) / (((double) bArr.length) / 32.0d))) + "\n");
                        }
                    }
                } else {
                    str = "zhang_san:0.7813";
                }
                Message obtainMessage = WVPEngineImpl.this.mWorkHandler.obtainMessage(7);
                obtainMessage.arg1 = 2;
                obtainMessage.obj = str;
                WVPEngineImpl.this.mWorkHandler.sendMessageAtFrontOfQueue(obtainMessage);
            }
        }).start();
    }

    public void abortEnrollment() {
        this.mVoicePrintManager.abortEnrollment();
    }

    public void cancelEnrollment() {
        this.mVoicePrintManager.cancelEnrollment();
    }

    public void commitEnrollment() {
        this.mVoicePrintManager.commitEnrollment();
    }

    public void generateModel() {
        this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(16));
    }

    public String getAllRegister() {
        return this.mVoicePrintManager.getAllRegister();
    }

    public void init(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "";
        }
        Message obtainMessage = this.mWorkHandler.obtainMessage(1);
        Bundle bundle = new Bundle();
        bundle.putString(ComposerHelper.CONFIG_PATH, str);
        bundle.putString("name", str2);
        obtainMessage.setData(bundle);
        this.mWorkHandler.sendMessage(obtainMessage);
    }

    public void openLog(boolean z) {
        this.isPrintLog = z;
    }

    public void openVoicePrint(boolean z) {
        this.openVoicePrint = z;
    }

    public void release() {
        this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(5));
        this.mWorkHandler.removeMessages(6);
    }

    public void removeAllRegister() {
        this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(9));
    }

    public void restart() {
        stop(0);
        start(100);
    }

    public void saveRecord(boolean z) {
        this.saveRecord = z;
    }

    public void setDebugPath(String str) {
        this.mDebugPath = str;
        this.saveVoiceUtil.setSaveDir(this.mDebugPath);
    }

    public void setListener(WVPListener wVPListener) {
        this.mOutListener = wVPListener;
        this.mVoicePrintManager.setListener(wVPListener);
    }

    public void start(int i) {
        this.mWorkHandler.sendMessageDelayed(this.mWorkHandler.obtainMessage(2), (long) i);
    }

    public void startEnrollment(int i) {
        Message obtainMessage = this.mWorkHandler.obtainMessage(8);
        obtainMessage.arg1 = i;
        this.mWorkHandler.sendMessage(obtainMessage);
    }

    public void stop(long j) {
        if (this.isPrintLog) {
            Log.d(TAG, "call stop, stopRecordMs:" + j);
        }
        this.mWorkHandler.sendMessageDelayed(this.mWorkHandler.obtainMessage(6), j);
        this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(4));
    }

    public String version() {
        if (!this.init) {
            return "";
        }
        return "wvp_sdk_202006011636 " + this.mWakeupEngineInterface.wakeupVersion() + " " + this.mVoicePrintManager.version();
    }
}
