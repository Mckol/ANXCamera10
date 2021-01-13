package com.miui.internal.hybrid;

import java.util.HashMap;
import java.util.Map;
import miui.hybrid.HybridFeature;

public class FeatureManager {
    private Config mConfig;
    private Map<String, HybridFeature> mFeatures = new HashMap();
    private ClassLoader mLoader;

    public FeatureManager(Config config, ClassLoader classLoader) {
        this.mConfig = config;
        this.mLoader = classLoader;
    }

    private HybridFeature initFeature(String str) throws HybridException {
        try {
            return (HybridFeature) this.mLoader.loadClass(str).newInstance();
        } catch (ClassNotFoundException unused) {
            throw new HybridException(204, "feature not found: " + str);
        } catch (InstantiationException unused2) {
            throw new HybridException(204, "feature cannot be instantiated: " + str);
        } catch (IllegalAccessException unused3) {
            throw new HybridException(204, "feature cannot be accessed: " + str);
        }
    }

    public HybridFeature lookupFeature(String str) throws HybridException {
        HybridFeature hybridFeature = this.mFeatures.get(str);
        if (hybridFeature != null) {
            return hybridFeature;
        }
        Feature feature = this.mConfig.getFeature(str);
        if (feature != null) {
            HybridFeature initFeature = initFeature(str);
            initFeature.setParams(feature.getParams());
            this.mFeatures.put(str, initFeature);
            return initFeature;
        }
        throw new HybridException(204, "feature not declared: " + str);
    }
}
