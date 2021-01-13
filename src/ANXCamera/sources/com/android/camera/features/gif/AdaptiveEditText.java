package com.android.camera.features.gif;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint({"AppCompatCustomView"})
public class AdaptiveEditText extends EditText {
    public static final String TAG = " AdaptiveTv";
    private float mMaxFontScale = 1.0f;
    private float mRealFontScale = 0.0f;

    public AdaptiveEditText(Context context) {
        super(context);
    }

    public AdaptiveEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AdaptiveEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public AdaptiveEditText(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.TextView, android.widget.EditText
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        super.setText(charSequence, bufferType);
        float f = getResources().getConfiguration().fontScale;
        if (f > this.mMaxFontScale) {
            if (this.mRealFontScale == 0.0f) {
                this.mRealFontScale = getTextSize() * (this.mMaxFontScale / f);
            }
            setTextSize(0, this.mRealFontScale);
            Log.i(" AdaptiveTv", "setText:  mMaxFontScale :" + this.mMaxFontScale + "   mRealFontScale : " + this.mRealFontScale);
        }
    }

    public void setmMaxFontScale(float f) {
        this.mMaxFontScale = f;
    }
}
