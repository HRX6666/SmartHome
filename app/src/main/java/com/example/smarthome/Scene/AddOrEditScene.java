package com.example.smarthome.Scene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.R;

public class AddOrEditScene extends AppCompatActivity {
    private Button add_condition;
    private Button add_task;
    private RecyclerView recy_condition;
    private RecyclerView recy_mission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addoreditmodel);
        add_condition=findViewById(R.id.add_condition);
        add_task=findViewById(R.id.add_mission);
        recy_condition=findViewById(R.id.recy_condition);
        recy_mission=findViewById(R.id.recy_mission);
        initButton();
    }
    //1.进入后先遍历删除所有Temp数据，之后再创建一个新的temp来储存
    //findfirst来找到当前temp
    //2.智能设备作为条件可以先弄灯的，空调那些可以先不显示
    //灯的就开关嘛
    //3.编写适配器，暂时只显示名称图标和开关
    //4.还有电器的自定义命名
    //
    private void initButton(){


        add_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddOrEditScene.this,ConditionActivity.class);
                //再设计一个暂存类Temp来储存设计好的条件和任务，如果场景保存了就存入Scene中，之后删除Temp中数据，如果没有，就删除Temp中数据
                startActivity(intent);

            }
        });
        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddOrEditScene.this,MissionActivity.class);
                startActivity(intent);

            }
        });


    }
}

