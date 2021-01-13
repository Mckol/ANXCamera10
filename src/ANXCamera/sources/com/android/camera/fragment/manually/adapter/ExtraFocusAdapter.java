package com.android.camera.fragment.manually.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.data.ComponentData;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.statistic.CameraStatUtils;

public class ExtraFocusAdapter extends AbstractZoomSliderAdapter<Integer> {
    private static final int MAX_POSITION = 1000;
    private static final int[] mImageIds = {R.drawable.ic_focusmode_flag_near_normal, R.drawable.ic_focusmode_flag_far_normal};
    private ComponentData mComponentData;
    private Context mContext;
    private int mCurrentMode;
    private ManuallyListener mManuallyListener;
    private int mTrackedFocusPosition;

    public ExtraFocusAdapter(Context context, ComponentData componentData, int i, ManuallyListener manuallyListener) {
        this.mContext = context;
        this.mComponentData = componentData;
        this.mCurrentMode = i;
        this.mManuallyListener = manuallyListener;
        this.mCurrentValue = componentData.getComponentValue(this.mCurrentMode);
        try {
            this.mTrackedFocusPosition = Integer.parseInt(this.mCurrentValue);
        } catch (NumberFormatException unused) {
            this.mTrackedFocusPosition = 1000;
        }
        initStyle(context);
    }

    private void drawImage(float f, int i, Canvas canvas, int i2) {
        Drawable drawable;
        int mapIndexToImagePosition = mapIndexToImagePosition(i);
        if (mapIndexToImagePosition != -1 && (drawable = this.mContext.getResources().getDrawable(mImageIds[mapIndexToImagePosition])) != null) {
            canvas.save();
            if (i2 == 2) {
                drawable.setBounds(Math.round(f - ((float) (drawable.getIntrinsicWidth() / 2))), Math.round((((float) (-this.mLineTextGap)) - this.mCurrentLineSelectHalfHeight) - ((float) drawable.getIntrinsicHeight())), Math.round(((float) (drawable.getIntrinsicWidth() / 2)) + f), Math.round(((float) (-this.mLineTextGap)) - this.mCurrentLineSelectHalfHeight));
                canvas.rotate(this.mDegree, f, (float) Math.round((((float) (-this.mLineTextGap)) - this.mCurrentLineSelectHalfHeight) - ((float) (drawable.getIntrinsicHeight() / 2))));
            } else {
                drawable.setBounds(Math.round(f - ((float) (drawable.getIntrinsicWidth() / 2))), Math.round((((float) (-this.mLineTextGap)) - this.mLineSelectHalfHeight) - ((float) drawable.getIntrinsicHeight())), Math.round(((float) (drawable.getIntrinsicWidth() / 2)) + f), Math.round(((float) (-this.mLineTextGap)) - this.mLineSelectHalfHeight));
                canvas.rotate(this.mDegree, f, (float) Math.round((((float) (-this.mLineTextGap)) - this.mLineSelectHalfHeight) - ((float) (drawable.getIntrinsicHeight() / 2))));
            }
            drawable.draw(canvas);
            canvas.restore();
        }
    }

    private int mapIndexToImagePosition(int i) {
        if (i == 0) {
            return 0;
        }
        return i == getCount() - 1 ? 1 : -1;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public void draw(int i, Canvas canvas, boolean z, int i2, float f) {
        float f2;
        super.draw(i, canvas, z, i2, f);
        if (isImagePoint(i)) {
            drawImage(0.0f, i, canvas, i2);
            f2 = (float) ((-this.mLineStopPointWidth) / 2);
        } else {
            f2 = (-measureWidth(i)) / 2.0f;
        }
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
        return Paint.Align.LEFT;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public int getCount() {
        return 40;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public void initStyle(Context context) {
        super.initStyle(context);
        this.mLineSelectMovingHalfHeight = ((float) context.getResources().getDimensionPixelSize(R.dimen.manually_line_selected_moving_height)) / 2.0f;
        this.mLineMovingHalfHeight = ((float) context.getResources().getDimensionPixelSize(R.dimen.manually_line_moving_height)) / 2.0f;
        this.mLineHalfHeight = ((float) context.getResources().getDimensionPixelSize(R.dimen.manually_line_height)) / 2.0f;
        this.mLineTextGap = context.getResources().getDimensionPixelSize(R.dimen.manually_item_line_text_gap);
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public boolean isEnable() {
        return false;
    }

    public boolean isImagePoint(int i) {
        return i == 0 || i == getCount() - 1;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public boolean isSingleValueLine(int i) {
        return false;
    }

    public boolean isStopPoint(int i) {
        return i == 0 || i == getCount() - 1 || i == 15 || i == 31;
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public Integer mapPositionToValue(float f) {
        return Integer.valueOf(Util.clamp((Math.round(1000.0f - (f * 1000.0f)) / 10) * 10, 0, 990));
    }

    public float mapValueToPosition(Integer num) {
        return (float) Math.round(((float) Integer.valueOf(1000 - num.intValue()).intValue()) / (1000.0f / ((float) (getCount() - 1))));
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public float measureWidth(int i) {
        return (float) (isStopPoint(i) ? this.mLineStopPointWidth : this.mLineWidth);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.OnPositionSelectListener
    public void onChangeValue(String str) {
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.OnPositionSelectListener
    public void onPositionSelect(View view, int i, float f) {
        int intValue = f == -1.0f ? mapPositionToValue(((float) i) / ((float) (getCount() - 1))).intValue() : mapPositionToValue(Util.clamp(f, 0.0f, 1.0f)).intValue();
        if (this.mTrackedFocusPosition != intValue) {
            this.mTrackedFocusPosition = intValue;
            CameraStatUtils.trackFocusPositionChanged(intValue, this.mCurrentMode);
        }
        String valueOf = String.valueOf(intValue);
        if (!valueOf.equals(this.mCurrentValue)) {
            this.mComponentData.setComponentValue(this.mCurrentMode, valueOf);
            ManuallyListener manuallyListener = this.mManuallyListener;
            if (manuallyListener != null) {
                manuallyListener.onManuallyDataChanged(this.mComponentData, this.mCurrentValue, valueOf, false, this.mCurrentMode);
            }
            this.mCurrentValue = valueOf;
        }
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public void setEnable(boolean z) {
    }
}
