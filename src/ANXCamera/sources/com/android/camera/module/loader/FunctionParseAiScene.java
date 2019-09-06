package com.android.camera.module.loader;

import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.Face;
import com.android.camera.CameraSettings;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CaptureResultParser;
import io.reactivex.functions.Function;

public class FunctionParseAiScene implements Function<CaptureResult, Integer> {
    private static final String TAG = "FunctionParseAiScene";
    private CameraCapabilities mCameraCapabilities;
    private int mCurrentFaceScene;
    private int mLatestFaceScene;
    private int mModuleIndex;
    private int mParsedAiScene;
    private int mSameFaceSceneDetectedTimes;
    private final boolean mSupportMoonMode = DataRepository.dataItemFeature().Vb();

    public FunctionParseAiScene(int i, CameraCapabilities cameraCapabilities) {
        this.mModuleIndex = i;
        this.mCameraCapabilities = cameraCapabilities;
    }

    private boolean faceSceneFiltering(int i) {
        int i2 = this.mLatestFaceScene;
        if (i2 != i) {
            this.mLatestFaceScene = i;
            this.mSameFaceSceneDetectedTimes = 0;
        } else {
            int i3 = this.mSameFaceSceneDetectedTimes;
            if (i3 < 20) {
                this.mSameFaceSceneDetectedTimes = i3 + 1;
                if (20 == this.mSameFaceSceneDetectedTimes) {
                    int i4 = this.mCurrentFaceScene;
                    if (i4 != i2) {
                        this.mLatestFaceScene = i4;
                        this.mCurrentFaceScene = this.mLatestFaceScene;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Integer apply(CaptureResult captureResult) {
        int i;
        Face[] faceArr = (Face[]) captureResult.get(CaptureResult.STATISTICS_FACES);
        int i2 = this.mModuleIndex;
        String str = TAG;
        if (i2 == 171 || CameraSettings.isFrontCamera() || faceArr == null || faceArr.length <= 0) {
            i = Integer.MIN_VALUE;
        } else {
            i = Integer.MIN_VALUE;
            for (Face face : faceArr) {
                if (face.getBounds().width() > 300) {
                    int hdrDetectedScene = CaptureResultParser.getHdrDetectedScene(captureResult);
                    StringBuilder sb = new StringBuilder();
                    sb.append("parseAiSceneResult: AI_SCENE_MODE_HUMAN  face.length = ");
                    sb.append(faceArr.length);
                    sb.append(";face.width = ");
                    sb.append(face.getBounds().width());
                    sb.append(";hdrMode = ");
                    sb.append(hdrDetectedScene);
                    Log.c(str, sb.toString());
                    if (hdrDetectedScene == 1) {
                        CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
                        if (cameraCapabilities != null && cameraCapabilities.getMiAlgoASDVersion() < 2.0f) {
                            i = -1;
                        }
                    }
                    i = 25;
                }
            }
        }
        if (faceSceneFiltering(i)) {
            if (i == Integer.MIN_VALUE) {
                int asdDetectedModes = CaptureResultParser.getAsdDetectedModes(captureResult);
                if (!this.mSupportMoonMode && asdDetectedModes == 35) {
                    Log.w(str, "detected moon mode on unsupported device, set scene negative");
                    asdDetectedModes = 0;
                }
                if (asdDetectedModes < 0) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("parseAiSceneResult: parse a error result: ");
                    sb2.append(asdDetectedModes);
                    Log.e(str, sb2.toString());
                    this.mParsedAiScene = 0;
                } else {
                    this.mParsedAiScene = asdDetectedModes;
                }
            } else {
                this.mParsedAiScene = i;
            }
        }
        return Integer.valueOf(this.mParsedAiScene);
    }

    public void resetScene() {
        this.mLatestFaceScene = 0;
        this.mParsedAiScene = 0;
    }
}
