package miui.module;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class Repository {
    public Set<String> contains(Set<String> set) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (String str : set) {
            if (contains(str)) {
                linkedHashSet.add(str);
            }
        }
        return linkedHashSet;
    }

    public boolean contains(String str) {
        return true;
    }

    public abstract String fetch(File file, String str);

    public Map<String, String> fetch(File file, Set<String> set) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (String str : set) {
            String fetch = fetch(file, str);
            if (fetch != null) {
                linkedHashMap.put(str, fetch);
            }
        }
        return linkedHashMap;
    }
}
