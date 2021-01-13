package com.android.camera2.vendortag.struct;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;

public class SatFusionCalibrationData {
    public final byte[] data;
    public final int size;
    public final int type;

    public static class SatFusionCalibrationInfoMarshalQueryable implements MarshalQueryable<SatFusionCalibrationData> {

        private static class MarshalerImpl extends Marshaler<SatFusionCalibrationData> {
            private MarshalerImpl(MarshalQueryable<SatFusionCalibrationData> marshalQueryable, TypeReference<SatFusionCalibrationData> typeReference, int i) {
                super(marshalQueryable, typeReference, i);
            }

            public int getNativeSize() {
                return Marshaler.NATIVE_SIZE_DYNAMIC;
            }

            public void marshal(SatFusionCalibrationData satFusionCalibrationData, ByteBuffer byteBuffer) {
                byteBuffer.putInt(satFusionCalibrationData.type);
                byteBuffer.putInt(satFusionCalibrationData.size);
                byteBuffer.put(satFusionCalibrationData.data);
            }

            public SatFusionCalibrationData unmarshal(ByteBuffer byteBuffer) {
                int i = byteBuffer.getInt();
                int i2 = byteBuffer.getInt();
                byte[] bArr = new byte[i2];
                byteBuffer.get(bArr);
                return new SatFusionCalibrationData(i, i2, bArr);
            }
        }

        public Marshaler<SatFusionCalibrationData> createMarshaler(TypeReference<SatFusionCalibrationData> typeReference, int i) {
            return new MarshalerImpl(this, typeReference, i);
        }

        public boolean isTypeMappingSupported(TypeReference<SatFusionCalibrationData> typeReference, int i) {
            return i == 0 && SatFusionCalibrationData.class.equals(typeReference.getType());
        }
    }

    public SatFusionCalibrationData(int i, int i2, byte[] bArr) {
        this.type = i;
        this.size = i2;
        this.data = bArr;
    }

    public String toString() {
        return "SatFusionCalibrationData(type: " + this.type + ", size: " + this.size + ", data: " + this.data + ")";
    }
}
