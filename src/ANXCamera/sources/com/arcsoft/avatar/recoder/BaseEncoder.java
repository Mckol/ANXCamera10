package com.arcsoft.avatar.recoder;

import android.media.MediaCodec;
import android.os.Bundle;
import android.view.Surface;
import com.arcsoft.avatar.util.CodecLog;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import miui.text.ExtraTextUtils;

public abstract class BaseEncoder {
    private static final String t = "Arc_BaseEncoder";
    private static final int u = 1000000;
    private static final int v = 1000000;
    private static final int w = 50564;
    private static final long x = 20000;
    private long A = 0;
    private long B = 0;
    private long C = 0;
    private long D = 0;

    /* renamed from: a  reason: collision with root package name */
    protected boolean f96a;

    /* renamed from: b  reason: collision with root package name */
    protected boolean f97b;

    /* renamed from: c  reason: collision with root package name */
    protected boolean f98c;

    /* renamed from: d  reason: collision with root package name */
    protected boolean f99d;

    /* renamed from: e  reason: collision with root package name */
    protected volatile boolean f100e;
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
    private long y = 0;
    private long z = 0;

    public BaseEncoder(MuxerWrapper muxerWrapper, Object obj, RecordingListener recordingListener) {
        this.o = recordingListener;
        this.h = muxerWrapper;
        this.f98c = false;
        this.f97b = false;
        this.f96a = true;
        this.f100e = false;
        this.f99d = false;
        this.k = -1;
        this.j = -1;
        this.f = obj;
        this.g = 0;
        this.l = false;
        this.m = 0;
        this.n = new LinkedList();
        CodecLog.d(t, "BaseEncoder constructor out");
    }

    /* access modifiers changed from: protected */
    public long a() {
        long nanoTime = System.nanoTime();
        long j2 = this.g;
        if (this.n.size() != 0) {
            j2 = this.n.poll().longValue();
        }
        long j3 = (nanoTime - j2) / 1000;
        CodecLog.d(t, "getPTSUs TotalPauseTime=" + (this.g / 1000));
        CodecLog.d(t, "getPTSUs preTime=" + this.z + " ,currentTime=" + (nanoTime / 1000) + " , result=" + j3);
        long j4 = this.z;
        if (j3 < j4) {
            long j5 = j4 - j3;
            return j5 < x ? (j4 + x) - j5 : j4 + x;
        } else if (0 == j4) {
            return j3;
        } else {
            long j6 = j3 - j4;
            return j6 < x ? (j3 + x) - j6 : j3;
        }
    }

    public void drain() {
        if (this.i == null) {
            CodecLog.e(t, "drain()->Encoder is not ready.");
            return;
        }
        String name = Thread.currentThread().getName();
        CodecLog.d(t, "drain()->Encoder one frame. threadName in=" + Thread.currentThread().getName());
        if (this.h == null) {
            CodecLog.e(t, "drain()->Muxer is not ready.");
            return;
        }
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int i2 = 0;
        while (true) {
            if (!this.f97b) {
                break;
            }
            int dequeueOutputBuffer = this.i.dequeueOutputBuffer(bufferInfo, this.p);
            if (-1 == dequeueOutputBuffer) {
                if (i2 >= 2) {
                    CodecLog.d(t, "drain()->Encoded frame is preparing, wait time out.");
                    break;
                }
                i2++;
                CodecLog.d(t, "drain()->Encoded frame is preparing, wait ... waitCount = " + i2);
            } else if (-2 == dequeueOutputBuffer) {
                if (!this.f98c) {
                    this.j = this.h.addTrack(this.i.getOutputFormat());
                    this.f98c = true;
                    if (!this.h.isStarted()) {
                        this.h.startMuxer();
                        CodecLog.d(t, "Muxer started: threadName =" + Thread.currentThread().getName());
                        synchronized (this.h) {
                            while (!this.h.isStarted() && !this.f99d) {
                                try {
                                    this.h.wait(100);
                                } catch (InterruptedException e2) {
                                    CodecLog.e(t, "drain()->Wait for muxer started, but be interrupted : " + e2.getMessage());
                                    this.f98c = false;
                                }
                            }
                            long a2 = a();
                            this.D = a2;
                            this.h.setStartTime(a2);
                            CodecLog.i(t, "Muxer start time =" + a2);
                        }
                    } else {
                        continue;
                    }
                } else {
                    CodecLog.e(t, "drain()->Encoder format change twice.");
                    throw new RuntimeException("Format only allow change once, but Encoder meet twice!");
                }
            } else if (dequeueOutputBuffer < 0) {
                CodecLog.i(t, "drain()->Encoder meet bufferStatus =" + dequeueOutputBuffer);
            } else {
                ByteBuffer outputBuffer = this.i.getOutputBuffer(dequeueOutputBuffer);
                if ((2 & bufferInfo.flags) != 0) {
                    bufferInfo.size = 0;
                    CodecLog.i(t, "drain()->Encoder meet bufferStatus : BUFFER_FLAG_CODEC_CONFIG ");
                }
                if (!this.f98c) {
                    CodecLog.e(t, "drain()->Encoder muxer has not started ");
                }
                if (bufferInfo.size != 0) {
                    outputBuffer.position(bufferInfo.offset);
                    outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                    CodecLog.d(t, "drain()->Encoder one frame. threadName=" + Thread.currentThread().getName() + " timestamp original buffer info =" + bufferInfo.presentationTimeUs);
                    bufferInfo.presentationTimeUs = a();
                    CodecLog.d(t, "time_diff _" + name + "= " + (bufferInfo.presentationTimeUs - this.y));
                    long j2 = bufferInfo.presentationTimeUs;
                    this.y = j2;
                    if (j2 - this.A >= ExtraTextUtils.MB) {
                        this.A = j2;
                        Bundle bundle = new Bundle();
                        bundle.putInt("request-sync", 1);
                        this.i.setParameters(bundle);
                    }
                    this.h.writeSampleData(this.j, outputBuffer, bufferInfo);
                    long j3 = bufferInfo.presentationTimeUs;
                    this.z = j3;
                    this.h.setCurrentTime(j3);
                    CodecLog.d(t, "drain()->Encoder one frame. threadName=" + Thread.currentThread().getName() + " timestamp=" + bufferInfo.presentationTimeUs);
                }
                this.i.releaseOutputBuffer(dequeueOutputBuffer, false);
                if ((bufferInfo.flags & 4) != 0) {
                    if (!this.f96a) {
                        CodecLog.e(t, "drain()->Encoder meet unexpected end of stream.");
                    } else {
                        CodecLog.d(t, "drain()->Encoder meet end of stream.");
                    }
                    this.f97b = false;
                }
            }
        }
        CodecLog.d(t, "drain()->Encoder one frame. threadName out=" + Thread.currentThread().getName());
    }

