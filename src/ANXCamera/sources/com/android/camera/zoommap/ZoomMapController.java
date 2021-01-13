package com.android.camera.zoommap;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Looper;
import android.util.Size;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.UiThread;
import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.data.config.ComponentConfigRatio;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.ui.CameraRootView;
import com.android.camera.ui.GLTextureView;
import com.android.camera2.vendortag.struct.MarshalQueryableChiRect;
import com.android.gallery3d.ui.ExtTexture;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;
import miui.view.animation.CubicEaseOutInterpolator;

public class ZoomMapController {
    private static final String TAG = "ZoomMap";
    private static final float ZOOM_RATIO_THRESHOLD = 15.0f;
    private static HashMap<String, Size> sPreviewSize = new HashMap<>(3);
    private ActivityBase mActivityBase;
    private ZoomMapCanvas mGLCanvas;
    private GLTextureView mGlTextureView;
    private AtomicBoolean mIsHidden = new AtomicBoolean(false);
    private AnimatorSet mLastAnimatorSet;
    private Handler mMainHandler;
    private Rect mMapRect = new Rect();
    private AtomicBoolean mNeedDrawMap = new AtomicBoolean(false);
    private String mPreviewRatio;
    private Size mPreviewSize;
    private RegionHelper mRegionHelper;
    private ZoomMapRenderManager mRenderManager;
    private AtomicBoolean mSubFrameReady = new AtomicBoolean(false);
    private Surface mZoomMapSurface;
    private SurfaceTexture mZoomMapSurfaceTexture;
    private ExtTexture mZoomMapTexture;
    private float mZoomRatio;

    /* access modifiers changed from: private */
    public static class PipWindowRender implements GLSurfaceView.Renderer {
        private WeakReference<ZoomMapController> mZoomMapControllerRef;

        public PipWindowRender(ZoomMapController zoomMapController) {
            this.mZoomMapControllerRef = new WeakReference<>(zoomMapController);
        }

        public void onDrawFrame(GL10 gl10) {
            ZoomMapController zoomMapController = this.mZoomMapControllerRef.get();
            if (zoomMapController != null) {
                zoomMapController.drawZoomMap();
            }
        }

        public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        }

