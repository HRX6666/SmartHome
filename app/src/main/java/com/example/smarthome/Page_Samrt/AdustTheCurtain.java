package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.smarthome.Database.Device;
import com.example.smarthome.Database.Room;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.R;
import com.example.smarthome.View.ArcSeekBar;

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
    private CardView bt_openAll;
//    private Button bt_openMid;
    private CardView bt_closeCurtain;
    private int home_choose;
    private String s_home_choose;
    private  ArcSeekBar deep;
    //下拉框进入默认是全屋，进入界面时应该根服务器同步数据，设置当前设备状态是怎么样的，那个seekbar也一样，要根据实际情况来变
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adust_the_curtain);
        curtain_tb = findViewById(R.id.curtain_tb);
        bt_openAll = findViewById(R.id.open_all);
        deep=findViewById(R.id.deep);

        bt_closeCurtain = findViewById(R.id.close_curtain);
//        initDropdown1();
        clientMQTT = new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(AdustTheCurtain.this);
        Intent intent=getIntent();
        String target_long_address=intent.getStringExtra(Device.TARGET_LONG_ADDRESS);
        Device device=LitePal.where("target_long_address = ?",target_long_address).findFirst(Device.class);
        String target_short_address=device.getTarget_short_address();
        String device_type=device.getDevice_type();

        deep.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String extent=String.valueOf(progress);
                if(extent.length()==1){
                    extent="0"+extent;
                }
                clientMQTT.publishMessagePlus(null,target_short_address,"0x"+device_type,"0x"+extent,"0x01");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        bt_openAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientMQTT.publishMessagePlus(null,target_short_address,"0x"+device_type,"0x80","0x01");
            }
        });

        bt_closeCurtain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientMQTT.publishMessagePlus(null,target_short_address,"0x"+device_type,"0x00","0x01");
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


