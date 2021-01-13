package miui.os;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.miui.internal.server.CoreService;
import com.miui.internal.server.DropBoxManagerService;
import com.miui.internal.server.IDropBoxManagerService;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.zip.GZIPInputStream;
import miui.util.AppConstants;
import miui.util.SoftReferenceSingleton;

public class DropBoxManager {
    public static final String ACTION_DROPBOX_ENTRY_ADDED = "miui.intent.action.DROPBOX_ENTRY_ADDED";
    public static final String EXTRA_TAG = "tag";
    public static final String EXTRA_TIME = "time";
    private static final int HAS_BYTE_ARRAY = 8;
    private static final SoftReferenceSingleton<DropBoxManager> INSTANCE = new SoftReferenceSingleton<DropBoxManager>() {
        /* class miui.os.DropBoxManager.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // miui.util.SoftReferenceSingleton
        public DropBoxManager createInstance() {
            return new DropBoxManager();
        }
    };
    public static final int IS_EMPTY = 1;
    public static final int IS_GZIPPED = 4;
    public static final int IS_TEXT = 2;
    private static final int QUEUE_QUOTA = 100;
    private static final String TAG = "DropBoxManager";
    private ServiceConnection mConn;
    private ArrayBlockingQueue<Entry> mEntries;

    public static class Entry implements Parcelable, Closeable {
        public static final Parcelable.Creator<Entry> CREATOR = new Parcelable.Creator() {
            /* class miui.os.DropBoxManager.Entry.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public Entry createFromParcel(Parcel parcel) {
                String readString = parcel.readString();
                long readLong = parcel.readLong();
                int readInt = parcel.readInt();
                return (readInt & 8) != 0 ? new Entry(readString, readLong, parcel.createByteArray(), readInt & -9) : new Entry(readString, readLong, parcel.readFileDescriptor(), readInt);
            }

            @Override // android.os.Parcelable.Creator
            public Entry[] newArray(int i) {
                return new Entry[i];
            }
        };
        private final byte[] mData;
        private final ParcelFileDescriptor mFileDescriptor;
        private final int mFlags;
        private final String mTag;
        private final long mTimeMillis;

        public Entry(String str, long j) {
            if (str != null) {
                this.mTag = str;
                this.mTimeMillis = j;
                this.mData = null;
                this.mFileDescriptor = null;
                this.mFlags = 1;
                return;
            }
            throw new NullPointerException("tag == null");
        }

        public Entry(String str, long j, ParcelFileDescriptor parcelFileDescriptor, int i) {
            if (str != null) {
                if (((i & 1) != 0) == (parcelFileDescriptor == null)) {
                    this.mTag = str;
                    this.mTimeMillis = j;
                    this.mData = null;
                    this.mFileDescriptor = parcelFileDescriptor;
                    this.mFlags = i;
                    return;
                }
                throw new IllegalArgumentException("Bad flags: " + i);
            }
            throw new NullPointerException("tag == null");
        }

        public Entry(String str, long j, File file, int i) throws IOException {
            if (str == null) {
                throw new NullPointerException("tag == null");
            } else if ((i & 1) == 0) {
                this.mTag = str;
                this.mTimeMillis = j;
                this.mData = null;
                this.mFileDescriptor = ParcelFileDescriptor.open(file, 268435456);
                this.mFlags = i;
            } else {
                throw new IllegalArgumentException("Bad flags: " + i);
            }
        }

        public Entry(String str, long j, String str2) {
            if (str == null) {
                throw new NullPointerException("tag == null");
            } else if (str2 != null) {
                this.mTag = str;
                this.mTimeMillis = j;
                this.mData = str2.getBytes();
                this.mFileDescriptor = null;
                this.mFlags = 2;
            } else {
                throw new NullPointerException("text == null");
            }
        }

        public Entry(String str, long j, byte[] bArr, int i) {
            if (str != null) {
                if (((i & 1) != 0) == (bArr == null)) {
                    this.mTag = str;
                    this.mTimeMillis = j;
                    this.mData = bArr;
                    this.mFileDescriptor = null;
                    this.mFlags = i;
                    return;
                }
                throw new IllegalArgumentException("Bad flags: " + i);
            }
            throw new NullPointerException("tag == null");
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            try {
                if (this.mFileDescriptor != null) {
                    this.mFileDescriptor.close();
                }
            } catch (IOException unused) {
            }
        }

        public int describeContents() {
            return this.mFileDescriptor != null ? 1 : 0;
        }

        public int getFlags() {
            return this.mFlags & -5;
        }

        public InputStream getInputStream() throws IOException {
            InputStream inputStream;
            byte[] bArr = this.mData;
            if (bArr != null) {
                inputStream = new ByteArrayInputStream(bArr);
            } else {
                ParcelFileDescriptor parcelFileDescriptor = this.mFileDescriptor;
                if (parcelFileDescriptor == null) {
                    return null;
                }
                inputStream = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
            }
            return (this.mFlags & 4) != 0 ? new GZIPInputStream(inputStream) : inputStream;
        }

        public String getTag() {
            return this.mTag;
        }

        /* JADX WARNING: Removed duplicated region for block: B:31:0x0045 A[SYNTHETIC, Splitter:B:31:0x0045] */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x004c A[SYNTHETIC, Splitter:B:39:0x004c] */
        public String getText(int i) {
            InputStream inputStream;
            Throwable th;
            if ((this.mFlags & 2) == 0) {
                return null;
            }
            byte[] bArr = this.mData;
            if (bArr != null) {
                return new String(bArr, 0, Math.min(i, bArr.length));
            }
            try {
                inputStream = getInputStream();
                if (inputStream == null) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused) {
                        }
                    }
                    return null;
                }
                try {
                    byte[] bArr2 = new byte[i];
                    int i2 = 0;
                    int i3 = 0;
                    while (i2 >= 0) {
                        i3 += i2;
                        if (i3 >= i) {
                            break;
                        }
                        i2 = inputStream.read(bArr2, i3, i - i3);
                    }
                    String str = new String(bArr2, 0, i3);
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    return str;
                } catch (IOException unused3) {
                    if (inputStream != null) {
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (inputStream != null) {
                    }
                    throw th;
                }
            } catch (IOException unused4) {
                inputStream = null;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException unused5) {
                    }
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
                inputStream = null;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException unused6) {
                    }
                }
                throw th;
            }
        }

        public long getTimeMillis() {
            return this.mTimeMillis;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mTag);
            parcel.writeLong(this.mTimeMillis);
            if (this.mFileDescriptor != null) {
                parcel.writeInt(this.mFlags & -9);
                this.mFileDescriptor.writeToParcel(parcel, i);
                return;
            }
            parcel.writeInt(this.mFlags | 8);
            parcel.writeByteArray(this.mData);
        }
    }

    private DropBoxManager() {
        this.mConn = new ServiceConnection() {
            /* class miui.os.DropBoxManager.AnonymousClass2 */

            /* JADX INFO: finally extract failed */
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                IDropBoxManagerService asInterface = IDropBoxManagerService.Stub.asInterface(iBinder);
                try {
                    Entry entry = (Entry) DropBoxManager.this.mEntries.poll();
                    while (entry != null) {
                        try {
                            asInterface.add(entry);
                            entry.close();
                            entry = (Entry) DropBoxManager.this.mEntries.poll();
                        } catch (Throwable th) {
                            entry.close();
                            throw th;
                        }
                    }
                    AppConstants.getCurrentApplication().unbindService(this);
                } catch (RemoteException e2) {
                    Log.w(DropBoxManager.TAG, "Call remote method 'add' failed", e2);
                }
            }

            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        this.mEntries = new ArrayBlockingQueue<>(100);
    }

    private void addEntry(Entry entry) {
        if (!this.mEntries.offer(entry)) {
            Log.w(TAG, "Fail to addEntry for queue is full");
            entry.close();
            return;
        }
        bindService();
    }

    private boolean bindService() {
        Application currentApplication = AppConstants.getCurrentApplication();
        Intent intent = new Intent(CoreService.ACTION_BIND_SERVICE);
        intent.setPackage("com.miui.core");
        intent.putExtra(CoreService.EXTRA_SERVICE_NAME, DropBoxManagerService.SERVICE_NAME);
        try {
            return currentApplication.bindService(intent, this.mConn, 1);
        } catch (Exception e2) {
            Log.e(TAG, "Fail to bind service", e2);
            return false;
        }
    }

    public static DropBoxManager getInstance() {
        return INSTANCE.get();
    }

    public void addData(String str, byte[] bArr, int i) {
        if (bArr != null) {
            addEntry(new Entry(str, 0, bArr, i));
            return;
        }
        throw new NullPointerException("data == null");
    }

    public void addFile(String str, File file, int i) throws IOException {
        if (file != null) {
            addEntry(new Entry(str, 0, file, i));
            return;
        }
        throw new NullPointerException("file == null");
    }

    public void addText(String str, String str2) {
        addEntry(new Entry(str, 0, str2));
    }
}
