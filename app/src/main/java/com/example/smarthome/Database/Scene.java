package com.example.smarthome.Database;

import org.litepal.crud.LitePalSupport;

import java.util.List;

public class Scene extends LitePalSupport {
    //加time和device的LIST
    private String isClick;
    private String schedule;//每天每周
    //自动执行场景我tm

    private List<Time> timeList;//多个时间点
    private List<Device> deviceList;//智能设备什么开灯时啊，什么检测到烟雾，逻辑交给服务器，咱们只需要做好保存条件和发送信息即可




}
