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
import com.xiaomi.asr.engine.record.AudioSource;
import com.xiaomi.asr.engine.record.DSPVoiceRecord;
import com.xiaomi.asr.engine.utils.DSPWakeupInfoParser;
import com.xiaomi.asr.engine.utils.SaveVoiceUtil;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Calendar;

public class W2VPEngineImpl {
    private static final int FEED_RESULT_DETECTED_END = 2;
    private static final int RECORD_BUFFER_SIZE = 320;
    private static final String SDK_VERSION = "w2vp_sdk_202006011636";
    private static final String TAG = "W2VPEngineImpl";
    private static W2VPEngineImpl mInstance;
    private boolean init = false;
    private boolean isPrintLog = false;
    private boolean isWakeup = false;
    private boolean isWakeupInfoParser = false;
    private AudioSource mAudioSource = null;
    private int mBspDataHandleTime = 0;
    private ByteArrayOutputStream mCacheCMVNData;
    private ByteArrayOutputStream mCacheSVData;
    private int mCaptureSession;
    private DSPWakeupInfoParser mDSPWakeupInfoParser;
    private String mDebugPath = "";
    private WVPListener mOutListener;
    private ByteArrayOutputStream mOutputStream;
    private int mReturnIndex = 0;
    private String mSoundVerifyStr = "";
    private VoicePrintManager mVoicePrintManager;
    private DSPVoiceRecord mVoiceRecord;
    private WakeupEngineInterface mWakeupEngineInterface;
    private long mWakeupHandleTime = 0;
    private Handler mWorkHandler;
    private HandlerThread mWorkHandlerThread;
    private boolean openVoicePrint = false;
    private boolean running = false;
    private boolean saveRecord = false;
    private SaveVoiceUtil saveVoiceUtil;

    class HandlerCallback implements Handler.Callback {
        HandlerCallback() {
        }

