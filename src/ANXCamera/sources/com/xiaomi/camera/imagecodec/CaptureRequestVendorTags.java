package com.xiaomi.camera.imagecodec;

import android.hardware.camera2.CaptureRequest;
import android.util.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class CaptureRequestVendorTags {
    private static final String TAG = "CaptureRequestVendorTags";
    public static final VendorTag<CaptureRequest.Key<Integer>> XIAOMI_JPEG_ORIENTATION = create(a.INSTANCE, Integer.class);
    private static Constructor<CaptureRequest.Key> requestConstructor;

    static /* synthetic */ String Zd() {
        return "xiaomi.jpeg.orientation";
    }

    private static <T> VendorTag<CaptureRequest.Key<T>> create(final Supplier<String> supplier, final Class<T> cls) {
        return new VendorTag<CaptureRequest.Key<T>>() {
            /* class com.xiaomi.camera.imagecodec.CaptureRequestVendorTags.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // com.xiaomi.camera.imagecodec.VendorTag
            public CaptureRequest.Key<T> create() {
                return CaptureRequestVendorTags.requestKey(getName(), cls);
            }

            @Override // com.xiaomi.camera.imagecodec.VendorTag
            public String getName() {
                return (String) supplier.get();
            }
        };
    }

    static <T> CaptureRequest.Key<T> requestKey(String str, Class<T> cls) {
        try {
            if (requestConstructor == null) {
                requestConstructor = CaptureRequest.Key.class.getConstructor(String.class, cls.getClass());
                requestConstructor.setAccessible(true);
            }
            return requestConstructor.newInstance(str, cls);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            Log.d(TAG, "Cannot find/call Key constructor: " + e2.getMessage());
            return null;
        }
    }
}
