package com.android.camera.module;

import android.graphics.Rect;
import android.graphics.RectF;
import com.android.camera2.CameraHardwareFace;
import com.android.camera2.vendortag.struct.MarshalQueryableSuperNightExif;

public class DebugInfoUtil {
    public static final String TAG = "DebugInfoUtil";

    public static String getFaceInfoString(CameraHardwareFace[] cameraHardwareFaceArr) {
        if (cameraHardwareFaceArr == null || cameraHardwareFaceArr.length <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(128);
        sb.append("size:" + cameraHardwareFaceArr.length + "  value:");
        for (CameraHardwareFace cameraHardwareFace : cameraHardwareFaceArr) {
            Rect rect = cameraHardwareFace.rect;
            sb.append("[" + rect.left + "," + rect.top + "," + rect.right + "," + rect.bottom + "] ");
        }
        return sb.toString();
    }

    public static String getRetriveFaceInfo(RectF[] rectFArr) {
        if (rectFArr == null || rectFArr.length <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(128);
        sb.append("size:" + rectFArr.length + "  value:");
        for (RectF rectF : rectFArr) {
            if (rectF != null) {
                sb.append("[" + Math.abs(rectF.left) + "," + Math.abs(rectF.top) + "," + Math.abs(rectF.right) + "," + Math.abs(rectF.bottom) + "] ");
            }
        }
        return sb.toString();
    }

    public static String getSuperNightExif(MarshalQueryableSuperNightExif.SuperNightExif superNightExif) {
        if (superNightExif == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" luxIndex: " + superNightExif.luxIndex);
        sb.append(" light: " + superNightExif.light);
        sb.append(" darkRatio: " + superNightExif.darkRatio);
        sb.append(" middleRatio: " + superNightExif.middleRatio);
        sb.append(" brightRatio: " + superNightExif.brightRatio);
        return sb.toString();
    }
}
