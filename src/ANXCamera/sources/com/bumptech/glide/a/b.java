package com.bumptech.glide.a;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: DiskLruCache */
public final class b implements Closeable {
    static final long ANY_SEQUENCE_NUMBER = -1;
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    static final String JOURNAL_FILE = "journal";
    static final String JOURNAL_FILE_BACKUP = "journal.bkp";
    static final String JOURNAL_FILE_TEMP = "journal.tmp";
    static final String MAGIC = "libcore.io.DiskLruCache";
    private static final String READ = "READ";
    private static final String REMOVE = "REMOVE";
    static final String VERSION_1 = "1";
    private final Callable<Void> Oc;
    private final int appVersion;
    /* access modifiers changed from: private */
    public final File directory;
    final ThreadPoolExecutor executorService;
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    /* access modifiers changed from: private */
    public Writer journalWriter;
    private final LinkedHashMap<String, c> lruEntries = new LinkedHashMap<>(0, 0.75f, true);
    private long maxSize;
    private long nextSequenceNumber = 0;
    /* access modifiers changed from: private */
    public int redundantOpCount;
    private long size = 0;
    /* access modifiers changed from: private */
    public final int valueCount;

    /* compiled from: DiskLruCache */
    private static final class a implements ThreadFactory {
        private a() {
        }

        /* synthetic */ a(a aVar) {
            this();
        }

        public synchronized Thread newThread(Runnable runnable) {
            Thread thread;
            thread = new Thread(runnable, "glide-disk-lru-cache-thread");
            thread.setPriority(1);
            return thread;
        }
    }

    /* renamed from: com.bumptech.glide.a.b$b reason: collision with other inner class name */
    /* compiled from: DiskLruCache */
    public final class C0003b {
        private boolean Nc;
        /* access modifiers changed from: private */
        public final c entry;
        /* access modifiers changed from: private */
        public final boolean[] written;

        private C0003b(c cVar) {
            this.entry = cVar;
            this.written = cVar.readable ? null : new boolean[b.this.valueCount];
        }

        /* synthetic */ C0003b(b bVar, c cVar, a aVar) {
            this(cVar);
        }

        private InputStream L(int i) throws IOException {
            synchronized (b.this) {
                if (this.entry.currentEditor != this) {
                    throw new IllegalStateException();
                } else if (!this.entry.readable) {
                    return null;
                } else {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(this.entry.p(i));
                        return fileInputStream;
                    } catch (FileNotFoundException unused) {
                        return null;
                    }
                }
            }
        }

        public void abort() throws IOException {
            b.this.a(this, false);
        }

        public void abortUnlessCommitted() {
            if (!this.Nc) {
                try {
                    abort();
                } catch (IOException unused) {
                }
            }
        }

        public void commit() throws IOException {
            b.this.a(this, true);
            this.Nc = true;
        }

        public String getString(int i) throws IOException {
            InputStream L = L(i);
            if (L != null) {
                return b.inputStreamToString(L);
            }
            return null;
        }

        public File o(int i) throws IOException {
            File q;
            synchronized (b.this) {
                if (this.entry.currentEditor == this) {
                    if (!this.entry.readable) {
                        this.written[i] = true;
                    }
                    q = this.entry.q(i);
                    if (!b.this.directory.exists()) {
                        b.this.directory.mkdirs();
                    }
                } else {
                    throw new IllegalStateException();
                }
            }
            return q;
        }

        public void set(int i, String str) throws IOException {
            OutputStreamWriter outputStreamWriter = null;
            try {
                OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(new FileOutputStream(o(i)), e.UTF_8);
                try {
                    outputStreamWriter2.write(str);
                    e.closeQuietly(outputStreamWriter2);
                } catch (Throwable th) {
                    th = th;
                    outputStreamWriter = outputStreamWriter2;
                    e.closeQuietly(outputStreamWriter);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                e.closeQuietly(outputStreamWriter);
                throw th;
            }
        }
    }

