package com.xiaomi.camera.imagecodec;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.xiaomi.camera.imagecodec.impl.HardwareCodecReprocessor;
import com.xiaomi.camera.imagecodec.impl.IspInterfaceReprocessor;
import com.xiaomi.camera.imagecodec.impl.SoftwareCodecReprocessor;
import com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor;

public class ReprocessorFactory {
    private static final String TAG = "ReprocessorFactory";
    private static ReprocessorType sDefaultReprocessorType;

    /* access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.camera.imagecodec.ReprocessorFactory$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$camera$imagecodec$ReprocessorFactory$ReprocessorType = new int[ReprocessorType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            $SwitchMap$com$xiaomi$camera$imagecodec$ReprocessorFactory$ReprocessorType[ReprocessorType.HARDWARE_CODEC.ordinal()] = 1;
            $SwitchMap$com$xiaomi$camera$imagecodec$ReprocessorFactory$ReprocessorType[ReprocessorType.SOFTWARE_CODEC.ordinal()] = 2;
            try {
                $SwitchMap$com$xiaomi$camera$imagecodec$ReprocessorFactory$ReprocessorType[ReprocessorType.ISP_INTERFACE.ordinal()] = 3;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public enum ReprocessorType {
        VIRTUAL_CAMERA,
        HARDWARE_CODEC,
        SOFTWARE_CODEC,
        ISP_INTERFACE
    }

    private ReprocessorFactory() {
    }

    public static synchronized Reprocessor getDefaultReprocessor() {
        Reprocessor reprocessor;
        synchronized (ReprocessorFactory.class) {
            if (sDefaultReprocessorType != null) {
                reprocessor = getReprocessor(sDefaultReprocessorType);
            } else {
                throw new IllegalStateException("The default reprocessingType is not defined yet, make sure #init(Context) has been called");
            }
        }
        return reprocessor;
    }

    public static synchronized Reprocessor getReprocessor(ReprocessorType reprocessorType) {
        synchronized (ReprocessorFactory.class) {
            if (reprocessorType != null) {
                int i = AnonymousClass1.$SwitchMap$com$xiaomi$camera$imagecodec$ReprocessorFactory$ReprocessorType[reprocessorType.ordinal()];
                if (i == 1) {
                    return HardwareCodecReprocessor.sInstance.get();
                } else if (i == 2) {
                    return SoftwareCodecReprocessor.sInstance.get();
                } else if (i != 3) {
                    return VirtualCameraReprocessor.sInstance.get();
                } else {
                    return IspInterfaceReprocessor.sInstance.get();
                }
            } else {
                throw new IllegalArgumentException("The given reprocessingType must not be null");
            }
        }
    }

    public static synchronized void init(Context context) {
        ReprocessorType reprocessorType;
        synchronized (ReprocessorFactory.class) {
            ReprocessorType reprocessorType2 = ReprocessorType.VIRTUAL_CAMERA;
            try {
                reprocessorType = ReprocessorType.values()[context.getResources().getInteger(R.integer.preferred_image_reprocessor_type)];
            } catch (Resources.NotFoundException | ArrayIndexOutOfBoundsException e2) {
                Log.d(TAG, "Failed to find the preferred reprocessor, defaults to use VIRTUAL_CAMERA instead", e2);
            }
            Log.d(TAG, "reprocessorType = " + reprocessorType);
            if (sDefaultReprocessorType == null) {
                sDefaultReprocessorType = reprocessorType;
            } else if (sDefaultReprocessorType != reprocessorType) {
                throw new IllegalStateException("The type of preferred reprocessor is not allowed to be changed.");
            }
        }
    }
}
