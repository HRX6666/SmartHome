package com.example.smarthome.Database.Scene;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthome.R;

public class TimeActivity extends AppCompatActivity {
    private Button add_time;
    private Button time_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addoreditmodel);
        add_time=findViewById(R.id.add_time);
        time_back=findViewById(R.id.time_back);
        add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        time_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
