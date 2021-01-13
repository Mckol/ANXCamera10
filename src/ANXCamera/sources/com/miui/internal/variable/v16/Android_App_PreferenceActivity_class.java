package com.miui.internal.variable.v16;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Android_App_PreferenceActivity_class extends com.miui.internal.variable.Android_App_PreferenceActivity_class {
    private static final String ID_HEADERS = "headers";
    private static final String ID_PREFS_FRAME = "prefs_frame";
    private static final String PACKAGE_ANDROID = "android";
    private static final String TYPE_ID = "id";

    @Override // com.miui.internal.variable.Android_App_PreferenceActivity_class
    public void onCreate(Activity activity, Bundle bundle) {
        View findViewById;
        View findViewById2;
        int identifier = activity.getResources().getIdentifier(ID_HEADERS, "id", PACKAGE_ANDROID);
        if (identifier > 0 && (findViewById2 = activity.findViewById(identifier)) != null) {
            findViewById2.setBackground(null);
            findViewById2.setPadding(0, 0, 0, 0);
        }
        int identifier2 = activity.getResources().getIdentifier(ID_PREFS_FRAME, "id", PACKAGE_ANDROID);
        if (identifier2 > 0 && (findViewById = activity.findViewById(identifier2)) != null) {
            findViewById.setBackground(null);
            findViewById.setPadding(0, 0, 0, 0);
        }
    }
}
