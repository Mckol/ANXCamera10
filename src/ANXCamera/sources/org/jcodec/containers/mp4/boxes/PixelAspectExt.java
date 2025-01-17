package org.jcodec.containers.mp4.boxes;

import java.nio.ByteBuffer;

public class PixelAspectExt extends Box {
    private int hSpacing;
    private int vSpacing;

    public PixelAspectExt(Header header) {
        super(header);
    }

    public static String fourcc() {
        return "pasp";
    }

    /* access modifiers changed from: protected */
    @Override // org.jcodec.containers.mp4.boxes.Box
    public void doWrite(ByteBuffer byteBuffer) {
        byteBuffer.putInt(this.hSpacing);
        byteBuffer.putInt(this.vSpacing);
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public int estimateSize() {
        return 16;
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public void parse(ByteBuffer byteBuffer) {
        this.hSpacing = byteBuffer.getInt();
        this.vSpacing = byteBuffer.getInt();
    }
}
