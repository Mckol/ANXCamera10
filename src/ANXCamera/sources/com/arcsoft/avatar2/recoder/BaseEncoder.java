package com.arcsoft.avatar2.recoder;

import android.media.MediaCodec;
import android.os.Bundle;
import android.view.Surface;
import com.arcsoft.avatar2.util.CodecLog;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import miui.text.ExtraTextUtils;

public abstract class BaseEncoder {
    private static final String u = "Arc_BaseEncoder";
    private static final int v = 1000000;
    private static final int w = 1000000;
    private static final int x = 50564;
    private static final long y = 20000;
    private long A = 0;
    private long B = 0;
    private long C = 0;
    private long D = 0;
    private long E = 0;

    /* renamed from: a  reason: collision with root package name */
    protected boolean f254a;

    /* renamed from: b  reason: collision with root package name */
    protected boolean f255b;

    /* renamed from: c  reason: collision with root package name */
    protected boolean f256c;

    /* renamed from: d  reason: collision with root package name */
    protected boolean f257d;

    /* renamed from: e  reason: collision with root package name */
    protected volatile boolean f258e;
    protected Object f;
    protected volatile long g;
    protected MuxerWrapper h;
    protected MediaCodec i;
    protected int j;
    protected int k;
    protected boolean l;
    protected long m;
    protected Queue<Long> n;
    protected RecordingListener o;
    protected long p = 500;
    protected Lock q;
    protected Condition r;
    protected FrameQueue s;
    protected float t = 0.0f;
    private long z = 0;

    public BaseEncoder(MuxerWrapper muxerWrapper, Object obj, RecordingListener recordingListener) {
        this.o = recordingListener;
        this.h = muxerWrapper;
        this.f256c = false;
        this.f255b = false;
        this.f254a = true;
        this.f258e = false;
        this.f257d = false;
        this.k = -1;
        this.j = -1;
        this.f = obj;
        this.g = 0;
        this.l = false;
        this.m = 0;
        this.n = new LinkedList();
        CodecLog.d(u, "BaseEncoder constructor out");
    }

    /* access modifiers changed from: protected */
    public long a() {
        long nanoTime = System.nanoTime();
        long j2 = this.g;
        if (this.n.size() != 0) {
            j2 = this.n.poll().longValue();
        }
        long j3 = (nanoTime - j2) / 1000;
        CodecLog.d(u, "getPTSUs TotalPauseTime=" + (this.g / 1000));
        CodecLog.d(u, "getPTSUs preTime=" + this.A + " ,currentTime=" + (nanoTime / 1000) + " , result=" + j3);
        long j4 = this.A;
        if (j3 < j4) {
            long j5 = j4 - j3;
            return j5 < y ? (j4 + y) - j5 : j4 + y;
        } else if (0 == j4) {
            return j3;
        } else {
            long j6 = j3 - j4;
            return j6 < y ? (j3 + y) - j6 : j3;
        }
    }

