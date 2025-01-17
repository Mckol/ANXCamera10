package androidx.core.os;

import android.os.Build;
import android.os.LocaleList;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.Size;
import java.util.Locale;

public final class LocaleListCompat {
    private static final LocaleListCompat sEmptyLocaleList = create(new Locale[0]);
    private LocaleListInterface mImpl;

    private LocaleListCompat(LocaleListInterface localeListInterface) {
        this.mImpl = localeListInterface;
    }

    @NonNull
    public static LocaleListCompat create(@NonNull Locale... localeArr) {
        return Build.VERSION.SDK_INT >= 24 ? wrap(new LocaleList(localeArr)) : new LocaleListCompat(new LocaleListCompatWrapper(localeArr));
    }

    static Locale forLanguageTagCompat(String str) {
        if (str.contains("-")) {
            String[] split = str.split("-", -1);
            if (split.length > 2) {
                return new Locale(split[0], split[1], split[2]);
            }
            if (split.length > 1) {
                return new Locale(split[0], split[1]);
            }
            if (split.length == 1) {
                return new Locale(split[0]);
            }
        } else if (!str.contains("_")) {
            return new Locale(str);
        } else {
            String[] split2 = str.split("_", -1);
            if (split2.length > 2) {
                return new Locale(split2[0], split2[1], split2[2]);
            }
            if (split2.length > 1) {
                return new Locale(split2[0], split2[1]);
            }
            if (split2.length == 1) {
                return new Locale(split2[0]);
            }
        }
        throw new IllegalArgumentException("Can not parse language tag: [" + str + "]");
    }

    @NonNull
    public static LocaleListCompat forLanguageTags(@Nullable String str) {
        if (str == null || str.isEmpty()) {
            return getEmptyLocaleList();
        }
        String[] split = str.split(",", -1);
        Locale[] localeArr = new Locale[split.length];
        for (int i = 0; i < localeArr.length; i++) {
            localeArr[i] = Build.VERSION.SDK_INT >= 21 ? Locale.forLanguageTag(split[i]) : forLanguageTagCompat(split[i]);
        }
        return create(localeArr);
    }

    @NonNull
    @Size(min = 1)
    public static LocaleListCompat getAdjustedDefault() {
        if (Build.VERSION.SDK_INT >= 24) {
            return wrap(LocaleList.getAdjustedDefault());
        }
        return create(Locale.getDefault());
    }

    @NonNull
    @Size(min = 1)
    public static LocaleListCompat getDefault() {
        if (Build.VERSION.SDK_INT >= 24) {
            return wrap(LocaleList.getDefault());
        }
        return create(Locale.getDefault());
    }

    @NonNull
    public static LocaleListCompat getEmptyLocaleList() {
        return sEmptyLocaleList;
    }

    @NonNull
    @RequiresApi(24)
    public static LocaleListCompat wrap(@NonNull LocaleList localeList) {
        return new LocaleListCompat(new LocaleListPlatformWrapper(localeList));
    }

    @RequiresApi(24)
    @Deprecated
    public static LocaleListCompat wrap(Object obj) {
        return wrap((LocaleList) obj);
    }

    public boolean equals(Object obj) {
        return (obj instanceof LocaleListCompat) && this.mImpl.equals(((LocaleListCompat) obj).mImpl);
    }

    public Locale get(int i) {
        return this.mImpl.get(i);
    }

    @Nullable
    public Locale getFirstMatch(@NonNull String[] strArr) {
        return this.mImpl.getFirstMatch(strArr);
    }

    public int hashCode() {
        return this.mImpl.hashCode();
    }

    @IntRange(from = -1)
    public int indexOf(Locale locale) {
        return this.mImpl.indexOf(locale);
    }

    public boolean isEmpty() {
        return this.mImpl.isEmpty();
    }

    @IntRange(from = 0)
    public int size() {
        return this.mImpl.size();
    }

    @NonNull
    public String toLanguageTags() {
        return this.mImpl.toLanguageTags();
    }

    public String toString() {
        return this.mImpl.toString();
    }

    @Nullable
    public Object unwrap() {
        return this.mImpl.getLocaleList();
    }
}
