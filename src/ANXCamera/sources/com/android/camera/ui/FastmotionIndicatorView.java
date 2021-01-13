package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.android.camera.R;
import com.android.camera.Util;

public class FastmotionIndicatorView extends View {
    private static final float HEIGHT_COMPENSATION = 1.1f;
    private static final float WIDTH_COMPENSATION = 1.1f;
    private String mDuration = "";
    protected TextPaint mDurationTextPaint;
    protected int mDurationTextSize;
    private String mNumber = "";
    protected Paint mPaint;
    private String mSpeed = "";
    protected TextPaint mSpeedTextPaint;
    protected int mSpeedTextSize;
    protected int mTextColor;
    protected int mlineColor;
    protected int mlineGap;
    protected int mlineHeight;
    protected int mlineWidth;

    public FastmotionIndicatorView(Context context) {
        super(context);
        init(context);
    }

    public FastmotionIndicatorView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public FastmotionIndicatorView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public FastmotionIndicatorView(Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(context);
    }

    public void init(Context context) {
        this.mSpeedTextSize = context.getResources().getDimensionPixelSize(R.dimen.fastmotion_indicator_speed_text_size);
        this.mDurationTextSize = context.getResources().getDimensionPixelSize(R.dimen.fastmotion_indicator_duration_text_size);
        this.mTextColor = context.getResources().getColor(R.color.zoom_popup_color_new_default);
        this.mlineColor = context.getResources().getColor(R.color.mode_edit_sub_title_color);
        this.mlineHeight = context.getResources().getDimensionPixelSize(R.dimen.fastmotionview_line_height);
        this.mlineWidth = context.getResources().getDimensionPixelSize(R.dimen.fastmotionview_line_width);
        this.mlineGap = context.getResources().getDimensionPixelSize(R.dimen.fastmotionview_line_gap);
        this.mSpeedTextPaint = new TextPaint(1);
        this.mSpeedTextPaint.setAntiAlias(true);
        this.mSpeedTextPaint.setTextSize((float) this.mSpeedTextSize);
        this.mSpeedTextPaint.setColor(this.mTextColor);
        this.mSpeedTextPaint.setTypeface(Util.getMiuiTypeface());
        this.mSpeedTextPaint.setShadowLayer(1.0f, 0.0f, 0.0f, Integer.MIN_VALUE);
        this.mSpeedTextPaint.setTypeface(Typeface.defaultFromStyle(1));
        this.mDurationTextPaint = new TextPaint(1);
        this.mDurationTextPaint.setAntiAlias(true);
        this.mDurationTextPaint.setTextSize((float) this.mDurationTextSize);
        this.mDurationTextPaint.setColor(this.mTextColor);
        this.mDurationTextPaint.setTypeface(Util.getMiuiTypeface());
        this.mDurationTextPaint.setShadowLayer(1.0f, 0.0f, 0.0f, Integer.MIN_VALUE);
        this.mDurationTextPaint.setTypeface(Typeface.defaultFromStyle(1));
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(this.mlineColor);
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (!TextUtils.isEmpty(this.mSpeed) && !TextUtils.isEmpty(this.mDuration)) {
            Rect rect = new Rect();
            TextPaint textPaint = this.mSpeedTextPaint;
            String str = this.mSpeed;
            textPaint.getTextBounds(str, 0, str.length(), rect);
            Rect rect2 = new Rect();
            TextPaint textPaint2 = this.mDurationTextPaint;
            String str2 = this.mDuration;
            textPaint2.getTextBounds(str2, 0, str2.length(), rect2);
            int round = Math.round(((float) Math.max(Math.max(rect.height(), rect2.height()), this.mlineHeight)) * 1.1f);
            Paint.FontMetricsInt fontMetricsInt = this.mSpeedTextPaint.getFontMetricsInt();
            Paint.FontMetricsInt fontMetricsInt2 = this.mDurationTextPaint.getFontMetricsInt();
            canvas.drawText(this.mSpeed, 0.0f, (float) (((round - fontMetricsInt.bottom) - fontMetricsInt.top) / 2), this.mSpeedTextPaint);
            canvas.drawRoundRect((float) (rect.width() + this.mlineGap), (float) ((round - this.mlineHeight) / 2), (float) (rect.width() + this.mlineGap + this.mlineWidth), (float) ((this.mlineHeight + round) / 2), 2.0f, 2.0f, this.mPaint);
            if (TextUtils.isEmpty(this.mNumber)) {
                canvas.drawText(this.mDuration, (float) (rect.width() + (this.mlineGap * 2) + this.mlineWidth), (float) (((round - fontMetricsInt2.bottom) - fontMetricsInt2.top) / 2), this.mDurationTextPaint);
                return;
            }
            Rect rect3 = new Rect();
            TextPaint textPaint3 = this.mSpeedTextPaint;
            String str3 = this.mNumber;
            textPaint3.getTextBounds(str3, 0, str3.length(), rect3);
            canvas.drawText(this.mNumber, (float) (rect.width() + (this.mlineGap * 2) + this.mlineWidth), (float) (((round - fontMetricsInt.bottom) - fontMetricsInt.top) / 2), this.mSpeedTextPaint);
            canvas.drawText(this.mDuration, (float) (rect.width() + rect3.width() + (this.mlineGap * 2) + this.mlineWidth + 4), (float) (((round - fontMetricsInt2.bottom) - fontMetricsInt2.top) / 2), this.mDurationTextPaint);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        if (!TextUtils.isEmpty(this.mSpeed) && !TextUtils.isEmpty(this.mDuration)) {
            Rect rect = new Rect();
            TextPaint textPaint = this.mSpeedTextPaint;
            String str = this.mSpeed;
            textPaint.getTextBounds(str, 0, str.length(), rect);
            Rect rect2 = new Rect();
            TextPaint textPaint2 = this.mDurationTextPaint;
            String str2 = this.mDuration;
            textPaint2.getTextBounds(str2, 0, str2.length(), rect2);
            int round = Math.round(((float) Math.max(Math.max(rect.height(), rect2.height()), this.mlineHeight)) * 1.1f);
            if (TextUtils.isEmpty(this.mNumber)) {
                i4 = rect.width() + rect2.width() + this.mlineWidth;
                i3 = this.mlineGap;
            } else {
                Rect rect3 = new Rect();
                TextPaint textPaint3 = this.mSpeedTextPaint;
                String str3 = this.mNumber;
                textPaint3.getTextBounds(str3, 0, str3.length(), rect3);
                i4 = rect.width() + rect3.width() + rect2.width() + this.mlineWidth;
                i3 = this.mlineGap;
            }
            setMeasuredDimension(Math.round(((float) (i4 + (i3 * 2))) * 1.1f), round);
        }
    }

    public void showFastmotion(String str, String str2, String str3) {
        this.mSpeed = str;
        this.mNumber = str2;
        this.mDuration = str3;
        requestLayout();
    }
}
