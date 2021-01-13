package com.miui.internal.hybrid;

import java.util.HashMap;
import java.util.Map;

public class Config {
    private String content;
    private Map<String, Feature> features = new HashMap();
    private Map<String, Permission> permissions = new HashMap();
    private Map<String, String> preferences = new HashMap();
    private Security security;
    private String vendor;

    public void addFeature(Feature feature) {
        this.features.put(feature.getName(), feature);
    }

    public void addPermission(Permission permission) {
        this.permissions.put(permission.getUri(), permission);
    }

    public void clearFeatures() {
        this.features.clear();
    }

    public void clearPermissions() {
        this.permissions.clear();
    }

    public void clearPreferences() {
        this.preferences.clear();
    }

    public String getContent() {
        return this.content;
    }

    public Feature getFeature(String str) {
        return this.features.get(str);
    }

    public Map<String, Feature> getFeatures() {
        return this.features;
    }

    public Permission getPermission(String str) {
        return this.permissions.get(str);
    }

    public Map<String, Permission> getPermissions() {
        return this.permissions;
    }

    public String getPreference(String str) {
        return this.preferences.get(str);
    }

    public Map<String, String> getPreferences() {
        return this.preferences;
    }

    public Security getSecurity() {
        return this.security;
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setFeatures(Map<String, Feature> map) {
        this.features = map;
    }

    public void setPermissions(Map<String, Permission> map) {
        this.permissions = map;
    }

    public void setPreference(String str, String str2) {
        this.preferences.put(str, str2);
    }

    public void setPreferences(Map<String, String> map) {
        this.preferences = map;
    }

    public void setSecurity(Security security2) {
        this.security = security2;
    }

    public void setVendor(String str) {
        this.vendor = str;
    }
}
