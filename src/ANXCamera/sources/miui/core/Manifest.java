package miui.core;

import java.util.LinkedHashMap;
import java.util.Map;
import miui.module.Dependency;
import miui.module.Module;

public class Manifest {
    private Map<String, Dependency> dependencies = new LinkedHashMap();
    private Module module;
    private Dependency sdkDependency;

    public void addDependency(String str, Dependency dependency) {
        this.dependencies.put(str, dependency);
    }

    public void clearDependencies() {
        this.dependencies.clear();
    }

    public Map<String, Dependency> getDependencies() {
        return this.dependencies;
    }

    public Dependency getDependency(String str) {
        return this.dependencies.get(str);
    }

    public int getLevel() {
        return this.module.getLevel();
    }

    public Module getModule() {
        return this.module;
    }

    public Dependency getSdkDependency() {
        return this.sdkDependency;
    }

    public void setDependencies(Map<String, Dependency> map) {
        this.dependencies = map;
    }

    public void setModule(Module module2) {
        this.module = module2;
    }

    public void setSdkDependency(Dependency dependency) {
        this.sdkDependency = dependency;
    }
}
