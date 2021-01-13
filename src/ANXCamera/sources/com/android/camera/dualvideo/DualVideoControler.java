package com.android.camera.dualvideo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.os.ConditionVariable;
import android.util.Size;
import android.view.MotionEvent;
import android.view.Surface;
import androidx.core.view.ViewCompat;
import com.android.camera.CameraScreenNail;
import com.android.camera.CameraSettings;
import com.android.camera.SurfaceTextureScreenNail;
import com.android.camera.Util;
import com.android.camera.constant.ColorConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.dualvideo.render.DualVideoRenderManager;
import com.android.camera.dualvideo.render.DualVideoUtil;
import com.android.camera.dualvideo.render.EaseOutAnim;
import com.android.camera.dualvideo.render.IRenderable;
import com.android.camera.dualvideo.render.MiscTextureManager;
import com.android.camera.dualvideo.render.Timer;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.draw_mode.DrawBasicTexAttribute;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.effect.draw_mode.DrawLineAttribute;
import com.android.camera.effect.draw_mode.FillRectAttribute;
import com.android.camera.log.Log;
import com.android.camera.module.VideoModule;
import com.android.camera.module.encoder.RenderHandler;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.gallery3d.ui.BasicTexture;
import com.android.gallery3d.ui.ExtTexture;
import com.android.gallery3d.ui.GLCanvas;
import com.android.gallery3d.ui.GLCanvasImpl;
import com.android.gallery3d.ui.GLPaint;
import com.android.gallery3d.ui.RawTexture;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class DualVideoControler implements SurfaceTextureScreenNail.ExternalFrameProcessor {
    private static final int BLUR_DURATION;
    private static final int BLUR_HEIGHT;
    public static final int LEFT_MARGIN = Util.dpToPixel(14.5f);
    private static final int SELECT_LINE_WIDTH = Util.dpToPixel(2.18f);
    private static final int SKIP_FRAMES;
    private static final String TAG;
    private Rect mAnimDstRect = Util.getDisplayRect(0);
    private Rect mAnimStartRect;
    private CameraIDManager mCameraIdManager = new CameraIDManager();
    private CaptureResult mCaptureResult;
    private EaseOutAnim mEaseOutAnim;
    private boolean mIsComposeSwitching;
    VideoModule.JpegPictureCallback mJpegCallback;
    private DualVideoRenderListener mListener;
    private DrawExtTexAttribute mMainDrawAttribute;
    private MiscTextureManager mMiscTexManager = new MiscTextureManager();
    private boolean mNeedRecording = false;
    private boolean mNeedUpdateBlurTex = false;
    private RenderHandler mRecordRenderHandler;
    private Object mRenderLock = new Object();
    private DualVideoRenderManager mRenderManager;
    private Resources mResources;
    private int mSkipFrame = 2;
    private Timer mSnapAnimTimer = new Timer();
    private int mSnapOrientation = 0;
    private AtomicBoolean mSnapPending = new AtomicBoolean(false);
    private ImageReader mSnapReader;
    private RenderHandler mSnapRenderHandler;
    private ExtTexture mSubExtTexture;
    private boolean mSubFrameReady = false;
    private Size mSubPreviewSize;
    private Surface mSubSurface;
    private SurfaceTexture mSubSurfaceTexture;
    private ConditionVariable mUpdateBlurConditionVar = new ConditionVariable();
    private boolean mWeNeedBlurAnim = true;

    public interface DualVideoRenderListener {
        void onRenderRequestNeeded();

        void onSubImageAvailable();
    }

    private Rect RectIterpolate(Rect rect, Rect rect2, float f) {
        float f2 = 1.0f - f;
        return new Rect((int) ((((float) rect.left) * f2) + (((float) rect2.left) * f)), (int) ((((float) rect.top) * f2) + (((float) rect2.top) * f)), (int) ((((float) rect.right) * f2) + (((float) rect2.right) * f)), (int) ((((float) rect.bottom) * f2) + (((float) rect2.bottom) * f)));
    }

    static /* synthetic */ void a(IRenderable iRenderable, UserSelectData userSelectData) {
        userSelectData.mPreviewType = iRenderable.getRenderComposeType();
    }

    static /* synthetic */ void a(StringBuilder sb, IRenderable iRenderable) {
        sb.append(" id: " + iRenderable.getRenderAttri().mExtTexture.getId());
    }

    static /* synthetic */ boolean a(float f, float f2, IRenderable iRenderable) {
        DrawExtTexAttribute renderAttri = iRenderable.getRenderAttri();
        int i = renderAttri.mX;
        boolean z = f >= ((float) i) && f <= ((float) (i + renderAttri.mWidth));
        int i2 = renderAttri.mY;
        return z && ((f2 > ((float) i2) ? 1 : (f2 == ((float) i2) ? 0 : -1)) >= 0 && (f2 > ((float) (i2 + renderAttri.mHeight)) ? 1 : (f2 == ((float) (i2 + renderAttri.mHeight)) ? 0 : -1)) <= 0) && iRenderable.getRenderComposeType() != 10;
    }

    static /* synthetic */ boolean a(IRenderable iRenderable) {
        return iRenderable.isVisible();
    }

    static /* synthetic */ boolean a(ConcurrentHashMap concurrentHashMap, UserSelectData userSelectData) {
        return userSelectData.mCameraID == ((Integer) concurrentHashMap.get(101)).intValue();
    }

    static /* synthetic */ boolean b(float f, float f2, IRenderable iRenderable) {
        DrawExtTexAttribute renderAttri = iRenderable.getRenderAttri();
        int i = renderAttri.mX;
        boolean z = f >= ((float) i) && f <= ((float) (i + renderAttri.mWidth));
        int i2 = renderAttri.mY;
        return z && ((f2 > ((float) i2) ? 1 : (f2 == ((float) i2) ? 0 : -1)) >= 0 && (f2 > ((float) (i2 + renderAttri.mHeight)) ? 1 : (f2 == ((float) (i2 + renderAttri.mHeight)) ? 0 : -1)) <= 0) && iRenderable.getRenderComposeType() != 10 && iRenderable.isVisible();
    }

    static /* synthetic */ boolean b(ConcurrentHashMap concurrentHashMap, UserSelectData userSelectData) {
        return userSelectData.mCameraID == ((Integer) concurrentHashMap.get(100)).intValue();
    }

    static /* synthetic */ boolean c(IRenderable iRenderable) {
        return iRenderable.getRenderComposeType() == 10;
    }

    static /* synthetic */ boolean d(IRenderable iRenderable) {
        return iRenderable.isFacingFront() && iRenderable.getRenderComposeType() == 13;
    }

    private void drawBlur(GLCanvasImpl gLCanvasImpl, CameraScreenNail cameraScreenNail, Rect rect) {
        cameraScreenNail.drawBlurTexture(gLCanvasImpl, rect.left, rect.top, rect.width(), rect.height());
        this.mListener.onRenderRequestNeeded();
    }

    private void drawBlurCover(GLCanvas gLCanvas, IRenderable iRenderable) {
        DrawExtTexAttribute renderAttri = iRenderable.getRenderAttri();
        gLCanvas.draw(new DrawBasicTexAttribute(iRenderable.isFacingFront() ? this.mMiscTexManager.getTexture(12) : this.mMiscTexManager.getTexture(11), renderAttri.mX, renderAttri.mY, renderAttri.mWidth, renderAttri.mHeight));
    }

    private void drawBottomMask(GLCanvas gLCanvas) {
        if (this.mRenderManager.hasMiniPreview() && isRecording()) {
            Rect displayRect = Util.getDisplayRect(1);
            gLCanvas.draw(new FillRectAttribute((float) displayRect.left, (float) displayRect.bottom, (float) displayRect.width(), (float) gLCanvas.getHeight(), ViewCompat.MEASURED_STATE_MASK));
        }
    }

    private void drawDarkCornerFrame(GLCanvas gLCanvas, IRenderable iRenderable) {
        if (iRenderable.getRenderComposeType() >= 20) {
            DrawExtTexAttribute renderAttri = iRenderable.getRenderAttri();
            BasicTexture texture = this.mMiscTexManager.getTexture(9);
            BasicTexture texture2 = this.mMiscTexManager.getTexture(10);
            gLCanvas.draw(new DrawBasicTexAttribute(texture, renderAttri.mX, renderAttri.mY, renderAttri.mWidth, texture.getHeight()));
            gLCanvas.draw(new DrawBasicTexAttribute(texture2, renderAttri.mX, (renderAttri.mY + renderAttri.mHeight) - texture2.getHeight(), renderAttri.mWidth, texture2.getHeight()));
        }
    }

    private boolean drawDualVideo(GLCanvas gLCanvas) {
        SurfaceTexture surfaceTexture = this.mSubSurfaceTexture;
        if (surfaceTexture == null) {
            return false;
        }
        surfaceTexture.updateTexImage();
        if (!this.mSubFrameReady && !this.mIsComposeSwitching) {
            return false;
        }
        if (this.mRenderManager == null) {
            this.mMiscTexManager.init(gLCanvas, this.mResources);
            initSubSurfaceTexture(gLCanvas);
            this.mRenderManager = new DualVideoRenderManager(this.mSubSurfaceTexture, this.mMainDrawAttribute, this.mSubExtTexture, this.mRenderLock);
        }
        drawForRecording();
        drawForPreview(gLCanvas);
        waitVideoDrawFinish();
        return true;
    }

    private void drawForPreview(GLCanvas gLCanvas) {
        List<IRenderable> list = (List) this.mRenderManager.getRenderableList().stream().filter(h.INSTANCE).sorted(y.INSTANCE).collect(Collectors.toList());
        if (!this.mSnapAnimTimer.isValid()) {
            for (IRenderable iRenderable : list) {
                DrawExtTexAttribute renderAttri = iRenderable.getRenderAttri();
                gLCanvas.getState().setAlpha(iRenderable.getAlpha());
                if (shouldDrawBlur(iRenderable)) {
                    drawBlurCover(gLCanvas, iRenderable);
                } else {
                    gLCanvas.draw(renderAttri);
                }
                drawGridLine(gLCanvas, iRenderable);
                gLCanvas.getState().setAlpha(1.0f);
                drawSelectedFrame(gLCanvas, iRenderable);
                drawSixPatchTag(gLCanvas, iRenderable);
                drawDarkCornerFrame(gLCanvas, iRenderable);
            }
        }
        drawBottomMask(gLCanvas);
        updateBlurTex(gLCanvas, this.mRenderManager.getRenderableList());
    }

    private void drawForRecording() {
        if (this.mNeedRecording) {
            List<DrawAttribute> renderableListForRecord = this.mRenderManager.getRenderableListForRecord();
            this.mRecordRenderHandler.draw(renderableListForRecord);
            if (this.mSnapPending.get()) {
                this.mSnapRenderHandler.draw(renderableListForRecord);
                this.mSnapPending.set(false);
            }
        }
    }

    private void drawGridLine(GLCanvas gLCanvas, IRenderable iRenderable) {
        int renderComposeType;
        boolean z = DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_REFERENCE_LINE, false);
        if ((iRenderable.isAnimating() && !iRenderable.isPressedIn6Patch()) || !z || (renderComposeType = iRenderable.getRenderComposeType()) == 10) {
            return;
        }
        if (renderComposeType != 13) {
            drawHorizontalLine(gLCanvas, iRenderable, 0.5f);
            drawVerticalLine(gLCanvas, iRenderable, 0.5f);
            return;
        }
        drawHorizontalLine(gLCanvas, iRenderable, 0.33333334f);
        drawVerticalLine(gLCanvas, iRenderable, 0.33333334f);
        drawHorizontalLine(gLCanvas, iRenderable, 0.6666667f);
        drawVerticalLine(gLCanvas, iRenderable, 0.6666667f);
    }

    private void drawHorizontalLine(GLCanvas gLCanvas, IRenderable iRenderable, float f) {
        DrawExtTexAttribute renderAttri = iRenderable.getRenderAttri();
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(770, 771);
        int i = renderAttri.mX;
        int i2 = renderAttri.mY;
        int i3 = renderAttri.mHeight;
        gLCanvas.draw(new DrawLineAttribute((float) i, ((float) i2) + (((float) i3) * f), (float) (i + renderAttri.mWidth), ((float) i2) + (((float) i3) * f), new GLPaint((float) Util.dpToPixel(0.73f), DualVideoUtil.LINE_COLOR)));
        GLES20.glDisable(3042);
    }

    private void drawSelectedFrame(GLCanvas gLCanvas, IRenderable iRenderable) {
        BasicTexture basicTexture;
        if (iRenderable.getSelectFrameAlpha() >= 0.01f) {
            gLCanvas.getState().setAlpha(iRenderable.getSelectFrameAlpha());
            DrawExtTexAttribute renderAttri = iRenderable.getRenderAttri();
            int i = renderAttri.mX;
            int i2 = SELECT_LINE_WIDTH;
            int i3 = renderAttri.mY;
            gLCanvas.draw(new DrawLineAttribute((float) ((i2 / 2) + i), (float) i3, (float) (i + (i2 / 2)), (float) (i3 + renderAttri.mHeight), new GLPaint((float) i2, ColorConstant.COLOR_COMMON_SELECTED)));
            int i4 = renderAttri.mX;
            int i5 = renderAttri.mWidth;
            int i6 = SELECT_LINE_WIDTH;
            int i7 = renderAttri.mY;
            gLCanvas.draw(new DrawLineAttribute((float) ((i4 + i5) - (i6 / 2)), (float) i7, (float) ((i4 + i5) - (i6 / 2)), (float) (i7 + renderAttri.mHeight), new GLPaint((float) i6, ColorConstant.COLOR_COMMON_SELECTED)));
            gLCanvas.draw(new DrawBasicTexAttribute(this.mMiscTexManager.getTexture(14), renderAttri.mX, renderAttri.mY, renderAttri.mWidth, this.mMiscTexManager.getTexture(14).getHeight()));
            gLCanvas.draw(new DrawBasicTexAttribute(this.mMiscTexManager.getTexture(15), renderAttri.mX, (renderAttri.mY + renderAttri.mHeight) - this.mMiscTexManager.getTexture(15).getHeight(), renderAttri.mWidth, this.mMiscTexManager.getTexture(15).getHeight()));
            BasicTexture texture = this.mMiscTexManager.getTexture(13);
            gLCanvas.draw(new DrawBasicTexAttribute(texture, (renderAttri.mX + renderAttri.mWidth) - texture.getWidth(), (renderAttri.mY + renderAttri.mHeight) - texture.getHeight(), texture.getWidth(), texture.getHeight()));
            int selectedType = iRenderable.getSelectedType();
            if (selectedType == 1) {
                basicTexture = this.mMiscTexManager.getTexture(7);
            } else if (selectedType == 2) {
                basicTexture = this.mMiscTexManager.getTexture(8);
            } else {
                return;
            }
            gLCanvas.getState().pushState();
            gLCanvas.getState().setTexMatrix(this.mMiscTexManager.getmAnimTexTransMatrix());
            gLCanvas.draw(new DrawBasicTexAttribute(basicTexture, (renderAttri.mX + renderAttri.mWidth) - basicTexture.getWidth(), (renderAttri.mY + renderAttri.mHeight) - basicTexture.getHeight(), basicTexture.getWidth(), basicTexture.getHeight()));
            gLCanvas.getState().popState();
            gLCanvas.getState().setAlpha(1.0f);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0069, code lost:
        if (r7 != 270) goto L_0x00df;
     */
    private void drawSixPatchTag(GLCanvas gLCanvas, IRenderable iRenderable) {
        BasicTexture basicTexture;
        switch (iRenderable.get6patchComposeType()) {
            case 20:
                basicTexture = this.mMiscTexManager.getTexture(1);
                break;
            case 21:
                basicTexture = this.mMiscTexManager.getTexture(2);
                break;
            case 22:
                basicTexture = this.mMiscTexManager.getTexture(3);
                break;
            case 23:
                basicTexture = this.mMiscTexManager.getTexture(4);
                break;
            case 24:
                basicTexture = this.mMiscTexManager.getTexture(5);
                break;
            case 25:
                basicTexture = this.mMiscTexManager.getTexture(6);
                break;
            default:
                return;
        }
        gLCanvas.getState().pushState();
        gLCanvas.getState().setAlpha(iRenderable.get6PatchTagAlpha());
        gLCanvas.getState().setTexMatrix(this.mMiscTexManager.getmTexTransMatrix());
        int texOrientation = this.mMiscTexManager.getTexOrientation();
        if (texOrientation != 0) {
            if (texOrientation != 90) {
                if (texOrientation != 180) {
                }
            }
            gLCanvas.draw(new DrawBasicTexAttribute(basicTexture, Util.dpToPixel(7.27f) + iRenderable.getRenderAttri().mX, ((iRenderable.getRenderAttri().mY + iRenderable.getRenderAttri().mHeight) - basicTexture.getWidth()) - Util.dpToPixel(7.27f), basicTexture.getHeight(), basicTexture.getWidth()));
            gLCanvas.getState().popState();
        }
        gLCanvas.draw(new DrawBasicTexAttribute(basicTexture, iRenderable.getRenderAttri().mX + Util.dpToPixel(10.91f), ((iRenderable.getRenderAttri().mY + iRenderable.getRenderAttri().mHeight) - basicTexture.getHeight()) - Util.dpToPixel(4.36f), basicTexture.getWidth(), basicTexture.getHeight()));
        gLCanvas.getState().popState();
    }

    private void drawVerticalLine(GLCanvas gLCanvas, IRenderable iRenderable, float f) {
        DrawExtTexAttribute renderAttri = iRenderable.getRenderAttri();
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(770, 771);
        int i = renderAttri.mX;
        int i2 = renderAttri.mWidth;
        int i3 = renderAttri.mY;
        gLCanvas.draw(new DrawLineAttribute((((float) i2) * f) + ((float) i), (float) i3, ((float) i) + (((float) i2) * f), (float) (i3 + renderAttri.mHeight), new GLPaint((float) Util.dpToPixel(0.73f), DualVideoUtil.LINE_COLOR)));
        GLES20.glDisable(3042);
    }

    static /* synthetic */ boolean f(IRenderable iRenderable) {
        return iRenderable.isVisible() && iRenderable.isFacingFront();
    }

    private Size findSubPreviewSize() {
        return Camera2DataContainer.getInstance().isFrontCameraId(DataRepository.dataItemRunning().getComponentRunningDualVideo().getIds().get(101).intValue()) ? DualVideoUtil.FRONT_PREVIE : DualVideoUtil.BACK_PREVIE;
    }

    static /* synthetic */ boolean h(IRenderable iRenderable) {
        return iRenderable.isVisible() && !iRenderable.isFacingFront();
    }

    static /* synthetic */ DrawExtTexAttribute i(IRenderable iRenderable) {
        return iRenderable.getRenderAttri();
    }

    private void initSubSurfaceTexture(GLCanvas gLCanvas) {
        if (this.mSubExtTexture == null) {
            Log.d(TAG, "initSubSurfaceTexture: ");
            createSubCameraSurfaceIfNeed();
            this.mSubExtTexture = new ExtTexture();
            this.mSubExtTexture.onBind(gLCanvas);
            this.mSubExtTexture.setSize(this.mSubPreviewSize.getWidth(), this.mSubPreviewSize.getHeight());
            this.mSubSurfaceTexture.detachFromGLContext();
            this.mSubSurfaceTexture.attachToGLContext(this.mSubExtTexture.getId());
        }
    }

    static /* synthetic */ boolean k(IRenderable iRenderable) {
        return iRenderable.isVisible() && !iRenderable.isFacingFront();
    }

    private void printTexId(List<IRenderable> list) {
        StringBuilder sb = new StringBuilder();
        sb.append(" tex id:  ");
        list.stream().forEachOrdered(new n(sb));
        String str = TAG;
        Log.d(str, "printTexId: " + sb.toString());
    }

    private void releaseRecordingRelated() {
        RenderHandler renderHandler = this.mRecordRenderHandler;
        if (renderHandler != null) {
            renderHandler.release();
            this.mRecordRenderHandler = null;
        }
        ImageReader imageReader = this.mSnapReader;
        if (imageReader != null) {
            imageReader.close();
            this.mSnapReader = null;
            this.mSnapRenderHandler.release();
            this.mSnapRenderHandler = null;
        }
    }

    private void releaseSurfaceTexture(GLCanvas gLCanvas) {
        Log.d(TAG, "releaseSurfaceTexture: ");
        SurfaceTexture surfaceTexture = this.mSubSurfaceTexture;
        if (surfaceTexture != null) {
            gLCanvas.deleteSurfaceTexture(surfaceTexture);
            this.mSubSurfaceTexture = null;
        }
        Surface surface = this.mSubSurface;
        if (surface != null) {
            surface.release();
            this.mSubSurface = null;
        }
        ExtTexture extTexture = this.mSubExtTexture;
        if (extTexture != null) {
            extTexture.recycle();
            this.mSubExtTexture = null;
        }
    }

    private void saveJpeg(Image image) {
        Bitmap bitmap;
        if (image != null) {
            int width = image.getWidth();
            int height = image.getHeight();
            Image.Plane[] planes = image.getPlanes();
            ByteBuffer buffer = planes[0].getBuffer();
            int pixelStride = planes[0].getPixelStride();
            int rowStride = planes[0].getRowStride() - (pixelStride * width);
            Bitmap createBitmap = Bitmap.createBitmap((rowStride / pixelStride) + width, height, Bitmap.Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(buffer);
            if (rowStride != 0) {
                bitmap = Bitmap.createBitmap(createBitmap, 0, 0, width, createBitmap.getHeight());
                createBitmap.recycle();
            } else {
                bitmap = createBitmap;
            }
            image.close();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Util.appendExifToBitmap(bitmap, byteArrayOutputStream, this.mSnapOrientation, this.mCaptureResult);
            bitmap.recycle();
            VideoModule.JpegPictureCallback jpegPictureCallback = this.mJpegCallback;
            if (jpegPictureCallback != null) {
                jpegPictureCallback.onPictureTaken(byteArrayOutputStream.toByteArray(), null);
                this.mJpegCallback = null;
            }
            try {
                byteArrayOutputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    private boolean shouldDrawBlur(IRenderable iRenderable) {
        return this.mIsComposeSwitching && (iRenderable.getRenderComposeType() >= 20 || iRenderable.getLastRenderComposeType() >= 20);
    }

    private void updateBlurTex(GLCanvas gLCanvas, List<IRenderable> list) {
        if (this.mNeedUpdateBlurTex) {
            boolean anyMatch = this.mRenderManager.getVisibileRenderList().stream().anyMatch(d.INSTANCE);
            String str = TAG;
            Log.d(str, "updateBlurTex: contain front: " + anyMatch);
            if (anyMatch) {
                DualVideoUtil.copyPreviewTexture(gLCanvas, (RawTexture) this.mMiscTexManager.getTexture(11), list.stream().filter(g.INSTANCE).findFirst().map(c.INSTANCE).orElse(null));
                DualVideoUtil.copyPreviewTexture(gLCanvas, (RawTexture) this.mMiscTexManager.getTexture(12), list.stream().filter(w.INSTANCE).findFirst().map(k.INSTANCE).orElse(null));
                DualVideoUtil.blurTex(gLCanvas, (RawTexture) this.mMiscTexManager.getTexture(11));
                DualVideoUtil.blurTex(gLCanvas, (RawTexture) this.mMiscTexManager.getTexture(12));
            } else {
                DualVideoUtil.copyPreviewTexture(gLCanvas, (RawTexture) this.mMiscTexManager.getTexture(11), list.stream().filter(e.INSTANCE).findFirst().map(p.INSTANCE).orElse(null));
                DualVideoUtil.blurTex(gLCanvas, (RawTexture) this.mMiscTexManager.getTexture(11));
            }
            this.mNeedUpdateBlurTex = false;
            this.mUpdateBlurConditionVar.open();
        }
    }

    private void waitVideoDrawFinish() {
        if (this.mNeedRecording) {
            this.mRecordRenderHandler.waitDrawFinish();
            this.mSnapRenderHandler.waitDrawFinish();
        }
    }

    public /* synthetic */ void a(SurfaceTexture surfaceTexture) {
        if (this.mSubFrameReady) {
            return;
        }
        if (this.mSkipFrame > 0) {
            Log.d(TAG, "sub frame skipped: ");
            this.mSkipFrame--;
            return;
        }
        Log.d(TAG, "subFrameReady");
        this.mSubFrameReady = true;
        enableContinuousRender(false);
    }

    public /* synthetic */ void a(ImageReader imageReader) {
        saveJpeg(imageReader.acquireNextImage());
    }

    public /* synthetic */ void a(DualVideoRenderManager dualVideoRenderManager) {
        dualVideoRenderManager.changeTexture(this.mCameraIdManager.get6Patch2CameraId());
    }

    public /* synthetic */ Integer b(IRenderable iRenderable) {
        return iRenderable.getRenderAttri().mExtTexture.getId() == this.mSubExtTexture.getId() ? DataRepository.dataItemRunning().getComponentRunningDualVideo().getIds().get(101) : DataRepository.dataItemRunning().getComponentRunningDualVideo().getIds().get(100);
    }

    public void captureVideoSnapshot(VideoModule.JpegPictureCallback jpegPictureCallback, int i, CaptureResult captureResult) {
        if (!this.mSnapPending.get()) {
            this.mSnapPending.set(true);
            this.mJpegCallback = jpegPictureCallback;
            this.mCaptureResult = captureResult;
            this.mSnapAnimTimer.init(60);
            if (i == -1) {
                this.mSnapOrientation = 0;
            } else {
                this.mSnapOrientation = i;
            }
        }
    }

    public Surface createSubCameraSurfaceIfNeed() {
        if (this.mSubSurface == null) {
            Log.d(TAG, "createSubCameraSurfaceIfNeed: ");
            this.mSubPreviewSize = findSubPreviewSize();
            this.mSubSurfaceTexture = new SurfaceTexture(0);
            this.mSubSurfaceTexture.setDefaultBufferSize(this.mSubPreviewSize.getWidth(), this.mSubPreviewSize.getHeight());
            this.mSubSurface = new Surface(this.mSubSurfaceTexture);
            this.mSubSurfaceTexture.setOnFrameAvailableListener(new q(this));
        } else if (!this.mSubPreviewSize.equals(findSubPreviewSize())) {
            this.mSubPreviewSize = findSubPreviewSize();
            this.mSubSurfaceTexture.setDefaultBufferSize(this.mSubPreviewSize.getWidth(), this.mSubPreviewSize.getHeight());
        }
        return this.mSubSurface;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        DualVideoRenderManager dualVideoRenderManager;
        if (!this.mSubFrameReady || (dualVideoRenderManager = this.mRenderManager) == null) {
            return false;
        }
        if (dualVideoRenderManager.hasMiniPreview()) {
            return this.mRenderManager.updateMiniWindowLocation(motionEvent);
        }
        if (!this.mRenderManager.is6PatchWindow()) {
            return false;
        }
        this.mRenderManager.selectItem(motionEvent);
        return true;
    }

    public void enableContinuousRender(boolean z) {
        String str = TAG;
        Log.d(str, "enableContinuousRender: " + z);
        this.mIsComposeSwitching = z;
        switchTexture();
    }

    public boolean expandBottom() {
        synchronized (this.mRenderLock) {
            if (this.mRenderManager == null) {
                return false;
            }
            boolean expandBottom = this.mRenderManager.expandBottom();
            saveSettingsForComposeTypeChanged();
            return expandBottom;
        }
    }

    public boolean expandOrShrinkTop() {
        synchronized (this.mRenderLock) {
            if (this.mRenderManager == null) {
                return false;
            }
            boolean expandOrShrinkTop = this.mRenderManager.expandOrShrinkTop();
            saveSettingsForComposeTypeChanged();
            return expandOrShrinkTop;
        }
    }

    public int getIdByPosition(float f, float f2) {
        DualVideoRenderManager dualVideoRenderManager = this.mRenderManager;
        if (dualVideoRenderManager == null) {
            return -1;
        }
        return ((Integer) dualVideoRenderManager.getVisibileRenderList().stream().filter(new l(f, f2)).findFirst().map(new m(this)).orElse(-1)).intValue();
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor
    public int getProcessorType() {
        return 1;
    }

    public int getRenderComposeTypeByPosition(float f, float f2) {
        DualVideoRenderManager dualVideoRenderManager = this.mRenderManager;
        if (dualVideoRenderManager == null) {
            return 13;
        }
        return ((Integer) dualVideoRenderManager.getRenderableList().stream().filter(new i(f, f2)).findFirst().map(x.INSTANCE).orElse(13)).intValue();
    }

    public boolean hasMiniComposeType() {
        DualVideoRenderManager dualVideoRenderManager = this.mRenderManager;
        return dualVideoRenderManager != null && !dualVideoRenderManager.getRenderableList().isEmpty() && this.mRenderManager.getRenderableList().stream().anyMatch(v.INSTANCE);
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor
    public boolean isProcessorReady() {
        return true;
    }

    public boolean isRecording() {
        return this.mNeedRecording;
    }

    public boolean isSwitching() {
        DualVideoRenderManager dualVideoRenderManager = this.mRenderManager;
        if (dualVideoRenderManager == null) {
            return false;
        }
        return this.mIsComposeSwitching || dualVideoRenderManager.isAnimating();
    }

    public boolean isZoomEnabled() {
        DualVideoRenderManager dualVideoRenderManager = this.mRenderManager;
        if (dualVideoRenderManager == null) {
            return false;
        }
        return !dualVideoRenderManager.getRenderableList().stream().anyMatch(r.INSTANCE);
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor
    public void onDrawFrame(GLCanvasImpl gLCanvasImpl, CameraScreenNail cameraScreenNail) {
        gLCanvasImpl.clearBuffer();
        float[] fArr = new float[16];
        cameraScreenNail.getSurfaceTexture().getTransformMatrix(fArr);
        Rect displayRect = cameraScreenNail.getDisplayRect();
        if (displayRect == null) {
            Log.d(TAG, "onDrawFrame: disaplay rect is null");
            return;
        }
        if (!DataRepository.dataItemFeature().tj()) {
            this.mWeNeedBlurAnim = false;
        }
        if (this.mWeNeedBlurAnim) {
            if (displayRect.height() == BLUR_HEIGHT) {
                this.mWeNeedBlurAnim = false;
            } else {
                if (this.mEaseOutAnim == null) {
                    this.mEaseOutAnim = new EaseOutAnim(200);
                    this.mAnimStartRect = new Rect(displayRect);
                }
                if (this.mEaseOutAnim.isFinished()) {
                    this.mWeNeedBlurAnim = false;
                    this.mEaseOutAnim = null;
                } else {
                    drawBlur(gLCanvasImpl, cameraScreenNail, RectIterpolate(this.mAnimStartRect, this.mAnimDstRect, this.mEaseOutAnim.getRatio()));
                }
            }
        }
        if (!this.mWeNeedBlurAnim && !onDrawFrame(gLCanvasImpl, new DrawExtTexAttribute(cameraScreenNail.getExtTexture(), fArr, displayRect.left, displayRect.top, displayRect.width(), displayRect.height()))) {
            if (DataRepository.dataItemFeature().tj()) {
                drawBlur(gLCanvasImpl, cameraScreenNail, this.mAnimDstRect);
            } else {
                drawBlur(gLCanvasImpl, cameraScreenNail, displayRect);
            }
        }
    }

    public boolean onDrawFrame(GLCanvas gLCanvas, DrawExtTexAttribute drawExtTexAttribute) {
        boolean drawDualVideo;
        this.mMainDrawAttribute = drawExtTexAttribute;
        synchronized (this.mRenderLock) {
            drawDualVideo = drawDualVideo(gLCanvas);
        }
        if (this.mIsComposeSwitching) {
            this.mListener.onRenderRequestNeeded();
        }
        return drawDualVideo;
    }

    public void release(GLCanvas gLCanvas) {
        Log.d(TAG, "release: ");
        synchronized (this.mRenderLock) {
            this.mSubFrameReady = false;
            this.mSkipFrame = 2;
            this.mJpegCallback = null;
            releaseRecordingRelated();
            releaseSurfaceTexture(gLCanvas);
            this.mMiscTexManager.release();
        }
    }

    public void resetFrameReady() {
        this.mSubFrameReady = false;
        this.mSkipFrame = 2;
    }

    public void saveSettingsForComposeTypeChanged() {
        this.mRenderManager.printRenderList();
        ArrayList<UserSelectData> arrayList = DataRepository.dataItemRunning().getComponentRunningDualVideo().getmSelectedData();
        ConcurrentHashMap<Integer, Integer> ids = DataRepository.dataItemRunning().getComponentRunningDualVideo().getIds();
        Iterator<IRenderable> it = this.mRenderManager.getVisibileRenderList().iterator();
        while (it.hasNext()) {
            IRenderable next = it.next();
            if (next.getRenderAttri().mExtTexture.getId() == this.mSubExtTexture.getId()) {
                arrayList.stream().filter(new j(ids)).forEach(new o(next));
            } else {
                arrayList.stream().filter(new f(ids)).forEach(new t(next));
            }
        }
    }

    public void setListener(DualVideoRenderListener dualVideoRenderListener) {
        this.mListener = dualVideoRenderListener;
    }

    public void setOrientation(int i) {
        this.mMiscTexManager.setTexTransDegree(i);
    }

    public void setmResources(Resources resources) {
        this.mResources = resources;
    }

    public void startRecording(EGLContext eGLContext, Surface surface) {
        Log.d(TAG, "startRecording: ");
        synchronized (this.mRenderLock) {
            if (this.mRecordRenderHandler == null) {
                this.mRecordRenderHandler = RenderHandler.createHandler(TAG, DualVideoUtil.OUTPUT_SIZE.getWidth(), DualVideoUtil.OUTPUT_SIZE.getHeight());
                this.mRecordRenderHandler.setEglContext(eGLContext, surface, true);
            }
            if (this.mSnapReader == null) {
                this.mSnapReader = ImageReader.newInstance(DualVideoUtil.OUTPUT_SIZE.getWidth(), DualVideoUtil.OUTPUT_SIZE.getHeight(), 1, 1);
                this.mSnapReader.setOnImageAvailableListener(new u(this), null);
            }
            if (this.mSnapRenderHandler == null) {
                this.mSnapRenderHandler = RenderHandler.createHandler(TAG, DualVideoUtil.OUTPUT_SIZE.getWidth(), DualVideoUtil.OUTPUT_SIZE.getHeight());
                this.mSnapRenderHandler.setEglContext(eGLContext, this.mSnapReader.getSurface(), true);
            }
            this.mNeedRecording = true;
        }
    }

    public void stopRecording() {
        Log.d(TAG, "stopRecording: ");
        synchronized (this.mRenderLock) {
            this.mNeedRecording = false;
            this.mJpegCallback = null;
            releaseRecordingRelated();
        }
    }

    public void switch6patch2preview() {
        Log.d(TAG, "switch6patch2preview: ");
        if (this.mRenderManager != null && !this.mIsComposeSwitching) {
            triggerUpdateBlurTex();
            this.mRenderManager.switch6patch2preview();
            this.mRenderManager.printRenderList();
        }
    }

    public void switchPreviewTo6Patch() {
        Log.d(TAG, "switchPreviewTo6Patch: ");
        DualVideoRenderManager dualVideoRenderManager = this.mRenderManager;
        if (dualVideoRenderManager != null && !this.mIsComposeSwitching) {
            dualVideoRenderManager.switchPreviewTo6Patch();
            this.mRenderManager.printRenderList();
        }
    }

    public void switchTexture() {
        Optional.ofNullable(this.mRenderManager).ifPresent(new s(this));
    }

    public boolean switchTopBottom() {
        Log.d(TAG, "switchTopBottom: ");
        DualVideoRenderManager dualVideoRenderManager = this.mRenderManager;
        if (dualVideoRenderManager == null) {
            return false;
        }
        boolean switchTopBottom = dualVideoRenderManager.switchTopBottom();
        saveSettingsForComposeTypeChanged();
        return switchTopBottom;
    }

    public void triggerUpdateBlurTex() {
        Log.d(TAG, "triggerUpdateBlurTex: ");
        if (!this.mNeedUpdateBlurTex) {
            this.mUpdateBlurConditionVar.close();
            this.mNeedUpdateBlurTex = true;
            this.mUpdateBlurConditionVar.block(500);
        }
    }
}
