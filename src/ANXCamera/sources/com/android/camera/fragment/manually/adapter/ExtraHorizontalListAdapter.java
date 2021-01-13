package com.android.camera.fragment.manually.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.log.Log;
import java.util.ArrayList;
import java.util.List;

public class ExtraHorizontalListAdapter extends AbstractZoomSliderAdapter<String> {
    private static final String TAG = "ExtraHorizontalListAdapter";
    private static final int[] sFastmotionDurationInfinityImages = {R.drawable.ic_fastmotion_duration_infinity_back, R.drawable.ic_fastmotion_duration_infinity_front};
    private ComponentData mComponentData;
    private Context mContext;
    private int mCurrentMode;
    private List<ComponentDataItem> mItems = new ArrayList(this.mComponentData.getItems());
    private ManuallyListener mManuallyListener;

    public ExtraHorizontalListAdapter(Context context, ComponentData componentData, int i, ManuallyListener manuallyListener) {
        this.mContext = context;
        this.mComponentData = componentData;
        this.mCurrentMode = i;
        this.mManuallyListener = manuallyListener;
        List<ComponentDataItem> list = this.mItems;
        boolean z = false;
        if (list != null && !list.isEmpty() && (this.mComponentData.getDisplayTitleString() == R.string.pref_camera_iso_title_abbr || this.mComponentData.getDisplayTitleString() == R.string.pref_manual_exposure_title_abbr)) {
            this.mItems.remove(0);
        }
        this.mNeedIllegalLine = this.mItems.size() < 7 ? true : z;
        initStyle(context);
        this.mCurrentValue = componentData.getComponentValue(this.mCurrentMode);
    }

    private void changeValue(int i) {
        List<ComponentDataItem> list = this.mItems;
        if (list == null || list.isEmpty()) {
            String str = TAG;
            Log.d(str, "Error change value, items is " + list);
            return;
        }
        String str2 = list.get(Util.clamp(i, 0, list.size() - 1)).mValue;
        if (str2 != null && !str2.equals(this.mCurrentValue)) {
            this.mComponentData.setComponentValue(this.mCurrentMode, str2);
            ManuallyListener manuallyListener = this.mManuallyListener;
            if (manuallyListener != null) {
                manuallyListener.onManuallyDataChanged(this.mComponentData, this.mCurrentValue, str2, false, this.mCurrentMode);
            }
            this.mCurrentValue = str2;
        }
    }

    private void drawIllegalLine(int i, Canvas canvas, float f) {
        if (needDrawIllegalLine(i)) {
            float f2 = (this.mIsRSL ? -this.mItemGap : this.mItemGap) / 3.0f;
            float f3 = f + f2;
            int i2 = this.mLineWidth;
            float f4 = this.mCurrentLineIllegalHalfHeight;
            canvas.drawRoundRect(f3 - ((float) (i2 / 2)), -f4, f3 + ((float) (i2 / 2)), f4, 1.0f, 1.0f, this.mIllegalLinePaint);
            float f5 = f3 + f2;
            int i3 = this.mLineWidth;
            float f6 = this.mCurrentLineIllegalHalfHeight;
            canvas.drawRoundRect(f5 - ((float) (i3 / 2)), -f6, f5 + ((float) (i3 / 2)), f6, 1.0f, 1.0f, this.mIllegalLinePaint);
        }
    }

