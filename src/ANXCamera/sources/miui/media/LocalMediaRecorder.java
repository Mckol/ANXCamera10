package miui.media;

import android.media.MediaRecorder;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import miui.media.Recorder;
import miui.reflect.Method;
import miui.reflect.NoSuchMethodException;

class LocalMediaRecorder implements Recorder {
    private static final String TAG = "Media:LocalMediaRecorder";
    private static final Method setParameter;
    private int mAudioEncoder = -1;
    private int mAudioSource = -1;
    private int mBitRate = -1;
    private String mDestFilePath;
    private String mExtraParameter;
    private boolean mIsPaused;
    private int mMaxDuration = -1;
    private long mMaxSize = -1;
    private MediaRecorder mMediaRecorder = new MediaRecorder();
    private int mNumChannels = -1;
    private MediaRecorder.OnErrorListener mOnErrorListener;
    private MediaRecorder.OnInfoListener mOnInfoListener;
    private int mOutputFormat = -1;
    private RecorderAppendThread mRecorderAppendThread;
    private ParcelFileDescriptor mRecorderAppendWriteFd;
    private Recorder.OnErrorListener mRecorderOnErrorListener;
    private int mRecordingDuration;
    private int mSamplingRate = -1;
    private long mSizeRecored = 0;
    private long mStartTime = 0;
    private Object mSyncer = new Object();

    /* access modifiers changed from: private */
    public class RecorderAppendThread extends Thread {
        private AtomicBoolean mRunningLatch;
        private ParcelFileDescriptor mSrcFd;

        RecorderAppendThread(ParcelFileDescriptor parcelFileDescriptor) {
            this.mSrcFd = parcelFileDescriptor;
        }