    /* compiled from: DiskLruCache */
    private final class c {
        File[] cleanFiles;
        /* access modifiers changed from: private */
        public C0003b currentEditor;
        File[] dirtyFiles;
        /* access modifiers changed from: private */
        public final String key;
        /* access modifiers changed from: private */
        public final long[] lengths;
        /* access modifiers changed from: private */
        public boolean readable;
        /* access modifiers changed from: private */
        public long sequenceNumber;

        private c(String str) {
            this.key = str;
            this.lengths = new long[b.this.valueCount];
            this.cleanFiles = new File[b.this.valueCount];
            this.dirtyFiles = new File[b.this.valueCount];
            StringBuilder sb = new StringBuilder(str);
            sb.append('.');
            int length = sb.length();
            for (int i = 0; i < b.this.valueCount; i++) {
                sb.append(i);
                this.cleanFiles[i] = new File(b.this.directory, sb.toString());
                sb.append(".tmp");
                this.dirtyFiles[i] = new File(b.this.directory, sb.toString());
                sb.setLength(length);
            }
        }

        /* synthetic */ c(b bVar, String str, a aVar) {
            this(str);
        }

        private IOException invalidLengths(String[] strArr) throws IOException {
            StringBuilder sb = new StringBuilder();
            sb.append("unexpected journal line: ");
            sb.append(Arrays.toString(strArr));
            throw new IOException(sb.toString());
        }

        /* access modifiers changed from: private */
        public void setLengths(String[] strArr) throws IOException {
            if (strArr.length == b.this.valueCount) {
                int i = 0;
                while (i < strArr.length) {
                    try {
                        this.lengths[i] = Long.parseLong(strArr[i]);
                        i++;
                    } catch (NumberFormatException unused) {
                        invalidLengths(strArr);
                        throw null;
                    }
                }
                return;
            }
            invalidLengths(strArr);
            throw null;
        }

        public String Zf() throws IOException {
            long[] jArr;
            StringBuilder sb = new StringBuilder();
            for (long j : this.lengths) {
                sb.append(' ');
                sb.append(j);
            }
            return sb.toString();
        }

        public File p(int i) {
            return this.cleanFiles[i];
        }

        public File q(int i) {
            return this.dirtyFiles[i];
        }
    }

    /* compiled from: DiskLruCache */
    public final class d {
        private final File[] files;
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;

        private d(String str, long j, File[] fileArr, long[] jArr) {
            this.key = str;
            this.sequenceNumber = j;
            this.files = fileArr;
            this.lengths = jArr;
        }

        /* synthetic */ d(b bVar, String str, long j, File[] fileArr, long[] jArr, a aVar) {
            this(str, j, fileArr, jArr);
        }

        public C0003b edit() throws IOException {
            return b.this.edit(this.key, this.sequenceNumber);
        }

        public long getLength(int i) {
            return this.lengths[i];
        }

        public String getString(int i) throws IOException {
            return b.inputStreamToString(new FileInputStream(this.files[i]));
        }

        public File o(int i) {
            return this.files[i];
        }
    }

