package miuix.preference;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.preference.MultiSelectListPreferenceDialogFragment;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import miui.app.AlertDialog;

public class MultiSelectListPreferenceDialogFragmentCompat extends MultiSelectListPreferenceDialogFragment {
    private PreferenceDialogFragmentCompatDelegate mDelegate = new PreferenceDialogFragmentCompatDelegate(this.mImpl, this);
    private IPreferenceDialogFragment mImpl = new IPreferenceDialogFragment() {
        /* class miuix.preference.MultiSelectListPreferenceDialogFragmentCompat.AnonymousClass1 */

        @Override // miuix.preference.IPreferenceDialogFragment
        public boolean needInputMethod() {
            return false;
        }

        @Override // miuix.preference.IPreferenceDialogFragment
        public void onBindDialogView(View view) {
            MultiSelectListPreferenceDialogFragmentCompat.this.onBindDialogView(view);
        }

        @Override // miuix.preference.IPreferenceDialogFragment
        public View onCreateDialogView(Context context) {
            return MultiSelectListPreferenceDialogFragmentCompat.this.onCreateDialogView(context);
        }

        @Override // miuix.preference.IPreferenceDialogFragment
        public void onPrepareDialogBuilder(AlertDialog.Builder builder) {
            MultiSelectListPreferenceDialogFragmentCompat.this.onPrepareDialogBuilder(builder);
        }
    };

    public static MultiSelectListPreferenceDialogFragmentCompat newInstance(String str) {
        MultiSelectListPreferenceDialogFragmentCompat multiSelectListPreferenceDialogFragmentCompat = new MultiSelectListPreferenceDialogFragmentCompat();
        Bundle bundle = new Bundle(1);
        bundle.putString(WatermarkConstant.ITEM_KEY, str);
        multiSelectListPreferenceDialogFragmentCompat.setArguments(bundle);
        return multiSelectListPreferenceDialogFragmentCompat;
    }

    @Override // androidx.preference.PreferenceDialogFragment
    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        return this.mDelegate.onCreateDialog(bundle);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.preference.MultiSelectListPreferenceDialogFragment, androidx.preference.PreferenceDialogFragment
    public final void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        throw new UnsupportedOperationException("using miuix builder instead");
    }

    /* access modifiers changed from: protected */
    public void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(new BuilderDelegate(getActivity(), builder));
    }
}
