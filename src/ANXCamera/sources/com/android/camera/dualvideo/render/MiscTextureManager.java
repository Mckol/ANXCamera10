package com.android.camera.dualvideo.render;

import android.content.res.Resources;
import android.opengl.Matrix;
import com.android.camera.R;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.BasicTexture;
import com.android.gallery3d.ui.BitmapTexture;
import com.android.gallery3d.ui.GLCanvas;
import com.android.gallery3d.ui.RawTexture;
import com.ss.android.vesdk.VEResult;
import java.util.ArrayList;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.IIntValueProperty;

public class MiscTextureManager {
    public static final int TEX_BACK_BLUR = 11;
    public static final int TEX_DARK_CORNER_BOTTOM = 10;
    public static final int TEX_DARK_CORNER_TOP = 9;
    public static final int TEX_FRONT_BLUR = 12;
    public static final int TEX_ROUND_CORNER_BOTTOM = 15;
    public static final int TEX_ROUND_CORNER_TOP = 14;
    public static final int TEX_SELECTED_BG = 13;
    public static final int TEX_SELECTED_FIRST = 7;
    public static final int TEX_SELECTED_SECOND = 8;
    public static final int TEX_TAG_06X = 1;
    public static final int TEX_TAG_10X = 5;
    public static final int TEX_TAG_1X = 2;
    public static final int TEX_TAG_2X = 3;
    public static final int TEX_TAG_5X = 4;
    public static final int TEX_TAG_FRONT = 6;
    private float[] mAnimTexTransMatrix = new float[16];
    private ArrayList<IMiscRenderable> mMiscRenderList = new ArrayList<>();
    private int mTexOrientation = 0;
    private float[] mTexTransMatrix = new float[16];

    public MiscTextureManager() {
        Matrix.setIdentityM(this.mTexTransMatrix, 0);
        Matrix.setIdentityM(this.mAnimTexTransMatrix, 0);
    }

    static /* synthetic */ boolean a(int i, IMiscRenderable iMiscRenderable) {
        return iMiscRenderable.getTexType() == i;
    }

