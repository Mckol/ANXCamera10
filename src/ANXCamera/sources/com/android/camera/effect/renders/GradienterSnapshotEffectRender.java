package com.android.camera.effect.renders;

import android.opengl.GLES20;
import com.android.camera.CameraAppImpl;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.module.ModuleManager;
import com.android.gallery3d.ui.GLCanvas;

public class GradienterSnapshotEffectRender extends PixelEffectRender {
    private static final String FRAG = "precision highp float; \nvarying vec2 vTexCoord; \nuniform sampler2D sTexture; \nuniform float sAngle; \nuniform vec2 vTexSize; \nuniform vec2 vTargetSize; \nuniform vec2 vTargetCenter; \nvoid main() \n{ \n  float pf = min(vTargetSize.s,vTargetSize.t) / max(vTargetSize.s,vTargetSize.t);\n  float mOrigin = atan(pf); \n  float rorate_angle = sAngle; \n  float fangle = radians(abs(rorate_angle)) + mOrigin;\n  float s = sin(mOrigin) / sin(fangle);\n  float center_x = vTargetCenter.x; \n  float center_y = vTargetCenter.y; \n  float tx = center_x - center_x * s; \n  float ty = center_y - center_y * s; \n  float cosangle = cos(radians(rorate_angle)); \n  float sinangle = sin(radians(rorate_angle)); \n  float x = s * (vTexCoord.s * vTexSize.s) + tx; \n  float y = s * (vTexCoord.t * vTexSize.t) + ty; \n  float  x1 = x - center_x; \n  float  y1 = y - center_y; \n  x = cosangle * x1 + sinangle * y1 + center_x; \n  y = -1.0 * sinangle * x1 + cosangle * y1 + center_y; \n  x = x / vTexSize.s; \n  y = y / vTexSize.t; \n  x = clamp(x,0.0,1.0); \n  y = clamp(y,0.0,1.0); \n  gl_FragColor = texture2D(sTexture, vec2(x,y)); \n} \n";
    private int mSquareModeExtraMargin;
    private int mUniformAngle;
    private int mUniformTargetCenter;
    private int mUniformTargetSize;
    private int mUniformTexSize;

    public GradienterSnapshotEffectRender(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    public GradienterSnapshotEffectRender(GLCanvas gLCanvas, int i) {
        super(gLCanvas, i);
    }

    private float getRotation() {
        float f = this.mShootRotation - ((float) this.mOrientation);
        return f > 180.0f ? f - 360.0f : f;
    }

    @Override // com.android.camera.effect.renders.ShaderRender
    public String getFragShaderString() {
        return FRAG;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.PixelEffectRender, com.android.camera.effect.renders.ShaderRender
    public void initShader() {
        super.initShader();
        this.mUniformAngle = GLES20.glGetUniformLocation(this.mProgram, "sAngle");
        this.mUniformTexSize = GLES20.glGetUniformLocation(this.mProgram, "vTexSize");
        this.mUniformTargetSize = GLES20.glGetUniformLocation(this.mProgram, "vTargetSize");
        this.mUniformTargetCenter = GLES20.glGetUniformLocation(this.mProgram, "vTargetCenter");
        this.mSquareModeExtraMargin = CameraAppImpl.getAndroidContext().getResources().getDimensionPixelSize(R.dimen.square_mode_bottom_cover_extra_margin);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.PixelEffectRender
    public void initShaderValue(boolean z) {
        int i;
        super.initShaderValue(z);
        int i2 = this.mSnapshotOriginWidth;
        if (i2 != 0 && (i = this.mSnapshotOriginHeight) != 0) {
            GLES20.glUniform2f(this.mUniformTexSize, (float) i2, (float) i);
            float f = ((float) this.mSnapshotOriginWidth) * 0.5f;
            float f2 = ((float) this.mSnapshotOriginHeight) * 0.5f;
            if (ModuleManager.isSquareModule()) {
                float min = (float) Math.min(this.mSnapshotOriginWidth, this.mSnapshotOriginHeight);
                GLES20.glUniform2f(this.mUniformTargetSize, min, min);
                int i3 = this.mSnapshotOriginWidth;
                int i4 = this.mSnapshotOriginHeight;
                if (i3 > i4) {
                    f -= (float) ((this.mSquareModeExtraMargin * i4) / Util.sWindowWidth);
                } else {
                    f2 -= (float) ((this.mSquareModeExtraMargin * i3) / Util.sWindowWidth);
                }
            } else {
                GLES20.glUniform2f(this.mUniformTargetSize, (float) this.mSnapshotOriginWidth, (float) this.mSnapshotOriginHeight);
            }
            GLES20.glUniform2f(this.mUniformTargetCenter, f, f2);
            GLES20.glUniform1f(this.mUniformAngle, getRotation());
        }
    }
}
