package com.example.smarthome.Database.Scene;

import org.litepal.crud.LitePalSupport;

import java.util.List;

public class Condition extends LitePalSupport {
    //对于一个设备但有多个判断条件的，那就在condition中用多个C_device来表示


    //场景控制就用    Scene就好
    //点击
    private int id;
    private Scene scene;//这个是condition属于什么场景

    //由于这些设备的短地址和长地址重复，那么想要设置数据肯定要save，更新条件也要通过category和和一些其他的属性来判断，scene一定要有，指不定不同场景有这个设备，而这个设备的条件还都一样
    private List<S_Device> S_deviceList;
    private String isClick;
    private List<C_Time> c_timeList;
    private Scene c_scene;//XX场景执行就执行


    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public List<S_Device> getS_deviceList() {
        return S_deviceList;
    }

    public void setS_deviceList(List<S_Device> s_deviceList) {
        S_deviceList = s_deviceList;
    }

    public String getIsClick() {
        return isClick;
    }

    public void setIsClick(String isClick) {
        this.isClick = isClick;
    }

    public List<C_Time> getC_timeList() {
        return c_timeList;
    }

    public void setC_timeList(List<C_Time> c_timeList) {
        this.c_timeList = c_timeList;
    }

    public Scene getC_scene() {
        return c_scene;
    }

    public void setC_scene(Scene c_scene) {
        this.c_scene = c_scene;
    }
}
