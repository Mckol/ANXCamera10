package com.android.camera.fragment.manually.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import b.c.a.b;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.manually.ZoomValueListener;
import java.util.Arrays;
import java.util.List;

public class ExtraSlideFNumberAdapter extends AbstractZoomSliderAdapter<String> {
    private static final int ENTRY_COUNT_TOTAL = F_NUMBERS.length;
    public static final String[] F_NUMBERS = {"1.0", "1.1", "1.2", "1.4", "1.6", "1.8", "2", "2.2", "2.5", "2.8", "3.2", "3.5", "4", "4.5", HybridZoomingSystem.STRING_SUPER_MOON_ZOOM_RATIO_NONE, "5.6", "6.3", "7.1", "8", "9", "10", "11", "13", "14", "16"};
    private String mCurrentValue;
    private b mDataItemFeature;
    private boolean mEnable;
    private ZoomValueListener mManuallyListener;
    private List<String> mValidFNumbers = Arrays.asList(F_NUMBERS);

    public ExtraSlideFNumberAdapter(Context context, String str, ZoomValueListener zoomValueListener) {
        this.mManuallyListener = zoomValueListener;
        this.mCurrentValue = str;
        this.mDataItemFeature = DataRepository.dataItemFeature();
        initStyle(context);
    }

    private boolean isFlagPosition(int i) {
        return F_NUMBERS[i].equals(this.mDataItemFeature.L(CameraSettings.isFrontCamera())) || i == 0 || i == F_NUMBERS.length - 1;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public void draw(int i, Canvas canvas, boolean z, int i2, float f) {
        super.draw(i, canvas, z, i2, f);
        if (z) {
            int i3 = this.mLineStopPointWidth;
            float f2 = this.mCurrentLineSelectHalfHeight;
            canvas.drawRoundRect((float) ((-i3) / 2), -f2, (float) (i3 / 2), f2, 2.0f, 2.0f, this.mSelectPaint);
        } else if (isFlagPosition(i)) {
            int i4 = this.mLineStopPointWidth;
            float f3 = this.mCurrentLineHalfHeight;
            canvas.drawRoundRect((float) ((-i4) / 2), -f3, (float) (i4 / 2), f3, 1.0f, 1.0f, this.mStopPointPaint);
        } else {
            int i5 = this.mLineWidth;
            float f4 = this.mCurrentLineHalfHeight;
            canvas.drawRoundRect((float) ((-i5) / 2), -f4, (float) (i5 / 2), f4, 1.0f, 1.0f, this.mNormalPaint);
        }
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public Paint.Align getAlign(int i) {
        return Paint.Align.CENTER;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public int getCount() {
        return ENTRY_COUNT_TOTAL;
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public boolean isEnable() {
        return false;
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public String mapPositionToValue(float f) {
        return F_NUMBERS[Util.clamp(Math.round(f), 0, getCount() - 1)];
    }

    public float mapValueToPosition(String str) {
        return (float) this.mValidFNumbers.indexOf(str);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public float measureWidth(int i) {
        return (float) (isFlagPosition(i) ? this.mLineStopPointWidth : this.mLineWidth);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.OnPositionSelectListener
    public void onChangeValue(String str) {
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.OnPositionSelectListener
    public void onPositionSelect(View view, int i, float f) {
        if (this.mEnable) {
            if (i == -1) {
                if (f != -1.0f) {
                    i = Math.round(f * ((float) (getCount() - 1)));
                } else {
                    return;
                }
            }
            String mapPositionToValue = mapPositionToValue((float) i);
            if (!mapPositionToValue.equals(this.mCurrentValue)) {
                ZoomValueListener zoomValueListener = this.mManuallyListener;
                if (zoomValueListener != null) {
                    zoomValueListener.onManuallyDataChanged(mapPositionToValue);
                    this.mManuallyListener.onZoomItemSlideOn(i, false);
                }
                this.mCurrentValue = mapPositionToValue;
            }
        }
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public void setEnable(boolean z) {
        this.mEnable = z;
    }
}
