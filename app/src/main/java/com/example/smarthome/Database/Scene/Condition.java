package com.example.smarthome.Database.Scene;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class Condition extends LitePalSupport {
    //对于一个设备但有多个判断条件的，那就在condition中用多个C_device来表示


    //场景控制就用    Scene就好
    //点击
    private int id;
    private Scene scene;//这个是condition属于什么场景
    private Temp temp;
    private String time;//用来判断是否是再次编辑，+ 也能通过时间找到条件，进而进行界面内容的初始化

    /**
     * @param flag 为了快速的查找一个condition对象里面存的条件的种类，放入的数据为第几个条件
     *
     */
    private int judge;

    //由于这些设备的短地址和长地址重复，那么想要设置数据肯定要save，更新条件也要通过category和和一些其他的属性来判断，scene一定要有，指不定不同场景有这个设备，而这个设备的条件还都一样
    private List<S_Device> S_deviceList=new ArrayList<>();//每一个这里面存放着条件
    //1
    private String isClick;
    //2
    private C_Time c_time=new C_Time();
    //3
    private Scene c_scene;//XX场景执行就执行
    //4

    public int getJudge() {
        return judge;
    }

    public void setJudge(int judge) {
        this.judge = judge;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
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

    public C_Time getC_time() {
        return c_time;
    }

    public void setC_time(C_Time c_time) {
        this.c_time = c_time;
    }

    public Scene getC_scene() {
        return c_scene;
    }

    public void setC_scene(Scene c_scene) {
        this.c_scene = c_scene;
    }
}
