package com.example.smarthome.Scene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthome.R;

public class AddOrEditScene extends AppCompatActivity {
    private Button add_condition;
    private Button add_task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addoreditmodel);
        add_condition=findViewById(R.id.add_condition);
        add_task=findViewById(R.id.add_task);
    }
    private void initButton(){
        add_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddOrEditScene.this,ConditionActivity.class);
            }
        });
        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
            }
        });


    }
}
