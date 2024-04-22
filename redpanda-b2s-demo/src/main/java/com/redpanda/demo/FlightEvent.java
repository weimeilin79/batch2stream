package com.redpanda.demo;

public class FlightEvent {
    private String icao24;
    private String callsign;
    private String origin_country;
    private long time_position;
    private long last_contact;
    private double longitude;
    private double latitude;
    private Double baro_altitude;
    private boolean on_ground;
    private Double velocity;
    private Double true_track;
    private Double vertical_rate;
    private Double sensors;
    private Double geo_altitude;
    private String squawk;
    private boolean spi;
    private Integer position_source;
    private String __deleted;
    public String getIcao24() {
        return icao24;
    }
    public void setIcao24(String icao24) {
        this.icao24 = icao24;
    }
    public String getCallsign() {
        return callsign;
    }
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }
    public String getOrigin_country() {
        return origin_country;
    }
    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
    }
    public long getTime_position() {
        return time_position;
    }
    public void setTime_position(long time_position) {
        this.time_position = time_position;
    }
    public long getLast_contact() {
        return last_contact;
    }
    public void setLast_contact(long last_contact) {
        this.last_contact = last_contact;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public Double getBaro_altitude() {
        return baro_altitude;
    }
    public void setBaro_altitude(Double baro_altitude) {
        this.baro_altitude = baro_altitude;
    }
    public boolean isOn_ground() {
        return on_ground;
    }
    public void setOn_ground(boolean on_ground) {
        this.on_ground = on_ground;
    }
    public Double getVelocity() {
        return velocity;
    }
    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }
    public Double getTrue_track() {
        return true_track;
    }
    public void setTrue_track(Double true_track) {
        this.true_track = true_track;
    }
    public Double getVertical_rate() {
        return vertical_rate;
    }
    public void setVertical_rate(Double vertical_rate) {
        this.vertical_rate = vertical_rate;
    }
    public Double getSensors() {
        return sensors;
    }
    public void setSensors(Double sensors) {
        this.sensors = sensors;
    }
    public Double getGeo_altitude() {
        return geo_altitude;
    }
    public void setGeo_altitude(Double geo_altitude) {
        this.geo_altitude = geo_altitude;
    }
    public String getSquawk() {
        return squawk;
    }
    public void setSquawk(String squawk) {
        this.squawk = squawk;
    }
    public boolean isSpi() {
        return spi;
    }
    public void setSpi(boolean spi) {
        this.spi = spi;
    }
    public Integer getPosition_source() {
        return position_source;
    }
    public void setPosition_source(Integer position_source) {
        this.position_source = position_source;
    }
    public String get__deleted() {
        return __deleted;
    }
    public void set__deleted(String __deleted) {
        this.__deleted = __deleted;
    }

   
}