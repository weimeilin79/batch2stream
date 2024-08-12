#!/bin/bash
export DBUS_SESSION_BUS_ADDRESS=/dev/null

# Loop indefinitely
while true; do
    # Start the process
    rpk connect run /root/batch2stream/benthos/pipeline.yaml >/dev/null 

    # Wait for 2 seconds
    sleep 2
done