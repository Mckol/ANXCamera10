package com.miui.internal.analytics;

import java.util.HashMap;
import java.util.Map;

public class LastPolicy extends Policy {
    public static final String TAG = "last";
    private Map<String, Event> mItems = new HashMap();

    @Override // com.miui.internal.analytics.Policy
    public void end() {
        for (String str : this.mItems.keySet()) {
            this.mItems.get(str).dispatch();
        }
        this.mItems.clear();
    }

    @Override // com.miui.internal.analytics.Policy
    public void execute(Event event) {
        this.mItems.put(event.getEventId(), event);
    }

    @Override // com.miui.internal.analytics.Policy
    public void prepare() {
    }
}
