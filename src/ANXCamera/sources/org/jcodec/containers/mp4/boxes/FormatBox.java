package org.jcodec.containers.mp4.boxes;

import java.nio.ByteBuffer;
import org.jcodec.common.JCodecUtil2;
import org.jcodec.common.io.NIOUtils;

public class FormatBox extends Box {
    private String fmt;

    public FormatBox(Header header) {
        super(header);
    }

    public static FormatBox createFormatBox(String str) {
        FormatBox formatBox = new FormatBox(new Header(fourcc()));
        formatBox.fmt = str;
        return formatBox;
    }

    public static String fourcc() {
        return "frma";
    }

    /* access modifiers changed from: protected */
    @Override // org.jcodec.containers.mp4.boxes.Box
    public void doWrite(ByteBuffer byteBuffer) {
        byteBuffer.put(JCodecUtil2.asciiString(this.fmt));
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public int estimateSize() {
        return JCodecUtil2.asciiString(this.fmt).length + 8;
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public void parse(ByteBuffer byteBuffer) {
        this.fmt = NIOUtils.readString(byteBuffer, 4);
    }
}
