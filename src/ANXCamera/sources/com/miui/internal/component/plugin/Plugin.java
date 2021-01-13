package com.miui.internal.component.plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Plugin {
    private Map<String, Dependency> dependencies = new HashMap();
    private Map<String, Extension> extensions = new HashMap();
    private File file;
    private String group;
    private int level;
    private int minCapatibleLevel;
    private String name;
    private Map<String, Outlet> outlets = new HashMap();
    private AccessPermission resources;
    private int versionCode;
    private String versionName;

    public void addDependency(Dependency dependency) {
        this.dependencies.put(dependency.getName(), dependency);
    }

    public void addExtension(Extension extension) {
        this.extensions.put(extension.getName(), extension);
    }

    public void addOutlet(Outlet outlet) {
        this.outlets.put(outlet.getName(), outlet);
    }

    public void clearDependencies() {
        this.dependencies.clear();
    }

    public void clearExtensions() {
        this.extensions.clear();
    }

    public void clearOutlets() {
        this.outlets.clear();
    }

    public Map<String, Dependency> getDependencies() {
        return this.dependencies;
    }

    public Dependency getDependency(String str) {
        return this.dependencies.get(str);
    }

    public Extension getExtension(String str) {
        return this.extensions.get(str);
    }

    public Map<String, Extension> getExtensions() {
        return this.extensions;
    }

    public File getFile() {
        return this.file;
    }

    public String getGroup() {
        return this.group;
    }

    public int getLevel() {
        return this.level;
    }

    public int getMinCapatibleLevel() {
        return this.minCapatibleLevel;
    }

    public String getName() {
        return this.name;
    }

    public Outlet getOutlet(String str) {
        return this.outlets.get(str);
    }

    public Map<String, Outlet> getOutlets() {
        return this.outlets;
    }

    public AccessPermission getResources() {
        return this.resources;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setDependencies(Map<String, Dependency> map) {
        this.dependencies = map;
    }

    public void setExtensions(Map<String, Extension> map) {
        this.extensions = map;
    }

    public void setFile(File file2) {
        this.file = file2;
    }

    public void setGroup(String str) {
        this.group = str;
    }

    public void setLevel(int i) {
        this.level = i;
    }

    public void setMinCapatibleLevel(int i) {
        this.minCapatibleLevel = i;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setOutlets(Map<String, Outlet> map) {
        this.outlets = map;
    }

    public void setResources(AccessPermission accessPermission) {
        this.resources = accessPermission;
    }

    public void setVersionCode(int i) {
        this.versionCode = i;
    }

    public void setVersionName(String str) {
        this.versionName = str;
    }
}
