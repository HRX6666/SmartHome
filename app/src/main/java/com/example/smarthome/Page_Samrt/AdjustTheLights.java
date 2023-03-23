package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.smarthome.Database.Device;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.R;
import com.example.smarthome.View.FButton;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class AdjustTheLights extends AppCompatActivity {
    public static final String DEVICE_TYPE="device_type";
    public static final String NETWORK_FLAG="network_flag";
    public static final String TARGET_LONG_ADDRESS="target_long_address";
    public static final String TARGET_SHORT_ADDRESS="target_short_address";
    public static final String CONTROLLER_LONG_ADDRESS="controller_long_address";

    Toolbar lights_tb;
    private    ClientMQTT clientMQTT;
    private TextView isOnline;
    private FButton open_light;
    private FButton shut_light;
    private Spinner spinner_model;
    private Spinner spinner_home;
    private String target_short_address;
    private String controller_long_address;
    private String target_long_address;
    private String network_flag;
    private String device_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_the_lights);
        isOnline=findViewById(R.id.isOnline);
        lights_tb=findViewById(R.id.lights_tb);
        open_light=findViewById(R.id.trail_open);
        shut_light=findViewById(R.id.trail_shut);
        spinner_model=findViewById(R.id.lights_choose_model);
        spinner_home=findViewById(R.id.lights_choose_home);
        SeekBar seekBar_bright=findViewById(R.id.brightness);
        lights_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initmodel();
        inithome();
        clientMQTT=new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(AdjustTheLights.this);

        Intent intent=getIntent();
        controller_long_address=intent.getStringExtra(AdjustTheLights.CONTROLLER_LONG_ADDRESS);
        target_short_address=intent.getStringExtra(AdjustTheLights.TARGET_SHORT_ADDRESS);
        target_long_address=intent.getStringExtra(AdjustTheLights.TARGET_LONG_ADDRESS);
        network_flag=intent.getStringExtra(AdjustTheLights.NETWORK_FLAG);
        device_type=intent.getStringExtra(AdjustTheLights.DEVICE_TYPE);
        if(device_type!=null)
            switch (network_flag){
            case "00":isOnline.setText("离线");
                open_light.setEnabled(false);
                shut_light.setEnabled(false);
                open_light.setBackgroundResource(R.drawable.gray_bt);
                shut_light.setBackgroundResource(R.drawable.gray_bt);
                break;//////////////加入一个入网判断，判断是否入网，否则直接推出network显示在线，应该再次获取终端信息
            case "01":isOnline.setText("在线");
                open_light.setBackgroundResource(R.drawable.gray_bt);
                shut_light.setBackgroundResource(R.drawable.gray_bt);
                open_light.setEnabled(true);
                shut_light.setEnabled(true);
                break;
        }
        seekBar_bright.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                clientMQTT.publishMessagePlus("2023-02-19T08:30:00Z","1.2.3",null,"0x4A69","0x01", "0x01");
//                Toast.makeText(AdjustTheLights.this, , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(AdjustTheLights.this, "start!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(AdjustTheLights.this, "finish!", Toast.LENGTH_SHORT).show();

            }
        });
        open_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                clientMQTT.startReconnect(AdjustTheLights.this);
                clientMQTT.publishMessagePlus(null,target_short_address,"0x01", "0x0401","0x02");
                Toast.makeText(AdjustTheLights.this, "开灯!", Toast.LENGTH_SHORT).show();
            }
        });
        shut_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                clientMQTT.startReconnect(AdjustTheLights.this);
                clientMQTT.publishMessagePlus(null,target_short_address,"0x01", "0x0401","0x02");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                LocalDateTime localDateTime = LocalDateTime.now();
                DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ISO_DATE_TIME;
                String iso=dateTimeFormatter.format(localDateTime);


                Toast.makeText(AdjustTheLights.this, iso, Toast.LENGTH_SHORT).show();
            }
        });

        spinner_model.setPopupBackgroundResource(R.drawable.bg);//下拉背景,可以优化一下

        spinner_home.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String[] spinner_arr= getResources().getStringArray(R.array.choose_lights_model);
//                String choose_model=spinner_arr[position];
                //获得目标地址
                switch (position){
                    case 1:;
                    case 2:;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String[] spinner_arr= getResources().getStringArray(R.array.choose_lights_model);
                String choose_model=spinner_arr[position];
//                Toast.makeText(AdjustTheLights.this, choose_model, Toast.LENGTH_SHORT).show();
//                Toast.makeText(AdjustTheLights.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                //灯控模式
                switch (position){//不同房间类型和全屋进入应该是有不同短地址来区分，通过判断选择哪个屋子执行，在根据此来发送不同的短地址
//                    case 1:clientMQTT.publishMessagePlus("2023-02-19T08:30:00Z","1.2.3",null,"0x4AA5","0x01", "0x0105");
//                    case 2:clientMQTT.publishMessagePlus("2023-02-19T08:30:00Z","1.2.3",null,"0x4AA5","0x01", "0x0106");;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Device device=new Device();
        device.setIsUpdate(0);//这个还是留着好
        device.updateAll();
       clientMQTT=null;//防止后台多次处理数据，这个由于退出后switch仍保存之前状态，那就不能置null了
    }
    private void initmodel() {
        ArrayAdapter starAdapter_model= ArrayAdapter.createFromResource(getApplicationContext(),R.array.choose_lights_model, android.R.layout.simple_spinner_item);
        Spinner sp_dropdown=findViewById(R.id.lights_choose_model);
        starAdapter_model.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_dropdown.setPrompt("请选择模式");
        sp_dropdown.setAdapter(starAdapter_model);
        sp_dropdown.setSelection(0);
        sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void inithome() {
        ArrayAdapter starAdapter_home=ArrayAdapter.createFromResource(getApplicationContext(),R.array.choose_home, android.R.layout.simple_spinner_item);
        Spinner sp_dropdown=findViewById(R.id.lights_choose_home);
        sp_dropdown.setPrompt("请选择房间");
        sp_dropdown.setAdapter(starAdapter_home);
        sp_dropdown.setSelection(0);
        sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}