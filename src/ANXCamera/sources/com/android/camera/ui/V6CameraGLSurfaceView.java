package com.android.camera.ui;

import android.content.Context;
import android.opengl.EGL14;
import android.opengl.GLSurfaceView;
import android.os.Process;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.android.camera.Camera;
import com.android.camera.SurfaceTextureScreenNail;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.gallery3d.exif.ExifInterface;
import com.android.gallery3d.ui.BasicTexture;
import com.android.gallery3d.ui.GLCanvasImpl;
import com.android.gallery3d.ui.UploadedTexture;
import com.android.gallery3d.ui.Utils;
import com.mi.config.b;
import com.xiaomi.mediaprocess.OpenGlRender;
import java.util.concurrent.locks.ReentrantLock;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import miui.reflect.Field;

public class V6CameraGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private static final boolean DEBUG_FPS = false;
    private static final boolean DEBUG_INVALIDATE = false;
    private static final String TAG = "GLRootView";
    private final Camera mActivity;
    private GLCanvasImpl mCanvas;
    private boolean mCatchUnTapableEvent;
    private EGLContext mEGLContext10;
    private android.opengl.EGLContext mEGLContext14;
    private final MyEGLConfigChooser mEglConfigChooser;
    private int mFrameCount;
    private long mFrameCountingStart;
    private GL11 mGL;
    protected int mHeight;
    private OpenGlRender mOpenGlRender;
    private final ReentrantLock mRenderLock;
    private volatile boolean mRenderRequested;
    protected int mWidth;

    private class MyEGLConfigChooser implements GLSurfaceView.EGLConfigChooser {
        private final int[] ATTR_ID;
        private final String[] ATTR_NAME;
        private final int[] mConfigSpec;

        private MyEGLConfigChooser() {
            int[] iArr = new int[13];
            iArr[0] = 12324;
            iArr[1] = b.ke() ? 8 : 5;
            iArr[2] = 12323;
            iArr[3] = b.ke() ? 8 : 6;
            iArr[4] = 12322;
            iArr[5] = b.ke() ? 8 : 5;
            iArr[6] = 12325;
            iArr[7] = 0;
            iArr[8] = 12321;
            iArr[9] = 0;
            iArr[10] = 12352;
            iArr[11] = 4;
            iArr[12] = 12344;
            this.mConfigSpec = iArr;
            this.ATTR_ID = new int[]{12324, 12323, 12322, 12321, 12325, 12326, 12328, 12327};
            this.ATTR_NAME = new String[]{"R", "G", Field.BYTE_SIGNATURE_PRIMITIVE, ExifInterface.GpsStatus.IN_PROGRESS, Field.DOUBLE_SIGNATURE_PRIMITIVE, "S", "ID", "CAVEAT"};
        }

        private EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr) {
            int[] iArr = new int[1];
            int length = eGLConfigArr.length;
            int i = Integer.MAX_VALUE;
            EGLConfig eGLConfig = null;
            for (int i2 = 0; i2 < length; i2++) {
                if (!egl10.eglGetConfigAttrib(eGLDisplay, eGLConfigArr[i2], 12324, iArr) || iArr[0] != 8) {
                    if (!egl10.eglGetConfigAttrib(eGLDisplay, eGLConfigArr[i2], 12326, iArr)) {
                        throw new RuntimeException("eglGetConfigAttrib error: " + egl10.eglGetError());
                    } else if (iArr[0] != 0 && iArr[0] < i) {
                        i = iArr[0];
                        eGLConfig = eGLConfigArr[i2];
                    }
                }
            }
            if (eGLConfig == null) {
                eGLConfig = eGLConfigArr[0];
            }
            egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, 12326, iArr);
            logConfig(egl10, eGLDisplay, eGLConfig);
            return eGLConfig;
        }

        private void logConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
            int[] iArr = new int[1];
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.ATTR_ID.length; i++) {
                egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, this.ATTR_ID[i], iArr);
                sb.append(this.ATTR_NAME[i]);
                sb.append(iArr[0]);
                sb.append(" ");
            }
            Log.i(V6CameraGLSurfaceView.TAG, "Config chosen: " + sb.toString());
        }

        public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay) {
            int[] iArr = new int[1];
            if (!egl10.eglChooseConfig(eGLDisplay, this.mConfigSpec, (EGLConfig[]) null, 0, iArr)) {
                throw new RuntimeException("eglChooseConfig failed");
            } else if (iArr[0] > 0) {
                EGLConfig[] eGLConfigArr = new EGLConfig[iArr[0]];
                if (egl10.eglChooseConfig(eGLDisplay, this.mConfigSpec, eGLConfigArr, eGLConfigArr.length, iArr)) {
                    return chooseConfig(egl10, eGLDisplay, eGLConfigArr);
                }
                throw new RuntimeException();
            } else {
                throw new RuntimeException("No configs match configSpec");
            }
        }
    }

    public V6CameraGLSurfaceView(Context context) {
        this(context, (AttributeSet) null);
    }

    public V6CameraGLSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFrameCount = 0;
        this.mFrameCountingStart = 0;
        this.mRenderRequested = false;
        this.mEglConfigChooser = new MyEGLConfigChooser();
        this.mRenderLock = new ReentrantLock();
        setEGLContextClientVersion(2);
        setEGLConfigChooser(this.mEglConfigChooser);
        setRenderer(this);
        setRenderMode(0);
        setPreserveEGLContextOnPause(true);
        getHolder().setFormat(4);
        if (b.jy()) {
            getHolder().setFixedSize(Util.LIMIT_SURFACE_WIDTH, (Util.sWindowHeight * Util.LIMIT_SURFACE_WIDTH) / Util.sWindowWidth);
        }
        this.mActivity = (Camera) context;
    }

    private void outputFps() {
        long nanoTime = System.nanoTime();
        if (this.mFrameCountingStart == 0) {
            this.mFrameCountingStart = nanoTime;
        } else if (nanoTime - this.mFrameCountingStart > 1000000000) {
            Log.d(TAG, "fps: " + ((((double) this.mFrameCount) * 1.0E9d) / ((double) (nanoTime - this.mFrameCountingStart))));
            this.mFrameCountingStart = nanoTime;
            this.mFrameCount = 0;
        }
        this.mFrameCount++;
    }

    public EGLContext getEGLContext() {
        return this.mEGLContext10;
    }

    public android.opengl.EGLContext getEGLContext14() {
        return this.mEGLContext14;
    }

    public GLCanvasImpl getGLCanvas() {
        return this.mCanvas;
    }

    public boolean isBusy() {
        return this.mRenderRequested;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mCanvas != null) {
            this.mCanvas.deleteProgram();
            this.mCanvas.recycledResources();
        }
    }

    public void onDrawFrame(GL10 gl10) {
        this.mCanvas.recycledResources();
        UploadedTexture.resetUploadLimit();
        this.mRenderRequested = false;
        synchronized (this.mCanvas) {
            this.mCanvas.getState().pushState();
            boolean isAnimationRunning = this.mActivity.getCameraScreenNail().isAnimationRunning();
            boolean isAnimationGaussian = this.mActivity.getCameraScreenNail().isAnimationGaussian();
            this.mActivity.getCameraScreenNail().draw(this.mCanvas);
            if (!isAnimationRunning || isAnimationGaussian) {
                SurfaceTextureScreenNail.ExternalFrameProcessor externalFrameProcessor = this.mActivity.getCameraScreenNail().getExternalFrameProcessor();
                if (externalFrameProcessor != null) {
                    externalFrameProcessor.onDrawFrame(this.mActivity.getCameraScreenNail().getDisplayRect(), 0, 0, false);
                }
            }
            this.mCanvas.getState().popState();
        }
        if (UploadedTexture.uploadLimitReached()) {
            requestRender();
        }
        this.mCanvas.recycledResources();
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if (this.mActivity != null) {
            this.mActivity.updateSurfaceState(2);
        }
    }

    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        Log.i(TAG, "onSurfaceChanged: " + i + "x" + i2 + ", gl10: " + gl10.toString());
        Process.setThreadPriority(-4);
        Utils.assertTrue(this.mGL == ((GL11) gl10));
        this.mWidth = i;
        this.mHeight = i2;
        this.mCanvas.setSize(i, i2);
        this.mEGLContext10 = ((EGL10) EGLContext.getEGL()).eglGetCurrentContext();
        this.mEGLContext14 = EGL14.eglGetCurrentContext();
        if (this.mActivity != null) {
            this.mActivity.getCameraScreenNail().acquireSurfaceTexture();
            this.mActivity.updateSurfaceState(4);
        }
    }

    /* JADX INFO: finally extract failed */
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        Log.i(TAG, "onSurfaceCreated");
        GL11 gl11 = (GL11) gl10;
        if (this.mGL != null) {
            Log.i(TAG, "GLObject has changed from " + this.mGL + " to " + gl11);
        }
        this.mRenderLock.lock();
        try {
            this.mGL = gl11;
            BasicTexture.invalidateAllTextures(this.mCanvas);
            this.mCanvas = new GLCanvasImpl();
            this.mRenderLock.unlock();
            setRenderMode(0);
            if (this.mActivity != null) {
                this.mActivity.getCameraScreenNail().acquireSurfaceTexture();
                this.mActivity.updateSurfaceState(4);
            }
        } catch (Throwable th) {
            this.mRenderLock.unlock();
            throw th;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mActivity.isCurrentModuleAlive()) {
            return false;
        }
        boolean isInTapableRect = ((BaseModule) this.mActivity.getCurrentModule()).isInTapableRect((int) motionEvent.getX(), (int) motionEvent.getY());
        if (motionEvent.getActionMasked() == 0 && !isInTapableRect) {
            V6GestureRecognizer.getInstance(this.mActivity).setScaleDetectorEnable(false);
            this.mCatchUnTapableEvent = true;
        } else if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
            V6GestureRecognizer.getInstance(this.mActivity).setScaleDetectorEnable(true);
            this.mCatchUnTapableEvent = false;
        }
        if (this.mCatchUnTapableEvent) {
            V6GestureRecognizer.getInstance(this.mActivity).onTouchEvent(motionEvent);
        }
        return this.mCatchUnTapableEvent;
    }

    public void requestRender() {
        if (!this.mRenderRequested) {
            this.mRenderRequested = true;
            super.requestRender();
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        SurfaceTextureScreenNail.ExternalFrameProcessor externalFrameProcessor = this.mActivity.getCameraScreenNail().getExternalFrameProcessor();
        if (externalFrameProcessor != null) {
            externalFrameProcessor.releaseRender();
        }
        super.surfaceDestroyed(surfaceHolder);
        Log.d(TAG, "surfaceDestroyed: mActivity = " + this.mActivity);
    }
}
