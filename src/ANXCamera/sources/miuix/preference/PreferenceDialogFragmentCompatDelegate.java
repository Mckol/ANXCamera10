package miuix.preference;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import androidx.preference.DialogPreference;
import androidx.preference.PreferenceDialogFragment;
import miui.app.AlertDialog;

class PreferenceDialogFragmentCompatDelegate {
    private PreferenceDialogFragment mFragmentCompat;
    private IPreferenceDialogFragment mInternal;

    public PreferenceDialogFragmentCompatDelegate(IPreferenceDialogFragment iPreferenceDialogFragment, PreferenceDialogFragment preferenceDialogFragment) {
        this.mInternal = iPreferenceDialogFragment;
        this.mFragmentCompat = preferenceDialogFragment;
    }

    public Dialog onCreateDialog(Bundle bundle) {
        Activity activity = this.mFragmentCompat.getActivity();
        DialogPreference preference = this.mFragmentCompat.getPreference();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        BuilderDelegate builderDelegate = new BuilderDelegate(activity, builder);
        builderDelegate.setTitle(preference.getDialogTitle());
        builderDelegate.setIcon(preference.getDialogIcon());
        builderDelegate.setPositiveButton(preference.getPositiveButtonText(), this.mFragmentCompat);
        builderDelegate.setNegativeButton(preference.getNegativeButtonText(), this.mFragmentCompat);
        View onCreateDialogView = this.mInternal.onCreateDialogView(activity);
        if (onCreateDialogView != null) {
            this.mInternal.onBindDialogView(onCreateDialogView);
            builderDelegate.setView(onCreateDialogView);
        } else {
            builderDelegate.setMessage(preference.getDialogMessage());
        }
        this.mInternal.onPrepareDialogBuilder(builder);
        AlertDialog create = builder.create();
        if (this.mInternal.needInputMethod()) {
            create.getWindow().setSoftInputMode(5);
        }
        return create;
    }
}
