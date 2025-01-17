package androidx.core.provider;

import android.util.Base64;
import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import java.util.List;

public final class FontRequest {
    private final List<List<byte[]>> mCertificates;
    private final int mCertificatesArray;
    private final String mIdentifier;
    private final String mProviderAuthority;
    private final String mProviderPackage;
    private final String mQuery;

    public FontRequest(@NonNull String str, @NonNull String str2, @NonNull String str3, @ArrayRes int i) {
        Preconditions.checkNotNull(str);
        this.mProviderAuthority = str;
        Preconditions.checkNotNull(str2);
        this.mProviderPackage = str2;
        Preconditions.checkNotNull(str3);
        this.mQuery = str3;
        this.mCertificates = null;
        Preconditions.checkArgument(i != 0);
        this.mCertificatesArray = i;
        this.mIdentifier = this.mProviderAuthority + "-" + this.mProviderPackage + "-" + this.mQuery;
    }

    public FontRequest(@NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull List<List<byte[]>> list) {
        Preconditions.checkNotNull(str);
        this.mProviderAuthority = str;
        Preconditions.checkNotNull(str2);
        this.mProviderPackage = str2;
        Preconditions.checkNotNull(str3);
        this.mQuery = str3;
        Preconditions.checkNotNull(list);
        this.mCertificates = list;
        this.mCertificatesArray = 0;
        this.mIdentifier = this.mProviderAuthority + "-" + this.mProviderPackage + "-" + this.mQuery;
    }

    @Nullable
    public List<List<byte[]>> getCertificates() {
        return this.mCertificates;
    }

    @ArrayRes
    public int getCertificatesArrayResId() {
        return this.mCertificatesArray;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public String getIdentifier() {
        return this.mIdentifier;
    }

    @NonNull
    public String getProviderAuthority() {
        return this.mProviderAuthority;
    }

    @NonNull
    public String getProviderPackage() {
        return this.mProviderPackage;
    }

    @NonNull
    public String getQuery() {
        return this.mQuery;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FontRequest {mProviderAuthority: " + this.mProviderAuthority + ", mProviderPackage: " + this.mProviderPackage + ", mQuery: " + this.mQuery + ", mCertificates:");
        for (int i = 0; i < this.mCertificates.size(); i++) {
            sb.append(" [");
            List<byte[]> list = this.mCertificates.get(i);
            for (int i2 = 0; i2 < list.size(); i2++) {
                sb.append(" \"");
                sb.append(Base64.encodeToString(list.get(i2), 0));
                sb.append("\"");
            }
            sb.append(" ]");
        }
        sb.append("}");
        sb.append("mCertificatesArray: " + this.mCertificatesArray);
        return sb.toString();
    }
}
