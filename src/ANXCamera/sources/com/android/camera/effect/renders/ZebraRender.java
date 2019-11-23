package com.android.camera.effect.renders;

import android.graphics.Color;
import android.opengl.GLES20;
import android.support.v4.internal.view.SupportMenu;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.gallery3d.ui.GLCanvas;

public class ZebraRender extends ConvolutionEffectRender {
    private static final String FRAG = "precision highp float; \nuniform sampler2D sTexture; \nvarying vec2 vTexCoord; \nuniform vec2 uSize; \nuniform vec2 uStep; \nuniform float uWidth; \nuniform float uGap; \nuniform float uOffset; \nuniform float uAlpha; \nuniform vec3 uOverColor; \nuniform vec3 uUnderColor; \nuniform float uOverExposure; \nuniform float uUnderExposure; \nvoid main() { \n    vec3 color = texture2D(sTexture, vTexCoord).rgb; \n    vec3 factor = vec3(0.2125, 0.7154, 0.0721); \n    float Y = dot(color, factor); \n    vec2 coord = vTexCoord * uSize; // convert to world coordinate \n    float x = coord.x + uOffset; \n    float y = coord.y; \n    float diff; \n    if (y > x) { \n        diff = y - x; \n    } else { \n        diff = x - y + uWidth; \n    } \n    if (mod(diff, uGap + uWidth) < uWidth) { \n        if (Y >= uOverExposure) { \n            gl_FragColor = vec4(uOverColor, 1)*uAlpha; \n        } else if (Y < uUnderExposure) { \n            gl_FragColor = vec4(uUnderColor, 1)*uAlpha; \n        } else { \n            gl_FragColor = vec4(color, 1)*uAlpha; \n        } \n    } else { \n        gl_FragColor = vec4(color, 1)*uAlpha; \n    } \n}";
    protected float mGap = 32.0f;
    protected float mOffset = 0.0f;
    protected int mOverColor = SupportMenu.CATEGORY_MASK;
    protected float mOverExposure = 0.99f;
    protected int mUnderColor = -16776961;
    protected float mUnderExposure = 0.01f;
    protected int mUniformGapH;
    protected int mUniformOffsetH;
    protected int mUniformOverColorH;
    protected int mUniformOverExposureH;
    protected int mUniformSizeH;
    protected int mUniformUnderColorH;
    protected int mUniformUnderExposureH;
    protected int mUniformWidthH;
    protected float mWidth = 32.0f;

    public ZebraRender(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    public ZebraRender(GLCanvas gLCanvas, int i) {
        super(gLCanvas, i);
    }

    public boolean draw(DrawAttribute drawAttribute) {
        this.mOffset += 1.0f;
        return super.draw(drawAttribute);
    }

    public String getFragShaderString() {
        return FRAG;
    }

    /* access modifiers changed from: protected */
    public void initShader() {
        super.initShader();
        this.mUniformSizeH = GLES20.glGetUniformLocation(this.mProgram, "uSize");
        this.mUniformOverExposureH = GLES20.glGetUniformLocation(this.mProgram, "uOverExposure");
        this.mUniformUnderExposureH = GLES20.glGetUniformLocation(this.mProgram, "uUnderExposure");
        this.mUniformOverColorH = GLES20.glGetUniformLocation(this.mProgram, "uOverColor");
        this.mUniformUnderColorH = GLES20.glGetUniformLocation(this.mProgram, "uUnderColor");
        this.mUniformWidthH = GLES20.glGetUniformLocation(this.mProgram, "uWidth");
        this.mUniformGapH = GLES20.glGetUniformLocation(this.mProgram, "uGap");
        this.mUniformOffsetH = GLES20.glGetUniformLocation(this.mProgram, "uOffset");
    }

    /* access modifiers changed from: protected */
    public void initShaderValue(boolean z) {
        super.initShaderValue(z);
        GLES20.glUniform2f(this.mUniformSizeH, (float) this.mPreviewWidth, (float) this.mPreviewHeight);
        GLES20.glUniform1f(this.mUniformOverExposureH, this.mOverExposure);
        GLES20.glUniform1f(this.mUniformUnderExposureH, this.mUnderExposure);
        GLES20.glUniform3f(this.mUniformOverColorH, ((float) Color.red(this.mOverColor)) / 255.0f, ((float) Color.green(this.mOverColor)) / 255.0f, ((float) Color.blue(this.mOverColor)) / 255.0f);
        GLES20.glUniform3f(this.mUniformUnderColorH, ((float) Color.red(this.mUnderColor)) / 255.0f, ((float) Color.green(this.mUnderColor)) / 255.0f, ((float) Color.blue(this.mUnderColor)) / 255.0f);
        GLES20.glUniform1f(this.mUniformWidthH, this.mWidth);
        GLES20.glUniform1f(this.mUniformGapH, this.mGap);
        GLES20.glUniform1f(this.mUniformOffsetH, this.mOffset);
    }

    public void setExposureThreshold(float f, float f2) {
        this.mOverExposure = f;
        this.mUnderExposure = f2;
    }

    public void setPreviewSize(int i, int i2) {
        super.setPreviewSize(i, i2);
    }
}
