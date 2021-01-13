package com.arcsoft.avatar2.gl;

import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GLRender {

    /* renamed from: a  reason: collision with root package name */
    private static final String f237a = "Arc_GLRender";
    private static final int l = 4;
    private static final String m = "attribute vec2 vPos;\nattribute vec2 vTex;\nvarying   vec2 vTextureCoord;\nvoid main(){\ngl_Position=vec4(vPos,0.0,1.0);\nvTextureCoord = vTex;\n}\n";
    private static final String n = "precision mediump float;\nvarying vec2 vTextureCoord;\nuniform sampler2D sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";

    /* renamed from: b  reason: collision with root package name */
    private int f238b;

    /* renamed from: c  reason: collision with root package name */
    private int f239c;

    /* renamed from: d  reason: collision with root package name */
    private int f240d;

    /* renamed from: e  reason: collision with root package name */
    private int f241e;
    private int[] f = new int[2];
    private int g;
    private int h;
    private boolean i;
    private int j;
    private boolean k;

    public GLRender(int i2, int i3, int i4, boolean z) {
        if (i3 * i3 <= 0 || i2 < 0 || i3 < 0) {
            throw new RuntimeException("GLRender() frameWidth=" + i2 + " ,frameHeight=" + i3 + " invalid.");
        }
        this.g = i2;
        this.h = i3;
        this.k = false;
    }

    public void initRender(boolean z, float f2) {
        GLES20.glGenBuffers(2, this.f, 0);
        float[] fArr = {-1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f};
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(fArr.length * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = allocateDirect.asFloatBuffer();
        asFloatBuffer.put(fArr);
        asFloatBuffer.position(0);
        GLES20.glBindBuffer(34962, this.f[0]);
        GLES20.glBufferData(34962, fArr.length * 4, asFloatBuffer, 35044);
        GLES20.glBindBuffer(34962, 0);
        float[] fArr2 = {0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f};
        if (f2 > 0.0f) {
            fArr2[7] = f2;
            fArr2[1] = f2;
            float f3 = 1.0f - f2;
            fArr2[5] = f3;
            fArr2[3] = f3;
        }
        float[] fArr3 = {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f};
        if (!z) {
            fArr3 = fArr2;
        }
        ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(fArr3.length * 4);
        allocateDirect2.order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer2 = allocateDirect2.asFloatBuffer();
        asFloatBuffer2.put(fArr3);
        asFloatBuffer2.position(0);
        GLES20.glBindBuffer(34962, this.f[1]);
        GLES20.glBufferData(34962, fArr3.length * 4, asFloatBuffer2, 35044);
        GLES20.glBindBuffer(34962, 0);
        this.f238b = ShaderManager.createProgram(m, n);
        int i2 = this.f238b;
        if (i2 != 0) {
            this.f239c = GLES20.glGetAttribLocation(i2, "vPos");
            this.f240d = GLES20.glGetAttribLocation(this.f238b, "vTex");
            this.f241e = GLES20.glGetUniformLocation(this.f238b, "sTexture");
            this.k = true;
        }
    }

    public void renderWithTextureId(int i2) {
        if (this.k) {
            GLES20.glUseProgram(this.f238b);
            GLES20.glClear(16640);
            GLES20.glBindBuffer(34962, this.f[0]);
            GLES20.glVertexAttribPointer(this.f239c, 2, 5126, false, 8, 0);
            GLES20.glEnableVertexAttribArray(this.f239c);
            GLES20.glBindBuffer(34962, this.f[1]);
            GLES20.glVertexAttribPointer(this.f240d, 2, 5126, false, 8, 0);
            GLES20.glEnableVertexAttribArray(this.f240d);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, i2);
            GLES20.glUniform1i(this.f241e, 0);
            GLES20.glDrawArrays(6, 0, 4);
            GLES20.glDisableVertexAttribArray(this.f239c);
            GLES20.glDisableVertexAttribArray(this.f240d);
            GLES20.glBindBuffer(34962, 0);
            GLES20.glBindTexture(3553, 0);
            GLES20.glUseProgram(0);
        }
    }

    public void unInitRender() {
        if (this.k) {
            GLES20.glDeleteProgram(this.f238b);
            GLES20.glDeleteBuffers(2, this.f, 0);
        }
        this.k = false;
    }

    public void updateMirrorAndOrientation() {
    }
}
