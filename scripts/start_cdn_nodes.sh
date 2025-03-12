#!/bin/bash

# Configuration
IMAGE_NAME="cdn-simulation:latest"
NETWORK_NAME="cdn_network"
NODES=("cdn-node1" "cdn-node2" "cdn-node3")
PORTS=(8081 8082 8083)

# If docker image does not exist, build it
docker network inspect $NETWORK_NAME >/dev/null 2>&1 || \
docker network create $NETWORK_NAME

# Start CDN nodes
for i in "${!NODES[@]}"; do
    echo "Starting ${NODES[$i]} on port ${PORTS[$i]}..."
    docker run -d \
        --name "${NODES[$i]}" \
        --network $NETWORK_NAME \
        -p "${PORTS[$i]}":8080 \
        $IMAGE_NAME
done

echo "All CDN nodes started successfully and ready to run!"