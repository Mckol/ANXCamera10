package miui.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import com.miui.internal.R;
import miui.widget.DateTimePicker;
import miui.widget.SlidingButton;

public class DateTimePickerDialog extends AlertDialog {
    private View mLunarModePanel;
    private SlidingButton mLunarModeState;
    private OnTimeSetListener mTimeListener;
    private DateTimePicker mTimePicker;

    public interface OnTimeSetListener {
        void onTimeSet(DateTimePickerDialog dateTimePickerDialog, long j);
    }

    public DateTimePickerDialog(Context context, OnTimeSetListener onTimeSetListener) {
        this(context, onTimeSetListener, 1);
    }

    public DateTimePickerDialog(Context context, OnTimeSetListener onTimeSetListener, int i) {
        super(context);
        this.mTimeListener = onTimeSetListener;
        init(i);
        setTitle(R.string.date_time_picker_dialog_title);
    }

    private void init(int i) {
        setButton(-1, getContext().getText(17039370), new DialogInterface.OnClickListener() {
            /* class miui.app.DateTimePickerDialog.AnonymousClass1 */

            public void onClick(DialogInterface dialogInterface, int i) {
                if (DateTimePickerDialog.this.mTimeListener != null) {
                    OnTimeSetListener onTimeSetListener = DateTimePickerDialog.this.mTimeListener;
                    DateTimePickerDialog dateTimePickerDialog = DateTimePickerDialog.this;
                    onTimeSetListener.onTimeSet(dateTimePickerDialog, dateTimePickerDialog.mTimePicker.getTimeInMillis());
                }
            }
        });
        setButton(-2, getContext().getText(17039360), (DialogInterface.OnClickListener) null);
        View inflate = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.datetime_picker_dialog, (ViewGroup) null);
        setView(inflate);
        this.mTimePicker = (DateTimePicker) inflate.findViewById(R.id.dateTimePicker);
        this.mTimePicker.setMinuteInterval(i);
        this.mLunarModePanel = inflate.findViewById(R.id.lunarModePanel);
        this.mLunarModeState = (SlidingButton) inflate.findViewById(R.id.datePickerLunar);
        this.mLunarModeState.setOnPerformCheckedChangeListener(new b(this));
    }

    public /* synthetic */ void b(CompoundButton compoundButton, boolean z) {
        this.mTimePicker.setLunarMode(z);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
    }

    public void setLunarMode(boolean z) {
        this.mLunarModePanel.setVisibility(z ? 0 : 8);
    }

    public void setMaxDateTime(long j) {
        this.mTimePicker.setMaxDateTime(j);
    }

    public void setMinDateTime(long j) {
        this.mTimePicker.setMinDateTime(j);
    }

    public void switchLunarState(boolean z) {
        this.mLunarModeState.setChecked(z);
    }

    public void update(long j) {
        this.mTimePicker.update(j);
    }
}
