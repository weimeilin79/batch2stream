SELECT 'CREATE DATABASE demo'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'demo')\gexec

\connect demo;
CREATE TABLE bos_air_traffic (
    icao24 text,
    callsign text,
    origin_country text,
    time_position bigint,
    last_contact bigint,
    longitude double precision,
    latitude double precision,
    baro_altitude double precision,
    on_ground boolean,
    velocity double precision,
    true_track double precision,
    vertical_rate double precision,
    sensors double precision,
    geo_altitude double precision,
    squawk double precision,
    spi boolean,
    position_source bigint,
    PRIMARY KEY (callsign, last_contact)
);