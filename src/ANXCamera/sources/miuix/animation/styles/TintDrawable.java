package miuix.animation.styles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import miuix.animation.Folme;
import miuix.animation.utils.CommonUtils;

public class TintDrawable extends Drawable {
    private static final View.OnAttachStateChangeListener sListener = new View.OnAttachStateChangeListener() {
        /* class miuix.animation.styles.TintDrawable.AnonymousClass1 */

        public void onViewAttachedToWindow(View view) {
        }

        public void onViewDetachedFromWindow(View view) {
            TintDrawable tintDrawable = TintDrawable.get(view);
            if (tintDrawable != null && Build.VERSION.SDK_INT >= 23) {
                Drawable drawable = tintDrawable.mOriDrawable;
                if (drawable != null) {
                    view.setForeground(drawable);
                }
                tintDrawable.clear();
                view.removeOnAttachStateChangeListener(this);
            }
        }
    };
    private PropertyStyle mAnim;
    private Bitmap mBitmap;
    private RectF mBounds = new RectF();
    private Drawable mOriDrawable;
    private Paint mPaint = new Paint();
    private Rect mSrcRect = new Rect();
    private View mView;

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void clear() {
        this.mAnim = null;
        recycleBitmap();
    }

    private void convertBmpToMask() {
        int width = this.mBitmap.getWidth();
        int height = this.mBitmap.getHeight();
        int[] iArr = new int[(width * height)];
        this.mBitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i = 0; i < iArr.length; i++) {
            if (Color.alpha(iArr[i]) > 1) {
                iArr[i] = -16777216;
            }
        }
        this.mBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
    }

    private void createBitmap(int i, int i2) {
        if (Build.VERSION.SDK_INT >= 23) {
            Bitmap bitmap = this.mBitmap;
            if (bitmap == null || bitmap.getWidth() != i || this.mBitmap.getHeight() != this.mView.getHeight()) {
                recycleBitmap();
                this.mPaint.setAntiAlias(true);
                try {
                    this.mBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                } catch (OutOfMemoryError unused) {
                    Log.w(CommonUtils.TAG, "TintDrawable.createBitmap failed, out of memory");
                }
            }
        }
    }

    public static TintDrawable get(View view) {
        if (Build.VERSION.SDK_INT < 23) {
            return null;
        }
        Drawable foreground = view.getForeground();
        if (foreground instanceof TintDrawable) {
            return (TintDrawable) foreground;
        }
        return null;
    }

    private void initBitmap(int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            Bitmap bitmap = this.mBitmap;
            if (bitmap == null || bitmap.isRecycled()) {
                this.mView.setForeground(this.mOriDrawable);
                return;
            }
            try {
                this.mBitmap.eraseColor(0);
                Canvas canvas = new Canvas(this.mBitmap);
                canvas.translate((float) (-this.mView.getScrollX()), (float) (-this.mView.getScrollY()));
                this.mView.setForeground(this.mOriDrawable);
                this.mView.draw(canvas);
                this.mView.setForeground(this);
                if (i == 0) {
                    convertBmpToMask();
                }
            } catch (Exception e2) {
                Log.w(CommonUtils.TAG, "TintDrawable.initBitmap failed, " + e2);
            }
        }
    }

    private void recycleBitmap() {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mBitmap = null;
        }
    }

    static TintDrawable setAndGet(final View view) {
        TintDrawable tintDrawable = get(view);
        if (tintDrawable != null || Build.VERSION.SDK_INT < 23) {
            return tintDrawable;
        }
        final TintDrawable tintDrawable2 = new TintDrawable();
        tintDrawable2.mView = view;
        tintDrawable2.setOriDrawable(view.getForeground());
        view.addOnAttachStateChangeListener(sListener);
        Folme.post(view, new Runnable() {
            /* class miuix.animation.styles.TintDrawable.AnonymousClass2 */

            public void run() {
                view.setForeground(tintDrawable2);
            }
        });
        return tintDrawable2;
    }

    private void setOriDrawable(Drawable drawable) {
        this.mOriDrawable = drawable;
    }

    public void draw(@NonNull Canvas canvas) {
        int scrollX = this.mView.getScrollX();
        int scrollY = this.mView.getScrollY();
        int width = this.mView.getWidth();
        int height = this.mView.getHeight();
        this.mBounds.set((float) scrollX, (float) scrollY, (float) (scrollX + width), (float) (scrollY + height));
        this.mSrcRect.set(0, 0, width, height);
        canvas.save();
        try {
            canvas.clipRect(this.mBounds);
            canvas.drawColor(0);
            if (this.mOriDrawable != null) {
                this.mOriDrawable.draw(canvas);
            }
            if (!(this.mAnim == null || this.mBitmap == null)) {
                if (!this.mBitmap.isRecycled()) {
                    this.mPaint.setColorFilter(new PorterDuffColorFilter(this.mAnim.getCurrentIntValue(), PorterDuff.Mode.SRC_IN));
                    canvas.drawBitmap(this.mBitmap, this.mSrcRect, this.mBounds, this.mPaint);
                    canvas.restore();
                    return;
                }
            }
            if (Build.VERSION.SDK_INT >= 23) {
                this.mView.setForeground(this.mOriDrawable);
            }
        } finally {
            canvas.restore();
        }
    }

    public int getOpacity() {
        return -2;
    }

    /* access modifiers changed from: package-private */
    public void initTintBuffer(PropertyStyle propertyStyle, int i) {
        View view = this.mView;
        if (view != null) {
            int width = view.getWidth();
            int height = this.mView.getHeight();
            if (width == 0 || height == 0) {
                recycleBitmap();
                return;
            }
            this.mAnim = propertyStyle;
            createBitmap(width, height);
            initBitmap(i);
        }
    }

    /* access modifiers changed from: package-private */
    public void restoreOriginalDrawable() {
        clear();
        invalidateSelf();
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }
}
