package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.smarthome.R;
import com.example.smarthome.Scene.TimeActivity;

public class Tesk extends AppCompatActivity {
    TextView temperature,time,humidity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tesk);
        inint();
        touch();
    }

    private void inint() {
        temperature=findViewById(R.id.set_terperature);
        time=findViewById(R.id.set_time);
        humidity=findViewById(R.id.set_humidity);//湿度
    }
    private void touch() {
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Tesk.this,Temperature.class);
                startActivity(intent1);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(Tesk.this, TimeActivity.class);
                startActivity(intent2);
            }
        });
        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(Tesk.this, Humidity.class);
                startActivity(intent2);
            }
        });
    }

}