package com.arcsoft.avatar2.gl;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import java.nio.IntBuffer;

public class TextureHelper {

    /* renamed from: a  reason: collision with root package name */
    private int[] f248a = new int[1];

    public void deleteTexture() {
        GLES20.glDeleteTextures(this.f248a.length, IntBuffer.wrap(this.f248a));
    }

    public int getTextureId() {
        int[] iArr = this.f248a;
        if (iArr[0] != 0) {
            return iArr[0];
        }
        throw new RuntimeException("Error generating texture name.");
    }

    public void initTexture() {
        int[] iArr = this.f248a;
        GLES20.glGenTextures(iArr.length, iArr, 0);
        int[] iArr2 = this.f248a;
        if (iArr2[0] != 0) {
            GLES20.glBindTexture(3553, iArr2[0]);
            GLES20.glTexParameteri(3553, 10241, 9728);
            GLES20.glTexParameteri(3553, 10240, 9728);
            GLES20.glBindTexture(3553, 0);
            return;
        }
        throw new RuntimeException("Error generating texture name.");
    }

    public void loadTexture(Bitmap bitmap) {
        GLES20.glBindTexture(3553, this.f248a[0]);
        GLUtils.texImage2D(3553, 0, bitmap, 0);
        GLES20.glBindTexture(3553, 0);
    }
}
