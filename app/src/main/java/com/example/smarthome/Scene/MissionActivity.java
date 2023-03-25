package com.example.smarthome.Scene;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.R;

import org.litepal.LitePal;

public class MissionActivity extends AppCompatActivity {
    LinearLayout sysMessage;
    LinearLayout smart_device;
    LinearLayout scene_control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missionactivity);
        sysMessage=findViewById(R.id.sysMessage);
        smart_device=findViewById(R.id.smart_device);
        scene_control=findViewById(R.id.scene_control);
        initLayout();
    }

    private void initLayout(){

        sysMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });

        smart_device.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });
        scene_control.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });

    }

}
