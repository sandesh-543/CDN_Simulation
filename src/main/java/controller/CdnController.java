package controller;

import loadbalancer.LoadBalancer;
import model.CachedObject;
import node.CdnNode;
import org.springframework.web.bind.annotation.*;
import service.CacheService;
import service.RedisService;

import java.util.Optional;

@RestController
@RequestMapping("/cdn")
public class CdnController {

    private final LoadBalancer loadBalancer;
    private final CacheService cacheService;
    private final RedisService redisService;

    public CdnController(LoadBalancer loadBalancer, CacheService cacheService, RedisService redisService) {
        this.loadBalancer = loadBalancer;
        this.cacheService = cacheService;
        this.redisService = redisService;
    }

    @GetMapping("/fetch/{key}")
    public String fetchContent(@PathVariable String key, @RequestParam(defaultValue = "LFU") String cacheType) {
        if (cacheType.equalsIgnoreCase("LFU")) {
            Optional<CachedObject> cachedObject = cacheService.getFromCache(key);
            if (cachedObject.isPresent()) {
                return "Served from LFU Cache: " + cachedObject.get().getValue();
            }
        } else if (cacheType.equalsIgnoreCase("Redis")) {
            String value = redisService.getFromRedis(key);
            if (value != null) {
                return "Served from Redis: " + value;
            }
        }

        // If not in the chosen cache, retrieve from a CDN node
        CdnNode cdnNode = loadBalancer.getNextNode(key);
        String content = String.valueOf(cdnNode.fetchContent(key));

        // Store the content in the chosen cache
        if (cacheType.equalsIgnoreCase("LFU")) {
            cacheService.addToCache(key, new CachedObject(key, content, 10000));
        } else if (cacheType.equalsIgnoreCase("Redis")) {
            redisService.put(key, content);
        }
        return "Content added to " + cacheType + " cache!";
    }

    @DeleteMapping("/remove/{key}")
    public String removeContent(@PathVariable String key, @RequestParam(defaultValue = "LFU") String cacheType) {
        if (cacheType.equalsIgnoreCase("LFU")) {
            cacheService.removeFromCache(key);
            return "Removed from LFU Cache.";
        } else if (cacheType.equalsIgnoreCase("Redis")) {
            redisService.removeFromRedis(key);
            return "Removed from Redis Cache.";
        }
        return "Invalid cache type!";
    }
}