    public void encode(ByteBuffer byteBuffer, long j2) {
        if (this.i == null) {
            CodecLog.e(t, "encode()->Encoder is not ready.");
            return;
        }
        CodecLog.d(t, "encode()->Encoder one frame. threadName in=" + Thread.currentThread().getName());
        int i2 = 0;
        if (this.f97b) {
            while (this.f97b && !this.f96a) {
                int dequeueInputBuffer = this.i.dequeueInputBuffer(500);
                if (-1 == dequeueInputBuffer) {
                    if (i2 < 3) {
                        i2++;
                        CodecLog.d(t, "encode()->Encoder is busy, wait ... waitCount = " + i2);
                    } else {
                        CodecLog.d(t, "encode()->Encoder is busy, wait time out.");
                        return;
                    }
                } else if (dequeueInputBuffer >= 0) {
                    ByteBuffer inputBuffer = this.i.getInputBuffer(dequeueInputBuffer);
                    if (byteBuffer == null) {
                        this.f96a = true;
                        this.i.queueInputBuffer(dequeueInputBuffer, 0, 0, j2, 4);
                        CodecLog.d(t, "encode()->Encoder meets end of stream.");
                        return;
                    }
                    inputBuffer.clear();
                    inputBuffer.put(byteBuffer);
                    inputBuffer.flip();
                    this.i.queueInputBuffer(dequeueInputBuffer, 0, inputBuffer.remaining(), j2, 0);
                    CodecLog.d(t, "encode()->Encoder is fed a new frame.");
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
        this.f100e = true;
        CodecLog.d(t, "Log_mIsRequestPause_Vaule_pauseRecording ->mIsRequestPause=" + this.f100e);
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
        this.f97b = false;
        this.f100e = false;
        this.f99d = false;
        this.f96a = true;
        this.f = null;
        this.q = null;
        this.r = null;
    }

    public void resumeRecording() {
        this.f100e = false;
        CodecLog.d(t, "Log_mIsRequestPause_Vaule_resumeRecording ->mIsRequestPause=" + this.f100e);
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
        if (this.f97b) {
            CodecLog.i(t, "startRecording()-> encoder is started, you can not start it again");
            return;
        }
        this.f97b = true;
        this.f99d = false;
        this.f96a = false;
        CodecLog.d(t, "startRecording()-> encoder is started.");
    }

    public void stopRecording() {
        if (this.f99d) {
            CodecLog.i(t, "stopRecording()-> stop encoder request command is received,you can not send stop command again.");
        } else {
            this.f99d = true;
        }
    }

    public void unLock() {
        Lock lock = this.q;
        if (lock != null) {
            lock.unlock();
        }
    }
}
