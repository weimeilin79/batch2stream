#!/bin/bash

# Define the Docker command
CMD="docker run --network batch2stream_batch2stream --rm -v /root/batch2stream/benthos:/data --name benthos -p 4195:4195 jeffail/benthos -c /data/backup.yaml"

# Loop indefinitely
while true; do
    # Start the Docker container
    $CMD &
    CONTAINER_ID=$!

    # Wait for 2 seconds
    sleep 2


done