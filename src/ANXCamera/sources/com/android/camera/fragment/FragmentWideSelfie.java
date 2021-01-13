package com.android.camera.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.UiThread;
import androidx.core.view.ViewCompat;
import com.airbnb.lottie.LottieAnimationView;
import com.android.camera.ActivityBase;
import com.android.camera.CameraScreenNail;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.customization.TintColor;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.effect.draw_mode.DrawRectAttribute;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.GLTextureView;
import com.android.camera.wideselfie.DrawImageView;
import com.android.camera.wideselfie.WideSelfieConfig;
import com.android.gallery3d.ui.GLCanvasImpl;
import com.android.gallery3d.ui.GLPaint;
import io.reactivex.Completable;
import java.util.List;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

public class FragmentWideSelfie extends BaseFragment implements ModeProtocol.WideSelfieProtocol {
    private static final int ALPHA_ANIM_MILLIS = 300;
    public static final int FRAGMENT_INFO = 4094;
    private static final int GUIDE_HIDDEN_DELAY_MILLIS = 3000;
    private static final String TAG = "WideSelfie";
    private LottieAnimationView mGuideImage;
    private boolean mIsShooting;
    private DrawImageView mProgressImageView;
    private GLTextureView mStillPreview;
    private int mStillPreviewHeight;
    private RelativeLayout mStillPreviewLayout;
    private StillPreviewRender mStillPreviewRender;
    private int mStillPreviewWidth;
    private int mThumbBgHeight;
    private int mThumbBgHeightVertical;
    private int mThumbBgTopMargin;
    private int mThumbBgTopMarginVertical;
    private int mThumbBgWidth;
    private int mThumbBgWidthVertical;
    private ViewGroup mThumbNailBackground;
    private int mThumbViewHeight;
    private int mThumbViewHeightVertical;
    private int mThumbViewTopMargin;
    private int mThumbViewTopMarginVertical;
    private int mThumbViewWidth;
    private int mThumbViewWidthVertical;
    private TextView mUseHint;
    private TextView mUseHintLeft;
    private TextView mUseHintRight;
    private View mViewRoot;
    private volatile boolean mWaitingFirstFrame = false;

    /* access modifiers changed from: private */
    public class StillPreviewRender implements GLSurfaceView.Renderer {
        private DrawExtTexAttribute mExtTexture;
        private GLPaint mGlPaint;
        private Handler mHandler;
        private DrawRectAttribute mRectAttribute;
        float[] mTransform;

        private StillPreviewRender() {
            this.mExtTexture = new DrawExtTexAttribute(true);
            this.mTransform = new float[16];
            this.mRectAttribute = new DrawRectAttribute();
            this.mGlPaint = new GLPaint(6.0f, TintColor.tintColor());
            this.mHandler = new Handler();
        }

        public void onDrawFrame(GL10 gl10) {
            CameraScreenNail cameraScreenNail = ((ActivityBase) FragmentWideSelfie.this.getContext()).getCameraScreenNail();
            GLCanvasImpl gLCanvas = ((ActivityBase) FragmentWideSelfie.this.getContext()).getGLView().getGLCanvas();
            if (cameraScreenNail != null && gLCanvas != null && cameraScreenNail.getSurfaceTexture() != null) {
                synchronized (gLCanvas) {
                    gLCanvas.clearBuffer();
                    int width = gLCanvas.getWidth();
                    int height = gLCanvas.getHeight();
                    gLCanvas.getState().pushState();
                    int width2 = FragmentWideSelfie.this.mStillPreview.getWidth();
                    int height2 = FragmentWideSelfie.this.mStillPreview.getHeight();
                    gLCanvas.setSize(width2, height2);
                    cameraScreenNail.getSurfaceTexture().getTransformMatrix(this.mTransform);
                    gLCanvas.draw(this.mExtTexture.init(cameraScreenNail.getExtTexture(), this.mTransform, 0, 0, width2, height2));
                    gLCanvas.draw(this.mRectAttribute.init(0.0f, 0.0f, (float) width2, (float) height2, this.mGlPaint));
                    gLCanvas.setSize(width, height);
                    gLCanvas.getState().popState();
                    gLCanvas.recycledResources();
                }
                if (FragmentWideSelfie.this.mWaitingFirstFrame) {
                    FragmentWideSelfie.this.mWaitingFirstFrame = false;
                    this.mHandler.post(new Runnable() {
                        /* class com.android.camera.fragment.FragmentWideSelfie.StillPreviewRender.AnonymousClass1 */

                        public void run() {
                            Log.d(FragmentWideSelfie.TAG, "onDrawFrame first frame");
                        }
                    });
                }
            }
        }

