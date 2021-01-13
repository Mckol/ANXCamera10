package com.arcsoft.avatar2.recoder;

import android.graphics.Bitmap;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.view.Surface;
import com.android.camera.storage.Storage;
import com.arcsoft.avatar2.gl.EGLWrapper;
import com.arcsoft.avatar2.gl.GLFramebuffer;
import com.arcsoft.avatar2.gl.GLRender;
import com.arcsoft.avatar2.util.CodecLog;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.locks.ReentrantLock;

public class VideoEncoder extends BaseEncoder {
    public static final String ENCODER_THREAD_NAME = "Arc_Video_Encoder";
    private static String F = "video/hevc";
    public static final String NAME = "ARC_V";
    private static final String v = "Arc_VideoEncoder";
    private static final long w = 1000000000;
    private static final int x = 10000000;
    private static final int y = 30;
    private static final int z = 10;
    private MediaFormat A;
    private int B;
    private int C;
    private boolean D;
    private int E;
    private Surface G;
    private Thread H;
    private EGLWrapper I;
    private EGLContext J = EGL14.EGL_NO_CONTEXT;
    private GLRender K;
    private int L;
    protected long u;

    public class SaveThread extends Thread {

        /* renamed from: b  reason: collision with root package name */
        private ByteBuffer f279b;

        public SaveThread(ByteBuffer byteBuffer) {
            this.f279b = byteBuffer;
        }

