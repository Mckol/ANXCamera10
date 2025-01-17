package com.android.camera.features.mimoji2.widget;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import b.c.a.c;
import com.android.camera.features.mimoji2.widget.helper.AvatarEngineManager2;
import com.android.camera.log.Log;
import com.android.camera.ui.GLTextureView;
import com.arcsoft.avatar2.AvatarEngine;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;
import miui.reflect.Field;
import miui.text.ExtraTextUtils;

public class MimojiEditGLTextureView2 extends GLTextureView implements GLSurfaceView.Renderer {
    private static float[] BACKGROUND_COLOR = {0.0f, 0.0f, 0.0f, 0.0f};
    private static final boolean DEBUG_FPS = false;
    public static final int DO_DRAW = 6;
    public static final int GET_SAVE_THUM_END = 4;
    private static final String TAG = "MimojiEditGLTextureView2";
    public static final int THUM_HEIGHT = 200;
    public static final int THUM_WIDTH = 200;
    public static final int UPDATE_THUM = 5;
    private AvatarEngine mAvatar;
    private int mDeviceRotation;
    private final MyEGLConfigChooser mEglConfigChooser;
    private int mFrameCount;
    private long mFrameCountingStart;
    private Handler mHandler;
    private boolean mIsStopRender;
    private boolean mIsStopRenderForce;
    private boolean mSaveConfigThum;

    private class MyEGLConfigChooser implements GLTextureView.EGLConfigChooser {
        private final int[] ATTR_ID;
        private final String[] ATTR_NAME;
        private final int[] mConfigSpec;

        private MyEGLConfigChooser() {
            int[] iArr = new int[13];
            iArr[0] = 12324;
            iArr[1] = c.rn() ? 8 : 5;
            iArr[2] = 12323;
            iArr[3] = c.rn() ? 8 : 6;
            iArr[4] = 12322;
            iArr[5] = c.rn() ? 8 : 5;
            iArr[6] = 12325;
            iArr[7] = 8;
            iArr[8] = 12321;
            iArr[9] = 0;
            iArr[10] = 12352;
            iArr[11] = 4;
            iArr[12] = 12344;
            this.mConfigSpec = iArr;
            this.ATTR_ID = new int[]{12324, 12323, 12322, 12321, 12325, 12326, 12328, 12327};
            this.ATTR_NAME = new String[]{"R", "G", Field.BYTE_SIGNATURE_PRIMITIVE, "A", Field.DOUBLE_SIGNATURE_PRIMITIVE, "S", "ID", "CAVEAT"};
        }

