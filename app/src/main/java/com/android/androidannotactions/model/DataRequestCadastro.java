package com.android.androidannotactions.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vinicius on 7/22/16.
 */
public class DataRequestCadastro {
    @JsonProperty("name")
    public String name;
    @JsonProperty("address")
    public String address;
    @JsonProperty("latitude")
    public double latitude;
    @JsonProperty("longitude")
    public double longitude;
    //1-cafe 2-breja - 3-ambos
    @JsonProperty("beverage")
    public int beverage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getBeverage() {
        return beverage;
    }

    public void setBeverage(int beverage) {
        this.beverage = beverage;
    }
}
