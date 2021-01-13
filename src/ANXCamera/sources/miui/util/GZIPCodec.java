package miui.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class GZIPCodec {
    protected GZIPCodec() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static byte[] decode(byte[] bArr) throws IOException {
        Throwable th;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        GZIPInputStream gZIPInputStream = null;
        try {
            GZIPInputStream gZIPInputStream2 = new GZIPInputStream(byteArrayInputStream);
            try {
                byte[] byteArray = IOUtils.toByteArray(gZIPInputStream2);
                IOUtils.closeQuietly((InputStream) gZIPInputStream2);
                return byteArray;
            } catch (Throwable th2) {
                th = th2;
                gZIPInputStream = gZIPInputStream2;
                IOUtils.closeQuietly((InputStream) gZIPInputStream);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            IOUtils.closeQuietly((InputStream) gZIPInputStream);
            throw th;
        }
    }

    public static byte[] encode(byte[] bArr) throws IOException {
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = null;
        try {
            GZIPOutputStream gZIPOutputStream2 = new GZIPOutputStream(byteArrayOutputStream);
            try {
                gZIPOutputStream2.write(bArr);
                IOUtils.closeQuietly((OutputStream) gZIPOutputStream2);
                return byteArrayOutputStream.toByteArray();
            } catch (Throwable th2) {
                th = th2;
                gZIPOutputStream = gZIPOutputStream2;
                IOUtils.closeQuietly((OutputStream) gZIPOutputStream);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            IOUtils.closeQuietly((OutputStream) gZIPOutputStream);
            throw th;
        }
    }

    public static String getID() {
        return "gzip";
    }
}