        private EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr) {
            int[] iArr = new int[1];
            int length = eGLConfigArr.length;
            int i = Integer.MAX_VALUE;
            EGLConfig eGLConfig = null;
            for (int i2 = 0; i2 < length; i2++) {
                if (!egl10.eglGetConfigAttrib(eGLDisplay, eGLConfigArr[i2], 12324, iArr) || iArr[0] != 8) {
                    if (!egl10.eglGetConfigAttrib(eGLDisplay, eGLConfigArr[i2], 12326, iArr)) {
                        throw new RuntimeException("eglGetConfigAttrib error: " + egl10.eglGetError());
                    } else if (iArr[0] != 0 && iArr[0] < i) {
                        i = iArr[0];
                        eGLConfig = eGLConfigArr[i2];
                    }
                }
            }
            if (eGLConfig == null) {
                eGLConfig = eGLConfigArr[0];
            }
            egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, 12326, iArr);
            logConfig(egl10, eGLDisplay, eGLConfig);
            return eGLConfig;
        }

        private void logConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
            int[] iArr = new int[1];
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (true) {
                int[] iArr2 = this.ATTR_ID;
                if (i < iArr2.length) {
                    egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, iArr2[i], iArr);
                    sb.append(this.ATTR_NAME[i]);
                    sb.append(iArr[0]);
                    sb.append(" ");
                    i++;
                } else {
                    Log.i(MimojiEditGLTextureView2.TAG, "Config chosen: " + sb.toString());
                    return;
                }
            }
        }

        @Override // com.android.camera.ui.GLTextureView.EGLConfigChooser
        public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay) {
            int[] iArr = new int[1];
            if (!egl10.eglChooseConfig(eGLDisplay, this.mConfigSpec, null, 0, iArr)) {
                throw new RuntimeException("eglChooseConfig failed");
            } else if (iArr[0] > 0) {
                EGLConfig[] eGLConfigArr = new EGLConfig[iArr[0]];
                if (egl10.eglChooseConfig(eGLDisplay, this.mConfigSpec, eGLConfigArr, eGLConfigArr.length, iArr)) {
                    return chooseConfig(egl10, eGLDisplay, eGLConfigArr);
                }
                throw new RuntimeException();
            } else {
                throw new RuntimeException("No configs match configSpec");
            }
        }
    }

    public MimojiEditGLTextureView2(Context context) {
        this(context, null);
    }

    public MimojiEditGLTextureView2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEglConfigChooser = new MyEGLConfigChooser();
        this.mFrameCount = 0;
        this.mFrameCountingStart = 0;
        this.mDeviceRotation = 90;
        this.mIsStopRender = false;
        this.mIsStopRenderForce = false;
        this.mSaveConfigThum = false;
        setEGLContextClientVersion(2);
        setEGLConfigChooser(this.mEglConfigChooser);
        setRenderer(this);
        setRenderMode(0);
        setPreserveEGLContextOnPause(true);
    }

    private void outputFps() {
        long nanoTime = System.nanoTime();
        long j = this.mFrameCountingStart;
        if (j == 0) {
            this.mFrameCountingStart = nanoTime;
        } else if (nanoTime - j > ExtraTextUtils.GB) {
            Log.d(TAG, "fps: " + ((((double) this.mFrameCount) * 1.0E9d) / ((double) (nanoTime - this.mFrameCountingStart))));
            this.mFrameCountingStart = nanoTime;
            this.mFrameCount = 0;
        }
        this.mFrameCount++;
    }

    public void onDeviceRotationChange(int i) {
        this.mDeviceRotation = i;
    }

    public void onDrawFrame(GL10 gl10) {
        GLES20.glClearColor(0.0823f, 0.0823f, 0.0823f, 1.0f);
        GLES20.glEnable(2929);
        GLES20.glClear(16640);
        if (!this.mIsStopRender) {
            int width = getWidth();
            int height = getHeight();
            this.mAvatar = AvatarEngineManager2.getInstance().getAvatar();
            AvatarEngine avatarEngine = this.mAvatar;
            if (avatarEngine == null) {
                Log.d(TAG, "onDrawFrame mAvatar  null");
                return;
            }
            avatarEngine.avatarRender(this.mDeviceRotation, width, height, 0, false, null);
            if (this.mSaveConfigThum) {
                this.mSaveConfigThum = false;
                byte[] bArr = new byte[160000];
                this.mAvatar.renderThumb(244, 292, 21, 20, bArr, 200, 200, 800, BACKGROUND_COLOR, 1.0f);
                Message obtainMessage = this.mHandler.obtainMessage();
                obtainMessage.what = 4;
                obtainMessage.obj = bArr;
                Handler handler = this.mHandler;
                if (handler != null) {
                    handler.sendMessage(obtainMessage);
                }
            }
        }
    }

    @Override // com.android.camera.ui.GLTextureView
    public void onPause() {
        setPreserveEGLContextOnPause(false);
        AvatarEngine avatarEngine = this.mAvatar;
        if (avatarEngine != null) {
            avatarEngine.releaseRender();
        }
        super.onPause();
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        GLES20.glViewport(0, 0, i, i2);
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    public void setIsStopRenderForce(boolean z) {
        String str = TAG;
        Log.d(str, "setIsStopRenderForce :" + z);
        this.mIsStopRenderForce = z;
        if (z) {
            setStopRender(true);
        }
    }

    public void setSaveConfigThum(boolean z) {
        this.mSaveConfigThum = z;
    }

    public void setStopRender(boolean z) {
        if (this.mIsStopRenderForce) {
            z = true;
        }
        if (this.mIsStopRender != z) {
            String str = TAG;
            Log.d(str, "setStopRender :" + z);
            this.mIsStopRender = z;
        }
    }
}
