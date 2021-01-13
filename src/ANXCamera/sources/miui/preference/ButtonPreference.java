package miui.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.miui.internal.R;
import miui.view.EditActionMode;

public class ButtonPreference extends Preference {
    private Preference.OnPreferenceClickListener mListener;
    private String mText;

    public ButtonPreference(Context context) {
        this(context, null);
    }

    public ButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.buttonPreferenceStyle);
    }

    public ButtonPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onBindView(View view) {
        super.onBindView(view);
        view.setBackground(null);
        Button button = (Button) view.findViewById(EditActionMode.BUTTON1);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                /* class miui.preference.ButtonPreference.AnonymousClass1 */

                public void onClick(View view) {
                    if (ButtonPreference.this.mListener != null) {
                        ButtonPreference.this.mListener.onPreferenceClick(ButtonPreference.this);
                    }
                }
            });
            button.setText(this.mText);
        }
    }

    public void setOnPreferenceClickListener(Preference.OnPreferenceClickListener onPreferenceClickListener) {
        this.mListener = onPreferenceClickListener;
    }

    public void setText(int i) {
        setText(getContext().getString(i));
    }

    public void setText(String str) {
        this.mText = str;
        notifyChanged();
    }
}
