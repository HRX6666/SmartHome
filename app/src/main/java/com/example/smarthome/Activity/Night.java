package com.example.smarthome.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;



import com.example.smarthome.R;

public class Night extends AppCompatActivity {
            Toolbar night_tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night);
        night_tb=findViewById(R.id.night_tb);
        night_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initcurtain();
        initlights();
        initmentor();
    }
    private void initcurtain() {
        ArrayAdapter adapter_curtain=ArrayAdapter.createFromResource(getApplicationContext(),R.array.choose_curtain_model, android.R.layout.simple_spinner_item);
        Spinner sp_dropdown=findViewById(R.id.night_curtain);
        adapter_curtain.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_dropdown.setPrompt("请选择模式");
        sp_dropdown.setAdapter( adapter_curtain);
        sp_dropdown.setSelection(0);
        sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void initlights() {
        ArrayAdapter adapter_lights=ArrayAdapter.createFromResource(getApplicationContext(),R.array.choose_lights_model, android.R.layout.simple_spinner_item);
        Spinner sp_dropdown=findViewById(R.id.night_lights);
        adapter_lights.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_dropdown.setPrompt("请选择模式");
        sp_dropdown.setAdapter(adapter_lights);
        sp_dropdown.setSelection(0);
        sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void initmentor() {
        ArrayAdapter adapter_mentor=ArrayAdapter.createFromResource(getApplicationContext(),R.array.choose_mentor, android.R.layout.simple_spinner_item);
        Spinner sp_dropdown=findViewById(R.id.night_mentor);
        adapter_mentor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_dropdown.setPrompt("请选择模式");
        sp_dropdown.setAdapter(adapter_mentor);
        sp_dropdown.setSelection(0);
        sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position3, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}