        public boolean handleMessage(Message message) {
            int i = message.what;
            boolean z = true;
            if (i == 9) {
                if (message.arg1 != 1) {
                    W2VPEngineImpl.this.mSoundVerifyStr = (String) message.obj;
                    Log.d(W2VPEngineImpl.TAG, "声纹结果返回 sv-wakeup, res:" + W2VPEngineImpl.this.mSoundVerifyStr);
                    if (W2VPEngineImpl.this.openVoicePrint && W2VPEngineImpl.this.mOutListener != null) {
                        W2VPEngineImpl.this.mOutListener.onDebug("sv-wakeup, res:" + W2VPEngineImpl.this.mSoundVerifyStr + "\n");
                    }
                } else if (!TextUtils.isEmpty((String) message.obj)) {
                    W2VPEngineImpl.this.isWakeup = true;
                }
                W2VPEngineImpl.this.mReturnIndex++;
                if (W2VPEngineImpl.this.mReturnIndex >= 2) {
                    if (W2VPEngineImpl.this.isPrintLog) {
                        Log.d(W2VPEngineImpl.TAG, "sv-wakeup, res:" + W2VPEngineImpl.this.mSoundVerifyStr);
                    }
                    PhraseWakeupResult phraseWakeupResult = new PhraseWakeupResult();
                    phraseWakeupResult.setVoconWakeupPassed(W2VPEngineImpl.this.isWakeup);
                    phraseWakeupResult.setAec(W2VPEngineImpl.this.mDSPWakeupInfoParser.isWakeupAec());
                    phraseWakeupResult.setWakeupStartTime(W2VPEngineImpl.this.mDSPWakeupInfoParser.getWakeupStartTime());
                    phraseWakeupResult.setWakeupEndTime(W2VPEngineImpl.this.mDSPWakeupInfoParser.getWakeupEndTime());
                    phraseWakeupResult.setScore((float) W2VPEngineImpl.this.mDSPWakeupInfoParser.getWakeupScore());
                    phraseWakeupResult.setVoconPhrase("小爱同学");
                    phraseWakeupResult.setVBPassed(!W2VPEngineImpl.this.mSoundVerifyStr.contains("error:"));
                    PhraseWakeupResult.PhraseWakeupResultDebugInfo phraseWakeupResultDebugInfo = new PhraseWakeupResult.PhraseWakeupResultDebugInfo();
                    phraseWakeupResultDebugInfo.setCacheCMVNData(W2VPEngineImpl.this.mCacheCMVNData.toByteArray());
                    phraseWakeupResultDebugInfo.setCacheSVData(W2VPEngineImpl.this.mCacheSVData.toByteArray());
                    phraseWakeupResult.setDebugInfo(phraseWakeupResultDebugInfo);
                    W2VPEngineImpl.this.mOutListener.onPhraseSpotted(phraseWakeupResult);
                    if (W2VPEngineImpl.this.mOutListener != null) {
                        W2VPEngineImpl.this.mOutListener.onPhraseSpotted(phraseWakeupResult);
                    }
                }
            } else if (i != 8192) {
                if (i != 16) {
                    if (i != 17) {
                        switch (i) {
                            case 1:
                                if (W2VPEngineImpl.this.isPrintLog) {
                                    Log.d(W2VPEngineImpl.TAG, "on init init:" + W2VPEngineImpl.this.init);
                                }
                                if (!W2VPEngineImpl.this.init) {
                                    Bundle data = message.getData();
                                    String string = data.getString(ComposerHelper.CONFIG_PATH);
                                    String string2 = data.getString("name");
                                    if (W2VPEngineImpl.this.isPrintLog) {
                                        Log.d(W2VPEngineImpl.TAG, "vp_model_path:" + string + ", vp_speaker:" + string2);
                                    }
                                    if (W2VPEngineImpl.this.mWakeupEngineInterface.wakeupInit("") + W2VPEngineImpl.this.mVoicePrintManager.init(string, string2) == 0) {
                                        W2VPEngineImpl.this.init = true;
                                    }
                                    if (!W2VPEngineImpl.this.init) {
                                        Log.e(W2VPEngineImpl.TAG, "init failed");
                                    }
                                }
                                if (W2VPEngineImpl.this.mOutListener != null) {
                                    W2VPEngineImpl.this.mOutListener.onInit(W2VPEngineImpl.this.init);
                                    break;
                                }
                                break;
                            case 2:
                                if (W2VPEngineImpl.this.init) {
                                    W2VPEngineImpl.this.mVoicePrintManager.startEnrollment(message.arg1);
                                    break;
                                }
                                break;
                            case 3:
                                if (W2VPEngineImpl.this.init) {
                                    if (!W2VPEngineImpl.this.running) {
                                        W2VPEngineImpl.this.mVoiceRecord.startRecord(W2VPEngineImpl.this.mCaptureSession);
                                        W2VPEngineImpl.this.running = true;
                                    }
                                    W2VPEngineImpl.this.isWakeupInfoParser = false;
                                    break;
                                }
                                break;
                            case 4:
                                if (W2VPEngineImpl.this.init) {
                                    if (W2VPEngineImpl.this.isPrintLog) {
                                        Log.d(W2VPEngineImpl.TAG, "wakeup feed handle");
                                    }
                                    byte[] byteArray = message.getData().getByteArray(PhotosOemApi.PATH_SPECIAL_TYPE_DATA);
                                    W2VPEngineImpl w2VPEngineImpl = W2VPEngineImpl.this;
                                    int i2 = message.arg1;
                                    if (message.arg2 != 1) {
                                        z = false;
                                    }
                                    w2VPEngineImpl.checkWakeup(byteArray, i2, z);
                                    break;
                                }
                                break;
                            case 5:
                                if (W2VPEngineImpl.this.init && W2VPEngineImpl.this.running) {
                                    W2VPEngineImpl.this.mVoiceRecord.stopRecord();
                                    W2VPEngineImpl.this.running = false;
                                    break;
                                }
                            case 6:
                                if (W2VPEngineImpl.this.init) {
                                    W2VPEngineImpl.this.mVoicePrintManager.removeAllRegister();
                                    break;
                                }
                                break;
                            case 7:
                                if (W2VPEngineImpl.this.isPrintLog) {
                                    Log.d(W2VPEngineImpl.TAG, "on release init:" + W2VPEngineImpl.this.init);
                                }
                                if (W2VPEngineImpl.this.init) {
                                    if (W2VPEngineImpl.this.running) {
                                        W2VPEngineImpl.this.mVoiceRecord.stopRecord();
                                        W2VPEngineImpl.this.running = false;
                                    }
                                    W2VPEngineImpl.this.mWakeupEngineInterface.wakeupDestroy();
                                    W2VPEngineImpl.this.mVoicePrintManager.release();
                                    W2VPEngineImpl.this.init = false;
                                    if (W2VPEngineImpl.this.mOutListener != null) {
                                        W2VPEngineImpl.this.mOutListener.onRelease();
                                        break;
                                    }
                                }
                                break;
                        }
                    } else if (W2VPEngineImpl.this.init && W2VPEngineImpl.this.mOutListener != null) {
                        W2VPEngineImpl.this.mOutListener.onAudioData(W2VPEngineImpl.this.mOutputStream.toByteArray());
                    }
                } else if (W2VPEngineImpl.this.init) {
                    W2VPEngineImpl.this.mVoicePrintManager.generateModel();
                }
            } else if (W2VPEngineImpl.this.init) {
                W2VPEngineImpl.this.mVoicePrintManager.startEnrollmentFileRecorded(W2VPEngineImpl.this.mAudioSource, message.arg1);
            }
            return false;
        }
    }

