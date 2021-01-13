package miui.app;

import android.widget.CompoundButton;

/* compiled from: lambda */
public final /* synthetic */ class b implements CompoundButton.OnCheckedChangeListener {
    private final /* synthetic */ DateTimePickerDialog tg;

    public /* synthetic */ b(DateTimePickerDialog dateTimePickerDialog) {
        this.tg = dateTimePickerDialog;
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.tg.b(compoundButton, z);
    }
}
