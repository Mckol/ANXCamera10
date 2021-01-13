package miui.media;

import android.media.AudioRecord;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.miui.internal.variable.Android_Media_AudioRecord_class;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import miui.media.Recorder;

class LocalWavRecorder implements Recorder {
    private static final Android_Media_AudioRecord_class AudioRecordClass = Android_Media_AudioRecord_class.Factory.getInstance().get();
    private static final int EVENT_ERROR = 1;
    private static final String LOG_TAG = "WavRecorder";
    private static final int STATE_IDEL = 0;
    private static final int STATE_PAUSED = 3;
    private static final int STATE_PREPARED = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_STOPED = 4;
    private final int OFFSET_WAVE_HEADER_AUDIO_DATA_LENGTH = 40;
    private final int OFFSET_WAVE_HEADER_DATA_LENGTH = 4;
    private final int WAVE_HEADER_LENGTH = 44;
    private int mAudioChannel;
    private int mAudioSource;
    private short[] mBufferPCM;
    private int mChannelCount;
    private long mCountRecordingSamples;
    private long mCurrentRecordingSize;
    private Handler mEventHandler;
    private int mMaxAmplitude;
    private long mMaxFileSize;
    private int mMinBufferSize;
    private Recorder.OnErrorListener mOnErrorListener;
    private File mOutputFile;
    private String mOutputFilePath;
    private DataOutputStream mOutputStream;
    private String mParams;
    private AudioRecord mRecorder;
    private int mRecordingState;
    private RecordingThread mRecordingThread;
    private int mSampleRate;

