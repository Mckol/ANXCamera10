package com.android.camera.effect.renders;

import android.graphics.Color;
import android.opengl.GLES20;
import com.android.gallery3d.ui.GLCanvas;

/* access modifiers changed from: package-private */
/* compiled from: FocusPeakingRender */
public class FocusPeakingSecondPassRender extends ConvolutionEffectRender {
    private static final String FRAG = "precision mediump float; \n//uniform vec2 uStep; \n//uniform vec3 uPeakColor; \nuniform sampler2D sTexture; \nvarying vec2 vTexCoord; \nvoid main() { \n    vec3 sum = texture2D(sTexture, vTexCoord).rgb; \n    if (any(greaterThan(sum, vec3(0.0, 0.0, 0.0)))) { \n        gl_FragColor.rgb = vec3(sum); \n        gl_FragColor.a = 1.0; \n    } else { \n        gl_FragColor.rgb = vec3(1.0, 0.0, 0.0); \n        gl_FragColor.a = 0.0; \n    } \n}";
    private int mPeakColor = 16331023;
    private int mUniformPeakColorH;

    public FocusPeakingSecondPassRender(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    public FocusPeakingSecondPassRender(GLCanvas gLCanvas, int i) {
        super(gLCanvas, i);
    }

    @Override // com.android.camera.effect.renders.ShaderRender
    public String getFragShaderString() {
        return FRAG;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.PixelEffectRender, com.android.camera.effect.renders.ConvolutionEffectRender, com.android.camera.effect.renders.ShaderRender
    public void initShader() {
        super.initShader();
        this.mUniformPeakColorH = GLES20.glGetUniformLocation(this.mProgram, "uPeakColor");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.PixelEffectRender, com.android.camera.effect.renders.ConvolutionEffectRender
    public void initShaderValue(boolean z) {
        super.initShaderValue(z);
        GLES20.glUniform3f(this.mUniformPeakColorH, ((float) Color.red(this.mPeakColor)) / 255.0f, ((float) Color.green(this.mPeakColor)) / 255.0f, ((float) Color.blue(this.mPeakColor)) / 255.0f);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.ShaderRender
    public void setBlendEnabled(boolean z) {
        GLES20.glEnable(3042);
    }

    public void setPeakColor(int i) {
        this.mPeakColor = i;
    }
}
