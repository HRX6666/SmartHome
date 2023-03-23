package com.example.smarthome.Database.Scene;

import org.litepal.crud.LitePalSupport;

import java.util.List;

public class Mission extends LitePalSupport {

    private List<Scene> sceneList;//执行的场景
    private List<String> messageList;//系统通知
    private List<S_Device> s_deviceList;//执行的设备

    public List<Scene> getSceneList() {
        return sceneList;
    }

    public void setSceneList(List<Scene> sceneList) {
        this.sceneList = sceneList;
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

    public List<S_Device> getS_deviceList() {
        return s_deviceList;
    }

    public void setS_deviceList(List<S_Device> s_deviceList) {
        this.s_deviceList = s_deviceList;
    }
}
