package d.a.a;

import android.media.AudioRecord;
import android.util.Log;
import com.ss.android.medialib.audio.AudioDataProcessThread;
import com.ss.android.medialib.common.LogUtil;
import com.ss.android.vesdk.VELogUtil;
import com.xiaomi.asr.engine.record.AudioType;

/* compiled from: BufferedAudioRecorder */
public class b {
    private static final String TAG = "BufferedAudioRecorder";
    protected static int channelConfigOffset = -1;
    protected static int[] channelConfigSuggested = {12, 16, 1};
    protected static int sampleRateOffset = -1;
    protected static int[] sampleRateSuggested = {44100, 8000, AudioType.Frequency.FREQ_11KHZ, 16000, AudioType.Frequency.FREQ_22KHZ};
    AudioRecord audio;
    int audioFormat = 2;
    int bufferSizeInBytes = 0;
    int channelConfig = -1;
    AudioDataProcessThread ej;
    a fj;
    boolean gj = false;
    int hj = 1;
    boolean isRecording = false;
    int sampleRateInHz = -1;

    /* access modifiers changed from: private */
    /* compiled from: BufferedAudioRecorder */
    public class a implements Runnable {
        boolean dj;
        private double speed;

        public a(double d2, boolean z) {
            this.speed = d2;
            this.dj = z;
        }

        public void run() {
            boolean z;
            b bVar = b.this;
            byte[] bArr = new byte[bVar.bufferSizeInBytes];
            bVar.gj = false;
            a aVar = bVar.fj;
            bVar.ej = new AudioDataProcessThread(aVar, aVar);
            b.this.ej.start();
            if (this.dj) {
                b bVar2 = b.this;
                bVar2.ej.startFeeding(bVar2.sampleRateInHz, this.speed);
            }
            try {
                if (b.this.audio != null) {
                    b.this.audio.startRecording();
                    if (b.this.audio == null || b.this.audio.getRecordingState() == 3) {
                        z = false;
                    } else {
                        b.this.fj.recordStatus(false);
                        z = true;
                    }
                    boolean z2 = z;
                    int i = 0;
                    while (true) {
                        b bVar3 = b.this;
                        if (bVar3.isRecording) {
                            AudioRecord audioRecord = bVar3.audio;
                            if (audioRecord != null) {
                                i = audioRecord.read(bArr, 0, bVar3.bufferSizeInBytes);
                            }
                            if (-3 == i) {
                                VELogUtil.e(b.TAG, "bad audio buffer len " + i);
                            } else if (i > 0) {
                                try {
                                    if (b.this.isRecording) {
                                        b.this.fj.addPCMData(bArr, i);
                                    }
                                    if (b.this.ej.isProcessing() && !b.this.gj) {
                                        b.this.ej.feed(bArr, i);
                                    }
                                } catch (Exception unused) {
                                }
                            } else {
                                AudioRecord audioRecord2 = b.this.audio;
                                if (!(audioRecord2 == null || audioRecord2.getRecordingState() == 3 || z2)) {
                                    b.this.fj.recordStatus(false);
                                    z2 = true;
                                }
                                Thread.sleep(50);
                            }
                        } else {
                            return;
                        }
                    }
                }
            } catch (Exception e2) {
                try {
                    if (b.this.audio != null) {
                        b.this.audio.release();
                    }
                } catch (Exception unused2) {
                }
                b.this.audio = null;
                VELogUtil.e(b.TAG, "audio recording failed!" + e2);
            }
        }
    }

    public b(a aVar) {
        this.fj = aVar;
    }

    public int C(int i) {
        return 16 == i ? 1 : 2;
    }

    public void Eo() {
        synchronized (this) {
            this.gj = true;
        }
    }

