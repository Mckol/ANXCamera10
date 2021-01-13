package com.android.camera.panorama;

import android.media.Image;

public class Camera2Image extends CaptureImage {
    public Camera2Image(Image image) {
        super(image);
    }

    @Override // com.android.camera.panorama.CaptureImage
    public int getHeight() {
        return this.mImage.getHeight();
    }

    @Override // com.android.camera.panorama.CaptureImage
    public String getImageFormat() {
        return PanoramaGP3ImageFormat.getImageFormat(this.mImage);
    }

    @Override // com.android.camera.panorama.CaptureImage
    public int getWidth() {
        return this.mImage.getWidth();
    }
}
