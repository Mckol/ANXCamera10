package com.android.camera.module.encoder;

import android.graphics.SurfaceTexture;
import android.opengl.EGLContext;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.android.camera.effect.VideoRecorderCanvas;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.log.Log;
import com.android.camera.module.encoder.EGLBase;
import com.android.gallery3d.ui.GLCanvas;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class RenderHandler implements Runnable {
    private static final String TAG = "RenderHandler";
    private GLCanvas mCanvas;
    private EGLBase mEgl;
    private ArrayList<DrawAttribute> mExtTextures = new ArrayList<>();
    private EGLBase.EglSurface mInputSurface;
    private boolean mIsReady;
    private boolean mIsRecordable;
    private int mPreviewHeight;
    private int mPreviewWidth;
    private int mRequestDraw;
    private boolean mRequestRelease;
    private boolean mRequestSetEglContext;
    private EGLContext mShardContext;
    private Object mSurface;
    private final Object mSync = new Object();

    private RenderHandler(int i, int i2) {
        this.mPreviewWidth = i;
        this.mPreviewHeight = i2;
    }

    public static final RenderHandler createHandler(String str, int i, int i2) {
        Log.v(TAG, String.format(Locale.ENGLISH, "init: previewSize=%dx%d", Integer.valueOf(i), Integer.valueOf(i2)));
        RenderHandler renderHandler = new RenderHandler(i, i2);
        synchronized (renderHandler.mSync) {
            renderHandler.mIsReady = false;
            if (TextUtils.isEmpty(str)) {
                str = TAG;
            }
            new Thread(renderHandler, str).start();
            if (!renderHandler.mIsReady) {
                try {
                    renderHandler.mSync.wait();
                } catch (InterruptedException e2) {
                    Log.e(TAG, e2.getMessage(), e2);
                }
            }
        }
        return renderHandler;
    }

    private final void internalPrepare() {
        Log.v(TAG, "internalPrepare");
        internalRelease();
        this.mEgl = new EGLBase(this.mShardContext, false, this.mIsRecordable);
        this.mInputSurface = this.mEgl.createFromSurface(this.mSurface);
        this.mInputSurface.makeCurrent();
        this.mCanvas = new VideoRecorderCanvas();
        this.mCanvas.setSize(this.mPreviewWidth, this.mPreviewHeight);
        this.mSurface = null;
        this.mSync.notifyAll();
    }

    private final void internalRelease() {
        Log.v(TAG, "internalRelease");
        EGLBase.EglSurface eglSurface = this.mInputSurface;
        if (eglSurface != null) {
            eglSurface.release();
            this.mInputSurface = null;
        }
        GLCanvas gLCanvas = this.mCanvas;
        if (gLCanvas != null) {
            gLCanvas.deleteProgram();
            this.mCanvas.recycledResources();
            this.mCanvas = null;
        }
        EGLBase eGLBase = this.mEgl;
        if (eGLBase != null) {
            eGLBase.release();
            this.mEgl = null;
        }
    }

    public final void draw(DrawAttribute drawAttribute) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(drawAttribute);
        draw(arrayList);
    }

    public final void draw(DrawExtTexAttribute drawExtTexAttribute) {
        ArrayList arrayList = new ArrayList();
        DrawExtTexAttribute drawExtTexAttribute2 = new DrawExtTexAttribute();
        drawExtTexAttribute2.init(drawExtTexAttribute.mExtTexture, drawExtTexAttribute.mTextureTransform, 0, 0, this.mPreviewWidth, this.mPreviewHeight);
        arrayList.add(drawExtTexAttribute2);
        draw(arrayList);
    }

    public final void draw(List<DrawAttribute> list) {
        synchronized (this.mSync) {
            if (!this.mRequestRelease) {
                for (DrawAttribute drawAttribute : list) {
                    this.mExtTextures.add(drawAttribute);
                }
                this.mRequestDraw++;
                this.mSync.notifyAll();
            }
        }
    }

    public boolean isValid() {
        boolean z;
        synchronized (this.mSync) {
            if (this.mSurface instanceof Surface) {
                if (!((Surface) this.mSurface).isValid()) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    public final void release() {
        Log.v(TAG, "release");
        synchronized (this.mSync) {
            if (!this.mRequestRelease) {
                this.mRequestRelease = true;
                this.mSync.notifyAll();
                try {
                    this.mSync.wait();
                } catch (InterruptedException e2) {
                    Log.e(TAG, e2.getMessage());
                }
            }
        }
    }

    public final void run() {
        Log.d(TAG, "renderHandlerThread>>>");
        synchronized (this.mSync) {
            this.mRequestSetEglContext = false;
            this.mRequestRelease = false;
            this.mRequestDraw = 0;
            this.mIsReady = true;
            this.mSync.notifyAll();
        }
        while (true) {
            synchronized (this.mSync) {
                if (!this.mRequestRelease) {
                    if (this.mRequestSetEglContext) {
                        this.mRequestSetEglContext = false;
                        internalPrepare();
                    }
                    boolean z = this.mRequestDraw > 0;
                    if (z) {
                        this.mRequestDraw--;
                    }
                    if (!z) {
                        this.mSync.notifyAll();
                        try {
                            this.mSync.wait();
                        } catch (InterruptedException unused) {
                        }
                    } else if (this.mEgl != null && !this.mExtTextures.isEmpty()) {
                        this.mInputSurface.makeCurrent();
                        this.mCanvas.clearBuffer();
                        Iterator<DrawAttribute> it = this.mExtTextures.iterator();
                        while (it.hasNext()) {
                            this.mCanvas.draw(it.next());
                        }
                        this.mExtTextures.clear();
                        this.mInputSurface.swap();
                    }
                }
            }
        }
        synchronized (this.mSync) {
            this.mRequestRelease = true;
            internalRelease();
            this.mSync.notifyAll();
        }
        Log.d(TAG, "renderHandlerThread<<<");
    }

    public final void setEglContext(EGLContext eGLContext, Object obj, boolean z) {
        Log.v(TAG, "setEglContext");
        if ((obj instanceof Surface) || (obj instanceof SurfaceTexture) || (obj instanceof SurfaceHolder)) {
            synchronized (this.mSync) {
                if (!this.mRequestRelease) {
                    this.mShardContext = eGLContext;
                    this.mSurface = obj;
                    this.mIsRecordable = z;
                    this.mRequestSetEglContext = true;
                    this.mSync.notifyAll();
                    try {
                        this.mSync.wait();
                    } catch (InterruptedException e2) {
                        Log.e(TAG, e2.getMessage(), e2);
                    }
                    return;
                }
                return;
            }
        }
        throw new RuntimeException("unsupported window type:" + obj);
    }

    public void waitDrawFinish() {
        synchronized (this.mSync) {
            while (!this.mExtTextures.isEmpty()) {
                this.mSync.notifyAll();
                try {
                    this.mSync.wait();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
