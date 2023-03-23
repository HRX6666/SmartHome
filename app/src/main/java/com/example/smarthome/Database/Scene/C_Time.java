package com.example.smarthome.Database.Scene;

import org.litepal.crud.LitePalSupport;

public class C_Time extends LitePalSupport {
    //时间可以设置多个
    private Condition condition;
    private Temp temp;
    private Scene scene;

    private String hours;
    private String minute;


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

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }
}
