package com.example.smarthome.Scene;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.Page_Samrt.Condition1;
import com.example.smarthome.R;
import com.example.smarthome.View.CustomizeGoodsAddView;
import com.example.smarthome.View.FButton;

import org.litepal.LitePal;

public class ConditionActivity extends AppCompatActivity {
    LinearLayout hand_click;
    LinearLayout time;
    LinearLayout smart_sensors;
    LinearLayout scene_control;
    Toolbar toolbar;
    private CustomizeGoodsAddView customizeGoodsAddView;
    private static int temperature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_condition);
        hand_click=findViewById(R.id.hand_click);
        time=findViewById(R.id.time);
        smart_sensors=findViewById(R.id.smart_sensors);
        scene_control=findViewById(R.id.scene_control);
        toolbar=findViewById(R.id.add_condition_back);
        initLayout();


    }
private void initLayout(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hand_click.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Temp temp= LitePal.findLast(Temp.class);
                temp.setIsClick("1");
                temp.save();
//conditionList整体用wrapcontent,确保随内容变化大小
                return true;
            }
        });
    time.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Intent intent=new Intent(ConditionActivity.this,TimeActivity.class);
            //再设计一个暂存类Temp来储存设计好的条件和任务，如果场景保存了就存入Scene中，之后删除Temp中数据，如果没有，就删除Temp中数据
            startActivity(intent);
            return true;
        }
    });

    smart_sensors.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
//            Intent intent=new Intent(ConditionActivity.this, Condition1.class);

            return true;
        }
    });
    scene_control.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            return true;
        }
    });
        customizeGoodsAddView=findViewById(R.id.select_temp);
        customizeGoodsAddView.setMaxValue(30);
        customizeGoodsAddView.setMinValue(16);
        customizeGoodsAddView.setValue(temperature);//用来更新选择不同房间后下方温度的变化
        customizeGoodsAddView.setOnValueChangeListene(new CustomizeGoodsAddView.OnValueChangeListener() {
            @Override
            public void onValueChange(int value) {
                if(value>30){
                    customizeGoodsAddView.setValue(30);


                }else if(value<16){
                    customizeGoodsAddView.setValue(16);

                }
            }
        });
}






}
