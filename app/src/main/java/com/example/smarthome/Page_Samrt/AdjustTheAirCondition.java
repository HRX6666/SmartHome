package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.ObjectAnimator;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.smarthome.R;
import com.example.smarthome.View.CustomizeGoodsAddView;
import com.example.smarthome.View.air_utils.AirBoardView;

public class AdjustTheAirCondition extends AppCompatActivity {
            Toolbar air_tb;
            ObjectAnimator animation_min,animation_mid,animation_max;
            Boolean start_min=false;
            Boolean start_mid=false;
            Boolean start_max=false;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetPrefix;
    private LinearInterpolator mPanLin=new LinearInterpolator();
    ImageView ib_wind_min,ib_wind_mid,ib_wind_max;
    private AirBoardView airview;
//            private CustomizeGoodsAddView customizeGoodsAddView;
//            private static int temperature;
            private int maxNum=30;
            private int minNum=16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_the_air_condition);
        air_tb=findViewById(R.id.air_tb);
        ib_wind_min=findViewById(R.id.ib_wind_min);
        ib_wind_mid=findViewById(R.id.ib_wind_mid);
        ib_wind_max=findViewById(R.id.ib_wind_max);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        air_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();}
        });
//        inithome();
        rote();
        airview = (AirBoardView) findViewById(R.id.airView);
        airview.setOnAirClickListener(new AirBoardView.OnAirClickListener() {
            @Override
            public void onAirClick(String temp) {
//                Toast.makeText( AdjustTheAirCondition.this, temp, Toast.LENGTH_SHORT).show();
            }
        });
//        initwindspeed();
//        initadd();
    }

        private void rote() {

            ib_wind_min.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    animation_min=ObjectAnimator.ofFloat(ib_wind_min,"rotation",0f,360.0f);
                    animation_min.setInterpolator(new LinearInterpolator());//匀速
                    animation_min.setRepeatCount(-1);//无线循环
                    animation_min.setDuration(2000);
                    start_min=true;
                    if(start_mid==true){
                        animation_mid.pause();
                    }
                    if(start_max==true){
                        animation_max.pause();
                    }
                    if(start_min==true){
                        animation_min.pause();
                    }

                    animation_min.start();

                }
            });
            ib_wind_mid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animation_mid=ObjectAnimator.ofFloat(ib_wind_mid,"rotation",0f,360.0f);
                    animation_mid.setInterpolator(new LinearInterpolator());//匀速
                    animation_mid.setRepeatCount(-1);//无线循环
                    animation_mid.setDuration(1300);
                    start_mid=true;
                    if(start_min==true){
                        animation_min.pause();
                    }
                    if(start_max==true){
                        animation_max.pause();
                    }
                    if(start_mid==true){
                        animation_mid.pause();
                    }
                    animation_mid.start();

                }
            });
            ib_wind_max.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animation_max=ObjectAnimator.ofFloat(ib_wind_max,"rotation",0f,360.0f);
                    animation_max.setInterpolator(new LinearInterpolator());//匀速
                    animation_max.setRepeatCount(-1);//无线循环
                    animation_max.setDuration(900);
                    start_max=true;
                    if(start_min==true){
                        animation_min.pause();
                    }
                    if(start_mid==true){
                        animation_mid.pause();
                    }
                    if(start_max==true){
                        animation_max.pause();
                    }
                    animation_max.start();

                }
            });

        }
//
//    private void initadd() {
//        customizeGoodsAddView=findViewById(R.id.customizeGoodsAddView);
//        customizeGoodsAddView.setMaxValue(maxNum);
//        customizeGoodsAddView.setMinValue(minNum);
//        customizeGoodsAddView.setValue(temperature);//用来更新选择不同房间后下方温度的变化
//        customizeGoodsAddView.setOnValueChangeListene(new CustomizeGoodsAddView.OnValueChangeListener() {
//            @Override
//            public void onValueChange(int value) {
//                if(value>maxNum){
//                    customizeGoodsAddView.setValue(maxNum);
//
//                }else{
//
//                }
//            }
//        });
//    }


//    private void initwindspeed() {
//        ArrayAdapter starAdapter_airwindspeed = ArrayAdapter.createFromResource(getApplicationContext(), R.array.choose_wind_speed, android.R.layout.simple_spinner_item);
//        Spinner sp_dropdown = findViewById(R.id.air_choose_windspeed);
//        starAdapter_airwindspeed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_dropdown.setPrompt("请选择风速");
//        sp_dropdown.setAdapter(starAdapter_airwindspeed);
//        sp_dropdown.setSelection(0);
//        sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parenAt, View view, int arg2, long id) {
//               List<Device> deviceList= LitePal.where("source_command= ?","0x02").find(Device.class);
//                for(Device devices:deviceList) {
//                    String source_long_address = devices.getSource_long_address();
////                    temperature = devices.getAir_temp();
//                    String tem=devices.getSource_data();
//                    String ee=tem.substring(2,3);
//                    temperature=Integer.parseInt(tem.substring(2,4));
////温度不显示，只是用来判断工作状态  temp1>temp2-->制热 temp1(空调吹气口的温度，temp2空调下部的温度)
//                //Litepal好像没存
//                }
//                initadd();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//    }

//    private void inithome() {
//        ArrayAdapter starAdapter_airhome = ArrayAdapter.createFromResource(getApplicationContext(), R.array.choose_home, android.R.layout.simple_spinner_item);
//        Spinner sp_dropdown = findViewById(R.id.air_choose_home);
//        sp_dropdown.setDropDownVerticalOffset(android.R.layout.simple_spinner_dropdown_item);
//        sp_dropdown.setPrompt("请选择房间");
//        sp_dropdown.setAdapter(starAdapter_airhome);
//        sp_dropdown.setSelection(0);
//        sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parenAt, View view, int arg1, long id) {
//
//                //房间选完后在调用一次init方法，大概能刷新数据？god sees
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//    }
}
