package com.airbnb.lottie.model;

import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: KeyPath */
public class d {
    @Nullable
    private e Xd;
    private final List<String> keys;

    private d(d dVar) {
        this.keys = new ArrayList(dVar.keys);
        this.Xd = dVar.Xd;
    }

    public d(String... strArr) {
        this.keys = Arrays.asList(strArr);
    }

    private boolean Vo() {
        List<String> list = this.keys;
        return list.get(list.size() - 1).equals("**");
    }

    private boolean da(String str) {
        return "__container".equals(str);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public e Fb() {
        return this.Xd;
    }

    public String Gb() {
        return this.keys.toString();
    }

    @CheckResult
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public d M(String str) {
        d dVar = new d(this);
        dVar.keys.add(str);
        return dVar;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public d a(e eVar) {
        d dVar = new d(this);
        dVar.Xd = eVar;
        return dVar;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean d(String str, int i) {
        if (i >= this.keys.size()) {
            return false;
        }
        boolean z = i == this.keys.size() - 1;
        String str2 = this.keys.get(i);
        if (!str2.equals("**")) {
            return (z || (i == this.keys.size() + -2 && Vo())) && (str2.equals(str) || str2.equals("*"));
        }
        if (!z && this.keys.get(i + 1).equals(str)) {
            return i == this.keys.size() + -2 || (i == this.keys.size() + -3 && Vo());
        }
        if (z) {
            return true;
        }
        int i2 = i + 1;
        if (i2 < this.keys.size() - 1) {
            return false;
        }
        return this.keys.get(i2).equals(str);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int e(String str, int i) {
        if (da(str)) {
            return 0;
        }
        if (!this.keys.get(i).equals("**")) {
            return 1;
        }
        return (i != this.keys.size() - 1 && this.keys.get(i + 1).equals(str)) ? 2 : 0;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean f(String str, int i) {
        if (da(str)) {
            return true;
        }
        if (i >= this.keys.size()) {
            return false;
        }
        return this.keys.get(i).equals(str) || this.keys.get(i).equals("**") || this.keys.get(i).equals("*");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean g(String str, int i) {
        return "__container".equals(str) || i < this.keys.size() - 1 || this.keys.get(i).equals("**");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("KeyPath{keys=");
        sb.append(this.keys);
        sb.append(",resolved=");
        sb.append(this.Xd != null);
        sb.append('}');
        return sb.toString();
    }
}
