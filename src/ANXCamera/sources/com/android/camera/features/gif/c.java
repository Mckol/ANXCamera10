package com.android.camera.features.gif;

import android.view.KeyEvent;
import android.widget.TextView;

/* compiled from: lambda */
public final /* synthetic */ class c implements TextView.OnEditorActionListener {
    public static final /* synthetic */ c INSTANCE = new c();

    private /* synthetic */ c() {
    }

    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return GifEditLayout.a(textView, i, keyEvent);
    }
}
