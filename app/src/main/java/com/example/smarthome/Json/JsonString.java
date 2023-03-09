package com.example.smarthome.Json;
import java.util.HashMap;
import java.util.Map;

public class JsonString {
    private String timestamp;
    private String firmware_version;
    private String device_id;
    private String misc;
    private String target_short_address;
    private String device_type;
    private String valid_data;
    private String valid_data_length;

    public JsonString( String timestamp,String device_id, String misc, String target_short_address, String device_type, String valid_data, String valid_data_length) {
        this.timestamp=timestamp;
        this.device_id = device_id;
        this.misc = misc;
        this.target_short_address = target_short_address;
        this.device_type = device_type;
        this.valid_data = valid_data;
        this.valid_data_length = valid_data_length;
    }

    @Override
    public String toString() {
        return "{" +"\"firmware_version\":"+"\"1.2.3\","+
                "\"device_id\":\""+device_id+"\"," +
                "\"other_data\": {" +
                "\"misc\":\""+misc+"\"}," +
                "\"target_short_address\":\""+target_short_address+"\","+
                "\"device_type\":\""+device_type+"\"," +
                "\"valid_data\":\"" +valid_data+"\"," +
                "\"valid_data_length\":\""+valid_data_length+"\""+
                "}";
    }
//    public String tString() {
//        return "{\"timestamp\":\""+timestamp+"\"," +
//                "\"firmware_version\":\""+firmware_version+"\"," +
//                "\"device_name\":\" "+device_id+"\"," +
//                "\"device_id\":\" "+device_id+"\"," +
//                "\"other_data\": {" +
//                "\"misc\":\""+misc+"\"}," +
//                "\"peripherals\": [" +
//                "{" +
//                "\"target_short_address\":\""+target_short_address+" \"," +
//                "\"target_command\": \""+target_command+"\"," +
//                "\"timestamp\": \""+timestamp+"\"," +
//                "\"target_data\":\" "+target_data+"\"," +
//                "\"misc\":{\"allow_access\":\""+target_data + "\"}}" + "]" +
//                "}";
//    }
}
