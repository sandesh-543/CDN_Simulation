package node;

import lombok.Getter;
import model.CachedObject;

import java.util.concurrent.ConcurrentHashMap;

public class CdnNode {
    @Getter
    private String nodeId;
    private final ConcurrentHashMap<String, CachedObject> cache;

    public CdnNode(String nodeId) {
        this.nodeId = nodeId;
        this.cache = new ConcurrentHashMap<>();
    }

    public void store(String key, CachedObject value) {
        cache.put(key, value);
    }

    public CachedObject fetchContent(String key) {
        return cache.getOrDefault(key, null);
    }

    public boolean hasObject(String key) {
        return cache.containsKey(key);
    }
}
