package com.android.camera.effect.renders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.opengl.GLES20;
import com.android.camera.CameraAppImpl;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.ShaderUtil;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.draw_mode.DrawBasicTexAttribute;
import com.android.camera.effect.draw_mode.DrawIntTexAttribute;
import com.android.camera.log.Log;
import com.android.camera.sticker.glutils.GlUtil;
import com.android.gallery3d.ui.GLCanvas;
import java.io.IOException;
import java.io.InputStream;

public class LightEffectRender extends PixelEffectRender {
    private static final int FILTER_TYPE_BRIGHT_RED = 49;
    private static final int FILTER_TYPE_DAZZLING = 47;
    private static final int FILTER_TYPE_DREAMLAND = 50;
    private static final int FILTER_TYPE_GORGEOUS = 48;
    private static final int FILTER_TYPE_LANSHAN = 46;
    private static final int FILTER_TYPE_NEON = 42;
    private static final int FILTER_TYPE_NOSTALGIA = 44;
    private static final int FILTER_TYPE_PHANTOM = 43;
    private static final int FILTER_TYPE_RAINBOW = 45;
    private static final String TAG = "LightEffectRender";
    private int mFilterTexture = 0;
    private boolean mIsSnapshot = false;
    private int mLastRotation = -1;
    private Bitmap mLightBitmap;
    private int mLightTexture = 0;
    private boolean mNeedBlur = false;
    private boolean mNeedColorSeparation = false;
    private boolean mNeedFilter = false;
    private boolean mNeedNoise = false;
    private Bitmap mNoiseBitmap;
    private int mNoiseTexture = 0;
    private int mPreviewHeight;
    private int mPreviewWidth;
    private int mUniformFilterTexture;
    private int mUniformLightTexture;
    private int mUniformNeedBlur;
    private int mUniformNeedColorSeparation;
    private int mUniformNeedFilter;
    private int mUniformNeedNoise;
    private int mUniformNoiseTexture;
    private int mUniformVerticalScreen;
    private boolean mVerticalScreen = true;

    /* access modifiers changed from: private */
    public class CropOperation {
        Matrix matrix;
        Rect rect;

        private CropOperation() {
        }
    }

    public @interface LightEffectId {
    }

    public LightEffectRender(GLCanvas gLCanvas, @LightEffectId int i) {
        super(gLCanvas);
        prepareLightEffect(i);
    }

