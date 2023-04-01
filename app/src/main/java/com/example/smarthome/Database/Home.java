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


    private List<Device> deviceList;


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
    public String getHomename() {
        return homename;
    }

    public void setHomename(String homename) {
        this.homename = homename;
    }

    private String homename;

    @Override
    public String toString() {
        return "Home{" +
                ", homename='" + homename + '\'' +
                '}';
    }

}
