package com.arcsoft.avatar2.gl;

import android.opengl.GLES30;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class DrawTexture {

    /* renamed from: a  reason: collision with root package name */
    private final String f217a = "#version 310 es\nprecision mediump float;\nin vec2 vTextureCoord;\nuniform sampler2D sTexture;\nout vec4 fragColor;\nvoid main()\n{ \n   fragColor = texture(sTexture, vTextureCoord); \n}";

    /* renamed from: b  reason: collision with root package name */
    private final String f218b = "#version 310 es\nlayout(location = 0) in vec2 arc_tex_coord;\nlayout(location = 1) in vec2 aPosition;\nout vec2 vTextureCoord;\nvoid main()\n{\n   vTextureCoord = arc_tex_coord;\n   gl_Position = vec4(aPosition, 1.0, 1.0);\n} ";

    /* renamed from: c  reason: collision with root package name */
    private int f219c = -1;

    /* renamed from: d  reason: collision with root package name */
    private int f220d = -1;

    /* renamed from: e  reason: collision with root package name */
    private int f221e = -1;
    private int f = -1;
    private int[] g = new int[2];
    private int[] h = new int[1];
    private FloatBuffer i;
    private boolean j;
    private float[] k = {-1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f};
    private float[] l = {0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f};
    private float[] m = {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f};

    private void a(boolean z) {
        float[] fArr = z ? this.m : this.l;
        int[] iArr = this.g;
        iArr[1] = -1;
        iArr[0] = -1;
        int[] iArr2 = this.h;
        iArr2[0] = -1;
        GLES30.glGenVertexArrays(1, iArr2, 0);
        GLES30.glBindVertexArray(this.h[0]);
        GLES30.glGenBuffers(2, this.g, 0);
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(this.k.length * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = allocateDirect.asFloatBuffer();
        asFloatBuffer.put(this.k);
        asFloatBuffer.position(0);
        this.f221e = GLES30.glGetAttribLocation(this.f220d, "aPosition");
        GLES30.glBindBuffer(34962, this.g[0]);
        GLES30.glBufferData(34962, this.k.length * 4, asFloatBuffer, 35044);
        GLES30.glEnableVertexAttribArray(this.f221e);
        GLES30.glVertexAttribPointer(this.f221e, 2, 5126, false, 8, 0);
        ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(fArr.length * 4);
        allocateDirect2.order(ByteOrder.nativeOrder());
        this.i = allocateDirect2.asFloatBuffer();
        this.i.put(fArr);
        this.i.position(0);
        this.j = false;
        this.f = GLES30.glGetAttribLocation(this.f220d, "arc_tex_coord");
        GLES30.glBindBuffer(34962, this.g[1]);
        GLES30.glBufferData(34962, fArr.length * 4, this.i, 35048);
        GLES30.glEnableVertexAttribArray(this.f);
        GLES30.glVertexAttribPointer(this.f, 2, 5126, false, 8, 0);
        GLES30.glBindBuffer(34962, 0);
        GLES30.glBindVertexArray(0);
    }

    private void b(boolean z) {
        this.i.position(0);
        if (z && !this.j) {
            this.i.put(this.m);
            this.j = true;
        } else if (!z && this.j) {
            this.i.put(this.l);
            this.j = false;
        } else {
            return;
        }
        GLES30.glBindBuffer(34962, this.g[1]);
        this.i.position(0);
        GLES30.glBufferSubData(34962, 0, this.l.length * 4, this.i);
        GLES30.glBindBuffer(34962, 0);
    }

    public void DrawSurfaceWithTex(int i2) {
        GLES30.glUseProgram(this.f220d);
        GLES30.glBindVertexArray(this.h[0]);
        GLES30.glActiveTexture(33984);
        GLES30.glBindTexture(3553, i2);
        GLES30.glUniform1i(this.f219c, 0);
        GLES30.glDrawArrays(6, 0, 4);
        GLES30.glBindTexture(3553, 0);
        GLES30.glBindVertexArray(0);
    }

    public void initGLFun(boolean z) {
        int glCreateShader = GLES30.glCreateShader(35633);
        int glCreateShader2 = GLES30.glCreateShader(35632);
        if (glCreateShader != 0) {
            GLES30.glShaderSource(glCreateShader, "#version 310 es\nlayout(location = 0) in vec2 arc_tex_coord;\nlayout(location = 1) in vec2 aPosition;\nout vec2 vTextureCoord;\nvoid main()\n{\n   vTextureCoord = arc_tex_coord;\n   gl_Position = vec4(aPosition, 1.0, 1.0);\n} ");
            GLES30.glCompileShader(glCreateShader);
            int[] iArr = new int[1];
            GLES30.glGetShaderiv(glCreateShader, 35713, iArr, 0);
            if (iArr[0] == 0) {
                GLES30.glDeleteShader(glCreateShader);
            }
        }
        if (glCreateShader2 != 0) {
            GLES30.glShaderSource(glCreateShader2, "#version 310 es\nprecision mediump float;\nin vec2 vTextureCoord;\nuniform sampler2D sTexture;\nout vec4 fragColor;\nvoid main()\n{ \n   fragColor = texture(sTexture, vTextureCoord); \n}");
            GLES30.glCompileShader(glCreateShader2);
            int[] iArr2 = new int[1];
            GLES30.glGetShaderiv(glCreateShader2, 35713, iArr2, 0);
            if (iArr2[0] == 0) {
                GLES30.glDeleteShader(glCreateShader2);
            }
        }
        this.f220d = GLES30.glCreateProgram();
        int i2 = this.f220d;
        if (i2 != 0) {
            GLES30.glAttachShader(i2, glCreateShader);
            GLES30.glAttachShader(this.f220d, glCreateShader2);
            GLES30.glLinkProgram(this.f220d);
            int[] iArr3 = new int[1];
            GLES30.glGetProgramiv(this.f220d, 35714, iArr3, 0);
            if (iArr3[0] == 0) {
                this.f220d = 0;
            }
        }
        a(z);
        this.f219c = GLES30.glGetUniformLocation(this.f220d, "sTexture");
    }

    public void unInitGLFun() {
        int i2 = this.f220d;
        if (i2 > 0) {
            GLES30.glDeleteProgram(i2);
            this.f220d = -1;
        }
        int[] iArr = this.h;
        if (iArr[0] > 0) {
            GLES30.glDeleteVertexArrays(1, iArr, 0);
            this.h[0] = -1;
        }
        int[] iArr2 = this.g;
        if (iArr2[0] > 0) {
            GLES30.glDeleteBuffers(2, iArr2, 0);
            int[] iArr3 = this.g;
            iArr3[1] = -1;
            iArr3[0] = -1;
        }
    }
}
