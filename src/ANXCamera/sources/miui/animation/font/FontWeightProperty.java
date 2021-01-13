package miui.animation.font;

import android.view.View;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.Objects;
import miui.animation.property.ISpecificProperty;
import miui.animation.property.ViewProperty;

public class FontWeightProperty extends ViewProperty implements ISpecificProperty {
    private static final String NAME = "fontweight";
    private float mCurWeight = Float.MAX_VALUE;
    private int mFontType;
    private WeakReference<TextView> mTextViewRef;

    public FontWeightProperty(TextView textView, int i) {
        super(NAME);
        this.mTextViewRef = new WeakReference<>(textView);
        this.mFontType = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || FontWeightProperty.class != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        TextView textView = this.mTextViewRef.get();
        return textView != null && textView.equals(((FontWeightProperty) obj).mTextViewRef.get());
    }

    public int getFontType() {
        return this.mFontType;
    }

    public float getScaledTextSize() {
        TextView textView = this.mTextViewRef.get();
        if (textView != null) {
            return textView.getTextSize() / textView.getResources().getDisplayMetrics().scaledDensity;
        }
        return 0.0f;
    }

    @Override // miui.animation.property.ISpecificProperty
    public float getSpecificValue(float f) {
        if (f >= ((float) VarFontUtils.MIN_WGHT)) {
            return f;
        }
        return (float) VarFontUtils.getScaleWght((int) f, getScaledTextSize(), this.mFontType, VarFontUtils.getSysFontScale());
    }

    public TextView getTextView() {
        return this.mTextViewRef.get();
    }

    public float getValue(View view) {
        return this.mCurWeight;
    }

    public int hashCode() {
        TextView textView = this.mTextViewRef.get();
        if (textView != null) {
            return Objects.hash(Integer.valueOf(super.hashCode()), textView);
        }
        return Objects.hash(Integer.valueOf(super.hashCode()), this.mTextViewRef);
    }

    public void setValue(View view, float f) {
        this.mCurWeight = f;
        TextView textView = this.mTextViewRef.get();
        if (textView != null) {
            VarFontUtils.setVariationFont(textView, (int) f);
        }
    }
}
