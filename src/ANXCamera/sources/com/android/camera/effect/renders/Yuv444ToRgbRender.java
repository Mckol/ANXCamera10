package com.android.camera.effect.renders;

import android.opengl.GLES20;
import b.c.a.c;
import com.android.camera.effect.MiYuvImage;
import com.android.camera.effect.ShaderUtil;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.draw_mode.DrawYuvAttribute;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera2.CameraCapabilities;
import com.android.gallery3d.ui.GLCanvas;
import java.nio.Buffer;
import java.util.Locale;

public class Yuv444ToRgbRender extends ShaderRender {
    private static final String FRAG = "precision highp float; \nvarying vec2 vTexCoord; \nuniform sampler2D uYTexture; \nuniform sampler2D uUTexture; \nuniform sampler2D uVTexture; \nuniform float uMtkPlatform; \nvoid main (void){ \nvec4 c = vec4((texture2D(uYTexture, vTexCoord).r - 16./255.) * 1.164);\nvec4 U = vec4(texture2D(uUTexture, vTexCoord).r - 128./255.);\nvec4 V = vec4(texture2D(uVTexture, vTexCoord).r - 128./255.);\nc += V * vec4(1.596, -0.813, 0, 0);\nc += U * vec4(0, -0.392, 2.017, 0);\nc.a = 1.0;\ngl_FragColor = c;\n}\n";
    private static final String TAG = "Yuv444ToRgbRender";
    private static final float[] TEXTURES = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final float[] VERTICES = {0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
    private int mUniformMTKPlatform;
    private int mUniformUTexture;
    private int mUniformVTexture;
    private int mUniformYTexture;
    private int[] mYuvTextureIds;

    public Yuv444ToRgbRender(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    public Yuv444ToRgbRender(GLCanvas gLCanvas, int i) {
        super(gLCanvas, i);
    }

    private void drawTexture(int[] iArr, float f, float f2, float f3, float f4, float[] fArr) {
        GLES20.glUseProgram(this.mProgram);
        updateViewport();
        setBlendEnabled(false);
        this.mGLCanvas.getState().pushState();
        this.mGLCanvas.getState().translate(f, f2, 0.0f);
        this.mGLCanvas.getState().scale(f3, f4, 1.0f);
        if (iArr[0] != -1) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glUniform1i(this.mUniformYTexture, 0);
        }
        if (iArr[1] != -1) {
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(3553, iArr[1]);
            GLES20.glUniform1i(this.mUniformUTexture, 1);
        }
        if (iArr[2] != -1) {
            GLES20.glActiveTexture(33986);
            GLES20.glBindTexture(3553, iArr[2]);
            GLES20.glUniform1i(this.mUniformVTexture, 2);
        }
        initShaderValue(fArr);
        GLES20.glDrawArrays(5, 0, 4);
        this.mGLCanvas.getState().popState();
    }

    private boolean genMiYuvTextures(DrawYuvAttribute drawYuvAttribute) {
        MiYuvImage miYuvImage = drawYuvAttribute.mYuvImage;
        return ShaderUtil.loadYuv444ToTextures(miYuvImage.mYBuffer, miYuvImage.mUBuffer, miYuvImage.mVBuffer, miYuvImage.mWidth, miYuvImage.mHeight, this.mYuvTextureIds);
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.ShaderRender
    public void destroy() {
        int[] iArr = this.mYuvTextureIds;
        if (iArr != null && iArr.length == 3) {
            GLES20.glDeleteTextures(3, iArr, 0);
            this.mYuvTextureIds = null;
        }
        super.destroy();
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.ShaderRender
    public boolean draw(DrawAttribute drawAttribute) {
        if (!isAttriSupported(drawAttribute.getTarget())) {
            String str = TAG;
            Log.w(str, "unsupported target " + drawAttribute.getTarget());
            return false;
        } else if (drawAttribute.getTarget() != 11) {
            return true;
        } else {
            DrawYuvAttribute drawYuvAttribute = (DrawYuvAttribute) drawAttribute;
            if (drawYuvAttribute.mYuvImage == null || !genMiYuvTextures(drawYuvAttribute)) {
                return true;
            }
            drawTexture(this.mYuvTextureIds, (float) drawYuvAttribute.mX, (float) drawYuvAttribute.mY, (float) drawYuvAttribute.mPictureSize.getWidth(), (float) drawYuvAttribute.mPictureSize.getHeight(), drawYuvAttribute.mTransform);
            return true;
        }
    }

    @Override // com.android.camera.effect.renders.ShaderRender
    public String getFragShaderString() {
        return FRAG;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.ShaderRender
    public void initShader() {
        this.mProgram = ShaderUtil.createProgram(getVertexShaderString(), getFragShaderString());
        int i = this.mProgram;
        if (i != 0) {
            GLES20.glUseProgram(i);
            this.mUniformMVPMatrixH = GLES20.glGetUniformLocation(this.mProgram, "uMVPMatrix");
            this.mUniformSTMatrixH = GLES20.glGetUniformLocation(this.mProgram, "uSTMatrix");
            this.mAttributePositionH = GLES20.glGetAttribLocation(this.mProgram, "aPosition");
            this.mAttributeTexCoorH = GLES20.glGetAttribLocation(this.mProgram, "aTexCoord");
            this.mUniformYTexture = GLES20.glGetUniformLocation(this.mProgram, "uYTexture");
            this.mUniformUTexture = GLES20.glGetUniformLocation(this.mProgram, "uUTexture");
            this.mUniformVTexture = GLES20.glGetUniformLocation(this.mProgram, "uVTexture");
            this.mUniformMTKPlatform = GLES20.glGetUniformLocation(this.mProgram, "uMtkPlatform");
            this.mYuvTextureIds = new int[3];
            GLES20.glGenTextures(3, this.mYuvTextureIds, 0);
            Log.d(TAG, String.format(Locale.ENGLISH, "genTexture: %d %d %d", Integer.valueOf(this.mYuvTextureIds[0]), Integer.valueOf(this.mYuvTextureIds[1]), Integer.valueOf(this.mYuvTextureIds[2])));
            return;
        }
        throw new IllegalArgumentException(Yuv444ToRgbRender.class + ": mProgram = 0");
    }

    /* access modifiers changed from: protected */
    public void initShaderValue(float[] fArr) {
        GLES20.glVertexAttribPointer(this.mAttributePositionH, 2, 5126, false, 8, (Buffer) this.mVertexBuffer);
        GLES20.glVertexAttribPointer(this.mAttributeTexCoorH, 2, 5126, false, 8, (Buffer) this.mTexCoorBuffer);
        GLES20.glEnableVertexAttribArray(this.mAttributePositionH);
        GLES20.glEnableVertexAttribArray(this.mAttributeTexCoorH);
        GLES20.glUniformMatrix4fv(this.mUniformMVPMatrixH, 1, false, this.mGLCanvas.getState().getFinalMatrix(), 0);
        if (fArr == null) {
            GLES20.glUniformMatrix4fv(this.mUniformSTMatrixH, 1, false, this.mGLCanvas.getState().getTexMatrix(), 0);
        } else {
            GLES20.glUniformMatrix4fv(this.mUniformSTMatrixH, 1, false, fArr, 0);
        }
        CameraCapabilities currentCameraCapabilities = Camera2DataContainer.getInstance().getCurrentCameraCapabilities();
        int i = -1;
        if (currentCameraCapabilities != null) {
            i = currentCameraCapabilities.getXiaomiYuvFormat();
        }
        if (c.isMTKPlatform() || 2 == i) {
            GLES20.glUniform1f(this.mUniformMTKPlatform, 1.0f);
        } else {
            GLES20.glUniform1f(this.mUniformMTKPlatform, 0.0f);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.ShaderRender
    public void initSupportAttriList() {
        this.mAttriSupportedList.add(11);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.ShaderRender
    public void initVertexData() {
        this.mVertexBuffer = ShaderRender.allocateByteBuffer((VERTICES.length * 32) / 8).asFloatBuffer();
        this.mVertexBuffer.put(VERTICES);
        this.mVertexBuffer.position(0);
        this.mTexCoorBuffer = ShaderRender.allocateByteBuffer((TEXTURES.length * 32) / 8).asFloatBuffer();
        this.mTexCoorBuffer.put(TEXTURES);
        this.mTexCoorBuffer.position(0);
    }
}
