CREATE KEYSPACE demo
WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1}
AND durable_writes = true;

USE demo;

CREATE TABLE IF NOT EXISTS flight_data (
    icao24 text,
    callsign text,
    origin_country text,
    time_position bigint,
    last_contact bigint,
    longitude double,
    latitude double,
    baro_altitude double,
    on_ground boolean,
    velocity double,
    true_track double,
    vertical_rate double,
    sensors text,
    geo_altitude double,
    squawk text,
    spi boolean,
    position_source int,
    PRIMARY KEY (icao24, last_contact)
) WITH cdc=true;


