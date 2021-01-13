package com.ss.android.vesdk;

import androidx.annotation.NonNull;
import com.android.camera.Util;

public class VEPreviewSettings {
    private VESize mCanvasSize;
    private boolean mEnableAudioRecord = true;
    private boolean mIsAsyncDetection = false;
    private VESize mRenderSize = new VESize(Util.LIMIT_SURFACE_WIDTH, 1280);

    public static class Builder {
        private VEPreviewSettings mExportPreviewSettings;

        public Builder() {
            this.mExportPreviewSettings = new VEPreviewSettings();
        }

        public Builder(VEPreviewSettings vEPreviewSettings) {
            this.mExportPreviewSettings = vEPreviewSettings;
        }

        public VEPreviewSettings build() {
            return this.mExportPreviewSettings;
        }

        public Builder enableAudioRecord(boolean z) {
            this.mExportPreviewSettings.mEnableAudioRecord = z;
            return this;
        }

        public Builder setAsyncDetection(boolean z) {
            this.mExportPreviewSettings.mIsAsyncDetection = z;
            return this;
        }

        public Builder setRenderSize(@NonNull VESize vESize) {
            this.mExportPreviewSettings.mRenderSize = vESize;
            return this;
        }
    }

    public VESize getCanvasSize() {
        return this.mCanvasSize;
    }

    public VESize getRenderSize() {
        return this.mRenderSize;
    }

    public boolean isAsyncDetection() {
        return this.mIsAsyncDetection;
    }

    public boolean isAudioRecordEnabled() {
        return this.mEnableAudioRecord;
    }
}
