package com.miui.internal.analytics;

import java.util.HashMap;
import java.util.Map;

public class CountPolicy extends Policy {
    private static final String COUNT_CATEGORY = "CountPolicyEvent";
    public static final String TAG = "count";
    private Map<String, Map<String, Integer>> mCountEventItems = new HashMap();

    @Override // com.miui.internal.analytics.Policy
    public void end() {
        for (String str : this.mCountEventItems.keySet()) {
            for (String str2 : this.mCountEventItems.get(str).keySet()) {
                HashMap hashMap = new HashMap();
                hashMap.put(str2, "counts");
                new TrackEvent(str, COUNT_CATEGORY, hashMap, (long) this.mCountEventItems.get(str).get(str2).intValue()).dispatch();
            }
        }
        this.mCountEventItems.clear();
    }

    @Override // com.miui.internal.analytics.Policy
    public void execute(Event event) {
        Map<String, Integer> map = this.mCountEventItems.get(event.getPackageName());
        if (map == null) {
            map = new HashMap<>();
            this.mCountEventItems.put(event.getPackageName(), map);
        }
        Integer num = map.get(event.getEventId());
        map.put(event.getEventId(), num == null ? 1 : Integer.valueOf(num.intValue() + 1));
    }

    @Override // com.miui.internal.analytics.Policy
    public void prepare() {
    }
}
