package com.airbnb.lottie;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import java.util.HashMap;
import java.util.Map;

/* compiled from: TextDelegate */
public class ea {
    private final Map<String, String> Sb;
    @Nullable
    private final LottieAnimationView Tb;
    private boolean Ub;
    @Nullable
    private final Q drawable;

    @VisibleForTesting
    ea() {
        this.Sb = new HashMap();
        this.Ub = true;
        this.Tb = null;
        this.drawable = null;
    }

    public ea(LottieAnimationView lottieAnimationView) {
        this.Sb = new HashMap();
        this.Ub = true;
        this.Tb = lottieAnimationView;
        this.drawable = null;
    }

    public ea(Q q) {
        this.Sb = new HashMap();
        this.Ub = true;
        this.drawable = q;
        this.Tb = null;
    }

    private String getText(String str) {
        return str;
    }

    private void invalidate() {
        LottieAnimationView lottieAnimationView = this.Tb;
        if (lottieAnimationView != null) {
            lottieAnimationView.invalidate();
        }
        Q q = this.drawable;
        if (q != null) {
            q.invalidateSelf();
        }
    }

    public void A(boolean z) {
        this.Ub = z;
    }

    public final String I(String str) {
        if (this.Ub && this.Sb.containsKey(str)) {
            return this.Sb.get(str);
        }
        getText(str);
        if (this.Ub) {
            this.Sb.put(str, str);
        }
        return str;
    }

    public void J(String str) {
        this.Sb.remove(str);
        invalidate();
    }

    public void ob() {
        this.Sb.clear();
        invalidate();
    }

    public void p(String str, String str2) {
        this.Sb.put(str, str2);
        invalidate();
    }
}