        public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        }

        public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        }

        public void setPaintColor(int i) {
            this.mGlPaint.setColor(i);
        }
    }

    private void updateGuideVisibility() {
        if (isLandScape()) {
            this.mGuideImage.cancelAnimation();
            this.mGuideImage.setVisibility(8);
        }
    }

    private void updateProgressImageViewLayout(boolean z) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mProgressImageView.getLayoutParams();
        if (z) {
            layoutParams.width = this.mThumbViewWidthVertical;
            layoutParams.height = this.mThumbViewHeightVertical;
            layoutParams.topMargin = this.mThumbViewTopMarginVertical;
            this.mProgressImageView.requestLayout();
            return;
        }
        layoutParams.width = this.mThumbViewWidth;
        layoutParams.height = this.mThumbViewHeight;
        layoutParams.topMargin = this.mThumbViewTopMargin;
        this.mProgressImageView.requestLayout();
    }

    private void updateStillPreviewLayout(boolean z) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mStillPreviewLayout.getLayoutParams();
        if (z) {
            layoutParams.topMargin = this.mThumbBgTopMarginVertical + 1 + (((this.mThumbBgHeightVertical - 2) - this.mStillPreviewHeight) / 2);
            this.mStillPreviewLayout.requestLayout();
            return;
        }
        layoutParams.topMargin = this.mThumbBgTopMargin + 1;
        this.mStillPreviewLayout.requestLayout();
    }

    private void updateThumbnailBackgroundLayout(boolean z) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mThumbNailBackground.getLayoutParams();
        if (z) {
            layoutParams.width = this.mThumbBgWidthVertical;
            layoutParams.height = this.mThumbBgHeightVertical;
            layoutParams.setMarginStart(0);
            layoutParams.setMarginEnd(0);
            layoutParams.topMargin = this.mThumbBgTopMarginVertical;
            layoutParams.addRule(14);
            this.mThumbNailBackground.requestLayout();
            return;
        }
        layoutParams.width = this.mThumbBgWidth;
        layoutParams.height = this.mThumbBgHeight;
        layoutParams.setMarginStart(0);
        layoutParams.setMarginEnd(0);
        layoutParams.topMargin = this.mThumbBgTopMargin;
        layoutParams.addRule(14);
        this.mThumbNailBackground.requestLayout();
    }

    private void updateUiLayout(boolean z) {
        AlphaOutOnSubscribe.directSetResult(this.mUseHint);
        AlphaOutOnSubscribe.directSetResult(this.mUseHintLeft);
        AlphaOutOnSubscribe.directSetResult(this.mUseHintRight);
        DrawImageView drawImageView = this.mProgressImageView;
        if (drawImageView != null) {
            drawImageView.setOrientation(this.mDegree);
        }
        if (isLandScape()) {
            Log.d(TAG, "updateUiLayout LandScape");
            if (isLeftLandScape()) {
                startAnimateViewVisible(this.mUseHintLeft, z);
            } else if (isRightLandScape()) {
                startAnimateViewVisible(this.mUseHintRight, z);
            }
            updateThumbnailBackgroundLayout(true);
            updateProgressImageViewLayout(true);
            updateStillPreviewLayout(true);
            return;
        }
        Log.d(TAG, "updateUiLayout Portrait");
        startAnimateViewVisible(this.mUseHint, z);
        updateThumbnailBackgroundLayout(false);
        updateProgressImageViewLayout(false);
        updateStillPreviewLayout(false);
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 4094;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.wideselfie_view;
    }

    @Override // com.android.camera.protocol.ModeProtocol.WideSelfieProtocol
    public void initPreviewLayout(int i, int i2, int i3, int i4) {
        ViewGroup.LayoutParams layoutParams = this.mStillPreview.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i2;
        this.mStillPreview.requestLayout();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        Log.d(TAG, "initView");
        this.mViewRoot = view;
        this.mUseHint = (TextView) this.mViewRoot.findViewById(R.id.wideselfie_use_hint_text);
        this.mUseHintLeft = (TextView) this.mViewRoot.findViewById(R.id.wideselfie_use_hint_text_left);
        this.mUseHintRight = (TextView) this.mViewRoot.findViewById(R.id.wideselfie_use_hint_text_right);
        this.mGuideImage = (LottieAnimationView) this.mViewRoot.findViewById(R.id.wideselfie_guide_image);
        ViewCompat.setRotation(this.mUseHintLeft, 90.0f);
        ViewCompat.setRotation(this.mUseHintRight, -90.0f);
        this.mProgressImageView = (DrawImageView) this.mViewRoot.findViewById(R.id.wideselfie_progress_image);
        this.mStillPreview = (GLTextureView) this.mViewRoot.findViewById(R.id.wideselfie_still_preview);
        this.mStillPreviewLayout = (RelativeLayout) this.mViewRoot.findViewById(R.id.wideselfie_still_preview_layout);
        this.mThumbNailBackground = (ViewGroup) this.mViewRoot.findViewById(R.id.wideselfie_thumbnail_layout);
        this.mProgressImageView.setParams(this.mStillPreviewWidth, this.mStillPreviewHeight, this.mThumbBgWidth, this.mThumbBgHeightVertical);
        if (this.mStillPreview.getRenderer() == null) {
            this.mStillPreviewRender = new StillPreviewRender();
            this.mStillPreview.setEGLContextClientVersion(2);
            this.mStillPreview.setEGLShareContextGetter(new GLTextureView.EGLShareContextGetter() {
                /* class com.android.camera.fragment.FragmentWideSelfie.AnonymousClass1 */

                @Override // com.android.camera.ui.GLTextureView.EGLShareContextGetter
                public EGLContext getShareContext() {
                    return ((ActivityBase) FragmentWideSelfie.this.getContext()).getGLView().getEGLContext();
                }
            });
            this.mStillPreview.setRenderer(this.mStillPreviewRender);
            this.mStillPreview.setRenderMode(0);
        }
        updateThumbnailBackgroundLayout(false);
        updateProgressImageViewLayout(false);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        if (this.mCurrentMode == 176) {
            showSmallPreview(true);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        WideSelfieConfig instance = WideSelfieConfig.getInstance(getContext());
        instance.init(getContext());
        this.mStillPreviewWidth = instance.getStillPreviewWidth();
        this.mStillPreviewHeight = instance.getStillPreviewHeight();
        this.mThumbBgWidth = instance.getThumbBgWidth();
        this.mThumbBgHeight = instance.getThumbBgHeight();
        this.mThumbBgTopMargin = instance.getThumbBgTopMargin();
        this.mThumbBgWidthVertical = instance.getThumbBgWidthVertical();
        this.mThumbBgHeightVertical = instance.getThumbBgHeightVertical();
        this.mThumbBgTopMarginVertical = instance.getThumbBgTopMarginVertical();
        this.mThumbViewWidth = instance.getThumbViewWidth();
        this.mThumbViewHeight = instance.getThumbViewHeight();
        this.mThumbViewTopMargin = instance.getThumbViewTopMargin();
        this.mThumbViewWidthVertical = instance.getThumbViewWidthVertical();
        this.mThumbViewHeightVertical = instance.getThumbViewHeightVertical();
        this.mThumbViewTopMarginVertical = instance.getThumbViewTopMarginVertical();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        this.mStillPreview.onResume();
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.mStillPreview.onPause();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (i == 176) {
            this.mViewRoot.setVisibility(4);
        } else if (this.mViewRoot.getVisibility() != 0) {
        } else {
            if (list == null) {
                AlphaOutOnSubscribe.directSetResult(this.mViewRoot);
            } else {
                list.add(Completable.create(new AlphaOutOnSubscribe(this.mViewRoot).setDurationTime(150)));
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        updateGuideVisibility();
        if (!this.mIsShooting) {
            updateUiLayout(false);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(216, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.WideSelfieProtocol
    public void requestRender() {
        RelativeLayout relativeLayout = this.mStillPreviewLayout;
        if (relativeLayout != null && relativeLayout.isShown()) {
            this.mStillPreview.requestRender();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.WideSelfieProtocol
    public void resetShootUI() {
        Log.d(TAG, "resetShootUI");
        this.mIsShooting = false;
        setClickEnable(true);
        this.mProgressImageView.setVisibility(8);
        this.mStillPreviewLayout.setVisibility(0);
        updateUiLayout(false);
        this.mStillPreview.onResume();
        this.mUseHint.setText(R.string.wideselfie_press_shoot_key_to_start);
        this.mUseHintLeft.setText(R.string.wideselfie_press_shoot_key_to_start);
        this.mUseHintRight.setText(R.string.wideselfie_press_shoot_key_to_start);
        if (this.mGuideImage.getVisibility() == 0) {
            this.mGuideImage.cancelAnimation();
            this.mGuideImage.setVisibility(8);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.WideSelfieProtocol
    public void setShootingUI() {
        Log.d(TAG, "setShootingUI");
        this.mIsShooting = true;
        setClickEnable(false);
        this.mProgressImageView.setImageBitmap(null, null, null);
        this.mProgressImageView.setVisibility(0);
        if (!isLandScape()) {
            this.mGuideImage.setVisibility(0);
            this.mGuideImage.T();
            this.mGuideImage.a(new AnimatorListenerAdapter() {
                /* class com.android.camera.fragment.FragmentWideSelfie.AnonymousClass2 */

                public void onAnimationCancel(Animator animator) {
                    super.onAnimationCancel(animator);
                    FragmentWideSelfie.this.mGuideImage.setVisibility(8);
                }

                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    FragmentWideSelfie.this.mGuideImage.setVisibility(8);
                }
            });
        }
    }

    public void showSmallPreview(boolean z) {
        Log.d(TAG, "showSmallPreview");
        this.mProgressImageView.setBorderColor(TintColor.tintColor());
        this.mStillPreviewRender.setPaintColor(TintColor.tintColor());
        this.mStillPreview.requestLayout();
        this.mStillPreview.onResume();
        this.mStillPreview.requestRender();
        this.mStillPreviewLayout.setVisibility(0);
        this.mWaitingFirstFrame = true;
        if (z) {
            Completable.create(new AlphaInOnSubscribe(this.mViewRoot).setDurationTime(600)).subscribe();
        } else {
            AlphaInOnSubscribe.directSetResult(this.mViewRoot);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(216, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.WideSelfieProtocol
    public void updateHintText(@StringRes int i) {
        this.mUseHint.setText(i);
        int i2 = R.string.wideselfie_rotate_to_right_slowly;
        if (i != R.string.wideselfie_rotate_to_left_slowly) {
            i2 = i == R.string.wideselfie_rotate_to_right_slowly ? R.string.wideselfie_rotate_to_left_slowly : i;
        }
        this.mUseHintRight.setText(i2);
        if (i == R.string.wideselfie_rotate_to_front) {
            i = R.string.wideselfie_rotate_to_back;
        } else if (i == R.string.wideselfie_rotate_to_back) {
            i = R.string.wideselfie_rotate_to_front;
        }
        this.mUseHintLeft.setText(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.WideSelfieProtocol
    @UiThread
    public void updatePreviewBitmap(@Nullable Bitmap bitmap, Rect rect, Rect rect2) {
        if (bitmap != null && this.mStillPreviewLayout.getVisibility() == 0) {
            this.mStillPreviewLayout.setVisibility(8);
        }
        this.mProgressImageView.setImageBitmap(bitmap, rect, rect2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.WideSelfieProtocol
    public void updateThumbBackgroudLayout(boolean z, boolean z2, int i) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mThumbNailBackground.getLayoutParams();
        if (z) {
            layoutParams.height -= i;
            if (z2) {
                layoutParams.topMargin += i;
            }
            this.mThumbNailBackground.requestLayout();
            return;
        }
        int i2 = layoutParams.width;
        layoutParams.width = i2 - i;
        if (z2) {
            layoutParams.setMarginStart(((Util.sWindowWidth - i2) / 2) + i);
            layoutParams.removeRule(14);
        } else {
            layoutParams.setMarginStart((Util.sWindowWidth - i2) / 2);
            layoutParams.removeRule(14);
        }
        this.mThumbNailBackground.requestLayout();
    }
}
