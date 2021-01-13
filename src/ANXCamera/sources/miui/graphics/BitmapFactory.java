package miui.graphics;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import com.miui.internal.R;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;
import miui.io.ResettableInputStream;
import miui.util.AppConstants;
import miui.util.AttributeResolver;
import miui.util.Utf8TextUtils;
import miui.util.ViewUtils;

public class BitmapFactory extends android.graphics.BitmapFactory {
    private static final String[] APPELLATION_SUFFIX = {"老师", "先生", "老板", "仔", "手机", "叔", "阿姨", "宅", "伯", "伯母", "伯父", "哥", "姐", "弟", "妹", "舅", "姑", "父", "主任", "经理", "工作", "同事", "律师", "司机", "师傅", "师父", "爷", "奶", "中介", "董", "总", "太太", "保姆", "某", "秘书", "处长", "局长", "班长", "兄", "助理"};
    private static final Pattern ASIALANGPATTERN = Pattern.compile("[㄀-ㄭㆠ-ㆺ一-鿌㐀-䶵豈-龎⼀-⿕⺀-⻳㇀-㇣ᄀ-ᇿꥠ-ꥼힰ-ퟻㄱ-ㆎ가-힣぀-ゟ゠-ヿㇰ-ㇿ㆐-㆟ꀀ-ꒌ꒐-꓆]");
    public static final int BITMAP_COLOR_MODE_DARK = 0;
    public static final int BITMAP_COLOR_MODE_LIGHT = 2;
    public static final int BITMAP_COLOR_MODE_MEDIUM = 1;
    public static final int MODE_DARK = 1;
    public static final int MODE_DAYNIGHT = 2;
    public static final int MODE_LIGHT = 0;
    private static byte[] PNG_HEAD_FORMAT = {-119, 80, 78, 71, 13, 10, 26, 10};
    private static final ThreadLocal<Canvas> sCanvasCache = new ThreadLocal<>();
    static Object sLockForRsContext = new Object();
    static RenderScript sRsContext;
    private static final Paint sSrcInPaint = new Paint(1);

    public static class CropOption {
        public int borderColor;
        public int borderWidth;
        public int rx;
        public int ry;
        public Rect srcBmpDrawingArea;

        public CropOption() {
        }

        public CropOption(int i, int i2, int i3, int i4) {
            this.rx = i;
            this.ry = i2;
            this.borderWidth = i3;
            this.borderColor = i4;
        }

        public CropOption(CropOption cropOption) {
            this.rx = cropOption.rx;
            this.ry = cropOption.ry;
            this.borderWidth = cropOption.borderWidth;
            this.borderColor = cropOption.borderColor;
            this.srcBmpDrawingArea = cropOption.srcBmpDrawingArea;
        }
    }

    static {
        sSrcInPaint.setFilterBitmap(true);
        sSrcInPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    protected BitmapFactory() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    private static int between(int i, int i2, int i3) {
        return Math.min(i2, Math.max(i, i3));
    }

    public static Bitmap composeBitmap(Bitmap bitmap, Bitmap bitmap2, Drawable drawable, Drawable drawable2, Drawable drawable3) {
        return composeBitmap(bitmap, bitmap2, drawable, drawable2, drawable3, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), bitmap2 != null ? new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight()) : new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
    }

    public static Bitmap composeBitmap(Bitmap bitmap, Bitmap bitmap2, Drawable drawable, Drawable drawable2, Drawable drawable3, int i) {
        return composeBitmap(bitmap, bitmap2, drawable, drawable2, drawable3, null, new Rect(0, 0, i, i));
    }

    public static Bitmap composeBitmap(Bitmap bitmap, Bitmap bitmap2, Drawable drawable, Drawable drawable2, Drawable drawable3, Rect rect, Rect rect2) {
        if (bitmap2 == null && rect2 == null) {
            return null;
        }
        if (bitmap2 == null) {
            if (rect2.height() <= 0 || rect2.width() <= 0) {
                return null;
            }
            bitmap2 = Bitmap.createBitmap(rect2.width(), rect2.height(), Bitmap.Config.ARGB_8888);
        } else if (rect2 == null) {
            rect2 = new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        }
        Bitmap maskOutBitmap = maskOutBitmap(bitmap, drawable, null, rect, rect2);
        Canvas cachedCanvas = getCachedCanvas();
        cachedCanvas.setBitmap(bitmap2);
        if (drawable3 != null) {
            drawable3.setBounds(rect2);
            drawable3.draw(cachedCanvas);
        }
        cachedCanvas.drawBitmap(maskOutBitmap, rect2, rect2, (Paint) null);
        maskOutBitmap.recycle();
        if (drawable2 != null) {
            drawable2.setBounds(rect2);
            drawable2.draw(cachedCanvas);
        }
        return bitmap2;
    }

