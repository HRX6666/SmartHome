package com.example.smarthome.Database;

import org.litepal.crud.LitePalSupport;

public class Sensor extends LitePalSupport {
    private String device_type;
    private String target_short_address;
    private String controller_long_address;
    private String temp;//温度
    private String wetness;//湿度
    private String valid_data;
    private int flag;
    private int isUpdate;
    //独立温度传感器
    private String temp_independent;

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
