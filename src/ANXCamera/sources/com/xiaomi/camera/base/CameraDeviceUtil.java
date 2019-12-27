package com.xiaomi.camera.base;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureResult;
import android.os.Parcelable;
import com.android.camera.log.Log;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class CameraDeviceUtil {
    private static final String TAG = "CameraDeviceUtil";

    private CameraDeviceUtil() {
    }

    public static int getCameraCombinationMode(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 1) {
            return 17;
        }
        if (i != 2) {
            if (i == 3) {
                return 513;
            }
            if (i == 4) {
                return 769;
            }
            if (i != 20) {
                if (i == 21) {
                    return 3;
                }
                if (i == 40) {
                    return 18;
                }
                if (i == 80) {
                    return 515;
                }
                if (i == 81) {
                    return 771;
                }
                switch (i) {
                    case 60:
                        return 513;
                    case 61:
                        return 769;
                    case 62:
                        return 514;
                    case 63:
                        return 770;
                    default:
                        return 0;
                }
            }
        }
        return 2;
    }

    public static ICustomCaptureResult getCustomCaptureResult(CaptureResult captureResult) {
        try {
            Method method = captureResult.getClass().getMethod("getNativeCopy", new Class[0]);
            method.setAccessible(true);
            Parcelable parcelable = (Parcelable) method.invoke(captureResult, new Object[0]);
            String str = TAG;
            Log.d(str, "getCustomCaptureResult: cameraMetadataNative =" + parcelable);
            ICustomCaptureResult iCustomCaptureResult = new ICustomCaptureResult();
            iCustomCaptureResult.setFrameNumber(captureResult.getFrameNumber());
            iCustomCaptureResult.setRequest(captureResult.getRequest());
            iCustomCaptureResult.setSequenceId(captureResult.getSequenceId());
            iCustomCaptureResult.setResults(parcelable);
            Long l = (Long) captureResult.get(CaptureResult.SENSOR_TIMESTAMP);
            if (l != null) {
                iCustomCaptureResult.setTimeStamp(l.longValue());
            }
            String str2 = TAG;
            Log.d(str2, "getCustomCaptureResult: " + iCustomCaptureResult);
            return iCustomCaptureResult;
        } catch (Exception e2) {
            Log.e(TAG, "getCustomCaptureResult: getCustomCaptureResult", e2);
            return null;
        }
    }

    public static void prepareCalibrationDataForAlgo(Context context, String str) {
        try {
            CameraCharacteristics cameraCharacteristics = ((CameraManager) context.getSystemService("camera")).getCameraCharacteristics(str);
            Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
            if (num != null) {
                boolean z = num.intValue() == 0;
                try {
                    byte[] bArr = (byte[]) cameraCharacteristics.get((CameraCharacteristics.Key) Class.forName("android.hardware.camera2.CameraCharacteristics$Key").getDeclaredConstructor(new Class[]{String.class, Class.class}).newInstance(new Object[]{"com.xiaomi.camera.algoup.dualCalibrationData", byte[].class}));
                    if (bArr != null) {
                        CommonUtil.saveCameraCalibrationToFile(context, bArr, z);
                    }
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
                    Log.e(TAG, "prepareCalibrationDataForAlgo: call reflect method failed!", e2);
                    throw new RuntimeException("getCameraCharacteristics's dualCalibrationData failed");
                }
            }
        } catch (CameraAccessException e3) {
            Log.e(TAG, "prepareCalibrationDataForAlgo: get getCameraCharacteristics failed!", e3);
        }
    }
}
