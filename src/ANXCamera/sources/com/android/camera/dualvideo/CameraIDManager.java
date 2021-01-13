package com.android.camera.dualvideo;

import android.util.SparseArray;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import java.util.ArrayList;
import java.util.Iterator;

public class CameraIDManager {
    public static final int MAIN_ID_TAG = 100;
    public static final int SUB_ID_TAG = 101;
    private static final String TAG = "CameraIDManager";
    private SparseArray<Integer> m6Patch2CameraId = new SparseArray<>();
    private ArrayList<IdZoomMap> mIdZoomList;

    /* access modifiers changed from: private */
    public class IdZoomMap {
        public int mId;
        public float mZoom;

        IdZoomMap(int i, float f) {
            this.mId = i;
            this.mZoom = f;
        }
    }

    public CameraIDManager() {
        init6PatchCameraIds();
    }

    static /* synthetic */ boolean a(int i, IdZoomMap idZoomMap) {
        return idZoomMap.mId == i;
    }

    private int getIdByZoom(ArrayList<IdZoomMap> arrayList, float f) {
        Iterator<IdZoomMap> it = arrayList.iterator();
        int i = -1;
        while (it.hasNext()) {
            IdZoomMap next = it.next();
            if (f <= next.mZoom - 0.01f) {
                break;
            }
            i = next.mId;
        }
        return i;
    }

    public static String getZoomDescription(int i) {
        switch (i) {
            case 20:
                return "UW";
            case 21:
            default:
                return "1X";
            case 22:
                return "2X";
            case 23:
                return "5X";
            case 24:
                return "10X";
            case 25:
                return "Front";
        }
    }

    private void init6PatchCameraIds() {
        initIdWithZoomList();
        this.m6Patch2CameraId.append(20, Integer.valueOf(getIdByZoom(this.mIdZoomList, 0.6f)));
        this.m6Patch2CameraId.append(21, Integer.valueOf(getIdByZoom(this.mIdZoomList, 1.0f)));
        this.m6Patch2CameraId.append(22, Integer.valueOf(getIdByZoom(this.mIdZoomList, 2.0f)));
        this.m6Patch2CameraId.append(23, Integer.valueOf(getIdByZoom(this.mIdZoomList, 5.0f)));
        this.m6Patch2CameraId.append(24, Integer.valueOf(getIdByZoom(this.mIdZoomList, 10.0f)));
        this.m6Patch2CameraId.append(25, Integer.valueOf(Camera2DataContainer.getInstance().getFrontCameraId()));
        Log.d(TAG, "init6PatchCameraIds: " + this.m6Patch2CameraId.toString());
    }

    private void initIdWithZoomList() {
        ArrayList<IdZoomMap> arrayList = new ArrayList<>();
        int ultraWideCameraId = Camera2DataContainer.getInstance().getUltraWideCameraId();
        if (ultraWideCameraId != -1) {
            arrayList.add(new IdZoomMap(ultraWideCameraId, 0.6f));
        }
        int mainBackCameraId = Camera2DataContainer.getInstance().getMainBackCameraId();
        if (mainBackCameraId != -1) {
            arrayList.add(new IdZoomMap(mainBackCameraId, 1.0f));
        }
        int auxCameraId = Camera2DataContainer.getInstance().getAuxCameraId();
        if (auxCameraId != -1) {
            arrayList.add(new IdZoomMap(auxCameraId, 2.0f));
        }
        int ultraTeleCameraId = Camera2DataContainer.getInstance().getUltraTeleCameraId();
        if (ultraTeleCameraId != -1) {
            arrayList.add(new IdZoomMap(ultraTeleCameraId, 5.0f));
        }
        this.mIdZoomList = arrayList;
    }

    public SparseArray<Integer> get6Patch2CameraId() {
        return this.m6Patch2CameraId;
    }

    public float getZoomById(int i) {
        return ((Float) this.mIdZoomList.stream().filter(new b(i)).findFirst().map(a.INSTANCE).orElse(Float.valueOf(0.0f))).floatValue();
    }
}