        /* JADX WARNING: Removed duplicated region for block: B:43:0x0086 A[SYNTHETIC, Splitter:B:43:0x0086] */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x008e A[Catch:{ IOException -> 0x008a }] */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x00a1 A[SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x00b5 A[SYNTHETIC, Splitter:B:63:0x00b5] */
        /* JADX WARNING: Removed duplicated region for block: B:68:0x00bd A[Catch:{ IOException -> 0x00b9 }] */
        /* JADX WARNING: Removed duplicated region for block: B:73:0x00d0 A[SYNTHETIC] */
        public void run() {
            FileInputStream fileInputStream;
            Throwable th;
            FileOutputStream fileOutputStream;
            IOException e2;
            if (LocalMediaRecorder.this.canPause()) {
                this.mRunningLatch = new AtomicBoolean(true);
                byte[] bArr = new byte[1024];
                FileInputStream fileInputStream2 = null;
                r2 = null;
                FileOutputStream fileOutputStream2 = null;
                try {
                    fileInputStream = new FileInputStream(this.mSrcFd.getFileDescriptor());
                    try {
                        fileOutputStream = new FileOutputStream(LocalMediaRecorder.this.mDestFilePath, true);
                        try {
                            int headerLen = LocalMediaRecorder.this.getHeaderLen();
                            int i = 0;
                            while (headerLen - i > 0) {
                                i = fileInputStream.read(bArr, 0, headerLen);
                                headerLen -= i;
                            }
                            while (true) {
                                int read = fileInputStream.read(bArr);
                                if (read > 0) {
                                    fileOutputStream.write(bArr, 0, read);
                                } else {
                                    try {
                                        break;
                                    } catch (IOException e3) {
                                        Log.e(LocalMediaRecorder.TAG, "IOException", e3);
                                    }
                                }
                            }
                            fileInputStream.close();
                            fileOutputStream.close();
                            this.mSrcFd.close();
                            synchronized (this.mRunningLatch) {
                                this.mRunningLatch.set(false);
                                this.mRunningLatch.notify();
                            }
                        } catch (IOException e4) {
                            e2 = e4;
                            fileInputStream2 = fileInputStream;
                            try {
                                Log.e(LocalMediaRecorder.TAG, "IOException", e2);
                                if (fileInputStream2 != null) {
                                }
                                if (fileOutputStream != null) {
                                }
                                this.mSrcFd.close();
                                synchronized (this.mRunningLatch) {
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                fileInputStream = fileInputStream2;
                                fileOutputStream2 = fileOutputStream;
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException e5) {
                                        Log.e(LocalMediaRecorder.TAG, "IOException", e5);
                                        synchronized (this.mRunningLatch) {
                                        }
                                    }
                                }
                                if (fileOutputStream2 != null) {
                                    fileOutputStream2.close();
                                }
                                this.mSrcFd.close();
                                synchronized (this.mRunningLatch) {
                                    this.mRunningLatch.set(false);
                                    this.mRunningLatch.notify();
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            fileOutputStream2 = fileOutputStream;
                            if (fileInputStream != null) {
                            }
                            if (fileOutputStream2 != null) {
                            }
                            this.mSrcFd.close();
                            synchronized (this.mRunningLatch) {
                            }
                        }
                    } catch (IOException e6) {
                        e2 = e6;
                        fileOutputStream = null;
                        fileInputStream2 = fileInputStream;
                        Log.e(LocalMediaRecorder.TAG, "IOException", e2);
                        if (fileInputStream2 != null) {
                            try {
                                fileInputStream2.close();
                            } catch (IOException e7) {
                                Log.e(LocalMediaRecorder.TAG, "IOException", e7);
                                synchronized (this.mRunningLatch) {
                                    this.mRunningLatch.set(false);
                                    this.mRunningLatch.notify();
                                }
                                return;
                            }
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        this.mSrcFd.close();
                        synchronized (this.mRunningLatch) {
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        if (fileInputStream != null) {
                        }
                        if (fileOutputStream2 != null) {
                        }
                        this.mSrcFd.close();
                        synchronized (this.mRunningLatch) {
                        }
                    }
                } catch (IOException e8) {
                    e2 = e8;
                    fileOutputStream = null;
                    Log.e(LocalMediaRecorder.TAG, "IOException", e2);
                    if (fileInputStream2 != null) {
                    }
                    if (fileOutputStream != null) {
                    }
                    this.mSrcFd.close();
                    synchronized (this.mRunningLatch) {
                    }
                } catch (Throwable th5) {
                    th = th5;
                    fileInputStream = null;
                    if (fileInputStream != null) {
                    }
                    if (fileOutputStream2 != null) {
                    }
                    this.mSrcFd.close();
                    synchronized (this.mRunningLatch) {
                    }
                }
            }
        }
    }

    static {
        Method method;
        try {
            method = Method.of(MediaRecorder.class, "setParameter", "(Ljava/lang/String;)V");
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "Could not get method:setParameter", e2);
            method = null;
        }
        setParameter = method;
    }

    private void setExtraParametersLocal(String str) {
        if (setParameter != null && !TextUtils.isEmpty(str)) {
            setParameter.invoke(MediaRecorder.class, this.mMediaRecorder, str);
        }
    }

    private void waitingForAppendThread() {
        RecorderAppendThread recorderAppendThread = this.mRecorderAppendThread;
        if (recorderAppendThread != null) {
            synchronized (recorderAppendThread.mRunningLatch) {
                while (this.mRecorderAppendThread.mRunningLatch.get()) {
                    try {
                        this.mRecorderAppendThread.mRunningLatch.wait();
                    } catch (InterruptedException e2) {
                        Log.e(TAG, "InterruptedException", e2);
                    }
                }
            }
            this.mRecorderAppendThread = null;
        }
    }

    @Override // miui.media.Recorder
    public boolean canPause() {
        synchronized (this.mSyncer) {
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public int getHeaderLen() {
        return 0;
    }

    @Override // miui.media.Recorder
    public int getMaxAmplitude() {
        int maxAmplitude;
        synchronized (this.mSyncer) {
            maxAmplitude = this.mIsPaused ? 0 : this.mMediaRecorder.getMaxAmplitude();
        }
        return maxAmplitude;
    }

    @Override // miui.media.Recorder
    public long getRecordingTimeInMillis() {
        return isPaused() ? (long) this.mRecordingDuration : (SystemClock.elapsedRealtime() - this.mStartTime) + ((long) this.mRecordingDuration);
    }

    @Override // miui.media.Recorder
    public boolean isPaused() {
        boolean z;
        synchronized (this.mSyncer) {
            z = this.mIsPaused;
        }
        return z;
    }

    @Override // miui.media.Recorder
    public void pause() {
        synchronized (this.mSyncer) {
            this.mIsPaused = true;
            this.mMediaRecorder.stop();
            this.mMediaRecorder.release();
            this.mMediaRecorder = null;
            this.mRecordingDuration = (int) (((long) this.mRecordingDuration) + (SystemClock.elapsedRealtime() - this.mStartTime));
            if (this.mRecorderAppendWriteFd != null) {
                try {
                    this.mRecorderAppendWriteFd.close();
                    waitingForAppendThread();
                } catch (IOException e2) {
                    throw new IllegalStateException(e2);
                }
            }
            this.mSizeRecored = new File(this.mDestFilePath).length();
        }
    }

    @Override // miui.media.Recorder
    public void prepare() throws IOException {
        synchronized (this.mSyncer) {
            this.mMediaRecorder.prepare();
        }
    }

    @Override // miui.media.Recorder
    public void release() {
        synchronized (this.mSyncer) {
            resetParameters();
            if (this.mMediaRecorder != null) {
                this.mMediaRecorder.release();
                this.mMediaRecorder = null;
            }
        }
    }

    @Override // miui.media.Recorder
    public void reset() {
        synchronized (this.mSyncer) {
            resetParameters();
            this.mMediaRecorder.reset();
        }
    }

    /* access modifiers changed from: protected */
    public void resetParameters() {
        this.mDestFilePath = null;
        this.mExtraParameter = null;
        this.mAudioSource = -1;
        this.mSamplingRate = -1;
        this.mAudioEncoder = -1;
        this.mOutputFormat = -1;
        this.mBitRate = -1;
        this.mMaxSize = -1;
        this.mNumChannels = -1;
        this.mSizeRecored = 0;
        this.mOnErrorListener = null;
        this.mOnInfoListener = null;
        this.mRecorderOnErrorListener = null;
    }

    @Override // miui.media.Recorder
    public void resume() throws IllegalStateException, IOException {
        synchronized (this.mSyncer) {
            if (this.mIsPaused) {
                this.mMediaRecorder = new MediaRecorder();
                if (!TextUtils.isEmpty(this.mExtraParameter)) {
                    setExtraParameters(this.mExtraParameter);
                }
                if (this.mAudioSource != -1) {
                    this.mMediaRecorder.setAudioSource(this.mAudioSource);
                }
                if (this.mSamplingRate != -1) {
                    this.mMediaRecorder.setAudioSamplingRate(this.mSamplingRate);
                }
                if (this.mOutputFormat != -1) {
                    this.mMediaRecorder.setOutputFormat(this.mOutputFormat);
                }
                if (this.mAudioEncoder != -1) {
                    this.mMediaRecorder.setAudioEncoder(this.mAudioEncoder);
                }
                if (this.mBitRate != -1) {
                    this.mMediaRecorder.setAudioEncodingBitRate(this.mBitRate);
                }
                if (this.mNumChannels != -1) {
                    this.mMediaRecorder.setAudioChannels(this.mNumChannels);
                }
                if (this.mMaxSize != -1) {
                    this.mMediaRecorder.setMaxFileSize(this.mMaxSize - this.mSizeRecored);
                }
                if (this.mMaxDuration != -1) {
                    this.mMediaRecorder.setMaxDuration(this.mMaxDuration);
                }
                if (this.mOnErrorListener != null) {
                    this.mMediaRecorder.setOnErrorListener(this.mOnErrorListener);
                }
                if (this.mOnInfoListener != null) {
                    this.mMediaRecorder.setOnInfoListener(this.mOnInfoListener);
                }
                ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
                this.mRecorderAppendWriteFd = createPipe[1];
                this.mRecorderAppendThread = new RecorderAppendThread(createPipe[0]);
                this.mMediaRecorder.setOutputFile(this.mRecorderAppendWriteFd.getFileDescriptor());
                this.mRecorderAppendThread.start();
                this.mMediaRecorder.prepare();
                this.mMediaRecorder.start();
                this.mIsPaused = false;
                this.mStartTime = SystemClock.elapsedRealtime();
            }
        }
    }

    @Override // miui.media.Recorder
    public void setAudioChannel(int i) {
        synchronized (this.mSyncer) {
            if (i == 16) {
                this.mNumChannels = 1;
            } else {
                this.mNumChannels = 2;
            }
            this.mMediaRecorder.setAudioChannels(this.mNumChannels);
        }
    }

    @Override // miui.media.Recorder
    public void setAudioEncoder(int i) {
        synchronized (this.mSyncer) {
            this.mAudioEncoder = i;
            this.mMediaRecorder.setAudioEncoder(i);
        }
    }

    @Override // miui.media.Recorder
    public void setAudioEncodingBitRate(int i) {
        synchronized (this.mSyncer) {
            this.mBitRate = i;
            this.mMediaRecorder.setAudioEncodingBitRate(i);
        }
    }

    @Override // miui.media.Recorder
    public void setAudioSamplingRate(int i) {
        synchronized (this.mSyncer) {
            this.mSamplingRate = i;
            this.mMediaRecorder.setAudioSamplingRate(i);
        }
    }

    @Override // miui.media.Recorder
    public void setAudioSource(int i) {
        synchronized (this.mSyncer) {
            this.mAudioSource = i;
            this.mMediaRecorder.setAudioSource(i);
        }
    }

    @Override // miui.media.Recorder
    public void setExtraParameters(String str) {
        synchronized (this.mSyncer) {
            this.mExtraParameter = str;
            setExtraParametersLocal(str);
        }
    }

    @Override // miui.media.Recorder
    public void setMaxDuration(int i) {
        synchronized (this.mSyncer) {
            this.mMaxDuration = i;
            this.mMediaRecorder.setMaxDuration(i);
        }
    }

    @Override // miui.media.Recorder
    public void setMaxFileSize(long j) {
        synchronized (this.mSyncer) {
            this.mMaxSize = j;
            this.mMediaRecorder.setMaxFileSize(j);
        }
    }

    public void setOnErrorListener(MediaRecorder.OnErrorListener onErrorListener) {
        synchronized (this.mSyncer) {
            this.mOnErrorListener = onErrorListener;
            this.mMediaRecorder.setOnErrorListener(onErrorListener);
        }
    }

    @Override // miui.media.Recorder
    public void setOnErrorListener(Recorder.OnErrorListener onErrorListener) {
        this.mRecorderOnErrorListener = onErrorListener;
        setOnErrorListener(new MediaRecorder.OnErrorListener() {
            /* class miui.media.LocalMediaRecorder.AnonymousClass1 */

            public void onError(MediaRecorder mediaRecorder, int i, int i2) {
                LocalMediaRecorder.this.mRecorderOnErrorListener.onError(LocalMediaRecorder.this, RecorderUtils.convertErrorCode(i, true));
            }
        });
        setOnInfoListener(new MediaRecorder.OnInfoListener() {
            /* class miui.media.LocalMediaRecorder.AnonymousClass2 */

            public void onInfo(MediaRecorder mediaRecorder, int i, int i2) {
                LocalMediaRecorder.this.mRecorderOnErrorListener.onError(LocalMediaRecorder.this, RecorderUtils.convertErrorCode(i, true));
            }
        });
    }

    public void setOnInfoListener(MediaRecorder.OnInfoListener onInfoListener) {
        synchronized (this.mSyncer) {
            this.mOnInfoListener = onInfoListener;
            this.mMediaRecorder.setOnInfoListener(onInfoListener);
        }
    }

    @Override // miui.media.Recorder
    public void setOutputFile(String str) {
        synchronized (this.mSyncer) {
            this.mDestFilePath = str;
            this.mMediaRecorder.setOutputFile(str);
        }
    }

    @Override // miui.media.Recorder
    public void setOutputFormat(int i) {
        synchronized (this.mSyncer) {
            this.mOutputFormat = i;
            this.mMediaRecorder.setOutputFormat(i);
        }
    }

    @Override // miui.media.Recorder
    public void setQuality(int i) {
    }

    @Override // miui.media.Recorder
    public void start() {
        synchronized (this.mSyncer) {
            this.mMediaRecorder.start();
            this.mStartTime = SystemClock.elapsedRealtime();
            this.mRecordingDuration = 0;
        }
    }

    @Override // miui.media.Recorder
    public void stop() {
        synchronized (this.mSyncer) {
            resetParameters();
            if (this.mMediaRecorder != null) {
                this.mMediaRecorder.stop();
                if (this.mRecorderAppendWriteFd != null) {
                    try {
                        this.mRecorderAppendWriteFd.close();
                        waitingForAppendThread();
                    } catch (IOException e2) {
                        throw new IllegalStateException(e2);
                    }
                }
            }
            this.mIsPaused = false;
        }
    }
}
