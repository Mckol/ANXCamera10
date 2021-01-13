package com.xiaomi.asr.engine.record;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.Process;
import android.util.Log;
import com.android.camera.statistic.MistatsConstants;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class DSPVoiceRecord {
    public static final int PARAM_KEY_SET_AUDIO_SOURCE = 1;
    public static final int PARAM_KEY_SET_CHANNEL = 0;
    public static final int PARAM_KEY_SET_ENCODING_BITS = 4;
    public static final int PARAM_KEY_SET_PRINT_LOG = 5;
    public static final int PARAM_KEY_SET_RECORD_BUFFER_SIZE = 2;
    public static final int PARAM_KEY_SET_SAMPLE_RATE = 3;
    private static final String TAG = "DSPVoiceRecord";
    private boolean isPrintLog = true;
    private int mAudioEncodingBits = 2;
    private int mAudioSource = 1;
    private int mCaptureSession;
    private int mChannels = 16;
    private RecordListener mListener;
    private int mRecordBufferSize = 1536;
    private RecordingRunnable mRecordThread;
    private int mSampleRate = 16000;
    private final Object syncObj = new Object();

    public interface RecordListener {
        void onAudioSessionId(int i);

        void onRecordCreateError();

        void onRecordRelease();

        void onRecording(byte[] bArr, int i);

        void onRecordingEnd();

        void onRecordingFailed();

        void onRecordingStart();
    }

    /* access modifiers changed from: private */
    public class RecordingRunnable implements Runnable {
        private AudioRecord mAudioRecord;
        private boolean mIsEnd;
        private boolean mIsExit;

        private RecordingRunnable() {
            this.mAudioRecord = null;
            this.mIsEnd = false;
            this.mIsExit = false;
        }

        private boolean init() {
            try {
                int minBufferSize = AudioRecord.getMinBufferSize(DSPVoiceRecord.this.mSampleRate, DSPVoiceRecord.this.mChannels, DSPVoiceRecord.this.mAudioEncodingBits);
                if (minBufferSize < 0) {
                    if (DSPVoiceRecord.this.mListener != null) {
                        DSPVoiceRecord.this.mListener.onRecordCreateError();
                    }
                    return false;
                }
                if (this.mAudioRecord == null) {
                    Constructor constructor = AudioRecord.class.getConstructor(AudioAttributes.class, AudioFormat.class, Integer.TYPE, Integer.TYPE);
                    Method method = AudioAttributes.Builder.class.getMethod("setInternalCapturePreset", Integer.TYPE);
                    AudioFormat build = new AudioFormat.Builder().setChannelMask(DSPVoiceRecord.this.mChannels).setEncoding(DSPVoiceRecord.this.mAudioEncodingBits).setSampleRate(DSPVoiceRecord.this.mSampleRate).build();
                    AudioAttributes.Builder builder = new AudioAttributes.Builder();
                    method.invoke(builder, Integer.valueOf(DSPVoiceRecord.this.mAudioSource));
                    this.mAudioRecord = (AudioRecord) constructor.newInstance(builder.build(), build, 800000, Integer.valueOf(DSPVoiceRecord.this.mCaptureSession));
                    Log.d(DSPVoiceRecord.TAG, "mAudioSource:" + DSPVoiceRecord.this.mAudioSource + ", mSampleRate:" + DSPVoiceRecord.this.mSampleRate + ", mChannels:" + DSPVoiceRecord.this.mChannels + ", mAudioEncodingBits:" + DSPVoiceRecord.this.mAudioEncodingBits + ", mRecordBufferSize:" + DSPVoiceRecord.this.mRecordBufferSize + ", mixRecordBufferSize:" + minBufferSize);
                    if (DSPVoiceRecord.this.mListener != null) {
                        DSPVoiceRecord.this.mListener.onAudioSessionId(this.mAudioRecord.getAudioSessionId());
                    }
                }
                if (this.mAudioRecord.getState() != 1) {
                    if (DSPVoiceRecord.this.mListener != null) {
                        DSPVoiceRecord.this.mListener.onRecordCreateError();
                    }
                    return false;
                }
                Log.d(DSPVoiceRecord.TAG, "init Recording");
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                if (DSPVoiceRecord.this.mListener != null) {
                    DSPVoiceRecord.this.mListener.onRecordCreateError();
                }
                return false;
            }
        }

        private boolean startup() {
            this.mIsEnd = false;
            this.mIsExit = false;
            long currentTimeMillis = System.currentTimeMillis();
            if (this.mAudioRecord.getState() == 1) {
                try {
                    Log.d(DSPVoiceRecord.TAG, "start Recording");
                    this.mAudioRecord.startRecording();
                    long currentTimeMillis2 = System.currentTimeMillis();
                    Log.d(DSPVoiceRecord.TAG, "start recording deltaTime = " + (currentTimeMillis2 - currentTimeMillis));
                    return true;
                } catch (IllegalStateException e2) {
                    e2.printStackTrace();
                    Log.e(DSPVoiceRecord.TAG, "start Recording failed");
                    if (DSPVoiceRecord.this.mListener != null) {
                        DSPVoiceRecord.this.mListener.onRecordCreateError();
                    }
                    return false;
                }
            } else {
                if (DSPVoiceRecord.this.mListener != null) {
                    DSPVoiceRecord.this.mListener.onRecordCreateError();
                }
                return false;
            }
        }

        public void run() {
            Process.setThreadPriority(-19);
            if (init()) {
                try {
                    byte[] bArr = new byte[DSPVoiceRecord.this.mRecordBufferSize];
                    Log.d(DSPVoiceRecord.TAG, "mRecordBufferSize: " + DSPVoiceRecord.this.mRecordBufferSize);
                    if (startup()) {
                        if (DSPVoiceRecord.this.mListener != null) {
                            DSPVoiceRecord.this.mListener.onRecordingStart();
                        }
                        while (true) {
                            if (this.mIsExit) {
                                break;
                            }
                            int read = this.mAudioRecord.read(bArr, 0, DSPVoiceRecord.this.mRecordBufferSize);
                            if (read <= 0) {
                                if (DSPVoiceRecord.this.mListener != null) {
                                    DSPVoiceRecord.this.mListener.onRecordingFailed();
                                }
                                Log.e(DSPVoiceRecord.TAG, "read() error :" + read);
                            } else {
                                synchronized (DSPVoiceRecord.this.syncObj) {
                                    if (DSPVoiceRecord.this.mListener != null) {
                                        DSPVoiceRecord.this.mListener.onRecording(bArr, read);
                                    }
                                    this.mIsExit = this.mIsEnd;
                                }
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (DSPVoiceRecord.this.mListener != null) {
                    DSPVoiceRecord.this.mListener.onRecordingEnd();
                }
                if (this.mAudioRecord != null) {
                    synchronized (DSPVoiceRecord.this.syncObj) {
                        try {
                            this.mAudioRecord.stop();
                            this.mAudioRecord.release();
                            this.mAudioRecord = null;
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                }
                Log.d(DSPVoiceRecord.TAG, "RecordingRunnable is exit");
                if (DSPVoiceRecord.this.mListener != null) {
                    DSPVoiceRecord.this.mListener.onRecordRelease();
                }
            }
        }

        public void stop() {
            synchronized (DSPVoiceRecord.this.syncObj) {
                this.mIsEnd = true;
                Log.d(DSPVoiceRecord.TAG, MistatsConstants.BaseEvent.STOP);
            }
        }
    }

    public DSPVoiceRecord(RecordListener recordListener) {
        this.mListener = recordListener;
    }

    private void printLog(String str, String str2) {
        if (this.isPrintLog) {
            Log.d(str, str2);
        }
    }

    public int getParamValue(int i) {
        if (i == 0) {
            return this.mChannels;
        }
        if (i == 1) {
            return this.mAudioSource;
        }
        if (i == 2) {
            return this.mRecordBufferSize;
        }
        if (i == 3) {
            return this.mSampleRate;
        }
        if (i != 4) {
            return -1;
        }
        return this.mAudioEncodingBits;
    }

    public void setParam(int i, int i2) {
        if (i != 0) {
            boolean z = true;
            if (i == 1) {
                this.mAudioSource = i2;
            } else if (i == 2) {
                this.mRecordBufferSize = i2;
            } else if (i == 3) {
                this.mSampleRate = i2;
            } else if (i == 4) {
                this.mAudioEncodingBits = i2;
            } else if (i == 5) {
                if (i2 == 0) {
                    z = false;
                }
                this.isPrintLog = z;
            }
        } else {
            this.mChannels = i2;
        }
    }

    public void startRecord(int i) {
        try {
            this.mCaptureSession = i;
            if (this.mRecordThread != null) {
                new Thread(this.mRecordThread).start();
                return;
            }
            this.mRecordThread = new RecordingRunnable();
            new Thread(this.mRecordThread).start();
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
            RecordListener recordListener = this.mListener;
            if (recordListener != null) {
                recordListener.onRecordingFailed();
            }
        }
    }

    public void stopRecord() {
        if (this.mRecordThread != null) {
            Log.d(TAG, "stopRecord");
            this.mRecordThread.stop();
            this.mRecordThread = null;
        }
    }
}