        public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        }
    }

    static {
        sPreviewSize.put(ComponentConfigRatio.RATIO_1X1, new Size(228, 228));
        sPreviewSize.put(ComponentConfigRatio.RATIO_4X3, new Size(304, 228));
        sPreviewSize.put(ComponentConfigRatio.RATIO_16X9, new Size(404, 228));
    }

    public ZoomMapController(ActivityBase activityBase, boolean z) {
        this.mActivityBase = activityBase;
        initGLTextureView(activityBase);
        this.mMainHandler = new Handler(Looper.getMainLooper());
        if (ModuleManager.isSquareModule()) {
            this.mPreviewRatio = ComponentConfigRatio.RATIO_1X1;
        } else {
            this.mPreviewRatio = CameraSettings.getPictureSizeRatioString(163);
        }
        HashMap<String, Size> hashMap = sPreviewSize;
        this.mPreviewSize = hashMap.getOrDefault(this.mPreviewRatio, hashMap.get(ComponentConfigRatio.RATIO_16X9));
        this.mRegionHelper = new RegionHelper(this.mGlTextureView, new Size(this.mPreviewSize.getHeight(), this.mPreviewSize.getWidth()), z);
    }

    @UiThread
    private void addPipWindowTextureViewIfNeeded() {
        if (this.mGlTextureView.getParent() == null) {
            Log.d(TAG, "addPipWindowTextureViewIfNeeded");
            this.mGlTextureView.setTranslationX(0.0f);
            this.mGlTextureView.setTranslationY(0.0f);
            Rect pipWindowDefaultLocation = this.mRegionHelper.getPipWindowDefaultLocation();
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(pipWindowDefaultLocation.width(), pipWindowDefaultLocation.height());
            layoutParams.setMargins(pipWindowDefaultLocation.left, pipWindowDefaultLocation.top, 0, 0);
            ((FrameLayout) ((CameraRootView) this.mActivityBase.findViewById(R.id.camera_app_root)).findViewById(R.id.zoom_map_content)).addView(this.mGlTextureView, 0, layoutParams);
        }
    }

    private void animatorInMapView() {
        Log.d(TAG, "animatorInMapView");
        AnimatorSet animatorSet = this.mLastAnimatorSet;
        if (animatorSet != null) {
            animatorSet.end();
            this.mLastAnimatorSet = null;
        }
        ObjectAnimator duration = ObjectAnimator.ofFloat(this.mGlTextureView, "scaleX", 0.8f, 1.0f).setDuration(200L);
        duration.setInterpolator(new CubicEaseOutInterpolator());
        ObjectAnimator duration2 = ObjectAnimator.ofFloat(this.mGlTextureView, "scaleY", 0.8f, 1.0f).setDuration(200L);
        duration2.setInterpolator(new CubicEaseOutInterpolator());
        ObjectAnimator duration3 = ObjectAnimator.ofFloat(this.mGlTextureView, "alpha", 0.0f, 1.0f).setDuration(200L);
        duration3.setInterpolator(new CubicEaseOutInterpolator());
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(duration, duration2, duration3);
        animatorSet2.addListener(new AnimatorListenerAdapter() {
            /* class com.android.camera.zoommap.ZoomMapController.AnonymousClass2 */

            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                ZoomMapController.this.mGlTextureView.setVisibility(0);
            }
        });
        animatorSet2.start();
        this.mLastAnimatorSet = animatorSet2;
    }

    private void animatorOutMapView() {
        Log.d(TAG, "animatorOutMapView");
        AnimatorSet animatorSet = this.mLastAnimatorSet;
        if (animatorSet != null) {
            animatorSet.end();
            this.mLastAnimatorSet = null;
        }
        ObjectAnimator duration = ObjectAnimator.ofFloat(this.mGlTextureView, "scaleX", 1.0f, 0.8f).setDuration(200L);
        duration.setInterpolator(new CubicEaseOutInterpolator());
        ObjectAnimator duration2 = ObjectAnimator.ofFloat(this.mGlTextureView, "scaleY", 1.0f, 0.8f).setDuration(200L);
        duration2.setInterpolator(new CubicEaseOutInterpolator());
        ObjectAnimator duration3 = ObjectAnimator.ofFloat(this.mGlTextureView, "alpha", 1.0f, 0.0f).setDuration(100L);
        duration3.setInterpolator(new CubicEaseOutInterpolator());
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(duration, duration2, duration3);
        animatorSet2.start();
        this.mLastAnimatorSet = animatorSet2;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void drawZoomMap() {
        if (!this.mSubFrameReady.get()) {
            Log.w(TAG, "drawZoomMap ignore, frame not ready");
        } else if (!this.mNeedDrawMap.get()) {
            Log.w(TAG, "drawZoomMap ignore, don't need draw");
        } else {
            initZoomMapSurfaceTextureIfNeeded();
            if (this.mRenderManager == null) {
                this.mRenderManager = new ZoomMapRenderManager(this.mZoomMapSurfaceTexture, this.mZoomMapTexture, this.mPreviewSize);
            }
            this.mRenderManager.drawZoomMap(this.mGLCanvas);
        }
    }

    private void initGLTextureView(ActivityBase activityBase) {
        this.mGlTextureView = new GLTextureView(activityBase);
        this.mGlTextureView.setOnTouchListener(new View.OnTouchListener() {
            /* class com.android.camera.zoommap.ZoomMapController.AnonymousClass1 */
            int mLastX;
            int mLastY;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                int action = motionEvent.getAction();
                if (action != 0) {
                    if (action != 1) {
                        if (action != 2) {
                            if (action != 3) {
                                return false;
                            }
                        } else if (!ZoomMapController.this.mRegionHelper.isHovering()) {
                            return false;
                        } else {
                            ZoomMapController.this.mRegionHelper.updateTranslation((int) (view.getTranslationX() + ((float) (rawX - this.mLastX))), (int) (view.getTranslationY() + ((float) (rawY - this.mLastY))));
                            this.mLastX = rawX;
                            this.mLastY = rawY;
                            return true;
                        }
                    }
                    if (!ZoomMapController.this.mRegionHelper.isHovering()) {
                        return false;
                    }
                    ZoomMapController.this.mRegionHelper.setHovering(false);
                    if (ZoomMapController.this.mRegionHelper.moveToEdge()) {
                        CameraStatUtils.trackZoomMapRemoveWindow();
                        Log.d(ZoomMapController.TAG, "hidden pip window");
                        ZoomMapController.this.mIsHidden.set(true);
                        ZoomMapController.this.mNeedDrawMap.set(false);
                    }
                    return true;
                }
                ZoomMapController.this.mRegionHelper.setHovering(true);
                ZoomMapController.this.mRegionHelper.initAnimConfig();
                this.mLastX = rawX;
                this.mLastY = rawY;
                return true;
            }
        });
    }

    private void initZoomMapSurfaceTextureIfNeeded() {
        if (this.mZoomMapTexture == null) {
            this.mGLCanvas = new ZoomMapCanvas();
            this.mGLCanvas.setSize(this.mPreviewSize.getHeight(), this.mPreviewSize.getWidth());
            Log.w(TAG, "initZoomMapSurfaceTextureIfNeeded " + this.mPreviewSize.getWidth() + "x" + this.mPreviewSize.getHeight());
            this.mZoomMapTexture = new ExtTexture();
            this.mZoomMapTexture.onBind(this.mGLCanvas);
            this.mZoomMapTexture.setSize(this.mPreviewSize.getWidth(), this.mPreviewSize.getHeight());
            this.mZoomMapSurfaceTexture.detachFromGLContext();
            this.mZoomMapSurfaceTexture.attachToGLContext(this.mZoomMapTexture.getId());
        }
    }

    private void removePipWindowTextureView() {
        if (this.mGlTextureView != null) {
            this.mMainHandler.post(new c(this));
        }
    }

    public /* synthetic */ void J(boolean z) {
        addPipWindowTextureViewIfNeeded();
        if (z) {
            CameraStatUtils.trackZoomMapShow();
            this.mGlTextureView.requestRender();
            animatorInMapView();
            return;
        }
        animatorOutMapView();
    }

    public /* synthetic */ EGLContext Xd() {
        return this.mActivityBase.getGLView().getEGLContext();
    }

    public /* synthetic */ void Yd() {
        Log.d(TAG, "removePipWindowTextureView");
        ViewGroup viewGroup = (ViewGroup) this.mGlTextureView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this.mGlTextureView);
        }
    }

    public /* synthetic */ void b(SurfaceTexture surfaceTexture) {
        this.mSubFrameReady.set(true);
        this.mGlTextureView.requestRender();
    }

    public Surface createZoomMapSurfaceIfNeeded() {
        if (this.mZoomMapSurface == null) {
            Log.d(TAG, "createZoomMapSurfaceIfNeeded " + this.mPreviewSize);
            this.mZoomMapSurfaceTexture = new SurfaceTexture(0);
            this.mZoomMapSurfaceTexture.setDefaultBufferSize(this.mPreviewSize.getWidth(), this.mPreviewSize.getHeight());
            this.mZoomMapSurface = new Surface(this.mZoomMapSurfaceTexture);
            this.mZoomMapSurfaceTexture.setOnFrameAvailableListener(new d(this));
            if (this.mGlTextureView.getRenderer() == null) {
                PipWindowRender pipWindowRender = new PipWindowRender(this);
                this.mGlTextureView.setEGLContextClientVersion(2);
                this.mGlTextureView.setEGLShareContextGetter(new b(this));
                this.mGlTextureView.setRenderer(pipWindowRender);
                this.mGlTextureView.onResume();
                this.mGlTextureView.setRenderMode(0);
            }
        }
        return this.mZoomMapSurface;
    }

    public void onModuleDestroy() {
        removePipWindowTextureView();
    }

    @UiThread
    public void onZoomRatioUpdate(float f) {
        this.mZoomRatio = f;
        if (this.mZoomRatio >= ZOOM_RATIO_THRESHOLD && this.mRegionHelper != null && this.mIsHidden.compareAndSet(true, false)) {
            this.mRegionHelper.initTranslation();
        }
    }

    public void setMapRect(MarshalQueryableChiRect.ChiRect chiRect) {
        if (this.mIsHidden.get()) {
            Log.w(TAG, "setMapRect ignore, pip is hidden");
        } else if (!this.mSubFrameReady.get()) {
            Log.w(TAG, "setMapRect ignore, frame not ready");
        } else {
            Rect rect = this.mMapRect;
            int i = chiRect.left;
            int i2 = chiRect.top;
            rect.set(i, i2, chiRect.height + i, chiRect.width + i2);
            boolean z = (this.mMapRect.width() == 0 || this.mMapRect.height() == 0) ? false : true;
            if (this.mNeedDrawMap.compareAndSet(!z, z)) {
                this.mMainHandler.post(new a(this, z));
                Log.d(TAG, "setMapRect update to " + z + ", mZoomRatio = " + this.mZoomRatio + ", " + chiRect.toString());
            }
            ZoomMapRenderManager zoomMapRenderManager = this.mRenderManager;
            if (zoomMapRenderManager != null) {
                zoomMapRenderManager.updateZoomMapRect(this.mMapRect);
            }
        }
    }
}
