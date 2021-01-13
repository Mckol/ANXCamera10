package miui.util;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DirectIndexedFile {
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "DensityIndexFile: ";

    /* access modifiers changed from: package-private */
    /* renamed from: miui.util.DirectIndexedFile$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type = new int[DataItemDescriptor.Type.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(20:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|20) */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0062 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            $SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.STRING.ordinal()] = 1;
            $SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.BYTE_ARRAY.ordinal()] = 2;
            $SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.SHORT_ARRAY.ordinal()] = 3;
            $SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.INTEGER_ARRAY.ordinal()] = 4;
            $SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.LONG_ARRAY.ordinal()] = 5;
            $SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.BYTE.ordinal()] = 6;
            $SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.SHORT.ordinal()] = 7;
            $SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.INTEGER.ordinal()] = 8;
            $SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[DataItemDescriptor.Type.LONG.ordinal()] = 9;
        }
    }

    public static class Builder {
        private IndexData mCurrentIndexData;
        private int mFileDataVersion;
        private FileHeader mFileHeader;
        private ArrayList<IndexData> mIndexDataList;

        /* access modifiers changed from: private */
        public class DataItemHolder {
            private ArrayList<Object> mList;
            private HashMap<Object, Integer> mMap;

            private DataItemHolder() {
                this.mMap = new HashMap<>();
                this.mList = new ArrayList<>();
            }

            /* synthetic */ DataItemHolder(Builder builder, AnonymousClass1 r2) {
                this();
            }

            /* access modifiers changed from: private */
            /* access modifiers changed from: public */
            private ArrayList<Object> getAll() {
                return this.mList;
            }

            /* access modifiers changed from: private */
            /* access modifiers changed from: public */
            private Integer put(Object obj) {
                Integer num = this.mMap.get(obj);
                if (num != null) {
                    return num;
                }
                Integer valueOf = Integer.valueOf(this.mList.size());
                this.mMap.put(obj, valueOf);
                this.mList.add(obj);
                return valueOf;
            }

            /* access modifiers changed from: private */
            /* access modifiers changed from: public */
            private int size() {
                return this.mList.size();
            }
        }

        /* access modifiers changed from: private */
        public static class IndexData {
            private DataItemDescriptor[] mDataItemDescriptions;
            private ArrayList<DataItemHolder> mDataItemHolders;
            private HashMap<Integer, Item> mDataMap;
            private Item mDefaultValue;
            private ArrayList<ArrayList<Item>> mIndexDataGroups;
            private IndexGroupDescriptor[] mIndexGroupDescriptions;

            private IndexData(int i) {
                this.mDataMap = new HashMap<>();
                this.mDataItemHolders = new ArrayList<>();
                this.mIndexDataGroups = new ArrayList<>();
                this.mDataItemDescriptions = new DataItemDescriptor[i];
            }

            /* synthetic */ IndexData(int i, AnonymousClass1 r2) {
                this(i);
            }
        }

        /* access modifiers changed from: private */
        public class Item implements Comparable<Item> {
            private int mIndex;
            private Object[] mObjects;

            private Item(int i, Object[] objArr) {
                this.mIndex = i;
                this.mObjects = objArr;
            }

            /* synthetic */ Item(Builder builder, int i, Object[] objArr, AnonymousClass1 r4) {
                this(i, objArr);
            }

            public int compareTo(Item item) {
                return this.mIndex - item.mIndex;
            }

            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                return (obj instanceof Item) && this.mIndex == ((Item) obj).mIndex;
            }

            public int hashCode() {
                return this.mIndex;
            }
        }

        private Builder(int i) {
            this.mIndexDataList = new ArrayList<>();
            this.mFileDataVersion = i;
        }

        /* synthetic */ Builder(int i, AnonymousClass1 r2) {
            this(i);
        }

        private void build() {
            int size = this.mIndexDataList.size();
            this.mFileHeader = new FileHeader(size, this.mFileDataVersion, null);
            for (int i = 0; i < size; i++) {
                IndexData indexData = this.mIndexDataList.get(i);
                this.mFileHeader.mDescriptionOffsets[i] = new DescriptionPair(0, 0, null);
                int i2 = 0;
                while (i2 < indexData.mIndexDataGroups.size() && ((ArrayList) indexData.mIndexDataGroups.get(i2)).size() != 0) {
                    i2++;
                }
                indexData.mIndexGroupDescriptions = new IndexGroupDescriptor[i2];
                for (int i3 = 0; i3 < indexData.mIndexGroupDescriptions.length; i3++) {
                    ArrayList arrayList = (ArrayList) indexData.mIndexDataGroups.get(i3);
                    Collections.sort(arrayList);
                    indexData.mIndexGroupDescriptions[i3] = new IndexGroupDescriptor(((Item) arrayList.get(0)).mIndex, ((Item) arrayList.get(arrayList.size() - 1)).mIndex + 1, 0, null);
                }
            }
            try {
                writeAll(null);
            } catch (IOException unused) {
            }
        }

        private void checkCurrentIndexingDataKind() {
            if (this.mCurrentIndexData == null) {
                throw new IllegalArgumentException("Please add a data kind before adding group");
            }
        }

        private void checkCurrentIndexingGroup() {
            checkCurrentIndexingDataKind();
            if (this.mCurrentIndexData.mIndexDataGroups.size() == 0) {
                throw new IllegalArgumentException("Please add a data group before adding data");
            }
        }

        private int writeAll(DataOutput dataOutput) throws IOException {
            int i = 0;
            int i2 = 0;
            while (i < this.mFileHeader.mDescriptionOffsets.length) {
                int write = i2 + this.mFileHeader.write(dataOutput);
                this.mFileHeader.mDescriptionOffsets[i].mIndexGroupDescriptionOffset = (long) write;
                IndexData indexData = this.mIndexDataList.get(i);
                if (dataOutput != null) {
                    dataOutput.writeInt(indexData.mIndexGroupDescriptions.length);
                }
                int i3 = write + 4;
                for (int i4 = 0; i4 < indexData.mIndexGroupDescriptions.length; i4++) {
                    i3 += indexData.mIndexGroupDescriptions[i4].write(dataOutput);
                }
                this.mFileHeader.mDescriptionOffsets[i].mDataItemDescriptionOffset = (long) i3;
                if (dataOutput != null) {
                    dataOutput.writeInt(indexData.mDataItemDescriptions.length);
                }
                int i5 = i3 + 4;
                for (int i6 = 0; i6 < indexData.mDataItemDescriptions.length; i6++) {
                    i5 += indexData.mDataItemDescriptions[i6].write(dataOutput);
                }
                for (int i7 = 0; i7 < indexData.mDataItemDescriptions.length; i7++) {
                    indexData.mDataItemDescriptions[i7].mOffset = (long) i5;
                    i5 += indexData.mDataItemDescriptions[i7].writeDataItems(dataOutput, ((DataItemHolder) indexData.mDataItemHolders.get(i7)).getAll());
                }
                for (int i8 = 0; i8 < indexData.mIndexGroupDescriptions.length; i8++) {
                    indexData.mIndexGroupDescriptions[i8].mOffset = (long) i5;
                    if (dataOutput == null) {
                        int i9 = 0;
                        for (int i10 = 0; i10 < indexData.mDataItemDescriptions.length; i10++) {
                            i9 += indexData.mDataItemDescriptions[i10].mIndexSize;
                        }
                        i5 += (indexData.mIndexGroupDescriptions[i8].mMaxIndex - indexData.mIndexGroupDescriptions[i8].mMinIndex) * i9;
                    } else {
                        int i11 = indexData.mIndexGroupDescriptions[i8].mMinIndex;
                        while (i11 < indexData.mIndexGroupDescriptions[i8].mMaxIndex) {
                            Item item = (Item) indexData.mDataMap.get(Integer.valueOf(i11));
                            if (item == null) {
                                item = indexData.mDefaultValue;
                            }
                            int i12 = i5;
                            for (int i13 = 0; i13 < indexData.mDataItemDescriptions.length; i13++) {
                                if (indexData.mDataItemDescriptions[i13].mIndexSize == 1) {
                                    dataOutput.writeByte(((Integer) item.mObjects[i13]).intValue());
                                } else if (indexData.mDataItemDescriptions[i13].mIndexSize == 2) {
                                    dataOutput.writeShort(((Integer) item.mObjects[i13]).intValue());
                                } else if (indexData.mDataItemDescriptions[i13].mIndexSize == 4) {
                                    dataOutput.writeInt(((Integer) item.mObjects[i13]).intValue());
                                } else if (indexData.mDataItemDescriptions[i13].mIndexSize == 8) {
                                    dataOutput.writeLong(((Long) item.mObjects[i13]).longValue());
                                }
                                i12 += indexData.mDataItemDescriptions[i13].mIndexSize;
                            }
                            i11++;
                            i5 = i12;
                        }
                    }
                }
                i++;
                i2 = i5;
            }
            return i2;
        }

        public void add(int i, Object... objArr) {
            checkCurrentIndexingGroup();
            if (this.mCurrentIndexData.mDataItemDescriptions.length == objArr.length) {
                for (int i2 = 0; i2 < objArr.length; i2++) {
                    switch (AnonymousClass1.$SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[this.mCurrentIndexData.mDataItemDescriptions[i2].mType.ordinal()]) {
                        case 1:
                            if (objArr[i2] instanceof String) {
                                objArr[i2] = ((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i2)).put(objArr[i2]);
                                this.mCurrentIndexData.mDataItemDescriptions[i2].mIndexSize = DataItemDescriptor.getSizeOf(((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i2)).size());
                                break;
                            } else {
                                throw new IllegalArgumentException("Object[" + i2 + "] should be String");
                            }
                        case 2:
                            if (objArr[i2] instanceof byte[]) {
                                objArr[i2] = ((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i2)).put(objArr[i2]);
                                this.mCurrentIndexData.mDataItemDescriptions[i2].mIndexSize = DataItemDescriptor.getSizeOf(((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i2)).size());
                                break;
                            } else {
                                throw new IllegalArgumentException("Object[" + i2 + "] should be byte[]");
                            }
                        case 3:
                            if (objArr[i2] instanceof short[]) {
                                objArr[i2] = ((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i2)).put(objArr[i2]);
                                this.mCurrentIndexData.mDataItemDescriptions[i2].mIndexSize = DataItemDescriptor.getSizeOf(((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i2)).size());
                                break;
                            } else {
                                throw new IllegalArgumentException("Object[" + i2 + "] should be short[]");
                            }
                        case 4:
                            if (objArr[i2] instanceof int[]) {
                                objArr[i2] = ((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i2)).put(objArr[i2]);
                                this.mCurrentIndexData.mDataItemDescriptions[i2].mIndexSize = DataItemDescriptor.getSizeOf(((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i2)).size());
                                break;
                            } else {
                                throw new IllegalArgumentException("Object[" + i2 + "] should be int[]");
                            }
                        case 5:
                            if (objArr[i2] instanceof long[]) {
                                objArr[i2] = ((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i2)).put(objArr[i2]);
                                this.mCurrentIndexData.mDataItemDescriptions[i2].mIndexSize = DataItemDescriptor.getSizeOf(((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i2)).size());
                                break;
                            } else {
                                throw new IllegalArgumentException("Object[" + i2 + "] should be long[]");
                            }
                        case 6:
                            if (objArr[i2] instanceof Byte) {
                                break;
                            } else {
                                throw new IllegalArgumentException("Object[" + i2 + "] should be byte");
                            }
                        case 7:
                            if (objArr[i2] instanceof Short) {
                                break;
                            } else {
                                throw new IllegalArgumentException("Object[" + i2 + "] should be short");
                            }
                        case 8:
                            if (objArr[i2] instanceof Integer) {
                                break;
                            } else {
                                throw new IllegalArgumentException("Object[" + i2 + "] should be int");
                            }
                        case 9:
                            if (objArr[i2] instanceof Long) {
                                break;
                            } else {
                                throw new IllegalArgumentException("Object[" + i2 + "] should be long");
                            }
                        default:
                            throw new IllegalArgumentException("Unsupported type of objects " + i2 + ", " + this.mCurrentIndexData.mDataItemDescriptions[i2].mType + " expected");
                    }
                }
                Item item = new Item(this, i, objArr, null);
                this.mCurrentIndexData.mDataMap.put(Integer.valueOf(i), item);
                ((ArrayList) this.mCurrentIndexData.mIndexDataGroups.get(this.mCurrentIndexData.mIndexDataGroups.size() - 1)).add(item);
                return;
            }
            throw new IllegalArgumentException("Different number of objects inputted, " + this.mCurrentIndexData.mDataItemDescriptions.length + " data expected");
        }

        public void addGroup(int[] iArr, Object[][] objArr) {
            checkCurrentIndexingDataKind();
            if (iArr.length == objArr.length) {
                newGroup();
                for (int i = 0; i < iArr.length; i++) {
                    add(iArr[i], objArr[i]);
                }
                return;
            }
            throw new IllegalArgumentException("Different number between indexes and objects");
        }

        public void addKind(Object... objArr) {
            DataItemDescriptor.Type type;
            this.mCurrentIndexData = new IndexData(objArr.length, null);
            this.mIndexDataList.add(this.mCurrentIndexData);
            for (int i = 0; i < objArr.length; i++) {
                this.mCurrentIndexData.mDataItemHolders.add(new DataItemHolder(this, null));
                byte b2 = 1;
                if (objArr[i] instanceof Byte) {
                    type = DataItemDescriptor.Type.BYTE;
                    ((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i)).put(objArr[i]);
                } else if (objArr[i] instanceof Short) {
                    type = DataItemDescriptor.Type.SHORT;
                    b2 = 2;
                    ((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i)).put(objArr[i]);
                } else if (objArr[i] instanceof Integer) {
                    type = DataItemDescriptor.Type.INTEGER;
                    b2 = 4;
                    ((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i)).put(objArr[i]);
                } else if (objArr[i] instanceof Long) {
                    type = DataItemDescriptor.Type.LONG;
                    b2 = 8;
                    ((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i)).put(objArr[i]);
                } else if (objArr[i] instanceof String) {
                    type = DataItemDescriptor.Type.STRING;
                    objArr[i] = ((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i)).put(objArr[i]);
                } else if (objArr[i] instanceof byte[]) {
                    type = DataItemDescriptor.Type.BYTE_ARRAY;
                    objArr[i] = ((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i)).put(objArr[i]);
                } else if (objArr[i] instanceof short[]) {
                    type = DataItemDescriptor.Type.SHORT_ARRAY;
                    objArr[i] = ((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i)).put(objArr[i]);
                } else if (objArr[i] instanceof int[]) {
                    type = DataItemDescriptor.Type.INTEGER_ARRAY;
                    objArr[i] = ((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i)).put(objArr[i]);
                } else if (objArr[i] instanceof long[]) {
                    type = DataItemDescriptor.Type.LONG_ARRAY;
                    objArr[i] = ((DataItemHolder) this.mCurrentIndexData.mDataItemHolders.get(i)).put(objArr[i]);
                } else {
                    throw new IllegalArgumentException("Unsupported type of the [" + i + "] argument");
                }
                this.mCurrentIndexData.mDataItemDescriptions[i] = new DataItemDescriptor(type, b2, (byte) 0, (byte) 0, 0, null);
            }
            this.mCurrentIndexData.mDefaultValue = new Item(this, -1, objArr, null);
        }

        public void newGroup() {
            if (this.mCurrentIndexData.mIndexDataGroups.size() == 0 || ((ArrayList) this.mCurrentIndexData.mIndexDataGroups.get(this.mCurrentIndexData.mIndexDataGroups.size() - 1)).size() != 0) {
                this.mCurrentIndexData.mIndexDataGroups.add(new ArrayList());
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:11:0x0020  */
        /* JADX WARNING: Removed duplicated region for block: B:14:0x002e  */
        public void write(String str) throws IOException {
            Throwable th;
            build();
            DataOutputStream dataOutputStream = null;
            try {
                DataOutputStream dataOutputStream2 = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(str)));
                try {
                    writeAll(dataOutputStream2);
                    dataOutputStream2.close();
                } catch (Throwable th2) {
                    th = th2;
                    dataOutputStream = dataOutputStream2;
                    if (dataOutputStream != null) {
                        dataOutputStream.close();
                    }
                    if (new File(str).delete()) {
                        PrintStream printStream = System.err;
                        printStream.println("Cannot delete file " + str);
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (dataOutputStream != null) {
                }
                if (new File(str).delete()) {
                }
                throw th;
            }
        }
    }

    private static class DataInputRandom implements InputFile {
        private RandomAccessFile mFile;

        DataInputRandom(RandomAccessFile randomAccessFile) {
            this.mFile = randomAccessFile;
        }

        @Override // miui.util.DirectIndexedFile.InputFile
        public void close() throws IOException {
            this.mFile.close();
        }

        @Override // miui.util.DirectIndexedFile.InputFile
        public long getFilePointer() throws IOException {
            return this.mFile.getFilePointer();
        }

        @Override // java.io.DataInput
        public boolean readBoolean() throws IOException {
            return this.mFile.readBoolean();
        }

        @Override // java.io.DataInput
        public byte readByte() throws IOException {
            return this.mFile.readByte();
        }

        @Override // java.io.DataInput
        public char readChar() throws IOException {
            return this.mFile.readChar();
        }

        @Override // java.io.DataInput
        public double readDouble() throws IOException {
            return this.mFile.readDouble();
        }

        @Override // java.io.DataInput
        public float readFloat() throws IOException {
            return this.mFile.readFloat();
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr) throws IOException {
            this.mFile.readFully(bArr);
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr, int i, int i2) throws IOException {
            this.mFile.readFully(bArr, i, i2);
        }

        @Override // java.io.DataInput
        public int readInt() throws IOException {
            return this.mFile.readInt();
        }

        @Override // java.io.DataInput
        public String readLine() throws IOException {
            return this.mFile.readLine();
        }

        @Override // java.io.DataInput
        public long readLong() throws IOException {
            return this.mFile.readLong();
        }

        @Override // java.io.DataInput
        public short readShort() throws IOException {
            return this.mFile.readShort();
        }

        @Override // java.io.DataInput
        public String readUTF() throws IOException {
            return this.mFile.readUTF();
        }

        @Override // java.io.DataInput
        public int readUnsignedByte() throws IOException {
            return this.mFile.readUnsignedByte();
        }

        @Override // java.io.DataInput
        public int readUnsignedShort() throws IOException {
            return this.mFile.readUnsignedShort();
        }

        @Override // miui.util.DirectIndexedFile.InputFile
        public void seek(long j) throws IOException {
            this.mFile.seek(j);
        }

        @Override // java.io.DataInput
        public int skipBytes(int i) throws IOException {
            return this.mFile.skipBytes(i);
        }
    }

    private static class DataInputStream implements InputFile {
        private InputStream mInputFile;
        private long mInputPos = 0;

        DataInputStream(InputStream inputStream) {
            this.mInputFile = inputStream;
            this.mInputFile.mark(0);
        }

        @Override // miui.util.DirectIndexedFile.InputFile
        public void close() throws IOException {
            this.mInputFile.close();
        }

        @Override // miui.util.DirectIndexedFile.InputFile
        public long getFilePointer() throws IOException {
            return this.mInputPos;
        }

        @Override // java.io.DataInput
        public boolean readBoolean() throws IOException {
            this.mInputPos++;
            return this.mInputFile.read() != 0;
        }

        @Override // java.io.DataInput
        public byte readByte() throws IOException {
            this.mInputPos++;
            return (byte) this.mInputFile.read();
        }

        @Override // java.io.DataInput
        public char readChar() throws IOException {
            byte[] bArr = new byte[2];
            this.mInputPos += 2;
            if (this.mInputFile.read(bArr) == 2) {
                return (char) (((char) (bArr[1] & 255)) | ((bArr[0] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK));
            }
            return 0;
        }

        @Override // java.io.DataInput
        public double readDouble() throws IOException {
            throw new IOException();
        }

        @Override // java.io.DataInput
        public float readFloat() throws IOException {
            throw new IOException();
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr) throws IOException {
            this.mInputPos += (long) this.mInputFile.read(bArr);
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr, int i, int i2) throws IOException {
            this.mInputPos += (long) this.mInputFile.read(bArr, i, i2);
        }

        @Override // java.io.DataInput
        public int readInt() throws IOException {
            byte[] bArr = new byte[4];
            this.mInputPos += 4;
            if (this.mInputFile.read(bArr) == 4) {
                return (bArr[3] & 255) | ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((bArr[1] << 16) & 16711680) | ((bArr[0] << 24) & ViewCompat.MEASURED_STATE_MASK);
            }
            return 0;
        }

        @Override // java.io.DataInput
        public String readLine() throws IOException {
            throw new IOException();
        }

        @Override // java.io.DataInput
        public long readLong() throws IOException {
            byte[] bArr = new byte[8];
            this.mInputPos += 8;
            if (this.mInputFile.read(bArr) != 8) {
                return 0;
            }
            return ((((long) bArr[0]) << 56) & -72057594037927936L) | ((long) (bArr[7] & 255)) | (((long) (bArr[6] << 8)) & 65280) | (((long) (bArr[5] << 16)) & 16711680) | (((long) (bArr[4] << 24)) & 4278190080L) | ((((long) bArr[3]) << 32) & 1095216660480L) | ((((long) bArr[2]) << 40) & 280375465082880L) | ((((long) bArr[1]) << 48) & 71776119061217280L);
        }

        @Override // java.io.DataInput
        public short readShort() throws IOException {
            byte[] bArr = new byte[2];
            this.mInputPos += 2;
            if (this.mInputFile.read(bArr) == 2) {
                return (short) (((short) (bArr[1] & 255)) | ((bArr[0] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK));
            }
            return 0;
        }

        @Override // java.io.DataInput
        public String readUTF() throws IOException {
            throw new IOException();
        }

        @Override // java.io.DataInput
        public int readUnsignedByte() throws IOException {
            this.mInputPos++;
            return (byte) this.mInputFile.read();
        }

        @Override // java.io.DataInput
        public int readUnsignedShort() throws IOException {
            byte[] bArr = new byte[2];
            this.mInputPos += 2;
            if (this.mInputFile.read(bArr) == 2) {
                return (short) (((short) (bArr[1] & 255)) | ((bArr[0] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK));
            }
            return 0;
        }

        @Override // miui.util.DirectIndexedFile.InputFile
        public void seek(long j) throws IOException {
            this.mInputFile.reset();
            if (this.mInputFile.skip(j) == j) {
                this.mInputPos = j;
                return;
            }
            throw new IOException("Skip failed");
        }

        @Override // java.io.DataInput
        public int skipBytes(int i) throws IOException {
            int skip = (int) this.mInputFile.skip((long) i);
            this.mInputPos += (long) skip;
            return skip;
        }
    }

    /* access modifiers changed from: private */
    public static class DataItemDescriptor {
        private static byte[] sBuffer = new byte[1024];
        private byte mIndexSize;
        private byte mLengthSize;
        private long mOffset;
        private byte mOffsetSize;
        private Type mType;

        /* access modifiers changed from: private */
        public enum Type {
            BYTE,
            SHORT,
            INTEGER,
            LONG,
            STRING,
            BYTE_ARRAY,
            SHORT_ARRAY,
            INTEGER_ARRAY,
            LONG_ARRAY
        }

        private DataItemDescriptor(Type type, byte b2, byte b3, byte b4, long j) {
            this.mType = type;
            this.mIndexSize = b2;
            this.mLengthSize = b3;
            this.mOffsetSize = b4;
            this.mOffset = j;
        }

        /* synthetic */ DataItemDescriptor(Type type, byte b2, byte b3, byte b4, long j, AnonymousClass1 r7) {
            this(type, b2, b3, b4, j);
        }

        private static byte[] aquireBuffer(int i) {
            byte[] bArr;
            synchronized (DataItemDescriptor.class) {
                if (sBuffer == null || sBuffer.length < i) {
                    sBuffer = new byte[i];
                }
                bArr = sBuffer;
                sBuffer = null;
            }
            return bArr;
        }

        /* access modifiers changed from: private */
        public static byte getSizeOf(int i) {
            byte b2 = 0;
            for (long j = (long) (i * 2); j > 0; j >>= 8) {
                b2 = (byte) (b2 + 1);
            }
            if (b2 == 3) {
                return 4;
            }
            if (b2 <= 4 || b2 >= 8) {
                return b2;
            }
            return 8;
        }

        /* access modifiers changed from: private */
        public static DataItemDescriptor read(DataInput dataInput) throws IOException {
            return new DataItemDescriptor(Type.values()[dataInput.readByte()], dataInput.readByte(), dataInput.readByte(), dataInput.readByte(), dataInput.readLong());
        }

        /* access modifiers changed from: private */
        public static long readAccordingToSize(DataInput dataInput, int i) throws IOException {
            int i2;
            if (i == 1) {
                i2 = dataInput.readByte();
            } else if (i == 2) {
                i2 = dataInput.readShort();
            } else if (i == 4) {
                i2 = dataInput.readInt();
            } else if (i == 8) {
                return dataInput.readLong();
            } else {
                throw new IllegalArgumentException("Unsuppoert size " + i);
            }
            return (long) i2;
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private Object[] readDataItems(InputFile inputFile) throws IOException {
            switch (AnonymousClass1.$SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[this.mType.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    Object[] objArr = new Object[inputFile.readInt()];
                    objArr[0] = readSingleDataItem(inputFile, 0);
                    return objArr;
                case 6:
                    return new Object[]{Byte.valueOf(inputFile.readByte())};
                case 7:
                    return new Object[]{Short.valueOf(inputFile.readShort())};
                case 8:
                    return new Object[]{Integer.valueOf(inputFile.readInt())};
                case 9:
                    return new Object[]{Long.valueOf(inputFile.readLong())};
                default:
                    return null;
            }
        }

        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:32:0x0095 */
        /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: miui.util.DirectIndexedFile$InputFile */
        /* JADX DEBUG: Multi-variable search result rejected for r5v1, resolved type: java.lang.Object */
        /* JADX DEBUG: Multi-variable search result rejected for r5v10, resolved type: short[] */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r5v4 */
        /* JADX WARN: Type inference failed for: r5v7, types: [byte[]] */
        /* JADX WARN: Type inference failed for: r5v13, types: [int[]] */
        /* JADX WARN: Type inference failed for: r5v16, types: [long[]] */
        /* JADX WARN: Type inference failed for: r5v17 */
        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private Object readSingleDataItem(InputFile inputFile, int i) throws IOException {
            short[] sArr;
            long filePointer = inputFile.getFilePointer();
            if (i != 0) {
                inputFile.seek(((long) (this.mOffsetSize * i)) + filePointer);
            }
            inputFile.seek(filePointer + readAccordingToSize(inputFile, this.mOffsetSize));
            int i2 = AnonymousClass1.$SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[this.mType.ordinal()];
            byte[] bArr = null;
            int i3 = 0;
            if (i2 == 1) {
                int readAccordingToSize = (int) readAccordingToSize(inputFile, this.mLengthSize);
                bArr = aquireBuffer(readAccordingToSize);
                inputFile.readFully(bArr, 0, readAccordingToSize);
                sArr = new String(bArr, 0, readAccordingToSize);
            } else if (i2 == 2) {
                sArr = new byte[((int) readAccordingToSize(inputFile, this.mLengthSize))];
                inputFile.readFully(sArr);
            } else if (i2 == 3) {
                sArr = new short[((int) readAccordingToSize(inputFile, this.mLengthSize))];
                while (i3 < sArr.length) {
                    sArr[i3] = inputFile.readShort();
                    i3++;
                }
            } else if (i2 == 4) {
                sArr = new int[((int) readAccordingToSize(inputFile, this.mLengthSize))];
                while (i3 < sArr.length) {
                    sArr[i3] = inputFile.readInt();
                    i3++;
                }
            } else if (i2 != 5) {
                sArr = 0;
            } else {
                sArr = new long[((int) readAccordingToSize(inputFile, this.mLengthSize))];
                while (i3 < sArr.length) {
                    sArr[i3] = inputFile.readLong();
                    i3++;
                }
            }
            releaseBuffer(bArr);
            return sArr;
        }

        private static void releaseBuffer(byte[] bArr) {
            synchronized (DataItemDescriptor.class) {
                if (bArr != null) {
                    if (sBuffer == null || sBuffer.length < bArr.length) {
                        sBuffer = bArr;
                    }
                }
            }
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private int write(DataOutput dataOutput) throws IOException {
            if (dataOutput == null) {
                return 12;
            }
            dataOutput.writeByte(this.mType.ordinal());
            dataOutput.writeByte(this.mIndexSize);
            dataOutput.writeByte(this.mLengthSize);
            dataOutput.writeByte(this.mOffsetSize);
            dataOutput.writeLong(this.mOffset);
            return 12;
        }

        private static void writeAccordingToSize(DataOutput dataOutput, int i, long j) throws IOException {
            if (i == 1) {
                dataOutput.writeByte((int) j);
            } else if (i == 2) {
                dataOutput.writeShort((int) j);
            } else if (i == 4) {
                dataOutput.writeInt((int) j);
            } else if (i == 8) {
                dataOutput.writeLong(j);
            } else {
                throw new IllegalArgumentException("Unsuppoert size " + i);
            }
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private int writeDataItems(DataOutput dataOutput, List<Object> list) throws IOException {
            int i = 4;
            switch (AnonymousClass1.$SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[this.mType.ordinal()]) {
                case 1:
                    if (dataOutput != null) {
                        dataOutput.writeInt(list.size());
                    }
                    Iterator<Object> it = list.iterator();
                    int writeOffsets = 4 + writeOffsets(dataOutput, list);
                    while (it.hasNext()) {
                        byte[] bytes = ((String) it.next()).getBytes();
                        if (dataOutput != null) {
                            writeAccordingToSize(dataOutput, this.mLengthSize, (long) bytes.length);
                            for (byte b2 : bytes) {
                                dataOutput.writeByte(b2);
                            }
                        }
                        writeOffsets += this.mLengthSize + bytes.length;
                    }
                    return writeOffsets;
                case 2:
                    if (dataOutput != null) {
                        dataOutput.writeInt(list.size());
                    }
                    Iterator<Object> it2 = list.iterator();
                    int writeOffsets2 = 4 + writeOffsets(dataOutput, list);
                    while (it2.hasNext()) {
                        byte[] bArr = (byte[]) it2.next();
                        if (dataOutput != null) {
                            writeAccordingToSize(dataOutput, this.mLengthSize, (long) bArr.length);
                            dataOutput.write(bArr);
                        }
                        writeOffsets2 += this.mLengthSize + bArr.length;
                    }
                    return writeOffsets2;
                case 3:
                    if (dataOutput != null) {
                        dataOutput.writeInt(list.size());
                    }
                    Iterator<Object> it3 = list.iterator();
                    int writeOffsets3 = 4 + writeOffsets(dataOutput, list);
                    while (it3.hasNext()) {
                        short[] sArr = (short[]) it3.next();
                        if (dataOutput != null) {
                            writeAccordingToSize(dataOutput, this.mLengthSize, (long) sArr.length);
                            for (short s : sArr) {
                                dataOutput.writeShort(s);
                            }
                        }
                        writeOffsets3 += this.mLengthSize + (sArr.length * 2);
                    }
                    return writeOffsets3;
                case 4:
                    if (dataOutput != null) {
                        dataOutput.writeInt(list.size());
                    }
                    Iterator<Object> it4 = list.iterator();
                    int writeOffsets4 = writeOffsets(dataOutput, list) + 4;
                    while (it4.hasNext()) {
                        int[] iArr = (int[]) it4.next();
                        if (dataOutput != null) {
                            writeAccordingToSize(dataOutput, this.mLengthSize, (long) iArr.length);
                            for (int i2 : iArr) {
                                dataOutput.writeInt(i2);
                            }
                        }
                        writeOffsets4 += this.mLengthSize + (iArr.length * 4);
                    }
                    return writeOffsets4;
                case 5:
                    if (dataOutput != null) {
                        dataOutput.writeInt(list.size());
                    }
                    i = 4 + writeOffsets(dataOutput, list);
                    Iterator<Object> it5 = list.iterator();
                    while (it5.hasNext()) {
                        long[] jArr = (long[]) it5.next();
                        if (dataOutput != null) {
                            writeAccordingToSize(dataOutput, this.mLengthSize, (long) jArr.length);
                            for (long j : jArr) {
                                dataOutput.writeLong(j);
                            }
                        }
                        i += this.mLengthSize + (jArr.length * 8);
                    }
                    break;
                case 6:
                    if (dataOutput != null) {
                        dataOutput.writeByte(((Byte) list.get(0)).byteValue());
                    }
                    return 1;
                case 7:
                    if (dataOutput != null) {
                        dataOutput.writeShort(((Short) list.get(0)).shortValue());
                    }
                    return 2;
                case 8:
                    if (dataOutput != null) {
                        dataOutput.writeInt(((Integer) list.get(0)).intValue());
                        break;
                    }
                    break;
                case 9:
                    if (dataOutput == null) {
                        return 8;
                    }
                    dataOutput.writeLong(((Long) list.get(0)).longValue());
                    return 8;
                default:
                    return 0;
            }
            return i;
        }

        /* JADX WARNING: Removed duplicated region for block: B:24:0x005c  */
        /* JADX WARNING: Removed duplicated region for block: B:46:0x001b A[SYNTHETIC] */
        private int writeOffsets(DataOutput dataOutput, List<Object> list) throws IOException {
            byte b2;
            int i;
            int i2;
            int i3;
            if (dataOutput == null || this.mOffsetSize == 0 || this.mLengthSize == 0) {
                int size = list.size() * 4;
                int i4 = 0;
                for (Object obj : list) {
                    int i5 = AnonymousClass1.$SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[this.mType.ordinal()];
                    if (i5 == 1) {
                        i2 = ((String) obj).getBytes().length;
                    } else if (i5 != 2) {
                        if (i5 == 3) {
                            i2 = ((short[]) obj).length;
                            i3 = i2 * 2;
                        } else if (i5 == 4) {
                            i2 = ((int[]) obj).length;
                            i3 = i2 * 4;
                        } else if (i5 != 5) {
                            i2 = 0;
                            if (i4 < i2) {
                                i4 = i2;
                            }
                        } else {
                            i2 = ((long[]) obj).length;
                            i3 = i2 * 8;
                        }
                        size += i3;
                        if (i4 < i2) {
                        }
                    } else {
                        i2 = ((byte[]) obj).length;
                    }
                    size += i2;
                    if (i4 < i2) {
                    }
                }
                this.mLengthSize = getSizeOf(i4);
                this.mOffsetSize = getSizeOf(size + (this.mLengthSize * list.size()));
            }
            int size2 = this.mOffsetSize * list.size();
            if (dataOutput != null) {
                int i6 = size2;
                for (Object obj2 : list) {
                    writeAccordingToSize(dataOutput, this.mOffsetSize, (long) i6);
                    int i7 = AnonymousClass1.$SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[this.mType.ordinal()];
                    if (i7 == 1) {
                        b2 = this.mLengthSize;
                        i = ((String) obj2).getBytes().length;
                    } else if (i7 == 2) {
                        b2 = this.mLengthSize;
                        i = ((byte[]) obj2).length;
                    } else if (i7 == 3) {
                        b2 = this.mLengthSize;
                        i = ((short[]) obj2).length * 2;
                    } else if (i7 == 4) {
                        b2 = this.mLengthSize;
                        i = ((int[]) obj2).length * 4;
                    } else if (i7 == 5) {
                        b2 = this.mLengthSize;
                        i = ((long[]) obj2).length * 8;
                    }
                    i6 += b2 + i;
                }
            }
            return size2;
        }
    }

    /* access modifiers changed from: private */
    public static class DescriptionPair {
        private long mDataItemDescriptionOffset;
        private long mIndexGroupDescriptionOffset;

        private DescriptionPair(long j, long j2) {
            this.mIndexGroupDescriptionOffset = j;
            this.mDataItemDescriptionOffset = j2;
        }

        /* synthetic */ DescriptionPair(long j, long j2, AnonymousClass1 r5) {
            this(j, j2);
        }

        /* access modifiers changed from: private */
        public static DescriptionPair read(DataInput dataInput) throws IOException {
            return new DescriptionPair(dataInput.readLong(), dataInput.readLong());
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private int write(DataOutput dataOutput) throws IOException {
            if (dataOutput == null) {
                return 16;
            }
            dataOutput.writeLong(this.mIndexGroupDescriptionOffset);
            dataOutput.writeLong(this.mDataItemDescriptionOffset);
            return 16;
        }
    }

    /* access modifiers changed from: private */
    public static class FileHeader {
        private static final int CURRENT_VERSION = 2;
        private static final byte[] FILE_TAG = {73, 68, 70, 32};
        private int mDataVersion;
        private DescriptionPair[] mDescriptionOffsets;

        private FileHeader(int i, int i2) {
            this.mDescriptionOffsets = new DescriptionPair[i];
            this.mDataVersion = i2;
        }

        /* synthetic */ FileHeader(int i, int i2, AnonymousClass1 r3) {
            this(i, i2);
        }

        /* access modifiers changed from: private */
        public static FileHeader read(DataInput dataInput) throws IOException {
            byte[] bArr = new byte[FILE_TAG.length];
            for (int i = 0; i < bArr.length; i++) {
                bArr[i] = dataInput.readByte();
            }
            if (!Arrays.equals(bArr, FILE_TAG)) {
                throw new IOException("File tag unmatched, file may be corrupt");
            } else if (dataInput.readInt() == 2) {
                int readInt = dataInput.readInt();
                FileHeader fileHeader = new FileHeader(readInt, dataInput.readInt());
                for (int i2 = 0; i2 < readInt; i2++) {
                    fileHeader.mDescriptionOffsets[i2] = DescriptionPair.read(dataInput);
                }
                return fileHeader;
            } else {
                throw new IOException("File version unmatched, please upgrade your reader");
            }
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private int write(DataOutput dataOutput) throws IOException {
            byte[] bArr = FILE_TAG;
            int length = bArr.length + 4 + 4 + 4;
            if (dataOutput != null) {
                dataOutput.write(bArr);
                dataOutput.writeInt(2);
                dataOutput.writeInt(this.mDescriptionOffsets.length);
                dataOutput.writeInt(this.mDataVersion);
            }
            for (DescriptionPair descriptionPair : this.mDescriptionOffsets) {
                length += descriptionPair.write(dataOutput);
            }
            return length;
        }
    }

    /* access modifiers changed from: private */
    public static class IndexGroupDescriptor {
        int mMaxIndex;
        int mMinIndex;
        long mOffset;

        private IndexGroupDescriptor(int i, int i2, long j) {
            this.mMinIndex = i;
            this.mMaxIndex = i2;
            this.mOffset = j;
        }

        /* synthetic */ IndexGroupDescriptor(int i, int i2, long j, AnonymousClass1 r5) {
            this(i, i2, j);
        }

        /* access modifiers changed from: private */
        public static IndexGroupDescriptor read(DataInput dataInput) throws IOException {
            return new IndexGroupDescriptor(dataInput.readInt(), dataInput.readInt(), dataInput.readLong());
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private int write(DataOutput dataOutput) throws IOException {
            if (dataOutput == null) {
                return 16;
            }
            dataOutput.writeInt(this.mMinIndex);
            dataOutput.writeInt(this.mMaxIndex);
            dataOutput.writeLong(this.mOffset);
            return 16;
        }
    }

    /* access modifiers changed from: private */
    public interface InputFile extends DataInput {
        void close() throws IOException;

        long getFilePointer() throws IOException;

        void seek(long j) throws IOException;
    }

    public static class Reader {
        private InputFile mFile;
        private FileHeader mHeader;
        private IndexData[] mIndexData;

        /* access modifiers changed from: private */
        public static class IndexData {
            private DataItemDescriptor[] mDataItemDescriptions;
            private Object[][] mDataItems;
            private IndexGroupDescriptor[] mIndexGroupDescriptions;
            private int mSizeOfItems;

            private IndexData() {
            }

            /* synthetic */ IndexData(AnonymousClass1 r1) {
                this();
            }

            static /* synthetic */ int access$912(IndexData indexData, int i) {
                int i2 = indexData.mSizeOfItems + i;
                indexData.mSizeOfItems = i2;
                return i2;
            }
        }

        private Reader(InputStream inputStream) throws IOException {
            this.mFile = new DataInputStream(inputStream);
            constructFromFile("assets");
        }

        /* synthetic */ Reader(InputStream inputStream, AnonymousClass1 r2) throws IOException {
            this(inputStream);
        }

        private Reader(String str) throws IOException {
            this.mFile = new DataInputRandom(new RandomAccessFile(str, "r"));
            constructFromFile(str);
        }

        /* synthetic */ Reader(String str, AnonymousClass1 r2) throws IOException {
            this(str);
        }

        private void constructFromFile(String str) throws IOException {
            System.currentTimeMillis();
            try {
                this.mFile.seek(0);
                this.mHeader = FileHeader.read(this.mFile);
                this.mIndexData = new IndexData[this.mHeader.mDescriptionOffsets.length];
                for (int i = 0; i < this.mHeader.mDescriptionOffsets.length; i++) {
                    this.mIndexData[i] = new IndexData(null);
                    this.mFile.seek(this.mHeader.mDescriptionOffsets[i].mIndexGroupDescriptionOffset);
                    int readInt = this.mFile.readInt();
                    this.mIndexData[i].mIndexGroupDescriptions = new IndexGroupDescriptor[readInt];
                    for (int i2 = 0; i2 < readInt; i2++) {
                        this.mIndexData[i].mIndexGroupDescriptions[i2] = IndexGroupDescriptor.read(this.mFile);
                    }
                    this.mFile.seek(this.mHeader.mDescriptionOffsets[i].mDataItemDescriptionOffset);
                    int readInt2 = this.mFile.readInt();
                    this.mIndexData[i].mSizeOfItems = 0;
                    this.mIndexData[i].mDataItemDescriptions = new DataItemDescriptor[readInt2];
                    for (int i3 = 0; i3 < readInt2; i3++) {
                        this.mIndexData[i].mDataItemDescriptions[i3] = DataItemDescriptor.read(this.mFile);
                        IndexData.access$912(this.mIndexData[i], this.mIndexData[i].mDataItemDescriptions[i3].mIndexSize);
                    }
                    this.mIndexData[i].mDataItems = new Object[readInt2][];
                    for (int i4 = 0; i4 < readInt2; i4++) {
                        this.mFile.seek(this.mIndexData[i].mDataItemDescriptions[i4].mOffset);
                        this.mIndexData[i].mDataItems[i4] = this.mIndexData[i].mDataItemDescriptions[i4].readDataItems(this.mFile);
                    }
                }
            } catch (IOException e2) {
                close();
                throw e2;
            }
        }

        private long offset(int i, int i2) {
            IndexGroupDescriptor indexGroupDescriptor;
            int length = this.mIndexData[i].mIndexGroupDescriptions.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    indexGroupDescriptor = null;
                    break;
                }
                int i4 = (length + i3) / 2;
                if (this.mIndexData[i].mIndexGroupDescriptions[i4].mMinIndex <= i2) {
                    if (this.mIndexData[i].mIndexGroupDescriptions[i4].mMaxIndex > i2) {
                        indexGroupDescriptor = this.mIndexData[i].mIndexGroupDescriptions[i4];
                        break;
                    }
                    i3 = i4 + 1;
                } else {
                    length = i4;
                }
            }
            if (indexGroupDescriptor != null) {
                return indexGroupDescriptor.mOffset + ((long) ((i2 - indexGroupDescriptor.mMinIndex) * this.mIndexData[i].mSizeOfItems));
            }
            return -1;
        }

        private Object readSingleDataItem(int i, int i2, int i3) throws IOException {
            if (this.mIndexData[i].mDataItems[i2][i3] == null) {
                this.mFile.seek(this.mIndexData[i].mDataItemDescriptions[i2].mOffset + 4);
                this.mIndexData[i].mDataItems[i2][i3] = this.mIndexData[i].mDataItemDescriptions[i2].readSingleDataItem(this.mFile, i3);
            }
            return this.mIndexData[i].mDataItems[i2][i3];
        }

        public synchronized void close() {
            if (this.mFile != null) {
                try {
                    this.mFile.close();
                } catch (IOException unused) {
                }
            }
            this.mFile = null;
            this.mHeader = null;
            this.mIndexData = null;
        }

        public synchronized Object get(int i, int i2, int i3) {
            Object obj;
            if (this.mFile == null) {
                throw new IllegalStateException("Get data from a corrupt file");
            } else if (i < 0 || i >= this.mIndexData.length) {
                throw new IllegalArgumentException("Kind " + i + " out of range[0, " + this.mIndexData.length + ")");
            } else if (i3 < 0 || i3 >= this.mIndexData[i].mDataItemDescriptions.length) {
                throw new IllegalArgumentException("DataIndex " + i3 + " out of range[0, " + this.mIndexData[i].mDataItemDescriptions.length + ")");
            } else {
                System.currentTimeMillis();
                long offset = offset(i, i2);
                Object obj2 = null;
                if (offset < 0) {
                    obj = this.mIndexData[i].mDataItems[i3][0];
                } else {
                    try {
                        this.mFile.seek(offset);
                        for (int i4 = 0; i4 <= i3; i4++) {
                            switch (AnonymousClass1.$SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[this.mIndexData[i].mDataItemDescriptions[i4].mType.ordinal()]) {
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                    try {
                                        int readAccordingToSize = (int) DataItemDescriptor.readAccordingToSize(this.mFile, this.mIndexData[i].mDataItemDescriptions[i4].mIndexSize);
                                        if (i4 == i3) {
                                            obj2 = readSingleDataItem(i, i3, readAccordingToSize);
                                            break;
                                        } else {
                                            break;
                                        }
                                    } catch (IOException e2) {
                                        throw new IllegalStateException("File may be corrupt due to invalid data index size", e2);
                                    }
                                case 6:
                                    obj2 = Byte.valueOf(this.mFile.readByte());
                                    break;
                                case 7:
                                    obj2 = Short.valueOf(this.mFile.readShort());
                                    break;
                                case 8:
                                    obj2 = Integer.valueOf(this.mFile.readInt());
                                    break;
                                case 9:
                                    obj2 = Long.valueOf(this.mFile.readLong());
                                    break;
                                default:
                                    throw new IllegalStateException("Unknown type " + this.mIndexData[i].mDataItemDescriptions[i4].mType);
                            }
                        }
                        obj = obj2;
                    } catch (IOException e3) {
                        throw new IllegalStateException("Seek data from a corrupt file", e3);
                    }
                }
            }
            return obj;
        }

        public synchronized Object[] get(int i, int i2) {
            if (this.mFile == null) {
                throw new IllegalStateException("Get data from a corrupt file");
            } else if (i < 0 || i >= this.mIndexData.length) {
                throw new IllegalArgumentException("Cannot get data kind " + i);
            } else {
                System.currentTimeMillis();
                long offset = offset(i, i2);
                Object[] objArr = new Object[this.mIndexData[i].mDataItemDescriptions.length];
                if (offset < 0) {
                    for (int i3 = 0; i3 < objArr.length; i3++) {
                        objArr[i3] = this.mIndexData[i].mDataItems[i3][0];
                    }
                    return objArr;
                }
                try {
                    this.mFile.seek(offset);
                    for (int i4 = 0; i4 < objArr.length; i4++) {
                        switch (AnonymousClass1.$SwitchMap$miui$util$DirectIndexedFile$DataItemDescriptor$Type[this.mIndexData[i].mDataItemDescriptions[i4].mType.ordinal()]) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                                try {
                                    long filePointer = this.mFile.getFilePointer();
                                    objArr[i4] = readSingleDataItem(i, i4, (int) DataItemDescriptor.readAccordingToSize(this.mFile, this.mIndexData[i].mDataItemDescriptions[i4].mIndexSize));
                                    this.mFile.seek(filePointer);
                                    break;
                                } catch (IOException e2) {
                                    throw new IllegalStateException("File may be corrupt due to invalid data index size", e2);
                                }
                            case 6:
                                objArr[i4] = Byte.valueOf(this.mFile.readByte());
                                break;
                            case 7:
                                objArr[i4] = Short.valueOf(this.mFile.readShort());
                                break;
                            case 8:
                                objArr[i4] = Integer.valueOf(this.mFile.readInt());
                                break;
                            case 9:
                                objArr[i4] = Long.valueOf(this.mFile.readLong());
                                break;
                            default:
                                throw new IllegalStateException("Unknown type " + this.mIndexData[i].mDataItemDescriptions[i4].mType);
                        }
                    }
                    return objArr;
                } catch (IOException e3) {
                    throw new IllegalStateException("Seek data from a corrupt file", e3);
                }
            }
        }

        public int getDataVersion() {
            FileHeader fileHeader = this.mHeader;
            if (fileHeader == null) {
                return -1;
            }
            return fileHeader.mDataVersion;
        }
    }

    protected DirectIndexedFile() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static Builder build(int i) {
        return new Builder(i, null);
    }

    public static Reader open(InputStream inputStream) throws IOException {
        return new Reader(inputStream, (AnonymousClass1) null);
    }

    public static Reader open(String str) throws IOException {
        return new Reader(str, (AnonymousClass1) null);
    }
}