    public void a(double d2, boolean z) {
        VELogUtil.i(TAG, "startRecording() called");
        synchronized (this) {
            if (this.isRecording) {
                VELogUtil.w(TAG, "recorder is started");
                if (z) {
                    a(d2);
                }
                return;
            }
            if (this.audio == null) {
                init(this.hj);
                if (this.audio == null) {
                    VELogUtil.e(TAG, "recorder is null");
                    return;
                }
            }
            this.isRecording = true;
            try {
                new Thread(new a(d2, z)).start();
            } catch (OutOfMemoryError unused) {
                Runtime.getRuntime().gc();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException unused2) {
                }
                System.runFinalization();
                new Thread(new a(d2, z)).start();
            }
        }
    }

    public boolean a(double d2) {
        AudioDataProcessThread audioDataProcessThread;
        VELogUtil.i(TAG, "startFeeding() called with: speed = [" + d2 + "]");
        if (!this.isRecording || (audioDataProcessThread = this.ej) == null) {
            VELogUtil.w(TAG, "startFeeding 录音未启动，将先启动startRecording");
            a(d2, true);
            return true;
        } else if (audioDataProcessThread.isProcessing()) {
            VELogUtil.w(TAG, "startFeeding 失败，已经调用过一次了");
            return false;
        } else {
            this.gj = false;
            this.ej.startFeeding(this.sampleRateInHz, d2);
            return true;
        }
    }

    public void discard() {
        AudioDataProcessThread audioDataProcessThread = this.ej;
        if (audioDataProcessThread != null) {
            audioDataProcessThread.discard();
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        AudioRecord audioRecord = this.audio;
        if (audioRecord != null) {
            try {
                if (audioRecord.getState() != 0) {
                    this.audio.stop();
                }
                this.audio.release();
            } catch (Exception unused) {
            }
            this.audio = null;
        }
        super.finalize();
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x0168  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x017f  */
    public void init(int i) {
        int i2;
        String str;
        int i3;
        int i4;
        int i5;
        int[] iArr;
        int i6;
        int i7;
        Exception e2;
        String str2 = " ";
        this.hj = i;
        if (this.audio != null) {
            VELogUtil.e(TAG, "second time audio init(), skip");
            return;
        }
        int i8 = -1;
        try {
            if (!(channelConfigOffset == -1 || sampleRateOffset == -1)) {
                this.channelConfig = channelConfigSuggested[channelConfigOffset];
                this.sampleRateInHz = sampleRateSuggested[sampleRateOffset];
                this.bufferSizeInBytes = AudioRecord.getMinBufferSize(this.sampleRateInHz, this.channelConfig, this.audioFormat);
                this.audio = new AudioRecord(i, this.sampleRateInHz, this.channelConfig, this.audioFormat, this.bufferSizeInBytes);
            }
        } catch (Exception e3) {
            VELogUtil.e(TAG, "使用预设配置" + channelConfigOffset + "," + sampleRateOffset + "实例化audio recorder失败，重新测试配置。" + e3);
            this.fj.lackPermission();
        }
        int i9 = 1;
        if (this.audio == null) {
            channelConfigOffset = -1;
            int[] iArr2 = channelConfigSuggested;
            int length = iArr2.length;
            int i10 = 0;
            boolean z = false;
            while (true) {
                if (i10 >= length) {
                    break;
                }
                this.channelConfig = iArr2[i10];
                channelConfigOffset += i9;
                sampleRateOffset = i8;
                int[] iArr3 = sampleRateSuggested;
                int length2 = iArr3.length;
                int i11 = 0;
                while (true) {
                    if (i11 >= length2) {
                        str = str2;
                        i3 = i10;
                        i2 = i9;
                        break;
                    }
                    int i12 = iArr3[i11];
                    sampleRateOffset += i9;
                    try {
                        this.bufferSizeInBytes = AudioRecord.getMinBufferSize(i12, this.channelConfig, this.audioFormat);
                        VELogUtil.e(TAG, "试用hz " + i12 + str2 + this.channelConfig + str2 + this.audioFormat);
                        if (this.bufferSizeInBytes > 0) {
                            this.sampleRateInHz = i12;
                            str = str2;
                            i7 = i12;
                            i4 = i11;
                            i5 = length2;
                            iArr = iArr3;
                            i3 = i10;
                            try {
                                this.audio = new AudioRecord(i, this.sampleRateInHz, this.channelConfig, this.audioFormat, this.bufferSizeInBytes);
                                i2 = 1;
                                z = true;
                                break;
                            } catch (Exception e4) {
                                e2 = e4;
                                this.sampleRateInHz = 0;
                                this.audio = null;
                                VELogUtil.e(TAG, "apply audio record sample rate " + i7 + " failed: " + e2.getMessage());
                                i6 = 1;
                                sampleRateOffset = sampleRateOffset + 1;
                                i11 = i4 + 1;
                                i9 = i6;
                                iArr3 = iArr;
                                i10 = i3;
                                length2 = i5;
                                str2 = str;
                            }
                        } else {
                            str = str2;
                            i4 = i11;
                            i5 = length2;
                            iArr = iArr3;
                            i3 = i10;
                            sampleRateOffset++;
                            i6 = 1;
                            i11 = i4 + 1;
                            i9 = i6;
                            iArr3 = iArr;
                            i10 = i3;
                            length2 = i5;
                            str2 = str;
                        }
                    } catch (Exception e5) {
                        e2 = e5;
                        str = str2;
                        i7 = i12;
                        i4 = i11;
                        i5 = length2;
                        iArr = iArr3;
                        i3 = i10;
                        this.sampleRateInHz = 0;
                        this.audio = null;
                        VELogUtil.e(TAG, "apply audio record sample rate " + i7 + " failed: " + e2.getMessage());
                        i6 = 1;
                        sampleRateOffset = sampleRateOffset + 1;
                        i11 = i4 + 1;
                        i9 = i6;
                        iArr3 = iArr;
                        i10 = i3;
                        length2 = i5;
                        str2 = str;
                    }
                }
                if (z) {
                    break;
                }
                i10 = i3 + 1;
                i9 = i2;
                str2 = str;
                i8 = -1;
            }
            if (this.sampleRateInHz > 0) {
                VELogUtil.e(TAG, "!Init audio recorder failed, hz " + this.sampleRateInHz);
                return;
            }
            if (this.channelConfig != 16) {
                i2 = 2;
            }
            this.fj.initAudioConfig(this.sampleRateInHz, i2);
            VELogUtil.e(TAG, "Init audio recorder succeed, apply audio record sample rate " + this.sampleRateInHz + " buffer " + this.bufferSizeInBytes + " state " + this.audio.getState());
            return;
        }
        i2 = i9;
        if (this.sampleRateInHz > 0) {
        }
    }

    public synchronized boolean isProcessing() {
        return this.ej != null && this.ej.isProcessing();
    }

    public boolean stopFeeding() {
        AudioDataProcessThread audioDataProcessThread;
        VELogUtil.i(TAG, "stopFeeding() called");
        if (this.isRecording && this.audio == null) {
            VELogUtil.e(TAG, "stopFeeding: 状态异常，重置状态");
            this.isRecording = false;
            this.gj = true;
            AudioDataProcessThread audioDataProcessThread2 = this.ej;
            if (audioDataProcessThread2 != null) {
                audioDataProcessThread2.stop();
            }
            return false;
        } else if (!this.isRecording || (audioDataProcessThread = this.ej) == null) {
            VELogUtil.e(TAG, "stopFeeding 失败，请先调用startRecording");
            return false;
        } else if (!audioDataProcessThread.isProcessing()) {
            VELogUtil.e(TAG, "stopFeeding 失败，请先startFeeding再stopFeeding");
            return false;
        } else {
            this.ej.stopFeeding();
            return true;
        }
    }

    public boolean stopRecording() {
        synchronized (this) {
            Log.d(TAG, "stopRecording() called");
            if (!this.isRecording) {
                return false;
            }
            this.isRecording = false;
            if (this.audio == null) {
                LogUtil.e(TAG, "未启动音频模块但调用stopRecording");
            } else if (this.audio.getState() != 0) {
                this.audio.stop();
            }
            if (this.ej != null) {
                this.ej.stop();
            }
            return true;
        }
    }

    public void unInit() {
        if (this.isRecording) {
            stopRecording();
        }
        AudioRecord audioRecord = this.audio;
        if (audioRecord != null) {
            try {
                if (audioRecord.getState() != 0) {
                    this.audio.stop();
                }
                this.audio.release();
            } catch (Exception unused) {
            }
            this.audio = null;
        }
        VELogUtil.i(TAG, "unInit()");
    }

    public void waitUtilAudioProcessDone() {
        AudioDataProcessThread audioDataProcessThread = this.ej;
        if (audioDataProcessThread != null) {
            audioDataProcessThread.waitUtilAudioProcessDone();
        }
    }
}
