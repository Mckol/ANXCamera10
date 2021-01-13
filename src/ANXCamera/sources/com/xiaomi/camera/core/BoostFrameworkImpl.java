package com.xiaomi.camera.core;

import b.c.a.c;
import com.android.camera.CameraAppImpl;
import com.android.camera.log.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class BoostFrameworkImpl implements BaseBoostFramework {
    private static final String BOOST_CLASS_NAME_MTK = "android.util.MtkBoostFramework";
    private static final String BOOST_CLASS_NAME_QUALCOM = "android.util.BoostFramework";
    private static final String BOOST_METHOD_NAME = "perfLockAcquire";
    private static final String BOOST_PARAM_NAME = (c.Bg + "_boost_params");
    private static final String BOOST_STOP_NAME = "perfLockRelease";
    private static final String TAG = "BoostFrameworkImpl";
    private static boolean isInited;
    private static Class<?> mBoostClass = null;
    private static Method mStartBoost = null;
    private static Method mStopBoost = null;
    private int[] mDefaultParams = new int[0];
    private Object mPerf = null;

    public BoostFrameworkImpl() {
        synchronized (BoostFrameworkImpl.class) {
            if (!isInited) {
                try {
                    mBoostClass = Class.forName(c.isMTKPlatform() ? BOOST_CLASS_NAME_MTK : BOOST_CLASS_NAME_QUALCOM);
                    if (mBoostClass != null) {
                        mStartBoost = mBoostClass.getMethod(BOOST_METHOD_NAME, Integer.TYPE, int[].class);
                        mStopBoost = mBoostClass.getMethod(BOOST_STOP_NAME, new Class[0]);
                    }
                    int[] boostParams = getBoostParams();
                    if (boostParams != null) {
                        this.mDefaultParams = boostParams;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                isInited = true;
            }
        }
        try {
            if (mBoostClass != null) {
                Constructor<?> constructor = mBoostClass.getConstructor(new Class[0]);
                constructor.setAccessible(true);
                this.mPerf = constructor.newInstance(new Object[0]);
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    private int[] getBoostParams() {
        int identifier = CameraAppImpl.getAndroidContext().getResources().getIdentifier(BOOST_PARAM_NAME, "array", "com.android.camera");
        if (identifier > 0) {
            return CameraAppImpl.getAndroidContext().getResources().getIntArray(identifier);
        }
        return null;
    }

    private boolean startBoostInternal(int i, int[] iArr) {
        Log.d(TAG, "startBoostInternal " + i);
        if (this.mPerf == null) {
            Log.d(TAG, "not init boost pref");
            return false;
        }
        try {
            if (mStartBoost != null) {
                Log.d(TAG, "ready to boost");
                mStartBoost.setAccessible(true);
                mStartBoost.invoke(this.mPerf, Integer.valueOf(i), iArr);
                return true;
            }
        } catch (Exception e2) {
            Log.e(TAG, "start boost exception " + e2);
            e2.printStackTrace();
        }
        return false;
    }

    private void stopBoostInternal() {
        Log.d(TAG, "stopBoostInternal:");
        if (this.mPerf == null) {
            Log.d(TAG, "not init boost pref, not need to stop");
            return;
        }
        try {
            if (mStopBoost != null) {
                Log.d(TAG, "ready to stop boost");
                mStopBoost.setAccessible(true);
                mStopBoost.invoke(this.mPerf, new Object[0]);
            }
        } catch (Exception e2) {
            Log.e(TAG, "stop boost exception " + e2);
            e2.printStackTrace();
        }
    }

    public void setDefaultParams(int[] iArr) {
        this.mDefaultParams = iArr;
    }

    @Override // com.xiaomi.camera.core.BaseBoostFramework
    public boolean startBoost() {
        return startBoost(0);
    }

    @Override // com.xiaomi.camera.core.BaseBoostFramework
    public boolean startBoost(int i) {
        boolean z;
        int[] iArr = this.mDefaultParams;
        if (iArr == null || iArr.length == 0) {
            Log.d(TAG, "could not boost for empty params");
            z = false;
        } else {
            z = startBoostInternal(i, iArr);
        }
        Log.d(TAG, "startBoost: isBoostStarted " + z);
        return z;
    }

    @Override // com.xiaomi.camera.core.BaseBoostFramework
    public boolean startBoost(int i, int[] iArr) {
        return startBoostInternal(i, iArr);
    }

    @Override // com.xiaomi.camera.core.BaseBoostFramework
    public void stopBoost() {
        stopBoostInternal();
    }
}