    class VoiceRecordListener implements DSPVoiceRecord.RecordListener {
        private final long STATIC_WAKEUP_BUFFER = 300;

        VoiceRecordListener() {
        }

        @Override // com.xiaomi.asr.engine.record.DSPVoiceRecord.RecordListener
        public void onAudioSessionId(int i) {
        }

        @Override // com.xiaomi.asr.engine.record.DSPVoiceRecord.RecordListener
        public void onRecordCreateError() {
            if (W2VPEngineImpl.this.mOutListener != null) {
                W2VPEngineImpl.this.mOutListener.onConflictAudio();
            }
        }

        @Override // com.xiaomi.asr.engine.record.DSPVoiceRecord.RecordListener
        public void onRecordRelease() {
            if (W2VPEngineImpl.this.mOutListener != null) {
                W2VPEngineImpl.this.mOutListener.onStopAudio();
            }
        }

        @Override // com.xiaomi.asr.engine.record.DSPVoiceRecord.RecordListener
        public void onRecording(byte[] bArr, int i) {
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArr, 0, bArr2, 0, i);
            if (!W2VPEngineImpl.this.isWakeupInfoParser) {
                W2VPEngineImpl.this.parserWakeupInfo(bArr2, i);
                W2VPEngineImpl.this.mBspDataHandleTime = 0;
                W2VPEngineImpl.this.mReturnIndex = 0;
                W2VPEngineImpl.this.isWakeup = false;
                W2VPEngineImpl.this.mSoundVerifyStr = "";
                W2VPEngineImpl.this.mWakeupHandleTime = 0;
                W2VPEngineImpl.this.mCacheSVData.reset();
                W2VPEngineImpl.this.mCacheCMVNData.reset();
                W2VPEngineImpl.this.mOutputStream.reset();
                if (W2VPEngineImpl.this.isWakeupInfoParser && W2VPEngineImpl.this.isPrintLog) {
                    String str = "wakeup start time:" + W2VPEngineImpl.this.mDSPWakeupInfoParser.getWakeupStartTime() + ", wakeup end time:" + W2VPEngineImpl.this.mDSPWakeupInfoParser.getWakeupEndTime() + ", wakeup score:" + W2VPEngineImpl.this.mDSPWakeupInfoParser.getWakeupScore() + ", wakeup pcm length:" + W2VPEngineImpl.this.mDSPWakeupInfoParser.getPcmLength() + ", wakeup aec:" + W2VPEngineImpl.this.mDSPWakeupInfoParser.isWakeupAec() + "\n";
                    if (W2VPEngineImpl.this.isPrintLog && W2VPEngineImpl.this.mOutListener != null) {
                        W2VPEngineImpl.this.mOutListener.onDebug(str);
                        return;
                    }
                    return;
                }
                return;
            }
            W2VPEngineImpl.this.mBspDataHandleTime += 10;
            if (((long) W2VPEngineImpl.this.mBspDataHandleTime) > W2VPEngineImpl.this.mDSPWakeupInfoParser.getWakeupStartTime() && ((long) W2VPEngineImpl.this.mBspDataHandleTime) <= W2VPEngineImpl.this.mDSPWakeupInfoParser.getWakeupEndTime()) {
                try {
                    W2VPEngineImpl.this.mCacheSVData.write(Arrays.copyOfRange(bArr2, 160, 320));
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                if (((long) W2VPEngineImpl.this.mBspDataHandleTime) == W2VPEngineImpl.this.mDSPWakeupInfoParser.getWakeupEndTime()) {
                    if (W2VPEngineImpl.this.isPrintLog && W2VPEngineImpl.this.mOutListener != null) {
                        W2VPEngineImpl.this.mOutListener.onDebug("read wakeup level one end time.\n");
                    }
                    W2VPEngineImpl.this.voicePrintReco();
                }
            }
            if (((long) W2VPEngineImpl.this.mBspDataHandleTime) <= W2VPEngineImpl.this.mDSPWakeupInfoParser.getWakeupEndTime() + 300) {
                Message obtainMessage = W2VPEngineImpl.this.mWorkHandler.obtainMessage(4);
                Bundle bundle = new Bundle();
                bundle.putByteArray(PhotosOemApi.PATH_SPECIAL_TYPE_DATA, Arrays.copyOfRange(bArr2, 0, 160));
                obtainMessage.setData(bundle);
                obtainMessage.arg1 = 160;
                obtainMessage.arg2 = 0;
                if (((long) W2VPEngineImpl.this.mBspDataHandleTime) == W2VPEngineImpl.this.mDSPWakeupInfoParser.getWakeupEndTime() + 300) {
                    obtainMessage.arg2 = 1;
                    if (W2VPEngineImpl.this.isPrintLog && W2VPEngineImpl.this.mOutListener != null) {
                        W2VPEngineImpl.this.mOutListener.onDebug("read wakeup level one end time + 300ms.\n");
                    }
                }
                W2VPEngineImpl.this.mWorkHandler.sendMessage(obtainMessage);
            } else if (((long) W2VPEngineImpl.this.mBspDataHandleTime) <= W2VPEngineImpl.this.mDSPWakeupInfoParser.getWakeupEndTime() + 300 + ((long) W2VPEngineImpl.this.mDSPWakeupInfoParser.getPcmLength())) {
                try {
                    W2VPEngineImpl.this.mOutputStream.write(bArr);
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                if (((long) W2VPEngineImpl.this.mBspDataHandleTime) == W2VPEngineImpl.this.mDSPWakeupInfoParser.getWakeupEndTime() + 300 + ((long) W2VPEngineImpl.this.mDSPWakeupInfoParser.getPcmLength())) {
                    if (W2VPEngineImpl.this.isPrintLog && W2VPEngineImpl.this.mOutListener != null) {
                        W2VPEngineImpl.this.mOutListener.onDebug("read pcm data end.\n");
                    }
                    W2VPEngineImpl.this.mWorkHandler.sendMessage(W2VPEngineImpl.this.mWorkHandler.obtainMessage(17));
                    W2VPEngineImpl.this.mVoiceRecord.stopRecord();
                    W2VPEngineImpl.this.running = false;
                }
            }
        }

        @Override // com.xiaomi.asr.engine.record.DSPVoiceRecord.RecordListener
        public void onRecordingEnd() {
        }

        @Override // com.xiaomi.asr.engine.record.DSPVoiceRecord.RecordListener
        public void onRecordingFailed() {
            if (W2VPEngineImpl.this.mOutListener != null) {
                W2VPEngineImpl.this.mOutListener.onConflictAudio();
            }
        }

        @Override // com.xiaomi.asr.engine.record.DSPVoiceRecord.RecordListener
        public void onRecordingStart() {
            if (W2VPEngineImpl.this.mOutListener != null) {
                W2VPEngineImpl.this.mOutListener.onStartAudio();
            }
            if (W2VPEngineImpl.this.saveRecord) {
                W2VPEngineImpl.this.saveVoiceUtil.createFile("w2vp");
            }
        }
    }

