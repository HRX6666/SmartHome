package com.example.smarthome.Json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.smarthome.Database.Device;
import com.example.smarthome.Database.Sensor;
import com.google.gson.Gson;

import org.litepal.LitePal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseJson {
    //这个是如果app向中控发送数据请求具体设备信息的
    public void parseJson(String jsonData, Map<String,String> map){
        JSONObject jsonObject=JSONObject.parseObject(jsonData);
        JSONArray sensors=jsonObject.getJSONArray("sensors");
        JSONArray jsonArray= JSON.parseArray(sensors.toString());
        int size=jsonArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject1=jsonArray.getJSONObject(i);
            map.put("source_short_address",jsonObject1.getString("source_short_address"));
            map.put("network_flag",jsonObject1.getString("network_flag"));
            map.put("source_command",jsonObject1.getString("source_command"));//设备类型
            map.put("source_data",jsonObject1.getString("source_data"));
            map.put("misc",jsonObject1.getString("misc"));

        }

    }
    public void ParseJsonData(String jsonData,int flag) {

        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        String device_type_ = jsonObject.getString("device_type");
        flag=0;
        if(flag==0){
            if (device_type_.equals("0x00")) {
                String controller_long_address = jsonObject.getString("controller_long_address");
                String valid_data = jsonObject.getString("valid_data");
                String device_type = valid_data.substring(2, 4);
                String network_flag = valid_data.substring(4, 6);
                String target_long_address = valid_data.substring(6, 22);
                String target_short_address = valid_data.substring(22, 26);
                List<Device> deviceList = LitePal.where("target_long_address = ?", target_long_address).find(Device.class);
                Device device = new Device();
                if (deviceList.isEmpty()) {
                    device.setDevice_type(device_type);
                    device.setNetwork_flag(network_flag);
                    device.setTarget_long_address(target_long_address);
                    device.setTarget_short_address(target_short_address);
                    device.setController_long_address(controller_long_address);
                    device.setData(jsonData);
                    device.setIsUpdate(1);
//                device.setMisc(jsonObject.getString("o"));
                    device.setFlag(0);
                    if (LitePal.where("target_long_address = ?", target_long_address).find(Device.class).isEmpty()) {
                        device.save();
                    }else
                        device.updateAll("target_long_address = ?", target_long_address);

                } else {
                    device.setDevice_type(device_type);
                    device.setNetwork_flag(network_flag);
                    device.setTarget_long_address(target_long_address);
                    device.setTarget_short_address(target_short_address);
                    device.setController_long_address(controller_long_address);
                    device.setData(jsonData);
                    device.setIsUpdate(1);
                    if (!LitePal.where("target_long_address = ?", target_long_address).find(Device.class).isEmpty()) {
                        device.updateAll("target_long_address = ?", target_long_address);
///////////////////厂地址一样把信息更新一遍
                    }
                }
            } else if(device_type_.equals("0xFC")){
                String controller_long_address = jsonObject.getString("controller_long_address");
                String valid_data = jsonObject.getString("valid_data");
                String device_type = valid_data.substring(2, 4);
                String network_flag = valid_data.substring(4, 6);
                String target_long_address = valid_data.substring(6, 22);
                String target_short_address = valid_data.substring(22, 26);
                List<Device> deviceList = LitePal.where("target_long_address = ?", target_long_address).find(Device.class);
                Device device = new Device();
                if (deviceList.isEmpty()) {
                    device.setDevice_type(device_type);
                    device.setNetwork_flag(network_flag);
                    device.setTarget_long_address(target_long_address);
                    device.setTarget_short_address(target_short_address);
                    device.setController_long_address(controller_long_address);
                    device.setData(jsonData);
                    device.setIsUpdate(1);
//                device.setMisc(jsonObject.getString("o"));
                    device.setFlag(1);
                    if (LitePal.where("target_long_address = ?", target_long_address).find(Device.class).isEmpty()) {
                        device.save();
                    }else
                        device.updateAll("target_long_address = ?", target_long_address);

                } else {
                    device.setDevice_type(device_type);
                    device.setNetwork_flag(network_flag);
                    device.setTarget_long_address(target_long_address);
                    device.setTarget_short_address(target_short_address);
                    device.setController_long_address(controller_long_address);
                    device.setData(jsonData);
                    device.setIsUpdate(1);
                    device.setFlag(1);
                    if (!LitePal.where("target_long_address = ?", target_long_address).find(Device.class).isEmpty()) {
                        device.updateAll("target_long_address = ?", target_long_address);
///////////////////厂地址一样把信息更新一遍
                    }
                }
            }
            else if (device_type_.equals("0x02")) {
                String controller_long_address = jsonObject.getString("controller_long_address");
                String valid_data = jsonObject.getString("valid_data");
                String target_short_address = jsonObject.getString("target_short_address");
                String temp_int = valid_data.substring(2, 4);
                String temp_decimal = valid_data.substring(5, 6);
                String wetness = valid_data.substring(6, 8);
                List<Device> deviceList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"02").find(Device.class);
                Device device = new Device();
                String temp_s = temp_int + "." + temp_decimal;
                int temp = Integer.valueOf(temp_s);
                device.setDevice_type("02");
                device.setAir_HotOrCold(temp);
                device.setTarget_short_address(target_short_address);
                device.setController_long_address(controller_long_address);
                device.setWetness(wetness);
                device.setData(jsonData);
                device.setIsUpdate(1);
                if (LitePal.where("target_short_address = ?", target_short_address).find(Device.class).isEmpty()) {
                    device.setFlag(0);
                    device.save();
                }else
                    device.updateAll("target_short_address = ?", target_short_address);
            } else if (device_type_.equals("0x01")) {
                String controller_long_address = jsonObject.getString("controller_long_address");
                String valid_data = jsonObject.getString("valid_data");
                String target_short_address = jsonObject.getString("target_short_address");
                List<Device> deviceList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"01").find(Device.class);
                Device device = new Device();
                if (deviceList.isEmpty()) {
                    device.setValid_data(valid_data);
                    device.setDevice_type("01");
                    device.setTarget_short_address(target_short_address);
                    device.setController_long_address(controller_long_address);
                    device.setData(jsonData);
                    device.setIsUpdate(1);
                    if (LitePal.where("target_short_address = ?", target_short_address).find(Device.class).isEmpty()) {
                        device.setFlag(0);
                        device.save();
                    }else
                        device.updateAll("target_short_address = ?", target_short_address);
                }

            }
            else if(device_type_.equals("0x03")){
//            else if(device_type_.equals("0x05")){
//                String controller_long_address = jsonObject.getString("controller_long_address");
//                String valid_data = jsonObject.getString("valid_data");
//                String target_short_address = jsonObject.getString("target_short_address");
//                String temp_int = valid_data.substring(2, 4);
//                String temp_decimal = valid_data.substring(4, 6);
//                String wetness = valid_data.substring(6, 8);
//                List<Sensor> sensorList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"04").find(Sensor.class);
//                Sensor sensor=new Sensor();
//                if (sensorList.isEmpty()) {
//                    String temp_s = temp_int + "." + temp_decimal;
//                    sensor.setTemp(temp_s);
//                    sensor.setTarget_short_address(target_short_address);
//                    sensor.setController_long_address(controller_long_address);
//                    sensor.setWetness(wetness);
//                    sensor.setDevice_type("05");
//                    sensor.setValid_data(valid_data);
//                    sensor.setIsUpdate(1);
//                    if (LitePal.where("target_short_address = ?", target_short_address).find(Sensor.class).isEmpty()) {
//                        sensor.setFlag(0);
//                        sensor.save();
//                    }else
//                        sensor.updateAll("target_short_address = ?", target_short_address);
//                }
//            }
                String controller_long_address = jsonObject.getString("controller_long_address");
                String deep = jsonObject.getString("valid_data");
                String target_short_address = jsonObject.getString("target_short_address");
                List<Device> deviceList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"03").find(Device.class);
                Device device = new Device();
                if (deviceList.isEmpty()) {
                    device.setCurtain_extent(Integer.valueOf(deep));
                    device.setDevice_type("03");
                    device.setTarget_short_address(target_short_address);
                    device.setController_long_address(controller_long_address);
                    device.setData(jsonData);
                    device.setIsUpdate(1);
                    if (LitePal.where("target_short_address = ?", target_short_address).find(Device.class).isEmpty()) {
                        device.setFlag(0);
                        device.save();
                    }else
                        device.updateAll("target_short_address = ?", target_short_address);
                }
            }else if(device_type_.equals("0x04")){
                String controller_long_address = jsonObject.getString("controller_long_address");
                String valid_data = jsonObject.getString("valid_data");
                String target_short_address = jsonObject.getString("target_short_address");
                String temp1=valid_data.substring(2,4);
                String temp2=valid_data.substring(4,6);
                String wetness=valid_data.substring(6,8);
                int temp_1=Integer.valueOf(temp1,16);
                int temp_2=Integer.valueOf(temp2,16);
                int wetness_i=Integer.valueOf(wetness,16);
                List<Sensor> sensorList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"04").find(Sensor.class);
                Sensor sensor=new Sensor();
                if (sensorList.isEmpty()) {
                    sensor.setDevice_type("04");
                    sensor.setTarget_short_address(target_short_address);
                    sensor.setController_long_address(controller_long_address);
                    sensor.setTemp(String.valueOf(temp_1+0.1*temp_2));
                    sensor.setWetness(String.valueOf(wetness_i));
                    sensor.setValid_data(valid_data);
                    sensor.setIsUpdate(1);
                    if (LitePal.where("target_short_address = ?", target_short_address).find(Sensor.class).isEmpty()) {
                        sensor.setFlag(0);
                        sensor.save();
                    }else
                        sensor.updateAll("target_short_address = ?", target_short_address);
                }

            }else if(device_type_.equals("0x05")){
                String controller_long_address = jsonObject.getString("controller_long_address");
                String valid_data = jsonObject.getString("valid_data");
                String target_short_address = jsonObject.getString("target_short_address");
                String temp1=valid_data.substring(2,4);
                String temp2=valid_data.substring(4,6);
                int temp_1=Integer.valueOf(temp1,16);
                int temp_2=Integer.valueOf(temp2,16);
                List<Sensor> sensorList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"05").find(Sensor.class);
                Sensor sensor=new Sensor();
                if (sensorList.isEmpty()) {
                    sensor.setDevice_type("05");
                    sensor.setTarget_short_address(target_short_address);
                    sensor.setController_long_address(controller_long_address);
                    sensor.setTemp(String.valueOf(temp_1+0.1*temp_2));
                    sensor.setValid_data(valid_data);
                    sensor.setIsUpdate(1);
                    if (LitePal.where("target_short_address = ?", target_short_address).find(Sensor.class).isEmpty()) {
                        sensor.setFlag(0);
                        sensor.save();
                    }else
                        sensor.updateAll("target_short_address = ?", target_short_address);
                }

            }else if(device_type_.equals("0x06")){
                String controller_long_address = jsonObject.getString("controller_long_address");
                String valid_data = jsonObject.getString("valid_data");
                String target_short_address = jsonObject.getString("target_short_address");
                String wetness=valid_data.substring(2,4);
                int wetness_i=Integer.valueOf(wetness,16);
                List<Sensor> sensorList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"06").find(Sensor.class);
                Sensor sensor=new Sensor();
                if (sensorList.isEmpty()) {
                    sensor.setDevice_type("06");
                    sensor.setTarget_short_address(target_short_address);
                    sensor.setController_long_address(controller_long_address);
                    sensor.setI_wetness(String.valueOf(wetness_i));
                    sensor.setValid_data(valid_data);
                    sensor.setIsUpdate(1);
                    if (LitePal.where("target_short_address = ?", target_short_address).find(Sensor.class).isEmpty()) {
                        sensor.setFlag(0);
                        sensor.save();
                    }else
                        sensor.updateAll("target_short_address = ?", target_short_address);
                }

            }else if(device_type_.equals("0x07")){
                String controller_long_address = jsonObject.getString("controller_long_address");
                String valid_data = jsonObject.getString("valid_data");
                String target_short_address = jsonObject.getString("target_short_address");
                String smoking=valid_data.substring(0,2);
                int smoking_i=Integer.valueOf(smoking,16);
                List<Sensor> sensorList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"07").find(Sensor.class);
                Sensor sensor=new Sensor();
                if (sensorList.isEmpty()) {
                    sensor.setDevice_type("07");
                    sensor.setTarget_short_address(target_short_address);
                    sensor.setController_long_address(controller_long_address);
                    sensor.setSmoking(String.valueOf(smoking_i));
                    sensor.setValid_data(valid_data);
                    sensor.setIsUpdate(1);
                    if (LitePal.where("target_short_address = ?", target_short_address).find(Sensor.class).isEmpty()) {
                        sensor.setFlag(0);
                        sensor.save();
                    }else
                        sensor.updateAll("target_short_address = ?", target_short_address);
                }

            }

        }


    }
    public void parseJsonAndUpdateDatabase(String jsonData){

        JSONObject jsonObject=JSONObject.parseObject(jsonData);
        JSONArray sensors=jsonObject.getJSONArray("sensors");
        JSONArray jsonArray= JSON.parseArray(sensors.toString());
        int size=jsonArray.size();

        for (int i = 0; i < size; i++) {

            JSONObject jsonObject1=jsonArray.getJSONObject(i);
            List<Device> devices=LitePal.where("source_long_address = ?",jsonObject1.getString("source_long_address")).find(Device.class);
            Device device=new Device();
            if(devices.isEmpty()){
                String source_data=jsonObject1.getString("source_data");
                device.setIsUpdate(1);
                device.setMisc(jsonObject1.getString("misc"));
                device.setFlag(0);
                device.save();
            }else{
                device.setIsUpdate(1);//关闭开放入网获得直接返回，还要加一个系统返回键的监听，要记得update为0防止下次没这个反而显示
                device.updateAll("source_long_address = ?",jsonObject1.getString("source_long_address"));
            }
        }}
    public void parseJsonAndUpdateDatabase(String jsonData, List<Map<String,String>> devicesList){
        Map<String,String> map=new HashMap<>();
        JSONObject jsonObject=JSONObject.parseObject(jsonData);
        JSONArray sensors=jsonObject.getJSONArray("sensors");
        JSONArray jsonArray= JSON.parseArray(sensors.toString());
        int size=jsonArray.size();

        for (int i = 0; i < size; i++) {

            JSONObject jsonObject1=jsonArray.getJSONObject(i);
            map.put("source_short_address",jsonObject1.getString("source_short_address"));
            map.put("source_long_address",jsonObject1.getString("source_long_address"));
            map.put("network_flag",jsonObject1.getString("network_flag"));
            map.put("source_command",jsonObject1.getString("source_command"));//设备类型
            map.put("source_data",jsonObject1.getString("source_data"));
            map.put("misc",jsonObject1.getString("misc"));
            Device device=new Device();
            device.setMisc(jsonObject1.getString("misc"));
            device.setFlag(0);
            boolean a=device.isSaved();

            if(device.isSaved())
                return;
            device.save();
            devicesList.add(map);



        }
    }



}
