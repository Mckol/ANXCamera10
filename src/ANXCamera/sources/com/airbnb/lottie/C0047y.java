package com.airbnb.lottie;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.annotation.WorkerThread;
import com.airbnb.lottie.c.u;
import com.airbnb.lottie.d.h;
import com.airbnb.lottie.model.f;
import com.airbnb.lottie.network.b;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.module.impl.component.FileUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import okio.Okio;
import org.json.JSONObject;

/* renamed from: com.airbnb.lottie.y  reason: case insensitive filesystem */
/* compiled from: LottieCompositionFactory */
public class C0047y {
    private static final Map<String, Z<C0035m>> xb = new HashMap();

    private C0047y() {
    }

    @Nullable
    private static S a(C0035m mVar, String str) {
        for (S s : mVar.eb().values()) {
            if (s.getFileName().equals(str)) {
                return s;
            }
        }
        return null;
    }

    private static X<C0035m> a(JsonReader jsonReader, @Nullable String str, boolean z) {
        try {
            C0035m b2 = u.b(jsonReader);
            if (str != null) {
                f.getInstance().a(str, b2);
            }
            X<C0035m> x = new X<>(b2);
            if (z) {
                h.closeQuietly(jsonReader);
            }
            return x;
        } catch (Exception e2) {
            X<C0035m> x2 = new X<>(e2);
            if (z) {
                h.closeQuietly(jsonReader);
            }
            return x2;
        } catch (Throwable th) {
            if (z) {
                h.closeQuietly(jsonReader);
            }
            throw th;
        }
    }

    @WorkerThread
    private static X<C0035m> a(InputStream inputStream, @Nullable String str, boolean z) {
        try {
            return b(JsonReader.a(Okio.buffer(Okio.source(inputStream))), str);
        } finally {
            if (z) {
                h.closeQuietly(inputStream);
            }
        }
    }

    public static Z<C0035m> a(Context context, @RawRes int i) {
        return a(context, i, c(context, i));
    }

    public static Z<C0035m> a(Context context, @RawRes int i, @Nullable String str) {
        return a(str, new CallableC0040r(new WeakReference(context), context.getApplicationContext(), i));
    }

    public static Z<C0035m> a(JsonReader jsonReader, @Nullable String str) {
        return a(str, new CallableC0044v(jsonReader, str));
    }

    private static Z<C0035m> a(@Nullable String str, Callable<X<C0035m>> callable) {
        C0035m mVar = str == null ? null : f.getInstance().get(str);
        if (mVar != null) {
            return new Z<>(new CallableC0046x(mVar));
        }
        if (str != null && xb.containsKey(str)) {
            return xb.get(str);
        }
        Z<C0035m> z = new Z<>(callable);
        if (str != null) {
            z.c(new C0036n(str));
            z.b(new C0037o(str));
            xb.put(str, z);
        }
        return z;
    }

    public static Z<C0035m> a(ZipInputStream zipInputStream, @Nullable String str) {
        return a(str, new CallableC0045w(zipInputStream, str));
    }

    @Deprecated
    public static Z<C0035m> a(JSONObject jSONObject, @Nullable String str) {
        return a(str, new CallableC0042t(jSONObject, str));
    }

    @WorkerThread
    public static X<C0035m> b(Context context, @RawRes int i) {
        return b(context, i, c(context, i));
    }

    @WorkerThread
    public static X<C0035m> b(Context context, @RawRes int i, @Nullable String str) {
        try {
            return c(context.getResources().openRawResource(i), str);
        } catch (Resources.NotFoundException e2) {
            return new X<>(e2);
        }
    }

    @WorkerThread
    public static X<C0035m> b(JsonReader jsonReader, @Nullable String str) {
        return a(jsonReader, str, true);
    }

    @WorkerThread
    public static X<C0035m> b(ZipInputStream zipInputStream, @Nullable String str) {
        try {
            return c(zipInputStream, str);
        } finally {
            h.closeQuietly(zipInputStream);
        }
    }

