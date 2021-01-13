package miui.smooth;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.miui.internal.smooth.SmoothPathProvider;
import miui.R;

public class SmoothFrameLayout extends FrameLayout {
    private Path mClipPath;
    private RectF mLayer;
    private Paint mPaint;
    private SmoothPathProvider mPathProvider;
    private float[] mRadii;
    private float mRadius;
    private float mSmooth;

    public SmoothFrameLayout(Context context) {
        this(context, null);
    }

    public SmoothFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SmoothFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaint = new Paint(1);
        this.mClipPath = new Path();
        this.mLayer = new RectF();
        this.mPathProvider = new SmoothPathProvider();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MiuiSmoothFrameLayout);
        setCornerRadius((float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiuiSmoothFrameLayout_android_radius, 0));
        if (obtainStyledAttributes.hasValue(R.styleable.MiuiSmoothFrameLayout_android_topLeftRadius) || obtainStyledAttributes.hasValue(R.styleable.MiuiSmoothFrameLayout_android_topRightRadius) || obtainStyledAttributes.hasValue(R.styleable.MiuiSmoothFrameLayout_android_bottomRightRadius) || obtainStyledAttributes.hasValue(R.styleable.MiuiSmoothFrameLayout_android_bottomLeftRadius)) {
            float dimensionPixelSize = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiuiSmoothFrameLayout_android_topLeftRadius, 0);
            float dimensionPixelSize2 = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiuiSmoothFrameLayout_android_topRightRadius, 0);
            float dimensionPixelSize3 = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiuiSmoothFrameLayout_android_bottomRightRadius, 0);
            float dimensionPixelSize4 = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.MiuiSmoothFrameLayout_android_bottomLeftRadius, 0);
            setCornerRadii(new float[]{dimensionPixelSize, dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize3, dimensionPixelSize3, dimensionPixelSize4, dimensionPixelSize4});
        }
        this.mSmooth = obtainStyledAttributes.getFloat(R.styleable.MiuiSmoothFrameLayout_miui_smooth, 0.7f);
        obtainStyledAttributes.recycle();
    }

    private Path getSmoothPathFromProvider(int i, int i2, float f) {
        float[] cornerRadii = getCornerRadii();
        if (cornerRadii == null) {
            this.mPathProvider.buildSmoothData(i, i2, getCornerRadius(), (double) f);
        } else {
            this.mPathProvider.buildSmoothData(i, i2, cornerRadii, (double) f);
        }
        return this.mPathProvider.getSmoothPath();
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        int saveLayer = canvas.saveLayer(this.mLayer, null, 31);
        super.dispatchDraw(canvas);
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawPath(this.mClipPath, this.mPaint);
        canvas.restoreToCount(saveLayer);
    }

    public void draw(Canvas canvas) {
        int saveLayer = canvas.saveLayer(this.mLayer, null, 31);
        super.draw(canvas);
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawPath(this.mClipPath, this.mPaint);
        canvas.restoreToCount(saveLayer);
    }

    public float[] getCornerRadii() {
        float[] fArr = this.mRadii;
        if (fArr == null) {
            return null;
        }
        return (float[]) fArr.clone();
    }

    public float getCornerRadius() {
        return this.mRadius;
    }

    public float getSmooth() {
        return this.mSmooth;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        float f = (float) i;
        float f2 = (float) i2;
        this.mLayer.set(0.0f, 0.0f, f, f2);
        Path smoothPathFromProvider = getSmoothPathFromProvider(i, i2, this.mSmooth);
        this.mClipPath.reset();
        this.mClipPath.addRect(0.0f, 0.0f, f, f2, Path.Direction.CW);
        this.mClipPath.op(smoothPathFromProvider, Path.Op.DIFFERENCE);
    }

    public void setCornerRadii(float[] fArr) {
        this.mRadii = fArr;
        if (fArr == null) {
            this.mRadius = 0.0f;
        }
        invalidate();
    }

    public void setCornerRadius(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.mRadius = f;
        this.mRadii = null;
        invalidate();
    }

    public void setSmooth(float f) {
        if (this.mSmooth != f) {
            this.mSmooth = f;
            invalidate();
        }
    }
}
