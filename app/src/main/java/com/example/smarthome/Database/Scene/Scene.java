package com.example.smarthome.Database.Scene;

import com.example.smarthome.Database.Device;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class Scene extends LitePalSupport {
    public static final String TIME="time";
    public static final String ID="id";
    //基本属性
    private List<Condition> conditionList=new ArrayList<>();//条件
    private List<Mission> missionList=new ArrayList<>();//任务
    private Temp temp;
    private int id;
    //加time和device的LIST
    private int isOpen;
    private String name;//场景名称
    private String time;
    private String isClick;
    private String schedule;//每天每周

    private List<C_Time> CTimeList=new ArrayList<>();//多个时间点
    private List<S_Device> s_deviceList=new ArrayList<>();//智能设备什么开灯时啊，什么检测到烟雾，逻辑交给服务器，咱们只需要做好保存条件和发送信息即可


    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public List<Mission> getMissionList() {
        return missionList;
    }

    public void setMissionList(List<Mission> missionList) {
        this.missionList = missionList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<Condition> conditionList) {
        this.conditionList = conditionList;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public String getIsClick() {
        return isClick;
    }

    public void setIsClick(String isClick) {
        this.isClick = isClick;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public List<C_Time> getCTimeList() {
        return CTimeList;
    }

    public void setCTimeList(List<C_Time> CTimeList) {
        this.CTimeList = CTimeList;
    }

    public List<S_Device> getS_deviceList() {
        return s_deviceList;
    }

    public void setS_deviceList(List<S_Device> s_deviceList) {
        this.s_deviceList = s_deviceList;
    }
}
