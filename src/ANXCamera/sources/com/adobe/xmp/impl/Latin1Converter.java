package com.adobe.xmp.impl;

import java.io.UnsupportedEncodingException;

public class Latin1Converter {
    private static final int STATE_START = 0;
    private static final int STATE_UTF8CHAR = 11;

    private Latin1Converter() {
    }

    public static ByteBuffer convert(ByteBuffer byteBuffer) {
        if (!"UTF-8".equals(byteBuffer.getEncoding())) {
            return byteBuffer;
        }
        byte[] bArr = new byte[8];
        ByteBuffer byteBuffer2 = new ByteBuffer((byteBuffer.length() * 4) / 3);
        int i = 0;
        boolean z = false;
        int i2 = 0;
        int i3 = 0;
        while (i < byteBuffer.length()) {
            int charAt = byteBuffer.charAt(i);
            if (z) {
                if (i2 <= 0 || (charAt & 192) != 128) {
                    byteBuffer2.append(convertToUTF8(bArr[0]));
                    i -= i3;
                } else {
                    int i4 = i3 + 1;
                    bArr[i3] = (byte) charAt;
                    i2--;
                    if (i2 == 0) {
                        byteBuffer2.append(bArr, 0, i4);
                    } else {
                        i3 = i4;
                    }
                }
                z = false;
                i3 = 0;
            } else if (charAt < 127) {
                byteBuffer2.append((byte) charAt);
            } else if (charAt >= 192) {
                int i5 = -1;
                int i6 = charAt;
                while (i5 < 8 && (i6 & 128) == 128) {
                    i5++;
                    i6 <<= 1;
                }
                bArr[i3] = (byte) charAt;
                i3++;
                i2 = i5;
                z = true;
            } else {
                byteBuffer2.append(convertToUTF8((byte) charAt));
            }
            i++;
        }
        if (z) {
            for (int i7 = 0; i7 < i3; i7++) {
                byteBuffer2.append(convertToUTF8(bArr[i7]));
            }
        }
        return byteBuffer2;
    }

    private static byte[] convertToUTF8(byte b2) {
        int i = b2 & 255;
        if (i >= 128) {
            if (i == 129 || i == 141 || i == 143 || i == 144 || i == 157) {
                return new byte[]{32};
            }
            try {
                return new String(new byte[]{b2}, "cp1252").getBytes("UTF-8");
            } catch (UnsupportedEncodingException unused) {
            }
        }
        return new byte[]{b2};
    }
}
