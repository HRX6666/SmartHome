package com.example.smarthome.Page_Huiju;

import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.FindDeviceAdapter;
import com.example.smarthome.Adapter.ManageAdaptor;
import com.example.smarthome.Database.Device;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.Page_Home.FindDevices;
import com.example.smarthome.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageDevices extends AppCompatActivity {
    private List<Map<String,String>> deviceList=new ArrayList<Map<String,String>>();
    private  List<Device> devicelist=new ArrayList<>();
    private ClientMQTT clientMQTT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managedevices);

        initContent();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recy_manage);
        LinearLayoutManager linearLayout = new LinearLayoutManager(ManageDevices.this);
        recyclerView.setLayoutManager(linearLayout);
        ManageAdaptor manageAdaptor = new ManageAdaptor(deviceList);
        recyclerView.setAdapter(manageAdaptor);
        manageAdaptor.notifyDataSetChanged();
    }

    private void initContent()
    {

        deviceList.clear();
        devicelist= LitePal.order("device_type desc").where("flag= ?","1").find(Device.class);
        for(Device devices:devicelist) {
            String source_long_address = devices.getTarget_long_address();
            int flag = devices.getFlag();
            String source_command = devices.getDevice_type();
            String target_short_adress=devices.getTarget_short_address();
            Map<String, String> map = new HashMap<>();
            map.put("device_type", source_command);
            map.put("target_short_address",target_short_adress);
            map.put("target_long_address", source_long_address);
            map.put("flag", String.valueOf(flag));
            deviceList.add(map);

        }
    }

}
