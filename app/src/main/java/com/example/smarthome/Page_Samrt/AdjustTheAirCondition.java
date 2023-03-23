package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.example.smarthome.Database.Device;
import com.example.smarthome.R;
import com.example.smarthome.View.CustomizeGoodsAddView;

import org.litepal.LitePal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjustTheAirCondition extends AppCompatActivity {
    Toolbar air_tb;
    private CustomizeGoodsAddView customizeGoodsAddView;
    private static int temperature;
    private int maxNum=30;
    private int minNum=16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_the_air_condition);
        air_tb=findViewById(R.id.air_tb);
        air_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inithome();
        initwindspeed();
        initmodle();
//        initadd();
    }

    private void initadd() {
        customizeGoodsAddView=findViewById(R.id.customizeGoodsAddView);
        customizeGoodsAddView.setMaxValue(maxNum);
        customizeGoodsAddView.setMinValue(minNum);
        customizeGoodsAddView.setValue(temperature);//用来更新选择不同房间后下方温度的变化
        customizeGoodsAddView.setOnValueChangeListene(new CustomizeGoodsAddView.OnValueChangeListener() {
            @Override
            public void onValueChange(int value) {
                if(value>maxNum){
                    customizeGoodsAddView.setValue(maxNum);

                }else{

                }
            }
        });
    }

    private void initmodle() {
        ArrayAdapter starAdapter_airmodel = ArrayAdapter.createFromResource(getApplicationContext(), R.array.choose_aircondition_modle, android.R.layout.simple_spinner_item);
        Spinner sp_dropdown = findViewById(R.id.air_choose_model);
        starAdapter_airmodel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_dropdown.setPrompt("请选择房间");
        sp_dropdown.setAdapter(starAdapter_airmodel);
        sp_dropdown.setSelection(0);
        sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parenAt, View view, int arg3, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initwindspeed() {
        ArrayAdapter starAdapter_airwindspeed = ArrayAdapter.createFromResource(getApplicationContext(), R.array.choose_wind_speed, android.R.layout.simple_spinner_item);
        Spinner sp_dropdown = findViewById(R.id.air_choose_windspeed);
        starAdapter_airwindspeed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_dropdown.setPrompt("请选择房间");
        sp_dropdown.setAdapter(starAdapter_airwindspeed);
        sp_dropdown.setSelection(0);
        sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parenAt, View view, int arg2, long id) {
                List<Device> deviceList= LitePal.where("device_type= ?","02").find(Device.class);
                if(!deviceList.isEmpty())
                    for(Device devices:deviceList) {
                        String source_long_address = devices.getTarget_long_address();
//                    temperature = devices.getAir_temp();
                        String tem=devices.getDevice_type();
                        String ee=tem.substring(2,3);
                        temperature=Integer.parseInt(tem.substring(2,4));
//温度不显示，只是用来判断工作状态  temp1>temp2-->制热 temp1(空调吹气口的温度，temp2空调下部的温度)
                        //Litepal好像没存
                    }
                else
                    temperature=16;
                initadd();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void inithome() {
        ArrayAdapter starAdapter_airhome = ArrayAdapter.createFromResource(getApplicationContext(), R.array.choose_home, android.R.layout.simple_spinner_item);
        Spinner sp_dropdown = findViewById(R.id.air_choose_home);
        sp_dropdown.setDropDownVerticalOffset(android.R.layout.simple_spinner_dropdown_item);
        sp_dropdown.setPrompt("请选择房间");
        sp_dropdown.setAdapter(starAdapter_airhome);
        sp_dropdown.setSelection(0);
        sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parenAt, View view, int arg1, long id) {

                //房间选完后在调用一次init方法，大概能刷新数据？god sees

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}