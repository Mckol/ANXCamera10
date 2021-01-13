package com.airbnb.lottie.network;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.core.util.Pair;
import com.airbnb.lottie.d.d;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: NetworkCache */
class a {
    private final Context sf;
    private final String url;

    a(Context context, String str) {
        this.sf = context.getApplicationContext();
        this.url = str;
    }

    private static String a(String str, FileExtension fileExtension, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("lottie_cache_");
        sb.append(str.replaceAll("\\W+", ""));
        sb.append(z ? fileExtension.Do() : fileExtension.extension);
        return sb.toString();
    }

    @Nullable
    private File fa(String str) throws FileNotFoundException {
        File file = new File(this.sf.getCacheDir(), a(str, FileExtension.JSON, false));
        if (file.exists()) {
            return file;
        }
        File file2 = new File(this.sf.getCacheDir(), a(str, FileExtension.ZIP, false));
        if (file2.exists()) {
            return file2;
        }
        return null;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public File a(InputStream inputStream, FileExtension fileExtension) throws IOException {
        File file = new File(this.sf.getCacheDir(), a(this.url, fileExtension, true));
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read != -1) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        return file;
                    }
                }
            } catch (Throwable th) {
                fileOutputStream.close();
                throw th;
            }
        } finally {
            inputStream.close();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(FileExtension fileExtension) {
        File file = new File(this.sf.getCacheDir(), a(this.url, fileExtension, true));
        File file2 = new File(file.getAbsolutePath().replace(".temp", ""));
        boolean renameTo = file.renameTo(file2);
        d.debug("Copying temp file to real file (" + file2 + ")");
        if (!renameTo) {
            d.warning("Unable to rename cache file " + file.getAbsolutePath() + " to " + file2.getAbsolutePath() + ".");
        }
    }

    /* access modifiers changed from: package-private */
    @Nullable
    @WorkerThread
    public Pair<FileExtension, InputStream> fetch() {
        try {
            File fa = fa(this.url);
            if (fa == null) {
                return null;
            }
            FileInputStream fileInputStream = new FileInputStream(fa);
            FileExtension fileExtension = fa.getAbsolutePath().endsWith(".zip") ? FileExtension.ZIP : FileExtension.JSON;
            d.debug("Cache hit for " + this.url + " at " + fa.getAbsolutePath());
            return new Pair<>(fileExtension, fileInputStream);
        } catch (FileNotFoundException unused) {
            return null;
        }
    }
}
