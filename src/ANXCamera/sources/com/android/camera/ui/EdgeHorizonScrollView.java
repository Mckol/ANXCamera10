package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.android.camera.R;
import com.android.camera.Util;

public class EdgeHorizonScrollView extends FrameLayout {
    private Paint mEdgePaint;
    private int mEdgeWidth;
    private boolean mIsRTL;

    public EdgeHorizonScrollView(Context context) {
        super(context);
        initView();
    }

    public EdgeHorizonScrollView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public EdgeHorizonScrollView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j) {
        int width = getWidth();
        int height = getHeight();
        float f = (float) height;
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, (float) Math.max(width, view.getWidth()), f, null, 31);
        boolean drawChild = super.drawChild(canvas, view, j);
        canvas.translate(this.mIsRTL ? (float) Math.max(0, view.getWidth() - width) : 0.0f, 0.0f);
        float f2 = (float) width;
        float f3 = f2 / 2.0f;
        float f4 = f / 2.0f;
        float f5 = ((float) (height - width)) / 2.0f;
        int save = canvas.save();
        canvas.rotate(90.0f, f3, f4);
        canvas.translate(0.0f, f5);
        float f6 = 0.0f - f5;
        float f7 = f2 + f5;
        canvas.drawRect(f6, 0.0f, f7, (float) this.mEdgeWidth, this.mEdgePaint);
        canvas.restoreToCount(save);
        int save2 = canvas.save();
        canvas.rotate(-90.0f, f3, f4);
        canvas.translate(0.0f, f5);
        canvas.drawRect(f6, 0.0f, f7, (float) this.mEdgeWidth, this.mEdgePaint);
        canvas.restoreToCount(save2);
        canvas.restoreToCount(saveLayer);
        return drawChild;
    }

    public void initView() {
        this.mIsRTL = Util.isLayoutRTL(getContext());
        this.mEdgeWidth = getResources().getDimensionPixelSize(R.dimen.mode_select_layout_edge);
        this.mEdgePaint = new Paint();
        this.mEdgePaint.setAntiAlias(true);
        this.mEdgePaint.setStyle(Paint.Style.FILL);
        this.mEdgePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        this.mEdgePaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, (float) this.mEdgeWidth, new int[]{ViewCompat.MEASURED_STATE_MASK, -1728053248, 0}, new float[]{0.0f, 0.3f, 2.0f}, Shader.TileMode.CLAMP));
        setFocusable(false);
    }
}