    private static int computeSampleSize(ResettableInputStream resettableInputStream, int i) {
        if (i <= 0) {
            return 1;
        }
        BitmapFactory.Options bitmapSize = getBitmapSize(resettableInputStream);
        return (int) Math.sqrt((((double) bitmapSize.outWidth) * ((double) bitmapSize.outHeight)) / ((double) i));
    }

    private static boolean containsEastAsianCharacter(String str) {
        return ASIALANGPATTERN.matcher(str).find();
    }

    private static Bitmap copyToEmpty(Bitmap bitmap) {
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        if (bitmap.getConfig() != null) {
            config = bitmap.getConfig();
        }
        return Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
    }

    @Deprecated
    public static Bitmap createNameBitmap(Context context, String str, int i) {
        return createNameBitmap(context, str, i, 0, 0);
    }

    public static Bitmap createNameBitmap(Context context, String str, int i, int i2) {
        return createNameBitmap(context, str, i, 0, 0, i2);
    }

    @Deprecated
    public static Bitmap createNameBitmap(Context context, String str, int i, int i2, int i3) {
        return createNameBitmap(context, str, i, i2, i3, 0);
    }

    public static Bitmap createNameBitmap(Context context, String str, int i, int i2, int i3, int i4) {
        Drawable drawable;
        int i5;
        int i6;
        if (str == null) {
            return null;
        }
        String wordFromName = getWordFromName(str.trim());
        if (TextUtils.isEmpty(wordFromName)) {
            return null;
        }
        if (i2 != 0) {
            drawable = context.getResources().getDrawable(i2);
        } else if (i4 == 0) {
            drawable = context.getResources().getDrawable(R.drawable.word_photo_bg_light);
        } else if (1 == i4) {
            drawable = context.getResources().getDrawable(R.drawable.word_photo_bg_dark);
        } else if (2 == i4) {
            drawable = AttributeResolver.resolveDrawable(context, R.attr.wordPhotoBackground);
            if (drawable == null) {
                drawable = ViewUtils.isNightMode(context) ? context.getResources().getDrawable(R.drawable.word_photo_bg_dark) : context.getResources().getDrawable(R.drawable.word_photo_bg_light);
            }
        } else {
            throw new IllegalArgumentException("unknown mode when get drawable: " + i4);
        }
        drawable.setBounds(new Rect(0, 0, i, i));
        if (i3 != 0) {
            i5 = context.getResources().getColor(i3);
        } else if (i4 == 0) {
            i5 = context.getResources().getColor(miui.R.color.word_photo_color);
        } else if (1 == i4) {
            i5 = context.getResources().getColor(R.color.word_photo_color_dark);
        } else if (2 == i4) {
            try {
                i6 = AttributeResolver.resolveColor(context, R.attr.wordPhotoTextColor);
            } catch (Exception unused) {
                i6 = -1;
            }
            i5 = i6 == -1 ? ViewUtils.isNightMode(context) ? context.getResources().getColor(R.color.word_photo_color_dark) : context.getResources().getColor(miui.R.color.word_photo_color) : i6;
        } else {
            throw new IllegalArgumentException("unknown mode when get photo color: " + i4);
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.draw(canvas);
        Paint paint = new Paint(1);
        paint.setFilterBitmap(true);
        paint.setColor(i5);
        paint.setTextSize(((float) i) * 0.6f);
        Rect rect = new Rect();
        paint.getTextBounds(wordFromName, 0, wordFromName.length(), rect);
        canvas.drawText(wordFromName, (float) ((int) (((double) (i - (rect.right + rect.left))) * 0.5d)), (float) ((int) (((double) (i - (rect.top + rect.bottom))) * 0.5d)), paint);
        return createBitmap;
    }

    public static Bitmap createPhoto(Context context, Bitmap bitmap) {
        return createPhoto(context, bitmap, context.getResources().getDimensionPixelSize(R.dimen.contact_photo_width));
    }

    public static Bitmap createPhoto(Context context, Bitmap bitmap, int i) {
        Resources resources = context.getResources();
        return composeBitmap(bitmap, null, resources.getDrawable(R.drawable.ic_contact_photo_mask), resources.getDrawable(R.drawable.ic_contact_photo_fg), resources.getDrawable(R.drawable.ic_contact_photo_bg), i);
    }

    public static Bitmap cropBitmap(Bitmap bitmap, Bitmap bitmap2, CropOption cropOption) {
        if (bitmap == null || bitmap2 == null) {
            return null;
        }
        CropOption cropOption2 = cropOption == null ? new CropOption() : cropOption;
        Rect rect = cropOption2.srcBmpDrawingArea;
        if (rect == null) {
            rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
        int between = between(0, bitmap.getWidth() - 1, rect.left);
        int between2 = between(between, bitmap.getWidth(), rect.right);
        int between3 = between(0, bitmap.getHeight() - 1, rect.top);
        int between4 = between(between3, bitmap.getHeight(), rect.bottom);
        int i = between2 - between;
        int i2 = between4 - between3;
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight();
        cropOption2.borderWidth = between(0, Math.min(width, height) / 2, cropOption2.borderWidth);
        cropOption2.rx = between(0, width / 2, cropOption2.rx);
        cropOption2.ry = between(0, height / 2, cropOption2.ry);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setDither(true);
        Canvas canvas = new Canvas(bitmap2);
        canvas.drawARGB(0, 0, 0, 0);
        int i3 = cropOption2.rx;
        int i4 = cropOption2.borderWidth;
        if (i3 - i4 > 0 && cropOption2.ry - i4 > 0) {
            RectF rectF = new RectF((float) i4, (float) i4, (float) (width - i4), (float) (height - i4));
            int i5 = cropOption2.rx;
            int i6 = cropOption2.borderWidth;
            canvas.drawRoundRect(rectF, (float) (i5 - i6), (float) (cropOption2.ry - i6), paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        }
        int i7 = cropOption2.borderWidth;
        float f = (float) i;
        float f2 = (float) (width - (i7 * 2));
        float f3 = (float) i2;
        float f4 = (float) (height - (i7 * 2));
        float min = Math.min((f * 1.0f) / f2, (1.0f * f3) / f4);
        int i8 = (int) ((f - (f2 * min)) / 2.0f);
        int i9 = (int) ((f3 - (f4 * min)) / 2.0f);
        Rect rect2 = new Rect(between + i8, between3 + i9, between2 - i8, between4 - i9);
        int i10 = cropOption2.borderWidth;
        canvas.drawBitmap(bitmap, rect2, new Rect(i10, i10, width - i10, height - i10), paint);
        if (cropOption2.borderWidth > 0) {
            int i11 = cropOption2.borderColor;
            if ((i11 >>> 24) != 0) {
                paint.setColor(i11);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
                canvas.drawRoundRect(new RectF(0.0f, 0.0f, (float) width, (float) height), (float) cropOption2.rx, (float) cropOption2.ry, paint);
            }
        }
        return bitmap2;
    }

    public static Bitmap cropBitmap(Bitmap bitmap, CropOption cropOption) {
        if (bitmap != null) {
            return cropBitmap(bitmap, copyToEmpty(bitmap), cropOption);
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0014  */
    public static Bitmap decodeBitmap(Context context, Uri uri, int i, int i2, boolean z) throws IOException {
        Throwable th;
        ResettableInputStream resettableInputStream = null;
        try {
            ResettableInputStream resettableInputStream2 = new ResettableInputStream(context, uri);
            try {
                Bitmap decodeBitmap = decodeBitmap(resettableInputStream2, i, i2, z);
                resettableInputStream2.close();
                return decodeBitmap;
            } catch (Throwable th2) {
                th = th2;
                resettableInputStream = resettableInputStream2;
                if (resettableInputStream != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (resettableInputStream != null) {
                resettableInputStream.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0014  */
    public static Bitmap decodeBitmap(Context context, Uri uri, int i, boolean z) throws IOException {
        Throwable th;
        ResettableInputStream resettableInputStream = null;
        try {
            ResettableInputStream resettableInputStream2 = new ResettableInputStream(context, uri);
            try {
                Bitmap decodeBitmap = decodeBitmap(resettableInputStream2, i, z);
                resettableInputStream2.close();
                return decodeBitmap;
            } catch (Throwable th2) {
                th = th2;
                resettableInputStream = resettableInputStream2;
                if (resettableInputStream != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (resettableInputStream != null) {
                resettableInputStream.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0015  */
    public static Bitmap decodeBitmap(Context context, Uri uri, boolean z) throws IOException {
        Throwable th;
        ResettableInputStream resettableInputStream = null;
        try {
            ResettableInputStream resettableInputStream2 = new ResettableInputStream(context, uri);
            try {
                Bitmap decodeBitmap = decodeBitmap(resettableInputStream2, -1, z);
                resettableInputStream2.close();
                return decodeBitmap;
            } catch (Throwable th2) {
                th = th2;
                resettableInputStream = resettableInputStream2;
                if (resettableInputStream != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (resettableInputStream != null) {
                resettableInputStream.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0014  */
    public static Bitmap decodeBitmap(String str, int i, int i2, boolean z) throws IOException {
        Throwable th;
        ResettableInputStream resettableInputStream = null;
        try {
            ResettableInputStream resettableInputStream2 = new ResettableInputStream(str);
            try {
                Bitmap decodeBitmap = decodeBitmap(resettableInputStream2, i, i2, z);
                resettableInputStream2.close();
                return decodeBitmap;
            } catch (Throwable th2) {
                th = th2;
                resettableInputStream = resettableInputStream2;
                if (resettableInputStream != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (resettableInputStream != null) {
                resettableInputStream.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0014  */
    public static Bitmap decodeBitmap(String str, int i, boolean z) throws IOException {
        Throwable th;
        ResettableInputStream resettableInputStream = null;
        try {
            ResettableInputStream resettableInputStream2 = new ResettableInputStream(str);
            try {
                Bitmap decodeBitmap = decodeBitmap(resettableInputStream2, i, z);
                resettableInputStream2.close();
                return decodeBitmap;
            } catch (Throwable th2) {
                th = th2;
                resettableInputStream = resettableInputStream2;
                if (resettableInputStream != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (resettableInputStream != null) {
                resettableInputStream.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0015  */
    public static Bitmap decodeBitmap(String str, boolean z) throws IOException {
        Throwable th;
        ResettableInputStream resettableInputStream = null;
        try {
            ResettableInputStream resettableInputStream2 = new ResettableInputStream(str);
            try {
                Bitmap decodeBitmap = decodeBitmap(resettableInputStream2, -1, z);
                resettableInputStream2.close();
                return decodeBitmap;
            } catch (Throwable th2) {
                th = th2;
                resettableInputStream = resettableInputStream2;
                if (resettableInputStream != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (resettableInputStream != null) {
                resettableInputStream.close();
            }
            throw th;
        }
    }

    public static Bitmap decodeBitmap(ResettableInputStream resettableInputStream, int i, int i2, boolean z) throws IOException {
        int i3 = i * i2;
        if (i <= 0 || i2 <= 0) {
            i3 = -1;
        }
        Bitmap decodeBitmap = decodeBitmap(resettableInputStream, i3, z);
        if (decodeBitmap == null) {
            return null;
        }
        if (i3 <= 0) {
            return decodeBitmap;
        }
        Bitmap scaleBitmap = scaleBitmap(decodeBitmap, i, i2);
        if (decodeBitmap != scaleBitmap) {
            decodeBitmap.recycle();
        }
        return scaleBitmap;
    }

    public static Bitmap decodeBitmap(ResettableInputStream resettableInputStream, int i, boolean z) throws IOException {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        int i2 = 0;
        options.inScaled = false;
        options.inSampleSize = computeSampleSize(resettableInputStream, i);
        while (true) {
            int i3 = i2 + 1;
            bitmap = null;
            if (i2 >= 3) {
                break;
            }
            try {
                resettableInputStream.reset();
                bitmap = android.graphics.BitmapFactory.decodeStream(resettableInputStream, null, options);
                break;
            } catch (OutOfMemoryError e2) {
                if (z) {
                    options.inSampleSize *= 2;
                    i2 = i3;
                } else {
                    throw e2;
                }
            }
        }
        return bitmap;
    }

    public static Bitmap fastBlur(Bitmap bitmap, int i) {
        Bitmap copyToEmpty = copyToEmpty(bitmap);
        fastBlur(bitmap, copyToEmpty, i);
        return copyToEmpty;
    }

    public static Bitmap fastBlur(Bitmap bitmap, Bitmap bitmap2, int i) {
        if (bitmap == null) {
            return null;
        }
        if (!(bitmap2 != null && bitmap.getWidth() == bitmap2.getWidth() && bitmap.getHeight() == bitmap2.getHeight())) {
            bitmap2 = copyToEmpty(bitmap);
        }
        fastblur_v17(bitmap, bitmap2, i);
        return bitmap2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: java.lang.Object[] */
    /* JADX WARN: Multi-variable type inference failed */
    private static Bitmap fastblur_v17(Bitmap bitmap, Bitmap bitmap2, int i) {
        int i2 = 1;
        while (i > 25) {
            i2 *= 2;
            i /= 2;
        }
        Bitmap scaleBitmap = i2 == 1 ? bitmap : scaleBitmap(bitmap, Math.max(bitmap.getWidth() / i2, 1), Math.max(bitmap.getHeight() / i2, 1));
        ContextWrapper currentApplication = AppConstants.getCurrentApplication();
        if (currentApplication.getApplicationContext() == null) {
            currentApplication = new ContextWrapper(currentApplication) {
                /* class miui.graphics.BitmapFactory.AnonymousClass1 */

                public Context getApplicationContext() {
                    return this;
                }
            };
        }
        try {
            Object[] enumConstants = currentApplication.getClassLoader().loadClass("android.graphics.Bitmap$Config").getEnumConstants();
            int length = enumConstants.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                Enum r7 = (Enum) enumConstants[i3];
                if ("RGBA_F16".equals(r7.name()) && scaleBitmap.getConfig() == r7) {
                    scaleBitmap = transferF16ToARGB(scaleBitmap);
                    break;
                }
                i3++;
            }
        } catch (Exception unused) {
        }
        synchronized (sLockForRsContext) {
            if (sRsContext == null) {
                sRsContext = RenderScript.create(currentApplication);
            }
            Bitmap bitmap3 = i2 == 1 ? bitmap2 : scaleBitmap;
            if (scaleBitmap.getRowBytes() != bitmap3.getRowBytes()) {
                scaleBitmap = scaleBitmap.copy(Bitmap.Config.ARGB_8888, true);
            }
            Allocation createFromBitmap = Allocation.createFromBitmap(sRsContext, scaleBitmap);
            Allocation createTyped = Allocation.createTyped(sRsContext, createFromBitmap.getType());
            ScriptIntrinsicBlur create = ScriptIntrinsicBlur.create(sRsContext, Element.U8_4(sRsContext));
            create.setRadius((float) i);
            create.setInput(createFromBitmap);
            create.forEach(createTyped);
            createTyped.copyTo(bitmap3);
            if (bitmap3 != bitmap2) {
                scaleBitmap(bitmap3, bitmap2);
            }
            if (scaleBitmap != bitmap) {
                scaleBitmap.recycle();
            }
            if (bitmap3 != bitmap2) {
                bitmap3.recycle();
            }
            createFromBitmap.destroy();
            createTyped.destroy();
            create.destroy();
        }
        return bitmap2;
    }

    public static int getBitmapColorMode(Bitmap bitmap, int i) {
        int height = bitmap.getHeight() / i;
        int width = bitmap.getWidth() / i;
        int i2 = (width * height) / 5;
        Bitmap scaleBitmap = scaleBitmap(bitmap, width, height);
        int i3 = 2;
        int i4 = 0;
        for (int i5 = 0; i5 < width; i5++) {
            int i6 = i3;
            int i7 = 0;
            while (true) {
                if (i7 >= height) {
                    i3 = i6;
                    break;
                }
                int pixel = scaleBitmap.getPixel(i5, i7);
                if (((int) ((((double) ((float) ((16711680 & pixel) >> 16))) * 0.3d) + (((double) ((float) ((65280 & pixel) >> 8))) * 0.59d) + (((double) ((float) (pixel & 255))) * 0.11d))) < 180) {
                    i4++;
                    if (i4 > i2) {
                        i6 = 1;
                    }
                    if (i4 > i2 * 2) {
                        i3 = 0;
                        break;
                    }
                }
                i7++;
            }
        }
        if (scaleBitmap != bitmap) {
            scaleBitmap.recycle();
        }
        return i3;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0014  */
    public static BitmapFactory.Options getBitmapSize(Context context, Uri uri) throws IOException {
        Throwable th;
        ResettableInputStream resettableInputStream = null;
        try {
            ResettableInputStream resettableInputStream2 = new ResettableInputStream(context, uri);
            try {
                BitmapFactory.Options bitmapSize = getBitmapSize(resettableInputStream2);
                resettableInputStream2.close();
                return bitmapSize;
            } catch (Throwable th2) {
                th = th2;
                resettableInputStream = resettableInputStream2;
                if (resettableInputStream != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (resettableInputStream != null) {
                resettableInputStream.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0014  */
    public static BitmapFactory.Options getBitmapSize(String str) throws IOException {
        Throwable th;
        ResettableInputStream resettableInputStream = null;
        try {
            ResettableInputStream resettableInputStream2 = new ResettableInputStream(str);
            try {
                BitmapFactory.Options bitmapSize = getBitmapSize(resettableInputStream2);
                resettableInputStream2.close();
                return bitmapSize;
            } catch (Throwable th2) {
                th = th2;
                resettableInputStream = resettableInputStream2;
                if (resettableInputStream != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (resettableInputStream != null) {
                resettableInputStream.close();
            }
            throw th;
        }
    }

    public static BitmapFactory.Options getBitmapSize(ResettableInputStream resettableInputStream) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        android.graphics.BitmapFactory.decodeStream(resettableInputStream, null, options);
        return options;
    }

    private static Canvas getCachedCanvas() {
        Canvas canvas = sCanvasCache.get();
        if (canvas != null) {
            return canvas;
        }
        Canvas canvas2 = new Canvas();
        sCanvasCache.set(canvas2);
        return canvas2;
    }

    private static String getWordFromName(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (!containsEastAsianCharacter(str)) {
            return Utf8TextUtils.subString(str, 0, 1).toUpperCase();
        }
        String removeSuffix = removeSuffix(str);
        if (TextUtils.isEmpty(removeSuffix)) {
            return null;
        }
        int length = removeSuffix.length();
        return removeSuffix.substring(length - 1, length).trim();
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0014  */
    public static boolean isPngFormat(Context context, Uri uri) throws IOException {
        Throwable th;
        ResettableInputStream resettableInputStream = null;
        try {
            ResettableInputStream resettableInputStream2 = new ResettableInputStream(context, uri);
            try {
                boolean isPngFormat = isPngFormat(resettableInputStream2);
                resettableInputStream2.close();
                return isPngFormat;
            } catch (Throwable th2) {
                th = th2;
                resettableInputStream = resettableInputStream2;
                if (resettableInputStream != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (resettableInputStream != null) {
                resettableInputStream.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0014  */
    public static boolean isPngFormat(String str) throws IOException {
        Throwable th;
        ResettableInputStream resettableInputStream = null;
        try {
            ResettableInputStream resettableInputStream2 = new ResettableInputStream(str);
            try {
                boolean isPngFormat = isPngFormat(resettableInputStream2);
                resettableInputStream2.close();
                return isPngFormat;
            } catch (Throwable th2) {
                th = th2;
                resettableInputStream = resettableInputStream2;
                if (resettableInputStream != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (resettableInputStream != null) {
                resettableInputStream.close();
            }
            throw th;
        }
    }

    public static boolean isPngFormat(ResettableInputStream resettableInputStream) throws IOException {
        byte[] bArr = new byte[PNG_HEAD_FORMAT.length];
        if (resettableInputStream.read(bArr) >= bArr.length) {
            return isPngFormat(bArr);
        }
        return false;
    }

    private static boolean isPngFormat(byte[] bArr) {
        if (bArr == null || bArr.length < PNG_HEAD_FORMAT.length) {
            return false;
        }
        int i = 0;
        while (true) {
            byte[] bArr2 = PNG_HEAD_FORMAT;
            if (i >= bArr2.length) {
                return true;
            }
            if (bArr[i] != bArr2[i]) {
                return false;
            }
            i++;
        }
    }

    public static Bitmap maskOutBitmap(Bitmap bitmap, Drawable drawable, Bitmap bitmap2, Rect rect, Rect rect2) {
        int i;
        int i2;
        if (bitmap2 == null && rect2 == null) {
            return null;
        }
        int i3 = 0;
        if (bitmap2 == null) {
            if (rect2.height() <= 0 || rect2.width() <= 0) {
                return null;
            }
            bitmap2 = Bitmap.createBitmap(rect2.width(), rect2.height(), Bitmap.Config.ARGB_8888);
        } else if (rect2 == null) {
            rect2 = new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        }
        Canvas cachedCanvas = getCachedCanvas();
        cachedCanvas.setBitmap(bitmap2);
        cachedCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        if (drawable != null) {
            drawable.setBounds(rect2);
            drawable.draw(cachedCanvas);
        }
        if (rect == null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int width2 = rect2.width();
            float height2 = (float) rect2.height();
            float f = (float) width2;
            float f2 = height2 / f;
            float f3 = (float) width;
            float f4 = f3 / f;
            float f5 = (float) height;
            float f6 = f5 / height2;
            if (f4 > f6) {
                int i4 = (int) (f5 / f2);
                i3 = (width - i4) / 2;
                width = i4;
                i2 = height;
                i = 0;
            } else if (f4 < f6) {
                i2 = (int) (f2 * f3);
                i = (height - i2) / 2;
            } else {
                i2 = height;
                i = 0;
            }
            rect = new Rect(i3, i, width + i3, i2 + i);
        }
        cachedCanvas.drawBitmap(bitmap, rect, rect2, sSrcInPaint);
        return bitmap2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0052 A[LOOP:1: B:5:0x000d->B:16:0x0052, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0055 A[EDGE_INSN: B:29:0x0055->B:17:0x0055 ?: BREAK  , SYNTHETIC] */
    private static String removeSuffix(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String str2 = str;
        do {
            String str3 = str2;
            int i = 0;
            boolean z = false;
            while (true) {
                String[] strArr = APPELLATION_SUFFIX;
                if (i >= strArr.length) {
                    break;
                }
                if (str3.endsWith(strArr[i])) {
                    str3 = str3.substring(0, str3.length() - APPELLATION_SUFFIX[i].length());
                } else {
                    if (!containsEastAsianCharacter(String.valueOf(str3.charAt(str3.length() - 1)))) {
                        str3 = str3.substring(0, str3.length() - 1);
                    }
                    if (!TextUtils.isEmpty(str3)) {
                        break;
                    }
                    i++;
                }
                z = true;
                if (!TextUtils.isEmpty(str3)) {
                }
            }
            str2 = str3;
            if (!z) {
                break;
            }
        } while (!TextUtils.isEmpty(str2));
        if (str2 != null) {
            str2 = str2.trim();
        }
        return TextUtils.isEmpty(str2) ? str.substring(str.length() - 1) : str2;
    }

    public static boolean saveToFile(Bitmap bitmap, String str) throws IOException {
        return saveToFile(bitmap, str, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x001f  */
    public static boolean saveToFile(Bitmap bitmap, String str, boolean z) throws IOException {
        Throwable th;
        FileOutputStream fileOutputStream;
        Bitmap.CompressFormat compressFormat;
        if (bitmap == null) {
            return false;
        }
        try {
            fileOutputStream = new FileOutputStream(str);
            if (z) {
                try {
                    compressFormat = Bitmap.CompressFormat.PNG;
                } catch (Throwable th2) {
                    th = th2;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }
            } else {
                compressFormat = Bitmap.CompressFormat.JPEG;
            }
            bitmap.compress(compressFormat, 100, fileOutputStream);
            fileOutputStream.close();
            return true;
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            if (fileOutputStream != null) {
            }
            throw th;
        }
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        if (bitmap.getWidth() == i && bitmap.getHeight() == i2) {
            return bitmap;
        }
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        if (bitmap.getConfig() != null) {
            config = bitmap.getConfig();
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, config);
        scaleBitmap(bitmap, createBitmap);
        return createBitmap;
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, Bitmap bitmap2) {
        if (bitmap == null || bitmap2 == null) {
            return null;
        }
        if (bitmap.getWidth() == bitmap2.getWidth() && bitmap.getHeight() == bitmap2.getHeight()) {
            return bitmap;
        }
        Canvas canvas = new Canvas(bitmap2);
        canvas.drawARGB(0, 0, 0, 0);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setDither(true);
        canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight()), paint);
        return bitmap2;
    }

    private static Bitmap transferF16ToARGB(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == 0 || height == 0) {
            return bitmap;
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setFlags(3);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return createBitmap;
    }
}
