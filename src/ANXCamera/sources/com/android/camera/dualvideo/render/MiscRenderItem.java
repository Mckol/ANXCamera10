package com.android.camera.dualvideo.render;

import com.android.gallery3d.ui.BasicTexture;

/* access modifiers changed from: package-private */
public class MiscRenderItem implements IMiscRenderable {
    private float mAlpha;
    private EaseOutAnim mAnim;
    private float mAnimDestAlpha;
    private BasicTexture mTex;
    private int mType;

    public MiscRenderItem(int i, float f, BasicTexture basicTexture) {
        this.mTex = basicTexture;
        this.mType = i;
        this.mAlpha = f;
    }

    private void animateAlpha(float f) {
        this.mAnimDestAlpha = f;
        this.mAnim = new EaseOutAnim(f > 0.5f ? 200 : 100);
    }

    private synchronized void setAlpha(float f) {
        this.mAlpha = f;
    }

    @Override // com.android.camera.dualvideo.render.IMiscRenderable
    public float getAlpha() {
        EaseOutAnim easeOutAnim = this.mAnim;
        if (easeOutAnim == null) {
            return this.mAlpha;
        }
        if (easeOutAnim.isFinished()) {
            setAlpha(this.mAnimDestAlpha);
            this.mAnim = null;
            return this.mAlpha;
        }
        float ratio = this.mAnim.getRatio();
        return (this.mAlpha * (1.0f - ratio)) + (this.mAnimDestAlpha * ratio);
    }

    @Override // com.android.camera.dualvideo.render.IMiscRenderable
    public synchronized BasicTexture getBasicTexture() {
        return this.mTex;
    }

    @Override // com.android.camera.dualvideo.render.IMiscRenderable
    public int getTexType() {
        return this.mType;
    }

    @Override // com.android.camera.dualvideo.render.IMiscRenderable
    public void setAlphaWithAnim(float f, boolean z) {
        if (Math.abs(this.mAlpha - f) >= 0.02f) {
            if (z) {
                animateAlpha(f);
            } else {
                setAlpha(f);
            }
        }
    }
}
