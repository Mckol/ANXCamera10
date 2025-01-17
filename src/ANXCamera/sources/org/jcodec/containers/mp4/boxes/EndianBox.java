package org.jcodec.containers.mp4.boxes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class EndianBox extends Box {
    private ByteOrder endian;

    public EndianBox(Header header) {
        super(header);
    }

    public static EndianBox createEndianBox(ByteOrder byteOrder) {
        EndianBox endianBox = new EndianBox(new Header(fourcc()));
        endianBox.endian = byteOrder;
        return endianBox;
    }

    public static String fourcc() {
        return "enda";
    }

    /* access modifiers changed from: protected */
    public int calcSize() {
        return 2;
    }

    /* access modifiers changed from: protected */
    @Override // org.jcodec.containers.mp4.boxes.Box
    public void doWrite(ByteBuffer byteBuffer) {
        byteBuffer.putShort((short) (this.endian == ByteOrder.LITTLE_ENDIAN ? 1 : 0));
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public int estimateSize() {
        return 10;
    }

    public ByteOrder getEndian() {
        return this.endian;
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public void parse(ByteBuffer byteBuffer) {
        if (((long) byteBuffer.getShort()) == 1) {
            this.endian = ByteOrder.LITTLE_ENDIAN;
        } else {
            this.endian = ByteOrder.BIG_ENDIAN;
        }
    }
}
