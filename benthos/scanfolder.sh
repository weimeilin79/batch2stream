#!/bin/bash

# Define the Docker command
CMD="rpk connect run pipeline.yaml"

# Loop indefinitely
while true; do
    # Start the process
    $CMD &

    # Wait for 2 seconds
    sleep 2


done