    private class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if (message.what == 1 && LocalWavRecorder.this.mOnErrorListener != null) {
                LocalWavRecorder.this.mOnErrorListener.onError(LocalWavRecorder.this, RecorderUtils.convertErrorCode(message.arg1, false));
            }
            super.handleMessage(message);
        }
    }

    private class RecordingThread extends Thread {
        private RecordingThread() {
        }

        public void run() {
            Process.setThreadPriority(-19);
            Log.v(LocalWavRecorder.LOG_TAG, "RecordingThread started");
            if (LocalWavRecorder.this.mRecordingState != 2) {
                Log.e(LocalWavRecorder.LOG_TAG, "Error illegal state: " + LocalWavRecorder.this.mRecordingState);
                LocalWavRecorder.this.notifyError(1001);
            } else if (LocalWavRecorder.this.mOutputStream == null) {
                Log.e(LocalWavRecorder.LOG_TAG, "Error out put stream not ready");
                LocalWavRecorder.this.notifyError(1002);
            } else {
                while (true) {
                    if (LocalWavRecorder.this.mRecordingState != 2) {
                        break;
                    }
                    int read = LocalWavRecorder.this.mRecorder.read(LocalWavRecorder.this.mBufferPCM, 0, LocalWavRecorder.this.mBufferPCM.length);
                    LocalWavRecorder.access$614(LocalWavRecorder.this, (long) read);
                    if (read <= 0) {
                        Log.e(LocalWavRecorder.LOG_TAG, "Error record sample failed, read size: " + read);
                        LocalWavRecorder.this.notifyError(1003);
                        break;
                    }
                    LocalWavRecorder localWavRecorder = LocalWavRecorder.this;
                    localWavRecorder.mMaxAmplitude = localWavRecorder.findMaxAmplitude(localWavRecorder.mBufferPCM, read);
                    if (!LocalWavRecorder.this.mOutputFile.exists()) {
                        LocalWavRecorder.this.notifyError(1009);
                        break;
                    }
                    try {
                        LocalWavRecorder.this.mOutputStream.write(RecorderUtils.short2byte(LocalWavRecorder.this.mBufferPCM, read));
                        LocalWavRecorder.access$1014(LocalWavRecorder.this, (long) (read * 2));
                        if (LocalWavRecorder.this.mCurrentRecordingSize >= LocalWavRecorder.this.mMaxFileSize) {
                            LocalWavRecorder.this.notifyError(1007);
                            break;
                        }
                    } catch (IOException unused) {
                        Log.e(LocalWavRecorder.LOG_TAG, "Error when write sample to file: " + LocalWavRecorder.this.mOutputFilePath);
                        LocalWavRecorder.this.notifyError(1005);
                    }
                }
                LocalWavRecorder.this.mMaxAmplitude = 0;
                Log.v(LocalWavRecorder.LOG_TAG, "RecordingThread stoped");
            }
        }
    }

    public LocalWavRecorder() {
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

    static /* synthetic */ long access$1014(LocalWavRecorder localWavRecorder, long j) {
        long j2 = localWavRecorder.mCurrentRecordingSize + j;
        localWavRecorder.mCurrentRecordingSize = j2;
        return j2;
    }

    static /* synthetic */ long access$614(LocalWavRecorder localWavRecorder, long j) {
        long j2 = localWavRecorder.mCountRecordingSamples + j;
        localWavRecorder.mCountRecordingSamples = j2;
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
            handler.sendMessage(handler.obtainMessage(1, i, 0));
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

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x007e A[SYNTHETIC, Splitter:B:37:0x007e] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0086 A[Catch:{ IOException -> 0x0082 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    private void updateWaveHeader() {
        Throwable th;
        RandomAccessFile randomAccessFile;
        FileNotFoundException e2;
        FileChannel fileChannel;
        IOException e3;
        FileChannel fileChannel2;
        RandomAccessFile randomAccessFile2 = null;
        r2 = null;
        r2 = null;
        r2 = null;
        FileChannel fileChannel3 = null;
        RandomAccessFile randomAccessFile3 = null;
        try {
            randomAccessFile = new RandomAccessFile(this.mOutputFile, "rw");
            try {
                fileChannel3 = randomAccessFile.getChannel();
                MappedByteBuffer map = fileChannel3.map(FileChannel.MapMode.READ_WRITE, 0, 44);
                RecorderUtils.updateBytes(map, 4, RecorderUtils.getBytes(this.mCurrentRecordingSize + 36));
                RecorderUtils.updateBytes(map, 40, RecorderUtils.getBytes(this.mCurrentRecordingSize));
                try {
                    randomAccessFile.close();
                    if (fileChannel3 != null) {
                        fileChannel3.close();
                    }
                } catch (IOException e4) {
                    Log.e(LOG_TAG, "file close", e4);
                }
            } catch (FileNotFoundException e5) {
                e2 = e5;
                fileChannel = fileChannel3;
                randomAccessFile2 = randomAccessFile;
                Log.e(LOG_TAG, "FileNotFoundException", e2);
                if (randomAccessFile2 != null) {
                    randomAccessFile2.close();
                }
                if (fileChannel == null) {
                    fileChannel.close();
                }
            } catch (IOException e6) {
                e3 = e6;
                fileChannel2 = fileChannel3;
                randomAccessFile3 = randomAccessFile;
                try {
                    Log.e(LOG_TAG, "IOException", e3);
                    if (randomAccessFile3 != null) {
                        randomAccessFile3.close();
                    }
                    if (fileChannel2 == null) {
                        fileChannel2.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    randomAccessFile = randomAccessFile3;
                    fileChannel3 = fileChannel2;
                    if (randomAccessFile != null) {
                    }
                    if (fileChannel3 != null) {
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException e7) {
                        Log.e(LOG_TAG, "file close", e7);
                        throw th;
                    }
                }
                if (fileChannel3 != null) {
                    fileChannel3.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e8) {
            e2 = e8;
            fileChannel = null;
            Log.e(LOG_TAG, "FileNotFoundException", e2);
            if (randomAccessFile2 != null) {
            }
            if (fileChannel == null) {
            }
        } catch (IOException e9) {
            e3 = e9;
            fileChannel2 = null;
            Log.e(LOG_TAG, "IOException", e3);
            if (randomAccessFile3 != null) {
            }
            if (fileChannel2 == null) {
            }
        } catch (Throwable th4) {
            th = th4;
            randomAccessFile = null;
            if (randomAccessFile != null) {
            }
            if (fileChannel3 != null) {
            }
            throw th;
        }
    }

    @Override // miui.media.Recorder
    public boolean canPause() {
        return true;
    }

    @Override // miui.media.Recorder
    public int getMaxAmplitude() {
        return this.mMaxAmplitude;
    }

    public long getRecordingSizeInByte() {
        return this.mCurrentRecordingSize;
    }

    @Override // miui.media.Recorder
    public long getRecordingTimeInMillis() {
        return (long) (((((double) this.mCountRecordingSamples) * 2.0d) / ((double) ((this.mSampleRate * 2) * this.mChannelCount))) * 1000.0d);
    }

    public boolean isExtraParamSupported() {
        return AudioRecordClass.isExtraParamSupported();
    }

    @Override // miui.media.Recorder
    public boolean isPaused() {
        return this.mRecordingState == 3;
    }

    @Override // miui.media.Recorder
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
        Log.v(LOG_TAG, "WAVRecorder paused");
    }

    @Override // miui.media.Recorder
    public void prepare() throws IllegalStateException, IOException {
        int i = 2;
        this.mMinBufferSize = AudioRecord.getMinBufferSize(this.mSampleRate, this.mAudioChannel, 2);
        int i2 = this.mMinBufferSize;
        if (i2 >= 0) {
            this.mBufferPCM = new short[(i2 * 2)];
            this.mRecorder = new AudioRecord(this.mAudioSource, this.mSampleRate, this.mAudioChannel, 2, this.mBufferPCM.length);
            prepareExtraParameters(this.mRecorder, this.mParams);
            Log.v(LOG_TAG, "Apply new AudioRecord");
            if (this.mAudioChannel != 12) {
                i = 1;
            }
            this.mChannelCount = i;
            this.mOutputFile = new File(this.mOutputFilePath);
            this.mRecordingState = 1;
            Log.v(LOG_TAG, "WAVRecorder prepared");
            return;
        }
        Log.e(LOG_TAG, "Error when getting min buffer size");
        throw new IllegalStateException("Could not calculate the min buffer size");
    }

    @Override // miui.media.Recorder
    public void release() {
        AudioRecord audioRecord = this.mRecorder;
        if (audioRecord != null) {
            audioRecord.release();
            this.mRecorder = null;
            Log.v(LOG_TAG, "AudioRecord released");
        }
        Log.v(LOG_TAG, "WAVRecorder released");
    }

    @Override // miui.media.Recorder
    public void reset() {
        this.mRecordingState = 0;
        this.mRecorder = null;
        this.mAudioSource = 1;
        this.mSampleRate = 44100;
        this.mAudioChannel = 16;
        this.mRecordingState = 0;
        this.mCountRecordingSamples = 0;
        this.mCurrentRecordingSize = 0;
        this.mMaxFileSize = Long.MAX_VALUE;
    }

    @Override // miui.media.Recorder
    public synchronized void resume() throws IllegalStateException {
        if (this.mRecordingState == 3) {
            this.mRecorder = new AudioRecord(this.mAudioSource, this.mSampleRate, this.mAudioChannel, 2, this.mBufferPCM.length);
            Log.v(LOG_TAG, "Apply new AudioRecord");
            prepareExtraParameters(this.mRecorder, this.mParams);
            this.mRecorder.startRecording();
            if (this.mRecorder.getRecordingState() == 3) {
                Log.v(LOG_TAG, "AudioRecord started");
                this.mRecordingState = 2;
                this.mRecordingThread = new RecordingThread();
                this.mRecordingThread.start();
                Log.v(LOG_TAG, "WAVRecorder resumed");
            } else {
                throw new IllegalStateException("Mp3 record could not start: other input already started");
            }
        } else {
            Log.e(LOG_TAG, "Recording is going on");
            throw new IllegalStateException("Recording is going on");
        }
    }

    @Override // miui.media.Recorder
    public void setAudioChannel(int i) {
        this.mAudioChannel = i;
    }

    @Override // miui.media.Recorder
    public void setAudioEncoder(int i) {
    }

    @Override // miui.media.Recorder
    public void setAudioEncodingBitRate(int i) {
    }

    @Override // miui.media.Recorder
    public void setAudioSamplingRate(int i) {
        this.mSampleRate = i;
    }

    @Override // miui.media.Recorder
    public void setAudioSource(int i) {
        this.mAudioSource = i;
    }

    @Override // miui.media.Recorder
    public void setExtraParameters(String str) {
        if (AudioRecordClass.isExtraParamSupported()) {
            this.mParams = str;
            return;
        }
        throw new IllegalArgumentException("Do not support extra parameter");
    }

    @Override // miui.media.Recorder
    public void setMaxDuration(int i) {
        if (i > 0) {
            this.mMaxFileSize = (long) (((((i / 1000) * this.mSampleRate) * 16) * this.mChannelCount) / 8);
        } else {
            this.mMaxFileSize = Long.MAX_VALUE;
        }
    }

    @Override // miui.media.Recorder
    public void setMaxFileSize(long j) {
        if (j > 0) {
            this.mMaxFileSize = j;
        } else {
            this.mMaxFileSize = Long.MAX_VALUE;
        }
    }

    @Override // miui.media.Recorder
    public void setOnErrorListener(Recorder.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    @Override // miui.media.Recorder
    public void setOutputFile(String str) {
        this.mOutputFilePath = str;
    }

    @Override // miui.media.Recorder
    public void setOutputFormat(int i) {
    }

    @Override // miui.media.Recorder
    public void setQuality(int i) {
    }

    @Override // miui.media.Recorder
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
                FileOutputStream fileOutputStream = new FileOutputStream(this.mOutputFile);
                this.mOutputStream = new DataOutputStream(new BufferedOutputStream(fileOutputStream));
                Log.v(LOG_TAG, "AudioRecord started");
                fileOutputStream.write(RecorderUtils.getWaveHeader(this.mAudioChannel, this.mSampleRate));
                this.mRecordingThread = new RecordingThread();
                this.mRecordingThread.start();
                Log.v(LOG_TAG, "WAVRecorder started");
            } else {
                throw new IllegalStateException("Wav record could not start: other input already started");
            }
        } else {
            Log.e(LOG_TAG, "Recorder not prepared");
            throw new IllegalStateException("Recorder not prepared");
        }
    }

    @Override // miui.media.Recorder
    public synchronized void stop() throws IllegalStateException {
        if (this.mRecordingState != 3) {
            if (this.mRecordingState != 2) {
                Log.e(LOG_TAG, "Recorder should not be stopped in state:" + this.mRecordingState);
                throw new IllegalStateException("Recorder should not be stopped in state : " + this.mRecordingState);
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
        try {
            this.mOutputStream.close();
        } catch (IOException e3) {
            Log.e(LOG_TAG, "Error file cannot be closed", e3);
        }
        if (this.mRecorder != null) {
            this.mRecorder.stop();
            Log.v(LOG_TAG, "AudioRecord stoped");
        }
        updateWaveHeader();
        Log.v(LOG_TAG, "WavRecorder stoped");
    }
}
