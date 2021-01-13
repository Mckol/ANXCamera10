package com.android.camera.effect;

import com.android.camera.effect.renders.BasicRender;
import com.android.camera.effect.renders.RenderGroup;
import com.android.gallery3d.ui.BaseGLCanvas;

public class SnapshotCanvas extends BaseGLCanvas {
    private final int BASIC_RENDER_INDEX = 1;
    private final int EFFECT_GROUP_INDEX = 0;

    public SnapshotCanvas() {
        this.mRenderCaches = new RenderGroup(this);
        this.mRenderGroup = new RenderGroup(this);
        this.mRenderGroup.addRender(this.mRenderCaches);
        this.mRenderGroup.addRender(new BasicRender(this));
        initialize();
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void deleteProgram() {
        super.deleteProgram();
    }

    public BasicRender getBasicRender() {
        return (BasicRender) this.mRenderGroup.getRenderByIndex(1);
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public RenderGroup getEffectRenderGroup() {
        return (RenderGroup) this.mRenderGroup.getRenderByIndex(0);
    }
}
