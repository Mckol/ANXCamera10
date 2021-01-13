package com.arcsoft.avatar2.recoder;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.arcsoft.avatar2.util.CodecLog;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MuxerWrapper {

    /* renamed from: a  reason: collision with root package name */
    private static final String f272a = "Arc_MuxerWrapper";
    private static final String g = "video";
    private static final String h = ".mp4";

    /* renamed from: b  reason: collision with root package name */
    private int f273b;

    /* renamed from: c  reason: collision with root package name */
    private volatile int f274c;

    /* renamed from: d  reason: collision with root package name */
    private volatile boolean f275d;

    /* renamed from: e  reason: collision with root package name */
    private MediaMuxer f276e;
    private String f;
    private long i;
    private long j;
    private RecordingListener k;

    public MuxerWrapper(@Nullable FileDescriptor fileDescriptor, int i2, RecordingListener recordingListener) {
        this.f = "";
        this.i = 0;
        this.j = 0;
        this.k = null;
        this.k = recordingListener;
        this.f273b = 0;
        this.f274c = 0;
        this.f275d = false;
        try {
            this.f276e = new MediaMuxer(fileDescriptor, 0);
            this.f276e.setOrientationHint(i2);
            CodecLog.d(f272a, "MuxerWrapper()-> screenOrientation=" + i2);
        } catch (IOException e2) {
            CodecLog.e(f272a, "MuxerWrapper()-> create MediaMuxer failed.");
            e2.printStackTrace();
            this.f276e = null;
            RecordingListener recordingListener2 = this.k;
            if (recordingListener2 != null) {
                recordingListener2.onRecordingListener(609, 0);
            }
        }
    }

    public MuxerWrapper(@Nullable String str, int i2, int i3, RecordingListener recordingListener) {
        this(str, i3, recordingListener);
    }

    public MuxerWrapper(@Nullable String str, int i2, RecordingListener recordingListener) {
        this.f = "";
        this.i = 0;
        this.j = 0;
        this.k = null;
        this.k = recordingListener;
        this.f = str;
        this.f273b = 0;
        this.f274c = 0;
        this.f275d = false;
        CodecLog.d(f272a, "MuxerWrapper()-> video name=" + this.f);
        try {
            this.f276e = new MediaMuxer(this.f, 0);
            this.f276e.setOrientationHint(i2);
            CodecLog.d(f272a, "MuxerWrapper()-> screenOrientation=" + i2);
        } catch (IOException e2) {
            CodecLog.e(f272a, "MuxerWrapper()-> create MediaMuxer failed.");
            e2.printStackTrace();
            this.f276e = null;
            RecordingListener recordingListener2 = this.k;
            if (recordingListener2 != null) {
                recordingListener2.onRecordingListener(609, 0);
            }
        }
    }

    private void a() {
        File file = new File(this.f);
        if (!file.exists()) {
            File file2 = new File(file.getParent());
            if (!file2.exists()) {
                file2.mkdirs();
                return;
            }
            return;
        }
        file.delete();
    }

    public synchronized int addTrack(@NonNull MediaFormat mediaFormat) {
        if (this.f276e == null) {
            CodecLog.e(f272a, "writeSampleData()-> mMuxer must be created , but it's null until now.");
            return -1;
        }
        int i2 = 0;
        try {
            i2 = this.f276e.addTrack(mediaFormat);
        } catch (Exception e2) {
            if (this.k != null) {
                this.k.onRecordingListener(610, 0);
            }
            e2.printStackTrace();
        }
        return i2;
    }

    public long getSizeRecordFile() {
        if (TextUtils.isEmpty(this.f)) {
            return 0;
        }
        File file = new File(this.f);
        if (!file.exists() || !file.isFile()) {
            return 0;
        }
        return file.length();
    }

    public long getTimeElapse() {
        return this.j - this.i;
    }

    public boolean isStarted() {
        return this.f275d;
    }

    public void setCurrentTime(long j2) {
        this.j = j2;
    }

    public void setEncoderCount(int i2) {
        if (i2 <= 0 || i2 > 2) {
            throw new RuntimeException("The encoder count must between 1 and 2.");
        }
        this.f273b = i2;
    }

    public void setStartTime(long j2) {
        this.i = j2;
    }

    public synchronized void startMuxer() {
        if (this.f276e == null) {
            CodecLog.e(f272a, "startMuxer()-> mMuxer must be created , but it's null until now");
            return;
        }
        this.f274c++;
        if (this.f274c == this.f273b) {
            try {
                CodecLog.d(f272a, "startMuxer()-> Muxerstart");
                this.f276e.start();
            } catch (Exception e2) {
                CodecLog.e(f272a, "startMuxer()-> Muxer start failed");
                if (this.k != null) {
                    this.k.onRecordingListener(611, 0);
                }
                e2.printStackTrace();
            }
            this.f275d = true;
            notifyAll();
            CodecLog.d(f272a, "startMuxer()-> mMuxer is started");
        }
    }

    public synchronized void stopMuxer() {
        if (this.f276e != null) {
            this.f274c--;
            CodecLog.d(f272a, "stopMuxer()-> mEncoderCount=" + this.f274c + " ,maxCount=" + this.f273b);
            if (this.f274c == 0) {
                try {
                    this.f276e.stop();
                } catch (Exception e2) {
                    CodecLog.e(f272a, "stopMuxer()-> muxer.stop() error=" + e2.getMessage());
                    if (this.k != null) {
                        this.k.onRecordingListener(613, 0);
                    }
                }
                try {
                    this.f276e.release();
                } catch (Exception e3) {
                    CodecLog.e(f272a, "stopMuxer()-> muxer.release() error=" + e3.getMessage());
                    if (this.k != null) {
                        this.k.onRecordingListener(614, 0);
                    }
                }
                this.f276e = null;
                CodecLog.d(f272a, "stopMuxer()-> Muxer is released.");
            }
        }
    }

    public synchronized void writeSampleData(int i2, @NonNull ByteBuffer byteBuffer, @NonNull MediaCodec.BufferInfo bufferInfo) {
        if (this.f276e == null) {
            CodecLog.e(f272a, "writeSampleData()-> mMuxer must be created , but it's null until now.");
            return;
        }
        try {
            this.f276e.writeSampleData(i2, byteBuffer, bufferInfo);
            CodecLog.d(f272a, "writeSampleData()-> writeSampleData done");
        } catch (Exception e2) {
            CodecLog.e(f272a, "writeSampleData()-> writeSampleData failed");
            e2.printStackTrace();
            if (this.k != null) {
                this.k.onRecordingListener(612, 0);
            }
        }
    }
}
