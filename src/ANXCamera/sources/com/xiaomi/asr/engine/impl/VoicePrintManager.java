package com.xiaomi.asr.engine.impl;

import android.media.AudioRecord;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.asr.engine.WVPListener;
import com.xiaomi.asr.engine.jni.VoicePrintInterface;
import com.xiaomi.asr.engine.record.AudioSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class VoicePrintManager {
    private static final int REGISTER_FEED_RESULT_NO_RESULT = 3;
    private static final int REGISTER_FEED_RESULT_SUCCESS = 0;
    private static final int SIZEOF_REGISTER_PCM = 5;
    private static final String TAG = "VoicePrintManager";
    FileRecordWorker fileRecordWorker = null;
    private boolean init = false;
    private int mCurrentRecordingPos = 0;
    private WVPListener mOutListener;
    private byte[][] mPcmSet = new byte[5][];
    private int mRecordSource = 1;
    private RecordWorker mRecordWorker;
    private String mSpeakerName = "";
    private VoicePrintInterface mVoicePrintInterface;
    private String mVoicePrintModelFullPath = "";

    /* access modifiers changed from: package-private */
    public class FileRecordWorker extends Thread {
        private AudioSource mAudioSource;
        private ByteArrayOutputStream mCacheData;
        private boolean mCancel;
        private int mMaxTime;
        private boolean mRecordWorking;
        private int mRegisterState;

        private FileRecordWorker(AudioSource audioSource, int i) {
            this.mAudioSource = null;
            this.mAudioSource = audioSource;
            this.mMaxTime = i;
            this.mCacheData = new ByteArrayOutputStream();
        }

        public void cancel() {
            this.mCancel = true;
            this.mRecordWorking = false;
        }

        public void end() {
            this.mRecordWorking = false;
        }

        public void run() {
            byte[] bArr = new byte[320];
            long uptimeMillis = SystemClock.uptimeMillis();
            this.mAudioSource.init();
            try {
                this.mAudioSource.startRecording();
                if (VoicePrintManager.this.mOutListener != null) {
                    VoicePrintManager.this.mOutListener.onStartAudio();
                }
                while (this.mRecordWorking && SystemClock.uptimeMillis() - uptimeMillis <= ((long) this.mMaxTime) && this.mAudioSource.read(bArr, 0, 320) > 0) {
                    try {
                        this.mCacheData.write(bArr);
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                if (this.mCacheData.size() > 0) {
                    this.mRegisterState = VoicePrintManager.this.mVoicePrintInterface.voiceprintReEnrollRegister(VoicePrintManager.this.mSpeakerName, this.mCacheData.toByteArray(), 0, this.mCacheData.size());
                }
                this.mAudioSource.release();
                if (VoicePrintManager.this.mOutListener != null) {
                    VoicePrintManager.this.mOutListener.onStopAudio();
                }
                if (this.mCancel) {
                    if (VoicePrintManager.this.mOutListener != null) {
                        VoicePrintManager.this.mOutListener.onAbortEnrollmentComplete();
                    }
                } else if (this.mRegisterState == 0) {
                    if (VoicePrintManager.this.mCurrentRecordingPos >= 5) {
                        VoicePrintManager.this.mCurrentRecordingPos = 0;
                    }
                    VoicePrintManager.this.mCurrentRecordingPos++;
                    if (VoicePrintManager.this.mOutListener != null) {
                        VoicePrintManager.this.mOutListener.onEnrollmentComplete(true, false, 0.0f, 0);
                    }
                    if (VoicePrintManager.this.mOutListener != null) {
                        VoicePrintManager.this.mOutListener.onEnrollAudioBufferAvailable(this.mCacheData.toByteArray(), true);
                    }
                } else {
                    if (VoicePrintManager.this.mOutListener != null) {
                        VoicePrintManager.this.mOutListener.onEnrollmentComplete(false, false, 0.0f, this.mRegisterState);
                    }
                    if (VoicePrintManager.this.mOutListener != null) {
                        VoicePrintManager.this.mOutListener.onEnrollAudioBufferAvailable(this.mCacheData.toByteArray(), false);
                    }
                }
            } catch (IllegalStateException e3) {
                Log.e(VoicePrintManager.TAG, "IllegalStateException", e3);
            }
        }

        public void start() {
            this.mRecordWorking = true;
            this.mCancel = false;
            super.start();
        }
    }

    /* access modifiers changed from: package-private */
    public class RecordWorker extends Thread {
        private ByteArrayOutputStream mCacheData = new ByteArrayOutputStream();
        private boolean mCancel;
        private int mMaxTime;
        private boolean mRecordWorking;
        public int mRegisterState;

        public RecordWorker(int i) {
            this.mMaxTime = i;
        }

        public void cancel() {
            this.mCancel = true;
            this.mRecordWorking = false;
        }

        public void end() {
            this.mRecordWorking = false;
        }

        public void run() {
            byte[] bArr = new byte[320];
            long uptimeMillis = SystemClock.uptimeMillis();
            int minBufferSize = AudioRecord.getMinBufferSize(16000, 16, 2);
            Log.d(VoicePrintManager.TAG, "mixRecordBufferSize:" + minBufferSize + ", mRecordSource:" + VoicePrintManager.this.mRecordSource);
            AudioRecord audioRecord = new AudioRecord(VoicePrintManager.this.mRecordSource, 16000, 16, 2, minBufferSize);
            audioRecord.startRecording();
            if (VoicePrintManager.this.mOutListener != null) {
                VoicePrintManager.this.mOutListener.onStartAudio();
            }
            while (true) {
                if (!this.mRecordWorking || SystemClock.uptimeMillis() - uptimeMillis > ((long) this.mMaxTime)) {
                    break;
                }
                int read = audioRecord.read(bArr, 0, 320);
                if (read > 0) {
                    this.mRegisterState = VoicePrintManager.this.mVoicePrintInterface.voiceprintRegister(VoicePrintManager.this.mSpeakerName, bArr);
                    if (this.mRegisterState != 3) {
                        break;
                    }
                    try {
                        this.mCacheData.write(bArr);
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    if (VoicePrintManager.this.mOutListener != null) {
                        VoicePrintManager.this.mOutListener.onEnergyLevelAvailable(VoicePrintManager.this.calculateVolume(Arrays.copyOf(bArr, read)), true);
                    }
                } else if (read < 0) {
                    Log.d(VoicePrintManager.TAG, "local record error:" + read);
                    if (VoicePrintManager.this.mOutListener != null) {
                        VoicePrintManager.this.mOutListener.onConflictAudio();
                    }
                }
            }
            audioRecord.stop();
            audioRecord.release();
            if (VoicePrintManager.this.mOutListener != null) {
                VoicePrintManager.this.mOutListener.onStopAudio();
            }
            if (this.mCancel) {
                if (VoicePrintManager.this.mOutListener != null) {
                    VoicePrintManager.this.mOutListener.onAbortEnrollmentComplete();
                }
            } else if (this.mRegisterState == 0) {
                if (VoicePrintManager.this.mCurrentRecordingPos >= 5) {
                    VoicePrintManager.this.mCurrentRecordingPos = 0;
                }
                VoicePrintManager.this.mPcmSet[VoicePrintManager.this.mCurrentRecordingPos] = Arrays.copyOf(this.mCacheData.toByteArray(), this.mCacheData.size());
                VoicePrintManager.this.mCurrentRecordingPos++;
                if (VoicePrintManager.this.mOutListener != null) {
                    VoicePrintManager.this.mOutListener.onEnrollmentComplete(true, false, 0.0f, 0);
                }
                if (VoicePrintManager.this.mOutListener != null) {
                    VoicePrintManager.this.mOutListener.onEnrollAudioBufferAvailable(this.mCacheData.toByteArray(), true);
                }
            } else {
                if (VoicePrintManager.this.mOutListener != null) {
                    VoicePrintManager.this.mOutListener.onEnrollmentComplete(false, false, 0.0f, this.mRegisterState);
                }
                if (VoicePrintManager.this.mOutListener != null) {
                    VoicePrintManager.this.mOutListener.onEnrollAudioBufferAvailable(this.mCacheData.toByteArray(), false);
                }
            }
        }

        public void start() {
            this.mRecordWorking = true;
            this.mCancel = false;
            super.start();
        }
    }

    /* access modifiers changed from: package-private */
    public class RegisterWorker extends Thread {
        RegisterWorker() {
        }

        public void run() {
            if (VoicePrintManager.this.mVoicePrintInterface.voiceprintGenerateModel(VoicePrintManager.this.mSpeakerName)) {
                Log.d(VoicePrintManager.TAG, VoicePrintManager.this.mSpeakerName + " register success.");
                if (VoicePrintManager.this.mOutListener != null) {
                    VoicePrintManager.this.mOutListener.onGenerateModel(true, VoicePrintManager.this.mVoicePrintModelFullPath);
                    return;
                }
                return;
            }
            Log.d(VoicePrintManager.TAG, VoicePrintManager.this.mSpeakerName + " register fail.");
            if (VoicePrintManager.this.mOutListener != null) {
                VoicePrintManager.this.mOutListener.onGenerateModel(false, VoicePrintManager.this.mVoicePrintModelFullPath);
            }
        }

        public void start() {
            super.start();
        }
    }

    public VoicePrintManager(String str) {
        VoicePrintInterface.loadLibrary(str);
        this.mVoicePrintInterface = new VoicePrintInterface();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float calculateVolume(byte[] bArr) {
        int length = bArr.length;
        if (bArr.length % 2 != 0) {
            length = bArr.length - 1;
        }
        double d2 = 0.0d;
        for (int i = 0; i < length; i += 2) {
            int i2 = (bArr[i] & 255) + ((bArr[i + 1] & 255) << 8);
            if (i2 >= 32768) {
                i2 = 65535 - i2;
            }
            d2 += Math.pow((double) i2, 2.0d);
        }
        double abs = Math.abs(Math.log10(d2 / ((double) (length / 2))) * 10.0d);
        if (abs > 100.0d) {
            abs = 100.0d;
        }
        return (float) Math.round(abs);
    }

    public void abortEnrollment() {
        RecordWorker recordWorker = this.mRecordWorker;
        if (recordWorker != null) {
            recordWorker.cancel();
            this.mRecordWorker = null;
            return;
        }
        WVPListener wVPListener = this.mOutListener;
        if (wVPListener != null) {
            wVPListener.onAbortEnrollmentComplete();
        }
    }

    public void cancelEnrollment() {
        abortEnrollment();
        this.mCurrentRecordingPos = 0;
    }

    public void commitEnrollment() {
        WVPListener wVPListener = this.mOutListener;
        if (wVPListener != null) {
            wVPListener.onCommitEnrollmentComplete();
        }
    }

    public void generateModel() {
        if (this.init && this.mCurrentRecordingPos >= 5) {
            new RegisterWorker().start();
        }
    }

    public String getAllRegister() {
        return this.init ? this.mVoicePrintInterface.voiceprintGetAllRegister() : "";
    }

    public int init(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return -1;
        }
        this.mVoicePrintModelFullPath = str;
        this.mSpeakerName = str2;
        int voiceprintInit = this.mVoicePrintInterface.voiceprintInit(this.mVoicePrintModelFullPath, this.mSpeakerName);
        if (voiceprintInit == 0) {
            this.init = true;
        }
        return voiceprintInit;
    }

    public String recognizeVoice(byte[] bArr, int i, int i2) {
        return this.init ? this.mVoicePrintInterface.voiceprintRecognition(bArr, i, i2) : "error:not init";
    }

    public void release() {
        if (this.init) {
            this.mVoicePrintInterface.voiceprintDestroy();
            this.init = false;
        }
    }

    public void removeAllRegister() {
        if (this.init) {
            this.mVoicePrintInterface.voiceprintRemoveAll();
        }
    }

    public void setListener(WVPListener wVPListener) {
        this.mOutListener = wVPListener;
    }

    public void setRecordSource(int i) {
        this.mRecordSource = i;
    }

    public void startEnrollment(int i) {
        if (this.init) {
            this.mRecordWorker = new RecordWorker(i);
            this.mRecordWorker.start();
        }
    }

    public void startEnrollmentFileRecorded(AudioSource audioSource, int i) {
        if (this.init) {
            this.fileRecordWorker = new FileRecordWorker(audioSource, i);
            this.fileRecordWorker.start();
        }
    }

    public String version() {
        return this.init ? this.mVoicePrintInterface.voiceprintVersion() : "";
    }
}
