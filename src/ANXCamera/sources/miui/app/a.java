package miui.app;

import android.widget.CompoundButton;

/* compiled from: lambda */
public final /* synthetic */ class a implements CompoundButton.OnCheckedChangeListener {
    private final /* synthetic */ DatePickerDialog tg;

    public /* synthetic */ a(DatePickerDialog datePickerDialog) {
        this.tg = datePickerDialog;
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.tg.a(compoundButton, z);
    }
}
