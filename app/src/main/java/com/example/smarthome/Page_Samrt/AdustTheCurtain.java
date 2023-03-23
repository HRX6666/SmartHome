package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.smarthome.Database.Device;
import com.example.smarthome.Database.Home;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.R;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdustTheCurtain extends AppCompatActivity {
    Toolbar curtain_tb;
    private ClientMQTT clientMQTT;
    private Spinner spinner_choose_home;
    private Spinner spinner_choose_model;
    private Button bt_openAll;
    //    private Button bt_openMid;
    private Button bt_closeCurtain;
    private int home_choose;
    private String s_home_choose;
    //下拉框进入默认是全屋，进入界面时应该根服务器同步数据，设置当前设备状态是怎么样的，那个seekbar也一样，要根据实际情况来变
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adust_the_curtain);
        curtain_tb = findViewById(R.id.curtain_tb);
        bt_openAll = findViewById(R.id.open_all);
//        bt_openMid = findViewById(R.id.open_mid);
//        spinner_choose_home = findViewById(R.id.curtain_choose_home);//这个因为要重新设计，毕竟房间可以自由添加，不能固定在spinner那几个
        bt_closeCurtain = findViewById(R.id.close_curtain);
//        initDropdown1();
        clientMQTT = new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(AdustTheCurtain.this);
        List<Device> devicelist= LitePal.where("flag = ? and device_type = ?","1","0x03").find(Device.class);
        List<Map<String,String>> deviceList=new ArrayList<>();
        Map<String,String> map=new HashMap<>();

        bt_openAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Device device:devicelist){
                    Home room=device.getRoom();
                    int a=room.getCategory();
                    String home="0x0"+a;
                    if(home.equals(s_home_choose)){
                        map.put("misc","0x00");
                        map.put("target_short_address",device.getTarget_short_address());
                        map.put("device_type",device.getDevice_type());
//                        clientMQTT.publishMessagePlusWithMap(map,"0x01");

                    }

                }
                //clientMQTT.publishMessagePlusWithMap(deviceList);
//                clientMQTT.publishMessagePlus("2023-02-19T08:30:00Z","1.2.3",null,"0x4AA5","0x03", "0x0101");
            }
        });

        bt_closeCurtain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Device device:devicelist){
                    Home room=device.getRoom();
                    int a=room.getCategory();
                    String home="0x0"+a;
                    if(home.equals(s_home_choose)){
                        map.put("misc","0x00");
                        map.put("target_short_address",device.getTarget_short_address());
                        map.put("device_type",device.getDevice_type());
//                        clientMQTT.publishMessagePlusWithMap(map,"0x00");

                    }


                }
//                clientMQTT.publishMessagePlus("2023-02-19T08:30:00Z","1.2.3",null,"0x4AA5","0x03", "0x0100");

            }
        });
    }
//        private void initDropdown1() {
//            ArrayAdapter starAdapter1= ArrayAdapter.createFromResource(getApplicationContext(), R.array.choose_home, android.R.layout.simple_spinner_item);
//            Spinner sp_dropdown=findViewById(R.id.curtain_choose_home);
//            starAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//            sp_dropdown.setAdapter(starAdapter1);
////        sp_dropdown.setSelection(0);
//            sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int arg1, long id) {
////                Toast.makeText(AdustTheCurtain.this, String.valueOf(arg1), Toast.LENGTH_SHORT).show();
////                Toast.makeText(AdustTheCurtain.this, String.valueOf(id), Toast.LENGTH_SHORT).show();
//                    home_choose=arg1;//转换为16进制加一个0x0
//                    s_home_choose="0x0"+home_choose;
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });
//
//
//        }
}


