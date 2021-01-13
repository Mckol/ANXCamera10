package miui.media;

import android.media.AudioRecord;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.miui.internal.variable.Android_Media_AudioRecord_class;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Mp3Recorder {
    private static final Android_Media_AudioRecord_class AudioRecordClass = Android_Media_AudioRecord_class.Factory.getInstance().get();
    public static final int ERR_COULD_NOT_START = 6;
    public static final int ERR_ENCODE_PCM_FAILED = 4;
    public static final int ERR_FILE_NOT_EXIST = 8;
    public static final int ERR_ILLEGAL_STATE = 1;
    public static final int ERR_MAX_SIZE_REACHED = 7;
    public static final int ERR_OUT_STREAM_NOT_READY = 2;
    public static final int ERR_RECORD_PCM_FAILED = 3;
    public static final int ERR_WRITE_TO_FILE = 5;
    private static final String LOG_TAG = "Mp3Recorder";
    private static final int MP3_RECORDER_EVENT_ERROR = 1;
    private static final int STATE_IDEL = 0;
    private static final int STATE_PAUSED = 3;
    private static final int STATE_PREPARED = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_STOPED = 4;
    public static final int VBR_QUALITY_HIGH = 2;
    public static final int VBR_QUALITY_LOW = 9;
    public static final int VBR_QUALITY_MEDIUM = 6;
    private byte[] bufferMP3;
    private short[] bufferPCM;
    private int mAudioChannel;
    private int mAudioSource;
    private int mChannelCount;
    private long mCountRecordingSamples;
    private long mCurrentRecordingSize;
    private Handler mEventHandler;
    private int mMaxAmplitude;
    private long mMaxFileSize;
    private int mMinBufferSize;
    private Mp3Encoder mMp3Encoder;
    private RecordingErrorListener mOnErrorListener;
    private int mOutBitRate;
    private int mOutMode;
    private File mOutputFile;
    private String mOutputFilePath;
    private FileOutputStream mOutputStream;
    private String mParams;
    private int mQuality;
    private AudioRecord mRecorder;
    private int mRecordingState;
    private RecordingThread mRecordingThread;
    private int mSampleRate;
    private int mVBRQuality;

    private class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if (message.what == 1 && Mp3Recorder.this.mOnErrorListener != null) {
                Mp3Recorder.this.mOnErrorListener.onError(Mp3Recorder.this, message.arg1);
            }
            super.handleMessage(message);
        }
    }

    public interface RecordingErrorListener {
        void onError(Mp3Recorder mp3Recorder, int i);
    }

    private class RecordingThread extends Thread {
        private RecordingThread() {
        }

        public void run() {
            Process.setThreadPriority(-19);
            Log.v(Mp3Recorder.LOG_TAG, "RecordingThread started");
            if (Mp3Recorder.this.mRecordingState != 2) {
                Log.e(Mp3Recorder.LOG_TAG, "Error illegal state: " + Mp3Recorder.this.mRecordingState);
                Mp3Recorder.this.notifyError(1);
            } else if (Mp3Recorder.this.mOutputStream == null) {
                Log.e(Mp3Recorder.LOG_TAG, "Error out put stream not ready");
                Mp3Recorder.this.notifyError(2);
            } else {
                while (true) {
                    if (Mp3Recorder.this.mRecordingState != 2) {
                        break;
                    }
                    int read = Mp3Recorder.this.mRecorder.read(Mp3Recorder.this.bufferPCM, 0, Mp3Recorder.this.bufferPCM.length);
                    Mp3Recorder.access$614(Mp3Recorder.this, (long) read);
                    if (read <= 0) {
                        Log.e(Mp3Recorder.LOG_TAG, "Error record sample failed, read size: " + read);
                        Mp3Recorder.this.notifyError(3);
                        break;
                    }
                    Mp3Recorder mp3Recorder = Mp3Recorder.this;
                    mp3Recorder.mMaxAmplitude = mp3Recorder.findMaxAmplitude(mp3Recorder.bufferPCM, read);
                    int encode = Mp3Recorder.this.mAudioChannel == 16 ? Mp3Recorder.this.mMp3Encoder.encode(Mp3Recorder.this.bufferPCM, Mp3Recorder.this.bufferPCM, read, Mp3Recorder.this.bufferMP3, Mp3Recorder.this.bufferMP3.length) : Mp3Recorder.this.mMp3Encoder.encodeInterleaved(Mp3Recorder.this.bufferPCM, read / Mp3Recorder.this.mChannelCount, Mp3Recorder.this.bufferMP3, Mp3Recorder.this.bufferMP3.length);
                    if (encode <= 0) {
                        if (encode == 0) {
                            Log.e(Mp3Recorder.LOG_TAG, "Not a complete frame samples to encode: skiped");
                        } else {
                            Log.e(Mp3Recorder.LOG_TAG, "Error encode PCM failed, encode size: " + encode + " read size: " + read);
                            if (Mp3Recorder.this.mOnErrorListener != null) {
                                Mp3Recorder.this.notifyError(4);
                            }
                        }
                    } else if (!Mp3Recorder.this.mOutputFile.exists()) {
                        Mp3Recorder.this.notifyError(8);
                        break;
                    } else {
                        try {
                            Mp3Recorder.this.mOutputStream.write(Mp3Recorder.this.bufferMP3, 0, encode);
                            Mp3Recorder.access$1514(Mp3Recorder.this, (long) encode);
                            if (Mp3Recorder.this.mCurrentRecordingSize >= Mp3Recorder.this.mMaxFileSize) {
                                Mp3Recorder.this.notifyError(7);
                                break;
                            }
                        } catch (IOException unused) {
                            Log.e(Mp3Recorder.LOG_TAG, "Error when write sample to file: " + Mp3Recorder.this.mOutputFilePath);
                            Mp3Recorder.this.notifyError(5);
                        }
                    }
                }
                Mp3Recorder.this.mMaxAmplitude = 0;
                Log.v(Mp3Recorder.LOG_TAG, "RecordingThread stoped");
            }
        }
    }

    public Mp3Recorder() {
        Looper myLooper = Looper.myLooper();
        if (myLooper != null) {
            this.mEventHandler = new EventHandler(myLooper);
        } else {
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper != null) {
                this.mEventHandler = new EventHandler(mainLooper);
            } else {
                Log.e(LOG_TAG, "Could not create event handler");
                this.mEventHandler = null;
            }
        }
        reset();
    }

    static /* synthetic */ long access$1514(Mp3Recorder mp3Recorder, long j) {
        long j2 = mp3Recorder.mCurrentRecordingSize + j;
        mp3Recorder.mCurrentRecordingSize = j2;
        return j2;
    }

    static /* synthetic */ long access$614(Mp3Recorder mp3Recorder, long j) {
        long j2 = mp3Recorder.mCountRecordingSamples + j;
        mp3Recorder.mCountRecordingSamples = j2;
        return j2;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int findMaxAmplitude(short[] sArr, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = sArr[i3] < 0 ? -sArr[i3] : sArr[i3];
            if (i4 > i2) {
                i2 = i4 == 1 ? 1 : 0;
            }
        }
        return i2;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void notifyError(int i) {
        Handler handler = this.mEventHandler;
        if (handler != null) {
            Message obtainMessage = handler.obtainMessage(1);
            obtainMessage.arg1 = i;
            this.mEventHandler.sendMessage(obtainMessage);
        }
    }

    private void prepareExtraParameters(AudioRecord audioRecord, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (AudioRecordClass.isExtraParamSupported()) {
            int parameters = AudioRecordClass.setParameters(audioRecord, str);
            Log.d(LOG_TAG, "setParameters: " + parameters);
            return;
        }
        Log.e(LOG_TAG, "Do not support extra parameters");
    }

    public int getMaxAmplitude() {
        return this.mMaxAmplitude;
    }

    public long getRecordingSizeInByte() {
        return this.mCurrentRecordingSize;
    }

    public long getRecordingTimeInMillis() {
        return (long) (((((double) this.mCountRecordingSamples) / ((double) this.mSampleRate)) * 1000.0d) / ((double) this.mChannelCount));
    }

    public boolean isExtraParamSupported() {
        return AudioRecordClass.isExtraParamSupported();
    }

    public boolean isPaused() {
        return this.mRecordingState == 3;
    }

    public synchronized void pause() throws IllegalStateException {
        if (this.mRecordingState != 2 || this.mRecordingThread == null) {
            throw new IllegalStateException("Recording not started");
        }
        this.mRecordingState = 3;
        try {
            this.mRecordingThread.join();
        } catch (InterruptedException e2) {
            Log.e(LOG_TAG, "InterruptedException when pause", e2);
        }
        this.mRecorder.stop();
        Log.v(LOG_TAG, "AudioRecord stoped");
        this.mRecorder.release();
        Log.v(LOG_TAG, "AudioRecord released");
        this.mRecorder = null;
        Log.v(LOG_TAG, "Mp3Recorder paused");
    }

    public void prepare() throws IllegalStateException, IOException {
        int i = 2;
        this.mMinBufferSize = AudioRecord.getMinBufferSize(this.mSampleRate, this.mAudioChannel, 2);
        int i2 = this.mMinBufferSize;
        if (i2 >= 0) {
            this.bufferPCM = new short[(i2 * 2)];
            this.mRecorder = new AudioRecord(this.mAudioSource, this.mSampleRate, this.mAudioChannel, 2, this.bufferPCM.length);
            prepareExtraParameters(this.mRecorder, this.mParams);
            Log.v(LOG_TAG, "Apply new AudioRecord");
            this.mOutMode = this.mAudioChannel == 12 ? 0 : 3;
            if (this.mAudioChannel != 12) {
                i = 1;
            }
            this.mChannelCount = i;
            this.bufferMP3 = new byte[((int) ((((double) this.bufferPCM.length) * 1.25d) + 7200.0d))];
            this.mMp3Encoder.setInSampleRate(this.mSampleRate);
            this.mMp3Encoder.setOutMode(this.mOutMode);
            this.mMp3Encoder.setChannelCount(this.mChannelCount);
            this.mMp3Encoder.setOutSampleRate(this.mSampleRate);
            this.mMp3Encoder.setOutBitRate(this.mOutBitRate);
            this.mMp3Encoder.setQuality(this.mQuality);
            this.mMp3Encoder.setVBRQuality(this.mVBRQuality);
            this.mMp3Encoder.init();
            this.mOutputFile = new File(this.mOutputFilePath);
            this.mRecordingState = 1;
            Log.v(LOG_TAG, "Mp3Recorder prepared");
            return;
        }
        Log.e(LOG_TAG, "Error when getting min buffer size");
        throw new IllegalStateException("Could not calculate the min buffer size");
    }

    public void release() {
        AudioRecord audioRecord = this.mRecorder;
        if (audioRecord != null) {
            audioRecord.release();
            this.mRecorder = null;
            Log.v(LOG_TAG, "AudioRecord released");
        }
        this.mMp3Encoder.close();
        Log.v(LOG_TAG, "Mp3Recorder released");
    }

    public void reset() {
        this.mRecordingState = 0;
        this.mRecorder = null;
        this.mAudioSource = 1;
        this.mSampleRate = 44100;
        this.mAudioChannel = 16;
        this.mQuality = 0;
        this.mRecordingState = 0;
        this.mOutBitRate = 64;
        this.mCountRecordingSamples = 0;
        this.mCurrentRecordingSize = 0;
        this.mMaxFileSize = Long.MAX_VALUE;
        this.mVBRQuality = -1;
        this.mMp3Encoder = new Mp3Encoder();
    }

    public synchronized void resume() throws IllegalStateException {
        if (this.mRecordingState == 3) {
            this.mRecorder = new AudioRecord(this.mAudioSource, this.mSampleRate, this.mAudioChannel, 2, this.bufferPCM.length);
            Log.v(LOG_TAG, "Apply new AudioRecord");
            prepareExtraParameters(this.mRecorder, this.mParams);
            this.mRecorder.startRecording();
            if (this.mRecorder.getRecordingState() == 3) {
                Log.v(LOG_TAG, "AudioRecord started");
                this.mRecordingState = 2;
                this.mRecordingThread = new RecordingThread();
                this.mRecordingThread.start();
                Log.v(LOG_TAG, "Mp3Recorder resumed");
            } else {
                throw new IllegalStateException("Mp3 record could not start: other input already started");
            }
        } else {
            Log.e(LOG_TAG, "Recording is going on");
            throw new IllegalStateException("Recording is going on");
        }
    }

    public void setAudioChannel(int i) {
        this.mAudioChannel = i;
    }

    public void setAudioSamplingRate(int i) {
        this.mSampleRate = i;
    }

    public void setAudioSource(int i) {
        this.mAudioSource = i;
    }

    public void setExtraParameters(String str) {
        if (AudioRecordClass.isExtraParamSupported()) {
            this.mParams = str;
            return;
        }
        throw new IllegalArgumentException("Do not support extra parameter");
    }

    public void setMaxDuration(long j) {
        if (j > 0) {
            this.mMaxFileSize = j * ((long) (this.mOutBitRate / 8));
        } else {
            this.mMaxFileSize = Long.MAX_VALUE;
        }
    }

    public void setMaxFileSize(long j) {
        if (j > 0) {
            this.mMaxFileSize = j;
        } else {
            this.mMaxFileSize = Long.MAX_VALUE;
        }
    }

    public void setOnErrorListener(RecordingErrorListener recordingErrorListener) {
        this.mOnErrorListener = recordingErrorListener;
    }

    public void setOutBitRate(int i) {
        this.mOutBitRate = i;
    }

    public void setOutputFile(String str) {
        this.mOutputFilePath = str;
    }

    public void setQuality(int i) {
        this.mQuality = i;
    }

    public void setVBRQuality(int i) {
        this.mVBRQuality = i;
    }

    public synchronized void start() throws IllegalStateException, IOException {
        if (this.mRecordingThread != null || this.mRecordingState == 2) {
            Log.e(LOG_TAG, "Recording has started");
            throw new IllegalStateException("Recording has already started");
        } else if (this.mRecordingState == 1) {
            this.mCountRecordingSamples = 0;
            this.mCurrentRecordingSize = 0;
            this.mRecordingState = 2;
            this.mRecorder.startRecording();
            if (this.mRecorder.getRecordingState() == 3) {
                this.mOutputStream = new FileOutputStream(this.mOutputFile);
                Log.v(LOG_TAG, "AudioRecord started");
                this.mRecordingThread = new RecordingThread();
                this.mRecordingThread.start();
                Log.v(LOG_TAG, "Mp3Recorder started");
            } else {
                throw new IllegalStateException("Mp3 record could not start: other input already started");
            }
        } else {
            Log.e(LOG_TAG, "Recorder not prepared");
            throw new IllegalStateException("Recorder not prepared");
        }
    }

    public synchronized void stop() throws IllegalStateException {
        String str;
        String str2;
        if (this.mRecordingState != 3) {
            if (this.mRecordingState != 2) {
                Log.e(LOG_TAG, "Recorder should not be stopped in state:" + this.mRecordingState);
                throw new IllegalStateException("Recorder shoul not be stopped in state : " + this.mRecordingState);
            }
        }
        this.mRecordingState = 4;
        try {
            if (this.mRecordingThread != null && this.mRecordingThread.isAlive()) {
                this.mRecordingThread.join();
            }
        } catch (InterruptedException e2) {
            Log.e(LOG_TAG, "InterruptedException when stop", e2);
        }
        this.mRecordingThread = null;
        int flush = this.mMp3Encoder.flush(this.bufferMP3, this.bufferMP3.length);
        if (flush > 0) {
            try {
                this.mOutputStream.write(this.bufferMP3, 0, flush);
                if (this.mVBRQuality >= 0 && this.mVBRQuality <= 9) {
                    this.mMp3Encoder.writeVBRHeader(this.mOutputFilePath);
                }
                try {
                    this.mOutputStream.close();
                } catch (IOException e3) {
                    Log.e(LOG_TAG, "Error file cannot be closed", e3);
                }
                if (this.mRecorder != null) {
                    this.mRecorder.stop();
                    str = LOG_TAG;
                    str2 = "AudioRecord stoped";
                    Log.v(str, str2);
                }
            } catch (IOException e4) {
                Log.e(LOG_TAG, "Error file cannot be written when flush", e4);
                try {
                    this.mOutputStream.close();
                } catch (IOException e5) {
                    Log.e(LOG_TAG, "Error file cannot be closed", e5);
                }
                if (this.mRecorder != null) {
                    this.mRecorder.stop();
                    str = LOG_TAG;
                    str2 = "AudioRecord stoped";
                }
            } catch (Throwable th) {
                try {
                    this.mOutputStream.close();
                } catch (IOException e6) {
                    Log.e(LOG_TAG, "Error file cannot be closed", e6);
                }
                if (this.mRecorder != null) {
                    this.mRecorder.stop();
                    Log.v(LOG_TAG, "AudioRecord stoped");
                }
                throw th;
            }
            Log.v(LOG_TAG, "Mp3Recorder stoped");
        } else {
            throw new IllegalStateException("Buffer flush must greater than 0");
        }
    }
}
