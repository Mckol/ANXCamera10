package com.android.camera.fragment.manually.adapter.sat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.manually.ZoomValueListener;
import com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.ModuleManager;
import java.util.ArrayList;
import java.util.List;

public class StopsZoomSliderAdapter extends AbstractZoomSliderAdapter<Float> {
    private static final String TAG = "StopsZoomSliderAdapter";
    private int mCurrentIndex = -1;
    protected final int mCurrentMode;
    private boolean mEnable;
    private List<Integer> mRulerLines = new ArrayList();
    private List<Float> mZoomIndexs = new ArrayList();
    protected final float mZoomRatioMax;
    protected final float mZoomRatioMin;

    public StopsZoomSliderAdapter(Context context, int i, ZoomValueListener zoomValueListener) {
        this.mCurrentMode = i;
        this.mZoomValueListener = zoomValueListener;
        this.mCurrentValue = String.valueOf(CameraSettings.readZoom());
        BaseModule baseModule = (BaseModule) ((ActivityBase) context).getCurrentModule();
        this.mZoomRatioMin = baseModule.getMinZoomRatio();
        this.mZoomRatioMax = baseModule.getMaxZoomRatio();
        Log.d(TAG, "ZOOM RATIO RANGE [" + this.mZoomRatioMin + ", " + this.mZoomRatioMax + "]");
        initStyle(context);
        boolean z = i == 188;
        boolean isVideoCategory = ModuleManager.isVideoCategory(i);
        List<Float> a2 = DataRepository.dataItemFeature().a(z, isVideoCategory, HybridZoomingSystem.ZOOM_INDEXS_DEFAULT);
        List<Integer> b2 = DataRepository.dataItemFeature().b(z, isVideoCategory, HybridZoomingSystem.ZOOM_RULER_DEFAULT);
        for (int i2 = 0; i2 < a2.size(); i2++) {
            float floatValue = a2.get(i2).floatValue();
            if (floatValue >= this.mZoomRatioMin && floatValue <= this.mZoomRatioMax) {
                this.mZoomIndexs.add(Float.valueOf(floatValue));
                if (i2 >= 1 && i2 <= b2.size()) {
                    int i3 = i2 - 1;
                    if (this.mZoomIndexs.contains(a2.get(i3))) {
                        this.mRulerLines.add(b2.get(i3));
                    }
                }
            }
        }
        if (this.mZoomIndexs.size() != this.mRulerLines.size() + 1) {
            throw new IllegalStateException("init zoom fail mZoomIndexs = " + this.mZoomIndexs + ",mRulerLines = " + this.mRulerLines);
        } else if (!this.mZoomIndexs.contains(Float.valueOf(this.mZoomRatioMax))) {
            this.mZoomIndexs.add(Float.valueOf(this.mZoomRatioMax));
            this.mRulerLines.add(10);
        }
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public void draw(int i, Canvas canvas, boolean z, int i2, float f) {
        super.draw(i, canvas, z, i2, f);
        float f2 = (-measureWidth(i)) / 2.0f;
        if (z) {
            float f3 = this.mCurrentLineSelectHalfHeight;
            canvas.drawRoundRect(f2, -f3, f2 + ((float) this.mLineStopPointWidth), f3, 2.0f, 2.0f, this.mSelectPaint);
        } else if (isStopPoint(i)) {
            float f4 = this.mCurrentLineHalfHeight;
            canvas.drawRoundRect(f2, -f4, f2 + ((float) this.mLineStopPointWidth), f4, 1.0f, 1.0f, this.mStopPointPaint);
        } else {
            float f5 = this.mCurrentLineHalfHeight;
            canvas.drawRoundRect(f2, -f5, f2 + ((float) this.mLineWidth), f5, 1.0f, 1.0f, this.mNormalPaint);
        }
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public Paint.Align getAlign(int i) {
        return null;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public int getCount() {
        int i = 1;
        for (Integer num : this.mRulerLines) {
            i += num.intValue();
        }
        return i;
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public boolean isEnable() {
        return this.mEnable;
    }

    public boolean isFirstStopPoint(int i) {
        return i == 0;
    }

    public boolean isLastStopPoint(int i) {
        return i == getCount() - 1;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public boolean isSingleValueLine(int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < this.mZoomIndexs.size() - 1; i3++) {
            i2 += this.mRulerLines.get(i3).intValue();
            if (i <= i2 && ((float) ((int) (((this.mZoomIndexs.get(i3 + 1).floatValue() * 10.0f) - (this.mZoomIndexs.get(i3).floatValue() * 10.0f)) / ((float) this.mRulerLines.get(i3).intValue())))) == 1.0f) {
                return true;
            }
        }
        return false;
    }

    public boolean isStopPoint(int i) {
        if (isFirstStopPoint(i) || isLastStopPoint(i)) {
            return true;
        }
        int i2 = 0;
        for (Integer num : this.mRulerLines) {
            i2 += num.intValue();
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public Float mapPositionToValue(float f) {
        int i = 0;
        float f2 = this.mZoomRatioMin;
        int i2 = 0;
        while (true) {
            if (i >= this.mZoomIndexs.size() - 1) {
                break;
            }
            i2 += this.mRulerLines.get(i).intValue();
            float f3 = (float) i2;
            if (f <= f3) {
                f2 += ((f - f3) + ((float) this.mRulerLines.get(i).intValue())) * ((this.mZoomIndexs.get(i + 1).floatValue() - this.mZoomIndexs.get(i).floatValue()) / ((float) this.mRulerLines.get(i).intValue()));
                break;
            }
            i++;
            f2 = this.mZoomIndexs.get(i).floatValue();
        }
        return Float.valueOf(f2);
    }

    public float mapValueToPosition(Float f) {
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i >= this.mZoomIndexs.size() - 1) {
                break;
            }
            if (f.floatValue() >= this.mZoomIndexs.get(i).floatValue()) {
                int i3 = i + 1;
                if (f.floatValue() < this.mZoomIndexs.get(i3).floatValue()) {
                    i2 = (int) (((float) i2) + (((f.floatValue() - this.mZoomIndexs.get(i).floatValue()) * ((float) this.mRulerLines.get(i).intValue())) / (this.mZoomIndexs.get(i3).floatValue() - this.mZoomIndexs.get(i).floatValue())));
                    break;
                }
            }
            i2 += this.mRulerLines.get(i).intValue();
            i++;
        }
        return (float) i2;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public float measureWidth(int i) {
        return (float) (isStopPoint(i) ? this.mLineStopPointWidth : this.mLineWidth);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.OnPositionSelectListener
    public void onChangeValue(String str) {
        ZoomValueListener zoomValueListener;
        if (!str.equals(this.mCurrentValue) && (zoomValueListener = this.mZoomValueListener) != null) {
            zoomValueListener.onManuallyDataChanged(str);
            this.mCurrentValue = str;
            this.mCurrentIndex = Math.round(mapValueToPosition(Float.valueOf(Float.parseFloat(this.mCurrentValue))));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0050, code lost:
        if (java.lang.Math.abs(((float) r5.mCurrentIndex) - r6) > 0.95f) goto L_0x0059;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0057, code lost:
        if (r5.mCurrentIndex != r7) goto L_0x0059;
     */
    @Override // com.android.camera.ui.BaseHorizontalZoomView.OnPositionSelectListener
    public void onPositionSelect(View view, int i, float f) {
        String str;
        if (this.mEnable) {
            float f2 = -1.0f;
            boolean z = true;
            if (f != -1.0f) {
                f = Util.clamp(f, 0.0f, 1.0f);
                str = String.valueOf(mapPositionToValue(((float) (getCount() - 1)) * f));
            } else if (i != -1) {
                str = String.valueOf(mapPositionToValue((float) i));
            } else {
                return;
            }
            if (!str.equals(this.mCurrentValue)) {
                if (i == -1) {
                    f2 = ((float) (getCount() - 1)) * f;
                }
                z = false;
                if (this.mZoomValueListener != null && z) {
                    this.mCurrentIndex = i == -1 ? Math.round(f2) : i;
                    this.mZoomValueListener.onZoomItemSlideOn(i, isStopPoint(i));
                }
                ZoomValueListener zoomValueListener = this.mZoomValueListener;
                if (zoomValueListener != null) {
                    zoomValueListener.onManuallyDataChanged(str);
                }
                this.mCurrentValue = str;
            }
        }
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public void setCurrentValue(String str) {
        this.mCurrentValue = String.valueOf(CameraSettings.readZoom());
        this.mCurrentIndex = Math.round(mapValueToPosition(Float.valueOf(Float.parseFloat(this.mCurrentValue))));
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public void setEnable(boolean z) {
        this.mEnable = z;
    }
}
