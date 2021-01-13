package com.android.camera.dualvideo.render;

import com.android.gallery3d.ui.BasicTexture;

/* access modifiers changed from: package-private */
public interface IMiscRenderable {
    float getAlpha();

    BasicTexture getBasicTexture();

    int getTexType();

    void setAlphaWithAnim(float f, boolean z);
}
