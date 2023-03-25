package com.example.smarthome.Scene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthome.Database.Scene.C_Time;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.R;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class TimeActivity extends AppCompatActivity {
    private Button add_time;
    private Button time_back;
    private Button time_save;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeactivity);
        add_time=findViewById(R.id.add_time);
        time_back=findViewById(R.id.time_back);
        time_save=findViewById(R.id.time_save);
        time_save.setClickable(false);
        time_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp temp= LitePal.findLast(Temp.class);
                C_Time c_time=new C_Time();
                c_time.setTime(date);
                c_time.setTemp(temp);
                c_time.save();
                Intent intent=new Intent(TimeActivity.this,AddOrEditScene.class);
                //再设计一个暂存类Temp来储存设计好的条件和任务，如果场景保存了就存入Scene中，之后删除Temp中数据，如果没有，就删除Temp中数据
                startActivity(intent);
            }
        });
        add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dateDialog=new DateDialog(TimeActivity.this);
                dateDialog.show();
                dateDialog.onClickOkBtnLisitener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        date=dateDialog.getDate();
                        dateDialog.dismiss();
                        time_save.setClickable(true);
                        Toast.makeText(TimeActivity.this,date,Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        time_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TimeActivity.this,ConditionActivity.class);
                startActivity(intent);
            }
        });
    }

}
