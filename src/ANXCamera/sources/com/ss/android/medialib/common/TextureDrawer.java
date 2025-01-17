package com.ss.android.medialib.common;

import android.opengl.GLES20;
import com.ss.android.vesdk.VELogUtil;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class TextureDrawer {
    public static final int DRAW_FUNCTION = 6;
    protected static final String m_fsh = "precision mediump float;\nvarying vec2 texCoord;\nuniform sampler2D inputImageTexture;\nvoid main()\n{\n   gl_FragColor = texture2D(inputImageTexture, texCoord);\n}";
    protected static final String m_vsh = "attribute vec2 vPosition;\nvarying vec2 texCoord;\nuniform mat2 rotation;\nuniform vec2 flipScale;\nvoid main()\n{\n   gl_Position = vec4(vPosition, 0.0, 1.0);\n   texCoord = flipScale * (vPosition / 2.0 * rotation) + 0.5;\n}";
    public static final float[] vertices = {-1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f};
    private int mFlipScaleLoc;
    protected ProgramObject mProgram;
    private int mRotLoc;
    private int mVertBuffer;

    protected TextureDrawer() {
    }

    public static TextureDrawer create() {
        TextureDrawer textureDrawer = new TextureDrawer();
        if (textureDrawer.init(m_vsh, m_fsh)) {
            return textureDrawer;
        }
        VELogUtil.e("TextureDrawer", "TextureDrawer create failed!");
        textureDrawer.release();
        return null;
    }

    public void bindVertexBuffer() {
        GLES20.glBindBuffer(34962, this.mVertBuffer);
    }

    public void drawTexture(int i) {
        drawTexture(i, 3553);
    }

    public void drawTexture(int i, int i2) {
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(i2, i);
        GLES20.glBindBuffer(34962, this.mVertBuffer);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(0, 2, 5126, false, 0, 0);
        this.mProgram.bind();
        GLES20.glDrawArrays(6, 0, 4);
    }

    /* access modifiers changed from: protected */
    public boolean init(String str, String str2) {
        this.mProgram = new ProgramObject();
        if (!this.mProgram.init(str, str2)) {
            this.mProgram.release();
            this.mProgram = null;
            return false;
        }
        this.mProgram.bind();
        this.mRotLoc = this.mProgram.getUniformLoc("rotation");
        this.mFlipScaleLoc = this.mProgram.getUniformLoc("flipScale");
        this.mProgram.bindAttribLocation("vPosition", 0);
        int[] iArr = new int[1];
        GLES20.glGenBuffers(1, iArr, 0);
        this.mVertBuffer = iArr[0];
        GLES20.glBindBuffer(34962, this.mVertBuffer);
        FloatBuffer asFloatBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        asFloatBuffer.put(vertices).position(0);
        GLES20.glBufferData(34962, 32, asFloatBuffer, 35044);
        setRotation(0.0f);
        setFlipScale(1.0f, 1.0f);
        VELogUtil.d("TextureDrawer", "init: success.");
        return true;
    }

    public void release() {
        this.mProgram.release();
        GLES20.glDeleteBuffers(1, new int[]{this.mVertBuffer}, 0);
        this.mProgram = null;
        this.mVertBuffer = 0;
    }

    public void setFlipScale(float f, float f2) {
        this.mProgram.bind();
        GLES20.glUniform2f(this.mFlipScaleLoc, 1.0f / f, 1.0f / f2);
    }

    public void setRotation(float f) {
        double d2 = (double) f;
        float cos = (float) Math.cos(d2);
        float sin = (float) Math.sin(d2);
        this.mProgram.bind();
        GLES20.glUniformMatrix2fv(this.mRotLoc, 1, false, new float[]{cos, sin, -sin, cos}, 0);
    }
}
