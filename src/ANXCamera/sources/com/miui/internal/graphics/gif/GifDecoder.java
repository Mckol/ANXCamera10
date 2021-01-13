package com.miui.internal.graphics.gif;

import android.graphics.Bitmap;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.core.view.ViewCompat;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Vector;

public class GifDecoder {
    public static final int MAX_DECODE_SIZE = 1048576;
    protected static final int MAX_STACK_SIZE = 4096;
    public static final int STATUS_DECODE_CANCEL = 3;
    public static final int STATUS_FORMAT_ERROR = 1;
    public static final int STATUS_OK = 0;
    public static final int STATUS_OPEN_ERROR = 2;
    protected int[] act;
    protected int bgColor;
    protected int bgIndex;
    protected byte[] block = new byte[256];
    protected int blockSize = 0;
    private boolean calledOnce = false;
    protected int delay = 0;
    private int[] dest;
    protected int dispose = 0;
    protected Vector<GifFrame> frames;
    protected int[] gct;
    protected boolean gctFlag;
    protected int gctSize;
    private int height;
    protected int ih;
    protected Bitmap image;
    protected BufferedInputStream in;
    protected boolean interlace;
    protected int iw;
    protected int ix;
    protected int iy;
    protected int lastBgColor;
    protected Bitmap lastBitmap;
    protected int lastDispose = 0;
    protected int[] lct;
    protected boolean lctFlag;
    protected int lctSize;
    protected int loopCount = 1;
    protected int lrh;
    protected int lrw;
    protected int lrx;
    protected int lry;
    private boolean mCancel = false;
    private long mDecodeBmSize;
    private boolean mDecodeToTheEnd;
    private int mDecodedFrames;
    private long mMaxDecodeSize = PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
    private int mStartFrame;
    protected int pixelAspect;
    protected byte[] pixelStack;
    protected byte[] pixels;
    protected short[] prefix;
    protected int status;
    protected byte[] suffix;
    protected int transIndex;
    protected boolean transparency = false;
    private int width;

    /* access modifiers changed from: private */
    public static class GifFrame {
        public int delay;
        public Bitmap image;

        public GifFrame(Bitmap bitmap, int i) {
            this.image = bitmap;
            this.delay = i;
        }

        public void recycle() {
            Bitmap bitmap = this.image;
            if (bitmap != null && !bitmap.isRecycled()) {
                this.image.recycle();
            }
        }
    }

    public static boolean isGifStream(InputStream inputStream) {
        int readOneByte;
        if (inputStream == null) {
            return false;
        }
        String str = "";
        for (int i = 0; i < 6 && (readOneByte = readOneByte(inputStream)) != -1; i++) {
            str = str + ((char) readOneByte);
        }
        return str.startsWith("GIF");
    }

    protected static int readOneByte(InputStream inputStream) {
        try {
            return inputStream.read();
        } catch (Exception unused) {
            return -1;
        }
    }

