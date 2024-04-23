#!/bin/bash

# Define the source and destination directories
SOURCE_DIR="/path/to/source/directory"  # Update this to your source directory for non-US CSV files
DEST_DIR="/ccc/REPLACEME"

# Change to the source directory
cd "$SOURCE_DIR"

# Loop through sorted files based on their timestamp in the filename
for file in $(ls non_us_boston_air_traffic_*.csv | sort); do
    echo "Moving $file to $DEST_DIR"
    mv "$file" "$DEST_DIR"
    echo "File moved successfully. Waiting for 15 seconds..."
    sleep 15  # Wait for 15 seconds before moving the next file
done

echo "All files have been moved."
