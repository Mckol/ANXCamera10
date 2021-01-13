package miui.io;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.Log;
import com.ss.android.ugc.effectmanager.effect.model.ComposerHelper;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResettableInputStream extends InputStream {
    private final AssetManager mAssetManager;
    private final String mAssetPath;
    private final byte[] mByteArray;
    private final Context mContext;
    private final Object mFinalizeGuardian;
    private volatile InputStream mInputStream;
    private IOException mLastException;
    private Throwable mOpenStack;
    private final String mPath;
    private final Type mType;
    private final Uri mUri;

    /* access modifiers changed from: package-private */
    /* renamed from: miui.io.ResettableInputStream$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$miui$io$ResettableInputStream$Type = new int[Type.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            $SwitchMap$miui$io$ResettableInputStream$Type[Type.Uri.ordinal()] = 1;
            $SwitchMap$miui$io$ResettableInputStream$Type[Type.File.ordinal()] = 2;
            $SwitchMap$miui$io$ResettableInputStream$Type[Type.Asset.ordinal()] = 3;
            try {
                $SwitchMap$miui$io$ResettableInputStream$Type[Type.ByteArray.ordinal()] = 4;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    /* access modifiers changed from: private */
    public enum Type {
        File,
        Uri,
        Asset,
        ByteArray
    }

    public ResettableInputStream(Context context, Uri uri) {
        this.mFinalizeGuardian = new Object() {
            /* class miui.io.ResettableInputStream.AnonymousClass1 */

            /* access modifiers changed from: protected */
            public void finalize() throws Throwable {
                try {
                    if (ResettableInputStream.this.mOpenStack != null) {
                        Log.e("ResettableInputStream", "InputStream is opened but never closed here", ResettableInputStream.this.mOpenStack);
                    }
                    ResettableInputStream.this.close();
                } finally {
                    super.finalize();
                }
            }
        };
        if (ComposerHelper.COMPOSER_PATH.equals(uri.getScheme())) {
            this.mType = Type.File;
            this.mPath = uri.getPath();
            this.mContext = null;
            this.mUri = null;
            this.mAssetManager = null;
            this.mAssetPath = null;
            this.mByteArray = null;
            return;
        }
        this.mType = Type.Uri;
        this.mContext = context;
        this.mUri = uri;
        this.mPath = null;
        this.mAssetManager = null;
        this.mAssetPath = null;
        this.mByteArray = null;
    }

    public ResettableInputStream(AssetManager assetManager, String str) {
        this.mFinalizeGuardian = new Object() {
            /* class miui.io.ResettableInputStream.AnonymousClass1 */

            /* access modifiers changed from: protected */
            public void finalize() throws Throwable {
                try {
                    if (ResettableInputStream.this.mOpenStack != null) {
                        Log.e("ResettableInputStream", "InputStream is opened but never closed here", ResettableInputStream.this.mOpenStack);
                    }
                    ResettableInputStream.this.close();
                } finally {
                    super.finalize();
                }
            }
        };
        this.mType = Type.Asset;
        this.mAssetManager = assetManager;
        this.mAssetPath = str;
        this.mPath = null;
        this.mContext = null;
        this.mUri = null;
        this.mByteArray = null;
    }

    public ResettableInputStream(String str) {
        this.mFinalizeGuardian = new Object() {
            /* class miui.io.ResettableInputStream.AnonymousClass1 */

            /* access modifiers changed from: protected */
            public void finalize() throws Throwable {
                try {
                    if (ResettableInputStream.this.mOpenStack != null) {
                        Log.e("ResettableInputStream", "InputStream is opened but never closed here", ResettableInputStream.this.mOpenStack);
                    }
                    ResettableInputStream.this.close();
                } finally {
                    super.finalize();
                }
            }
        };
        this.mType = Type.File;
        this.mPath = str;
        this.mContext = null;
        this.mUri = null;
        this.mAssetManager = null;
        this.mAssetPath = null;
        this.mByteArray = null;
    }

    public ResettableInputStream(byte[] bArr) {
        this.mFinalizeGuardian = new Object() {
            /* class miui.io.ResettableInputStream.AnonymousClass1 */

            /* access modifiers changed from: protected */
            public void finalize() throws Throwable {
                try {
                    if (ResettableInputStream.this.mOpenStack != null) {
                        Log.e("ResettableInputStream", "InputStream is opened but never closed here", ResettableInputStream.this.mOpenStack);
                    }
                    ResettableInputStream.this.close();
                } finally {
                    super.finalize();
                }
            }
        };
        this.mType = Type.ByteArray;
        this.mByteArray = bArr;
        this.mPath = null;
        this.mContext = null;
        this.mUri = null;
        this.mAssetManager = null;
        this.mAssetPath = null;
    }

    private void openStream() throws IOException {
        IOException iOException = this.mLastException;
        if (iOException != null) {
            throw iOException;
        } else if (this.mInputStream == null) {
            synchronized (this.mFinalizeGuardian) {
                if (this.mInputStream == null) {
                    int i = AnonymousClass2.$SwitchMap$miui$io$ResettableInputStream$Type[this.mType.ordinal()];
                    if (i == 1) {
                        this.mInputStream = this.mContext.getContentResolver().openInputStream(this.mUri);
                    } else if (i == 2) {
                        this.mInputStream = new FileInputStream(this.mPath);
                    } else if (i == 3) {
                        this.mInputStream = this.mAssetManager.open(this.mAssetPath);
                    } else if (i == 4) {
                        this.mInputStream = new ByteArrayInputStream(this.mByteArray);
                    } else {
                        throw new IllegalStateException("Unkown type " + this.mType);
                    }
                    this.mOpenStack = new Throwable();
                }
            }
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        openStream();
        return this.mInputStream.available();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable, java.io.InputStream
    public void close() throws IOException {
        if (this.mInputStream != null) {
            synchronized (this.mFinalizeGuardian) {
                if (this.mInputStream != null) {
                    try {
                        this.mInputStream.close();
                    } finally {
                        this.mOpenStack = null;
                        this.mInputStream = null;
                        this.mLastException = null;
                    }
                }
            }
        }
    }

    public void mark(int i) {
        try {
            openStream();
            this.mInputStream.mark(i);
        } catch (IOException e2) {
            this.mLastException = e2;
        }
    }

    public boolean markSupported() {
        try {
            openStream();
            return this.mInputStream.markSupported();
        } catch (IOException e2) {
            this.mLastException = e2;
            return super.markSupported();
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        openStream();
        return this.mInputStream.read();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        openStream();
        return this.mInputStream.read(bArr);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        openStream();
        return this.mInputStream.read(bArr, i, i2);
    }

    @Override // java.io.InputStream
    public synchronized void reset() throws IOException {
        if (this.mInputStream != null) {
            if (this.mInputStream instanceof FileInputStream) {
                ((FileInputStream) this.mInputStream).getChannel().position(0L);
            } else if ((this.mInputStream instanceof AssetManager.AssetInputStream) || (this.mInputStream instanceof ByteArrayInputStream)) {
                this.mInputStream.reset();
            } else {
                close();
            }
        }
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        openStream();
        return this.mInputStream.skip(j);
    }
}
