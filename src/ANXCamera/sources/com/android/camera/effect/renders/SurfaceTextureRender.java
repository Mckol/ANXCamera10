package com.android.camera.effect.renders;

import android.opengl.GLES20;
import com.android.camera.effect.ShaderUtil;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.ExtTexture;
import com.android.gallery3d.ui.GLCanvas;
import java.nio.Buffer;

public class SurfaceTextureRender extends ShaderRender {
    private static final String FRAG = "#extension GL_OES_EGL_image_external : require  \nprecision mediump float; \nuniform float uAlpha; \nuniform float uMixAlpha; \nuniform samplerExternalOES sTexture; \nvarying vec2 vTexCoord; \nvoid main() \n{ \n    gl_FragColor = texture2D(sTexture, vTexCoord)*uAlpha; \n    if (uMixAlpha >= 0.0) { \n       gl_FragColor.a = uMixAlpha; \n    } \n}";
    private static final String TAG = "SurfaceTextureRender";
    private static final float[] TEXTURES = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
    private static final float[] VERTICES = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};

    public SurfaceTextureRender(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    private void drawTexture(ExtTexture extTexture, float[] fArr, float f, float f2, float f3, float f4) {
        GLES20.glUseProgram(0);
        GLES20.glUseProgram(this.mProgram);
        if (!bindTexture(extTexture, 33984)) {
            Log.e(TAG, "fail bind texture " + extTexture.getId());
            return;
        }
        initAttributePointer();
        updateViewport();
        float alpha = this.mGLCanvas.getState().getAlpha();
        float blendAlpha = this.mGLCanvas.getState().getBlendAlpha();
        setBlendEnabled(this.mBlendEnabled && (!extTexture.isOpaque() || alpha < 0.95f || blendAlpha >= 0.0f));
        this.mGLCanvas.getState().pushState();
        this.mGLCanvas.getState().translate(f, f2, 0.0f);
        this.mGLCanvas.getState().scale(f3, f4, 1.0f);
        GLES20.glUniformMatrix4fv(this.mUniformMVPMatrixH, 1, false, this.mGLCanvas.getState().getFinalMatrix(), 0);
        GLES20.glUniformMatrix4fv(this.mUniformSTMatrixH, 1, false, fArr, 0);
        GLES20.glUniform1i(this.mUniformTextureH, 0);
        GLES20.glUniform1f(this.mUniformAlphaH, this.mGLCanvas.getState().getAlpha());
        GLES20.glUniform1f(this.mUniformBlendAlphaH, blendAlpha);
        GLES20.glDrawArrays(5, 0, 4);
        this.mGLCanvas.getState().popState();
    }

    private void initAttributePointer() {
        GLES20.glVertexAttribPointer(this.mAttributePositionH, 2, 5126, false, 8, (Buffer) this.mVertexBuffer);
        GLES20.glVertexAttribPointer(this.mAttributeTexCoorH, 2, 5126, false, 8, (Buffer) this.mTexCoorBuffer);
        GLES20.glEnableVertexAttribArray(this.mAttributePositionH);
        GLES20.glEnableVertexAttribArray(this.mAttributeTexCoorH);
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.ShaderRender
    public boolean draw(DrawAttribute drawAttribute) {
        if (!isAttriSupported(drawAttribute.getTarget())) {
            return false;
        }
        DrawExtTexAttribute drawExtTexAttribute = (DrawExtTexAttribute) drawAttribute;
        drawTexture(drawExtTexAttribute.mExtTexture, drawExtTexAttribute.mTextureTransform, (float) drawExtTexAttribute.mX, (float) drawExtTexAttribute.mY, (float) drawExtTexAttribute.mWidth, (float) drawExtTexAttribute.mHeight);
        return true;
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
            this.mUniformTextureH = GLES20.glGetUniformLocation(this.mProgram, "sTexture");
            this.mUniformAlphaH = GLES20.glGetUniformLocation(this.mProgram, "uAlpha");
            this.mUniformBlendAlphaH = GLES20.glGetUniformLocation(this.mProgram, "uMixAlpha");
            this.mAttributePositionH = GLES20.glGetAttribLocation(this.mProgram, "aPosition");
            this.mAttributeTexCoorH = GLES20.glGetAttribLocation(this.mProgram, "aTexCoord");
            return;
        }
        throw new IllegalArgumentException(SurfaceTextureRender.class + ": mProgram = 0");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.ShaderRender
    public void initSupportAttriList() {
        this.mAttriSupportedList.add(8);
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
