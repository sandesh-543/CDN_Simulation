package loadbalancer;

import node.CdnNode;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
// AtomicInteger used so that the index can be updated atomically, without the need for synchronization

public class LoadBalancer {
    private final List<CdnNode> nodes;
    private final AtomicInteger currentIndex;

    public LoadBalancer(List<CdnNode> nodes) {
        this.nodes = nodes;
        this.currentIndex = new AtomicInteger(0);
    }

    public CdnNode getNextNode(String key) {
        if(nodes.isEmpty()) {
            throw new IllegalStateException("No CDN nodes available");
        }
        int index = Math.abs(currentIndex.getAndIncrement() % nodes.size());
        return nodes.get(index);
    }

    public void addNode(CdnNode node) {
        nodes.add(node);
    }

    public void removeNode(CdnNode node) {
        nodes.remove(node);
    }
}
