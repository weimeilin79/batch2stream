#!/bin/bash
set -e

# Append configuration for logical replication
echo "host replication all all md5" >> "$PGDATA/pg_hba.conf"
echo "wal_level = logical" >> "$PGDATA/postgresql.conf"
echo "max_replication_slots = 4" >> "$PGDATA/postgresql.conf"
echo "max_wal_senders = 4" >> "$PGDATA/postgresql.conf"

