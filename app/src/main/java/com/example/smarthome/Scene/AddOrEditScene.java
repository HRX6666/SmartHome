package com.example.smarthome.Scene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthome.R;

import java.net.Inet4Address;

public class AddOrEditScene extends AppCompatActivity {
    private Button add_condition;
    private Button add_task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addoreditmodel);
        add_condition=findViewById(R.id.add_condition);
        add_task=findViewById(R.id.add_mission);
    }
    private void initButton(){
        add_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddOrEditScene.this,ConditionActivity.class);
                //再设计一个暂存类Temp来储存设计好的条件和任务，如果场景保存了就存入Scene中，之后删除Temp中数据，如果没有，就删除Temp中数据

            }
        });
        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
            }
            Intent intent=new Intent(AddOrEditScene.this,MissionActivity.class);

        });


    }
}