    private void requestCancel() {
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:64:0x00bf */
    /* JADX DEBUG: Multi-variable search result rejected for r4v10, resolved type: short[] */
    /* JADX DEBUG: Multi-variable search result rejected for r2v19, resolved type: short */
    /* JADX WARN: Multi-variable type inference failed */
    /* access modifiers changed from: protected */
    public void decodeBitmapData() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        short s;
        int i7 = this.iw * this.ih;
        byte[] bArr = this.pixels;
        if (bArr == null || bArr.length < i7) {
            this.pixels = new byte[i7];
        }
        if (this.prefix == null) {
            this.prefix = new short[4096];
        }
        if (this.suffix == null) {
            this.suffix = new byte[4096];
        }
        if (this.pixelStack == null) {
            this.pixelStack = new byte[4097];
        }
        int read = read();
        int i8 = 1 << read;
        int i9 = i8 + 1;
        int i10 = i8 + 2;
        int i11 = read + 1;
        int i12 = (1 << i11) - 1;
        for (int i13 = 0; i13 < i8; i13++) {
            this.prefix[i13] = 0;
            this.suffix[i13] = (byte) i13;
        }
        int i14 = i11;
        int i15 = i12;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        int i20 = 0;
        int i21 = 0;
        int i22 = 0;
        int i23 = 0;
        int i24 = -1;
        int i25 = i10;
        while (i16 < i7) {
            if (i17 != 0) {
                i2 = i11;
                i = i9;
                i3 = i8;
                i4 = i22;
            } else if (i18 >= i14) {
                int i26 = i19 & i15;
                i19 >>= i14;
                i18 -= i14;
                if (i26 > i25 || i26 == i9) {
                    break;
                } else if (i26 == i8) {
                    i14 = i11;
                    i25 = i10;
                    i15 = i12;
                    i24 = -1;
                } else if (i24 == -1) {
                    this.pixelStack[i17] = this.suffix[i26];
                    i24 = i26;
                    i22 = i24;
                    i17++;
                    i11 = i11;
                } else {
                    i2 = i11;
                    if (i26 == i25) {
                        i6 = i17 + 1;
                        i5 = i26;
                        this.pixelStack[i17] = (byte) i22;
                        s = i24;
                    } else {
                        i5 = i26;
                        i6 = i17;
                        s = i5;
                    }
                    while (s > i8) {
                        this.pixelStack[i6] = this.suffix[s];
                        s = this.prefix[s];
                        i6++;
                        i8 = i8;
                    }
                    i3 = i8;
                    byte[] bArr2 = this.suffix;
                    i4 = bArr2[s] & 255;
                    if (i25 >= 4096) {
                        break;
                    }
                    i17 = i6 + 1;
                    i = i9;
                    byte b2 = (byte) i4;
                    this.pixelStack[i6] = b2;
                    this.prefix[i25] = (short) i24;
                    bArr2[i25] = b2;
                    i25++;
                    if ((i25 & i15) == 0 && i25 < 4096) {
                        i14++;
                        i15 += i25;
                    }
                    i24 = i5;
                }
            } else {
                if (i20 == 0) {
                    i20 = readBlock();
                    if (i20 <= 0) {
                        break;
                    }
                    i21 = 0;
                }
                i19 += (this.block[i21] & 255) << i18;
                i18 += 8;
                i21++;
                i20--;
            }
            i17--;
            this.pixels[i23] = this.pixelStack[i17];
            i16++;
            i23++;
            i8 = i3;
            i9 = i;
            i22 = i4;
            i11 = i2;
        }
        for (int i27 = i23; i27 < i7; i27++) {
            this.pixels[i27] = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean err() {
        return this.status != 0;
    }

    public Bitmap getBitmap() {
        return getFrame(0);
    }

    public int getDelay(int i) {
        this.delay = -1;
        int frameCount = getFrameCount();
        if (i >= 0 && i < frameCount) {
            this.delay = this.frames.elementAt(i).delay;
        }
        return this.delay;
    }

    public Bitmap getFrame(int i) {
        int frameCount = getFrameCount();
        if (frameCount <= 0) {
            return null;
        }
        return this.frames.elementAt(i % frameCount).image;
    }

    public int getFrameCount() {
        Vector<GifFrame> vector = this.frames;
        if (vector == null) {
            return 0;
        }
        return vector.size();
    }

    public int getHeight() {
        return this.height;
    }

    public int getLoopCount() {
        return this.loopCount;
    }

    public int getRealFrameCount() {
        if (this.mDecodeToTheEnd) {
            return this.mDecodedFrames;
        }
        return 0;
    }

    public int getWidth() {
        return this.width;
    }

    /* access modifiers changed from: protected */
    public void init() {
        this.status = 0;
        this.frames = new Vector<>();
        this.gct = null;
        this.lct = null;
    }

    public boolean isDecodeToTheEnd() {
        return this.mDecodeToTheEnd;
    }

    /* access modifiers changed from: protected */
    public int read() {
        try {
            return this.in.read();
        } catch (Exception unused) {
            this.status = 1;
            return 0;
        }
    }

    public int read(InputStream inputStream) {
        this.mDecodeToTheEnd = false;
        if (!this.calledOnce) {
            this.calledOnce = true;
            init();
            if (inputStream != null) {
                this.in = new BufferedInputStream(inputStream);
                try {
                    readHeader();
                    if (!this.mCancel && !err()) {
                        readContents();
                        if (getFrameCount() < 0) {
                            this.status = 1;
                        }
                    }
                } catch (OutOfMemoryError unused) {
                    this.status = 2;
                    recycle();
                }
            } else {
                this.status = 2;
            }
            if (this.mCancel) {
                recycle();
                this.status = 3;
            }
            return this.status;
        }
        throw new IllegalStateException("decoder cannot be called more than once");
    }

    /* access modifiers changed from: protected */
    public void readBitmap() {
        this.ix = readShort();
        this.iy = readShort();
        this.iw = readShort();
        this.ih = readShort();
        int read = read();
        int i = 0;
        this.lctFlag = (read & 128) != 0;
        this.lctSize = 2 << (read & 7);
        this.interlace = (read & 64) != 0;
        if (this.lctFlag) {
            this.lct = readColorTable(this.lctSize);
            this.act = this.lct;
        } else {
            this.act = this.gct;
            if (this.bgIndex == this.transIndex) {
                this.bgColor = 0;
            }
        }
        if (this.transparency) {
            int[] iArr = this.act;
            int i2 = this.transIndex;
            int i3 = iArr[i2];
            iArr[i2] = 0;
            i = i3;
        }
        if (this.act == null) {
            this.status = 1;
        }
        if (!err()) {
            decodeBitmapData();
            skip();
            if (!err() && !this.mCancel) {
                setPixels();
                if (this.mDecodedFrames >= this.mStartFrame) {
                    this.frames.addElement(new GifFrame(this.image, this.delay));
                }
                this.mDecodedFrames++;
                if (this.transparency) {
                    this.act[this.transIndex] = i;
                }
                resetFrame();
            }
        }
    }

    /* access modifiers changed from: protected */
    public int readBlock() {
        this.blockSize = read();
        int i = 0;
        if (this.blockSize > 0) {
            while (i < this.blockSize) {
                try {
                    int read = this.in.read(this.block, i, this.blockSize - i);
                    if (read == -1) {
                        break;
                    }
                    i += read;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (i < this.blockSize) {
                this.status = 1;
            }
        }
        return i;
    }

    /* access modifiers changed from: protected */
    public int[] readColorTable(int i) {
        int i2;
        int i3 = i * 3;
        byte[] bArr = new byte[i3];
        try {
            i2 = this.in.read(bArr, 0, bArr.length);
        } catch (Exception e2) {
            e2.printStackTrace();
            i2 = 0;
        }
        if (i2 < i3) {
            this.status = 1;
            return null;
        }
        int[] iArr = new int[256];
        int i4 = 0;
        for (int i5 = 0; i5 < i; i5++) {
            int i6 = i4 + 1;
            int i7 = i6 + 1;
            iArr[i5] = ((bArr[i4] & 255) << 16) | ViewCompat.MEASURED_STATE_MASK | ((bArr[i6] & 255) << 8) | (bArr[i7] & 255);
            i4 = i7 + 1;
        }
        return iArr;
    }

    /* access modifiers changed from: protected */
    public void readContents() {
        this.mDecodedFrames = 0;
        boolean z = false;
        while (!z && !err() && !this.mCancel) {
            int read = read();
            if (read != 33) {
                if (read == 44) {
                    int size = this.frames.size();
                    readBitmap();
                    if (this.frames.size() > size) {
                        this.mDecodeBmSize += (long) (this.image.getRowBytes() * this.image.getHeight());
                    }
                    if (this.mDecodeBmSize <= this.mMaxDecodeSize) {
                    }
                } else if (read != 59) {
                    this.status = 1;
                } else {
                    this.mDecodeToTheEnd = true;
                }
                z = true;
            } else {
                int read2 = read();
                if (read2 == 1) {
                    skip();
                } else if (read2 == 249) {
                    readGraphicControlExt();
                } else if (read2 == 254) {
                    skip();
                } else if (read2 != 255) {
                    skip();
                } else {
                    readBlock();
                    String str = "";
                    for (int i = 0; i < 11; i++) {
                        str = str + ((char) this.block[i]);
                    }
                    if (str.equals("NETSCAPE2.0")) {
                        readNetscapeExt();
                    } else {
                        skip();
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void readGraphicControlExt() {
        read();
        int read = read();
        this.dispose = (read & 28) >> 2;
        boolean z = true;
        if (this.dispose == 0) {
            this.dispose = 1;
        }
        if ((read & 1) == 0) {
            z = false;
        }
        this.transparency = z;
        this.delay = readShort() * 10;
        if (this.delay <= 0) {
            this.delay = 100;
        }
        this.transIndex = read();
        read();
    }

    /* access modifiers changed from: protected */
    public void readHeader() {
        if (!this.mCancel) {
            String str = "";
            for (int i = 0; i < 6; i++) {
                str = str + ((char) read());
            }
            if (!str.startsWith("GIF")) {
                this.status = 1;
                return;
            }
            readLSD();
            if (this.gctFlag && !err()) {
                this.gct = readColorTable(this.gctSize);
                this.bgColor = this.gct[this.bgIndex];
            }
        }
    }

    /* access modifiers changed from: protected */
    public void readLSD() {
        this.width = readShort();
        this.height = readShort();
        int read = read();
        this.gctFlag = (read & 128) != 0;
        this.gctSize = 2 << (read & 7);
        this.bgIndex = read();
        this.pixelAspect = read();
    }

    /* access modifiers changed from: protected */
    public void readNetscapeExt() {
        do {
            readBlock();
            byte[] bArr = this.block;
            if (bArr[0] == 1) {
                this.loopCount = ((bArr[2] & 255) << 8) | (bArr[1] & 255);
            }
            if (this.blockSize <= 0) {
                return;
            }
        } while (!err());
    }

    /* access modifiers changed from: protected */
    public int readShort() {
        return (read() << 8) | read();
    }

    public void recycle() {
        Vector<GifFrame> vector = this.frames;
        if (vector != null) {
            int size = vector.size();
            for (int i = 0; i < size; i++) {
                this.frames.elementAt(i).recycle();
            }
        }
    }

    public void requestCancelDecode() {
        this.mCancel = true;
        requestCancel();
    }

    /* access modifiers changed from: protected */
    public void resetFrame() {
        this.lastDispose = this.dispose;
        this.lrx = this.ix;
        this.lry = this.iy;
        this.lrw = this.iw;
        this.lrh = this.ih;
        this.lastBitmap = this.image;
        this.lastBgColor = this.bgColor;
        this.dispose = 0;
        this.transparency = false;
        this.delay = 0;
        this.lct = null;
    }

    public void setMaxDecodeSize(long j) {
        this.mMaxDecodeSize = j;
    }

    /* access modifiers changed from: protected */
    public void setPixels() {
        Bitmap bitmap;
        int i;
        if (this.dest == null) {
            this.dest = new int[(this.width * this.height)];
        }
        int i2 = this.lastDispose;
        int i3 = 0;
        if (i2 > 0) {
            if (i2 == 3) {
                int frameCount = getFrameCount() - 2;
                if (frameCount > 0) {
                    Bitmap frame = getFrame(frameCount - 1);
                    if (!frame.equals(this.lastBitmap)) {
                        this.lastBitmap = frame;
                        Bitmap bitmap2 = this.lastBitmap;
                        int[] iArr = this.dest;
                        int i4 = this.width;
                        bitmap2.getPixels(iArr, 0, i4, 0, 0, i4, this.height);
                    }
                } else {
                    this.lastBitmap = null;
                    this.dest = new int[(this.width * this.height)];
                }
            }
            if (this.lastBitmap != null && this.lastDispose == 2) {
                int i5 = !this.transparency ? this.lastBgColor : 0;
                int i6 = (this.lry * this.width) + this.lrx;
                for (int i7 = 0; i7 < this.lrh; i7++) {
                    int i8 = this.lrw + i6;
                    for (int i9 = i6; i9 < i8; i9++) {
                        this.dest[i9] = i5;
                    }
                    i6 += this.width;
                }
            }
        }
        int i10 = 8;
        int i11 = 0;
        int i12 = 1;
        while (true) {
            int i13 = this.ih;
            if (i3 >= i13) {
                break;
            }
            if (this.interlace) {
                if (i11 >= i13) {
                    i12++;
                    if (i12 == 2) {
                        i11 = 4;
                    } else if (i12 == 3) {
                        i11 = 2;
                        i10 = 4;
                    } else if (i12 == 4) {
                        i10 = 2;
                        i11 = 1;
                    }
                }
                i = i11 + i10;
            } else {
                i = i11;
                i11 = i3;
            }
            int i14 = i11 + this.iy;
            if (i14 < this.height) {
                int i15 = this.width;
                int i16 = i14 * i15;
                int i17 = this.ix + i16;
                int i18 = this.iw + i17;
                if (i16 + i15 < i18) {
                    i18 = i16 + i15;
                }
                int i19 = this.iw * i3;
                while (i17 < i18) {
                    int i20 = i19 + 1;
                    int i21 = this.act[this.pixels[i19] & 255];
                    if (i21 != 0) {
                        this.dest[i17] = i21;
                    }
                    i17++;
                    i19 = i20;
                }
            }
            i3++;
            i11 = i;
        }
        if (this.mDecodedFrames <= this.mStartFrame && (bitmap = this.image) != null && !bitmap.isRecycled()) {
            this.image.recycle();
        }
        this.image = Bitmap.createBitmap(this.dest, this.width, this.height, Bitmap.Config.ARGB_8888);
    }

    public void setStartFrame(int i) {
        this.mStartFrame = i;
    }

    /* access modifiers changed from: protected */
    public void skip() {
        do {
            readBlock();
            if (this.blockSize <= 0) {
                return;
            }
        } while (!err());
    }
}
