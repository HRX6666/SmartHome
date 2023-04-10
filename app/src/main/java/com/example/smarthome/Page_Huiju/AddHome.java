package com.example.smarthome.Page_Huiju;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.example.smarthome.Database.AddModel;
import com.example.smarthome.Page_Samrt.Tesk;
import com.example.smarthome.R;

import org.litepal.LitePal;

public class AddHome extends AppCompatActivity {
    View view;
    Toolbar toolbar;
    Button create_home;
    EditText home_name;
    String name_hm;
    public   Integer dra=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home);
//        toolbar=findViewById(R.id.addhome_tb);
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             finish();
//            }
//        });
        inite();
    }
    private void inite() {;
        create_home=findViewById(R.id.create_home);
        home_name=findViewById(R.id.home_name);
        create_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
                name_hm=home_name.getText().toString();    //获取输入框值
                //判断输入框中是否有值
                if(TextUtils.isEmpty(name_hm)){
                    Toast.makeText(AddHome.this,"请输入房间名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                AddModel addModel=new AddModel();
                addModel.setModel(name_hm);
                addModel.save();
                finish();


            }
        });
    }

}