package miui.net.http;

import android.util.Log;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import miui.net.http.Cache;
import miui.security.DigestUtils;
import miui.text.ExtraTextUtils;
import miui.util.AppConstants;
import miui.util.IOUtils;
import miui.util.SoftReferenceSingleton;

public class DiskBasedCache implements Cache {
    private static final int CACHE_MAGIC = 538182184;
    private static final int DEFAULT_DISK_USAGE_BYTES = 10485760;
    private static final float HYSTERESIS_FACTOR = 0.9f;
    private static final SoftReferenceSingleton<DiskBasedCache> INSTANCE = new SoftReferenceSingleton<DiskBasedCache>() {
        /* class miui.net.http.DiskBasedCache.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // miui.util.SoftReferenceSingleton
        public DiskBasedCache createInstance() {
            DiskBasedCache diskBasedCache = new DiskBasedCache();
            diskBasedCache.initialize();
            return diskBasedCache;
        }
    };
    private static final String TAG = "DisBasedCache";
    private final Map<String, DiskCacheEntry> mEntries;
    private final int mMaxCacheSizeInBytes;
    private final File mRootDirectory;
    private long mTotalSize;

    /* access modifiers changed from: private */
    public static class CacheFileContentInputStream extends FilterInputStream {
        private DiskCacheEntry mDiskCacheEntry;

        protected CacheFileContentInputStream(DiskCacheEntry diskCacheEntry) throws IOException {
            super(getInputStream(diskCacheEntry));
            if (((FilterInputStream) this).in != null) {
                this.mDiskCacheEntry = diskCacheEntry;
            }
        }

        private static InputStream getInputStream(DiskCacheEntry diskCacheEntry) throws IOException {
            diskCacheEntry.acquire();
            try {
                FileInputStream fileInputStream = new FileInputStream(diskCacheEntry.file);
                if (fileInputStream.skip(diskCacheEntry.offset) == diskCacheEntry.offset) {
                    return fileInputStream;
                }
                throw new IOException("skip failed for file " + diskCacheEntry.file.getName());
            } catch (Throwable th) {
                diskCacheEntry.release();
                throw th;
            }
        }

        @Override // java.io.FilterInputStream, java.io.Closeable, java.lang.AutoCloseable, java.io.InputStream
        public void close() throws IOException {
            this.mDiskCacheEntry.release();
            super.close();
        }
    }

    /* access modifiers changed from: private */
    public static class DiskCacheEntry {
        public String contentEncoding;
        public String contentType;
        public String etag;
        public File file;
        public String key;
        private volatile boolean markDelete;
        public long offset;
        public Map<String, String> responseHeaders;
        public long serverDate;
        public long size;
        public long softTtl;
        public long ttl;
        private volatile int using;

        private DiskCacheEntry() {
        }

        public static DiskCacheEntry fromFile(File file2) {
            FileInputStream fileInputStream;
            Throwable th;
            try {
                fileInputStream = new FileInputStream(file2);
                try {
                    if (readInt(fileInputStream) != DiskBasedCache.CACHE_MAGIC) {
                        IOUtils.closeQuietly((InputStream) fileInputStream);
                        return null;
                    }
                    DiskCacheEntry diskCacheEntry = new DiskCacheEntry();
                    diskCacheEntry.key = readString(fileInputStream);
                    diskCacheEntry.etag = readString(fileInputStream);
                    if ("".equals(diskCacheEntry.etag)) {
                        diskCacheEntry.etag = null;
                    }
                    diskCacheEntry.contentType = readString(fileInputStream);
                    if ("".equals(diskCacheEntry.contentType)) {
                        diskCacheEntry.contentType = null;
                    }
                    diskCacheEntry.contentEncoding = readString(fileInputStream);
                    if ("".equals(diskCacheEntry.contentEncoding)) {
                        diskCacheEntry.contentEncoding = null;
                    }
                    diskCacheEntry.serverDate = readLong(fileInputStream);
                    diskCacheEntry.ttl = readLong(fileInputStream);
                    diskCacheEntry.softTtl = readLong(fileInputStream);
                    diskCacheEntry.responseHeaders = readHeaders(fileInputStream);
                    diskCacheEntry.offset = fileInputStream.getChannel().position();
                    diskCacheEntry.file = file2;
                    diskCacheEntry.size = file2.length();
                    IOUtils.closeQuietly((InputStream) fileInputStream);
                    return diskCacheEntry;
                } catch (IOException unused) {
                    IOUtils.closeQuietly((InputStream) fileInputStream);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    IOUtils.closeQuietly((InputStream) fileInputStream);
                    throw th;
                }
            } catch (IOException unused2) {
                fileInputStream = null;
                IOUtils.closeQuietly((InputStream) fileInputStream);
                return null;
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = null;
                IOUtils.closeQuietly((InputStream) fileInputStream);
                throw th;
            }
        }

        private static int read(InputStream inputStream) throws IOException {
            int read = inputStream.read();
            if (read != -1) {
                return read;
            }
            throw new EOFException();
        }

        static Map<String, String> readHeaders(InputStream inputStream) throws IOException {
            int readInt = readInt(inputStream);
            HashMap hashMap = new HashMap();
            for (int i = 0; i < readInt; i++) {
                hashMap.put(readString(inputStream).intern(), readString(inputStream).intern());
            }
            return hashMap;
        }

        static int readInt(InputStream inputStream) throws IOException {
            return (read(inputStream) << 24) | read(inputStream) | 0 | (read(inputStream) << 8) | (read(inputStream) << 16);
        }

        static long readLong(InputStream inputStream) throws IOException {
            return (((long) read(inputStream)) & 255) | 0 | ((((long) read(inputStream)) & 255) << 8) | ((((long) read(inputStream)) & 255) << 16) | ((((long) read(inputStream)) & 255) << 24) | ((((long) read(inputStream)) & 255) << 32) | ((((long) read(inputStream)) & 255) << 40) | ((((long) read(inputStream)) & 255) << 48) | ((255 & ((long) read(inputStream))) << 56);
        }

        static String readString(InputStream inputStream) throws IOException {
            int readLong = (int) readLong(inputStream);
            if (readLong < 0 || readLong > 8192) {
                throw new IOException("Malformed data structure encountered");
            }
            byte[] bArr = new byte[readLong];
            int i = 0;
            while (i < readLong) {
                int read = inputStream.read(bArr, i, readLong - i);
                if (read == -1) {
                    break;
                }
                i += read;
            }
            if (i == readLong) {
                return new String(bArr, "UTF-8");
            }
            throw new IOException("Expected " + readLong + " bytes but read " + i + " bytes");
        }

        /* JADX WARNING: Removed duplicated region for block: B:34:0x0109  */
        /* JADX WARNING: Removed duplicated region for block: B:38:0x0129  */
        /* JADX WARNING: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
        public static DiskCacheEntry toFile(File file2, String str, Cache.Entry entry) {
            Throwable th;
            FileOutputStream fileOutputStream;
            IOException e2;
            InputStream inputStream = entry.data;
            try {
                entry.data = null;
                fileOutputStream = new FileOutputStream(file2);
                try {
                    writeInt(fileOutputStream, DiskBasedCache.CACHE_MAGIC);
                    writeString(fileOutputStream, str);
                    String str2 = "";
                    writeString(fileOutputStream, entry.etag == null ? str2 : entry.etag);
                    writeString(fileOutputStream, entry.contentType == null ? str2 : entry.contentType);
                    if (entry.contentEncoding != null) {
                        str2 = entry.contentEncoding;
                    }
                    writeString(fileOutputStream, str2);
                    writeLong(fileOutputStream, entry.serverDate);
                    writeLong(fileOutputStream, entry.ttl);
                    writeLong(fileOutputStream, entry.softTtl);
                    writeHeaders(entry.responseHeaders, fileOutputStream);
                    fileOutputStream.flush();
                    DiskCacheEntry diskCacheEntry = new DiskCacheEntry();
                    diskCacheEntry.key = str;
                    diskCacheEntry.etag = entry.etag;
                    diskCacheEntry.contentType = entry.contentType;
                    diskCacheEntry.contentEncoding = entry.contentEncoding;
                    diskCacheEntry.serverDate = entry.serverDate;
                    diskCacheEntry.ttl = entry.ttl;
                    diskCacheEntry.softTtl = entry.softTtl;
                    diskCacheEntry.responseHeaders = entry.responseHeaders;
                    diskCacheEntry.file = file2;
                    diskCacheEntry.offset = fileOutputStream.getChannel().position();
                    IOUtils.copy(inputStream, fileOutputStream);
                    fileOutputStream.flush();
                    diskCacheEntry.size = fileOutputStream.getChannel().position();
                    if (entry.length <= 0) {
                        entry.length = diskCacheEntry.size - diskCacheEntry.offset;
                    } else if (entry.length != diskCacheEntry.size - diskCacheEntry.offset) {
                        throw new IOException("length not match " + entry.length + ":" + (diskCacheEntry.size - diskCacheEntry.offset));
                    }
                    fileOutputStream.close();
                    entry.data = new CacheFileContentInputStream(diskCacheEntry);
                    IOUtils.closeQuietly(inputStream);
                    IOUtils.closeQuietly((OutputStream) null);
                    return diskCacheEntry;
                } catch (IOException e3) {
                    e2 = e3;
                    try {
                        Log.w(DiskBasedCache.TAG, "Cannot save cache to disk file " + file2, e2);
                        IOUtils.closeQuietly(inputStream);
                        IOUtils.closeQuietly((OutputStream) fileOutputStream);
                        if (!file2.delete()) {
                            return null;
                        }
                        Log.e(DiskBasedCache.TAG, "Cannot delete file " + file2);
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        IOUtils.closeQuietly(inputStream);
                        IOUtils.closeQuietly((OutputStream) fileOutputStream);
                        if (!file2.delete()) {
                            Log.e(DiskBasedCache.TAG, "Cannot delete file " + file2);
                        }
                        throw th;
                    }
                }
            } catch (IOException e4) {
                e2 = e4;
                fileOutputStream = null;
                Log.w(DiskBasedCache.TAG, "Cannot save cache to disk file " + file2, e2);
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly((OutputStream) fileOutputStream);
                if (!file2.delete()) {
                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly((OutputStream) fileOutputStream);
                if (!file2.delete()) {
                }
                throw th;
            }
        }

        static void writeHeaders(Map<String, String> map, OutputStream outputStream) throws IOException {
            if (map == null || map.size() == 0) {
                writeInt(outputStream, 0);
                return;
            }
            writeInt(outputStream, map.size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                writeString(outputStream, entry.getKey());
                writeString(outputStream, entry.getValue());
            }
        }

        static void writeInt(OutputStream outputStream, int i) throws IOException {
            outputStream.write(i & 255);
            outputStream.write((i >> 8) & 255);
            outputStream.write((i >> 16) & 255);
            outputStream.write((i >> 24) & 255);
        }

        static void writeLong(OutputStream outputStream, long j) throws IOException {
            outputStream.write((byte) ((int) j));
            outputStream.write((byte) ((int) (j >>> 8)));
            outputStream.write((byte) ((int) (j >>> 16)));
            outputStream.write((byte) ((int) (j >>> 24)));
            outputStream.write((byte) ((int) (j >>> 32)));
            outputStream.write((byte) ((int) (j >>> 40)));
            outputStream.write((byte) ((int) (j >>> 48)));
            outputStream.write((byte) ((int) (j >>> 56)));
        }

        static void writeString(OutputStream outputStream, String str) throws IOException {
            byte[] bytes = str.getBytes("UTF-8");
            writeLong(outputStream, (long) bytes.length);
            outputStream.write(bytes, 0, bytes.length);
        }

        public synchronized void acquire() {
            this.using++;
        }

        public synchronized void delete() {
            if (this.using > 0) {
                this.markDelete = true;
            } else if (!this.file.delete()) {
                Log.e(DiskBasedCache.TAG, "Cannot delete file " + this.file);
            }
        }

        public synchronized boolean isUsing() {
            return this.using > 0;
        }

        public synchronized void release() {
            this.using--;
            if (this.using <= 0 && this.markDelete && !this.file.delete()) {
                Log.e(DiskBasedCache.TAG, "Cannot delete file " + this.file);
            }
        }

        public Cache.Entry toCacheEntry() {
            try {
                Cache.Entry entry = new Cache.Entry();
                entry.data = new FileInputStream(this.file);
                if (entry.data.skip(this.offset) != this.offset) {
                    return null;
                }
                entry.length = this.size - this.offset;
                entry.etag = this.etag;
                entry.contentType = this.contentType;
                entry.contentEncoding = this.contentEncoding;
                entry.serverDate = this.serverDate;
                entry.ttl = this.ttl;
                entry.softTtl = this.softTtl;
                entry.responseHeaders = this.responseHeaders;
                return entry;
            } catch (IOException unused) {
                return null;
            }
        }
    }

    public DiskBasedCache() {
        this(null, DEFAULT_DISK_USAGE_BYTES);
    }

    public DiskBasedCache(File file) {
        this(file, DEFAULT_DISK_USAGE_BYTES);
    }

    public DiskBasedCache(File file, int i) {
        this.mEntries = new ConcurrentHashMap(16, 0.75f);
        this.mTotalSize = 0;
        this.mRootDirectory = file == null ? new File(AppConstants.getCurrentApplication().getCacheDir(), "miui.net.http") : file;
        this.mMaxCacheSizeInBytes = i;
    }

    public static DiskBasedCache getDefault() {
        return INSTANCE.get();
    }

    private File getFileForKey(String str) {
        return new File(this.mRootDirectory, ExtraTextUtils.toHexReadable(DigestUtils.get(str, DigestUtils.ALGORITHM_MD5)));
    }

    private void pruneIfNeeded(long j) {
        if (this.mTotalSize + j >= ((long) this.mMaxCacheSizeInBytes)) {
            long currentTimeMillis = System.currentTimeMillis();
            synchronized (this.mEntries) {
                if (this.mTotalSize + j >= ((long) this.mMaxCacheSizeInBytes)) {
                    Iterator<Map.Entry<String, DiskCacheEntry>> it = this.mEntries.entrySet().iterator();
                    while (it.hasNext()) {
                        DiskCacheEntry value = it.next().getValue();
                        if (value.ttl < currentTimeMillis) {
                            value.delete();
                            this.mTotalSize -= value.size;
                            it.remove();
                        }
                    }
                    if (this.mTotalSize + j >= ((long) this.mMaxCacheSizeInBytes)) {
                        Iterator<Map.Entry<String, DiskCacheEntry>> it2 = this.mEntries.entrySet().iterator();
                        while (it2.hasNext()) {
                            DiskCacheEntry value2 = it2.next().getValue();
                            if (value2.softTtl < currentTimeMillis) {
                                value2.delete();
                                this.mTotalSize -= value2.size;
                                it2.remove();
                                if (((float) (this.mTotalSize + j)) < ((float) this.mMaxCacheSizeInBytes) * 0.9f) {
                                    break;
                                }
                            }
                        }
                        if (this.mTotalSize + j >= ((long) this.mMaxCacheSizeInBytes)) {
                            Iterator<Map.Entry<String, DiskCacheEntry>> it3 = this.mEntries.entrySet().iterator();
                            while (it3.hasNext()) {
                                DiskCacheEntry value3 = it3.next().getValue();
                                if (!value3.isUsing()) {
                                    value3.delete();
                                    it3.remove();
                                    if (((float) (this.mTotalSize + j)) < ((float) this.mMaxCacheSizeInBytes) * 0.9f) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void putEntry(DiskCacheEntry diskCacheEntry) {
        synchronized (this.mEntries) {
            DiskCacheEntry diskCacheEntry2 = this.mEntries.get(diskCacheEntry.key);
            if (diskCacheEntry2 == null) {
                this.mTotalSize += diskCacheEntry.size;
            } else {
                this.mTotalSize += diskCacheEntry.size - diskCacheEntry2.size;
            }
            this.mEntries.put(diskCacheEntry.key, diskCacheEntry);
        }
    }

    private void removeEntry(DiskCacheEntry diskCacheEntry) {
        synchronized (this.mEntries) {
            this.mTotalSize -= diskCacheEntry.size;
            this.mEntries.remove(diskCacheEntry.key);
        }
    }

    @Override // miui.net.http.Cache
    public void clear() {
        for (Map.Entry<String, DiskCacheEntry> entry : this.mEntries.entrySet()) {
            entry.getValue().delete();
        }
        this.mEntries.clear();
        this.mTotalSize = 0;
    }

    @Override // miui.net.http.Cache
    public Cache.Entry get(String str) {
        DiskCacheEntry diskCacheEntry = this.mEntries.get(str);
        if (diskCacheEntry == null) {
            return null;
        }
        return diskCacheEntry.toCacheEntry();
    }

    @Override // miui.net.http.Cache
    public void initialize() {
        this.mEntries.clear();
        this.mTotalSize = 0;
        if (this.mRootDirectory.exists()) {
            File[] listFiles = this.mRootDirectory.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    DiskCacheEntry fromFile = DiskCacheEntry.fromFile(file);
                    if (fromFile != null) {
                        putEntry(fromFile);
                    } else if (!file.delete()) {
                        Log.e(TAG, "Cannot delete file " + file);
                    }
                }
            }
        } else if (!this.mRootDirectory.mkdirs()) {
            Log.e(TAG, "Cannot create directory " + this.mRootDirectory);
        }
    }

    @Override // miui.net.http.Cache
    public void invalidate(String str, boolean z) {
        DiskCacheEntry diskCacheEntry = this.mEntries.get(str);
        if (diskCacheEntry != null) {
            diskCacheEntry.softTtl = 0;
            if (z) {
                diskCacheEntry.ttl = 0;
            }
        }
    }

    @Override // miui.net.http.Cache
    public boolean put(String str, Cache.Entry entry) {
        DiskCacheEntry file = DiskCacheEntry.toFile(getFileForKey(str), str, entry);
        if (file == null) {
            return false;
        }
        pruneIfNeeded(file.size);
        putEntry(file);
        return true;
    }

    @Override // miui.net.http.Cache
    public void remove(String str) {
        DiskCacheEntry diskCacheEntry = this.mEntries.get(str);
        if (diskCacheEntry != null) {
            diskCacheEntry.delete();
            removeEntry(diskCacheEntry);
        }
    }
}
