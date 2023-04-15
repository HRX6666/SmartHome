package com.example.smarthome.Scene.SmartDevice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.SmartDeviceAdaptor;
import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.S_Device;
import com.example.smarthome.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * 编辑场景的智能设备执行时，显示智能设备的界面
 */
public class SmartDeviceList extends AppCompatActivity {
    private List<S_Device> s_deviceList=new ArrayList<>();
    private List<S_Device> sDeviceList=new ArrayList<>();
    private List<Condition> conditionList=new ArrayList<>();
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smartdevicelist);
        initListener();
        initRecyclerView();
        Intent intent=getIntent();
        time=intent.getStringExtra("time");

    }

    private void initRecyclerView() {
        initContent();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recy_smart_device);
        LinearLayoutManager linearLayout = new LinearLayoutManager(SmartDeviceList.this);
        recyclerView.setLayoutManager(linearLayout);
        SmartDeviceAdaptor smartDeviceAdaptor=new SmartDeviceAdaptor(sDeviceList);
        recyclerView.setAdapter(smartDeviceAdaptor);
        smartDeviceAdaptor.notifyDataSetChanged();

    }

    private void initListener() {
    toolbar=findViewById(R.id.smart_device_back);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    }


    private void initContent(){

        conditionList= LitePal.order("time desc").where("time= ?",time).find(Condition.class);
        for(Condition condition:conditionList) {
            s_deviceList.clear();
            s_deviceList=condition.getS_deviceList();
            for(S_Device s_device:s_deviceList){
                sDeviceList.add(s_device);
            }
        }
    }


}