    private void drawImage(float f, int i, boolean z, Canvas canvas, int i2) {
        if (i == 0) {
            for (int i3 = 0; i3 < sFastmotionDurationInfinityImages.length; i3++) {
                Drawable drawable = this.mContext.getResources().getDrawable(sFastmotionDurationInfinityImages[i3]);
                canvas.save();
                if (i2 == 2) {
                    drawable.setBounds(Math.round(f - ((float) (drawable.getIntrinsicWidth() / 2))), Math.round(((((float) (-this.mLineTextGap)) - this.mCurrentLineSelectHalfHeight) - ((float) drawable.getIntrinsicHeight())) + ((float) this.mContext.getResources().getDimensionPixelSize(R.dimen.fastmotion_duration_infinity_icon_gap))), Math.round(((float) (drawable.getIntrinsicWidth() / 2)) + f), Math.round((((float) (-this.mLineTextGap)) - this.mCurrentLineSelectHalfHeight) + ((float) this.mContext.getResources().getDimensionPixelSize(R.dimen.fastmotion_duration_infinity_icon_gap))));
                    canvas.rotate(this.mDegree, f, (float) Math.round(((((float) (-this.mLineTextGap)) - this.mCurrentLineSelectHalfHeight) - ((float) (drawable.getIntrinsicHeight() / 2))) + ((float) this.mContext.getResources().getDimensionPixelSize(R.dimen.fastmotion_duration_infinity_icon_gap))));
                } else {
                    drawable.setBounds(Math.round(f - ((float) (drawable.getIntrinsicWidth() / 2))), Math.round(((((float) (-this.mLineTextGap)) - this.mLineSelectHalfHeight) - ((float) drawable.getIntrinsicHeight())) + ((float) this.mContext.getResources().getDimensionPixelSize(R.dimen.fastmotion_duration_infinity_icon_gap))), Math.round(((float) (drawable.getIntrinsicWidth() / 2)) + f), Math.round((((float) (-this.mLineTextGap)) - this.mLineSelectHalfHeight) + ((float) this.mContext.getResources().getDimensionPixelSize(R.dimen.fastmotion_duration_infinity_icon_gap))));
                    canvas.rotate(this.mDegree, f, (float) Math.round(((((float) (-this.mLineTextGap)) - this.mLineSelectHalfHeight) - ((float) (drawable.getIntrinsicHeight() / 2))) + ((float) this.mContext.getResources().getDimensionPixelSize(R.dimen.fastmotion_duration_infinity_icon_gap))));
                }
                drawable.draw(canvas);
                canvas.restore();
            }
        }
    }