    @WorkerThread
    @Deprecated
    public static X<C0035m> b(JSONObject jSONObject, @Nullable String str) {
        return o(jSONObject.toString(), str);
    }

    public static Z<C0035m> b(InputStream inputStream, @Nullable String str) {
        return a(str, new CallableC0041s(inputStream, str));
    }

    @WorkerThread
    public static X<C0035m> c(InputStream inputStream, @Nullable String str) {
        return a(inputStream, str, true);
    }

    @WorkerThread
    private static X<C0035m> c(ZipInputStream zipInputStream, @Nullable String str) {
        HashMap hashMap = new HashMap();
        try {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            C0035m mVar = null;
            while (nextEntry != null) {
                String name = nextEntry.getName();
                if (name.contains("__MACOSX")) {
                    zipInputStream.closeEntry();
                } else if (nextEntry.getName().contains(".json")) {
                    mVar = a(JsonReader.a(Okio.buffer(Okio.source(zipInputStream))), (String) null, false).getValue();
                } else {
                    if (!name.contains(FileUtils.FILTER_FILE_SUFFIX)) {
                        if (!name.contains(".webp")) {
                            zipInputStream.closeEntry();
                        }
                    }
                    String[] split = name.split("/");
                    hashMap.put(split[split.length - 1], BitmapFactory.decodeStream(zipInputStream));
                }
                nextEntry = zipInputStream.getNextEntry();
            }
            if (mVar == null) {
                return new X<>(new IllegalArgumentException("Unable to parse composition"));
            }
            for (Map.Entry entry : hashMap.entrySet()) {
                S a2 = a(mVar, (String) entry.getKey());
                if (a2 != null) {
                    a2.setBitmap(h.a((Bitmap) entry.getValue(), a2.getWidth(), a2.getHeight()));
                }
            }
            for (Map.Entry<String, S> entry2 : mVar.eb().entrySet()) {
                if (entry2.getValue().getBitmap() == null) {
                    return new X<>(new IllegalStateException("There is no image for " + entry2.getValue().getFileName()));
                }
            }
            if (str != null) {
                f.getInstance().a(str, mVar);
            }
            return new X<>(mVar);
        } catch (IOException e2) {
            return new X<>(e2);
        }
    }

    public static Z<C0035m> c(Context context, String str, @Nullable String str2) {
        return a(str2, new CallableC0039q(context.getApplicationContext(), str, str2));
    }

    private static String c(Context context, @RawRes int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("rawRes");
        sb.append(isNightMode(context) ? "_night_" : "_day_");
        sb.append(i);
        return sb.toString();
    }

    @WorkerThread
    public static X<C0035m> d(Context context, String str, @Nullable String str2) {
        try {
            return str.endsWith(".zip") ? b(new ZipInputStream(context.getAssets().open(str)), str2) : c(context.getAssets().open(str), str2);
        } catch (IOException e2) {
            return new X<>(e2);
        }
    }

    public static Z<C0035m> e(Context context, String str, @Nullable String str2) {
        return a(str2, new CallableC0038p(context, str));
    }

    public static Z<C0035m> h(Context context, String str) {
        return c(context, str, "asset_" + str);
    }

    @WorkerThread
    public static X<C0035m> i(Context context, String str) {
        return d(context, str, "asset_" + str);
    }

    private static boolean isNightMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static Z<C0035m> j(Context context, String str) {
        return e(context, str, "url_" + str);
    }

    @WorkerThread
    public static X<C0035m> k(Context context, String str) {
        return b.l(context, str);
    }

    public static Z<C0035m> n(String str, @Nullable String str2) {
        return a(str2, new CallableC0043u(str, str2));
    }

    @WorkerThread
    public static X<C0035m> o(String str, @Nullable String str2) {
        return b(JsonReader.a(Okio.buffer(Okio.source(new ByteArrayInputStream(str.getBytes())))), str2);
    }

    public static void q(int i) {
        f.getInstance().resize(i);
    }
}
