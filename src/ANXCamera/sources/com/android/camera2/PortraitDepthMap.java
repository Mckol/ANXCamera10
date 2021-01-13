package com.android.camera2;

import android.graphics.Point;
import android.util.Xml;
import b.c.a.c;
import b.c.a.g;
import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMeta;
import com.android.camera.Util;
import com.android.camera.XmpHelper;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.gallery3d.exif.ExifInterface;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import miui.telephony.phonenumber.TelocationProvider;
import org.xmlpull.v1.XmlSerializer;

public class PortraitDepthMap {
    private static final int AF_CODE_START_ADDR = 156;
    private static final int BLUR_LEVEL_START_ADDR = 16;
    private static final int DAPTH_MAP_START_ADDR = 152;
    private static final int DATA_LENGTH_4 = 4;
    private static final int DATA_LENGTH_START_ADDR = 148;
    private static final int HEADER_LENGTH_START_ADDR = 4;
    private static final int HEADER_START_ADDR = 0;
    private static final int MI_BOKEH_DAPTH_MAP_START_ADDR = 168;
    private static final int POINT_X_START_ADDR = 8;
    private static final int POINT_Y_START_ADDR = 12;
    private static final int SCENE_TAG = 160;
    private static final int SCENE_TAG_CONFIDENCE = 164;
    private static final String TAG = "PortraitDepthMap";
    @Deprecated
    public static final int TAG_DEPTH_MAP_BLUR_LEVEL = ExifInterface.defineTag(2, -30575);
    @Deprecated
    public static final int TAG_DEPTH_MAP_FOCUS_POINT = ExifInterface.defineTag(2, -30576);
    private static final int TOF_START_ADDR = 152;
    private byte[] mDepthMapHeader;
    private byte[] mDepthMapOriginalData;

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th != null) {
            try {
                autoCloseable.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        } else {
            autoCloseable.close();
        }
    }

    public PortraitDepthMap(byte[] bArr) {
        if (bArr != null) {
            int headerTag = getHeaderTag(bArr);
            if (headerTag == 128) {
                this.mDepthMapOriginalData = bArr;
                this.mDepthMapHeader = getDepthMapHeader();
                return;
            }
            throw new IllegalArgumentException("Illegal depth format! 0x80 != " + headerTag);
        }
        throw new IllegalArgumentException("Null depth data!");
    }

    private static byte[] getBytes(byte[] bArr, int i, int i2) {
        if (i2 <= 0 || i < 0 || i2 > bArr.length - i) {
            throw new IllegalArgumentException("WRONG ARGUMENT: from =" + i + ", length = " + i2);
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    private static int getHeaderTag(byte[] bArr) {
        return getInteger(getBytes(bArr, 0, 4));
    }

    private static int getInteger(byte[] bArr) {
        if (bArr.length == 4) {
            int i = 0;
            for (int i2 = 0; i2 < 4; i2++) {
                i += (bArr[i2] & 255) << (i2 * 8);
            }
            return i;
        }
        throw new IllegalArgumentException("bytes can not covert to a integer value!");
    }

    public static boolean isDepthMapData(byte[] bArr) {
        boolean z = bArr != null && bArr.length > 4 && getHeaderTag(bArr) == 128;
        if (!z) {
            Log.e(TAG, "Illegal depthmap format");
        }
        return z;
    }

    public int getAfCode() {
        return getInteger(getBytes(this.mDepthMapHeader, 156, 4));
    }

    public int getBlurLevel() {
        return getInteger(getBytes(this.mDepthMapHeader, 16, 4));
    }

    public byte[] getDepthMapData(boolean z) {
        return z ? getBytes(this.mDepthMapOriginalData, 168, getDepthMapLength()) : getBytes(this.mDepthMapOriginalData, 152, getDepthMapLength());
    }

    public byte[] getDepthMapHeader() {
        return getBytes(this.mDepthMapOriginalData, 0, getInteger(getBytes(this.mDepthMapOriginalData, 4, 4)));
    }

    public int getDepthMapLength() {
        return getInteger(getBytes(this.mDepthMapHeader, 148, 4));
    }

    public Point getFocusPoint() {
        return new Point(getInteger(getBytes(this.mDepthMapHeader, 8, 4)), getInteger(getBytes(this.mDepthMapHeader, 12, 4)));
    }

    public int getSceneTag() {
        return getInteger(getBytes(this.mDepthMapHeader, 160, 4));
    }

    public int getSceneTagConfidence() {
        return getInteger(getBytes(this.mDepthMapHeader, 164, 4));
    }

    public int getTof() {
        return getInteger(getBytes(this.mDepthMapHeader, 152, 4));
    }

    public int getVendor() {
        return 1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0215, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0216, code lost:
        $closeResource(r0, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x021b, code lost:
        throw r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x068c  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0698 A[SYNTHETIC, Splitter:B:123:0x0698] */
    /* JADX WARNING: Removed duplicated region for block: B:191:0x074f  */
    public byte[] writePortraitExif(PortraitDepthMapExif portraitDepthMapExif) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        byte[] bArr;
        int i6;
        File file;
        long j;
        byte[] bArr2;
        File file2;
        long j2;
        int i7;
        int i8;
        int i9;
        int i10;
        long j3;
        int i11;
        int i12;
        File file3;
        String str;
        byte[] bArr3;
        byte[] bArr4;
        Throwable th;
        byte[] bArr5;
        Throwable th2;
        Throwable th3;
        Point focusPoint = getFocusPoint();
        int blurLevel = getBlurLevel();
        int portraitLightingVersioin = portraitDepthMapExif.getPortraitLightingVersioin();
        int cameraPreferredMode = portraitDepthMapExif.getCameraPreferredMode();
        boolean isFrontCamera = portraitDepthMapExif.getPictureInfo().isFrontCamera();
        int zh = portraitDepthMapExif.isSupportZeroDegreeOrientationImage() ? 2 : DataRepository.dataItemFeature().zh();
        int i13 = -1;
        if (zh > 0) {
            int i14 = isFrontCamera ? (!portraitDepthMapExif.getPictureInfo().isAiEnabled() || portraitDepthMapExif.getPictureInfo().getAiType() != 10) ? 40 : 70 : (!portraitDepthMapExif.getPictureInfo().isAiEnabled() || portraitDepthMapExif.getPictureInfo().getAiType() != 10) ? 10 : 30;
            i5 = 5;
            if (portraitDepthMapExif.isSupportMiDualBokeh()) {
                int tof = getTof();
                int afCode = getAfCode();
                int sceneTag = getSceneTag();
                i = getSceneTagConfidence();
                i2 = sceneTag;
                i3 = afCode;
                i4 = tof;
            } else {
                i4 = -1;
                i3 = -1;
                i2 = -1;
                i = -1;
            }
            i13 = i14;
        } else {
            i5 = -1;
            i4 = -1;
            i3 = -1;
            i2 = -1;
            i = -1;
        }
        int vendor = zh > 1 ? getVendor() : 1;
        Log.d(TAG, "writePortraitExif: focusPoint: " + focusPoint);
        Log.d(TAG, "writePortraitExif: blurLevel: " + blurLevel);
        Log.d(TAG, "writePortraitExif: shineThreshold: " + i5);
        Log.d(TAG, "writePortraitExif: shineLevel: " + i13);
        Log.d(TAG, "writePortraitExif: lightingPattern: " + portraitDepthMapExif.getLightingPattern());
        Log.d(TAG, "writePortraitExif: isCinematicAspectRatio: " + portraitDepthMapExif.isCinematicAspectRatio());
        Log.d(TAG, "writePortraitExif: rotation: " + portraitDepthMapExif.getRotation());
        Log.d(TAG, "writePortraitExif: vendor: " + vendor);
        Log.d(TAG, "writePortraitExif: portraitLightingVersion: " + portraitLightingVersioin);
        Log.d(TAG, "writePortraitExif: tof: " + i4);
        Log.d(TAG, "writePortraitExif: afCode: " + i3);
        Log.d(TAG, "writePortraitExif: sceneTag: " + i2);
        Log.d(TAG, "writePortraitExif: sceneTagConfidence: " + i);
        Log.d(TAG, "writePortraitExif: cameraPreferredMode: " + cameraPreferredMode);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ExifInterface exifInterface = new ExifInterface();
            exifInterface.readExif(portraitDepthMapExif.getJpeg());
            exifInterface.addXiaomiDepthmapVersion(zh);
            exifInterface.addDepthMapBlurLevel(blurLevel);
            exifInterface.addPortraitLighting(portraitDepthMapExif.getLightingPattern());
            if (portraitDepthMapExif.isBokehFrontCamera()) {
                exifInterface.addFrontMirror(portraitDepthMapExif.isFrontMirror() ? 1 : 0);
            }
            exifInterface.writeExif(portraitDepthMapExif.getJpeg(), byteArrayOutputStream);
            bArr = byteArrayOutputStream.toByteArray();
            try {
                $closeResource(null, byteArrayOutputStream);
            } catch (IOException unused) {
            }
        } catch (IOException unused2) {
            bArr = null;
            Log.e(TAG, "writePortraitExif(): Failed to write depthmap associated exif metadata");
            if (bArr != null) {
            }
            Log.e(TAG, "writePortraitExif(): #1: return original jpeg");
            return portraitDepthMapExif.getJpeg();
        }
        if (bArr != null || (bArr.length <= portraitDepthMapExif.getJpeg().length && (!c.isMTKPlatform() || !DataRepository.dataItemFeature().Ol()))) {
            Log.e(TAG, "writePortraitExif(): #1: return original jpeg");
            return portraitDepthMapExif.getJpeg();
        }
        String str2 = "sdcard/DCIM/Camera/evMinusMainImage" + portraitDepthMapExif.getTimeStamp() + ".yuv";
        File file4 = new File("sdcard/DCIM/Camera/evZeroMainImage" + portraitDepthMapExif.getTimeStamp() + ".yuv");
        File file5 = new File("sdcard/DCIM/Camera/evZeroSubImage" + portraitDepthMapExif.getTimeStamp() + ".yuv");
        if (!file4.exists() || !file5.exists()) {
            file = file5;
            file2 = file4;
            bArr2 = bArr;
            i6 = i3;
            j2 = 0;
            j = 0;
            i10 = 0;
            i9 = 0;
            i8 = 0;
            i7 = 0;
        } else {
            i7 = Util.getHeader2Int(file4, 0);
            i8 = Util.getHeader2Int(file4, 4);
            j2 = file4.length() - 8;
            file2 = file4;
            bArr2 = bArr;
            i9 = Util.getHeader2Int(file5, 0);
            i10 = Util.getHeader2Int(file5, 4);
            j = file5.length() - 8;
            String str3 = TAG;
            file = file5;
            StringBuilder sb = new StringBuilder();
            i6 = i3;
            sb.append("main width = ");
            sb.append(i7);
            sb.append(", main height = ");
            sb.append(i8);
            sb.append(", sub width =");
            sb.append(i9);
            sb.append(", sub height = ");
            sb.append(i10);
            Log.d(str3, sb.toString());
        }
        File file6 = new File(str2);
        if (file6.exists()) {
            i12 = Util.getHeader2Int(file6, 0);
            i11 = Util.getHeader2Int(file6, 4);
            j3 = file6.length() - 8;
        } else {
            j3 = 0;
            i12 = 0;
            i11 = 0;
        }
        try {
            XmlSerializer newSerializer = Xml.newSerializer();
            file3 = file6;
            try {
                StringWriter stringWriter = new StringWriter();
                newSerializer.setOutput(stringWriter);
                newSerializer.startDocument("UTF-8", true);
                newSerializer.startTag(null, "depthmap");
                newSerializer.attribute(null, "version", String.valueOf(zh));
                newSerializer.attribute(null, "focuspoint", focusPoint.x + "," + focusPoint.y);
                newSerializer.attribute(null, "blurlevel", String.valueOf(blurLevel));
                newSerializer.attribute(null, "shinethreshold", String.valueOf(i5));
                newSerializer.attribute(null, "shinelevel", String.valueOf(i13));
                newSerializer.attribute(null, "rawlength", String.valueOf(portraitDepthMapExif.getRawLength()));
                newSerializer.attribute(null, "depthlength", String.valueOf(portraitDepthMapExif.getDepthLength()));
                newSerializer.attribute(null, "mimovie", String.valueOf(portraitDepthMapExif.isCinematicAspectRatio()));
                newSerializer.attribute(null, "depthOrientation", String.valueOf(portraitDepthMapExif.getRotation()));
                newSerializer.attribute(null, g.VENDOR, String.valueOf(vendor));
                newSerializer.attribute(null, "portraitLightingVersion", String.valueOf(portraitLightingVersioin));
                newSerializer.attribute(null, "tof", String.valueOf(i4));
                newSerializer.attribute(null, "afcode", String.valueOf(i6));
                newSerializer.attribute(null, "sceneTag", String.valueOf(i2));
                newSerializer.attribute(null, "sceneTagConfidence", String.valueOf(i));
                newSerializer.attribute(null, "cameraPreferredMode", String.valueOf(cameraPreferredMode));
                newSerializer.endTag(null, "depthmap");
                long j4 = j2 + j + j3;
                if (j4 != 0) {
                    newSerializer.startTag(null, "mainyuv");
                    newSerializer.attribute(null, "offset", String.valueOf(((long) (portraitDepthMapExif.getRawLength() + portraitDepthMapExif.getDepthLength())) + j4));
                    newSerializer.attribute(null, TelocationProvider.Contract.Params.LENGTH, String.valueOf(j2));
                    newSerializer.attribute(null, "width", String.valueOf(i7));
                    newSerializer.attribute(null, "height", String.valueOf(i8));
                    newSerializer.endTag(null, "mainyuv");
                    newSerializer.startTag(null, "subyuv");
                    newSerializer.attribute(null, "offset", String.valueOf(((long) (portraitDepthMapExif.getRawLength() + portraitDepthMapExif.getDepthLength())) + j + j3));
                    newSerializer.attribute(null, TelocationProvider.Contract.Params.LENGTH, String.valueOf(j));
                    newSerializer.attribute(null, "width", String.valueOf(i9));
                    newSerializer.attribute(null, "height", String.valueOf(i10));
                    newSerializer.endTag(null, "subyuv");
                }
                if (j3 != 0) {
                    newSerializer.startTag(null, "evminusyuv");
                    newSerializer.attribute(null, "offset", String.valueOf(((long) (portraitDepthMapExif.getRawLength() + portraitDepthMapExif.getDepthLength())) + j3));
                    newSerializer.attribute(null, TelocationProvider.Contract.Params.LENGTH, String.valueOf(j3));
                    newSerializer.attribute(null, "width", String.valueOf(i12));
                    newSerializer.attribute(null, "height", String.valueOf(i11));
                    newSerializer.endTag(null, "evminusyuv");
                }
                if (portraitDepthMapExif.getJpegDataOfTheRegionUnderWatermarks() != null && portraitDepthMapExif.getJpegDataOfTheRegionUnderWatermarks().length > 0 && portraitDepthMapExif.getCoordinatesOfOfTheRegionUnderWatermarks() != null && portraitDepthMapExif.getCoordinatesOfOfTheRegionUnderWatermarks().length >= 4) {
                    newSerializer.startTag(null, "subimage");
                    newSerializer.attribute(null, "offset", String.valueOf(((long) (portraitDepthMapExif.getJpegDataOfTheRegionUnderWatermarks().length + (portraitDepthMapExif.getDulWaterMark() != null ? portraitDepthMapExif.getDulWaterMark().length : 0) + (portraitDepthMapExif.getTimeWaterMark() != null ? portraitDepthMapExif.getTimeWaterMark().length : 0))) + j4 + ((long) portraitDepthMapExif.getRawLength()) + ((long) portraitDepthMapExif.getDepthLength())));
                    newSerializer.attribute(null, TelocationProvider.Contract.Params.LENGTH, String.valueOf(portraitDepthMapExif.getJpegDataOfTheRegionUnderWatermarks().length));
                    newSerializer.attribute(null, "paddingx", String.valueOf(portraitDepthMapExif.getCoordinatesOfOfTheRegionUnderWatermarks()[0]));
                    newSerializer.attribute(null, "paddingy", String.valueOf(portraitDepthMapExif.getCoordinatesOfOfTheRegionUnderWatermarks()[1]));
                    newSerializer.attribute(null, "width", String.valueOf(portraitDepthMapExif.getCoordinatesOfOfTheRegionUnderWatermarks()[2]));
                    newSerializer.attribute(null, "height", String.valueOf(portraitDepthMapExif.getCoordinatesOfOfTheRegionUnderWatermarks()[3]));
                    newSerializer.attribute(null, "rotation", String.valueOf(portraitDepthMapExif.getRotation()));
                    newSerializer.endTag(null, "subimage");
                }
                if (portraitDepthMapExif.getDulWaterMark() != null && portraitDepthMapExif.getDulWaterMark().length > 0) {
                    newSerializer.startTag(null, "lenswatermark");
                    newSerializer.attribute(null, "offset", String.valueOf(((long) (portraitDepthMapExif.getDulWaterMark().length + (portraitDepthMapExif.getTimeWaterMark() != null ? portraitDepthMapExif.getTimeWaterMark().length : 0))) + j4 + ((long) portraitDepthMapExif.getRawLength()) + ((long) portraitDepthMapExif.getDepthLength())));
                    newSerializer.attribute(null, TelocationProvider.Contract.Params.LENGTH, String.valueOf(portraitDepthMapExif.getDulWaterMark().length));
                    newSerializer.attribute(null, "width", String.valueOf(portraitDepthMapExif.getDulCameraWaterMarkLocation()[0]));
                    newSerializer.attribute(null, "height", String.valueOf(portraitDepthMapExif.getDulCameraWaterMarkLocation()[1]));
                    newSerializer.attribute(null, "paddingx", String.valueOf(portraitDepthMapExif.getDulCameraWaterMarkLocation()[2]));
                    newSerializer.attribute(null, "paddingy", String.valueOf(portraitDepthMapExif.getDulCameraWaterMarkLocation()[3]));
                    newSerializer.endTag(null, "lenswatermark");
                }
                if (portraitDepthMapExif.getTimeWaterMark() != null && portraitDepthMapExif.getTimeWaterMark().length > 0) {
                    newSerializer.startTag(null, "timewatermark");
                    newSerializer.attribute(null, "offset", String.valueOf(((long) portraitDepthMapExif.getTimeWaterMark().length) + j4 + ((long) portraitDepthMapExif.getRawLength()) + ((long) portraitDepthMapExif.getDepthLength())));
                    newSerializer.attribute(null, TelocationProvider.Contract.Params.LENGTH, String.valueOf(portraitDepthMapExif.getTimeWaterMark().length));
                    newSerializer.attribute(null, "width", String.valueOf(portraitDepthMapExif.getTimeWaterMarkLocation()[0]));
                    newSerializer.attribute(null, "height", String.valueOf(portraitDepthMapExif.getTimeWaterMarkLocation()[1]));
                    newSerializer.attribute(null, "paddingx", String.valueOf(portraitDepthMapExif.getTimeWaterMarkLocation()[2]));
                    newSerializer.attribute(null, "paddingy", String.valueOf(portraitDepthMapExif.getTimeWaterMarkLocation()[3]));
                    newSerializer.endTag(null, "timewatermark");
                }
                newSerializer.endDocument();
                str = stringWriter.toString();
            } catch (IOException unused3) {
                Log.e(TAG, "writePortraitExif(): Failed to generate depthmap associated xmp metadata");
                str = null;
                if (str == null) {
                }
            }
        } catch (IOException unused4) {
            file3 = file6;
            Log.e(TAG, "writePortraitExif(): Failed to generate depthmap associated xmp metadata");
            str = null;
            if (str == null) {
            }
        }
        if (str == null) {
            Log.e(TAG, "writePortraitExif(): #2: return original jpeg");
            return portraitDepthMapExif.getJpeg();
        }
        try {
            bArr4 = bArr2;
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr4);
                try {
                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                    try {
                        XMPMeta createXMPMeta = XmpHelper.createXMPMeta();
                        createXMPMeta.setProperty(XmpHelper.XIAOMI_XMP_METADATA_NAMESPACE, XmpHelper.XIAOMI_XMP_METADATA_PROPERTY_NAME, str);
                        XmpHelper.writeXMPMeta(byteArrayInputStream, byteArrayOutputStream2, createXMPMeta);
                        if (portraitDepthMapExif.getJpegDataOfTheRegionUnderWatermarks() != null) {
                            try {
                                if (portraitDepthMapExif.getJpegDataOfTheRegionUnderWatermarks().length > 0 && portraitDepthMapExif.getCoordinatesOfOfTheRegionUnderWatermarks() != null && portraitDepthMapExif.getCoordinatesOfOfTheRegionUnderWatermarks().length >= 4) {
                                    byteArrayOutputStream2.write(portraitDepthMapExif.getJpegDataOfTheRegionUnderWatermarks());
                                }
                            } catch (Throwable th4) {
                                th3 = th4;
                                bArr5 = null;
                                try {
                                    throw th3;
                                } catch (Throwable th5) {
                                    th2 = th5;
                                    th = th2;
                                    bArr3 = bArr5;
                                    try {
                                        throw th;
                                    } catch (XMPException | IOException unused5) {
                                        Log.d(TAG, "writePortraitExif(): Failed to insert depthmap associated xmp metadata");
                                        if (bArr3 == null) {
                                        }
                                        Log.e(TAG, "writePortraitExif(): #3: return original jpeg");
                                        return portraitDepthMapExif.getJpeg();
                                    } catch (Throwable th6) {
                                        $closeResource(th, byteArrayInputStream);
                                        throw th6;
                                    }
                                }
                            }
                        }
                        if (portraitDepthMapExif.getDulWaterMark() != null) {
                            byteArrayOutputStream2.write(portraitDepthMapExif.getDulWaterMark());
                        }
                        if (portraitDepthMapExif.getTimeWaterMark() != null) {
                            byteArrayOutputStream2.write(portraitDepthMapExif.getTimeWaterMark());
                        }
                        if (j2 != 0) {
                            Util.writeFile2Stream(file2, byteArrayOutputStream2, 8);
                            file2.delete();
                        }
                        if (j != 0) {
                            Util.writeFile2Stream(file, byteArrayOutputStream2, 8);
                            file.delete();
                        }
                        if (j3 != 0) {
                            Util.writeFile2Stream(file3, byteArrayOutputStream2, 8);
                            file3.delete();
                        }
                        byteArrayOutputStream2.flush();
                        bArr3 = byteArrayOutputStream2.toByteArray();
                    } catch (Throwable th7) {
                        bArr5 = null;
                        th3 = th7;
                        throw th3;
                    }
                    try {
                        $closeResource(null, byteArrayOutputStream2);
                        $closeResource(null, byteArrayInputStream);
                        if (bArr3 == null && bArr3.length > bArr4.length) {
                            return bArr3;
                        }
                        Log.e(TAG, "writePortraitExif(): #3: return original jpeg");
                        return portraitDepthMapExif.getJpeg();
                    } catch (Throwable th8) {
                        th = th8;
                        throw th;
                    }
                } catch (Throwable th9) {
                    th2 = th9;
                    bArr5 = null;
                    th = th2;
                    bArr3 = bArr5;
                    throw th;
                }
            } catch (XMPException | IOException unused6) {
                bArr3 = null;
                Log.d(TAG, "writePortraitExif(): Failed to insert depthmap associated xmp metadata");
                if (bArr3 == null) {
                }
                Log.e(TAG, "writePortraitExif(): #3: return original jpeg");
                return portraitDepthMapExif.getJpeg();
            }
        } catch (XMPException | IOException unused7) {
            bArr4 = bArr2;
            bArr3 = null;
            Log.d(TAG, "writePortraitExif(): Failed to insert depthmap associated xmp metadata");
            if (bArr3 == null) {
            }
            Log.e(TAG, "writePortraitExif(): #3: return original jpeg");
            return portraitDepthMapExif.getJpeg();
        }
    }
}
