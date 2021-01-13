package com.android.camera2.vendortag.struct;

import com.android.camera.log.Log;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.Serializable;
import java.util.Arrays;

public class HdrEvValue implements Serializable {
    public static final int HDR_TYPE_HDR = 0;
    public static final int HDR_TYPE_LLHDR = 1;
    private static final byte[] NEW_HDR_TYPE = {104, 100, 114, 32};
    public static final String TAG = "HdrEvValue";
    private static final long serialVersionUID = 1;
    private int[] mHdrCheckerEvValue;
    private int mHdrType;
    private int mSequenceNum;

    public HdrEvValue(byte[] bArr) {
        this(bArr, false);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: byte[] */
    /* JADX DEBUG: Multi-variable search result rejected for r1v1, resolved type: byte */
    /* JADX DEBUG: Multi-variable search result rejected for r7v2, resolved type: byte */
    /* JADX DEBUG: Multi-variable search result rejected for r7v6, resolved type: int[] */
    /* JADX DEBUG: Multi-variable search result rejected for r3v2, resolved type: byte */
    /* JADX DEBUG: Multi-variable search result rejected for r7v11, resolved type: byte */
    /* JADX WARN: Multi-variable type inference failed */
    public HdrEvValue(byte[] bArr, boolean z) {
        int i = 0;
        this.mHdrType = 0;
        if (bArr != 0 && bArr.length >= 1 && bArr[0] != 0) {
            int i2 = 8;
            if (bArr.length <= 8 || !isNewHdrFormat(bArr)) {
                i2 = 0;
            } else {
                Log.d(TAG, "HdrEvValue is new version");
                if (bArr[4] == 1) {
                    this.mHdrType = 1;
                }
            }
            this.mSequenceNum = bArr[i2];
            this.mHdrCheckerEvValue = new int[this.mSequenceNum];
            while (i < this.mSequenceNum) {
                int i3 = i + 1;
                this.mHdrCheckerEvValue[i] = bArr[(i3 * 4) + i2];
                Log.d(TAG, "HdrEvValue: evValue[" + i + "]=" + this.mHdrCheckerEvValue[i]);
                i = i3;
            }
        } else if (!z) {
            this.mSequenceNum = 3;
            this.mHdrCheckerEvValue = new int[]{-6, 0, 6};
        } else {
            this.mSequenceNum = 12;
            this.mHdrCheckerEvValue = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -6, -12};
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0023, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0028, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0029, code lost:
        r2.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002c, code lost:
        throw r1;
     */
    private static boolean isNewHdrFormat(byte[] bArr) {
        try {
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
            byte[] bArr2 = new byte[4];
            dataInputStream.read(bArr2);
            if (Arrays.equals(bArr2, NEW_HDR_TYPE)) {
                dataInputStream.close();
                return true;
            }
            dataInputStream.close();
            return false;
        } catch (Exception e2) {
            android.util.Log.d(TAG, "Exception parsing hdrCheckerValues type box.", e2);
            return false;
        }
    }

    public int[] getHdrCheckerEvValue() {
        return this.mHdrCheckerEvValue;
    }

    public int getHdrType() {
        return this.mHdrType;
    }

    public int getSequenceNum() {
        return this.mSequenceNum;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(16);
        int[] iArr = this.mHdrCheckerEvValue;
        if (iArr != null && iArr.length > 0) {
            sb.append("[");
            int i = 0;
            while (true) {
                int[] iArr2 = this.mHdrCheckerEvValue;
                if (i >= iArr2.length) {
                    break;
                }
                sb.append(iArr2[i]);
                if (i != this.mHdrCheckerEvValue.length - 1) {
                    sb.append(",");
                }
                i++;
            }
            sb.append("]");
        }
        return sb.toString();
    }
}
