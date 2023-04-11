package com.example.smarthome.Database;

import org.litepal.crud.LitePalSupport;

public class AddHomes extends LitePalSupport {
    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    private String home;

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
