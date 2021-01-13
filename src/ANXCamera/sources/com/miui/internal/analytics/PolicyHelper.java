package com.miui.internal.analytics;

import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

public class PolicyHelper {
    private static final String SPLITTER = ":";
    private static final String TAG = "PolicyHelper";
    private ObjectBuilder<Policy> mObjBuilder = new ObjectBuilder<>();
    private Map<String, Policy> mPolicy = new HashMap();

    public PolicyHelper() {
        this.mObjBuilder.registerClass(LastPolicy.class, LastPolicy.TAG);
        this.mObjBuilder.registerClass(NormalPolicy.class, "normal");
        this.mObjBuilder.registerClass(CountPolicy.class, "count");
    }

    public void clear() {
        this.mPolicy.clear();
    }

    public void end() {
        for (String str : this.mPolicy.keySet()) {
            this.mPolicy.get(str).end();
        }
    }

    public Policy getPolicy(String str) {
        PatternSyntaxException e2;
        Policy policy = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            String[] split = str.split(SPLITTER);
            if (split.length <= 0) {
                return null;
            }
            Policy policy2 = this.mPolicy.get(split[0]);
            try {
                String str2 = split.length > 1 ? split[1] : "";
                if (policy2 == null) {
                    Policy buildObject = this.mObjBuilder.buildObject(str);
                    if (buildObject == null) {
                        return buildObject;
                    }
                    this.mPolicy.put(str, buildObject);
                    buildObject.setParam(str2);
                    buildObject.prepare();
                    return buildObject;
                }
                policy2.setParam(str2);
                return policy2;
            } catch (PatternSyntaxException e3) {
                e2 = e3;
                policy = policy2;
                Log.e(TAG, "PatternSyntaxException catched when getPolicy", e2);
                return policy;
            }
        } catch (PatternSyntaxException e4) {
            e2 = e4;
            Log.e(TAG, "PatternSyntaxException catched when getPolicy", e2);
            return policy;
        }
    }
}
