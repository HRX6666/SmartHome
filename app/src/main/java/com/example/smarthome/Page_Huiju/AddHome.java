package com.example.smarthome.Page_Huiju;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.smarthome.Database.AddHomes;
import com.example.smarthome.Database.AddModel;
import com.example.smarthome.Page_Samrt.Tesk;
import com.example.smarthome.R;
import com.example.smarthome.View.SelfTextView;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;

//TODO huiJu没换
public class AddHome extends AppCompatActivity {
    View view;
    Toolbar addHome_back;
    Button create_home;
    EditText home_name;
    AddHomes addHomes=new AddHomes();
    RecyclerView device_list;
    String name_hm;
    String time="0";
    public   Integer dra=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home);
        addHome_back=findViewById(R.id.addHome_back);
        device_list=findViewById(R.id.device_list);
        Intent intent=getIntent();
        time=intent.getStringExtra("time");
        if(time!=null)
            addHomes=LitePal.where("time = ?",time).findFirst(AddHomes.class);
        init();
    }
    private void init() {
        create_home=findViewById(R.id.create_home);
        home_name=findViewById(R.id.home_name);
        if(!time.equals("0"))
            home_name.setText(addHomes.getHome());
        addHome_back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        create_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_hm=home_name.getText().toString();    //获取输入框值
                //判断输入框中是否有值
                if(TextUtils.isEmpty(name_hm)){
                    Toast.makeText(AddHome.this,"请输入房间名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date=new Date(System.currentTimeMillis());
                String time1=simpleDateFormat.format(date);
                AddHomes addHomes=new AddHomes();
                addHomes.setHome(name_hm);
                addHomes.setTime(time1);
                if(time==null)
                    addHomes.save();
                else
                    addHomes.updateAll("time = ?",time);
                finish();
            }
        });
    }
    private void recyclerView(){

    }
}