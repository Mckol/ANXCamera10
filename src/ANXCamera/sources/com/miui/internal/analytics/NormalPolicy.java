package com.miui.internal.analytics;

public class NormalPolicy extends Policy {
    public static final String TAG = "normal";

    @Override // com.miui.internal.analytics.Policy
    public void end() {
    }

    @Override // com.miui.internal.analytics.Policy
    public void execute(Event event) {
        event.dispatch();
    }

    @Override // com.miui.internal.analytics.Policy
    public void prepare() {
    }
}