        public void run() {
            super.run();
            Bitmap createBitmap = Bitmap.createBitmap(VideoEncoder.this.B, VideoEncoder.this.C, Bitmap.Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(this.f279b);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream("/sdcard/Pictures/_" + System.currentTimeMillis() + Storage.JPEG_SUFFIX);
                createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
                createBitmap.recycle();
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    public VideoEncoder(MuxerWrapper muxerWrapper, int i, int i2, Object obj, RecordingListener recordingListener, EGLContext eGLContext, int i3, String str) {
        super(muxerWrapper, obj, recordingListener);
        this.B = i;
        this.C = i2;
        this.H = null;
        this.L = i3;
        this.J = eGLContext;
        F = str;
        prepare(true);
        b();
        this.q = new ReentrantLock();
        this.r = this.q.newCondition();
        CodecLog.d(v, "VideoEncoder constructor mCustomerBitRate = " + this.L);
        CodecLog.d(v, "VideoEncoder constructor mWidth = " + i + " ,mHeight = " + i2);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void a(float f) {
        this.K = new GLRender(this.B, this.C, this.E, true);
        this.K.initRender(false, f);
        CodecLog.d(v, "VideoEncoder initGL glError = " + GLES20.glGetError());
    }

    private void a(boolean z2) {
        CodecLog.d(v, "initVideoEncoder()->in");
        this.A = MediaFormat.createVideoFormat(F, this.B, this.C);
        this.A.setInteger("color-format", 2130708361);
        this.A.setInteger("bitrate", this.L);
        this.A.setInteger("frame-rate", 30);
        this.A.setInteger("i-frame-interval", 10);
        try {
            this.i = MediaCodec.createEncoderByType(F);
            CodecLog.i(v, "initVideoEncoder(): selected_codec_name = " + this.i.getName());
        } catch (IOException e2) {
            CodecLog.e(v, "initVideoEncoder()->createEncoderByType failed.");
            e2.printStackTrace();
            RecordingListener recordingListener = this.o;
            if (recordingListener != null) {
                recordingListener.onRecordingListener(561, 0);
            }
        }
        try {
            this.i.configure(this.A, (Surface) null, (MediaCrypto) null, 1);
        } catch (Exception e3) {
            CodecLog.e(v, "initVideoEncoder()->configure failed.");
            e3.printStackTrace();
            RecordingListener recordingListener2 = this.o;
            if (recordingListener2 != null) {
                recordingListener2.onRecordingListener(562, 0);
            }
        }
        if (z2) {
            try {
                this.G = this.i.createInputSurface();
            } catch (Exception e4) {
                CodecLog.e(v, "initVideoEncoder()->createInputSurface failed.");
                e4.printStackTrace();
                RecordingListener recordingListener3 = this.o;
                if (recordingListener3 != null) {
                    recordingListener3.onRecordingListener(562, 0);
                }
            }
        } else {
            this.G = null;
        }
        CodecLog.d(v, "initVideoEncoder()->out");
    }

    private void b() {
        this.I = new EGLWrapper(getInputSurface(), this.J);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void c() {
        this.K.unInitRender();
        this.K = null;
    }

    private void d() {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(this.B * this.C * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        GLES20.glReadPixels(0, 0, this.B, this.C, 6408, 5121, allocateDirect);
        new SaveThread(allocateDirect).start();
    }

    @Override // com.arcsoft.avatar2.recoder.BaseEncoder
    public Surface getInputSurface() {
        return this.i != null ? this.G : super.getInputSurface();
    }

    @Override // com.arcsoft.avatar2.recoder.BaseEncoder
    public void notifyNewFrameAvailable() {
    }

    @Override // com.arcsoft.avatar2.recoder.BaseEncoder
    public void pauseRecording() {
        if (!this.f258e) {
            this.f258e = true;
            this.u = System.nanoTime();
        }
    }

    @Override // com.arcsoft.avatar2.recoder.BaseEncoder
    public void prepare(boolean z2) {
        a(z2);
        if (this.i == null) {
            throw new RuntimeException("Init video encoder is failed.");
        }
    }

    @Override // com.arcsoft.avatar2.recoder.BaseEncoder
    public void release(boolean z2) {
        try {
            this.q.lock();
        } catch (Exception e2) {
            CodecLog.e(v, "release()-> meet error when get lock : " + e2.getMessage());
        } catch (Throwable th) {
            sinalCondition();
            this.q.unlock();
            throw th;
        }
        sinalCondition();
        this.q.unlock();
        Thread thread = this.H;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e3) {
                CodecLog.d(v, "Encoder Thread has been Interrupted, errors may be occurred.");
                e3.printStackTrace();
            } catch (Throwable th2) {
                this.H = null;
                throw th2;
            }
            this.H = null;
        }
        EGLWrapper eGLWrapper = this.I;
        if (eGLWrapper != null) {
            eGLWrapper.release();
            this.I = null;
        }
        this.J = EGL14.EGL_NO_CONTEXT;
        CodecLog.d(v, "VideoEncoder release() encoder thread exit. threadName =" + "Arc_Video_Encoder");
        this.G = null;
        this.q = null;
        this.r = null;
        this.s = null;
        super.release(z2);
    }

    @Override // com.arcsoft.avatar2.recoder.BaseEncoder
    public void resumeRecording() {
        if (this.f258e) {
            this.f258e = false;
            this.g += System.nanoTime() - this.u;
            this.n.add(Long.valueOf(this.g));
        }
    }

    @Override // com.arcsoft.avatar2.recoder.BaseEncoder
    public void startRecording() {
        if (this.H == null) {
            super.startRecording();
            this.H = new Thread("Arc_Video_Encoder") {
                /* class com.arcsoft.avatar2.recoder.VideoEncoder.AnonymousClass1 */

                public void run() {
                    super.run();
                    setName("ARC_V");
                    try {
                        VideoEncoder.this.i.start();
                        VideoEncoder.this.I.makeCurrent();
                        VideoEncoder videoEncoder = VideoEncoder.this;
                        videoEncoder.a((VideoEncoder) videoEncoder.t);
                        while (true) {
                            VideoEncoder videoEncoder2 = VideoEncoder.this;
                            if (!videoEncoder2.f257d) {
                                FrameItem frameItem = null;
                                try {
                                    videoEncoder2.lock();
                                    while (VideoEncoder.this.s.queueSize() == 0 && !VideoEncoder.this.f257d) {
                                        try {
                                            VideoEncoder.this.r.await();
                                        } catch (InterruptedException e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                    frameItem = VideoEncoder.this.s.getFrameForConsumer();
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                } catch (Throwable th) {
                                    VideoEncoder.this.unLock();
                                    throw th;
                                }
                                VideoEncoder.this.unLock();
                                if (frameItem != null) {
                                    GLFramebuffer gLFramebuffer = frameItem.mFramebuffer;
                                    VideoEncoder.this.drain();
                                    CodecLog.d(VideoEncoder.v, "VideoEncoder frame_item_index = " + frameItem.mFrameIndex);
                                    long j = frameItem.f259a;
                                    if (0 != j) {
                                        GLES30.glWaitSync(j, 0, -1);
                                    }
                                    VideoEncoder.this.K.renderWithTextureId(gLFramebuffer.getTextureId());
                                    try {
                                        VideoEncoder.this.lock();
                                        VideoEncoder.this.s.addEmptyFrameForConsumer();
                                    } catch (Exception e4) {
                                        e4.printStackTrace();
                                        CodecLog.e(VideoEncoder.v, "VideoEncoder meet exception when add item : " + e4.getMessage());
                                    } catch (Throwable th2) {
                                        VideoEncoder.this.unLock();
                                        throw th2;
                                    }
                                    VideoEncoder.this.unLock();
                                    VideoEncoder.this.I.swapBuffers();
                                }
                            } else {
                                videoEncoder2.f254a = true;
                                videoEncoder2.i.signalEndOfInputStream();
                                VideoEncoder.this.drain();
                                VideoEncoder.this.c();
                                VideoEncoder.this.I.makeUnCurrent();
                                return;
                            }
                        }
                    } catch (Exception e5) {
                        e5.printStackTrace();
                        RecordingListener recordingListener = VideoEncoder.this.o;
                        if (recordingListener != null) {
                            recordingListener.onRecordingListener(563, 0);
                        }
                    }
                }
            };
            this.H.start();
            CodecLog.d(v, "VideoEncoder is started.");
            return;
        }
        throw new RuntimeException("Video encoder thread has been started already, can not start twice.");
    }

    @Override // com.arcsoft.avatar2.recoder.BaseEncoder
    public void stopRecording() {
        super.stopRecording();
        try {
            this.q.lock();
        } catch (Exception e2) {
            CodecLog.e(v, "stopRecording()-> meet error when get lock : " + e2.getMessage());
        } catch (Throwable th) {
            sinalCondition();
            this.q.unlock();
            throw th;
        }
        sinalCondition();
        this.q.unlock();
    }
}
