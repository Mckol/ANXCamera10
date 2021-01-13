package com.miui.internal.hybrid;

import java.util.TreeSet;

public class ConfigUtils {
    private static final String KEY_FEATURES = "features";
    private static final String KEY_NAME = "name";
    private static final String KEY_ORIGIN = "origin";
    private static final String KEY_PARAMS = "params";
    private static final String KEY_PERMISSIONS = "permissions";
    private static final String KEY_SUBDOMAINS = "subdomains";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_VALUE = "value";
    private static final String KEY_VENDOR = "vendor";

    private ConfigUtils() {
    }

    private static String buildFeature(Config config) {
        StringBuilder sb = new StringBuilder();
        TreeSet<String> treeSet = new TreeSet(config.getFeatures().keySet());
        if (treeSet.isEmpty()) {
            return "";
        }
        for (String str : treeSet) {
            sb.append("{");
            sb.append(KEY_NAME);
            sb.append(":");
            sb.append("\"");
            sb.append(str);
            sb.append("\"");
            sb.append(",");
            sb.append(KEY_PARAMS);
            sb.append(":");
            sb.append("[");
            sb.append(buildParam(config.getFeature(str)));
            sb.append("]");
            sb.append("}");
            sb.append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static String buildFeatures(Config config) {
        return KEY_FEATURES + ":" + "[" + buildFeature(config) + "]";
    }

    private static String buildParam(Feature feature) {
        StringBuilder sb = new StringBuilder();
        TreeSet<String> treeSet = new TreeSet(feature.getParams().keySet());
        if (treeSet.isEmpty()) {
            return "";
        }
        for (String str : treeSet) {
            sb.append("{");
            sb.append(KEY_NAME);
            sb.append(":");
            sb.append("\"");
            sb.append(str);
            sb.append("\"");
            sb.append(",");
            sb.append("value");
            sb.append(":");
            sb.append("\"");
            sb.append(feature.getParam(str));
            sb.append("\"");
            sb.append("}");
            sb.append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static Object buildPermission(Config config) {
        StringBuilder sb = new StringBuilder();
        TreeSet<String> treeSet = new TreeSet(config.getPermissions().keySet());
        if (treeSet.isEmpty()) {
            return "";
        }
        for (String str : treeSet) {
            sb.append("{");
            sb.append("origin");
            sb.append(":");
            sb.append("\"");
            sb.append(str);
            sb.append("\"");
            sb.append(",");
            sb.append(KEY_SUBDOMAINS);
            sb.append(":");
            sb.append(config.getPermission(str).isApplySubdomains());
            sb.append("}");
            sb.append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static String buildPermissions(Config config) {
        return KEY_PERMISSIONS + ":" + "[" + buildPermission(config) + "]";
    }

    public static String getRawConfig(Config config) {
        return "{" + KEY_TIMESTAMP + ":" + config.getSecurity().getTimestamp() + "," + "vendor" + ":" + "\"" + config.getVendor() + "\"" + "," + buildFeatures(config) + "," + buildPermissions(config) + "}";
    }
}
