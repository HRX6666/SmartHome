package com.example.smarthome.Database.Scene;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class Temp extends LitePalSupport {//暂时储存创建场景前的信息来进行显示
    private Scene scene;//这个是condition属于什么场景
    private int id;
    private String time;//创建新的Scene,寻找当前temp的办法就是通过看temp的time是否为null(new的时候就setTime),至于编辑Scene的temp就通过Scene去找
    //由于这些设备的短地址和长地址重复，那么想要设置数据肯定要save，更新条件也要通过category和和一些其他的属性来判断，scene一定要有，指不定不同场景有这个设备，而这个设备的条件还都一样

    private List<S_Device> S_deviceList=new ArrayList<>();
    private String isClick="-1";/**
    @description 0是关，1是开，-1是默认值，表示未赋值
    **/
    private List<C_Time> c_timeList=new ArrayList<>();
    private Scene c_scene;//XX场景执行就执行，应该会跟上面那个分开，毕竟一个数据库有都多个int类的都没事
    private Scene m_scene;//被执行场景
    private List<Condition> conditionList=new ArrayList<>();//条件
    private List<Mission> missionList=new ArrayList<>();//条件
    //暂时先不进行多场景关联把

    // getter and setter methods

    // 定义设备与房间之间的关联，使用litepal中的注解 @OneToMany


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Mission> getMissionList() {
        return missionList;
    }

    public void setMissionList(List<Mission> missionList) {
        this.missionList = missionList;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<Condition> conditionList) {
        this.conditionList = conditionList;
    }

    public Scene getM_scene() {
        return m_scene;
    }

    public void setM_scene(Scene m_scene) {
        this.m_scene = m_scene;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
