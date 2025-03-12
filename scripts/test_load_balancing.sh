#!/bin/bash

# Load balancer URL
LOAD_BALANCER_URL="http://localhost:8080"

# Number of test requests to be sent
REQUEST_COUNT=10

echo "Testing load balancing..."

for i in $(seq 1 $REQUEST_COUNT); do
    RESPONSE=$(curl -s "$LOAD_BALANCER_URL")
    echo "Response $i: $RESPONSE"
done

echo "Load balancing test completed!"
