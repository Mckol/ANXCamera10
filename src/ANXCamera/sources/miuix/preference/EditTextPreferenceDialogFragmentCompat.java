package miuix.preference;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.preference.EditTextPreferenceDialogFragment;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import miui.app.AlertDialog;

public class EditTextPreferenceDialogFragmentCompat extends EditTextPreferenceDialogFragment {
    private PreferenceDialogFragmentCompatDelegate mDelegate = new PreferenceDialogFragmentCompatDelegate(this.mImpl, this);
    private IPreferenceDialogFragment mImpl = new IPreferenceDialogFragment() {
        /* class miuix.preference.EditTextPreferenceDialogFragmentCompat.AnonymousClass1 */

        @Override // miuix.preference.IPreferenceDialogFragment
        public boolean needInputMethod() {
            return true;
        }

        @Override // miuix.preference.IPreferenceDialogFragment
        public void onBindDialogView(View view) {
            EditTextPreferenceDialogFragmentCompat.this.onBindDialogView(view);
        }

        @Override // miuix.preference.IPreferenceDialogFragment
        public View onCreateDialogView(Context context) {
            return EditTextPreferenceDialogFragmentCompat.this.onCreateDialogView(context);
        }

        @Override // miuix.preference.IPreferenceDialogFragment
        public void onPrepareDialogBuilder(AlertDialog.Builder builder) {
            EditTextPreferenceDialogFragmentCompat.this.onPrepareDialogBuilder(builder);
        }
    };

    public static EditTextPreferenceDialogFragmentCompat newInstance(String str) {
        EditTextPreferenceDialogFragmentCompat editTextPreferenceDialogFragmentCompat = new EditTextPreferenceDialogFragmentCompat();
        Bundle bundle = new Bundle(1);
        bundle.putString(WatermarkConstant.ITEM_KEY, str);
        editTextPreferenceDialogFragmentCompat.setArguments(bundle);
        return editTextPreferenceDialogFragmentCompat;
    }

    @Override // androidx.preference.PreferenceDialogFragment
    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        return this.mDelegate.onCreateDialog(bundle);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.preference.PreferenceDialogFragment
    public final void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        throw new UnsupportedOperationException("using miuix builder instead");
    }

    /* access modifiers changed from: protected */
    public void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(new BuilderDelegate(getActivity(), builder));
    }
}
