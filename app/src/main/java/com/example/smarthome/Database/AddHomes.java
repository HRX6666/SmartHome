package com.example.smarthome.Database;

import org.litepal.crud.LitePalSupport;

import java.util.List;

public class AddHomes extends LitePalSupport {
    private String home;//名称
    private String time;//判断
    private List<Device> deviceList;


    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }



    public AddHomes(){
        this.home=home;
    }
    @Override
    public String toString() {
        return "AddHomes{" +
                ", home='" + home + '\'' +
                '}';
    }
}
