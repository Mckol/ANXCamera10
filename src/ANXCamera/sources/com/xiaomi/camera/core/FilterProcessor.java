package com.xiaomi.camera.core;

import android.media.Image;
import android.os.ConditionVariable;
import android.util.Size;
import androidx.annotation.NonNull;
import b.c.a.c;
import com.android.camera.Util;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.draw_mode.DrawYuvAttribute;
import com.android.camera.effect.renders.DeviceWatermarkParam;
import com.android.camera.effect.renders.SnapshotRender;
import com.android.camera.log.Log;
import com.xiaomi.camera.base.ImageUtil;

public class FilterProcessor {
    private static final String TAG = "FilterProcessor";
    ConditionVariable mBlockVariable;
    private Size mRenderSize = new Size(0, 0);

    public static class YuvAttributeWrapper {
        public DrawYuvAttribute mAttribute;
        public ConditionVariable mBlocker;

        public YuvAttributeWrapper(DrawYuvAttribute drawYuvAttribute, ConditionVariable conditionVariable) {
            this.mAttribute = drawYuvAttribute;
            this.mBlocker = conditionVariable;
        }
    }

    public FilterProcessor(ConditionVariable conditionVariable) {
        this.mBlockVariable = conditionVariable;
    }

    private static DrawYuvAttribute getDrawYuvAttribute(Image image, boolean z, ParallelTaskData parallelTaskData) {
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        return new DrawYuvAttribute(image, dataParameter.getPreviewSize(), dataParameter.getPictureSize(), dataParameter.getFilterId(), dataParameter.getOrientation(), dataParameter.getJpegRotation(), dataParameter.getShootRotation(), System.currentTimeMillis(), dataParameter.isMirror(), z, dataParameter.getOutputFormat() == 1212500294, dataParameter.getTiltShiftMode(), dataParameter.getTimeWaterMarkString(), EffectController.getInstance().copyEffectRectAttribute(), dataParameter.getFaceWaterMarkList(), dataParameter.getMajorAIWatermark(), dataParameter.getMinorAIWatermark());
    }

    private boolean isWatermarkEnabled(ParallelTaskData parallelTaskData) {
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        return !dataParameter.getVendorWaterMark() && (dataParameter.isHasDualWaterMark() || dataParameter.isHasFrontWaterMark() || dataParameter.getTimeWaterMarkString() != null || dataParameter.isAgeGenderAndMagicMirrorWater() || dataParameter.getMajorAIWatermark() != null);
    }

    private void prepareEffectProcessor(ParallelTaskDataParameter parallelTaskDataParameter) {
        int filterId = parallelTaskDataParameter.getFilterId();
        Size pictureSize = parallelTaskDataParameter.getPictureSize();
        DeviceWatermarkParam deviceWatermarkParam = parallelTaskDataParameter.getDeviceWatermarkParam();
        if (!this.mRenderSize.equals(pictureSize)) {
            init(pictureSize);
        }
        Log.d(TAG, String.format("prepareEffectProcessor: %x", Integer.valueOf(filterId)));
        SnapshotRender.getRender().prepareEffectRender(deviceWatermarkParam, filterId);
    }

    private boolean shouldApplyEffect(@NonNull ParallelTaskData parallelTaskData) {
        if (isWatermarkEnabled(parallelTaskData)) {
            return true;
        }
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        return (dataParameter == null || (FilterInfo.FILTER_ID_NONE == dataParameter.getFilterId() && dataParameter.getTiltShiftMode() == null && !dataParameter.isCinematicAspectRatio())) ? false : true;
    }

    public Image doFilterSync(@NonNull ParallelTaskData parallelTaskData, @NonNull Image image, int i) {
        byte[] bArr;
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        if (shouldApplyEffect(parallelTaskData)) {
            prepareEffectProcessor(dataParameter);
            DrawYuvAttribute drawYuvAttribute = getDrawYuvAttribute(image, i == 0 && isWatermarkEnabled(parallelTaskData), parallelTaskData);
            drawYuvAttribute.mJpegQuality = dataParameter.getJpegQuality();
            drawYuvAttribute.mOutputSize = dataParameter.getOutputSize();
            YuvAttributeWrapper yuvAttributeWrapper = new YuvAttributeWrapper(drawYuvAttribute, this.mBlockVariable);
            String str = TAG;
            Log.d(str, "apply filter (id: " + drawYuvAttribute.mEffectIndex + ") to the captured photo");
            this.mBlockVariable.close();
            SnapshotRender.getRender().processImageSync(yuvAttributeWrapper);
            this.mBlockVariable.block();
            if (!c.Zg && (bArr = drawYuvAttribute.mDataOfTheRegionUnderWatermarks) != null) {
                parallelTaskData.setDataOfTheRegionUnderWatermarks(bArr);
                parallelTaskData.setCoordinatesOfTheRegionUnderWatermarks(drawYuvAttribute.mCoordinatesOfTheRegionUnderWatermarks);
            }
            if (Util.isDumpImageEnabled()) {
                ImageUtil.dumpYuvImage(image, "filter_done");
            }
        }
        return image;
    }

    public void init(Size size) {
        this.mRenderSize = size;
    }
}
