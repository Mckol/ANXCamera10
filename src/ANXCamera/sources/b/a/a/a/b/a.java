package b.a.a.a.b;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import b.a.a.a.a.e;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: IBufferReadResult */
public final class a {
    public int value;
    public final ArrayList<Byte> zk = new ArrayList<>();

    public static final ArrayList<a> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<a> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16);
        int int32 = readBuffer.getInt32(8);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer((long) (int32 * 24), readBuffer.handle(), 0, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            a aVar = new a();
            aVar.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, (long) (i * 24));
            arrayList.add(aVar);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<a> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8, size);
        hwBlob.putBool(12, false);
        HwBlob hwBlob2 = new HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, (long) (i * 24));
        }
        hwBlob.putBlob(0, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != a.class) {
            return false;
        }
        a aVar = (a) obj;
        return this.value == aVar.value && HidlSupport.deepEquals(this.zk, aVar.zk);
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.value))), Integer.valueOf(HidlSupport.deepHashCode(this.zk))});
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        HwBlob hwBlob2 = hwBlob;
        this.value = hwBlob2.getInt32(j + 0);
        long j2 = j + 8;
        int int32 = hwBlob2.getInt32(8 + j2);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer((long) (int32 * 1), hwBlob.handle(), j2 + 0, true);
        this.zk.clear();
        for (int i = 0; i < int32; i++) {
            this.zk.add(Byte.valueOf(readEmbeddedBuffer.getInt8((long) (i * 1))));
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24), 0);
    }

    public final String toString() {
        return "{" + ".value = " + e.toString(this.value) + ", .data = " + this.zk + "}";
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j + 0, this.value);
        int size = this.zk.size();
        long j2 = j + 8;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        HwBlob hwBlob2 = new HwBlob(size * 1);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8((long) (i * 1), this.zk.get(i).byteValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0);
        hwParcel.writeBuffer(hwBlob);
    }
}
