package com.android.camera2.vendortag.struct;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import java.nio.ByteBuffer;

public class MarshalQueryableChiRect implements MarshalQueryable<ChiRect> {
    private static final int SIZE = 16;

    public static class ChiRect {
        public int height;
        public int left;
        public int top;
        public int width;

        public ChiRect(int i, int i2, int i3, int i4) {
            this.left = i;
            this.top = i2;
            this.width = i3;
            this.height = i4;
        }

        public String toString() {
            return "ChiRect{left=" + this.left + ", top=" + this.top + ", width=" + this.width + ", height=" + this.height + '}';
        }
    }

    private class MarshalerRect extends Marshaler<ChiRect> {
        protected MarshalerRect(TypeReference<ChiRect> typeReference, int i) {
            super(MarshalQueryableChiRect.this, typeReference, i);
        }

        public int getNativeSize() {
            return 16;
        }

        public void marshal(ChiRect chiRect, ByteBuffer byteBuffer) {
            byteBuffer.putInt(chiRect.left);
            byteBuffer.putInt(chiRect.top);
            byteBuffer.putInt(chiRect.width);
            byteBuffer.putInt(chiRect.height);
        }

        public ChiRect unmarshal(ByteBuffer byteBuffer) {
            return new ChiRect(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt());
        }
    }

    public Marshaler<ChiRect> createMarshaler(TypeReference<ChiRect> typeReference, int i) {
        return new MarshalerRect(typeReference, i);
    }

    public boolean isTypeMappingSupported(TypeReference<ChiRect> typeReference, int i) {
        return ChiRect.class.equals(typeReference.getType());
    }
}
