package com.example.smarthome.Database.Scene;

import org.litepal.crud.LitePalSupport;

public class S_Device extends LitePalSupport {
    private String category;//判断是条件电器还是执行电器
   //只需要记录设备长地址短地址
    //条件就一个，同一设备多条件就在condition中同一个设备放多个，只不过条件不同来表示
    private Condition condition;
    private Temp temp;
    private Scene scene;

    private String tem_over;//温度高于
    private String tem_below;//温度低于
    private String isSmoke;//是否烟雾报警
//执行电器该有的属性
    private String open_light;
    private String close_light;
    private String air_open;
    private String air_close;

    //发现设备的·那个switch退出应用后仍然保存状态


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public String getTem_over() {
        return tem_over;
    }

    public void setTem_over(String tem_over) {
        this.tem_over = tem_over;
    }

    public String getTem_below() {
        return tem_below;
    }

    public void setTem_below(String tem_below) {
        this.tem_below = tem_below;
    }

    public String getIsSmoke() {
        return isSmoke;
    }

    public void setIsSmoke(String isSmoke) {
        this.isSmoke = isSmoke;
    }

    public String getOpen_light() {
        return open_light;
    }

    public void setOpen_light(String open_light) {
        this.open_light = open_light;
    }

    public String getClose_light() {
        return close_light;
    }

    public void setClose_light(String close_light) {
        this.close_light = close_light;
    }

    public String getAir_open() {
        return air_open;
    }

    public void setAir_open(String air_open) {
        this.air_open = air_open;
    }

    public String getAir_close() {
        return air_close;
    }

    public void setAir_close(String air_close) {
        this.air_close = air_close;
    }
}