    private b(File file, int i, int i2, long j) {
        File file2 = file;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), new a(null));
        this.executorService = threadPoolExecutor;
        this.Oc = new a(this);
        this.directory = file2;
        this.appVersion = i;
        this.journalFile = new File(file2, JOURNAL_FILE);
        this.journalFileTmp = new File(file2, JOURNAL_FILE_TEMP);
        this.journalFileBackup = new File(file2, JOURNAL_FILE_BACKUP);
        this.valueCount = i2;
        this.maxSize = j;
    }

    public static b a(File file, int i, int i2, long j) throws IOException {
        if (j <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (i2 > 0) {
            File file2 = new File(file, JOURNAL_FILE_BACKUP);
            if (file2.exists()) {
                File file3 = new File(file, JOURNAL_FILE);
                if (file3.exists()) {
                    file2.delete();
                } else {
                    a(file2, file3, false);
                }
            }
            b bVar = new b(file, i, i2, j);
            if (bVar.journalFile.exists()) {
                try {
                    bVar.readJournal();
                    bVar.processJournal();
                    return bVar;
                } catch (IOException e2) {
                    PrintStream printStream = System.out;
                    StringBuilder sb = new StringBuilder();
                    sb.append("DiskLruCache ");
                    sb.append(file);
                    sb.append(" is corrupt: ");
                    sb.append(e2.getMessage());
                    sb.append(", removing");
                    printStream.println(sb.toString());
                    bVar.delete();
                }
            }
            file.mkdirs();
            b bVar2 = new b(file, i, i2, j);
            bVar2.rebuildJournal();
            return bVar2;
        } else {
            throw new IllegalArgumentException("valueCount <= 0");
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0107, code lost:
        return;
     */
    public synchronized void a(C0003b bVar, boolean z) throws IOException {
        c a2 = bVar.entry;
        if (a2.currentEditor == bVar) {
            if (z && !a2.readable) {
                int i = 0;
                while (i < this.valueCount) {
                    if (!bVar.written[i]) {
                        bVar.abort();
                        StringBuilder sb = new StringBuilder();
                        sb.append("Newly created entry didn't create value for index ");
                        sb.append(i);
                        throw new IllegalStateException(sb.toString());
                    } else if (!a2.q(i).exists()) {
                        bVar.abort();
                        return;
                    } else {
                        i++;
                    }
                }
            }
            for (int i2 = 0; i2 < this.valueCount; i2++) {
                File q = a2.q(i2);
                if (!z) {
                    g(q);
                } else if (q.exists()) {
                    File p = a2.p(i2);
                    q.renameTo(p);
                    long j = a2.lengths[i2];
                    long length = p.length();
                    a2.lengths[i2] = length;
                    this.size = (this.size - j) + length;
                }
            }
            this.redundantOpCount++;
            a2.currentEditor = null;
            if (a2.readable || z) {
                a2.readable = true;
                this.journalWriter.append(CLEAN);
                this.journalWriter.append(' ');
                this.journalWriter.append(a2.key);
                this.journalWriter.append(a2.Zf());
                this.journalWriter.append(10);
                if (z) {
                    long j2 = this.nextSequenceNumber;
                    this.nextSequenceNumber = 1 + j2;
                    a2.sequenceNumber = j2;
                }
            } else {
                this.lruEntries.remove(a2.key);
                this.journalWriter.append(REMOVE);
                this.journalWriter.append(' ');
                this.journalWriter.append(a2.key);
                this.journalWriter.append(10);
            }
            this.journalWriter.flush();
            if (this.size > this.maxSize || journalRebuildRequired()) {
                this.executorService.submit(this.Oc);
            }
        } else {
            throw new IllegalStateException();
        }
    }

    private static void a(File file, File file2, boolean z) throws IOException {
        if (z) {
            g(file2);
        }
        if (!file.renameTo(file2)) {
            throw new IOException();
        }
    }

    private void checkNotClosed() {
        if (this.journalWriter == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001e, code lost:
        return null;
     */
    public synchronized C0003b edit(String str, long j) throws IOException {
        checkNotClosed();
        c cVar = (c) this.lruEntries.get(str);
        if (j == -1 || (cVar != null && cVar.sequenceNumber == j)) {
            if (cVar == null) {
                cVar = new c(this, str, null);
                this.lruEntries.put(str, cVar);
            } else if (cVar.currentEditor != null) {
                return null;
            }
            C0003b bVar = new C0003b(this, cVar, null);
            cVar.currentEditor = bVar;
            this.journalWriter.append(DIRTY);
            this.journalWriter.append(' ');
            this.journalWriter.append(str);
            this.journalWriter.append(10);
            this.journalWriter.flush();
            return bVar;
        }
    }

    private static void g(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    /* access modifiers changed from: private */
    public static String inputStreamToString(InputStream inputStream) throws IOException {
        return e.readFully(new InputStreamReader(inputStream, e.UTF_8));
    }

    /* access modifiers changed from: private */
    public boolean journalRebuildRequired() {
        int i = this.redundantOpCount;
        return i >= 2000 && i >= this.lruEntries.size();
    }

    private void processJournal() throws IOException {
        g(this.journalFileTmp);
        Iterator it = this.lruEntries.values().iterator();
        while (it.hasNext()) {
            c cVar = (c) it.next();
            int i = 0;
            if (cVar.currentEditor == null) {
                while (i < this.valueCount) {
                    this.size += cVar.lengths[i];
                    i++;
                }
            } else {
                cVar.currentEditor = null;
                while (i < this.valueCount) {
                    g(cVar.p(i));
                    g(cVar.q(i));
                    i++;
                }
                it.remove();
            }
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:16|17|(1:19)(1:20)|21|22) */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r8.redundantOpCount = r0 - r8.lruEntries.size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006c, code lost:
        if (r1._f() != false) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006e, code lost:
        rebuildJournal();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0072, code lost:
        r8.journalWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(r8.journalFile, true), com.bumptech.glide.a.e.US_ASCII));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x008b, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x005f */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:23:0x008c=Splitter:B:23:0x008c, B:16:0x005f=Splitter:B:16:0x005f} */
    private void readJournal() throws IOException {
        String str = ", ";
        d dVar = new d(new FileInputStream(this.journalFile), e.US_ASCII);
        try {
            String readLine = dVar.readLine();
            String readLine2 = dVar.readLine();
            String readLine3 = dVar.readLine();
            String readLine4 = dVar.readLine();
            String readLine5 = dVar.readLine();
            if (!MAGIC.equals(readLine) || !"1".equals(readLine2) || !Integer.toString(this.appVersion).equals(readLine3) || !Integer.toString(this.valueCount).equals(readLine4) || !"".equals(readLine5)) {
                StringBuilder sb = new StringBuilder();
                sb.append("unexpected journal header: [");
                sb.append(readLine);
                sb.append(str);
                sb.append(readLine2);
                sb.append(str);
                sb.append(readLine4);
                sb.append(str);
                sb.append(readLine5);
                sb.append("]");
                throw new IOException(sb.toString());
            }
            int i = 0;
            while (true) {
                readJournalLine(dVar.readLine());
                i++;
            }
        } finally {
            e.closeQuietly(dVar);
        }
    }

    private void readJournalLine(String str) throws IOException {
        String str2;
        int indexOf = str.indexOf(32);
        String str3 = "unexpected journal line: ";
        if (indexOf != -1) {
            int i = indexOf + 1;
            int indexOf2 = str.indexOf(32, i);
            if (indexOf2 == -1) {
                str2 = str.substring(i);
                if (indexOf == 6 && str.startsWith(REMOVE)) {
                    this.lruEntries.remove(str2);
                    return;
                }
            } else {
                str2 = str.substring(i, indexOf2);
            }
            c cVar = (c) this.lruEntries.get(str2);
            if (cVar == null) {
                cVar = new c(this, str2, null);
                this.lruEntries.put(str2, cVar);
            }
            if (indexOf2 != -1 && indexOf == 5 && str.startsWith(CLEAN)) {
                String[] split = str.substring(indexOf2 + 1).split(" ");
                cVar.readable = true;
                cVar.currentEditor = null;
                cVar.setLengths(split);
            } else if (indexOf2 == -1 && indexOf == 5 && str.startsWith(DIRTY)) {
                cVar.currentEditor = new C0003b(this, cVar, null);
            } else if (!(indexOf2 == -1 && indexOf == 4 && str.startsWith(READ))) {
                StringBuilder sb = new StringBuilder();
                sb.append(str3);
                sb.append(str);
                throw new IOException(sb.toString());
            }
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str3);
        sb2.append(str);
        throw new IOException(sb2.toString());
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public synchronized void rebuildJournal() throws IOException {
        if (this.journalWriter != null) {
            this.journalWriter.close();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFileTmp), e.US_ASCII));
        try {
            bufferedWriter.write(MAGIC);
            bufferedWriter.write("\n");
            bufferedWriter.write("1");
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.appVersion));
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.valueCount));
            bufferedWriter.write("\n");
            bufferedWriter.write("\n");
            for (c cVar : this.lruEntries.values()) {
                if (cVar.currentEditor != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("DIRTY ");
                    sb.append(cVar.key);
                    sb.append(10);
                    bufferedWriter.write(sb.toString());
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("CLEAN ");
                    sb2.append(cVar.key);
                    sb2.append(cVar.Zf());
                    sb2.append(10);
                    bufferedWriter.write(sb2.toString());
                }
            }
            bufferedWriter.close();
            if (this.journalFile.exists()) {
                a(this.journalFile, this.journalFileBackup, true);
            }
            a(this.journalFileTmp, this.journalFile, false);
            this.journalFileBackup.delete();
            this.journalWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.journalFile, true), e.US_ASCII));
        } catch (Throwable th) {
            bufferedWriter.close();
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public void trimToSize() throws IOException {
        while (this.size > this.maxSize) {
            remove((String) ((Entry) this.lruEntries.entrySet().iterator().next()).getKey());
        }
    }

    public synchronized void close() throws IOException {
        if (this.journalWriter != null) {
            Iterator it = new ArrayList(this.lruEntries.values()).iterator();
            while (it.hasNext()) {
                c cVar = (c) it.next();
                if (cVar.currentEditor != null) {
                    cVar.currentEditor.abort();
                }
            }
            trimToSize();
            this.journalWriter.close();
            this.journalWriter = null;
        }
    }

    public void delete() throws IOException {
        close();
        e.deleteContents(this.directory);
    }

    public C0003b edit(String str) throws IOException {
        return edit(str, -1);
    }

    public synchronized void flush() throws IOException {
        checkNotClosed();
        trimToSize();
        this.journalWriter.flush();
    }

    public synchronized d get(String str) throws IOException {
        checkNotClosed();
        c cVar = (c) this.lruEntries.get(str);
        if (cVar == null) {
            return null;
        }
        if (!cVar.readable) {
            return null;
        }
        for (File exists : cVar.cleanFiles) {
            if (!exists.exists()) {
                return null;
            }
        }
        this.redundantOpCount++;
        this.journalWriter.append(READ);
        this.journalWriter.append(' ');
        this.journalWriter.append(str);
        this.journalWriter.append(10);
        if (journalRebuildRequired()) {
            this.executorService.submit(this.Oc);
        }
        d dVar = new d(this, str, cVar.sequenceNumber, cVar.cleanFiles, cVar.lengths, null);
        return dVar;
    }

    public File getDirectory() {
        return this.directory;
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    public synchronized boolean isClosed() {
        return this.journalWriter == null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x008c, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x008e, code lost:
        return false;
     */
    public synchronized boolean remove(String str) throws IOException {
        checkNotClosed();
        c cVar = (c) this.lruEntries.get(str);
        if (cVar != null) {
            if (cVar.currentEditor == null) {
                for (int i = 0; i < this.valueCount; i++) {
                    File p = cVar.p(i);
                    if (p.exists()) {
                        if (!p.delete()) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("failed to delete ");
                            sb.append(p);
                            throw new IOException(sb.toString());
                        }
                    }
                    this.size -= cVar.lengths[i];
                    cVar.lengths[i] = 0;
                }
                this.redundantOpCount++;
                this.journalWriter.append(REMOVE);
                this.journalWriter.append(' ');
                this.journalWriter.append(str);
                this.journalWriter.append(10);
                this.lruEntries.remove(str);
                if (journalRebuildRequired()) {
                    this.executorService.submit(this.Oc);
                }
            }
        }
    }

    public synchronized void setMaxSize(long j) {
        this.maxSize = j;
        this.executorService.submit(this.Oc);
    }

    public synchronized long size() {
        return this.size;
    }
}
