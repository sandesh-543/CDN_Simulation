package service;

import model.CachedObject;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

public class CacheService {
    private final int CACHE_SIZE = 1000;
    private final Map<String, CachedObject> cache;
    private final Map<String, Integer> frequencyMap;
    private final PriorityQueue<String> evictionQueue;

    public CacheService() {
        this.cache = new ConcurrentHashMap<>();
        this.frequencyMap = new ConcurrentHashMap<>();
        this.evictionQueue = new PriorityQueue<>(Comparator.comparingInt(frequencyMap::get));
    }

    public Optional<CachedObject> getFromCache(String key) {
        if(!cache.containsKey(key) || cache.get(key).isExpired()) {
            removeFromCache(key);
            return Optional.empty();
        }
        frequencyMap.put(key, frequencyMap.getOrDefault(key, 0) + 1);
        evictionQueue.remove(key);
        evictionQueue.add(key);

        return Optional.of(cache.get(key));
    }

    public void addToCache(String key, CachedObject value) {
        if(cache.size() >= CACHE_SIZE) {
            String keyToRemove = evictionQueue.poll();
            removeFromCache(keyToRemove);
        }
        cache.put(key, value);
        frequencyMap.put(key, 1);
        evictionQueue.add(key);
    }

    public void removeFromCache(String key) {
        cache.remove(key);
        frequencyMap.remove(key);
        evictionQueue.remove(key);
    }
}