    private W2VPEngineImpl() {
        WakeupEngineInterface.loadLibrary("wakeup-engine-level2");
        this.mWakeupEngineInterface = new WakeupEngineInterface();
        this.mDSPWakeupInfoParser = new DSPWakeupInfoParser();
        this.mCacheSVData = new ByteArrayOutputStream();
        this.mCacheCMVNData = new ByteArrayOutputStream();
        this.mVoiceRecord = new DSPVoiceRecord(new VoiceRecordListener());
        this.mVoiceRecord.setParam(2, 320);
        this.mVoiceRecord.setParam(1, 1999);
        this.mWorkHandlerThread = new HandlerThread("wakeup-engine-impl-thread");
        this.mWorkHandlerThread.start();
        this.mWorkHandler = new Handler(this.mWorkHandlerThread.getLooper(), new HandlerCallback());
        this.saveVoiceUtil = new SaveVoiceUtil();
        this.mVoicePrintManager = new VoicePrintManager("voiceprint-engine-bsp");
        this.mVoicePrintManager.setRecordSource(1999);
        this.mOutputStream = new ByteArrayOutputStream();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void checkWakeup(byte[] bArr, int i, boolean z) {
        if (!this.isWakeup) {
            if (this.saveRecord) {
                this.saveVoiceUtil.writeFile(bArr);
                try {
                    this.mCacheCMVNData.write(Arrays.copyOfRange(bArr, 0, i));
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (z && this.saveRecord) {
                this.saveVoiceUtil.closeFile();
            }
            long currentTimeMillis = System.currentTimeMillis();
            int wakeupFeedData = this.mWakeupEngineInterface.wakeupFeedData(bArr, i, 1);
            this.mWakeupHandleTime += System.currentTimeMillis() - currentTimeMillis;
            if (this.isPrintLog) {
                Log.d(TAG, "wakeup feed return:" + wakeupFeedData + ", data len:" + i + ", isLastFrame:" + z);
            }
            if (wakeupFeedData == 2) {
                this.isWakeup = true;
                WVPListener wVPListener = this.mOutListener;
                if (wVPListener != null) {
                    wVPListener.onDebug("wakeup two has wakeup. wakeup handle time:" + this.mWakeupHandleTime + "\n");
                }
                Message obtainMessage = this.mWorkHandler.obtainMessage(9);
                obtainMessage.arg1 = 1;
                obtainMessage.obj = "keyword detected";
                this.mWorkHandler.sendMessageAtFrontOfQueue(obtainMessage);
            }
            if (z && !this.isWakeup) {
                PhraseWakeupResult phraseWakeupResult = new PhraseWakeupResult();
                phraseWakeupResult.setVoconWakeupPassed(false);
                phraseWakeupResult.setAec(this.mDSPWakeupInfoParser.isWakeupAec());
                phraseWakeupResult.setWakeupStartTime(this.mDSPWakeupInfoParser.getWakeupStartTime());
                phraseWakeupResult.setWakeupEndTime(this.mDSPWakeupInfoParser.getWakeupEndTime());
                phraseWakeupResult.setScore((float) this.mDSPWakeupInfoParser.getWakeupScore());
                phraseWakeupResult.setVoconPhrase("小爱同学");
                phraseWakeupResult.setVBPassed(!this.mSoundVerifyStr.contains("error:"));
                PhraseWakeupResult.PhraseWakeupResultDebugInfo phraseWakeupResultDebugInfo = new PhraseWakeupResult.PhraseWakeupResultDebugInfo();
                phraseWakeupResultDebugInfo.setCacheCMVNData(this.mCacheCMVNData.toByteArray());
                phraseWakeupResultDebugInfo.setCacheSVData(this.mCacheSVData.toByteArray());
                phraseWakeupResult.setDebugInfo(phraseWakeupResultDebugInfo);
                WVPListener wVPListener2 = this.mOutListener;
                if (wVPListener2 != null) {
                    wVPListener2.onPhraseSpotted(phraseWakeupResult);
                }
                this.mWakeupEngineInterface.wakeupReset();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getCurrentTime() {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(1);
        int i2 = instance.get(2);
        int i3 = instance.get(5);
        int i4 = instance.get(11);
        int i5 = instance.get(12);
        int i6 = instance.get(13);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(i);
        stringBuffer.append("-");
        stringBuffer.append(i2 + 1);
        stringBuffer.append("-");
        stringBuffer.append(i3);
        stringBuffer.append("-");
        stringBuffer.append(i4);
        stringBuffer.append("-");
        stringBuffer.append(i5);
        stringBuffer.append("-");
        stringBuffer.append(i6);
        return stringBuffer.toString();
    }

    public static W2VPEngineImpl getInstance() {
        if (mInstance == null) {
            synchronized (W2VPEngineImpl.class) {
                if (mInstance == null) {
                    mInstance = new W2VPEngineImpl();
                }
            }
        }
        return mInstance;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void parserWakeupInfo(byte[] bArr, int i) {
        short[] sArr = new short[(i / 2)];
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(sArr);
        } else {
            ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).asShortBuffer().get(sArr);
        }
        this.mDSPWakeupInfoParser.parse(sArr);
        this.isWakeupInfoParser = this.mDSPWakeupInfoParser.isInfoValid();
        if (this.mDSPWakeupInfoParser.isInfoValid()) {
            Log.d(TAG, "wakeup start time:" + this.mDSPWakeupInfoParser.getWakeupStartTime() + ", wakeup end time:" + this.mDSPWakeupInfoParser.getWakeupEndTime() + ", wakeup score:" + this.mDSPWakeupInfoParser.getWakeupScore() + ", pcm length:" + this.mDSPWakeupInfoParser.getPcmLength() + ", wakeup aec:" + this.mDSPWakeupInfoParser.isWakeupAec());
            return;
        }
        Log.e(TAG, "dsp parser fail.");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void voicePrintReco() {
        final byte[] byteArray = this.mCacheSVData.toByteArray();
        if (this.isPrintLog) {
            Log.d(TAG, "sv data len:" + byteArray.length);
        }
        new Thread(new Runnable() {
            /* class com.xiaomi.asr.engine.impl.W2VPEngineImpl.AnonymousClass1 */

            public void run() {
                String str;
                if (W2VPEngineImpl.this.openVoicePrint) {
                    long currentTimeMillis = System.currentTimeMillis();
                    str = W2VPEngineImpl.this.mVoicePrintManager.recognizeVoice(byteArray, W2VPEngineImpl.this.mDSPWakeupInfoParser.isWakeupAec() ? 0 : -1, 1);
                    if (W2VPEngineImpl.this.mOutListener != null) {
                        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                        WVPListener wVPListener = W2VPEngineImpl.this.mOutListener;
                        wVPListener.onDebug(str + ", sv handle data len:" + byteArray.length + ", handle time:" + currentTimeMillis2 + ", rtf:" + String.format("%.3f", Float.valueOf(((float) currentTimeMillis2) / ((float) (W2VPEngineImpl.this.mDSPWakeupInfoParser.getWakeupEndTime() - W2VPEngineImpl.this.mDSPWakeupInfoParser.getWakeupStartTime())))) + "\n");
                    }
                } else {
                    str = "zhang_san:0.7813";
                }
                Message obtainMessage = W2VPEngineImpl.this.mWorkHandler.obtainMessage(9);
                obtainMessage.arg1 = 2;
                obtainMessage.obj = str;
                W2VPEngineImpl.this.mWorkHandler.sendMessageAtFrontOfQueue(obtainMessage);
                if (W2VPEngineImpl.this.saveRecord) {
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(W2VPEngineImpl.this.mDebugPath + "/" + W2VPEngineImpl.this.getCurrentTime() + "_sv.mfcc");
                        fileOutputStream.write(byteArray);
                        fileOutputStream.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
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
        return this.init ? this.mVoicePrintManager.getAllRegister() : "";
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
        DSPVoiceRecord dSPVoiceRecord = this.mVoiceRecord;
        if (dSPVoiceRecord != null) {
            dSPVoiceRecord.setParam(5, z ? 1 : 0);
        }
    }

    public void openVoicePrint(boolean z) {
        this.openVoicePrint = z;
    }

    public void release() {
        this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(7));
    }

    public void removeAllRegister() {
        this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(6));
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
        this.mCaptureSession = i;
        this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(3));
    }

    public void startEnrollment(int i) {
        Message obtainMessage = this.mWorkHandler.obtainMessage(2);
        obtainMessage.arg1 = i;
        this.mWorkHandler.sendMessage(obtainMessage);
    }

    public void startEnrollmentStreamingFileRecorder(AudioSource audioSource, int i) {
        Log.i(TAG, "preStartAudio");
        this.mAudioSource = audioSource;
        Message obtainMessage = this.mWorkHandler.obtainMessage(8192);
        obtainMessage.arg1 = i;
        this.mWorkHandler.sendMessage(obtainMessage);
    }

    public void stop() {
        this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(5));
    }

    public String version() {
        if (!this.init) {
            return "";
        }
        return "w2vp_sdk_202006011636 " + this.mWakeupEngineInterface.wakeupVersion() + " " + this.mVoicePrintManager.version();
    }
}
