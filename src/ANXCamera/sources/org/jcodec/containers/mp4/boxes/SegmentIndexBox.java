package org.jcodec.containers.mp4.boxes;

import com.android.gallery3d.exif.ExifInterface;
import java.nio.ByteBuffer;
import org.jcodec.platform.Platform;

public class SegmentIndexBox extends FullBox {
    public long earliest_presentation_time;
    public long first_offset;
    public long reference_ID;
    public int reference_count;
    public Reference[] references;
    public int reserved;
    public long timescale;

    public static class Reference {
        public long SAP_delta_time;
        public int SAP_type;
        public boolean reference_type;
        public long referenced_size;
        public boolean starts_with_SAP;
        public long subsegment_duration;

        public String toString() {
            return "Reference [reference_type=" + this.reference_type + ", referenced_size=" + this.referenced_size + ", subsegment_duration=" + this.subsegment_duration + ", starts_with_SAP=" + this.starts_with_SAP + ", SAP_type=" + this.SAP_type + ", SAP_delta_time=" + this.SAP_delta_time + "]";
        }
    }

    public SegmentIndexBox(Header header) {
        super(header);
    }

    public static SegmentIndexBox createSegmentIndexBox() {
        return new SegmentIndexBox(new Header(fourcc()));
    }

    public static String fourcc() {
        return "sidx";
    }

    /* access modifiers changed from: protected */
    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.FullBox
    public void doWrite(ByteBuffer byteBuffer) {
        super.doWrite(byteBuffer);
        byteBuffer.putInt((int) this.reference_ID);
        byteBuffer.putInt((int) this.timescale);
        if (this.version == 0) {
            byteBuffer.putInt((int) this.earliest_presentation_time);
            byteBuffer.putInt((int) this.first_offset);
        } else {
            byteBuffer.putLong(this.earliest_presentation_time);
            byteBuffer.putLong(this.first_offset);
        }
        byteBuffer.putShort((short) this.reserved);
        byteBuffer.putShort((short) this.reference_count);
        for (int i = 0; i < this.reference_count; i++) {
            Reference reference = this.references[i];
            int i2 = (int) (((long) ((reference.reference_type ? 1 : 0) << 31)) | reference.referenced_size);
            int i3 = (int) reference.subsegment_duration;
            int i4 = reference.starts_with_SAP ? Integer.MIN_VALUE : 0;
            byteBuffer.putInt(i2);
            byteBuffer.putInt(i3);
            byteBuffer.putInt((int) (((long) (i4 | ((reference.SAP_type & 7) << 28))) | (reference.SAP_delta_time & 268435455)));
        }
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public int estimateSize() {
        return (this.reference_count * 12) + 40;
    }

    @Override // org.jcodec.containers.mp4.boxes.Box, org.jcodec.containers.mp4.boxes.FullBox
    public void parse(ByteBuffer byteBuffer) {
        super.parse(byteBuffer);
        this.reference_ID = Platform.unsignedInt(byteBuffer.getInt());
        this.timescale = Platform.unsignedInt(byteBuffer.getInt());
        if (this.version == 0) {
            this.earliest_presentation_time = Platform.unsignedInt(byteBuffer.getInt());
            this.first_offset = Platform.unsignedInt(byteBuffer.getInt());
        } else {
            this.earliest_presentation_time = byteBuffer.getLong();
            this.first_offset = byteBuffer.getLong();
        }
        this.reserved = byteBuffer.getShort();
        this.reference_count = byteBuffer.getShort() & ExifInterface.ColorSpace.UNCALIBRATED;
        this.references = new Reference[this.reference_count];
        for (int i = 0; i < this.reference_count; i++) {
            long unsignedInt = Platform.unsignedInt(byteBuffer.getInt());
            long unsignedInt2 = Platform.unsignedInt(byteBuffer.getInt());
            long unsignedInt3 = Platform.unsignedInt(byteBuffer.getInt());
            Reference reference = new Reference();
            boolean z = true;
            reference.reference_type = ((unsignedInt >>> 31) & 1) == 1;
            reference.referenced_size = unsignedInt & 2147483647L;
            reference.subsegment_duration = unsignedInt2;
            if (((unsignedInt3 >>> 31) & 1) != 1) {
                z = false;
            }
            reference.starts_with_SAP = z;
            reference.SAP_type = (int) ((unsignedInt3 >>> 28) & 7);
            reference.SAP_delta_time = 268435455 & unsignedInt3;
            this.references[i] = reference;
        }
    }

    @Override // org.jcodec.containers.mp4.boxes.Box
    public String toString() {
        return "SegmentIndexBox [reference_ID=" + this.reference_ID + ", timescale=" + this.timescale + ", earliest_presentation_time=" + this.earliest_presentation_time + ", first_offset=" + this.first_offset + ", reserved=" + this.reserved + ", reference_count=" + this.reference_count + ", references=" + Platform.arrayToString(this.references) + ", version=" + ((int) this.version) + ", flags=" + this.flags + ", header=" + this.header + "]";
    }
}
