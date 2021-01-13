package com.android.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.location.Location;
import android.media.Image;
import com.android.camera.LocationManager;
import com.android.camera.SurfaceTextureScreenNail;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.storage.ImageSaver;
import com.android.camera2.Camera2Proxy;

public class MiCamera2ShotSimplePreview extends MiCamera2Shot<byte[]> implements SurfaceTextureScreenNail.PreviewSaveListener {
    private static final String TAG = "MiCamera2ShotSimplePreview";
    private ImageSaver mSaver;

    public MiCamera2ShotSimplePreview(MiCamera2 miCamera2) {
        super(miCamera2);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return null;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        return null;
    }

    /* access modifiers changed from: protected */
    public void notifyResultData(byte[] bArr) {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void onImageReceived(Image image, int i) {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void prepare() {
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.PreviewSaveListener
    public void save(byte[] bArr, int i, int i2, int i3) {
        Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
        if (pictureCallback != null) {
            pictureCallback.onPictureTakenFinished(true);
        }
        long currentTimeMillis = System.currentTimeMillis();
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        ImageSaver imageSaver = this.mSaver;
        if (imageSaver != null) {
            imageSaver.addSimpleImage(bArr, true, Util.createJpegName(currentTimeMillis), null, System.currentTimeMillis(), null, currentLocation, i, i2, null, i3, false, false, true, false, false, null, null, -1, null);
        }
    }

    public void setImageSaver(ImageSaver imageSaver) {
        this.mSaver = imageSaver;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void startSessionCapture() {
        Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
        if (pictureCallback != null) {
            pictureCallback.onCaptureShutter(true, false, false, false);
        } else {
            Log.w(TAG, "startSessionCapture: null picture callback");
        }
    }
}