    public void drain() {
        if (this.i == null) {
            CodecLog.e(u, "drain()->Encoder is not ready.");
            return;
        }
        String name = Thread.currentThread().getName();
        CodecLog.d(u, "drain()->Encoder one frame. threadName in=" + Thread.currentThread().getName());
        if (this.h == null) {
            CodecLog.e(u, "drain()->Muxer is not ready.");
            return;
        }
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int i2 = 0;
        while (true) {
            if (!this.f255b) {
                break;
            }
            int dequeueOutputBuffer = this.i.dequeueOutputBuffer(bufferInfo, this.p);
            if (-1 == dequeueOutputBuffer) {
                if (i2 >= 2) {
                    CodecLog.d(u, "drain()->Encoded frame is preparing, wait time out.");
                    break;
                }
                i2++;
                CodecLog.d(u, "drain()->Encoded frame is preparing, wait ... waitCount = " + i2);
            } else if (-2 == dequeueOutputBuffer) {
                if (!this.f256c) {
                    this.j = this.h.addTrack(this.i.getOutputFormat());
                    this.f256c = true;
                    if (!this.h.isStarted()) {
                        this.h.startMuxer();
                        CodecLog.d(u, "Muxer started: threadName =" + Thread.currentThread().getName());
                        synchronized (this.h) {
                            while (!this.h.isStarted() && !this.f257d) {
                                try {
                                    this.h.wait(100);
                                } catch (InterruptedException e2) {
                                    CodecLog.e(u, "drain()->Wait for muxer started, but be interrupted : " + e2.getMessage());
                                    this.f256c = false;
                                }
                            }
                            long a2 = a();
                            this.E = a2;
                            this.h.setStartTime(a2);
                            CodecLog.i(u, "Muxer start time =" + a2);
                        }
                    } else {
                        continue;
                    }
                } else {
                    CodecLog.e(u, "drain()->Encoder format change twice.");
                    throw new RuntimeException("Format only allow change once, but Encoder meet twice!");
                }
            } else if (dequeueOutputBuffer < 0) {
                CodecLog.i(u, "drain()->Encoder meet bufferStatus =" + dequeueOutputBuffer);
            } else {
                ByteBuffer outputBuffer = this.i.getOutputBuffer(dequeueOutputBuffer);
                if ((2 & bufferInfo.flags) != 0) {
                    bufferInfo.size = 0;
                    CodecLog.i(u, "drain()->Encoder meet bufferStatus : BUFFER_FLAG_CODEC_CONFIG ");
                }
                if (!this.f256c) {
                    CodecLog.e(u, "drain()->Encoder muxer has not started ");
                }
                if (bufferInfo.size != 0) {
                    outputBuffer.position(bufferInfo.offset);
                    outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                    CodecLog.d(u, "drain()->Encoder one frame. threadName=" + Thread.currentThread().getName() + " timestamp original buffer info =" + bufferInfo.presentationTimeUs);
                    bufferInfo.presentationTimeUs = a();
                    CodecLog.d(u, "time_diff _" + name + "= " + (bufferInfo.presentationTimeUs - this.z));
                    long j2 = bufferInfo.presentationTimeUs;
                    this.z = j2;
                    if (j2 - this.B >= ExtraTextUtils.MB) {
                        this.B = j2;
                        Bundle bundle = new Bundle();
                        bundle.putInt("request-sync", 1);
                        this.i.setParameters(bundle);
                    }
                    this.h.writeSampleData(this.j, outputBuffer, bufferInfo);
                    long j3 = bufferInfo.presentationTimeUs;
                    this.A = j3;
                    this.h.setCurrentTime(j3);
                    CodecLog.d(u, "drain()->Encoder one frame. threadName=" + Thread.currentThread().getName() + " timestamp=" + bufferInfo.presentationTimeUs);
                }
                this.i.releaseOutputBuffer(dequeueOutputBuffer, false);
                if ((bufferInfo.flags & 4) != 0) {
                    if (!this.f254a) {
                        CodecLog.e(u, "drain()->Encoder meet unexpected end of stream.");
                    } else {
                        CodecLog.d(u, "drain()->Encoder meet end of stream.");
                    }
                    this.f255b = false;
                }
            }
        }
        CodecLog.d(u, "drain()->Encoder one frame. threadName out=" + Thread.currentThread().getName());
    }

    public void encode(ByteBuffer byteBuffer, long j2) {
        if (this.i == null) {
            CodecLog.e(u, "encode()->Encoder is not ready.");
            return;
        }
        CodecLog.d(u, "encode()->Encoder one frame. threadName in=" + Thread.currentThread().getName());
        int i2 = 0;
        if (this.f255b) {
            while (this.f255b && !this.f254a) {
                int dequeueInputBuffer = this.i.dequeueInputBuffer(500);
                if (-1 == dequeueInputBuffer) {
                    if (i2 < 3) {
                        i2++;
                        CodecLog.d(u, "encode()->Encoder is busy, wait ... waitCount = " + i2);
                    } else {
                        CodecLog.d(u, "encode()->Encoder is busy, wait time out.");
                        return;
                    }
                } else if (dequeueInputBuffer >= 0) {
                    ByteBuffer inputBuffer = this.i.getInputBuffer(dequeueInputBuffer);
                    if (byteBuffer == null) {
                        this.f254a = true;
                        this.i.queueInputBuffer(dequeueInputBuffer, 0, 0, j2, 4);
                        CodecLog.d(u, "encode()->Encoder meets end of stream.");
                        return;
                    }
                    inputBuffer.clear();
                    inputBuffer.put(byteBuffer);
                    inputBuffer.flip();
                    this.i.queueInputBuffer(dequeueInputBuffer, 0, inputBuffer.remaining(), j2, 0);
                    CodecLog.d(u, "encode()->Encoder is fed a new frame.");
                    return;
                }
            }
        }
    }

