package com.example.smarthome.Database;

import org.litepal.crud.LitePalSupport;

import java.util.List;

public class Home extends LitePalSupport {
    private int category;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private List<Light> lightList;
    private List<Curtain> curtainList;
    private List<AirConditioner> airConditionerList;
    private List<Device> deviceList;

    public List<Light> getLightList() {
        return lightList;
    }

    public void setLightList(List<Light> lightList) {
        this.lightList = lightList;
    }

    public List<Curtain> getCurtainList() {
        return curtainList;
    }

    public void setCurtainList(List<Curtain> curtainList) {
        this.curtainList = curtainList;
    }

    public List<AirConditioner> getAirConditionerList() {
        return airConditionerList;
    }

    public void setAirConditionerList(List<AirConditioner> airConditionerList) {
        this.airConditionerList = airConditionerList;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public Home() {
    }
}
