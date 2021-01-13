package com.xiaomi.camera.liveshot.writer;

import android.media.MediaCodec;
import android.media.MediaMuxer;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.xiaomi.camera.liveshot.LivePhotoResult;
import com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder;
import com.xiaomi.camera.liveshot.writer.SampleWriter;
import java.nio.ByteBuffer;

public class VideoSampleWriter extends SampleWriter {
    private static final boolean DEBUG = true;
    private static final long MIN_DURATION = 500000;
    private static final String TAG = "VideoSampleWriter";
    private final MediaMuxer mMediaMuxer;
    private final SampleWriter.StatusNotifier<Long> mVideoFirstKeyFrameArrivedNotifier;
    private final CircularMediaEncoder.Snapshot mVideoSnapshot;
    private final int mVideoTrackId;

    public VideoSampleWriter(MediaMuxer mediaMuxer, CircularMediaEncoder.Snapshot snapshot, int i, SampleWriter.StatusNotifier<Long> statusNotifier) {
        this.mMediaMuxer = mediaMuxer;
        this.mVideoSnapshot = snapshot;
        this.mVideoTrackId = i;
        this.mVideoFirstKeyFrameArrivedNotifier = statusNotifier;
    }

    /* access modifiers changed from: protected */
    @Override // com.xiaomi.camera.liveshot.writer.SampleWriter
    public void writeSample() {
        long j;
        long j2;
        boolean z;
        Log.d(TAG, "writeVideoSamples: E");
        CircularMediaEncoder.Snapshot snapshot = this.mVideoSnapshot;
        long j3 = snapshot.head;
        long j4 = snapshot.tail;
        long j5 = snapshot.time;
        int i = snapshot.filterId;
        Log.d(TAG, "writeVideoSamples: head timestamp: " + this.mVideoSnapshot.head + ":" + j3);
        Log.d(TAG, "writeVideoSamples: snap timestamp: " + this.mVideoSnapshot.time + ":" + j5);
        Log.d(TAG, "writeVideoSamples: tail timestamp: " + this.mVideoSnapshot.tail + ":" + j4);
        Log.d(TAG, "writeVideoSamples: curr filterId: " + this.mVideoSnapshot.filterId + ":" + i);
        long j6 = -1;
        boolean z2 = false;
        long j7 = 0;
        boolean z3 = false;
        boolean z4 = false;
        loop0:
        while (true) {
            boolean z5 = false;
            while (true) {
                if (z2) {
                    break loop0;
                }
                Log.d(TAG, "writeVideoSamples: take: E");
                try {
                    CircularMediaEncoder.Sample take = this.mVideoSnapshot.samples.take();
                    Log.d(TAG, "writeVideoSamples: take: X");
                    if (take == null) {
                        Log.e(TAG, "sample null return");
                        break loop0;
                    }
                    ByteBuffer byteBuffer = take.data;
                    MediaCodec.BufferInfo bufferInfo = take.info;
                    LivePhotoResult livePhotoResult = take.livePhotoResult;
                    z = z2;
                    j2 = j4;
                    Log.d(TAG, "writeVideoSamples: livePhotoResult " + livePhotoResult);
                    if (byteBuffer.limit() == 0 || (bufferInfo.flags & 4) != 0) {
                        Log.d(TAG, "writeVideoSamples: EOF");
                    } else if (j5 - bufferInfo.presentationTimeUs < MIN_DURATION || z3) {
                        if ((bufferInfo.flags & 1) != 0 || z4) {
                            j = j5;
                            long j8 = bufferInfo.presentationTimeUs;
                            if (j8 >= j3 && j6 < j8 - j7) {
                                if (!z4) {
                                    CircularMediaEncoder.Snapshot snapshot2 = this.mVideoSnapshot;
                                    snapshot2.offset = j8 - snapshot2.head;
                                    SampleWriter.StatusNotifier<Long> statusNotifier = this.mVideoFirstKeyFrameArrivedNotifier;
                                    if (statusNotifier != null) {
                                        statusNotifier.notify(Long.valueOf(snapshot2.offset));
                                    }
                                    Log.d(TAG, "writeVideoSamples: first video sample timestamp: " + j8);
                                    z4 = true;
                                } else {
                                    j8 = j7;
                                }
                                if (bufferInfo.presentationTimeUs >= j2) {
                                    Log.d(TAG, "writeVideoSamples: stop writing as reaching the ending timestamp");
                                    bufferInfo.flags = 4;
                                }
                                bufferInfo.presentationTimeUs -= j8;
                                this.mMediaMuxer.writeSampleData(this.mVideoTrackId, byteBuffer, bufferInfo);
                                long j9 = bufferInfo.presentationTimeUs;
                                Log.d(TAG, "writeVideoSamples: video sample timestamp: " + (bufferInfo.presentationTimeUs + j8));
                                j7 = j8;
                                j6 = j9;
                            }
                            z2 = byteBuffer.limit() == 0 || (bufferInfo.flags & 4) != 0;
                            j4 = j2;
                            j5 = j;
                        } else {
                            Log.d(TAG, "writeVideoSamples: drop non-key frame sample timestamp: " + bufferInfo.presentationTimeUs);
                            j = j5;
                            z2 = z;
                            j4 = j2;
                            j5 = j;
                        }
                    } else if (!z5) {
                        if (!Util.isLivePhotoStable(livePhotoResult, i)) {
                            Log.d(TAG, "writeVideoSamples: drop non-stable frame sample timestamp: " + bufferInfo.presentationTimeUs);
                            break;
                        }
                        Log.d(TAG, "writeVideoSamples: drop first stable frame sample timestamp: " + bufferInfo.presentationTimeUs);
                        z2 = z;
                        j4 = j2;
                        z3 = false;
                        z5 = true;
                    } else if (!Util.isLivePhotoStable(livePhotoResult, i)) {
                        Log.d(TAG, "writeVideoSamples: drop second non-stable frame sample timestamp: " + bufferInfo.presentationTimeUs);
                        break;
                    } else {
                        Log.d(TAG, "writeVideoSamples: drop first and second stable frame sample timestamp: " + bufferInfo.presentationTimeUs);
                        z2 = z;
                        j4 = j2;
                        z3 = true;
                        z5 = true;
                    }
                } catch (InterruptedException unused) {
                    j2 = j4;
                    j = j5;
                    z = z2;
                    Log.d(TAG, "writeVideoSamples: take: meet interrupted exception");
                }
            }
            z2 = z;
            j4 = j2;
            z3 = false;
        }
        Log.d(TAG, "writeVideoSamples: EOF");
        CircularMediaEncoder.Snapshot snapshot3 = this.mVideoSnapshot;
        snapshot3.time = Math.max(0L, snapshot3.time - j7);
        Log.d(TAG, "writeVideoSamples: cover frame timestamp: " + this.mVideoSnapshot.time);
        Log.d(TAG, "writeVideoSamples: X: duration: " + j6);
        Log.d(TAG, "writeVideoSamples: X: offset: " + this.mVideoSnapshot.offset);
    }
}