    private void drawText(float f, int i, Canvas canvas, int i2) {
        ComponentDataItem componentDataItem = this.mItems.get(i);
        String string = TextUtils.isEmpty(componentDataItem.mDisplayNameStr) ? this.mContext.getResources().getString(componentDataItem.mDisplayNameRes) : componentDataItem.mDisplayNameStr;
        Rect rect = new Rect();
        this.mTextPaint.getTextBounds(string, 0, string.length(), rect);
        canvas.save();
        if (i2 == 2) {
            canvas.rotate(this.mDegree, f, (((float) (-this.mLineTextGap)) - this.mCurrentLineSelectHalfHeight) - ((float) (rect.height() / 2)));
            float f2 = this.mDegree;
            if (f2 == 90.0f) {
                canvas.translate((float) (((-rect.width()) / 2) + (rect.height() / 2)), 0.0f);
            } else if (f2 == 270.0f) {
                canvas.translate((float) ((rect.width() / 2) - (rect.height() / 2)), 0.0f);
            }
            canvas.drawText(string, f, (((float) (-this.mLineTextGap)) - this.mCurrentLineSelectHalfHeight) - ((float) rect.bottom), this.mTextPaint);
        } else {
            canvas.rotate(this.mDegree, f, (((float) (-this.mLineTextGap)) - this.mLineSelectHalfHeight) - ((float) (rect.height() / 2)));
            float f3 = this.mDegree;
            if (f3 == 90.0f) {
                canvas.translate((float) (((-rect.width()) / 2) + (rect.height() / 2)), 0.0f);
            } else if (f3 == 270.0f) {
                canvas.translate((float) ((rect.width() / 2) - (rect.height() / 2)), 0.0f);
            }
            canvas.drawText(string, f, (((float) (-this.mLineTextGap)) - this.mLineSelectHalfHeight) - ((float) rect.bottom), this.mTextPaint);
        }
        canvas.restore();
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean isStopPoint(int i) {
        int displayTitleString = this.mComponentData.getDisplayTitleString();
        if (i == 0 || i == getCount() - 1) {
            return true;
        }
        switch (displayTitleString) {
            case R.string.pref_camera_fastmotion_duration:
            case R.string.pref_camera_fastmotion_speed:
                return i % 3 == 0;
            case R.string.pref_camera_iso_title_abbr:
            case R.string.pref_manual_exposure_title_abbr:
                float count = (float) (getCount() / 3);
                if (count < 3.0f) {
                    count = (float) (getCount() / 2);
                }
                if (count >= 3.0f) {
                    return i % Math.round(count) == 0 && (getCount() - 1) - i >= 3;
                }
                break;
            case R.string.pref_camera_manually_exposure_value_abbr:
                return getCount() > 14 ? i % Math.round((float) (getCount() / 4)) == 0 : i % Math.round((float) (getCount() / 2)) == 0;
        }
        return false;
    }

    private boolean needDrawIllegalLine(int i) {
        return this.mNeedIllegalLine && i < getCount() - 1;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public void draw(int i, Canvas canvas, boolean z, int i2, float f) {
        ComponentData componentData;
        super.draw(i, canvas, z, i2, f);
        if (isTextPoint(i) && !(i == 0 && (componentData = this.mComponentData) != null && componentData.getDisplayTitleString() == R.string.pref_camera_fastmotion_duration)) {
            drawText(0.0f, i, canvas, i2);
        }
        ComponentData componentData2 = this.mComponentData;
        if (componentData2 != null && componentData2.getDisplayTitleString() == R.string.pref_camera_fastmotion_duration) {
            drawImage(0.0f, i, z, canvas, i2);
        }
        canvas.save();
        if (isStopPoint(i)) {
            int i3 = this.mLineStopPointWidth;
            float f2 = this.mCurrentLineHalfHeight;
            canvas.drawRoundRect(0.0f - ((float) (i3 / 2)), -f2, 0.0f + ((float) (i3 / 2)), f2, 1.0f, 1.0f, this.mStopPointPaint);
            drawIllegalLine(i, canvas, 0.0f);
        } else if (i != -1) {
            int i4 = this.mLineWidth;
            float f3 = this.mCurrentLineHalfHeight;
            canvas.drawRoundRect(0.0f - ((float) (i4 / 2)), -f3, 0.0f + ((float) (i4 / 2)), f3, 1.0f, 1.0f, this.mNormalPaint);
            drawIllegalLine(i, canvas, 0.0f);
        }
        if (z) {
            int i5 = this.mLineStopPointWidth;
            float f4 = (float) ((-i5) / 2);
            float f5 = this.mCurrentLineSelectHalfHeight;
            canvas.drawRoundRect(f4, -f5, f4 + ((float) i5), f5, 2.0f, 2.0f, this.mSelectPaint);
        }
        canvas.restore();
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public Paint.Align getAlign(int i) {
        return null;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public int getCount() {
        return this.mItems.size();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public void initStyle(Context context) {
        super.initStyle(context);
        this.mLineTextGap = context.getResources().getDimensionPixelSize(R.dimen.manually_item_line_text_gap);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mLineSelectMovingHalfHeight = ((float) context.getResources().getDimensionPixelSize(R.dimen.manually_line_selected_moving_height)) / 2.0f;
        this.mLineMovingHalfHeight = ((float) context.getResources().getDimensionPixelSize(R.dimen.manually_line_moving_height)) / 2.0f;
        this.mLineHalfHeight = ((float) context.getResources().getDimensionPixelSize(R.dimen.manually_line_height)) / 2.0f;
        this.mSelectPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public boolean isEnable() {
        return false;
    }

    public boolean isTextPoint(int i) {
        if (!this.mNeedIllegalLine) {
            return isStopPoint(i);
        }
        if (i == 0 || i == getCount() - 1) {
            return true;
        }
        float count = (float) (getCount() / 3);
        if (count >= 2.0f) {
            return i % Math.round(count) == 0;
        }
        return false;
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public String mapPositionToValue(float f) {
        return this.mItems.get(Util.clamp(Math.round(f), 0, getCount() - 1)).mValue;
    }

    public float mapValueToPosition(String str) {
        int count = getCount();
        for (int i = 0; i < count; i++) {
            if (str.equals(this.mItems.get(i).mValue)) {
                return (float) i;
            }
        }
        return -1.0f;
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
        if (i == -1) {
            if (f != -1.0f) {
                i = Math.round(f * ((float) (getCount() - 1)));
            } else {
                return;
            }
        }
        changeValue(i);
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public void setEnable(boolean z) {
    }
}
