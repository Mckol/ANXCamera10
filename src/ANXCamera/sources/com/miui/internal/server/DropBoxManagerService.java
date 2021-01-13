package com.miui.internal.server;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.os.SystemClock;
import android.text.format.Time;
import android.util.Log;
import com.android.camera.module.loader.FunctionParseBeautyBodySlimCount;
import com.google.android.apps.photos.api.PhotosOemApi;
import com.miui.internal.server.IDropBoxManagerService;
import com.ss.android.vesdk.VEEditor;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.zip.GZIPOutputStream;
import miui.os.DropBoxManager;
import miui.os.FileUtils;
import miui.util.AppConstants;
import miui.util.SoftReferenceSingleton;

public final class DropBoxManagerService extends IDropBoxManagerService.Stub {
    private static final int DEFAULT_AGE_SECONDS = 259200;
    private static final int DEFAULT_MAX_FILES = 1000;
    private static final int DEFAULT_QUOTA_KB = 5120;
    private static final int DEFAULT_QUOTA_PERCENT = 10;
    private static final int DEFAULT_RESERVE_PERCENT = 10;
    private static final String DROPBOX_TAG_PREFIX = "dropbox:";
    private static final SoftReferenceSingleton<DropBoxManagerService> INSTANCE = new SoftReferenceSingleton<DropBoxManagerService>() {
        /* class com.miui.internal.server.DropBoxManagerService.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // miui.util.SoftReferenceSingleton
        public DropBoxManagerService createInstance() {
            return new DropBoxManagerService();
        }
    };
    private static final int MSG_SEND_BROADCAST = 1;
    private static final boolean PROFILE_DUMP = false;
    private static final int QUOTA_RESCAN_MILLIS = 5000;
    public static final String SERVICE_NAME = "DropBoxManagerService";
    private static final String TAG = "DropBoxManagerService";
    private FileList mAllFiles;
    private int mBlockSize;
    private int mCachedQuotaBlocks;
    private long mCachedQuotaUptimeMillis;
    private Context mContext;
    private File mDropBoxDir;
    private HashMap<String, FileList> mFilesByTag;
    private Handler mHandler;
    private StatFs mStatFs;

    /* access modifiers changed from: private */
    public static final class EntryFile implements Comparable<EntryFile> {
        public final int blocks;
        public final File file;
        public final int flags;
        public final String tag;
        public final long timestampMillis;

        public EntryFile(long j) {
            this.tag = null;
            this.timestampMillis = j;
            this.flags = 1;
            this.file = null;
            this.blocks = 0;
        }

        public EntryFile(File file2, int i) {
            String str;
            this.file = file2;
            long j = (long) i;
            this.blocks = (int) (((this.file.length() + j) - 1) / j);
            String name = file2.getName();
            int lastIndexOf = name.lastIndexOf(64);
            long j2 = 0;
            if (lastIndexOf < 0) {
                this.tag = null;
                this.timestampMillis = 0;
                this.flags = 1;
                return;
            }
            int i2 = 0;
            this.tag = Uri.decode(name.substring(0, lastIndexOf));
            if (name.endsWith(".gz")) {
                name = name.substring(0, name.length() - 3);
                i2 = 4;
            }
            if (name.endsWith(".lost")) {
                i2 |= 1;
                str = name.substring(lastIndexOf + 1, name.length() - 5);
            } else if (name.endsWith(".txt")) {
                i2 |= 2;
                str = name.substring(lastIndexOf + 1, name.length() - 4);
            } else if (name.endsWith(".dat")) {
                str = name.substring(lastIndexOf + 1, name.length() - 4);
            } else {
                this.flags = 1;
                this.timestampMillis = 0;
                return;
            }
            this.flags = i2;
            try {
                j2 = Long.valueOf(str).longValue();
            } catch (NumberFormatException unused) {
            }
            this.timestampMillis = j2;
        }

        public EntryFile(File file2, File file3, String str, long j, int i, int i2) throws IOException {
            if ((i & 1) == 0) {
                this.tag = str;
                this.timestampMillis = j;
                this.flags = i;
                StringBuilder sb = new StringBuilder();
                sb.append(Uri.encode(str));
                sb.append("@");
                sb.append(j);
                sb.append((i & 2) != 0 ? ".txt" : ".dat");
                sb.append((i & 4) != 0 ? ".gz" : "");
                this.file = new File(file3, sb.toString());
                if (file2.renameTo(this.file)) {
                    long j2 = (long) i2;
                    this.blocks = (int) (((this.file.length() + j2) - 1) / j2);
                    return;
                }
                throw new IOException("Can't rename " + file2 + " to " + this.file);
            }
            throw new IllegalArgumentException();
        }

        public EntryFile(File file2, String str, long j) throws IOException {
            this.tag = str;
            this.timestampMillis = j;
            this.flags = 1;
            this.file = new File(file2, Uri.encode(str) + "@" + j + ".lost");
            this.blocks = 0;
            new FileOutputStream(this.file).close();
        }

        public final int compareTo(EntryFile entryFile) {
            File file2;
            long j = this.timestampMillis;
            long j2 = entryFile.timestampMillis;
            if (j < j2) {
                return -1;
            }
            if (j > j2) {
                return 1;
            }
            File file3 = this.file;
            if (file3 != null && (file2 = entryFile.file) != null) {
                return file3.compareTo(file2);
            }
            if (entryFile.file != null) {
                return -1;
            }
            if (this.file != null) {
                return 1;
            }
            if (this == entryFile) {
                return 0;
            }
            if (hashCode() < entryFile.hashCode()) {
                return -1;
            }
            return hashCode() > entryFile.hashCode() ? 1 : 0;
        }
    }

    /* access modifiers changed from: private */
    public static final class FileList implements Comparable<FileList> {
        public int blocks;
        public final TreeSet<EntryFile> contents;

        private FileList() {
            this.blocks = 0;
            this.contents = new TreeSet<>();
        }

        public final int compareTo(FileList fileList) {
            int i = this.blocks;
            int i2 = fileList.blocks;
            if (i != i2) {
                return i2 - i;
            }
            if (this == fileList) {
                return 0;
            }
            if (hashCode() < fileList.hashCode()) {
                return -1;
            }
            return hashCode() > fileList.hashCode() ? 1 : 0;
        }
    }

    private DropBoxManagerService() {
        this.mAllFiles = null;
        this.mFilesByTag = null;
        this.mStatFs = null;
        this.mBlockSize = 0;
        this.mCachedQuotaBlocks = 0;
        this.mCachedQuotaUptimeMillis = 0;
        this.mContext = AppConstants.getCurrentApplication();
        this.mDropBoxDir = new File(this.mContext.getFilesDir().getAbsolutePath() + File.separator + "dropbox");
        this.mHandler = new Handler(Looper.getMainLooper()) {
            /* class com.miui.internal.server.DropBoxManagerService.AnonymousClass2 */

            public void handleMessage(Message message) {
                if (message.what == 1) {
                    DropBoxManagerService.this.mContext.sendBroadcast((Intent) message.obj, "miui.permission.READ_LOGS");
                }
            }
        };
        initAndTrimAsync();
    }

    private synchronized long createEntry(File file, String str, int i) throws IOException {
        long j;
        long currentTimeMillis = System.currentTimeMillis();
        SortedSet<EntryFile> tailSet = this.mAllFiles.contents.tailSet(new EntryFile(FunctionParseBeautyBodySlimCount.TIP_INTERVAL_TIME + currentTimeMillis));
        EntryFile[] entryFileArr = null;
        if (!tailSet.isEmpty()) {
            entryFileArr = (EntryFile[]) tailSet.toArray(new EntryFile[tailSet.size()]);
            tailSet.clear();
        }
        if (!this.mAllFiles.contents.isEmpty()) {
            currentTimeMillis = Math.max(currentTimeMillis, this.mAllFiles.contents.last().timestampMillis + 1);
        }
        if (entryFileArr != null) {
            j = currentTimeMillis;
            for (EntryFile entryFile : entryFileArr) {
                this.mAllFiles.blocks -= entryFile.blocks;
                FileList fileList = this.mFilesByTag.get(entryFile.tag);
                if (fileList != null && fileList.contents.remove(entryFile)) {
                    fileList.blocks -= entryFile.blocks;
                }
                if ((entryFile.flags & 1) == 0) {
                    enrollEntry(new EntryFile(entryFile.file, this.mDropBoxDir, entryFile.tag, j, entryFile.flags, this.mBlockSize));
                    j++;
                } else {
                    enrollEntry(new EntryFile(this.mDropBoxDir, entryFile.tag, j));
                    j++;
                }
            }
        } else {
            j = currentTimeMillis;
        }
        if (file == null) {
            enrollEntry(new EntryFile(this.mDropBoxDir, str, j));
        } else {
            enrollEntry(new EntryFile(file, this.mDropBoxDir, str, j, i, this.mBlockSize));
        }
        return j;
    }

    private synchronized void enrollEntry(EntryFile entryFile) {
        this.mAllFiles.contents.add(entryFile);
        this.mAllFiles.blocks += entryFile.blocks;
        if (!(entryFile.tag == null || entryFile.file == null || entryFile.blocks <= 0)) {
            FileList fileList = this.mFilesByTag.get(entryFile.tag);
            if (fileList == null) {
                fileList = new FileList();
                this.mFilesByTag.put(entryFile.tag, fileList);
            }
            fileList.contents.add(entryFile);
            fileList.blocks += entryFile.blocks;
        }
    }

    public static DropBoxManagerService getInstance() {
        return INSTANCE.get();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private synchronized void init() throws IOException {
        if (this.mStatFs == null) {
            if (!this.mDropBoxDir.isDirectory()) {
                if (!this.mDropBoxDir.mkdirs()) {
                    throw new IOException("Can't mkdir: " + this.mDropBoxDir);
                }
            }
            try {
                this.mStatFs = new StatFs(this.mDropBoxDir.getPath());
                this.mBlockSize = this.mStatFs.getBlockSize();
            } catch (IllegalArgumentException unused) {
                throw new IOException("Can't statfs: " + this.mDropBoxDir);
            }
        }
        if (this.mAllFiles == null) {
            File[] listFiles = this.mDropBoxDir.listFiles();
            if (listFiles != null) {
                this.mAllFiles = new FileList();
                this.mFilesByTag = new HashMap<>();
                for (File file : listFiles) {
                    if (file.getName().endsWith(".tmp")) {
                        Log.i("DropBoxManagerService", "Cleaning temp file: " + file);
                        file.delete();
                    } else {
                        EntryFile entryFile = new EntryFile(file, this.mBlockSize);
                        if (entryFile.tag == null) {
                            Log.w("DropBoxManagerService", "Unrecognized file: " + file);
                        } else if (entryFile.timestampMillis == 0) {
                            Log.w("DropBoxManagerService", "Invalid filename: " + file);
                            file.delete();
                        } else {
                            enrollEntry(entryFile);
                        }
                    }
                }
            } else {
                throw new IOException("Can't list files: " + this.mDropBoxDir);
            }
        }
    }

    private void initAndTrimAsync() {
        this.mCachedQuotaUptimeMillis = 0;
        new Thread() {
            /* class com.miui.internal.server.DropBoxManagerService.AnonymousClass3 */

            public void run() {
                try {
                    DropBoxManagerService.this.init();
                    DropBoxManagerService.this.trimToFit();
                } catch (IOException e2) {
                    Log.e("DropBoxManagerService", "Can't init", e2);
                }
            }
        }.start();
    }

    public static void onReceive(Context context, Intent intent) {
        if (Receiver.isActionEquals(intent, "android.intent.action.DEVICE_STORAGE_LOW")) {
            getInstance().initAndTrimAsync();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private synchronized long trimToFit() {
        long currentTimeMillis = System.currentTimeMillis() - ((long) 259200000);
        while (true) {
            if (this.mAllFiles.contents.isEmpty()) {
                break;
            }
            EntryFile first = this.mAllFiles.contents.first();
            if (first.timestampMillis > currentTimeMillis && this.mAllFiles.contents.size() < 1000) {
                break;
            }
            FileList fileList = this.mFilesByTag.get(first.tag);
            if (fileList != null && fileList.contents.remove(first)) {
                fileList.blocks -= first.blocks;
            }
            if (this.mAllFiles.contents.remove(first)) {
                this.mAllFiles.blocks -= first.blocks;
            }
            if (first.file != null) {
                first.file.delete();
            }
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        int i = 0;
        if (uptimeMillis > this.mCachedQuotaUptimeMillis + 5000) {
            this.mStatFs.restat(this.mDropBoxDir.getPath());
            this.mCachedQuotaBlocks = Math.min(5242880 / this.mBlockSize, Math.max(0, ((this.mStatFs.getAvailableBlocks() - ((this.mStatFs.getBlockCount() * 10) / 100)) * 10) / 100));
            this.mCachedQuotaUptimeMillis = uptimeMillis;
        }
        if (this.mAllFiles.blocks > this.mCachedQuotaBlocks) {
            int i2 = this.mAllFiles.blocks;
            TreeSet treeSet = new TreeSet(this.mFilesByTag.values());
            Iterator it = treeSet.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                FileList fileList2 = (FileList) it.next();
                if (i > 0 && fileList2.blocks <= (this.mCachedQuotaBlocks - i2) / i) {
                    break;
                }
                i2 -= fileList2.blocks;
                i++;
            }
            int i3 = (this.mCachedQuotaBlocks - i2) / i;
            Iterator it2 = treeSet.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                FileList fileList3 = (FileList) it2.next();
                if (this.mAllFiles.blocks < this.mCachedQuotaBlocks) {
                    break;
                }
                while (fileList3.blocks > i3 && !fileList3.contents.isEmpty()) {
                    EntryFile first2 = fileList3.contents.first();
                    if (fileList3.contents.remove(first2)) {
                        fileList3.blocks -= first2.blocks;
                    }
                    if (this.mAllFiles.contents.remove(first2)) {
                        this.mAllFiles.blocks -= first2.blocks;
                    }
                    try {
                        if (first2.file != null) {
                            first2.file.delete();
                        }
                        enrollEntry(new EntryFile(this.mDropBoxDir, first2.tag, first2.timestampMillis));
                    } catch (IOException e2) {
                        Log.e("DropBoxManagerService", "Can't write tombstone file", e2);
                    }
                }
            }
        }
        return ((long) this.mCachedQuotaBlocks) * ((long) this.mBlockSize);
    }

    /* JADX WARNING: Removed duplicated region for block: B:103:0x017e A[SYNTHETIC, Splitter:B:103:0x017e] */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x0186  */
    /* JADX WARNING: Removed duplicated region for block: B:116:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0165 A[SYNTHETIC, Splitter:B:87:0x0165] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x016a A[SYNTHETIC, Splitter:B:91:0x016a] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0172  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0179 A[SYNTHETIC, Splitter:B:99:0x0179] */
    @Override // com.miui.internal.server.IDropBoxManagerService
    public void add(DropBoxManager.Entry entry) {
        File file;
        InputStream inputStream;
        BufferedOutputStream bufferedOutputStream;
        Throwable th;
        IOException e2;
        long createEntry;
        String tag = entry.getTag();
        try {
            int flags = entry.getFlags();
            if ((flags & 1) == 0) {
                init();
                if (!isTagEnabled(tag)) {
                    entry.close();
                    return;
                }
                long trimToFit = trimToFit();
                long currentTimeMillis = System.currentTimeMillis();
                byte[] bArr = new byte[this.mBlockSize];
                inputStream = entry.getInputStream();
                int i = 0;
                while (true) {
                    try {
                        if (i >= bArr.length) {
                            break;
                        }
                        int read = inputStream.read(bArr, i, bArr.length - i);
                        if (read <= 0) {
                            break;
                        }
                        i += read;
                    } catch (IOException e3) {
                        e2 = e3;
                        bufferedOutputStream = null;
                        file = null;
                        try {
                            Log.e("DropBoxManagerService", "Can't write: " + tag, e2);
                            if (bufferedOutputStream != null) {
                            }
                            if (inputStream != null) {
                            }
                            entry.close();
                            if (file == null) {
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (bufferedOutputStream != null) {
                            }
                            if (inputStream != null) {
                            }
                            entry.close();
                            if (file != null) {
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedOutputStream = null;
                        file = null;
                        if (bufferedOutputStream != null) {
                        }
                        if (inputStream != null) {
                        }
                        entry.close();
                        if (file != null) {
                        }
                        throw th;
                    }
                }
                File file2 = new File(this.mDropBoxDir, "drop" + Thread.currentThread().getId() + ".tmp");
                try {
                    int i2 = this.mBlockSize;
                    if (i2 > 4096) {
                        i2 = 4096;
                    }
                    if (i2 < 512) {
                        i2 = 512;
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(fileOutputStream, i2);
                    try {
                        int i3 = i;
                        if (i3 == bArr.length && (flags & 4) == 0) {
                            bufferedOutputStream = new GZIPOutputStream(bufferedOutputStream2);
                            flags |= 4;
                        } else {
                            bufferedOutputStream = bufferedOutputStream2;
                        }
                        while (true) {
                            try {
                                bufferedOutputStream.write(bArr, 0, i3);
                                long currentTimeMillis2 = System.currentTimeMillis();
                                if (currentTimeMillis2 - currentTimeMillis > 30000) {
                                    trimToFit = trimToFit();
                                    currentTimeMillis = currentTimeMillis2;
                                }
                                i3 = inputStream.read(bArr);
                                if (i3 <= 0) {
                                    FileUtils.sync(fileOutputStream);
                                    bufferedOutputStream.close();
                                    bufferedOutputStream = null;
                                } else {
                                    bufferedOutputStream.flush();
                                }
                                if (file2.length() <= trimToFit) {
                                    if (i3 <= 0) {
                                        break;
                                    }
                                } else {
                                    Log.w("DropBoxManagerService", "Dropping: " + tag + " (" + file2.length() + " > " + trimToFit + " bytes)");
                                    file2.delete();
                                    file2 = null;
                                    break;
                                }
                            } catch (IOException e4) {
                                e2 = e4;
                                file = file2;
                                Log.e("DropBoxManagerService", "Can't write: " + tag, e2);
                                if (bufferedOutputStream != null) {
                                    try {
                                        bufferedOutputStream.close();
                                    } catch (IOException unused) {
                                    }
                                }
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException unused2) {
                                    }
                                }
                                entry.close();
                                if (file == null) {
                                    file.delete();
                                    return;
                                }
                                return;
                            } catch (Throwable th4) {
                                th = th4;
                                file = file2;
                                if (bufferedOutputStream != null) {
                                    try {
                                        bufferedOutputStream.close();
                                    } catch (IOException unused3) {
                                    }
                                }
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException unused4) {
                                    }
                                }
                                entry.close();
                                if (file != null) {
                                    file.delete();
                                }
                                throw th;
                            }
                        }
                        createEntry = createEntry(file2, tag, flags);
                    } catch (IOException e5) {
                        e2 = e5;
                        bufferedOutputStream = bufferedOutputStream2;
                        file = file2;
                        Log.e("DropBoxManagerService", "Can't write: " + tag, e2);
                        if (bufferedOutputStream != null) {
                        }
                        if (inputStream != null) {
                        }
                        entry.close();
                        if (file == null) {
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        bufferedOutputStream = bufferedOutputStream2;
                        file = file2;
                        if (bufferedOutputStream != null) {
                        }
                        if (inputStream != null) {
                        }
                        entry.close();
                        if (file != null) {
                        }
                        throw th;
                    }
                    try {
                        Intent intent = new Intent(DropBoxManager.ACTION_DROPBOX_ENTRY_ADDED);
                        intent.putExtra(DropBoxManager.EXTRA_TAG, tag);
                        intent.putExtra("time", createEntry);
                        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, intent));
                        if (bufferedOutputStream != null) {
                            try {
                                bufferedOutputStream.close();
                            } catch (IOException unused5) {
                            }
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException unused6) {
                            }
                        }
                        entry.close();
                    } catch (IOException e6) {
                        e2 = e6;
                        file = null;
                        Log.e("DropBoxManagerService", "Can't write: " + tag, e2);
                        if (bufferedOutputStream != null) {
                        }
                        if (inputStream != null) {
                        }
                        entry.close();
                        if (file == null) {
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        file = null;
                        if (bufferedOutputStream != null) {
                        }
                        if (inputStream != null) {
                        }
                        entry.close();
                        if (file != null) {
                        }
                        throw th;
                    }
                } catch (IOException e7) {
                    e2 = e7;
                    file = file2;
                    bufferedOutputStream = null;
                    Log.e("DropBoxManagerService", "Can't write: " + tag, e2);
                    if (bufferedOutputStream != null) {
                    }
                    if (inputStream != null) {
                    }
                    entry.close();
                    if (file == null) {
                    }
                } catch (Throwable th7) {
                    th = th7;
                    file = file2;
                    bufferedOutputStream = null;
                    if (bufferedOutputStream != null) {
                    }
                    if (inputStream != null) {
                    }
                    entry.close();
                    if (file != null) {
                    }
                    throw th;
                }
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IOException e8) {
            e2 = e8;
            bufferedOutputStream = null;
            inputStream = null;
            file = null;
            Log.e("DropBoxManagerService", "Can't write: " + tag, e2);
            if (bufferedOutputStream != null) {
            }
            if (inputStream != null) {
            }
            entry.close();
            if (file == null) {
            }
        } catch (Throwable th8) {
            th = th8;
            bufferedOutputStream = null;
            inputStream = null;
            file = null;
            if (bufferedOutputStream != null) {
            }
            if (inputStream != null) {
            }
            entry.close();
            if (file != null) {
            }
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:104:0x01e2, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x01e3, code lost:
        r22 = r14;
        r14 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x0210, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0217, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x0218, code lost:
        r22 = r14;
        r14 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x021e, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x026f, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x0275, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x0276, code lost:
        r14 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x027a, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x027b, code lost:
        r23 = r5;
        r21 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x02ae, code lost:
        r14.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x02b3, code lost:
        r22.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x02ba, code lost:
        r14.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:?, code lost:
        r22.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0217 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:96:0x01d1] */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x0275 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:83:0x01a5] */
    /* JADX WARNING: Removed duplicated region for block: B:165:0x02ae  */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x02b3  */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x02ba  */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x02bf A[SYNTHETIC, Splitter:B:172:0x02bf] */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x02ca  */
    public synchronized void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder sb;
        ArrayList arrayList;
        boolean z;
        int size;
        int i;
        EntryFile next;
        ArrayList arrayList2;
        int i2;
        InputStreamReader inputStreamReader;
        DropBoxManager.Entry entry;
        Throwable th;
        IOException e2;
        DropBoxManager.Entry entry2;
        InputStreamReader inputStreamReader2;
        DropBoxManager.Entry entry3;
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: Can't dump DropBoxManagerService");
            return;
        }
        try {
            init();
            sb = new StringBuilder();
            arrayList = new ArrayList();
            int i3 = 0;
            z = false;
            boolean z2 = false;
            while (strArr != null && i3 < strArr.length) {
                if (strArr[i3].equals("-p") || strArr[i3].equals("--print")) {
                    z = true;
                } else if (strArr[i3].equals("-f") || strArr[i3].equals("--file")) {
                    z2 = true;
                } else if (strArr[i3].startsWith("-")) {
                    sb.append("Unknown argument: ");
                    sb.append(strArr[i3]);
                    sb.append("\n");
                } else {
                    arrayList.add(strArr[i3]);
                }
                i3++;
            }
            sb.append("Drop box contents: ");
            sb.append(this.mAllFiles.contents.size());
            sb.append(" entries\n");
            if (!arrayList.isEmpty()) {
                sb.append("Searching for:");
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    sb.append(" ");
                    sb.append((String) it.next());
                }
                sb.append("\n");
            }
            size = arrayList.size();
            Time time = new Time();
            sb.append("\n");
            Iterator<EntryFile> it2 = this.mAllFiles.contents.iterator();
            i = 0;
            while (it2.hasNext()) {
                next = it2.next();
                time.set(next.timestampMillis);
                String format = time.format("%Y-%m-%d %H:%M:%S");
                boolean z3 = true;
                for (int i4 = 0; i4 < size && z3; i4++) {
                    String str = (String) arrayList.get(i4);
                    z3 = format.contains(str) || str.equals(next.tag);
                }
                if (z3) {
                    int i5 = i + 1;
                    if (z) {
                        sb.append("========================================\n");
                    }
                    sb.append(format);
                    sb.append(" ");
                    sb.append(next.tag == null ? "(no tag)" : next.tag);
                    if (next.file == null) {
                        sb.append(" (no file)\n");
                    } else if ((next.flags & 1) != 0) {
                        sb.append(" (contents lost)\n");
                    } else {
                        sb.append(" (");
                        if ((next.flags & 4) != 0) {
                            sb.append("compressed ");
                        }
                        sb.append((next.flags & 2) != 0 ? VEEditor.MVConsts.TYPE_TEXT : PhotosOemApi.PATH_SPECIAL_TYPE_DATA);
                        sb.append(", ");
                        sb.append(next.file.length());
                        sb.append(" bytes)\n");
                        if (z2 || (z && (next.flags & 2) == 0)) {
                            if (!z) {
                                sb.append("    ");
                            }
                            sb.append(next.file.getPath());
                            sb.append("\n");
                        }
                        if ((next.flags & 2) == 0 || (!z && z2)) {
                            arrayList2 = arrayList;
                            i2 = size;
                        } else {
                            try {
                                i2 = size;
                                arrayList2 = arrayList;
                                entry2 = new DropBoxManager.Entry(next.tag, next.timestampMillis, next.file, next.flags);
                                if (z) {
                                    try {
                                        inputStreamReader2 = new InputStreamReader(entry2.getInputStream());
                                        try {
                                            char[] cArr = new char[4096];
                                            boolean z4 = false;
                                            while (true) {
                                                int read = inputStreamReader2.read(cArr);
                                                if (read <= 0) {
                                                    break;
                                                }
                                                sb.append(cArr, 0, read);
                                                z4 = cArr[read + -1] == '\n';
                                                if (sb.length() > 65536) {
                                                    printWriter.write(sb.toString());
                                                    sb.setLength(0);
                                                }
                                            }
                                            if (!z4) {
                                                sb.append("\n");
                                            }
                                            entry3 = entry2;
                                        } catch (IOException e3) {
                                            e2 = e3;
                                        } catch (Throwable th2) {
                                        }
                                    } catch (IOException e4) {
                                        e2 = e4;
                                        entry = entry2;
                                        inputStreamReader = null;
                                        try {
                                            sb.append("*** ");
                                            sb.append(e2.toString());
                                            sb.append("\n");
                                            Log.e("DropBoxManagerService", "Can't read: " + next.file, e2);
                                            if (entry != null) {
                                            }
                                            if (inputStreamReader != null) {
                                            }
                                            if (z) {
                                            }
                                            i = i5;
                                            size = i2;
                                            arrayList = arrayList2;
                                        } catch (Throwable th3) {
                                            th = th3;
                                            if (entry != null) {
                                            }
                                            if (inputStreamReader != null) {
                                            }
                                            throw th;
                                        }
                                    } catch (Throwable th4) {
                                        th = th4;
                                        entry = entry2;
                                        inputStreamReader = null;
                                        if (entry != null) {
                                        }
                                        if (inputStreamReader != null) {
                                        }
                                        throw th;
                                    }
                                } else {
                                    entry3 = entry2;
                                    try {
                                        String text = entry3.getText(70);
                                        boolean z5 = text.length() == 70;
                                        sb.append("    ");
                                        sb.append(text.trim().replace('\n', '/'));
                                        if (z5) {
                                            sb.append(" ...");
                                        }
                                        sb.append("\n");
                                        inputStreamReader2 = null;
                                    } catch (IOException e5) {
                                        e2 = e5;
                                        entry = entry3;
                                        inputStreamReader = null;
                                        sb.append("*** ");
                                        sb.append(e2.toString());
                                        sb.append("\n");
                                        Log.e("DropBoxManagerService", "Can't read: " + next.file, e2);
                                        if (entry != null) {
                                        }
                                        if (inputStreamReader != null) {
                                        }
                                        if (z) {
                                        }
                                        i = i5;
                                        size = i2;
                                        arrayList = arrayList2;
                                    } catch (Throwable th5) {
                                        th = th5;
                                        entry = entry3;
                                        inputStreamReader = null;
                                        if (entry != null) {
                                        }
                                        if (inputStreamReader != null) {
                                        }
                                        throw th;
                                    }
                                }
                                entry3.close();
                                if (inputStreamReader2 != null) {
                                    try {
                                        inputStreamReader2.close();
                                    } catch (IOException unused) {
                                    }
                                }
                            } catch (IOException e6) {
                                e2 = e6;
                                arrayList2 = arrayList;
                                entry = null;
                                inputStreamReader = null;
                                sb.append("*** ");
                                sb.append(e2.toString());
                                sb.append("\n");
                                Log.e("DropBoxManagerService", "Can't read: " + next.file, e2);
                                if (entry != null) {
                                }
                                if (inputStreamReader != null) {
                                }
                                if (z) {
                                }
                                i = i5;
                                size = i2;
                                arrayList = arrayList2;
                            } catch (Throwable th6) {
                            }
                        }
                        if (z) {
                            sb.append("\n");
                        }
                        i = i5;
                        size = i2;
                        arrayList = arrayList2;
                    }
                    arrayList2 = arrayList;
                    i2 = size;
                    i = i5;
                    size = i2;
                    arrayList = arrayList2;
                }
            }
            if (i == 0) {
                sb.append("(No entries found.)\n");
            }
            if (strArr == null || strArr.length == 0) {
                if (!z) {
                    sb.append("\n");
                }
                sb.append("Usage: dumpsys dropbox [--print|--file] [YYYY-mm-dd] [HH:MM:SS] [tag]\n");
            }
            printWriter.write(sb.toString());
            return;
        } catch (IOException e7) {
            printWriter.println("Can't initialize: " + e7);
            Log.e("DropBoxManagerService", "Can't init", e7);
            return;
        }
        inputStreamReader = inputStreamReader2;
        entry = entry2;
        sb.append("*** ");
        sb.append(e2.toString());
        sb.append("\n");
        Log.e("DropBoxManagerService", "Can't read: " + next.file, e2);
        if (entry != null) {
        }
        if (inputStreamReader != null) {
        }
        if (z) {
        }
        i = i5;
        size = i2;
        arrayList = arrayList2;
    }