    private void animTexTransMatrix(int i) {
        final int i2 = i - this.mTexOrientation;
        if (i2 < -180) {
            i2 += 360;
        } else if (i2 > 180) {
            i2 += VEResult.TER_EGL_BAD_MATCH;
        }
        AnimConfig animConfig = new AnimConfig();
        animConfig.setEase(6, 300.0f);
        animConfig.addListeners(new TransitionListener() {
            /* class com.android.camera.dualvideo.render.MiscTextureManager.AnonymousClass1 */
            int srcRotation = MiscTextureManager.this.mTexOrientation;

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
                int i2 = this.srcRotation + ((int) ((((float) i) / 1000.0f) * ((float) i2)));
                Matrix.setIdentityM(MiscTextureManager.this.mAnimTexTransMatrix, 0);
                MiscTextureManager miscTextureManager = MiscTextureManager.this;
                miscTextureManager.rotateTexTransMatrix(miscTextureManager.mAnimTexTransMatrix, i2);
            }
        });
        Folme.useValue("animTexTrans").setTo((Object) 0).to(1000, animConfig);
    }

    static /* synthetic */ boolean b(int i, IMiscRenderable iMiscRenderable) {
        return iMiscRenderable.getTexType() == i;
    }

    public IMiscRenderable getRenderable(int i) {
        return (IMiscRenderable) this.mMiscRenderList.stream().filter(new T(i)).findFirst().get();
    }

    public synchronized int getTexOrientation() {
        return this.mTexOrientation;
    }

    public BasicTexture getTexture(int i) {
        return ((IMiscRenderable) this.mMiscRenderList.stream().filter(new P(i)).findFirst().get()).getBasicTexture();
    }

    public synchronized float[] getmAnimTexTransMatrix() {
        return this.mAnimTexTransMatrix;
    }

    public synchronized float[] getmTexTransMatrix() {
        return this.mTexTransMatrix;
    }

    public void init(GLCanvas gLCanvas, Resources resources) {
        this.mMiscRenderList.add(new MiscRenderItem(1, 1.0f, new BitmapTexture(DualVideoUtil.textAsBitmap("0.5X", -1), false, false)));
        this.mMiscRenderList.add(new MiscRenderItem(2, 1.0f, new BitmapTexture(DualVideoUtil.textAsBitmap("1X", -1), false, false)));
        this.mMiscRenderList.add(new MiscRenderItem(3, 1.0f, new BitmapTexture(DualVideoUtil.textAsBitmap("2X", -1), false, false)));
        this.mMiscRenderList.add(new MiscRenderItem(4, 1.0f, new BitmapTexture(DualVideoUtil.textAsBitmap("5X", -1), false, false)));
        this.mMiscRenderList.add(new MiscRenderItem(5, 1.0f, new BitmapTexture(DualVideoUtil.textAsBitmap("10X", -1), false, false)));
        this.mMiscRenderList.add(new MiscRenderItem(6, 1.0f, new BitmapTexture(DualVideoUtil.textAsBitmap(resources.getString(R.string.pref_multi_camera_dual_video_front), -1), false, false)));
        this.mMiscRenderList.add(new MiscRenderItem(13, 1.0f, new BitmapTexture(DualVideoUtil.getBitmap(resources, R.drawable.ic_dual_video_selected_bg), false, false)));
        this.mMiscRenderList.add(new MiscRenderItem(7, 1.0f, new BitmapTexture(DualVideoUtil.getBitmap(resources, R.drawable.ic_dual_video_selected_1), false, false)));
        this.mMiscRenderList.add(new MiscRenderItem(8, 1.0f, new BitmapTexture(DualVideoUtil.getBitmap(resources, R.drawable.ic_dual_video_selected_2), false, false)));
        this.mMiscRenderList.add(new MiscRenderItem(14, 1.0f, new BitmapTexture(DualVideoUtil.getBitmap(resources, R.drawable.ic_dual_video_round_corner_top), false, false)));
        this.mMiscRenderList.add(new MiscRenderItem(15, 1.0f, new BitmapTexture(DualVideoUtil.getBitmap(resources, R.drawable.ic_dual_video_round_corner_bottom), false, false)));
        this.mMiscRenderList.add(new MiscRenderItem(9, 1.0f, new BitmapTexture(DualVideoUtil.getBitmap(resources, R.drawable.ic_dual_video_dark_corner_top), false, false)));
        this.mMiscRenderList.add(new MiscRenderItem(10, 1.0f, new BitmapTexture(DualVideoUtil.getBitmap(resources, R.drawable.ic_dual_video_dark_corner_bottom), false, false)));
        RawTexture rawTexture = new RawTexture(DualVideoUtil.OUTPUT_SIZE.getWidth() / 8, DualVideoUtil.OUTPUT_SIZE.getHeight() / 8, true);
        RawTexture rawTexture2 = new RawTexture(DualVideoUtil.OUTPUT_SIZE.getWidth() / 8, DualVideoUtil.OUTPUT_SIZE.getHeight() / 8, true);
        this.mMiscRenderList.add(new MiscRenderItem(11, 1.0f, rawTexture));
        this.mMiscRenderList.add(new MiscRenderItem(12, 1.0f, rawTexture2));
        this.mMiscRenderList.forEach(new Q(gLCanvas));
    }

    public void release() {
        this.mMiscRenderList.forEach(S.INSTANCE);
        this.mMiscRenderList.clear();
    }

    public synchronized void rotateTexTransMatrix(float[] fArr, int i) {
        if (i != 0) {
            Matrix.translateM(fArr, 0, 0.5f, 0.5f, 0.0f);
            Matrix.rotateM(fArr, 0, (float) i, 0.0f, 0.0f, 1.0f);
            Matrix.translateM(fArr, 0, -0.5f, -0.5f, 0.0f);
        }
    }

    public synchronized void setTexTransDegree(int i) {
        Log.d("TAG", "setTexTransDegree: src: " + this.mTexOrientation + "  dst: " + i);
        animTexTransMatrix(i);
        Matrix.setIdentityM(this.mTexTransMatrix, 0);
        rotateTexTransMatrix(this.mTexTransMatrix, i);
        this.mTexOrientation = i;
    }
}
