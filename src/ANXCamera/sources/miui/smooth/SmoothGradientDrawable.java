package miui.smooth;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import com.miui.internal.smooth.SmoothPathProvider;
import java.io.IOException;
import miui.R;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SmoothGradientDrawable extends GradientDrawable {
    private BitmapShader mBitmapShader;
    private Paint mPaint;
    private GradientDrawable mParentDrawable;
    private Path mPath;
    private SmoothPathProvider mPathProvider;
    protected SmoothConstantState mSmoothConstantState;

    /* access modifiers changed from: protected */
    public static class SmoothConstantState extends Drawable.ConstantState {
        Drawable.ConstantState mParent;
        float mSmooth = 0.7f;

        public SmoothConstantState() {
        }

        public SmoothConstantState(SmoothConstantState smoothConstantState) {
            this.mSmooth = smoothConstantState.mSmooth;
            this.mParent = smoothConstantState.mParent;
        }

        public boolean canApplyTheme() {
            return true;
        }

        public int getChangingConfigurations() {
            return this.mParent.getChangingConfigurations();
        }

        public Drawable newDrawable() {
            if (this.mParent == null) {
                return null;
            }
            return new SmoothGradientDrawable(null, null, this);
        }

        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            if (this.mParent == null) {
                return null;
            }
            return newSmoothGradientDrawable(resources, theme, new SmoothConstantState(this));
        }

        /* access modifiers changed from: protected */
        public Drawable newSmoothGradientDrawable(Resources resources, Resources.Theme theme, SmoothConstantState smoothConstantState) {
            return new SmoothGradientDrawable(resources, theme, smoothConstantState);
        }

        public void setConstantState(Drawable.ConstantState constantState) {
            this.mParent = constantState;
        }
    }

    public SmoothGradientDrawable() {
        this(new SmoothConstantState());
    }

    private SmoothGradientDrawable(Resources resources, Resources.Theme theme, SmoothConstantState smoothConstantState) {
        this.mPaint = new Paint(1);
        Drawable drawable = null;
        this.mPath = null;
        this.mPathProvider = new SmoothPathProvider();
        if (resources == null) {
            drawable = smoothConstantState.mParent.newDrawable();
        } else if (theme == null) {
            drawable = smoothConstantState.mParent.newDrawable(resources);
        } else if (Build.VERSION.SDK_INT >= 21) {
            drawable = smoothConstantState.mParent.newDrawable(resources, theme);
        }
        if (drawable != null) {
            smoothConstantState.mParent = drawable.getConstantState();
        }
        this.mParentDrawable = (GradientDrawable) drawable;
        this.mSmoothConstantState = smoothConstantState;
        this.mSmoothConstantState.setConstantState(smoothConstantState.mParent);
    }

    private SmoothGradientDrawable(SmoothConstantState smoothConstantState) {
        this.mPaint = new Paint(1);
        this.mPath = null;
        this.mPathProvider = new SmoothPathProvider();
        this.mSmoothConstantState = smoothConstantState;
        this.mSmoothConstantState.setConstantState(super.getConstantState());
    }

    private Bitmap getBitmapFromDrawable() {
        Bitmap createBitmap = Bitmap.createBitmap(getBounds().width(), getBounds().height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        GradientDrawable gradientDrawable = this.mParentDrawable;
        if (gradientDrawable != null) {
            gradientDrawable.draw(canvas);
        } else {
            super.draw(canvas);
        }
        return createBitmap;
    }

    private BitmapShader getBitmapShaderFromDrawable() {
        Bitmap bitmapFromDrawable = getBitmapFromDrawable();
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        return new BitmapShader(bitmapFromDrawable, tileMode, tileMode);
    }

    private float[] getCornerRadiiInner() {
        try {
            return this.mParentDrawable != null ? this.mParentDrawable.getCornerRadii() : getCornerRadii();
        } catch (Exception unused) {
            return null;
        }
    }

    private float getCornerRadiusInner() {
        GradientDrawable gradientDrawable = this.mParentDrawable;
        return gradientDrawable != null ? gradientDrawable.getCornerRadius() : getCornerRadius();
    }

    private Path getSmoothPathFromProvider(Rect rect, float f) {
        float[] cornerRadiiInner = getCornerRadiiInner();
        if (cornerRadiiInner == null) {
            this.mPathProvider.buildSmoothData(rect.width(), rect.height(), getCornerRadiusInner(), (double) f);
        } else {
            this.mPathProvider.buildSmoothData(rect.width(), rect.height(), cornerRadiiInner, (double) f);
        }
        return this.mPathProvider.getSmoothPath();
    }

    private TypedArray obtainAttributes(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] iArr) {
        return theme == null ? resources.obtainAttributes(attributeSet, iArr) : theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    public void draw(Canvas canvas) {
        if (this.mBitmapShader == null) {
            this.mBitmapShader = getBitmapShaderFromDrawable();
            this.mPaint.setShader(this.mBitmapShader);
        }
        if (this.mPath == null) {
            this.mPath = getSmoothPathFromProvider(getBounds(), getSmooth());
        }
        Path path = this.mPath;
        if (path != null) {
            canvas.drawPath(path, this.mPaint);
        }
    }

    public Drawable.ConstantState getConstantState() {
        return this.mSmoothConstantState;
    }

    public float getSmooth() {
        return this.mSmoothConstantState.mSmooth;
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws IOException, XmlPullParserException {
        TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.MiuiSmoothGradientDrawable);
        this.mSmoothConstantState.mSmooth = obtainAttributes.getFloat(R.styleable.MiuiSmoothGradientDrawable_miui_smooth, 0.7f);
        obtainAttributes.recycle();
        super.inflate(resources, xmlPullParser, attributeSet, theme);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mPath = null;
        this.mBitmapShader = null;
        this.mPaint.setShader(null);
        GradientDrawable gradientDrawable = this.mParentDrawable;
        if (gradientDrawable != null) {
            gradientDrawable.setBounds(rect);
        }
    }

    public void setSmooth(float f) {
        SmoothConstantState smoothConstantState = this.mSmoothConstantState;
        if (smoothConstantState.mSmooth != f) {
            smoothConstantState.mSmooth = f;
            invalidateSelf();
        }
    }
}