    @Override // com.miui.internal.server.IDropBoxManagerService
    public synchronized DropBoxManager.Entry getNextEntry(String str, long j) {
        if (this.mContext.checkCallingOrSelfPermission("miui.permission.READ_LOGS") == 0) {
            try {
                init();
                FileList fileList = str == null ? this.mAllFiles : this.mFilesByTag.get(str);
                if (fileList == null) {
                    return null;
                }
                for (EntryFile entryFile : fileList.contents.tailSet(new EntryFile(j + 1))) {
                    if (entryFile.tag != null) {
                        if ((entryFile.flags & 1) != 0) {
                            return new DropBoxManager.Entry(entryFile.tag, entryFile.timestampMillis);
                        }
                        try {
                            return new DropBoxManager.Entry(entryFile.tag, entryFile.timestampMillis, entryFile.file, entryFile.flags);
                        } catch (IOException e2) {
                            Log.e("DropBoxManagerService", "Can't read: " + entryFile.file, e2);
                        }
                    }
                }
                return null;
            } catch (IOException e3) {
                Log.e("DropBoxManagerService", "Can't init", e3);
                return null;
            }
        } else {
            throw new SecurityException("READ_LOGS permission required");
        }
    }

    @Override // com.miui.internal.server.IDropBoxManagerService
    public boolean isTagEnabled(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return !"disabled".equals(DROPBOX_TAG_PREFIX + str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
