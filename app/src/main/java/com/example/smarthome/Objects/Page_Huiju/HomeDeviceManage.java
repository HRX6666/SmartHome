package com.example.smarthome.Objects.Page_Huiju;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.HomeDeviceAdaptor;
import com.example.smarthome.Database.Device;
import com.example.smarthome.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class HomeDeviceManage extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<Device> deviceList=new ArrayList<>();
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeadddevice);
        Intent intent=getIntent();
        recyclerView=findViewById(R.id.home_add_device);
        toolbar=findViewById(R.id.manageHome_back);
        id=intent.getStringExtra("id");//id
        initRecyclerView();
    }

    private void initRecyclerView(){
        if(id!=null){
            deviceList= LitePal.where("addHomes_id = ?",id).find(Device.class);
        }
        HomeDeviceAdaptor homeDeviceAdaptor=new HomeDeviceAdaptor();
        homeDeviceAdaptor.setDevices(deviceList);
        //TODO 设置为已选择
        GridLayoutManager gridLayoutManager=new GridLayoutManager(HomeDeviceManage.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(homeDeviceAdaptor);
        homeDeviceAdaptor.notifyDataSetChanged();
    }
}
