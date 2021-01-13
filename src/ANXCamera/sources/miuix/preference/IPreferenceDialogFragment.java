package miuix.preference;

import android.content.Context;
import android.view.View;
import miui.app.AlertDialog;

interface IPreferenceDialogFragment {
    boolean needInputMethod();

    void onBindDialogView(View view);

    View onCreateDialogView(Context context);

    void onPrepareDialogBuilder(AlertDialog.Builder builder);
}
