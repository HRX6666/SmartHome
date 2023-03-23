package com.example.smarthome.Database;

import org.litepal.crud.LitePalSupport;

public class Device extends LitePalSupport {
    //    private String source_command;//种类，根据种类查询
//    private String source_long_address;
//    private String source_short_address;
//    private String misc;//
//    private String network_flag;//电器是否自动入网(电器重启后自动组网)
//    private String source_data;//这玩意是开关键决定的，不需要保存

    private String misc;
    private String target_short_address;
    private String device_type;//设备码
    private String valid_data;
    private String valid_data_length;
    private String network_flag;
    private String target_long_address;
    private String controller_long_address;
    private String data;//存放jsonDate

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getController_long_address() {
        return controller_long_address;
    }

    public void setController_long_address(String controller_long_address) {
        this.controller_long_address = controller_long_address;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    //    timestamp 时间戳，ISO 8601格式
//
//    device_id 设备的唯一标识符，用于区分不同的设备
//
//    other_data 其他数据（可选）
//
//    target_short_address 目标短地址
//
//    device_type 命令码(设备类型码)
//
//    valid_data 有效数据
//
//    valid_data_length 有效数据长度，十六进制编码
private int curtain_extent;//窗帘程度
    //表关联
    private Model model;//离家模式等等等。。。。。。
    private Home home;//房间

    private int flag;//是否同意允许该家器接入
    private int isUpdate;//判断app开启电器入网是否收到了这条电器信息
    private String light_id;
    private int light_brightness;//亮度
    private int light_temp;//色温
    private String wetness;
    private int air_HotOrCold;//制热还是制冷
    private String air_temp;//空调温度
    //condition_一个电器可以有多个相同类别的条件，比如温度
    //因此condition要对应
    //一个场景多个条件
    //所以一个条件对应几个电器？
    //一个电器可以对应多个同一类的条件，而区分这些条件的就是场景


    public String getWetness() {
        return wetness;
    }

    public String getLight_id() {
        return light_id;
    }

    public void setLight_id(String light_id) {
        this.light_id = light_id;
    }

    public void setWetness(String wetness) {
        this.wetness = wetness;
    }

    public String getTarget_long_address() {
        return target_long_address;
    }

    public void setTarget_long_address(String target_long_address) {
        this.target_long_address = target_long_address;
    }


    public String getNetwork_flag() {
        return network_flag;
    }

    public void setNetwork_flag(String network_flag) {
        this.network_flag = network_flag;
    }

    public String getMisc() {
        return misc;
    }

    public void setMisc(String misc) {
        this.misc = misc;
    }

    public String getTarget_short_address() {
        return target_short_address;
    }

    public void setTarget_short_address(String target_short_address) {
        this.target_short_address = target_short_address;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getValid_data() {
        return valid_data;
    }

    public void setValid_data(String valid_data) {
        this.valid_data = valid_data;
    }

    public String getValid_data_length() {
        return valid_data_length;
    }

    public void setValid_data_length(String valid_data_length) {
        this.valid_data_length = valid_data_length;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }


    public int getLight_brightness() {
        return light_brightness;
    }

    public void setLight_brightness(int light_brightness) {
        this.light_brightness = light_brightness;
    }

    public int getLight_temp() {
        return light_temp;
    }

    public void setLight_temp(int light_temp) {
        this.light_temp = light_temp;
    }

    public int getAir_HotOrCold() {
        return air_HotOrCold;
    }

    public void setAir_HotOrCold(int air_HotOrCold) {
        this.air_HotOrCold = air_HotOrCold;
    }

    public String getAir_temp() {
        return air_temp;
    }

    public void setAir_temp(String air_temp) {
        this.air_temp = air_temp;
    }

    public int getCurtain_extent() {
        return curtain_extent;
    }

    public void setCurtain_extent(int curtain_extent) {
        this.curtain_extent = curtain_extent;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Home getRoom() {
        return home;
    }

    public void setRoom(Home home) {
        this.home = home;
    }



    public Device() {
    }
}
