package com.android.gallery3d.ui;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FrameBuffer;
import com.android.camera.effect.GLCanvasState;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.renders.RenderGroup;
import com.arcsoft.supernight.SuperNightProcess;
import java.util.ArrayList;
import java.util.Iterator;

public class BaseGLCanvas implements GLCanvas {
    public static int sMaxTextureSize = 4096;
    private final IntArray mDeleteBuffers = new IntArray();
    private final IntArray mDeleteFrameBuffers = new IntArray();
    private final ArrayList<Integer> mDeletePrograms = new ArrayList<>();
    private final ArrayList<SurfaceTexture> mDeleteSurfaceTexture = new ArrayList<>();
    private final IntArray mDeleteTextures = new IntArray();
    private int mHeight;
    protected RenderGroup mRenderCaches;
    protected RenderGroup mRenderGroup;
    protected GLCanvasState mState = new GLCanvasState();
    private int mWidth;

    public BaseGLCanvas() {
        int[] iArr = new int[1];
        GLES20.glGetIntegerv(3379, iArr, 0);
        sMaxTextureSize = iArr[0];
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void beginBindFrameBuffer(int i, int i2, int i3) {
        this.mRenderGroup.beginBindFrameBuffer(i, i2, i3);
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void beginBindFrameBuffer(FrameBuffer frameBuffer) {
        this.mRenderGroup.beginBindFrameBuffer(frameBuffer);
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void clearBuffer() {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(16384);
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void deleteBuffer(int i) {
        synchronized (this.mDeleteBuffers) {
            this.mDeleteBuffers.add(i);
        }
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void deleteFrameBuffer(int i) {
        synchronized (this.mDeleteFrameBuffers) {
            this.mDeleteFrameBuffers.add(i);
        }
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void deleteProgram() {
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void deleteProgram(int i) {
        synchronized (this.mDeletePrograms) {
            this.mDeletePrograms.add(Integer.valueOf(i));
        }
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void deleteSurfaceTexture(SurfaceTexture surfaceTexture) {
        synchronized (this.mDeleteSurfaceTexture) {
            if (!surfaceTexture.isReleased()) {
                this.mDeleteSurfaceTexture.add(surfaceTexture);
            }
        }
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public boolean deleteTexture(int i) {
        synchronized (this.mDeleteTextures) {
            if (i == 0) {
                return false;
            }
            this.mDeleteTextures.add(i);
            return true;
        }
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public boolean deleteTexture(BasicTexture basicTexture) {
        synchronized (this.mDeleteTextures) {
            if (!basicTexture.isLoaded()) {
                return false;
            }
            this.mDeleteTextures.add(basicTexture.getId());
            return true;
        }
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void draw(DrawAttribute drawAttribute) {
        this.mRenderGroup.draw(drawAttribute);
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void endBindFrameBuffer() {
        this.mRenderGroup.endBindFrameBuffer();
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public RenderGroup getEffectRenderGroup() {
        return this.mRenderCaches;
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public int getHeight() {
        return this.mHeight;
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public GLCanvasState getState() {
        return this.mState;
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public int getWidth() {
        return this.mWidth;
    }

    /* access modifiers changed from: protected */
    public void initialize() {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClearStencil(0);
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(770, 771);
        GLES20.glPixelStorei(3317, 1);
        GLES20.glPixelStorei(SuperNightProcess.ASVL_PAF_RAW12_RGGB_12B, 1);
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void prepareBlurRenders() {
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void prepareEffectRenders(boolean z, int i) {
        if (this.mRenderCaches.isNeedInit(i)) {
            EffectController.getInstance().getEffectGroup(this, this.mRenderCaches, z, true, i);
        }
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void prepareYuvRenders() {
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void recycledResources() {
        synchronized (this.mDeleteTextures) {
            IntArray intArray = this.mDeleteTextures;
            if (intArray.size() > 0) {
                GLES20.glDeleteTextures(intArray.size(), intArray.getInternalArray(), 0);
                intArray.clear();
            }
            IntArray intArray2 = this.mDeleteBuffers;
            if (intArray2.size() > 0) {
                GLES20.glDeleteBuffers(intArray2.size(), intArray2.getInternalArray(), 0);
                intArray2.clear();
            }
            IntArray intArray3 = this.mDeleteFrameBuffers;
            if (intArray3.size() > 0) {
                GLES20.glDeleteFramebuffers(intArray3.size(), intArray3.getInternalArray(), 0);
                intArray3.clear();
            }
        }
        synchronized (this.mDeleteSurfaceTexture) {
            Iterator<SurfaceTexture> it = this.mDeleteSurfaceTexture.iterator();
            while (it.hasNext()) {
                SurfaceTexture next = it.next();
                next.detachFromGLContext();
                next.release();
            }
            this.mDeleteSurfaceTexture.clear();
        }
        synchronized (this.mDeletePrograms) {
            while (this.mDeletePrograms.size() > 0) {
                GLES20.glDeleteProgram(this.mDeletePrograms.remove(0).intValue());
            }
        }
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void setPreviewSize(int i, int i2) {
        this.mRenderGroup.setPreviewSize(i, i2);
    }

    @Override // com.android.gallery3d.ui.GLCanvas
    public void setSize(int i, int i2) {
        Utils.assertTrue(i >= 0 && i2 >= 0);
        this.mWidth = i;
        this.mHeight = i2;
        this.mRenderGroup.setViewportSize(i, i2);
        this.mRenderGroup.setPreviewSize(i, i2);
        this.mState.identityAllM();
        this.mState.setAlpha(1.0f);
        this.mState.translate(0.0f, (float) i2, 0.0f);
        this.mState.scale(1.0f, -1.0f, 1.0f);
    }
}