    public String getEncoderType() {
        MediaCodec mediaCodec = this.i;
        return mediaCodec != null ? mediaCodec.getName().toLowerCase().contains("google") ? "Software Encoder" : "Hardware Encoder" : "No Encoder";
    }

    public FrameQueue getFrameQueue() {
        return null;
    }

    public Surface getInputSurface() {
        return null;
    }

    public void lock() {
        Lock lock = this.q;
        if (lock != null) {
            lock.lock();
        }
    }

    public abstract void notifyNewFrameAvailable();

    public void pauseRecording() {
        this.f258e = true;
        CodecLog.d(u, "Log_mIsRequestPause_Vaule_pauseRecording ->mIsRequestPause=" + this.f258e);
    }

    public abstract void prepare(boolean z2);

    public void release(boolean z2) {
        MediaCodec mediaCodec = this.i;
        if (mediaCodec != null) {
            try {
                mediaCodec.stop();
            } catch (Exception e2) {
                e2.printStackTrace();
                if (z2) {
                    RecordingListener recordingListener = this.o;
                    if (recordingListener != null) {
                        recordingListener.onRecordingListener(564, 0);
                    }
                } else {
                    RecordingListener recordingListener2 = this.o;
                    if (recordingListener2 != null) {
                        recordingListener2.onRecordingListener(548, 0);
                    }
                }
            }
            try {
                this.i.release();
            } catch (Exception e3) {
                e3.printStackTrace();
                if (z2) {
                    RecordingListener recordingListener3 = this.o;
                    if (recordingListener3 != null) {
                        recordingListener3.onRecordingListener(565, 0);
                    }
                } else {
                    RecordingListener recordingListener4 = this.o;
                    if (recordingListener4 != null) {
                        recordingListener4.onRecordingListener(549, 0);
                    }
                }
            }
            this.i = null;
        }
        MuxerWrapper muxerWrapper = this.h;
        if (muxerWrapper != null) {
            muxerWrapper.stopMuxer();
            this.h = null;
        }
        this.f255b = false;
        this.f258e = false;
        this.f257d = false;
        this.f254a = true;
        this.f = null;
        this.q = null;
        this.r = null;
    }

    public void resumeRecording() {
        this.f258e = false;
        CodecLog.d(u, "Log_mIsRequestPause_Vaule_resumeRecording ->mIsRequestPause=" + this.f258e);
    }

    public void setCropFactor(float f2) {
        this.t = f2;
    }

    public void setFrameQueue(FrameQueue frameQueue) {
        this.s = frameQueue;
    }

    public void sinalCondition() {
        Condition condition = this.r;
        if (condition != null) {
            condition.signalAll();
        }
    }

    public void startRecording() {
        if (this.f255b) {
            CodecLog.i(u, "startRecording()-> encoder is started, you can not start it again");
            return;
        }
        this.f255b = true;
        this.f257d = false;
        this.f254a = false;
        CodecLog.d(u, "startRecording()-> encoder is started.");
    }

    public void stopRecording() {
        if (this.f257d) {
            CodecLog.i(u, "stopRecording()-> stop encoder request command is received,you can not send stop command again.");
        } else {
            this.f257d = true;
        }
    }

    public void unLock() {
        Lock lock = this.q;
        if (lock != null) {
            lock.unlock();
        }
    }
}
