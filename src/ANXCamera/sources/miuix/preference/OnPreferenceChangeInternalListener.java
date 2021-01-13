package miuix.preference;

import androidx.preference.Preference;

/* access modifiers changed from: package-private */
public interface OnPreferenceChangeInternalListener {
    void notifyPreferenceChangeInternal(Preference preference);

    boolean onPreferenceChangeInternal(Preference preference, Object obj);
}
