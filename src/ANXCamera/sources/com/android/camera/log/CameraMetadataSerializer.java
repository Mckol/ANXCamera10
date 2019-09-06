package com.android.camera.log;

import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureRequest.Key;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.LensShadingMap;
import android.util.Pair;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.List;

public final class CameraMetadataSerializer {
    private static final String TAG = "CameraMetadataSerializer";

    private interface Writable {
        void write(Writer writer) throws IOException;
    }

    private CameraMetadataSerializer() {
    }

    private static void dumpMetadata(Writable writable, Writer writer) {
        String str = "dumpMetadata - Failed to close writer.";
        try {
            writable.write(writer);
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e2) {
                    Log.e(TAG, str, e2);
                }
            }
        } catch (IOException e3) {
            Log.e(TAG, "dumpMetadata - Failed to dump metadata", e3);
            if (writer != null) {
                writer.close();
            }
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e4) {
                    Log.e(TAG, str, e4);
                }
            }
        }
    }

    private static void dumpMetadata(final String str, final CaptureRequest captureRequest, Writer writer) {
        dumpMetadata(new Writable() {
            public void write(Writer writer) throws IOException {
                List<Key> keys = captureRequest.getKeys();
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(10);
                writer.write(sb.toString());
                for (Key key : keys) {
                    writer.write(String.format("    %s\n", new Object[]{key.getName()}));
                    writer.write(String.format("        %s\n", new Object[]{CameraMetadataSerializer.metadataValueToString(captureRequest.get(key))}));
                }
            }
        }, new BufferedWriter(writer));
    }

    private static void dumpMetadata(final String str, final CaptureResult captureResult, Writer writer) {
        dumpMetadata(new Writable() {
            public void write(Writer writer) throws IOException {
                List<CaptureResult.Key> keys = captureResult.getKeys();
                StringBuilder sb = new StringBuilder();
                sb.append(String.format(str, new Object[0]));
                sb.append(10);
                writer.write(sb.toString());
                for (CaptureResult.Key key : keys) {
                    writer.write(String.format("    %s\n", new Object[]{key.getName()}));
                    writer.write(String.format("        %s\n", new Object[]{CameraMetadataSerializer.metadataValueToString(captureResult.get(key))}));
                }
            }
        }, new BufferedWriter(writer));
    }

    /* access modifiers changed from: private */
    public static String metadataValueToString(Object obj) {
        if (obj == null) {
            return "<null>";
        }
        if (!obj.getClass().isArray()) {
            return obj instanceof LensShadingMap ? toString((LensShadingMap) obj) : obj instanceof Pair ? toString((Pair) obj) : obj.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            sb.append(metadataValueToString(Array.get(obj, i)));
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    public static void serialize(String str, CameraMetadata<?> cameraMetadata, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            if (cameraMetadata instanceof CaptureRequest) {
                dumpMetadata(str, (CaptureRequest) cameraMetadata, (Writer) fileWriter);
            } else if (cameraMetadata instanceof CaptureResult) {
                dumpMetadata(str, (CaptureResult) cameraMetadata, (Writer) fileWriter);
            } else {
                fileWriter.close();
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot generate debug data from type ");
                sb.append(cameraMetadata.getClass().getName());
                throw new IllegalArgumentException(sb.toString());
            }
            fileWriter.close();
        } catch (IOException e2) {
            Log.e(TAG, "Could not write capture data to file.", e2);
        }
    }

    private static String toString(LensShadingMap lensShadingMap) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("LensShadingMap{");
        String[] strArr = {"R", "G_even", "G_odd", "B"};
        int rowCount = lensShadingMap.getRowCount();
        int columnCount = lensShadingMap.getColumnCount();
        for (int i = 0; i < 4; i++) {
            sb.append(strArr[i]);
            sb.append(":(");
            int i2 = 0;
            while (true) {
                str = ", ";
                if (i2 >= rowCount) {
                    break;
                }
                sb.append("[");
                for (int i3 = 0; i3 < columnCount; i3++) {
                    sb.append(lensShadingMap.getGainFactor(i, i3, i2));
                    if (i3 < columnCount - 1) {
                        sb.append(str);
                    }
                }
                sb.append("]");
                if (i2 < rowCount - 1) {
                    sb.append(str);
                }
                i2++;
            }
            sb.append(")");
            if (i < 3) {
                sb.append(str);
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private static String toString(Pair<?, ?> pair) {
        StringBuilder sb = new StringBuilder();
        sb.append("Pair: ");
        sb.append(metadataValueToString(pair.first));
        sb.append(" / ");
        sb.append(metadataValueToString(pair.second));
        return sb.toString();
    }
}
