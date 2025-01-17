package com.android.camera.effect;

import android.opengl.Matrix;
import com.android.gallery3d.ui.Utils;
import java.util.Stack;

public class GLCanvasState {
    private float mAlpha = 1.0f;
    private float mBlendAlpha = -1.0f;
    private Stack<CanvasStateConfig> mCanvasStateStack = new Stack<>();
    private final float[] mIdentityMatrix = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private float[] mMVPMatrix = new float[16];
    private float[] mModelMatrix = new float[16];
    private float[] mProjectionMatrix = new float[16];
    private float[] mTexMatrix = new float[16];
    private float[] mViewMatrix = new float[16];

    /* access modifiers changed from: private */
    public class CanvasStateConfig {
        float mAlpha = 1.0f;
        float mBlendAlpha = -1.0f;
        float[] mModelMatrix = new float[16];
        float[] mTexMatrix = new float[16];

        public CanvasStateConfig(float[] fArr, float[] fArr2, float f, float f2) {
            System.arraycopy(fArr, 0, this.mModelMatrix, 0, 16);
            System.arraycopy(fArr2, 0, this.mTexMatrix, 0, 16);
            this.mAlpha = f;
            this.mBlendAlpha = f2;
        }

        public float getAlpha() {
            return this.mAlpha;
        }

        public float getBlendAlpha() {
            return this.mBlendAlpha;
        }

        public float[] getModelMatrix() {
            return this.mModelMatrix;
        }

        public float[] getTexMatrix() {
            return this.mTexMatrix;
        }
    }

    public GLCanvasState() {
        Matrix.setIdentityM(this.mModelMatrix, 0);
        Matrix.setIdentityM(this.mViewMatrix, 0);
        Matrix.setIdentityM(this.mProjectionMatrix, 0);
        Matrix.setIdentityM(this.mTexMatrix, 0);
    }

    public void frustum(float f, float f2, float f3, float f4) {
        Matrix.frustumM(this.mProjectionMatrix, 0, f, f2, f3, f4, -1.0f, 1.0f);
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public float getBlendAlpha() {
        return this.mBlendAlpha;
    }

    public float[] getFinalMatrix() {
        Matrix.multiplyMM(this.mMVPMatrix, 0, this.mViewMatrix, 0, this.mModelMatrix, 0);
        float[] fArr = this.mMVPMatrix;
        Matrix.multiplyMM(fArr, 0, this.mProjectionMatrix, 0, fArr, 0);
        return this.mMVPMatrix;
    }

    public float[] getIdentityMatrix() {
        return this.mIdentityMatrix;
    }

    public float[] getModelMatrix() {
        return this.mModelMatrix;
    }

    public float[] getProjectionMatrix() {
        return this.mProjectionMatrix;
    }

    public float[] getTexMatrix() {
        return this.mTexMatrix;
    }

    public float[] getViewMatrix() {
        return this.mViewMatrix;
    }

    public void identityAllM() {
        Matrix.setIdentityM(this.mModelMatrix, 0);
        Matrix.setIdentityM(this.mTexMatrix, 0);
        Matrix.setIdentityM(this.mViewMatrix, 0);
        Matrix.setIdentityM(this.mProjectionMatrix, 0);
    }

    public void identityModelM() {
        Matrix.setIdentityM(this.mModelMatrix, 0);
    }

    public void identityProjectionM() {
        Matrix.setIdentityM(this.mModelMatrix, 0);
    }

    public void identityTexM() {
        Matrix.setIdentityM(this.mTexMatrix, 0);
    }

    public void identityViewM() {
        Matrix.setIdentityM(this.mViewMatrix, 0);
    }

    public void multiplyAlpha(float f) {
        Utils.assertTrue(f >= 0.0f && f <= 1.0f);
        this.mAlpha *= f;
    }

    public void multiplyMatrix(float[] fArr, int i) {
        float[] fArr2 = new float[16];
        Matrix.multiplyMM(fArr2, 0, this.mModelMatrix, 0, fArr, i);
        this.mModelMatrix = fArr2;
    }

    public void ortho(float f, float f2, float f3, float f4) {
        Matrix.orthoM(this.mProjectionMatrix, 0, f, f2, f3, f4, -1.0f, 1.0f);
    }

    public void popState() {
        if (!this.mCanvasStateStack.isEmpty()) {
            CanvasStateConfig pop = this.mCanvasStateStack.pop();
            if (pop != null) {
                this.mModelMatrix = pop.getModelMatrix();
                this.mTexMatrix = pop.getTexMatrix();
                this.mAlpha = pop.getAlpha();
                this.mBlendAlpha = pop.getBlendAlpha();
                return;
            }
            throw new IllegalStateException();
        }
    }

    public void projection(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        if (f != 0.0f && Math.abs(f7) >= Float.MIN_VALUE) {
            multiplyMatrix(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, (-f5) / f7, (-f6) / f7, 0.0f, -1.0f / f7, 0.0f, 0.0f, 0.0f, 1.0f}, 0);
            rotate(f, f2, f3, f4);
        }
    }

    public void pushState() {
        this.mCanvasStateStack.push(new CanvasStateConfig(this.mModelMatrix, this.mTexMatrix, this.mAlpha, this.mBlendAlpha));
    }

    public void rotate(float f, float f2, float f3, float f4) {
        if (f != 0.0f) {
            Matrix.rotateM(this.mModelMatrix, 0, f, f2, f3, f4);
        }
    }

    public void scale(float f, float f2, float f3) {
        Matrix.scaleM(this.mModelMatrix, 0, f, f2, f3);
    }

    public void setAlpha(float f) {
        Utils.assertTrue(f >= 0.0f && f <= 1.0f);
        this.mAlpha = f;
    }

    public void setBlendAlpha(float f) {
        Utils.assertTrue(f >= 0.0f && f <= 1.0f);
        this.mBlendAlpha = f;
    }

    public void setIdentity() {
        Matrix.setIdentityM(this.mModelMatrix, 0);
    }

    public void setLookAt(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        Matrix.setLookAtM(this.mViewMatrix, 0, f, f2, f3, f4, f5, f6, f7, f8, f9);
    }

    public void setTexMatrix(float f, float f2, float f3, float f4) {
        Matrix.setIdentityM(this.mTexMatrix, 0);
        float[] fArr = this.mTexMatrix;
        fArr[0] = f3 - f;
        fArr[5] = f4 - f2;
        fArr[10] = 1.0f;
        fArr[12] = f;
        fArr[13] = f2;
        fArr[15] = 1.0f;
    }

    public void setTexMatrix(float[] fArr) {
        System.arraycopy(fArr, 0, this.mTexMatrix, 0, 16);
    }

    public void translate(float f, float f2) {
        Matrix.translateM(this.mModelMatrix, 0, f, f2, 0.0f);
    }

    public void translate(float f, float f2, float f3) {
        Matrix.translateM(this.mModelMatrix, 0, f, f2, f3);
    }
}
