package com.android.camera.dualvideo.render;

import com.android.camera.effect.draw_mode.DrawExtTexAttribute;

public interface IRenderable {
    static default int compare(IRenderable iRenderable, IRenderable iRenderable2) {
        int i = Compose.PRIORITYS[Compose.getIndex(iRenderable.getRenderComposeType())][Compose.getIndex(iRenderable.getLastRenderComposeType())];
        int i2 = Compose.PRIORITYS[Compose.getIndex(iRenderable2.getRenderComposeType())][Compose.getIndex(iRenderable2.getLastRenderComposeType())];
        if (i > i2) {
            return -1;
        }
        return i < i2 ? 1 : 0;
    }

    void alphaIn6PatchTag(boolean z);

    void alphaInSelectedFrame(boolean z);

    float get6PatchTagAlpha();

    int get6patchComposeType();

    float getAlpha();

    int getLastRenderComposeType();

    DrawExtTexAttribute getRenderAttri();

    int getRenderComposeType();

    float getSelectFrameAlpha();

    int getSelectedType();

    boolean isAnimating();

    boolean isFacingFront();

    boolean isPressedIn6Patch();

    boolean isVisible();

    void onKeyDown();

    void onKeyUp();

    void setBasicPreviewTransMatrix(float[] fArr);

    void setComposeTypeWithAnimation(int i, RegionHelper regionHelper, boolean z);

    void setRenderAttri(DrawExtTexAttribute drawExtTexAttribute);

    void setSelectedtypeWithAnim(int i, boolean z);

    void setVisibilityWithAnim(boolean z, boolean z2);

    void updateRenderAttri(RegionHelper regionHelper);
}