    private Bitmap getBitmapFromAssert(String str) {
        try {
            InputStream open = CameraAppImpl.getAndroidContext().getAssets().open(str);
            if (open.available() == 0) {
                return null;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap decodeStream = BitmapFactory.decodeStream(open, null, options);
            open.close();
            return decodeStream;
        } catch (IOException unused) {
            String str2 = TAG;
            Log.e(str2, "get assert failed, path:" + str);
            return null;
        }
    }

    private CropOperation getCropOperationForCapture(int i) {
        int i2;
        int i3;
        Matrix matrix = null;
        CropOperation cropOperation = new CropOperation();
        int width = this.mLightBitmap.getWidth();
        int height = this.mLightBitmap.getHeight();
        int i4 = (i + 90) % 360;
        if (i4 != 0) {
            if (i4 == 90) {
                i3 = this.mPreviewHeight;
                i2 = (width - i3) / 2;
                matrix = new Matrix();
                matrix.setRotate(270.0f);
            } else if (i4 == 180) {
                matrix = new Matrix();
                matrix.setRotate(180.0f);
            } else if (i4 != 270) {
                Log.w(TAG, "unknown degree:" + i4);
            } else {
                i3 = this.mPreviewHeight;
                i2 = (width - i3) / 2;
                matrix = new Matrix();
                matrix.setRotate(90.0f);
            }
            cropOperation.rect = new Rect(i2, 0, i3, height);
            cropOperation.matrix = matrix;
            return cropOperation;
        }
        i3 = width;
        i2 = 0;
        cropOperation.rect = new Rect(i2, 0, i3, height);
        cropOperation.matrix = matrix;
        return cropOperation;
    }

    private CropOperation getCropOperationForPreview(int i) {
        int i2;
        int i3;
        Matrix matrix = null;
        CropOperation cropOperation = new CropOperation();
        int width = this.mLightBitmap.getWidth();
        int height = this.mLightBitmap.getHeight();
        if (i != 0) {
            if (i == 90) {
                matrix = new Matrix();
                matrix.setRotate(270.0f);
            } else if (i == 180) {
                i3 = this.mPreviewWidth;
                i2 = (width - i3) / 2;
                matrix = new Matrix();
                matrix.setRotate(180.0f);
            } else if (i != 270) {
                Log.w(TAG, "unknown degree:" + i);
            } else {
                matrix = new Matrix();
                matrix.setRotate(90.0f);
            }
            i2 = 0;
            cropOperation.rect = new Rect(i2, 0, width, height);
            cropOperation.matrix = matrix;
            return cropOperation;
        }
        i3 = this.mPreviewWidth;
        i2 = (width - i3) / 2;
        width = i3;
        cropOperation.rect = new Rect(i2, 0, width, height);
        cropOperation.matrix = matrix;
        return cropOperation;
    }

    private void prepareLightEffect(@LightEffectId int i) {
        String str;
        String str2 = "";
        switch (i) {
            case 42:
                str = "effect/light_effect/NEON.png";
                break;
            case 43:
                str = "effect/light_effect/PHANTOM.png";
                break;
            case 44:
                this.mNeedNoise = true;
                str = "effect/light_effect/NOSTALGIA.png";
                str2 = "effect/light_effect/NOSTALGIA_NOISE.png";
                break;
            case 45:
                str = "effect/light_effect/RAINBOW.png";
                break;
            case 46:
                str = "effect/light_effect/LANSHAN.png";
                break;
            case 47:
                str = "effect/light_effect/DAZZLING.png";
                break;
            case 48:
                str = "effect/light_effect/GORGEOUS.png";
                break;
            case 49:
                str = "effect/light_effect/BRIGHT_RED.png";
                break;
            case 50:
                str = "effect/light_effect/DREAMLAND.png";
                break;
            default:
                str = str2;
                break;
        }
        if (!str.isEmpty()) {
            this.mLightBitmap = getBitmapFromAssert(str);
        }
        if (!str2.isEmpty()) {
            this.mNoiseBitmap = getBitmapFromAssert(str2);
        }
        String str3 = TAG;
        Log.d(str3, "prepareLightEffect: " + i);
    }

    private void safeDeleteTexture(int i) {
        GLES20.glDeleteTextures(1, new int[]{i}, 0);
    }

    private void updateLightTexture() {
        Bitmap bitmap;
        int orientation = EffectController.getInstance().getOrientation();
        if (this.mLastRotation != orientation) {
            CropOperation cropOperationForCapture = this.mIsSnapshot ? getCropOperationForCapture(orientation) : getCropOperationForPreview(orientation);
            Bitmap bitmap2 = this.mLightBitmap;
            if (bitmap2 != null) {
                Rect rect = cropOperationForCapture.rect;
                Bitmap createBitmap = Bitmap.createBitmap(bitmap2, rect.left, rect.top, rect.right, rect.bottom, cropOperationForCapture.matrix, false);
                int i = this.mLightTexture;
                if (i != 0) {
                    safeDeleteTexture(i);
                }
                this.mLightTexture = GlUtil.createTexture(3553, createBitmap);
            }
            if (this.mNeedNoise && (bitmap = this.mNoiseBitmap) != null) {
                Rect rect2 = cropOperationForCapture.rect;
                Bitmap createBitmap2 = Bitmap.createBitmap(bitmap, rect2.left, rect2.top, rect2.right, rect2.bottom, cropOperationForCapture.matrix, false);
                int i2 = this.mNoiseTexture;
                if (i2 != 0) {
                    safeDeleteTexture(i2);
                }
                this.mNoiseTexture = GlUtil.createTexture(3553, createBitmap2);
            }
            this.mLastRotation = orientation;
            Log.d(TAG, String.format("updateLightTexture rotation(%d) bitmap(%d,%d,%d,%d) preview(%d,%d) mIsSnapshot(%b)", Integer.valueOf(orientation), Integer.valueOf(cropOperationForCapture.rect.left), Integer.valueOf(cropOperationForCapture.rect.top), Integer.valueOf(cropOperationForCapture.rect.right), Integer.valueOf(cropOperationForCapture.rect.bottom), Integer.valueOf(this.mPreviewWidth), Integer.valueOf(this.mPreviewHeight), Boolean.valueOf(this.mIsSnapshot)));
        }
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.ShaderRender
    public void destroy() {
        int i = this.mLightTexture;
        if (i != 0) {
            safeDeleteTexture(i);
            this.mLightTexture = 0;
        }
        int i2 = this.mNoiseTexture;
        if (i2 != 0) {
            safeDeleteTexture(i2);
            this.mNoiseTexture = 0;
        }
        Bitmap bitmap = this.mLightBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mLightBitmap = null;
        }
        Bitmap bitmap2 = this.mNoiseBitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.mNoiseBitmap = null;
        }
        super.destroy();
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.PixelEffectRender, com.android.camera.effect.renders.ShaderRender
    public boolean draw(DrawAttribute drawAttribute) {
        if (!isAttriSupported(drawAttribute.getTarget())) {
            return false;
        }
        int target = drawAttribute.getTarget();
        if (target == 5) {
            DrawBasicTexAttribute drawBasicTexAttribute = (DrawBasicTexAttribute) drawAttribute;
            this.mFilterTexture = drawBasicTexAttribute.mBasicTexture.getId();
            this.mPreviewWidth = drawBasicTexAttribute.mWidth;
            this.mPreviewHeight = drawBasicTexAttribute.mHeight;
            this.mIsSnapshot = drawBasicTexAttribute.mIsSnapshot;
        } else if (target != 6) {
            String str = TAG;
            Log.w(str, "attr unsupported, target:" + drawAttribute.getTarget());
        } else {
            DrawIntTexAttribute drawIntTexAttribute = (DrawIntTexAttribute) drawAttribute;
            this.mFilterTexture = drawIntTexAttribute.mTexId;
            this.mPreviewWidth = drawIntTexAttribute.mWidth;
            this.mPreviewHeight = drawIntTexAttribute.mHeight;
            this.mIsSnapshot = drawIntTexAttribute.mIsSnapshot;
        }
        updateLightTexture();
        return super.draw(drawAttribute);
    }

    @Override // com.android.camera.effect.renders.ShaderRender
    public String getFragShaderString() {
        return ShaderUtil.loadFromAssetsFile("frag_light.c");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.PixelEffectRender, com.android.camera.effect.renders.ShaderRender
    public void initShader() {
        super.initShader();
        this.mUniformFilterTexture = GLES20.glGetUniformLocation(this.mProgram, "text_filter");
        this.mUniformLightTexture = GLES20.glGetUniformLocation(this.mProgram, "text_light");
        this.mUniformNoiseTexture = GLES20.glGetUniformLocation(this.mProgram, "text_noise");
        this.mUniformNeedFilter = GLES20.glGetUniformLocation(this.mProgram, "needFilter");
        this.mUniformNeedNoise = GLES20.glGetUniformLocation(this.mProgram, "needNoise");
        this.mUniformNeedBlur = GLES20.glGetUniformLocation(this.mProgram, "needBlur");
        this.mUniformNeedColorSeparation = GLES20.glGetUniformLocation(this.mProgram, "needColorSeparation");
        this.mUniformVerticalScreen = GLES20.glGetUniformLocation(this.mProgram, "verticalScreen");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.PixelEffectRender
    public void initShaderValue(boolean z) {
        super.initShaderValue(z);
        int i = this.mFilterTexture;
        if (i != 0) {
            bindTexture(i, 33985);
            GLES20.glUniform1i(this.mUniformFilterTexture, 1);
        }
        int i2 = this.mLightTexture;
        if (i2 != 0) {
            bindTexture(i2, 33986);
            GLES20.glUniform1i(this.mUniformLightTexture, 2);
        }
        int i3 = this.mNoiseTexture;
        if (i3 != 0) {
            bindTexture(i3, 33987);
            GLES20.glUniform1i(this.mUniformNoiseTexture, 3);
        }
        GLES20.glUniform1i(this.mUniformNeedFilter, this.mNeedFilter ? 1 : 0);
        GLES20.glUniform1i(this.mUniformNeedNoise, this.mNeedNoise ? 1 : 0);
        GLES20.glUniform1i(this.mUniformNeedBlur, this.mNeedBlur ? 1 : 0);
        GLES20.glUniform1i(this.mUniformNeedColorSeparation, this.mNeedColorSeparation ? 1 : 0);
        GLES20.glUniform1i(this.mUniformVerticalScreen, this.mVerticalScreen ? 1 : 0);
    }
}
