package com.example.smarthome.Database;

import org.litepal.crud.LitePalSupport;

public class Sensor extends LitePalSupport {
    private int id;
    private String device_type;
    private String target_short_address;
    private String controller_long_address;
    private String temp;//温度
    private String i_temp;
    private String wetness;//空调湿度
    private String i_wetness;//独立湿度
    private String smoking;
    private String valid_data;
    private String time;//点击传感器的时间，通过intent传过去，再通过litepal寻找到sensor
    private int flag;
    private int isUpdate;
    //独立温度传感器
    private String temp_independent;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getI_temp() {
        return i_temp;
    }

    public void setI_temp(String i_temp) {
        this.i_temp = i_temp;
    }

    public String getI_wetness() {
        return i_wetness;
    }

    public void setI_wetness(String i_wetness) {
        this.i_wetness = i_wetness;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getWetness() {
        return wetness;
    }

    public void setWetness(String wetness) {
        this.wetness = wetness;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getController_long_address() {
        return controller_long_address;
    }

    public void setController_long_address(String controller_long_address) {
        this.controller_long_address = controller_long_address;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getTarget_short_address() {
        return target_short_address;
    }

    public void setTarget_short_address(String target_short_address) {
        this.target_short_address = target_short_address;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemp_independent() {
        return temp_independent;
    }

    public void setTemp_independent(String temp_independent) {
        this.temp_independent = temp_independent;
    }



    public String getValid_data() {
        return valid_data;
    }

    public void setValid_data(String valid_data) {
        this.valid_data = valid_data;
    }
}
