package com.example.smarthome.Page_Home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.smarthome.Adapter.FindDeviceAdapter;
import com.example.smarthome.Database.Device;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.Page_Samrt.AdjustTheLights;
import com.example.smarthome.R;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class FindDevices extends AppCompatActivity {
    private List<Map<String,String>> deviceList=new ArrayList<Map<String,String>>();
    private  List<Device> devicelist=new ArrayList<>();
    private Switch aSwitch;
    private ClientMQTT clientMQTT;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finddevices);
        swipeRefreshLayout=findViewById(R.id.swipeRefresh);
        aSwitch=findViewById(R.id.switch1);


        Device device=new Device();
        device.setToDefault("isUpdate");
        device.updateAll();
        //防止不打开按钮一刷新就显示了

        clientMQTT=new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(FindDevices.this);
        //设置进度View样式的大小，只有两个值DEFAULT和LARGE
//设置进度View下拉的起始点和结束点，scale 是指设置是否需要放大或者缩小动画
        swipeRefreshLayout.setProgressViewOffset(true, -0, 100);
//设置进度View下拉的结束点，scale 是指设置是否需要放大或者缩小动画
        swipeRefreshLayout.setProgressViewEndTarget(true, 180);
//设置进度View的组合颜色，在手指上下滑时使用第一个颜色，在刷新中，会一个个颜色进行切换
        swipeRefreshLayout.setColorSchemeColors(Color.BLACK, Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE);

//设置触发刷新的距离
        swipeRefreshLayout.setDistanceToTriggerSync(200);
//如果child是自己自定义的view，可以通过这个回调，告诉mSwipeRefreshLayoutchild是否可以滑动
        swipeRefreshLayout.setOnChildScrollUpCallback(null);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                deviceList.clear();
                                devicelist= LitePal.order("device_type desc").where("flag= ? and isUpdate= ?","0","1").find(Device.class);
                                initContent();
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recy_devices);
                                LinearLayoutManager linearLayout = new LinearLayoutManager(FindDevices.this);
                                recyclerView.setLayoutManager(linearLayout);
                                FindDeviceAdapter findDeviceAdapter = new FindDeviceAdapter(deviceList);
                                recyclerView.setAdapter(findDeviceAdapter);
                                findDeviceAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //之后在数据库加一个是否是点击入网后能够获得的电器isupdate,解析那里的litepal要记得让isupdate为1，这里switch变成false后再全弄为0
//                    clientMQTT.Subscribe();//没有显示不要担心，做一个下拉刷新就好了

                    clientMQTT.publishMessagePlus(null,"0x0000","0xFF", "0x0002","0x02");
                    deviceList.clear();
                    initContent();
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recy_devices);
                    LinearLayoutManager linearLayout = new LinearLayoutManager(FindDevices.this);
                    recyclerView.setLayoutManager(linearLayout);
                    FindDeviceAdapter findDeviceAdapter = new FindDeviceAdapter(deviceList);
                    recyclerView.setAdapter(findDeviceAdapter);
                    findDeviceAdapter.notifyDataSetChanged();
                }else {
                    //setIsUpdate全部都set成0
                    clientMQTT.unSubscribe();//取消订阅

                    Device device=new Device();
                    device.setToDefault("isUpdate");
                    device.updateAll();

                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Device device=new Device();
        device.setIsUpdate(0);
        device.updateAll();

    }
    private void initContent()
    {


        for(Device devices:devicelist) {
            String source_long_address = devices.getTarget_long_address();
            int flag = devices.getFlag();
            String source_command = devices.getDevice_type();
            String target_short_adress=devices.getTarget_short_address();
            Map<String, String> map = new HashMap<>();
            map.put("device_type", source_command);
            map.put("target_short_address",target_short_adress);
            map.put("target_long_address", String.valueOf(source_long_address));
            map.put("flag", String.valueOf(flag));
            deviceList.add(map);

        }
    }
//    class MyRunnable implements Runnable{
//        @Override
//        public void run() {
//            devicelist= LitePal.order("source_command desc").where("flag = ? and isUpdate = ?","0","1").find(Device.class);
//            while (devicelist.isEmpty()) {
//                devicelist= LitePal.order("source_command desc").where("flag = ? and isUpdate = ?","0","1").find(Device.class);
//            }
//            deviceList.clear();
//            initContent();
//            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recy_devices);
//            LinearLayoutManager linearLayout = new LinearLayoutManager(FindDevices.this);
//            recyclerView.setLayoutManager(linearLayout);
//            FindDeviceAdapter findDeviceAdapter = new FindDeviceAdapter(deviceList);
//            recyclerView.setAdapter(findDeviceAdapter);
//            findDeviceAdapter.notifyDataSetChanged();
//        }
//    }

}
