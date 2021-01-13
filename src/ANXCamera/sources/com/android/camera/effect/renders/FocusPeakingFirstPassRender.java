package com.android.camera.effect.renders;

import android.graphics.Color;
import android.opengl.GLES20;
import com.android.gallery3d.ui.GLCanvas;

/* access modifiers changed from: package-private */
/* compiled from: FocusPeakingRender */
public class FocusPeakingFirstPassRender extends ConvolutionEffectRender {
    private static final String FRAG = "precision mediump float; \nuniform float uThreshold; \nuniform vec3 uPeakColor; \nuniform vec2 uStep; \nuniform sampler2D sTexture; \nvarying vec2 vTexCoord; \nfloat laplacian_filter() { \n    vec2 step = uStep; \n    float tl = texture2D(sTexture, vTexCoord + vec2(-step.x, step.y)).r; \n    float tm = texture2D(sTexture, vTexCoord + vec2(0.0,     step.y)).r; \n    float tr = texture2D(sTexture, vTexCoord + vec2(step.x,  step.y)).r; \n    float ml = texture2D(sTexture, vTexCoord + vec2(-step.x,    0.0)).r; \n    float mr = texture2D(sTexture, vTexCoord + vec2(step.x,     0.0)).r; \n    float bl = texture2D(sTexture, vTexCoord + vec2(-step.x,    -step.y)).r; \n    float bm = texture2D(sTexture, vTexCoord + vec2(0.0,    -step.y)).r; \n    float br = texture2D(sTexture, vTexCoord + vec2(step.x,    -step.y)).r; \n    float GradX = -tl + tr - 2.0 * ml + 2.0 * mr - bl + br; \n    float GradY = tl +  2.0 * tm + tr - bl - 2.0 * bm - br; \n    return length(vec2(GradX,GradY)); \n} \nvoid main() { \n    float gray = laplacian_filter(); \n    if (gray > uThreshold) { \n        gl_FragColor.rgb = uPeakColor; \n        gl_FragColor.a = 1.0; \n    } else { \n        gl_FragColor.rgb = vec3(0.0, 0.0, 0.0); \n        gl_FragColor.a = 1.0; \n    } \n}";
    private int mPeakColor = 16331023;
    private float mThreshold = FocusPeakingRender.DEFAULT_THRESHOLD;
    private int mUniformPeakColorH;
    private int mUniformThresholdH;

    public FocusPeakingFirstPassRender(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    public FocusPeakingFirstPassRender(GLCanvas gLCanvas, int i) {
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
        this.mUniformThresholdH = GLES20.glGetUniformLocation(this.mProgram, "uThreshold");
        this.mUniformPeakColorH = GLES20.glGetUniformLocation(this.mProgram, "uPeakColor");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.PixelEffectRender, com.android.camera.effect.renders.ConvolutionEffectRender
    public void initShaderValue(boolean z) {
        super.initShaderValue(z);
        GLES20.glUniform1f(this.mUniformThresholdH, this.mThreshold);
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

    public void setThreshold(float f) {
        this.mThreshold = f;
    }
}
