package com.arcsoft.avatar.recoder;

import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.view.Surface;
import com.xiaomi.asr.engine.record.AudioType;
import java.io.IOException;
import java.nio.ByteBuffer;

public class AudioEncoder extends BaseEncoder {
    private static final String t = "Arc_BaseEncoder";
    private static final String u = "Arc_Audio_Encoder";
    private final int A;
    private final String B;
    private final int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private int H;
    private int I;
    private int J;
    private Object K;
    private long L;
    private boolean M;
    public final String NAME = "ARC_S";
    private AudioRecord v;
    private Thread w;
    private final int x;
    private final int y;
    private final int z;

    public AudioEncoder(MuxerWrapper muxerWrapper, Object obj, RecordingListener recordingListener) {
        super(muxerWrapper, obj, recordingListener);
        int i = 44100;
        this.x = 44100;
        this.y = 16;
        this.z = 2;
        this.A = 1;
        this.B = "audio/mp4a-latm";
        this.C = 2000000;
        this.D = Build.VERSION.SDK_INT > 28 ? AudioType.Frequency.FREQ_22KHZ : i;
        this.E = 16;
        this.F = 2;
        this.G = 1;
        this.H = 2000000;
        this.K = new Object();
        this.f98c = false;
        this.L = 0;
    }

    private boolean b() {
        this.I = AudioRecord.getMinBufferSize(this.D, this.E, this.F);
        try {
            this.v = new AudioRecord(this.G, this.D, this.E, this.F, this.I);
        } catch (Exception e2) {
            RecordingListener recordingListener = this.o;
            if (recordingListener != null) {
                recordingListener.onRecordingListener(625, 0);
            }
            e2.printStackTrace();
        }
        AudioRecord audioRecord = this.v;
        if (audioRecord == null || 1 != audioRecord.getState()) {
            this.v = null;
            return false;
        }
        this.J = this.I;
        return true;
    }

    private boolean c() {
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", this.D, this.E);
        createAudioFormat.setInteger("aac-profile", 2);
        createAudioFormat.setInteger("channel-count", d());
        createAudioFormat.setInteger("bitrate", this.H);
        try {
            this.i = MediaCodec.createEncoderByType("audio/mp4a-latm");
        } catch (IOException e2) {
            this.i = null;
            RecordingListener recordingListener = this.o;
            if (recordingListener != null) {
                recordingListener.onRecordingListener(545, 0);
            }
            e2.printStackTrace();
        }
        MediaCodec mediaCodec = this.i;
        if (mediaCodec == null) {
            return false;
        }
        try {
            mediaCodec.configure(createAudioFormat, (Surface) null, (MediaCrypto) null, 1);
        } catch (Exception e3) {
            e3.printStackTrace();
            RecordingListener recordingListener2 = this.o;
            if (recordingListener2 != null) {
                recordingListener2.onRecordingListener(546, 0);
            }
        }
        return true;
    }

    private int d() {
        return 12 == this.F ? 2 : 1;
    }

    @Override // com.arcsoft.avatar.recoder.BaseEncoder
    public void notifyNewFrameAvailable() {
    }

    @Override // com.arcsoft.avatar.recoder.BaseEncoder
    public void prepare(boolean z2) {
        if (b() && c()) {
            this.M = true;
        }
    }

    @Override // com.arcsoft.avatar.recoder.BaseEncoder
    public void release(boolean z2) {
        Thread thread = this.w;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            } catch (Throwable th) {
                this.w = null;
                throw th;
            }
            this.w = null;
        }
        this.v.release();
        this.M = false;
        super.release(z2);
    }

    @Override // com.arcsoft.avatar.recoder.BaseEncoder
    public void startRecording() {
        if (this.M) {
            if (this.w == null) {
                super.startRecording();
                this.w = new Thread(u) {
                    /* class com.arcsoft.avatar.recoder.AudioEncoder.AnonymousClass1 */

                    public void run() {
                        super.run();
                        setName("ARC_S");
                        try {
                            AudioEncoder.this.v.startRecording();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            RecordingListener recordingListener = AudioEncoder.this.o;
                            if (recordingListener != null) {
                                recordingListener.onRecordingListener(626, 0);
                            }
                        }
                        try {
                            AudioEncoder.this.i.start();
                        } catch (Exception unused) {
                            RecordingListener recordingListener2 = AudioEncoder.this.o;
                            if (recordingListener2 != null) {
                                recordingListener2.onRecordingListener(547, 0);
                            }
                        }
                        long nanoTime = System.nanoTime();
                        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(AudioEncoder.this.J);
                        while (true) {
                            AudioEncoder audioEncoder = AudioEncoder.this;
                            if (!audioEncoder.f99d) {
                                if (audioEncoder.f100e) {
                                    long nanoTime2 = System.nanoTime();
                                    synchronized (AudioEncoder.this.f) {
                                        if (AudioEncoder.this.f100e) {
                                            try {
                                                AudioEncoder.this.v.stop();
                                            } catch (Exception e3) {
                                                e3.printStackTrace();
                                                if (AudioEncoder.this.o != null) {
                                                    AudioEncoder.this.o.onRecordingListener(627, 0);
                                                }
                                            }
                                            try {
                                                AudioEncoder.this.f.wait();
                                                try {
                                                    AudioEncoder.this.v.startRecording();
                                                } catch (Exception e4) {
                                                    e4.printStackTrace();
                                                    if (AudioEncoder.this.o != null) {
                                                        AudioEncoder.this.o.onRecordingListener(626, 0);
                                                    }
                                                }
                                                AudioEncoder.this.g += System.nanoTime() - nanoTime2;
                                                AudioEncoder.this.n.add(Long.valueOf(AudioEncoder.this.g));
                                            } catch (InterruptedException e5) {
                                                e5.printStackTrace();
                                            }
                                        }
                                    }
                                }
                                allocateDirect.clear();
                                if (AudioEncoder.this.v.read(allocateDirect, AudioEncoder.this.J) > 0) {
                                    AudioEncoder.this.encode(allocateDirect, ((System.nanoTime() - nanoTime) - AudioEncoder.this.g) / 1000);
                                    AudioEncoder.this.drain();
                                }
                            } else {
                                try {
                                    audioEncoder.v.stop();
                                } catch (Exception e6) {
                                    e6.printStackTrace();
                                    RecordingListener recordingListener3 = AudioEncoder.this.o;
                                    if (recordingListener3 != null) {
                                        recordingListener3.onRecordingListener(627, 0);
                                    }
                                }
                                AudioEncoder.this.encode(null, 0);
                                AudioEncoder.this.drain();
                                return;
                            }
                        }
                    }
                };
                this.w.start();
                return;
            }
            throw new RuntimeException("Audio encoder thread has been started already, can not start twice.");
        }
    }
}
