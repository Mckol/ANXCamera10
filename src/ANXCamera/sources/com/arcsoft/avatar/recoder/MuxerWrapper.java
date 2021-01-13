package com.arcsoft.avatar.recoder;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.arcsoft.avatar.util.CodecLog;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MuxerWrapper {

    /* renamed from: a  reason: collision with root package name */
    private static final String f114a = "Arc_MuxerWrapper";
    private static final String g = "video";
    private static final String h = ".mp4";

    /* renamed from: b  reason: collision with root package name */
    private int f115b;

    /* renamed from: c  reason: collision with root package name */
    private volatile int f116c;

    /* renamed from: d  reason: collision with root package name */
    private volatile boolean f117d;

    /* renamed from: e  reason: collision with root package name */
    private MediaMuxer f118e;
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
        this.f115b = 0;
        this.f116c = 0;
        this.f117d = false;
        try {
            this.f118e = new MediaMuxer(fileDescriptor, 0);
            this.f118e.setOrientationHint(i2);
            CodecLog.d(f114a, "MuxerWrapper()-> screenOrientation=" + i2);
        } catch (IOException e2) {
            CodecLog.e(f114a, "MuxerWrapper()-> create MediaMuxer failed.");
            e2.printStackTrace();
            this.f118e = null;
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
        this.f115b = 0;
        this.f116c = 0;
        this.f117d = false;
        CodecLog.d(f114a, "MuxerWrapper()-> video name=" + this.f);
        try {
            this.f118e = new MediaMuxer(this.f, 0);
            this.f118e.setOrientationHint(i2);
            CodecLog.d(f114a, "MuxerWrapper()-> screenOrientation=" + i2);
        } catch (IOException e2) {
            CodecLog.e(f114a, "MuxerWrapper()-> create MediaMuxer failed.");
            e2.printStackTrace();
            this.f118e = null;
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
        if (this.f118e == null) {
            CodecLog.e(f114a, "writeSampleData()-> mMuxer must be created , but it's null until now.");
            return -1;
        }
        int i2 = 0;
        try {
            i2 = this.f118e.addTrack(mediaFormat);
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
        return this.f117d;
    }

    public void setCurrentTime(long j2) {
        this.j = j2;
    }

    public void setEncoderCount(int i2) {
        if (i2 <= 0 || i2 > 2) {
            throw new RuntimeException("The encoder count must between 1 and 2.");
        }
        this.f115b = i2;
    }

    public void setStartTime(long j2) {
        this.i = j2;
    }

    public synchronized void startMuxer() {
        if (this.f118e == null) {
            CodecLog.e(f114a, "startMuxer()-> mMuxer must be created , but it's null until now");
            return;
        }
        this.f116c++;
        if (this.f116c == this.f115b) {
            try {
                CodecLog.d(f114a, "startMuxer()-> Muxerstart");
                this.f118e.start();
            } catch (Exception e2) {
                CodecLog.e(f114a, "startMuxer()-> Muxer start failed");
                if (this.k != null) {
                    this.k.onRecordingListener(611, 0);
                }
                e2.printStackTrace();
            }
            this.f117d = true;
            notifyAll();
            CodecLog.d(f114a, "startMuxer()-> mMuxer is started");
        }
    }

    public synchronized void stopMuxer() {
        if (this.f118e != null) {
            this.f116c--;
            CodecLog.d(f114a, "stopMuxer()-> mEncoderCount=" + this.f116c + " ,maxCount=" + this.f115b);
            if (this.f116c == 0) {
                try {
                    this.f118e.stop();
                } catch (Exception e2) {
                    CodecLog.e(f114a, "stopMuxer()-> muxer.stop() error=" + e2.getMessage());
                    if (this.k != null) {
                        this.k.onRecordingListener(613, 0);
                    }
                }
                try {
                    this.f118e.release();
                } catch (Exception e3) {
                    CodecLog.e(f114a, "stopMuxer()-> muxer.release() error=" + e3.getMessage());
                    if (this.k != null) {
                        this.k.onRecordingListener(614, 0);
                    }
                }
                this.f118e = null;
                CodecLog.d(f114a, "stopMuxer()-> Muxer is released.");
            }
        }
    }

    public synchronized void writeSampleData(int i2, @NonNull ByteBuffer byteBuffer, @NonNull MediaCodec.BufferInfo bufferInfo) {
        if (this.f118e == null) {
            CodecLog.e(f114a, "writeSampleData()-> mMuxer must be created , but it's null until now.");
            return;
        }
        try {
            this.f118e.writeSampleData(i2, byteBuffer, bufferInfo);
            CodecLog.d(f114a, "writeSampleData()-> writeSampleData done");
        } catch (Exception e2) {
            CodecLog.e(f114a, "writeSampleData()-> writeSampleData failed");
            e2.printStackTrace();
            if (this.k != null) {
                this.k.onRecordingListener(612, 0);
            }
        }
    }
}
