package com.airbnb.lottie.d;

import android.util.Log;
import com.airbnb.lottie.C0027e;
import com.airbnb.lottie.U;
import java.util.HashSet;
import java.util.Set;

/* compiled from: LogcatLogger */
public class c implements U {
    private static final Set<String> Zf = new HashSet();

    @Override // com.airbnb.lottie.U
    public void b(String str, Throwable th) {
        if (C0027e.DBG) {
            Log.d(C0027e.TAG, str, th);
        }
    }

    @Override // com.airbnb.lottie.U
    public void c(String str, Throwable th) {
        if (!Zf.contains(str)) {
            Log.w(C0027e.TAG, str, th);
            Zf.add(str);
        }
    }

    @Override // com.airbnb.lottie.U
    public void debug(String str) {
        f(str, null);
    }

    @Override // com.airbnb.lottie.U
    public void f(String str, Throwable th) {
        if (C0027e.DBG) {
            Log.d(C0027e.TAG, str, th);
        }
    }

    @Override // com.airbnb.lottie.U
    public void warning(String str) {
        c(str, null);
    }
}
