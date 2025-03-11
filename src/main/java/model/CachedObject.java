package model;

import lombok.Getter;

public class CachedObject {
    @Getter
    private final String key;
    @Getter
    private final String value;
    private final long expiryTime;
    @Getter
    private int frequency;

    public CachedObject(String key, String value, long ttl) {
        this.key = key;
        this.value = value;
        this.expiryTime = System.currentTimeMillis() + ttl;
        this.frequency = 1;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }

    public void incrementFrequency() {
        this.frequency++;
    }
}
