package miui.smooth;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import com.miui.internal.smooth.SmoothPathProvider;
import java.io.IOException;
import miui.R;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SmoothContainerDrawable extends Drawable implements Drawable.Callback {
    private BitmapShader mBitmapShader;
    private ContainerState mContainerState;
    private Paint mPaint;
    private Path mPath;
    private SmoothPathProvider mPathProvider;

    /* access modifiers changed from: package-private */
    public static class ChildDrawable {
        Drawable mDrawable;

        ChildDrawable() {
        }

        ChildDrawable(ChildDrawable childDrawable, SmoothContainerDrawable smoothContainerDrawable, Resources resources, Resources.Theme theme) {
            Drawable drawable;
            Drawable drawable2 = childDrawable.mDrawable;
            if (drawable2 != null) {
                Drawable.ConstantState constantState = drawable2.getConstantState();
                drawable = constantState == null ? drawable2 : resources == null ? constantState.newDrawable() : theme == null ? constantState.newDrawable(resources) : Build.VERSION.SDK_INT >= 21 ? constantState.newDrawable(resources, theme) : constantState.newDrawable(resources);
                drawable.setLayoutDirection(drawable2.getLayoutDirection());
                drawable.setBounds(drawable2.getBounds());
                drawable.setLevel(drawable2.getLevel());
                drawable.setCallback(smoothContainerDrawable);
            } else {
                drawable = null;
            }
            this.mDrawable = drawable;
        }
    }

    /* access modifiers changed from: package-private */
    public static final class ContainerState extends Drawable.ConstantState {
        ChildDrawable mChildDrawable;
        float[] mRadii;
        float mRadius;
        float mSmooth;

        public ContainerState() {
        }

        public ContainerState(ContainerState containerState, SmoothContainerDrawable smoothContainerDrawable, Resources resources, Resources.Theme theme) {
            this.mChildDrawable = new ChildDrawable(containerState.mChildDrawable, smoothContainerDrawable, resources, theme);
            this.mRadius = containerState.mRadius;
            this.mRadii = containerState.mRadii;
            this.mSmooth = containerState.mSmooth;
        }

        public boolean canApplyTheme() {
            return super.canApplyTheme();
        }

        public int getAlpha() {
            return this.mChildDrawable.mDrawable.getAlpha();
        }

        public Rect getBounds() {
            return this.mChildDrawable.mDrawable.getBounds();
        }

        public int getChangingConfigurations() {
            return this.mChildDrawable.mDrawable.getChangingConfigurations();
        }

        public Rect getDirtyBounds() {
            return this.mChildDrawable.mDrawable.getDirtyBounds();
        }

        public int getIntrinsicHeight() {
            return this.mChildDrawable.mDrawable.getIntrinsicHeight();
        }

        public int getIntrinsicWidth() {
            return this.mChildDrawable.mDrawable.getIntrinsicWidth();
        }

        public int getOpacity() {
            return this.mChildDrawable.mDrawable.getOpacity();
        }

        public boolean getPadding(Rect rect) {
            return this.mChildDrawable.mDrawable.getPadding(rect);
        }

        public final boolean isStateful() {
            return this.mChildDrawable.mDrawable.isStateful();
        }

        public void jumpToCurrentState() {
            this.mChildDrawable.mDrawable.jumpToCurrentState();
        }

        public Drawable newDrawable() {
            return new SmoothContainerDrawable(null, null, this);
        }

        public Drawable newDrawable(Resources resources) {
            return new SmoothContainerDrawable(resources, null, this);
        }

        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            return new SmoothContainerDrawable(resources, theme, this);
        }

        public void onBoundsChange(Rect rect) {
            this.mChildDrawable.mDrawable.setBounds(rect);
        }

        public boolean onStateChange(int[] iArr) {
            return isStateful() && this.mChildDrawable.mDrawable.setState(iArr);
        }

        public void setAlpha(int i) {
            this.mChildDrawable.mDrawable.setAlpha(i);
        }

        public void setBounds(int i, int i2, int i3, int i4) {
            this.mChildDrawable.mDrawable.setBounds(i, i2, i3, i4);
        }

        public void setBounds(Rect rect) {
            this.mChildDrawable.mDrawable.setBounds(rect);
        }

        public void setChangingConfigurations(int i) {
            this.mChildDrawable.mDrawable.setChangingConfigurations(i);
        }

        public void setColorFilter(ColorFilter colorFilter) {
            this.mChildDrawable.mDrawable.setColorFilter(colorFilter);
        }

        public void setDither(boolean z) {
            this.mChildDrawable.mDrawable.setDither(z);
        }

        public void setFilterBitmap(boolean z) {
            this.mChildDrawable.mDrawable.setFilterBitmap(z);
        }
    }

    public SmoothContainerDrawable() {
        this.mPaint = new Paint(1);
        this.mPath = null;
        this.mPathProvider = new SmoothPathProvider();
        this.mContainerState = new ContainerState();
    }

    private SmoothContainerDrawable(Resources resources, Resources.Theme theme, ContainerState containerState) {
        this.mPaint = new Paint(1);
        this.mPath = null;
        this.mPathProvider = new SmoothPathProvider();
        this.mContainerState = new ContainerState(containerState, this, resources, theme);
    }

    private BitmapShader getBitmapShaderFromDrawable() {
        Bitmap createBitmap = Bitmap.createBitmap(getBoundsInner().width(), getBoundsInner().height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.save();
        canvas.translate((float) (-getBoundsInner().left), (float) (-getBoundsInner().top));
        this.mContainerState.mChildDrawable.mDrawable.draw(canvas);
        canvas.restore();
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        return new BitmapShader(createBitmap, tileMode, tileMode);
    }

    private Path getSmoothPathFromProvider(Rect rect, float f) {
        float[] cornerRadii = getCornerRadii();
        if (cornerRadii == null) {
            this.mPathProvider.buildSmoothData(rect.width(), rect.height(), getCornerRadius(), (double) f);
        } else {
            this.mPathProvider.buildSmoothData(rect.width(), rect.height(), cornerRadii, (double) f);
        }
        return this.mPathProvider.getSmoothPath();
    }

    private void inflateInnerDrawable(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws IOException, XmlPullParserException {
        int next;
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 != 1) {
                int depth2 = xmlPullParser.getDepth();
                if (depth2 < depth && next2 == 3) {
                    return;
                }
                if (next2 == 2 && depth2 <= depth && xmlPullParser.getName().equals("child")) {
                    do {
                        next = xmlPullParser.next();
                    } while (next == 4);
                    if (next == 2) {
                        ChildDrawable childDrawable = new ChildDrawable();
                        childDrawable.mDrawable = Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
                        childDrawable.mDrawable.setCallback(this);
                        this.mContainerState.mChildDrawable = childDrawable;
                        return;
                    }
                    throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <child> tag requires a 'drawable' attribute or child tag defining a drawable");
                }
            } else {
                return;
            }
        }
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
            this.mPath = getSmoothPathFromProvider(getBoundsInner(), getSmooth());
        }
        canvas.save();
        canvas.translate((float) getBoundsInner().left, (float) getBoundsInner().top);
        canvas.drawPath(this.mPath, this.mPaint);
        canvas.restore();
    }

    public int getAlpha() {
        return this.mContainerState.getAlpha();
    }

    public final Rect getBoundsInner() {
        return this.mContainerState.getBounds();
    }

    public Drawable.ConstantState getConstantState() {
        return this.mContainerState;
    }

    public float[] getCornerRadii() {
        float[] fArr = this.mContainerState.mRadii;
        if (fArr == null) {
            return null;
        }
        return (float[]) fArr.clone();
    }

    public float getCornerRadius() {
        return this.mContainerState.mRadius;
    }

    public Rect getDirtyBounds() {
        return this.mContainerState.getDirtyBounds();
    }

    public int getIntrinsicHeight() {
        return this.mContainerState.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        return this.mContainerState.getIntrinsicWidth();
    }

    public int getOpacity() {
        return this.mContainerState.getOpacity();
    }

    public boolean getPadding(Rect rect) {
        return this.mContainerState.getPadding(rect);
    }

    public float getSmooth() {
        return this.mContainerState.mSmooth;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws IOException, XmlPullParserException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.MiuiSmoothContainerDrawable);
        setCornerRadius((float) obtainAttributes.getDimensionPixelSize(R.styleable.MiuiSmoothContainerDrawable_android_radius, 0));
        if (obtainAttributes.hasValue(R.styleable.MiuiSmoothContainerDrawable_android_topLeftRadius) || obtainAttributes.hasValue(R.styleable.MiuiSmoothContainerDrawable_android_topRightRadius) || obtainAttributes.hasValue(R.styleable.MiuiSmoothContainerDrawable_android_bottomRightRadius) || obtainAttributes.hasValue(R.styleable.MiuiSmoothContainerDrawable_android_bottomLeftRadius)) {
            float dimensionPixelSize = (float) obtainAttributes.getDimensionPixelSize(R.styleable.MiuiSmoothContainerDrawable_android_topLeftRadius, 0);
            float dimensionPixelSize2 = (float) obtainAttributes.getDimensionPixelSize(R.styleable.MiuiSmoothContainerDrawable_android_topRightRadius, 0);
            float dimensionPixelSize3 = (float) obtainAttributes.getDimensionPixelSize(R.styleable.MiuiSmoothContainerDrawable_android_bottomRightRadius, 0);
            float dimensionPixelSize4 = (float) obtainAttributes.getDimensionPixelSize(R.styleable.MiuiSmoothContainerDrawable_android_bottomLeftRadius, 0);
            setCornerRadii(new float[]{dimensionPixelSize, dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize3, dimensionPixelSize3, dimensionPixelSize4, dimensionPixelSize4});
        }
        setSmooth(obtainAttributes.getFloat(R.styleable.MiuiSmoothContainerDrawable_miui_smooth, 0.7f));
        obtainAttributes.recycle();
        inflateInnerDrawable(resources, xmlPullParser, attributeSet, theme);
    }

    public void invalidateDrawable(Drawable drawable) {
        this.mPath = null;
        this.mBitmapShader = null;
        this.mPaint.setShader(null);
        invalidateSelf();
    }

    public boolean isStateful() {
        return this.mContainerState.isStateful();
    }

    public void jumpToCurrentState() {
        this.mContainerState.jumpToCurrentState();
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        this.mPath = null;
        this.mBitmapShader = null;
        this.mPaint.setShader(null);
        this.mContainerState.onBoundsChange(rect);
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        this.mPath = null;
        this.mBitmapShader = null;
        this.mPaint.setShader(null);
        return this.mContainerState.onStateChange(iArr);
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    public void setAlpha(int i) {
        this.mContainerState.setAlpha(i);
    }

    public void setChangingConfigurations(int i) {
        this.mContainerState.setChangingConfigurations(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mContainerState.setColorFilter(colorFilter);
    }

    public void setCornerRadii(float[] fArr) {
        ContainerState containerState = this.mContainerState;
        containerState.mRadii = fArr;
        if (fArr == null) {
            containerState.mRadius = 0.0f;
        }
        invalidateSelf();
    }

    public void setCornerRadius(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        ContainerState containerState = this.mContainerState;
        containerState.mRadius = f;
        containerState.mRadii = null;
        invalidateSelf();
    }

    public void setDither(boolean z) {
        this.mContainerState.setDither(z);
    }

    public void setFilterBitmap(boolean z) {
        this.mContainerState.setFilterBitmap(z);
    }

    public void setSmooth(float f) {
        ContainerState containerState = this.mContainerState;
        if (containerState.mSmooth != f) {
            containerState.mSmooth = f;
            invalidateSelf();
        }
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }
}
